package com.forgetutorials.lib.renderers;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public enum FluidTessallator {
	// TODO change into class and move enums to separate enum in MTC
	InfuserTank(0.1, 0.1, 0.05, 0.95);

	final double distS, distL;
	final double spaceS, spaceL;
	//centered x,z
	final double distCS, distCL;
	final double spaceCS, spaceCL;
	//
	final double yStart, yEnd;

	FluidTessallator(double distance, double space, double yStart, double yEnd) {
		this.distS = 0 + distance;
		this.distL = 1 - distance;

		this.spaceS = 0 + space;
		this.spaceL = 1 - space;

		this.yStart = yStart;
		this.yEnd = yEnd;
		
		this.distCS = -0.5 + distance;
		this.distCL = 0.5 - distance;

		this.spaceCS = -0.5 + space;
		this.spaceCL = 0.5 - space;
	}

	public static double interpolateU(IIcon icon, double inter) {
		double min = icon.getMinU();
		return min + ((icon.getMaxU() - min) * inter);
	}

	public static double interpolateV(IIcon icon, double inter) {
		double min = icon.getMinV();
		return min + ((icon.getMaxV() - min) * inter);
	}

	public void addToTessallator(Tessellator tessellator, double x, double y, double z, IIcon icon, double amount, double size) {
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
		tessellator.addVertexWithUV(x + this.spaceS, y + amount, z + this.distS, icon.getMinU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(x + this.spaceL, y + amount, z + this.distS, icon.getMaxU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(x + this.spaceL, y + this.yStart, z + this.distS, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + this.spaceL, y + this.yStart, z + this.distL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.spaceL, y + amount, z + this.distL, icon.getMinU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(x + this.spaceS, y + amount, z + this.distL, icon.getMaxU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(x + this.spaceS, y + this.yStart, z + this.distL, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + this.distL, y + this.yStart, z + this.spaceS, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.distL, y + amount, z + this.spaceS, icon.getMinU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(x + this.distL, y + amount, z + this.spaceL, icon.getMaxU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(x + this.distL, y + this.yStart, z + this.spaceL, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + this.distS, y + this.yStart, z + this.spaceL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.distS, y + amount, z + this.spaceL, icon.getMinU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(x + this.distS, y + amount, z + this.spaceS, icon.getMaxU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(x + this.distS, y + this.yStart, z + this.spaceS, icon.getMaxU(), icon.getMinV());
	}
	
	/**
	 * Centered x,z!
	 * @param tessellator
	 * @param icon
	 * @param amount
	 * @param size
	 */
	public void addToTessallator(Tessellator tessellator, IIcon icon, double amount, double size) {
		amount = this.yStart + ((this.yEnd - this.yStart) * amount);

		// top
		tessellator.addVertexWithUV(this.distCS, amount, this.distCS, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(this.distCS, amount, this.distCL, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(this.distCL, amount, this.distCL, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(this.distCL, amount, this.distCS, icon.getMaxU(), icon.getMinV());

		// bottom
		tessellator.addVertexWithUV(this.distCS, this.yStart, this.distCL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(this.distCS, this.yStart, this.distCS, icon.getMinU(), icon.getMaxV());
		tessellator.addVertexWithUV(this.distCL, this.yStart, this.distCS, icon.getMaxU(), icon.getMaxV());
		tessellator.addVertexWithUV(this.distCL, this.yStart, this.distCL, icon.getMaxU(), icon.getMinV());

		// sides
		tessellator.addVertexWithUV(this.spaceCS, this.yStart, this.distCS, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(this.spaceCS, amount, this.distCS, icon.getMinU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(this.spaceCL, amount, this.distCS, icon.getMaxU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(this.spaceCL, this.yStart, this.distCS, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(this.spaceCL, this.yStart, this.distCL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(this.spaceCL, amount, this.distCL, icon.getMinU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(this.spaceCS, amount, this.distCL, icon.getMaxU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(this.spaceCS, this.yStart, this.distCL, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(this.distCL, this.yStart, this.spaceCS, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(this.distCL, amount, this.spaceCS, icon.getMinU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(this.distCL, amount, this.spaceCL, icon.getMaxU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(this.distCL, this.yStart, this.spaceCL, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(this.distCS, this.yStart, this.spaceCL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(this.distCS, amount, this.spaceCL, icon.getMinU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(this.distCS, amount, this.spaceCS, icon.getMaxU(), FluidTessallator.interpolateV(icon, size));
		tessellator.addVertexWithUV(this.distCS, this.yStart, this.spaceCS, icon.getMaxU(), icon.getMinV());
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

	public static IIcon getFluidTexture(FluidStack fluidStack, boolean flowing) {
		if (fluidStack == null) {
			return null;
		}
		return FluidTessallator.getFluidTexture(fluidStack.getFluid(), flowing);
	}

	public static IIcon getFluidTexture(Fluid fluid, boolean flowing) {
		if (fluid == null) {
			return null;
		}
		IIcon icon = flowing ? fluid.getFlowingIcon() : fluid.getStillIcon();
		if (icon == null) {
			icon = ((TextureMap) Minecraft.getMinecraft().renderEngine.getTexture(TextureMap.locationBlocksTexture)).registerIcon("missingno");
		}
		return icon;
	}
	public void renderFluidStack(Tessellator tessellator, FluidStack fluidstack, double x, double y, double z) {
		renderFluidStack(tessellator, fluidstack, x, y, z, null);
	}
	public void renderFluidStack(Tessellator tessellator, FluidStack fluidstack, double x, double y, double z, int facing) {
		renderFluidStack(tessellator, fluidstack, x, y, z, EnumFacing.getFront(facing));
	}

	public void renderFluidStack(Tessellator tessellator, FluidStack fluidstack, double x, double y, double z, EnumFacing facing) {
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
		IIcon icon = FluidTessallator.getFluidTexture(fluidstack, false);

		double size = fluidstack.amount * 0.001;
		GL11.glTranslated(x, y, z);
		FTAOpenGL.glRotate(facing);
		tessellator.startDrawingQuads();
		addToTessallator(tessellator, icon, size, size);
		tessellator.draw();

		GL11.glColor4f(1, 1, 1, 1);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}

}
