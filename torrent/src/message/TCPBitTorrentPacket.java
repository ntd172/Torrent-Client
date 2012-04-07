package message;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import util.Util;

public class TCPBitTorrentPacket implements BitTorrentPacket{
	
	protected InputStream input; 
	protected static TCPBitTorrentPacket packet;
	protected int type; 
	private int pieceIndex, index, begin, length, port;
	private byte[] bitfield, block, message;
	
	public TCPBitTorrentPacket() {
	}
	
	public TCPBitTorrentPacket(TCPBitTorrentPacket other) { 
		packet = other;
	}
	
	public TCPBitTorrentPacket(InputStream input) {
		this.input = input;
		matchMessage(); 
	}
	
	public TCPBitTorrentPacket(byte[] input) { 
		this.input = new ByteArrayInputStream(input);
	}
	
	public void matchMessage()  {
		try {
			byte[] numBytes = Util.read(input, 4); 
			int number = Util.convertBytesToInt(numBytes);
			if (number == 0) { 
				packet = new KeepAlive();
			} else { 
				byte id = Util.read(input, 1)[0]; 
				construct(id, number);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public void construct(int id, int size) throws IOException { 
		switch (id) { 
		case 0: 
			packet = new Choke(); break;
		case 1: 
			packet = new UnChoke(); break; 
		case 2: 
			packet = new Interested(); break; 
		case 3: 
			packet = new NotInterested(); break;
		case 4: 
			packet = new Have(input, size); break;
		case 5: 
			packet = new BitField(input, size); break;
		case 6: 
			packet = new Request(input, size); break; 
		case 7: 
			packet = new Piece(input, size); break;
		case 8: 
			packet = new Cancel(input, size); break;
		case 9: 
			packet = new Port(input, size); break;
		case 20: 
			packet = new Extended(input, size); break;
		default: 
			throw new IOException("Unknow id"); 
		}
	}
	
	@Override
	public byte[] getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getType() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void setType(int type) { 
		this.type = type;
	}
}
