package cz.uhk.fim.avatar.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketListener extends Thread {

	ObjectInputStream ois;

	public SocketListener(Socket socket) {
		try {
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		setName(getClass().getSimpleName());

	}
	
	LinkedBlockingQueue<Object> inQueue = new LinkedBlockingQueue<Object>(100);
	
	public boolean running;
	
	@Override
	public void run() {
		
		running = true;
		
		Object obj;
		while (running) {
			try {
				obj = ois.readObject();
				if (obj!=null) inQueue.put(obj);
				
			} catch (IOException e) {
				running = false;
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				running = false;
				e.printStackTrace();
			} catch (InterruptedException e) {
				running = false;
				e.printStackTrace();
			}
		}
		
		
		
	}
	
	public Object receive() {
		return inQueue.poll();
	}
	
	
}
