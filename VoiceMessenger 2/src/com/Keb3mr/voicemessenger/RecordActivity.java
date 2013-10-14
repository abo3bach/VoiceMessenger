package com.Keb3mr.voicemessenger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import Message.Message;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class RecordActivity extends Activity implements HttpCallback, OnClickListener {

	private TextView results_;
	private Button record_;
	private EditText userName_;
	private EditText clientName_;
	boolean isRecording = false;
	
	private Message m1_;
	
	private String url_ = "http://192.168.1.120:8080/vshare/message/upload";
	
	MediaRecorder mRecorder = new MediaRecorder();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        setTitle("Voice Messenger");
        results_ = (TextView) findViewById(R.id.textView1);
        record_ = (Button) findViewById(R.id.record_Button);
        userName_ = (EditText) findViewById(R.id.myUserName);
        clientName_ = (EditText) findViewById(R.id.clientUserName);
        
        
        record_.setOnClickListener(this);
        
    }
	public void onClick(View v) {
		
		if(v.getId() == R.id.record_Button){
			if(isRecording){
				isRecording = false;
				record_.setText("record");
				mRecorder.reset();
				File file1 = new File("/sdcard/message.3gp");
				doShare(url_, file1);
			}
			else{
				try{
					mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
					mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
					mRecorder.setOutputFile("/sdcard/message.3gp");
					mRecorder.prepare();
					mRecorder.start();
					record_.setText("stop");
					isRecording = true;			
				}
				catch(Exception e){
					Toast.makeText(this, "Error starting recorder.",Toast.LENGTH_SHORT).show();
				}
				
			}
		}
		
	}
	
	
	
	
	public void onDestroy() {
		if (isRecording) {
			Toast.makeText(this, "Recorder stopped.",Toast.LENGTH_SHORT).show();
			mRecorder.stop();
		}
			mRecorder.release();
			super.onDestroy();
	}
	
	
	
	
	public void doShare(String server, File file1) {
		try {

			sendData(server, file1);


		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendData(String url, File file1) 
			throws IOException {
		
		
		
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("theFile", new FileBody(file1, "audio/3gpp"));
	
				
	
		String userName = userName_.getText().toString();
		String recieverName = clientName_.getText().toString();
		entity.addPart("sender", new StringBody(userName));
		entity.addPart("reciever", new StringBody(recieverName));
				
				
		UploadTask t = new UploadTask(entity, url, this);
		t.execute();
		
	}
	@Override
	public void onResponse(HttpResponse resp) {
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
				sb.append(line + "\n");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String result = "Message sent";
		
		result += " message id, sender:" + sb.toString();
		
		
	
		results_.setText(result);
		
	}
	@Override
	public void onError(Exception e) {
		results_.setText("Error:"+e.getMessage());
		
	}
	

}
