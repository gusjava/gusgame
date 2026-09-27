package gus.game5.main.game.p1.c.solitaire8;

import static gus.game5.core.util.UtilGui.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JComponent;

import gus.game5.core.gui.ImageDisplay;
import gus.game5.core.gui.JPanelDialogClose;
import gus.game5.core.gui.JTextPane1;

public class JTextPaneAbout extends JPanelDialogClose {
	private static final long serialVersionUID = 1L;

	public JTextPaneAbout() {
		super();
		setHeight(450);
	}
	
	protected JComponent buildCenter() {
		JTextPane1 p1 = new JTextPane1();
		p1.setFont(new Font("Calibri", Font.PLAIN, 20));
		
		p1.appendBoldLine(25, "About The 8 enemy pawns");
		p1.appendLine("");
		p1.appendLine("This game consists in shifting the red and black pawns positions");
		

		JTextPane1 p2 = new JTextPane1();
		p2.setFont(new Font("Calibri", Font.PLAIN, 20));
		
		p2.appendBoldLine(25, "Game Rules");
		p2.appendLine("");
		p2.appendLine("\u26ac At start, the center cell is empty, separating the 8 red pawns and the 8 black pawns.");
		p2.appendLine("\u26ac Pawns can move vertically or horizontally to an empty cell.");
		p2.appendLine("\u26ac They can move directly or jump over another pawn.");
		p2.appendLine("\u26ac They can only move forward.");

		ImageDisplay image = new ImageDisplay("/gus/game5/main/game/p1/c/solitaire8/illustration.jpg");
		image.setPreferredSize(new Dimension(200,0));
		image.setBackground(Color.WHITE);
		
		return panelCN(p2, panelCE(p1, image));
	}
}
