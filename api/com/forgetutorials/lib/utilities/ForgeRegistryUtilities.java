package com.forgetutorials.lib.utilities;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ForgeRegistryUtilities {

	String prefix;
	String modID;

	public ForgeRegistryUtilities(String prefix, String modID) {
		this.prefix = prefix;
		this.modID = modID;
	}

	public void registerBlock(Block block, String unlocalizedName, String name) {
		block.setUnlocalizedName(this.prefix + "." + unlocalizedName);
		GameRegistry.registerBlock(block, this.prefix + "." + unlocalizedName);
		LanguageRegistry.addName(block, name);
	}

	public void registerItem(Item item, String unlocalizedName, String name) {
		item.setUnlocalizedName(this.prefix + "." + unlocalizedName);
		LanguageRegistry.addName(item, name);
		GameRegistry.registerItem(item, item.getUnlocalizedName(), this.modID);
	}
}
