package message;

import bittorrent.Constant;
import util.Util;

public class Choke extends TCPBitTorrentPacket{
	public Choke() { 
		setType(Constant.CHOKE);
	}
	
	public byte[] getData() { 
		return Util.concatAll(new byte[] {0, 0, 0, 1}, new byte[] {0});
	}
	
}
