package cz.uhk.fim.avatar;

import cz.uhk.fim.avatar.client.socket.SocketClient;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//Worker.go(this);
		new SocketClient("localhost", 9999).start();
		
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		MyAvatar.getInstance().setBitmap(bmp);
		
		World.randomize();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	
	@Override
	protected void onDestroy() {

		//Worker.end();
		super.onDestroy();
	}
	

}
