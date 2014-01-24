package com.forgetutorials.lib.renderers;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class VertexRenderer {

	ByteBuffer byteBuffer = BufferUtils.createByteBuffer(1024 * 4 * 8 * 4);
	IntBuffer intBuffer = this.byteBuffer.asIntBuffer();
	FloatBuffer floatBuffer = this.byteBuffer.asFloatBuffer();
	ShortBuffer shortBuffer = this.byteBuffer.asShortBuffer();

	// FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(1024 * 4 * 3);
	// ByteBuffer colorBuffer = BufferUtils.createByteBuffer(1024 * 4 * 4);
	// FloatBuffer uvBuffer = BufferUtils.createFloatBuffer(1024 * 4 * 2);
	// FloatBuffer stBuffer = BufferUtils.createFloatBuffer(1024 * 4 * 2);

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
		addVertexUVSTColorLight(X1, Y1, Z1, U1, V1, S1, T1, LS1, LB1, R1, G1, B1, A1);

		addVertexUVSTColorLight(X2, Y2, Z2, U2, V2, S2, T2, LS2, LB2, R2, G2, B2, A2);

		addVertexUVSTColorLight(X3, Y3, Z3, U3, V3, S3, T3, LS3, LB3, R3, G3, B3, A3);

		addVertexUVSTColorLight(X4, Y4, Z4, U4, V4, S4, T4, LS4, LB4, R4, G4, B4, A4);

	}

	// @formatter:off
	public void addQuadUVSTColor(
			double X1, double Y1, double Z1, float U1, float V1, float S1, float T1, int R1, int G1, int B1, int A1,
			double X2, double Y2, double Z2, float U2, float V2, float S2, float T2, int R2, int G2, int B2, int A2,
			double X3, double Y3, double Z3, float U3, float V3, float S3, float T3, int R3, int G3, int B3, int A3,
			double X4, double Y4, double Z4, float U4, float V4, float S4, float T4, int R4, int G4, int B4, int A4
			) {
	// @formatter:on

		// 240 = full light

		addVertexUVSTColorLight(X1, Y1, Z1, U1, V1, S1, T1, 240, 240, R1, G1, B1, A1);

		addVertexUVSTColorLight(X2, Y2, Z2, U2, V2, S2, T2, 240, 240, R2, G2, B2, A2);

		addVertexUVSTColorLight(X3, Y3, Z3, U3, V3, S3, T3, 240, 240, R3, G3, B3, A3);

		addVertexUVSTColorLight(X4, Y4, Z4, U4, V4, S4, T4, 240, 240, R4, G4, B4, A4);

	}

	private void addVertexUVSTColorLight(double X, double Y, double Z, float U, float V, float S, float T, int Q, int P, int R, int G, int B, int A) {
		this.intBuffer.position(this.vertexCount * VertexRenderer.INTS);
		this.intBuffer.put(Float.floatToRawIntBits((float) X));
		this.intBuffer.put(Float.floatToRawIntBits((float) Y));
		this.intBuffer.put(Float.floatToRawIntBits((float) Z));

		this.intBuffer.put(Float.floatToRawIntBits(U));
		this.intBuffer.put(Float.floatToRawIntBits(V));

		this.intBuffer.put((Q << 16) | P);

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
		//             4x4y4z 4u4v 4s4t 4q4p rgba
		// pos           0     12   20   24   28   32
		// size          12    8    4    4    4
		//
		// @formatter:on

		this.intBuffer.rewind();
		this.floatBuffer.rewind();
		this.shortBuffer.rewind();
		this.byteBuffer.rewind();
		// vertexBuffer.rewind();
		// colorBuffer.rewind();
		// uvBuffer.rewind();
		// stBuffer.rewind();
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
}