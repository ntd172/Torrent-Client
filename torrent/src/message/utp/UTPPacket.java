package message.utp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

import util.Util;

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
	public byte[] extra = new byte[ConstantState.MAX_BUFFER];
	public int size = 0;
	
	public InetAddress inet;
	public int port;
	public UTPPacket() {
	}
	
	public UTPPacket(InetAddress inet, int port) {
		this.inet = inet;
		this.port = port;
	}
	
	public UTPPacket(UTPPacket other) {
		this.ver = other.ver;
		this.type = other.type;
		this.extension = other.extension;
		this.connection_id = other.connection_id;
		this.connection_id_recv = other.connection_id_recv;
		this.connection_id_send = other.connection_id_send;
		this.timestamp_difference_microseconds = other.timestamp_difference_microseconds;
		this.timestamp_microseconds = other.timestamp_microseconds;
		this.wnd_size = other.wnd_size;
		this.seq_nr = other.seq_nr;
		this.ack_nr = other.ack_nr;
		this.size = other.size;
		for (int i = 0; i < extra.length; i++) 
			this.extra[i] = other.extra[i];
		
		try {
			this.inet = InetAddress.getByAddress(other.inet.getAddress());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.port = other.port;
	}
	public UTPPacket(DatagramPacket packet) {
		inet = packet.getAddress();
		port = packet.getPort();
		
		byte[] data = new byte[packet.getLength()];
		byte[] origin = packet.getData();
		for (int i = 0; i < data.length; i++) 
			data[i] = origin[i];
		
		int first = Util.convertBytesToInt(data, 0, 1);
		ver = 1;
		type = (first & 0xf0) >> 2; 
		extension = Util.convertBytesToInt(data, 1, 1);
		connection_id = Util.convertBytesToInt(data, 2, 2);
		timestamp_microseconds = Util.convertBytesToInt(data, 4, 4);
		timestamp_difference_microseconds = Util.convertBytesToInt(data, 8, 4);
		wnd_size = Util.convertBytesToInt(data, 12, 4);
		seq_nr = Util.convertBytesToInt(data, 16, 2);
		ack_nr = Util.convertBytesToInt(data, 18, 2);
		
		
		int len = data.length - 20;
		if (len > 0) {
			size = len;
			for (int i = 0; i < len; i++) 
				extra[i] = data[i + 20];
		}
	}
	
	public DatagramPacket getDatagramPacket() {
		//TODO: need to return the 
		System.out.println("Getting DatagramPacket");
		
		int first = ver | (type << 2);
		byte[] firstData = new byte[] {(byte) first};
		byte[] extensionData =  new byte[] {(byte) extension};
		byte[] connection_idData = Util.converShorttoBytes((short) connection_id, 2);
		
		byte[] timestamp_microsecondsData = Util.converIntToBytes(timestamp_microseconds, 4);
		byte[] timestamp_differenceData = Util.converIntToBytes(timestamp_difference_microseconds, 4);
		
		byte[] wnd_sizeData = Util.converIntToBytes(wnd_size, 4);
		byte[] seq_nrData = Util.converShorttoBytes((short) seq_nr, 2);
		byte[] ack_nrData = Util.converShorttoBytes((short) ack_nr, 2);
		byte[] extraData = new byte[size];
		for (int i = 0; i < size; i++)
			extraData[i] = extra[i];
		
		byte[] data = Util.concatAll(firstData, extensionData, connection_idData, 
				timestamp_microsecondsData, timestamp_differenceData, 
				wnd_sizeData, seq_nrData, ack_nrData, extraData);
		System.out.println(data.length);
		return new DatagramPacket(data, data.length, inet, port);
	}
	
	public void add(int data) {
		extra[size] = (byte) data;
		size += 1;
	}
	
	public void add(byte[] data, int off, int len) {
		//TODO: need to improve expand the extra if the extra is full
		for (int i = 0; i < len; i++) 
			extra[size + i] = data[off + i];
		
		size += len;
	}
	
	public void add(byte[] data) {
		add(data, 0, data.length);
	}
}
