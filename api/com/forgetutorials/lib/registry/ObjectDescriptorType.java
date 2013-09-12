package com.forgetutorials.lib.registry;

public enum ObjectDescriptorType {
	OBJECT(DescriptorObject.class), ITEM(DescriptorItem.class), BLOCK(DescriptorBlock.class), FLUID(DescriptorFluid.class), ORE(DescriptorOreBlock.class);
	Class<?> objectDescpriptClass;

	private ObjectDescriptorType(Class<?> objectDescpriptClass) {
		this.objectDescpriptClass = objectDescpriptClass;
	}

	public Class<?> getObjectDescpriptClass() {
		return this.objectDescpriptClass;
	}
}
