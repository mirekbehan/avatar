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
	}
	
	LinkedBlockingQueue<Object> inQueue = new LinkedBlockingQueue<Object>(100);
	
	boolean running;
	
	@Override
	public void run() {
		
		running = true;
		
		while (running) {
			try {
				Object obj = ois.readObject();
				inQueue.put(obj);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public Object receive() {
		return inQueue.poll();
	}
	
	
}
