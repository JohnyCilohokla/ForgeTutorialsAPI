package com.forgetutorials.core;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class ASMInjector implements IFMLLoadingPlugin {
	public class ASMClass {
		public String name;

		public HashMap<String, ASMMethod> methodMap = new HashMap<String, ASMMethod>();

		public ASMClass(String name) {
			this.name = name;
		}
	}

	public class ASMMethod {
		public String name;
		public String desc;

		public ASMMethod(String name, String desc) {
			super();
			this.name = name;
			this.desc = desc;
		}

	}

	static public HashMap<String, ASMClass> classMap = new HashMap<String, ASMClass>();

	@Override
	public String[] getASMTransformerClass() {
		System.out.println("FTA >> ASM >> Setting up classMap");

		File mcpToNotch = new File("S:\\minecraft\\mcp\\forge\\build\\unpacked\\confmcp-notch.srg");

		ASMClass renderAllRenderListsClass = new ASMClass("bls");
		renderAllRenderListsClass.methodMap.put("renderAllRenderLists(ID)V", new ASMMethod("a", "(ID)V"));
		ASMInjector.classMap.put("net/minecraft/client/renderer/RenderGlobal".replace("/", "."), renderAllRenderListsClass);

		ASMClass worldRendererClass = new ASMClass("blg");
		worldRendererClass.methodMap.put("preRenderBlocks(I)V", new ASMMethod("b", "(I)V"));
		worldRendererClass.methodMap.put("postRenderBlocks(ILnet/minecraft/entity/EntityLivingBase;)V", new ASMMethod("a", "(ILrh;)V"));
		ASMInjector.classMap.put("net/minecraft/client/renderer/WorldRenderer".replace("/", "."), worldRendererClass);

		System.out.println("FTA >> ASM >> Setting up ASMTransformerClass");
		return new String[] { ASMTransformer.class.getName() };
	}

	@Override
	public String getModContainerClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
