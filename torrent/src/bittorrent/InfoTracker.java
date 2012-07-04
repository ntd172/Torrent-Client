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
		
		infoHash = new byte[] {
				(byte)0x4B,
				(byte)0x5F,
				(byte)0x6A,
				(byte)0x3E,
				(byte)0x50,
				(byte)0x49,
				(byte)0x49,
				(byte) 0xAF,
				(byte) 0xE1,
				(byte) 0x07,
				(byte) 0x58,
				(byte) 0x0A,
				(byte) 0x67,
				(byte) 0x84,
				(byte) 0x43,
				(byte) 0x7E,
				(byte) 0xD0,
				(byte) 0x9B,
				(byte) 0xE5,
				(byte)0x2A
		};		
		pieceSize = 1369;
		pieceLength = 512 * 1024;
		fileSize = 717533184;
		
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
