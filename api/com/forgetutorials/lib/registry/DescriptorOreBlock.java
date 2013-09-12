package com.forgetutorials.lib.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class DescriptorOreBlock extends DescriptorBlock {
	// public String name;
	public String oreDictionaryName;
	// public ItemStack oreStack;
	public int oreID;
	public int oreMeta;

	@Override
	public ObjectDescriptorType getType() {
		return ObjectDescriptorType.ORE;
	}

	@Override
	protected void register(String unlocalizedName, String name, ItemStack itemStack) {
		super.register(unlocalizedName, name, itemStack);
	}

	public DescriptorBlock registerOreBlock(String unlocalizedName, String oreDictionaryName, String name, ItemStack itemStack) {
		register(unlocalizedName, name, itemStack);
		this.oreDictionaryName = oreDictionaryName;
		this.oreID = this.itemStack.itemID;
		this.oreMeta = this.itemStack.getItemDamage();
		return this;
	}

	public DescriptorOreBlock registerInOreDictionary() {
		OreDictionary.registerOre(this.oreDictionaryName, this.itemStack);
		return this;
	}

	@Override
	public DescriptorOreBlock setTool(String harvestTool, int harvestLevel) {
		this.harvestTool = harvestTool;
		this.harvestLevel = harvestLevel;
		return this;
	}
}
