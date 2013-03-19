package pl.byd.promand.Team4;

import com.actionbarsherlock.app.SherlockActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
 
public class InviteActivity extends SherlockActivity {
 
	Button buttonSend;
	EditText textTo;
	EditText textSubject;
	EditText textMessage;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.invitation);

        Button button1=(Button)findViewById(R.id.cancelInvite);
        button1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                InviteActivity.this.finish();
            }
        });
 
		buttonSend = (Button) findViewById(R.id.buttonSend);
		textTo = (EditText) findViewById(R.id.editTextTo);
		textMessage = (EditText) findViewById(R.id.editTextMessage);

		//code neede for future
		/*
		buttonSend.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View v) { //
 
			  String to = textTo.getText().toString();
			  String subject = textSubject.getText().toString();
			  String message = textMessage.getText().toString();
			}
		});
		*/
	}
}