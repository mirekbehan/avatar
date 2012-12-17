package cz.uhk.fim.avatar;

import cz.uhk.fim.avatar.MyAvatar.Direction;
import android.graphics.Canvas;


public interface IAvatar {
	
	
	public void move(Direction direction);
	public void action();

	public void onDraw(Canvas canvas);

	
	
}

