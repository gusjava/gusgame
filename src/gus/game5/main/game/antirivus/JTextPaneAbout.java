package gus.game5.main.game.antirivus;

import gus.game5.core.game.gui.JDialog1;
import gus.game5.core.game.gui.JTextPane1;

public class JTextPaneAbout extends JTextPane1 {
	private static final long serialVersionUID = 1L;
	
	private JDialog1 dialog = new JDialog1();

	public JTextPaneAbout() {
		super();
		setMargin(50);
		appendBoldLine(25, "Game Rules");
		appendLine(20, "");
		appendLine(20, "For each level from 1 to 60");
		appendLine(20, "Move the game pieces around, so you can maneuver the virus (the red game piece) to the exit :");
		appendLine(20, "\u26ac Game pieces can only slide diagonally, in 4 directions.");
		appendLine(20, "\u26ac Sometimes you need to move pieces together as a group.");
		appendLine(20, "\u26ac You are allowed to push part of a game piece through the opening,");
		appendLine(20, "to make space to move other game pieces around during your challenge");
		appendLine(20, "\u26ac You can move all the game pieces except the small white ones.");
		appendLine(20, "You should consider them part of the game board during that specific challenge");
		appendLine(20, "");
		appendBoldLine(25, "About Antivirus game");
		appendLine(20, "");
		appendLine(20, "Antivirus game is edited by SmartGames");
		appendLine(20, "https://www.smartgames.eu/fr/jeux-pour-1-joueur/anti-virus");
	}
	
	public void display() {
		dialog.showContent(this, 1000, 700);
	}
}
