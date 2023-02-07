package gui.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import javax.swing.JPanel;

import ai.AI;
import board.Board;
import board.Engine;
import gui.MainFrame;

public class GamePanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	
	private MainFrame frame;
	private Engine engine;
	private AI ai;
	private GraphicOperations graphicOpr;
	
	private final int WIDTH = 720;
	private final int HEIGHT = 727;
	
	private HashMap<String, String> possibleClickPos;
	private ArrayList<String> posToMove = null;
	private String posClicked;
	private int playerId;
	private int dificulty;
	private String advPosMove = null;
	private boolean canPaintMovablePos = true;
	private boolean eat = false;
	private Semaphore semaphore;
	private int isWinOrLose = 0;

	public GamePanel(MainFrame frame, Engine engine, AI ai, int firstPlayer, int dificulty) {
		this.frame = frame;
		this.engine = engine;
		this.playerId = firstPlayer;
		this.ai = ai;
		this.dificulty = dificulty;
		graphicOpr = new GraphicOperations();
		setLayout(null);
		setBackground(new Color(49, 46, 43));
		this.addMouseListener(this);
		semaphore = new Semaphore(1);
		if(this.playerId == 2) {
			canPaintMovablePos = false;
			new Thread() {
				public void run() {
					enemyPlay();
					canPaintMovablePos = true;
				}
			}.start();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();

		// paint board
		int[] coord = getImageCoordinates();
		g.drawImage(graphicOpr.getBackground(), coord[0], coord[1], this);

		
		// to play
		if (isWinOrLose == 0) {
			if (canPaintMovablePos)
				paintBorderOnPlayablePieces(g, coord);

			if (posToMove != null) {
				paintPlayablePos(g, posToMove, coord);
			}

			paintBoardOnEnemy(g, coord);

			paintPieces(g, coord);

			g2d.dispose();
		}
		//when win or lose
		else if(isWinOrLose == 1) {
			g.drawImage(graphicOpr.getLose(), 32+coord[0], 150+coord[1], this);
			g.drawImage(graphicOpr.getReplay(), 240+coord[0], 550+coord[1], this);
		}
		
		else if(isWinOrLose == 2) {
			g.drawImage(graphicOpr.getWin(), 150+coord[0], 120+coord[1], this);
			g.drawImage(graphicOpr.getReplay(), 240+coord[0], 550+coord[1], this);
		}
		
		
		semaphore.release();
	}
	
	// paint the pieces on the board
	private void paintPieces(Graphics g, int[] coord) {
		HashMap<String, Integer> posPieces = graphicOpr.getPositionsPiece(this.engine, this.playerId);
		for (String key : posPieces.keySet()) {
			Image img = graphicOpr.getImageByIdentifier(posPieces.get(key));
			int[] positions = graphicOpr.convertStringToPosImage(key, coord);

			g.drawImage(img, positions[0], positions[1], this);

		}
	}
	
	private void moveImage(String newPos) {
		posToMove = null;
		canPaintMovablePos = false;
		repaint();
		String[] posxy = newPos.split(Board.SEPARATOR);
		String[] posxy2 = posClicked.split(Board.SEPARATOR);
		int oldx = Integer.parseInt(posxy2[0]);
		int oldy = Integer.parseInt(posxy2[1]);
		int newx = Integer.parseInt(posxy[0]);
		int newy = Integer.parseInt(posxy[1]);
		
		if(eat) {
			String choosenPath = graphicOpr.getChoosenPathBigger(this.engine, this.posClicked, newPos, 1);
			String[] newPosObtd = choosenPath.split(Board.SECOND_SEPARATOR);
			
			for(int i = 1; i < newPosObtd.length; i++) {
				oldx = Integer.parseInt(newPosObtd[i-1].split(Board.SEPARATOR)[0]);
				oldy = Integer.parseInt(newPosObtd[i-1].split(Board.SEPARATOR)[1]);
				
				newx = Integer.parseInt(newPosObtd[i].split(Board.SEPARATOR)[0]);
				newy = Integer.parseInt(newPosObtd[i].split(Board.SEPARATOR)[1]);
				
				int posRemoveX = newx - oldx > 0 ? 1 : -1;
				int posRemoveY = newy - oldy > 0 ? 1 : -1;
				
				this.engine.removePiece(newx - posRemoveX, newy - posRemoveY);
				engine.makeMove(oldx, oldy, newx, newy);
			}
		}
		else 
			engine.makeMove(oldx, oldy, newx, newy);
		
		eat = false;
		
		repaint();
		posClicked = null;
		possibleClickPos = null;
		
		
		new Thread() {
			public void run() {
				try {
					Thread.sleep(50);
					semaphore.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				enemyPlay();
				semaphore.release();
			}
		}.start();
		
	}
	
	private void enemyPlay() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int player = 2;
		
		String eat = null;
		if(dificulty == MainFrame.EASY_MODE) 
			eat = ai.selectEatPositionRandom(this.engine.getMostEatablePosPiece(player), this.engine.getMostEatablePosQueen(player));
		
		
		else if (dificulty == MainFrame.MEDIMUM_MODE) {
			eat = ai.selectEatPositionBiggerValue(this.engine.getMostEatablePosPiece(player), this.engine.getMostEatablePosQueen(player));
		}
		
		if (eat != null) {
			String[] posxy = eat.split(Board.SECOND_SEPARATOR);
			for (int i = 1; i < posxy.length; i++) {
				int oldx = Integer.parseInt(posxy[i - 1].split(Board.SEPARATOR)[0]);
				int oldy = Integer.parseInt(posxy[i - 1].split(Board.SEPARATOR)[1]);
				int newx = Integer.parseInt(posxy[i].split(Board.SEPARATOR)[0]);
				int newy = Integer.parseInt(posxy[i].split(Board.SEPARATOR)[1]);

				int posRemoveX = newx - oldx > 0 ? 1 : -1;
				int posRemoveY = newy - oldy > 0 ? 1 : -1;

				this.engine.makeMove(oldx, oldy, newx, newy);
				this.engine.removePiece(newx - posRemoveX, newy - posRemoveY);

			}
		}
		
		else {
			HashMap<String, ArrayList<String>> posPiece = null;
			HashMap<String, ArrayList<String>> posQueen = null;
			
			posPiece = this.engine.getPosMovePieceBlack();
			posQueen = this.engine.getPosMoveQueenBlack();
			
			
			//player win
			if(posPiece.size() == 0 && posQueen.size() == 0) {
				isWinOrLose = 2;
				repaint();
				return;
			}
			eat = ai.selectMovePosition(posPiece, posQueen);
			String[] positions = eat.split(Board.SECOND_SEPARATOR);
			int oldx = Integer.parseInt(positions[0].split(Board.SEPARATOR)[0]);
			int oldy = Integer.parseInt(positions[0].split(Board.SEPARATOR)[1]);
			int newx = Integer.parseInt(positions[1].split(Board.SEPARATOR)[0]);
			int newy = Integer.parseInt(positions[1].split(Board.SEPARATOR)[1]);
			this.engine.makeMove(oldx, oldy, newx, newy);
		}
		
		advPosMove = eat;
		canPaintMovablePos = true;
		repaint();
	}
	
	private void paintBorderOnPlayablePieces(Graphics g, int[] coord) {
		HashMap<String, ArrayList<String>> posPiece = null;
		HashMap<String, ArrayList<String>> posQueen = null;

		
		posPiece = engine.getPosEatablePieceWhite();
		posQueen = engine.getPosEatableQueenWhite();
		eat = true;
		
		
		if (posPiece.size() == 0 && posQueen.size() == 0) {
			posPiece = engine.getPosMovePieceWhite();
			posQueen = engine.getPosMoveQueenWhite();
			
			eat = false;
		}
		
		//player lost
		if(posPiece.size() == 0 && posQueen.size() == 0) {
			isWinOrLose = 1;
			repaint();
			return;
		}
		// draw the border
		for (String pos : posPiece.keySet()) {
			int[] positions = graphicOpr.convertStringToPosImage(pos, coord);

			
			g.drawImage(graphicOpr.getBorder(), positions[0]-5, positions[1]-6, this);

		}
		for (String pos : posQueen.keySet()) {
			int[] positions = graphicOpr.convertStringToPosImage(pos, coord);

			g.drawImage(graphicOpr.getBorder(), positions[0]-5, positions[1]-6, this);
		}
		
		// sets the position on pixels that will perfome a action
		possibleClickPos = graphicOpr.getChoosablePositions(posPiece, posQueen, coord);
	}
	
	private void paintBoardOnEnemy(Graphics g, int[] coord) {
		// paint borders on enemy moves
		if (advPosMove != null) {
			String[] positionsAdv = advPosMove.split(Board.SECOND_SEPARATOR);
			for (int i = 0; i < positionsAdv.length; i++) {
				int[] posPaint = graphicOpr.convertStringToPosImage(positionsAdv[i], coord);

				g.drawImage(graphicOpr.getBorder3(), posPaint[0] - 5, posPaint[1] - 6, this);
			}
		}
	}
	
	private void paintPlayablePos(Graphics g, ArrayList<String> pos, int[] coord) {
		for(int i = 0; i < pos.size(); i++) {
			int[] posxy = graphicOpr.convertStringToPosImage(pos.get(i), coord);
			g.drawImage(graphicOpr.getBorder2(), posxy[0]-6, posxy[1]-6, this);
		}
	}
	
	// gives the addiction needed to center the image
	private int[] getImageCoordinates() {
		int difWidth = frame.getWidth() - WIDTH <= 0 ? 0 : frame.getWidth() - WIDTH;
		int difHeight = frame.getHeight() - HEIGHT <= 0 ? 0 : frame.getHeight() - HEIGHT;
		
		int dividedDifWidth = ((int)difWidth/2)-10;
		int dividedDifHeight = ((int)difHeight/2)-15;
		
		return new int[] {dividedDifWidth, dividedDifHeight};
	}	

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public synchronized void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		
		// during the game
		if (isWinOrLose == 0) {
			String pos = graphicOpr.isPlayablePiece(x, y, possibleClickPos);
			if (pos != null) {
				posClicked = pos;
				posToMove = graphicOpr.getPlayablePositions(this.engine, pos, this.playerId);
				repaint();
			}
			if (posToMove != null) {
				String selectedMove = graphicOpr.isSelectedPos(x, y, posToMove, getImageCoordinates());
				if (selectedMove != null)
					moveImage(selectedMove);
			}
		}
		// win or lose
		else {
			int[] coord = getImageCoordinates();
			if (x > coord[0] + 260 && x < coord[0] + 260 + 210 && y > coord[1] + 550 && y < coord[1] + 550 + 60) {
				engine.createNewBoard();
				isWinOrLose = 0;
				advPosMove = null;
				repaint();
				if(this.playerId == 2) {
					canPaintMovablePos = false;
					new Thread() {
						public void run() {
							enemyPlay();
							canPaintMovablePos = true;
						}
					}.start();
				}
				else 
					canPaintMovablePos = true;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
