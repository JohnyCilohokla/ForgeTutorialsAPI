package com.forgetutorials.lib.registry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class DescriptorBlock extends DescriptorItem {

	Block block;

	public int harvestLevel;
	public String harvestTool = null;

	@Override
	public ObjectDescriptorType getType() {
		return ObjectDescriptorType.BLOCK;
	}

	@Override
	protected void register(String unlocalizedName, String name, ItemStack itemStack) {
		super.register(unlocalizedName, name, itemStack);
		this.block = Block.blocksList[itemStack.itemID];
		if (this.harvestTool != null) {
			MinecraftForge.setBlockHarvestLevel(this.block, this.itemStack.getItemDamage(), this.harvestTool, this.harvestLevel);
		}
		System.out.println(">>Registery(FTA)<< Register Block: " + this.block.getClass().getCanonicalName() + " [" + this.itemStack.getItemDamage()
				+ "] + tool(" + this.harvestTool + "@" + this.harvestLevel + ")");
	}

	public DescriptorBlock registerBlock(String unlocalizedName, String name, ItemStack itemStack) {
		register(unlocalizedName, name, itemStack);
		return this;
	}

	public Block getBlock() {
		return this.block;
	}

	public DescriptorBlock setTool(String harvestTool, int harvestLevel) {
		this.harvestTool = harvestTool;
		this.harvestLevel = harvestLevel;
		return this;
	}
}
