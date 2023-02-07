package main;

import ai.AI;
import board.Board;
import board.Engine;
import gui.MainFrame;

public class Main {
	
	private static Engine engine;
	private static Board board;
	private static MainFrame frame;
	private static AI ai;
	
	public static void main(String[] args) {
		board = new Board();
		engine = new Engine(board);
		ai = new AI();
		//engine.run();
		
		frame = new MainFrame(engine, ai);
		frame.setVisible(true);
	}

}
