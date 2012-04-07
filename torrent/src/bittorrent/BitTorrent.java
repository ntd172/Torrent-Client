package bittorrent;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

import message.BitTorrentPacket;
import message.HandShake;

public class BitTorrent {
	protected Socket socket = null;
	protected InputStream input = null; 
	protected OutputStream output = null; 
	
	public BitTorrent(InetAddress inet, int port) throws IOException { 
		socket = new Socket(inet, port);
		input = socket.getInputStream();
		output = socket.getOutputStream();
	}
	
	public BitTorrent(byte[] addr, int port) throws IOException { 
		InetAddress inet = InetAddress.getByAddress(addr);
		socket = new Socket(inet, port); 
		input = socket.getInputStream();
		output = socket.getOutputStream();
	}
	
	public InputStream getInputStream() { 
		return input;
	}
	
	public OutputStream getOuputStream() { 
		return output;
	}
	
	
	public void send(BitTorrentPacket packet) { 
		try {
//			System.out.println(packet.getData().length);
			output.write(packet.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Can't send data packet to peer.");
		}
	}
	
	public void receive(BitTorrentPacket packet) { 
		if (packet.getType() == Constant.HANDSHAKE) { 
			packet = new HandShake(input);
		}
	}
	
	public static void main(String[] args) throws IOException { 
		
		InputStream input = new FileInputStream("hand-shake-input");
		HandShake hs = new HandShake(input);
		
		byte[] addr = new byte[] {75, 67, 65, 23};
		int port = 35710;
		BitTorrent torrent = new BitTorrent(addr, port);
		
		torrent.send(hs);
		torrent.receive(hs);
	}
}
