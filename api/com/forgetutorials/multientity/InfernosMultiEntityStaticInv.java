package com.forgetutorials.multientity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public class InfernosMultiEntityStaticInv extends InfernosMultiEntityStatic implements ISidedInventory {

	@Override
	public int getSizeInventory() {

		return getProxyEntity().getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return getProxyEntity().getStackInSlot(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		return getProxyEntity().decrStackSize(i, j);
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return getProxyEntity().getStackInSlotOnClosing(i);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		getProxyEntity().setInventorySlotContents(i, itemstack);
	}

	@Override
	public String getInventoryName() {
		return getProxyEntity().getInventoryName();
	}

	@Override
	public int getInventoryStackLimit() {
		return getProxyEntity().getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return getProxyEntity().isUseableByPlayer(entityplayer);
	}

	@Override
	public void openInventory() {
		getProxyEntity().openInventory();
	}

	@Override
	public void closeInventory() {
		getProxyEntity().closeInventory();
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return getProxyEntity().isItemValidForSlot(i, itemstack);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		return getProxyEntity().getAccessibleSlotsFromSide(var1);
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return getProxyEntity().canInsertItem(i, itemstack, j);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return getProxyEntity().canExtractItem(i, itemstack, j);
	}

	@Override
	public void markDirty() {
		super.markDirty();
		getProxyEntity().onInventoryChanged();
	}

	@Override
	public boolean hasCustomInventoryName() {
		return getProxyEntity().hasCustomInventoryName();
	}
}
