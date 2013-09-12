package com.forgetutorials.lib.registry;

import java.util.HashMap;

public enum MetaTechRegistry {
	INSTANCE();
	private HashMap<String, DescriptorObject> objects = new HashMap<String, DescriptorObject>();

	public DescriptorObject getObject(String name) {
		return this.objects.get(name);
	}

	public void addObject(String name, DescriptorObject object) {
		this.objects.put(name, object);
	}

}
