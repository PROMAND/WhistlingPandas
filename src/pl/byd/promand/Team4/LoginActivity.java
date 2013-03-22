package pl.byd.promand.Team4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import pl.byd.promand.Team4.domain.Task;
import pl.byd.promand.Team4.twitter.AbstractTaskManagerTweet;
import pl.byd.promand.Team4.twitter.AddMemberTweet;
import pl.byd.promand.Team4.twitter.CreateTaskTweet;
import pl.byd.promand.Team4.twitter.NewProjectTweet;
import pl.byd.promand.Team4.twitter.UpdateTaskTweet;
import pl.byd.promand.Team4.utils.Constants;
import pl.byd.promand.Team4.utils.MainModel;
import com.actionbarsherlock.app.SherlockActivity;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends SherlockActivity {

	private static final String TAG = "T4JSample";

	private Button buttonLogin;
	private Button getTweetButton;
	private TextView tweetText;
	private ScrollView scrollView;

	private static Twitter twitter;
	private static RequestToken requestToken;
	private static SharedPreferences mSharedPreferences;
	ProgressDialog pDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		buttonLogin = (Button)findViewById(R.id.twitterLogin);
		buttonLogin.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = getIntent();
				finish();
				startActivity(intent);
			}
			
		});
		MainModel.mSharedPreferences = getSharedPreferences(
				Constants.PREFERENCE_NAME, MODE_PRIVATE);

		mSharedPreferences = MainModel.mSharedPreferences;
		MainModel.loginActivityInstance = this;
		MainModel.getInstance().disconnectTwitter();
		/**
		 * Handle OAuth Callback
		 */
		Uri uri = getIntent().getData();
		if (uri != null && uri.toString().startsWith(Constants.CALLBACK_URL)) {
			String verifier = uri
					.getQueryParameter(Constants.IEXTRA_OAUTH_VERIFIER);
			try {
				AccessToken accessToken = twitter.getOAuthAccessToken(
						requestToken, verifier);
				Editor e = mSharedPreferences.edit();
				e.putString(Constants.PREF_KEY_TOKEN, accessToken.getToken());
				e.putString(Constants.PREF_KEY_SECRET,
						accessToken.getTokenSecret());
				e.commit();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
		
		if(!isConnected()){
			askOAuth();
		}
	}

	protected void onResume() {
		super.onResume();
		if(isConnected()){
			fetchTweets();
		}
	}

	/**
	 * check if the account is authorized
	 * 
	 * @return
	 */
	private boolean isConnected() {
		return mSharedPreferences.getString(Constants.PREF_KEY_TOKEN, null) != null;
	}

	private void askOAuth() {
		ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setOAuthConsumerKey(Constants.CONSUMER_KEY);
		configurationBuilder.setOAuthConsumerSecret(Constants.CONSUMER_SECRET);
		Configuration configuration = configurationBuilder.build();
		MainModel.twitter = new TwitterFactory(configuration).getInstance();
		twitter = MainModel.twitter;

		try {
			requestToken = twitter.getOAuthRequestToken(Constants.CALLBACK_URL);
			Toast.makeText(this, "Please authorize this app!",
					Toast.LENGTH_LONG).show();
			this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse(requestToken.getAuthenticationURL())));
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Remove Token, Secret from preferences
	 */

	private void fetchTweets() {
		showProgressDialog();
		boolean result = MainModel.getInstance().parseTwitterPosts();
        SharedPreferences sp = getApplicationContext().getSharedPreferences("setting", 0);
        Intent iAssigned;
        if (!result || sp.getString("creator", "") == "") {
            iAssigned = new Intent(LoginActivity.this, PropertiesActivity.class);
        }   else {
		    iAssigned = new Intent(LoginActivity.this, MainViewActivity.class);
        }
		this.finish();
		
		startActivity(iAssigned);
	}

	private void showProgressDialog() {
		pDialog = new ProgressDialog(this);
		pDialog.setMessage("Fetching tweets. Please Wait.");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(false);
		pDialog.show();
	}

	private void hideProgressDialog() {
		pDialog.dismiss();
	}
}
