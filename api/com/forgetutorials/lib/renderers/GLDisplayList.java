package com.forgetutorials.lib.renderers;

import org.lwjgl.opengl.GL11;

public class GLDisplayList {
	int displayListID = 0;
	static int currentDisplayListID = 0;

	public GLDisplayList() {
	}

	public void generate() {
		if ((this.displayListID == 0)) {
			this.displayListID = GL11.glGenLists(1);
		}
	}

	public void delete() {
		if ((this.displayListID != 0)) {
			GL11.glDeleteLists(this.displayListID, 1);
		}
		this.displayListID = 0;
	}

	public void bind() {
		generate();
		if ((GLDisplayList.currentDisplayListID != 0)) {
			System.out.println("DisplayListContainer->bind() error DisplayList already bound! Trying to bind " + this.displayListID + " over "
					+ GLDisplayList.currentDisplayListID);
		}
		GLDisplayList.currentDisplayListID = this.displayListID;
		GL11.glNewList(this.displayListID, GL11.GL_COMPILE);
		GL11.glPushMatrix();
	}

	public boolean isGenerated() {
		return this.displayListID != 0;
	}

	public void unbind() {
		if ((GLDisplayList.currentDisplayListID == 0) || (GLDisplayList.currentDisplayListID != this.displayListID)) {
			System.out.println("DisplayListContainer->unbind() error " + GLDisplayList.currentDisplayListID + "!=" + this.displayListID);
		}
		GL11.glPopMatrix();
		GL11.glEndList();
		GLDisplayList.currentDisplayListID = 0;
	}

	public void render() {
		if ((this.displayListID != 0)) {
			GL11.glCallList(this.displayListID);
		}
	}
}
