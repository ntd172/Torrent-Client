package other;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;

/*
Run this on the server side, it sits on port 5000 and waits for an incoming connection.
If a connection is established, it reads bytes from that connection until the connection is 
terminated (by the client) and writes them to a file called temp.txt.
*/
public class utpserver {
	
	public static void main(String [] args) throws Exception {
		UTPServerSocket server = new UTPServerSocket(5000);
		UTPSocket conn = server.listen();
	   InputStream in = conn.getInputStream();
		String incoming = "";  
		File test = new File("temp.txt");
		FileWriter outfile = new FileWriter(test);
		while (true) {
			int n = in.read();
			if (n == -1) {
				break;
			} else {
				outfile.write(n);
			}
		}
		outfile.flush();
		outfile.close();
		System.out.println("FINISHED READ");
	}

	private static void writeAsBits(int test) {
		String bits = Integer.toBinaryString(test&0xFF);
		String prefix = "";
		for (int i = 0;i < 8 - bits.length();i++) {
			prefix += "0";
		}
		System.out.print(prefix + bits);
	}
	






}
