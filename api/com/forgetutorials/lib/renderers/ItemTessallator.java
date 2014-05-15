package com.forgetutorials.lib.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemTessallator {

	private static CustomItemRenderer customItemRenderer;

	public static void renderItemStack(World world, ItemStack ghostStack, double x, double y, double z) {
		if (RenderManager.instance.renderEngine == null) {
			return; // rendering engine is still not initized!
		}
		if (ItemTessallator.customItemRenderer == null) {
			ItemTessallator.customItemRenderer = new CustomItemRenderer();
		}
		if (ghostStack != null) {
			if (world == null) {
				world = Minecraft.getMinecraft().theWorld;
			}
			EntityItem ghostEntityItem = new EntityItem(world, x, y, z);
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
		// if (gui){
		// ItemTessallator.customItemRenderer.renderItemIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().renderEngine,
		// ghostEntityItem.getEntityItem(), 0, 0);
		// }else{
		ItemTessallator.customItemRenderer.doRender(ghostEntityItem, 0, 0, 0, 0, 0);
		// }
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_BLEND);
	}
}
