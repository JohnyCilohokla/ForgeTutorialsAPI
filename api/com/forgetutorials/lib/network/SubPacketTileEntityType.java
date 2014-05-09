package com.forgetutorials.lib.network;

import io.netty.buffer.ByteBuf;

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

public enum SubPacketTileEntityType {
	ITEM_UPDATE(SubPacketTileEntitySimpleItemUpdate.class), FLUID_UPDATE(SubPacketTileEntityFluidUpdate.class), BLOCK_UPDATE(
			SubPacketTileEntityBlockUpdate.class), CUSTOM(
					SubPacketTileEntityCustom.class);

	private Class<? extends SubPacketTileEntityChild> _class;

	SubPacketTileEntityType(Class<? extends SubPacketTileEntityChild> _class) {
		this._class = _class;
	}

	public static SubPacketTileEntityChild buildPacket(ByteBuf data) {

		int selector = data.readByte();

		SubPacketTileEntityChild packet = null;

		try {
			packet = SubPacketTileEntityType.values()[selector]._class.newInstance();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		packet.readPopulate(data);

		return packet;
	}
}
