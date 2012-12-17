package cz.uhk.fim.avatar;

import java.util.ArrayList;
import java.util.List;

import cz.uhk.fim.avatar.client.AsyncRest;
import cz.uhk.fim.avatar.client.OnResultListener;

public class World {

	
	static int worldVisibleSizeX = 10, 
					  worldVisibleSizeY = 10;
	static int worldSizeX=50, 
					  worldSizeY=50;

	static int worldFieldSizeX=10, 
			  worldFieldSizeY=10;
	
	
	static public List<WorldObject> getVisibleWorldObject() {

		final List<WorldObject> list = new ArrayList<WorldObject>();
		
		if (instance!=null) {
			new AsyncRest(instance)
				.setOnResultListener(new OnResultListener() {
				@Override
				public void onResult(Object obj) {
					if (obj instanceof World) {
						instance.allObjects = ((World)obj).allObjects;
					}
				}
			}).post();
			
			for (WorldObject wo : instance.allObjects) {
				if (wo.x > MyAvatar.getInstance().x - worldVisibleSizeX/2
					&& wo.x < MyAvatar.getInstance().x + worldVisibleSizeX/2
					&& wo.y > MyAvatar.getInstance().y - worldVisibleSizeY/2
					&& wo.y < MyAvatar.getInstance().y + worldVisibleSizeY/2
				) {
					list.add(wo);
				}
			}
		}
		return list;
	}

	static public World instance;
	
	public World() {
		instance = this;
		new AsyncRest(this).setOnResultListener(new OnResultListener() {
			@Override
			public void onResult(Object obj) {
				if (obj instanceof World) {
					allObjects = ((World)obj).allObjects;
				}
			}
		}).get();
		
	}
	
	List<WorldObject> allObjects = new ArrayList<WorldObject>();

	
	static public void randomize() {
		if (instance ==null ) instance = new World();
		instance.allObjects = new ArrayList<WorldObject>();
		for (int i=0; i < 100; i++) {
			instance.allObjects.add(new WorldObject());
		}
	}

	static public float getRatioX() {
		return  worldSizeX / worldVisibleSizeX ;
	}
	static public float getRatioY() {
		return worldSizeY  /worldVisibleSizeY ;
	}
	
	
	
}


