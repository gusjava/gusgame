package gus.game5.main.game.p1.towerofhanoi;

import java.awt.Font;

import javax.swing.JComponent;

import gus.game5.core.gui.JPanelDialogClose;
import gus.game5.core.gui.JTextPane1;

public class JTextPaneAbout extends JPanelDialogClose {
	private static final long serialVersionUID = 1L;

	public JTextPaneAbout() {
		super();
		setHeight(500);
		setWidth(700);
	}
	
	protected JComponent buildCenter() {
		JTextPane1 p = new JTextPane1();
		p.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		
		p.appendBoldLine(25, "About Tower of Hanoi");
		p.appendLine("This game has been invented by the French mathematician Edouard Lucas at the end of the 19th century.");
		p.appendLine("");
		p.appendBoldLine(25, "Game Rules");
		p.appendLine("At the beginning of the game, there is a tower situated on the left, composed of disks of different sizes. The purpose of the game is to move all the disks from the left to the right.");
		p.appendLine("");
		p.appendLine("\u26ac You can only move one single disk at the time.");
		p.appendLine("\u26ac Moves can only be perform from top to top.");
		p.appendLine("\u26ac You may not place a disk on top of a smaller disk.");
		p.appendLine("");
		p.appendBoldLine(25, "Game's duration :");
		p.appendLine("You will notice that time increases exponentially with disks number.");
		p.appendLine("A time estimation is given considering that one second is needed for each move.");
		
		return p;
	}
}
