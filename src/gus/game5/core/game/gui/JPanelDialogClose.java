package gus.game5.core.game.gui;

import static gus.game5.core.util.UtilGui.*;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class JPanelDialogClose extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JDialog2 dialog = new JDialog2();
	
	private JButton buttonClose;
	
	private int width = 1000;
	private int height = 500;

	public JPanelDialogClose() {
		super(new BorderLayout());
		empty(this, 20);
		setBackground(Color.WHITE);
		
		buttonClose = button("Close", this::close);
		
		JComponent top = buildTop();
		JComponent center = buildCenter();
		JComponent bottom = buildBottom();
		
		addN(this, top);
		addC(this, center);
		addS(this, bottom);
	}
	
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void display() {
		beforeDisplay();
		dialog.showContent(this, width, height);
		afterDisplay();
	}
	
	private void close() {
		beforeClose();
		dialog.hideContent();
		afterClose();
	}
	
	protected void beforeDisplay() {}
	protected void beforeClose() {}
	
	protected void afterDisplay() {}
	protected void afterClose() {}
	
	protected JComponent buildTop() {
		return null;
	}
	
	protected JComponent buildCenter() {
		return null;
	}
	
	protected JComponent buildBottom() {
		return panelE(buttonClose);
	}
}
