package com.forgetutorials.multientity;

import com.forgetutorials.lib.FTA;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class InfernosMultiBlockOpaque extends InfernosMultiBlock {

	public InfernosMultiBlockOpaque(int par1, Material material) {
		super(par1, material);
		setBlockName("MES.Opaque");
		GameRegistry.registerBlock(this, InfernosMultiItemOpaque.class, "MES.Opaque");
		LanguageRegistry.addName(this, "MES.Opaque");
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	Block getBlockType(){
		return FTA.infernosMultiBlockOpaque;
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}
}
