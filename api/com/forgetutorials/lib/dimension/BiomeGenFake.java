package com.forgetutorials.lib.dimension;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenFake extends BiomeGenBase {

	public BiomeGenFake(int par1, String name, float temperature, float rainfall) {
		super(par1);
		setTemperatureRainfall(temperature, rainfall);
		setBiomeName(name);
	}

}
