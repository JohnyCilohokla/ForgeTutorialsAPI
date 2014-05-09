package com.infernosstudio.johny;

import java.util.regex.Pattern;

public class JCUtils {

	static public String[] split(CharSequence text, String separator) {
		return Pattern.compile(separator, Pattern.LITERAL).split(text);
	}

	public static String join(String[] strings, String join) {
		if ((strings == null) || (strings.length == 0)) {
			return "";
		} else if (strings.length == 1) {
			return strings[0];
		} else {
			StringBuilder sb = new StringBuilder();
			sb.append(strings[0]);
			for (int i = 1; i < strings.length; i++) {
				sb.append(join).append(strings[i]);
			}
			return sb.toString();
		}
	}

	public static String removeComments(String text, String start, String end, String line) {
		StringBuilder out = new StringBuilder(text.length());
		int i = 0;
		boolean lineComment = false;
		boolean blockComment = false;
		while (i < text.length()) {
			if (blockComment) {
				if ((text.length() >= (i + end.length())) && (text.substring(i, i + end.length()).equals(end))) {
					blockComment = false;
					i += end.length();
				} else {
					i++;
				}
			} else if (lineComment) {
				if ((text.length() >= (i + 2)) && (text.charAt(i) == '\r') && (text.charAt(i + 1) == '\n')) {
					lineComment = false;
					i++;
					i++;
				} else if ((text.length() >= (i + 1)) && (text.charAt(i + 1) == '\n')) {
					lineComment = false;
					i++;
				} else if ((text.length() >= (i + 1)) && (text.charAt(i) == '\r')) {
					lineComment = false;
					i++;
				} else {
					i++;
				}
			} else if ((text.length() >= (i + start.length())) && (text.substring(i, i + start.length()).equals(start))) {
				blockComment = true;
				i += start.length();
			} else if ((text.length() >= (i + line.length())) && (text.substring(i, i + line.length()).equals(line))) {
				lineComment = true;
				i += line.length();
			} else {
				out.append(text.charAt(i));
				i++;
			}
		}

		return out.toString();
	}

	public static String normalizeScript(String text, boolean replaceSpecial, boolean removeComments) {
		String parsered = text;
		if (removeComments) {
			parsered = JCUtils.removeComments(parsered, "/*#", "#*/", "//#");
		}
		if (replaceSpecial) {
			parsered = parsered.replace("\t", "").replace("\\t", "\t");
			parsered = parsered.replace(System.lineSeparator(), "").replace("\\n", System.lineSeparator());
			parsered = parsered.replace("\\#n", "\\n");
		}
		return parsered;
	}

	public static String numberString(String text, String def) {
		try {
			String out = "" + Integer.parseInt(text);
			if ((out == null) || out.equals("")) {
				return def;
			}
			return out;
		} catch (Exception e) {
			return def;
		}
	}
}
