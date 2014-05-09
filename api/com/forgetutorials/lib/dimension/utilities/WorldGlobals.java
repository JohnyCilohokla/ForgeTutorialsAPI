package com.forgetutorials.lib.dimension.utilities;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.imageio.stream.FileImageInputStream;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import com.forgetutorials.lib.FTA;

import cpw.mods.fml.common.network.ByteBufUtils;

public class WorldGlobals {

	private final IncrementalMap<IncrementalItemStack> dropsMap = new IncrementalMap<IncrementalItemStack>();
	private final IncrementalMap<IncrementalBlock> blocksMap = new IncrementalMap<IncrementalBlock>();
	private final IncrementalMap<IncrementalItemStack> pickMap = new IncrementalMap<IncrementalItemStack>();
	private final World world;

	public WorldGlobals(World world) {
		this.world = world;

		File file = FTA.serverHandler.getFile((WorldServer) this.world, "genMap.bin");
		if (file.exists()) {
			ByteBuf data = Unpooled.buffer();
			ByteBufOutputStream out = new ByteBufOutputStream(data);
			FileImageInputStream in = null;
			try {
				in = new FileImageInputStream(file);
				byte[] tmp = new byte[4 * 1024];
				int length = 0;

				while ((length = in.read(tmp)) != -1) {
					out.write(tmp, 0, length);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FTA.out("Loading 0 "+file.getAbsolutePath());
			if (data.writerIndex() > 4) {
				int length = data.readInt();
				FTA.out("Loading 1 ("+data.writerIndex()+","+(length + 4)+")");
				if (data.writerIndex() >= length + 4) {
					FTA.out("Loading 2");
					dropsMap.counter.clear();
					blocksMap.counter.clear();
					int count = data.readInt();
					for (int i = 0; i < count; i++) {
						long amount = data.readLong();
						ItemStack stack = ByteBufUtils.readItemStack(data);
						dropsMap.add(new IncrementalItemStack(stack, amount));
					}
					count = data.readInt();
					for (int i = 0; i < count; i++) {
						long amount = data.readLong();
						
						Block block = Block.getBlockById(data.readInt());
						int metadata = data.readInt();
						
						blocksMap.add(new IncrementalBlock(new BlockWithMetadata(block, metadata),amount));
					}
				}
			}
		}
	}

	public void save() {

		File file = FTA.serverHandler.getFile((WorldServer) this.world, "genMap.bin");
		if (file.exists()) {
			file.delete();
		}
		ByteBuf data = Unpooled.buffer();
		data.writeInt(dropsMap.getList().size());
		for (IncrementalItemStack b : dropsMap.getList()) {
			data.writeLong(b.current);
			ByteBufUtils.writeItemStack(data, b.stack);
		}
		data.writeInt(blocksMap.getList().size());
		for (IncrementalBlock b : blocksMap.getList()) {
			data.writeLong(b.current);
			data.writeInt(Block.getIdFromBlock(b.block.block));
			data.writeInt(b.block.metadata);
		}
		RandomAccessFile raf = null;
		try {
			raf = new RandomAccessFile(file, "rw");
			raf.writeInt(data.writerIndex());
			raf.write(data.array());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				raf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public IncrementalMap<IncrementalItemStack> getDropsMap() {
		return dropsMap;
	}

	public IncrementalMap<IncrementalBlock> getBlocksMap() {
		return blocksMap;
	}

	public IncrementalMap<IncrementalItemStack> getPickMap() {
		return pickMap;
	}


}
