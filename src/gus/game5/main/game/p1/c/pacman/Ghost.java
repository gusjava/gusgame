package gus.game5.main.game.p1.c.pacman;

import static gus.game5.core.util.UtilRandom.randomInt;

import java.awt.Color;

import gus.game5.core.util.Dir;
import java.util.ArrayList;
import java.util.List;

public class Ghost extends Mover {
	
	public enum Kind {BLINKY, PINKY, INKY, CLYDE}
	public enum State {WAITING, LEAVING, OUT, EYES}
	
	public static final int RESPAWN_DELAY = 60;
	public static final int CLYDE_RANGE2 = 64;
	
	private Kind kind;
	private Color color;
	private int homeCol;
	private int scatterCol;
	private int scatterRow;
	
	private State state;
	private boolean frightened;
	private int releaseDelay;
	private int waitCount;
	
	public Ghost(Maze maze, Kind kind, int homeCol) {
		super(maze);
		this.kind = kind;
		this.homeCol = homeCol;
		this.color = buildColor(kind);
		initScatterTarget(kind);
	}
	
	private static Color buildColor(Kind kind) {
		switch(kind) {
			case BLINKY: return new Color(255,0,0);
			case PINKY: return new Color(255,184,255);
			case INKY: return new Color(0,255,255);
			default: return new Color(255,184,82);
		}
	}
	
	private void initScatterTarget(Kind kind) {
		switch(kind) {
			case BLINKY: scatterCol = Maze.COLS-3; scatterRow = 0; break;
			case PINKY: scatterCol = 2; scatterRow = 0; break;
			case INKY: scatterCol = Maze.COLS-1; scatterRow = Maze.ROWS-1; break;
			default: scatterCol = 0; scatterRow = Maze.ROWS-1; break;
		}
	}
	
	public Kind getKind() {
		return kind;
	}
	
	public Color getColor() {
		return color;
	}
	
	public boolean isEyes() {
		return state==State.EYES;
	}
	
	public boolean isFrightened() {
		return frightened;
	}
	
	public boolean isCatchable() {
		return state==State.OUT || state==State.LEAVING;
	}
	
	public void reset(boolean outside, int releaseDelay) {
		this.frightened = false;
		this.releaseDelay = releaseDelay;
		this.waitCount = 0;
		if(outside) {
			place(Maze.HOME_COL, Maze.GATE_ROW, Dir.WEST);
			state = State.OUT;
		}
		else {
			place(homeCol, Maze.HOME_ROW, Dir.NORTH);
			state = State.WAITING;
		}
	}
	
	public void frighten() {
		if(state!=State.OUT) return;
		frightened = true;
		setDir(getDir().opposite());
	}
	
	public void calm() {
		frightened = false;
	}
	
	public void turnBack() {
		if(state!=State.OUT || frightened) return;
		setDir(getDir().opposite());
	}
	
	public void eaten() {
		frightened = false;
		state = State.EYES;
	}
	
	protected boolean doorOpen() {
		return state==State.EYES || state==State.LEAVING;
	}
	
	public void update(int speed, boolean scatter, Mover pacman, Ghost blinky) {
		switch(state) {
			case WAITING: waitStep(); return;
			case LEAVING: leaveStep(speed); return;
			default: break;
		}
		
		if(isCentered()) setDir(chooseDir(scatter, pacman, blinky));
		advance(speed);
		
		if(state==State.EYES && isCentered() 
				&& getCol()==Maze.HOME_COL && getRow()==Maze.HOME_ROW) {
			place(homeCol, Maze.HOME_ROW, Dir.NORTH);
			state = State.WAITING;
			waitCount = 0;
			releaseDelay = RESPAWN_DELAY;
		}
	}
	
	private void waitStep() {
		waitCount++;
		setY(Maze.centerY(Maze.HOME_ROW) + (int)(Maze.CELL/3.0*Math.sin(waitCount*0.12)));
		if(waitCount<releaseDelay) return;
		place(homeCol, Maze.HOME_ROW, Dir.NORTH);
		state = State.LEAVING;
	}
	
	private void leaveStep(int speed) {
		int gateX = Maze.centerX(Maze.HOME_COL);
		int gateY = Maze.centerY(Maze.GATE_ROW);
		if(getX()!=gateX) {
			setDir(getX()<gateX ? Dir.EAST : Dir.WEST);
			stepToward(gateX, getY(), speed);
		}
		else if(getY()!=gateY) {
			setDir(Dir.NORTH);
			stepToward(gateX, gateY, speed);
		}
		else {
			state = State.OUT;
			setDir(Dir.WEST);
		}
	}
	
	private Dir chooseDir(boolean scatter, Mover pacman, Ghost blinky) {
		if(state==State.EYES) {
			Dir d = maze.firstStepTo(getCol(), getRow(), Maze.HOME_COL, Maze.HOME_ROW, true);
			return d!=null ? d : getDir();
		}
		
		List<Dir> options = new ArrayList<>();
		for(Dir d : Dir.values())
			if(d!=getDir().opposite() && canGo(d)) options.add(d);
		
		if(options.isEmpty()) return getDir().opposite();
		if(options.size()==1) return options.get(0);
		if(frightened) return options.get(randomInt(options.size()));
		
		int[] target = scatter ? scatterTarget() : chaseTarget(pacman, blinky);
		Dir best = null;
		long bestDist = Long.MAX_VALUE;
		for(Dir d : options) {
			long dx = getCol()+d.getDx()-target[0];
			long dy = getRow()+d.getDy()-target[1];
			long dist = dx*dx+dy*dy;
			if(dist<bestDist) {
				bestDist = dist;
				best = d;
			}
		}
		return best;
	}
	
	private int[] scatterTarget() {
		return new int[]{scatterCol, scatterRow};
	}
	
	private int[] chaseTarget(Mover pacman, Ghost blinky) {
		int pc = pacman.getCol();
		int pr = pacman.getRow();
		Dir pd = pacman.getDir();
		switch(kind) {
			case BLINKY: 
				return new int[]{pc, pr};
			case PINKY: 
				return new int[]{pc+pd.getDx()*4, pr+pd.getDy()*4};
			case INKY: 
				int ax = pc+pd.getDx()*2;
				int ay = pr+pd.getDy()*2;
				return new int[]{2*ax-blinky.getCol(), 2*ay-blinky.getRow()};
			default:
				int dx = pc-getCol();
				int dy = pr-getRow();
				if(dx*dx+dy*dy>CLYDE_RANGE2) return new int[]{pc, pr};
				return scatterTarget();
		}
	}
}
