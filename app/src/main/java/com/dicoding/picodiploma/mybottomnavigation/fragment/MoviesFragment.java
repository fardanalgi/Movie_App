package com.dicoding.picodiploma.mybottomnavigation.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.picodiploma.mybottomnavigation.ItemClickSupport;
import com.dicoding.picodiploma.mybottomnavigation.R;
import com.dicoding.picodiploma.mybottomnavigation.activity.DetailActivity;
import com.dicoding.picodiploma.mybottomnavigation.adapter.MovieAdapter;
import com.dicoding.picodiploma.mybottomnavigation.model.Movie;
import com.dicoding.picodiploma.mybottomnavigation.viewmodel.MovieViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {


    private RecyclerView rvMovie;
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;




    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_movies,container,false);

    }
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        rvMovie= view.findViewById(R.id.rv_Movies);
        progressBar = view.findViewById(R.id.progressbarMovie_id);
        progressBar.setVisibility(View.VISIBLE);

        showRecyclerList(view);

        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModel.setMovie();
        movieViewModel.getMovie().observe(this, getMovie);

    }

    private final Observer<ArrayList<Movie>> getMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<Movie> list) {
            if (list != null){
                movieAdapter.setData(list);
                progressBar.setVisibility(View.GONE);
                ItemClickSupport.addTo(rvMovie).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        showSelectedPopularData(list.get(position));
                    }
                });

            }
        }
    };

    private void showSelectedPopularData(Movie itemPopularData) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_MOVIE, itemPopularData);
        startActivity(intent);
    }

    private void showRecyclerList(View view){
        movieAdapter = new MovieAdapter();
        movieAdapter.notifyDataSetChanged();
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,view.isInLayout()));
        rvMovie.setAdapter(movieAdapter);

    }

}
