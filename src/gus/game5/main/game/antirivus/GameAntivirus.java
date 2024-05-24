package gus.game5.main.game.antirivus;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import gus.game5.core.angle.Angle;
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
	
	public static final Color EDGE_BORDER = Color.LIGHT_GRAY;
	public static final Color BACKGROUND = new Color(246,246,246);
	public static final Composite ALPHA = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.4f);
	
	public static void main(String[] args) {
		GameAntivirus main = new GameAntivirus();
		main.displayInWindows();
		main.start();
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
		addDraw(new LevelChip());
		
	}
	
	private void changeLevel(int level) {
		if(level<1) return;
		if(level>UtilAntivirus.LEVEL_NUMBER) return;
		
		this.level = level;
		data = UtilAntivirus.dataForLevel(level);
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
		if(k.in().up()) changeLevel(1);
		if(k.in().down()) changeLevel(UtilAntivirus.LEVEL_NUMBER);
		
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
			double r = CELL_SIZE*0.5;
			Color color = getColor();
			Composite composite = g2_getComposite();
			boolean isDraggedPiece = draggedCell!=null && draggedCell.getValue()==getValue();
			
			if (isIJ(0, 3)) {
				Point1 p1 = Angle.ANGLE135.pointAt(r + 3);
				Point1 p2 = Angle.ANGLE315.pointAt(r + 3);
				drawLine(EDGE_BORDER, p1, p2);
			}
			
			if(!isExternal()) {
				if(isPiece() || isBlocked()) {
					fillRoundC(Color.WHITE, r);
				}
				if(isDraggedPiece) g2_setComposite(ALPHA);
				if(isPiece() || isBlocked()) {
					drawRoundC(Color.GRAY, r);
				}
				if(isEmpty() && isOutput()) 
					fillArcC(color, p(0,0), r*0.7, Angle.ANGLE315, Angle.ANGLE180);
				else fillRoundC(color, r*0.7);
				if(isDraggedPiece) g2_setComposite(composite);
			}
			
			// West edge
			if(isIJ(1, 2) || isIJ(2, 1) || isIJ(3, 0)) {
				drawArcC(EDGE_BORDER, p(0,0), r-3, Angle.ANGLE315, Angle.ANGLE90);
			}
			if(isIJ(2, 2) || isIJ(3, 1)) {
				drawArcC(EDGE_BORDER, p(0,0), r+3, Angle.ANGLE135, Angle.ANGLE90);
			}
			// North edge
			if(isIJ(1, 4) || isIJ(2, 5) || isIJ(3, 6)) {
				drawArcC(EDGE_BORDER, p(0,0), r-3, Angle.ANGLE45, Angle.ANGLE90);
			}
			if(isIJ(2, 4) || isIJ(3, 5)) {
				drawArcC(EDGE_BORDER, p(0,0), r+3, Angle.ANGLE225, Angle.ANGLE90);
			}
			// East edge
			if(isIJ(5, 6) || isIJ(6, 5) || isIJ(7, 4)) {
				drawArcC(EDGE_BORDER, p(0,0), r-3, Angle.ANGLE135, Angle.ANGLE90);
			}
			if(isIJ(5, 5) || isIJ(6, 4)) {
				drawArcC(EDGE_BORDER, p(0,0), r+3, Angle.ANGLE315, Angle.ANGLE90);
			}
			// South edge
			if(isIJ(5, 0) || isIJ(6, 1) || isIJ(7, 2)) {
				drawArcC(EDGE_BORDER, p(0,0), r-3, Angle.ANGLE225, Angle.ANGLE90);
			}
			if(isIJ(5, 1) || isIJ(6, 2)) {
				drawArcC(EDGE_BORDER, p(0,0), r+3, Angle.ANGLE45, Angle.ANGLE90);
			}
			
			// North-East corner
			if(isIJ(4, 6)) {
				drawArcC(EDGE_BORDER, p(0,0), r+3, Angle.ANGLE225, Angle.ANGLE180);
			}
			// South-East corner
			if(isIJ(7, 3)) {
				drawArcC(EDGE_BORDER, p(0,0), r+3, Angle.ANGLE315, Angle.ANGLE180);
			}
			
			// South-West corner
			if(isIJ(4, 0)) {
				drawArcC(EDGE_BORDER, p(0,0), r+3, Angle.ANGLE45, Angle.ANGLE180);
			}
			
			// North-West corner (output)
			if (isIJ(1, 3)) {

				Point1 p1 = Angle.ANGLE135.pointAt(r + 3);
				Point1 p2 = p1.pAdd(r, Angle.ANGLE225);
				Point1 p3 = Angle.ANGLE315.pointAt(r + 3);
				Point1 p4 = p3.pAdd(r, Angle.ANGLE225);
				
				drawLine(EDGE_BORDER, p1, p2);
				drawLine(EDGE_BORDER, p3, p4);
			}
			if (isIJ(0, 3)) {
				Point1 p1 = Angle.ANGLE135.pointAt(r + 3);
				Point1 p2 = Angle.ANGLE315.pointAt(r + 3);
				Point1 pp = Angle.ANGLE45.pointAt(15.2*r);
				
				Point1 p1a = p1.pAdd(r, Angle.ANGLE45);
				Point1 p2a = p2.pAdd(r, Angle.ANGLE45);
				
				double d1 = 10*r;
				double d2 = d1+10;
				double d3 = r+3;
				
				Point1 p1b = p1.pAdd(0, d1);
				Point1 p2b = p2.pAdd(d1, 0);
				
				Point1 p1c = p1b.pAdd(d3, 0);
				Point1 p2c = p2b.pAdd(0, d3);
				
				Point1 p1d = p1b.pAdd(d3, d3);
				Point1 p2d = p2b.pAdd(d3, d3);
				
				Point1 p1e = p1d.pAdd(d2, 0);
				Point1 p2e = p2d.pAdd(0, d2);
				
				drawLine(EDGE_BORDER, p1, p1a);
				drawLine(EDGE_BORDER, p2, p2a);
				
				drawLine(EDGE_BORDER, p1, p1b);
				drawLine(EDGE_BORDER, p2, p2b);
				
				drawArcC(EDGE_BORDER, p1c, d3, Angle.ANGLE90, Angle.ANGLE90);
				drawArcC(EDGE_BORDER, p2c, d3, Angle.ANGLE270, Angle.ANGLE90);
				
				drawLine(EDGE_BORDER, p1d, p1e);
				drawLine(EDGE_BORDER, p2d, p2e);
				
				drawArcC(EDGE_BORDER, pp, d3, Angle.ANGLE0, Angle.ANGLE90);
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
	
	/*
	 * LEVEL CHIP
	 */
	
	private class LevelChip extends Drawing1 {
		public LevelChip() {
			super();
			setOrigin(p1(243, 80));
		}
		protected void draw() {
				Color color = UtilAntivirus.getLevelColor(level);
				fillRoundC(color, 20);
				drawStringC(Color.WHITE, fontBold(23), p(0,-2), ""+level);
		}
	}
}
