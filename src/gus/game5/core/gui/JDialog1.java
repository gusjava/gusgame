package gus.game5.core.gui;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class JDialog1 extends JDialog implements AWTEventListener {
	private static final long serialVersionUID = 1L;
	
	public JDialog1() {
		super((JFrame) null, false);
		setUndecorated(true);
		setResizable(false);
	}

	public void eventDispatched(AWTEvent event) {
		if(event.getID()==501) hideContent();
	}
	
	public void showContent(JComponent content, int width, int height) {
		setContentPane(content);
		pack();
		setSize(width, height);
		setLocationRelativeTo(null);
		if(isVisible()) return;
		
		Toolkit.getDefaultToolkit().addAWTEventListener(this,AWTEvent.MOUSE_EVENT_MASK);
		setVisible(true);
	}

	private void hideContent() {
		if(!isVisible()) return;
		
		Toolkit.getDefaultToolkit().removeAWTEventListener(this);
		setVisible(false);
	}
}
