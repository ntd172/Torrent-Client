package message;

import java.io.IOException;
import java.io.InputStream;

import util.Util;

public class Port extends TCPBitTorrentPacket{
	private int port;
	
	public Port(InputStream input, int size) throws IOException { 
		port = Util.convertBytesToInt(Util.read(input, 4));
	}
	
	public Port(int port) { 
		this.port = port;
	}
	
	public byte[] getData() { 
		byte[] len = new byte[] { 0, 0, 0, 3}; 
		byte[] id = new byte[] {9}; 
		byte[] port = Util.converIntToBytes(this.port, 2);
		return Util.concatAll(len, id, port);
	}
}
