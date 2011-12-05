package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import model.Location;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@Path(value = "services")
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

	public Location geocodeYahoo(String endereco) {
		Location local = new Location();
		try {
			URL url = new URL(
					"http://where.yahooapis.com/geocode?q="
							+ URLEncoder.encode(endereco, "UTF-8")
							+ "&appid=dj0yJmk9Y2dYUDVqVDJhRlBnJmQ9WVdrOVZVMVFha2wwTlRBbWNHbzlOamswTlRRME5qWXkmcz1jb25zdW1lcnNlY3JldCZ4PTAy");

			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			InputStream is = url.openStream();
			Document doc = builder.parse(is);

			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile("//Error");
			
			String status = expr.evaluate(doc);
			expr = xpath.compile("//Found");
			String found = expr.evaluate(doc);
			if (status.equals("0") && !found.equals("0")) {

				expr = xpath.compile("//latitude");
				String lat = expr.evaluate(doc);
				local.setLatitude(Double.parseDouble(lat));

				expr = xpath.compile("//longitude");
				String lng = expr.evaluate(doc);
				local.setLongitude(Double.parseDouble(lng));

				return local;
			} else {
				System.out.println(status+" - "+ endereco);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

			System.out.println(endereco);
		}

		return null;

	}
	@Path("geocode")
	public String googleGeocode(@QueryParam("endereco")String endereco) {
		//Location local = geocodeEndereco(endereco);
		Location local = geocodeYahoo(endereco);
		if(local!=null) {
			return local.getLatitude() +";"+local.getLongitude();
		} 
		return "";
		
	}
	public Location geocodeEndereco( String endereco) {
		Location local = new Location();
		try {
			URL url = new URL(
					"http://maps.google.com/maps/api/geocode/xml?address="
							+ URLEncoder.encode(endereco, "UTF-8")
							+ "&sensor=false");

			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			InputStream is = url.openStream();
			Document doc = builder.parse(is);

			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile("//status");
			String status = expr.evaluate(doc);
			if (status.equals("OK")) {

				expr = xpath.compile("//location/lat");
				String lat = expr.evaluate(doc);
				local.setLatitude(Double.parseDouble(lat));

				expr = xpath.compile("//location/lng");
				String lng = expr.evaluate(doc);
				local.setLongitude(Double.parseDouble(lng));

				return local;
			} else {
				System.out.println(status +" - endereco: " + endereco);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

			System.out.println(endereco);
		}

		return null;
	}
	public Location getLatLng(String busca) {
		Location local = new Location();
		try {
			URL url = new URL(
					"http://where.yahooapis.com/geocode?q="
							+ URLEncoder.encode(busca, "UTF-8")
							+ "&appid=dj0yJmk9Y2dYUDVqVDJhRlBnJmQ9WVdrOVZVMVFha2wwTlRBbWNHbzlOamswTlRRME5qWXkmcz1jb25zdW1lcnNlY3JldCZ4PTAy");
			DocumentBuilderFactory domFactory = DocumentBuilderFactory
					.newInstance();
			domFactory.setNamespaceAware(true);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			InputStream is = url.openStream();
			Document doc = builder.parse(is);

			XPath xpath = XPathFactory.newInstance().newXPath();
			XPathExpression expr = xpath.compile("//Erro");
			String status = expr.evaluate(doc);
			if (status.equals("0")) {

				expr = xpath.compile("//latitude");
				String lat = expr.evaluate(doc);
				local.setLatitude(Double.parseDouble(lat));

				expr = xpath.compile("//longitude");
				String lng = expr.evaluate(doc);
				local.setLongitude(Double.parseDouble(lng));
				
				expr = xpath.compile("//state");
				String estado = expr.evaluate(doc);
				local.setEstado(estado);
				
				expr = xpath.compile("//city");
				String cidade = expr.evaluate(doc);
				local.setCidade(cidade);
				
				

				return local;
			} else {
				System.out.println(status);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}

	
	private String extraiConteudo(String c, String tagInicio, String tagFim) {
		int inicio = c.indexOf(tagInicio);
		int fim = c.indexOf(tagFim);
		try {
			return c.substring(inicio + tagInicio.length(), fim);
		} catch (Exception e) {
			return "0";
		}

	}
}
