package board;

import java.util.ArrayList;
import java.util.HashMap;

import board.pieces.Piece;

public class Engine {

	private Board board;

	public Engine(Board board) {
		this.board = board;

	}
	
	
	/*public void makePlay() {
		//promotes the black or white piece into queen
		int[] isPromoted = board.promovePieceIntoQueen();
		if(isPromoted != null)
			System.out.println("Is promoted piece");
		
		if (currentPlayer == 1) {
			System.out.println("WHITE:");
			
			HashMap<String, ArrayList<String>> posEatable = board.getEatablePositions(board.getWhitePieceId());
			printHash(posEatable, "Pos eat piece: ");
			
			
			HashMap<String, ArrayList<String>> positionsEatQueen = board.getEatablePositions(board.getWhiteQueenId());
			printHash(positionsEatQueen, "Eat queen:");
			
			ArrayList<String> mostPositions = board.getMostEatablePositions(board.getWhitePieceId());
			printPositionsArrayList(mostPositions, "Most Positions piece: ");
			
			ArrayList<String> mostPositionsQueen = board.getMostEatablePositions(board.getWhiteQueenId());
			printPositionsArrayList(mostPositionsQueen, "Most Positions queen: ");
			
			
			if(posEatable.size() == 0 && positionsEatQueen.size() == 0) {
				
				HashMap<String, ArrayList<String>> possibleMoviments = board.getMovimentation(board.getWhitePieceId());
				printHash(possibleMoviments, "Possible mov piece: ");
				
				HashMap<String, ArrayList<String>> possibleMovimentsQueen = board.getMovimentation(board.getWhiteQueenId());
				printHash(possibleMovimentsQueen, "Possible mov queen: ");
				
				if(checkWinner(possibleMoviments, possibleMovimentsQueen)) 
					winner = BLACK_PLAYER_ID;
			}
			
			currentPlayer = 2;
			
		} else {
			System.out.println("BLACK:");
			
			HashMap<String, ArrayList<String>> positionsEatQueen = board.getEatablePositions(board.getBlackQueenId());
			printHash(positionsEatQueen, "Eat queen:");
			
			
			HashMap<String, ArrayList<String>> posEatable = board.getEatablePositions(board.getBlackQueenId());
			printHash(posEatable, "Pos eat piece: ");
			
			ArrayList<String> mostPositions = board.getMostEatablePositions(board.getBlackPieceId());
			printPositionsArrayList(mostPositions, "Most Positions piece: ");
			
			ArrayList<String> mostPositionsQueen = board.getMostEatablePositions(board.getBlackQueenId());
			printPositionsArrayList(mostPositionsQueen, "Most Positions queen: ");
			
			
			if(posEatable.size() == 0 && positionsEatQueen.size() == 0) {
				
				HashMap<String, ArrayList<String>> possibleMovimentsPiece = board.getMovimentation(board.getBlackPieceId());
				printHash(possibleMovimentsPiece, "Possible mov piece: ");
				
				
				HashMap<String, ArrayList<String>> possibleMovimentsQueen = board.getMovimentation(board.getBlackQueenId());
				printHash(possibleMovimentsQueen, "Possible mov queen: ");
				
				if(checkWinner(possibleMovimentsPiece, possibleMovimentsQueen)) 
					winner = WHITE_PLAYER_ID;
			}
			
			currentPlayer = 1;
		}
	}*/

	// TODO tirar depois
	public void printPositionsArrayList(ArrayList<String> pos, String queen) {
		for (int i = 0; i < pos.size(); i++) {
			System.out.println(queen+pos.get(i));
		}
	}
	
	public void printHash(HashMap<String, ArrayList<String>> pos, String initial) {
		for(String position : pos.keySet()) {
			String result = initial+position;
			ArrayList<String> posibleMove = pos.get(position);
			for(int i = 0; i < posibleMove.size();i++) {
				result += " | "+posibleMove.get(i);
			}
			System.out.println(result);
		}
		
	}
 	
	public Piece[][] getBoard() {
		return this.board.getBoard();
	}
	
	public int getWhitePieceId() {
		return board.getWhitePieceId();
	}
	
	public int getBlackPieceId() {
		return board.getBlackPieceId();
	}
	
	public int getWhiteQueenId() {
		return board.getWhiteQueenId();
	}
	
	public int getBlackQueenId() {
		return board.getBlackQueenId();
	}
	
	public HashMap<String, ArrayList<String>> getPosEatablePieceWhite() {
		return board.getEatablePositions(board.getWhitePieceId());
	}
	
	public HashMap<String, ArrayList<String>> getPosEatablePieceBlack() {
		return board.getEatablePositions(board.getBlackPieceId());
	}
	
	public HashMap<String, ArrayList<String>> getPosMovePieceWhite() {
		return board.getMovimentation(board.getWhitePieceId());
	}
	
	public HashMap<String, ArrayList<String>> getPosMovePieceBlack() {
		return board.getMovimentation(board.getBlackPieceId());
	}
	
	public HashMap<String, ArrayList<String>> getPosEatableQueenWhite() {
		return board.getEatablePositions(board.getWhiteQueenId());
	}
	
	public HashMap<String, ArrayList<String>> getPosEatableQueenBlack() {
		return board.getEatablePositions(board.getBlackQueenId());
	}
	
	public HashMap<String, ArrayList<String>> getPosMoveQueenWhite() {
		return board.getMovimentation(board.getWhiteQueenId());
	}
	
	public HashMap<String, ArrayList<String>> getPosMoveQueenBlack() {
		return board.getMovimentation(board.getBlackQueenId());
	}
	
	public ArrayList<String> getMostEatablePosPiece(int player) {
		if(player == 1) 
			return board.getMostEatablePositions(board.getWhitePieceId());
		
		else
			return board.getMostEatablePositions(board.getBlackPieceId());
	}
	
	public ArrayList<String> getMostEatablePosQueen(int player) {
		if(player == 1)
			return board.getMostEatablePositions(board.getWhiteQueenId());
		else
			return board.getMostEatablePositions(board.getBlackQueenId());
	}
	
	//moves a piece and automatically promotes a piece into queen
	public void makeMove(int previousPosWidth, int previousPosHeight, int newPosWidth, int newPosHeight) {
		board.makeMove(previousPosWidth, previousPosHeight, newPosWidth, newPosHeight);
		board.promovePieceIntoQueen();
	}
	
	public void printBoard() {
		board.printBoard();
	}
	
	public void removePiece(int posx, int posy) {
		board.removePiece(posx, posy);
	}
	
	public void createNewBoard() {
		this.board.createBoard();
	}
	
}
