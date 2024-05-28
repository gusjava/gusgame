package gus.game5.core.game.gui;

import static gus.game5.core.util.UtilGui.*;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class JPanelDialogOkCancel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JDialog2 dialog = new JDialog2();
	
	private JButton buttonOk;
	private JButton buttonCancel;
	
	private int width = 1000;
	private int height = 500;

	public JPanelDialogOkCancel() {
		super(new BorderLayout());
		empty(this, 20);
		setBackground(Color.WHITE);
		
		buttonOk = button("Ok", this::ok);
		buttonCancel = button("Cancel", this::cancel);
		
		JComponent top = buildTop();
		JComponent center = buildCenter();
		JComponent bottom = buildBottom();
		
		addN(this, top);
		addC(this, center);
		addS(this, bottom);
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
	
	private void ok() {
		beforeOk();
		dialog.hideContent();
		afterOk();
	}
	
	private void cancel() {
		beforeCancel();
		dialog.hideContent();
		afterCancel();
	}
	
	protected void beforeDisplay() {}
	protected void beforeOk() {}
	protected void beforeCancel() {}
	
	protected void afterDisplay() {}
	protected void afterOk() {}
	protected void afterCancel() {}
	
	protected JComponent buildTop() {
		return null;
	}
	
	protected JComponent buildCenter() {
		return null;
	}
	
	protected JComponent buildBottom() {
		JPanel panelButtons = panelGrid(1,2,10);
		panelButtons.add(buttonCancel);
		panelButtons.add(buttonOk);
		
		return panelE(panelButtons);
	}
}
