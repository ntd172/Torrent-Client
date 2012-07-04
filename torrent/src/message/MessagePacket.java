package message;

import java.io.IOException;
import java.io.InputStream;

public class MessagePacket extends TCPBitTorrentPacket{
	
	public MessagePacket(TCPBitTorrentPacket input) throws IOException { 
		super(input); 
	}

}
