package gus.game5.core.game.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JRadioButtonMenuItem;

public class JRadioButtonMenuItem1 extends JRadioButtonMenuItem implements ItemListener {
	private static final long serialVersionUID = 1L;
	
	private Runnable r;

	public JRadioButtonMenuItem1(String name, Runnable r) {
		super(name);
		this.r = r;
		addItemListener(this);
	}

	public void itemStateChanged(ItemEvent e) {
		if(isSelected()) r.run();
	}
}
