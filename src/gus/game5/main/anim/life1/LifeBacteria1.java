package gus.game5.main.anim.life1;

import java.awt.Color;
import java.util.List;

import gus.game5.core.features.p.P;
import gus.game5.core.util.UtilList;
import gus.game5.core.util.UtilRandom;

public class LifeBacteria1 extends LifeBacteria0 {
	
	public static void main(String[] args) {
		LifeBacteria1 main = new LifeBacteria1();
		main.displayInWindows();
		main.start();
	}

	@SuppressWarnings("unchecked")
	protected List<P<BacteriaType>> typeCustList() {
		return UtilList.asList(
			this::cust1,
			this::cust2,
			this::cust3,
			this::cust4
		);
	}
	
	private void cust1(BacteriaType type) {
		type.setRadius(12);
		type.setMaxEnergy(120);
		type.setLossEnergy(0.009);
		type.setInitSpeed(0.8);
		type.setSpawningMinimalEnergy(65);
		type.setChanceSpawning(400);
		type.setChanceNewDirection(700);
		type.setColor(Color.CYAN);
		type.setName("Phylosis");
		type.setInitNb(1);
		
	}
	
	private void cust2(BacteriaType type) {
		type.setRadius(10);
		type.setMaxEnergy(100);
		type.setLossEnergy(0.02);
		type.setInitSpeed(2);
		type.setSpawningMinimalEnergy(55);
		type.setChanceSpawning(290);
		type.setChanceNewDirection(150);
		type.setColor(Color.RED);
		type.setName("Clorodris");
		type.setInitNb(1);
	}
	
	private void cust3(BacteriaType type) {
		type.setRadius(19);
		type.setMaxEnergy(150);
		type.setLossEnergy(0.008);
		type.setInitSpeed(1);
		type.setSpawningMinimalEnergy(50);
		type.setChanceSpawning(700);
		type.setChanceNewDirection(15);
		type.setColor(Color.GREEN);
		type.setName("Demogris");
		type.setInitNb(1);
	}
	
	private void cust4(BacteriaType type) {
		type.setRadius(3);
		type.setMaxEnergy(200);
		type.setLossEnergy(0.0045);
		type.setGInitSpeed(()->UtilRandom.randomDouble(0.5,2.5));
		type.setSpawningMinimalEnergy(60);
		type.setChanceSpawning(100);
		type.setChanceNewDirection(1000);
		type.setColor(Color.ORANGE);
		type.setName("Kabatris");
		type.setInitNb(1);
	}
}
