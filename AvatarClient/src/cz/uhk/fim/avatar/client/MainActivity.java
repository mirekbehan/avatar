package cz.uhk.fim.avatar.client;

import java.io.ByteArrayOutputStream;

import cz.uhk.fim.avatar.Message;
import cz.uhk.fim.avatar.World;
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
		new SocketClient("angelmobil.com", 9999).start();
		//new SocketClient("10.0.0.1", 9999).start();
		//new SocketClient("192.168.1.7", 9999).start();
		
		
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		
		MyAvatar avatar = new MyAvatar();
		avatar.setImageData(byteArray);
		Model.getInstance().setAvatar(avatar);
		SocketClient.send(avatar.getClone());

		
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
