package message.utp;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import util.Util;


public class TestingUTP {
	public static void main(String[] args) throws IOException { 
		byte[] ip = new byte[] {(byte) 77, (byte) 79, (byte) 154, (byte) 13};
		int port = 46570;
		
		FileInputStream file = new FileInputStream("test/utp-host-1");
		byte[] data = new byte[10000]; 
		int len = file.available();
		file.read(data, 0, len);
		
		System.out.println("Sending data");
		Util.printHex(data, len);
		DatagramSocket socket = new DatagramSocket();
		DatagramPacket packet = new DatagramPacket(data, len, InetAddress.getByAddress(ip), port);
		socket.send(packet);
		
		socket.receive(packet);
		System.out.println("Receving data");
		Util.printHex(packet.getData());
		
		file = new FileInputStream("test/utp-host-2");
		len = file.available();
		file.read(data, 0, len);
		System.out.println("Sending data");
		Util.printHex(data, len);
		packet = new DatagramPacket(data, len, InetAddress.getByAddress(ip), port);
		socket.send(packet);
		
		
		socket.receive(packet);
		System.out.println("Receiving data");
		Util.printHex(packet.getData());
		
		file = new FileInputStream("test/utp-host-request");
		len = file.available();
		file.read(data, 0, len);
		System.out.println("Sending data");
		
		data = new byte[1480];
		packet = new DatagramPacket(data, data.length, InetAddress.getByAddress(ip), port);
		System.out.println("Receiving data");
		socket.receive(packet);
		Util.printHex(packet.getData());
	}
}
