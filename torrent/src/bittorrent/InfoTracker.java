package bittorrent;

public class InfoTracker {
	private byte[] infoHash, peerId;
	private int pieceSize, pieceLength;
	private long fileSize; 
	
	public InfoTracker() {
		// TODO: need to implement this class 
		// in order to pass information from tracker to Bittorrent
		infoHash = new byte[] {
				0x12, (byte) 0xed, (byte) 0xd2, (byte) 0x4f, 0x13, 
				0x65, (byte) 0xc4, 0x05, (byte) 0xdb, (byte) 0x9e, 
				0x74, 0x3f, (byte) 0xa3, (byte) 0xa1, 0x3d, 
				(byte) 0xb2, (byte) 0xc0, 0x2d, (byte) 0xf1, (byte) 0xf6
				
		};
		peerId = new byte[] {
				0x2d, 0x55, 0x4d, 0x31, 0x35, 
				0x31, 0x34, 0x30, 0x4b, 0x68, 
				(byte) 0xbe, (byte) 0x8c, (byte) 0xb1, (byte) 0xaf, 0x15, 
				0x4c, 0x47, (byte) 0x83, (byte) 0x9f, 0x65
			
		};
		pieceSize = 179;
		pieceLength = 64 * 1024; 
		fileSize = 11730102;
	}
	
	public byte[] getInfoHash() { 
		return infoHash;
	}
	
	public byte[] getPeerId() { 
		return peerId;
	}
	
	public int getPieceSize() { 
		return pieceSize; 
	}
	
	public int getPieceLength() { 
		return pieceLength;
	}
	
	public long getFileSize() { 
		return fileSize;
	}
}
