package cz.uhk.fim.avatar;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import cz.uhk.fim.avatar.MyAvatar.Direction;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;

public class WorldView extends View {

	
	public WorldView(Context context) {
		super(context);
		setup(this);
	}

	public WorldView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup(this);
	}

	Timer timer;
	public void setup(final WorldView wv) {
		TimerTask t = new TimerTask() {
			@Override
			public void run() {
				wv.postInvalidate();
			}
		};
		timer = new Timer();
		timer.scheduleAtFixedRate(t, new Date(), 500);
	}
	
	Paint backPaint;
	public Paint getBackPaint() {
		if (backPaint==null) {
			backPaint = new Paint();
			backPaint.setColor(Color.BLUE);
		}
		return backPaint;
	}

	Paint controlPaint;
	public Paint getControlPaint() {
		if (controlPaint==null) {
			controlPaint = new Paint();
			controlPaint.setColor(Color.LTGRAY);
			controlPaint.setTextAlign(Align.CENTER);
			controlPaint.setStyle(Style.STROKE);
			controlPaint.setTextSize(20);
			controlPaint.setStrokeWidth(3);
		}
		return controlPaint;
	}

	
	Paint frontPaint;
	public Paint getFrontPaint() {
		if (frontPaint==null) {
			frontPaint = new Paint();
			frontPaint.setColor(Color.WHITE);
		}
		return frontPaint;
	}

	int width, height;
	Rect rLeft, rRight, rUp, rDown, rAction;

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		width = MeasureSpec.getSize(widthMeasureSpec);
		height = MeasureSpec.getSize(heightMeasureSpec);
		int size = width / 5;
		rLeft = new Rect(0 * size, height-size, 1 * size, height);
		rRight 	= new Rect(1 * size, height-size, 2 * size, height);
		rUp 		= new Rect(2 * size, height-size, 3 * size, height);
		rDown	= new Rect(3 * size, height-size, 4 * size, height);
		rAction	= new Rect(4 * size, height-size, 5 * size, height);
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@SuppressLint("NewApi")
	@Override
	protected void onDraw(Canvas canvas) {
		if (isInEditMode()) { 
			canvas.drawRect(0, 0, width, height, getBackPaint());
			canvas.drawText("This is world", width/2, height/2, getFrontPaint());
		} else {
			
			for (WorldObject wo : World.getVisibleWorldObject()) {
				wo.onDraw(canvas);
			}
			
			MyAvatar.getInstance().onDraw(canvas);

			// draw control bar
			canvas.drawRect(rLeft, getControlPaint());
			canvas.drawText("<", rLeft.centerX(), rLeft.centerY(), getControlPaint());

			canvas.drawRect(rRight, getControlPaint());
			canvas.drawText(">", rRight.centerX(), rRight.centerY(), getControlPaint());

			canvas.drawRect(rUp, getControlPaint());
			canvas.drawText("u", rUp.centerX(), rUp.centerY(), getControlPaint());

			canvas.drawRect(rDown, getControlPaint());
			canvas.drawText("d", rDown.centerX(), rDown.centerY(), getControlPaint());

			canvas.drawRect(rAction, getControlPaint());
			canvas.drawText("a", rAction.centerX(), rAction.centerY(), getControlPaint());

			canvas.drawRect(0, 0, World.worldSizeX*World.getRatioX(),
					World.worldSizeY*World.getRatioY(), getControlPaint());
			
			int x = (int) (MyAvatar.getInstance().x * World.getRatioX());
			int y = (int) (MyAvatar.getInstance().y * World.getRatioY());
			canvas.drawRect(x -  World.worldVisibleSizeX * World.getRatioX() / 2,
					y -  World.worldVisibleSizeY * World.getRatioY() / 2,
					x +  World.worldVisibleSizeX * World.getRatioX() / 2,
					y +  World.worldVisibleSizeY * World.getRatioY() / 2,
					getControlPaint());
			
		}
		super.onDraw(canvas);
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int  x = (int) event.getX();
		int y = (int) event.getY();
		
		if (rLeft.contains(x, y)) {
			MyAvatar.getInstance().move(Direction.LEFT);
		}
		if (rRight.contains(x, y)) {
			MyAvatar.getInstance().move(Direction.RIGHT);
		}
		if (rUp.contains(x, y)) {
			MyAvatar.getInstance().move(Direction.UP);
		}
		if (rDown.contains(x, y)) {
			MyAvatar.getInstance().move(Direction.DOWN);
		}
		if (rAction.contains(x, y)) {
			MyAvatar.getInstance().action();
		}
		
		return super.onTouchEvent(event);
		//return false;
	}
	
	
	
	
	
	
	
}
