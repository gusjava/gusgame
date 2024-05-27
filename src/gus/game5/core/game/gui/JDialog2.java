package gus.game5.core.game.gui;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class JDialog2 extends JDialog {
	private static final long serialVersionUID = 1L;
	
	public JDialog2() {
		super((JFrame) null, true);
		setUndecorated(true);
		setResizable(false);
	}
	
	public void showContent(JComponent content, int width, int height) {
		setContentPane(content);
		pack();
		setSize(width, height);
		setLocationRelativeTo(null);
		if(isVisible()) return;
		setVisible(true);
	}

	public void hideContent() {
		if(!isVisible()) return;
		setVisible(false);
	}
}
