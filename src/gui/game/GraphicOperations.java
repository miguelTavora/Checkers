package gui.game;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;

import board.Board;
import board.Engine;

public class GraphicOperations {
	
	private final String pathBackground = "src/img/board.png";
	private final String pathWhitePiece = "src/img/white_piece2.png";
	private final String pathBlackPiece = "src/img/black_piece2.png";
	private final String pathWhiteQueen = "src/img/white_queen2.png";
	private final String pathBlackQueen = "src/img/black_queen2.png";
	private final String pathBorder = "src/img/border.png";
	private final String pathBorder2 = "src/img/border2.png";
	private final String pathBorder3 = "src/img/border3.png";
	private final String pathWin = "src/img/win.png";
	private final String pathLose = "src/img/lose.png";
	private final String pathReplay = "src/img/replay3.png";
	
	private Image background;
	private Image whitePiece;
	private Image blackPiece;
	private Image whiteQueen;
	private Image blackQueen;
	private Image border;
	private Image border2;
	private Image border3;
	private Image win;
	private Image lose;
	private Image replay;
	
	
	private final int WIDTH_BOX = 86;
	private final int HEIGHT_BOX = 85;
	
	public GraphicOperations() {
		background = Toolkit.getDefaultToolkit().createImage(pathBackground);
		whitePiece = Toolkit.getDefaultToolkit().createImage(pathWhitePiece);
		blackPiece = Toolkit.getDefaultToolkit().createImage(pathBlackPiece);
		whiteQueen = Toolkit.getDefaultToolkit().createImage(pathWhiteQueen);
		blackQueen = Toolkit.getDefaultToolkit().createImage(pathBlackQueen);
		border = Toolkit.getDefaultToolkit().createImage(pathBorder);
		border2 = Toolkit.getDefaultToolkit().createImage(pathBorder2);
		border3 = Toolkit.getDefaultToolkit().createImage(pathBorder3);
		win = Toolkit.getDefaultToolkit().createImage(pathWin);
		lose = Toolkit.getDefaultToolkit().createImage(pathLose);
		replay = Toolkit.getDefaultToolkit().createImage(pathReplay);
	}
	
	// by identifier give a image
	public Image getImageByIdentifier(int identifier) {
		switch(identifier) {
			case 1:
				return whitePiece;
				
			case 2:
				return blackPiece;
				
			case 3:
				return whiteQueen;
			
			case 4:
				return blackQueen;
		}
		return null;
	}
	
	
	// obtains the pos in pixel of the string of indexes of board
	public int[] convertStringToPosImage(String pos, int[] coord) {
		String[] coordPieces = pos.split(Board.SEPARATOR);

		// coord of the images
		int posX = Integer.parseInt(coordPieces[1]);
		posX = posX * WIDTH_BOX + coord[0] + (WIDTH_BOX / 2) - 13;

		int posY = Integer.parseInt(coordPieces[0]);
		posY = posY * HEIGHT_BOX + (HEIGHT_BOX / 2) + coord[1] - 27 + (int) (posY * 1);
		
		return new int[] {posX, posY};
	}
	
	// obtains a hash with begin and end of positions and the index on board
	public HashMap<String, String> getChoosablePositions(HashMap<String, ArrayList<String>> posPiece, HashMap<String, ArrayList<String>> posQueen, int[] coord) {
		HashMap<String, String> possibleClickPos = new HashMap<String, String>();
		
		for (String pos : posPiece.keySet()) {
			int[] positions = convertStringToPosImage(pos, coord);
			
			possibleClickPos.put(positions[0]+Board.SEPARATOR+positions[1]+Board.SEPARATOR+(positions[0]+WIDTH_BOX)+Board.SEPARATOR+(positions[1]+WIDTH_BOX), pos);
		}

		for (String pos : posQueen.keySet()) {
			int[] positions = convertStringToPosImage(pos, coord);
			
			possibleClickPos.put(positions[0]+Board.SEPARATOR+positions[1]+Board.SEPARATOR+(positions[0]+HEIGHT_BOX)+Board.SEPARATOR+(positions[1]+HEIGHT_BOX), pos);
		}
		
		return possibleClickPos;
	}
	
	
	public String isPlayablePiece(int posX, int posY, HashMap<String, String> possibleClickPos) {
		if (possibleClickPos != null) {
			for (String coordPixel : possibleClickPos.keySet()) {
				String[] coordPixels = coordPixel.split(Board.SEPARATOR);
				int beginX = Integer.parseInt(coordPixels[0]) - 5;
				int beginY = Integer.parseInt(coordPixels[1]) - 6;
				int endX = Integer.parseInt(coordPixels[2]) - 5;
				int endY = Integer.parseInt(coordPixels[3]) - 6;

				if (posX > beginX && posX < endX && posY > beginY && posY < endY) {
					return possibleClickPos.get(coordPixel);
				}
			}
		}
		return null;
	}
	
	public String isSelectedPos(int posX, int posY, ArrayList<String> possiblePos, int[] coord) {
		for (int i = 0; i < possiblePos.size(); i++) {
			int[] begin = convertStringToPosImage(possiblePos.get(i), coord);
			
			int beginX = begin[0]-5;
			int beginY = begin[1]-6;
			int endX = beginX+WIDTH_BOX;
			int endY = beginY+HEIGHT_BOX;
			
			if(posX > beginX && posX < endX && posY > beginY && posY < endY) {
				return possiblePos.get(i);
			}
		}
		return null;
	}

	// gets the indexes that can draw a piece
	public HashMap<String, Integer> getPositionsPiece(Engine engine, int player) {
		HashMap<String, Integer> positions = new HashMap<String, Integer>();

		for (int i = 0; i < engine.getBoard().length; i++) {
			for (int j = 0; j < engine.getBoard()[0].length; j++) {
				int ident = getIdentifier(engine, engine.getBoard()[i][j].getPositionType(), player);
				if (ident != 0)
					positions.put(i + Board.SEPARATOR + j, ident);
			}
		}
		return positions;
	}
	
	public ArrayList<String> getPlayablePositions(Engine engine, String posChoosed, int player) {
		HashMap<String, ArrayList<String>> posPiece = null;
		HashMap<String, ArrayList<String>> posQueen = null;

		
		posPiece = engine.getPosEatablePieceWhite();
		posQueen = engine.getPosEatableQueenWhite();
		

		if (posPiece.size() == 0 && posQueen.size() == 0) {
			posPiece = engine.getPosMovePieceWhite();
			posQueen = engine.getPosMoveQueenWhite();
			
		}
		
		for (String pos : posPiece.keySet()) {
			if(pos.equals(posChoosed)) 
				return posPiece.get(pos);
		}
		
		for (String pos : posQueen.keySet()) {
			if(pos.equals(posChoosed)) 
				return posQueen.get(pos);
		}
		return null;
	}
	
	public String getChoosenPathBigger(Engine engine, String posClicked, String newPos, int playerId) {
		ArrayList<String> eatablePos = null;
		
		// check if is a piece or queen which was clicked
		String[] posxy = posClicked.split(Board.SEPARATOR);
		int oldx = Integer.parseInt(posxy[0]);
		int oldy = Integer.parseInt(posxy[1]);
		
		
		int posType = engine.getBoard()[oldx][oldy].getPositionType();
		
		if(posType == engine.getBlackPieceId() || posType == engine.getWhitePieceId()) 
			eatablePos = engine.getMostEatablePosPiece(playerId);
		
		else 
			eatablePos = engine.getMostEatablePosQueen(playerId);
		
		ArrayList<String> posObtained = new ArrayList<String>();
		
		String choosenPath = posClicked+Board.SECOND_SEPARATOR+newPos;
		for(int i = 0; i < eatablePos.size(); i++) {
			if(eatablePos.get(i).startsWith(choosenPath)) 
				posObtained.add(eatablePos.get(i));
			
		}
		// gets the bigger eat for the clicked pos
		int index = 0;
		int max  = 0;
		for(int i = 0; i < posObtained.size(); i++) {
			if(posObtained.get(i).split(Board.SECOND_SEPARATOR).length > max) {
				index  = i;
				max = posObtained.get(i).split(Board.SECOND_SEPARATOR).length;
			}
		}
		return posObtained.get(index);
	}

	public int getIdentifier(Engine engine, int pieceType, int player) {
		if (player == 1) {
			return pieceType;
		} else {
			if (engine.getWhitePieceId() == pieceType)
				return engine.getBlackPieceId();

			else if (engine.getBlackPieceId() == pieceType)
				return engine.getWhitePieceId();

			else if (engine.getWhiteQueenId() == pieceType)
				return engine.getBlackQueenId();

			else if (engine.getBlackQueenId() == pieceType)
				return engine.getWhiteQueenId();

		}
		return 0;
	}
	
	public Image getBackground() {
		return this.background;
	}
	
	public Image getBorder() {
		return this.border;
	}
	
	public Image getBorder2() {
		return this.border2;
	}
	
	public Image getBorder3() {
		return this.border3;
	}
	
	public Image getWin() {
		return this.win;
	}
	
	public Image getLose() {
		return this.lose;
	}
	
	public Image getReplay() {
		return this.replay;
	}
}
