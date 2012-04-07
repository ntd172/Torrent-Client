package message;

import java.io.IOException;
import java.io.InputStream;

import util.Util;

public class BitField extends TCPBitTorrentPacket {
	
	private byte[] bitfield; 
	
	public BitField(InputStream input, int size) throws IOException { 
		this.bitfield = Util.read(input, size - 1);
	}
	public BitField(byte[] bitfield) { 
		this.bitfield = bitfield;
	}
	
	public byte[] getData() { 
		byte[] len = Util.converIntToBytes(2 + bitfield.length, 4);
		byte[] id = new byte[] {5}; 
		return Util.concatAll(len, id, bitfield);
	}

}
