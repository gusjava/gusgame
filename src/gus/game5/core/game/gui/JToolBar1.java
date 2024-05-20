package gus.game5.core.game.gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.border.Border;

public class JToolBar1 extends JToolBar {
	private static final long serialVersionUID = 1L;

	public static final int H = 20;
	public static final Border EMPTY = BorderFactory.createEmptyBorder();

	private Dimension buttonSize;

	public JToolBar1() {
		this(H);
	}

	public JToolBar1(int x) {
		super();
		setFloatable(false);
		buttonSize = new Dimension(x, x);
		setMinimumSize(buttonSize);
		setMaximumSize(buttonSize);

		setFocusable(true);
		setBorder(EMPTY);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				requestFocusInWindow();
			}
		});
	}

	public JButton add(Action a) {
		JButton b = super.add(a);
		b.setMinimumSize(buttonSize);
		b.setMaximumSize(buttonSize);
		b.setBorder(EMPTY);
		setMinimumSize(new Dimension(0, 0));
		return b;
	}
}
