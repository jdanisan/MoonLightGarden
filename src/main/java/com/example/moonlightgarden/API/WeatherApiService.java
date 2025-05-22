package com.example.moonlightgarden.API;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiService {
    @GET("v1/astronomy.json")
    Call<AstronomyResponse> getAstronomy(
            @Query("key") String apiKey,
            @Query("q") String location,
            @Query("dt") String date
    );
}
