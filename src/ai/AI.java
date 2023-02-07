package ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import board.Board;

public class AI {
	
	private Random rd;
	
	public AI() {
		rd  = new Random();
	}
	
	// return a random position, where index 0 is old position and 1 the new one
	public String selectMovePosition(HashMap<String, ArrayList<String>> posPiece, HashMap<String, ArrayList<String>> posQueen) {
		String result = "";

		int selected = posPiece.size() > 0 && posQueen.size() > 0 ? 1 : 2;

		selected = selected != 1 && posPiece.size() > 0 ? 3 : selected;
		selected = selected != 1 && posQueen.size() > 0 ? 4 : selected;

		if (selected == 1) {
			selected = rd.nextInt(2);
		}

		if(selected == 1 || selected == 3) {

			int index = rd.nextInt(posPiece.size());

			int count = 0;
			for (String position : posPiece.keySet()) {
				if (count == index) {
					result+= position;
					break;
				}
				count++;
			}
			
			ArrayList<String> possiblePos = posPiece.get(result);

			int index2 = rd.nextInt(possiblePos.size());

			result+= Board.SECOND_SEPARATOR+possiblePos.get(index2);
			return result;
		} 
		else {
			int index = rd.nextInt(posQueen.size());

			int count = 0;
			for (String position : posQueen.keySet()) {
				if (count == index) {
					result +=position;
					break;
				}
				count++;
			}
			ArrayList<String> possiblePos = posQueen.get(result);

			int index2 = rd.nextInt(possiblePos.size());

			result+= Board.SECOND_SEPARATOR+possiblePos.get(index2);
			return result;

		}
	}
	
	// always returns a random choose of eat a piece
	public String selectEatPositionRandom(ArrayList<String> posPiece, ArrayList<String> posQueen) {
		
		if(posPiece.size() > 0 && posQueen.size() > 0) {
			int pieceOrQueen = new Random().nextBoolean() ? 1 : 2;
			
			if(pieceOrQueen == 1) {
				int index = rd.nextInt(posPiece.size());
				
				return posPiece.get(index);
				
			}
			else {
				int index = rd.nextInt(posQueen.size());
				
				return posQueen.get(index);
			}
		}
		else if(posPiece.size() > 0) {
			int index = rd.nextInt(posPiece.size());
			
			return posPiece.get(index);
		}
		else if(posQueen.size() > 0) {
			int index = rd.nextInt(posQueen.size());
			return posQueen.get(index);
		}
		return null;
	}
	
	// always returns the biggest value of to eat a piece
	public String selectEatPositionBiggerValue(ArrayList<String> posPiece, ArrayList<String> posQueen) {
		
		if(posPiece.size() > 0 && posQueen.size() > 0) {
			int max = -1;
			int index = -1;
			for(int i = 0; i < posPiece.size(); i++) {
				if(posPiece.get(i).split(Board.SECOND_SEPARATOR).length > max) {
					max = posPiece.get(i).split(Board.SECOND_SEPARATOR).length;
					index = i;
				}
			}
			boolean isQueen = false;
			for(int i = 0; i < posQueen.size(); i++) {
				if(posQueen.get(i).split(Board.SECOND_SEPARATOR).length > max) {
					isQueen = true;
					max = posQueen.get(i).split(Board.SECOND_SEPARATOR).length;
					index = i;
				}
			}
			String result = isQueen == true ? posQueen.get(index) : posPiece.get(index);
			return result;
			
		}
		else if(posPiece.size() > 0) {
			int max = -1;
			int index = -1;
			for(int i = 0; i < posPiece.size(); i++) {
				if(posPiece.get(i).split(Board.SECOND_SEPARATOR).length > max) {
					max = posPiece.get(i).split(Board.SECOND_SEPARATOR).length;
					index = i;
				}
			}
			return posPiece.get(index);
		}
		else if(posQueen.size() > 0) {
			int max = -1;
			int index = -1;
			
			for(int i = 0; i < posQueen.size(); i++) {
				if(posQueen.get(i).split(Board.SECOND_SEPARATOR).length > max) {
					max = posQueen.get(i).split(Board.SECOND_SEPARATOR).length;
					index = i;
				}
			}
			return posQueen.get(index);
		}
		
		return null;
	}
	

}
