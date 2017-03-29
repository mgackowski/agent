package com.mgackowski.agent.objects;

import java.util.ArrayList;
import java.util.List;

/**
 * A pool containing all Objects present in a given simulation. Contains methods to add, remove
 * and retrieve Objects.
 * @author Mikolaj.Gackowski
 *
 */
public class ObjectPool {
	
	private List<UseableObject> objects = new ArrayList<UseableObject>();
	
	/**
	 * @return a List of Objects in the pool
	 */
	public List<UseableObject> getObjects() {
		return objects;
	}
	
	/**
	 * @param newObject a new Object to add to the ObjectPool
	 * @return true if new Object was successfully added
	 */
	public boolean addObject(UseableObject newObject) {
		return objects.add(newObject);
	}
	
	/**
	 * @param objectToRemove the Object to remove from the ObjectPool
	 * @return true if new Object was successfully removed
	 */
	public boolean removeObject(UseableObject objectToRemove) {
		return objects.remove(objectToRemove);
	}

}
