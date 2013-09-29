package com.forgetutorials.lib.utilities;

import com.forgetutorials.lib.ModInfo;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

public class ForgeRegistryUtilities {
	public static void registerBlock(Block block, String unlocalizedName, String name) {
		block.setUnlocalizedName(ModInfo.MOD_ID + "." + unlocalizedName);
		GameRegistry.registerBlock(block, ModInfo.MOD_ID + "." + unlocalizedName);
		LanguageRegistry.addName(block, name);
	}

	public static void registerItem(Item item, String unlocalizedName, String name) {
		item.setUnlocalizedName(ModInfo.MOD_ID + "." + unlocalizedName);
		LanguageRegistry.addName(item, name);
	}
}
