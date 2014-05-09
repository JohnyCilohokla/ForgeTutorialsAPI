package com.forgetutorials.lib;

import java.io.File;
import java.util.HashMap;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import com.forgetutorials.lib.dimension.utilities.WorldGlobals;

public class FTAHandler {
	
	private final File dir;
	private final HashMap<World, WorldGlobals> worldGlobals = new HashMap<World, WorldGlobals>();

	public FTAHandler() {
		this.dir=new File(DimensionManager.getCurrentSaveRootDirectory(), "FTA");
		this.dir.mkdirs();
	}
	
	public File getFile(String path){
		File file = new File(dir, path);
		file.getParentFile().mkdirs();
		return file;
	}
	
	public File getFile(WorldServer world, String path){
		File file = new File(new File(world.getChunkSaveLocation(),"FTA"),path);
		file.getParentFile().mkdirs();
		return file;
	}
	
	public WorldGlobals getWorldGlobals(World world){
		if (!worldGlobals.containsKey(world)){
			worldGlobals.put(world, new WorldGlobals(world));
		}
		return worldGlobals.get(world);
	}

}
