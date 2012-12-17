package cz.uhk.fim.avatar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class MyAvatar extends WorldObject implements IAvatar {

	static private MyAvatar instance;
	static public MyAvatar getInstance() {
		if (instance==null) instance = new MyAvatar();
		return instance;
	}
	
	enum Direction {
		RIGHT, LEFT, UP, DOWN;
	}
	
	public MyAvatar() {
		x = World.worldSizeX /2;
		y = World.worldSizeX /2;
		
	}
	
	
	Direction direction;
	String name = "UNKNOWN";
	Bitmap photo;
	@Override
	public void move(Direction direction) {
		switch (direction) {
		case DOWN:
			y +=1; 
			break;
		case LEFT:
			x -=1; 
			break;
		case RIGHT:
			x +=1; 
			break;
		case UP:
			y -=1;
			break;
		default:
			break;
		}
		
		
	}

	
	boolean visible;
	@Override
	public void action() {
		if (visible) {
			visible = false;
		} else {
			visible = true;
		}
	}

	public void setBitmap(Bitmap bmp) {
		photo = bmp;
	}
	
	
	private Rect rPhoto;
	
	int size = 10;
	
	
	private int getCanvasX () {
		return (int) (x * World.getRatioX());
	}

	private int getCanvasY () {
		return (int) (y * World.getRatioY());
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		
		rPhoto = new Rect(getCanvasX() - size/2, getCanvasY()-size/2, getCanvasX()+size/2, getCanvasY()+size/2);
		
		if (visible) {
			canvas.drawBitmap(photo, null, rPhoto, null);
		} else {
			canvas.drawCircle(getCanvasX(), getCanvasY(), size / 2, getPaint());
		}
		canvas.drawText(name, getCanvasX(), getCanvasY(), getPaint());
		//canvas.drawCircle(getCanvasX(), getCanvasY(), size / 2, getPaint());
		super.onDraw(canvas);
	}
	
	
	
	
	
}
