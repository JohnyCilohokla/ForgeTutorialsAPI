package com.forgetutorials.multientity.renderers;

import com.forgetutorials.lib.FTA;
import com.forgetutorials.lib.registry.InfernosRegisteryProxyEntity;
import com.forgetutorials.multientity.InfernosMultiItem;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;

import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class InfernosMultiItemRenderer implements IItemRenderer {

	public static void initizeRenderer() {
		MinecraftForgeClient.registerItemRenderer(FTA.infernosMultiBlockID, new InfernosMultiItemRenderer());
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemStack, Object... data) {
		InfernosProxyEntityBase entity = InfernosRegisteryProxyEntity.INSTANCE.getStaticMultiEntity(((InfernosMultiItem) (itemStack.getItem()))
				.getProxyEntity(itemStack));
		if (entity != null) {
			entity.renderItem(type);
		}
	}

}
