package gus.game5.main.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gus.game5.core.game.Game;

public class GameArea extends JPanel implements WindowListener {
	private static final long serialVersionUID = 1L;
	
	private Game game;
	private JFrame frame;
	private JButton button;
	
	public GameArea(Game game) {
		super(new BorderLayout());
		this.game = game;
		game.setExit(this::stopGame);
		
		button = new JButton(game.getTitle());
		button.setForeground(Color.GRAY);
		button.addActionListener(e->this.startGame());
		add(button, BorderLayout.CENTER);
		setPreferredSize(new Dimension(0,150));
	}
	
	private void startGame() {
		if(game.isRunning()) game.stop();
		
		disposeFrame();
		frame = game.displayInWindows(false);
		frame.addWindowListener(this);
		
		game.start();
		button.setForeground(Color.BLUE);
	}
	
	private void stopGame() {
		disposeFrame();
		if(game.isRunning()) game.stop();
		button.setForeground(Color.GRAY);
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
