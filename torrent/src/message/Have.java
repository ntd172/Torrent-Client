package message;

import java.io.DataInputStream;
import java.io.IOException;

import util.Util;
import bittorrent.Constant;

public class Have extends TCPBitTorrentPacket{
	private int pieceIndex; 
	
	public Have(DataInputStream input, int size) throws IOException { 
		this.pieceIndex = Util.convertBytesToInt(Util.read(input, 4));
		setType(Constant.HAVE);
	}
	
	public Have(int pieceIndex) { 
		this.pieceIndex = pieceIndex;
		setType(Constant.HAVE);
	}
	
	public byte[] getData() { 
		byte[] len = Util.converIntToBytes(5, 4);
		byte[] id = new byte[] {4}; 
		byte[] piece = Util.converIntToBytes(pieceIndex, 4);
		return Util.concatAll(len, id , piece);
	}
}
