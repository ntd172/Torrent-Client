import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.TreeMap;

import org.ardverk.coding.BencodingInputStream;
import org.ardverk.coding.BencodingOutputStream;

public class Testing {
	public static void main(String[] args) throws Exception {
		BencodingInputStream input = new BencodingInputStream(
				new FileInputStream("file.torrent"));
		BencodingOutputStream output = new BencodingOutputStream(
				new FileOutputStream("temp"));
		TreeMap tree = (TreeMap) input.readMap();
		System.out.println(tree.keySet());
		System.out.println(new String((byte[]) tree.get("announce")));
		String mainURL = new String((byte[]) tree.get("announce"));
		// String mainURL = "http://tracker.openbittorrent.com:80/announce";
		System.out.println(tree.get("encoded rate"));
		List<Object> list = (List<Object>) tree.get("announce-list");
		for (int i = 0; i < list.size(); i++) {
			System.out
					.println(new String((byte[]) ((List) list.get(i)).get(0)));
		}

		TreeMap info = (TreeMap) tree.get("info");
		System.out.println(info);
		output.writeMap(info);
		output.close();
		System.out.println(new String((byte[]) info.get("name")));

		byte[] a = (byte[]) info.get("pieces");
		byte[] test = new byte[] {13, -15, -122, 52, -11, 70, -115, -2, -10, 116, 38, -67, -76, 43, -45, -118, -104, -95, 52, 28};
		int length = (int) Math.ceil(7957957 / 16384.0);
		for (int i = 0; i < length; i++) {
			boolean check = true;
			for (int j = 0; j < 20; j++) { 
				if (a[i * 20 + j] != test[j]) {
					check = false;
					break;
				}
			}
			if (check) { 
				System.out.println("YES");
				break;
			}
		}
		System.out.println(length);
		System.out.println(a.length / 20);
		MessageDigest sha1 = MessageDigest.getInstance("SHA1");
		for (int i = 0; i < length; i++) {
			byte[] temp = Arrays.copyOfRange(a, i * 20, (i + 1) * 20);
		}
		// DatagramPacket connection = new DatagramPacket(new byte[126], 126,
		// udp, 80);

		String infoHash = calculateHash("temp");
		System.out.println(infoHash);
		String peerId = calculateHash("temp-id");
		System.out.println(peerId);
		int port = 6700;
		int uploaded = 0;
		int downloaded = 0;
		int left = 7957957;

		String request = "?info_hash=" + infoHash + "&peer_id=" + peerId
				+ "&port=" + port + "&downloaded=" + downloaded + "&left="
				+ left + "&event=started";

		System.out.println(mainURL);
		System.out.println(request);
	}

	public static String calculateHash(String fileName) throws Exception {

		MessageDigest algorithm = MessageDigest.getInstance("SHA1");
		FileInputStream fis = new FileInputStream(fileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		DigestInputStream dis = new DigestInputStream(bis, algorithm);

		// read the file and update the hash calculation
		while (dis.read() != -1)
			;

		// get the hash value as byte array
		byte[] hash = algorithm.digest();

		return byteArray2Hex(hash);
	}

	private static String byteArray2Hex(byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		return formatter.toString();
	}
}
