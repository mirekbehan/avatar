package cz.uhk.fim.avatar;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class WorldObject {

	public WorldObject() {
		x = (int) ( Math.random() *  World.worldSizeX);
		y = (int) ( Math.random() *  World.worldSizeY);
		size = (int) ( Math.random() *  30);
		color = Color.argb(250, (int) (Math.random() *  255), 
				(int) (Math.random() *  255),
				(int) (Math.random() *  255));
	}
	
	int x,y;
	
	Paint paint;
	public Paint getPaint() {
		if (paint==null) {
			paint = new Paint();
			paint.setColor(color);
		}
		return paint;
	}

	int size;
	int color;
	
	public void onDraw(Canvas canvas) {
		canvas.drawCircle(getCanvasX(), getCanvasY(), size, getPaint());
	}

	
	private int getCanvasX () {
		return (int) (x * World.getRatioX());
	}

	private int getCanvasY () {
		return (int) (y * World.getRatioY());
	}
	
}
