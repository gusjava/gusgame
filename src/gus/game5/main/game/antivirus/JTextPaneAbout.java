package gus.game5.main.game.antivirus;

import static gus.game5.core.util.UtilGui.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComponent;

import gus.game5.core.game.gui.ImageDisplay;
import gus.game5.core.game.gui.JLabelLink;
import gus.game5.core.game.gui.JPanelDialogClose;
import gus.game5.core.game.gui.JTextPane1;

public class JTextPaneAbout extends JPanelDialogClose {
	private static final long serialVersionUID = 1L;
	
	public static final String LINK = "https://www.smartgames.eu/fr/jeux-pour-1-joueur/anti-virus";

	public JTextPaneAbout() {
		super();
		setHeight(500);
	}
	
	protected JComponent buildCenter() {
		JTextPane1 p1 = new JTextPane1();
		p1.setFont(new Font("Calibri", Font.PLAIN, 20));
		
		p1.appendBoldLine(25, "About Antivirus game");
		p1.appendLine("");
		p1.appendLine("Antivirus game is edited by SmartGames");
		
		JLabelLink link = new JLabelLink(LINK);
		fontSize(link, 20);
		empty(link, 0, 10, 0, 10);

		JTextPane1 p2 = new JTextPane1();
		p2.setFont(new Font("Calibri", Font.PLAIN, 20));
		
		p2.appendBoldLine(25, "Game Rules");
		p2.appendLine("");
		p2.appendLine("For each level from 1 to 60");
		p2.appendLine("Move the game pieces around, so you can maneuver the virus (the red game piece) to the exit :");
		p2.appendLine("\u26ac Game pieces can only slide diagonally, in 4 directions.");
		p2.appendLine("\u26ac Sometimes you need to move pieces together as a group.");
		p2.appendLine("\u26ac You are allowed to push part of a game piece through the opening,");
		p2.appendLine("to make space to move other game pieces around during your challenge");
		p2.appendLine("\u26ac You can move all the game pieces except the small white ones.");
		p2.appendLine("You should consider them part of the game board during that specific challenge");

		ImageDisplay image = new ImageDisplay("/gus/game5/main/game/antivirus/illustration.jpg");
		image.setPreferredSize(new Dimension(200,0));
		image.setBackground(Color.WHITE);
		
		return panelCN(p2, panelCE(panelCN(link, p1), image));
	}
}
