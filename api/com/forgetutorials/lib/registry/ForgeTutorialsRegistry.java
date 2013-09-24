package com.forgetutorials.lib.registry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public enum ForgeTutorialsRegistry {
	INSTANCE();
	private HashMap<String, DescriptorObject> objects = new HashMap<String, DescriptorObject>();
	private HashMap<CreativeTabs,ArrayList<ItemStack>> tabsList = new HashMap<CreativeTabs,ArrayList<ItemStack>>();

	public DescriptorObject getObject(String name) {
		return this.objects.get(name);
	}

	public void addObject(String name, DescriptorObject object) {
		this.objects.put(name, object);
	}

	public void getSubItems(int id, CreativeTabs creativeTab, List<ItemStack> list) {
		if (tabsList.containsKey(creativeTab)){
			for(ItemStack itemStack : tabsList.get(creativeTab)){
					list.add(itemStack);
			}
		}
	}
	
	public void addToCreativeTab(CreativeTabs tab, ItemStack itemStack){
		if (!tabsList.containsKey(tab)){
			tabsList.put(tab, new ArrayList<ItemStack>());
		}
		tabsList.get(tab).add(itemStack);
	}
	
	public CreativeTabs[] getCreativeTabs() {
		return tabsList.keySet().toArray(new CreativeTabs[tabsList.keySet().size()]);
	}
}
