package board.pieces;

import java.util.ArrayList;

import board.Board;

public abstract class Queen implements Piece{
	
	private int EMPTY_PIECE_ID;
	private Board board;
	private int posWidth;
	private int posHeight;
	private int width;
	private int height;
	
	public Queen() {
		
	}
	
	public Queen(Board board, int posWidth, int posHeight) {
		this.board = board;
		this.width = board.getBoard().length;
		this.height = board.getBoard()[0].length;
		this.posWidth = posWidth;
		this.posHeight = posHeight;
		this.EMPTY_PIECE_ID = board.getEmptyPositionId();
	}

	public abstract int getPositionType();
	
	public abstract int getAdversaryPieceType();
	
	public abstract int getAdversaryQueenType();

	@Override
	public ArrayList<String> getEatablePositions() {
		ArrayList<String> posEatable = new ArrayList<String>();
		
		int surroundingX = this.posWidth;
		int surroundingY = this.posHeight;
		
		
		for (int i = 0; i < 4; i++) {
			while (true) {
				if (i == 0) {
					surroundingX = surroundingX - 1;
					surroundingY = surroundingY - 1;
				} else if (i == 1) {
					surroundingX = surroundingX + 1;
					surroundingY = surroundingY - 1;
				} else if (i == 2) {
					surroundingX = surroundingX + 1;
					surroundingY = surroundingY + 1;
				} else {
					surroundingX = surroundingX - 1;
					surroundingY = surroundingY + 1;
				}

				if (surroundingX < this.width && surroundingX > -1 && surroundingY < this.height && surroundingY > -1) {
					int posCheck = board.getBoard()[surroundingX][surroundingY].getPositionType();
					// can eat
					if (posCheck == getAdversaryPieceType() || posCheck == getAdversaryQueenType()) {
						
						if (i == 0) {
							surroundingX = surroundingX - 1;
							surroundingY = surroundingY - 1;
						} else if (i == 1) {
							surroundingX = surroundingX + 1;
							surroundingY = surroundingY - 1;
						} else if (i == 2) {
							surroundingX = surroundingX + 1;
							surroundingY = surroundingY + 1;
						} else {
							surroundingX = surroundingX - 1;
							surroundingY = surroundingY + 1;
						}

						if (surroundingX < this.width && surroundingX > -1 && surroundingY < this.height && surroundingY > -1) {
							if (board.getBoard()[surroundingX][surroundingY].getPositionType() == EMPTY_PIECE_ID)  
								posEatable.add(surroundingX + Board.SEPARATOR + surroundingY);
							
						}
						surroundingX = this.posWidth;
						surroundingY = this.posHeight;
						break;
					}
					// if a piece of the current player it doesnt go further
					else if(board.getBoard()[surroundingX][surroundingY].getPositionType() != EMPTY_PIECE_ID) {
						surroundingX = this.posWidth;
						surroundingY = this.posHeight;
						break;
					}
				} 
				// out of bounds
				else {
					surroundingX = this.posWidth;
					surroundingY = this.posHeight;
					break;
				}
			}
		}
		return posEatable;
	}

	@Override
	public ArrayList<String> getMovePositions() {
		ArrayList<String> positions = new ArrayList<String>();
		
		int changePosX = this.posWidth;
		int changePosY = this.posHeight;
		
		for (int i = 0; i < 4; i++) {
			while (true) {
				// check on the 4 axis
				if (i == 0) {
					changePosX = changePosX - 1;
					changePosY = changePosY - 1;
				} else if (i == 1) {
					changePosX = changePosX + 1;
					changePosY = changePosY - 1;
				} else if (i == 2) {
					changePosX = changePosX + 1;
					changePosY = changePosY + 1;
				} else {
					changePosX = changePosX - 1;
					changePosY = changePosY + 1;
				}
				
				// check if the position is empty and it's inside the board
				if (changePosX < width && changePosX > -1 && changePosY < height && changePosY > -1) {
					
					if (board.getBoard()[changePosX][changePosY].getPositionType() == EMPTY_PIECE_ID) 
						positions.add(changePosX + Board.SEPARATOR + changePosY);
					
					
					else {
						changePosX = this.posWidth;
						changePosY = this.posHeight;
						break;
					}
					
				} else {
					changePosX = this.posWidth;
					changePosY = this.posHeight;
					break;
				}
			}
		}
		
		return positions;
	}
	
	public ArrayList<String> getSurroundings() {
		ArrayList<String> getSurroundings = new ArrayList<String>();

		int changePosX = this.posWidth;
		int changePosY = this.posHeight;

		for (int i = 0; i < 4; i++) {
			if (i == 0) {
				changePosX = changePosX - 1;
				changePosY = changePosY - 1;
			} else if (i == 1) {
				changePosX = changePosX + 1;
				changePosY = changePosY - 1;
			} else if (i == 2) {
				changePosX = changePosX + 1;
				changePosY = changePosY + 1;
			} else {
				changePosX = changePosX - 1;
				changePosY = changePosY + 1;
			}
			
			if (changePosX < this.width && changePosX > -1 && changePosY < this.height && changePosY > -1) {
				int typePos = board.getBoard()[changePosX][changePosY].getPositionType();
				// can eat
				if (typePos == getAdversaryPieceType() || typePos == getAdversaryQueenType()) {
					if (i == 0) {
						changePosX = changePosX - 1;
						changePosY = changePosY - 1;
					} else if (i == 1) {
						changePosX = changePosX + 1;
						changePosY = changePosY - 1;
					} else if (i == 2) {
						changePosX = changePosX + 1;
						changePosY = changePosY + 1;
					} else {
						changePosX = changePosX - 1;
						changePosY = changePosY + 1;
					}
					
					if (changePosX < this.width && changePosX > -1 && changePosY < this.height && changePosY > -1)
						if (board.getBoard()[changePosX][changePosY].getPositionType() == EMPTY_PIECE_ID)
							getSurroundings.add(changePosX + Board.SEPARATOR + changePosY);
				}
			}
			changePosX = this.posWidth;
			changePosY = this.posHeight;
		}
		return getSurroundings;
	}

	@Override
	public void setPosition(int posWidth, int posHeight) {
		this.posWidth = posWidth;
		this.posHeight = posHeight;
	}

}
