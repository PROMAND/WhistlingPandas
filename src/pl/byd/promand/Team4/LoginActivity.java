package pl.byd.promand.Team4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import twitter4j.*;

import com.actionbarsherlock.app.SherlockActivity;

public class LoginActivity extends SherlockActivity {

	Button btnLogin;
	EditText txtLogin;
	EditText txtPassword;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		this.txtLogin = (EditText)findViewById(R.id.txtLoginField);
		this.txtPassword = (EditText)findViewById(R.id.txtPasswordField);
		this.btnLogin = (Button)findViewById(R.id.login);
		
		this.btnLogin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Login();
			}});
	}
	
	private void Login(){
		
		//Twitter allowed apart @ #.
		String login = txtLogin.getText().toString();
		String password = txtPassword.getText().toString();
		
//		Twitter twitter = new TwitterFactory().getInstance();
//		twitter.setOAuthConsumer("tYMXHS5dLfLG2TVCXJGBQg", "plgycHfsH6e6sou5LZHGkDMg1MlgM6gFq6YM6U38Y");
//		twitter.setOAuthAccessToken("");
//		
		
		startActivity(new Intent(LoginActivity.this, MainViewActivity.class));
	}
	
	
}