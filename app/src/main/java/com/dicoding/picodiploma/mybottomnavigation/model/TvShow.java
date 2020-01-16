package com.dicoding.picodiploma.mybottomnavigation.model;


import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

public class TvShow implements Parcelable {

    private int idTv;
    private String titleTv;
    private String overviewTv;
    private String posterpathTv;
    private String backdroppathTv;
    private String releseDateTv;

    public int getIdTv() {
        return idTv;
    }

    public void setIdTv(int idTv) {
        this.idTv = idTv;
    }

    public String getTitleTv() {
        return titleTv;
    }

    public void setTitleTv(String titleTv) {
        this.titleTv = titleTv;
    }

    public String getOverviewTv() {
        return overviewTv;
    }

    public void setOverviewTv(String overviewTv) {
        this.overviewTv = overviewTv;
    }

    public String getPosterpathTv() {
        return posterpathTv;
    }

    public void setPosterpathTv(String posterpathTv) {
        this.posterpathTv = posterpathTv;
    }

    public String getBackdroppathTv() {
        return backdroppathTv;
    }

    public void setBackdroppathTv(String backdroppathTv) {
        this.backdroppathTv = backdroppathTv;
    }

    public String getReleseDateTv() {
        return releseDateTv;
    }

    public void setReleseDateTv(String releseDateTv) {
        this.releseDateTv = releseDateTv;
    }



    public TvShow (JSONObject object){
        try {
            this.idTv = object.getInt("id");
            this.overviewTv = object.getString("overview");
            String poster = object.getString("poster_path");
            this.posterpathTv = "https://image.tmdb.org/t/p/w780/" + poster;
            String backdrop = object.getString("backdrop_path");
            this.backdroppathTv = "https://image.tmdb.org/t/p/w780/" + backdrop;
            this.releseDateTv = object.getString("first_air_date");
            this.titleTv= object.getString("name");
        }catch (Exception e){
            Log.d("Data Eror", e.getMessage());
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idTv);
        dest.writeString(this.titleTv);
        dest.writeString(this.overviewTv);
        dest.writeString(this.posterpathTv);
        dest.writeString(this.backdroppathTv);
        dest.writeString(this.releseDateTv);
    }

    public TvShow(){

    }

    protected TvShow(Parcel in) {
        this.idTv = in.readInt();
        this.titleTv = in.readString();
        this.overviewTv = in.readString();
        this.posterpathTv = in.readString();
        this.backdroppathTv = in.readString();
        this.releseDateTv = in.readString();
    }

    public static final Parcelable.Creator<TvShow> CREATOR = new Parcelable.Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };
}

