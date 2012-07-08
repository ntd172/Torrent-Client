package message.utp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class TestingUTP {
	public static void main(String[] args) throws IOException { 
		new Thread(new Runnable() {
			public void run() {
				try {
					DatagramSocket server = new DatagramSocket(1235);
					byte[] data = new byte[1024];
					DatagramPacket packet = new DatagramPacket(data, data.length);
					
					server.receive(packet);
					System.out.println("[Server] receive: " + new String(packet.getData(), 0, packet.getLength()));
					System.out.println("[Server] display port: " + packet.getPort());
					System.out.println("[Server] display ip: " + packet.getAddress().toString());
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
		
		new Thread(new Runnable() {
			public void run() {
				try {
					DatagramSocket socket = new DatagramSocket();
					String str = "Nguyen tan do";
					DatagramPacket packet = new DatagramPacket(str.getBytes(), str.getBytes().length, InetAddress.getLocalHost(), 1235);
					System.out.println("[Client] Send: " + str);
					socket.send(packet);
					
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}).start();
	}
}
