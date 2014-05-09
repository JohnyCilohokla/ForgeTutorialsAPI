package com.forgetutorials.lib.dimension;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.world.ChunkPosition;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class SingleBiomeChunkManager extends WorldChunkManager {

	private final BiomeGenBase biome;
	private final float metaWorldRainfall;

	public SingleBiomeChunkManager(BiomeGenBase biome) {
		this.biome = biome;
		this.metaWorldRainfall = biome.rainfall;
	}

/*	@Override
	public float[] getTemperatures(float[] temperatureArray, int x, int y, int width, int height) {
		if ((temperatureArray == null) || (temperatureArray.length < (width * height))) {
			temperatureArray = new float[width * height];
		}

		Arrays.fill(temperatureArray, 0, width * height, this.temperature);
		return temperatureArray;
	}*/

	@Override
	public float[] getRainfall(float[] rainfallArray, int x, int y, int width, int height) {
		if ((rainfallArray == null) || (rainfallArray.length < (width * height))) {
			rainfallArray = new float[width * height];
		}

		Arrays.fill(rainfallArray, 0, width * height, this.metaWorldRainfall);
		return rainfallArray;
	}

	@Override
	public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] biomesArray, int x, int y, int width, int height, boolean cacheFlag) {
		return loadBlockGeneratorData(biomesArray, x, y, width, height);
	}

	@Override
	public BiomeGenBase getBiomeGenAt(int x, int y) {
		return this.biome;
	}

	@Override
	public BiomeGenBase[] loadBlockGeneratorData(BiomeGenBase[] biomesArray, int x, int y, int width, int height) {
		if ((biomesArray == null) || (biomesArray.length < (width * height))) {
			biomesArray = new BiomeGenBase[width * height];
		}

		Arrays.fill(biomesArray, 0, width * height, this.biome);
		return biomesArray;
	}

	@Override
	public ChunkPosition findBiomePosition(int par1, int par2, int par3, @SuppressWarnings("rawtypes") List biomesList, Random par5Random) {
		return biomesList.contains(this.biome) ? new ChunkPosition((par1 - par3) + par5Random.nextInt((par3 * 2) + 1), 0, (par2 - par3)
				+ par5Random.nextInt((par3 * 2) + 1)) : null;
	}

	@Override
	public boolean areBiomesViable(int par1, int par2, int par3, @SuppressWarnings("rawtypes") List biomesList) {
		return biomesList.contains(this.biome);
	}

}
