package cz.uhk.fim.avatar.client;

 import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
import cz.uhk.fim.avatar.Avatar;
import cz.uhk.fim.avatar.Direction;
import cz.uhk.fim.avatar.World;
import cz.uhk.fim.avatar.WorldObject;
import cz.uhk.fim.avatar.client.socket.OnReceiveListener;
import cz.uhk.fim.avatar.client.socket.SocketClient;

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
		timer.scheduleAtFixedRate(t, new Date(), 100);
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
			
			if (getAvatar()!=null && getWorld()!=null) {
				// draw all world objects
				for (WorldObject wo : getWorld().getVisibleObjects(getAvatar().getX(), getAvatar().getY())) {
					worldObjectView(wo, canvas);
				}
			}
			
			// draw avatar
			if (getAvatar()!=null)	getAvatar().onDraw(canvas);
				
			// draw control bar
			canvas.drawRect(rLeft, getControlPaint());
			canvas.drawText("<", rLeft.centerX(), rLeft.centerY(), getControlPaint());

			canvas.drawRect(rRight, getControlPaint());
			canvas.drawText(">", rRight.centerX(), rRight.centerY(), getControlPaint());

			canvas.drawRect(rUp, getControlPaint());
			canvas.drawText("▲", rUp.centerX(), rUp.centerY(), getControlPaint());

			canvas.drawRect(rDown, getControlPaint());
			canvas.drawText("▼", rDown.centerX(), rDown.centerY(), getControlPaint());

			canvas.drawRect(rAction, getControlPaint());
			canvas.drawText("●", rAction.centerX(), rAction.centerY(), getControlPaint());

/*			if (getWorld()!=null) {
				canvas.drawRect(0, 0, getWorld().worldSizeX*getWorld().getRatioX(),
						getWorld().worldSizeY*getWorld().getRatioY(), getControlPaint());
			}
				
			if (getAvatar()!=null && getWorld()!=null) {

				// show visible world boundaries
				int x = (int) (getAvatar().getX() * getWorld().getRatioX());
				int y = (int) (getAvatar().getY() * getWorld().getRatioY());
				canvas.drawRect(x -  getWorld().worldVisibleSizeX * getWorld().getRatioX() / 2,
						y -  getWorld().worldVisibleSizeY * getWorld().getRatioY() / 2,
						x +  getWorld().worldVisibleSizeX * getWorld().getRatioX() / 2,
						y +  getWorld().worldVisibleSizeY * getWorld().getRatioY() / 2,
						getControlPaint());

			}
*/			
			
		}
		super.onDraw(canvas);
	}

	
	public void worldObjectView(WorldObject wo, Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.rgb(wo.getRed(), wo.getGreen(), wo.getBlue()));
		
		int x = wo.getX() - (getAvatar().getX() - getWorld().worldVisibleSizeX / 2);
		int y = wo.getY() - (getAvatar().getY() - getWorld().worldVisibleSizeY / 2);
		int px = x * (canvas.getWidth() / getWorld().worldVisibleSizeX);
		int py = y * (canvas.getHeight() / getWorld().worldVisibleSizeY);
		
		int psize = ((canvas.getWidth() / getWorld().worldVisibleSizeX) / 15) * wo.getSize();
		
		canvas.drawCircle(px, py, psize , paint);
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int  x = (int) event.getX();
		int y = (int) event.getY();
		
		if (getAvatar()!=null) {
			if (rLeft.contains(x, y)) {
				getAvatar().move(Direction.LEFT);
			}
			if (rRight.contains(x, y)) {
				getAvatar().move(Direction.RIGHT);
			}
			if (rUp.contains(x, y)) {
				getAvatar().move(Direction.UP);
			}
			if (rDown.contains(x, y)) {
				getAvatar().move(Direction.DOWN);
			}
			if (rAction.contains(x, y)) {
				getAvatar().action();
			}
		}
		return super.onTouchEvent(event);
		//return false;
	}
	
	
	public World getWorld() {
		return Model.getInstance().getWorld();
	}
	public Avatar getAvatar() {
		return Model.getInstance().getAvatar();
	}
		
	
	
}
