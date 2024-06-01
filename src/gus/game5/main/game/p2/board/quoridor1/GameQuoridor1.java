package gus.game5.main.game.p2.board.quoridor1;

import static gus.game5.core.util.UtilGui.action;
import static gus.game5.core.util.UtilGui.menu;
import static gus.game5.core.util.UtilGui.panelCNS;
import static gus.game5.core.util.UtilGui.radioMenuItem;
import static gus.game5.main.game.p2.board.quoridor1.UtilQuoridor.INIT_STATE;
import static gus.game5.main.game.p2.board.quoridor1.UtilQuoridor.PAWN1;
import static gus.game5.main.game.p2.board.quoridor1.UtilQuoridor.PAWN2;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JLabel;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Settings;
import gus.game5.core.gui.JMenuBar1;
import gus.game5.core.play1.Play1;
import gus.game5.core.play1.Player1;
import gus.game5.core.point.point1.Point1D0;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;

public class GameQuoridor1 extends Play1 {
	
	public static final String TITLE = "Quoridor";
	public static final int CELL_SIZE = 40;
	
	public static final Color COLOR1 = new Color(255,204,0);
	public static final Color COLOR2 = new Color(153,51,0);
	public static final Composite ALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
	
	public static void main(String[] args) {
		GameQuoridor1 main = new GameQuoridor1();
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
		return panelCNS(panel(), labelInfo1, labelInfo2);
	}
	
	/*
	 * MENU BAR
	 */
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Game", 
			action("New game (F1)", this::restart),
			action("Exit (F2)", this::exit)
		);
		menuBar.add("Players", 
			menu("Player 1 (white)",
				radioMenuItem("Human", ()->changeMode1(Mode.HUMAN)),
				radioMenuItem("Computer (Random)", ()->changeMode1(Mode.RANDOM))
//				radioMenuItem("Computer (Min-Max)", ()->changeMode1(Mode.MINMAX))
			),
			menu("Player 2 (black)",
				radioMenuItem("Human", ()->changeMode2(Mode.HUMAN)),
				radioMenuItem("Computer (Random)", ()->changeMode2(Mode.RANDOM))
//				radioMenuItem("Computer (Min-Max)", ()->changeMode2(Mode.MINMAX))
			)
		);
	}
	
	protected void initSettings(Settings s) {
		s.setTitle(TITLE);
		s.setWidth(700);
		s.setHeight(700);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
		s.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
	}
	
	/*
	 * DATA
	 */
	
	private ShapeBoard<Cell> board;
	private Cell dragged;
	private Mode mode1 = Mode.HUMAN;
	private Mode mode2 = Mode.HUMAN;
	
	protected void initialize2() {
		addPlayer(buildPlayer(mode1));
		addPlayer(buildPlayer(mode2));
		
		board = newShapeBoard(CELL_SIZE, 9, (i,j)->new Cell(i, j, INIT_STATE[i][j]));
		
		addDraw(new Drag());
	}
	
	
	protected void played() {
		
	}
	
	protected void turnStart() {
		
	}
	
	protected void turnEnd() {
		
	}
	
	/*
	 * MODE
	 */
	
	private enum Mode {
		HUMAN, RANDOM
//		, MINMAX
	}
	
	private void changeMode1(Mode mode) {
		mode1 = mode;
		updateLabelInfo2();
	}
	
	private void changeMode2(Mode mode) {
		mode2 = mode;
		updateLabelInfo2();
	}
	
	/*
	 * CELL
	 */
	
	public class Cell extends ShapeCell {
		private int value;
		public Cell(int i, int j, int value) {
			super(i, j);
			this.value = value;
		}
		
		protected void drawShape() {
			Composite composite = g2_getComposite();
			if(this==dragged) g2_setComposite(ALPHA);
			drawSquareC(getRadius()*2-5);
			if(value==PAWN1) fillRoundC(COLOR1, getRadius()-5);
			else if(value==PAWN2) fillRoundC(COLOR2, getRadius()-5);
			if(this==dragged) g2_setComposite(composite);
		}
		
		public boolean isPlayer(int player) {
			return player==value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	public Cell getPressedCell() {
		if(!mouse().button1().justPressed()) return null;
		return board.cellAt(mouse().pointCurrent());
	}
	
	public Cell getReleasedCell() {
		if(!mouse().button1().justReleased()) return null;
		return board.cellAt(mouse().pointCurrent());
	}
	
	public void setDragged(Cell dragged) {
		this.dragged = dragged;
	}
	
	public Cell getDragged() {
		return dragged;
	}
	
	public boolean attemptToPlay(int player, int[] start, int[] end) {
		return false;
	}
	
	/*
	 * LABEL INFO
	 */
	
	private void updateLabelInfo1() {
//		if(isGameOver()) {
//			labelInfo1.setText(" "+getGameOverDisplay());
//		}
//		else {
//			String info = " "+currentPlayer().getDisplay()+" is playing";
//			if(engine.isPlayerChecked()) info += " (check)";
//			labelInfo1.setText(info);
//		}
	}
	
	private void updateLabelInfo2() {
		if(playerNumber()>0) {
			String type1 = firstPlayer().getType();
			String type2 = secondPlayer().getType();
			labelInfo2.setText("White: "+type1+" , Black: "+type2);
		}
		else labelInfo2.setText(" ");
	}
	
	/*
	 * DRAG
	 */
	
	private class Drag extends Drawing1 {
		public Drag() {
			super();
			setOrigin(new Point1D0(mouse()::point));
		}
		protected void draw() {
			if(dragged!=null) {
				int value = dragged.getValue();
				double r = dragged.getRadius();
				
				if(value==PAWN1) fillRoundC(COLOR1, r-5);
				else if(value==PAWN2) fillRoundC(COLOR2, r-5);
			}
		}
	}
	
	/*
	 * PLAYER
	 */
	
	private Player1 playerForValue(int value) {
		if(value==PAWN1) return firstPlayer();
		if(value==PAWN2) return secondPlayer();
		return null;
	}
	
	private Player1 buildPlayer(Mode mode) {
		switch(mode) {
		case HUMAN: return new PlayerQuoridorHuman(this);
//		case RANDOM: return new PlayerQuoridorRandom(this);
//		case MINMAX: return new PlayerComputerMinmax(this);
		}
		return null;
	}
}
