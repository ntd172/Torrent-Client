package message.utp;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UTPInputStream extends InputStream {
	
	private DatagramSocket socket; 
	private UTPPacket uTPpacket;
	private byte[] buffer;
	
	public UTPInputStream(DatagramSocket socket, DatagramPacket packet) {
		this.socket = socket;
		this.uTPpacket = new UTPPacket(packet);
	}
	
	public UTPInputStream(DatagramSocket socket, UTPPacket packet) {
		this.socket = socket; 
		this.uTPpacket = packet;
	}
	
	public int available() {
		if (buffer == null)
			buffer = new byte[ConstantState.MAX_BUFFER];
		int len = 0;
		try {
			len = read(buffer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return len;
	}
	
	public void close() {
	}
	
	public int read(byte[] buffer) throws IOException{
		DatagramPacket receive= new DatagramPacket(buffer, buffer.length);
		socket.receive(receive);
		
		UTPPacket other = new UTPPacket(receive);
		uTPpacket.ack_nr = other.seq_nr;
		
		for (int i = 0; i < other.extra.length; i++)
			buffer[i] = other.extra[i];
		
		return other.extra.length;
	}
	
	public int read(byte[] data, int off, int len) {
		for (int i = 0; i < len; i++) 
			data[i] = buffer[i + off]; 
		return len;
	}
	
	public void reset() {
	}
	
	public long skip(long n) {
		return n;
	}
	
	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[1];
		DatagramPacket receive= new DatagramPacket(buffer, buffer.length);
		socket.receive(receive);
		
		UTPPacket other = new UTPPacket(receive);
		uTPpacket.ack_nr = other.seq_nr;
		return buffer[0];
	}
}
