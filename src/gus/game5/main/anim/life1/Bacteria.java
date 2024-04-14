package gus.game5.main.anim.life1;

import gus.game5.core.angle.Angle;
import gus.game5.core.point.point0.Point0;
import gus.game5.core.point.point1.Point1;
import gus.game5.core.shape.ShapeRound;
import gus.game5.core.util.UtilDisplay;

public class Bacteria extends ShapeRound {
	
	public Bacteria(Point0 anchor, BacteriaType type) {
		super(anchor, type.getRadius());
		setColor(type.getColor());
		getAnchor().initDerived().setXY(type.getInitSpeed(), Angle.random());
		
		this.type = type;
		maxEnergy = type.getMaxEnergy();
		lossEnergy = type.getLossEnergy();
		energy = maxEnergy;
	}
	
	/*
	 * ENERGY
	 */
	
	private double energy;
	
	public double getEnergy() {
		return energy;
	}
	
	public void setEnergy(double energy) {
		this.energy = energy;
	}
	
	/*
	 * DEAD
	 */
	
	private boolean dead = false;
	
	/*
	 * OVER
	 */
	
	public boolean isOver() {
		return dead;
	}
	
	/*
	 * MAX ENERGY
	 */
	
	private double maxEnergy;
	
	public double getMaxEnergy() {
		return maxEnergy;
	}
	
	public void setMaxEnergy(double maxEnergy) {
		this.maxEnergy = maxEnergy;
	}
	
	/*
	 * LOSS ENERGY
	 */
	
	private double lossEnergy;
	
	public double getLossEnergy() {
		return lossEnergy;
	}
	
	public void setLossEnergy(double lossEnergy) {
		this.lossEnergy = lossEnergy;
	}

	
	/*
	 * TYPE
	 */

	private BacteriaType type;
	
	public BacteriaType getType() {
		return type;
	}
	
	/*
	 * DRAW SHAPE
	 */
	
	protected void drawShape() {
		double r = getRadius();
		drawStarC(r, 12);
		drawString(new Point1(r+5,0), UtilDisplay.dec2(energy));
	}
	
	/*
	 * GO NEXT
	 */
	
	public void goNext() {
		type.handleChangeDirection(this);
		type.beforeGoNext(this);
		super.goNext();
		type.afterGoNext(this);
		loseEnergy(lossEnergy);
	}
	
	/*
	 * GAIN / LOSE ENERGY
	 */
	
	public void gainEnergy(double gain) {
		if(gain<=0) return;
		energy += gain;
		if(energy>maxEnergy) energy = maxEnergy;
	}
	
	public void loseEnergy(double loss) {
		if(loss<=0) return;
		energy-=loss;
		if(energy<=0) {
			energy = 0;
			dead = true;
			type.handleDeath(this);
		}
	}
	
	/*
	 * CHANGE DIRECTION
	 */
	
	public void changeDirection() {
		getAnchor().initDerived().setAngle(Angle.random());
	}
	
	/*
	 * GENERATE SPAWN
	 */
	
	public Bacteria generateSpawn() {
		return type.generateSpawn(this);
	}
}
