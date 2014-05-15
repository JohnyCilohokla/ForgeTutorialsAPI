package com.infernosstudio.johny;

import java.util.ArrayList;
import java.util.Arrays;

public class RandomNameGenerator {

	private ArrayList<String> vocals = new ArrayList<String>();
	private ArrayList<String> startConsonants = new ArrayList<String>();
	private ArrayList<String> endConsonants = new ArrayList<String>();
	private ArrayList<String> nameInstructions = new ArrayList<String>();

	// class variable
	final String lexicon = "abcdefghijklmnopqrstuwxyz1234567890";

	final java.util.Random rand = new java.util.Random();

	public RandomNameGenerator() {
		String demoVocals[] = { "a", "e", "i", "o", "u", "ei", "ai", "ou", "j", "ji", "y", "oi", "au", "oo" };

		String demoStartConsonants[] = { "b", "c", "d", "f", "g", "h", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "z", "ch", "bl", "br", "fl",
				"gl", "gr", "kl", "pr", "st", "sh", "th" };

		String demoEndConsonants[] = { "b", "d", "f", "g", "h", "k", "l", "m", "n", "p", "r", "s", "t", "v", "w", "z", "ch", "gh", "nn", "st", "sh", "th",
				"tt", "ss", "pf", "nt" };

		String nameInstructions[] = { "vd", "cvdvd", "cvd", "vdvd" };

		this.vocals.addAll(Arrays.asList(demoVocals));
		this.startConsonants.addAll(Arrays.asList(demoStartConsonants));
		this.endConsonants.addAll(Arrays.asList(demoEndConsonants));
		this.nameInstructions.addAll(Arrays.asList(nameInstructions));
	}

	/**
	 * 
	 * The names will look like this (v=vocal,c=startConsonsonant,d=endConsonants): vd, cvdvd, cvd, vdvd
	 * 
	 * @param vocals pass something like {"a","e","ou",..}
	 * @param startConsonants pass something like {"s","f","kl",..}
	 * @param endConsonants pass something like {"th","sh","f",..}
	 */
	public RandomNameGenerator(String[] vocals, String[] startConsonants, String[] endConsonants) {
		this.vocals.addAll(Arrays.asList(vocals));
		this.startConsonants.addAll(Arrays.asList(startConsonants));
		this.endConsonants.addAll(Arrays.asList(endConsonants));
	}

	/**
	 * see {@link RandomNameGenerator#RandomNameGenerator(String[], String[], String[])}
	 * 
	 * @param vocals
	 * @param startConsonants
	 * @param endConsonants
	 * @param nameInstructions Use only the following letters: (v=vocal,c=startConsonsonant,d=endConsonants)! Pass something like {"vd", "cvdvd", "cvd", "vdvd"}
	 */
	public RandomNameGenerator(String[] vocals, String[] startConsonants, String[] endConsonants, String[] nameInstructions) {
		this(vocals, startConsonants, endConsonants);
		this.nameInstructions.addAll(Arrays.asList(nameInstructions));
	}

	private String firstCharUppercase(String name) {
		return Character.toString(name.charAt(0)).toUpperCase() + name.substring(1);
	}

	public String getName() {
		return firstCharUppercase(getNameByInstructions(getRandomElementFrom(this.nameInstructions)));
	}

	private String getNameByInstructions(String nameInstructions) {
		StringBuilder name = new StringBuilder();
		int l = nameInstructions.length();

		for (int i = 0; i < l; i++) {
			char x = nameInstructions.charAt(0);
			switch (x) {
			case 'v':
				name.append(getRandomElementFrom(this.vocals));
				break;
			case 'c':
				name.append(getRandomElementFrom(this.startConsonants));
				break;
			case 'd':
				name.append(getRandomElementFrom(this.endConsonants));
				break;
			}
			nameInstructions = nameInstructions.substring(1);
		}
		return name.toString();
	}

	private String getRandomElementFrom(ArrayList<String> v) {
		return v.get(randomInt(0, v.size() - 1));
	}

	public String randomIdentifier(int stringLength) {
		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() < stringLength) {
			builder.append(this.lexicon.charAt(this.rand.nextInt(this.lexicon.length())));
		}
		return builder.toString();
	}

	private int randomInt(int min, int max) {
		return (int) (min + (Math.random() * ((max + 1) - min)));
	}

}
