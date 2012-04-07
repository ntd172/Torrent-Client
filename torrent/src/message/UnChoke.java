package message;

import util.Util;

public class UnChoke extends TCPBitTorrentPacket{
	public UnChoke() { 
	}
	
	public byte[] getData() { 
		return Util.concatAll(new byte[] {1}, new byte[] {1});
	}
}
