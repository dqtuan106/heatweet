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
	private final String NEIGHBORHOOD_TAG_INICIO = "<neighborhood>";
	private final String NEIGHBORHOOD_TAG_FIM = "</neighborhood>";
	private final String COUNTY_TAG_INICIO = "<county>";
	private final String COUNTY_TAG_FIM = "</county>";
	private final String CITY_TAG_INICIO = "<city>";
	private final String CITY_TAG_FIM = "</city>";
	private final String STATE_TAG_INICIO = "<state>";
	private final String STATE_TAG_FIM = "</state>";

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

			local.setWoeid(Integer.parseInt(extraiConteudo(c, WOEID_TAG_INICIO,
					WOEID_TAG_FIM)));

			local.setLatitude(Double.parseDouble(extraiConteudo(c,
					LATITUDE_TAG_INICIO, LATITUDE_TAG_FIM)));

			local.setLongitude(Double.parseDouble(extraiConteudo(c,
					LONGITUDE_TAG_INICIO, LONGITUDE_TAG_FIM)));

			local.setCidade(extraiConteudo(c, CITY_TAG_INICIO, CITY_TAG_FIM));

			local.setVizinhança(extraiConteudo(c, NEIGHBORHOOD_TAG_INICIO,
					NEIGHBORHOOD_TAG_INICIO));

			local.setCounty(extraiConteudo(c, COUNTY_TAG_INICIO, COUNTY_TAG_FIM));

			local.setEstado(extraiConteudo(c, STATE_TAG_INICIO, STATE_TAG_FIM));

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return local;
	}

	private String extraiConteudo(String c, String tagInicio, String tagFim)
			 {
		int inicio = c.indexOf(tagInicio);
		int fim = c.indexOf(tagFim);
		try {
			return c.substring(inicio + tagInicio.length(), fim);
		} catch (Exception e) {
			return "0";
		}
		

	}
}
