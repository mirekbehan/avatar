package cz.uhk.fim.avatar.client.rest;

import java.io.InputStream;
import java.security.KeyStore;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.HttpParams;

import android.R;
import android.content.res.Resources;

public class MyHttpClient extends DefaultHttpClient {

private Resources resources;
private int resource;


public MyHttpClient(HttpParams httpParams) {
	super(httpParams);
}

public MyHttpClient(HttpParams httpParams, Resources value, int resource) {
	super(httpParams);
	this.resources = value;
	this.resource = resource;
}

@Override
protected ClientConnectionManager createClientConnectionManager() {
    SchemeRegistry registry = new SchemeRegistry();
    registry.register(new Scheme("http", PlainSocketFactory .getSocketFactory(), 80));
    if (resources != null) {
        registry.register(new Scheme("https", newSslSocketFactory(), 443));
    } else {
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
    }
    return new SingleClientConnManager(getParams(), registry);
}

private SSLSocketFactory newSslSocketFactory() {
    try {
        KeyStore trusted = KeyStore.getInstance("BKS");
        //KeyStore trusted = KeyStore.getInstance("JKS");
        InputStream in = resources.openRawResource(resource);
        try {
            trusted.load(in, "pass".toCharArray());
        } finally {
            in.close();
        }
        return new SSLSocketFactory(trusted);
    } catch (Exception e) {
        throw new AssertionError(e);
    }
}
}