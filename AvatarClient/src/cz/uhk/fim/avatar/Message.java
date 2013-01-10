package cz.uhk.fim.avatar;

import java.io.Serializable;
import java.util.List;

public class Message implements Serializable {

	private static final long serialVersionUID = -4244296490021654207L;

	WorldObject from;
	// if to null = to all
	public List<WorldObject> to;

	Object content;
	
	public Message() {
	}
	
	public Message(WorldObject from, Object content) {
		this.content = content;
		this.from = from;
	}
	
	
	public Object getContent() {
		return content;
	}
	
	
	static public Message getWorld(WorldObject from) {
		return new Message(from, "GET_WORLD");
	}
	
}
