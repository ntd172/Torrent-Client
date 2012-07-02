import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.TreeMap;

import org.ardverk.coding.BencodingInputStream;

import util.Util;


public class ReadInput {
	public static void main(String[] args) throws IOException { 
		BencodingInputStream stream = new BencodingInputStream(new FileInputStream("test/input"));
		stream.skip(1);
		TreeMap map = (TreeMap) stream.readMap();
		System.out.println(map.keySet());
		
		System.out.println("[complete_ago] " + map.get("complete_ago"));
		System.out.println("[e] " + map.get("e"));
		System.out.println("[ipv4] " + Arrays.toString((byte[])map.get("ipv4")));
		System.out.println("[m] " + map.get("m"));
		System.out.println("[metadata_size] " + map.get("metadata_size"));
		System.out.println("[p] " + map.get("p"));
		System.out.println("[reqq] " + map.get("reqq"));
		System.out.println("[v] " + Util.bytesToString((byte[])map.get("v")));
		System.out.println("[yourip]i " + Arrays.toString((byte[])map.get("yourip")));
	}
}
