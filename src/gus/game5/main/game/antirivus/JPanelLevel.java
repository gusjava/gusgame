package gus.game5.main.game.antirivus;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import gus.game5.core.game.gui.JDialog2;

public class JPanelLevel extends JPanel {
	private static final long serialVersionUID = 1L;

	private LevelManager levelManager;
	private int currentLevel;
	private int lastLevel;
	
	private JDialog2 dialog = new JDialog2();
	
	private JPanel panelCenter;
	private JPanelCategory panelCategory1;
	private JPanelCategory panelCategory2;
	private JPanelCategory panelCategory3;
	private JPanelCategory panelCategory4;
	private JPanelCategory panelCategory5;
	
	private JButton buttonOk;
	private JButton buttonCancel;

	public JPanelLevel(LevelManager levelManager) {
		super(new BorderLayout());
		this.levelManager = levelManager;
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setBackground(Color.WHITE);
		
		panelCategory1 = new JPanelCategory(12);
		panelCategory2 = new JPanelCategory(24);
		panelCategory3 = new JPanelCategory(36);
		panelCategory4 = new JPanelCategory(48);
		panelCategory5 = new JPanelCategory(60);
		
		buttonOk = new JButton("Ok");
		buttonOk.addActionListener(e->ok());
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(e->cancel());
		
		JLabel labelTitle = new JLabel("LEVEL CHOOSER");
		labelTitle.setHorizontalAlignment(JLabel.CENTER);
		labelTitle.setForeground(Color.LIGHT_GRAY);
		labelTitle.setFont(labelTitle.getFont().deriveFont((float) 25).deriveFont(Font.BOLD));
		
		JPanel panelButtons = new JPanel(new GridLayout(1,2,10,10));
		panelButtons.setOpaque(false);
		panelButtons.add(buttonOk);
		panelButtons.add(buttonCancel);
		
		panelCenter = new JPanel(new GridLayout(0, 5, 20, 20));
		panelCenter.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
		panelCenter.setOpaque(false);
		
		panelCenter.add(panelCategory1);
		panelCenter.add(panelCategory2);
		panelCenter.add(panelCategory3);
		panelCenter.add(panelCategory4);
		panelCenter.add(panelCategory5);
		
		add(labelTitle, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(panelButtons, BorderLayout.SOUTH);
	}
	
	public void display() {
		lastLevel = levelManager.getLastLevel();
		currentLevel = levelManager.getLevel();
		rebuildPanel();
		dialog.showContent(this, 1000, 500);
	}
	
	private void ok() {
		levelManager.changeLevel(currentLevel);
		dialog.hideContent();
	}
	
	private void cancel() {
		dialog.hideContent();
	}
	
	private void rebuildPanel() {
		panelCategory1.clear();
		panelCategory2.clear();
		panelCategory3.clear();
		panelCategory4.clear();
		panelCategory5.clear();
		
		ButtonGroup group = new ButtonGroup();
		for(int i=0;i<lastLevel;i++) {
			int level = i+1;
			JRadioButton1 radio = new JRadioButton1(level);
			group.add(radio);
			
			if(level<=panelCategory1.getMaxLevel())
				panelCategory1.addRadio(radio);
			else if(level<=panelCategory2.getMaxLevel())
				panelCategory2.addRadio(radio);
			else if(level<=panelCategory3.getMaxLevel())
				panelCategory3.addRadio(radio);
			else if(level<=panelCategory4.getMaxLevel())
				panelCategory4.addRadio(radio);
			else if(level<=panelCategory5.getMaxLevel())
				panelCategory5.addRadio(radio);
		}
		
		panelCategory1.refresh();
		panelCategory2.refresh();
		panelCategory3.refresh();
		panelCategory4.refresh();
		panelCategory5.refresh();
	}
	
	
	private class JPanelCategory extends JPanel {
		private static final long serialVersionUID = 1L;
		private int maxLevel;
		private JPanel radioPanel;
		
		public JPanelCategory(int maxLevel) {
			super(new BorderLayout());
			this.maxLevel = maxLevel;
			setOpaque(false);
			
			JLabel labelTitle = new JLabel(UtilAntivirus.getLevelTitle(maxLevel));
			labelTitle.setHorizontalAlignment(JLabel.CENTER);
			labelTitle.setForeground(UtilAntivirusColor.getLevelColor(maxLevel));
			labelTitle.setFont(labelTitle.getFont().deriveFont((float) 20).deriveFont(Font.BOLD));
			
			radioPanel = new JPanel(new GridLayout(0, 1));
			radioPanel.setOpaque(false);
			
			JPanel radioPanel1 = new JPanel(new BorderLayout());
			radioPanel1.setOpaque(false);
			radioPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 20));
			radioPanel1.add(radioPanel, BorderLayout.NORTH);
			
			add(labelTitle, BorderLayout.NORTH);
			add(radioPanel1, BorderLayout.CENTER);
		}
		
		public void addRadio(JRadioButton1 radio) {
			radioPanel.add(radio);
		}
		public int getMaxLevel() {
			return maxLevel;
		}
		public void clear() {
			radioPanel.removeAll();
		}
		public void refresh() {
			synchronized(getTreeLock())
			{validateTree();}
			validate();
			repaint();
		}
	}
	
	
	private class JRadioButton1 extends JRadioButton implements ActionListener {
		private static final long serialVersionUID = 1L;
		private int level;
		
		public JRadioButton1(int level) {
			super("Level "+level);
			this.level = level;
			setOpaque(false);
			setFont(getFont().deriveFont(Font.PLAIN));
			setSelected(level==currentLevel);
			addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			currentLevel = level;
			setSelected(true);
		}
	}
}
