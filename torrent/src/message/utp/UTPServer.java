package message.utp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Random;

public class UTPServer {
	private DatagramSocket server;
	private DatagramPacket packet;
	
	public UTPServer() {
	}
	public UTPServer(int port) throws SocketException {
		server = new DatagramSocket(port);
		byte[] data = new byte[1024];
		packet = new DatagramPacket(data, data.length);
	}
	
	public UTPSocket accept() throws IOException {
		server.receive(packet);
		System.out.println("Accept");
		
		// TODO:
		// make the handshake here
		UTPPacket other = new UTPPacket(packet);
		UTPPacket handshake = new UTPPacket(packet.getAddress(), packet.getPort());
		handshake.connection_id_recv = other.connection_id + 1;
		handshake.connection_id_send = other.connection_id;
		handshake.seq_nr = new Random().nextInt();
		handshake.ack_nr = other.seq_nr;
		
		handshake.type = ConstantState.ST_STATE;
		handshake.seq_nr ++;
		handshake.connection_id = handshake.connection_id_send;
		
		// Send the data to other end, 
		// Connection is already established
		server.send(handshake.getDatagramPacket());
		
		UTPSocket result = new UTPSocket(server, handshake);
		return result;
	}
	
	public void close() {
	}
	
	public InetAddress getInetAddress() {
		return null;
	}
}
