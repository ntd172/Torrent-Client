package message.bittorrent;

import java.io.DataInputStream;
import java.io.IOException;

import util.Util;
import bittorrent.Constant;

public class Piece extends TCPBitTorrentPacket{
	private int index, begin; 
	private byte[] block;
	
	public Piece(DataInputStream input, int size) throws IOException { 
		index = Util.convertBytesToInt(Util.read(input, 4)); 
		begin = Util.convertBytesToInt(Util.read(input, 4)); 
		block = Util.read(input, size  - 9); 
		setType(Constant.PIECE);
	}
	public Piece(int index, int begin, byte[] block) { 
		this.index = index; 
		this.begin = begin; 
		this.block = block;
		setType(Constant.PIECE);
	}
	
	public byte[] getData() { 
		byte[] len = Util.converIntToBytes(9 + block.length, 4);
		byte[] id = new byte[] {7}; 
		byte[] index = Util.converIntToBytes(this.index, 4); 
		byte[] begin = Util.converIntToBytes(this.begin, 4); 
		return Util.concatAll(len, id, index, begin, block);
	}
	
	public int getIndex() {
		return index;
	}
	
	public int getLen() { 
		return 9 + block.length + 4;
	}
	
	public int getId() { 
		return 7;
	}
	
	public int getBegin() { 
		return begin; 
	}
	
	public int getPieceLen() { 
		return block.length;
	}
	public byte[] getPiece() { 
		return block;
	}
	
	
	public void debug() { 
		Util.debug("Piece"); 
		Util.debug("Id = " + index);
		Util.debug("Begin = " + begin);
		Util.debug("Block length = " + block.length);
		Util.debug("");
	}
}
