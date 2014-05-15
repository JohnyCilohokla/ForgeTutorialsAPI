package com.infernosstudio.johny;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Arrays;

public class JC {
	public static class DoubleStream extends OutputStream {
		OutputStream first;
		OutputStream second;

		public DoubleStream(OutputStream first, OutputStream second) {
			this.first = first;
			this.second = second;
		}

		@Override
		public void close() throws IOException {
			this.first.close();
			this.second.close();
		}

		@Override
		public void flush() throws IOException {
			this.first.flush();
			this.second.flush();
		}

		@Override
		public void write(byte[] b) throws IOException {
			this.first.write(b);
			this.second.write(b);
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			this.first.write(b, off, len);
			this.second.write(b, off, len);
		}

		@Override
		public void write(int b) throws IOException {
			this.first.write(b);
			this.second.write(b);
		}
	}

	public final static Charset utf8 = Charset.forName("UTF-8");
	private static PrintStream outF;
	private static PrintStream errF;
	private static PrintStream out;
	private static PrintStream err;

	private static PrintStream logExt;
	private static boolean logs = false;

	public static void changeLogs(File out, File ext, File err) {
		JC.outF = JC.outputFile(out);
		JC.errF = JC.outputFile(err);
		JC.logExt = JC.outputFile(ext);
		JC.out = new PrintStream(new DoubleStream(System.out, JC.outF), true);
		JC.err = new PrintStream(new DoubleStream(System.err, JC.errF), true);
		System.setOut(JC.out);
		System.setErr(JC.err);
		JC.logs = true;
	}

	public static void defaultLogs() {
		JC.outF = JC.outputFile("log-out");
		JC.errF = JC.outputFile("log-err");
		JC.logExt = JC.outputFile("log-ext");
		JC.out = new PrintStream(new DoubleStream(System.out, JC.outF), true);
		JC.err = new PrintStream(new DoubleStream(System.err, JC.errF), true);
		System.setOut(JC.out);
		System.setErr(JC.err);
		JC.logs = true;
	}

	public static void err(String string) {
		if (JC.logs == false) {
			JC.defaultLogs();
		}
		System.err.println(string);
		JC.errF.flush();
	}

	public static void errF(String string) {
		if (JC.logs == false) {
			JC.defaultLogs();
		}
		JC.errF.println(string);
		JC.errF.flush();
	}

	public static void log(String string) {
		if (JC.logs == false) {
			JC.defaultLogs();
		}
		JC.logExt.println(string);
		JC.logExt.flush();
	}

	public static void out(String string) {
		if (JC.logs == false) {
			JC.defaultLogs();
		}
		System.out.println(string);
		JC.outF.flush();
	}

	public static void out(char[] chars) {
		if (JC.logs == false) {
			JC.defaultLogs();
		}
		System.out.println(chars);
		JC.outF.flush();
	}

	public static void outF(String string) {
		if (JC.logs == false) {
			JC.defaultLogs();
		}
		JC.outF.println(string);
		JC.outF.flush();
	}

	protected static PrintStream outputFile(String name) {
		PrintStream stream = null;
		try {
			stream = new PrintStream(new BufferedOutputStream(new FileOutputStream(name)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return stream;
	}

	protected static PrintStream outputFile(File file) {
		PrintStream stream = null;
		try {
			stream = new PrintStream(new BufferedOutputStream(new FileOutputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return stream;
	}

	public static int powInt(int n, int p) {
		int out = n;
		for (int i = 1; i < p; i++) {
			out *= n;
		}
		return out;
	}

	private static RandomNameGenerator rng = new RandomNameGenerator();

	public static String getRandomIdentifier(int lenght) {
		return JC.rng.randomIdentifier(lenght);
	}

	public static String fString(char charToFill, int length) {
		if (length > 0) {
			char[] array = new char[length];
			Arrays.fill(array, charToFill);
			return new String(array);
		}
		return "";
	}

	public static boolean isTrue(String string) {
		return string == null ? false : (string.equalsIgnoreCase("true") || string.equalsIgnoreCase("yes") || string.equalsIgnoreCase("1")
				|| string.equalsIgnoreCase("ok") || string.equalsIgnoreCase("on"));
	}

	public static boolean isFalse(String string) {
		return string == null ? true : (string.equalsIgnoreCase("false") || string.equalsIgnoreCase("no") || string.equalsIgnoreCase("-1")
				|| string.equalsIgnoreCase("nope") || string.equalsIgnoreCase("off"));
	}

	public static void out(InfernosTimer timer) {
		JC.out(timer.toString());
	}
}
