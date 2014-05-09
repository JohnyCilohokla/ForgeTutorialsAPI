package com.forgetutorials.multientity.renderers;

import org.lwjgl.opengl.GL11;

import com.forgetutorials.lib.FTA;
import com.forgetutorials.lib.registry.InfernosRegisteryProxyEntity;
import com.forgetutorials.multientity.InfernosMultiItem;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class InfernosMultiItemRenderer implements IItemRenderer {
	
	private static EntityRenderer entityRenderer = null;

	public static void initizeRenderer() {
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(FTA.infernosMultiBlockTranslucent), new InfernosMultiItemRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(FTA.infernosMultiBlockOpaque), new InfernosMultiItemRenderer());
		InfernosMultiItemRenderer.entityRenderer = Minecraft.getMinecraft().entityRenderer;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	public static void disableLightmap() {
		InfernosMultiItemRenderer.entityRenderer.disableLightmap(0);
	}

	/**
	 * Enable lightmap in secondary texture unit
	 */
	public static void enableLightmap() {
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {
		InfernosProxyEntityBase entity = InfernosRegisteryProxyEntity.INSTANCE.getStaticMultiEntity(((InfernosMultiItem) (itemStack.getItem()))
				.getProxyEntity(itemStack));
		if (entity != null) {
			GL11.glPushMatrix();
			if (type.equals(ItemRenderType.ENTITY)){
				GL11.glTranslated(-0.5, 0.0, -0.5);
			}
			entity.renderItem(type, itemStack, data);
			GL11.glPopMatrix();
			GL11.glEnable(GL11.GL_LIGHTING);
		}
	}

}
