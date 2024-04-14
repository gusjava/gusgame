package gus.game5.core.game;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import gus.game5.core.angle.Angle;
import gus.game5.core.draw.Draw;
import gus.game5.core.keyboard.Keyboard;
import gus.game5.core.mouse.Mouse;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.point.point2.Point2;

public abstract class Game {
	
	private Settings settings;
	private JPanel1 panel;
	private Keyboard keyboard;
	private Mouse mouse;

	private Thread1 thread;
	private long count = 0;
	private long startTime = 0;
	private boolean running = false;
	
	public Game() {
		settings = new Settings();
		initSettings(settings);
		
		panel = new JPanel1();
		panel.setPreferredSize(new Dimension(gameWidth(), gameHeight()));
		panel.setBackground(getBackground());
		
		keyboard = new Keyboard(panel);
		mouse = new Mouse(panel);
	}
	
	private class Thread1 extends Thread {
		public void run() {
			reset();
			running = true;
			while(running) {
				doPerform();
				count++;
				doSleep();
			}
		}
	}
	
	private void doPerform() {
		try {SwingUtilities.invokeAndWait(this::perform);}
		catch (Exception e) {e.printStackTrace();}
	}
	
	private void doSleep() {
		try { Thread.sleep(getSleep()); }
		catch (InterruptedException e) {}
	}
	
	private void reset() {
		count = 0;
		startTime = System.currentTimeMillis();
	}
	

	
	private void perform() {
		turn();
		paintPanel();
		keyboard.reset();
		mouse.reset();
	}
	
	private void paintPanel() {
		panel.setDraw(buildDraw());
		panel.repaint();
	}
	
	/*
	 * PUBLIC
	 */
	
	public void exit() {
		System.exit(0);
	}
	
	public void start() {
		if(thread!=null) throw new RuntimeException("Game already started");

		initialize();
		thread = new Thread1();
		thread.start();
	}
	
	public void stop() {
		if(thread==null) throw new RuntimeException("Game not started yet");
		
		running = false;
		thread = null;
	}
	
	public void restart() {
		if(thread==null) throw new RuntimeException("Game not started yet");
		
		initialize();
		reset();
	}
	
	public JFrame displayInWindows() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(panel);
		frame.setResizable(false);
		frame.setTitle(settings.getTitle());
		
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		return frame;
	}
	
	
	

	
	public long getCount() {
		return count;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public JPanel1 getPanel() {
		return panel;
	}
	
	public Keyboard keyboard() {
		return keyboard;
	}
	
	public Mouse mouse() {
		return mouse;
	}
	
	public int gameWidth() {
		return settings.getWidth();
	}
	
	public int gameHeight() {
		return settings.getHeight();
	}
	
	public Point0 gameCenter() {
		return new Point1(gameWidth()/2, gameHeight()/2);
	}
	
	public long getSleep() {
		return settings.getSleep();
	}
	
	public String getTitle() {
		return settings.getTitle();
	}
	
	public Color getBackground() {
		return settings.getBackground();
	}
	
	
	/*
	 * POINT 1
	 */
	
	public Point1 p1(double x, double y) {
		return new Point1(x, y);
	}
	public Point1 p1(double dist, Angle angle) {
		return new Point1(dist, angle);
	}
	public Point1 p1(Point0 p) {
		return new Point1(p);
	}
	public Point1 p1(double val) {
		return new Point1(val);
	}
	public Point1 p1() {
		return new Point1();
	}
	
	
	/*
	 * POINT 2
	 */
	
	public Point2 p2(double x, double y) {
		return new Point2(x, y);
	}
	public Point2 p2(double dist, Angle angle) {
		return new Point2(dist, angle);
	}
	public Point2 p2(Point0 p) {
		return new Point2(p);
	}
	public Point2 p2(double val) {
		return new Point2(val);
	}
	public Point2 p2() {
		return new Point2();
	}
	
	
	
	/*
	 * ABSTRACTS
	 */
	
	protected abstract Draw buildDraw();
	protected abstract void turn();
	protected abstract void initSettings(Settings settings);
	protected abstract void initialize();
}
