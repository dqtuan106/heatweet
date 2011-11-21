package mb;

import java.util.List;

import javax.faces.bean.ManagedBean;

import twitter4j.Place;
import twitter4j.Trend;

import model.Location;
import controller.ServicesController;
import controller.TwitController;

@ManagedBean(name = "tweetMB")
public class TweetMB {
	private int woeid;
	private float latitude;
	private float longitude;
	private String busca;

	private TwitController twitController;
	private ServicesController servicesController;

	public TweetMB() {
		twitController = new TwitController();
		servicesController = new ServicesController();
	}

	public void findWoeid() {
		try {
			Location local = servicesController.findWoeid(busca);
			woeid = local.getWoeid();
			List<Place> places = twitController.findTwitPlaces(busca,
					local.getLatitude(), local.getLongitude());
			for (Place place : places) {

				local = servicesController.findWoeid(place.getName());

				List<Trend> trends = twitController.searchByLocation(local
						.getWoeid());
				System.out.println(place.getName());
				if (trends.size() > 0) {
					System.out.println("Trends");
					for (Trend trend : trends) {
						System.out.println(trend.getName());
					}
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
