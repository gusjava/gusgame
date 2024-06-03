package gus.game5.core.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JLabel;

import gus.game5.core.exception.TechnicalException;

public class JLabelLink extends JLabel implements MouseListener {
	private static final long serialVersionUID = 1L;
	public static final Color COLOR = Color.BLUE;

	public JLabelLink(String text) {
		super(text);
		addMouseListener(this);
		setForeground(COLOR);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		activate();
	}

	public void mouseExited(MouseEvent e) {
		disactivate();
	}

	public void mousePressed(MouseEvent e) {
		perform();
	}

	protected void activate() {
	}

	protected void disactivate() {
	}

	private void perform() {
		disactivate();
		try {
			URI uri = new URI(getText());
			Desktop.getDesktop().browse(uri);
		} catch (URISyntaxException | IOException e) {
			throw new TechnicalException(e);
		}
	}
}
