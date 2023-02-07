package board.pieces;

import java.util.ArrayList;

public interface Piece {
	
	public abstract int getPositionType();
	
	public abstract ArrayList<String> getEatablePositions();
	
	public abstract ArrayList<String> getMovePositions();
	
	public abstract void setPosition(int posWidth, int posHeight);
	
}

