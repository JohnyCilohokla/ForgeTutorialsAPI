package com.forgetutorials.lib.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;

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

public abstract class SubPacketTileEntityChild {

	public SubPacketTileEntityType packetType;
	protected PacketMultiTileEntity parent;

	public SubPacketTileEntityChild(SubPacketTileEntityType type) {
		this.packetType = type;
	}

	public ByteBuf populate() {

		ByteBuf buf = Unpooled.buffer();
		try {
			buf.writeByte(this.packetType.ordinal());
			writeData(buf);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}

		return buf;
	}

	public void readPopulate(ByteBuf data) {

		try {
			readData(data);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}

	public abstract void readData(ByteBuf data) throws IOException;

	public abstract void writeData(ByteBuf dos) throws IOException;

	public abstract void execute(PacketMultiTileEntity network, EntityPlayer player);
}
