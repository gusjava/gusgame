package gus.game5.core.util;

import java.awt.BorderLayout;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import gus.game5.core.game.gui.Action1;
import gus.game5.core.game.gui.JMenu1;
import gus.game5.core.game.gui.JRadioButtonMenuItem1;
import gus.game5.core.game.gui.JToolBar1;

public class UtilGui {
	
	/*
	 * ACTION
	 */
	
	public static Action action(String name, Runnable r) {
		return new Action1(name, r);
	}
	
	public static Action action(Icon icon) {
		return new Action1(icon);
	}
	
	/*
	 * MENU ITEM
	 */
	
	public static JRadioButtonMenuItem1 radioMenuItem(String name, Runnable r) {
		return new JRadioButtonMenuItem1(name, r);
	}
	
	/*
	 * MENU
	 */
	
	public static JMenu1 menu(String title, JMenuItem... items) {
		return new JMenu1(title, items);
	}
	
	public static JMenu1 menu(String title, int index, JMenuItem... items) {
		return new JMenu1(title , index, items);
	}
	
	/*
	 * BAR
	 */
	
	public static JToolBar1 bar(Action... actions) {
		JToolBar1 bar = new JToolBar1();
		for(Action action : actions) bar.add(action);
		return bar;
	}
	
	public static JToolBar1 bar(Icon... icons) {
		JToolBar1 bar = new JToolBar1();
		for(Icon icon : icons) bar.add(action(icon));
		return bar;
	}
	
	/*
	 * PANEL
	 */
	
	public static JPanel panel(JComponent c, JComponent n, JComponent s, JComponent w, JComponent e) {
		JPanel p = new JPanel(new BorderLayout());
		p.setOpaque(false);
		if(c!=null) p.add(c, BorderLayout.CENTER);
		if(n!=null) p.add(n, BorderLayout.NORTH);
		if(s!=null) p.add(s, BorderLayout.SOUTH);
		if(w!=null) p.add(w, BorderLayout.WEST);
		if(e!=null) p.add(e, BorderLayout.EAST);
		return p;
	}
	
	/*
	 * PANEL : N S W E
	 */
	
	public static JPanel panelN(JComponent n) {
		return panel(null, n, null, null, null);
	}
	
	public static JPanel panelS(JComponent s) {
		return panel(null, null, s, null, null);
	}
	
	public static JPanel panelW(JComponent w) {
		return panel(null, null, null, w, null);
	}
	
	public static JPanel panelE(JComponent e) {
		return panel(null, null, null, null, e);
	}
	
	/*
	 * PANEL : CN CS CW CE
	 */
	
	public static JPanel panelCN(JComponent c, JComponent n) {
		return panel(c, n, null, null, null);
	}
	
	public static JPanel panelCS(JComponent c, JComponent s) {
		return panel(c, null, s, null, null);
	}
	
	public static JPanel panelCW(JComponent c, JComponent w) {
		return panel(c, null, null, w, null);
	}
	
	public static JPanel panelCE(JComponent c, JComponent e) {
		return panel(c, null, null, null, e);
	}
	
	/*
	 * PANEL CNS, CWE
	 */
	
	public static JPanel panelCNS(JComponent c, JComponent n, JComponent s) {
		return panel(c, n, s, null, null);
	}
	
	public static JPanel panelCWE(JComponent c, JComponent w, JComponent e) {
		return panel(c, null, null, w, e);
	}
	
	/*
	 * PANEL NS, WE
	 */
	
	public static JPanel panelNS(JComponent n, JComponent s) {
		return panel(null, n, s, null, null);
	}
	
	public static JPanel panelWE(JComponent w, JComponent e) {
		return panel(null, null, null, w, e);
	}
	
	/*
	 * BORDER
	 */
	
	public static JComponent raised(JComponent comp) {
		comp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		return comp;
	}
	
	public static JComponent empty(JComponent comp, int gap) {
		comp.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
		return comp;
	}
}
