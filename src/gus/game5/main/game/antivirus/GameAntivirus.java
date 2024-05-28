package gus.game5.main.game.antivirus;

import static gus.game5.core.util.UtilGui.action;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.drawing.text.DrawingText;
import gus.game5.core.dyn.DynTimer;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.game.gui.JMenuBar1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point1.Point1D0;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;
import gus.game5.core.util.UtilArray;
import gus.game5.core.util.UtilList;

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
	 * MENU BAR
	 */
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Game", 
			action("New game (F1)", this::restart),
			action("Exit (F2)", this::exit),
			action("About (F3)", this::displayAbout),
			null,
			action("Choose level (SHIFT)", this::chooseLevel),
			action("Next level (\u2192)", this::levelUp),
			action("Previous level (\u2190)", this::levelDown),
			action("First level (\u2191)", this::levelFirst),
			action("Last level (\u2193)", this::levelLast)
		);
		menuBar.add("Profile", 
			action("Create new profile", this::createNewProfile),
			action("Load profile", this::loadProfile),
			action("Close profile", this::closeProfile),
			action("Remove profile", this::removeProfile));
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
		s.setFont(new Font("Calibri", Font.PLAIN, 12));
	}
	
	/*
	 * DATA
	 */

	private ShapeBoard<Cell> board;
	private DrawingText levelTitle;
	private DrawingText profileDisplay;
	private DrawingText completeDisplay;
	
	private Cell draggedCell;
	private List<Cell> draggedPiece;
	private int draggedValue;
	
	private int[][] data;
	private LevelManager levelManager;
	private JTextPaneAbout paneAbout;
	private JPanelLevel panelLevel;
	private DynTimer completeTimer;
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize1() {
		levelManager = new LevelManager(this);
		panelLevel = new JPanelLevel(levelManager);
		paneAbout = new JTextPaneAbout();
		completeTimer = newDynTimer();
		
		board = newShapeBoard(CELL_SIZE, 8, 7, Cell::new);
		draggedCell = null;
		draggedPiece = null;
		draggedValue = UtilAntivirus.EMPTY;
		
		addDraw(new Drag());
		addDraw(new LevelChip());
		
		levelTitle = newDrawingTextP(p1(gameWidth()-20, 20), levelManager::getLevelTitle, 1, 1);
		levelTitle.setGColor(levelManager::getLevelColor);
		levelTitle.setFontBold(26);
		
		profileDisplay = newDrawingTextP(p1(gameWidth()-20, 50), levelManager::getProfileDisplay, 1, 1);
		profileDisplay.setDrawable(levelManager::hasProfile);
		profileDisplay.setFontBold(14);
		
		completeDisplay = newDrawingTextC(p1(240, 30), "Level Complete");
		completeDisplay.setDrawable(completeTimer);
		completeDisplay.setFontBold(25);
		
		levelManager.loadCurrent();
	}
	
	/*
	 * ABOUT
	 */
	
	private void displayAbout() {
		paneAbout.display();
	}
	
	/*
	 * DATA
	 */
	
	public void setData(int[][] data) {
		this.data = data;
	}
	
	/*
	 * LEVEL	
	 */
	
	private void levelWon() {
		levelManager.levelWon();
	}
	
	private void levelUp() {
		levelManager.levelUp();
	}
	
	private void levelDown() {
		levelManager.levelDown();
	}
	
	private void levelFirst() {
		levelManager.levelFirst();
	}
	
	private void levelLast() {
		levelManager.levelLast();
	}
	
	private void chooseLevel() {
		panelLevel.display();
	}
	
	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(k.in().F1())	restart();
		if(k.in().F2())	exit();
		if(k.in().F3())	displayAbout();

		goNext();
		if(completeTimer.gBool()) return;

		if(k.in().shift())	chooseLevel();
		if(k.in().right()) levelUp();
		if(k.in().left()) levelDown();
		if(k.in().up()) levelFirst();
		if(k.in().down()) levelLast();
		
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
				handleMoves(draggedPos, releasedPos);
			}
			draggedCell = null;
			draggedPiece = null;
			draggedValue = UtilAntivirus.EMPTY;
			
			if(getOutputCell().hasVirus()) {
				completeTimer.start(60, this::levelWon);
			}
		}
	}
	
	private void handleMoves(int[] startPos, int[] endPos) {
		int di = endPos[0]-startPos[0];
		int dj = endPos[1]-startPos[1];
		
		if(di==0 && dj==0) return;
		if(di==0) {
			int dj1 = Math.abs(dj);
			int dj0 = dj<0 ? -1 : 1;
			
			for(int n=0;n<dj1;n++) {
				List<Cell[]> moves = findMoves(0, dj0);
				if(moves==null) return;
				
				int[][] data1 = UtilArray.clone(data);
				for(Cell[] move : moves)
					UtilArray.set(data1, move[0].getIJ(), UtilAntivirus.EMPTY);
				for(Cell[] move : moves)
					UtilArray.set(data1, move[1].getIJ(), move[0].getValue());
				data = data1;
			}
		}
		if(dj==0) {
			int di1 = Math.abs(di);
			int di0 = di<0 ? -1 : 1;
			
			for(int n=0;n<di1;n++) {
				List<Cell[]> moves = findMoves(di0, 0);
				if(moves==null) return;
				
				int[][] data1 = UtilArray.clone(data);
				for(Cell[] move : moves)
					UtilArray.set(data1, move[0].getIJ(), UtilAntivirus.EMPTY);
				for(Cell[] move : moves)
					UtilArray.set(data1, move[1].getIJ(), move[0].getValue());
				data = data1;
			}
		}
	}
	
	private List<Cell[]> findMoves(int di, int dj) {
		List<Cell[]> target = new ArrayList<>();
		List<Integer> values1 = UtilList.asList(draggedValue);
		List<Integer> values2 = UtilList.asList(draggedValue);
		
		while(!values1.isEmpty()) {
			int value1 = values1.get(0);
			values1.remove(0);
			List<Cell> piece = board.findAll(f->f.getValue()==value1);
			for(Cell c : piece) {
				Cell c1 = board.cellAt(c.getI()+di, c.getJ()+dj);
				if(c1==null || c1.isBlocked()) return null;
				
				target.add(new Cell[] {c, c1});
				if(c1.isPiece()) {
					int newValue = c1.getValue();
					if(!values2.contains(newValue)) {
						values1.add(newValue);
						values2.add(newValue);
					}
				}
			}
		}
		return target;
	}
	
	private Cell getOutputCell() {
		return board.cellAt(UtilAntivirus.OUTPUT_I, UtilAntivirus.OUTPUT_J);
	}
	
	
	public class Cell extends ShapeCell {
		public Cell(int i, int j) {
			super(i, j);
			setColor(Color.GRAY);
		}
		
		protected void initAnchor() {
			int k = i+j;
			double px = gameHeight()*0.5 + CELL_SIZE*1.4*0.5*(k - 0.9*x);
			double py = gameWidth()*0.5 - 50 - CELL_SIZE*1.4*(j - 0.5*k);
			setAnchor(new Point2(px, py));
		}
		
		public Color getColor() {
			return UtilAntivirusColor.getValueColor(getValue());
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
		public boolean isDragged() {
			return draggedCell!=null && draggedCell.getValue()==getValue();
		}
		public boolean isOutput() {
			return UtilAntivirus.isOutput(i,j);
		}
		public boolean hasVirus() {
			return data[i][j]==UtilAntivirus.PIECE0;
		}
		
		/*
		 * DRAW
		 */
		
		protected void drawShape() {
			drawBoardEdges();
			
			if(!isExternal()) {
				if(isBlocked()) drawBlocked();
				else if(isEmpty()) drawEmpty();
				else if(isPiece()) drawPiece();
			}
		}
		
		private void drawPiece() {
			Composite composite = g2_getComposite();
			boolean isDragged = isDragged();
			
			if(isDragged) g2_setComposite(ALPHA);
			UtilAntivirusDraw.drawPiece(this, p(0,0), getRadius(), data, i, j);
			if(isDragged) g2_setComposite(composite);
		}
		
		private void drawEmpty() {
			UtilAntivirusDraw.drawEmpty(this, getRadius(), data, i, j);
		}
		
		private void drawBlocked() {
			UtilAntivirusDraw.drawBlocked(this, getRadius());
		}
		
		private void drawBoardEdges() {
			UtilAntivirusDraw.drawBoardEdges(this, getRadius(), getIJ());
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
		public Color getColor() {
			return UtilAntivirusColor.getValueColor(draggedValue);
		}
		protected void draw() {
			if(draggedCell==null) return;
			
			double r = CELL_SIZE*0.5;
			Point1 draggedCellAnchor = draggedCell.getAnchor();
			for(Cell c : draggedPiece) {
				Point1 m = c.getAnchor().pSub(draggedCellAnchor);
				UtilAntivirusDraw.drawPiece(this, m, r, data, c.getI(), c.getJ());
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
				Color color = levelManager.getLevelColor();
				int level = levelManager.getLevel();
				fillRoundC(color, 20);
				drawStringC(Color.WHITE, fontBold(23), p(0,-2), ""+level);
		}
	}
	
	/*
	 * PROFILE
	 */
	
	private void createNewProfile() {
		levelManager.createNewProfile();
	}
	
	private void loadProfile() {
		levelManager.loadProfile();
	}
	
	private void removeProfile() {
		levelManager.removeProfile();
	}
	
	private void closeProfile() {
		levelManager.closeProfile();
	}
}
