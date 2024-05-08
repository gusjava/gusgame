package gus.game5.core.game.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class Action1 extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	private Runnable r;
	
	public Action1(String name, Runnable r) {
		super(name);
		this.r = r;
	}

	public void actionPerformed(ActionEvent e) {
		r.run();
	}
}
