package com.forgetutorials.multientity;

import com.forgetutorials.lib.utilities.IFluidStackProxy;

import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class InfernosMultiEntityStaticLiq extends InfernosMultiEntityStatic implements IFluidHandler, IFluidStackProxy {

	@Override
	public FluidStack getFluid(ForgeDirection direction) {
		return getProxyEntity().getFluid(direction);
	}

	@Override
	public FluidStack getFluid(int i) {
		return getProxyEntity().getFluid(i);
	}

	@Override
	public void setFluid(int i, FluidStack fluid) {
		getProxyEntity().setFluid(i, fluid);
	}

	@Override
	public int getFluidsCount() {
		return getProxyEntity().getFluidsCount();
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return getProxyEntity().fill(from, resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		FluidStack tmpFluid = getFluid(from);
		if ((tmpFluid == null) || !tmpFluid.isFluidEqual(resource)) {
			return null;
		}
		return this.drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return getProxyEntity().drain(from, maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return getProxyEntity().canFill(from, fluid);
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return getProxyEntity().canDrain(from, fluid);
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		FluidStack tmpFluid = getFluid(from);
		return new FluidTankInfo[] { new FluidTankInfo(tmpFluid, (tmpFluid != null ? tmpFluid.amount : 0)) };
	}

}
