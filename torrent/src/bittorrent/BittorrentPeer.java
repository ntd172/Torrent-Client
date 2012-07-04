package bittorrent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import message.BitTorrentPacket;
import message.bittorrent.BitField;
import message.bittorrent.Extended;
import message.bittorrent.HandShake;
import message.bittorrent.Have;
import message.bittorrent.Interested;
import message.bittorrent.Piece;
import message.bittorrent.Port;
import message.bittorrent.Request;
import message.bittorrent.TCPBitTorrentPacket;
import message.bittorrent.UnChoke;
import util.Util;

public class BittorrentPeer extends Thread implements Protocol{
	static final int CAPACITY = 500;
	private Peer peer; 
	private BittorrentPeer[] peers;
	private byte[] infoHash; 
	private byte[] peerId;
	private byte[] reserved; 
	private boolean handshakeCheck = false;
	private boolean[][] goingDownload;
	private boolean[] bitmask;
	private int piecesSize;
	private SendThread send; 
	private ReceiveThread receive;
	private long trunkLength, fileSize;
	
	public BittorrentPeer(Peer peer, InfoTracker info) {
		this.peer = peer;
		this.infoHash = info.getInfoHash();
		this.peerId = info.getPeerId();
		this.piecesSize = info.getPieceSize();
		this.trunkLength = info.getPieceLength() / Constant.TRUNK_LENGTH;
		this.reserved = new byte[] {0x00, 0x00, 0x00, 0x00, 0x00, 0x10, 0x00, 0x05};
		this.fileSize = info.getFileSize();
		
		// TODO: sometimes there are no BitField packet in order to figure the valid packet
//		bitmask = new boolean[piecesSize];
//		Arrays.fill(bitmask, true);
		
		// check the status of downloading pieces
		goingDownload = new boolean[piecesSize][(int) trunkLength];
	}
	

	public void setPeers(BittorrentPeer[] peers) {
		// TODO Auto-generated method stub
		this.peers = peers;
	}
	
	public void run() { 
		// make connection to this peer
		try {
			Socket socket = new Socket(peer.getIp(), peer.getPort());
			DataInputStream input = new DataInputStream(socket.getInputStream()); 
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			
			// first need to make handshake with peer first
			handshake(input, output);
			
			if (handshakeCheck) { 
				send = new SendThread(output); 
				receive = new ReceiveThread(input);
				
				// start those threads 
				send.start();
				receive.start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void handshake(DataInputStream input, DataOutputStream output) throws IOException {
		// send handshake to peer
		HandShake handshake = new HandShake((byte) Constant.DEFAULT_BITTORRENT, Constant.DEFAULT_STRING, reserved, infoHash, peerId);
		output.write(handshake.getData());
		
		handshake = new HandShake(input);
		// already make connection
		
		byte[] reponseInfoHash = handshake.getInfoHash();
		if (!Arrays.equals(reponseInfoHash, infoHash)) { 
			if (Constant.DEBUG) { 
				System.out.println("[Bittorrent HandShake Different InfoHash] " + peer.getIp().getHostAddress() + ":" + peer.getPort());
				Util.printHex(reponseInfoHash);
				Util.printHex(infoHash);
			}
		} else { 
			if (Constant.DEBUG) { 
				System.out.println("[Bittorrent HandShake] " + peer.getIp().getHostAddress() + ":" + peer.getPort());
			}
			handshakeCheck = true;
		}
	}
	
	public synchronized boolean download(int piece) { 
		if (bitmask == null || (bitmask != null && bitmask[piece])) {
			goingDownload[piece] = new boolean[(int) trunkLength];
			Arrays.fill(goingDownload[piece], true);
			return true;
		}
		return false;
	}
	
	public Request makeRequest() { 
		int index = -1;
		int begin = 0;
		int length = (int) Constant.TRUNK_LENGTH;
		for (int i = 0; i < piecesSize; i++) { 
			if (bitmask == null || (bitmask != null && bitmask[i])) { 
				for (int j = 0; j < trunkLength; j++) { 
					if (goingDownload[i][j]) { 
						goingDownload[i][j] = false;
						index = i;
						begin = (int) (j * Constant.TRUNK_LENGTH);
						break;
					}
				}
				
				if (index != -1) { 
					break;
				}
			}
		}
		if (index == piecesSize - 1) { 
			length = (int) Math.abs(fileSize - (Constant.TRUNK_LENGTH * (piecesSize-1) * trunkLength));
			begin = 0;
			Arrays.fill(goingDownload[index], false);
		}
		
		if (index == -1) { 
			send.setDone();
			receive.setDone();
		}
		return new Request(index, begin, length);
	}
	
	public void writeToDisk(Piece piece) { 
		int part = piece.getBegin() / piece.getPieceLen();
		String nameFile = piece.getIndex() + "_" + part;
		File file = new File(Constant.DIR,nameFile);
		
		Util.writeData(file, piece.getPiece());
	}

	@Override
	public void updatePieces(List<Piece> pieces) {
		// TODO Auto-generated method stub
		if (handshakeCheck) { 
			for (Iterator<Piece> iter = pieces.iterator(); iter.hasNext();) { 
				Piece p = iter.next();
				Have have = new Have(p.getId());
				synchronized(send) { 
					send.sendPacket(have);
				}
			}
		}
	}
	
	class SendThread extends Thread {
		DataOutputStream out;
		boolean done = false;
		BlockingQueue<BitTorrentPacket> queuePacket = new ArrayBlockingQueue<BitTorrentPacket>(CAPACITY);
		
		public SendThread(DataOutputStream out) { 
			this.out = out;
		}
		
		public void run() { 
			while (!done) { 
				BitTorrentPacket packet  = queuePacket.poll(); 
				if (queuePacket.size() > CAPACITY * 0.7) { 
					if (packet.getType() == Constant.HAVE) 
						continue;
				}
				
				if (packet != null) {
					try {
						packet.write(out);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println(packet.getType());
						System.out.println("==================");
						((TCPBitTorrentPacket) packet).debug();
						System.out.println("==================");
						e.printStackTrace();
					}
				}
			}
		}
		
		public void sendPacket(BitTorrentPacket packet) { 
			queuePacket.add(packet);
		}
		
		public synchronized void setDone() { 
			done = true;
		}
	}
	
	class ReceiveThread extends Thread {
		DataInputStream input;
		boolean done = false;
		public ReceiveThread (DataInputStream input) { 
			this.input = input;
		}
		
		public void run() { 
			while (!done) { 
				TCPBitTorrentPacket packet = new TCPBitTorrentPacket(input).getInstance();
				if (packet == null) {
				} else {
					switch (packet.getType()) { 
					case Constant.BITFIELD: 
						handleBitField(packet);
						break;
					case Constant.PORT: 
						handlePort(packet);
						break;
					case Constant.EXTENDED:
						handleExtended(packet);
						break;
					case Constant.UNCHOKE: 
						handleUnChoke(packet);
						break;
					case Constant.PIECE: 
						handlePiece(packet);
						break;
					default:
						// just do nothing
					}
				}
			}
		}
		
		public void handleBitField(TCPBitTorrentPacket packet) { 
			if (Constant.DEBUG) { 
				Util.debug("recieving BitField");
			}
			BitField p = (BitField) packet;
			bitmask = p.getValid();
			
			// find other peers who can download invlalid pieces
			for (int i = 0; i < piecesSize; i++) { 
				if (!bitmask[i]) { 
					boolean found = false;
					for (int j = 0; j < peers.length; j++) {
						if (peers[j].download(i)) {
							found = true;
							break;
						}
					}
					if (found) {
						Util.debug("Can't download piece " + i + ".");
					}
				}
			}
			
			if (Constant.DEBUG) { 
				p.debug();
				Util.pause();
			}
			
		}
		
		public void handlePort(TCPBitTorrentPacket packet) { 
			if (Constant.DEBUG) { 
				Util.debug("recieving Port");
			}
			Port p = (Port) packet;
			peer.setPort(p.getPort());
			
			if (Constant.DEBUG) { 
				p.debug();
			}
		}
		
		public void handleExtended(TCPBitTorrentPacket packet) {
			if (Constant.DEBUG) { 
				Util.debug("recieving Extended");
			}
			Extended p = (Extended) packet;
			// need to extract the information from here
			// TODO: use this one for testing 
			// print it out and see what we should do wit it
			if (Constant.DEBUG) {
				p.debug();
				Util.pause();
			}	
			
			Interested interested = new Interested();
			send.sendPacket(interested);
			if (Constant.DEBUG) { 
				interested.debug();
				Util.pause();
			}
		}
		
		public void handleUnChoke(TCPBitTorrentPacket packet) { 
			if (Constant.DEBUG) { 
				Util.debug("recieving UnChoke");
			}
			UnChoke p = (UnChoke) packet;
			
			// make the request and send to peer
			Request request = makeRequest();
			send.sendPacket(request);
			
			// print out sending request
			if (Constant.DEBUG) { 
				p.debug();
				Util.pause();
				
				request.debug();
				Util.pause();
			}
		}
		
		public void handlePiece(TCPBitTorrentPacket packet) { 
			if (Constant.DEBUG) { 
				Util.debug("recieving Piece");
			}
			Piece p = (Piece) packet;
			writeToDisk(p);
			 
			if (Constant.DEBUG) { 
				p.debug();
				Util.pause();
			}
			
			Request request = makeRequest();
			if (Constant.DEBUG) { 
				request.debug();
				Util.pause();
			}
			send.sendPacket(request);
		}
	
		public synchronized void setDone() { 
			done = true;
		}
	}
}
