package message;

import util.Util;

public class Interested extends TCPBitTorrentPacket {
	public Interested() { 
	}
	
	public byte[] getData() { 
		return Util.concatAll(new byte[] {0, 0, 0, 1}, new byte[] {2}); 
	}
}
