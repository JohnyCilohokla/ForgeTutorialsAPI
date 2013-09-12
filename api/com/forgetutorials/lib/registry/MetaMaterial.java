package com.forgetutorials.lib.registry;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MetaMaterial extends Material {

	public static MetaMaterial metaMaterial = new MetaMaterial(MapColor.tntColor);

	public MetaMaterial(MapColor par1MapColor) {
		super(par1MapColor);
		setRequiresTool();
	}

}
