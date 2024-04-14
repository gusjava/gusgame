package gus.game5.main.anim.life1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import gus.game5.core.features.g.GDouble;
import gus.game5.core.features.g.GInt;
import gus.game5.core.features.p.P;
import gus.game5.core.game.Game;
import gus.game5.core.util.UtilRandom;

public class BacteriaType {
	
	private Game game;
	
	public BacteriaType(Game game) {
		this.game = game;
	}
	
	/*
	 * GENERATE NEW
	 */
	
	public List<Bacteria> generateNew() {
		List<Bacteria> list = new ArrayList<>();
		for(int i=0;i<initNb;i++) {
			Bacteria bacteria = new Bacteria(UtilRandom.randomPoint1(game), this);
			list.add(bacteria);
		}
		nbBirth += list.size();
		return list;
	}
	
	/*
	 * GENERATE SPAWN
	 */
	
	public Bacteria generateSpawn(Bacteria parent) {
		double parentEnergy = parent.getEnergy();
		if(parentEnergy<getSpawningMinimalEnergy()) return null;
		if(!UtilRandom.chance(getChanceSpawning())) return null;
		
		Bacteria spawn = new Bacteria(parent.getAnchor().p1(), this);
		spawn.setEnergy(parentEnergy/2);
		parent.setEnergy(parentEnergy/2);
		nbBirth ++;
		
		return spawn;
	}
	
	/*
	 * HANDLE DEATH
	 */
	
	public void handleDeath(Bacteria bacteria) {
		nbDeath++;
	}
	
	/*
	 * BEFORE GO NEXT
	 */
	
	private List<P<Bacteria>> beforeList = new ArrayList<>();
	
	public void addBefore(P<Bacteria> before) {
		beforeList.add(before);
	}
	
	public void beforeGoNext(Bacteria bacteria) {
		beforeList.forEach(p->p.p(bacteria));
	}
	
	/*
	 * AFTER GO NEXT
	 */
	
	private List<P<Bacteria>> afterList = new ArrayList<>();
	
	public void addAfter(P<Bacteria> after) {
		afterList.add(after);
	}
	
	public void afterGoNext(Bacteria bacteria) {
		afterList.forEach(p->p.p(bacteria));
	}
	
	/*
	 * NAME
	 */
	
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	/*
	 * INIT NB
	 */
	
	private int initNb = 10;
	
	public int getInitNb() {
		return initNb;
	}
	
	public void setInitNb(int initNb) {
		this.initNb = initNb;
	}
	
	/*
	 * COLOR
	 */
	
	private Color color;
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	/*
	 * INIT SPEED
	 */
	
	private GDouble gInitSpeed = ()->2;
	
	public double getInitSpeed() {
		return gInitSpeed.gDouble();
	}
	
	public void setGInitSpeed(GDouble gInitSpeed) {
		this.gInitSpeed = gInitSpeed;
	}
	
	public void setInitSpeed(double initSpeed) {
		setGInitSpeed(()->initSpeed);
	}
	
	/*
	 * MAX ENERGY
	 */
	
	private GDouble gMaxEnergy = ()->100;
	
	public double getMaxEnergy() {
		return gMaxEnergy.gDouble();
	}
	
	public void setGMaxEnergy(GDouble gMaxEnergy) {
		this.gMaxEnergy = gMaxEnergy;
	}
	
	public void setMaxEnergy(double maxEnergy) {
		setGMaxEnergy(()->maxEnergy);
	}
	
	/*
	 * LOSS ENERGY
	 */
	
	private GDouble gLossEnergy = ()->0.5;
	
	public double getLossEnergy() {
		return gLossEnergy.gDouble();
	}
	
	public void setGLossEnergy(GDouble gLossEnergy) {
		this.gLossEnergy = gLossEnergy;
	}
	
	public void setLossEnergy(double lossEnergy) {
		setGLossEnergy(()->lossEnergy);
	}
	
	/*
	 * RADIUS
	 */
	
	private GDouble gRadius = ()->10;
	
	public double getRadius() {
		return gRadius.gDouble();
	}
	
	public void setGRadius(GDouble gRadius) {
		this.gRadius = gRadius;
	}
	
	public void setRadius(double radius) {
		setGRadius(()->radius);
	}
	
	/*
	 * CHANCE NEW DIRECTION
	 */
	
	private GInt gChanceNewDirection = ()->0;
	
	public int getChanceNewDirection() {
		return gChanceNewDirection.gInt();
	}
	
	public void setGChanceNewDirection(GInt gChanceNewDirection) {
		this.gChanceNewDirection = gChanceNewDirection;
	}
	
	public void setChanceNewDirection(int chanceNewDirection) {
		setGChanceNewDirection(()->chanceNewDirection);
	}
	
	/*
	 * CHANGE DIRECTION
	 */
	
	public void handleChangeDirection(Bacteria bacteria) {
		int c = getChanceNewDirection();
		if(c==0) return;
		
		if(UtilRandom.chance(c)) {
			bacteria.changeDirection();
		}
	}
	
	/*
	 * SPAWNING MINIMAL ENERGY
	 */
	
	private GDouble gSpawningMinimalEnergy = ()->50;
	
	public double getSpawningMinimalEnergy() {
		return gSpawningMinimalEnergy.gDouble();
	}
	
	public void setGSpawningMinimalEnergy(GDouble gSpawningMinimalEnergy) {
		this.gSpawningMinimalEnergy = gSpawningMinimalEnergy;
	}
	
	public void setSpawningMinimalEnergy(double spawningMinimalEnergy) {
		setGSpawningMinimalEnergy(()->spawningMinimalEnergy);
	}
	
	/*
	 * CHANCE SPAWNING
	 */
	
	private GInt gChanceSpawning = ()->50;
	
	public int getChanceSpawning() {
		return gChanceSpawning.gInt();
	}
	
	public void setGChanceSpawning(GInt gChanceSpawning) {
		this.gChanceSpawning = gChanceSpawning;
	}
	
	public void setChanceSpawning(int chanceSpawning) {
		setGChanceSpawning(()->chanceSpawning);
	}

	/*
	 * NB BIRTH
	 */

	private int nbBirth = 0;
	
	public int getNbBirth() {
		return nbBirth;
	}
	
	/*
	 * NB DEATH
	 */
	
	private int nbDeath = 0;
	
	public int getNbDeath() {
		return nbDeath;
	}
	
	/*
	 * NB LIVING
	 */
	
	public int getNbLiving() {
		return nbBirth - nbDeath;
	}
	
	
	/*
	 * DESCRIPTION
	 */
	
	public String getDescription() {
		return name + " : [" + nbBirth + ", " + nbDeath+ ", " + getNbLiving() + "]";
	}
}
