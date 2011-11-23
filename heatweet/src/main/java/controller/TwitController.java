package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import model.Location;
import model.Value;
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
import twitter4j.auth.RequestToken;
import factory.TwitterBuilderFactory;

@Path("tweet")
public class TwitController {
	public static RequestToken requestToken;

	@GET
	@Path(value = "oauth")
	public String oauthSingleUser() {

		return null;
	}

	public void search() {
		try {
			Twitter twitter = TwitterBuilderFactory.getTwitter();
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
			Twitter twitter = TwitterBuilderFactory.getTwitter();
			GeoLocation location = new GeoLocation(latitude, longitude);
			GeoQuery q = new GeoQuery(location);
			https: // www.google.com/fusiontables/api/query

			places = twitter.searchPlaces(q);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return places;
	}

	@GET
	@Path(value = "trends")
	public List<Trend> searchByLocation(@QueryParam("woeid") int woeid) {
		List<Trend> trendsList = new ArrayList<Trend>();
		try {

			Twitter twitter = TwitterBuilderFactory.getTwitter();

			Trends trends = twitter.getLocationTrends(woeid);
			Trend[] ts = trends.getTrends();
			for (Trend trend : ts) {
				trendsList.add(trend);
			}

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return trendsList;
	}

	@GET
	@Path(value = "location")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTweetsquery(@QueryParam("q") String query,
			@QueryParam("latitude") double latitude,
			@QueryParam("longitude") double longitude) {
		StringBuffer sb = new StringBuffer();
		sb.append("{places:[");
		List<Place> places = findTwitPlaces(query, latitude, longitude);
		for (Place place : places) {
			sb.append(place.getName() + " : " + place.getPlaceType() + " ,");
		}
		sb.append("]}");
		return sb.toString();

	}

	@GET
	@Path(value = "maxperlocation")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMaxResultsPerLocation(@QueryParam("q") String query,
			@QueryParam("r") double raio,
			@QueryParam("latitude") double latitude,
			@QueryParam("longitude") double longitude) {
		StringBuffer sb = new StringBuffer();
		try {

			GeoLocation local = new GeoLocation(latitude, longitude);
			Query q = new Query(query);
			q.geoCode(local, raio, Query.KILOMETERS);
			Twitter twitter = TwitterBuilderFactory.getTwitter();
			QueryResult result;
			result = twitter.search(q);
			List<Tweet> tweets = result.getTweets();

			sb.append("{tweets: [");
			for (Tweet tweet : tweets) {

				sb.append("{tweet :" + tweet.getText() + ",");
				sb.append(" location :" + tweet.getLocation() + "}");

			}
			sb.append("]}");
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sb.toString();

	}

	@GET
	@Path("maxtweets")
	@Produces(MediaType.APPLICATION_JSON)
	public String populateTables(@QueryParam("q") String query,
			@QueryParam("r") double raio,
			@QueryParam("latitude") double latitude,
			@QueryParam("longitude") double longitude) {
		try {
			ServicesController servicesController = new ServicesController();
			GeoLocation local = new GeoLocation(latitude, longitude);
			Query q = new Query(query);
			q.geoCode(local, raio , Query.KILOMETERS);
			q.setRpp(100);
			Twitter twitter = TwitterBuilderFactory.getTwitter();
			TreeMap<String, Integer> twitTrends = new TreeMap<String, Integer>();
			TreeMap<String, Location> locations = new TreeMap<String, Location>();
			QueryResult result;
			for (int i = 1; i < 15; i++) {
				q.setPage(i);
				result = twitter.search(q);
				List<Tweet> tweets = result.getTweets();
				for (Tweet tweet : tweets) {
					try {
						Location location = servicesController.getLatLng(tweet
								.getLocation());

						String chave = location.getEstado()
								+ location.getCidade();
						if (!locations.containsKey(chave)) {
							locations.put(chave, location);
						}
						if (twitTrends.containsKey(chave)) {
							int valorAnterior = twitTrends.get(chave);
							twitTrends.put(chave, valorAnterior + 1);
						} else {
							twitTrends.put(chave, 1);
						}
					} catch (Exception e) {
						System.out.println(tweet.getLocation());
					}
				}
			}

			Set<String> chaves = twitTrends.keySet();
			/*
			 * for (String chave : chaves) { fusionController.insert( "2228251",
			 * "local,tweets,latitude,longitude", "'" +
			 * locations.get(chave).getCidade() + "'," + twitTrends.get(chave) +
			 * "," + locations.get(chave).getLatitude() + "," +
			 * locations.get(chave).getLongitude()); }
			 */
			StringBuffer retorno = new StringBuffer();
			retorno.append("{ \"points\" :[");
			for (String chave : chaves) {
				retorno.append("{ \"total\" : \"" + twitTrends.get(chave)
						+ "\",");
				retorno.append("\"lat\" : \""
						+ locations.get(chave).getLatitude() + "\",");
				retorno.append("\"lng\" : \""
						+ locations.get(chave).getLongitude() + "\"} ,");

			}		
			retorno.delete(retorno.length() -1, retorno.length());
			retorno.append("]}");

			
			return retorno.toString();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "nok";
	}

	@GET
	@Path("carregatweets")
	public String carregaTweets(@QueryParam("q") String query,
			@QueryParam("latitude") double latitude,
			@QueryParam("longitude") double longitude) {
		try {
			FusionController fusionController = new FusionController();
			ServicesController servicesController = new ServicesController();
			GeoLocation local = new GeoLocation(latitude, longitude);
			Query q = new Query(query);
			q.geoCode(local, 500.0, Query.KILOMETERS);
			q.setResultType(Query.RECENT);
			q.setRpp(100);
			fusionController.deleteAll("2228772");
			Twitter twitter = TwitterBuilderFactory.getTwitter();
			QueryResult result;
			List<Value> values = new ArrayList<Value>();
			for (int i = 1; i < 5; i++) {
				q.setPage(i);
				result = twitter.search(q);
				List<Tweet> tweets = result.getTweets();
				for (Tweet tweet : tweets) {

					/*
					 * fusionController.insert( "2228772", "tweet,Location",
					 * "'"+tweet.getText()+"'"+ "'"+tweet.getLocation()+"'");
					 */
					try {
						/*
						 * Location location =
						 * servicesController.findWoeid(tweet .getLocation());
						 */
						Location location = servicesController
								.geocodeEndereco(tweet.getLocation());
						Value value = new Value(tweet.getText().replaceAll("'",
								""), location.getLatitude() + " "
								+ location.getLongitude());
						values.add(value);
					} catch (Exception e) {
						System.out.println("PROBLEMA em "
								+ tweet.getText().replaceAll("'", "") + " - "
								+ tweet.getLocation());
						e.printStackTrace();
					}

				}
				fusionController.batchInsert(values);
			}

			return "ok";
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			return "nok " + e.getErrorMessage();

		} catch (Exception e) {
			return "nok " + e.getMessage();
		}

	}

}
