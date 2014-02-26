package com.forgetutorials.multientity;

import java.util.ArrayList;

import com.forgetutorials.lib.registry.InfernosRegisteryProxyEntity;
import com.forgetutorials.lib.utilities.ItemUtilities;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;
import com.forgetutorials.multientity.renderers.InfernosMultiBlockRenderer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

abstract public class InfernosMultiBlock extends Block {

	public InfernosMultiBlock(int par1, Material material) {
		super(material);
	}

	Block getBlockType(){
		return null;
	}

	@Override
	public boolean hasTileEntity(int metadata) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int metadata) {
		// FIXME hax?
		InfernosMultiEntityStatic entity = InfernosMultiEntityType.newMultiEntity(InfernosMultiEntityType.values()[metadata
				% InfernosMultiEntityType.values().length]);
		return entity;
	}

	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9) {
		return super.onBlockPlaced(par1World, par2, par3, par4, par5, par6, par7, par8, par9);
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack);
		String proxyEntityName = ((InfernosMultiItem) (par6ItemStack.getItem())).getProxyEntity(par6ItemStack);
		InfernosMultiEntityStatic entity = (InfernosMultiEntityStatic) par1World.getTileEntity(par2, par3, par4);
		entity.newEntity(proxyEntityName);
	}

	@Override
	public void onPostBlockPlaced(World par1World, int par2, int par3, int par4, int par5) {
		InfernosMultiEntityStatic entity = (InfernosMultiEntityStatic) par1World.getTileEntity(par2, par3, par4);
		if (!entity.hasProxyEntity()) {
			System.out.println(">> MES error block has no proxy entity");
		}
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		InfernosMultiEntityStatic entity = (InfernosMultiEntityStatic) world.getTileEntity(x, y, z);
		return (entity != null) ? entity.getBlockHardness() : 0;
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4) {
		super.onBlockAdded(par1World, par2, par3, par4);
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6) {
		super.breakBlock(par1World, par2, par3, par4, par5, par6);
		par1World.removeTileEntity(par2, par3, par4);
	}

	@Override
	public boolean onBlockEventReceived(World par1World, int par2, int par3, int par4, int par5, int par6) {
		super.onBlockEventReceived(par1World, par2, par3, par4, par5, par6);
		TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
		return tileentity != null ? tileentity.receiveClientEvent(par5, par6) : false;
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		InfernosMultiEntityStatic entity = (InfernosMultiEntityStatic) world.getTileEntity(x, y, z);
		return (entity != null) ? entity.getSilkTouchItemStack() : null;
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		InfernosMultiEntityStatic entity = (InfernosMultiEntityStatic) world.getTileEntity(x, y, z);
		return (entity != null) ? entity.getLightValue() : 0;
	}

	@Override
	public boolean canSilkHarvest(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		InfernosMultiEntityStatic entity = (InfernosMultiEntityStatic) world.getTileEntity(x, y, z);
		return (entity != null) ? entity.canSilkHarvest(player) : false;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int damage) {
		// ignore it, as its too late to check the tile entity
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int par5, EntityPlayer player) {
		// break the block here instead
		if (!world.isRemote) {
			InfernosMultiEntityStatic entity = (InfernosMultiEntityStatic) world.getTileEntity(x, y, z);
			if (entity != null) {
				entity.harvestBlock(player);
			}
		}
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		InfernosMultiEntityStatic entity = (InfernosMultiEntityStatic) world.getTileEntity(x, y, z);
		return (entity != null) ? entity.getBlockDropped(fortune) : null;
	}

	@Override
	public int getRenderType() {
		return InfernosMultiBlockRenderer.multiBlockRendererId;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side) {
		InfernosMultiEntityStatic entity = (InfernosMultiEntityStatic) blockAccess.getTileEntity(x, y, z);
		return (entity != null) ? entity.getIconFromSide(side) : null;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) {

		InfernosMultiEntityStatic entity = (InfernosMultiEntityStatic) world.getTileEntity(x, y, z);
		ItemStack currentItem = entityplayer.inventory.getCurrentItem();

		InfernosProxyEntityBase proxyEntity = entity.getProxyEntity();
		if (currentItem != null) {
			if (proxyEntity.hasLiquids()) {
				FluidStack liquid = FluidContainerRegistry.getFluidForFilledItem(currentItem);

				if (liquid != null) {
					int qty = proxyEntity.fill(net.minecraftforge.common.util.ForgeDirection.UNKNOWN, liquid, false);
					if (qty > 0) {
						proxyEntity.fill(net.minecraftforge.common.util.ForgeDirection.UNKNOWN, liquid, true);

						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem,
								ItemUtilities.useSingleItemOrDropAndReturn(world, entityplayer, currentItem));
					}
					return true;

				} else {

					FluidStack available = proxyEntity.getFluid(ForgeDirection.UNKNOWN);
					if (available != null) {
						ItemStack filled = FluidContainerRegistry.fillFluidContainer(available, currentItem);

						liquid = FluidContainerRegistry.getFluidForFilledItem(filled);

						if (liquid != null) {
							if (!entityplayer.capabilities.isCreativeMode) {
								entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem,
										ItemUtilities.replaceSingleItemOrDropAndReturn(world, entityplayer, currentItem, filled));
							}
							proxyEntity.drain(ForgeDirection.UNKNOWN, liquid.amount, true);
							return true;
						}
					}
				}
			}
		} else {
			proxyEntity.onBlockActivated(entityplayer, world, x, y, z, par6, par7, par8, par9);
		}

		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		InfernosRegisteryProxyEntity.INSTANCE.registerIcons(iconRegister);
		super.registerBlockIcons(iconRegister);
		this.blockIcon = iconRegister.registerIcon("MetaTechCraft".toLowerCase() + ":" + "strangeObsidian");
	}

	@Override
	public boolean hasComparatorInputOverride() {
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
		InfernosMultiEntityStatic entity = (InfernosMultiEntityStatic) world.getTileEntity(x, y, z);
		return entity.getComparatorInputOverride(side);
	}
}
