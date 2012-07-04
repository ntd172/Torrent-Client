package util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Formatter;
import java.util.Map;
import java.util.TreeMap;

import org.ardverk.coding.BencodingInputStream;
import org.ardverk.coding.BencodingOutputStream;

import bittorrent.Constant;

public class Util {
	
	public static int convertBytesToInt(byte[] a) {
		int total = 0;
		int term = 1;
		for (int i = a.length - 1; i >= 0; i--) {
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

	public static byte[] read(DataInputStream input, int n) throws IOException {
		byte[] result = new byte[n];
		input.readFully(result);
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
	
	public static void printHex(byte[] peerId, int len) {
		Formatter formatter = new Formatter();
		int count = 0;
		for (byte a : peerId) {
			if (count == len) 
				break;
			formatter.format("%02x ", a);
			count += 1;
		}
		System.out.println(formatter.toString());
	}

	public static void printStr(byte[] peerId) {
		String result = "";
		for (byte a : peerId) {
			result += (char) a;
		}

		System.out.println(result);
	}

	public static String bytesToString(byte[] data) {
		return new String(data);
	}

	public static int getBit(int n, int a) {
		return (n >> a) & 1;
	}

	public static boolean checksum(int index, byte[] data) {
		// TODO: need to check the SHA-1 with the origin file with this data
		return true;
	}

	public static void writeData(File file, byte[] data) {
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new FileOutputStream(file));
			out.write(data);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				out.close();
				if (Constant.DEBUG) {
					System.out.println("[WRITE SUCCESSFULLY] " + file);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void pause() {
		// TODO Auto-generated method stub
		// try {
		// System.in.read();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	public static void debug(String str) {
		// TODO Auto-generated method stub
		System.out.println("[DEBUG] " + str);
	}

	public static void printDecode(String fileName) throws IOException {
		// TODO Auto-generated method stub
		BencodingInputStream stream = new BencodingInputStream(
				new FileInputStream(fileName));
		stream.skip(1);
		TreeMap map = (TreeMap) stream.readMap();
		System.out.println(map.keySet());

		System.out.println("[complete_ago] " + map.get("complete_ago"));
		System.out.println("[e] " + map.get("e"));
		System.out.println("[ipv4] "
				+ Arrays.toString((byte[]) map.get("ipv4")));
		System.out.println("[m] " + map.get("m"));
		System.out.println("[metadata_size] " + map.get("metadata_size"));
		System.out.println("[p] " + map.get("p"));
		System.out.println("[reqq] " + map.get("reqq"));
		System.out.println("[v] " + Util.bytesToString((byte[]) map.get("v")));
		System.out.println("[yourip]i "
				+ Arrays.toString((byte[]) map.get("yourip")));

		stream.close();
	}

	public static byte[] loadData(String fileName) throws IOException {
		DataInputStream input = new DataInputStream(new FileInputStream(
				fileName));
		long size = new File(fileName).length();
		byte[] data = new byte[(int) size];

		input.readFully(data);
		input.close();
		return data;
	}

	public static byte[] saveData(TreeMap tree) throws IOException {
		// TODO Auto-generated method stub
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		BencodingOutputStream stream = new BencodingOutputStream(data);

		stream.writeMap((Map) tree);
		return data.toByteArray();
	}
}
