package com.fdmgroup.agent.objects;

import java.util.ArrayList;
import java.util.List;

public class ObjectPool {
	
	private static ObjectPool instance = new ObjectPool();
	private List<UseableObject> objects = new ArrayList<UseableObject>();
	
	private ObjectPool() {}
	
	public static ObjectPool getInstance() {
		return instance;
	}
	
	public List<UseableObject> getObjects() {
		return objects;
	}
	
	public boolean addObject(UseableObject newObject) {
		return objects.add(newObject);
	}

}
