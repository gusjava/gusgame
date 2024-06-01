package gus.game5.core.gui;

import java.awt.Component;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class JMenuBar1 extends JMenuBar {
	private static final long serialVersionUID = 1L;

	public void add(String title, Action... actions) {
		JMenu menu = new JMenu(title);
		for(Action action : actions) {
			if(action!=null) menu.add(action);
			else menu.addSeparator();
		}
		super.add(menu);
	}

	public void add(String title, Component... comps) {
		JMenu menu = new JMenu(title);
		for(Component comp : comps) {
			if(comp!=null) menu.add(comp);
			else menu.addSeparator();
		}
		super.add(menu);
	}
	
	public void add(String title, JMenuItem... items) {
		add(title, 0, items);
	}
	
	public void add(String title, int index, JMenuItem... items) {
		JMenu menu = new JMenu(title);
		ButtonGroup group = new ButtonGroup();
		for(JMenuItem item : items) {
			group.add(item);
			menu.add(item);
		}
		if(index>=0) items[index].setSelected(true);
		super.add(menu);
	}
	
	public void add(String title, JMenu... menus) {
		JMenu menu = new JMenu(title);
		for(JMenu item : menus) {
			menu.add(item);
		}
		super.add(menu);
	}
}
