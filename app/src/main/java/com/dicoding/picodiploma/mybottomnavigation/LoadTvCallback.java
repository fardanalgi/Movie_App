package com.dicoding.picodiploma.mybottomnavigation;

import com.dicoding.picodiploma.mybottomnavigation.model.TvShow;

import java.util.ArrayList;

public interface LoadTvCallback {

    void preExecute();
    void postExecute(ArrayList<TvShow> tvList);
}
