package gus.game5.main.edu.prog.modulo1;

import static gus.game5.core.util.UtilGui.action;
import static gus.game5.core.util.UtilGui.panelCNS;
import static gus.game5.core.util.UtilGui.raised;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;

import gus.game5.core.exp.exception.ExpException;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.gui.JMenuBar1;
import gus.game5.core.gui.JTextField1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point2.Point2;
import gus.game5.core.shape.board.ShapeBoard;
import gus.game5.core.shape.board.ShapeCell;
import gus.game5.core.util.UtilExp;

public class EduModulo1 extends Game1 {
	
	public static final String TITLE = "Modulo training";
	
	public static final double CELL_SIZE = 50;
	public static final int X = 12;
	public static final int Y = 15;

	public static final int GAME_HEIGHT = 650;
	public static final int GAME_WIDTH = 1000;
	
	public static final Font FONT = new Font("Comic Sans MS", Font.PLAIN, 12);
	public static final Color COLOR = new Color(240,240,240);
	
	public static void main(String[] args) {
		EduModulo1 main = new EduModulo1();
		main.displayInWindows();
		main.start();
	}
	
	/*
	 * CONTENT PANE
	 */

	private JTextField1 field;
	private JLabel labelInfo2;
	
	protected Container buildContentPane() {
		field = new JTextField1();
		labelInfo2 = new JLabel(" ");
		
		return panelCNS(panel(), 
				field, 
				raised(labelInfo2));
	}
	
	/*
	 * MENU BAR
	 */
	
	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Training", 
			action("Restart (F1)", this::restart),
			action("Exit (F2)", this::exit)
		);
	}
	
	/*
	 * SETTINGS
	 */
	
	protected void initSettings(Settings s) {
		s.setTitle(TITLE);
		s.setWidth(GAME_WIDTH);
		s.setHeight(GAME_HEIGHT);
		s.setSleep(100);
		s.setBackground(Color.LIGHT_GRAY);
		s.setFont(FONT);
	}
	
	/*
	 * DATA
	 */

	private ShapeBoard<Cell> board;
	private Object[][] content;
	private String expression = "";
	
	/*
	 * INITIALIZE
	 */
	
	protected void initialize1() {
		board = newShapeBoard(CELL_SIZE, X, Y, Cell::new);
		content = new Object[X][Y];
		
		field.setText("");
		expression = "";
	}
	
	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(k.F1())	restart();
		if(k.F2())	exit();
		
		String input = field.getText();
		if(!input.equals(expression)) {
			expression = input;
			buildContent();
		}
	}
	
	
	private void buildContent() {
		for(int i=0;i<X;i++)
		for(int j=0;j<Y;j++) {
			content[i][j] = perform(i,j);
		}
	}
	
	private Object perform(int i, int j) {
		Map<String,Object> dataMap = new HashMap<>();
		dataMap.put("i", i);
		dataMap.put("j", j);
		dataMap.put("x", X);
		dataMap.put("y", Y);
		
		String expression = field.getText();
		try {
			return UtilExp.estimate(expression, dataMap);
		} catch (ExpException e) {
			return null;
		}
//		return i+":"+j;
		
//		return i+j;
//		return (i+j)%3==0;
//		return (i+j-1)%3==0;
//		return (i+j)%3==1;
		
//		return i==j;
//		return i==j+2;

//		return Math.abs(j-i)==1;
//		return Math.abs(j-i)==3;
//		return Math.abs(j-i)%3==0;

//		return (j-i)%3==0;
//		return (j-i-1)%3==0;
//		return (j-i)%3==1;
//		return (j-i)%3==-1;

//		return i%2==0;
//		return i%3==0;
//		return j%2==0;
//		return j%3==0;
		
//		return i%2==0 || j%2==0;
//		return i%2==0 && j%2==0;
//		return i%4>0 && j%2==0;
//		return i%4>0 && j%4>0;
//		return i%4>0 || j%4>0;
		
//		return i%4==0 || j%4>0;
//		return i%4==0 && j%4>0;
//		return i%4==0 && j%4==0;
//		return i%4==0 || j%4==0;
		
//		return i+"%4=="+(i%4);
//		return i+"+"+j+"="+(i+j);
		
//		return i==2 && j==5;
//		return i==2 || j==5;
//		return i==2 ^ j==5;
	}
	
	/*
	 * CELL
	 */
	
	public class Cell extends ShapeCell {
		public Cell(int i, int j) {
			super(i, j);
			setFont(FONT);
		}
		
		protected void drawShape() {
			Object result = content[i][j];
			if(result==null) {
				fillSquareC(COLOR, CELL_SIZE);
				drawSquareC(CELL_SIZE);
				return;
			}
			
			if(result instanceof Boolean) {
				Boolean b = (Boolean) result;
				Color c = b ? Color.BLACK : Color.WHITE;
				fillSquareC(c, CELL_SIZE);
				drawSquareC(CELL_SIZE);
				return;
			}
			else if(result instanceof String) {
				String s = (String) result;
				fillSquareC(Color.WHITE, CELL_SIZE);
				drawSquareC(CELL_SIZE);
				drawStringC(font(10), s);
			}
			else if(result instanceof Number) {
				Number s = (Number) result;
				fillSquareC(Color.WHITE, CELL_SIZE);
				drawSquareC(CELL_SIZE);
				drawStringC(font(10), ""+s);
			}
		}
		
		protected void initAnchor() {
			double h = getHeight();
			double w = getWidth();
			
			double px = (gameWidth()-w*y)*0.5 + w*(j+0.5);
			double py = (gameHeight()-h*x)*0.5 + h*(i+0.5);
			setAnchor(new Point2(px, py));
		}
	}
}
