package com.forgetutorials.lib.generators;

import java.util.ArrayList;
import java.util.Random;

import com.forgetutorials.lib.FTA;
import com.forgetutorials.lib.dimension.utilities.BlockWithMetadata;
import com.forgetutorials.lib.dimension.utilities.IncrementalBlock;
import com.forgetutorials.lib.dimension.utilities.IncrementalItemStack;
import com.forgetutorials.lib.dimension.utilities.IncrementalMap;
import com.forgetutorials.lib.dimension.utilities.WorldGlobals;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;

class ChunkScanner implements IWorldGenerator {

	public static ChunkScanner INSTANCE = new ChunkScanner();

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		int sX = chunkX;
		int sZ = chunkZ;
		int eX = sX + 1;
		int eZ = sZ + 1;
		Block block = null;
		int metadata = 0;

		WorldGlobals worldGlobals = FTA.getServerHandler().getWorldGlobals(world);
		IncrementalMap<IncrementalItemStack> dropsMap = FTA.serverHandler.getWorldGlobals(world).getDropsMap();
		IncrementalMap<IncrementalBlock> blocksMap = FTA.serverHandler.getWorldGlobals(world).getBlocksMap();
		IncrementalMap<IncrementalItemStack> pickMap = FTA.serverHandler.getWorldGlobals(world).getPickMap();
		for (int cY = 0; cY < world.getHeight(); cY++) {
			for (int cX = sX * 16; cX < eX * 16; cX++) {
				for (int cZ = sZ * 16; cZ < eZ * 16; cZ++) {
					block = world.getBlock(cX, cY, cZ);
					metadata = world.getBlockMetadata(cX, cY, cZ);

		            
					if (block != null && !block.equals(Blocks.air)) {
						blocksMap.add(new IncrementalBlock(new BlockWithMetadata(block, metadata)));
			            ItemStack itemstack = this.createStackedBlock(block, metadata);
						if (itemstack != null) {
							// FIXME
						//pickMap.add(new IncrementalItemStack(itemstack, 1));
						}
					}
					
					
					// if (world.getBlock(cX, cY, cZ).getHarvestLevel(metadata)
					// != -1) {
					ArrayList<ItemStack> drops = block.getDrops(world, cX, cY, cZ, metadata, 0);

					if (drops != null && drops.size() > 0) {
						for (ItemStack drop : drops) {
							if (drop != null) {
								dropsMap.add(new IncrementalItemStack(drop, drop.stackSize));
							}
						}
					}
					// }
				}
			}
		}
		worldGlobals.save();
	}

    protected ItemStack createStackedBlock(Block block, int metadata)
    {
        int j = 0;
        Item item = Item.getItemFromBlock(block);

        if (item != null && item.getHasSubtypes())
        {
            j = metadata;
        }

        return new ItemStack(item, 1, j);
    }
}