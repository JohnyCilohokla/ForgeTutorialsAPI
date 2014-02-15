package com.forgetutorials.multientity.renderers;

import com.forgetutorials.multientity.*;

import cpw.mods.fml.client.registry.ClientRegistry;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

public class InfernosMultiEntityRenderer extends TileEntitySpecialRenderer {

	static InfernosMultiEntityRenderer infernosMultiRenderer = null;

	public static void initizeRenderer() {
		if (InfernosMultiEntityRenderer.infernosMultiRenderer != null) {
			return;
		}
		InfernosMultiEntityRenderer.infernosMultiRenderer = new InfernosMultiEntityRenderer();
		ClientRegistry.bindTileEntitySpecialRenderer(InfernosMultiEntityDynamic.class, InfernosMultiEntityRenderer.infernosMultiRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(InfernosMultiEntityDynamicInv.class, InfernosMultiEntityRenderer.infernosMultiRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(InfernosMultiEntityDynamicInvLiq.class, InfernosMultiEntityRenderer.infernosMultiRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(InfernosMultiEntityDynamicLiq.class, InfernosMultiEntityRenderer.infernosMultiRenderer);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		if (tileentity instanceof InfernosMultiEntityStatic) {
			InfernosMultiEntityStatic tile = (InfernosMultiEntityStatic) tileentity;
			tile.renderTileEntityAt(x, y, z);
		}
	}
}
