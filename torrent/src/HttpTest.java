import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Formatter;

class HttpTest { 
	public static void main(String[] args) throws IOException {
		
		// HOST = 192.168.0.11
		// TRACKER = 95.215.62.5		port = http (80)
		InetAddress ip = InetAddress.getByAddress(new byte[] {(byte) 95, (byte) 215, (byte) 62, (byte) 5});
		
		// connection request
		// data : 00 00 04 17 27 10 19 80 00 00 00 00 c6 a2 92 eb
		// convert to byte[] array
		byte[] buffer = new byte[] {0x00, 0x00, 0x04, 0x17, 0x27, 0x10, 0x19, (byte) 0x80, 0x00, 0x00, 0x00, 0x00, (byte) 0xc6, (byte) 0xa2, (byte) 0x92, (byte) 0xeb};
		int length = buffer.length;
		
		// DatagramPacket is used to send and received UDP packet 
		DatagramPacket packet = new DatagramPacket(buffer, length, ip, 80);
		DatagramSocket socket = new DatagramSocket();
		socket.send(packet);
		
		System.out.println("Already sent");
		
		socket.receive(packet);
		
		System.out.println(byteArray2Hex(packet.getData()));
		System.out.println("Connect response");
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

