package com.nwu.yelpapi;

import com.nwu.yelpapi.type.Business;
import com.nwu.yelpapi.type.ReviewResponse;
import com.nwu.yelpapi.type.SearchResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface YelpV3API {
   String SEARCH_PATH = "/v3/businesses/search";
   String BUSINESS_PATH = "/v3/businesses/{id}";
   String REVIEWS_PATH = "/v3/businesses/{id}/reviews";

   @GET(SEARCH_PATH)
   Call<SearchResponse> search(@QueryMap Map<String, String> parameters);

   @GET(BUSINESS_PATH)
   Call<Business> business(@Path("id") String id, @Query("locale") String locale);

   @GET(REVIEWS_PATH)
   Call<ReviewResponse> reviews(@Path("id") String id, @Query("locale") String locale);
}
