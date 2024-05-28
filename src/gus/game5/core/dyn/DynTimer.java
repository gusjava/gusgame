package gus.game5.core.dyn;

import gus.game5.core.features.g.GBool;

public class DynTimer implements Dyn, GBool, Runnable {

	private int count = 0;
	private Runnable r;
	
	public DynTimer() {
		
	}
	
	public DynTimer(Runnable r) {
		this.r = r;
	}
	
	public void setRunnable(Runnable r) {
		this.r = r;
	}
	
	/*
	 * START
	 */
	
	public void start(int count) {
		this.count = count;
		r = null;
	}
	
	public void start(int count, Runnable r) {
		this.count = count;
		this.r = r;
	}
	
	public boolean gBool() {
		return count>0;
	}

	public void goNext() {
		if(count>0) {
			count--;
			if(count==0) run();
		}
	}

	public void goBack() {
		count++;
	}
	
	public void run() {
		if(r!=null) r.run();
	}
}
