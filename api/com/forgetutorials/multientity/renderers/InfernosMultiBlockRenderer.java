package com.forgetutorials.multientity.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
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
		/*Tessellator tessellator = Tessellator.instance;
		FluidStack fluidstack = new FluidStack(MetaLiquids.metaFluids[1].getFluid(), 200);
		Icon icon = InfuserRenderer.getFluidTexture(fluidstack, false);

		double size = fluidstack.amount * 0.001;

		FluidTessallator.InfuserTank.addToTessallator(tessellator, x, y, z, icon, size, size);*/
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public int getRenderId() {
		return InfernosMultiBlockRenderer.multiBlockRendererId;
	}

}
