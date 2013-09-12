package com.forgetutorials.lib.utilities;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;

public class CustomItemRenderer extends RenderItem {
	public CustomItemRenderer() {
		setRenderManager(RenderManager.instance);
	}

	@Override
	public boolean shouldBob() {

		return false;
	};
}
