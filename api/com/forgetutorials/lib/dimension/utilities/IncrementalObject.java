package com.forgetutorials.lib.dimension.utilities;

public class IncrementalObject {
	public long current;

	public IncrementalObject(long start) {
		this.current = start;
	}

	public void increment(long i) {
		this.current += i;
	}
}
