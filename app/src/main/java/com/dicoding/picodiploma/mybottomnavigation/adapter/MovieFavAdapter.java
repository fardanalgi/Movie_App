package com.dicoding.picodiploma.mybottomnavigation.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.mybottomnavigation.R;
import com.dicoding.picodiploma.mybottomnavigation.model.Movie;

import java.util.ArrayList;

public class MovieFavAdapter extends RecyclerView.Adapter<MovieFavAdapter.ListViewHolder> {

    private ArrayList<Movie> listMovie = new ArrayList<>();
    private Activity activity;

    public MovieFavAdapter(Activity activity){
        this.activity = activity;
    }

    public ArrayList<Movie> getListMovie(){
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie){

        if (listMovie.size() > 0 ) {
            this.listMovie.clear();
        }

        this.listMovie.addAll(listMovie);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i) {
        listViewHolder.bind(listMovie.get(i));
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama, tvDeskripsi;
        ImageView imgpoto;
        CardView cardView;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.nama_id);
            tvDeskripsi = itemView.findViewById(R.id.deskrips_id);
            imgpoto = itemView.findViewById(R.id.gambar_id);
            cardView = itemView.findViewById(R.id.cardview_id);
        }

        void bind(Movie movie) {
            tvNama.setText(movie.getTitle());
            tvDeskripsi.setText(movie.getOverview());
            Glide.with(itemView)
                    .load(movie.getPosterPath())
                    .into(imgpoto);
        }
    }
}
