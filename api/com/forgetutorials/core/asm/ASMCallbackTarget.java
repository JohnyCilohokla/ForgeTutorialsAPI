package com.forgetutorials.core.asm;

import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class ASMCallbackTarget {

	final ASMCallbackPosition position;
	final String methodName;
	final String methodDesc;

	final String targetMethodName;
	final String targetMethodDesc;
	final String targetMethodOwner;

	public ASMCallbackTarget(String methodName, String methodDesc, ASMCallbackPosition position) {
		super();
		this.methodName = methodName;
		this.methodDesc = methodDesc;
		this.position = position;
		if ((this.position.needsTarget)) {
			throw new UnsupportedOperationException("Target method is needed!");
		}
		this.targetMethodName = null;
		this.targetMethodDesc = null;
		this.targetMethodOwner = null;
	}

	public ASMCallbackTarget(String methodName, String methodDesc, ASMCallbackPosition position, String targetMethodName, String targetMethodDesc,
			String targetMethodOwner) {
		super();
		this.position = position;
		this.methodName = methodName;
		this.methodDesc = methodDesc;
		this.targetMethodName = targetMethodName;
		this.targetMethodDesc = targetMethodDesc;
		this.targetMethodOwner = targetMethodOwner;
	}

	public boolean compareToMethodName(MethodNode node) {

		return (this.methodName.equalsIgnoreCase(node.name) && ((this.methodDesc == null) || this.methodDesc.equalsIgnoreCase(node.desc)));
	}

	public boolean compareTargetMethodName(MethodInsnNode node) {
		return ((this.targetMethodName.equalsIgnoreCase(node.name)) && ((this.targetMethodDesc == null) || this.targetMethodDesc.equalsIgnoreCase(node.desc)) && ((this.targetMethodOwner == null) || this.targetMethodOwner
				.equalsIgnoreCase(node.owner)));
	}

	public boolean needsTarget() {
		return this.position.needsTarget;
	}

	@Override
	public String toString() {
		return "[position=" + this.position + ", methodName=" + this.methodName + ", methodDesc=" + this.methodDesc + ", targetMethodName="
				+ this.targetMethodName + ", targetMethodDesc=" + this.targetMethodDesc + ", targetMethodOwner=" + this.targetMethodOwner + "]";
	}

	public ASMCallbackTarget copy(String name, String desc) {
		return new ASMCallbackTarget(name, desc, this.position, this.targetMethodName, this.targetMethodDesc, this.targetMethodOwner);
	}
}
