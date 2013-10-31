package com.forgetutorials.multientity;

import java.util.ArrayList;

import com.forgetutorials.lib.network.MultiEntitySystem;
import com.forgetutorials.lib.network.PacketMultiTileEntity;
import com.forgetutorials.lib.network.PacketType;
import com.forgetutorials.lib.network.SubPacketTileEntityBlockUpdate;
import com.forgetutorials.lib.registry.InfernosRegisteryProxyEntity;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;
import com.forgetutorials.multientity.base.InfernosProxyEntityDummy;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class InfernosMultiEntity extends TileEntity {
	private InfernosProxyEntityBase proxyEntity;
	private int side = -1;
	private boolean requestBlockUpdate = true;

	public InfernosMultiEntity() {
		super();
	}

	public InfernosProxyEntityBase getProxyEntity() {
		return (this.proxyEntity != null) ? (this.proxyEntity) : (InfernosProxyEntityBase.DUMMY);
	}

	public InfernosProxyEntityBase getProxyEntity(boolean allowDummy) {
		InfernosProxyEntityBase target = getProxyEntity();
		return ((!(target instanceof InfernosProxyEntityDummy)) || allowDummy) ? (target) : (null);
	}

	private void setProxyEntity(InfernosProxyEntityBase proxyEntity) {
		if (proxyEntity == null) {
			this.proxyEntity = proxyEntity;
			return;
		}
		int meta = proxyEntity.validateTileEntity(this);
		if (meta != -1) {
			System.out.println(">> MES Updating meta! (" + this.getClass().getCanonicalName() + ")");
			this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, MultiEntitySystem.infernosMultiBlockID, meta, 3);
			InfernosMultiEntity entity = (InfernosMultiEntity) this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord);
			entity.newEntity(proxyEntity);
		}
		this.proxyEntity = proxyEntity;
	}

	private void newEntity(InfernosProxyEntityBase proxyEntity) {
		try {
			setProxyEntity(proxyEntity);
		} catch (Exception e) {
			e.printStackTrace();
			this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
		}
	}

	public void newEntity(String entityName) {
		entityName = InfernosRegisteryProxyEntity.INSTANCE.getCompatibleName(entityName);
		if ((this.proxyEntity == null) || (!this.proxyEntity.getTypeName().equals(entityName))) {
			try {
				setProxyEntity(InfernosRegisteryProxyEntity.INSTANCE.newMultiEntity(entityName, this));
			} catch (Exception e) {
				e.printStackTrace();
				this.worldObj.setBlockToAir(this.xCoord, this.yCoord, this.zCoord);
			}
		}
	}

	public float getBlockHardness() {
		return getProxyEntity().getBlockHardness();
	}

	public boolean canSilkHarvest(EntityPlayer player) {
		return false;
	}

	public void harvestBlock(EntityPlayer player) {
		// TODO abstractify
		player.addExhaustion(0.025F);
		dropBlockAsItems();
		/*if (canSilkHarvest(player) && (player.getCurrentEquippedItem().getItem() == MetaItems.strangeChisel)) {
			ItemStack itemstack = getSilkTouchItemStack();
		
			if (itemstack != null) {
				dropBlockAsItem_do(itemstack);
			}
		} else {
			int fortune = EnchantmentHelper.getFortuneModifier(player);
			dropBlockAsItem(fortune);
		}*/

	}

	/**
	 * Drops the block items with a specified chance of dropping the specified
	 * items
	 */
	public void dropBlockAsItemWithChance(int fortune) {
		ArrayList<ItemStack> items = getBlockDropped(fortune);

		for (ItemStack item : items) {
			if (this.worldObj.rand.nextFloat() <= fortune) {
				dropItemStack(item);
			}
		}
	}

	public void dropBlockAsItems() {
		ArrayList<ItemStack> items = getBlockDropped(0);

		for (ItemStack item : items) {
			dropItemStack(item);
		}
	}

	protected void dropItemStack(ItemStack itemstack) {
		if (!this.worldObj.isRemote && this.worldObj.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
			float f = 0.7F;
			double d0 = (this.worldObj.rand.nextFloat() * f) + ((1.0F - f) * 0.5D);
			double d1 = (this.worldObj.rand.nextFloat() * f) + ((1.0F - f) * 0.5D);
			double d2 = (this.worldObj.rand.nextFloat() * f) + ((1.0F - f) * 0.5D);
			EntityItem entityitem = new EntityItem(this.worldObj, this.xCoord + d0, this.yCoord + d1, this.zCoord + d2, itemstack);
			entityitem.delayBeforeCanPickup = 10;
			this.worldObj.spawnEntityInWorld(entityitem);
		}

	}

	protected ItemStack getSilkTouchItemStack() {
		return getProxyEntity().getSilkTouchItemStack();
	}

	public ArrayList<ItemStack> getBlockDropped(int fortune) {
		return getProxyEntity().getBlockDropped(fortune);
	}

	public void renderTileEntityAt(double x, double y, double z) {
		getProxyEntity().renderTileEntityAt(x, y, z);
	}

	public void renderStaticBlockAt(RenderBlocks renderer, int x, int y, int z) {
		getProxyEntity().renderStaticBlockAt(renderer, x, y, z);
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		String mes = tagCompound.getString("MES");
		newEntity(mes);
		this.side = tagCompound.getInteger("MES.s");
		getProxyEntity().readFromNBT(tagCompound);
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		if (getProxyEntity() != null) {
			tagCompound.setString("MES", getProxyEntity().getTypeName());
			tagCompound.setInteger("MES.s", this.side);
			getProxyEntity().writeToNBT(tagCompound);
		}
	}

	@Override
	public Packet getDescriptionPacket() {
		PacketMultiTileEntity packet = new PacketMultiTileEntity(this.xCoord, this.yCoord, this.zCoord, this.side, getProxyEntity().getTypeName());
		if (this.requestBlockUpdate == true) {
			packet.addPacket(new SubPacketTileEntityBlockUpdate());
			this.requestBlockUpdate = false;
		}
		getProxyEntity().addToDescriptionPacket(packet);
		return PacketType.populatePacket(packet);
	}

	@Override
	public void updateEntity() {
		getProxyEntity().tick();
	}

	@Override
	public void invalidate() {
		if (getProxyEntity() != null) {
			getProxyEntity().invalidate();
			setProxyEntity(null);
		}
	}

	public boolean hasProxyEntity() {
		return this.proxyEntity != null;
	}

	public void onBlockPlaced(World world, EntityPlayer player, int side, int x, int y, int z, float hitX, float hitY, float hitZ, int metadata) {
		this.side = side;
		int direction = MathHelper.floor_double((player.rotationYaw * 4.0F) / 360.0F + 0.5D) & 3;
		getProxyEntity().onBlockPlaced(world, player, side, direction, x, y, z, hitX, hitY, hitZ, metadata);
	}

	public void setSide(int side) {
		this.side = side;
	}

	public int getSide() {
		return this.side == -1 ? -1 : Facing.oppositeSide[this.side];
	}

	public Icon getIconFromSide(int side) {
		return getProxyEntity().getIconFromSide(side);
	}

	public int getFacingInt() {
		return this.side == -1 ? 6 : Facing.oppositeSide[this.side];
	}

	public void markRenderUpdate() {
		this.requestBlockUpdate = true;
		if (!this.worldObj.isRemote) {
			this.worldObj.markBlockForUpdate(this.xCoord, this.yCoord, this.zCoord);
		}
	}

	public int getComparatorInputOverride(int side) {
		return getProxyEntity().getComparatorInputOverride(side);
	}
}