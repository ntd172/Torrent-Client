package bittorrent;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BitTorrent {
	public static final int MAX = 10;
	public static final int PIECES = 1369;
	public static void main(String[] args) throws UnknownHostException {
		List<Peer> ips = new ArrayList<Peer>();
		
		// add one ip
//		byte[] ip = new byte[] {41, 34, 30, (byte) 184};
//		Peer testPeer = new Peer(ip, 14435);
//		ips.add(testPeer);
		
		byte[] ip = new byte[] {(byte) 109, (byte) 206, (byte) 36, (byte) 53};
		int port = 55555;
		
		ip = new byte[] {(byte) 91, (byte) 189, (byte) 90, (byte) 143}; 
		port = 6939;
		
		Peer testPeer = new Peer(ip, port);
		ips.add(testPeer);
		
		
		// InfoTracker is just only for testing
		InfoTracker info = new InfoTracker();
		BittorrentPeer[] peers = new BittorrentPeer[ips.size()];
		for (int i = 0; i < ips.size(); i++) { 
			peers[i] = new BittorrentPeer(ips.get(i), info);
		}
		
		for (int i = 0; i < info.getPieceSize(); i++) { 
			Random random = new Random();
			int index = random.nextInt(ips.size());
			
			peers[index].download(i);
		}
		
		
		for (int i = 0; i < ips.size(); i ++) { 
			peers[i].setPeers(peers);
			peers[i].start();
		}
	}
}
