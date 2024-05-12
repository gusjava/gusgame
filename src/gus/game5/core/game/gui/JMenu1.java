package gus.game5.core.game.gui;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class JMenu1 extends JMenu {
	private static final long serialVersionUID = 1L;

	public JMenu1(String title) {
		super(title);
	}
	
	public JMenu1(String title, int index, JMenuItem... items) {
		super(title);
		add(index, items);
	}
	
	public JMenu1(String title, JMenuItem... items) {
		super(title);
		add(0, items);
	}
	
	public void add(int index, JMenuItem... items) {
		ButtonGroup group = new ButtonGroup();
		for(JMenuItem item : items) {
			group.add(item);
			add(item);
		}
		items[index].setSelected(true);
	}
}
