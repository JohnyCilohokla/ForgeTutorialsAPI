package com.forgetutorials.lib.renderers;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

public class BlockTessallator {
	public static void addToTessallator(Tessellator tessellator, double x, double y, double z, Icon topIcon, Icon bottomIcon, Icon side1Icon, Icon side2Icom,
			Icon side3Icom, Icon side4Icon) {

		// top
		tessellator.addVertexWithUV(x, y + 1, z, topIcon.getMinU(), topIcon.getMaxV());
		tessellator.addVertexWithUV(x, y + 1, z + 1, topIcon.getMinU(), topIcon.getMinV());
		tessellator.addVertexWithUV(x + 1, y + 1, z + 1, topIcon.getMaxU(), topIcon.getMinV());
		tessellator.addVertexWithUV(x + 1, y + 1, z, topIcon.getMaxU(), topIcon.getMaxV());

		// bottom
		tessellator.addVertexWithUV(x, y, z + 1, bottomIcon.getMinU(), bottomIcon.getMaxV());
		tessellator.addVertexWithUV(x, y, z, bottomIcon.getMinU(), bottomIcon.getMinV());
		tessellator.addVertexWithUV(x + 1, y, z, bottomIcon.getMaxU(), bottomIcon.getMinV());
		tessellator.addVertexWithUV(x + 1, y, z + 1, bottomIcon.getMaxU(), bottomIcon.getMaxV());

		// sides
		tessellator.addVertexWithUV(x, y, z, side1Icon.getMinU(), side1Icon.getMaxV());
		tessellator.addVertexWithUV(x, y + 1, z, side1Icon.getMinU(), side1Icon.getMinV());
		tessellator.addVertexWithUV(x + 1, y + 1, z, side1Icon.getMaxU(), side1Icon.getMinV());
		tessellator.addVertexWithUV(x + 1, y, z, side1Icon.getMaxU(), side1Icon.getMaxV());

		tessellator.addVertexWithUV(x + 1, y, z + 1, side2Icom.getMinU(), side2Icom.getMaxV());
		tessellator.addVertexWithUV(x + 1, y + 1, z + 1, side2Icom.getMinU(), side2Icom.getMinV());
		tessellator.addVertexWithUV(x, y + 1, z + 1, side2Icom.getMaxU(), side2Icom.getMinV());
		tessellator.addVertexWithUV(x, y, z + 1, side2Icom.getMaxU(), side2Icom.getMaxV());

		tessellator.addVertexWithUV(x + 1, y, z, side3Icom.getMinU(), side3Icom.getMaxV());
		tessellator.addVertexWithUV(x + 1, y + 1, z, side3Icom.getMinU(), side3Icom.getMinV());
		tessellator.addVertexWithUV(x + 1, y + 1, z + 1, side3Icom.getMaxU(), side3Icom.getMinV());
		tessellator.addVertexWithUV(x + 1, y, z + 1, side3Icom.getMaxU(), side3Icom.getMaxV());

		tessellator.addVertexWithUV(x, y, z + 1, side4Icon.getMinU(), side4Icon.getMaxV());
		tessellator.addVertexWithUV(x, y + 1, z + 1, side4Icon.getMinU(), side4Icon.getMinV());
		tessellator.addVertexWithUV(x, y + 1, z, side4Icon.getMaxU(), side4Icon.getMinV());
		tessellator.addVertexWithUV(x, y, z, side4Icon.getMaxU(), side4Icon.getMaxV());
	}

	public static void addToTessallator(Tessellator tessellator, double x, double y, double z, Icon icon) {
		BlockTessallator.addToTessallator(tessellator, x, y, z, icon, icon, icon, icon, icon, icon);
	}

	public static void addToRenderer(VertexRenderer v, RenderBlocks blockRenderer, int x, int y, int z, Icon icon, Icon icon2) {
		// @formatter:off
		// top
		v.addQuadUVSTColor(
				x, y + 1, z, icon.getMinU(), icon.getMaxV(), icon2.getMinU(), icon2.getMaxV(),
				(int) (Math.random() * 255),(int) (Math.random() * 255),(int) (Math.random() * 255),(int) (Math.random() * 255),
				
				x, y + 1, z + 1, icon.getMinU(), icon.getMinV(), icon2.getMinU(), icon2.getMinV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x + 1, y + 1, z + 1, icon.getMaxU(), icon.getMinV(), icon2.getMaxU(), icon2.getMinV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x + 1, y + 1, z, icon.getMaxU(), icon.getMaxV(), icon2.getMaxU(), icon2.getMaxV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)
				);

		// bottom
		v.addQuadUVSTColor(
				x, y, z + 1, icon.getMinU(), icon.getMaxV(), icon2.getMinU(), icon2.getMaxV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x, y, z, icon.getMinU(), icon.getMinV(), icon2.getMinU(), icon2.getMinV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x + 1, y, z, icon.getMaxU(), icon.getMinV(), icon2.getMaxU(), icon2.getMinV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x + 1, y, z + 1, icon.getMaxU(), icon.getMaxV(), icon2.getMaxU(), icon2.getMaxV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)
				);

		// sides
		v.addQuadUVSTColor(x, y, z, icon.getMinU(), icon.getMaxV(), icon2.getMinU(), icon2.getMaxV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x, y + 1, z, icon.getMinU(), icon.getMinV(), icon2.getMinU(), icon2.getMinV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x + 1, y + 1, z, icon.getMaxU(), icon.getMinV(), icon2.getMaxU(), icon2.getMinV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x + 1, y, z, icon.getMaxU(), icon.getMaxV(), icon2.getMaxU(), icon2.getMaxV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)
				);

		v.addQuadUVSTColor(
				x + 1, y, z + 1, icon.getMinU(), icon.getMaxV(), icon2.getMinU(), icon2.getMaxV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x + 1, y + 1, z + 1, icon.getMinU(), icon.getMinV(), icon2.getMinU(), icon2.getMinV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x, y + 1, z + 1, icon.getMaxU(), icon.getMinV(), icon2.getMaxU(), icon2.getMinV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x, y, z + 1, icon.getMaxU(), icon.getMaxV(), icon2.getMaxU(), icon2.getMaxV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)
				);

		v.addQuadUVSTColor(
				x + 1, y, z, icon.getMinU(), icon.getMaxV(), icon2.getMinU(), icon2.getMaxV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x + 1, y + 1, z, icon.getMinU(), icon.getMinV(), icon2.getMinU(), icon2.getMinV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x + 1, y + 1, z + 1, icon.getMaxU(), icon.getMinV(), icon2.getMaxU(), icon2.getMinV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x + 1, y, z + 1, icon.getMaxU(), icon.getMaxV(), icon2.getMaxU(), icon2.getMaxV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)
				);

		v.addQuadUVSTColor(
				x, y, z + 1, icon.getMinU(), icon.getMaxV(), icon2.getMinU(), icon2.getMaxV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x, y + 1, z + 1, icon.getMinU(), icon.getMinV(), icon2.getMinU(), icon2.getMinV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x, y + 1, z, icon.getMaxU(), icon.getMinV(), icon2.getMaxU(), icon2.getMinV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255),
				
				x, y, z, icon.getMaxU(), icon.getMaxV(), icon2.getMaxU(), icon2.getMaxV(),
				(int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)
				);

		// @formatter:on
	}
}
