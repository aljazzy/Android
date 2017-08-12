package com.jatmiko.juli.popularmovie2;

import com.jatmiko.juli.popularmovie2.api.RetrofitApi;
import com.jatmiko.juli.popularmovie2.event.MovieErrorEvent;
import com.jatmiko.juli.popularmovie2.event.MovieEvent;
import com.jatmiko.juli.popularmovie2.model.MovieResponse;
import com.jatmiko.juli.popularmovie2.utility.Constant;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Miko on 10/08/2017.
 */

public class MovieController {
    private EventBus eventBus = MainApp.getInstance().getEventBus();

    private void getMovies(int type, int page){
        Call<MovieResponse> movieResponseCall = MainApp.getInstance().getApiService().getMovies(Constant.Data.MOVIE_LIST_TYPE[type], RetrofitApi.API_KEY, RetrofitApi.LANG_SOURCE, page, RetrofitApi.MOVIES_REGION);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.code() == 200){
                    eventBus.post(new MovieEvent(response.message(), response.body()));
                } else {
                    eventBus.post(new MovieErrorEvent(response.message()));
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                eventBus.post(new MovieErrorEvent(t.getMessage()));

            }
        });
    }



    public void getPopularMovies(int page) {
        getMovies(0, page);
    }

    public void getTopRatedMovies(int page) {
        getMovies(1, page);
    }

}
