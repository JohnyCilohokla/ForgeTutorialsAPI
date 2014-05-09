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
		targetMethodName = null;
		targetMethodDesc = null;
		targetMethodOwner = null;
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

		return (methodName.equalsIgnoreCase(node.name) && (methodDesc == null || methodDesc.equalsIgnoreCase(node.desc)));
	}

	public boolean compareTargetMethodName(MethodInsnNode node) {
		return ((targetMethodName.equalsIgnoreCase(node.name)) && (targetMethodDesc == null || targetMethodDesc.equalsIgnoreCase(node.desc)) && (targetMethodOwner == null || targetMethodOwner
				.equalsIgnoreCase(node.owner)));
	}

	public boolean needsTarget() {
		return this.position.needsTarget;
	}

	@Override
	public String toString() {
		return "[position=" + position + ", methodName=" + methodName + ", methodDesc=" + methodDesc + ", targetMethodName="
				+ targetMethodName + ", targetMethodDesc=" + targetMethodDesc + ", targetMethodOwner=" + targetMethodOwner + "]";
	}

	public ASMCallbackTarget copy(String name, String desc) {
		return new ASMCallbackTarget(name, desc, position, targetMethodName, targetMethodDesc, targetMethodOwner);
	}
}
