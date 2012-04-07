package message;

import java.io.IOException;
import java.io.InputStream;

import util.Util;


public class Request extends TCPBitTorrentPacket{
	private int index, begin, length; 
	
	
	public Request(InputStream input, int size) throws IOException { 
		index = Util.convertBytesToInt(Util.read(input, 4));
		begin = Util.convertBytesToInt(Util.read(input, 4));
		length = Util.convertBytesToInt(Util.read(input, 4));
	}
	
	public Request(int index, int begin, int length) {
		this.index = index; 
		this.begin = begin; 
		this.length = length;
	}
	
	public byte[] getData() {
		byte[] len = new byte[] {0, 0, 0, 13}; 
		byte[] id = new byte[] {6};
		
		byte[] index = Util.converIntToBytes(this.index, 4); 
		byte[] begin = Util.converIntToBytes(this.begin, 4); 
		byte[] length = Util.converIntToBytes(this.length, 4); 
		
		return Util.concatAll(len, id, index, begin, length);
	}
}
