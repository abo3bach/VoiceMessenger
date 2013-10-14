package com.Keb3mr.voicemessenger;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class VoiceMessenger extends Activity implements OnClickListener {

	Button send_; 
	Button recieve_;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Voice Messenger");
        send_ = (Button) findViewById(R.id.sendButton);
    	recieve_ = (Button) findViewById(R.id.recieveButton);
    	
    	send_.setOnClickListener(this);
    	recieve_.setOnClickListener(this);
    	
    }

	public void onClick(View v) {
		if(v.getId() == R.id.sendButton){
			Intent i = new Intent(this, RecordActivity.class);
			startActivity(i);
		}
		else if(v.getId() == R.id.recieveButton){
			Intent i = new Intent(this, RecieveActivity.class);
			startActivity(i);
		}
	}

}
