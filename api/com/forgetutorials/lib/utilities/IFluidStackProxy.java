package com.forgetutorials.lib.utilities;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidStackProxy {

	public FluidStack getFluid(ForgeDirection direction);

	public FluidStack getFluid(int i);

	public void setFluid(int i, FluidStack fluid);

	public int getFluidsCount();
}
