package com.forgetutorials.lib.registry;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class DescriptorFluid extends DescriptorBlock {
	int meltingPoint;
	int boilingPoint;
	Fluid fluid;
	public int blockID;

	public DescriptorFluid(Fluid fluid) {
		this.fluid = fluid;
	}

	public static DescriptorFluid newFluid(String fluidName, int luminosity, int density, int viscosity) {
		Fluid fluid = new Fluid(fluidName);
		fluid.setLuminosity(luminosity).setDensity(density).setViscosity(viscosity);
		return new DescriptorFluid(fluid);
	}

	@Override
	public ObjectDescriptorType getType() {
		return ObjectDescriptorType.FLUID;
	}

	public DescriptorFluid registerFluid(String unlocalizedName, ItemStack blockLiquidStack) {
		register(unlocalizedName, blockLiquidStack.getDisplayName(), blockLiquidStack);
		return this;
	}

	@Override
	protected void register(String unlocalizedName, String name, ItemStack blockLiquidStack) {
		super.register(unlocalizedName, name, blockLiquidStack);
		FluidRegistry.registerFluid(getFluid());
		this.fluid.setBlockID(getBlock());
		this.blockID = this.fluid.getBlockID();
		ForgeTutorialsRegistry.INSTANCE.addObject(unlocalizedName, this);
		System.out.println(">>Registery(FTA)<< Register Fluid: " + this.block.getClass().getCanonicalName() + " [" + this.fluid.getName() + "]");
	}

	public Fluid getFluid() {
		return this.fluid;
	}
}
