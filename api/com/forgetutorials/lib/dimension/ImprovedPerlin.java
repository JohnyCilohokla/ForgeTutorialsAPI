package com.forgetutorials.lib.dimension;

import java.util.Random;

public final class ImprovedPerlin {
	public final long seed;
	public final int sizeX;
	public final int sizeY;
	public final int sizeZ;
	public final double increment;
	public final double incrementZ;
	static final private int /*p[] = new int[512],*/permutation[] = { 151, 160, 137, 91, 90, 15, 131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30,
			69, 142, 8, 99, 37, 240, 21, 10, 23, 190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 88, 237, 149,
			56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166, 77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220,
			105, 92, 41, 55, 46, 245, 40, 244, 102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196, 135, 130, 116,
			188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123, 5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47,
			16, 58, 17, 182, 189, 28, 42, 223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9, 129, 22, 39, 253, 19,
			98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228, 251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235,
			249, 14, 239, 107, 49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254, 138, 236, 205, 93, 222, 114, 67,
			29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180 };
	/*static {
		for (int i = 0; i < 256; i++)
			p[256 + i] = p[i] = permutation[i];
	}*/

	final private int p[] = new int[512];

	public ImprovedPerlin(long seed, int sizeX, int sizeY, int sizeZ, double increment, double incrementZ) {
		this.seed = seed;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
		this.increment = increment;
		this.incrementZ = incrementZ;
		Random rnd = new Random(seed);

		int tmp[] = new int[512];
		for (int i = 0; i < 256; i++) {
			tmp[i] = ImprovedPerlin.permutation[i];
		}
		for (int i = 0; i < 1024; i++) {
			int pos = rnd.nextInt(256);
			int temp = tmp[i % 256];
			tmp[i % 256] = tmp[pos];
			tmp[pos] = temp;
		}
		for (int i = 0; i < 256; i++) {
			this.p[256 + i] = this.p[i] = tmp[i];
		}
	}

	final public double noise(double x, double y, double z) {
		int X = ImprovedPerlin.fastfloor(x) & 255, Y = ImprovedPerlin.fastfloor(y) & 255, Z = ImprovedPerlin.fastfloor(z) & 255;
		x -= ImprovedPerlin.fastfloor(x);
		y -= ImprovedPerlin.fastfloor(y);
		z -= ImprovedPerlin.fastfloor(z);
		double u = ImprovedPerlin.fade(x), v = ImprovedPerlin.fade(y), w = ImprovedPerlin.fade(z);
		int A = this.p[X] + Y, AA = this.p[A] + Z, AB = this.p[A + 1] + Z, B = this.p[X + 1] + Y, BA = this.p[B] + Z, BB = this.p[B + 1] + Z;

		return ImprovedPerlin.lerp(w, ImprovedPerlin.lerp(v,
				ImprovedPerlin.lerp(u, ImprovedPerlin.grad(this.p[AA], x, y, z), ImprovedPerlin.grad(this.p[BA], x - 1, y, z)),
				ImprovedPerlin.lerp(u, ImprovedPerlin.grad(this.p[AB], x, y - 1, z), ImprovedPerlin.grad(this.p[BB], x - 1, y - 1, z))), ImprovedPerlin.lerp(v,
				ImprovedPerlin.lerp(u, ImprovedPerlin.grad(this.p[AA + 1], x, y, z - 1), ImprovedPerlin.grad(this.p[BA + 1], x - 1, y, z - 1)),
				ImprovedPerlin.lerp(u, ImprovedPerlin.grad(this.p[AB + 1], x, y - 1, z - 1), ImprovedPerlin.grad(this.p[BB + 1], x - 1, y - 1, z - 1))));
	}

	final public double noise(double x, double y) {
		int X = ImprovedPerlin.fastfloor(x) & 255, Y = ImprovedPerlin.fastfloor(y) & 255;
		x -= ImprovedPerlin.fastfloor(x);
		y -= ImprovedPerlin.fastfloor(y);
		double u = ImprovedPerlin.fade(x), v = ImprovedPerlin.fade(y);
		int A = this.p[X] + Y, AA = this.p[A], AB = this.p[A + 1], B = this.p[X + 1] + Y, BA = this.p[B], BB = this.p[B + 1];

		return ImprovedPerlin.lerp(v, ImprovedPerlin.lerp(u, ImprovedPerlin.grad(this.p[AA], x, y), ImprovedPerlin.grad(this.p[BA], x - 1, y)),
				ImprovedPerlin.lerp(u, ImprovedPerlin.grad(this.p[AB], x, y - 1), ImprovedPerlin.grad(this.p[BB], x - 1, y - 1)));
	}

	static final double fade(double t) {
		return t * t * t * ((t * ((t * 6) - 15)) + 10);
	}

	static final double lerp(double t, double a, double b) {
		return a + (t * (b - a));
	}

	/**
	 * 3d version of grad
	 * 
	 * @param hash
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	static final double grad(int hash, double x, double y, double z) {
		int h = hash & 15;
		double u = h < 8 ? x : y, v = h < 4 ? y : (h == 12) || (h == 14) ? x : z;
		return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
	}

	/**
	 * 2d version of grad
	 * 
	 * @param hash
	 * @param x
	 * @param y
	 * @return
	 */
	static final double grad(int hash, double x, double y) {
		int h = hash & 11;
		double u = h < 8 ? x : y, v = h < 4 ? y : (h == 12) || (h == 14) ? x : 0D;
		return ((h & 1) == 0 ? u : -u) + ((h & 2) == 0 ? v : -v);
	}

	// This method is a *lot* faster than using (int)Math.floor(x)
	private static final int fastfloor(double x) {
		return x > 0 ? (int) x : (int) x - 1;
	}

	final public void populate(double[] array, double varX, double varY, double varZ) {
		int X, Y, Z, pos = 0;
		double x, y, z, u, v, w;
		int A, B, AA, AB, BA, BB;
		double trueX, trueY, trueZ;

		trueZ = varZ * this.incrementZ;
		Z = ImprovedPerlin.fastfloor(trueZ) & 255;
		z = trueZ - ImprovedPerlin.fastfloor(trueZ);
		w = ImprovedPerlin.fade(z);

		double startX = varX * this.sizeX, startY = varY * this.sizeY;

		for (int posX = 0; posX < this.sizeX; posX++) {
			trueX = (startX + posX) * this.increment;
			X = ImprovedPerlin.fastfloor(trueX) & 255;
			x = trueX - ImprovedPerlin.fastfloor(trueX);
			u = ImprovedPerlin.fade(x);

			A = this.p[X];
			B = this.p[X + 1];
			for (int posY = 0; posY < this.sizeY; posY++) {
				trueY = (startY + posY) * this.increment;
				Y = ImprovedPerlin.fastfloor(trueY) & 255;
				y = trueY - ImprovedPerlin.fastfloor(trueY);
				v = ImprovedPerlin.fade(y);
				// for (int posZ = 0; posZ < 32; posZ++) {

				AA = this.p[A + Y] + Z;
				AB = this.p[A + Y + 1] + Z;
				BA = this.p[B + Y] + Z;
				BB = this.p[B + Y + 1] + Z;
				array[pos] = ImprovedPerlin.lerp(w, ImprovedPerlin.lerp(v,
						ImprovedPerlin.lerp(u, ImprovedPerlin.grad(this.p[AA], x, y, z), ImprovedPerlin.grad(this.p[BA], x - 1, y, z)),
						ImprovedPerlin.lerp(u, ImprovedPerlin.grad(this.p[AB], x, y - 1, z), ImprovedPerlin.grad(this.p[BB], x - 1, y - 1, z))), ImprovedPerlin
						.lerp(v,
								ImprovedPerlin.lerp(u, ImprovedPerlin.grad(this.p[AA + 1], x, y, z - 1), ImprovedPerlin.grad(this.p[BA + 1], x - 1, y, z - 1)),
								ImprovedPerlin.lerp(u, ImprovedPerlin.grad(this.p[AB + 1], x, y - 1, z - 1),
										ImprovedPerlin.grad(this.p[BB + 1], x - 1, y - 1, z - 1))));
				pos++;
				// }
			}
		}
	}

	final public void populateInter(double[] array, double varX, double varY) {
		int X, Y, Z, pos = 0;
		double x, y, z, u, v, w;
		int A, B, AA, AB, BA, BB;
		double trueX, trueY, trueZ;

		for (int posZ = 0; posZ <= this.sizeZ; posZ++) {
			trueZ = (posZ) * this.incrementZ;
			Z = ImprovedPerlin.fastfloor(trueZ) & 255;
			z = trueZ - ImprovedPerlin.fastfloor(trueZ);
			w = ImprovedPerlin.fade(z);

			double startX = varX * this.sizeX, startY = varY * this.sizeY;

			for (int posX = 0; posX <= this.sizeX; posX++) {
				trueX = (startX + posX) * this.increment;
				X = ImprovedPerlin.fastfloor(trueX) & 255;
				x = trueX - ImprovedPerlin.fastfloor(trueX);
				u = ImprovedPerlin.fade(x);

				A = this.p[X];
				B = this.p[X + 1];
				for (int posY = 0; posY <= this.sizeY; posY++) {
					trueY = (startY + posY) * this.increment;
					Y = ImprovedPerlin.fastfloor(trueY) & 255;
					y = trueY - ImprovedPerlin.fastfloor(trueY);
					v = ImprovedPerlin.fade(y);
					// for (int posZ = 0; posZ < 32; posZ++) {

					AA = this.p[A + Y] + Z;
					AB = this.p[A + Y + 1] + Z;
					BA = this.p[B + Y] + Z;
					BB = this.p[B + Y + 1] + Z;
					array[pos] = ImprovedPerlin.lerp(w, ImprovedPerlin.lerp(v,
							ImprovedPerlin.lerp(u, ImprovedPerlin.grad(this.p[AA], x, y, z), ImprovedPerlin.grad(this.p[BA], x - 1, y, z)),
							ImprovedPerlin.lerp(u, ImprovedPerlin.grad(this.p[AB], x, y - 1, z), ImprovedPerlin.grad(this.p[BB], x - 1, y - 1, z))),
							ImprovedPerlin.lerp(
									v,
									ImprovedPerlin.lerp(u, ImprovedPerlin.grad(this.p[AA + 1], x, y, z - 1),
											ImprovedPerlin.grad(this.p[BA + 1], x - 1, y, z - 1)),
									ImprovedPerlin.lerp(u, ImprovedPerlin.grad(this.p[AB + 1], x, y - 1, z - 1),
											ImprovedPerlin.grad(this.p[BB + 1], x - 1, y - 1, z - 1))));
					pos++;
					// }
				}
			}
		}
	}
	/*final public void populate(double[] array, double varX, double varY, double increase) {
		int X, Y, pos = 0;
		double x, y, u, v;
		int A, B, AA, AB, BA, BB;
		double trueX, trueY;
		for (int posX = 0; posX < this.sizeX; posX++) {
			trueX = varX + posX * increase;
			X = fastfloor(trueX) & 255;
			x = trueX - fastfloor(trueX);
			u = fade(x);

			A = p[X];
			B = p[X + 1];
			for (int posY = 0; posY < this.sizey; posY++) {
				trueY = varY + posY * increase;
				Y = fastfloor(trueY) & 255;
				y = trueY - fastfloor(trueY);
				v = fade(y);

				AA = p[A + Y];
				AB = p[A + Y + 1];
				BA = p[B + Y];
				BB = p[B + Y + 1];
				array[pos] = lerp(v, lerp(u, grad(p[AA], x, y), grad(p[BA], x - 1, y)), lerp(u, grad(p[AB], x, y - 1), grad(p[BB], x - 1, y - 1)));
				pos++;
			}
		}
	}*/

}