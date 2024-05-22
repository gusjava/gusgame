package gus.game5.core.dyn;

import gus.game5.core.features.g.GDouble;

public class DynGDouble implements GDouble, Dyn {

	private double value;
	private Double previous;
	private DynGDouble derived;
	
	public DynGDouble() {
		this.value = 0;
	}
	
	public DynGDouble(double val) {
		this.value = val;
	}
	
	public DynGDouble(double val, DynGDouble derived) {
		this.value = val;
		setDerived(derived);
	}
	
	public DynGDouble(double val, double derived) {
		this.value = val;
		setDerived(derived);
	}
	
	public void setValue(double value) {
		this.value = value;
	}

	public double gDouble() {
		return value;
	}

	public void goNext() {
		if(derived==null) return;

		previous = value;
		derived.goNext();
		value += derived.gDouble();
	}

	public void goBack() {
		if(derived==null) return;
		if(previous==null) return;

		derived.goBack();
		value = previous;
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
