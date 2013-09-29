package com.forgetutorials.lib.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.forgetutorials.lib.utilities.CustomItemRenderer;

public class ItemTessallator {

	private static final CustomItemRenderer customItemRenderer = new CustomItemRenderer();

	public static void renderItemStack(World world, ItemStack ghostStack) {
		if (ghostStack != null) {
			EntityItem ghostEntityItem = new EntityItem(world);
			ghostEntityItem.hoverStart = 0.0F;
			ghostEntityItem.setEntityItemStack(ghostStack);
			float scale = 1.0f;
			if (ghostStack.getItem() instanceof ItemBlock) {
				scale = 0.9f;
			} else {
				scale = 0.6f;
			}
			GL11.glScalef(scale, scale, scale);
			ItemTessallator.renderEntityItem(ghostEntityItem);
		}
	}

	public static void renderEntityItem(EntityItem ghostEntityItem) {
		ItemTessallator.customItemRenderer.doRenderItem(ghostEntityItem, 0, 0, 0, 0, 0);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_BLEND);
	}
}
