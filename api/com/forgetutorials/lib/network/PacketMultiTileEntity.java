package com.forgetutorials.lib.network;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

import com.forgetutorials.multientity.InfernosMultiEntityStatic;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class PacketMultiTileEntity extends InfernosPacket {

	public int x, y, z, side;
	public String entityName;
	TileEntity tileEntity;
	private final List<SubPacketTileEntityChild> children;

	public PacketMultiTileEntity() {
		super();
		this.children = new ArrayList<SubPacketTileEntityChild>();
	}

	public PacketMultiTileEntity(int x, int y, int z, int side, String entityName) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.side = side;
		this.entityName = entityName;
		this.children = new ArrayList<SubPacketTileEntityChild>();
	}

	public void addPacket(SubPacketTileEntityChild packet) {
		this.children.add(packet);
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeInt(this.x);
		buffer.writeInt(this.y);
		buffer.writeInt(this.z);
		buffer.writeInt(this.side);
		byte[] nameBytes = this.entityName.getBytes();
		int nameLenght = nameBytes.length;
		buffer.writeInt(nameLenght);
		buffer.writeBytes(nameBytes);
		buffer.writeInt(this.children.size());
		for (SubPacketTileEntityChild packet : this.children) {
			ByteBuf bytes = packet.populate();
			buffer.writeInt(bytes.writerIndex());
			buffer.writeBytes(bytes,0,bytes.writerIndex());
		}
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		this.x = buffer.readInt();
		this.y = buffer.readInt();
		this.z = buffer.readInt();
		this.side = buffer.readInt();
		int nameLenght = buffer.readInt();
		byte[] nameBytes = new byte[nameLenght];
		buffer.readBytes(nameBytes);
		this.entityName = new String(nameBytes);
		int childrenCount = buffer.readInt();
		for (int i = 0; i < childrenCount; i++) {
			int childLenght = buffer.readInt();
			
			ByteBuf buf = Unpooled.buffer(childLenght);
			buffer.readBytes(buf, childLenght);
			SubPacketTileEntityChild child = SubPacketTileEntityType.buildPacket(buf);
			child.parent = this;
			addPacket(child);
		}
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		World world = player.worldObj;
		this.tileEntity = world.getTileEntity(this.x, this.y, this.z);
		if (this.tileEntity instanceof InfernosMultiEntityStatic) {
			InfernosMultiEntityStatic multiEntity = (InfernosMultiEntityStatic) this.tileEntity;
			multiEntity.newEntity(this.entityName);
			multiEntity.setSide(this.side);
		}

		for (SubPacketTileEntityChild packet : this.children) {
			packet.execute(this, player);
		}
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
	}
}
