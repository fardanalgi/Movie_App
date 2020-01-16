package com.dicoding.picodiploma.mybottomnavigation.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dicoding.picodiploma.mybottomnavigation.ItemClickSupport;
import com.dicoding.picodiploma.mybottomnavigation.LoadMovieCallback;
import com.dicoding.picodiploma.mybottomnavigation.R;
import com.dicoding.picodiploma.mybottomnavigation.activity.DetailActivity;
import com.dicoding.picodiploma.mybottomnavigation.adapter.MovieFavAdapter;
import com.dicoding.picodiploma.mybottomnavigation.database.MovieHelper;
import com.dicoding.picodiploma.mybottomnavigation.model.Movie;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFavFragment extends Fragment implements LoadMovieCallback {

    private RecyclerView rvMovie;
    private MovieFavAdapter favAdapter;
    private final static String LIST_STATE_KEY = "STATE";
    private ArrayList<Movie> movieArrayList = new ArrayList<>();

    public MovieFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvMovie = view.findViewById(R.id.recyclerView_FavMovie);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvMovie.setHasFixedSize(true);

        MovieHelper movieHelper = MovieHelper.getInstance(getActivity());
        movieHelper.open();

        favAdapter = new MovieFavAdapter(getActivity());
        rvMovie.setAdapter(favAdapter);

        if (savedInstanceState == null){
            new LoadMovieAsync(movieHelper, this).execute();
        }else {
            final ArrayList<Movie> movieState = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            assert movieState != null;
            movieArrayList.addAll(movieState);
            favAdapter.setListMovie(movieState);
            ItemClickSupport.addTo(rvMovie).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_MOVIE, movieState.get(position));
                    startActivity(intent);
                }
            });
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE_KEY,movieArrayList);
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(final ArrayList<Movie> movies) {
        favAdapter.setListMovie(movies);
        rvMovie.setAdapter(favAdapter);
        movieArrayList.addAll(movies);
        ItemClickSupport.addTo(rvMovie).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getActivity(),DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_MOVIE, movies.get(position));
                startActivity(intent);
            }
        });
    }

    private static class LoadMovieAsync extends AsyncTask<Void, Void, ArrayList<Movie>>{

        private final WeakReference<MovieHelper> movieHelperWeakReference;
        private final WeakReference<LoadMovieCallback> callbackWeakReference;

        private LoadMovieAsync(MovieHelper movieHelper, LoadMovieCallback callback) {
            movieHelperWeakReference = new WeakReference<>(movieHelper);
            callbackWeakReference = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callbackWeakReference.get().preExecute();
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return movieHelperWeakReference.get().getALLMovi();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> list) {
            super.onPostExecute(list);
            callbackWeakReference.get().postExecute(list);
        }
    }

    
}
