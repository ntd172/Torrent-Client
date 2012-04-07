package message;

import util.Util;

public class NotInterested extends TCPBitTorrentPacket {
	public NotInterested() {
	}
	
	public byte[] getData() { 
		return Util.concatAll(new byte[] {0, 0, 0, 1}, new byte[] {3});
	}
}
