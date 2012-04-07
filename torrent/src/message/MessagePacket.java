package message;

import java.io.IOException;
import java.io.InputStream;

public class MessagePacket extends TCPBitTorrentPacket{
	
	public MessagePacket(InputStream input) throws IOException { 
		super(input); 
	}

}
