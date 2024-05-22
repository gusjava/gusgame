package gus.game5.core.dyn;

import gus.game5.core.features.g.GLong;

public class DynGLong implements GLong, Dyn {

	private long value;
	private Long previous;
	private DynGLong derived;
	
	public DynGLong() {
		this.value = 0;
	}
	
	public DynGLong(long val) {
		this.value = val;
	}
	
	public DynGLong(long val, DynGLong derived) {
		this.value = val;
		setDerived(derived);
	}
	
	public DynGLong(long val, long derived) {
		this.value = val;
		setDerived(derived);
	}
	
	public void setValue(long value) {
		this.value = value;
	}

	public long gLong() {
		return value;
	}

	public void goNext() {
		if(derived==null) return;

		previous = value;
		derived.goNext();
		value += derived.gLong();
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
	
	public DynGLong getDerived() {
		return derived;
	}
	
	public DynGLong initDerived() {
		if(derived==null) derived = new DynGLong();
		return derived;
	}
	
	public void setDerived(DynGLong derived) {
		this.derived = derived;
	}
	
	public void setDerived(long v) {
		this.derived = new DynGLong(v);
	}
}
