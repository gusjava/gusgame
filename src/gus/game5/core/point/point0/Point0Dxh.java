package gus.game5.core.point.point0;

import gus.game5.core.features.g.G;
import gus.game5.core.features.g.GDouble;
import gus.game5.core.features.h.H;

public class Point0Dxh extends Point0 {

	private G<H> gH;
	private GDouble gX;
	
	public Point0Dxh(G<H> gH, GDouble gX) {
		this.gH = gH;
		this.gX = gX;
	}
	
	public Point0Dxh(H h, GDouble gX) {
		this.gH = ()->h;
		this.gX = gX;
	}
	
	public H getH() {
		return gH.g();
	}

	public double getX() {
		return gX.gDouble();
	}

	public double getY() {
		return getH().h(getX());
	}
}
