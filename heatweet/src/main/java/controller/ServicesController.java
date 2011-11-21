package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import model.Location;

public class ServicesController {

	private final String WOEID_TAG_INICIO = "<woeid>";
	private final String WOEID_TAG_FIM = "</woeid>";
	private final String LATITUDE_TAG_INICIO = "<latitude>";
	private final String LATITUDE_TAG_FIM = "</latitude>";
	private final String LONGITUDE_TAG_INICIO = "<longitude>";
	private final String LONGITUDE_TAG_FIM = "</longitude>";
	private final String CITY_TAG_INICIO = "<city>";
	private final String CITY_TAG_FIM= "</city>";
	private final String STATE_TAG_INICIO = "<state>";
	private final String STATE_TAG_FIM= "</state>";

	public Location findWoeid(String busca) throws Exception {
		Location local = new Location();

		try {
			URL url = new URL(
					"http://where.yahooapis.com/geocode?q="
							+ URLEncoder.encode(busca, "UTF-8")
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
			int inicio = c.indexOf(WOEID_TAG_INICIO);
			int fim = c.indexOf(WOEID_TAG_FIM);
			String value = c.substring(inicio + WOEID_TAG_INICIO.length(), fim);

		
			local.setWoeid(Integer.parseInt(value));

			

			inicio = c.indexOf(LATITUDE_TAG_INICIO);
			fim = c.indexOf(LATITUDE_TAG_FIM);
			value = c.substring(inicio + LATITUDE_TAG_INICIO.length(), fim);

			local.setLatitude(Double.parseDouble(value));

			inicio = c.indexOf(LONGITUDE_TAG_INICIO);
			fim = c.indexOf(LONGITUDE_TAG_FIM);
			value = c.substring(inicio + LONGITUDE_TAG_INICIO.length(), fim);

			local.setLongitude(Double.parseDouble(value));
			
			inicio = c.indexOf(CITY_TAG_INICIO);
			fim = c.indexOf(CITY_TAG_FIM);
			value = c.substring(inicio + CITY_TAG_INICIO.length(), fim);
			
			local.setCidade(value);
			
			inicio = c.indexOf(STATE_TAG_INICIO);
			fim = c.indexOf(STATE_TAG_FIM);
			value = c.substring(inicio + STATE_TAG_INICIO.length(), fim);
			
			local.setEstado(value);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return local;
	}
}
