package gus.game5.core.game;

public class Time {

	private long sleep = 0;
	private long count = 0;
	private long startTime = 0;
	private long stepDuration = 0;
	
	private long stepStart = 0;
	private long stepEnd = 0;

	private long step10Start = 0;
	private double step10Duration = 0;
	
	/*
	 * START STEP / END STEP
	 */
	
	public void startStep() {
		stepStart = System.currentTimeMillis();
		if(count%10==0) step10Start = stepStart;
	}
	
	public void endStep() {
		stepEnd = System.currentTimeMillis();
		stepDuration = stepEnd - stepStart;
		count++;
		
		if(count%10==0) {
			step10Duration = (stepEnd - step10Start)*0.1;
		}
		
		try { Thread.sleep(sleep); }
		catch (InterruptedException e) {}
	}
	
	public void initSleep(long sleep) {
		this.sleep = sleep;
	}
	
	public void reset() {
		count = 0;
		startTime = System.currentTimeMillis();
	}
	
	/*
	 * GET
	 */
	
	public long getSleep() {
		return sleep;
	}

	public long getCount() {
		return count;
	}
	
	public long getStartTime() {
		return startTime;
	}
	
	public long getStepDuration() {
		return stepDuration;
	}
	
	public double getStep10Duration() {
		return step10Duration;
	}
}
