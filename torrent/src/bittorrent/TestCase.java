package bittorrent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import message.HandShake;

import org.junit.Test;


public class TestCase {

	@Test
	public void test() throws IOException {
		InputStream input = new FileInputStream("hand-shake-input");
		HandShake hs = new HandShake(input);
		assertEquals(hs.getPtr(),("BitTorrent protocol"));
		
		byte[] addr = new byte[] {74, 125, (byte) 224, 49};
		int port = 80;
		BitTorrent torrent = new BitTorrent(addr, port);
		assertFalse(torrent.getInputStream() == null);
		
	}

}
