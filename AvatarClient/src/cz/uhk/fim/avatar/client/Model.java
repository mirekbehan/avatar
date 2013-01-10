package cz.uhk.fim.avatar.client;

import cz.uhk.fim.avatar.Avatar;
import cz.uhk.fim.avatar.World;
import cz.uhk.fim.avatar.WorldObject;
import cz.uhk.fim.avatar.client.socket.OnReceiveListener;
import cz.uhk.fim.avatar.client.socket.SocketClient;

public class Model {

	static private Model instance;
	static public Model getInstance() {
		if (instance==null) instance = new Model();
		return instance;
	}
	
	public Model() {
		SocketClient.addOnReceive(new OnReceiveListener() {
			@Override
			public void onReceive(Object obj) {
				if (obj instanceof World) {
					setWorld(((World)obj));
				}
				if (obj instanceof Avatar) {
					Avatar a= (Avatar)obj;
					if (a.equals(avatar)) {
						avatar.setX(a.getX());
						avatar.setY(a.getY());
					}
				}
			}
		});
		SocketClient.send("JOIN");

	}
	private World world;
	public World getWorld() {
		return world;
	}
	public void setWorld(World value) {
		world = value;
	}

	private Avatar avatar;
	public Avatar getAvatar() {
		return avatar;
	}
	public void setAvatar(Avatar avatar) {
		this.avatar = avatar;
	}

	
}
