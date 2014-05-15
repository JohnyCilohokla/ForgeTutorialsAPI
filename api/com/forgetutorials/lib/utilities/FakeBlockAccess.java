package com.forgetutorials.lib.utilities;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3Pool;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;

public class FakeBlockAccess implements IBlockAccess {
	int x;
	int y;
	int z;
	Block block;

	public FakeBlockAccess(int x, int y, int z, Block block) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
	}

	public Block blockOrAir(int x, int y, int z) {
		return ((this.x == x) && (this.y == y) && (this.z == z)) ? this.block : Blocks.air;
	}

	@Override
	public int isBlockProvidingPowerTo(int i, int j, int k, int l) {
		return 0;
	}

	@Override
	public boolean isAirBlock(int i, int j, int k) {
		return blockOrAir(i, j, k) == Blocks.air;
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
	public int getHeight() {
		return 0;
	}

	@Override
	public int getBlockMetadata(int i, int j, int k) {
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
	public Block getBlock(int var1, int var2, int var3) {
		return blockOrAir(var1, var2, var3);
	}

	@Override
	public TileEntity getTileEntity(int var1, int var2, int var3) {
		return null;
	}

	@Override
	public boolean isSideSolid(int x, int y, int z, ForgeDirection side, boolean _default) {
		return blockOrAir(x, y, z).isSideSolid(this, x, y, z, side);
	}
}
