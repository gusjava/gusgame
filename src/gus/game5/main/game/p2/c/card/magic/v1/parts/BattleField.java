package gus.game5.main.game.p2.c.card.magic.v1.parts;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeLand;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanent;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanentLand;

public class BattleField {
	
	private List<IPermanent> permanents;
	
	public BattleField() {
		permanents = new ArrayList<>();
	}
	
	public void add(IPermanent permanent) {
		permanents.add(permanent);
	}
	
	/*
	 * LANDS
	 */
	
	public List<IPermanent> getLands() {
		return UtilList.findAll(permanents, IPermanent::ofPermanentTypeLand);
	}
	
	/*
	 * COUNT LANDS
	 */
	
	public int countLands(ETypeLand typeLand) {
		int count = 0;
		for(IPermanent card : getLands()) {
			if(((IPermanentLand) card).ofLandType(typeLand)) count++;
		}
		return count;
	}
	
	public int countLandsSwamp() {
		return countLands(ETypeLand.SWAMP);
	}
	
	/*
	 * HAS LAND
	 */
	
	public boolean hasLand(ETypeLand typeLand) {
		for(IPermanent card : getLands()) {
			if(((IPermanentLand) card).ofLandType(typeLand)) return true;
		}
		return false;
	}
	
	public boolean hasLandForest() {
		return hasLand(ETypeLand.FOREST);
	}
}
