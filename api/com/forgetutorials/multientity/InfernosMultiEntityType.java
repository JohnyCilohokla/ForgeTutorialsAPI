package com.forgetutorials.multientity;

public enum InfernosMultiEntityType {
	BASIC(InfernosMultiEntity.class), INVENTORY(InfernosMultiEntityInv.class), LIQUID(InfernosMultiEntityLiq.class), BOTH(InfernosMultiEntityInvLiq.class);

	private Class<? extends InfernosMultiEntity> _class;

	InfernosMultiEntityType(Class<? extends InfernosMultiEntity> _class) {
		this._class = _class;
	}

	public static InfernosMultiEntity newMultiEntity(InfernosMultiEntityType type) {

		InfernosMultiEntity packet = null;

		try {
			packet = InfernosMultiEntityType.values()[type.ordinal()]._class.newInstance();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		return packet;
	}
}
