package demo;


import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

public class FlightsHelper {
	
	private static final String AIRLINE_FLIGHTS_URL = "https://data-cloud.flightradar24.com/zones/fcgi/feed.js?airline=";
	private static final String FLIGHT_DETALIS_URL = "https://data-live.flightradar24.com/clickhandler/?flight=";

	@SuppressWarnings("unchecked")
	public static String getFlightsByAirline(String airline) {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
			        .uri(new URI(AIRLINE_FLIGHTS_URL + airline))
			        .build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			String text = response.body();
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> map = objectMapper.readValue(text, Map.class);
			return map.keySet().stream()
				.filter(key -> !"full_count".equals(key)
						&& !"version".equals(key))
				.collect(Collectors.joining(","));
		} catch (URISyntaxException | IOException | InterruptedException e) {
			return "";
		}
	}
	
	@SuppressWarnings("unchecked")
	public static String getFlightDetails(String code)  {
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder()
			        .uri(new URI(FLIGHT_DETALIS_URL + code))
			        .build();
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			String text = response.body();
			ObjectMapper objectMapper = new ObjectMapper();
			Map<String, Object> map = objectMapper.readValue(text, Map.class);
			StringBuilder builder = new StringBuilder();
			builder.append("<flight>")
				.append("<number>")
				.append(getValue(map, "identification", "number", "default"))
				.append("</number>")
				.append("<airline>")
				.append(getValue(map, "airline", "name"))
				.append("</airline>")
				.append("<aircraft>")
				.append(getValue(map, "aircraft", "model", "text"))
				.append("</aircraft>")
				.append("<origin>")
				.append(getValue(map, "airport", "origin", "name"))
				.append("</origin>")
				.append("<destination>")
				.append(getValue(map, "airport", "destination", "name"))
				.append("</destination>")
				.append("<status>")
				.append(getValue(map, "status", "text"))
				.append("</status>")
				.append("</flight>");
			return builder.toString();
		} catch (URISyntaxException | IOException | InterruptedException e) {
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	private static String getValue(Map<String, Object> map, String...segments) {
		Map<String, Object> context = map;
		for (int i = 0; i < segments.length - 1; i++) {
			if (context == null) {
				return "";
			}
			context = (Map<String, Object>) context.get(segments[i]);
		}
		Object value = context != null
				? context.get(segments[segments.length - 1])
				: null;
		return value != null ? value.toString() : "";
	}
}

