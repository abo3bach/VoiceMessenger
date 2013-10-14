package com.Keb3mr.voicemessenger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import Message.Message;
import android.os.AsyncTask;
import android.os.Environment;

public class DownloadTask extends AsyncTask<Void, Void, String> {
	
	private String urlId_;
	String urlMsg_;
	private HttpEntity entity_;
	private HttpCallback2 callback_;
	
	
	public DownloadTask(String urlId, String urlMsg, HttpEntity entity, HttpCallback2 callback){
		
		urlId_ = urlId;
		urlMsg_ = urlMsg;
		entity_ = entity;
		callback_ = callback;
	}

	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		HttpResponse resp = null;
		
		String result = null;
		try{
			
			HttpPost post = new HttpPost(urlId_);
			post.setEntity(entity_);
			HttpClient client = new DefaultHttpClient();
			resp = client.execute(post);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		String line = null;
		InputStream is = null;
		try {
			is = resp.getEntity().getContent();
		} catch (IllegalStateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		StringBuilder sb = new StringBuilder();
		
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		result = sb.toString();
		String[] msgIdL = result.split("[}]");
		
		for(String str: msgIdL){
			 
			String[] parts = str.split("]");
			Message m1 = new Message();
			m1.setSender(parts[1]);
			str +=  "}";
			MyProperties.getInstance().pubMsgids.add(str);
			MyProperties.getInstance().pubMesslist.add(m1);
		}
		
		int i = 1;
		for(String str: msgIdL){
			MultipartEntity e1 = new MultipartEntity();
			
			try {
				e1.addPart("messageId", new StringBody(str));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			HttpPost post2 = new HttpPost(urlMsg_);
			post2.setEntity(e1);
			HttpClient client = new DefaultHttpClient();
			
			try {
				resp = client.execute(post2);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				InputStream inputStream = resp.getEntity().getContent();
				File mess = new File(Environment.getExternalStorageDirectory(),"/Music/message"+ i +".3gp") ;
				//File mess = new File("sdcard/Music/message"+ i +".3gp");
			 
				// write the inputStream to a FileOutputStream
				OutputStream out = new FileOutputStream(mess);
		 
				int read = 0;
				byte[] bytes = new byte[1024];
		 
				while ((read = inputStream.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}
		 
				inputStream.close();
				out.flush();
				out.close();
				MyProperties.getInstance().pubMesslist.get(i-1).setMessage(mess);
			}catch (IOException e) {
				e.printStackTrace();
		    }
			i++;
		}
		
		return result;
	}
	
	protected void onPostExecute(String result)
	{
		
		callback_.onResponse(result);
	}
	
	

}
