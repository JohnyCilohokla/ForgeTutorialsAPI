package com.infernosstudio.johny.files;

import java.io.File;


public abstract class InfernosFileParser {
	
	public String parse(String in){
		return this.parse(in, "");
	}

	public boolean shouldParse(File sourceFile) {
		return true;
	}

	abstract public String parse(String in, String comment);
}
