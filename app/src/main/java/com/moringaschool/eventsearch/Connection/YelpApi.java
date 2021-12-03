package com.moringaschool.eventsearch.Connection;

import com.moringaschool.eventsearch.Models.YelpEventSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YelpApi {
    @GET("businesses/search")
    Call<YelpEventSearchResponse> getRestaurants(
            @Query("location") String location
    );
}
