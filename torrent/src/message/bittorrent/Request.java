package message.bittorrent;

import java.io.DataInputStream;
import java.io.IOException;

import util.Util;
import bittorrent.Constant;


public class Request extends TCPBitTorrentPacket{
	private int index, begin, length; 
	
	
	public Request(DataInputStream input, int size) throws IOException { 
		index = Util.convertBytesToInt(Util.read(input, 4));
		begin = Util.convertBytesToInt(Util.read(input, 4));
		length = Util.convertBytesToInt(Util.read(input, 4));
		setType(Constant.REQUEST);
	}
	
	public Request(int index, int begin, int length) {
		this.index = index; 
		this.begin = begin; 
		this.length = length;
		setType(Constant.REQUEST);
	}
	
	public void setIndex(int index) { 
		this.index = index;
	}
	
	public void setBegin(int begin) { 
		this.begin = begin;
	}
	
	public void setLength(int length) { 
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
	
	public void debug() { 
		Util.debug("Sending Request"); 
		Util.debug("index = " + index);
		Util.debug("begin = " + begin);
		Util.debug("length = " + length);
		Util.debug("");
	}
}
