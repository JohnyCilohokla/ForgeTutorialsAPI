package com.forgetutorials.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import com.forgetutorials.core.asm.ASMCallbackPatch;
import com.forgetutorials.core.asm.ASMCallbackPosition;
import com.forgetutorials.core.asm.ASMCallbackTarget;
import com.forgetutorials.core.asm.ASMHelper;
import com.forgetutorials.lib.FTA;

import net.minecraft.launchwrapper.IClassTransformer;

public class ASMTransformer implements IClassTransformer {
	
	ArrayList<ASMCallbackPatch> patches = new ArrayList<ASMCallbackPatch>();

	public ASMTransformer() {
		// @formatter:off
		patches.add(new ASMCallbackPatch("net.minecraft.client.renderer.WorldRenderer",//"blg",
				ASMHelper.createNewStaticCallback("com.forgetutorials.lib.FTAEventHandler", "onChunkDLPostRender"),
				new ASMCallbackTarget("postRenderBlocks", "(ILnet/minecraft/entity/EntityLivingBase;)V", ASMCallbackPosition.BEFORE_METHOD, "glEndList",null,null)//,
				//new ASMCallbackTarget("a", "(ILrh;)V", ASMCallbackPosition.BEFORE_METHOD, "glEndList",null,null)
		));
		patches.add(new ASMCallbackPatch("net.minecraft.client.renderer.WorldRenderer",//"blg",
				ASMHelper.createNewStaticCallback("com.forgetutorials.lib.FTAEventHandler", "onChunkDLPreRender"),
				new ASMCallbackTarget("preRenderBlocks", "(I)V", ASMCallbackPosition.FIRST_AUTO)//,
				//new ASMCallbackTarget("b", "(I)V", ASMCallbackPosition.FIRST)
		));
		patches.add(new ASMCallbackPatch("net.minecraft.client.renderer.RenderGlobal",//"bls",
				ASMHelper.createNewStaticCallback("com.forgetutorials.lib.FTAEventHandler", "onWorldRenderBlocks"),
				new ASMCallbackTarget("renderAllRenderLists", "(ID)V", ASMCallbackPosition.LAST)//,
				//new ASMCallbackTarget("a", "(ID)V", ASMCallbackPosition.LAST)
		));
		// @formatter:on

	}

	@Override
	public byte[] transform(String arg0, String arg1, byte[] arg2) {
		for (ASMCallbackPatch patch : patches) {
			for (String patchName : patch.methodsNames) {
				if (arg0.equalsIgnoreCase(patchName)) {
					FTA.out("FTA >> ASM >> AMS TRANSFORMER >> " + arg0);
					arg2 = patchClassASM(arg0, arg2, patch);
				}
			}
		}
		return arg2;
	}

	public byte[] patchClassASM(String name, byte[] bytes, ASMCallbackPatch patch) {

		ClassReader classReader = new ClassReader(bytes);
		ClassNode classNode = new ClassNode();
		classReader.accept(classNode, ClassReader.EXPAND_FRAMES);

		Iterator<MethodNode> methods = classNode.methods.iterator();
		boolean found = false;
		while (methods.hasNext()) {
			MethodNode m = methods.next();
			found = found || transformMethod(m, patch);
		}
		if (!found) {
			FTA.out("FTA >> ASM >> Failed to find target method!");
			FTA.out(patch.toString());
			FTA.out("FTA >> ASM >> Listing all possible methods");
			methods = classNode.methods.iterator();
			while (methods.hasNext()) {
				MethodNode m = methods.next();
				FTA.out("FTA >> ASM >> " + m.name + " " + m.desc);
			}
		}

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS/* | ClassWriter.COMPUTE_FRAMES*/);
		classNode.accept(writer);
		return writer.toByteArray();
	}

	public boolean transformMethod(MethodNode m, ASMCallbackPatch patch) {
		boolean found = false;

		for (ASMCallbackTarget target : patch.targetMethodsNodes) {

			if (target.compareToMethodName(m)) {
				found = true;

				AbstractInsnNode currentNode = null;
				AbstractInsnNode targetNode = null;

				Iterator<AbstractInsnNode> iter = m.instructions.iterator();

				if (target.needsTarget()) {
					while (iter.hasNext()) {
						currentNode = iter.next();
						if (currentNode instanceof MethodInsnNode) {
							MethodInsnNode methodNode = (MethodInsnNode) currentNode;
							if (target.compareTargetMethodName(methodNode)) {
								targetNode = currentNode;
							}
						}

					}
				}

				if (targetNode != null || !target.needsTarget()) {
					if (ASMHelper.injectCode(m, target, targetNode, patch)){
						FTA.out("FTA >> ASM >> Injection Complete!");
						currentNode = null;
						iter = m.instructions.iterator();
						while (iter.hasNext()) {
							currentNode = iter.next();
							FTA.out("FTA >> ASM >> " + currentNode.getOpcode() + " " + currentNode.getType() + " " + currentNode.toString());
							if (currentNode instanceof MethodInsnNode) {
								MethodInsnNode methodNode = (MethodInsnNode) currentNode;
								FTA.out("FTA >> ASM >> " + methodNode.desc + " " + methodNode.name + " " + methodNode.owner);
							}
						}
					}else{
						FTA.out("FTA >> ASM >> Injection Failed " + target + "!");
					}
				} else {

					currentNode = null;
					iter = m.instructions.iterator();
					for (ASMCallbackTarget dTarget : patch.targetMethodsNodes) {
					FTA.out("FTA >> ASM >> Failed to find target " + dTarget + "!");
					}
					FTA.out("FTA >> ASM >> Listing all possible targets:");
					while (iter.hasNext()) {
						currentNode = iter.next();
						if (currentNode instanceof MethodInsnNode) {
							MethodInsnNode methodNode = (MethodInsnNode) currentNode;
							FTA.out("FTA >> ASM >> " + methodNode.desc + " " + methodNode.name + " " + methodNode.owner);
						}
					}
				}

				iter = m.instructions.iterator();
			}
		}
		return found;
	}
}
