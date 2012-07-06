package message.utp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

import util.Util;

public class UTPSocket {
	private InetAddress inet; 
	private int port;
	private DatagramSocket socket; 
	private UTPInputStream input; 
	private UTPOutputStream output;
	public UTPSocket(InetAddress inet, int port) throws IOException {
		this.inet = inet;
		this.port = port;
	}
	
	public UTPSocket(byte[] ip, int port) throws IOException {
		this.inet = InetAddress.getByAddress(ip);
		this.port = port;
	}
	
	public UTPSocket(DatagramSocket socket, UTPPacket handshake) {
		System.out.println("get input/output");
		input = new UTPInputStream(socket, handshake);
		output = new UTPOutputStream(socket, handshake);
	}
	
	/*
	 * Try go make connection with other end
	 */
	public void connect() throws IOException {
		// make the datagramsocket here
		socket = new DatagramSocket();
		
		// make the handshake with the server
		UTPPacket handshake = new UTPPacket(inet, port);
		handshake.ver = 1;
		handshake.type = ConstantState.ST_SYN;
		handshake.seq_nr = 1;
		handshake.connection_id_recv = new Random().nextInt();
		handshake.connection_id_send = handshake.connection_id_recv + 1;
		
		handshake.connection_id = handshake.connection_id_recv;
		handshake.seq_nr += 1;
		
		// send the first packet to initialize the connection
		Util.debug("sending package");
		socket.send(handshake.getDatagramPacket());
		System.out.println("Testing 1");
		
		// receive back the responding from  other  endd
		byte[] buffer = new byte[ConstantState.MAX_BUFFER]; 
		DatagramPacket receive = new DatagramPacket(buffer, buffer.length);
		socket.receive(receive);
		System.out.println("Testing 2");
		UTPPacket other = new UTPPacket(receive);
		handshake.type = ConstantState.ST_CONNECTED;
		handshake.ack_nr = other.seq_nr;
		
		input = new UTPInputStream(socket, handshake);
		output = new UTPOutputStream(socket, handshake);
	}
	
	public InputStream getIntputStream() {
		return input;
	}
	
	public OutputStream getOutputStream() {
		System.out.println("output = " + output);
		return output;
	}
	
	public void close() {
	}
}
