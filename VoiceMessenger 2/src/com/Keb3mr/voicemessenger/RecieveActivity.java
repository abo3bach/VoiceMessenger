package com.Keb3mr.voicemessenger;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class RecieveActivity extends Activity implements OnClickListener {
	
	
	private Button recieve_;
	private EditText userName_;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve);
        setTitle("Voice Messenger");
        userName_ = (EditText) findViewById(R.id.myUserName);
        recieve_ = (Button) findViewById(R.id.getButton);
        
        recieve_.setOnClickListener(this);
        
    }


	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.getButton)	{
			Intent i = new Intent(this, PlayActivity.class);
			i.putExtra("userName", userName_.getText().toString());
			startActivity(i);
		}
	}
}
