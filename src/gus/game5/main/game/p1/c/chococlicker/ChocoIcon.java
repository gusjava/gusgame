package gus.game5.main.game.p1.c.chococlicker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;

import javax.swing.Icon;

public class ChocoIcon implements Icon {

	public enum Kind {CHOCOLATE, MONEY, CAKE, SELLER, BAKER}

	public static final Color COCOA_DARK = new Color(0x3E2512);
	public static final Color COCOA = new Color(0x5A3519);
	public static final Color COCOA_LIGHT = new Color(0x7B4B2A);
	public static final Color GOLD_DARK = new Color(0x7A5312);
	public static final Color GOLD = new Color(0xB8812A);
	public static final Color GOLD_LIGHT = new Color(0xE8B850);
	public static final Color CREAM = new Color(0xFFF6E6);
	public static final Color CREAM_DARK = new Color(0xE8D4B0);
	public static final Color SKIN = new Color(0xE8C9A0);
	public static final Color CHERRY = new Color(0xC0392B);
	public static final Color LEAF = new Color(0x4A7A2C);
	public static final Color WHITE = new Color(0xFFFFFF);
	public static final Color WHITE_SHADE = new Color(0xEDE3D4);
	public static final Color PLATE = new Color(0xD8C4A8);

	private final Kind kind;
	private final int size;

	public ChocoIcon(Kind kind, int size) {
		this.kind = kind;
		this.size = size;
	}

	public int getIconWidth() {
		return size;
	}

	public int getIconHeight() {
		return size;
	}

	public void paintIcon(Component c, Graphics g, int x, int y) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		g2.translate(x, y);
		g2.scale(size/100.0, size/100.0);

		switch(kind) {
			case CHOCOLATE: paintChocolate(g2); break;
			case MONEY: paintMoney(g2); break;
			case CAKE: paintCake(g2); break;
			case SELLER: paintSeller(g2); break;
			case BAKER: paintBaker(g2); break;
		}

		g2.dispose();
	}

	private void paintChocolate(Graphics2D g2) {
		g2.setColor(COCOA);
		g2.fillRoundRect(8, 24, 84, 56, 12, 12);

		g2.setColor(COCOA_LIGHT);
		for(int i=0 ; i<3 ; i++)
			for(int j=0 ; j<2 ; j++)
				g2.fillRoundRect(15+i*26, 30+j*25, 21, 20, 6, 6);

		g2.setColor(COCOA_DARK);
		g2.setStroke(new BasicStroke(4));
		g2.drawRoundRect(8, 24, 84, 56, 12, 12);
	}

	private void paintMoney(Graphics2D g2) {
		g2.setColor(GOLD);
		g2.fillOval(8, 8, 84, 84);
		g2.setColor(GOLD_LIGHT);
		g2.fillOval(17, 17, 66, 66);

		g2.setColor(GOLD_DARK);
		g2.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.drawArc(34, 28, 36, 44, 40, 280);
		g2.drawLine(26, 44, 58, 44);
		g2.drawLine(26, 58, 58, 58);
	}

	private void paintCake(Graphics2D g2) {
		g2.setColor(PLATE);
		g2.fillRoundRect(4, 80, 92, 11, 10, 10);

		g2.setColor(CREAM_DARK);
		g2.fillRoundRect(16, 46, 68, 36, 8, 8);
		g2.setColor(CREAM);
		g2.fillRect(16, 60, 68, 9);

		g2.setColor(COCOA);
		g2.fillRoundRect(13, 34, 74, 20, 12, 12);
		for(int i=0 ; i<4 ; i++) g2.fillOval(15+i*20, 44, 19, 15);

		g2.setColor(CHERRY);
		g2.fillOval(41, 16, 19, 19);
		g2.setColor(LEAF);
		g2.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		g2.drawLine(51, 18, 59, 7);
	}

	private void paintSeller(Graphics2D g2) {
		g2.setColor(COCOA_LIGHT);
		g2.fillRoundRect(70, 60, 27, 22, 5, 5);
		g2.setColor(COCOA_DARK);
		g2.fillRect(78, 55, 11, 6);

		g2.setColor(SKIN);
		g2.fillOval(32, 8, 33, 33);

		g2.setColor(COCOA_DARK);
		g2.fillRoundRect(19, 45, 58, 47, 16, 16);

		g2.setColor(CREAM);
		g2.fillPolygon(new Polygon(new int[]{48, 35, 48, 61}, new int[]{45, 51, 72, 51}, 4));

		g2.setColor(GOLD);
		g2.fillPolygon(new Polygon(new int[]{48, 42, 54}, new int[]{54, 76, 76}, 3));
	}

	private void paintBaker(Graphics2D g2) {
		g2.setColor(WHITE);
		g2.fillOval(24, 8, 23, 23);
		g2.fillOval(38, 2, 25, 25);
		g2.fillOval(54, 9, 21, 21);
		g2.fillRoundRect(28, 20, 43, 17, 6, 6);

		g2.setColor(WHITE_SHADE);
		g2.fillRect(28, 32, 43, 9);

		g2.setColor(SKIN);
		g2.fillOval(33, 38, 33, 31);

		g2.setColor(CREAM);
		g2.fillRoundRect(20, 66, 59, 30, 14, 14);

		g2.setColor(COCOA_LIGHT);
		g2.fillOval(46, 73, 8, 8);
		g2.fillOval(46, 85, 8, 8);
	}
}
