package com.Keb3mr.voicemessenger;

import org.apache.http.HttpResponse;

public interface HttpCallback2 {

	public void onResponse(String result);
	
	public void onError(Exception e);
}
