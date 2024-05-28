package gus.game5.core.game.gui;

import static gus.game5.core.util.UtilGui.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class JPanelDialog2 extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JDialog2 dialog = new JDialog2();
	
	private JButton buttonOk;
	private JButton buttonCancel;
	
	private int width = 1000;
	private int height = 500;

	public JPanelDialog2() {
		super(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setBackground(Color.WHITE);
		
		buttonOk = new JButton("Ok");
		buttonOk.addActionListener(e->ok());
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(e->cancel());
		
		
		JComponent top = buildTop();
		JComponent center = buildCenter();
		JComponent bottom = buildBottom();
		
		if(top!=null) add(top, BorderLayout.NORTH);
		if(center!=null) add(center, BorderLayout.CENTER);
		if(bottom!=null) add(bottom, BorderLayout.SOUTH);
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
		JPanel panelButtons = new JPanel(new GridLayout(1,2,10,10));
		panelButtons.setOpaque(false);
		panelButtons.add(buttonCancel);
		panelButtons.add(buttonOk);
		
		return panelE(panelButtons);
	}
	
	
	
}
