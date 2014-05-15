package com.forgetutorials.lib.dimension.utilities;

import net.minecraft.block.Block;

public class BlockWithMetadata {
	public Block block;
	public int metadata;

	public BlockWithMetadata(Block block, int metadata) {
		this.block = block;
		this.metadata = metadata;
	}
}
