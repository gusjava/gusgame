package gus.game5.core.keyboard;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class Keyboard extends KeyboardState {
	
	private KeyboardState in;
	private KeyboardState out;
	
	public Keyboard(JPanel panel) {
		super();
		in = new KeyboardState();
		out = new KeyboardState();
				
		panel.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				state.add(code);
				in.state.add(code);
			}
			public void keyReleased(KeyEvent e) {
				int code = e.getKeyCode();
				state.remove(code);
				out.state.add(code);
			}
		});
	}
	
	
	public KeyboardState in() {
		return in;
	}
	
	public KeyboardState out() {
		return out;
	}
	
	public void reset() {
		in.clear();
		out.clear();
	}
}
