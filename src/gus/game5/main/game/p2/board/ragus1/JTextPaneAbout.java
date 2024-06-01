package gus.game5.main.game.p2.board.ragus1;

import java.awt.Font;

import javax.swing.JComponent;

import gus.game5.core.gui.JPanelDialogClose;
import gus.game5.core.gui.JTextPane1;

public class JTextPaneAbout extends JPanelDialogClose {
	private static final long serialVersionUID = 1L;

	public JTextPaneAbout() {
		super();
		setHeight(700);
	}
	
	protected JComponent buildCenter() {
		JTextPane1 p = new JTextPane1();
		p.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
		
		p.appendBoldLine(25, "About Ragus game");
		p.appendLine("Ragus is an original 2 players game invented by two friends, Rage and Gus.");
		p.appendLine("");
		p.appendBoldLine(25, "Game Rules");
		p.appendLine("At the beginning of the game, each player has got 24 pawns of level 1 displayed on 3 rows.");
		p.appendLine("The purpose of the game is to bring 4 pawns in the enemy area (row at the top or at the bottom).");
		p.appendLine("");
		p.appendBoldLine("General rules for moving a pawn :");
		p.appendLine("\u26ac A pawn can be moved one square d'une case horizontally or vertically.");
		p.appendLine("\u26ac If the cell is occupied by an identical pawn (same player, same level), they merge into a new pawn of the next level.");
		p.appendLine("(1+1\u21922, 2+2\u21923 3+3\u21924...)");
		p.appendLine("\u26ac If the cell is occupied by another pawn, the two pawns exchange their positions.");
		p.appendLine("\u26ac When one pawn has reached the enemy area, it is removed from the board.");
		p.appendLine("");
		p.appendBoldLine("Specific rule for moving a pawn of level 1 :");
		p.appendLine("\u26ac If a pawn of level 1 is moved on an empty cell, it duplicates.");
		p.appendLine("");
		p.appendBoldLine("Blocking rules :");
		p.appendLine("\u26ac Each pawn has two possible states : free or blocked.");
		p.appendLine("\u26ac At the beginning of the game, every pawn is free.");
		p.appendLine("\u26ac A pawn becomes blocked as soon as its neighborhood (the 8 surrounding cells) contains at least one free enemy pawn of strictly higher level.");
		p.appendLine("");
		p.appendBoldLine("Pawn moving conditions :");
		p.appendLine("\u26ac A blocked pawn cannot be moved nor duplicated.");
		p.appendLine("\u26ac Merging pawns is not possible as one of them at least is blocked.");
		p.appendLine("\u26ac However, you may permute one free pawn with any other pawn, even a blocked pawn.");
		p.appendLine("\u26ac It is not possible to duplicate a pawn of level 1 if the starting of ending cells are situated in the two last rows before the enemy area.");
		p.appendLine("In this case, the pawn is moved.");
		p.appendLine("\u26ac It is not possible to enter its own area.");
		
		return p;
	}
}
