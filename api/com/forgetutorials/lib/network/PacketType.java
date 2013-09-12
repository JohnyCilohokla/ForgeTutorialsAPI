package com.forgetutorials.lib.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import com.forgetutorials.lib.ModInfo;

import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;

public enum PacketType {
	MULTIPACKET_TILE_ENTITY(PacketMultiTileEntity.class, null);

	private Class<? extends InfernosPacket> _class;
	protected final PacketType parent;

	PacketType(Class<? extends InfernosPacket> _class, PacketType parent) {
		this._class = _class;
		this.parent = parent;
	}

	public static InfernosPacket buildPacket(byte[] data) {

		ByteArrayInputStream bis = new ByteArrayInputStream(data);
		int selector = bis.read();
		DataInputStream dis = new DataInputStream(bis);

		InfernosPacket packet = null;

		try {
			packet = PacketType.values()[selector]._class.newInstance();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		packet.readPopulate(dis);

		return packet;
	}

	public static InfernosPacket newPacket(PacketType type) {

		InfernosPacket packet = null;

		try {
			packet = PacketType.values()[type.ordinal()]._class.newInstance();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		return packet;
	}

	public static Packet populatePacket(InfernosPacket infernosPacket) {

		byte[] data = infernosPacket.populate();

		Packet250CustomPayload packet250 = new Packet250CustomPayload();
		packet250.channel = ModInfo.CHANNEL_ID;
		packet250.data = data;
		packet250.length = data.length;
		packet250.isChunkDataPacket = infernosPacket.isChunkDataPacket;

		return packet250;
	}
}