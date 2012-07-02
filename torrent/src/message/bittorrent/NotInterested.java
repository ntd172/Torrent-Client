package message.bittorrent;

import bittorrent.Constant;
import util.Util;

public class NotInterested extends TCPBitTorrentPacket {
	public NotInterested() {
		setType(Constant.NOTINTERESTED);
	}
	
	public byte[] getData() { 
		return Util.concatAll(new byte[] {0, 0, 0, 1}, new byte[] {3});
	}
	
	public void debug() { 
		Util.debug("Not Interested");
		Util.debug("");
	}
}
