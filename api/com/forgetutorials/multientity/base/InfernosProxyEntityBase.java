package com.forgetutorials.multientity.base;

import java.util.ArrayList;

import com.forgetutorials.lib.network.PacketMultiTileEntity;
import com.forgetutorials.multientity.InfernosMultiEntity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class InfernosProxyEntityBase {
	public static final InfernosProxyEntityBase DUMMY = new InfernosProxyEntityBase(null);
	protected InfernosMultiEntity entity;

	public InfernosProxyEntityBase(InfernosMultiEntity entity) {
		this.entity = entity;
	}

	public boolean hasInventory() {
		return false;
	}

	public boolean hasLiquids() {
		return false;
	}

	public int getSizeInventory() {
		return 0;
	}

	public ItemStack getStackInSlot(int i) {
		return null;
	}

	public ItemStack decrStackSize(int i, int j) {
		return null;
	}

	public ItemStack getStackInSlotOnClosing(int i) {
		return null;
	}

	public void setInventorySlotContents(int i, ItemStack itemstack) {
	}

	public String getInvName() {
		return null;
	}

	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	public void closeChest() {

	}

	public void openChest() {

	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return false;
	}

	public int getInventoryStackLimit() {
		return 0;
	}

	public boolean isInvNameLocalized() {
		return false;
	}

	int[] nullArray = new int[] {};

	public int[] getAccessibleSlotsFromSide(int var1) {
		return this.nullArray;
	}

	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return false;
	}

	public void invalidate() {
		this.entity = null;
	}

	public FluidStack getFluid(int i) {
		return null;
	}

	public FluidStack getFluid(ForgeDirection direction) {
		return null;
	}

	public void setFluid(int i, FluidStack fluid) {

	}

	public int getFluidsCount() {
		return 0;
	}

	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return false;
	}

	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return false;
	}

	public void readFromNBT(NBTTagCompound tagCompound) {
	}

	public void writeToNBT(NBTTagCompound tagCompound) {
	}

	public void addToDescriptionPacket(PacketMultiTileEntity packet) {
	}

	public void onInventoryChanged() {
		if (!this.entity.worldObj.isRemote) {
			this.entity.worldObj.markBlockForUpdate(this.entity.xCoord, this.entity.yCoord, this.entity.zCoord);
		}
	}

	public void renderTileEntityAt(double x, double y, double z) {

	}

	public String getTypeName() {
		return "InternalError!";
	}

	public float getBlockHardness() {
		return 1;
	}

	public ItemStack getSilkTouchItemStack() {
		return null;
	}

	public ArrayList<ItemStack> getBlockDropped(int fortune) {
		ArrayList<ItemStack> droppedItems = new ArrayList<ItemStack>();
		return droppedItems;
	}

}
