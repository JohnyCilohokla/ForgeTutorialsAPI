package com.forgetutorials.lib.renderers;

import net.minecraft.util.EnumFacing;

import org.lwjgl.opengl.GL11;

public class FTAOpenGL {

	static public void glRotate(EnumFacing facing, boolean center) {
		if (center) {
			GL11.glTranslated(0.5, 0.0, 0.5);
		}
		if (facing != null) {
			GL11.glTranslated(0.0, 0.5, 0.0);
			switch (facing) {
			case DOWN:
				break;
			case UP:
				GL11.glRotated(180, 1, 0, 0);// up-side down
				break;
			case NORTH:
				GL11.glRotated(90, 1, 0, 0);// north
				break;
			case SOUTH:
				GL11.glRotated(90, -1, 0, 0);// south
				break;
			case EAST:
				GL11.glRotated(90, 0, 0, -1);// east
				break;
			case WEST:
				GL11.glRotated(90, 0, 0, 1);// west
				break;
			default:
				break;

			}
			GL11.glTranslated(0.0, -0.5, 0.0);
		}
	}

	static public void glRotate(int facing) {
		FTAOpenGL.glRotate(EnumFacing.getFront(facing), true);
	}

	static public void glRotate(EnumFacing facing) {
		FTAOpenGL.glRotate(facing, true);
	}

	static public void glRotate(int facing, boolean center) {
		FTAOpenGL.glRotate(EnumFacing.getFront(facing), center);
	}

}
