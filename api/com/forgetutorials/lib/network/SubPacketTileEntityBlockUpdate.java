package com.forgetutorials.lib.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import com.forgetutorials.lib.utilities.IFluidStackProxy;

import cpw.mods.fml.common.network.Player;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

/**
 * MetaTech Craft
 * 
 * As the packet system was based on Pahimar's EE3 packet system it is licensed
 * by LGPL v3 I have modified it greatly splitting the Tile Entity packet into
 * Main packet (x,y,z) Simple Item Update subpacket (pos, item) Fluid Update
 * subpacket (pos, fluidTag) more to come
 * 
 * @author johnycilohokla
 * @license Lesser GNU Public License v3 (http://www.gnu.org/licenses/lgpl.html)
 * 
 */

public class SubPacketTileEntityBlockUpdate extends SubPacketTileEntityChild {

	public SubPacketTileEntityBlockUpdate() {
		super(SubPacketTileEntityType.BLOCK_UPDATE);
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
	}

	@Override
	public void execute(INetworkManager manager, Player player) {
		TileEntity tileEntity = this.parent.tileEntity;

		if (tileEntity != null) {
			tileEntity.worldObj.markBlockForRenderUpdate(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
		}
	}
}
