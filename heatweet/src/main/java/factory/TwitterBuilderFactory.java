package factory;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterBuilderFactory {
	private static Twitter twitter;

	public static Twitter getTwitter() {
		if (twitter == null) {

			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
					.setOAuthConsumerKey("4xyZR4XZHHG013alEUIkQ")
					.setOAuthConsumerSecret(
							"EN3CMxhZvojYMkhFbikWde0RC99STiHmWbBp80l8dE")
					.setOAuthAccessToken(
							"49757568-EFqSTfW7XkC0shQ1GrB3vmGtD4Zj1rtUZ7wUroPk")
					.setOAuthAccessTokenSecret(
							"HZiKpdPxN561cZEmaabLPX7B2A6Uzc6F6FG9UYVJexE");
			TwitterFactory tf = new TwitterFactory(cb.build());
			twitter = tf.getInstance();

		}

		return twitter;

	}
}
