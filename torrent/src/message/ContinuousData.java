package message;

import java.io.DataInputStream;
import java.io.IOException;

import util.Util;

public class ContinuousData extends TCPBitTorrentPacket {
	public ContinuousData(DataInputStream input, int size) throws IOException {
		Util.read(input, size - 1);
	}
	
	public byte[] getData() {
		return null;
	}
}
