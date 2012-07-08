package message.utp;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UTPInputStream extends InputStream {
	
	private DatagramSocket socket; 
	private UTPPacket uTPpacket;
	private byte[] buffer = new byte[ConstantState.MAX_BUFFER];
	private int bufferSize = 0;
	private int availability = 0;
	
	public UTPInputStream(DatagramSocket socket, DatagramPacket packet) {
		this.socket = socket;
		this.uTPpacket = new UTPPacket(packet);
	}
	
	public UTPInputStream(DatagramSocket socket, UTPPacket packet) {
		this.socket = socket; 
		this.uTPpacket = new UTPPacket(packet);
	}
	
	public int available() {
		System.out.println("available()");
		byte[] data = new byte[ConstantState.MAX_BUFFER];
		DatagramPacket receive = new DatagramPacket(data, data.length);
		try {
			socket.receive(receive);
			UTPPacket other = new UTPPacket(receive);
			
			int newLength = other.size;
			uTPpacket.ack_nr = other.seq_nr;
			
			if (newLength + bufferSize  + availability > ConstantState.MAX_BUFFER) {
				bufferSize = 0;
				byte[] temp = new byte[availability];
				for (int i = 0; i < availability; i++)
					temp[i] = buffer[i + availability];
				
				for (int i = 0; i < availability; i++) 
					buffer[i] = temp[i];
			}
			for (int i = 0; i < receive.getLength(); i++)
				buffer[i + bufferSize + availability] = other.extra[i];
			
			availability = newLength;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return availability;
	}
	
	public void close() {
	}
	
	public int read(byte[] buffer) throws IOException {
		return read(buffer, 0, buffer.length);
	}
	
	public int read(byte[] data, int off, int len) throws IOException {
		for (int i = 0; i < len; i++) {
			byte temp = (byte) read();
			data[off + i] =  temp;
		}
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
		if (availability == 0 || bufferSize == ConstantState.MAX_BUFFER) {
			available();
			return buffer[bufferSize++];
		} else {
			availability --;
			return buffer[bufferSize++];
		}
	}
}
