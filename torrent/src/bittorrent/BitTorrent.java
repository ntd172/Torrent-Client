package bittorrent;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Bittorrent {
	public static final int MAX = 10;
	public static final int PIECES = 179;
	public static void main(String[] args) throws UnknownHostException {
		List<Peer> ips = new ArrayList<Peer>();
		
		// add one ip
//		byte[] ip = new byte[] {41, 34, 30, (byte) 184};
//		Peer testPeer = new Peer(ip, 14435);
//		ips.add(testPeer);
		
		byte[] ip = new byte[] {24, 57, 11, (byte) 128};
		Peer testPeer = new Peer(ip, 46241);
		ips.add(testPeer);
		
//		ip = new byte[] {24, 102, 15, 12}; 
//		testPeer = new Peer(ip, 18205);
//		ips.add(testPeer);
//		
//		ip = new byte[] {24, (byte) 142, 43, (byte) 242};
//		testPeer = new Peer(ip, 56691);
//		ips.add(testPeer);
		
		
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
		
		
//		for (int i = 0; i < ips.size(); i ++) { 
			peers[0].setPeers(peers);
			peers[0].start();
//		}
	}
}
