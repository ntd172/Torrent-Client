package message;

import java.io.DataInputStream;
import java.io.IOException;

import util.Util;
import bittorrent.Constant;

public class Port extends TCPBitTorrentPacket{
	private int port;
	
	public Port(DataInputStream input, int size) throws IOException { 
		byte[] data = Util.read(input, 2);
		port =  Util.convertBytesToInt(data);
		setType(Constant.PORT);
	}
	
	public Port(short port) { 
		this.port = port;
		setType(Constant.PORT);
	}
	
	public byte[] getData() { 
		byte[] len = new byte[] { 0, 0, 0, 3}; 
		byte[] id = new byte[] {9}; 
		byte[] port = Util.converShorttoBytes((short)this.port, 2);
		return Util.concatAll(len, id, port);
	}
}
