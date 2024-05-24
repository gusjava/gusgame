package gus.game5.main.game.antirivus;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.game.gui.JRadioButtonMenuItem1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point1.Point1D0;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;
import gus.game5.core.util.UtilArray;

public class GameAntivirus extends Game1 {

	public static final String TITLE = "Antivirus";
	public static final int GAME_HEIGHT = 500;
	public static final int GAME_WIDTH = 500;
	public static final double CELL_SIZE = 50;
	
	public static final Composite ALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
	
	public static void main(String[] args) {
		GameAntivirus main = new GameAntivirus();
		main.displayInWindows();
		main.start();
	}
	
	/*
	 * CONTENT PANE
	 */

	private JLabel labelInfo;
	
	protected Container buildContentPane() {
		labelInfo = new JLabel(" ");
		
		return panelCN(panel(), 
				raised(labelInfo));
	}
	
	/*
	 * MENU BAR
	 */
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Game", 
			action("New game (F1)", this::restart),
			action("Exit (F2)", this::exit)
		);
		
		int nb = UtilAntivirus.LEVEL_NUMBER;
		JRadioButtonMenuItem1[] levelButtons = new JRadioButtonMenuItem1[nb];
		for(int i=0;i<nb;i++) {
			final int level = i+1;
			levelButtons[i] = radioMenuItem(""+level, ()->changeLevel(level));
		}
		menuBar.add("Level",levelButtons);
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
	 * DATA
	 */

	private ShapeBoard<Cell> board;
	
	private Cell draggedCell;
	private List<Cell> draggedPiece;
	private int draggedValue;
	
	private int[][] data;
	private int level;
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize1() {
		changeLevel(1);
		board = newShapeBoard(CELL_SIZE, 8, 7, Cell::new);
		draggedCell = null;
		draggedPiece = null;
		draggedValue = UtilAntivirus.EMPTY;
		addDraw(new Drag());
	}
	
	private void changeLevel(int level) {
		if(level<1) return;
		if(level>UtilAntivirus.LEVEL_NUMBER) return;
		
		this.level = level;
		data = UtilAntivirus.dataForLevel(level);
		labelInfo.setText(" Level "+level);
	}
	
	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		if(k.in().right()) changeLevel(level+1);
		if(k.in().left()) changeLevel(level-1);
		
		if(mouse().button1().justPressed()) {
			Cell pressedCell = board.cellAt(mouse().pointCurrent());
			if(pressedCell!=null && pressedCell.isDraggable()) {
				draggedCell = pressedCell;
				draggedValue = pressedCell.getValue();
				draggedPiece = board.findAll(c->c.getValue()==draggedValue);
			}
		}
		else if(mouse().button1().justReleased()) {
			Cell releasedCell = board.cellAt(mouse().pointCurrent());
			if(releasedCell!=null && draggedCell!=null) {
				int[] releasedPos = releasedCell.getIJ();
				int[] draggedPos = draggedCell.getIJ();
				if(UtilArray.distance(releasedPos, draggedPos)==1) {
					int di = releasedPos[0]-draggedPos[0];
					int dj = releasedPos[1]-draggedPos[1];
					List<Cell> draggedPieceTarget = findDraggedPieceTarget(di, dj);
					
					if(draggedPieceTarget!=null) {
						for(Cell c : draggedPiece) {
							UtilArray.set(data, c.getIJ(), UtilAntivirus.EMPTY);
						}
						for(Cell c : draggedPieceTarget) {
							UtilArray.set(data, c.getIJ(), draggedValue);
						}
						if(board.find(Cell::isOutput).hasAntivirus()) {
							changeLevel(level+1);
						}
					}
				}
			}
			draggedCell = null;
			draggedPiece = null;
			draggedValue = UtilAntivirus.EMPTY;
		}
	}
	
	private List<Cell> findDraggedPieceTarget(int di, int dj) {
		List<Cell> target = new ArrayList<>();
		for(Cell c : draggedPiece) {
			Cell c1 = board.cellAt(c.getI()+di, c.getJ()+dj);
			if(c1.isEmpty() || c1.getValue()==draggedValue) target.add(c1);
			else return null;
		}
		return target;
	}
	
	
	
	
	public class Cell extends ShapeCell {
		public Cell(int i, int j) {
			super(i, j);
		}
		
		protected void drawShape() {
			Color color = getColor();
			Composite composite = g2_getComposite();
			double r = CELL_SIZE*0.5;
			boolean isDraggedPiece = draggedCell!=null && draggedCell.getValue()==getValue();
			
			if(isDraggedPiece) g2_setComposite(ALPHA);
			if(isPiece() || isBlocked() && !isExternal()) drawRoundC(Color.GRAY, r);
			fillRoundC(color, r*0.7);
			if(isDraggedPiece) g2_setComposite(composite);
			
			if(isOutput()) {
				drawArrow(p(8,8), p(-8,-8));
			}
		}
		
		protected void initAnchor() {
			int k = i+j;
			double px = gameHeight()*0.5 + CELL_SIZE*1.4*0.5*(k - 0.9*x);
			double py = gameWidth()*0.5 - 50 - CELL_SIZE*1.4*(j - 0.5*k);
			setAnchor(new Point2(px, py));
		}
		
		public Color getColor() {
			return isEmpty() ? Color.LIGHT_GRAY : UtilAntivirus.COLORS[getValue()];
		}
		public int getValue() {
			return data[i][j];
		}
		public boolean isPiece() {
			return data[i][j]>=0 && data[i][j]<=8;
		}
		public boolean isEmpty() {
			return data[i][j]==UtilAntivirus.EMPTY;
		}
		public boolean isBlocked() {
			return data[i][j]==UtilAntivirus.BLOCKED;
		}
		public boolean isExternal() {
			return UtilAntivirus.isExternal(i,j);
		}
		public boolean isDraggable() {
			return isPiece();
		}
		public boolean isOutput() {
			return i==UtilAntivirus.OUTPUT_I && j==UtilAntivirus.OUTPUT_J;
		}
		public boolean hasAntivirus() {
			return data[i][j]==UtilAntivirus.PIECE0;
		}
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
			if(draggedCell!=null) {
				Color color = draggedCell.getColor();
				Point1 draggedCellAnchor = draggedCell.getAnchor();
				
				for(Cell c : draggedPiece) {
					Point1 m = c.getAnchor().pSub(draggedCellAnchor);
					double r = CELL_SIZE*0.5;
					drawRoundC(Color.GRAY, m, r);
					fillRoundC(color, m, r*0.7);
				}
			}
		}
	}
}
