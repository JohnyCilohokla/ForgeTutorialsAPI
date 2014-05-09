package com.forgetutorials.lib.network;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

import cpw.mods.fml.common.network.ByteBufUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
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

public class SubPacketTileEntitySimpleItemUpdate extends SubPacketTileEntityChild {

	public int position;
	public ItemStack item;

	public SubPacketTileEntitySimpleItemUpdate() {
		super(SubPacketTileEntityType.ITEM_UPDATE);
	}

	public SubPacketTileEntitySimpleItemUpdate(int position, ItemStack item) {
		super(SubPacketTileEntityType.ITEM_UPDATE);
		this.position = position;
		this.item = item;
	}

	@Override
	public void writeData(ByteBuf data) throws IOException {
		data.writeInt(this.position);
		ByteBufUtils.writeItemStack(data, this.item);
	}

	@Override
	public void readData(ByteBuf data) throws IOException {

		this.position = data.readInt();
		this.item = ByteBufUtils.readItemStack(data);
	}

	@Override
	public void execute(PacketMultiTileEntity manager, EntityPlayer player) {
		TileEntity tileEntity = this.parent.tileEntity;

		if (tileEntity != null) {
			if (tileEntity instanceof IInventory) {
				((IInventory) tileEntity).setInventorySlotContents(this.position, this.item);
			}
		}
	}
}
