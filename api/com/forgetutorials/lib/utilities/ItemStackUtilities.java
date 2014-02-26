package com.forgetutorials.lib.utilities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemStackUtilities {
	public static void addStringTag(ItemStack stack, String key, String value) {
		if (stack.stackTagCompound == null) {
			stack.setTagCompound(new NBTTagCompound());
		}

		stack.stackTagCompound.setString(key, value);
	}
}
