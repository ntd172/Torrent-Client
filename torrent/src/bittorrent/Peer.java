package bittorrent;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Peer {
	private InetAddress ip; 
	private int port;
	private byte[] id;
	
	public Peer(byte[] address, int port) throws UnknownHostException { 
		this.ip = InetAddress.getByAddress(address);
		this.port = port;
	}
	
	public int getPort() { 
		return port;
	}
	
	public InetAddress getIp() { 
		return ip;
	}
	
	public void setPeerId(byte[] id) { 
		this.id = id;
	}
	
	public byte[] getPeerId() { 
		return id;
	}
	
	public void setPort(int port) { 
		this.port = port;
	}
}
