package com.forgetutorials.multientity.base;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import com.forgetutorials.multientity.InfernosMultiEntity;

public class InfernosProxyEntityDummy extends InfernosProxyEntityBase {

	public InfernosProxyEntityDummy(InfernosMultiEntity entity) {
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
	public void renderTileEntityAt(double x, double y, double z) {
	}

	@Override
	public void renderStaticBlockAt(RenderBlocks renderer, int x, int y, int z) {
		renderer.renderBlockAllFaces(Block.web, x, y, z);
	}

	@Override
	public void renderItem(ItemRenderType type, Object[] data) {
	}

}
