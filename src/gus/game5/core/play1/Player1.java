package gus.game5.core.play1;

public abstract class Player1 {
	
	/*
	 * INDEX
	 */
	
	private int index;
	
	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public boolean isFirst() {
		return index==0;
	}
	
	public boolean isSecond() {
		return index==1;
	}
	
	/*
	 * DISPLAY
	 */
	
	private String display;
	
	public String getDisplay() {
		return display;
	}
	
	public void setDisplay(String display) {
		this.display = display;
	}

	/*
	 * PLAY
	 */
	
	public abstract boolean play();
}
