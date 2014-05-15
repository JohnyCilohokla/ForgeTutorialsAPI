package com.forgetutorials.lib.registry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

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
		Item item = itemStack.getItem();
		if (item instanceof ItemBlock) {
			this.block = ((ItemBlock) item).field_150939_a;
			if (this.harvestTool != null) {
				// TODO FIXME

				// MinecraftForge.setBlockHarvestLevel(this.block, this.itemStack.getItemDamage(), this.harvestTool, this.harvestLevel);
			}
			System.out.println(">>Registery(FTA)<< Register Block: " + this.block.getClass().getCanonicalName() + " [" + this.itemStack.getItemDamage()
					+ "] + tool(" + this.harvestTool + "@" + this.harvestLevel + ")");
		}
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
