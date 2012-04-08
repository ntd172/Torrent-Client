package bittorrent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.TreeMap;

import org.ardverk.coding.BencodingInputStream;

public class TorrentData {
	private TreeMap tree;
	public TorrentData(byte[] message) throws IOException { 
		BencodingInputStream encode = new BencodingInputStream(new ByteArrayInputStream(message));
		tree = (TreeMap) encode.readMap();
	}
	
	public TreeMap getMap() { 
		return tree;
	}
}
