package message;

import bittorrent.Constant;
import util.Util;

public class KeepAlive extends TCPBitTorrentPacket {
	public KeepAlive() { 
		setType(Constant.KEEPALIVE);
	}

	public byte[] getData() { 
		byte[] len = new byte[] {0, 0, 0, 0}; 
		return Util.concatAll(len);
	}
}
