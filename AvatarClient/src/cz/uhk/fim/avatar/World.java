package cz.uhk.fim.avatar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
 
public class World implements Serializable {
	private static final long serialVersionUID = -2113207559920642982L;

	public int worldVisibleSizeX = 10, worldVisibleSizeY = 10;
	public int worldSizeX=50, worldSizeY=40;

	public float getRatioX() {
		return  worldSizeX / worldVisibleSizeX ;
	}
	public float getRatioY() {
		return worldSizeY  /worldVisibleSizeY ;
	}
	
	public List<WorldObject> treasures = new ArrayList<WorldObject>();
	public List<Avatar> avatars = new ArrayList<Avatar>();

	public List<WorldObject> getAllObjects() {
		List<WorldObject> list = new ArrayList<WorldObject>();
		list.addAll(treasures);
		list.addAll(avatars);
		return list;
	}
	public List<Avatar> getAllAvatars() {
		List<Avatar> list = new ArrayList<Avatar>();
		list.addAll(avatars);
		return list;
	}
	
	public void refresh(WorldObject wo) {
		if (wo instanceof Avatar) {
			Avatar a = (Avatar)wo;
			if (avatars.contains(wo)) {
				avatars.get(avatars.indexOf(wo)).merge(a);
			} else {
				avatars.add(a);
			}
		} else {
			if (treasures.contains(wo)) treasures.remove(wo);
			treasures.add(wo);
		}
	}

	// from center
	public List<WorldObject> getVisibleObjects(int x, int y) {
		List<WorldObject> list = new ArrayList<WorldObject>();
		for (WorldObject wo : treasures) {
			if (wo.getX() > x - worldVisibleSizeX/2
					&& wo.getX() < x + worldVisibleSizeX/2
						&& wo.getY() > y - worldVisibleSizeY/2
						&& wo.getY() < y +worldVisibleSizeY/2
					) {
				list.add(wo);
			}
		}
		return list;
	}

	static private World instance;
	static public World getInstance() {
		if (instance==null) instance = new World();
		return instance;
	}
	
	// all
	static public void randomize() {
		reset();
		for (int i=0; i < 100; i++) {
			getInstance().treasures.add(new WorldObject(getInstance()));
		}
	}
	static public void reset() {
		getInstance().treasures.clear();
		getInstance().avatars.clear();
	}
	

	static private Map<Avatar, List<Message>> messages = Collections.synchronizedMap(new HashMap<Avatar, List<Message>>());
	static public List<Message> getMessages(WorldObject wo) {
		List<Message> list = messages.get(wo);
		if (list==null) list = new ArrayList<Message>();
		return list;
	}
	static public List<Message> getMessages() {
		List<Message> list = new ArrayList<Message>();
		for (List<Message> l : messages.values()) {
			list.addAll(l);
		}
		return list;
	}
	static public void addMessage(Message message) {
		if (message!=null) {
			getMessages(message.from).add(message);
		}
	}
	static public void clearMessages(WorldObject wo) {
		getMessages(wo).clear();
	}
	
	
	static public Map<Integer, byte[]> images = Collections.synchronizedMap(new HashMap<Integer, byte[]>());
	static public void setImage(Integer id, byte[] imageData) {
		images.put(id, imageData);
	}
	static public byte[] getImage(int id) {
		return images.get(id);
	}
	
	static public Map<Integer, byte[]> imagesDefault = Collections.synchronizedMap(new HashMap<Integer, byte[]>());
//	static byte[] imageDefault;
	static public byte[] getImageDefault(int id) {
		if (imagesDefault.size() == 0) {
			for (int i = 1; i < 17; i++) {
				String name = "avatars_"+ (i < 10 ? "0"+i : ""+ i);
				try {
					File file = new File("WebContent/images/" + name + ".png");
					InputStream is = new FileInputStream(file);
					imagesDefault.put(i, getBytes(is));
					is.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		byte[] out =imagesDefault.get(id);
		return out;
	}
	
	
	public static byte[] getBytes(InputStream is) throws IOException {

	    int len;
	    int size = 1024;
	    byte[] buf;

	    if (is instanceof ByteArrayInputStream) {
	      size = is.available();
	      buf = new byte[size];
	      len = is.read(buf, 0, size);
	    } else {
	      ByteArrayOutputStream bos = new ByteArrayOutputStream();
	      buf = new byte[size];
	      while ((len = is.read(buf, 0, size)) != -1)
	        bos.write(buf, 0, len);
	      buf = bos.toByteArray();
	    }
	    return buf;
	  }
	
	
}


