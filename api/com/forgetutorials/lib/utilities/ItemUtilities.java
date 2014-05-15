package com.forgetutorials.lib.utilities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class ItemUtilities {

	public static void damageItemOrDestroy(ItemStack itemStack, int damage, EntityLivingBase entity, World world, int x, int y, int z, ItemStack onDestroyStack) {
		if ((onDestroyStack == null) || (((itemStack.getMaxDamage() - itemStack.getItemDamage()) + 1) > (damage))) {
			itemStack.damageItem((damage), entity);
		} else {
			// destroy item!!!
			itemStack.damageItem(damage * 100, entity);
			// !client
			if (!world.isRemote) {
				EntityItem entityitem = new EntityItem(world, x, y, z, onDestroyStack);
				world.spawnEntityInWorld(entityitem);
			}
		}
	}

	public static void damageItemOrDestroy(ItemStack itemStack, int damage, EntityLivingBase entity, World world, int x, int y, int z, Item onDestroyItem,
			int itemCount, int itemMeta) {
		damageItemOrDestroy(itemStack, damage, entity, world, x, y, z, onDestroyItem!=null?new ItemStack(onDestroyItem, itemCount, itemMeta):null);
	}

	public static void damageItemOrDestroy(ItemStack itemStack, int damage, EntityLivingBase entity, World world, int x, int y, int z, Item onDestroyItem) {
		ItemUtilities.damageItemOrDestroy(itemStack, damage, entity, world, x, y, z, onDestroyItem, 1, 0);
	}

	public static ItemStack replaceSingleItemOrDropAndReturn(World world, EntityPlayer player, ItemStack stack, ItemStack replaced) {
		if (stack.stackSize > 1) {
			if (!world.isRemote) {
				EntityItem entityitem = new EntityItem(world, player.posX, player.posY - 1.0D, player.posZ, replaced);
				world.spawnEntityInWorld(entityitem);
				if (!(player instanceof FakePlayer)) {
					entityitem.onCollideWithPlayer(player);
				}
			}
			stack.splitStack(1);
			return stack;
		} else {
			return replaced;
		}
	}

	public static ItemStack useSingleItemOrDropAndReturn(World world, EntityPlayer player, ItemStack stack) {
		if (stack.getItem().hasContainerItem(stack)) {
			ItemStack replaced = stack.getItem().getContainerItem(stack);
			return ItemUtilities.replaceSingleItemOrDropAndReturn(world, player, stack, replaced);
		} else {
			if (stack.stackSize > 1) {
				stack.splitStack(1);
				return stack;
			} else {
				return null;
			}
		}
	}

	public static void dropItem(World world, double posX, double posY, double posZ, ItemStack itemStack) {
		if (!world.isRemote) {
			EntityItem entityitem = new EntityItem(world, posX, posY, posZ, itemStack);
			world.spawnEntityInWorld(entityitem);
		}
	}

	public static boolean areItemStacksEqualItem(ItemStack stack1, ItemStack stack2) {
		return stack1.getItem().equals(stack2.getItem()) ? false : (stack1.getItemDamage() != stack2.getItemDamage() ? false : (stack1.stackSize > stack1
				.getMaxStackSize() ? false : ItemStack.areItemStackTagsEqual(stack1, stack2)));
	}
}
