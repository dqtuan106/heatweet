package store;

import java.io.IOException;
import java.util.Properties;

import com.google.gdata.client.ClientLoginAccountType;
import com.google.gdata.client.GoogleService;
import com.google.gdata.util.AuthenticationException;

import twitter4j.auth.AccessToken;

public class AccessTokenStore {
	private static AccessToken accessToken;
	private static Properties tokenProperties;
	private static GoogleService service;
	
	public static GoogleService authenticate() throws AuthenticationException {
		if (service == null) {
			service = new GoogleService("fusiontables", "HeaTweet");
			service.setUserCredentials("heatweet@gmail.com", "ht2011infovis",
					ClientLoginAccountType.GOOGLE);
		
		}
		
		return service;

	}
	private static void carregaProperties() {
		try {
			if (tokenProperties == null) {
				tokenProperties = new Properties();

				tokenProperties.load(AccessToken.class
						.getResourceAsStream("/resource/token.properties"));

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void carregaToken(){
		carregaProperties();
		accessToken = new AccessToken(tokenProperties.getProperty("token"), tokenProperties.getProperty("tokenSecret"));		
	}
	public static AccessToken getAccessToken() {
		
		return accessToken;
	}

	private static void setAccessToken(AccessToken accessToken) {
		AccessTokenStore.accessToken = accessToken;
	}

	public static void gravarToken(AccessToken accessToken) {
		carregaProperties();
		tokenProperties.setProperty("token", accessToken.getToken());
		tokenProperties
				.setProperty("tokenSecret", accessToken.getTokenSecret());
		tokenProperties.setProperty("userId",
				String.valueOf(accessToken.getUserId()));
		setAccessToken(accessToken);
	}

}
