package message.bittorrent;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

import util.Util;
import bittorrent.Constant;

public class HandShake extends TCPBitTorrentPacket{
	
	private byte ptrLen;
	private byte[] reserved = new byte[8]; 
	private byte[] ptr;
	private byte[] infoHash = new byte[20]; 
	private byte[] peerID = new byte[20];  
	
	public HandShake(DataInputStream input) {
		this.input = input;
		matchMessage();
		setType(Constant.HANDSHAKE);
	}
	
	public HandShake(byte ptrLen, String ptr, byte[] reserved, byte[] infoHash, byte[] peerID) { 
		this.ptrLen = ptrLen; 
		this.ptr = new String(ptr).getBytes();
		this.reserved = reserved; 
		this.infoHash = infoHash; 
		this.peerID = peerID; 
		setType(Constant.HANDSHAKE);
	}
	
		
	public void matchMessage() { 
		byte[] temp = new byte[1];
		try {
			input.read(temp);
			ptrLen = temp[0];
			if (ptrLen != 19) {
				System.out.println("Different from default.");
			}

			// make the default first
			ptr = new byte[ptrLen];
			input.read(ptr);

			reserved = new byte[8];
			input.read(reserved);
			
			infoHash = new byte[20];
			input.read(infoHash);
			
			peerID = new byte[20];
			input.read(peerID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] getData() { 
		byte[] ptrLen = new byte[] {this.ptrLen};
		return Util.concatAll(ptrLen, this.ptr, this.reserved, this.infoHash, this.peerID);
	}
	
	public byte[] getInfoHash() { 
		return infoHash;
	}
	
	public byte[] peerId() { 
		return peerID;
	}
	
	public String getPtr() { 
		return new String(ptr);
	}
	
	public int getType() { 
		return Constant.HANDSHAKE;
	}
	
	public void debug() { 
		Util.debug("HandShake"); 
		Util.debug("InfoHash : " + Arrays.toString(getInfoHash()));
		Util.debug("PeerId : " + Arrays.toString(peerId()));
		Util.debug("");
	}
}
