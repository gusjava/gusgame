package gus.game5.main.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import gus.game5.core.game.Game;
import gus.game5.core.util.launch.GameLaunch;

public class GameArea extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private GameLaunch launch;
	private JButton button;
	
	public GameArea(Game game) {
		super(new BorderLayout());
		launch = new GameLaunch(game);
		launch.setOnGameStarted(this::onGameStarted);
		launch.setOnGameStopped(this::onGameStopped);
		
		button = new JButton(game.getTitle());
		button.setForeground(Color.GRAY);
		button.addActionListener(e->launch.startGame());
		add(button, BorderLayout.CENTER);
		setPreferredSize(new Dimension(0,150));
	}
	
	private void onGameStarted() {
		button.setForeground(Color.BLUE);
	}
	
	private void onGameStopped() {
		button.setForeground(Color.GRAY);
	}
}
