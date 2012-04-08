package message;

import bittorrent.Constant;
import util.Util;

public class UnChoke extends TCPBitTorrentPacket{
	public UnChoke() { 
		setType(Constant.UNCHOKE);
	}
	
	public byte[] getData() { 
		return Util.concatAll(new byte[] {0, 0, 0, 1}, new byte[] {1});
	}
}
