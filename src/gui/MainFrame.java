package gui;

import javax.swing.JFrame;

import ai.AI;
import board.Engine;
import gui.game.GamePanel;
import gui.menu.Menu;

public class MainFrame extends JFrame {

	public static final int EASY_MODE = 1;
	public static final int MEDIMUM_MODE = 2;
	private static final long serialVersionUID = 1L;
	private Menu menu;
	private GamePanel gamePanel;
	private Engine engine;
	private AI ai;

	public MainFrame(Engine engine, AI ai) {
		setFrame();
		this.engine = engine;
		this.ai = ai;
		menu = new Menu(this);
		setContentPane(menu);
	}
	
	public void setFrame() {
		setTitle("Damas");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
	}
	
	public void createGame(int player, int dificulty) {
		gamePanel = new GamePanel(this, engine, ai, player, dificulty);
		setContentPane(gamePanel);
		gamePanel.revalidate();
		gamePanel.repaint();

	}

}
