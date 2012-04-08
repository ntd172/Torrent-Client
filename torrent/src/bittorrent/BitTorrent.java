package bittorrent;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import message.BitField;
import message.BitTorrentPacket;
import message.HandShake;
import message.Interested;
import message.Piece;
import message.Request;
import message.TCPBitTorrentPacket;
import message.UnChoke;
import util.Util;


public class BitTorrent {
	protected Socket socket = null;
	protected DataInputStream input = null; 
	protected OutputStream output = null; 
	
	public BitTorrent(InetAddress inet, int port) throws IOException { 
		socket = new Socket(inet, port);
		input = new DataInputStream(socket.getInputStream());
		output = socket.getOutputStream();
	}
	
	public BitTorrent(byte[] addr, int port) throws IOException { 
		InetAddress inet = InetAddress.getByAddress(addr);
		socket = new Socket(inet, port); 
		input = new DataInputStream(socket.getInputStream());
		output = socket.getOutputStream();
	}
	
	public InputStream getInputStream() { 
		return input;
	}
	
	public OutputStream getOuputStream() { 
		return output;
	}
	
	
	public void send(BitTorrentPacket packet) { 
		try {
			output.write(packet.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Can't send data packet to peer.");
		}
	}
	
	public BitTorrentPacket receive(boolean handshake) { 
		if (handshake) { 
			return new HandShake(input);
		} else  {
			return new TCPBitTorrentPacket(input).getInstance();
		}
	}
	
	// Override
	public void finalize() { 
		try {
			socket.close();
			input.close();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException { 
		
		int pieces = 179; 
		
		byte[] addr = new byte[] {89, (byte) 153, 98, 89};
		int port = 6881;
		BitTorrent torrent = new BitTorrent(addr, port);
		System.out.println("here");
		
		// send handshake from host to peer
		DataInputStream input = new DataInputStream(new FileInputStream("test/hand-shake-version-2"));
		HandShake hs = new HandShake(input);
		torrent.send(hs);
		Util.printHex(hs.getData());
		
		// receive handshake from peer
		hs = (HandShake) torrent.receive(true);
		System.out.println("here");
		
		// send Interested from host to peer
		input = new DataInputStream(new FileInputStream("test/interested"));
		Interested interested = (Interested) new TCPBitTorrentPacket(input).getInstance();
		torrent.send(interested);
		
		BitField bitfield = null;
		while (true) {
			BitTorrentPacket packet = torrent.receive(false);
			if (packet.getType() == Constant.BITFIELD) { 
				bitfield = (BitField) packet;
				break;
			}
		}
		
		UnChoke unchoke = null;
		while (true) {
			BitTorrentPacket packet = torrent.receive(false);
			if (packet.getType() == Constant.UNCHOKE) { 
				unchoke = (UnChoke) packet;
				break;
			}
		}
		System.out.println(bitfield.getInvalidCount());
		
		
		input = new DataInputStream(new FileInputStream("test/request-version-2"));
		Request request = (Request) new TCPBitTorrentPacket(input).getInstance();
		Util.printHex(request.getData());
		torrent.send(request);
		System.out.println("send request");
		
		Piece piece = null;
		while (true) {
			BitTorrentPacket packet = torrent.receive(false);
			if (packet.getType() == Constant.PIECE) { 
				piece = (Piece) packet;
				break;
			}
		}
		
			
//		// receive extended from peer
//		BitTorrentPacket packet = null;
//		while (true) { 
//			packet =  torrent.receive(false);
//			if (packet.getType() == Constant.EXTENDED) { 
//				break;
//			}
//		}
//		
//		// receive BitField from peer
//		BitField bitfield = (BitField) torrent.receive(false);
//		System.out.println(bitfield.getInvalidCount());
//		
//		// just pass the message
//		for (int i = 0; i < 24; i++) { 
//			torrent.receive(false);
//		}
//		
//		// send extended from host to peer
//		input = new DataInputStream(new FileInputStream("test/extended-2"));
//		Extended extended = (Extended) new TCPBitTorrentPacket(input).getInstance();
//		torrent.send(extended);
//		
//		// send port from host to peer
//		input = new DataInputStream(new FileInputStream("test/port"));
//		Port portMessage = (Port) new TCPBitTorrentPacket(input).getInstance();
//		torrent.send(portMessage);
//		
//		// send other extended from host to peer
//		input = new DataInputStream(new FileInputStream("test/extended-3"));
//		extended = (Extended) new TCPBitTorrentPacket(input).getInstance();
//		torrent.send(extended);
//		
//		// send Interested from host to peer
//		input = new DataInputStream(new FileInputStream("test/interested"));
//		Interested interested = (Interested) new TCPBitTorrentPacket(input).getInstance();
//		torrent.send(interested);
//		
//		
//		portMessage = (Port) torrent.receive(false);
//		extended = (Extended) torrent.receive(false);
//		UnChoke unchoke = (UnChoke) torrent.receive(false);
//		
//		input = new DataInputStream(new FileInputStream("test/request"));
//		Request request = (Request) new TCPBitTorrentPacket(input).getInstance();
//		Random random = new Random();
//		for (int i = 0; i < 10; i++) { 
//			int index = random.nextInt(pieces);
//			if (bitfield.isValid(index)) { 
//				int begin = 0; 
//				int length = 0x4000;
//				
//				request = new Request(index, begin, length);
//				torrent.send(request);
//				
//				begin = 0x4000;
//				request = new Request(index, begin, length);
//				torrent.send(request);
//				
//				begin = 0x8000;
//				request = new Request(index, begin, length);
//				torrent.send(request);
//				
//				begin = 0xc000;
//				request = new Request(index, begin, length);
//				torrent.send(request);
//				
//				Piece piece = null;
//				while (true) {
//					packet = torrent.receive(false);
//					if (packet.getType() == Constant.PIECE) { 
//						piece = (Piece) packet;
//						break;
//					}
//				}
//				piece = (Piece) torrent.receive(false);
//				piece = (Piece) torrent.receive(false);
//				System.out.println(i);
//			}
//		}
	}
}
