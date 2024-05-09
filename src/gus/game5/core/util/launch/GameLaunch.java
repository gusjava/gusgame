package gus.game5.core.util.launch;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import gus.game5.core.game.Game;

public class GameLaunch implements WindowListener {

	private Game game;
	private JFrame frame;
	private Runnable onGameStopped;
	private Runnable onGameStarted;
	
	public GameLaunch(Game game) {
		this.game = game;
		game.setExit(this::stopGame);
	}
	
	public void setOnGameStopped(Runnable onGameStopped) {
		this.onGameStopped = onGameStopped;
	}
	
	public void setOnGameStarted(Runnable onGameStarted) {
		this.onGameStarted = onGameStarted;
	}
	
	public void startGame() {
		if(game.isRunning()) game.stop();
		
		disposeFrame();
		frame = game.displayInWindows(false);
		frame.addWindowListener(this);
		
		game.start();
		if(onGameStarted!=null) onGameStarted.run();
	}
	
	public void stopGame() {
		disposeFrame();
		if(game.isRunning()) game.stop();
		if(onGameStopped!=null) onGameStopped.run();
	}
	
	public boolean isRunning() {
		return game.isRunning();
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Game getGame() {
		return game;
	}
	
	private void disposeFrame() {
		if(frame==null) return;
		frame.removeWindowListener(this);
		frame.dispose();
		frame = null;
	}

	public void windowOpened(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {stopGame();}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
}
