package gus.game5.main.game.p1.c.simon;

import static gus.game5.core.util.UtilGui.*;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.gui.JMenuBar1;
import gus.game5.core.util.UtilRandom;

public class GameSimon extends Game1 {

	public static final long SLEEP = 16;
	public static final int ZONES = 4;

	public static final int ZONE_SIZE = 140;
	public static final int ROUND_START_TICKS = 40;
	public static final int FLASH_ON_TICKS = 24;
	public static final int FLASH_OFF_TICKS = 14;
	public static final int CLICK_FLASH_MS = 150;

	public static final Color[] BASE_COLORS = {
		new Color(170,40,40), new Color(30,110,40), new Color(30,60,140), new Color(160,140,20)
	};
	public static final Color[] LIT_COLORS = {
		new Color(255,70,70), new Color(60,200,80), new Color(90,140,245), new Color(250,220,40)
	};

	private enum Phase {ROUND_START, FLASH_ON, FLASH_OFF, INPUT, WRONG}


	private JLabel labelInfo;
	private JButton[] zoneButtons;

	private List<Integer> sequence;
	private int round;
	private int best;
	private int inputIndex;

	private Phase phase;
	private int showStep;
	private int timer;


	public static void main(String[] args) {
		GameSimon main = new GameSimon();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Simon");
		s.setSleep(SLEEP);
	}

	protected void initialize1() {
		sequence = new ArrayList<>();
		round = 0;
		startRound();
	}


	/*
	 * CONTENT PANE
	 */

	protected Container buildContentPane() {
		labelInfo = new JLabel("", SwingConstants.CENTER);
		fontSize(labelInfo, 18);

		JPanel grid = panelGrid(2, 2, 10);
		zoneButtons = new JButton[ZONES];
		for(int i=0;i<ZONES;i++) {
			int zone = i;
			JButton b = button("", () -> onZoneClicked(zone));
			b.setPreferredSize(new Dimension(ZONE_SIZE, ZONE_SIZE));
			b.setOpaque(true);
			b.setBorderPainted(true);
			setLit(b, zone, false);
			zoneButtons[zone] = b;
			grid.add(b);
		}

		JPanel south = new JPanel();
		south.add(button("Nouvelle partie", this::restart));

		return panelCNS(empty(grid, 16), empty(labelInfo, 12), south);
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
	 * ROUND
	 */

	private void startRound() {
		round++;
		sequence.add(UtilRandom.randomInt(ZONES));
		inputIndex = 0;
		showStep = -1;
		phase = Phase.ROUND_START;
		timer = ROUND_START_TICKS;
		updateInfo();
	}


	/*
	 * TURN
	 */

	protected void turn() {
		if(phase==Phase.INPUT || phase==Phase.WRONG) return;

		timer--;
		if(timer>0) return;
		advance();
	}

	private void advance() {
		if(phase==Phase.ROUND_START) {
			showStep = 0;
			setLit(zoneButtons[sequence.get(showStep)], sequence.get(showStep), true);
			phase = Phase.FLASH_ON;
			timer = FLASH_ON_TICKS;
			return;
		}
		if(phase==Phase.FLASH_ON) {
			setLit(zoneButtons[sequence.get(showStep)], sequence.get(showStep), false);
			phase = Phase.FLASH_OFF;
			timer = FLASH_OFF_TICKS;
			return;
		}
		if(phase==Phase.FLASH_OFF) {
			showStep++;
			if(showStep>=sequence.size()) {
				phase = Phase.INPUT;
				updateInfo();
				return;
			}
			setLit(zoneButtons[sequence.get(showStep)], sequence.get(showStep), true);
			phase = Phase.FLASH_ON;
			timer = FLASH_ON_TICKS;
		}
	}


	/*
	 * INPUT
	 */

	private void onZoneClicked(int zone) {
		if(phase!=Phase.INPUT) return;

		flashClick(zone);

		if(zone!=sequence.get(inputIndex)) {
			phase = Phase.WRONG;
			updateInfo();
			return;
		}

		inputIndex++;
		if(inputIndex>=sequence.size()) {
			if(round>best) best = round;
			startRound();
		}
	}

	private void flashClick(int zone) {
		setLit(zoneButtons[zone], zone, true);
		Timer t = new Timer(CLICK_FLASH_MS, e -> setLit(zoneButtons[zone], zone, false));
		t.setRepeats(false);
		t.start();
	}


	/*
	 * DISPLAY
	 */

	private void setLit(JButton b, int zone, boolean lit) {
		b.setBackground(lit ? LIT_COLORS[zone] : BASE_COLORS[zone]);
	}

	private void updateInfo() {
		for(JButton b : zoneButtons) b.setEnabled(phase==Phase.INPUT);

		if(phase==Phase.WRONG) {
			labelInfo.setText("Perdu !  Score "+(round-1)+"  -  Meilleur "+best);
			return;
		}
		if(phase==Phase.INPUT) {
			labelInfo.setText("A vous  -  Manche "+round+"  -  Meilleur "+best);
			return;
		}
		labelInfo.setText("Memorisez la sequence  -  Manche "+round+"  -  Meilleur "+best);
	}
}
