package gus.game5.core.game;

import java.awt.Color;

public class Settings {

	private String title = "Jeu";
	private int width = 900;
	private int height = 700;
	private long sleep = 10;
	private Color background;
	
	/*
	 * TITLE
	 */
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	/*
	 * WIDTH
	 */
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	/*
	 * HEIGHT
	 */
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	/*
	 * SLEEP
	 */
	
	public long getSleep() {
		return sleep;
	}
	public void setSleep(long sleep) {
		this.sleep = sleep;
	}
	
	/*
	 * BACKGROUND
	 */
	
	public Color getBackground() {
		return background;
	}
	public void setBackground(Color background) {
		this.background = background;
	}
	
	/*
	 * SIZE
	 */
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
