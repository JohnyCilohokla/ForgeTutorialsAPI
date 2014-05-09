package com.infernosstudio.johny;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Compressor {

	public static byte[] compress(ByteBuffer buffer) {

		byte[] bytesGZIP;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream output = null;
		try {
			output = new DataOutputStream(new GZIPOutputStream(out));
			output.writeInt(buffer.position());
			output.write(buffer.array(), 0, buffer.position());
		} catch (Exception e) {
			System.out.println(buffer.position() + " " + buffer.position());
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			bytesGZIP = out.toByteArray();
		}
		return bytesGZIP;
	}

	public static void decompress(byte[] bytes, ByteBuffer out) {
		DataInputStream input = null;

		try {
			input = new DataInputStream(new GZIPInputStream(new ByteArrayInputStream(bytes)));
			out.clear();
			int size = input.readInt();
			input.readFully(out.array(), 0, size);
			out.position(size);
			out.flip();
		} catch (Exception e) {
			System.out.println("out.position() = " + out.position());
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static byte[] compressNoSize(ByteBuffer buffer) {

		byte[] bytesGZIP;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		DataOutputStream output = null;
		try {
			output = new DataOutputStream(new GZIPOutputStream(out));
			output.write(buffer.array(), 0, buffer.position());
		} catch (Exception e) {
			System.out.println(buffer.position() + " " + buffer.position());
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			bytesGZIP = out.toByteArray();
		}
		return bytesGZIP;
	}
}
