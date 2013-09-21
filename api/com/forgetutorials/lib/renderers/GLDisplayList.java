package com.forgetutorials.lib.renderers;

import static org.lwjgl.opengl.GL11.GL_COMPILE;

import org.lwjgl.opengl.GL11;

public class GLDisplayList {
	int displayListID = 0;
	static int currentDisplayListID = 0;

	public GLDisplayList() {
	}

	public void generate() {
		if ((displayListID == 0)) {
			displayListID = GL11.glGenLists(1);
		}
	}

	public void delete() {
		if ((displayListID != 0)) {
			GL11.glDeleteLists(displayListID, 1);
		}
		displayListID = 0;
	}

	public void bind() {
		generate();
		if ((currentDisplayListID != 0)) {
			System.out.println("DisplayListContainer->bind() error DisplayList already bound! Trying to bind " + displayListID + " over "
					+ currentDisplayListID);
		}
		currentDisplayListID = displayListID;
		GL11.glNewList(displayListID, GL_COMPILE);
		GL11.glPushMatrix();
	}

	public boolean isGenerated() {
		return displayListID != 0;
	}

	public void unbind() {
		if ((currentDisplayListID == 0) || (currentDisplayListID != displayListID)) {
			System.out.println("DisplayListContainer->unbind() error " + currentDisplayListID + "!=" + displayListID);
		}
		GL11.glPopMatrix();
		GL11.glEndList();
		currentDisplayListID = 0;
	}

	public void render() {
		if ((displayListID != 0)) {
			GL11.glCallList(displayListID);
		}
	}
}
