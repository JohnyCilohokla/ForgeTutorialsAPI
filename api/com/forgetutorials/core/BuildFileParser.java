package com.forgetutorials.core;

import java.io.File;
import java.util.HashMap;

import com.infernosstudio.johny.JC;
import com.infernosstudio.johny.files.InfernosFileParser;

public class BuildFileParser extends InfernosFileParser {

	String exts[] = new String[] { "java", "info", "lang", "gradle" };
	HashMap<String, String> pramMap = new HashMap<String, String>();

	public BuildFileParser() {
	}

	public void addPram(String pram, String value) {
		this.pramMap.put(pram, value);
	}

	@Override
	public String parse(String in, String comment) {
		StringBuilder builder = new StringBuilder();
		char c;
		boolean parameter = false;
		String pram = "";
		int i = 0;
		while (i < in.length()) {
			c = in.charAt(i);
			if (c == '@') {
				if (parameter) {
					if (this.pramMap.containsKey(pram)) {
						builder.append(this.pramMap.get(pram));
						JC.outF("@PARSED:" + pram + "@" + comment);
					} else {
						builder.append("@" + pram + "@");
						JC.outF("@MISSED:" + pram + "@" + comment);
					}
					parameter = false;
				} else {
					pram = "";
					parameter = true;
				}
			} else if (parameter && !(((c >= 'A') && (c <= 'Z')) || ((c >= '0') && (c <= '9')) || (c == '-') || (c == '_') || (c == '.'))) {
				builder.append("@" + pram);
				builder.append(c);
				pram = "";
				parameter = false;
			} else if (parameter) {
				pram += c;
			} else {
				builder.append(c);
			}
			i++;
		}
		builder.trimToSize();
		return builder.toString();
	}

	@Override
	public boolean shouldParse(File sourceFile) {
		String path = sourceFile.getAbsolutePath();
		String ext = path.substring(path.lastIndexOf(".") + 1);

		for (String extx : this.exts) {
			if (ext.equalsIgnoreCase(extx)) {
				return true;
			}
		}
		return false;
	}
}
