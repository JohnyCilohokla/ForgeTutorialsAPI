package com.forgetutorials.lib.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.forgetutorials.multientity.InfernosMultiEntity;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.Player;

import net.minecraft.network.INetworkManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketMultiTileEntity extends InfernosPacket {

	public int x, y, z;
	public String entityName;
	TileEntity tileEntity;
	private final List<SubPacketTileEntityChild> children;

	public PacketMultiTileEntity() {
		super(PacketType.MULTIPACKET_TILE_ENTITY, true);
		this.children = new ArrayList<SubPacketTileEntityChild>();
	}

	public PacketMultiTileEntity(int x, int y, int z, String entityName) {
		super(PacketType.MULTIPACKET_TILE_ENTITY, true);
		this.x = x;
		this.y = y;
		this.z = z;
		this.entityName = entityName;
		this.children = new ArrayList<SubPacketTileEntityChild>();
	}

	@Override
	public void writeData(DataOutputStream data) throws IOException {
		data.writeInt(this.x);
		data.writeInt(this.y);
		data.writeInt(this.z);
		data.writeUTF(this.entityName);
		data.writeInt(this.children.size());
		for (SubPacketTileEntityChild packet : this.children) {
			byte[] bytes = packet.populate();
			data.writeInt(bytes.length);
			data.write(bytes);
		}
	}

	@Override
	public void readData(DataInputStream data) throws IOException {
		this.x = data.readInt();
		this.y = data.readInt();
		this.z = data.readInt();
		this.entityName = data.readUTF();
		int childrenCount = data.readInt();
		for (int i = 0; i < childrenCount; i++) {
			int childLenght = data.readInt();
			byte[] bytes = new byte[childLenght];
			data.read(bytes);
			SubPacketTileEntityChild child = SubPacketTileEntityType.buildPacket(bytes);
			child.parent = this;
			addPacket(child);
		}

	}

	@Override
	public void execute(INetworkManager manager, Player player) {
		World world = FMLClientHandler.instance().getClient().theWorld;
		this.tileEntity = world.getBlockTileEntity(this.x, this.y, this.z);
		if (this.tileEntity instanceof InfernosMultiEntity) {
			InfernosMultiEntity multiEntity = (InfernosMultiEntity) this.tileEntity;
			multiEntity.newEntity(this.entityName);
		}

		for (SubPacketTileEntityChild packet : this.children) {
			packet.execute(manager, player);
		}
	}

	public void addPacket(SubPacketTileEntityChild packet) {
		this.children.add(packet);
	}
}
