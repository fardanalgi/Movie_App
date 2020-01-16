package com.dicoding.picodiploma.mybottomnavigation.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONObject;

public class Movie implements Parcelable {

    private int id;
    private String title;
    private String posterPath;
    private String backdroppath;
    private String overview;
    private String releseDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdroppath() {
        return backdroppath;
    }

    public void setBackdroppath(String backdroppath) {
        this.backdroppath = backdroppath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleseDate() {
        return releseDate;
    }

    public void setReleseDate(String releseDate) {
        this.releseDate = releseDate;
    }

    public Movie(){

    }



    public Movie(JSONObject object){
        try {
            this.id = object.getInt("id");
            this.title = object.getString("title");
            String poster = object.getString("poster_path");
            this.posterPath = "https://image.tmdb.org/t/p/w780/"+poster;
            String backdrop = object.getString("backdrop_path");
            this.backdroppath = "https://image.tmdb.org/t/p/w780/" + backdrop;
            this.overview = object.getString("overview");
            this.releseDate = object.getString("release_date");

        }catch (Exception e){
            e.printStackTrace();
            Log.d("Data Error", e.getMessage());
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdroppath);
        dest.writeString(this.overview);
        dest.writeString(this.releseDate);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.posterPath = in.readString();
        this.backdroppath = in.readString();
        this.overview = in.readString();
        this.releseDate = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
