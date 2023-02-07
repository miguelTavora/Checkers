package gui.menu;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;


public class FilesObtainer {
	
	private String pathBackground = "src/img/back.jpg";
	private String pathNewGame = "src/img/newgame3.png";
	private String pathPlayMusic = "src/img/playmusic2.png";
	private String pathStopMusic = "src/img/stopmusic.png";
	private String pathRules = "src/img/rules.png";
	private String pathExit = "src/img/exit4.png";
	private String pathRulesText = "src/img/rulestext3.png";
	private String pathReturn = "src/img/return.png";
	private String pathFirstPlayer = "src/img/firstplayer.png";
	private String pathSecondPlayer = "src/img/secondplayer.png";
	private String pathEasyDificulty = "src/img/easydificulty.png";
	private String pathMediumDificulty = "src/img/mediumdificulty.png";
	
	private Image background;
	private Image newGame;
	private Image playMusic;
	private Image stopMusic;
	private Image rules;
	private Image exit;
	private Image rulesText;
	private Image returnImg;
	private Image firstPlayer;
	private Image secondPlayer;
	private Image easyDificulty;
	private Image mediumDificulty;
	
	private Clip clip;
	private File musicFile;
	
	private String pathSong = "src/music/airport_infiltration.wav";
	
	
	private boolean stopThread = false;

	//construtor que faz load das musicas
	public FilesObtainer() {
		background = Toolkit.getDefaultToolkit().createImage(pathBackground);
		newGame = Toolkit.getDefaultToolkit().createImage(pathNewGame);
		playMusic = Toolkit.getDefaultToolkit().createImage(pathPlayMusic);
		stopMusic = Toolkit.getDefaultToolkit().createImage(pathStopMusic);
		rules = Toolkit.getDefaultToolkit().createImage(pathRules);
		exit = Toolkit.getDefaultToolkit().createImage(pathExit);
		rulesText = Toolkit.getDefaultToolkit().createImage(pathRulesText);
		returnImg = Toolkit.getDefaultToolkit().createImage(pathReturn);
		firstPlayer = Toolkit.getDefaultToolkit().createImage(pathFirstPlayer);
		secondPlayer = Toolkit.getDefaultToolkit().createImage(pathSecondPlayer);
		easyDificulty = Toolkit.getDefaultToolkit().createImage(pathEasyDificulty);
		mediumDificulty = Toolkit.getDefaultToolkit().createImage(pathMediumDificulty);
		
		musicFile = new File(pathSong);
		if (musicFile.exists()) {
			try {
				clip = AudioSystem.getClip();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
			JOptionPane.showMessageDialog(null, "File not found");
	}

	// runs the music as thread
	public void run() {
		new Thread() {
			public void run() {
				for (;;) {
					if (!stopThread) {
						play(pathSong);
					} else
						break;
				}
				stopThread = false;
			}
		}.start();

	}

	// runs the music stored
	public void play(String path) {
		try {
			AudioInputStream input = AudioSystem.getAudioInputStream(musicFile);
			clip.open(input);

			clip.start();

			// playing
			while ((clip.getFramePosition() < clip.getFrameLength()) && clip.isActive());

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (clip.isOpen())
				clip.close();
		}
	}
	
	// stops the music and also the thread created
	public void stopMusic() {
		stopThread = true;
		clip.stop();
		if(clip.isOpen())
			clip.close();
	}

	public Image getBackground() {
		return this.background;
	}
	
	public Image getNewGame() {
		return this.newGame;
	}

	public Image getPlayMusic() {
		return this.playMusic;
	}
	
	public Image getStopMusic() {
		return this.stopMusic;
	}
	
	public Image getRules() {
		return this.rules;
	}
	
	public Image getExit() {
		return this.exit;
	}
	
	public Image getRulesText() {
		return this.rulesText;
	}
	
	public Image getReturnImg() {
		return this.returnImg;
	}
	
	public Image getFirstPlayer() {
		return this.firstPlayer;
	}
	
	public Image getSecondPlayer() {
		return this.secondPlayer;
	}
	
	public Image getEasyDificulty() {
		return this.easyDificulty;
	}
	
	public Image getMediumDificulty() {
		return this.mediumDificulty;
	}
}

