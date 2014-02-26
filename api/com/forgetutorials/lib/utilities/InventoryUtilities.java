package com.forgetutorials.lib.utilities;

import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class InventoryUtilities {

	public static IInventory getInventoryAtLocation(World world, double x, double y, double z) {
		IInventory iinventory = null;
		int i = MathHelper.floor_double(x);
		int j = MathHelper.floor_double(y);
		int k = MathHelper.floor_double(z);
		TileEntity tileentity = world.getTileEntity(i, j, k);

		if ((tileentity != null) && (tileentity instanceof IInventory)) {
			iinventory = (IInventory) tileentity;
		}

		if (iinventory == null) {
			@SuppressWarnings("rawtypes")
			List list = world.getEntitiesWithinAABBExcludingEntity((Entity) null, AxisAlignedBB.getAABBPool().getAABB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D),
					IEntitySelector.selectInventories);

			if ((list != null) && (list.size() > 0)) {
				iinventory = (IInventory) list.get(world.rand.nextInt(list.size()));
			}
		}

		return iinventory;
	}

	public static IInventory getInventoryAbove(World world, double x, double y, double z) {
		return InventoryUtilities.getInventoryAtLocation(world, x, y + 1.0D, z);
	}

	public static IInventory getInventoryBelow(World world, double x, double y, double z) {
		return InventoryUtilities.getInventoryAtLocation(world, x, y - 1.0D, z);
	}
}
