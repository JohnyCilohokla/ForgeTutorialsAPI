package com.forgetutorials.lib.registry;

import java.util.HashMap;

public class DescriptorObject {
	protected boolean registered;
	private String unlocalizedName;
	private String name;

	private HashMap<String, Object> custom;

	public DescriptorObject() {
		this.registered = false;
		this.custom = new HashMap<String, Object>();
	}

	public String getName() {
		return this.name;
	}

	public String getUnlocalizedName() {
		return this.unlocalizedName;
	}

	public ObjectDescriptorType getType() {
		return ObjectDescriptorType.OBJECT;
	}

	void register(String unlocalizedName, String name) {
		this.unlocalizedName = unlocalizedName;
		this.name = name;
		this.registered = true;
		ForgeTutorialsRegistry.INSTANCE.addObject(unlocalizedName, this);
		System.out.println(">>Registery(FTA)<< Register object: " + unlocalizedName + " [" + name + "]");
	}

	public void setCustom(String string, Object object) {
		this.custom.put(string, object);
	}

	public Object getCustom(String string) {
		return this.custom.get(string);
	}

	public int getCustomInt(String string, int def) {
		return this.custom.containsKey(string) ? (Integer) getCustom(string) : def;
	}

	public int getCustomInt(String string) {
		return this.custom.containsKey(string) ? (Integer) getCustom(string) : -1;
	}
}
