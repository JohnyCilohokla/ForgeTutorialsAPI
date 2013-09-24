package com.forgetutorials.lib.renderers;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public enum FluidTessallator {
	// TODO change into class and move enums to separate enum in MTC
	InfuserTank(0.1, 0.1, 0.05, 0.95);

	final double distS, distL;
	final double spaceS, spaceL;
	final double yStart, yEnd;

	FluidTessallator(double distance, double space, double yStart, double yEnd) {
		this.distS = 0 + distance;
		this.distL = 1 - distance;

		this.spaceS = 0 + space;
		this.spaceL = 1 - space;

		this.yStart = yStart;
		this.yEnd = yEnd;
	}
	
	public static double interpolateU(Icon icon, double inter){
		double min = icon.getMinU();
		return min+((icon.getMaxU()-min)*inter);
	}
	
	public static double interpolateV(Icon icon, double inter){
		double min = icon.getMinV();
		return min+((icon.getMaxV()-min)*inter);
	}

	public void addToTessallator(Tessellator tessellator, double x, double y, double z, Icon icon, double amount, double size) {
		amount = this.yStart + ((this.yEnd - this.yStart) * amount);

		// top
		tessellator.addVertexWithUV(x + this.distS, y + amount, z + this.distS, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.distS, y + amount, z + this.distL, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + this.distL, y + amount, z + this.distL, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + this.distL, y + amount, z + this.distS, icon.getMaxU(), icon.getMinV());

		// bottom
		tessellator.addVertexWithUV(x + this.distS, y + this.yStart, z + this.distL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.distS, y + this.yStart, z + this.distS, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + this.distL, y + this.yStart, z + this.distS, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(x + this.distL, y + this.yStart, z + this.distL, icon.getMaxU(), icon.getMinV());

		// sides
		tessellator.addVertexWithUV(x + this.spaceS, y + this.yStart, z + this.distS, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.spaceS, y + amount, z + this.distS, icon.getMinU(), interpolateV(icon,size));
		tessellator.addVertexWithUV(x + this.spaceL, y + amount, z + this.distS, icon.getMaxU(), interpolateV(icon,size));
		tessellator.addVertexWithUV(x + this.spaceL, y + this.yStart, z + this.distS, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + this.spaceL, y + this.yStart, z + this.distL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.spaceL, y + amount, z + this.distL, icon.getMinU(), interpolateV(icon,size));
		tessellator.addVertexWithUV(x + this.spaceS, y + amount, z + this.distL, icon.getMaxU(), interpolateV(icon,size));
		tessellator.addVertexWithUV(x + this.spaceS, y + this.yStart, z + this.distL, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + this.distL, y + this.yStart, z + this.spaceS, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.distL, y + amount, z + this.spaceS, icon.getMinU(), interpolateV(icon,size));
		tessellator.addVertexWithUV(x + this.distL, y + amount, z + this.spaceL, icon.getMaxU(), interpolateV(icon,size));
		tessellator.addVertexWithUV(x + this.distL, y + this.yStart, z + this.spaceL, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + this.distS, y + this.yStart, z + this.spaceL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.distS, y + amount, z + this.spaceL, icon.getMinU(), interpolateV(icon,size));
		tessellator.addVertexWithUV(x + this.distS, y + amount, z + this.spaceS, icon.getMaxU(), interpolateV(icon,size));
		tessellator.addVertexWithUV(x + this.distS, y + this.yStart, z + this.spaceS, icon.getMaxU(), icon.getMinV());
	}

	public static void setColorForFluidStack(FluidStack fluidstack) {
		if (fluidstack == null) {
			return;
		}

		int color = fluidstack.getFluid().getColor(fluidstack);
		float red = ((color >> 16) & 255) / 255.0F;
		float green = ((color >> 8) & 255) / 255.0F;
		float blue = (color & 255) / 255.0F;
		GL11.glColor4f(red, green, blue, 0.6f);
	}

	public static Icon getFluidTexture(FluidStack fluidStack, boolean flowing) {
		if (fluidStack == null) {
			return null;
		}
		return FluidTessallator.getFluidTexture(fluidStack.getFluid(), flowing);
	}

	public static Icon getFluidTexture(Fluid fluid, boolean flowing) {
		if (fluid == null) {
			return null;
		}
		Icon icon = flowing ? fluid.getFlowingIcon() : fluid.getStillIcon();
		if (icon == null) {
			icon = ((TextureMap) Minecraft.getMinecraft().renderEngine.getTexture(TextureMap.locationBlocksTexture)).registerIcon("missingno");
		}
		return icon;
	}
	
	
	
	public void renderFluidStack(Tessellator tessellator, FluidStack fluidstack, double x, double y, double z) {
		if ((fluidstack == null) || (fluidstack.amount <= 0)) {
			return;
		}

		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_ENABLE_BIT);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		FMLClientHandler.instance().getClient().renderEngine.bindTexture(TextureMap.locationBlocksTexture);
		FluidTessallator.setColorForFluidStack(fluidstack);
		Icon icon = FluidTessallator.getFluidTexture(fluidstack, false);

		double size = fluidstack.amount * 0.001;

		tessellator.startDrawingQuads();
		this.addToTessallator(tessellator, x, y, z, icon, size, size);
		tessellator.draw();

		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

}
