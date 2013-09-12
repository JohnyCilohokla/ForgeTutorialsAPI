package com.forgetutorials.lib.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public enum FluidTessallator {
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
		tessellator.addVertexWithUV(x + this.spaceS, y + amount, z + this.distS, icon.getMinU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.spaceL, y + amount, z + this.distS, icon.getMaxU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.spaceL, y + this.yStart, z + this.distS, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + this.spaceL, y + this.yStart, z + this.distL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.spaceL, y + amount, z + this.distL, icon.getMinU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.spaceS, y + amount, z + this.distL, icon.getMaxU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.spaceS, y + this.yStart, z + this.distL, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + this.distL, y + this.yStart, z + this.spaceS, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.distL, y + amount, z + this.spaceS, icon.getMinU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.distL, y + amount, z + this.spaceL, icon.getMaxU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.distL, y + this.yStart, z + this.spaceL, icon.getMaxU(), icon.getMinV());

		tessellator.addVertexWithUV(x + this.distS, y + this.yStart, z + this.spaceL, icon.getMinU(), icon.getMinV());
		tessellator.addVertexWithUV(x + this.distS, y + amount, z + this.spaceL, icon.getMinU(), icon.getInterpolatedV(size));
		tessellator.addVertexWithUV(x + this.distS, y + amount, z + this.spaceS, icon.getMaxU(), icon.getInterpolatedV(size));
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
			icon = ((TextureMap) Minecraft.getMinecraft().func_110434_K().func_110581_b(TextureMap.field_110575_b)).func_110572_b("missingno");
		}
		return icon;
	}

}
