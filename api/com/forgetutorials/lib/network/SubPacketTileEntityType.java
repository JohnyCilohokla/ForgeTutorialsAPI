package com.forgetutorials.lib.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

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
	ITEM_UPDATE(SubPacketTileEntitySimpleItemUpdate.class), FLUID_UPDATE(SubPacketTileEntityFluidUpdate.class);

	private Class<? extends SubPacketTileEntityChild> _class;

	SubPacketTileEntityType(Class<? extends SubPacketTileEntityChild> _class) {
		this._class = _class;
	}

	public static SubPacketTileEntityChild buildPacket(byte[] data) {

		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		int selector = bis.read();
		DataInputStream dis = new DataInputStream(bis);

		SubPacketTileEntityChild packet = null;

		try {
			packet = SubPacketTileEntityType.values()[selector]._class.newInstance();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		packet.readPopulate(dis);

		return packet;
	}

	public static SubPacketTileEntityChild newPacket(PacketType type) {

		SubPacketTileEntityChild packet = null;

		try {
			packet = SubPacketTileEntityType.values()[type.ordinal()]._class.newInstance();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		return packet;
	}
}
