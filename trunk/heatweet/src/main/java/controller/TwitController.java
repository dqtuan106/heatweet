package controller;

import java.util.ArrayList;
import java.util.List;

import store.AccessTokenStore;
import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import factory.TwitterFactory;

public class TwitController {
	public static RequestToken requestToken;

	public void authenticate() {
		try {
			Twitter twitter = TwitterFactory.getTwitter();
			twitter.setOAuthConsumer("4xyZR4XZHHG013alEUIkQ",
					"EN3CMxhZvojYMkhFbikWde0RC99STiHmWbBp80l8dE");

			requestToken = twitter.getOAuthRequestToken();
			System.out.println(requestToken.getAuthorizationURL());

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getAccessToken(String pin) {
		try {
			Twitter twitter = TwitterFactory.getTwitter();
			AccessToken accessToken = null;

			accessToken = twitter.getOAuthAccessToken(requestToken, pin);

			AccessTokenStore.gravarToken(accessToken);
			System.out.println(accessToken.toString());
			System.out.println(accessToken.getToken());
			System.out.println(accessToken.getTokenSecret());
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void authorize(String pin) {
		Twitter twitter = TwitterFactory.getTwitter();
		if (AccessTokenStore.getAccessToken() == null) {
			getAccessToken(pin);
		}

		AccessToken accessToken = AccessTokenStore.getAccessToken();
		twitter.setOAuthAccessToken(accessToken);
	}

	public void setAccessToken() {
		Twitter twitter = TwitterFactory.getTwitter();
		twitter.setOAuthConsumer("4xyZR4XZHHG013alEUIkQ",
				"EN3CMxhZvojYMkhFbikWde0RC99STiHmWbBp80l8dE");
		AccessTokenStore.carregaToken();
		AccessToken accessToken = AccessTokenStore.getAccessToken();
		twitter.setOAuthAccessToken(accessToken);
	}

	public void search() {
		try {
			Twitter twitter = TwitterFactory.getTwitter();
			Query query = new Query("flamengo");
			QueryResult result;

			result = twitter.search(query);

			for (Tweet tweet : result.getTweets()) {
				System.out.println(tweet.getFromUser() + ":" + tweet.getText());
			}
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Place> findTwitPlaces(String search, double latitude,
			double longitude) {
		ResponseList<Place> places = null;
		try {
			Twitter twitter = TwitterFactory.getTwitter();
			GeoLocation location = new GeoLocation(latitude, longitude);
			GeoQuery q = new GeoQuery(location);

			places = twitter.searchPlaces(q);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return places;
	}

	public List<Trend> searchByLocation(int woeid) {
		try {
			List<Trend> trendsList = new ArrayList<Trend>();
			Twitter twitter = TwitterFactory.getTwitter();

			Trends trends = twitter.getLocationTrends(woeid);
			Trend[] ts = trends.getTrends();
			for (Trend trend : ts) {
				trendsList.add(trend);
			}
			return trendsList;

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
