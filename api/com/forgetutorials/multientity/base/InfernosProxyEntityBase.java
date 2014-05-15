package com.forgetutorials.multientity.base;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

import com.forgetutorials.lib.FTA;
import com.forgetutorials.lib.network.PacketMultiTileEntity;
import com.forgetutorials.lib.utilities.ItemStackUtilities;
import com.forgetutorials.multientity.InfernosMultiBlock;
import com.forgetutorials.multientity.InfernosMultiEntityStatic;
import com.forgetutorials.multientity.InfernosMultiEntityStaticInv;
import com.forgetutorials.multientity.InfernosMultiEntityStaticInvLiq;
import com.forgetutorials.multientity.InfernosMultiEntityStaticLiq;
import com.forgetutorials.multientity.InfernosMultiEntityType;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

abstract public class InfernosProxyEntityBase {
	public static final InfernosProxyEntityDummy DUMMY = new InfernosProxyEntityDummy(null);
	protected InfernosMultiEntityStatic entity;

	public InfernosProxyEntityBase(InfernosMultiEntityStatic entity) {
		this.entity = entity;
	}

	abstract public boolean hasInventory();

	abstract public boolean hasLiquids();

	abstract public boolean isDynamiclyRendered();

	abstract public boolean isOpaque();

	public InfernosMultiBlock getBlock() {
		return isOpaque() ? FTA.infernosMultiBlockOpaque : FTA.infernosMultiBlockTranslucent;
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

	public String getInventoryName() {
		return "multientity." + getTypeName();
	}

	public String getItemStackDisplayName(ItemStack itemStack) {
		return itemStack.getTagCompound().toString();
	}

	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return false;
	}

	public void closeInventory() {

	}

	public void openInventory() {

	}

	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return false;
	}

	public int getInventoryStackLimit() {
		return 0;
	}

	public boolean hasCustomInventoryName() {
		return true;
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
		if (!this.entity.getWorldObj().isRemote) {
			this.entity.getWorldObj().markBlockForUpdate(this.entity.xCoord, this.entity.yCoord, this.entity.zCoord);
		}
	}

	abstract public void renderItem(ItemRenderType type, ItemStack stack, Object[] data);

	abstract public void renderTileEntityAt(double x, double y, double z);

	abstract public void renderStaticBlockAt(RenderBlocks renderer, int x, int y, int z);

	public String getTypeName() {
		return "InternalError!";
	}

	public float getBlockHardness() {
		return 1;
	}

	public ItemStack getSilkTouchItemStack() {
		ItemStack stack = new ItemStack(this.entity.getBlockType(), 1, 3);
		ItemStackUtilities.addStringTag(stack, "MES", getTypeName());
		writeToNBT(stack.getTagCompound());
		return stack;
	}

	public ArrayList<ItemStack> getBlockDropped(int fortune) {
		ArrayList<ItemStack> droppedItems = new ArrayList<ItemStack>();
		return droppedItems;
	}

	public void onBlockActivated(EntityPlayer entityplayer, World world, int x, int y, int z, int par1, float par2, float par3, float par4) {

	}

	public void tick() {

	}

	/**
	 * Returns -1 if entity is valid otherwise meta of entity
	 * 
	 * @param infernosMultiEntity
	 */
	public int validateTileEntity(InfernosMultiEntityStatic infernosMultiEntity) {
		boolean entityInv = false;
		boolean entityLiq = false;
		if (infernosMultiEntity instanceof InfernosMultiEntityStaticInvLiq) {
			entityInv = true;
			entityLiq = true;
		} else if (infernosMultiEntity instanceof InfernosMultiEntityStaticInv) {
			entityInv = true;
		} else if (infernosMultiEntity instanceof InfernosMultiEntityStaticLiq) {
			entityLiq = true;
		}
		int meta = -1;
		if ((hasInventory() != entityInv) || (hasLiquids() != entityLiq)) {
			if (isDynamiclyRendered()) {
				if (hasInventory()) {
					if (hasLiquids()) {
						meta = InfernosMultiEntityType.DYNAMIC_BOTH.ordinal();
					} else {
						meta = InfernosMultiEntityType.DYNAMIC_INVENTORY.ordinal();
					}
				} else {
					if (hasLiquids()) {
						meta = InfernosMultiEntityType.DYNAMIC_LIQUID.ordinal();
					} else {
						meta = InfernosMultiEntityType.DYNAMIC_BASIC.ordinal();
					}
				}
			} else {
				if (hasInventory()) {
					if (hasLiquids()) {
						meta = InfernosMultiEntityType.STATIC_BOTH.ordinal();
					} else {
						meta = InfernosMultiEntityType.STATIC_INVENTORY.ordinal();
					}
				} else {
					if (hasLiquids()) {
						meta = InfernosMultiEntityType.STATIC_LIQUID.ordinal();
					} else {
						meta = InfernosMultiEntityType.STATIC_BASIC.ordinal();
					}
				}
			}
		}
		return meta;
	}

	public IIcon getIconFromSide(int side) {
		return null;
	}

	public void onBlockPlaced(World world, EntityPlayer player, int side, int direction, int x, int y, int z, float hitX, float hitY, float hitZ, int metadata) {
	}

	public int getComparatorInputOverride(int side) {
		return 0;
	}

	public void onDataReceived(ByteBuf data) {
	}
}
