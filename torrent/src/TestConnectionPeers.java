import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Formatter;
import java.util.TreeMap;

import org.ardverk.coding.BencodingInputStream;

public class TestConnectionPeers {
	public static void main(String[] args) throws IOException {
		// HOST = 192.168.0.11
		// TRACKER = 95.215.62.5 port = http (80)
		
		BufferedInputStream is = new BufferedInputStream(new FileInputStream("data"));
		File file = new File("data"); 
		long size = file.length();
		byte[] buffer = new byte[(int) size]; 
		
//		is.read(buffer); 
//		System.out.println(getPos(buffer, 0, 4));
//		System.out.println(getPos(buffer, 4, 4));
//		System.out.println(getPos(buffer, 8, 4));
//		System.out.println(getPos(buffer, 12, 4));
//		System.out.println(getPos(buffer, 16, 4));
//		System.out.println(getPos(buffer, 16, 4));
//		
//		for (int i = 20; i < size; i += 6) { 
//			System.out.println(getIp(buffer, i));
//			System.out.println(getPort(buffer, i + 4));
//		}
		
		BencodingInputStream input = new BencodingInputStream(
				new FileInputStream("data1"));
		TreeMap tree = (TreeMap) input.readMap();		
		System.out.println(tree.keySet());
		TreeMap a = (TreeMap) tree.get("a");
		System.out.println(a.keySet());
		byte[] id = (byte[]) a.get("id"); 
		System.out.println(getPos(id, 0, id.length));
		
		byte[] q = (byte[]) tree.get("q");
		System.out.println(getPos(q, 0, q.length));
		
		byte[] t = (byte[]) tree.get("t"); 
		System.out.println(getPos(t, 0, t.length));
		
		byte[] v = (byte[]) tree.get("v"); 
		System.out.println(getPos(v, 0, v.length));
		
		byte[] y = (byte[]) tree.get("y"); 
		System.out.println(getPos(y, 0, y.length));
		
		input = new BencodingInputStream(new FileInputStream("data2"));
		tree = (TreeMap) input.readMap();
		System.out.println(tree.keySet());
		TreeMap r = (TreeMap) tree.get("r"); 
		System.out.println(r.keySet());
		
		id = (byte[]) r.get("id"); 
		System.out.println("tree.r.id :\t\t" + getPos(id, 0, id.length));
		
		byte[] ip = (byte[]) r.get("ip");
		System.out.println("tree.r.ip :\t\t" + getIp(ip, 0));
		
		t = (byte[]) tree.get("t"); 
		System.out.println("tree.t :\t\t" + getPos(t, 0, t.length));
		
		v = (byte[]) tree.get("v"); 
		System.out.println("tree.v :\t\t" + getPos(v, 0, v.length));
		
		y = (byte[]) tree.get("y"); 
		System.out.println("tree.y :\t\t" + getPos(y, 0, y.length));
		
		input = new BencodingInputStream(new FileInputStream("data3"));
		size = new File("data3").length();
		byte[] data = new byte[(int) size]; 
		input.read(data);
		System.out.println(getPos(data, 0, data.length));
		
		
		input = new BencodingInputStream(new FileInputStream("data4"));
		size = new File("data4").length();
		data = new byte[(int) size]; 
		input.read(data);
		System.out.println(getPos(data, 0, data.length));
		
		// try to make connection to this host
//		int port = 59121;
//		size = new File("data1").length();
//		buffer = new byte[(int) size]; 
//		input = new BencodingInputStream(
//				new FileInputStream("data1"));	
//		input.read(buffer);
//		System.out.println(getPos(buffer, 0, (int) size));
//		InetAddress ipAddr = InetAddress.getByAddress(new byte[] {(byte) 201, (byte) 227, (byte) 243, (byte) 174}); 
//		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, ipAddr, 59121); 
//		DatagramSocket socket = new DatagramSocket();
//		
//		socket.send(packet); 
//		socket.receive(packet); 
//		buffer = packet.getData();
//		System.out.println();
		
		// try to make TCP connection with this host
//		Socket s = null;
//		try { 
//			s = new Socket(ipAddr, port);
//			System.out.println("already make connection");
//			
//			BufferedInputStream read =  new BufferedInputStream(s.getInputStream());
//			BufferedOutputStream out = new BufferedOutputStream(s.getOutputStream());
//			
//			size = new File("bittorent-handshake").length();
//			System.out.println(size);
//			buffer = new byte[(int) size]; 
//			input = new BencodingInputStream(
//					new FileInputStream("bittorrent-handshake"));	
//			input.read(buffer);
//			
//			out.write(buffer); 
//			System.out.println("already sent.");
//			int length = input.read(buffer); 
//			System.out.println(length);
//		} finally { 
//			s.close();
//		}
	}
	
	
	public static String getPos(byte[] a, int start, int size) { 
		Formatter format = new Formatter();
		
		for (int i = start; i < start + size; i++) { 
			format.format("%02x ", a[i]);
		}
		return format.toString();
	}
	
	public static String getIp(byte[] a, int start) { 
		String result = "";
		for (int i = start; i < start + 4; i++) { 
			String hex = getPos(a, i, 1).trim(); 
			int x = Integer.parseInt(hex, 16);
			result += x  + ".";
		}
		return result;
	}
	
	public static int getPort(byte[] a, int start) { 
		String hex = getPos(a, start, 2); 
		return Integer.parseInt(hex, 16);
	}
}
