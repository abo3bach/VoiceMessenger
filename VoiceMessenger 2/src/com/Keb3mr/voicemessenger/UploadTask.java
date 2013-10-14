package com.Keb3mr.voicemessenger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;

public class UploadTask extends AsyncTask<Void, Void, HttpResponse> {
	
	private HttpEntity entity_;
	
	private String url_;
	
	private HttpCallback callback_;
	

	public UploadTask(HttpEntity entity, String url, HttpCallback callback){
		super();
		entity_ = entity;
		url_ = url;
		callback_ = callback;
	}
	
	
	
	protected void onPostExecute(HttpResponse resp){
		super.onPostExecute(resp);
		callback_.onResponse(resp);
		
	}

	@Override
	protected HttpResponse doInBackground(Void... params) {
		// TODO Auto-generated method stub
		HttpResponse resp = null;
		try{
			HttpPost post = new HttpPost(url_);
			post.setEntity(entity_);
			HttpClient client = new DefaultHttpClient();
			resp = client.execute(post);
		}
		catch(Exception e){
			e.printStackTrace();
		
		}
		return resp;
	}



}
