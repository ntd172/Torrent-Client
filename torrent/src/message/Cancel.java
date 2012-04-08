package message;

import java.io.DataInputStream;
import java.io.IOException;

import util.Util;
import bittorrent.Constant;

public class Cancel extends TCPBitTorrentPacket {
	private int begin, index, length;
	public Cancel(DataInputStream input, int size) throws IOException {
		index = Util.convertBytesToInt(Util.read(input, 4));
		begin = Util.convertBytesToInt(Util.read(input, 4));
		length = Util.convertBytesToInt(Util.read(input, 4));
		setType(Constant.CANCEL);
	}
	
	public byte[] getData() { 
		byte[] len = Util.converIntToBytes(13, 4);
		byte[] id = new byte[] {7}; 
		byte[] index = Util.converIntToBytes(this.index, 4); 
		byte[] begin = Util.converIntToBytes(this.begin, 4); 
		byte[] length = Util.converIntToBytes(this.length, 4); 
		
		return Util.concatAll(len, id, index, begin, length);
	}
}
