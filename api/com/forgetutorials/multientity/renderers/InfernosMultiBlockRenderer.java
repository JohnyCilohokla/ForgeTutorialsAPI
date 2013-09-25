package com.forgetutorials.multientity.renderers;

import com.forgetutorials.multientity.InfernosMultiEntity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class InfernosMultiBlockRenderer implements ISimpleBlockRenderingHandler {

	public static int multiBlockRendererId = -1;

	public static void initizeRenderer() {
		if (InfernosMultiBlockRenderer.multiBlockRendererId != -1) {
			return;
		}
		InfernosMultiBlockRenderer.multiBlockRendererId = RenderingRegistry.getNextAvailableRenderId();
		InfernosMultiBlockRenderer infernosMultiBlockRenderer = new InfernosMultiBlockRenderer();
		RenderingRegistry.registerBlockHandler(InfernosMultiBlockRenderer.multiBlockRendererId, infernosMultiBlockRenderer);
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		TileEntity tileentity = world.getBlockTileEntity(x, y, z);
		if (tileentity == null) {
			return true;
		}
		if (tileentity instanceof InfernosMultiEntity) {
			InfernosMultiEntity tile = (InfernosMultiEntity) tileentity;
			tile.renderStaticBlockAt(renderer, x, y, z);
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return InfernosMultiBlockRenderer.multiBlockRendererId;
	}

}
