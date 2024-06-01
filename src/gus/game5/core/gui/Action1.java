package gus.game5.core.gui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;

public class Action1 extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	private Runnable r;
	
	public Action1(String name, Runnable r) {
		super(name);
		this.r = r;
	}
	
	public Action1(Icon icon) {
		super();
		putValue(Action.SMALL_ICON, icon);
	}

	public void actionPerformed(ActionEvent e) {
		if(r!=null) r.run();
	}
}
