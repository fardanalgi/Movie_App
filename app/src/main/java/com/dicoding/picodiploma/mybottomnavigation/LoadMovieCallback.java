package com.dicoding.picodiploma.mybottomnavigation;

import com.dicoding.picodiploma.mybottomnavigation.model.Movie;

import java.util.ArrayList;

public interface LoadMovieCallback {
    void preExecute();
    void postExecute(ArrayList<Movie> movies);
}
