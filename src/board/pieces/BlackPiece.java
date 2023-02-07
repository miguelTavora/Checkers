package board.pieces;

import java.util.ArrayList;

import board.Board;

public class BlackPiece implements Piece {

	private int EMPTY_PIECE_ID;
	private int WHITE_PIECE_ID;
	private int WHITE_QUEEN_ID;
	private Board board;
	private int posWidth;
	private int posHeight;
	private int width;
	private int height;
	
	// this constructor is used when it's only needed the position type
	public BlackPiece() {
		
	}

	public BlackPiece(Board board, int posWidth, int posHeight) {
		this.board = board;
		this.width = board.getBoard().length;
		this.height = board.getBoard()[0].length;
		this.posWidth = posWidth;
		this.posHeight = posHeight;
		this.EMPTY_PIECE_ID = board.getEmptyPositionId();
		this.WHITE_PIECE_ID = board.getWhitePieceId();
		this.WHITE_QUEEN_ID = board.getWhiteQueenId();
	}

	@Override
	public int getPositionType() {
		return 2;
	}

	@Override
	public ArrayList<String> getEatablePositions() {
		ArrayList<String> positionsEatable = new ArrayList<String>();
		
		// check if the positions where the piece eat other is possible, first other
		// piece player and second empty pos
		if (posWidth + 2 < this.width && posHeight - 2 > -1) {
			int advPos = board.getBoard()[posWidth + 1][posHeight - 1].getPositionType();
			// other piece pos
			if (advPos == WHITE_PIECE_ID || advPos == WHITE_QUEEN_ID) {
				// the following place must be empty
				if (board.getBoard()[posWidth + 2][posHeight - 2].getPositionType() == EMPTY_PIECE_ID) 
					positionsEatable.add((posWidth + 2) + Board.SEPARATOR + (posHeight - 2));
				
			}
		}

		if (posWidth + 2 < this.width && posHeight + 2 < this.height) {
			int newPos = board.getBoard()[posWidth + 1][posHeight + 1].getPositionType();
			if (newPos == WHITE_PIECE_ID || newPos == WHITE_QUEEN_ID) 
				if (board.getBoard()[posWidth + 2][posHeight + 2].getPositionType() == EMPTY_PIECE_ID) 
					positionsEatable.add((posWidth + 2) + Board.SEPARATOR + (posHeight + 2));
		}
		
		return positionsEatable;
	}

	@Override
	public ArrayList<String> getMovePositions() {
		ArrayList<String> positions = new ArrayList<String>();

		if (posWidth + 1 < this.width && posHeight - 1 > -1)
			if (board.getBoard()[posWidth + 1][posHeight - 1].getPositionType() == EMPTY_PIECE_ID)
				positions.add((posWidth + 1) + Board.SEPARATOR + (posHeight - 1));

		if (this.posWidth + 1 < this.width && this.posHeight + 1 < this.height)
			if (board.getBoard()[posWidth + 1][posHeight + 1].getPositionType() == EMPTY_PIECE_ID)
				positions.add((posWidth + 1) + Board.SEPARATOR + (posHeight + 1));

		return positions;
	}

	@Override
	public void setPosition(int posWidth, int posHeight) {
		this.posWidth = posWidth;
		this.posHeight = posHeight;
	}
}
