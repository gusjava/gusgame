package gus.game5.core.dyn;

import gus.game5.core.angle.Angle;
import gus.game5.core.features.g.GAngle;

public class DynGAngle implements GAngle, Dyn {

	private Angle angle;
	private Angle previous;
	private DynGDouble derived;
	
	public DynGAngle() {
		this.angle = Angle.ANGLE0;
	}
	
	public DynGAngle(Angle angle) {
		this.angle = angle;
	}
	
	public DynGAngle(Angle angle, DynGDouble derived) {
		this.angle = angle;
		setDerived(derived);
	}
	
	public DynGAngle(Angle angle, double derived) {
		this.angle = angle;
		setDerived(derived);
	}
	
	public static DynGAngle angleRad(double val) {
		return new DynGAngle(Angle.angleRad(val));
	}
	
	public static DynGAngle angleDeg(double val) {
		return new DynGAngle(Angle.angleDeg(val));
	}
	
	public void setAngle(Angle angle) {
		this.angle = angle;
	}
	
	public Angle g() {
		return angle;
	}

	public void goNext() {
		if(derived==null) return;

		previous = angle;
		derived.goNext();
		angle = angle.add(derived.gDouble());
	}

	public void goBack() {
		if(derived==null) return;
		if(previous==null) return;

		derived.goBack();
		angle = previous;
		previous = null;
	}
	
	/*
	 * DERIVED
	 */
	
	public DynGDouble getDerived() {
		return derived;
	}
	
	public DynGDouble initDerived() {
		if(derived==null) derived = new DynGDouble();
		return derived;
	}
	
	public void setDerived(DynGDouble derived) {
		this.derived = derived;
	}
	
	public void setDerived(double v) {
		this.derived = new DynGDouble(v);
	}
}
