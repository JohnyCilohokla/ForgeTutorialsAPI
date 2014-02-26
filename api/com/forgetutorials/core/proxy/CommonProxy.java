package com.forgetutorials.core.proxy;

import com.forgetutorials.lib.FTA;
import com.forgetutorials.multientity.*;

import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void initizeRendering() {

	}

	public void registerTileEntities() {

		GameRegistry.registerTileEntity(InfernosMultiEntityStatic.class, "tile.MES.Static");
		GameRegistry.registerTileEntity(InfernosMultiEntityStaticInv.class, "tile.MES.Static.Inv");
		GameRegistry.registerTileEntity(InfernosMultiEntityStaticInvLiq.class, "tile.MES.Static.InvLiq");
		GameRegistry.registerTileEntity(InfernosMultiEntityStaticLiq.class, "tile.MES.Static.Liq");
		GameRegistry.registerTileEntity(InfernosMultiEntityDynamic.class, "tile.MES.Dynamic");
		GameRegistry.registerTileEntity(InfernosMultiEntityDynamicInv.class, "tile.MES.Dynamic.Inv");
		GameRegistry.registerTileEntity(InfernosMultiEntityDynamicInvLiq.class, "tile.MES.Dynamic.InvLiq");
		GameRegistry.registerTileEntity(InfernosMultiEntityDynamicLiq.class, "tile.MES.Dynamic.Liq");
	}

	public void initize() {
		FTA.packetHandler.initalise();
	}
}
