package controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import model.Value;

import com.google.gdata.client.ClientLoginAccountType;
import com.google.gdata.client.GoogleService;
import com.google.gdata.client.Service.GDataRequest;
import com.google.gdata.client.Service.GDataRequest.RequestType;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ContentType;
import com.google.gdata.util.ServiceException;

@Path("fusion")
public class FusionController {
	private static final Pattern CSV_VALUE_PATTERN = Pattern
			.compile("([^,\\r\\n\"]*|\"(([^\"]*\"\")*[^\"]*)\")(,|\\r?\\n)");
	private GoogleService service;
	private static final String FUSION_SERVICE_URL = "https://www.google.com/fusiontables/api/query";
	URL url;

	private GoogleService authenticate() throws AuthenticationException {
		if (service == null) {
			service = new GoogleService("fusiontables", "HeaTweet");
			service.setUserCredentials("heatweet@gmail.com", "ht2011infovis",
					ClientLoginAccountType.GOOGLE);
		}

		return service;
	}

	@GET
	@Path(value = "create")
	public String createTable(@QueryParam("param") String param) {
		try {
			this.url = new URL(FUSION_SERVICE_URL);
			authenticate();
			GDataRequest request = service.getRequestFactory().getRequest(
					RequestType.INSERT, this.url,
					new ContentType("application/x-www-form-urlencoded"));
			OutputStreamWriter writer = new OutputStreamWriter(
					request.getRequestStream());
			writer.append("sql=" + URLEncoder.encode(param, "UTF-8"));
			writer.flush();

			request.execute();

			String decoded = new String();
			Scanner scanner = new Scanner(request.getResponseStream(), "UTF-8");
			while (scanner.hasNextLine()) {
				scanner.findWithinHorizon(CSV_VALUE_PATTERN, 0);
				MatchResult match = scanner.match();
				String quotedString = match.group(2);
				decoded = quotedString == null ? match.group(1) : quotedString
						.replaceAll("\"\"", "\"");
				System.out.print("|" + decoded);
				if (!match.group(4).equals(",")) {
					System.out.println("|");
				}
			}
			return decoded;

		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOK";

	}

	@GET
	@Path(value = "insert")
	public String insert(@QueryParam("tb") String table,
			@QueryParam("c") String columns, @QueryParam("v") String values) {
		try {
			this.url = new URL(FUSION_SERVICE_URL);

			authenticate();
			GDataRequest request = service.getRequestFactory().getRequest(
					RequestType.INSERT, this.url,
					new ContentType("application/x-www-form-urlencoded"));
			OutputStreamWriter writer = new OutputStreamWriter(
					request.getRequestStream());
			writer.append("sql="
					+ URLEncoder.encode("INSERT INTO " + table + " (" + columns
							+ ") VALUES (" + values + ")", "UTF-8"));
			writer.flush();

			request.execute();

			String decoded = new String();
			Scanner scanner = new Scanner(request.getResponseStream(), "UTF-8");
			while (scanner.hasNextLine()) {
				scanner.findWithinHorizon(CSV_VALUE_PATTERN, 0);
				MatchResult match = scanner.match();
				String quotedString = match.group(2);
				decoded = quotedString == null ? match.group(1) : quotedString
						.replaceAll("\"\"", "\"");
				System.out.print("|" + decoded);
				if (!match.group(4).equals(",")) {
					System.out.println("|");
				}
			}
			return decoded;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOK";

	}

	public String batchInsert(List<Value> values) {

		try {
			this.url = new URL(FUSION_SERVICE_URL);

			authenticate();
			GDataRequest request = service.getRequestFactory().getRequest(
					RequestType.INSERT, this.url,
					new ContentType("application/x-www-form-urlencoded"));
			OutputStreamWriter writer;

			writer = new OutputStreamWriter(request.getRequestStream());
			writer.append("sql=");
			Iterator<Value> iter = values.iterator();
			while (iter.hasNext()) {
				Value value = iter.next();
				String q =URLEncoder.encode(
						"INSERT INTO 2228772 (tweet,Location) VALUES (' "
								+ value.getTweet() + " ','" + value.getLocation()
								+ "');", "UTF-8");
				System.out.println(q);
				writer.append(q);
			}
			
			writer.flush();

			request.execute();

			String decoded = new String();
			Scanner scanner = new Scanner(request.getResponseStream(), "UTF-8");
			while (scanner.hasNextLine()) {
				scanner.findWithinHorizon(CSV_VALUE_PATTERN, 0);
				MatchResult match = scanner.match();
				String quotedString = match.group(2);
				decoded = quotedString == null ? match.group(1) : quotedString
						.replaceAll("\"\"", "\"");
				System.out.print("|" + decoded);
				if (!match.group(4).equals(",")) {
					System.out.println("|");
				}
			}
			return decoded;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOK";
	}

	@GET
	@Path(value = "deleteAll")
	public String deleteAll(@QueryParam("table") String table) {
		try {
			this.url = new URL(FUSION_SERVICE_URL);

			authenticate();
			GDataRequest request = service.getRequestFactory().getRequest(
					RequestType.INSERT, this.url,
					new ContentType("application/x-www-form-urlencoded"));
			OutputStreamWriter writer;

			writer = new OutputStreamWriter(request.getRequestStream());

			writer.append("sql="
					+ URLEncoder.encode("DELETE FROM " + table, "UTF-8"));
			writer.flush();

			request.execute();

			String decoded = new String();
			Scanner scanner = new Scanner(request.getResponseStream(), "UTF-8");
			while (scanner.hasNextLine()) {
				scanner.findWithinHorizon(CSV_VALUE_PATTERN, 0);
				MatchResult match = scanner.match();
				String quotedString = match.group(2);
				decoded = quotedString == null ? match.group(1) : quotedString
						.replaceAll("\"\"", "\"");
				System.out.print("|" + decoded);
				if (!match.group(4).equals(",")) {
					System.out.println("|");
				}
			}
			return decoded;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AuthenticationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "NOK";

	}
}
