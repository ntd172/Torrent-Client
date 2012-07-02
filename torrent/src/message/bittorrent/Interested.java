package message.bittorrent;

import bittorrent.Constant;
import util.Util;

public class Interested extends TCPBitTorrentPacket {
	public Interested() { 
		setType(Constant.INTERESTED);
	}
	
	public byte[] getData() { 
		return Util.concatAll(new byte[] {0, 0, 0, 1}, new byte[] {2}); 
	}
	
	public void debug() { 
		Util.debug("Interested");
		Util.debug("");
	}
}
