package message;

import static org.junit.Assert.assertTrue;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.junit.Test;

import util.Util;


public class TestCase {

	@Test
	public void test() throws IOException {
		// testing HandShake
		DataInputStream input = new DataInputStream(new FileInputStream("test/hand-shake-input"));
		HandShake hs = new HandShake(input);
		input.close();
		input = new DataInputStream(new FileInputStream("test/hand-shake-input"));
		
		long fileSize = Util.getFileSZie("test/hand-shake-input");
		byte[] result = new byte[(int) fileSize]; 
		input.read(result);
		input.close();
		
		assertTrue(Arrays.equals(hs.getData(), result));
		
		//testing Extended
		testFile("test/extended");
		
		//testing BitField
		testFile("test/bitfield");
		
		// testing Have
		testFile("test/have");
		
		//testing Port
		testFile("test/port");
		
		//tesing Interested
		testFile("test/interested");
		
		//testing UnChoke
		testFile("test/unchoke");
		
		//testing ReQuest
		testFile("test/request");
		
		//testing piece
		testFile("test/piece");
	}
	
	public void testFile(String fileName) throws IOException {
		
		DataInputStream input = new DataInputStream(new FileInputStream(fileName));
		BitTorrentPacket packet = new TCPBitTorrentPacket(input); 
		input.close();
		
		input = new DataInputStream(new FileInputStream(fileName)); 
		long fileSize = Util.getFileSZie(fileName);
		byte[] result = new byte[(int) fileSize]; 
		input.read(result); 
		input.close();
		
//		Util.printBytes(packet.getData());
//		Util.printBytes(result);
		assertTrue(Arrays.equals(packet.getData(), result));
	}

}
