package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Formatter;

public class Util {
	public static int convertBytesToInt(byte[] a) { 
		int total = 0; 
		int term = 1;
		for (int i = a.length -1; i >= 0; i--) { 
			short read = (short) ((short) a[i] & 0xff);
			total += (read) * term; 
			term *= Math.pow(2, 8);
		}
		
		return total;
	}
	
	public static byte[] concatAll(byte[] first, byte[]... rest) {
		int totalLength = first.length;
		for (byte[] array : rest) {
			totalLength += array.length;
		}
		byte[] result = Arrays.copyOf(first, totalLength);
		int offset = first.length;
		for (byte[] array : rest) {
			System.arraycopy(array, 0, result, offset, array.length);
			offset += array.length;
		}
		return result;
	}
	
	public static byte[] converIntToBytes(int a, int n) { 
		return ByteBuffer.allocate(n).putInt(a).array();
	}
	
	public static byte[] converShorttoBytes(short a, int n) { 
		return ByteBuffer.allocate(n).putShort(a).array();
	}
	
	public static byte[] read(InputStream input, int n) throws IOException { 
		byte[] result = new byte[n]; 
		input.read(result); 
		return result;
	}

	public static long getFileSZie(String string) {
		// TODO Auto-generated method stub
		return new File(string).length();
	}
	
	public static void printBytes(byte[] a) { 
		System.out.println(Arrays.toString(a));
	}

	public static void printHex(byte[] peerId) {
		// TODO Auto-generated method stub
		Formatter formatter = new Formatter(); 
		for (byte a : peerId) { 
			formatter.format("%02x ", a);
		}
		System.out.println(formatter.toString());
	}
	
	public static void printStr(byte[] peerId) { 
		Formatter formatter = new Formatter(); 
		for (byte a : peerId) { 
			formatter.format("%c", a);
		}
		System.out.println(formatter.toString());
	}
}
