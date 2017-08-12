package com.jatmiko.juli.popularmovie2.event;

import com.jatmiko.juli.popularmovie2.model.MovieResponse;

/**
 * Created by Miko on 10/08/2017.
 */

public class MovieEvent extends Event {
    private MovieResponse body;

    public MovieEvent(String message, MovieResponse body) {
        super(message);
        this.body = body;
    }
    public MovieResponse getBody(){
        return body;
    }
}
