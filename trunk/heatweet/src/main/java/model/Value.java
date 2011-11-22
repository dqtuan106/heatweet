package model;

public class Value {

	private String tweet;
	private String location;
	
	public Value() {
		// TODO Auto-generated constructor stub
	}
	public Value(String tweeet,String location) {
		this.tweet = tweeet;
		this.location = location;
	}
	public String getTweet() {
		return tweet;
	}
	public void setTweet(String tweet) {
		this.tweet = tweet;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
}
