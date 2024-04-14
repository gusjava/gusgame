package gus.game5.core.draw;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class DrawList<E extends Draw> extends ArrayList<E> implements Draw {
	private static final long serialVersionUID = 1L;
	
	public DrawList() {
		super();
	}
	
	public DrawList(List<E> draws) {
		super(draws);
	}
	
	@SuppressWarnings("unchecked")
	public DrawList(E... draws) {
		super();
		for(E draw : draws) add(draw);
	}

	public void draw(Graphics2D g2) {
		for(E element : this) element.draw(g2);
	}
}
