package factory;

import twitter4j.Twitter;

public class TwitterFactory {
	private static Twitter twitter;
	
	public static Twitter getTwitter() {
		if(twitter == null) {
			twitter = new twitter4j.TwitterFactory().getInstance();
			
		} 
		
		return twitter;
		
	}
}
