package message.utp;

import java.io.IOException;

import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UTPOutputStream extends OutputStream {
	private DatagramSocket socket; 
	private UTPPacket uTPpacket;
	private byte[] buffer = new byte[ConstantState.MAX_BUFFER];
	
	public UTPOutputStream(DatagramSocket socket, DatagramPacket packet) {
		this.socket = socket; 
		this.uTPpacket = new UTPPacket(packet);
	}
	
	public UTPOutputStream(DatagramSocket socket, UTPPacket packet) {
		this.socket = socket; 
		this.uTPpacket = packet;
	}
	
	public void close() {
	}
	
	
	public void flush() {
	}
	
	public void write(byte[] data, int off, int len) throws IOException {
		uTPpacket.seq_nr ++;
		uTPpacket.connection_id = uTPpacket.connection_id_send;
		uTPpacket.add(data, off, len);
		
		socket.send(uTPpacket.getDatagramPacket());
	}

	public void write(byte[] data) throws IOException {
		write(data, 0, data.length);
	}
	
	@Override
	public void write(int data) throws IOException {
		// TODO Auto-generated method stub
		uTPpacket.seq_nr ++;
		uTPpacket.connection_id = uTPpacket.connection_id_send;
		uTPpacket.add(data);
		
		socket.send(uTPpacket.getDatagramPacket());
	}
}
