package cz.uhk.fim.avatar.client.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SocketClient extends Thread {

	Socket socket;
	SocketListener listener;
	SocketSender sender;

	public SocketClient(String host, int port) {
		try {
			socket = new Socket(host, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sender = new SocketSender(socket);
		listener = new SocketListener(socket);

	}
	
	boolean running;
	@Override
	public void run() {
		sender.start();
		listener.start();
		running = true;
		
		while (running) {

			Object obj = receive();
			if (obj!=null) {
				for (OnReceiveListener e : executors) {
					e.onReceive(obj);
				}
			} else {
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	public void close() {
		if (socket!=null) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			socket = null;
		}
	}

	public Object receive() {
		return listener.receive();
	}
	
	static List<OnReceiveListener> executors = new ArrayList<OnReceiveListener>();
	static public void addOnReceive(OnReceiveListener listener) {
		executors.add(listener);
	}
	
	public void send(Object obj) {
		sender.send(obj);
	}

	
}
