package gus.game5.core.dyn;

import gus.game5.core.features.g.GInt;

public class DynGInt implements GInt, Dyn {

	private int value;
	private Integer previous;
	private DynGInt derived;
	
	public DynGInt() {
		this.value = 0;
	}
	
	public DynGInt(int val) {
		this.value = val;
	}
	
	public DynGInt(int val, DynGInt derived) {
		this.value = val;
		setDerived(derived);
	}
	
	public DynGInt(int val, int derived) {
		this.value = val;
		setDerived(derived);
	}
	
	public void setValue(int value) {
		this.value = value;
	}

	public int gInt() {
		return value;
	}

	public void goNext() {
		if(derived==null) return;

		previous = value;
		derived.goNext();
		value += derived.gInt();
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
	
	public DynGInt getDerived() {
		return derived;
	}
	
	public DynGInt initDerived() {
		if(derived==null) derived = new DynGInt();
		return derived;
	}
	
	public void setDerived(DynGInt derived) {
		this.derived = derived;
	}
	
	public void setDerived(int v) {
		this.derived = new DynGInt(v);
	}
}
