package com.forgetutorials.core.asm;

import java.util.ArrayList;

import org.objectweb.asm.tree.InsnList;

import com.forgetutorials.core.ASMInjector;
import com.forgetutorials.core.ASMInjector.ASMClass;
import com.forgetutorials.core.ASMInjector.ASMMethod;

public class ASMCallbackPatch {
	public ArrayList<String> methodsNames = new ArrayList<String>();
	public ArrayList<ASMCallbackTarget> targetMethodsNodes = new ArrayList<ASMCallbackTarget>();
	public InsnList toInject;

	public ASMCallbackPatch(String name, InsnList toInject, ASMCallbackTarget target) {
		System.out.println("FTA >> ASM >> Setting up " + name + " patch");
		this.toInject = toInject;
		this.methodsNames.add(name);
		this.targetMethodsNodes.add(target);
		if (ASMInjector.classMap.containsKey(name)) {
			ASMClass classMapping = ASMInjector.classMap.get(name);
			this.methodsNames.add(classMapping.name);
			System.out.println(classMapping.name);

			if (classMapping.methodMap.containsKey(target.methodName + target.methodDesc)) {
				ASMMethod methodMapping = classMapping.methodMap.get(target.methodName + target.methodDesc);
				System.out.println(methodMapping.name);
				this.targetMethodsNodes.add(target.copy(methodMapping.name, methodMapping.desc));
			} else {
				System.out.println("FTA >> ASM >> Couldn't find obf " + target.methodName + target.methodDesc + " method name");
			}

		} else {
			System.out.println("FTA >> ASM >> Couldn't find obf " + name + " name");
		}

		/*if (target.methodName!=null&&ASMTransformer.classMap.containsKey(target.targetMethodOwner)) {
			ASMClass methodClassMapping = ASMTransformer.classMap.get(target.targetMethodOwner);
			if (methodClassMapping.methodMap.containsKey(name)) {
				ASMMethod methodMapping = methodClassMapping.methodMap.get(target.targetMethodName + target.targetMethodDesc);
				FTA.out(methodMapping.name);
				this.targetMethodsNodes.add(target.copy(methodMapping.name, methodMapping.desc));
			} else {
				FTA.out("FTA >> ASM >> Couldn't find obf " + target.targetMethodName + target.targetMethodDesc + " method name");
			}
		} else {
			FTA.out("FTA >> ASM >> Couldn't find obf " + target.targetMethodOwner + " method class name");
		}*/
	}

	@Override
	public String toString() {
		String out = "ASMCallbackPatch {";

		out += "METHODS: ";
		for (String methodsName : this.methodsNames) {
			out += "[" + methodsName + "]";
		}
		out += "TARGETS: ";
		for (ASMCallbackTarget targetMethodsNode : this.targetMethodsNodes) {
			out += "[" + targetMethodsNode + "]";
		}

		out += "}";
		return out;
	}
}
