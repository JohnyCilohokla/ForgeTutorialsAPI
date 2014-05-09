package com.forgetutorials.core.asm;

public enum ASMCallbackPosition {
	// @formatter:off
	FIRST(false),
	FIRST_AUTO(false),
	LAST(false),
	BEFORE_METHOD(true),
	AFTER_METHOD(true),
	BEFORE2_METHOD(true),
	BEFORE3_METHOD(true),
	BEFORE_AUTO_METHOD(true);
	// @formatter:on

	public final boolean needsTarget;

	private ASMCallbackPosition(boolean needTarget) {
		this.needsTarget = needTarget;
	}
}
