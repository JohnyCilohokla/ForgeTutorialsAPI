package com.forgetutorials.lib;

import com.forgetutorials.core.proxy.CommonProxy;
import com.forgetutorials.lib.network.InfernosPacketHandler;
import com.forgetutorials.lib.registry.DescriptorBlock;
import com.forgetutorials.lib.registry.MetaMaterial;
import com.forgetutorials.lib.utilities.ForgeRegistryUtilities;
import com.forgetutorials.multientity.InfernosMultiBlockOpaque;
import com.forgetutorials.multientity.InfernosMultiBlockTranslucent;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION)
public class FTA {
	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY_CLASS, serverSide = ModInfo.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static InfernosPacketHandler packetHandler = new InfernosPacketHandler();

	private static int infernosMultiBlockID = 2888;
	private static int infernosMultiBlockOpaqueID = 2889;
	public static InfernosMultiBlockTranslucent infernosMultiBlockTranslucent;
	public static InfernosMultiBlockOpaque infernosMultiBlockOpaque;

	public static ForgeRegistryUtilities registry = new ForgeRegistryUtilities("mes", ModInfo.MOD_ID);

	public static FTAHandler serverHandler = null;

	public FTAEventHandler eventHandeler = new FTAEventHandler();

	@Instance
	public static FTA INSTANCE;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(">> MES_API: preInit");
		FMLCommonHandler.instance().bus().register(FTA.INSTANCE.eventHandeler);
		FTA.infernosMultiBlockTranslucent = new InfernosMultiBlockTranslucent(FTA.infernosMultiBlockID, MetaMaterial.metaMaterial);
		FTA.infernosMultiBlockOpaque = new InfernosMultiBlockOpaque(FTA.infernosMultiBlockOpaqueID, MetaMaterial.metaMaterial);
		new DescriptorBlock().setTool("metaHammer", 1).registerBlock("forgetutorials.MultiEntityBlock", FTA.infernosMultiBlockTranslucent.getLocalizedName(),
				new ItemStack(FTA.infernosMultiBlockTranslucent));
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		System.out.println(">> MES_API: serverStarting");
		FTA.serverHandler = new FTAHandler();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println(">> MES_API: init");
		FTA.proxy.initizeRendering();
		FTA.proxy.initize();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println(">> MES_API: postInit");
		FTA.proxy.registerTileEntities();
	}

	public static void out(String string) {
		System.out.println(string);
	}

	public static FTAHandler getServerHandler() {
		return FTA.serverHandler != null ? FTA.serverHandler : (FTA.serverHandler = new FTAHandler());
	}

}
