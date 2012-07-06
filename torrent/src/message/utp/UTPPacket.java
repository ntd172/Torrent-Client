package message.utp;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class UTPPacket {
	public int ver;
	public int type; 
	public int extension; 
	public int connection_id;
	public int connection_id_recv;
	public int connection_id_send;
	public int timestamp_microseconds;
	public int timestamp_difference_microseconds;
	public int wnd_size;
	public int seq_nr;
	public int ack_nr;
	
	private InetAddress inet;
	private int port;
	public UTPPacket() {
	}
	
	public UTPPacket(InetAddress inet, int port) {
		this.inet = inet;
		this.port = port;
	}
	public UTPPacket(DatagramPacket packet) {
	}
	
	public DatagramPacket getDatagramPacket() {
		//TODO: need to return the 
		DatagramPacket result = null;
		return result;
	}
	
	public void add(int data) {
	}
	
	public void add(byte[] data, int off, int len) {
	}
}
