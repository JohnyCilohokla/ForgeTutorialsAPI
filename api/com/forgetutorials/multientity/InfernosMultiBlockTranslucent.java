package com.forgetutorials.multientity;

import com.forgetutorials.lib.FTA;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.IBlockAccess;

public class InfernosMultiBlockTranslucent extends InfernosMultiBlock {

	public InfernosMultiBlockTranslucent(int par1, Material material) {
		super(par1, material);
		setBlockName("MES.Translucent");
		GameRegistry.registerBlock(this, InfernosMultiItemTranslucent.class, "MES.Translucent");
		LanguageRegistry.addName(this, "MES.Translucent");
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	Block getBlockType(){
		return FTA.infernosMultiBlockTranslucent;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}
}
