package com.forgetutorials.lib.registry;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.forgetutorials.multientity.InfernosMultiEntity;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;

public enum InfernosRegisteryProxyEntity {
	INSTANCE();

	private HashMap<String, Class<? extends InfernosProxyEntityBase>> classes;

	InfernosRegisteryProxyEntity() {
		this.classes = new HashMap<String, Class<? extends InfernosProxyEntityBase>>();
	}

	public boolean addMultiEntity(String name, Class<? extends InfernosProxyEntityBase> entity) {
		if (this.classes.containsKey(name)) {
			return false;
		}
		Logger.getLogger("MES").log(Level.INFO, "Registering ProxyEntity " + name + " as " + entity.getCanonicalName());
		this.classes.put(name, entity);
		return true;
	}

	public boolean overrideMultiEntity(String name, Class<? extends InfernosProxyEntityBase> entity) {
		if (!this.classes.containsKey(name)) {
			return false;
		}
		Logger.getLogger("MES").log(Level.INFO, "Override ProxyEntity " + name + " from " + this.classes.get(name).getCanonicalName() + " to " + entity.getCanonicalName());
		this.classes.put(name, entity);
		return true;
	}

	public InfernosProxyEntityBase newMultiEntity(String name, InfernosMultiEntity infernosMultiEntity) {
		InfernosProxyEntityBase entity = null;
		try {
			Class<? extends InfernosProxyEntityBase> _class = this.classes.get(name);
			Constructor<? extends InfernosProxyEntityBase> constructor = _class.getConstructor(InfernosMultiEntity.class);
			entity = constructor.newInstance(infernosMultiEntity);
		} catch (Exception e) {
			Logger.getLogger("MES").log(Level.SEVERE, ">> DEBUG: newMultiEntity("+name+", "+infernosMultiEntity.getClass().getCanonicalName()+")");
			e.printStackTrace(System.err);
		}
		return entity;
	}
}
