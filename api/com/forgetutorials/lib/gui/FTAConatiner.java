package com.forgetutorials.lib.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class FTAConatiner extends Container {

	public FTAConatiner() {

	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(inventoryPlayer, j + (i * 9) + 9, 45 + (j * 18), 56 + 84 + (i * 18)));
			}
		}

		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 45 + (i * 18), 56 + 142));
		}
	}

}
