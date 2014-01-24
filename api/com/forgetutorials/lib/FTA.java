package com.forgetutorials.lib;

import com.forgetutorials.core.proxy.CommonProxy;
import com.forgetutorials.lib.network.InfernosPacket;
import com.forgetutorials.lib.network.PacketType;
import com.forgetutorials.lib.registry.DescriptorBlock;
import com.forgetutorials.lib.registry.MetaMaterial;
import com.forgetutorials.lib.utilities.ForgeRegistryUtilities;
import com.forgetutorials.multientity.InfernosMultiBlock;

import net.minecraft.item.ItemStack;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.Player;

@Mod(modid = ModInfo.MOD_ID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION)
@NetworkMod(channels = { ModInfo.CHANNEL_ID }, clientSideRequired = true, serverSideRequired = false, packetHandler = FTA.class)
public class FTA implements IPacketHandler {

	@SidedProxy(clientSide = ModInfo.CLIENT_PROXY_CLASS, serverSide = ModInfo.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static int infernosMultiBlockID = 2888;
	public static InfernosMultiBlock infernosMultiBlock;

	public static ForgeRegistryUtilities registry = new ForgeRegistryUtilities("mes", ModInfo.MOD_ID);

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		InfernosPacket infernosPacket = PacketType.buildPacket(packet.data);
		infernosPacket.execute(manager, player);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(">> MES_API: preInit");
		FTA.infernosMultiBlock = new InfernosMultiBlock(FTA.infernosMultiBlockID, MetaMaterial.metaMaterial);
		new DescriptorBlock().setTool("metaHammer", 1).registerBlock("forgetutorials.MultiEntityBlock", FTA.infernosMultiBlock.getLocalizedName(),
				new ItemStack(FTA.infernosMultiBlock));
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println(">> MES_API: init");
		FTA.proxy.initizeRendering();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println(">> MES_API: postInit");
		FTA.proxy.registerTileEntities();
	}
}
