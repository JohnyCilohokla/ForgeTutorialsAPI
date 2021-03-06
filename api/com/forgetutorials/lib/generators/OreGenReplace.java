package com.forgetutorials.lib.generators;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.forgetutorials.lib.registry.DescriptorOreBlock;

import net.minecraft.block.Block;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.ChunkProviderHell;

/**
 * This class is used for storing ore generation data. If you are too lazy to generate your own ores, you can do {@link #OreGenerator.ORES_TO_GENERATE.add()} to
 * add your ore to the list of ores to generate.
 * 
 * @author Calclavia
 * 
 */
public class OreGenReplace extends OreGenBase {

	public int minGenerateLevel;
	public int maxGenerateLevel;
	public int amountPerChunk;
	public int amountPerBranch;
	public Block replaceBlock;

	/**
	 * Dimensions to ignore ore generation
	 */
	public boolean ignoreSurface = false;
	public boolean ignoreNether = true;
	public boolean ignoreEnd = true;

	public OreGenReplace(DescriptorOreBlock ore, Block replaceBlock, int minGenerateLevel, int maxGenerateLevel, int amountPerChunk, int amountPerBranch) {
		super(ore);
		this.minGenerateLevel = minGenerateLevel;
		this.maxGenerateLevel = maxGenerateLevel;
		this.amountPerChunk = amountPerChunk;
		this.amountPerBranch = amountPerBranch;
		this.replaceBlock = replaceBlock;
	}

	@Override
	public void generate(World world, Random random, int varX, int varZ) {
		try {
			for (int i = 0; i < this.amountPerChunk; i++) {
				int x = varX + random.nextInt(16);
				int z = varZ + random.nextInt(16);
				int y = random.nextInt(Math.max(this.maxGenerateLevel - this.minGenerateLevel, 0)) + this.minGenerateLevel;
				generateReplace(world, random, x, y, z);
			}
		} catch (Exception e) {
			Logger.getLogger("FT_ORE").log(Level.SEVERE, ">> Error: generating ore " + this.ore.getName() + " (" + this.ore.getUnlocalizedName() + ")");
			e.printStackTrace();
		}
	}

	public boolean generateReplace(World par1World, Random par2Random, int par3, int par4, int par5) {
		float var6 = par2Random.nextFloat() * (float) Math.PI;
		double var7 = par3 + 8 + ((MathHelper.sin(var6) * this.amountPerBranch) / 8.0F);
		double var9 = (par3 + 8) - ((MathHelper.sin(var6) * this.amountPerBranch) / 8.0F);
		double var11 = par5 + 8 + ((MathHelper.cos(var6) * this.amountPerBranch) / 8.0F);
		double var13 = (par5 + 8) - ((MathHelper.cos(var6) * this.amountPerBranch) / 8.0F);
		double var15 = (par4 + par2Random.nextInt(3)) - 2;
		double var17 = (par4 + par2Random.nextInt(3)) - 2;

		for (int var19 = 0; var19 <= this.amountPerBranch; ++var19) {
			double var20 = var7 + (((var9 - var7) * var19) / this.amountPerBranch);
			double var22 = var15 + (((var17 - var15) * var19) / this.amountPerBranch);
			double var24 = var11 + (((var13 - var11) * var19) / this.amountPerBranch);
			double var26 = (par2Random.nextDouble() * this.amountPerBranch) / 16.0D;
			double var28 = ((MathHelper.sin((var19 * (float) Math.PI) / this.amountPerBranch) + 1.0F) * var26) + 1.0D;
			double var30 = ((MathHelper.sin((var19 * (float) Math.PI) / this.amountPerBranch) + 1.0F) * var26) + 1.0D;
			int var32 = MathHelper.floor_double(var20 - (var28 / 2.0D));
			int var33 = MathHelper.floor_double(var22 - (var30 / 2.0D));
			int var34 = MathHelper.floor_double(var24 - (var28 / 2.0D));
			int var35 = MathHelper.floor_double(var20 + (var28 / 2.0D));
			int var36 = MathHelper.floor_double(var22 + (var30 / 2.0D));
			int var37 = MathHelper.floor_double(var24 + (var28 / 2.0D));

			for (int var38 = var32; var38 <= var35; ++var38) {
				double var39 = ((var38 + 0.5D) - var20) / (var28 / 2.0D);

				if ((var39 * var39) < 1.0D) {
					for (int var41 = var33; var41 <= var36; ++var41) {
						double var42 = ((var41 + 0.5D) - var22) / (var30 / 2.0D);

						if (((var39 * var39) + (var42 * var42)) < 1.0D) {
							for (int var44 = var34; var44 <= var37; ++var44) {
								double var45 = ((var44 + 0.5D) - var24) / (var28 / 2.0D);

								Block block = par1World.getBlock(var38, var41, var44);
								// int meta = par1World.getBlockMetadata(var38,
								// var41, var44);
								if ((((var39 * var39) + (var42 * var42) + (var45 * var45)) < 1.0D) && (block == this.replaceBlock)) {
									par1World.setBlock(var38, var41, var44, this.ore.getBlock(), this.ore.oreMeta, 2);
								}
							}
						}
					}
				}
			}
		}

		return true;
	}

	@Override
	public boolean isOreGeneratedInWorld(World world, IChunkProvider chunkGenerator) {
		if (!this.shouldGenerate) {
			return false;
		}
		if (this.ignoreSurface && (chunkGenerator instanceof ChunkProviderGenerate)) {
			return false;
		}
		if (this.ignoreNether && (chunkGenerator instanceof ChunkProviderHell)) {
			return false;
		}
		if (this.ignoreEnd && (chunkGenerator instanceof ChunkProviderEnd)) {
			return false;
		}
		return true;
	}
}