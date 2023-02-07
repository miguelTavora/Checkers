package board;

import java.util.ArrayList;
import java.util.HashMap;

import board.pieces.BlackPiece;
import board.pieces.BlackQueen;
import board.pieces.Empty;
import board.pieces.Piece;
import board.pieces.Unplayable;
import board.pieces.WhitePiece;
import board.pieces.WhiteQueen;

public class Board {

	public static final String SEPARATOR = ";";
	public static final String SECOND_SEPARATOR = "%";
	private final int WIDTH = 8;
	private final int HEIGHT = 8;
	private Piece[][] board;
	private final int WHITE_PIECE_ID = new WhitePiece().getPositionType();
	private final int BLACK_PIECE_ID = new BlackPiece().getPositionType();
	private final int WHITE_QUEEN_ID = new WhiteQueen().getPositionType();
	private final int BLACK_QUEEN_ID = new BlackQueen().getPositionType();
	private final int EMPTY_PIECE_ID = new Empty().getPositionType();

	public Board() {
		board = new Piece[8][8];
		createBoard();
		
	}

	// creates the board of the game
	public void createBoard() {
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				boolean isUnplayable = (i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0);
				boolean isBlack = ((i % 2 != 0 && j % 2 == 0) || (i % 2 == 0 && j % 2 != 0)) && i < 3;
				boolean isWhite = ((i % 2 != 0 && j % 2 == 0) || (i % 2 == 0 && j % 2 != 0)) && i > 4;
				if (isUnplayable) {
					Piece emptyPiece = new Unplayable();
					board[i][j] = emptyPiece;
				} else if (isBlack) {
					Piece blackPiece = new BlackPiece(this, i, j);
					board[i][j] = blackPiece;
				} else if (isWhite) {
					Piece blackPiece = new WhitePiece(this, i, j);
					board[i][j] = blackPiece;
				} else {
					Piece unplayble = new Empty();
					board[i][j] = unplayble;
				}
			}
		}
	}
	
	// creates the board of the game
	/*public void createBoard() {
		
		Piece emptyPiece2 = new WhiteQueen(this, 5, 2);
		board[5][2] = emptyPiece2;

		Piece emptyPiece4 = new BlackPiece(this, 4, 1);
		board[4][1] = emptyPiece4;
		
		
		Piece emptyPiece59 = new BlackPiece(this, 2, 1);
		board[2][1] = emptyPiece59;
		
		
		Piece emptyPiece59s = new WhitePiece(this, 7, 0);
		board[7][0] = emptyPiece59s;

		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if (board[i][j] == null) {
					boolean isUnplayable = (i % 2 == 0 && j % 2 == 0) || (i % 2 != 0 && j % 2 != 0);
					boolean isBlack = ((i % 2 != 0 && j % 2 == 0) || (i % 2 == 0 && j % 2 != 0)) && i < 2;
					boolean isWhite = ((i % 2 != 0 && j % 2 == 0) || (i % 2 == 0 && j % 2 != 0)) && i > 4;
					if (isUnplayable) {
						Piece emptyPiece = new Unplayable();
						board[i][j] = emptyPiece;
					} 
						 
					else {
						Piece unplayble = new Empty();
						board[i][j] = unplayble;
					}
				}
			}
		}

	}*/

	// print lines to see better the board
	private void printTopOrBottom() {
		for (int i = 0; i < WIDTH * 4 - 2; i++) {
			if (i == WIDTH * 4 - 3)
				System.out.print("_\n");
			else
				System.out.print("_");
		}
	}

	// print the board
	public void printBoard() {
		printTopOrBottom();

		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if (j == HEIGHT - 1)
					System.out.print(" " + board[i][j].getPositionType() + " |\n");

				else if (j == 0)
					System.out.print("| " + board[i][j].getPositionType() + " ");

				else
					System.out.print(" " + board[i][j].getPositionType() + " ");
			}
		}

		printTopOrBottom();
	}

	// promotes the piece into queen
	public int[] promovePieceIntoQueen() {
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if (board[i][j].getPositionType() == WHITE_PIECE_ID) { 
					if (i == 0) {
						board[i][j] = new WhiteQueen(this, i, j);
						return new int[] { i, j };
					}
				}
				
				else if (board[i][j].getPositionType() == BLACK_PIECE_ID) {
					if (i == WIDTH - 1) {
						board[i][j] = new BlackQueen(this, i, j);
						return new int[] { i, j };
					}
				}				
			}
		}
		return null;
	}

	// makes a move on the board
	public void makeMove(int previousPosWidth, int previousPosHeight, int newPosWidth, int newPosHeight) {
		//obtains the object and reset the position to the new one
		Piece piece = board[previousPosWidth][previousPosHeight];
		
		board[previousPosWidth][previousPosHeight] = new Empty();
		board[newPosWidth][newPosHeight] = piece;
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				int type = board[i][j].getPositionType();
				if(type == WHITE_PIECE_ID || type == BLACK_PIECE_ID || type == WHITE_QUEEN_ID || type == BLACK_QUEEN_ID) {
					board[i][j].setPosition(i, j);
				}
			}
		}
	}
	
	public void removePiece(int positionWidth, int positionHeight) {
		board[positionWidth][positionHeight] = new Empty();
	}
	
	public HashMap<String, ArrayList<String>> getEatablePositions(int typePiece) {
		HashMap<String, ArrayList<String>> values = new HashMap<String, ArrayList<String>>();
		
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				if(board[i][j].getPositionType() == typePiece) {
					ArrayList<String> positionsEatable = board[i][j].getEatablePositions();
					// only if the arraylist is bigger than 0 means that can eat
					if(positionsEatable.size() != 0)
						values.put(i+SEPARATOR+j, positionsEatable);
				}
			}
		}
		return values;
	}

	// get all the possible path that the pieces can make
	public ArrayList<String> getMostEatablePositions(int queenType) {
		Piece[][] originalBoard = copyBoard();

		ArrayList<String> paths = new ArrayList<String>();

		HashMap<String, ArrayList<String>> piecePos = getEatablePositions(queenType);
		HashMap<String, ArrayList<ArrayList<Piece>>> boardConf = new HashMap<String, ArrayList<ArrayList<Piece>>>();

		String key = null;
		for (String keySet : piecePos.keySet()) {
			key = keySet;
			break;
		}

		while (piecePos.size() != 0) {
			// when the key is already removed
			if (!piecePos.containsKey(key)) {
				for (String keySet : piecePos.keySet()) {
					key = keySet;
					break;
				}
			}

			// means that the bpard already suffered changes, so reset the board
			if (boardConf.containsKey(key)) {
				board = convertListToBoard(boardConf.get(key));
			}

			// obtains the movable postions
			ArrayList<String> posObtained = piecePos.get(key);
			
			if (posObtained.size() == 0) {
				piecePos.remove(key);
				paths.add(key);
				continue;
			}

			String newPos = posObtained.get(0);

			String[] lastNumbers = key.split(SECOND_SEPARATOR);

			int previousX = Integer.parseInt(lastNumbers[lastNumbers.length - 1].split(SEPARATOR)[0]);
			int previousY = Integer.parseInt(lastNumbers[lastNumbers.length - 1].split(SEPARATOR)[1]);

			int newX = Integer.parseInt(newPos.split(SEPARATOR)[0]);
			int newY = Integer.parseInt(newPos.split(SEPARATOR)[1]);

			int posRemoveX = newX - previousX > 0 ? 1 : -1;
			int posRemoveY = newY - previousY > 0 ? 1 : -1;

			if (posObtained.size() > 1) {
				for (int i = 1; i < posObtained.size(); i++) {
					boardConf.put(key, convertBoardToList(board));
				}
			}
		
			// makes changes on the board
			makeMove(previousX, previousY, newX, newY);
			removePiece(newX - posRemoveX, newY - posRemoveY);

			ArrayList<String> posBefore = null;
			// when it's queen
			if (queenType == WHITE_QUEEN_ID || queenType == BLACK_QUEEN_ID) {
				// already used big move, so only small now
				if (queenType == WHITE_QUEEN_ID) {
					WhiteQueen queen = (WhiteQueen) board[newX][newY];
					posBefore = queen.getSurroundings();
				} else {
					BlackQueen queen = (BlackQueen) board[newX][newY];
					posBefore = queen.getSurroundings();
				}
			}
			// when its a piece
			else
				posBefore = board[newX][newY].getEatablePositions();
			
			
			String oldKey = key;
			piecePos.put(key + SECOND_SEPARATOR + posObtained.get(0), posBefore);
			key = key + SECOND_SEPARATOR + posObtained.get(0);

			posObtained.remove(0);
			piecePos.put(oldKey, posObtained);
		}
		board = originalBoard;
		return removeRepeats(paths);
	}
	
	public HashMap<String, ArrayList<String>> getMovimentation(int typePiece) {
		HashMap<String, ArrayList<String>> values = new HashMap<String, ArrayList<String>>();
		
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				if(board[i][j].getPositionType() == typePiece) {
					ArrayList<String> positionsMovable = board[i][j].getMovePositions();
					// only if the arraylist is bigger than 0 means that can eat
					if(positionsMovable.size() != 0)
						values.put(i+SEPARATOR+j, positionsMovable);
				}
			}
		}
		return values;
	}

	public int getWhitePieceId() {
		return this.WHITE_PIECE_ID;
	}

	public int getBlackPieceId() {
		return this.BLACK_PIECE_ID;
	}

	public int getWhiteQueenId() {
		return this.WHITE_QUEEN_ID;
	}

	public int getBlackQueenId() {
		return this.BLACK_QUEEN_ID;
	}
	
	public int getEmptyPositionId() {
		return this.EMPTY_PIECE_ID;
	}
	
	public Piece[][] getBoard() {
		return this.board;
	}
	
	private Piece[][] copyBoard() {
		Piece[][] newboard = new Piece[WIDTH][HEIGHT];
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				newboard[i][j] = board[i][j];
			}
		}
		return newboard;
	}
	
	private ArrayList<ArrayList<Piece>> convertBoardToList(Piece[][] board) {
		ArrayList<ArrayList<Piece>> list = new ArrayList<ArrayList<Piece>>();
		
		for(int i = 0; i < WIDTH; i++) {
			list.add(new ArrayList<Piece>());
		}
		
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				list.get(i).add(board[i][j]);
			}
		}
		return list;
	}
	
	private Piece[][] convertListToBoard(ArrayList<ArrayList<Piece>> board) {
		Piece[][] newboard = new Piece[WIDTH][HEIGHT];
		
		for(int i = 0; i < WIDTH; i++) {
			for(int j = 0; j < HEIGHT; j++) {
				newboard[i][j] = board.get(i).get(j);
			}
		}
		return newboard;
	}
	
	private ArrayList<String> removeRepeats(ArrayList<String> list) {
		ArrayList<String> indexes = new ArrayList<String>();
		
		for(int i = 0; i < list.size(); i++) {
			for(int j = i; j < list.size(); j++) {
				if(list.get(i).startsWith(list.get(j)) && list.get(j).length() < list.get(i).length() && i != j) {
					if(!indexes.contains(list.get(j)))
						indexes.add(list.get(j));
				}
			}
		}

		for(int i = 0; i < indexes.size(); i++) {
			list.remove(indexes.get(i));
		}
		return list;
	}
	
}
