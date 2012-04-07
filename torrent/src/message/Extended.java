package message;

import java.io.IOException;
import java.io.InputStream;

import util.Util;

public class Extended extends TCPBitTorrentPacket{
	private byte[] message; 
	
	public Extended(InputStream input, int size) throws IOException { 
		message = Util.read(input, size - 1);
	}
	public Extended(byte[] message) {
		this.message = message; 
	}
	
	
	public byte[] getData() { 
		byte[] len = Util.converIntToBytes(message.length + 1, 4);
		byte[] id = new byte[] {1};
		return Util.concatAll(len, id, message);
	}
}
