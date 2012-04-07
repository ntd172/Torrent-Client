package message;

import java.io.IOException;
import java.io.InputStream;

import util.Util;

public class Have extends TCPBitTorrentPacket{
	private int pieceIndex; 
	
	public Have(InputStream input, int size) throws IOException { 
		this.pieceIndex = Util.convertBytesToInt(Util.read(input, 4));
	}
	
	public Have(int pieceIndex) { 
		this.pieceIndex = pieceIndex;
	}
	
	public byte[] getData() { 
		byte[] len = Util.converIntToBytes(5, 4);
		byte[] id = new byte[] {4}; 
		byte[] piece = Util.converIntToBytes(pieceIndex, 4);
		return Util.concatAll(len, id , piece);
	}
}
