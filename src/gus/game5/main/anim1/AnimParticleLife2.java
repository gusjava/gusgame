package gus.game5.main.anim1;

import static gus.game5.core.util.UtilGui.*;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.SwingUtilities;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.features.g.GBool;
import gus.game5.core.features.g.GDouble;
import gus.game5.core.game.Game1;
import gus.game5.core.game.Settings;
import gus.game5.core.gui.JMenu1;
import gus.game5.core.gui.JMenuBar1;
import gus.game5.core.gui.JRadioButtonMenuItem1;
import gus.game5.core.keyboard.KeyboardState;
import gus.game5.core.util.UtilRandom;

public class AnimParticleLife2 extends Game1 {

	public static final long SLEEP = 16;
	public static final int WIDTH = 900;
	public static final int HEIGHT = 700;

	public static final int COUNT_START = 650;
	public static final int TYPE_MIN = 3;
	public static final int TYPE_MAX = 7;
	public static final int TYPE_START = 5;

	public static final double R_MIN_START = 14;
	public static final double R_MAX_START = 82;
	public static final double REPULSION_START = 2.4;
	public static final double FORCE_FACTOR_START = 0.52;
	public static final double SPEED_MAX_START = 4.5;
	public static final double RADIUS_START = 2.6;

	public static final double FRICTION_START = 0.86;
	public static final double FRICTION_MIN = 0.60;
	public static final double FRICTION_MAX = 1.00;
	public static final double FRICTION_STEP = 0.02;

	public static final int RESEED_PERIOD = 1700;
	public static final int FADE_LENGTH = 90;

	public static final int DRIFT_PERIOD = 1700;
	public static final double DRIFT_SPEED_START = 1.0;
	public static final double DRIFT_SPEED_MIN = 0.25;
	public static final double DRIFT_SPEED_MAX = 4.0;
	public static final double DRIFT_SPEED_FACTOR = 2.0;

	public static final double FILTER_ATTRACTION = 0.0;
	public static final double FILTER_SELF = 0.0;
	public static final double FILTER_ASYMMETRY = 0.55;
	public static final int FILTER_TRIES = 400;

	public static final int CELL = 50;
	public static final double SMOOTH = 30;
	public static final double GAS_DISPERSION = 2.0;
	public static final double FROZEN_SPEED = 0.15;
	public static final double TAIL_FACTOR = 4.0;

	public static final double[] COUNTS = {200, 350, 500, 650, 900, 1200, 1600};
	public static final double[] R_MAXES = {50, 65, 82, 100, 120};
	public static final double[] R_MINS = {6, 10, 14, 20, 28};
	public static final double[] REPULSIONS = {0.8, 1.6, 2.4, 3.5, 5.0};
	public static final double[] FORCE_FACTORS = {0.20, 0.35, 0.52, 0.75, 1.00};
	public static final double[] SPEED_MAXES = {1.5, 3.0, 4.5, 7.0, 10.0};
	public static final double[] RADIUSES = {1.5, 2.0, 2.6, 3.5, 5.0};
	public static final double[] DRIFT_SPEEDS = {0.25, 0.50, 1.00, 2.00, 4.00};

	public static final Color COLOR_BG = new Color(8,8,16);
	public static final Color COLOR_VEIL = new Color(8,8,16,205);
	public static final Color COLOR_TEXT = new Color(205,205,225);
	public static final Color COLOR_DIM = new Color(120,120,145);
	public static final Color COLOR_FLAG = new Color(255,205,110);

	public static final Color[] PALETTE = {
		new Color(255,95,95),
		new Color(255,160,70),
		new Color(240,225,90),
		new Color(110,235,140),
		new Color(90,220,225),
		new Color(100,185,255),
		new Color(220,130,255)
	};

	public static void main(String[] args) {
		AnimParticleLife2 main = new AnimParticleLife2();
		main.displayInWindows();
		main.start();
	}

	protected void initSettings(Settings s) {
		s.setTitle("Particle life V2");
		s.setWidth(WIDTH);
		s.setHeight(HEIGHT);
		s.setSleep(SLEEP);
		s.setBackground(COLOR_BG);
	}

	/*
	 * DATA
	 */

	private double[] px;
	private double[] py;
	private double[] vx;
	private double[] vy;
	private int[] type;

	private double[][] matrix;
	private double[][] matrixFrom;
	private double[][] matrixTo;
	private double driftT;
	private double driftSpeed;
	private long matrixSeed;
	private int typeCount;
	private double friction;
	private int sinceReseed;

	private int count;
	private double rMin;
	private double rMax;
	private double repulsion;
	private double forceFactor;
	private double speedMax;
	private double radius;

	private boolean paused;
	private boolean filtered;
	private boolean tails;
	private boolean hud;
	private boolean drifting;

	private int gridW;
	private int gridH;
	private double cellW;
	private double cellH;
	private int[] cellStart;
	private int[] bucket;

	private int[] census;
	private double speedAvg;
	private double dispersionAvg;
	private int rejected;

	private final List<Runnable> pending = new ArrayList<>();
	private final List<Choice> choices = new ArrayList<>();
	private final List<Flag> flags = new ArrayList<>();
	private boolean syncing;
	private boolean dirty;

	protected void initialize1() {
		count = COUNT_START;
		typeCount = TYPE_START;
		friction = FRICTION_START;
		driftSpeed = DRIFT_SPEED_START;
		rMin = R_MIN_START;
		rMax = R_MAX_START;
		repulsion = REPULSION_START;
		forceFactor = FORCE_FACTOR_START;
		speedMax = SPEED_MAX_START;
		radius = RADIUS_START;

		filtered = true;
		tails = true;
		hud = true;
		paused = false;
		drifting = true;

		census = new int[((WIDTH+CELL-1)/CELL)*((HEIGHT+CELL-1)/CELL)];
		resizeGrid();
		allocate();

		scatter();
		assignTypes();
		resetMatrices();

		addDraw(new Field());
		addDraw(new Hud());
	}

	protected void turn() {
		runPending();
		readKeyboard();
		refreshIfNeeded();
		if(paused) return;

		sinceReseed++;
		if(drifting) advanceDrift();
		else if(sinceReseed>=RESEED_PERIOD) newMatrix();

		applyForces();
		moveParticles();
		measure();
	}

	private void readKeyboard() {
		KeyboardState k = keyboard().in();
		if(k.F2()) exit();
		if(k.F1()) scatter();
		if(k.F3()) setFriction(friction-FRICTION_STEP);
		if(k.F4()) setFriction(friction+FRICTION_STEP);
		if(k.F5()) setTypeCount(typeCount-1);
		if(k.F6()) setTypeCount(typeCount+1);
		if(k.F7()) newMatrix();
		if(k.F8()) setFiltered(!filtered);
		if(k.F9()) setTails(!tails);
		if(k.F10()) setHud(!hud);
		if(k.F11()) setDrifting(!drifting);
		if(k.plus()) setDriftSpeed(driftSpeed*DRIFT_SPEED_FACTOR);
		if(k.minus()) setDriftSpeed(driftSpeed/DRIFT_SPEED_FACTOR);
		if(k.space()) setPaused(!paused);
		if(k.F12()) applyFullScreen(true);
		if(k.escap()) applyFullScreen(false);
	}

	/*
	 * CONFIGURATION
	 */

	private void allocate() {
		px = new double[count];
		py = new double[count];
		vx = new double[count];
		vy = new double[count];
		type = new int[count];
		bucket = new int[count];
	}

	private void resizeGrid() {
		gridW = Math.max(1, (int) (WIDTH/rMax));
		gridH = Math.max(1, (int) (HEIGHT/rMax));
		cellW = WIDTH/(double) gridW;
		cellH = HEIGHT/(double) gridH;
	}

	private void scatter() {
		for(int i=0;i<count;i++) {
			px[i] = UtilRandom.randomDouble(WIDTH);
			py[i] = UtilRandom.randomDouble(HEIGHT);
			vx[i] = 0;
			vy[i] = 0;
		}
		speedAvg = 0;
		dispersionAvg = 0;
	}

	private void assignTypes() {
		for(int i=0;i<count;i++) type[i] = i*typeCount/count;
	}

	private void setCount(int value) {
		if(value==count) return;

		count = value;
		allocate();
		scatter();
		assignTypes();
		dirty = true;
	}

	private void setFriction(double value) {
		friction = Math.min(FRICTION_MAX, Math.max(FRICTION_MIN, value));
		dirty = true;
	}

	private void setTypeCount(int value) {
		int wanted = Math.min(TYPE_MAX, Math.max(TYPE_MIN, value));
		if(wanted==typeCount) return;

		typeCount = wanted;
		assignTypes();
		resetMatrices();
		dirty = true;
	}

	private void setDrifting(boolean value) {
		if(value==drifting) return;

		drifting = value;
		if(drifting) aimAtNewTarget();
		sinceReseed = FADE_LENGTH;
		dirty = true;
	}

	private void setDriftSpeed(double value) {
		driftSpeed = Math.min(DRIFT_SPEED_MAX, Math.max(DRIFT_SPEED_MIN, value));
		dirty = true;
	}

	private void setRMin(double value) {
		rMin = value;
		dirty = true;
	}

	private void setRMax(double value) {
		rMax = value;
		resizeGrid();
		dirty = true;
	}

	private void setRepulsion(double value) {
		repulsion = value;
		dirty = true;
	}

	private void setForceFactor(double value) {
		forceFactor = value;
		dirty = true;
	}

	private void setSpeedMax(double value) {
		speedMax = value;
		dirty = true;
	}

	private void setRadius(double value) {
		radius = value;
		dirty = true;
	}

	private void setFiltered(boolean value) {
		filtered = value;
		dirty = true;
	}

	private void setTails(boolean value) {
		tails = value;
		dirty = true;
	}

	private void setHud(boolean value) {
		hud = value;
		dirty = true;
	}

	private void setPaused(boolean value) {
		paused = value;
		dirty = true;
	}

	private void applyFullScreen(boolean value) {
		setFullScreen(value);
		dirty = true;
	}

	private void applyDefaults() {
		setCount(COUNT_START);
		setTypeCount(TYPE_START);
		setFriction(FRICTION_START);
		setDriftSpeed(DRIFT_SPEED_START);
		setRMin(R_MIN_START);
		setRMax(R_MAX_START);
		setRepulsion(REPULSION_START);
		setForceFactor(FORCE_FACTOR_START);
		setSpeedMax(SPEED_MAX_START);
		setRadius(RADIUS_START);
		setDrifting(true);
		setFiltered(true);
		setTails(true);
		setHud(true);
		setPaused(false);
	}

	private void newMatrix() {
		if(drifting) aimAtNewTarget();
		else resetMatrices();
	}

	private void resetMatrices() {
		matrix = randomFiltered();
		matrixFrom = matrix;
		matrixTo = randomFiltered();
		driftT = 0;
		sinceReseed = 0;
	}

	private void aimAtNewTarget() {
		matrixFrom = matrix;
		matrixTo = randomFiltered();
		driftT = 0;
	}

	private void advanceDrift() {
		driftT += driftSpeed/DRIFT_PERIOD;
		if(driftT>=1) {
			matrixFrom = matrixTo;
			matrixTo = randomFiltered();
			driftT = 0;
		}
		matrix = blend(matrixFrom, matrixTo, driftT);
	}

	private double[][] blend(double[][] from, double[][] to, double t) {
		double[][] m = new double[typeCount][typeCount];
		for(int a=0;a<typeCount;a++)
		for(int b=0;b<typeCount;b++)
		m[a][b] = from[a][b]+(to[a][b]-from[a][b])*t;
		return m;
	}

	private double[][] randomFiltered() {
		matrixSeed = UtilRandom.randomInt(Integer.MAX_VALUE);
		Random random = new Random(matrixSeed);
		rejected = 0;

		double[][] m = draw(random);
		while(filtered && !worthWatching(m) && rejected<FILTER_TRIES) {
			m = draw(random);
			rejected++;
		}
		return m;
	}

	private double[][] draw(Random random) {
		double[][] m = new double[typeCount][typeCount];
		for(int a=0;a<typeCount;a++)
		for(int b=0;b<typeCount;b++)
		m[a][b] = -1.0+2.0*random.nextDouble();
		return m;
	}

	private boolean worthWatching(double[][] m) {
		return meanAttraction(m)>FILTER_ATTRACTION
			&& selfAttraction(m)>FILTER_SELF
			&& asymmetry(m)>FILTER_ASYMMETRY;
	}

	private double meanAttraction(double[][] m) {
		double s = 0;
		for(int a=0;a<typeCount;a++) for(int b=0;b<typeCount;b++) s += m[a][b];
		return s/(typeCount*typeCount);
	}

	private double selfAttraction(double[][] m) {
		double s = 0;
		for(int a=0;a<typeCount;a++) s += m[a][a];
		return s/typeCount;
	}

	private double asymmetry(double[][] m) {
		double s = 0;
		int n = 0;
		for(int a=0;a<typeCount;a++)
		for(int b=a+1;b<typeCount;b++) {
			s += Math.abs(m[a][b]-m[b][a]);
			n++;
		}
		return n==0 ? 0 : s/n;
	}

	/*
	 * MENU BAR
	 */

	private interface Apply {
		void apply(double value);
	}

	private interface Switch {
		void set(boolean value);
	}

	protected void initMenuBar(JMenuBar1 menuBar) {
		menuBar.add(simulationMenu());
		menuBar.add(typesMenu());
		menuBar.add(matrixMenu());
		menuBar.add(displayMenu());
		menuBar.add(gameMenu());
	}

	private JMenu simulationMenu() {
		JMenu menu = new JMenu("Simulation");
		menu.add(choice("Particules", () -> count, v -> setCount((int) v), COUNTS, 0).menu);
		menu.add(choice("Portee", () -> rMax, this::setRMax, R_MAXES, 0).menu);
		menu.add(choice("Distance mini", () -> rMin, this::setRMin, R_MINS, 0).menu);
		menu.add(choice("Repulsion", () -> repulsion, this::setRepulsion, REPULSIONS, 2).menu);
		menu.add(choice("Intensite", () -> forceFactor, this::setForceFactor, FORCE_FACTORS, 2).menu);
		menu.add(choice("Friction (F3/F4)", () -> friction, this::setFriction, frictionValues(), 2).menu);
		menu.add(choice("Vitesse max", () -> speedMax, this::setSpeedMax, SPEED_MAXES, 1).menu);
		return menu;
	}

	private JMenu typesMenu() {
		return choice("Types (F5/F6)", () -> typeCount, v -> setTypeCount((int) v), typeValues(), 0).menu;
	}

	private JMenu matrixMenu() {
		JMenu menu = new JMenu("Matrice");
		menu.add(choice("Mode (F11)", () -> drifting ? 1 : 0, v -> setDrifting(v>0.5),
			new double[] {1, 0}, new String[] {"Derive", "Sauts"}).menu);
		menu.add(choice("Vitesse de derive (+/-)", () -> driftSpeed, this::setDriftSpeed,
			DRIFT_SPEEDS, labels(DRIFT_SPEEDS, 2, "x")).menu);
		menu.add(flag("Filtre des matrices ternes (F8)", () -> filtered, this::setFiltered).item);
		menu.addSeparator();
		menu.add(action("Nouvelle cible (F7)", () -> later(this::newMatrix)));
		return menu;
	}

	private JMenu displayMenu() {
		JMenu menu = new JMenu("Affichage");
		menu.add(flag("Queues de vitesse (F9)", () -> tails, this::setTails).item);
		menu.add(flag("Bandeau (F10)", () -> hud, this::setHud).item);
		menu.addSeparator();
		menu.add(choice("Taille des particules", () -> radius, this::setRadius, RADIUSES, 1).menu);
		return menu;
	}

	private JMenu gameMenu() {
		JMenu menu = new JMenu("Partie");
		menu.add(action("Disperser (F1)", () -> later(this::scatter)));
		menu.add(flag("Pause (espace)", () -> paused, this::setPaused).item);
		menu.add(flag("Plein ecran (F12, Echap pour sortir)", this::isFullScreen, this::applyFullScreen).item);
		menu.addSeparator();
		menu.add(action("Valeurs par defaut", () -> later(this::applyDefaults)));
		menu.addSeparator();
		menu.add(action("Quitter (F2)", this::exit));
		return menu;
	}

	private Choice choice(String title, GDouble current, Apply apply, double[] values, int decimals) {
		return choice(title, current, apply, values, labels(values, decimals, ""));
	}

	private Choice choice(String title, GDouble current, Apply apply, double[] values, String[] labels) {
		return new Choice(title, current, apply, values, labels);
	}

	private Flag flag(String title, GBool current, Switch apply) {
		return new Flag(title, current, apply);
	}

	private String[] labels(double[] values, int decimals, String prefix) {
		String[] labels = new String[values.length];
		for(int i=0;i<values.length;i++) labels[i] = prefix+text(values[i], decimals);
		return labels;
	}

	private double[] frictionValues() {
		int n = (int) Math.round((FRICTION_MAX-FRICTION_MIN)/FRICTION_STEP)+1;
		double[] values = new double[n];
		for(int i=0;i<n;i++) values[i] = FRICTION_MIN+i*FRICTION_STEP;
		return values;
	}

	private double[] typeValues() {
		double[] values = new double[TYPE_MAX-TYPE_MIN+1];
		for(int i=0;i<values.length;i++) values[i] = TYPE_MIN+i;
		return values;
	}

	private class Choice {

		private final JMenu1 menu;
		private final GDouble current;
		private final double[] values;
		private final JRadioButtonMenuItem1[] items;

		private Choice(String title, GDouble current, Apply apply, double[] values, String[] labels) {
			this.current = current;
			this.values = values;

			items = new JRadioButtonMenuItem1[values.length];
			for(int i=0;i<values.length;i++) {
				double value = values[i];
				items[i] = radioMenuItem(labels[i], () -> {
					if(!syncing) later(() -> apply.apply(value));
				});
			}

			menu = menu(title, -1, items);
			choices.add(this);
			select();
		}

		private void select() {
			double value = current.gDouble();
			int best = 0;
			for(int i=1;i<values.length;i++)
			if(Math.abs(values[i]-value)<Math.abs(values[best]-value)) best = i;

			if(items[best].isSelected()) return;

			syncing = true;
			items[best].setSelected(true);
			syncing = false;
		}
	}

	private class Flag {

		private final JCheckBoxMenuItem item;
		private final GBool current;

		private Flag(String title, GBool current, Switch apply) {
			this.current = current;

			item = new JCheckBoxMenuItem(title);
			item.addActionListener(e -> later(() -> apply.set(item.isSelected())));
			flags.add(this);
			select();
		}

		private void select() {
			if(item.isSelected()==current.gBool()) return;
			item.setSelected(current.gBool());
		}
	}

	private void later(Runnable r) {
		synchronized(pending) {
			pending.add(r);
		}
	}

	private void runPending() {
		synchronized(pending) {
			if(pending.isEmpty()) return;
			for(Runnable r : pending) r.run();
			pending.clear();
		}
	}

	private void refreshIfNeeded() {
		if(!dirty) return;

		dirty = false;
		SwingUtilities.invokeLater(this::refreshMenus);
	}

	private void refreshMenus() {
		for(Choice choice : choices) choice.select();
		for(Flag flag : flags) flag.select();
	}

	/*
	 * PHYSICS
	 */

	private void applyForces() {
		buildGrid();

		for(int i=0;i<count;i++) {
			double fx = 0;
			double fy = 0;

			int cx = cellX(px[i]);
			int cy = cellY(py[i]);

			for(int oy=-1;oy<=1;oy++)
			for(int ox=-1;ox<=1;ox++) {
				int nx = ((cx+ox)%gridW+gridW)%gridW;
				int ny = ((cy+oy)%gridH+gridH)%gridH;
				int cell = ny*gridW+nx;

				for(int k=cellStart[cell];k<cellStart[cell+1];k++) {
					int j = bucket[k];
					if(i==j) continue;

					double dx = shortest(px[j]-px[i], WIDTH);
					double dy = shortest(py[j]-py[i], HEIGHT);
					double d2 = dx*dx+dy*dy;
					if(d2>=rMax*rMax || d2==0) continue;

					double d = Math.sqrt(d2);
					double f = force(d, matrix[type[i]][type[j]]);
					fx += f*dx/d;
					fy += f*dy/d;
				}
			}

			vx[i] = (vx[i]+fx*forceFactor)*friction;
			vy[i] = (vy[i]+fy*forceFactor)*friction;

			double speed = Math.sqrt(vx[i]*vx[i]+vy[i]*vy[i]);
			if(speed>speedMax) {
				vx[i] *= speedMax/speed;
				vy[i] *= speedMax/speed;
			}
		}
	}

	private void buildGrid() {
		int cells = gridW*gridH;
		int[] start = new int[cells+1];

		for(int i=0;i<count;i++) start[cellOf(i)+1]++;
		for(int c=0;c<cells;c++) start[c+1] += start[c];

		int[] cursor = start.clone();
		for(int i=0;i<count;i++) bucket[cursor[cellOf(i)]++] = i;

		cellStart = start;
	}

	private int cellOf(int i) {
		return cellY(py[i])*gridW+cellX(px[i]);
	}

	private int cellX(double x) {
		int cx = (int) (x/cellW);
		return cx>=gridW ? gridW-1 : cx;
	}

	private int cellY(double y) {
		int cy = (int) (y/cellH);
		return cy>=gridH ? gridH-1 : cy;
	}

	private double force(double d, double attraction) {
		if(d<rMin) return repulsion*(d/rMin-1);
		double mid = (rMin+rMax)/2;
		return attraction*(1-Math.abs(d-mid)/(mid-rMin));
	}

	private double shortest(double delta, double span) {
		if(delta>span/2) return delta-span;
		if(delta<-span/2) return delta+span;
		return delta;
	}

	private void moveParticles() {
		for(int i=0;i<count;i++) {
			px[i] = wrap(px[i]+vx[i], WIDTH);
			py[i] = wrap(py[i]+vy[i], HEIGHT);
		}
	}

	private double wrap(double v, double span) {
		if(v<0) return v+span;
		if(v>=span) return v-span;
		return v;
	}

	/*
	 * MEASURE
	 */

	private void measure() {
		double sum = 0;
		for(int i=0;i<count;i++) sum += Math.sqrt(vx[i]*vx[i]+vy[i]*vy[i]);
		speedAvg += (sum/count-speedAvg)/SMOOTH;

		int columns = (WIDTH+CELL-1)/CELL;
		int rows = (HEIGHT+CELL-1)/CELL;
		for(int c=0;c<census.length;c++) census[c] = 0;
		for(int i=0;i<count;i++) {
			int cx = Math.min(columns-1, (int) (px[i]/CELL));
			int cy = Math.min(rows-1, (int) (py[i]/CELL));
			census[cy*columns+cx]++;
		}

		double mean = count/(double) census.length;
		double variance = 0;
		for(int c : census) variance += (c-mean)*(c-mean);
		variance /= census.length;

		dispersionAvg += (variance/mean-dispersionAvg)/SMOOTH;
	}

	private String regime() {
		if(dispersionAvg<GAS_DISPERSION) return "GAZ";
		if(speedAvg<FROZEN_SPEED) return "FIGE";
		return "STRUCTURES";
	}

	private Color colorOf(int t) {
		Color base = PALETTE[t*(PALETTE.length-1)/(typeCount-1)];
		if(sinceReseed>=FADE_LENGTH) return base;
		int alpha = 90+165*sinceReseed/FADE_LENGTH;
		return new Color(base.getRed(), base.getGreen(), base.getBlue(), alpha);
	}

	private String text(double value, int decimals) {
		return String.format(Locale.FRANCE, "%."+decimals+"f", value);
	}

	private String modeText() {
		if(!drifting) return "sauts";
		return "derive "+Math.round(driftT*100)+"%  (x"+text(driftSpeed, 2)+")";
	}

	/*
	 * FIELD
	 */

	private class Field extends Drawing1 {

		protected void draw() {
			for(int i=0;i<count;i++) {
				Color color = colorOf(type[i]);
				if(tails) drawTail(i, color);
				fillRoundC(color, p(px[i], py[i]), radius);
			}
		}

		private void drawTail(int i, Color color) {
			double tx = px[i]-vx[i]*TAIL_FACTOR;
			double ty = py[i]-vy[i]*TAIL_FACTOR;
			if(tx<0 || tx>=WIDTH || ty<0 || ty>=HEIGHT) return;

			drawLine(new Color(color.getRed(), color.getGreen(), color.getBlue(), 90),
				p(tx, ty), p(px[i], py[i]));
		}
	}

	/*
	 * HUD
	 */

	private class Hud extends Drawing1 {

		protected void draw() {
			if(!hud) {
				drawString(COLOR_DIM, fontPlain(11), p(12, HEIGHT-12), "F10 : affichage");
				return;
			}

			fillRect(COLOR_VEIL, p(0, 0), WIDTH, 54);
			fillRect(COLOR_VEIL, p(0, HEIGHT-26), WIDTH, 26);

			Font bold = fontBold(13);
			Font plain = fontPlain(12);

			drawString(COLOR_TEXT, bold, p(14, 22),
				typeCount+" types     friction "+text(friction, 2)
				+"     "+modeText()
				+"     filtre "+(filtered ? "ON" : "OFF")
				+"     cible "+Long.toHexString(matrixSeed).toUpperCase());

			drawString(COLOR_DIM, plain, p(14, 42),
				count+" particules"
				+"     vitesse "+text(speedAvg, 2)
				+"     dispersion "+text(dispersionAvg, 1)
				+"     queues "+(tails ? "ON" : "OFF")
				+(rejected>0 ? "     rejets "+rejected : ""));

			drawString(COLOR_FLAG, bold, p(WIDTH-240, 42), "regime "+regime());
			if(paused) drawString(COLOR_FLAG, bold, p(WIDTH-240, 22), "PAUSE");

			drawString(COLOR_DIM, fontPlain(11), p(14, HEIGHT-9),
				"F1 disperser   F3/F4 friction   F5/F6 types   F7 cible   F8 filtre   "
				+"F9 queues   F10 hud   F11 mode   +/- vitesse   espace pause   F12 plein ecran   Echap quitter plein ecran   F2 quitter");
		}
	}
}
