package board.pieces;

import board.Board;

public class BlackQueen extends Queen{
	
	private int WHITE_PIECE_ID;
	private int WHITE_QUEEN_ID;
	
	public BlackQueen() {
		
	}
	
	public BlackQueen(Board board, int posWidth, int posHeight) {
		super(board, posWidth, posHeight);
		
		this.WHITE_PIECE_ID = board.getWhitePieceId();
		this.WHITE_QUEEN_ID = board.getWhiteQueenId();
	}

	@Override
	public int getPositionType() {
		return 4;
	}

	@Override
	public int getAdversaryPieceType() {
		return this.WHITE_PIECE_ID;
	}

	@Override
	public int getAdversaryQueenType() {
		return this.WHITE_QUEEN_ID;
	}
}
