package aws.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import aws.test.utils.SingletonGson;

public class HotelSearchHandler {

	private static String bearerToken;
	private static Gson gson;

	static {
		gson = SingletonGson.getInstance();
	}

	public static String getSearchResult(Object searchRequest) {
		Map<String, String> headerParams = new HashMap<>();
		headerParams.put("Content-Type", "application/json");
		headerParams.put("Authorization", "Bearer " + bearerToken);
		HttpUtils httpUtils = HttpUtils.builder().postData(gson.toJson(searchRequest)).headerParams(headerParams)
				.requestMethod("POST").urlString("https://www.tripjack.com/hms/v1/hotel-searchquery-list").build();
		try {
			return httpUtils.getResponse();
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	public static void setBearerToken(String token) {
		bearerToken = token;
	}

	public static String getBearerToken() {
		return bearerToken;
	}

}
