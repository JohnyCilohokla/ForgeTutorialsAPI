package com.forgetutorials.lib.utilities;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;

public class FakeBlockAccess implements IBlockAccess {

	@Override
	public boolean isBlockSolidOnSide(int x, int y, int z, ForgeDirection side, boolean _default) {
		return false;
	}

	@Override
	public int isBlockProvidingPowerTo(int i, int j, int k, int l) {
		return 0;
	}

	@Override
	public boolean isBlockOpaqueCube(int i, int j, int k) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(int i, int j, int k) {
		return false;
	}

	@Override
	public boolean isAirBlock(int i, int j, int k) {
		return true;
	}

	@Override
	public Vec3Pool getWorldVec3Pool() {
		return null;
	}

	@Override
	public int getLightBrightnessForSkyBlocks(int i, int j, int k, int l) {
		return 15;
	}

	@Override
	public float getLightBrightness(int i, int j, int k) {
		return 1;
	}

	@Override
	public int getHeight() {
		return 0;
	}

	@Override
	public float getBrightness(int i, int j, int k, int l) {
		return 1;
	}

	@Override
	public TileEntity getBlockTileEntity(int i, int j, int k) {
		return null;
	}

	@Override
	public int getBlockMetadata(int i, int j, int k) {
		return 0;
	}

	@Override
	public Material getBlockMaterial(int i, int j, int k) {
		return Material.air;
	}

	@Override
	public int getBlockId(int i, int j, int k) {
		return 0;
	}

	@Override
	public BiomeGenBase getBiomeGenForCoords(int i, int j) {
		return BiomeGenBase.plains;
	}

	@Override
	public boolean extendedLevelsInChunkCache() {
		return false;
	}

	@Override
	public boolean doesBlockHaveSolidTopSurface(int i, int j, int k) {
		return false;
	}
}
