package com.forgetutorials.multientity.base;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import com.forgetutorials.multientity.InfernosMultiEntityStatic;

public class InfernosProxyEntityDummy extends InfernosProxyEntityBase {


	public InfernosProxyEntityDummy(InfernosMultiEntityStatic entity) {
		super(entity);
	}

	@Override
	public boolean hasInventory() {
		return false;
	}

	@Override
	public boolean hasLiquids() {
		return false;
	}

	@Override
	public boolean isDynamiclyRendered() {
		return false;
	}

	@Override
	public boolean isOpaque() {
		return false;
	}

	@Override
	public ItemStack getSilkTouchItemStack() {
		return null;
	}

	@Override
	public void renderTileEntityAt(double x, double y, double z) {
	}

	@Override
	public void renderStaticBlockAt(RenderBlocks renderer, int x, int y, int z) {
		renderer.renderBlockAllFaces(Blocks.web, x, y, z);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object[] data) {
	}

}
