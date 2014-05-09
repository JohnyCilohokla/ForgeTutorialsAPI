package com.forgetutorials.lib.dimension.utilities;

import net.minecraft.block.Block;

public class IncrementalBlock extends IncrementalObject {
	public BlockWithMetadata block;
	public int metadata;

	public IncrementalBlock(BlockWithMetadata block, long start) {
		super(start);
		this.block = block;
	}
	public IncrementalBlock(BlockWithMetadata block) {
		super(1);
		this.block = block;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Block.getIdFromBlock(block.block);
		result = prime * result + block.block.getUnlocalizedName().hashCode();
		result = prime * result + block.metadata;
		return result;
	}

	public boolean areItemStacksEqual(BlockWithMetadata block1, BlockWithMetadata block2) {
		return block1.block.getUnlocalizedName().equals(block2.block.getUnlocalizedName()) && block1.metadata == block2.metadata;
	}

	@Override
	public boolean equals(Object obj) {
		IncrementalBlock other = (IncrementalBlock) obj;
		if (!areItemStacksEqual(this.block, other.block))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return block.toString() +" "+ metadata;
	}
}
