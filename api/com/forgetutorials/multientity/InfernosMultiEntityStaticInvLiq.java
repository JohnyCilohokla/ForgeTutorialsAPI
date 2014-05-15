package com.forgetutorials.multientity;

import com.forgetutorials.lib.utilities.IFluidStackProxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class InfernosMultiEntityStaticInvLiq extends InfernosMultiEntityStatic implements ISidedInventory, IFluidHandler, IFluidStackProxy {

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

	@Override
	public FluidStack getFluid(ForgeDirection direction) {
		return getProxyEntity().getFluid(direction);
	}

	@Override
	public FluidStack getFluid(int i) {
		return getProxyEntity().getFluid(i);
	}

	@Override
	public void setFluid(int i, FluidStack fluid) {
		getProxyEntity().setFluid(i, fluid);
	}

	@Override
	public int getFluidsCount() {
		return getProxyEntity().getFluidsCount();
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return getProxyEntity().fill(from, resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		FluidStack tmpFluid = getFluid(from);
		if ((tmpFluid == null) || !tmpFluid.isFluidEqual(resource)) {
			return null;
		}
		return this.drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return getProxyEntity().drain(from, maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return getProxyEntity().canFill(from, fluid);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return getProxyEntity().canDrain(from, fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		FluidStack tmpFluid = getFluid(from);
		return new FluidTankInfo[] { new FluidTankInfo(tmpFluid, (tmpFluid != null ? tmpFluid.amount : 0)) };
	}

}
