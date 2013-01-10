package cz.uhk.fim.avatar;

import cz.uhk.fim.avatar.client.socket.SocketClient;

public class Avatar extends WorldObject {
	private static final long serialVersionUID = 4389991755107454877L;

	protected String name;
	Direction dir;
	int score;
	
	public void onDraw(Object canvas) {};
	
	public int getX() { return x; };
	public int getY() { return y; };
	
	public void move(Direction dir) {
		SocketClient.send(this.getClone());
	};
	public void action() {
		SocketClient.send(this.getClone());
	}

	
	
	public Object getClone() {
		Avatar out = new Avatar();
		out.id = this.id;
		out.x = this.x;
		out.y = this.y;
		out.size = this.size;
		out.RGBA = this.RGBA;
		out.urlImage = this.urlImage;
		out.dir = this.dir;
		out.imageData = this.imageData;
		out.score = this.score;
		return out;
	}
	
	public void merge(Avatar avatar) {
		this.dir = avatar.dir;
		this.imageData = avatar.imageData;
		this.name = avatar.name;
		this.RGBA = avatar.RGBA;
		this.score = avatar.score;
		this.size = avatar.size;
		this.x = avatar.x;
		this.y = avatar.y;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	};
	
	
	
}
