package com.forgetutorials.multientity.renderers;

import com.forgetutorials.multientity.InfernosMultiEntity;
import com.forgetutorials.multientity.InfernosMultiEntityInv;
import com.forgetutorials.multientity.InfernosMultiEntityInvLiq;
import com.forgetutorials.multientity.InfernosMultiEntityLiq;

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
		ClientRegistry.bindTileEntitySpecialRenderer(InfernosMultiEntity.class, InfernosMultiEntityRenderer.infernosMultiRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(InfernosMultiEntityInv.class, InfernosMultiEntityRenderer.infernosMultiRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(InfernosMultiEntityInvLiq.class, InfernosMultiEntityRenderer.infernosMultiRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(InfernosMultiEntityLiq.class, InfernosMultiEntityRenderer.infernosMultiRenderer);
	}

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float f) {
		if (tileentity instanceof InfernosMultiEntity) {
			InfernosMultiEntity tile = (InfernosMultiEntity) tileentity;
			tile.renderTileEntityAt(x, y, z);
		}
	}
}
