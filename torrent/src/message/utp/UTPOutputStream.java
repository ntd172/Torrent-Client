package message.utp;

import java.io.IOException;

import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UTPOutputStream extends OutputStream {
	private DatagramSocket socket; 
	private UTPPacket uTPpacket;
	private byte[] buffer = new byte[ConstantState.MAX_BUFFER];
	private int bufferSize = 0;
	
	public UTPOutputStream(DatagramSocket socket, DatagramPacket packet) {
		this.socket = socket; 
		this.uTPpacket = new UTPPacket(packet);
	}
	
	public UTPOutputStream(DatagramSocket socket, UTPPacket packet) {
		this.socket = socket; 
		this.uTPpacket = new UTPPacket(packet);
	}
	
	public void close() {
	}
	
	
	public void flush() {
		try {
			send();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write(byte[] data, int off, int len) throws IOException {
		for (int i = 0; i < len; i++) 
			write(data[off + i]);
	}

	public void write(byte[] data) throws IOException {
		write(data, 0, data.length);
	}
	
	@Override
	public void write(int data) throws IOException {
		// TODO Auto-generated method stub
		buffer[bufferSize++] = (byte) data;
		
		if (bufferSize == ConstantState.MAX_BUFFER) {
			send();
		}
	}
	
	private void send() throws IOException {
		uTPpacket.seq_nr ++;
		uTPpacket.connection_id = uTPpacket.connection_id_send;
		uTPpacket.add(buffer, 0, bufferSize);
		
		socket.send(uTPpacket.getDatagramPacket());
		// reset bufferSize to 0
		bufferSize = 0;
	}
}
