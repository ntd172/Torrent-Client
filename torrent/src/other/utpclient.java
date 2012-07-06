package other;

import java.util.*;
import java.io.*;
import java.net.*;


/*
Run this on the client side, passing in an ip-address as the argument. 
It tries to connect to a utpserver instance waiting on port 5000 of that ip address,
and sends a bunch of bytes to that instance.
*/
public class utpclient {

	public static void main(String [] args) throws Exception {
		System.out.println(System.nanoTime());
		UTPSocket conn = new UTPSocket(InetAddress.getByName(args[0]),5000);
		OutputStream out = conn.getOutputStream();
	   String outgoing = "Hello, this is a test of the emergency alert system\n";
		for (int j = 0;j < 10000;j++) {
			String num = j+"";
			for (int i = 0;i < num.length();i++) {
				out.write(num.charAt(i));
			}
			out.write(':');
			for (int k = 0;k < outgoing.length();k++) {
				out.write(outgoing.charAt(k));
			}
		}
		conn.close();
		System.out.println("SENT!!");
	}










}
