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
		
		byte[] ip = new byte[] {75, (byte) 74, (byte) 176, 68};
		Peer testPeer = new Peer(ip, 60419);
//		ips.add(testPeer);
		
		ip = new byte[] {(byte) 76, (byte) 16, (byte) 180, (byte) 31};
		testPeer = new Peer(ip, 62110);
//		ips.add(testPeer);
		
		ip = new byte[] {(byte) 85, (byte) 73, (byte) 49, (byte) 218};
		testPeer = new Peer(ip, 26017);
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
