package com.jatmiko.juli.popularmovie2.api;

import com.jatmiko.juli.popularmovie2.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Miko on 10/08/2017.
 */

public interface RetrofitApi {
    String BASE_URL = "https://api.themoviedb.org/3/";
    String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/w185";
    String API_KEY = "0256b2ce662960b6e6e503d4fb6ffa29";
    String LANG_SOURCE = "en-US";
    String MOVIES_REGION = "US";

    @GET("movie/{type}")
    Call<MovieResponse> getMovies(@Path("type") String type, @Query("api_key") String apiKey, @Query("language") String language, @Query("page") int page, @Query("region") String region);


}
