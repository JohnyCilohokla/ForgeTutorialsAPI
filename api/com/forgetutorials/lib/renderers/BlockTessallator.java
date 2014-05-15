package com.forgetutorials.lib.renderers;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

public class BlockTessallator {
	public static void addToTessallator(Tessellator tessellator, double x, double y, double z, IIcon topIcon, IIcon bottomIcon, IIcon side1Icon,
			IIcon side2Icom, IIcon side3Icom, IIcon side4Icon) {

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

	public static void addToTessallator(Tessellator tessellator, double x, double y, double z, IIcon icon) {
		BlockTessallator.addToTessallator(tessellator, x, y, z, icon, icon, icon, icon, icon, icon);
	}

	public static void addToRenderer(Block block, VertexRenderer v, RenderBlocks blockRenderer, double x, double y, double z, IIcon icon, IIcon icon2,
			int trueX, int trueY, int trueZ) {
		v.renderBlockInWorld(block, blockRenderer, trueX, trueY, trueZ, icon, icon2, x, y, z, 1, 1, 1);
	}

	public static void renderBlockAsItem(Block block, VertexRenderer v, IIcon icon, IIcon icon2, float r, float g, float b, int brightness) {
		v.renderBlockAsItem(block, icon, icon2, r, g, b, brightness);
	}

	static boolean SWITCH = false;

	public static void enableOverlay() {
		OpenGlHelper.setActiveTexture(33986);
		if (!BlockTessallator.SWITCH) {
			BlockTessallator.SWITCH = true;
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);

			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL13.GL_COMBINE);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_COMBINE_RGB, GL13.GL_INTERPOLATE);

			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE0_RGB, GL13.GL_PREVIOUS);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_OPERAND0_RGB, GL11.GL_SRC_COLOR);

			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE1_RGB, GL11.GL_TEXTURE);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_OPERAND1_RGB, GL11.GL_SRC_COLOR);

			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE2_RGB, GL13.GL_PREVIOUS);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_COMBINE_ALPHA, GL11.GL_ADD);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_SOURCE0_ALPHA, GL11.GL_TEXTURE);
			GL11.glTexEnvi(GL11.GL_TEXTURE_ENV, GL13.GL_OPERAND0_ALPHA, GL11.GL_SRC_ALPHA);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

	}

	public static void disableOverlay() {
		OpenGlHelper.setActiveTexture(33986);

		GL11.glDisable(GL11.GL_TEXTURE_2D);

		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	public static void renderDualTextureBlockAsItem(VertexRenderer vertexRenderer, Block block, ItemRenderType type, IIcon icon, IIcon icon2, Object[] data) {

		GL11.glPushMatrix();

		GL11.glColor4f(1f, 1f, 1f, 1f);
		BlockTessallator.enableOverlay();
		GL11.glDisable(GL11.GL_LIGHTING);
		switch (type) {
		case ENTITY:
			EntityItem entityItem = (EntityItem) data[1];
			BlockTessallator.renderBlockAsItem(block, vertexRenderer, icon, icon2, 1, 1, 1, (entityItem.getBrightnessForRender(1)));
			break;
		case EQUIPPED:
			BlockTessallator.renderBlockAsItem(block, vertexRenderer, icon, icon2, 1, 1, 1, (((EntityLivingBase) data[1]).getBrightnessForRender(1)));
			break;
		case EQUIPPED_FIRST_PERSON:
			BlockTessallator.renderBlockAsItem(block, vertexRenderer, icon, icon2, 1, 1, 1, (Minecraft.getMinecraft().thePlayer.getBrightnessForRender(1)));
			break;
		case INVENTORY:
			BlockTessallator.addToRenderer(block, vertexRenderer, null, 0.1, 0, 0.1, icon, icon2, 0, 0, 0);
			break;
		default:
			BlockTessallator.renderBlockAsItem(block, vertexRenderer, icon, icon2, 1, 1, 1, 240);
			break;
		}

		vertexRenderer.render();

		BlockTessallator.disableOverlay();

		GL11.glPopMatrix();
	}

}
