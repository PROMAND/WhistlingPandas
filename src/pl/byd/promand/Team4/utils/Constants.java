package pl.byd.promand.Team4.utils;

/**
 * 
 * Constants used in the application
 * 
 * @author veskikri
 *
 */
public interface Constants {
	
	String 
	/**
	 * Extra item moved between screens
	 */
	INTENT_EXTRA_TASK = "extra_task", 
	/**
	 * Custom tweet format separator
	 */
	SEPARATOR = ";";
	
	
	//Twitter api
	static String CONSUMER_KEY = "DAkfdZiYhOiNgSdJ5vMWA";
	static String CONSUMER_SECRET = "HhQN7hPcQddP4ahyjHjRDK9s6byWHKtxlCTwVKXA";

	static String PREFERENCE_NAME = "twitter_oauth";
	static final String PREF_KEY_SECRET = "oauth_token_secret";
	static final String PREF_KEY_TOKEN = "oauth_token";
	static final String CALLBACK_URL = "oauth://t4jsample";
	static final String IEXTRA_AUTH_URL = "auth_url";
	static final String IEXTRA_OAUTH_VERIFIER = "oauth_verifier";
	static final String IEXTRA_OAUTH_TOKEN = "oauth_token";
	
}
