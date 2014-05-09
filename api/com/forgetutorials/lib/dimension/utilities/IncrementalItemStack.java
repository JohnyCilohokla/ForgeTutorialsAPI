package com.forgetutorials.lib.dimension.utilities;

import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class IncrementalItemStack extends IncrementalObject {
	public ItemStack stack;

	public IncrementalItemStack(ItemStack stack, long start) {
		super(start);
		this.stack = stack;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + stack.getItemDamage();
		result = prime * result + stack.getItem().getUnlocalizedName(stack).hashCode();
		result = prime * result + (stack.getTagCompound() != null ? stack.getTagCompound()/*.toString()*/.hashCode() : 0);
		return result;
	}

	public boolean areItemStacksEqual(ItemStack stack1, ItemStack stack2) {
		return ((!stack1.getUnlocalizedName().equals(stack2.getUnlocalizedName()) ? false : (stack1.getItemDamage() != stack2.getItemDamage() ? false
				: (stack1.stackTagCompound == null && stack2.stackTagCompound != null ? false : stack1.stackTagCompound == null
						|| stack1.stackTagCompound.equals(stack2.stackTagCompound)))));
	}

	@Override
	public boolean equals(Object obj) {
		IncrementalItemStack other = (IncrementalItemStack) obj;
		if (!areItemStacksEqual(stack, other.stack))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return StatCollector.translateToLocal(stack.getUnlocalizedName());
	}
}
