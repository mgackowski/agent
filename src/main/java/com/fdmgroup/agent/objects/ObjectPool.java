package com.fdmgroup.agent.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * Containing all objects available to agents and methods to create, retrieve
 * and delete them.
 * @author Mikolaj Gackowski
 *
 */
public class ObjectPool {
	
	private List<UseableObject> objects = new ArrayList<UseableObject>();
	
	private ObjectPool() {}
	
	public List<UseableObject> getObjects() {
		return objects;
	}
	
	public boolean addObject(UseableObject newObject) {
		return objects.add(newObject);
	}
	
	public boolean removeObject(UseableObject objectToRemove) {
		return objects.remove(objectToRemove);
	}

}
