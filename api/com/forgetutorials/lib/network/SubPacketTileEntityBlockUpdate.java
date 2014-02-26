package com.forgetutorials.lib.network;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

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
	public void writeData(ByteBuf data) throws IOException {
	}

	@Override
	public void readData(ByteBuf data) throws IOException {
	}

	@Override
	public void execute(PacketMultiTileEntity manager, EntityPlayer player) {
		TileEntity tileEntity = this.parent.tileEntity;

		if (tileEntity != null) {
			tileEntity.getWorldObj().markBlockForUpdate(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
		}
	}
}
