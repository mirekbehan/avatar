package cz.uhk.fim.avatar;

import java.io.Serializable;
import java.util.Random;

public class WorldObject implements Serializable {
	private static final long serialVersionUID = -1871425359588240597L;

	
	public WorldObject() {
		id = ((int)(Math.random() * 100000));
	}
	
	public WorldObject(World world) {
			this();
			x  = (int) ( Math.random() *  world.worldSizeX);
			y = (int) ( Math.random() *  world.worldSizeY);
			size = 5+ (int) ( Math.random() *  5);
			RGBA[0] = (int) (Math.random() *  255);
			RGBA[1] =	(int) (Math.random() *  255);
			RGBA[2]=	(int) (Math.random() *  255);
			RGBA[3] = 255;
	}
	
	int id;
	int x,y;
	protected int size;
	int[] RGBA = new int[4];

	byte[] imageData;
	String urlImage;

	
	@Override
	public String toString() {
		return "[id:"+ id +",X:" + getX() + ",Y:" + getY() + "size:" + size+"]";
	}
	@Override
	public boolean equals(Object obj) {
		if (obj!=null && obj instanceof WorldObject) {
			WorldObject wo =(WorldObject )obj; 
			if (this.id == wo.id) return true;
		}
		return false;
	}
	@Override
	public int hashCode() {
		return id;
	}

	
	
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getRed() {
		return RGBA[0];
	}
	public int getGreen() {
		return RGBA[1];
	}
	public int getBlue() {
		return RGBA[2];
	}
	public int getAlfa() {
		return RGBA[3];
	}
	public int[] getRGBA() {
		return RGBA;
	}
	public void setRGBA(int[] rGBA) {
		RGBA = rGBA;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte[] getImageData() {
		return imageData;
	}
	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}
	public String getUrlImage() {
		return urlImage;
	}
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	
	
}
