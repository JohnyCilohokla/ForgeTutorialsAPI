package com.forgetutorials.core.proxy;

import com.forgetutorials.multientity.renderers.InfernosMultiBlockRenderer;
import com.forgetutorials.multientity.renderers.InfernosMultiEntityRenderer;

public class ClientProxy extends CommonProxy {

	@Override
	public void initizeRendering() {
		InfernosMultiEntityRenderer.initizeRenderer();
		InfernosMultiBlockRenderer.initizeRenderer();
	}

}
