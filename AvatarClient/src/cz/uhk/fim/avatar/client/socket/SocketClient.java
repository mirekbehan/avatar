package cz.uhk.fim.avatar.client.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cz.uhk.fim.avatar.server.SocketListener;
import cz.uhk.fim.avatar.server.SocketSender;

public class SocketClient extends Thread {

	Socket socket;
	SocketListener listener;
	SocketSender sender;
	
	static SocketClient instance;

	public SocketClient(String host, int port) {
		instance = this;
		try {
			socket = new Socket(host, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sender = new SocketSender(socket);
		listener = new SocketListener(socket);

		setName(getClass().getSimpleName());

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

			// test if listener and sender works
			if (!listener.running || !sender.running) {
				running = false;

			}
			
		}
		
		listener.running = false;
		sender.running= false;
		
		
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
	
	static public void send(Object obj) {
		if (instance!=null && instance.sender!=null) instance.sender.send(obj);
	}

	
}
