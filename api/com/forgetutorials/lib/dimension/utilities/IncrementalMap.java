package com.forgetutorials.lib.dimension.utilities;

import java.util.HashMap;
import java.util.Set;

public class IncrementalMap<Type extends IncrementalObject> {

	protected HashMap<Type, Type> counter = new HashMap<Type, Type>();

	public void add(Type item) {
		if (counter.containsKey(item)) {
			counter.get(item).increment(item.current);
		} else {
			counter.put(item,item);
		}
	}

	public Type get(Type item) {
		if (counter.containsKey(item)) {
			return counter.get(item);
		} else {
			return item;
		}
	}

	public Set<Type> getList() {
		return counter.keySet();
	}

}
