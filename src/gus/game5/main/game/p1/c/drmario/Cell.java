package gus.game5.main.game.p1.c.drmario;

public class Cell {

	private boolean virus;
	private GColor color;
	private Link link;

	public Cell(boolean virus, GColor color, Link link) {
		this.virus = virus;
		this.color = color;
		this.link = link;
	}

	public boolean isVirus() {
		return virus;
	}

	public GColor getColor() {
		return color;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}
}
