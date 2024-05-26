package gus.game5.main.game.antirivus;

import gus.game5.core.angle.Angle;
import java.awt.Color;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.util.UtilArray;

public class UtilAntivirusDraw {
	public static final boolean DEBUG = false;

	public static final Angle E = Angle.ANGLE0;
	public static final Angle SE = Angle.ANGLE45;
	public static final Angle S = Angle.ANGLE90;
	public static final Angle SW = Angle.ANGLE135;
	public static final Angle W = Angle.ANGLE180;
	public static final Angle NW = Angle.ANGLE225;
	public static final Angle N = Angle.ANGLE270;
	public static final Angle NE = Angle.ANGLE315;
	
	public static void drawPiece(Drawing1 d, Point1 m, double r, int[][] data, int i, int j) {
		drawPieceContent(d, m, r, data, i, j);
		drawPieceBorder(d, m, r, data, i, j);
	}
	
	private static void drawPieceContent(Drawing1 d, Point1 m, double r, int[][] data, int i, int j) {
		int value = data[i][j];
		Color color = UtilAntivirus.COLORS[value];
		
		d.fillRoundC(Color.WHITE, m, r-1);
		d.fillRoundC(color, m, r*0.7);
		
		if(value==UtilAntivirus.PIECE0) {
			boolean hasSE = UtilArray.is(data, i+1, j, value);
			Angle angle = hasSE ? SE : NW;
			Point1 p1 = m.pAdd(angle.pointAt(r*0.6));
			Point1 p2 = m.pAdd(angle.pointAt(r));
			d.drawThickLine(color, p1, p2, r*0.3);
		}
	}
	
	private static void drawPieceBorder(Drawing1 d, Point1 m, double r, int[][] data, int i, int j) {
		int value = data[i][j];
		boolean hasE = UtilArray.is(data, i+1, j+1, value);
		boolean hasS = UtilArray.is(data, i+1, j-1, value);
		boolean hasN = UtilArray.is(data, i-1, j+1, value);
		boolean hasW = UtilArray.is(data, i-1, j-1, value);
		boolean hasNE = UtilArray.is(data, i, j+1, value);
		boolean hasSW = UtilArray.is(data, i, j-1, value);
		boolean hasSE = UtilArray.is(data, i+1, j, value);
		boolean hasNW = UtilArray.is(data, i-1, j, value);
		
		StringBuffer b = new StringBuffer();
		if(hasN) b.append("N-");
		if(hasS) b.append("S-");
		if(hasE) b.append("E-");
		if(hasW) b.append("W-");
		if(hasNE) b.append("NE-");
		if(hasNW) b.append("NW-");
		if(hasSW) b.append("SW-");
		if(hasSE) b.append("SE-");
		b.deleteCharAt(b.length()-1);
		String sign = b.toString();
		
		switch(sign) {
			case "N" : {
				arc(d,m,r, NE, N);
				arc(d, m, r, NW, E, SE);
				arc(d, m, r, NE, SW, SE);
				break;
			}
			case "S" : {
				arc(d,m,r, SW, N);
				arc(d, m, r, SE, W, SE);
				arc(d, m, r, SW, NE, SE);
				break;
			}
			case "W" : {
				arc(d,m,r, NW, N);
				arc(d, m, r, SW, N, SE);
				arc(d, m, r, NW, SE, SE);
				break;
			}
			case "E" : {
				arc(d,m,r, SE, N);
				arc(d, m, r, SE, NW, SE);
				arc(d, m, r, NE, S, SE);
				break;
			}
			case "NW": {
				arc(d,m,r, NE, W);
				line(d, m, r, NE, NW);
				line(d, m, r, SW, NW);
				break;
			}
			case "NE": {
				arc(d,m,r, SE, W);
				line(d, m, r, SE, NE);
				line(d, m, r, NW, NE);
				break;
			}
			case "SE": {
				arc(d,m,r, SW, W);
				line(d, m, r, NE, SE);
				line(d, m, r, SW, SE);
				break;
			}
			case "SW": {
				arc(d,m,r, NW, W);
				line(d, m, r, SE, SW);
				line(d, m, r, NW, SW);
				break;
			}
			case "S-E": {
				arc(d,m,r, SW, W);
				arc(d,m,r, SW, NE, SE);
				arc(d,m,r, NE, S, SE);
				arc(d,m,r, SE, W, S);
				break;
			}
			case "S-W": {
				arc(d,m,r, NW, W);
				arc(d,m,r, SW, N, S);
				arc(d,m,r, NW, SE, SE);
				arc(d,m,r, SE, W, SE);
				break;
			}
			case "E-W": {
				arc(d,m,r, NW, S);
				arc(d,m,r, SE, S);
				arc(d,m,r, SW, N, SE);
				arc(d,m,r, NW, SE, SE);
				arc(d,m,r, NE, S, SE);
				arc(d,m,r, SE, NW, SE);
				break;
			}
			case "N-W": {
				arc(d,m,r, NE, W);
				arc(d,m,r, NW, E, S);
				arc(d,m,r, NE, SW, SE);
				arc(d,m,r, SW, N, SE);
				break;
			}
			case "N-E": {
				arc(d,m,r, SE, W);
				arc(d,m,r, NE, S, S);
				arc(d,m,r, SE, NW, SE);
				arc(d,m,r, NW, E, SE);
				break;
			}
			case "N-S": {
				arc(d,m,r, NE, S);
				arc(d,m,r, SW, S);
				arc(d,m,r, SW, NE, SE);
				arc(d,m,r, NW, E, SE);
				arc(d,m,r, NE, SW, SE);
				arc(d,m,r, SE, W, SE);
				break;
			}
			case "S-NE": {
				arc(d,m,r, SW, S);
				line(d,m,r, SE, NE);
				line(d,m,r, NW, NE);
				arc(d,m,r, SE, W, SE);
				arc(d,m,r, SW, NE, SE);
				break;
			}
			case "S-SW": {
				arc(d,m,r, NW, W);
				line(d,m,r, NW, SW);
				arc(d,m,r, SE, W, SE);
				break;
			}
			case "S-SE": {
				arc(d,m,r, SW, W);
				line(d,m,r, NE, SE);
				arc(d,m,r, SW, NE, SE);
				break;
			}
			case "N-NW": {
				arc(d,m,r, NE, W);
				line(d,m,r, SW, NW);
				arc(d,m,r, NE, SW, SE);
				break;
			}
			case "N-NE": {
				arc(d,m,r, SE, W);
				line(d,m,r, SE, NE);
				arc(d,m,r, NW, E, SE);
				break;
			}
			case "S-NW": {
				arc(d,m,r, NE, S);
				line(d,m,r, NE, NW);
				line(d,m,r, SW, NW);
				arc(d,m,r, SE, W, SE);
				arc(d,m,r, SW, NE, SE);
				break;
			}
			case "N-SW": {
				arc(d,m,r, NE, S);
				line(d,m,r, SE, SW);
				line(d,m,r, NW, SW);
				arc(d,m,r, NE, SW, SE);
				arc(d,m,r, NW, E, SE);
				break;
			}
			case "N-SE": {
				arc(d,m,r, SW, S);
				line(d,m,r, NE, SE);
				line(d,m,r, SW, SE);
				arc(d,m,r, NE, SW, SE);
				arc(d,m,r, NW, E, SE);
				break;
			}
			case "E-SW": {
				arc(d,m,r, NW, S);
				line(d,m,r, SE, SW);
				line(d,m,r, NW, SW);
				arc(d,m,r, NE, S, SE);
				arc(d,m,r, SE, NW, SE);
				break;
			}
			case "E-NW": {
				arc(d,m,r, SE, S);
				line(d,m,r, NE, NW);
				line(d,m,r, SW, NW);
				arc(d,m,r, NE, S, SE);
				arc(d,m,r, SE, NW, SE);
				break;
			}
			case "E-NE": {
				arc(d,m,r, SE, W);
				line(d,m,r, NW, NE);
				arc(d,m,r, SE, NW, SE);
				break;
			}
			case "W-SE": {
				arc(d,m,r, NW, S);
				line(d,m,r, NE, SE);
				line(d,m,r, SW, SE);
				arc(d,m,r, NW, SE, SE);
				arc(d,m,r, SW, N, SE);
				break;
			}
			case "W-NE": {
				arc(d,m,r, SE, S);
				line(d,m,r, NW, NE);
				line(d,m,r, SE, NE);
				arc(d,m,r, NW, SE, SE);
				arc(d,m,r, SW, N, SE);
				break;
			}
			case "W-NW": {
				arc(d,m,r, NE, W);
				line(d,m,r, NE, NW);
				arc(d,m,r, SW, N, SE);
				break;
			}
			case "E-SE": {
				arc(d,m,r, SW, W);
				line(d,m,r, SW, SE);
				arc(d,m,r, NE, S, SE);
				break;
			}
			case "W-SW": {
				arc(d,m,r, NW, W);
				line(d,m,r, SE, SW);
				arc(d,m,r, NW, SE, SE);
				break;
			}
			case "NE-NW": {
				arc(d,m,r, SE, S);
				line(d,m,r, SE, NE);
				line(d,m,r, SW, NW);
				break;
			}
			case "SW-SE": {
				arc(d,m,r, NW, S);
				line(d,m,r, NW, SW);
				line(d,m,r, NE, SE);
				break;
			}
			case "NE-SE": {
				arc(d,m,r, SW, S);
				line(d,m,r, SW, SE);
				line(d,m,r, NW, NE);
				break;
			}
			case "NW-SW": {
				arc(d,m,r, NE, S);
				arc(d,m,r, NE, S);
				line(d,m,r, NE, NW);
				line(d,m,r, SE, SW);
				break;
			}
			default:d.drawRoundC(m, r);
		}
		
		if(DEBUG) d.drawStringC(Color.WHITE, d.fontBold(10), m, b.toString());
	}
	
	private static void arc(Drawing1 d, Point1 m, double r, Angle a1, Angle a2) {
		d.drawArcC(m, r, a1, a2);
	}
	
	private static void arc(Drawing1 d, Point1 m, double r, Angle aC, Angle a1, Angle a2) {
		Point1 p = m.pAdd(aC.pointAt(2*r));
		d.drawArcC(p, r, a1, a2);
	}
	
	private static void line(Drawing1 d, Point1 m, double r, Angle aC, Angle aD) {
		Point1 p = m.pAdd(aC.pointAt(r));
		d.drawLine(p, p.pAdd(r, aD));
	}
}
