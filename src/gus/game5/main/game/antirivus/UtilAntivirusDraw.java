package gus.game5.main.game.antirivus;

import gus.game5.core.angle.Angle;

import gus.game5.core.drawing.Drawing1;
import gus.game5.core.point.point1.Point1;
import static gus.game5.core.util.UtilArray.*;
import static gus.game5.main.game.antirivus.UtilAntivirusColor.*;

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
	
	/*
	 * PIECE
	 */
	
	public static void drawPiece(Drawing1 d, Point1 m, double r, int[][] data, int i, int j) {
		drawPieceContent(d, m, r, data, i, j);
		drawPieceBorder(d, m, r, data, i, j);
	}
	
	private static void drawPieceContent(Drawing1 d, Point1 m, double r, int[][] data, int i, int j) {
		int value = data[i][j];
		
		d.fillRoundC(WHITE, m, r-1);
		d.fillRoundC(COLORS[value], m, r*0.7);
		
		if(value==UtilAntivirus.PIECE0) {
			boolean hasSE = is(data, i+1, j, value);
			Angle angle = hasSE ? SE : NW;
			Point1 p1 = m.pAdd(angle.pointAt(r*0.6));
			Point1 p2 = m.pAdd(angle.pointAt(r));
			d.drawThickLine(COLORS[value], p1, p2, r*0.3);
		}
	}
	
	private static void drawPieceBorder(Drawing1 d, Point1 m, double r, int[][] data, int i, int j) {
		int value = data[i][j];
		boolean hasE = is(data, i+1, j+1, value);
		boolean hasS = is(data, i+1, j-1, value);
		boolean hasN = is(data, i-1, j+1, value);
		boolean hasW = is(data, i-1, j-1, value);
		boolean hasNE = is(data, i, j+1, value);
		boolean hasSW = is(data, i, j-1, value);
		boolean hasSE = is(data, i+1, j, value);
		boolean hasNW = is(data, i-1, j, value);
		
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
		
		if(DEBUG) d.drawStringC(WHITE, d.fontBold(10), m, b.toString());
	}
	
	private static void arc(Drawing1 d, Point1 m, double r, Angle a1, Angle a2) {
		d.drawArcC(LIGHT_GRAY, m, r, a1, a2);
	}
	
	private static void arc(Drawing1 d, Point1 m, double r, Angle aC, Angle a1, Angle a2) {
		Point1 p = m.pAdd(aC.pointAt(2*r));
		d.drawArcC(LIGHT_GRAY, p, r, a1, a2);
	}
	
	private static void line(Drawing1 d, Point1 m, double r, Angle aC, Angle aD) {
		Point1 p = m.pAdd(aC.pointAt(r));
		d.drawLine(LIGHT_GRAY, p, p.pAdd(r, aD));
	}
	
	/*
	 * EMPTY
	 */
	
	public static void drawEmpty(Drawing1 d, double r, int[][] data, int i, int j) {
		d.fillRoundC(WHITE, r-1);
		if(UtilAntivirus.isOutput(i,j)) {
			d.fillArcC(LIGHT_GRAY, r*0.7, NE, W);
			Point1 p1 = SW.pointAt(r + 3);
			Point1 p2 = NE.pointAt(r + 3);
			d.drawLine(LIGHT_GRAY, p1, p2);
		}
		else d.fillRoundC(LIGHT_GRAY, r*0.7);
	}
	
	/*
	 * BLOCKED
	 */
	
	public static void drawBlocked(Drawing1 d, double r) {
		d.drawRoundC(LIGHT_GRAY, r);
		d.fillRoundC(WHITE, r-1);
	}
	
	/*
	 * BOARD EDGES
	 */
	
	public static void drawBoardEdges(Drawing1 d, double r, int[] pos) {
		// West edge
		if (eq(pos, 1, 2) || eq(pos, 2, 1) || eq(pos, 3, 0)) {
			d.drawArcC(LIGHT_GRAY, r - 3, NE, S);
		}
		if (eq(pos, 2, 2) || eq(pos, 3, 1)) {
			d.drawArcC(LIGHT_GRAY, r + 3, SW, S);
		}
		// North edge
		if (eq(pos, 1, 4) || eq(pos, 2, 5) || eq(pos, 3, 6)) {
			d.drawArcC(LIGHT_GRAY, r - 3, SE, S);
		}
		if (eq(pos, 2, 4) || eq(pos, 3, 5)) {
			d.drawArcC(LIGHT_GRAY, r + 3, NW, S);
		}
		// East edge
		if (eq(pos, 5, 6) || eq(pos, 6, 5) || eq(pos, 7, 4)) {
			d.drawArcC(LIGHT_GRAY, r - 3, SW, S);
		}
		if (eq(pos, 5, 5) || eq(pos, 6, 4)) {
			d.drawArcC(LIGHT_GRAY, r + 3, NE, S);
		}
		// South edge
		if (eq(pos, 5, 0) || eq(pos, 6, 1) || eq(pos, 7, 2)) {
			d.drawArcC(LIGHT_GRAY, r - 3, NW, S);
		}
		if (eq(pos, 5, 1) || eq(pos, 6, 2)) {
			d.drawArcC(LIGHT_GRAY, r + 3, SE, S);
		}

		// North-East corner
		if (eq(pos, 4, 6)) {
			d.drawArcC(LIGHT_GRAY, r + 3, NW, W);
		}
		// South-East corner
		if (eq(pos, 7, 3)) {
			d.drawArcC(LIGHT_GRAY, r + 3, NE, W);
		}

		// South-West corner
		if (eq(pos, 4, 0)) {
			d.drawArcC(LIGHT_GRAY, r + 3, SE, W);
		}

		// North-West corner (output)
		if (eq(pos, 1, 3)) {

			Point1 p1 = SW.pointAt(r + 3);
			Point1 p2 = p1.pAdd(r, NW);
			Point1 p3 = NE.pointAt(r + 3);
			Point1 p4 = p3.pAdd(r, NW);

			d.drawLine(LIGHT_GRAY, p1, p2);
			d.drawLine(LIGHT_GRAY, p3, p4);
		}
		if (eq(pos, 0, 3)) {
			Point1 p1 = SW.pointAt(r + 3);
			Point1 p2 = NE.pointAt(r + 3);
			Point1 pp = SE.pointAt(15.2 * r);

			Point1 p1a = p1.pAdd(r, SE);
			Point1 p2a = p2.pAdd(r, SE);

			double d1 = 10 * r;
			double d2 = d1 + 10;
			double d3 = r + 3;

			Point1 p1b = p1.pAdd(0, d1);
			Point1 p2b = p2.pAdd(d1, 0);

			Point1 p1c = p1b.pAdd(d3, 0);
			Point1 p2c = p2b.pAdd(0, d3);

			Point1 p1d = p1b.pAdd(d3, d3);
			Point1 p2d = p2b.pAdd(d3, d3);

			Point1 p1e = p1d.pAdd(d2, 0);
			Point1 p2e = p2d.pAdd(0, d2);

			d.drawLine(LIGHT_GRAY, p1, p1a);
			d.drawLine(LIGHT_GRAY, p2, p2a);

			d.drawLine(LIGHT_GRAY, p1, p1b);
			d.drawLine(LIGHT_GRAY, p2, p2b);

			d.drawArcC(LIGHT_GRAY, p1c, d3, S, S);
			d.drawArcC(LIGHT_GRAY, p2c, d3, N, S);

			d.drawLine(LIGHT_GRAY, p1d, p1e);
			d.drawLine(LIGHT_GRAY, p2d, p2e);

			d.drawArcC(LIGHT_GRAY, pp, d3, E, S);
		}
	}
}
