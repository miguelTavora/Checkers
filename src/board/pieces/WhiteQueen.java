package board.pieces;

import board.Board;

public class WhiteQueen extends Queen{
	
	private int BLACK_PIECE_ID;
	private int BLACK_QUEEN_ID;

	// when its only to get the identifier
	public WhiteQueen() {
		
	}
	
	public WhiteQueen(Board board, int posWidth, int posHeight) {
		super(board, posWidth, posHeight);
		
		this.BLACK_PIECE_ID = board.getBlackPieceId();
		this.BLACK_QUEEN_ID = board.getBlackQueenId();
	}

	@Override
	public int getPositionType() {
		return 3;
	}

	@Override
	public int getAdversaryPieceType() {
		return this.BLACK_PIECE_ID;
	}

	@Override
	public int getAdversaryQueenType() {
		return this.BLACK_QUEEN_ID;
	}

}
