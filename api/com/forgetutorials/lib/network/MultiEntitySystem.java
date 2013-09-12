package com.forgetutorials.lib.network;

import com.forgetutorials.core.proxy.CommonProxy;
import com.forgetutorials.lib.registry.DescriptorBlock;
import com.forgetutorials.lib.registry.MetaMaterial;
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

@Mod(modid = "MES_API", name = "MultiEntitySystem API", version = "v1")
@NetworkMod(channels = { "MES" }, clientSideRequired = true, serverSideRequired = false, packetHandler = MultiEntitySystem.class)
public class MultiEntitySystem implements IPacketHandler {

	public static int infernosMultiBlockID = 2888;
	public static InfernosMultiBlock infernosMultiBlock;

	@SidedProxy(clientSide = "com.forgetutorials.core.proxy.ClientProxy", serverSide = "com.forgetutorials.core.proxy.CommonProxy")
	public static CommonProxy proxy;

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		InfernosPacket infernosPacket = PacketType.buildPacket(packet.data);
		infernosPacket.execute(manager, player);
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(">> MES_API: preInit");
		MultiEntitySystem.infernosMultiBlock = new InfernosMultiBlock(MultiEntitySystem.infernosMultiBlockID, MetaMaterial.metaMaterial);
		new DescriptorBlock().setTool("metaHammer", 1).registerBlock("forgetutorials.MultiEntityBlock", MultiEntitySystem.infernosMultiBlock.getLocalizedName(),
				new ItemStack(MultiEntitySystem.infernosMultiBlock));
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println(">> MES_API: init");
		MultiEntitySystem.proxy.initizeRendering();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println(">> MES_API: postInit");
		MultiEntitySystem.proxy.registerTileEntities();
	}
}
