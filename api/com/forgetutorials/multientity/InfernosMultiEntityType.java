package com.forgetutorials.multientity;

/**
 * Static and dynamic refers to the renderer type Static - use only
 * renderStaticBlockAt Dynamic - use renderStaticBlockAt and renderTileEntityAt
 * 
 * @author Johny
 * 
 */
public enum InfernosMultiEntityType {
	STATIC_BASIC(InfernosMultiEntityStatic.class), STATIC_INVENTORY(InfernosMultiEntityStaticInv.class), STATIC_LIQUID(InfernosMultiEntityStaticLiq.class), STATIC_BOTH(
			InfernosMultiEntityStaticInvLiq.class), DYNAMIC_BASIC(InfernosMultiEntityDynamic.class), DYNAMIC_INVENTORY(InfernosMultiEntityDynamicInv.class), DYNAMIC_LIQUID(
			InfernosMultiEntityDynamicLiq.class), DYNAMIC_BOTH(InfernosMultiEntityDynamicInvLiq.class);

	private Class<? extends InfernosMultiEntityStatic> _class;

	InfernosMultiEntityType(Class<? extends InfernosMultiEntityStatic> _class) {
		this._class = _class;
	}

	public static InfernosMultiEntityStatic newMultiEntity(InfernosMultiEntityType type) {

		InfernosMultiEntityStatic entity = null;

		try {
			entity = InfernosMultiEntityType.values()[type.ordinal()]._class.newInstance();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

		return entity;
	}
}
