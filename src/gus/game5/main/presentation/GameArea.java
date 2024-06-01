package gus.game5.main.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import gus.game5.core.game.Game;
import gus.game5.core.gui.ImageDisplay;
import gus.game5.core.util.UtilCapture;
import gus.game5.core.util.launch.GameLaunch;

public class GameArea extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static final Color COLOR1 = new Color(102,102,255);
	public static final Color COLOR2 = new Color(204,204,255);
	
	private GameLaunch launch;
	
	private JLabel labelTitle;
	private JTextArea areaDescription;
	private JButton buttonStart;
	private JButton buttonCapture;
	private ImageDisplay imageDisplay;
	
	public GameArea(Game game) {
		super(new BorderLayout());
		launch = new GameLaunch(game);
		launch.setOnGameStarted(this::onGameStarted);
		launch.setOnGameStopped(this::onGameStopped);
		
		labelTitle = new JLabel(game.getTitle());
		labelTitle.setIcon(game.getIcon());
		labelTitle.setHorizontalAlignment(JLabel.CENTER);
		labelTitle.setFont(labelTitle.getFont().deriveFont(Font.BOLD, 15f));
		
		buttonStart = new JButton("Start");
		buttonStart.setBackground(COLOR2);
		buttonStart.setForeground(COLOR1);
		buttonStart.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		buttonStart.addActionListener(e->launch.startGame());

		buttonCapture = new JButton();
		buttonCapture.setBackground(Color.LIGHT_GRAY);
		buttonCapture.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		buttonCapture.addActionListener(e->captureFrame());
		
		areaDescription = new JTextArea();
		areaDescription.setEditable(false);
		
		imageDisplay = new ImageDisplay("/gus/game5/main/presentation/data/"+game.getClass().getSimpleName()+".jpg");

		JPanel panelButtons = new JPanel(new BorderLayout());
		panelButtons.add(buttonStart, BorderLayout.CENTER);
		panelButtons.add(buttonCapture, BorderLayout.EAST);
		
		JPanel panelCenter = new JPanel(new BorderLayout());
		panelCenter.add(panelButtons, BorderLayout.NORTH);
		panelCenter.add(imageDisplay, BorderLayout.CENTER);
		
		add(labelTitle, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		setPreferredSize(new Dimension(0,200));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}
	
	private void onGameStarted() {
		buttonStart.setEnabled(false);
		buttonStart.setText("Running");
		buttonStart.setForeground(Color.GREEN);
	}
	
	private void onGameStopped() {
		buttonStart.setEnabled(true);
		buttonStart.setText("Start");
		buttonStart.setForeground(Color.BLUE);
	}
	
	public void setStartIcon(Icon icon) {
		buttonStart.setIcon(icon);
	}
	
	public void setCaptureIcon(Icon icon) {
		buttonCapture.setIcon(icon);
	}
	
	private void captureFrame() {
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {}
				UtilCapture.captureFrame(launch.getFrame());
			}
		}).start();
	}
}
