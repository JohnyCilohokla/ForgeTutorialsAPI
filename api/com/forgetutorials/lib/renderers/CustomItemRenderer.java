package com.forgetutorials.lib.renderers;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;

class CustomItemRenderer extends RenderItem {
	public CustomItemRenderer() {
		setRenderManager(RenderManager.instance);
	}

	@Override
	public boolean shouldBob() {

		return false;
	};
}
