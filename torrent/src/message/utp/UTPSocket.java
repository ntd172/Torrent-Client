package message.utp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

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
		handshake.type = ConstantState.ST_SYN;
		handshake.seq_nr = 1;
		handshake.connection_id_recv = new Random().nextInt();
		handshake.connection_id_send = handshake.connection_id_recv + 1;
		
		// send the first packet to initialize the connection
		socket.send(handshake.getDatagramPacket());
		
		// receive back the responding from  other  endd
		byte[] buffer = new byte[ConstantState.MAX_BUFFER]; 
		DatagramPacket receive = new DatagramPacket(buffer, buffer.length);
		socket.receive(receive);
		UTPPacket other = new UTPPacket(receive);
		handshake.type = ConstantState.ST_CONNECTED;
		handshake.ack_nr = other.seq_nr;
		
		new UTPSocket(socket, handshake);
	}
	
	public InputStream getIntputStream() {
		return input;
	}
	
	public OutputStream getOutputStream() {
		return output;
	}
	
	public void close() {
	}
}
