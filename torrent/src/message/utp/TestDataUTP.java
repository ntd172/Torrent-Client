package message.utp;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import util.Util;

public class TestDataUTP {
	public static void main(String[] args) throws IOException {
		String fileName = "udp-data-"; 
		long total = 0;
		boolean flag = false;
		for (int i = 1; i <= 12; i++) {
			DataInputStream input = new DataInputStream(new FileInputStream("test/" + fileName + i));
			input.skip(20);
			Util.printHex(Util.read(input, 2 + 2 + 9));
			input.close();
			break;
		}
	}
}
