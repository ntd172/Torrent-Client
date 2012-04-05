import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Formatter;

class HttpTest { 
	public static void main(String[] args) throws IOException {
		
		// connect input
		byte[] buffer = new byte[100];
		int length = 100;
		InetAddress ip = InetAddress.getByAddress(new byte[] {(byte) 95, (byte) 215, (byte) 62, (byte) 26});
		DatagramPacket packet = new DatagramPacket(buffer, length, ip, 80);
		DatagramSocket socket = new DatagramSocket();
		socket.send(packet);
		
		socket.connect(ip, 80);
		socket.receive(packet);
		System.out.println(byteArray2Hex(packet.getData()));
		socket.close();
		
    }
	private static String byteArray2Hex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}

