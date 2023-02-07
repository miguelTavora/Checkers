package board.pieces;

import java.util.ArrayList;

public class Empty implements Piece{

	@Override
	public int getPositionType() {
		return 0;
	}

	@Override
	public ArrayList<String> getEatablePositions() {
		return null;
	}

	@Override
	public ArrayList<String> getMovePositions() {
		return null;
	}

	@Override
	public void setPosition(int posWidth, int posHeight) {
		
	}

}
