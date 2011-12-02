package store;

import java.util.HashMap;

import model.Location;

public class LastTweetStore {

	private static HashMap<String, Long> tweetIds;
	
	public static HashMap<String, Long> getTweetIds() {
		if(tweetIds == null) {
			tweetIds = new HashMap<String, Long>();
		}
		return tweetIds;	
	}
	
	public static void setTweetIds(HashMap<String, Long> tweetIds) {
		LastTweetStore.tweetIds = tweetIds;
	}
}
