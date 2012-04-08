package message;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeMap;

import util.Util;
import bittorrent.Constant;
import bittorrent.TorrentData;
import bittorrent.TreeTorrentData;

public class Extended extends TCPBitTorrentPacket implements TreeTorrentData{
	private byte[] message; 
	private TorrentData torrentData; 
	
	public Extended(DataInputStream input, int size) throws IOException { 
		message = Util.read(input, size - 1);
		extractData();
		setType(Constant.EXTENDED);
	}
	public Extended(byte[] message) throws IOException {
		this.message = message; 
		extractData();
		setType(Constant.EXTENDED);
	}
	
	
	public byte[] getData() { 
		byte[] len = Util.converIntToBytes(message.length + 1, 4);
		byte[] id = new byte[] {20};
		return Util.concatAll(len, id, message);
	}
	
	public void extractData() throws IOException { 
		// there is 0x00 between the header and message 
		// be careful about this
		byte[] data =  Arrays.copyOfRange(message, 1, message.length);
		try { 
			torrentData = new TorrentData(data);
		} catch (IOException e) { 
			torrentData = null;
		}
	}
	
	public TreeMap getMessage() {
		return torrentData.getMap();
	}
	
	public void printMessage() { 
		 for (Iterator iter = getMessage().keySet().iterator(); iter.hasNext();) { 
			 String key = (String) iter.next();
			 if (key.equals("v"))
				 System.out.println("[" +  key + " = " + (new String((byte[]) getMessage().get(key))) + "]");
			 else if (key.equals("yourip"))
				 System.out.println("[" +  key + " = " + Arrays.toString((byte[])getMessage().get(key)) + "]");
			 else 
				 System.out.println("[" +  key + " = " + getMessage().get(key) + "]");
		 }
	}
}
