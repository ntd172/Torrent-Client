package message.utp;

import java.io.IOException;

import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UTPOutputStream extends OutputStream {
	private DatagramSocket socket; 
	private DatagramPacket packet;
	
	public UTPOutputStream(DatagramSocket socket, DatagramPacket packet) {
		
	}
	
	public UTPOutputStream(DatagramSocket socket, UTPPacket packet) {
	}
	
	public void close() {
	}
	
	
	public void flush() {
	}
	
	public void write(byte[] b, int off, int len) {
	}
	
	@Override
	public void write(int arg0) throws IOException {
		// TODO Auto-generated method stub

	}
}
