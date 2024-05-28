package gus.game5.main.canevas;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JLabel;

import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;

import static gus.game5.core.util.UtilGui.*;

public class Canevas1a extends Game1 {
	
	public static final String TITLE = "title";

	public static final int GAME_HEIGHT = 800;
	public static final int GAME_WIDTH = 800;
	
	public static void main(String[] args) {
		Canevas1a main = new Canevas1a();
		main.displayInWindows();
		main.start();
	}
	
	/*
	 * CONTENT PANE
	 */

	private JLabel labelInfo1;
	private JLabel labelInfo2;
	
	protected Container buildContentPane() {
		labelInfo1 = new JLabel(" ");
		labelInfo2 = new JLabel(" ");
		
		return panelCNS(panel(), 
				raised(labelInfo1), 
				raised(labelInfo2));
	}
	
	/*
	 * MENU BAR
	 */
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Game", 
			action("New game (F1)", this::restart),
			action("Exit (F2)", this::exit)
		);
	}
	
	/*
	 * SETTINGS
	 */
	
	protected void initSettings(Settings s) {
		s.setTitle(TITLE);
		s.setWidth(GAME_WIDTH);
		s.setHeight(GAME_HEIGHT);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
		s.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
	}
	
	
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize1() {
		
	}
	
	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
	}
}
