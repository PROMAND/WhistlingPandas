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
import pl.byd.promand.Team4.utils.TestDataPopulator;

import com.actionbarsherlock.app.SherlockActivity;

import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import android.app.Activity;
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
	private static TwitterStream twitterStream;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mSharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME,
				MODE_PRIVATE);
		scrollView = (ScrollView) findViewById(R.id.scrollView);
		tweetText = (TextView) findViewById(R.id.tweetText);
		getTweetButton = (Button) findViewById(R.id.getTweet);
		
		this.disconnectTwitter();
		
		getTweetButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// sendTweet("Tweeting Fun");
				/*
				List<String> marshaledTweets = TestDataPopulator.getMarshaledProject();
				for (String cur : marshaledTweets) {
					sendTweet(cur);
				}
				*/
				
				List<NewProjectTweet> unmarshalledProjectTweets = new ArrayList<NewProjectTweet>();
				List<AddMemberTweet> unmarshalledAddMemberTweets = new ArrayList<AddMemberTweet>();
				List<CreateTaskTweet> unmarshalledCreateTaskTweets = new ArrayList<CreateTaskTweet>();
				List<UpdateTaskTweet> unamrshalledUpdateTaskTweets = new ArrayList<UpdateTaskTweet>();
				
				ResponseList<Status> retrievedTweets = getTweets();
				Log.i("THREADS", "size=" + retrievedTweets.size());
				Iterator<Status> it = retrievedTweets.iterator();
				
				while(it.hasNext()) {
					Status curTweet = it.next();
					String text = curTweet.getText();
					AbstractTaskManagerTweet cur = AbstractTaskManagerTweet.parseTweet(text);
						switch (cur.getType()) {
						case AM:
							unmarshalledAddMemberTweets.add((AddMemberTweet)cur);
							break;
						case CT:
							unmarshalledCreateTaskTweets.add((CreateTaskTweet)cur);
							break;
						case NP:
							unmarshalledProjectTweets.add((NewProjectTweet)cur);
							break;
						case UT:
							unamrshalledUpdateTaskTweets.add((UpdateTaskTweet)cur);
							break;
						default:
							throw new IllegalArgumentException("Unknown tweet type: " + cur.getType());
						}
				}
				MainModel.getInstance().setState(unamrshalledUpdateTaskTweets, unmarshalledAddMemberTweets, unmarshalledCreateTaskTweets, unmarshalledProjectTweets);

				Log.i("THREADS", "STARTING INTENT");
	            Intent iAssigned =new Intent(LoginActivity.this, MainViewActivity.class);
	            startActivity(iAssigned);
			}

		});

		buttonLogin = (Button) findViewById(R.id.twitterLogin);

		buttonLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				askOAuth();
			}

		});
		// buttonLogin.setOnClickListener(this);

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
	}

	protected void onResume() {
		super.onResume();

		if (isConnected()) {
			String oauthAccessToken = mSharedPreferences.getString(
					Constants.PREF_KEY_TOKEN, "");
			String oAuthAccessTokenSecret = mSharedPreferences.getString(
					Constants.PREF_KEY_SECRET, "");

			ConfigurationBuilder confbuilder = new ConfigurationBuilder();
			Configuration conf = confbuilder
					.setOAuthConsumerKey(Constants.CONSUMER_KEY)
					.setOAuthConsumerSecret(Constants.CONSUMER_SECRET)
					.setOAuthAccessToken(oauthAccessToken)
					.setOAuthAccessTokenSecret(oAuthAccessTokenSecret).build();
			twitterStream = new TwitterStreamFactory(conf).getInstance();

			buttonLogin.setText(R.string.label_disconnect);
			getTweetButton.setEnabled(true);
		} else {
			buttonLogin.setText(R.string.label_connect);
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
		twitter = new TwitterFactory(configuration).getInstance();

		try {
			requestToken = twitter.getOAuthRequestToken(Constants.CALLBACK_URL);
			Toast.makeText(this, "Please authorize this app!",
					Toast.LENGTH_LONG).show();
			this.startActivity(new Intent(Intent.ACTION_VIEW, Uri
					.parse(requestToken.getAuthenticationURL())));
			// TODO retrieve tweets and build project
			
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Remove Token, Secret from preferences
	 */
	public void disconnectTwitter() {
		SharedPreferences.Editor editor = mSharedPreferences.edit();
		editor.remove(Constants.PREF_KEY_TOKEN);
		editor.remove(Constants.PREF_KEY_SECRET);

		editor.commit();
	}

	// Tweet methods

	public ResponseList<Status> getTweets() {
		try {
			Paging paging = new Paging(1, 1000);
			ResponseList<Status> ht = twitter.getUserTimeline(paging); // getHomeTimeline();
			String name = Thread.currentThread().getName();
			Log.i("thread", name);
			// ht.wait();
			return ht;
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static long sendTweet(String tweet) {

		try {
			Status response = twitter.updateStatus(tweet);
			return response.getId();

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public Status getTweetById(long tweetID) {
		try {
			Status tweet = twitter.showStatus(tweetID);
			if (tweet == null) { //
				return null;
			} else {
				return tweet;
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return null;
	}

}
