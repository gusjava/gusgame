package gus.game5.main.game.p2.c.card.magic.v1.i.permanent;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.features.f.F;
import gus.game5.core.util.UtilBoolean;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreature;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureDeathTouch;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureDefender;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureFirstStrike;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureFlying;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureIndestructible;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureLifeLink;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureMenace;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureReach;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureTrample;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreatureVigilance;
import gus.game5.main.game.p2.c.card.magic.v1.alter.effect.creature.EffectCreature;
import gus.game5.main.game.p2.c.card.magic.v1.alter.enchant.creature.EnchantCreature;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCreature;

public interface IPermanentCreature extends IPermanent {
	
	public List<ETypeCreature> getCreatureTypes();
	public List<AbilityCreature> getCreatureAbilities();
	public List<EnchantCreature> getCreatureEnchants();
	public List<EffectCreature> getCreatureEffects();
	
	/*
	 * DAMAGES
	 */
	
	public void setDamages(int damages);
	public int getDamages();
	
	public default void addDamages(int n) {
		int newDamages = getDamages()+n;
		setDamages(newDamages);
	}
	
	public default void removeDamages(int n) {
		int newDamages = getDamages()-n;
		if(newDamages<0) newDamages = 0;
		setDamages(newDamages);
	}
	
	/*
	 * FORCE
	 */
	
	public int getForceDefault();
	
	public default int getForce() {
		//TODO altered by effects and enchantments
		return getForceDefault();
	}
	
	/*
	 * TOUCHNESS
	 */
	
	public int getTouchnessDefault();
	
	public default int getTouchness() {
		//TODO altered by effects and enchantments
		return getTouchnessDefault()-getDamages();
	}
	
	/*
	 * SUMMONING SICKNESS
	 */
	
	public boolean hasSummoningSicknessDefault();

	public default boolean hasSummoningSickness() {
		List<Boolean> interrupts = new ArrayList<>();
		for(AbilityCreature ability : getCreatureAbilities()) {
			Boolean interrupt = ability.interruptForSummoningSickness();
			if(interrupt!=null) interrupts.add(interrupt);
		}
		if(interrupts.size()>0) {
			if(UtilBoolean.allTrue(interrupts)) return hasSummoningSicknessDefault();
			if(UtilBoolean.allFalse(interrupts)) return false;
			throw new RuntimeException("Inconsistent abilities found inside creature for summoning sickness check");
		}
		return hasSummoningSicknessDefault();
	}
	
	/*
	 * CAN ATTACK
	 */
	
	public boolean canAttackDefault();
	
	public default boolean canAttack() {
		List<Boolean> interrupts = new ArrayList<>();
		for(AbilityCreature ability : getCreatureAbilities()) {
			Boolean interrupt = ability.interruptForCanAttack();
			if(interrupt!=null) interrupts.add(interrupt);
		}
		if(interrupts.size()>0) {
			if(UtilBoolean.allTrue(interrupts)) return canAttackDefault();
			if(UtilBoolean.allFalse(interrupts)) return false;
			throw new RuntimeException("Inconsistent abilities found inside creature for canAttack check");
		}
		return canAttackDefault();
	}
	
	/*
	 * CAN BLOCK
	 */
	
	public boolean canBlockDefault(IPermanentCreature card);
	
	public default boolean canBlock(IPermanentCreature card) {
		List<Boolean> interrupts = new ArrayList<>();
		for(AbilityCreature ability : getCreatureAbilities()) {
			F<IPermanentCreature> f = ability.interruptForCanBeBlocked();
			if(f!=null) interrupts.add(f.f(card));
		}
		if(interrupts.size()>0) {
			if(UtilBoolean.allTrue(interrupts)) return canBlockDefault(card);
			if(UtilBoolean.allFalse(interrupts)) return false;
			throw new RuntimeException("Inconsistent abilities found inside creature for canBeBlocked check");
		}
		return canBlockDefault(card);
	}
	
	/*
	 * CAN BE BLOCKED
	 */
	
	public boolean canBeBlockedDefault(IPermanentCreature card);

	public default boolean canBeBlocked(IPermanentCreature card) {
		List<Boolean> interrupts = new ArrayList<>();
		for(AbilityCreature ability : getCreatureAbilities()) {
			F<IPermanentCreature> f = ability.interruptForCanBeBlocked();
			if(f!=null) interrupts.add(f.f(card));
		}
		if(interrupts.size()>0) {
			if(UtilBoolean.allTrue(interrupts)) return canBeBlockedDefault(card);
			if(UtilBoolean.allFalse(interrupts)) return false;
			throw new RuntimeException("Inconsistent abilities found inside creature for canBeBlocked check");
		}
		return canBeBlockedDefault(card);
	}
	

	/*
	 * CREATURE ABILITIES
	 */
	
	public default boolean hasCreatureAbility(Class<? extends AbilityCreature> abilityClass) {
		for(AbilityCreature ability : getCreatureAbilities()) {
			if(ability.getClass().equals(abilityClass)) return true;
		}
		return false;
	}
	
	public default boolean hasCreatureAbilityDeathTouch() {
		return hasCreatureAbility(AbilityCreatureDeathTouch.class);
	}
	
	public default boolean hasCreatureAbilityDefender() {
		return hasCreatureAbility(AbilityCreatureDefender.class);
	}
	
	public default boolean hasCreatureAbilityFirstStrike() {
		return hasCreatureAbility(AbilityCreatureFirstStrike.class);
	}
	
	public default boolean hasCreatureAbilityFlying() {
		return hasCreatureAbility(AbilityCreatureFlying.class);
	}
	
	public default boolean hasCreatureAbilityIndestructible() {
		return hasCreatureAbility(AbilityCreatureIndestructible.class);
	}
	
	public default boolean hasCreatureAbilityLifeLink() {
		return hasCreatureAbility(AbilityCreatureLifeLink.class);
	}
	
	public default boolean hasCreatureAbilityMenace() {
		return hasCreatureAbility(AbilityCreatureMenace.class);
	}
	
	public default boolean hasCreatureAbilityReach() {
		return hasCreatureAbility(AbilityCreatureReach.class);
	}
	
	public default boolean hasCreatureAbilityTrample() {
		return hasCreatureAbility(AbilityCreatureTrample.class);
	}
	
	public default boolean hasCreatureAbilityVigilance() {
		return hasCreatureAbility(AbilityCreatureVigilance.class);
	}
}
