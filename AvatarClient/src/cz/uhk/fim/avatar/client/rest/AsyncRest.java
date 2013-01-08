package cz.uhk.fim.avatar.client.rest;

import java.lang.reflect.Method;

import android.util.Log;

import com.google.gson.Gson;

public class AsyncRest implements OnResultListener {
	
	enum Type { GET, POST, PUT, DELETE,  HEAD }
	
	static int counter = 0;

	public AsyncRest(Object obj) {
		counter++;
		this.obj = obj;
		clazz = obj.getClass();
	}
	
	private Class<?> clazz;
	public Class<?> getObjectClass() {
		return clazz;
	}
	
	private Object obj;
	
	public String  getUri() {
		if (obj!=null) {
			Method method;
			Object uri = null;
			try {
				method = clazz.getDeclaredMethod("getApiBase", null);
				uri = method.invoke (obj, null);
				if (id!=null) {
					return uri.toString() + "/" + id;
				} else {
					return uri.toString();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} 
		return null;
	}
	
	public String getContentJSON() {
		Gson gson = new Gson();
		if (obj!=null )	{
			String payload = gson.toJson(obj);
			Log.i("Worker", "Payload(Size-"+payload.length()+"):" + payload);
			return payload;
		}
		return null;
	}

	public Request getRequest() {
		Request request = new Request(getUri());
		request.addHeader("Content-Type", "application/json");
		if (obj!=null)
		//request.addHeader("If-Modified-Since", com.getUpdatedMark());
		// e-tag request.addHeader("If-None-Match: "686897696a7c876b7e"
		//request.addHeader("X-Autorize", App.getToken().getHash());
		request.addPayload(getContentJSON());
		return request;
	}

	private Type type;
	private Long id;
	
	// REST GET
	private void common(Type type) {
		this.type = type;
		Worker.addAsyncRest(this);
	}
	
	public void get() {
		common(Type.GET);
	}
	public void get(Long id) {
		this.id = id;
		common(Type.GET);
	}

	
	// REST POST
	public void post() {
		common(Type.POST);
	}
	public void post(Long  id) {
		this.id = id;
		common(Type.POST);
	}
	
	// REST PUT
	public void put() {
		common(Type.PUT);
	}
	public void put(Long  id) {
		this.id = id;
		common(Type.PUT);
	}

	
	// REST DELETE
	public void delete() {
		common(Type.DELETE);
	}
	public void delete(Long  id) {
		this.id = id;
		common(Type.DELETE);
	}

	
	
	public boolean isGet() {
		return type!=null && type.equals(Type.GET);
	}
	public boolean isPost() {
		return type!=null && type.equals(Type.POST);
	}
	public boolean isPut() {
		return type!=null && type.equals(Type.PUT);
	}
	public boolean isDelete() {
		return type!=null && type.equals(Type.DELETE);
	}
	
	
	
	
	public void onStart(Object obj) {
	}

	public void onError(Object obj) {
	}

	public void onTimeOut(Object obj) {
	}
	

	private OnResultListener onResultListener;
	public AsyncRest setOnResultListener(OnResultListener listener) {
		this.onResultListener = listener;
		return this;
	}
	public void onResult(Object obj) {
		if (onResultListener!=null) onResultListener.onResult(obj);
	}

	
	
	
}
