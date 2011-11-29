package store;

import java.util.HashMap;

import model.Location;

public class PlaceStore {
	
	private static HashMap<String, Location> places;
	
	public static HashMap<String, Location> getPlaces() {
		if(places == null) {
			places = new HashMap<String, Location>();
		}
		return places;
	}
	
	public static void setPlaces(HashMap<String, Location> places) {
		PlaceStore.places = places;
	}
}
