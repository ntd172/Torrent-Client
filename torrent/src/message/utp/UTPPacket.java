package message.utp;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class UTPPacket {
	int ver;
	int type; 
	int extension; 
	int connection_id;
	int connection_id_recv;
	int connection_id_send;
	int timestamp_microseconds;
	int timestamp_difference_microseconds;
	int wnd_size;
	int seq_nr;
	int ack_nr;
	
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
		DatagramPacket result = null;
		return result;
	}
}
