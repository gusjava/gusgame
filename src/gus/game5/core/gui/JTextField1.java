package gus.game5.core.gui;

import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;

public class JTextField1 extends JTextField implements KeyListener {
	private static final long serialVersionUID = 1L;

	public static final int KEY = KeyEvent.VK_ESCAPE;

	public JTextField1() {
		super();
		setMargin(new Insets(3, 3, 3, 3));
		addKeyListener(this);

	}

	public void keyReleased(KeyEvent evt) {
	}

	public void keyTyped(KeyEvent evt) {
	}

	public void keyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KEY)
			clear();
	}

	private void clear() {
		setText("");
	}

}
