package gus.game5.core.game;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class JMenuBar1 extends JMenuBar {
	private static final long serialVersionUID = 1L;

	public void add(String title, Action... actions) {
		JMenu menu = new JMenu(title);
		for(Action action : actions) menu.add(action);
		super.add(menu);
	}
}
