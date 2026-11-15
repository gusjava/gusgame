package gus.game5.main.game.p1.c.mastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.mouse.Mouse;
import gus.game5.core.point.point0.Point0;

public class GameMastermind extends Game1 {

	public static final long SLEEP = 16;

	public static final int PEGS = 4;
	public static final int COLORS = 6;
	public static final int TRIES = 10;

	public static final int PEG_D = 36;
	public static final double PEG_R = PEG_D/2.0;
	public static final int PEG_GAP = 10;

	public static final int ROW_PEGS_W = PEGS*PEG_D + (PEGS-1)*PEG_GAP;

	public static final int INDIC_D = 12;
	public static final double INDIC_R = INDIC_D/2.0;
	public static final int INDIC_GAP = 4;
	public static final int INDIC_W = 2*INDIC_D + INDIC_GAP;
	public static final int INDIC_H = 2*INDIC_D + INDIC_GAP;
	public static final int ROW_GAP_INDIC = 26;
	public static final int ROW_W = ROW_PEGS_W + ROW_GAP_INDIC + INDIC_W;

	public static final int PALETTE_W = COLORS*PEG_D + (COLORS-1)*PEG_GAP;
	public static final int BOARD_W = Math.max(ROW_W, PALETTE_W);
	public static final int ROW_OFFSET_X = (BOARD_W - ROW_W)/2;

	public static final int ROW_H = 44;
	public static final int ROWS_AREA_H = TRIES*ROW_H;

	public static final int PALETTE_GAP = 20;
	public static final int PALETTE_LABEL_H = 16;
	public static final int PALETTE_H = PEG_D + PALETTE_LABEL_H;
	public static final int PALETTE_Y = ROWS_AREA_H + PALETTE_GAP;

	public static final int MARGIN = 16;
	public static final int TOP = 76;
	public static final int BOTTOM = 58;

	private Random random;
	private int[] secret;

	private List<int[]> guesses;
	private List<int[]> results;

	private int[] current;
	private int currentCount;

	private boolean won;
	private boolean over;


	public static void main(String[] args) {
		GameMastermind main = new GameMastermind();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Mastermind");
		s.setWidth(BOARD_W + 2*MARGIN);
		s.setHeight(TOP + ROWS_AREA_H + PALETTE_GAP + PALETTE_H + BOTTOM);
		s.setSleep(SLEEP);
		s.setBackground(Palette.WINDOW);
	}

	protected void initialize1() {
		random = new Random();
		secret = new int[PEGS];
		for(int k=0;k<PEGS;k++) secret[k] = random.nextInt(COLORS);

		guesses = new ArrayList<>();
		results = new ArrayList<>();
		current = new int[PEGS];
		currentCount = 0;
		won = false;
		over = false;

		addDraw(new GameDrawing());
	}


	/*
	 * TURN
	 */

	protected void turn() {
		Keyboard k = keyboard();
		if(handleRestartExit()) return;
		if(won || over) return;

		if(k.in().c()) {
			erasePeg();
			return;
		}
		if(k.in().space()) {
			validate();
			return;
		}
		int color = colorFromKeyboard(k);
		if(color<0) color = colorFromMouse(mouse());
		if(color>=0) addPeg(color);
	}

	private int colorFromKeyboard(Keyboard k) {
		if(k.in().num1()) return 0;
		if(k.in().num2()) return 1;
		if(k.in().num3()) return 2;
		if(k.in().num4()) return 3;
		if(k.in().num5()) return 4;
		if(k.in().num6()) return 5;
		return -1;
	}

	private int colorFromMouse(Mouse mse) {
		if(!mse.button1().justPressed()) return -1;
		Point0 p = mse.button1().justPressedPoint();
		double x = p.getX() - MARGIN;
		double y = p.getY() - TOP - PALETTE_Y;
		if(y<0 || y>=PEG_D) return -1;
		int col = (int) Math.floor(x/(PEG_D+PEG_GAP));
		if(col<0 || col>=COLORS) return -1;
		double localX = x - col*(PEG_D+PEG_GAP);
		if(localX>=PEG_D) return -1;
		return col;
	}

	private void addPeg(int color) {
		if(currentCount>=PEGS) return;
		current[currentCount++] = color;
	}

	private void erasePeg() {
		if(currentCount>0) currentCount--;
	}

	private void validate() {
		if(currentCount<PEGS) return;
		int[] guess = new int[PEGS];
		System.arraycopy(current, 0, guess, 0, PEGS);
		int[] score = Score.count(secret, guess, COLORS);

		guesses.add(guess);
		results.add(score);
		currentCount = 0;

		if(score[0]==PEGS) won = true;
		else if(guesses.size()>=TRIES) over = true;
	}


	/*
	 * DRAWING
	 */

	public class GameDrawing extends Drawing1 {

		public GameDrawing() {
			super();
			initOrigin().setXY(MARGIN, TOP);
		}

		protected void draw() {
			drawHeader();
			drawRows();
			drawPalette();
			drawHelp();
			drawMessage();
		}

		private void drawHeader() {
			drawString(Palette.LIGHT_TEXT, fontBold(28), p(0, -48), "MASTERMIND");
			drawString(Palette.LABEL, fontPlain(13), p(BOARD_W-150, -14),
					"Tentative "+Math.min(guesses.size()+1, TRIES)+" / "+TRIES);
		}

		private void drawRows() {
			for(int i=0;i<TRIES;i++) drawRow(i);
		}

		private void drawRow(int i) {
			boolean active = !won && !over && i==guesses.size();
			double top = i*ROW_H + 2;
			double height = ROW_H - 4;

			fillRect(active ? Palette.ROW_ACTIVE : Palette.BOARD, p(0, top), BOARD_W, height);
			if(active) drawRect(Palette.HIGHLIGHT, p(0, top), BOARD_W, height);

			double cy = i*ROW_H + ROW_H*0.5;
			for(int k=0;k<PEGS;k++) drawPeg(i, k, active, cy);
			drawIndicators(i, cy);
		}

		private void drawPeg(int row, int k, boolean active, double cy) {
			double cx = ROW_OFFSET_X + k*(PEG_D+PEG_GAP) + PEG_D*0.5;
			int color = pegColorAt(row, k, active);
			if(color<0) {
				fillRoundC(Palette.EMPTY, p(cx, cy), PEG_R);
				return;
			}
			fillRoundC(Palette.peg(color), p(cx, cy), PEG_R);
		}

		private int pegColorAt(int row, int k, boolean active) {
			if(row<guesses.size()) return guesses.get(row)[k];
			if(active && k<currentCount) return current[k];
			return -1;
		}

		private void drawIndicators(int row, double cy) {
			double x0 = ROW_OFFSET_X + ROW_PEGS_W + ROW_GAP_INDIC;
			double y0 = cy - INDIC_H*0.5;
			int blacks = row<results.size() ? results.get(row)[0] : 0;
			int whites = row<results.size() ? results.get(row)[1] : 0;
			for(int j=0;j<PEGS;j++) {
				int col = j%2;
				int line = j/2;
				double cx = x0 + col*(INDIC_D+INDIC_GAP) + INDIC_D*0.5;
				double y = y0 + line*(INDIC_D+INDIC_GAP) + INDIC_D*0.5;
				fillRoundC(indicatorColor(j, blacks, whites), p(cx, y), INDIC_R);
			}
		}

		private java.awt.Color indicatorColor(int j, int blacks, int whites) {
			if(j<blacks) return Palette.INDIC_BLACK;
			if(j<blacks+whites) return Palette.INDIC_WHITE;
			return Palette.INDIC_EMPTY;
		}

		private void drawPalette() {
			for(int c=0;c<COLORS;c++) {
				double cx = c*(PEG_D+PEG_GAP) + PEG_D*0.5;
				double cy = PALETTE_Y + PEG_D*0.5;
				fillRoundC(Palette.peg(c), p(cx, cy), PEG_R);
				drawStringC(Palette.LABEL, fontPlain(11), p(cx, PALETTE_Y+PEG_D+8), ""+(c+1));
			}
		}

		private void drawHelp() {
			double y = ROWS_AREA_H + PALETTE_GAP + PALETTE_H;
			drawString(Palette.LABEL, fontPlain(11), p(0, y+14),
					"1-6 (pave numerique) ou clic : choisir une couleur");
			drawString(Palette.LABEL, fontPlain(11), p(0, y+28),
					"C effacer   ESPACE valider la ligne");
			drawString(Palette.LABEL, fontPlain(11), p(0, y+42),
					"F1 rejouer   F2 quitter");
		}

		private void drawMessage() {
			if(!won && !over) return;
			fillRect(Palette.VEIL, p(0, 0), BOARD_W, ROWS_AREA_H);

			double cx = BOARD_W*0.5;
			double cy = ROWS_AREA_H*0.5;
			if(won) drawStringC(Palette.HIGHLIGHT, fontBold(34), p(cx, cy-60), "GAGNE !");
			else drawStringC(Palette.LOSE, fontBold(34), p(cx, cy-60), "PERDU");

			drawStringC(Palette.LABEL, fontPlain(12), p(cx, cy-28), "CODE SECRET");
			double startX = cx - ROW_PEGS_W*0.5 + PEG_D*0.5;
			for(int k=0;k<PEGS;k++)
				fillRoundC(Palette.peg(secret[k]), p(startX+k*(PEG_D+PEG_GAP), cy), PEG_R);

			drawStringC(Palette.LIGHT_TEXT, fontPlain(14), p(cx, cy+40), "F1 pour rejouer");
		}
	}
}
