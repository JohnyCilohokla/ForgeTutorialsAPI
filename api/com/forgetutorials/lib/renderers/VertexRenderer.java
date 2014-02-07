package com.forgetutorials.lib.renderers;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.Icon;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import com.forgetutorials.lib.utilities.FakeBlockAccess;

public class VertexRenderer {

	ByteBuffer byteBuffer = BufferUtils.createByteBuffer(1024 * 4 * 8 * 4);
	IntBuffer intBuffer = this.byteBuffer.asIntBuffer();
	FloatBuffer floatBuffer = this.byteBuffer.asFloatBuffer();
	ShortBuffer shortBuffer = this.byteBuffer.asShortBuffer();

	int vertexCount = 0;

	boolean activeUV = false;
	boolean activeST = false;
	boolean activeQP = false;

	boolean activeColor = false;

	public VertexRenderer() {

	}

	// @formatter:off
	public void addQuadUVSTColorLight(
			double X1, double Y1, double Z1, float U1, float V1, float S1, float T1, int LS1, int LB1, int R1, int G1, int B1, int A1,
			double X2, double Y2, double Z2, float U2, float V2, float S2, float T2, int LS2, int LB2, int R2, int G2, int B2, int A2,
			double X3, double Y3, double Z3, float U3, float V3, float S3, float T3, int LS3, int LB3, int R3, int G3, int B3, int A3,
			double X4, double Y4, double Z4, float U4, float V4, float S4, float T4, int LS4, int LB4, int R4, int G4, int B4, int A4
			) {
	// @formatter:on
		addVertexUVSTColorLight(X1, Y1, Z1, U1, V1, S1, T1, (LS1 << 16) | LB1, R1, G1, B1, A1);

		addVertexUVSTColorLight(X2, Y2, Z2, U2, V2, S2, T2, (LS2 << 16) | LB2, R2, G2, B2, A2);

		addVertexUVSTColorLight(X3, Y3, Z3, U3, V3, S3, T3, (LS3 << 16) | LB3, R3, G3, B3, A3);

		addVertexUVSTColorLight(X4, Y4, Z4, U4, V4, S4, T4, (LS4 << 16) | LB4, R4, G4, B4, A4);

	}

	// @formatter:off
	public void addQuadUVSTColor(
			double X1, double Y1, double Z1, float U1, float V1, float S1, float T1, int Q1, int R1, int G1, int B1, int A1,
			double X2, double Y2, double Z2, float U2, float V2, float S2, float T2, int Q2, int R2, int G2, int B2, int A2,
			double X3, double Y3, double Z3, float U3, float V3, float S3, float T3, int Q3, int R3, int G3, int B3, int A3,
			double X4, double Y4, double Z4, float U4, float V4, float S4, float T4, int Q4, int R4, int G4, int B4, int A4
			) {
	// @formatter:on

		// 240 = full light

		addVertexUVSTColorLight(X1, Y1, Z1, U1, V1, S1, T1, Q1, R1, G1, B1, A1);

		addVertexUVSTColorLight(X2, Y2, Z2, U2, V2, S2, T2, Q2, R2, G2, B2, A2);

		addVertexUVSTColorLight(X3, Y3, Z3, U3, V3, S3, T3, Q3, R3, G3, B3, A3);

		addVertexUVSTColorLight(X4, Y4, Z4, U4, V4, S4, T4, Q4, R4, G4, B4, A4);

	}

	private void addVertexUVSTColorLight(double X, double Y, double Z, float U, float V, float S, float T, int Q, int R, int G, int B, int A) {
		this.intBuffer.position(this.vertexCount * VertexRenderer.INTS);
		this.intBuffer.put(Float.floatToRawIntBits((float) X));
		this.intBuffer.put(Float.floatToRawIntBits((float) Y));
		this.intBuffer.put(Float.floatToRawIntBits((float) Z));

		this.intBuffer.put(Float.floatToRawIntBits(U));
		this.intBuffer.put(Float.floatToRawIntBits(V));

		this.intBuffer.put(Q);

		this.intBuffer.put(Float.floatToRawIntBits(S));
		this.intBuffer.put(Float.floatToRawIntBits(T));

		this.intBuffer.put(((255 << 24) | (B << 16) | (G << 8) | R));
		this.vertexCount += 1;
		this.activeUV = true;
		this.activeST = true;
		this.activeQP = true;
		this.activeColor = true;
	}

	public int getVertexCount() {
		return this.vertexCount;
	}

	private static int INTS = 9;
	private static int STRIDE = VertexRenderer.INTS * 4;

	public int render() {

		// @formatter:off
		//
		//             4x4y4z 4u4v 4s4t 4q4p rgba  --
		// pos           0     12   20   24   28   32
		// size          12    8    4    4    4    --
		//
		// @formatter:on

		this.intBuffer.rewind();
		this.floatBuffer.rewind();
		this.shortBuffer.rewind();
		this.byteBuffer.rewind();
		GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
		this.floatBuffer.position(0); // 0 +12
		GL11.glVertexPointer(3, VertexRenderer.STRIDE, this.floatBuffer);

		if (this.activeUV) {
			GL13.glClientActiveTexture(GL13.GL_TEXTURE0);
			GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			this.floatBuffer.position(3); // 12 +8
			GL11.glTexCoordPointer(2, VertexRenderer.STRIDE, this.floatBuffer);
		}
		if (this.activeST) {
			GL13.glClientActiveTexture(GL13.GL_TEXTURE1);
			GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			this.shortBuffer.position(10); // 20 +4
			GL11.glTexCoordPointer(2, VertexRenderer.STRIDE, this.shortBuffer);
		}
		if (this.activeQP) {
			GL13.glClientActiveTexture(GL13.GL_TEXTURE2);
			GL11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			this.floatBuffer.position(6); // 24 +8
			GL11.glTexCoordPointer(2, VertexRenderer.STRIDE, this.floatBuffer);
		}
		if (this.activeColor) {
			GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
			this.byteBuffer.position(32); // 32 +4
			GL11.glColorPointer(4, true, VertexRenderer.STRIDE, this.byteBuffer);
		}

		int oldVertexCount = this.vertexCount;
		if (this.vertexCount > 0) {
			GL11.glDrawArrays(GL11.GL_QUADS, 0, this.vertexCount);
		}
		this.vertexCount = 0;

		if (this.activeQP) {
			GL13.glClientActiveTexture(GL13.GL_TEXTURE2);
			GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		}

		if (this.activeST) {
			GL13.glClientActiveTexture(GL13.GL_TEXTURE1);
			GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		}

		if (this.activeUV) {
			GL13.glClientActiveTexture(GL13.GL_TEXTURE0);
			GL11.glDisableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
		}

		if (this.activeColor) {
			GL11.glDisableClientState(GL11.GL_COLOR_ARRAY);
		}

		GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);

		this.intBuffer.rewind();
		this.floatBuffer.rewind();
		this.shortBuffer.rewind();
		this.byteBuffer.rewind();
		// vertexBuffer.rewind();
		// colorBuffer.rewind();
		// uvBuffer.rewind();
		// stBuffer.rewind();
		this.activeUV = false;
		this.activeST = false;
		this.activeQP = false;

		return oldVertexCount;
	}

	private static RenderBlocks blankRenderBlocks = null;

	public RenderBlocks getValidRenderBlocks(RenderBlocks blockRenderer) {
		if (blockRenderer == null) {
			if (blankRenderBlocks == null) {
				blankRenderBlocks = new RenderBlocks();
				blankRenderBlocks.renderAllFaces = true;
				blankRenderBlocks.blockAccess = new FakeBlockAccess();
			}
			blockRenderer = blankRenderBlocks;
		}
		return blockRenderer;
	}

	public void renderBlockInWorld(Block block, RenderBlocks blockRenderer, int trueX, int trueY, int trueZ, Icon icon, Icon icon2, double x, double y,
			double z, float r, float g, float b) {

		blockRenderer = getValidRenderBlocks(blockRenderer);
		
		blockRenderer.renderMinX = 0.0D;
		blockRenderer.renderMinY = 0.0D;
		blockRenderer.renderMinZ = 0.0D;
		blockRenderer.renderMaxX = 1.0D;
		blockRenderer.renderMaxY = 1.0D;
		blockRenderer.renderMaxZ = 1.0D;

		blockRenderer.enableAO = true;
		int brightness = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY, trueZ);

		if (blockRenderer.renderAllFaces || block.shouldSideBeRendered(blockRenderer.blockAccess, trueX, trueY - 1, trueZ, 0)) {
			renderNegY(block, blockRenderer, trueX, trueY, trueZ, icon, icon2, x, y, z, r, g, b, brightness);
		}

		if (blockRenderer.renderAllFaces || block.shouldSideBeRendered(blockRenderer.blockAccess, trueX, trueY + 1, trueZ, 1)) {
			renderPosY(block, blockRenderer, trueX, trueY, trueZ, icon, icon2, x, y, z, r, g, b, brightness);
		}
		if (blockRenderer.renderAllFaces || block.shouldSideBeRendered(blockRenderer.blockAccess, trueX, trueY, trueZ - 1, 2)) {
			renderNegZ(block, blockRenderer, trueX, trueY, trueZ, icon, icon2, x, y, z, r, g, b, brightness);
		}
		if (blockRenderer.renderAllFaces || block.shouldSideBeRendered(blockRenderer.blockAccess, trueX, trueY, trueZ + 1, 3)) {
			renderPosZ(block, blockRenderer, trueX, trueY, trueZ, icon, icon2, x, y, z, r, g, b, brightness);
		}
		if (blockRenderer.renderAllFaces || block.shouldSideBeRendered(blockRenderer.blockAccess, trueX - 1, trueY, trueZ, 4)) {
			renderNegX(block, blockRenderer, trueX, trueY, trueZ, icon, icon2, x, y, z, r, g, b, brightness);

		}
		if (blockRenderer.renderAllFaces || block.shouldSideBeRendered(blockRenderer.blockAccess, trueX + 1, trueY, trueZ, 5)) {
			renderPosX(block, blockRenderer, trueX, trueY, trueZ, icon, icon2, x, y, z, r, g, b, brightness);
		}
		blockRenderer.enableAO = false;
	}

	public void renderPosX(Block block, RenderBlocks blockRenderer, int trueX, int trueY, int trueZ, Icon icon, Icon icon2, double x, double y, double z,
			float r, float g, float b, int brightness) {
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f3;
		float f4;
		float f5;
		float f6;
		float f7;
		int i1;

		if (blockRenderer.renderMaxX >= 1.0D) {
			++trueX;
		}

		blockRenderer.aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY - 1, trueZ);
		blockRenderer.aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY, trueZ - 1);
		blockRenderer.aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY, trueZ + 1);
		blockRenderer.aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY + 1, trueZ);
		blockRenderer.aoBrightnessXYPN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY - 1, trueZ);
		blockRenderer.aoBrightnessXZPN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY, trueZ - 1);
		blockRenderer.aoBrightnessXZPP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY, trueZ + 1);
		blockRenderer.aoBrightnessXYPP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY + 1, trueZ);
		flag3 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX + 1, trueY + 1, trueZ)];
		flag2 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX + 1, trueY - 1, trueZ)];
		flag5 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX + 1, trueY, trueZ + 1)];
		flag4 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX + 1, trueY, trueZ - 1)];

		if (!flag2 && !flag4) {
			blockRenderer.aoLightValueScratchXYZPNN = blockRenderer.aoLightValueScratchXZPN;
			blockRenderer.aoBrightnessXYZPNN = blockRenderer.aoBrightnessXZPN;
		} else {
			blockRenderer.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY - 1, trueZ - 1);
			blockRenderer.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY - 1, trueZ - 1);
		}

		if (!flag2 && !flag5) {
			blockRenderer.aoLightValueScratchXYZPNP = blockRenderer.aoLightValueScratchXZPP;
			blockRenderer.aoBrightnessXYZPNP = blockRenderer.aoBrightnessXZPP;
		} else {
			blockRenderer.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY - 1, trueZ + 1);
			blockRenderer.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY - 1, trueZ + 1);
		}

		if (!flag3 && !flag4) {
			blockRenderer.aoLightValueScratchXYZPPN = blockRenderer.aoLightValueScratchXZPN;
			blockRenderer.aoBrightnessXYZPPN = blockRenderer.aoBrightnessXZPN;
		} else {
			blockRenderer.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY + 1, trueZ - 1);
			blockRenderer.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY + 1, trueZ - 1);
		}

		if (!flag3 && !flag5) {
			blockRenderer.aoLightValueScratchXYZPPP = blockRenderer.aoLightValueScratchXZPP;
			blockRenderer.aoBrightnessXYZPPP = blockRenderer.aoBrightnessXZPP;
		} else {
			blockRenderer.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY + 1, trueZ + 1);
			blockRenderer.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY + 1, trueZ + 1);
		}

		if (blockRenderer.renderMaxX >= 1.0D) {
			--trueX;
		}

		i1 = brightness;

		if (blockRenderer.renderMaxX >= 1.0D || !blockRenderer.blockAccess.isBlockOpaqueCube(trueX + 1, trueY, trueZ)) {
			i1 = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX + 1, trueY, trueZ);
		}

		f7 = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX + 1, trueY, trueZ);
		f3 = (blockRenderer.aoLightValueScratchXYPN + blockRenderer.aoLightValueScratchXYZPNP + f7 + blockRenderer.aoLightValueScratchXZPP) / 4.0F;
		f4 = (blockRenderer.aoLightValueScratchXYZPNN + blockRenderer.aoLightValueScratchXYPN + blockRenderer.aoLightValueScratchXZPN + f7) / 4.0F;
		f5 = (blockRenderer.aoLightValueScratchXZPN + f7 + blockRenderer.aoLightValueScratchXYZPPN + blockRenderer.aoLightValueScratchXYPP) / 4.0F;
		f6 = (f7 + blockRenderer.aoLightValueScratchXZPP + blockRenderer.aoLightValueScratchXYPP + blockRenderer.aoLightValueScratchXYZPPP) / 4.0F;
		blockRenderer.brightnessTopLeft = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXYPN, blockRenderer.aoBrightnessXYZPNP,
				blockRenderer.aoBrightnessXZPP, i1);
		blockRenderer.brightnessTopRight = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXZPP, blockRenderer.aoBrightnessXYPP,
				blockRenderer.aoBrightnessXYZPPP, i1);
		blockRenderer.brightnessBottomRight = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXZPN, blockRenderer.aoBrightnessXYZPPN,
				blockRenderer.aoBrightnessXYPP, i1);
		blockRenderer.brightnessBottomLeft = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXYZPNN, blockRenderer.aoBrightnessXYPN,
				blockRenderer.aoBrightnessXZPN, i1);

		blockRenderer.colorRedTopLeft = blockRenderer.colorRedBottomLeft = blockRenderer.colorRedBottomRight = blockRenderer.colorRedTopRight = r * 0.6F;
		blockRenderer.colorGreenTopLeft = blockRenderer.colorGreenBottomLeft = blockRenderer.colorGreenBottomRight = blockRenderer.colorGreenTopRight = g * 0.6F;
		blockRenderer.colorBlueTopLeft = blockRenderer.colorBlueBottomLeft = blockRenderer.colorBlueBottomRight = blockRenderer.colorBlueTopRight = b * 0.6F;

		blockRenderer.colorRedTopLeft *= f3;
		blockRenderer.colorGreenTopLeft *= f3;
		blockRenderer.colorBlueTopLeft *= f3;
		blockRenderer.colorRedBottomLeft *= f4;
		blockRenderer.colorGreenBottomLeft *= f4;
		blockRenderer.colorBlueBottomLeft *= f4;
		blockRenderer.colorRedBottomRight *= f5;
		blockRenderer.colorGreenBottomRight *= f5;
		blockRenderer.colorBlueBottomRight *= f5;
		blockRenderer.colorRedTopRight *= f6;
		blockRenderer.colorGreenTopRight *= f6;
		blockRenderer.colorBlueTopRight *= f6;

		// @formatter:off
		this.addQuadUVSTColor(
				x + 1, y, z, icon.getMaxU(), icon.getMaxV(), icon2.getMinU(), icon2.getMaxV(),
				blockRenderer.brightnessBottomLeft,
				(int) (blockRenderer.colorRedBottomLeft * 255), (int) (blockRenderer.colorGreenBottomLeft * 255), (int) (blockRenderer.colorBlueBottomLeft * 255), (int) (1.00 * 255),
				
				x + 1, y + 1, z, icon.getMaxU(), icon.getMinV(), icon2.getMinU(), icon2.getMinV(),
				blockRenderer.brightnessBottomRight,
				(int) (blockRenderer.colorRedBottomRight * 255),(int) (blockRenderer.colorGreenBottomRight * 255),(int) (blockRenderer.colorBlueBottomRight * 255),(int) (1.00 * 255),
				
				x + 1, y + 1, z + 1, icon.getMinU(), icon.getMinV(), icon2.getMaxU(), icon2.getMinV(),
				blockRenderer.brightnessTopRight,
				(int) (blockRenderer.colorRedTopRight * 255), (int) (blockRenderer.colorGreenTopRight * 255), (int) (blockRenderer.colorBlueTopRight * 255), (int) (1.00 * 255),
				
				x + 1, y, z + 1, icon.getMinU(), icon.getMaxV(), icon2.getMaxU(), icon2.getMaxV(),
				blockRenderer.brightnessTopLeft,
				(int) (blockRenderer.colorRedTopLeft * 255), (int) (blockRenderer.colorGreenTopLeft * 255), (int) (blockRenderer.colorBlueTopLeft * 255), (int) (1.00 * 255)
				);
		// @formatter:on
	}

	public void renderNegX(Block block, RenderBlocks blockRenderer, int trueX, int trueY, int trueZ, Icon icon, Icon icon2, double x, double y, double z,
			float r, float g, float b, int brightness) {
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f3;
		float f4;
		float f5;
		float f6;
		float f7;
		int i1;
		if (blockRenderer.renderMinX <= 0.0D) {
			--trueX;
		}

		blockRenderer.aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY - 1, trueZ);
		blockRenderer.aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY, trueZ - 1);
		blockRenderer.aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY, trueZ + 1);
		blockRenderer.aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY + 1, trueZ);
		blockRenderer.aoBrightnessXYNN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY - 1, trueZ);
		blockRenderer.aoBrightnessXZNN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY, trueZ - 1);
		blockRenderer.aoBrightnessXZNP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY, trueZ + 1);
		blockRenderer.aoBrightnessXYNP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY + 1, trueZ);
		flag3 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX - 1, trueY + 1, trueZ)];
		flag2 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX - 1, trueY - 1, trueZ)];
		flag5 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX - 1, trueY, trueZ - 1)];
		flag4 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX - 1, trueY, trueZ + 1)];

		if (!flag5 && !flag2) {
			blockRenderer.aoLightValueScratchXYZNNN = blockRenderer.aoLightValueScratchXZNN;
			blockRenderer.aoBrightnessXYZNNN = blockRenderer.aoBrightnessXZNN;
		} else {
			blockRenderer.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY - 1, trueZ - 1);
			blockRenderer.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY - 1, trueZ - 1);
		}

		if (!flag4 && !flag2) {
			blockRenderer.aoLightValueScratchXYZNNP = blockRenderer.aoLightValueScratchXZNP;
			blockRenderer.aoBrightnessXYZNNP = blockRenderer.aoBrightnessXZNP;
		} else {
			blockRenderer.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY - 1, trueZ + 1);
			blockRenderer.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY - 1, trueZ + 1);
		}

		if (!flag5 && !flag3) {
			blockRenderer.aoLightValueScratchXYZNPN = blockRenderer.aoLightValueScratchXZNN;
			blockRenderer.aoBrightnessXYZNPN = blockRenderer.aoBrightnessXZNN;
		} else {
			blockRenderer.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY + 1, trueZ - 1);
			blockRenderer.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY + 1, trueZ - 1);
		}

		if (!flag4 && !flag3) {
			blockRenderer.aoLightValueScratchXYZNPP = blockRenderer.aoLightValueScratchXZNP;
			blockRenderer.aoBrightnessXYZNPP = blockRenderer.aoBrightnessXZNP;
		} else {
			blockRenderer.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY + 1, trueZ + 1);
			blockRenderer.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY + 1, trueZ + 1);
		}

		if (blockRenderer.renderMinX <= 0.0D) {
			++trueX;
		}

		i1 = brightness;

		if (blockRenderer.renderMinX <= 0.0D || !blockRenderer.blockAccess.isBlockOpaqueCube(trueX - 1, trueY, trueZ)) {
			i1 = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX - 1, trueY, trueZ);
		}

		f7 = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX - 1, trueY, trueZ);
		f6 = (blockRenderer.aoLightValueScratchXYNN + blockRenderer.aoLightValueScratchXYZNNP + f7 + blockRenderer.aoLightValueScratchXZNP) / 4.0F;
		f3 = (f7 + blockRenderer.aoLightValueScratchXZNP + blockRenderer.aoLightValueScratchXYNP + blockRenderer.aoLightValueScratchXYZNPP) / 4.0F;
		f4 = (blockRenderer.aoLightValueScratchXZNN + f7 + blockRenderer.aoLightValueScratchXYZNPN + blockRenderer.aoLightValueScratchXYNP) / 4.0F;
		f5 = (blockRenderer.aoLightValueScratchXYZNNN + blockRenderer.aoLightValueScratchXYNN + blockRenderer.aoLightValueScratchXZNN + f7) / 4.0F;
		blockRenderer.brightnessTopRight = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXYNN, blockRenderer.aoBrightnessXYZNNP,
				blockRenderer.aoBrightnessXZNP, i1);
		blockRenderer.brightnessTopLeft = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXZNP, blockRenderer.aoBrightnessXYNP,
				blockRenderer.aoBrightnessXYZNPP, i1);
		blockRenderer.brightnessBottomLeft = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXZNN, blockRenderer.aoBrightnessXYZNPN,
				blockRenderer.aoBrightnessXYNP, i1);
		blockRenderer.brightnessBottomRight = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXYZNNN, blockRenderer.aoBrightnessXYNN,
				blockRenderer.aoBrightnessXZNN, i1);

		blockRenderer.colorRedTopLeft = blockRenderer.colorRedBottomLeft = blockRenderer.colorRedBottomRight = blockRenderer.colorRedTopRight = r * 0.6F;
		blockRenderer.colorGreenTopLeft = blockRenderer.colorGreenBottomLeft = blockRenderer.colorGreenBottomRight = blockRenderer.colorGreenTopRight = g * 0.6F;
		blockRenderer.colorBlueTopLeft = blockRenderer.colorBlueBottomLeft = blockRenderer.colorBlueBottomRight = blockRenderer.colorBlueTopRight = b * 0.6F;

		blockRenderer.colorRedTopLeft *= f3;
		blockRenderer.colorGreenTopLeft *= f3;
		blockRenderer.colorBlueTopLeft *= f3;
		blockRenderer.colorRedBottomLeft *= f4;
		blockRenderer.colorGreenBottomLeft *= f4;
		blockRenderer.colorBlueBottomLeft *= f4;
		blockRenderer.colorRedBottomRight *= f5;
		blockRenderer.colorGreenBottomRight *= f5;
		blockRenderer.colorBlueBottomRight *= f5;
		blockRenderer.colorRedTopRight *= f6;
		blockRenderer.colorGreenTopRight *= f6;
		blockRenderer.colorBlueTopRight *= f6;

		// @formatter:off
		this.addQuadUVSTColor(
				x, y, z + 1, icon.getMaxU(), icon.getMaxV(), icon2.getMinU(), icon2.getMaxV(),
				blockRenderer.brightnessTopRight,
				(int) (blockRenderer.colorRedTopRight * 255), (int) (blockRenderer.colorGreenTopRight * 255), (int) (blockRenderer.colorBlueTopRight * 255), (int) (1.00 * 255),
				
				x, y + 1, z + 1, icon.getMaxU(), icon.getMinV(), icon2.getMinU(), icon2.getMinV(),
				blockRenderer.brightnessTopLeft,
				(int) (blockRenderer.colorRedTopLeft * 255), (int) (blockRenderer.colorGreenTopLeft * 255), (int) (blockRenderer.colorBlueTopLeft * 255), (int) (1.00 * 255),
				
				x, y + 1, z, icon.getMinU(), icon.getMinV(), icon2.getMaxU(), icon2.getMinV(),
				blockRenderer.brightnessBottomLeft,
				(int) (blockRenderer.colorRedBottomLeft * 255), (int) (blockRenderer.colorGreenBottomLeft * 255), (int) (blockRenderer.colorBlueBottomLeft * 255), (int) (1.00 * 255),
				
				x, y, z, icon.getMinU(), icon.getMaxV(), icon2.getMaxU(), icon2.getMaxV(),
				blockRenderer.brightnessBottomRight,
				(int) (blockRenderer.colorRedBottomRight * 255),(int) (blockRenderer.colorGreenBottomRight * 255),(int) (blockRenderer.colorBlueBottomRight * 255),(int) (1.00 * 255)
				);
		// @formatter:on
	}

	public void renderPosZ(Block block, RenderBlocks blockRenderer, int trueX, int trueY, int trueZ, Icon icon, Icon icon2, double x, double y, double z,
			float r, float g, float b, int brightness) {
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f3;
		float f4;
		float f5;
		float f6;
		float f7;
		int i1;
		
		if (blockRenderer.renderMaxZ >= 1.0D) {
			++trueZ;
		}

		blockRenderer.aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX - 1, trueY, trueZ);
		blockRenderer.aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX + 1, trueY, trueZ);
		blockRenderer.aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY - 1, trueZ);
		blockRenderer.aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY + 1, trueZ);
		blockRenderer.aoBrightnessXZNP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX - 1, trueY, trueZ);
		blockRenderer.aoBrightnessXZPP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX + 1, trueY, trueZ);
		blockRenderer.aoBrightnessYZNP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY - 1, trueZ);
		blockRenderer.aoBrightnessYZPP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY + 1, trueZ);

		flag3 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX + 1, trueY, trueZ + 1)];
		flag2 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX - 1, trueY, trueZ + 1)];
		flag5 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX, trueY + 1, trueZ + 1)];
		flag4 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX, trueY - 1, trueZ + 1)];

		if (!flag2 && !flag4) {
			blockRenderer.aoLightValueScratchXYZNNP = blockRenderer.aoLightValueScratchXZNP;
			blockRenderer.aoBrightnessXYZNNP = blockRenderer.aoBrightnessXZNP;
		} else {
			blockRenderer.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX - 1, trueY - 1, trueZ);
			blockRenderer.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX - 1, trueY - 1, trueZ);
		}

		if (!flag2 && !flag5) {
			blockRenderer.aoLightValueScratchXYZNPP = blockRenderer.aoLightValueScratchXZNP;
			blockRenderer.aoBrightnessXYZNPP = blockRenderer.aoBrightnessXZNP;
		} else {
			blockRenderer.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX - 1, trueY + 1, trueZ);
			blockRenderer.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX - 1, trueY + 1, trueZ);
		}

		if (!flag3 && !flag4) {
			blockRenderer.aoLightValueScratchXYZPNP = blockRenderer.aoLightValueScratchXZPP;
			blockRenderer.aoBrightnessXYZPNP = blockRenderer.aoBrightnessXZPP;
		} else {
			blockRenderer.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX + 1, trueY - 1, trueZ);
			blockRenderer.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX + 1, trueY - 1, trueZ);
		}

		if (!flag3 && !flag5) {
			blockRenderer.aoLightValueScratchXYZPPP = blockRenderer.aoLightValueScratchXZPP;
			blockRenderer.aoBrightnessXYZPPP = blockRenderer.aoBrightnessXZPP;
		} else {
			blockRenderer.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX + 1, trueY + 1, trueZ);
			blockRenderer.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX + 1, trueY + 1, trueZ);
		}

		if (blockRenderer.renderMaxZ >= 1.0D) {
			--trueZ;
		}

		i1 = brightness;

		if (blockRenderer.renderMaxZ >= 1.0D || !blockRenderer.blockAccess.isBlockOpaqueCube(trueX, trueY, trueZ + 1)) {
			i1 = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY, trueZ + 1);
		}

		f7 = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY, trueZ + 1);
		f3 = (blockRenderer.aoLightValueScratchXZNP + blockRenderer.aoLightValueScratchXYZNPP + f7 + blockRenderer.aoLightValueScratchYZPP) / 4.0F;
		f6 = (f7 + blockRenderer.aoLightValueScratchYZPP + blockRenderer.aoLightValueScratchXZPP + blockRenderer.aoLightValueScratchXYZPPP) / 4.0F;
		f5 = (blockRenderer.aoLightValueScratchYZNP + f7 + blockRenderer.aoLightValueScratchXYZPNP + blockRenderer.aoLightValueScratchXZPP) / 4.0F;
		f4 = (blockRenderer.aoLightValueScratchXYZNNP + blockRenderer.aoLightValueScratchXZNP + blockRenderer.aoLightValueScratchYZNP + f7) / 4.0F;
		blockRenderer.brightnessTopLeft = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXZNP, blockRenderer.aoBrightnessXYZNPP,
				blockRenderer.aoBrightnessYZPP, i1);
		blockRenderer.brightnessTopRight = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessYZPP, blockRenderer.aoBrightnessXZPP,
				blockRenderer.aoBrightnessXYZPPP, i1);
		blockRenderer.brightnessBottomRight = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessYZNP, blockRenderer.aoBrightnessXYZPNP,
				blockRenderer.aoBrightnessXZPP, i1);
		blockRenderer.brightnessBottomLeft = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXYZNNP, blockRenderer.aoBrightnessXZNP,
				blockRenderer.aoBrightnessYZNP, i1);

		blockRenderer.colorRedTopLeft = blockRenderer.colorRedBottomLeft = blockRenderer.colorRedBottomRight = blockRenderer.colorRedTopRight = r * 0.8F;
		blockRenderer.colorGreenTopLeft = blockRenderer.colorGreenBottomLeft = blockRenderer.colorGreenBottomRight = blockRenderer.colorGreenTopRight = g * 0.8F;
		blockRenderer.colorBlueTopLeft = blockRenderer.colorBlueBottomLeft = blockRenderer.colorBlueBottomRight = blockRenderer.colorBlueTopRight = b * 0.8F;

		blockRenderer.colorRedTopLeft *= f3;
		blockRenderer.colorGreenTopLeft *= f3;
		blockRenderer.colorBlueTopLeft *= f3;
		blockRenderer.colorRedBottomLeft *= f4;
		blockRenderer.colorGreenBottomLeft *= f4;
		blockRenderer.colorBlueBottomLeft *= f4;
		blockRenderer.colorRedBottomRight *= f5;
		blockRenderer.colorGreenBottomRight *= f5;
		blockRenderer.colorBlueBottomRight *= f5;
		blockRenderer.colorRedTopRight *= f6;
		blockRenderer.colorGreenTopRight *= f6;
		blockRenderer.colorBlueTopRight *= f6;

		// @formatter:off
		this.addQuadUVSTColor(
				x + 1, y, z + 1, icon.getMaxU(), icon.getMaxV(), icon2.getMinU(), icon2.getMaxV(),
				blockRenderer.brightnessBottomRight,
				(int) (blockRenderer.colorRedBottomRight * 255),(int) (blockRenderer.colorGreenBottomRight * 255),(int) (blockRenderer.colorBlueBottomRight * 255),(int) (1.00 * 255),
				
				x + 1, y + 1, z + 1, icon.getMaxU(), icon.getMinV(), icon2.getMinU(), icon2.getMinV(),
				blockRenderer.brightnessTopRight,
				(int) (blockRenderer.colorRedTopRight * 255), (int) (blockRenderer.colorGreenTopRight * 255), (int) (blockRenderer.colorBlueTopRight * 255), (int) (1.00 * 255),
				
				x, y + 1, z + 1, icon.getMinU(), icon.getMinV(), icon2.getMaxU(), icon2.getMinV(),
				blockRenderer.brightnessTopLeft,
				(int) (blockRenderer.colorRedTopLeft * 255), (int) (blockRenderer.colorGreenTopLeft * 255), (int) (blockRenderer.colorBlueTopLeft * 255), (int) (1.00 * 255),
				
				x, y, z + 1, icon.getMinU(), icon.getMaxV(), icon2.getMaxU(), icon2.getMaxV(),
				blockRenderer.brightnessBottomLeft,
				(int) (blockRenderer.colorRedBottomLeft * 255), (int) (blockRenderer.colorGreenBottomLeft * 255), (int) (blockRenderer.colorBlueBottomLeft * 255), (int) (1.00 * 255)
				);
		// @formatter:on
	}

	public void renderNegZ(Block block, RenderBlocks blockRenderer, int trueX, int trueY, int trueZ, Icon icon, Icon icon2, double x, double y, double z,
			float r, float g, float b, int brightness) {
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f3;
		float f4;
		float f5;
		float f6;
		float f7;
		int i1;

		if (blockRenderer.renderMinZ <= 0.0D) {
			--trueZ;
		}

		blockRenderer.aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX - 1, trueY, trueZ);
		blockRenderer.aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY - 1, trueZ);
		blockRenderer.aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY + 1, trueZ);
		blockRenderer.aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX + 1, trueY, trueZ);
		blockRenderer.aoBrightnessXZNN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX - 1, trueY, trueZ);
		blockRenderer.aoBrightnessYZNN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY - 1, trueZ);
		blockRenderer.aoBrightnessYZPN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY + 1, trueZ);
		blockRenderer.aoBrightnessXZPN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX + 1, trueY, trueZ);
		flag3 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX + 1, trueY, trueZ - 1)];
		flag2 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX - 1, trueY, trueZ - 1)];
		flag5 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX, trueY + 1, trueZ - 1)];
		flag4 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX, trueY - 1, trueZ - 1)];

		if (!flag2 && !flag4) {
			blockRenderer.aoLightValueScratchXYZNNN = blockRenderer.aoLightValueScratchXZNN;
			blockRenderer.aoBrightnessXYZNNN = blockRenderer.aoBrightnessXZNN;
		} else {
			blockRenderer.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX - 1, trueY - 1, trueZ);
			blockRenderer.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX - 1, trueY - 1, trueZ);
		}

		if (!flag2 && !flag5) {
			blockRenderer.aoLightValueScratchXYZNPN = blockRenderer.aoLightValueScratchXZNN;
			blockRenderer.aoBrightnessXYZNPN = blockRenderer.aoBrightnessXZNN;
		} else {
			blockRenderer.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX - 1, trueY + 1, trueZ);
			blockRenderer.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX - 1, trueY + 1, trueZ);
		}

		if (!flag3 && !flag4) {
			blockRenderer.aoLightValueScratchXYZPNN = blockRenderer.aoLightValueScratchXZPN;
			blockRenderer.aoBrightnessXYZPNN = blockRenderer.aoBrightnessXZPN;
		} else {
			blockRenderer.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX + 1, trueY - 1, trueZ);
			blockRenderer.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX + 1, trueY - 1, trueZ);
		}

		if (!flag3 && !flag5) {
			blockRenderer.aoLightValueScratchXYZPPN = blockRenderer.aoLightValueScratchXZPN;
			blockRenderer.aoBrightnessXYZPPN = blockRenderer.aoBrightnessXZPN;
		} else {
			blockRenderer.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX + 1, trueY + 1, trueZ);
			blockRenderer.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX + 1, trueY + 1, trueZ);
		}

		if (blockRenderer.renderMinZ <= 0.0D) {
			++trueZ;
		}

		i1 = brightness;

		if (blockRenderer.renderMinZ <= 0.0D || !blockRenderer.blockAccess.isBlockOpaqueCube(trueX, trueY, trueZ - 1)) {
			i1 = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY, trueZ - 1);
		}

		f7 = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY, trueZ - 1);
		f3 = (blockRenderer.aoLightValueScratchXZNN + blockRenderer.aoLightValueScratchXYZNPN + f7 + blockRenderer.aoLightValueScratchYZPN) / 4.0F;
		f4 = (f7 + blockRenderer.aoLightValueScratchYZPN + blockRenderer.aoLightValueScratchXZPN + blockRenderer.aoLightValueScratchXYZPPN) / 4.0F;
		f5 = (blockRenderer.aoLightValueScratchYZNN + f7 + blockRenderer.aoLightValueScratchXYZPNN + blockRenderer.aoLightValueScratchXZPN) / 4.0F;
		f6 = (blockRenderer.aoLightValueScratchXYZNNN + blockRenderer.aoLightValueScratchXZNN + blockRenderer.aoLightValueScratchYZNN + f7) / 4.0F;
		blockRenderer.brightnessTopLeft = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXZNN, blockRenderer.aoBrightnessXYZNPN,
				blockRenderer.aoBrightnessYZPN, i1);
		blockRenderer.brightnessBottomLeft = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessYZPN, blockRenderer.aoBrightnessXZPN,
				blockRenderer.aoBrightnessXYZPPN, i1);
		blockRenderer.brightnessBottomRight = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessYZNN, blockRenderer.aoBrightnessXYZPNN,
				blockRenderer.aoBrightnessXZPN, i1);
		blockRenderer.brightnessTopRight = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXYZNNN, blockRenderer.aoBrightnessXZNN,
				blockRenderer.aoBrightnessYZNN, i1);

		blockRenderer.colorRedTopLeft = blockRenderer.colorRedBottomLeft = blockRenderer.colorRedBottomRight = blockRenderer.colorRedTopRight = r * 0.8F;
		blockRenderer.colorGreenTopLeft = blockRenderer.colorGreenBottomLeft = blockRenderer.colorGreenBottomRight = blockRenderer.colorGreenTopRight = g * 0.8F;
		blockRenderer.colorBlueTopLeft = blockRenderer.colorBlueBottomLeft = blockRenderer.colorBlueBottomRight = blockRenderer.colorBlueTopRight = b * 0.8F;

		blockRenderer.colorRedTopLeft *= f3;
		blockRenderer.colorGreenTopLeft *= f3;
		blockRenderer.colorBlueTopLeft *= f3;
		blockRenderer.colorRedBottomLeft *= f4;
		blockRenderer.colorGreenBottomLeft *= f4;
		blockRenderer.colorBlueBottomLeft *= f4;
		blockRenderer.colorRedBottomRight *= f5;
		blockRenderer.colorGreenBottomRight *= f5;
		blockRenderer.colorBlueBottomRight *= f5;
		blockRenderer.colorRedTopRight *= f6;
		blockRenderer.colorGreenTopRight *= f6;
		blockRenderer.colorBlueTopRight *= f6;

		i1 = brightness;

		if (blockRenderer.renderMinZ <= 0.0D || !blockRenderer.blockAccess.isBlockOpaqueCube(trueX, trueY, trueZ - 1)) {
			i1 = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY, trueZ - 1);
		}

		// @formatter:off
		this.addQuadUVSTColor(x, y, z, icon.getMinU(), icon.getMaxV(), icon2.getMinU(), icon2.getMaxV(),
				blockRenderer.brightnessTopRight,
				(int) (blockRenderer.colorRedTopRight * 255), (int) (blockRenderer.colorGreenTopRight * 255), (int) (blockRenderer.colorBlueTopRight * 255), (int) (1.00 * 255),
				
				x, y + 1, z, icon.getMinU(), icon.getMinV(), icon2.getMinU(), icon2.getMinV(),
				blockRenderer.brightnessTopLeft,
				(int) (blockRenderer.colorRedTopLeft * 255), (int) (blockRenderer.colorGreenTopLeft * 255), (int) (blockRenderer.colorBlueTopLeft * 255), (int) (1.00 * 255),
				
				x + 1, y + 1, z, icon.getMaxU(), icon.getMinV(), icon2.getMaxU(), icon2.getMinV(),
				blockRenderer.brightnessBottomLeft,
				(int) (blockRenderer.colorRedBottomLeft * 255), (int) (blockRenderer.colorGreenBottomLeft * 255), (int) (blockRenderer.colorBlueBottomLeft * 255), (int) (1.00 * 255),
				
				x + 1, y, z, icon.getMaxU(), icon.getMaxV(), icon2.getMaxU(), icon2.getMaxV(),
				blockRenderer.brightnessBottomRight,
				(int) (blockRenderer.colorRedBottomRight * 255),(int) (blockRenderer.colorGreenBottomRight * 255),(int) (blockRenderer.colorBlueBottomRight * 255),(int) (1.00 * 255)
				);
		// @formatter:on
	}

	public void renderPosY(Block block, RenderBlocks blockRenderer, int trueX, int trueY, int trueZ, Icon icon, Icon icon2, double x, double y, double z,
			float r, float g, float b, int brightness) {
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f3;
		float f4;
		float f5;
		float f6;
		float f7;
		int i1;
		if (blockRenderer.renderMaxY >= 1.0D) {
			++trueY;
		}

		blockRenderer.aoBrightnessXYNP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX - 1, trueY, trueZ);
		blockRenderer.aoBrightnessXYPP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX + 1, trueY, trueZ);
		blockRenderer.aoBrightnessYZPN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY, trueZ - 1);
		blockRenderer.aoBrightnessYZPP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY, trueZ + 1);
		blockRenderer.aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX - 1, trueY, trueZ);
		blockRenderer.aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX + 1, trueY, trueZ);
		blockRenderer.aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY, trueZ - 1);
		blockRenderer.aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY, trueZ + 1);

		flag3 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX + 1, trueY + 1, trueZ)];
		flag2 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX - 1, trueY + 1, trueZ)];
		flag5 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX, trueY + 1, trueZ + 1)];
		flag4 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX, trueY + 1, trueZ - 1)];

		if (!flag4 && !flag2) {
			blockRenderer.aoLightValueScratchXYZNPN = blockRenderer.aoLightValueScratchXYNP;
			blockRenderer.aoBrightnessXYZNPN = blockRenderer.aoBrightnessXYNP;
		} else {
			blockRenderer.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX - 1, trueY, trueZ - 1);
			blockRenderer.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX - 1, trueY, trueZ - 1);
		}

		if (!flag4 && !flag3) {
			blockRenderer.aoLightValueScratchXYZPPN = blockRenderer.aoLightValueScratchXYPP;
			blockRenderer.aoBrightnessXYZPPN = blockRenderer.aoBrightnessXYPP;
		} else {
			blockRenderer.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX + 1, trueY, trueZ - 1);
			blockRenderer.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX + 1, trueY, trueZ - 1);
		}

		if (!flag5 && !flag2) {
			blockRenderer.aoLightValueScratchXYZNPP = blockRenderer.aoLightValueScratchXYNP;
			blockRenderer.aoBrightnessXYZNPP = blockRenderer.aoBrightnessXYNP;
		} else {
			blockRenderer.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX - 1, trueY, trueZ + 1);
			blockRenderer.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX - 1, trueY, trueZ + 1);
		}

		if (!flag5 && !flag3) {
			blockRenderer.aoLightValueScratchXYZPPP = blockRenderer.aoLightValueScratchXYPP;
			blockRenderer.aoBrightnessXYZPPP = blockRenderer.aoBrightnessXYPP;
		} else {
			blockRenderer.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX + 1, trueY, trueZ + 1);
			blockRenderer.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX + 1, trueY, trueZ + 1);
		}

		if (blockRenderer.renderMaxY >= 1.0D) {
			--trueY;
		}

		i1 = brightness;

		if (blockRenderer.renderMaxY >= 1.0D || !blockRenderer.blockAccess.isBlockOpaqueCube(trueX, trueY + 1, trueZ)) {
			i1 = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY + 1, trueZ);
		}

		f7 = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY + 1, trueZ);
		f6 = (blockRenderer.aoLightValueScratchXYZNPP + blockRenderer.aoLightValueScratchXYNP + blockRenderer.aoLightValueScratchYZPP + f7) / 4.0F;
		f3 = (blockRenderer.aoLightValueScratchYZPP + f7 + blockRenderer.aoLightValueScratchXYZPPP + blockRenderer.aoLightValueScratchXYPP) / 4.0F;
		f4 = (f7 + blockRenderer.aoLightValueScratchYZPN + blockRenderer.aoLightValueScratchXYPP + blockRenderer.aoLightValueScratchXYZPPN) / 4.0F;
		f5 = (blockRenderer.aoLightValueScratchXYNP + blockRenderer.aoLightValueScratchXYZNPN + f7 + blockRenderer.aoLightValueScratchYZPN) / 4.0F;
		blockRenderer.brightnessTopRight = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXYZNPP, blockRenderer.aoBrightnessXYNP,
				blockRenderer.aoBrightnessYZPP, i1);
		blockRenderer.brightnessTopLeft = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessYZPP, blockRenderer.aoBrightnessXYZPPP,
				blockRenderer.aoBrightnessXYPP, i1);
		blockRenderer.brightnessBottomLeft = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessYZPN, blockRenderer.aoBrightnessXYPP,
				blockRenderer.aoBrightnessXYZPPN, i1);
		blockRenderer.brightnessBottomRight = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXYNP, blockRenderer.aoBrightnessXYZNPN,
				blockRenderer.aoBrightnessYZPN, i1);

		blockRenderer.colorRedTopLeft = blockRenderer.colorRedBottomLeft = blockRenderer.colorRedBottomRight = blockRenderer.colorRedTopRight = r;
		blockRenderer.colorGreenTopLeft = blockRenderer.colorGreenBottomLeft = blockRenderer.colorGreenBottomRight = blockRenderer.colorGreenTopRight = g;
		blockRenderer.colorBlueTopLeft = blockRenderer.colorBlueBottomLeft = blockRenderer.colorBlueBottomRight = blockRenderer.colorBlueTopRight = b;

		blockRenderer.colorRedTopLeft *= f3;
		blockRenderer.colorGreenTopLeft *= f3;
		blockRenderer.colorBlueTopLeft *= f3;
		blockRenderer.colorRedBottomLeft *= f4;
		blockRenderer.colorGreenBottomLeft *= f4;
		blockRenderer.colorBlueBottomLeft *= f4;
		blockRenderer.colorRedBottomRight *= f5;
		blockRenderer.colorGreenBottomRight *= f5;
		blockRenderer.colorBlueBottomRight *= f5;
		blockRenderer.colorRedTopRight *= f6;
		blockRenderer.colorGreenTopRight *= f6;
		blockRenderer.colorBlueTopRight *= f6;

		// @formatter:off
		this.addQuadUVSTColor(
				x, y + 1, z, icon.getMinU(), icon.getMinV(), icon2.getMinU(), icon2.getMaxV(),
				blockRenderer.brightnessBottomRight,
				(int) (blockRenderer.colorRedBottomRight * 255),(int) (blockRenderer.colorGreenBottomRight * 255),(int) (blockRenderer.colorBlueBottomRight * 255),(int) (1.00 * 255),
				
				x, y + 1, z + 1, icon.getMinU(), icon.getMaxV(), icon2.getMinU(), icon2.getMinV(),
				blockRenderer.brightnessTopRight,
				(int) (blockRenderer.colorRedTopRight * 255), (int) (blockRenderer.colorGreenTopRight * 255), (int) (blockRenderer.colorBlueTopRight * 255), (int) (1.00 * 255),
				
				x + 1, y + 1, z + 1, icon.getMaxU(), icon.getMaxV(), icon2.getMaxU(), icon2.getMinV(),
				blockRenderer.brightnessTopLeft,
				(int) (blockRenderer.colorRedTopLeft * 255), (int) (blockRenderer.colorGreenTopLeft * 255), (int) (blockRenderer.colorBlueTopLeft * 255), (int) (1.00 * 255),
				
				x + 1, y + 1, z, icon.getMaxU(), icon.getMinV(), icon2.getMaxU(), icon2.getMaxV(),
				blockRenderer.brightnessBottomLeft,
				(int) (blockRenderer.colorRedBottomLeft * 255), (int) (blockRenderer.colorGreenBottomLeft * 255), (int) (blockRenderer.colorBlueBottomLeft * 255), (int) (1.00 * 255)
				);
		// @formatter:on
	}

	public void renderNegY(Block block, RenderBlocks blockRenderer, int trueX, int trueY, int trueZ, Icon icon, Icon icon2, double x, double y, double z,
			float r, float g, float b, int brightness) {
		boolean flag2;
		boolean flag3;
		boolean flag4;
		boolean flag5;
		float f3;
		float f4;
		float f5;
		float f6;
		float f7;
		int i1;
		if (blockRenderer.renderMinY <= 0.0D) {
			--trueY;
		}

		blockRenderer.aoBrightnessXYNN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX - 1, trueY, trueZ);
		blockRenderer.aoBrightnessYZNN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY, trueZ - 1);
		blockRenderer.aoBrightnessYZNP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY, trueZ + 1);
		blockRenderer.aoBrightnessXYPN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX + 1, trueY, trueZ);
		blockRenderer.aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX - 1, trueY, trueZ);
		blockRenderer.aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY, trueZ - 1);
		blockRenderer.aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY, trueZ + 1);
		blockRenderer.aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX + 1, trueY, trueZ);

		flag3 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX + 1, trueY - 1, trueZ)];
		flag2 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX - 1, trueY - 1, trueZ)];
		flag5 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX, trueY - 1, trueZ + 1)];
		flag4 = Block.canBlockGrass[blockRenderer.blockAccess.getBlockId(trueX, trueY - 1, trueZ - 1)];

		if (!flag4 && !flag2) {
			blockRenderer.aoLightValueScratchXYZNNN = blockRenderer.aoLightValueScratchXYNN;
			blockRenderer.aoBrightnessXYZNNN = blockRenderer.aoBrightnessXYNN;
		} else {
			blockRenderer.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX - 1, trueY, trueZ - 1);
			blockRenderer.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX - 1, trueY, trueZ - 1);
		}

		if (!flag5 && !flag2) {
			blockRenderer.aoLightValueScratchXYZNNP = blockRenderer.aoLightValueScratchXYNN;
			blockRenderer.aoBrightnessXYZNNP = blockRenderer.aoBrightnessXYNN;
		} else {
			blockRenderer.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX - 1, trueY, trueZ + 1);
			blockRenderer.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX - 1, trueY, trueZ + 1);
		}

		if (!flag4 && !flag3) {
			blockRenderer.aoLightValueScratchXYZPNN = blockRenderer.aoLightValueScratchXYPN;
			blockRenderer.aoBrightnessXYZPNN = blockRenderer.aoBrightnessXYPN;
		} else {
			blockRenderer.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX + 1, trueY, trueZ - 1);
			blockRenderer.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX + 1, trueY, trueZ - 1);
		}

		if (!flag5 && !flag3) {
			blockRenderer.aoLightValueScratchXYZPNP = blockRenderer.aoLightValueScratchXYPN;
			blockRenderer.aoBrightnessXYZPNP = blockRenderer.aoBrightnessXYPN;
		} else {
			blockRenderer.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX + 1, trueY, trueZ + 1);
			blockRenderer.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX + 1, trueY, trueZ + 1);
		}

		if (blockRenderer.renderMinY <= 0.0D) {
			++trueY;
		}

		i1 = brightness;

		if (blockRenderer.renderMinY <= 0.0D || !blockRenderer.blockAccess.isBlockOpaqueCube(trueX, trueY - 1, trueZ)) {
			i1 = block.getMixedBrightnessForBlock(blockRenderer.blockAccess, trueX, trueY - 1, trueZ);
		}

		f7 = block.getAmbientOcclusionLightValue(blockRenderer.blockAccess, trueX, trueY - 1, trueZ);
		f3 = (blockRenderer.aoLightValueScratchXYZNNP + blockRenderer.aoLightValueScratchXYNN + blockRenderer.aoLightValueScratchYZNP + f7) / 4.0F;
		f6 = (blockRenderer.aoLightValueScratchYZNP + f7 + blockRenderer.aoLightValueScratchXYZPNP + blockRenderer.aoLightValueScratchXYPN) / 4.0F;
		f5 = (f7 + blockRenderer.aoLightValueScratchYZNN + blockRenderer.aoLightValueScratchXYPN + blockRenderer.aoLightValueScratchXYZPNN) / 4.0F;
		f4 = (blockRenderer.aoLightValueScratchXYNN + blockRenderer.aoLightValueScratchXYZNNN + f7 + blockRenderer.aoLightValueScratchYZNN) / 4.0F;
		blockRenderer.brightnessTopLeft = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXYZNNP, blockRenderer.aoBrightnessXYNN,
				blockRenderer.aoBrightnessYZNP, i1);
		blockRenderer.brightnessTopRight = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessYZNP, blockRenderer.aoBrightnessXYZPNP,
				blockRenderer.aoBrightnessXYPN, i1);
		blockRenderer.brightnessBottomRight = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessYZNN, blockRenderer.aoBrightnessXYPN,
				blockRenderer.aoBrightnessXYZPNN, i1);
		blockRenderer.brightnessBottomLeft = blockRenderer.getAoBrightness(blockRenderer.aoBrightnessXYNN, blockRenderer.aoBrightnessXYZNNN,
				blockRenderer.aoBrightnessYZNN, i1);

		blockRenderer.colorRedTopLeft = blockRenderer.colorRedBottomLeft = blockRenderer.colorRedBottomRight = blockRenderer.colorRedTopRight = r * 0.5f;
		blockRenderer.colorGreenTopLeft = blockRenderer.colorGreenBottomLeft = blockRenderer.colorGreenBottomRight = blockRenderer.colorGreenTopRight = g * 0.5f;
		blockRenderer.colorBlueTopLeft = blockRenderer.colorBlueBottomLeft = blockRenderer.colorBlueBottomRight = blockRenderer.colorBlueTopRight = b * 0.5f;

		blockRenderer.colorRedTopLeft *= f3;
		blockRenderer.colorGreenTopLeft *= f3;
		blockRenderer.colorBlueTopLeft *= f3;
		blockRenderer.colorRedBottomLeft *= f4;
		blockRenderer.colorGreenBottomLeft *= f4;
		blockRenderer.colorBlueBottomLeft *= f4;
		blockRenderer.colorRedBottomRight *= f5;
		blockRenderer.colorGreenBottomRight *= f5;
		blockRenderer.colorBlueBottomRight *= f5;
		blockRenderer.colorRedTopRight *= f6;
		blockRenderer.colorGreenTopRight *= f6;
		blockRenderer.colorBlueTopRight *= f6;

		// @formatter:off
		this.addQuadUVSTColor(
				x, y, z + 1, icon.getMinU(), icon.getMaxV(), icon2.getMinU(), icon2.getMaxV(),
				blockRenderer.brightnessTopLeft,
				(int) (blockRenderer.colorRedTopLeft * 255), (int) (blockRenderer.colorGreenTopLeft * 255), (int) (blockRenderer.colorBlueTopLeft * 255), (int) (1.00 * 255),
				
				x, y, z, icon.getMinU(), icon.getMinV(), icon2.getMinU(), icon2.getMinV(),
				blockRenderer.brightnessBottomLeft,
				(int) (blockRenderer.colorRedBottomLeft * 255), (int) (blockRenderer.colorGreenBottomLeft * 255), (int) (blockRenderer.colorBlueBottomLeft * 255), (int) (1.00 * 255),
				
				x + 1, y, z, icon.getMaxU(), icon.getMinV(), icon2.getMaxU(), icon2.getMinV(),
				blockRenderer.brightnessBottomRight,
				(int) (blockRenderer.colorRedBottomRight * 255),(int) (blockRenderer.colorGreenBottomRight * 255),(int) (blockRenderer.colorBlueBottomRight * 255),(int) (1.00 * 255),
				
				x + 1, y, z + 1, icon.getMaxU(), icon.getMaxV(), icon2.getMaxU(), icon2.getMaxV(),
				blockRenderer.brightnessTopRight,
				(int) (blockRenderer.colorRedTopRight * 255), (int) (blockRenderer.colorGreenTopRight * 255), (int) (blockRenderer.colorBlueTopRight * 255), (int) (1.00 * 255)
				);
		// @formatter:on
	}
}