package cz.uhk.fim.avatar.client;

 
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import cz.uhk.fim.avatar.Avatar;
import cz.uhk.fim.avatar.Direction;
import cz.uhk.fim.avatar.client.socket.SocketClient;

public class MyAvatar extends Avatar {
	private static final long serialVersionUID = 788126546781346135L;

	public MyAvatar() {
		
		setRGBA(new int[]{(int)Math.random()*255,
				(int)Math.random()*255,
				(int)Math.random()*255, 255});
		
		name =  "Noname" + (int)(Math.random() * 1000);
		size = 10;
	}
	
	transient Bitmap photo;

	@Override
	public void move(Direction direction) {
		switch (direction) {
		case DOWN:
			setY(getY() +1); 
			break;
		case LEFT:
			setX(getX() - 1); 
			break;
		case RIGHT:
			setX(getX() + 1); 
			break;
		case UP:
			setY(getY() - 1); 
			break;
		default:
			break;
		}
		//Message message = new Message(this.getClone(), "move");
		//SocketClient.send(message);
		SocketClient.send(this.getClone());
	}

	boolean visible;

	@Override
	public void action() {
		if (visible) {
			visible = false;
		} else {
			visible = true;
		}
		SocketClient.send(this.getClone());
	}

	transient private Rect rPhoto;
	
	
	@Override
	public void onDraw(Object canvas) {
		if (canvas instanceof Canvas) {
			Canvas c = (Canvas)canvas;

			int x =c.getWidth() / 2;
			int y = c.getHeight() / 2;
			int size = c.getWidth() / 10;

			
			rPhoto = new Rect(x - size/2, y-size/2, x+size/2, y+size/2);
			
			if (photo!=null) {
				c.drawBitmap(photo, null, rPhoto, null);
			} else {
				c.drawCircle(x, y, size / 2, getPaint());
			}
			
			if (visible) c.drawText(getName(), x, y, getPaint());
			//canvas.drawCircle(getCanvasX(), getCanvasY(), size / 2, getPaint());
		}
	}


	transient Paint paint;
	public Paint getPaint() {
		if (paint==null) {
			paint = new Paint();
			paint.setColor(Color.rgb(getRed(), getGreen(), getBlue()));
		}
		return paint;
	}
	
	
	
}
