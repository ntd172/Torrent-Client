package message.utp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.junit.Test;

import other.UTPSocket.UTPInputStream;

public class TestUTP {

	protected static final int PORT = 0;
	@Test
	public void test() {
		// Try to set up a connection with local host
		// First going to create server socket
		new Thread(new Runnable() {
			public void run() {
				UTPServer server = null;
				UTPSocket socket = null;
				try {
					server = new UTPServer(PORT);
					socket = server.accept();
					server.close();

					UTPInputStream input = (UTPInputStream) socket
							.getIntputStream();
					UTPOutputStream output = (UTPOutputStream) socket
							.getOutputStream();

					int len;
					len = input.available();
					byte[] readData = new byte[len];
					
					input.read(readData, 0, len);
					System.out.println("[Server] Receive: " + new String(readData));
					
					String data = "Hello, World! from Server.";
					output.write(data.getBytes());
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					server.close();
					socket.close();
				}
				
			}
		}).start();

		// Second, going to create socket client
		new Thread(new Runnable() {
			public void run() {
				UTPSocket socket = null;
				try {
					InetAddress inet = InetAddress.getLocalHost();
					int port = PORT;
					socket = new UTPSocket(inet, port);
					socket.connect();
					UTPInputStream input = (UTPInputStream) socket.getIntputStream();
					UTPOutputStream output = (UTPOutputStream) socket.getOutputStream();
					
					String data = "Hello World";
					output.write(data.getBytes());
					System.out.println("[Client] Send: " + data);
					
					int len = input.available();
					byte[] readData = new byte[len];
					input.read(readData, 0, len);
					
					System.out.println("[Client] Receive: " + new String(readData));
				} catch (SocketException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					socket.close();
				}
			}
		}).start();
	}

}
