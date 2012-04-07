package message;

import util.Util;

public class KeepAlive extends TCPBitTorrentPacket {
	public KeepAlive() { 
	}

	public byte[] getData() { 
		byte[] len = new byte[] {0, 0, 0, 0}; 
		return Util.concatAll(len);
	}
}
