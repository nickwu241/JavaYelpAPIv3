package com.nwu.yelpapi;

import com.nwu.yelpapi.datatype.AccessToken;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import java.io.IOException;

public class YelpV3Factory {
   //---------------------------------------------------------------------------
   private static final String API_HOST = "https://api.yelp.com";
   private static final String TOKEN_PATH = "/oauth2/token";
   private static final String GRANT_TYPE = "client_credentials";

   private final YelpAuthorization yelpAuth;
   private final String client_id;
   private final String client_secret;

   //---------------------------------------------------------------------------
   public YelpV3Factory(String client_id, String client_secret) {
      yelpAuth = new Retrofit.Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(YelpAuthorization.class);
      this.client_id = client_id;
      this.client_secret = client_secret;
   }

   //---------------------------------------------------------------------------
   public AccessToken getAccessToken() throws IOException {
      Call<AccessToken> call = yelpAuth.getToken(GRANT_TYPE, client_id, client_secret);
      return call.execute().body();
   }

   //---------------------------------------------------------------------------
   public YelpAPI getAPI(final AccessToken accessToken) {
      OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
         public Response intercept(Chain chain) throws IOException {
            Request request =
                  chain.request().newBuilder().header("Authorization", "Bearer " + accessToken.access_token).build();

            return chain.proceed(request);
         }
      }).build();

      return new Retrofit.Builder()
            .baseUrl(API_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build().create(YelpAPI.class);
   }

   //---------------------------------------------------------------------------
   private interface YelpAuthorization {
      @FormUrlEncoded
      @POST(TOKEN_PATH)
      Call<AccessToken> getToken(@Field("grant_type") String grant_type,
                                 @Field("client_id") String client_id,
                                 @Field("client_secret") String client_secret);
   }

   //---------------------------------------------------------------------------
}
