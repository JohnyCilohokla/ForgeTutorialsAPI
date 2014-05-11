package com.forgetutorials.lib;

import net.minecraft.client.Minecraft;

public class FTAEventHandler {
	
	static int i1=0;
	static int i2=0;
	static int i3=0;

	public static void onWorldRenderBlocks() {
		// TODO Auto-generated method stub
		//FTA.out("FTAEventHandler >> onWorldRenderBlocks()");

		if (Minecraft.getMinecraft().thePlayer != null) {
			if (i1%10000==0){
			Minecraft.getMinecraft().thePlayer.sendChatMessage("FTAEventHandler >> onWorldRenderBlocks()");
			}
			i1++;
		}
	}

	public static void onChunkDLPostRender() {
		//FTA.out("FTAEventHandler >> onChunkDLPostRender()!");
		if (Minecraft.getMinecraft().thePlayer != null) {
			if (i2%10000==0){
			Minecraft.getMinecraft().thePlayer.sendChatMessage("FTAEventHandler >> onChunkDLPostRender()");
			}
			i2++;
		}
	}

	public static void onChunkDLPreRender() {
		//FTA.out("FTAEventHandler >> onChunkDLPreRender()!");
		if (Minecraft.getMinecraft().thePlayer != null) {
			if (i3%10000==0){
			Minecraft.getMinecraft().thePlayer.sendChatMessage("FTAEventHandler >> onChunkDLPreRender()");
			}
			i3++;
		}
	}
	/*
	    @SubscribeEvent(priority = EventPriority.HIGHEST)
	    public void onEntityJoinWorld(ClientConnectedToServerEvent event)
	    {
	    	FTA.out(">> onEntityJoinWorld "+event.connectionType);
	    	
	    	//FTA.clientHandler = new FTAHandler(Minecraft.getMinecraft().thePlayer.);
	    }
	*/

}
