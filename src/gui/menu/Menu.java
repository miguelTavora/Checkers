package gui.menu;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import gui.MainFrame;

public class Menu extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;
	private MainFrame frame;
	private FilesObtainer filesObt;
	
	private final int WIDTH = 2560;
	private final int HEIGHT = 1707;
	private int state = 0;
	private boolean musicPlaying = false;
	private int player = 1;
	private int dificulty = 1;
	
	public Menu(MainFrame frame) {
		this.frame = frame;
		filesObt = new FilesObtainer();
		this.addMouseListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		int[] coord = getImageCoordinates();
		
		
		
		g.drawImage(filesObt.getBackground(), coord[0], coord[1], this);
		if (state == 0) {
			int[] coord2 = getImageCoordinates(400, 96);
			g.drawImage(filesObt.getNewGame(), coord2[0], coord2[1] - 120, this);
			Image music = musicPlaying == false ? filesObt.getPlayMusic() : filesObt.getStopMusic();
			g.drawImage(music, coord2[0], coord2[1] + 20, this);
			g.drawImage(filesObt.getRules(), coord2[0], coord2[1] + 160, this);
			g.drawImage(filesObt.getExit(), coord2[0], coord2[1] + 300, this);
		}
		else if(state == 1) {
			int[] coord2 = getImageCoordinates(400, 96);
			g.drawImage(filesObt.getFirstPlayer(), coord2[0], coord2[1] - 150, this);
			g.drawImage(filesObt.getSecondPlayer(), coord2[0], coord2[1] + 150, this);
		}
		
		else if(state == 2) {
			int[] coord2 = getImageCoordinates(400, 96);
			g.drawImage(filesObt.getEasyDificulty(), coord2[0], coord2[1] - 150, this);
			g.drawImage(filesObt.getMediumDificulty(), coord2[0], coord2[1] + 150, this);
		}
		
		else if(state == 3) {
			int[] coord2 = getImageCoordinates(720, 727);
			g.drawImage(filesObt.getRulesText(), coord2[0], coord2[1]-10, this);
			g.drawImage(filesObt.getReturnImg(), coord2[0]+500, coord2[1]+660, this);
		}
	}

	// gives the addiction needed to center the image
	private int[] getImageCoordinates() {
		int difWidth = frame.getWidth() - WIDTH;
		int difHeight = frame.getHeight() - HEIGHT;

		int dividedDifWidth = ((int) difWidth / 2);
		int dividedDifHeight = ((int) difHeight / 2);

		return new int[] { dividedDifWidth, dividedDifHeight };
	}
	
	private int[] getImageCoordinates(int width, int height) {
		int difWidth = frame.getWidth() - width;
		int difHeight = frame.getHeight() - height;

		int dividedDifWidth = ((int) difWidth / 2);
		int dividedDifHeight = ((int) difHeight / 2);

		return new int[] { dividedDifWidth, dividedDifHeight };
	}
	
	public int getPlayer() {
		return this.player;
	}
	
	public int getDificulty() {
		return this.dificulty;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		int[] coord = getImageCoordinates(400, 96);
		
		
		if (state == 0) {
			if (x > coord[0] + 20 && x < coord[0] + 400 - 20 && y > coord[1] - 120 && y < coord[1] - 28) {
				state = 1;
				repaint();
			} 
			else if (x > coord[0] + 20 && x < coord[0] + 400 - 20 && y > coord[1] + 20 && y < coord[1] + 112) {
				if(!musicPlaying) {
					filesObt.run();
					musicPlaying = true;
				}
				else {
					filesObt.stopMusic();
					musicPlaying = false;
				}
				repaint();
			} 
			else if (x > coord[0] + 20 && x < coord[0] + 400 - 20 && y > coord[1] + 160 && y < coord[1] + 252) {
				state = 3;
				repaint();
			} 
			
			else if (x > coord[0] + 20 && x < coord[0] + 400 - 20 && y > coord[1] + 300 && y < coord[1] + 392) 
				System.exit(0);
			
		}
		else if(state == 1) {
			if (x > coord[0] + 20 && x < coord[0] + 400 - 20 && y > coord[1] - 150 && y < coord[1] - 59) {
				player = 1;
				state = 2;
			}
			else if(x > coord[0] + 20 && x < coord[0] + 400 - 20 && y > coord[1] + 150 && y < coord[1] + 242) {
				player = 2;
				state = 2;
			}
			repaint();
		}
		
		else if(state == 2) {
			if (x > coord[0] + 20 && x < coord[0] + 400 - 20 && y > coord[1] - 150 && y < coord[1] - 59) {
				dificulty = 1;
				frame.createGame(player, dificulty);
			}
			else if(x > coord[0] + 20 && x < coord[0] + 400 - 20 && y > coord[1] + 150 && y < coord[1] + 242) {
				dificulty = 2;
				frame.createGame(player, dificulty);
			}
		}
		
		else if(state == 3) {
			int[] coord2 = getImageCoordinates(720, 727);
			if(x > coord2[0]+510 && x < coord2[0]+700 && y > coord[1] +345 && y < coord[1] +345+48) {
				state = 0;
				repaint();
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
