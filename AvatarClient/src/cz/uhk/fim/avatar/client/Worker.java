package cz.uhk.fim.avatar.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.google.gson.Gson;

public	class Worker extends Thread {

	static private BlockingQueue<AsyncRest> qUpload = new LinkedBlockingQueue<AsyncRest>(100);
	static public void addAsyncRest(AsyncRest as) {

		try {
			qUpload.put(as);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			
/*		try {
//			qUpload.offer(as, 10, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
*/	}
	
	static private Worker instance;
	static public void go(Context context, int trustStore) {
		if (instance==null) {
			instance = new Worker(context, trustStore);
			instance.start();
		}
	}
	static public void go(Context context) {
		if (instance==null) {
			instance = new Worker(context);
			instance.start();
		}
	}
	
	static public void end() {
		running = false;
	}
	static public boolean isRunning() {
		return running;
	}

	
	static private boolean running;
	
 	static ConnectivityManager cm;

	static RestClient restClient;

 	public String getHost() {
 	 		return "localhost:9998";
 	}

 	public Worker(Context context) {
		setName("Worker");
		running = false;
		cm = (ConnectivityManager)context.getSystemService(Service.CONNECTIVITY_SERVICE);
		restClient = new RestClient(getHost());
 	}
	
 	
	public Worker(Context context, int trustStore) {
		setName("Worker");
		running = false;
		cm = (ConnectivityManager)context.getSystemService(Service.CONNECTIVITY_SERVICE);
		restClient = new RestClient(getHost(), context.getResources(), trustStore);
	}

	@Override
	public void run() {
		running = true;
		while(running) {
			while (cm.getActiveNetworkInfo()==null) {
				Log.i(getClass().getSimpleName(), "No network connection waiting 10s"); 
				//context.sendBroadcast(new Intent("NO_NETWORK"));
				 //Toast.makeText(context, "No network connection", 1000).show();
				 try {
					 sleep(10000);
				 } catch (InterruptedException e) {
					 e.printStackTrace();
				 }
			}

			while(qUpload.size() > 0) {
				try {
					AsyncRest asyncRest = qUpload.take();
					Response res = null;
					
					try {
						if (asyncRest.isGet()) {
							res = restClient.get(asyncRest.getRequest());
						} else if (asyncRest.isPost()) {
							res = restClient.post(asyncRest.getRequest());
						} else if  (asyncRest.isPut()) {
							res = restClient.put(asyncRest.getRequest());
						} else if (asyncRest.isDelete()) {
							res = restClient.delete(asyncRest.getRequest());
						}
					} catch (Exception e) {
						asyncRest.onError(e.getMessage());
					}
	
					
					if (res!=null) {
						String out =res.getResponse();
						Log.i("Worker", "Received:" + out);
						Object obj;
						try {
							Gson g = new Gson();
							obj = g.fromJson(out, asyncRest.getObjectClass());
							asyncRest.onResult(obj);
						} catch (Exception e) {
							asyncRest.onError(out);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}

			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Log.i("Worker", "Quiting ...");
	}
	
	
}

