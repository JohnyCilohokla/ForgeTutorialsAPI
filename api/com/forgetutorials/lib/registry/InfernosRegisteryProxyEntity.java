package com.forgetutorials.lib.registry;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;

import com.forgetutorials.multientity.InfernosMultiEntity;
import com.forgetutorials.multientity.InfernosMultiEntityType;
import com.forgetutorials.multientity.base.InfernosProxyEntityBase;

public enum InfernosRegisteryProxyEntity {
	INSTANCE();

	private class Container {
		Class<? extends InfernosProxyEntityBase> _class;
		InfernosProxyEntityBase instance;
		InfernosMultiEntityType type;

		public Container(Class<? extends InfernosProxyEntityBase> _class, InfernosProxyEntityBase instance, InfernosMultiEntityType type) {
			super();
			this._class = _class;
			this.instance = instance;
			this.type = type;
		}
	}

	private HashMap<String, Container> proxyEntities;
	private HashMap<String, String> compatibilityNames;
	private HashMap<String, Icon> icons;
	private List<String> iconsToRegister;

	InfernosRegisteryProxyEntity() {
		this.proxyEntities = new HashMap<String, Container>();
		this.proxyEntities.put("null", new Container(null, null, null));
		this.compatibilityNames = new HashMap<String, String>();
		this.icons = new HashMap<String, Icon>();
		this.iconsToRegister = new ArrayList<String>();
	}

	private Container getProxyContainer(String name) {
		Container container = this.proxyEntities.get(name);
		return container != null ? container : this.proxyEntities.get("null");
	}

	public InfernosMultiEntityType getType(String name) {
		return getProxyContainer(name).type;
	}

	public boolean addCompatibilityName(String oldName, String newName) {
		if (this.compatibilityNames.containsKey(oldName)) {
			return false;
		}
		Logger.getLogger("MES").log(Level.INFO, "Registering Compability Name " + oldName + " as " + newName);
		this.compatibilityNames.put(oldName, newName);
		return true;
	}

	public boolean addMultiEntity(String name, Class<? extends InfernosProxyEntityBase> entity, InfernosMultiEntityType type) {
		if (this.proxyEntities.containsKey(name)) {
			return false;
		}
		Logger.getLogger("MES").log(Level.INFO, "Registering ProxyEntity " + name + " as " + entity.getCanonicalName() + " (" + type + ")");
		this.proxyEntities.put(name, new Container(entity, null, type));
		return true;
	}

	public boolean overrideMultiEntity(String name, Class<? extends InfernosProxyEntityBase> entity, InfernosMultiEntityType type) {
		if (!this.proxyEntities.containsKey(name)) {
			return false;
		}
		Logger.getLogger("MES").log(
				Level.INFO,
				"Override ProxyEntity " + name + " from " + getProxyContainer(name)._class.getCanonicalName() + " (" + getProxyContainer(name).type + ") to "
						+ entity.getCanonicalName() + " (" + type + ")");
		this.proxyEntities.put(name, new Container(entity, null, type));
		return true;
	}

	public InfernosProxyEntityBase newMultiEntity(String name, InfernosMultiEntity infernosMultiEntity) throws Exception {
		name = getCompatibleName(name);
		InfernosProxyEntityBase entity = null;
		try {
			Class<? extends InfernosProxyEntityBase> _class = getProxyContainer(name)._class;
			Constructor<? extends InfernosProxyEntityBase> constructor = _class.getConstructor(InfernosMultiEntity.class);
			entity = constructor.newInstance(infernosMultiEntity);
		} catch (Exception e) {
			System.out.println(">> DEBUG: newMultiEntity(" + name + ", " + infernosMultiEntity.getClass().getCanonicalName() + ")");
			throw (e);
		}
		return entity;
	}

	public InfernosProxyEntityBase getStaticMultiEntity(String name) {
		name = getCompatibleName(name);
		InfernosProxyEntityBase entity = getProxyContainer(name).instance;
		if (entity == null) {
			try {
				entity = newMultiEntity(name, new InfernosMultiEntity());
			} catch (Exception e) {
			}
			getProxyContainer(name).instance = entity;
		}
		return entity;
	}

	public void registerIcons(IconRegister iconRegister) {
		for (String iconPath : this.iconsToRegister) {
			this.icons.put(iconPath, iconRegister.registerIcon(iconPath));
		}
	}

	public void addIcon(String path) {
		this.iconsToRegister.add(path);
	}

	public Icon getIcon(String path) {
		return this.icons.get(path);
	}

	public String getCompatibleName(String entityName) {
		String newName = this.compatibilityNames.get(entityName);
		return (newName != null) ? newName : entityName;
	}
}
