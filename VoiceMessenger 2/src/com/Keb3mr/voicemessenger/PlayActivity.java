package com.Keb3mr.voicemessenger;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import Message.Message;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class PlayActivity extends Activity implements OnClickListener, HttpCallback2{
	
	private String userName_;
	private String url1_ = "http://192.168.1.120:8080/vshare/message/id";
	private String url2_ = "http://192.168.1.120:8080/vshare/message/download";
	private Spinner s1_;
	private Button b_;
	private boolean run_ = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        setTitle("Voice Messenger");
        Intent i = getIntent();
        userName_ = i.getExtras().getString("userName");
        b_ = (Button) findViewById(R.id.testB);
        
        doRecieve(url1_, url2_);
        
        
    }
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

    public void doRecieve(String server1, String server2){
    	try{
    		
    		getData(server1, server2);
    		
    	}catch(Exception e){
    		
    		e.printStackTrace();
    	}
    }
    
    public void getData(String url1, String url2)
    		throws IOException{
    	b_.setText("sent");
    	File f1 = new File("/sdcard/Music/message.3gp");
    	MultipartEntity entity1 = new MultipartEntity();
        entity1.addPart("reciever", new StringBody(userName_));
    	DownloadTask t = new DownloadTask(url1, url2, entity1, this);
    	t.execute();
    	
    }

	@Override
	public void onResponse(String result) {
		// TODO Auto-generated method stub
		//results_.setText(result);
	}

	@Override
	public void onError(Exception e) {
		// TODO Auto-generated method stub
		
	}

	

	
}
