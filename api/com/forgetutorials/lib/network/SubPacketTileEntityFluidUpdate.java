package com.forgetutorials.lib.network;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import com.forgetutorials.lib.utilities.IFluidStackProxy;

import cpw.mods.fml.common.network.ByteBufUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

/**
 * MetaTech Craft
 * 
 * As the packet system was based on Pahimar's EE3 packet system it is licensed by LGPL v3 I have modified it greatly splitting the Tile Entity packet into Main
 * packet (x,y,z) Simple Item Update subpacket (pos, item) Fluid Update subpacket (pos, fluidTag) more to come
 * 
 * @author johnycilohokla
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class SubPacketTileEntityFluidUpdate extends SubPacketTileEntityChild {

	public int position;
	public FluidStack fluid;

	public SubPacketTileEntityFluidUpdate() {
		super(SubPacketTileEntityType.FLUID_UPDATE);
	}

	public SubPacketTileEntityFluidUpdate(int position, FluidStack fluid) {

		super(SubPacketTileEntityType.FLUID_UPDATE);
		this.fluid = fluid;
		this.position = position;
	}

	@Override
	public void writeData(ByteBuf data) throws IOException {
		data.writeInt(this.position);
		NBTTagCompound tag = new NBTTagCompound();
		if (this.fluid != null) {
			this.fluid.writeToNBT(tag);
			tag.setBoolean("null", false);
		} else {
			tag.setBoolean("null", true);
		}
		ByteBufUtils.writeTag(data, tag);
	}

	@Override
	public void readData(ByteBuf data) throws IOException {
		this.position = data.readInt();
		NBTTagCompound fluidTag = ByteBufUtils.readTag(data);
		if (fluidTag.getBoolean("null")) {
			this.fluid = null;
		} else {
			this.fluid = FluidStack.loadFluidStackFromNBT(fluidTag);
		}
	}

	@Override
	public void execute(PacketMultiTileEntity manager, EntityPlayer player) {
		TileEntity tileEntity = this.parent.tileEntity;

		if (tileEntity != null) {
			if (tileEntity instanceof IFluidStackProxy) {
				((IFluidStackProxy) tileEntity).setFluid(this.position, this.fluid);
			}
		}
	}
}
