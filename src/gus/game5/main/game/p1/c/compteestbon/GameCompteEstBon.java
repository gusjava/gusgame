package gus.game5.main.game.p1.c.compteestbon;

import static gus.game5.core.util.UtilGui.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.gui.JMenuBar1;
import gus.game5.core.util.UtilRandom;

public class GameCompteEstBon extends Game1 {

	public static final long SLEEP = 30;

	public static final int VALUE_COUNT = 6;
	public static final int TARGET_MIN = 100;
	public static final int TARGET_MAX = 999;

	public static final Color COLOR_SELECTED = new Color(250,220,60);
	public static final Color COLOR_VALUE = new Color(225,225,230);
	public static final Color COLOR_OP_SELECTED = new Color(250,220,60);
	public static final Color COLOR_OP = new Color(210,210,218);

	private enum Phase {PLAYING, FINISHED}


	private JLabel labelTarget;
	private JLabel labelInfo;
	private JPanel valuesPanel;
	private JButton[] opButtons;

	private int target;
	private List<Integer> values;
	private Integer firstIdx;
	private Character op;
	private int wins;
	private Phase phase;


	public static void main(String[] args) {
		GameCompteEstBon main = new GameCompteEstBon();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Le compte est bon");
		s.setSleep(SLEEP);
	}

	protected void initialize1() {
		resetState();
		refreshAll();
	}

	protected void turn() {
	}

	private void resetState() {
		target = UtilRandom.randomInt(TARGET_MIN, TARGET_MAX+1);
		values = drawPlates();
		firstIdx = null;
		op = null;
		phase = Phase.PLAYING;
	}


	/*
	 * CONTENT PANE
	 */

	protected Container buildContentPane() {
		labelTarget = new JLabel("", SwingConstants.CENTER);
		fontSize(labelTarget, 26);
		labelInfo = new JLabel("", SwingConstants.CENTER);
		fontSize(labelInfo, 14);

		valuesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

		JPanel opsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 6));
		opButtons = new JButton[4];
		char[] ops = {'+','-','*','/'};
		for(int i=0;i<4;i++) {
			char c = ops[i];
			JButton b = button(String.valueOf(c), () -> onOpClicked(c));
			b.setPreferredSize(new Dimension(56, 56));
			opButtons[i] = b;
			opsPanel.add(b);
		}

		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		center.add(empty(valuesPanel, 8));
		center.add(empty(opsPanel, 8));

		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
		north.add(labelTarget);
		north.add(labelInfo);

		JPanel south = new JPanel();
		south.add(button("Valider", this::validate));
		south.add(button("Nouvelle partie", this::restart));

		return panelCNS(empty(center, 10), empty(north, 12), south);
	}


	/*
	 * MENU BAR
	 */

	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add("Partie",
			action("Nouvelle partie", this::restart),
			action("Quitter", this::exit)
		);
	}


	/*
	 * PLATES
	 */

	private List<Integer> drawPlates() {
		List<Integer> small = new ArrayList<>();
		for(int v=1;v<=10;v++) {small.add(v); small.add(v);}
		Collections.shuffle(small);

		List<Integer> large = new ArrayList<>(Arrays.asList(25,50,75,100));
		Collections.shuffle(large);

		int largeCount = UtilRandom.randomInt(5);
		List<Integer> drawn = new ArrayList<>();
		for(int i=0;i<largeCount;i++) drawn.add(large.get(i));
		for(int i=0;i<VALUE_COUNT-largeCount;i++) drawn.add(small.get(i));
		Collections.shuffle(drawn);
		return drawn;
	}


	/*
	 * INTERACTION
	 */

	private void onValueClicked(int idx) {
		if(phase!=Phase.PLAYING) return;

		if(firstIdx==null) {
			firstIdx = idx;
			refreshAll();
			return;
		}
		if(idx==firstIdx) {
			firstIdx = null;
			op = null;
			refreshAll();
			return;
		}
		if(op==null) return;

		char usedOp = op;
		int a = values.get(firstIdx);
		int b = values.get(idx);
		Integer result = compute(a, usedOp, b);
		if(result==null) {
			labelInfo.setText("Operation impossible");
			firstIdx = null;
			op = null;
			refreshAll();
			return;
		}

		values.remove(Math.max(firstIdx, idx));
		values.remove(Math.min(firstIdx, idx));
		values.add(result);
		firstIdx = null;
		op = null;

		if(result==target) {
			wins++;
			phase = Phase.FINISHED;
			labelInfo.setText("LE COMPTE EST BON !  "+a+" "+usedOp+" "+b+" = "+result);
		}
		else {
			labelInfo.setText(a+" "+usedOp+" "+b+" = "+result);
		}
		refreshAll();
	}

	private void onOpClicked(char c) {
		if(phase!=Phase.PLAYING || firstIdx==null) return;
		op = (op!=null && op==c) ? null : c;
		refreshAll();
	}

	private Integer compute(int a, char c, int b) {
		switch(c) {
			case '+': return a+b;
			case '-': return a>b ? a-b : null;
			case '*': return a*b;
			case '/': return (b!=0 && a%b==0) ? a/b : null;
			default: return null;
		}
	}

	private void validate() {
		if(phase!=Phase.PLAYING) return;

		int best = values.get(0);
		for(int v : values) if(Math.abs(v-target)<Math.abs(best-target)) best = v;

		phase = Phase.FINISHED;
		if(best==target) {
			wins++;
			labelInfo.setText("LE COMPTE EST BON !  "+best);
		}
		else {
			labelInfo.setText("Plus proche : "+best+"  (ecart "+Math.abs(best-target)+")");
		}
		firstIdx = null;
		op = null;
		refreshAll();
	}


	/*
	 * DISPLAY
	 */

	private void refreshAll() {
		labelTarget.setText("Cible : "+target+"   -   Reussites : "+wins);
		if(phase==Phase.PLAYING && labelInfo.getText().isEmpty())
			labelInfo.setText("Choisissez un nombre, une operation, puis un second nombre");

		valuesPanel.removeAll();
		for(int i=0;i<values.size();i++) {
			int idx = i;
			boolean selected = firstIdx!=null && firstIdx==idx;
			JButton b = button(String.valueOf(values.get(i)), () -> onValueClicked(idx));
			b.setPreferredSize(new Dimension(64, 64));
			b.setEnabled(phase==Phase.PLAYING);
			b.setOpaque(true);
			b.setBackground(selected ? COLOR_SELECTED : COLOR_VALUE);
			valuesPanel.add(b);
		}
		valuesPanel.revalidate();
		valuesPanel.repaint();

		char[] ops = {'+','-','*','/'};
		for(int i=0;i<opButtons.length;i++) {
			boolean selected = op!=null && op==ops[i];
			opButtons[i].setBackground(selected ? COLOR_OP_SELECTED : COLOR_OP);
			opButtons[i].setEnabled(phase==Phase.PLAYING && firstIdx!=null);
		}
	}
}
