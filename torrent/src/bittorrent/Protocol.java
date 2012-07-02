package bittorrent;

import java.util.List;

import message.bittorrent.Piece;

public interface Protocol {
	public void updatePieces(List<Piece> pieces);
}
