package com.forgetutorials.multientity.renderers;

import org.lwjgl.opengl.GL11;

import com.forgetutorials.lib.FTA;
import com.forgetutorials.lib.registry.InfernosRegisteryProxyEntity;
import com.forgetutorials.multientity.InfernosMultiItem;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class InfernosMultiItemRenderer implements IItemRenderer {

	private static EntityRenderer entityRenderer = null;

	public static void initizeRenderer() {
		MinecraftForgeClient.registerItemRenderer(FTA.infernosMultiBlockID, new InfernosMultiItemRenderer());
		entityRenderer = Minecraft.getMinecraft().entityRenderer;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}
	
	
	
    public static void disableLightmap()
    {
        entityRenderer.disableLightmap(0);
    }

    /**
     * Enable lightmap in secondary texture unit
     */
    public static void enableLightmap()
    {
        entityRenderer.enableLightmap(0);
    }
    
	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {
		InfernosProxyEntityBase entity = InfernosRegisteryProxyEntity.INSTANCE.getStaticMultiEntity(((InfernosMultiItem) (itemStack.getItem()))
				.getProxyEntity(itemStack));
		if (entity != null) {
			entity.renderItem(type,data);
		}
	}

}
