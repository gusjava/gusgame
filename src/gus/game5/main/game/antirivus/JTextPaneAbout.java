package gus.game5.main.game.antirivus;

import javax.swing.JComponent;

import gus.game5.core.game.gui.JPanelDialogClose;
import gus.game5.core.game.gui.JTextPane1;

public class JTextPaneAbout extends JPanelDialogClose {
	private static final long serialVersionUID = 1L;

	public JTextPaneAbout() {
		super();
	}
	
	protected JComponent buildCenter() {
		JTextPane1 p = new JTextPane1();
		p.appendBoldLine(25, "About Antivirus game");
		p.appendLine(20, "");
		p.appendLine(20, "Antivirus game is edited by SmartGames");
		p.appendLine(20, "https://www.smartgames.eu/fr/jeux-pour-1-joueur/anti-virus");
		p.appendLine(20, "");
		
		p.appendBoldLine(25, "Game Rules");
		p.appendLine(20, "");
		p.appendLine(20, "For each level from 1 to 60");
		p.appendLine(20, "Move the game pieces around, so you can maneuver the virus (the red game piece) to the exit :");
		p.appendLine(20, "\u26ac Game pieces can only slide diagonally, in 4 directions.");
		p.appendLine(20, "\u26ac Sometimes you need to move pieces together as a group.");
		p.appendLine(20, "\u26ac You are allowed to push part of a game piece through the opening,");
		p.appendLine(20, "to make space to move other game pieces around during your challenge");
		p.appendLine(20, "\u26ac You can move all the game pieces except the small white ones.");
		p.appendLine(20, "You should consider them part of the game board during that specific challenge");
		return p;
	}
}
