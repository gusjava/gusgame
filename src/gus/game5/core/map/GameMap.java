package gus.game5.core.map;

import java.awt.Color;
import java.awt.Graphics2D;

import gus.game5.core.alter.Alter;
import gus.game5.core.clean.Clean;
import gus.game5.core.clean.CleanList;
import gus.game5.core.draw.Draw;
import gus.game5.core.draw.DrawList;
import gus.game5.core.dyn.Dyn;
import gus.game5.core.dyn.DynList;
import gus.game5.core.game.Game1;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.shape.Shape;
import gus.game5.core.shape.ShapeList;

public class GameMap implements Draw, Dyn, Clean, Alter {
	
	private Game1 game;
	
	private DrawList<Draw> drawList;
	private DynList<Dyn> dynList;
	private CleanList<Clean> cleanList;

	private Point1 mapOrigin;
	private int mapWidth;
	private int mapHeight;

	private Point1 frameOrigin;
	private int frameWidth;
	private int frameHeight;
	private Color frameBackground;
	
	private int speed;
	
	
	public GameMap(Game1 game, int mapWidth, int mapHeight) {
		this.game = game;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		
		frameWidth = game.gameWidth();
		frameHeight = game.gameHeight();
		frameBackground = Color.WHITE;
		speed = 2;
		
		mapOrigin = new Point1();
		frameOrigin = new Point1();
		
		drawList = new DrawList<>();
		dynList = new DynList<>();
		cleanList = new CleanList<>();
	}
	
	/*
	 * MAP
	 */
	
	public int getMapWidth() {
		return mapWidth;
	}
	
	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}
	
	public int getMapHeight() {
		return mapHeight;
	}
	
	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}
	
	public void setMapSize(int mapWidth, int mapHeight) {
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
	}
	
	public Point1 getMapOrigin() {
		return mapOrigin;
	}
	
	/*
	 * FRAME
	 */
	
	public int getFrameWidth() {
		return frameWidth;
	}
	
	public void setFrameWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}
	
	public int getFrameHeight() {
		return frameHeight;
	}
	
	public void setFrameHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}
	
	public void setFrameSize(int frameWidth, int frameHeight) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;
	}
	
	public Point1 getFrameOrigin() {
		return frameOrigin;
	}
	
	public Color getFrameBackground() {
		return frameBackground;
	}
	
	
	
	/*
	 * SPEED
	 */
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	/*
	 * MOUSE
	 */
	
	public Point0 mousePosition() {
		return revPoint(game.mouse().pointCurrent());
	}
	
	/*
	 * DRAW
	 */
	
	public void addDraw(Draw draw) {
		drawList.add(draw);
	}

	public void draw(Graphics2D g2) {
		g2.setColor(frameBackground);
		g2.fillRect((int) frameOrigin.getX(), (int) frameOrigin.getY(), frameWidth, frameHeight);
		drawList.draw(g2);
	}
	
	/*
	 * DYN
	 */
	
	public void addDyn(Dyn dyn) {
		dynList.add(dyn);
	}

	public void goNext() {
		kbMove(false);
		dynList.goNext();
	}

	public void goBack() {
		kbMove(true);
		dynList.goBack();
	}
	
	/*
	 * CLEAN
	 */
	
	public void addClean(Clean clean) {
		cleanList.add(clean);
	}

	public void clean() {
		cleanList.clean();
	}
	
	/*
	 * SHAPE
	 */
	
	public <E extends Shape> ShapeList<E> newShapeList() {
		ShapeList<E> shapeList = new ShapeList<>();
		addDraw(shapeList);
		addDyn(shapeList);
		addClean(shapeList);

		shapeList.setAlter(this);
		return shapeList;
	}
	
	private void kbMove(boolean back) {
		Keyboard k = game.keyboard();
		int d = back ? speed : -speed;
		
		if(k.up()) mapOrigin.addY(d);
		if(k.down()) mapOrigin.addY(-d);
		if(k.left()) mapOrigin.addX(d);
		if(k.right()) mapOrigin.addX(-d);
		
		int widthMax = mapWidth-frameWidth;
		int heightMax = mapHeight-frameHeight;
		
		if(mapOrigin.getX()<0) mapOrigin.setX(0);
		if(mapOrigin.getX()>widthMax) mapOrigin.setX(widthMax);
		
		if(mapOrigin.getY()<0) mapOrigin.setY(0); 
		if(mapOrigin.getY()>heightMax) mapOrigin.setY(heightMax);
	}
	
	/*
	 * ALTER
	 */

	public Point0 alterPoint(Point0 p) {
		if(p==null) return null;
		return p.pAdd(frameOrigin).pSub(mapOrigin);
	}

	public double alterDistance(double dist) {
		return dist;
	}

	public Point0 revPoint(Point0 p) {
		if(p==null) return null;
		return p.pSub(frameOrigin).pAdd(mapOrigin);
	}

	public double revDistance(double dist) {
		return dist;
	}
}
