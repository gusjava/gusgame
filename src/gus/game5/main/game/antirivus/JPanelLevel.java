package gus.game5.main.game.antirivus;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class JPanelLevel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private int level;
	private int maxLevel;

	public JPanelLevel() {
		super(new GridLayout(0, 5, 10, 10));
		setOpaque(true);
		setBackground(Color.WHITE);
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
}
