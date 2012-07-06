package message.utp;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UTPInputStream extends InputStream {
	
	public UTPInputStream(DatagramSocket socket, DatagramPacket packet) {
	}
	
	public UTPInputStream(DatagramSocket socket, UTPPacket packet) {
	}
	
	public int available() {
		return 0;
	}
	
	public void close() {
	}
	
	public int read(byte[] b) {
		return 0;
	}
	
	public void reset() {
	}
	
	public long skip(long n) {
		return n;
	}
	
	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		return 0;
	}

}
