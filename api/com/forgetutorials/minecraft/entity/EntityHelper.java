package com.forgetutorials.minecraft.entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityXPOrb;

public class EntityHelper {
	
	private static Method methodIsPlayer = null;
	
	static private void initize(){
        ClassLoader cl = EntityHelper.class.getClassLoader();
        try {
			Field f_ucp = URLClassLoader.class.getDeclaredField("ucp");
	        Class<?> URLClassPath = Class.forName("sun.misc.URLClassPath");
	        Field f_loaders = URLClassPath.getDeclaredField("loaders");
	        Field f_lmap = URLClassPath.getDeclaredField("lmap");
	        Method methodIsPlayer = URLClassPath.getMethod("isPlayer", ClassLoader.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
