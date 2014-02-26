package com.forgetutorials.lib.utilities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class ItemBlockWithInfo extends ItemBlock{

	public ItemBlockWithInfo(Block p_i45328_1_) {
		super(p_i45328_1_);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List list, boolean par4) {
		if (StatCollector.canTranslate(par1ItemStack.getUnlocalizedName()+".tooltip")){
		list.add(StatCollector.translateToLocal(par1ItemStack.getUnlocalizedName()+".tooltip"));
		}
	}

}
