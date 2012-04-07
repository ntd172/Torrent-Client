package message;

import java.io.IOException;
import java.io.InputStream;

import bittorrent.Constant;

public class HandShake extends TCPBitTorrentPacket{
	
	private byte ptrLen;
	private byte[] reserved = new byte[8]; 
	private byte[] ptr;
	private byte[] infoHash = new byte[20]; 
	private byte[] peerID = new byte[20];  
	
	public HandShake(InputStream input) {
		super(input);
	}
	
	public HandShake(byte ptrLen, String ptr, byte[] reserved, byte[] infoHash, byte[] peerID) { 
		this.ptrLen = ptrLen; 
		this.ptr = new String(ptr).getBytes();
		this.reserved = reserved; 
		this.infoHash = infoHash; 
		this.peerID = peerID; 
	}
	
		
	public void matchMessage() { 
		byte[] temp = new byte[1];
		try {
			input.read(temp);
			ptrLen = temp[0];
			if (ptrLen != 19) {
				throw new IOException();
			}

			// make the default first
			ptr = new byte[ptrLen];
			input.read(ptr);

			input.read(reserved);
			input.read(infoHash);
			input.read(peerID);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] getData() { 
		int size = 1 + ptrLen + 8 + 20 * 2; 
		int curr = 1;
		byte[] result = new byte[size]; 
		
		result[0] = ptrLen; 
		for (int i = 0; i < ptrLen; i++) { 
			result[curr + i] = ptr[i];
		}
		curr += ptrLen;
		
		for (int i = 0; i < 8; i++) { 
			result[curr + i] = reserved[i]; 
		}
		
		curr += 8;
		
		for (int i = 0; i < 20; i++) { 
			result[curr + i] = infoHash[i]; 
		}
		
		curr += 20;
		
		for (int i = 0; i < 20; i++) { 
			result[curr + i] = peerID[i]; 
		}
		
		curr += 20;
		return result;
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
}
