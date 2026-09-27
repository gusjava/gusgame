package gus.game5.main.game.p2.c.card.magic.v1.card0;

import java.util.ArrayList;
import java.util.List;

import gus.game5.core.util.UtilList;
import gus.game5.main.game.p2.c.card.magic.v1.MagicPlayer;
import gus.game5.main.game.p2.c.card.magic.v1.alter.ability.creature.AbilityCreature;
import gus.game5.main.game.p2.c.card.magic.v1.alter.effect.creature.EffectCreature;
import gus.game5.main.game.p2.c.card.magic.v1.alter.enchant.creature.EnchantCreature;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypeCard;
import gus.game5.main.game.p2.c.card.magic.v1.enu.ETypePermanent;
import gus.game5.main.game.p2.c.card.magic.v1.i.permanent.IPermanentCreature;
import gus.game5.main.game.p2.c.card.magic.v1.i.spell.ISpellArtifact;
import gus.game5.main.game.p2.c.card.magic.v1.i.spell.ISpellCreature;

public abstract class Card0ArtifactCreature 
	extends Card0Permanent 
	implements 
	ISpellArtifact,
	ISpellCreature {

	public Card0ArtifactCreature(MagicPlayer owner, String imgKey) {
		super(owner, imgKey);
		
		creatureAbilities = new ArrayList<>();
		creatureEnchants = new ArrayList<>();
		creatureEffects = new ArrayList<>();
	}

	@Override
	public List<ETypeCard> getCardTypes() {
		return UtilList.asList(ETypeCard.SPELL_CREATURE, ETypeCard.SPELL_ARTIFACT);
	}

	@Override
	public List<ETypePermanent> getPermanentTypes() {
		return UtilList.asList(ETypePermanent.CREATURE, ETypePermanent.ARTIFACT);
	}
	
	/*
	 * SUMMONING SICKNESS
	 */

	@Override
	public boolean hasSummoningSicknessDefault() {
		return justEnteredBattleField();
	}
	
	/*
	 * CAN ATTACK
	 */
	
	@Override
	public boolean canAttackDefault() {
		return !hasSummoningSickness();
	}
	
	/*
	 * CAN BLOCK
	 */
	
	@Override
	public boolean canBlockDefault(IPermanentCreature card) {
		return card.canBeBlocked(this);
	}
	
	/*
	 * CAN BE BLOCKED
	 */

	@Override
	public boolean canBeBlockedDefault(IPermanentCreature card) {
		return true;
	}
	
	/*
	 * ABILITIES
	 */
	
	protected List<AbilityCreature> creatureAbilities;
	
	public List<AbilityCreature> getCreatureAbilities() {
		return creatureAbilities;
	}
	
	protected void addCreatureAbility(AbilityCreature ability) {
		creatureAbilities.add(ability);
	}
	
	/*
	 * ENCHANTS
	 */
	
	protected List<EnchantCreature> creatureEnchants;
	
	public List<EnchantCreature> getCreatureEnchants() {
		return creatureEnchants;
	}
	
	protected void addCreatureEnchant(EnchantCreature enchant) {
		creatureEnchants.add(enchant);
	}
	
	/*
	 * EFFECTS
	 */
	
	protected List<EffectCreature> creatureEffects;
	
	public List<EffectCreature> getCreatureEffects() {
		return creatureEffects;
	}
	
	protected void addCreatureEffect(EffectCreature effect) {
		creatureEffects.add(effect);
	}
	
	/*
	 * DAMAGES
	 */
	
	protected int damages = 0;
	
	public void setDamages(int damages)  {
		this.damages = damages;
	}
	
	@Override
	public int getDamages() {
		return damages;
	}
}
