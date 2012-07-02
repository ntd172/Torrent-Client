package bittorrent;

public class Constant {
	public static final int HANDSHAKE = 1; 
	public static final int KEEPALIVE = 2; 
	public static final int CHOKE = 3; 
	public static final int UNCHOKE = 4; 
	public static final int INTERESTED = 5; 
	public static final int NOTINTERESTED = 6;
	public static final int HAVE = 7; 
	public static final int BITFIELD = 8; 
	public static final int REQUEST = 9;
	public static final int CANCEL = 10;
	public static final int PIECE = 11; 
	public static final int EXTENDED = 12;
	public static final int PORT = 13;
	public static final int BITTORRENT = 14;
	public static final int UTP = 15;
	public static final int DEFAULT_BITTORRENT = 19;
	public static final String DEFAULT_STRING = "BitTorrent protocol";
	public static final boolean DEBUG = true;
	public static final long TRUNK_LENGTH = 0x4000;
	public static final String DIR = "data";
}
