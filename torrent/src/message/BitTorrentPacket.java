package message;

public interface BitTorrentPacket {
	public byte[] getData();
	public int getType();
	public void setType(int type);
}
