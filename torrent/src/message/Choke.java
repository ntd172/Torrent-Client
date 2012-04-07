package message;

import util.Util;

public class Choke extends TCPBitTorrentPacket{
	public Choke() { 
	}
	
	public byte[] getData() { 
		return Util.concatAll(new byte[] {0, 0, 0, 1}, new byte[] {0});
	}
	
}
