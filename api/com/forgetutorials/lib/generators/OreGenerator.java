package com.forgetutorials.lib.generators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import cpw.mods.fml.common.registry.GameRegistry;

public class OreGenerator implements IWorldGenerator {
	public static boolean isInitiated = false;

	/**
	 * Add your ore data to this list of ores for it to automatically generate! No hassle indeed!
	 */
	private static final List<OreGenBase> ORES_TO_GENERATE = new ArrayList<OreGenBase>();

	/**
	 * Adds an ore to the ore generate list. Do this in pre-init.
	 */
	public static void addOre(OreGenBase data) {
		if (!OreGenerator.isInitiated) {
			GameRegistry.registerWorldGenerator(new OreGenerator(), 100);
			GameRegistry.registerWorldGenerator(ChunkScanner.INSTANCE, Integer.MAX_VALUE);
			OreGenerator.isInitiated = true;
		}

		OreGenerator.ORES_TO_GENERATE.add(data);
	}

	/**
	 * Checks to see if this ore exists
	 * 
	 * @param oreName
	 * @return
	 */
	public static boolean oreExists(String oreName) {
		for (OreGenBase ore : OreGenerator.ORES_TO_GENERATE) {
			if (ore.ore.oreDictionaryName == oreName) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Removes an ore to the ore generate list. Do this in init.
	 */
	public static void removeOre(OreGenBase data) {
		OreGenerator.ORES_TO_GENERATE.remove(data);
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		chunkX = chunkX << 4;
		chunkZ = chunkZ << 4;

		Iterator<OreGenBase> it = OreGenerator.ORES_TO_GENERATE.iterator();
		while (it.hasNext()) {
			OreGenBase oreData = it.next();
			if (oreData.shouldGenerate && oreData.isOreGeneratedInWorld(world, chunkGenerator)) {
				oreData.generate(world, rand, chunkX, chunkZ);
			}
		}
	}
}