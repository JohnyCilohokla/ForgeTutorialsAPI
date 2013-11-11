package com.forgetutorials.lib.utilities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.forgetutorials.multientity.InfernosMultiEntity;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;

public class ProxyEntityUtils {
	// Proxy Entities
	public static InfernosProxyEntityBase getAbove(World world, int x, int y, int z) {
		TileEntity taget = world.getBlockTileEntity(x, y + 1, z);
		return (taget instanceof InfernosMultiEntity) ? ((InfernosMultiEntity) taget).getProxyEntity(false) : null;
	}

	public static InfernosProxyEntityBase getBelow(World world, int x, int y, int z) {
		TileEntity taget = world.getBlockTileEntity(x, y - 1, z);
		return (taget instanceof InfernosMultiEntity) ? ((InfernosMultiEntity) taget).getProxyEntity(false) : null;
	}

	public static InfernosProxyEntityBase getToDirection(World world, int x, int y, int z, EnumFacing direction) {
		TileEntity taget = world.getBlockTileEntity(x + direction.getFrontOffsetX(), y + direction.getFrontOffsetY(), z + direction.getFrontOffsetZ());
		return (taget instanceof InfernosMultiEntity) ? ((InfernosMultiEntity) taget).getProxyEntity(false) : null;
	}

	public static InfernosProxyEntityBase getToDirection(TileEntity tileEntity, EnumFacing direction) {
		if (tileEntity == null) {
			System.out.println(">> MES getToDirection() tileEntity == null!!! ");
			return null;
		}
		TileEntity taget = tileEntity.worldObj.getBlockTileEntity(tileEntity.xCoord + direction.getFrontOffsetX(),
				tileEntity.yCoord + direction.getFrontOffsetY(), tileEntity.zCoord + direction.getFrontOffsetZ());
		return (taget instanceof InfernosMultiEntity) ? ((InfernosMultiEntity) taget).getProxyEntity(false) : null;
	}

	public static InfernosProxyEntityBase getAbove(TileEntity tileEntity) {
		return ProxyEntityUtils.getToDirection(tileEntity, EnumFacing.UP);
	}
}
