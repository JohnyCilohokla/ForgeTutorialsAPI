package com.forgetutorials.core.proxy;

import com.forgetutorials.multientity.InfernosMultiEntity;
import com.forgetutorials.multientity.InfernosMultiEntityInv;
import com.forgetutorials.multientity.InfernosMultiEntityInvLiq;
import com.forgetutorials.multientity.InfernosMultiEntityLiq;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void initizeRendering() {

	}

	public void registerTileEntities() {

		GameRegistry.registerTileEntity(InfernosMultiEntity.class, "tile.infernosMultiEntity");
		GameRegistry.registerTileEntity(InfernosMultiEntityInv.class, "tile.infernosMultiEntity.Inv");
		GameRegistry.registerTileEntity(InfernosMultiEntityInvLiq.class, "tile.infernosMultiEntity.InvLiq");
		GameRegistry.registerTileEntity(InfernosMultiEntityLiq.class, "tile.infernosMultiEntity.Liq");
	}
}
