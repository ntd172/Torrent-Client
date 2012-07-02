package message;

import java.io.DataOutputStream;
import java.io.IOException;

public interface BitTorrentPacket {
	public byte[] getData();
	public int getType();
	public void setType(int type);
	public void write(DataOutputStream out) throws IOException;
}
