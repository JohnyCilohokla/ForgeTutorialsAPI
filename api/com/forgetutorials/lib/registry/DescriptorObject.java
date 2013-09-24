package com.forgetutorials.lib.registry;

public class DescriptorObject {
	protected boolean registered;
	private String unlocalizedName;
	private String name;

	public DescriptorObject() {
		this.registered = false;
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
		ForgeTutorialsRegistry.INSTANCE.addObject(unlocalizedName,this);
	}

}
