package util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Util {
	public static int convertBytesToInt(byte[] a) { 
		int total = 0; 
		int term = 1;
		for (int i = a.length -1; i >= 0; i--) { 
			int read = (a[i] & 0xff);
			total += (read) * term; 
			term *= 10;
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
		byte[] bytes = ByteBuffer.allocate(n).putInt(a).array();
		return bytes;
	}
	
	public static byte[] read(InputStream input, int n) throws IOException { 
		byte[] result = new byte[n]; 
		input.read(result); 
		return result;
	}
}
