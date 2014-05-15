package net.minecraft.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityXPOrb;

public class EntityHelper {

	static public void dropFewItems(EntityLiving living, boolean killedByPlayer, int looting, boolean dropXp) {
		if (living.isPlayer()) {
			return; // security (no fake player killing!)
		}
		living.dropFewItems(killedByPlayer, looting);
		living.dropRareDrop(looting);
		if (dropXp && !living.worldObj.isRemote && !living.isChild() && living.worldObj.getGameRules().getGameRuleBooleanValue("doMobLoot")) {
			int i = living.getExperiencePoints(living.attackingPlayer);

			while (i > 0) {
				int j = EntityXPOrb.getXPSplit(i);
				i -= j;
				living.worldObj.spawnEntityInWorld(new EntityXPOrb(living.worldObj, living.posX, living.posY, living.posZ, j));
			}
		}
	}
}
