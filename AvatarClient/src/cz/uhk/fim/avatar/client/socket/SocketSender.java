package cz.uhk.fim.avatar.client.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketSender extends Thread {

	ObjectOutputStream oos;

	public SocketSender(Socket socket) {
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	LinkedBlockingQueue<Object> outQueue = new LinkedBlockingQueue<Object>(100);
	
	@Override
	public void run() {
		
		try {
			Object obj = outQueue.take();
			oos.writeObject(obj);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean send(Object obj) {
		return outQueue.add(obj);
	}
	
}
