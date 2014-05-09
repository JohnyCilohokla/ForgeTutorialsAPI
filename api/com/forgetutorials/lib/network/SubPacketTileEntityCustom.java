package com.forgetutorials.lib.network;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import com.forgetutorials.lib.FTA;
import com.forgetutorials.lib.utilities.ICustomDataReceiver;

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

public class SubPacketTileEntityCustom extends SubPacketTileEntityChild {

	public int position;
	public ByteBuf custom;

	public SubPacketTileEntityCustom() {
		super(SubPacketTileEntityType.CUSTOM);
	}

	public SubPacketTileEntityCustom(ByteBuf data) {
		super(SubPacketTileEntityType.CUSTOM);
		this.custom = data;
	}

	@Override
	public void writeData(ByteBuf data) throws IOException {
		data.writeInt(this.custom.writerIndex());
		data.writeBytes(this.custom);
	}

	@Override
	public void readData(ByteBuf data) throws IOException {

		int lenght = data.readInt();
		this.custom = data.readBytes(lenght);
	}

	@Override
	public void execute(PacketMultiTileEntity manager, EntityPlayer player) {
		TileEntity tileEntity = this.parent.tileEntity;

		if (tileEntity != null) {
			if (tileEntity instanceof ICustomDataReceiver) {
				FTA.out("execute");

				((ICustomDataReceiver) tileEntity).onDataReceived(this.custom);
			}
		}
	}
}
