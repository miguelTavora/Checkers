package board.pieces;

import java.util.ArrayList;

import board.Board;

public class WhitePiece implements Piece {

	private int EMPTY_PIECE_ID;
	private int BLACK_PIECE_ID;
	private int BLACK_QUEEN_ID;
	private Board board;
	private int posWidth;
	private int posHeight;
	private int height;
	
	// this constructor is used when it's only needed the position type
	public WhitePiece() {
		
	}

	public WhitePiece(Board board, int posWidth, int posHeight) {
		this.board = board;
		this.height = board.getBoard()[0].length;
		this.posWidth = posWidth;
		this.posHeight = posHeight;
		this.EMPTY_PIECE_ID = board.getEmptyPositionId();
		this.BLACK_PIECE_ID = board.getBlackPieceId();
		this.BLACK_QUEEN_ID = board.getBlackQueenId();
	}

	@Override
	public int getPositionType() {
		return 1;
	}

	@Override
	public ArrayList<String> getEatablePositions() {
		ArrayList<String> positionsEatable = new ArrayList<String>();
		
		if (posWidth - 2 > -1 && posHeight - 2 > -1) {
			int advPos = board.getBoard()[posWidth - 1][posHeight - 1].getPositionType();
			if (advPos == BLACK_PIECE_ID || advPos == BLACK_QUEEN_ID)
				if (board.getBoard()[posWidth - 2][posHeight - 2].getPositionType() == EMPTY_PIECE_ID)
					positionsEatable.add((posWidth - 2) + Board.SEPARATOR + (posHeight - 2));
		}

		if (posWidth - 2 > -1 && posHeight + 2 < this.height) {
			int advPos =  board.getBoard()[posWidth - 1][posHeight + 1].getPositionType();
			if (advPos == BLACK_PIECE_ID || advPos == BLACK_QUEEN_ID)
				if (board.getBoard()[posWidth - 2][posHeight + 2].getPositionType() == EMPTY_PIECE_ID)
					positionsEatable.add((posWidth - 2) + Board.SEPARATOR + (posHeight + 2));
		}
		
		return positionsEatable;
	}

	@Override
	public ArrayList<String> getMovePositions() {		
		ArrayList<String> positions = new ArrayList<String>();

		// check if the following positions is playable and if it's free
		if (posWidth - 1 > -1 && posHeight - 1 > -1)
			if (board.getBoard()[posWidth - 1][posHeight - 1].getPositionType() == EMPTY_PIECE_ID)
				positions.add((posWidth - 1) + Board.SEPARATOR + (posHeight - 1));

		if (posWidth - 1 > -1 && posHeight + 1 < this.height)
			if (board.getBoard()[posWidth - 1][posHeight + 1].getPositionType() == EMPTY_PIECE_ID)
				positions.add((posWidth - 1) + Board.SEPARATOR + (posHeight + 1));
		
		return positions;
	}

	@Override
	public void setPosition(int posWidth, int posHeight) {
		this.posWidth = posWidth;
		this.posHeight = posHeight;
	}

}
