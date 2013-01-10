package cz.uhk.fim.avatar.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class SocketSender extends Thread {

	ObjectOutputStream oos;

	public SocketSender(Socket socket) {
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		setName(getClass().getSimpleName());

	}
	
	LinkedBlockingQueue<Object> outQueue = new LinkedBlockingQueue<Object>(100);
	
	public boolean running;
	
	@Override
	public void run() {
	
		running = true;
		
		while (running) {
		
			try {
				Object obj = outQueue.poll(1000, TimeUnit.MILLISECONDS);
				oos.writeObject(obj);
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
	}
	
	public boolean send(Object obj) {
		return outQueue.add(obj);
	}
	
}
