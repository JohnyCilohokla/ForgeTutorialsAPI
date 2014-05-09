package com.forgetutorials.core.asm;

import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;


public class ASMHelper {

	// "com/forgetutorials/core/ASMTransformer"
	// "onPostRender"


	public static InsnList createNewStaticCallback(String path, String method) {
		InsnList toInject = new InsnList();

		//toInject.add(new VarInsnNode(Opcodes.ALOAD, aLoad));
		toInject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, path.replace(".", "/"), method, "()V"));

		return toInject;
	}

	public static boolean injectCode(MethodNode m, ASMCallbackTarget target, AbstractInsnNode targetNode, ASMCallbackPatch patch) {
		try {
			if (target.position == ASMCallbackPosition.FIRST) {
				m.instructions.insertBefore(m.instructions.getFirst(), patch.toInject);
				return true;
			} else if (target.position == ASMCallbackPosition.FIRST_AUTO) {
				targetNode = m.instructions.getFirst();
				while (targetNode.getNext() != null && targetNode.getNext().getOpcode() == -1) {
					// skip until you find a non-label node
					targetNode = targetNode.getNext();
				}
				m.instructions.insert(targetNode, patch.toInject);
				return true;
			} else if (target.position == ASMCallbackPosition.LAST) {
				targetNode = m.instructions.getLast();
				while (targetNode.getPrevious() != null && (targetNode = targetNode.getPrevious()).getOpcode() != 177) {
					// skip until return!
				}
				m.instructions.insertBefore(targetNode, patch.toInject);
				return true;
			}
			if (targetNode == null) {
				return false;
			}

			if (target.position == ASMCallbackPosition.AFTER_METHOD) {
				m.instructions.insert(targetNode, patch.toInject);
				return true;
			} else if (target.position == ASMCallbackPosition.BEFORE_METHOD) {
				m.instructions.insertBefore(targetNode, patch.toInject);
				return true;
			} else if (target.position == ASMCallbackPosition.BEFORE2_METHOD) {
				m.instructions.insertBefore(targetNode.getPrevious(), patch.toInject);
				return true;
			} else if (target.position == ASMCallbackPosition.BEFORE3_METHOD) {
				m.instructions.insertBefore(targetNode.getPrevious().getPrevious(), patch.toInject);
				return true;
			} else if (target.position == ASMCallbackPosition.BEFORE_AUTO_METHOD) {
				while (targetNode.getPrevious() != null && (targetNode = targetNode.getPrevious()).getOpcode() != -1) {
					// go back until you find a label node, makes sure it will not inject into assigments/parameters
				}
				m.instructions.insertBefore(targetNode, patch.toInject);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
