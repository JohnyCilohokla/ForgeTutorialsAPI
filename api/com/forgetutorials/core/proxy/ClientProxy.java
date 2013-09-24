package com.forgetutorials.core.proxy;

import com.forgetutorials.multientity.renderers.InfernosMultiBlockRenderer;
import com.forgetutorials.multientity.renderers.InfernosMultiEntityRenderer;
import com.forgetutorials.multientity.renderers.InfernosMultiItemRenderer;

public class ClientProxy extends CommonProxy {

	@Override
	public void initizeRendering() {
		InfernosMultiEntityRenderer.initizeRenderer();
		InfernosMultiBlockRenderer.initizeRenderer();
		InfernosMultiItemRenderer.initizeRenderer();
	}

}
