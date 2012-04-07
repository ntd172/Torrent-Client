package message;

import java.io.IOException;
import java.io.InputStream;

import util.Util;

public class Piece extends TCPBitTorrentPacket{
	private int index, begin; 
	private byte[] block;
	
	public Piece(InputStream input, int size) throws IOException { 
		index = Util.convertBytesToInt(Util.read(input, 4)); 
		begin = Util.convertBytesToInt(Util.read(input, 4)); 
		block = Util.read(input, size  - 9); 
	}
	public Piece(int index, int begin, byte[] block) { 
		this.index = index; 
		this.begin = begin; 
		this.block = block;
	}
	
	public byte[] getData() { 
		byte[] len = Util.converIntToBytes(9 + block.length + 8, 4);
		byte[] id = new byte[] {7}; 
		byte[] index = Util.converIntToBytes(this.index, 4); 
		byte[] begin = Util.converIntToBytes(this.begin, 4); 
		return Util.concatAll(len, id, index, begin, block);
	}
}
