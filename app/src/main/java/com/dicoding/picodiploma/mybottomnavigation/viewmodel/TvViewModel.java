package com.dicoding.picodiploma.mybottomnavigation.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.dicoding.picodiploma.mybottomnavigation.model.TvShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvViewModel extends ViewModel {

    private static final String API_KEY = "7c683eb3dd5ed987ea483bb9df557ad5";
    private MutableLiveData<ArrayList<TvShow>> listTV = new MutableLiveData<>();

    public void setTvData (){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShow> listTvAPI = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/popular?api_key=" + API_KEY + "&language=en-US&page=1";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                String results = new String(responseBody);
                try {
                    JSONObject responObject = new JSONObject(results);
                    JSONArray list = responObject.getJSONArray("results");

                    for (int i = 0; i< list.length(); i++){
                        JSONObject tv = list.getJSONObject(i);
                        TvShow tvShow = new TvShow(tv);
                        listTvAPI.add(tvShow);
                    }
                    listTV.postValue(listTvAPI);
                }catch (Exception e){
                    Log.d("Exception", e.getMessage());
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("Failur", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TvShow>> getTv(){
        return listTV;
    }

}
