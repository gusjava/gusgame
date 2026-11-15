package gus.game5.main.game.p1.c.pacman;

import gus.game5.core.util.Dir;

public class Pacman extends Mover {
	
	private Dir wanted;
	
	public Pacman(Maze maze) {
		super(maze);
	}
	
	public void setWanted(Dir wanted) {
		this.wanted = wanted;
	}
	
	protected boolean doorOpen() {
		return false;
	}
	
	public void update(int speed) {
		if(wanted!=null && wanted==getDir().opposite()) {
			setDir(wanted);
			wanted = null;
		}
		if(isCentered()) {
			if(wanted!=null && canGo(wanted)) {
				setDir(wanted);
				wanted = null;
			}
			if(!canGo(getDir())) return;
		}
		advance(speed);
	}
}
