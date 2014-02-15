package com.forgetutorials.multientity;

import java.util.List;

import com.forgetutorials.lib.registry.ForgeTutorialsRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class InfernosMultiItemTranslucent extends InfernosMultiItem {

	public InfernosMultiItemTranslucent(int par1) {
		super(par1);
	}

	@Override
	public InfernosMultiEntityStatic placeBlock(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8,
			float par9, float par10) {
			Block block = Block.blocksList[getBlockID()];
			int j1 = getMetadata(par1ItemStack.getItemDamage());
			int k1 = Block.blocksList[getBlockID()].onBlockPlaced(par3World, par4, par5, par6, par7, par8, par9, par10, j1);

			if (placeBlockAt(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10, k1)) {
				par3World.playSoundEffect(par4 + 0.5F, par5 + 0.5F, par6 + 0.5F, block.stepSound.getPlaceSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F,
						block.stepSound.getPitch() * 0.8F);
				--par1ItemStack.stackSize;
				InfernosMultiEntityStatic entity = (InfernosMultiEntityStatic) par3World.getBlockTileEntity(par4, par5, par6);
				entity.onBlockPlaced(par3World, par2EntityPlayer, par7, par4, par5, par6, par8, par9, par10, k1);
				return entity;
			}
			return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTab, List list) {
	}

	@Override
	public CreativeTabs[] getCreativeTabs() {
		return ForgeTutorialsRegistry.INSTANCE.getCreativeTabs();
	}
}
