package com.nwu.yelpapi.sample;

import com.google.gson.Gson;
import com.nwu.yelpapi.YelpAPI;
import com.nwu.yelpapi.YelpV3Factory;
import com.nwu.yelpapi.type.AccessToken;
import com.nwu.yelpapi.type.Business;
import com.nwu.yelpapi.type.SearchResponse;
import retrofit2.Call;

import java.io.IOException;
import java.util.HashMap;

public class JavaYelpAPISample {
   private static final String CLIENT_ID = null; // ENTER CLIENT_ID
   private static final String CLIENT_SECRET = null; // ENTER CLIENT_SECRET

   private static final String DEFAULT_LOCATION = "San Francisco, CA";
   private static final String SAMPLE_BUSINESS_ID = "anchor-oyster-bar-san-francisco";

   public static void main(String... args) throws IOException {
      assert(CLIENT_ID != null && CLIENT_SECRET != null);

      final YelpV3Factory factory = new YelpV3Factory(CLIENT_ID, CLIENT_SECRET);
      final AccessToken accessToken = factory.getAccessToken();

      final YelpAPI yelp = factory.getAPI(accessToken);

      final HashMap<String, String> params = new HashMap<String, String>();
      params.put("location", DEFAULT_LOCATION);
      final Call<SearchResponse> searchCall = yelp.search(params);
      final SearchResponse searchResponse = searchCall.execute().body();

      final Call<Business> businessCall = yelp.business(SAMPLE_BUSINESS_ID, null);
      final Business business = businessCall.execute().body();

      Gson gson = new Gson();

      System.out.println("--Search API--");
      System.out.println(gson.toJson(searchResponse));
      System.out.println("--Business API--");
      System.out.println(gson.toJson(business));
   }

}
