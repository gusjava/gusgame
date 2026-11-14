package gus.game5.main.game.p2.c.board.quoridor.v1;

import static gus.game5.core.util.UtilGui.action;
import static gus.game5.core.util.UtilGui.menu;
import static gus.game5.core.util.UtilGui.panelCNS;
import static gus.game5.core.util.UtilGui.radioMenuItem;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.CELL_SIZE;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.PAWN1;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.PAWN2;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.SIZE;
import static gus.game5.main.game.p2.c.board.quoridor.v1.UtilQuoridor.WALL_SIZE;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Font;

import javax.swing.JLabel;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.game.Settings;
import gus.game5.core.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.play1.Play1;
import gus.game5.core.play1.Player1;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point1.Point1D0;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;

public class GameQuoridor1 extends Play1 {

	public static final String TITLE = "Quoridor";
	public static final int BOARD_SIZE = CELL_SIZE*SIZE;
	public static final double WALL_THICKNESS = 8;
	public static final double WALL_LENGTH = CELL_SIZE*2-6;
	public static final double CATCH_RADIUS = CELL_SIZE*0.45;

	public static final Color COLOR1 = new Color(255,204,0);
	public static final Color COLOR2 = new Color(153,51,0);
	public static final Color COLOR_CELL = new Color(240,240,230);
	public static final Color COLOR_WALL = new Color(102,51,0);
	public static final Color COLOR_WALL_KO = new Color(204,0,0);
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
			menu("Player 1 (yellow)",
				radioMenuItem("Human", ()->changeMode1(Mode.HUMAN)),
				radioMenuItem("Computer (Random)", ()->changeMode1(Mode.RANDOM)),
				radioMenuItem("Computer (Min-Max)", ()->changeMode1(Mode.MINMAX))
			),
			menu("Player 2 (brown)",
				radioMenuItem("Human", ()->changeMode2(Mode.HUMAN)),
				radioMenuItem("Computer (Random)", ()->changeMode2(Mode.RANDOM)),
				radioMenuItem("Computer (Min-Max)", ()->changeMode2(Mode.MINMAX))
			)
		);
	}

	protected void initSettings(Settings s) {
		s.setTitle(TITLE);
		s.setWidth(BOARD_SIZE);
		s.setHeight(BOARD_SIZE);
		s.setSleep(10);
		s.setBackground(Color.WHITE);
		s.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
	}

	/*
	 * DATA
	 */

	private QuoridorState state;
	private ShapeBoard<Cell> board;
	private Cell dragged;
	private boolean wallHorizontal;
	private Mode mode1 = Mode.HUMAN;
	private Mode mode2 = Mode.HUMAN;

	protected void initialize2() {
		addPlayer(buildPlayer(mode1));
		addPlayer(buildPlayer(mode2));

		state = new QuoridorState();
		board = newShapeBoard(CELL_SIZE, SIZE, (i,j)->new Cell(i, j));
		dragged = null;
		wallHorizontal = true;

		addDraw(new Walls());
		addDraw(new WallPreview());
		addDraw(new Drag());

		DrawingText gameOverText = newDrawingTextC(Color.GRAY, gameCenter(), "Game Over");
		gameOverText.setFontBold(40);
		gameOverText.setDrawable(this::isGameOver);

		updateLabelInfo1();
		updateLabelInfo2();
	}

	/*
	 * TURN
	 */

	protected void turnStart() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		if(mouse().button3().justPressed()) wallHorizontal = !wallHorizontal;
	}

	protected void played() throws Exception {
		handleGameOver();
		updateLabelInfo2();
	}

	protected void turnEnd() {
		updateLabelInfo1();
	}

	/*
	 * MODE
	 */

	private enum Mode {
		HUMAN, RANDOM, MINMAX
	}

	private void changeMode1(Mode mode) {
		mode1 = mode;
	}

	private void changeMode2(Mode mode) {
		mode2 = mode;
	}

	/*
	 * CELL
	 */

	public class Cell extends ShapeCell {
		public Cell(int i, int j) {
			super(i, j);
		}

		protected void drawShape() {
			fillSquareC(COLOR_CELL, CELL_SIZE-2);
			drawSquareC(Color.GRAY, CELL_SIZE-2);

			int value = state.pawnAt(i, j);
			if(value==0) return;

			Composite composite = g2_getComposite();
			if(this==dragged) g2_setComposite(ALPHA);
			fillRoundC(value==PAWN1 ? COLOR1 : COLOR2, getRadius()-8);
			if(this==dragged) g2_setComposite(composite);
		}

		public boolean isPlayer(int player) {
			return state.pawnAt(i, j)==player;
		}
	}

	/*
	 * MOUSE
	 */

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

	public int[] nearestIntersection(Point0 p) {
		if(p==null) return null;
		int j = (int) Math.round(p.getX()/CELL_SIZE)-1;
		int i = (int) Math.round(p.getY()/CELL_SIZE)-1;
		if(i<0 || i>=WALL_SIZE || j<0 || j>=WALL_SIZE) return null;
		if(intersectionPoint(i, j).dist(p)>CATCH_RADIUS) return null;
		return new int[] {i,j};
	}

	public int[] getWallIntersection() {
		if(!mouse().button1().justPressed()) return null;
		return nearestIntersection(mouse().pointCurrent());
	}

	public boolean isWallHorizontal() {
		return wallHorizontal;
	}

	/*
	 * GAME ACTIONS
	 */

	public QuoridorState getState() {
		return state;
	}

	public boolean attemptToMove(int player, int i, int j) {
		if(!UtilQuoridor.canMove(state, player, i, j)) return false;
		state.setPawn(player, i, j);
		return true;
	}

	public boolean attemptToPlaceWall(int player, int i, int j, boolean horizontal) {
		if(state.wallsLeft(player)<=0) return false;
		if(!UtilQuoridor.canPlaceWall(state, i, j, horizontal)) return false;
		state.setWall(i, j, horizontal);
		state.useWall(player);
		return true;
	}

	public boolean attemptToPlay(int player, QuoridorPlay play) {
		if(play==null) return false;
		if(play.isMove()) return attemptToMove(player, play.getI(), play.getJ());
		return attemptToPlaceWall(player, play.getI(), play.getJ(), play.isHorizontal());
	}

	/*
	 * GAME OVER
	 */

	private void handleGameOver() throws Exception {
		int winner = UtilQuoridor.winner(state);
		if(winner!=0) setGameOver(playerForValue(winner));
	}

	private String getGameOverDisplay() {
		Player1 winner = getGameOver().getWinner();
		return winner!=null ? winner.getDisplay()+" won the game" : "Draw";
	}

	/*
	 * PLAYER
	 */

	private int currentValue() {
		return playIndex()==0 ? PAWN1 : PAWN2;
	}

	private Player1 playerForValue(int value) {
		if(value==PAWN1) return firstPlayer();
		if(value==PAWN2) return secondPlayer();
		return null;
	}

	private Player1 buildPlayer(Mode mode) {
		switch(mode) {
		case HUMAN: return new PlayerQuoridorHuman(this);
		case RANDOM: return new PlayerQuoridorRandom(this);
		case MINMAX: return new PlayerQuoridorMinmax(this);
		}
		return null;
	}

	/*
	 * LABEL INFO
	 */

	private void updateLabelInfo1() {
		if(isGameOver()) labelInfo1.setText(" "+getGameOverDisplay());
		else labelInfo1.setText(" "+currentPlayer().getDisplay()+" is playing"
				+"   wall: "+(wallHorizontal ? "horizontal" : "vertical")
				+"  (right click to rotate, left click to place)");
	}

	private void updateLabelInfo2() {
		if(playerNumber()>0) {
			labelInfo2.setText(" Yellow: "+firstPlayer().getType()+" ("+state.wallsLeft(PAWN1)+" walls)"
					+" , Brown: "+secondPlayer().getType()+" ("+state.wallsLeft(PAWN2)+" walls)");
		}
		else labelInfo2.setText(" ");
	}

	/*
	 * DRAWING
	 */

	private Point1 intersectionPoint(int i, int j) {
		return new Point1(CELL_SIZE*(j+1), CELL_SIZE*(i+1));
	}

	private void drawWall(Drawing1 drawing, int i, int j, boolean horizontal, Color color) {
		Point1 p = intersectionPoint(i, j);
		if(horizontal) drawing.fillRectC(color, p, WALL_LENGTH, WALL_THICKNESS);
		else drawing.fillRectC(color, p, WALL_THICKNESS, WALL_LENGTH);
	}

	private class Walls extends Drawing1 {
		protected void draw() {
			for(int i=0;i<WALL_SIZE;i++) for(int j=0;j<WALL_SIZE;j++) {
				if(state.wallH(i, j)) drawWall(this, i, j, true, COLOR_WALL);
				if(state.wallV(i, j)) drawWall(this, i, j, false, COLOR_WALL);
			}
		}
	}

	private class WallPreview extends Drawing1 {
		protected void draw() {
			if(isGameOver()) return;
			int[] p = nearestIntersection(mouse().pointCurrent());
			if(p==null) return;

			boolean allowed = state.wallsLeft(currentValue())>0
					&& UtilQuoridor.canPlaceWall(state, p[0], p[1], wallHorizontal);

			Composite composite = g2_getComposite();
			g2_setComposite(ALPHA);
			drawWall(this, p[0], p[1], wallHorizontal, allowed ? COLOR_WALL : COLOR_WALL_KO);
			g2_setComposite(composite);
		}
	}

	private class Drag extends Drawing1 {
		public Drag() {
			super();
			setOrigin(new Point1D0(mouse()::point));
		}
		protected void draw() {
			if(dragged==null) return;
			int value = state.pawnAt(dragged.getI(), dragged.getJ());
			if(value==PAWN1) fillRoundC(COLOR1, dragged.getRadius()-8);
			else if(value==PAWN2) fillRoundC(COLOR2, dragged.getRadius()-8);
		}
	}
}
