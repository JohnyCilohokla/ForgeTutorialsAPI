package com.forgetutorials.lib.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DescriptorItem extends DescriptorObject {
	Item item;
	int meta;
	ItemStack itemStack;

	@Override
	public ObjectDescriptorType getType() {
		return ObjectDescriptorType.ITEM;
	}

	void register(String unlocalizedName, String name, ItemStack itemStack) {
		super.register(unlocalizedName, name);
		this.itemStack = itemStack;
		this.item = this.itemStack.getItem();
		this.meta = this.itemStack.getItemDamage();
		System.out.println(">>Registery(FTA)<< Register Item: " + this.itemStack.getClass().getCanonicalName() + " ["
				+ this.itemStack.getItem().getUnlocalizedName(itemStack) + "]");
	}

	public ItemStack getItemStack() {
		return this.itemStack.copy();
	}

	public ItemStack getItemStack(int stackSize) {
		ItemStack stack = this.itemStack.copy();
		stack.stackSize = stackSize;
		return stack;
	}

}
