package com.infernosstudio.johny;

public class InfernosTimer {

	long start;
	long last = -1;
	String lastName = "";
	long end = -1;
	
	public InfernosTimer(String name) {
		this.lastName = name;
		this.start = System.nanoTime();
		this.last = System.nanoTime();
	}
	
	public InfernosTimer tick(String name){
		this.lastName = name;
		last = System.nanoTime();
		return this;
	}
	
	public InfernosTimer end(){
		end = System.nanoTime();
		return this;
	}
	
	@Override
	public String toString() {
		return "InfernosTimer [\n"+
					"\ttotal: "+((((end!=-1)?this.end:System.nanoTime())-this.start)/1000000L/1000.0)+"s,\n" +
					((last!=-1)?("\tlast: "+((System.nanoTime()-this.last)/1000000L/1000.0)+"s ("+lastName+")\n"):"") +
					"]"
	
				;
	}
}
