package message.bittorrent;

import java.io.DataInputStream;
import java.io.IOException;

import util.Util;
import bittorrent.Constant;

public class BitField extends TCPBitTorrentPacket {
	
	private byte[] bitfield; 
	private boolean[] valid;
	
	public BitField(DataInputStream input, int size) throws IOException { 
		this.bitfield = Util.read(input, size - 1);
		updateValid();
		setType(Constant.BITFIELD);
	}
	public BitField(byte[] bitfield) { 
		this.bitfield = bitfield;
		updateValid();
		setType(Constant.BITFIELD);
	}
	
	public void updateValid() { 
		valid = new boolean[bitfield.length * 8];
		
		for (int i = 0; i < bitfield.length; i++) { 
			byte temp = bitfield[i];
			for (int j = 0; j < 8; j++) { 
				if (Util.getBit(temp, 7 - j) == 1)  { 
					valid[i * 8 + j] = true;
				} else { 
					valid[i * 8 + j] = false;
				}
			}
		}
	}
	
	public byte[] getData() { 
		byte[] len = Util.converIntToBytes(1 + bitfield.length, 4);
		byte[] id = new byte[] {5}; 
		return Util.concatAll(len, id, bitfield);
	}
	
	public byte[] getBitField() { 
		return bitfield;
	}
	
	public boolean[] getValid() { 
		return valid;
	}
	
	public boolean isValid(int n) { 
		return valid[n]; 
	}
	
	public int getInvalidCount() { 
		int count = 0;
		for (int i = 0; i < bitfield.length * 8; i++) { 
			if (!isValid(i)) {
				count += 1;
			}
		}
		return count;
	}
	
	public String getInvalidStr() { 
		String result = "";
		for (int i = 0; i < bitfield.length * 8; i++) { 
			if (!isValid(i)) {
				result += " " + i;
			}
		}
		return result;
	}
	
	public void debug() { 
		Util.debug("BitField");
		Util.debug("Num Invalid Pieces: " + getInvalidCount());
		Util.debug(getInvalidStr());
		Util.debug("");
	}

}
