package mb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import twitter4j.Place;
import controller.TwitController;

@Path("tweet")
@ManagedBean(name="tweetMB")
public class TweetMB {
	private int woeid;
	private float latitude;
	private float longitude;
	private String busca;
	
	private TwitController control;
	
	public TweetMB() {
		control = new TwitController();
	}
	public void findWoeid() {

		try {
			URL url = new URL("http://where.yahooapis.com/geocode?q="
							+ URLEncoder.encode(busca,"UTF-8")
							+ "&appid=dj0yJmk9Y2dYUDVqVDJhRlBnJmQ9WVdrOVZVMVFha2wwTlRBbWNHbzlOamswTlRRME5qWXkmcz1jb25zdW1lcnNlY3JldCZ4PTAy");			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String line;
			StringWriter conteudo = new StringWriter();
			while ((line = br.readLine()) != null) {
				conteudo.append(line);
			}
			String c = null;
			c = conteudo.toString();
			System.out.println(c);
			int inicio = c.indexOf("<woeid>");
			int fim = c.indexOf("</woeid>");
			c= c.substring(inicio+"<woeid>".length(), fim);
			
			woeid = Integer.parseInt(c);			
			
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@GET
	@Path(value="location")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTweetsquery(@QueryParam("q") String query,@QueryParam("latitude")float latitude,@QueryParam("longitude") float longitude ) {
		StringBuffer sb = new StringBuffer();
		sb.append("{places:[");
		List<Place> places = control.findTwitPlaces(query, latitude, longitude);
		for (Place place : places) {
			sb.append(place.getName() + " : " +place.getPlaceType() +" ,");
		}
		sb.append("]}");
		return sb.toString();
		
	}
	public int getWoeid() {
		return woeid;
	}

	public void setWoeid(int woeid) {
		this.woeid = woeid;
	}

	public String getBusca() {
		return busca;
	}

	public void setBusca(String busca) {
		this.busca = busca;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
}
