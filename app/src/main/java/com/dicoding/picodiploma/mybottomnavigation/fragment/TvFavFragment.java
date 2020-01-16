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
import com.dicoding.picodiploma.mybottomnavigation.LoadTvCallback;
import com.dicoding.picodiploma.mybottomnavigation.R;
import com.dicoding.picodiploma.mybottomnavigation.activity.DetailTv_Activity;
import com.dicoding.picodiploma.mybottomnavigation.adapter.TvFavAdapter;
import com.dicoding.picodiploma.mybottomnavigation.database.TvHelper;
import com.dicoding.picodiploma.mybottomnavigation.model.TvShow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TvFavFragment extends Fragment implements LoadTvCallback {

    private RecyclerView rvTv;
    private TvFavAdapter tvFavAdapter;
    private final static String LIST_STATE_KEY2 = "STATE2";
    private ArrayList<TvShow> tvArrayList = new ArrayList<>();


    public TvFavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvTv = view.findViewById(R.id.recyclerView_FavTv);
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvTv.setHasFixedSize(true);

        TvHelper tvHelper = TvHelper.getInstance(getActivity());
        tvHelper.open();

        tvFavAdapter = new TvFavAdapter(getActivity());
        rvTv.setAdapter(tvFavAdapter);

        if (savedInstanceState == null) {
            new LoadTvAsync(tvHelper, this).execute();
        } else {
            final ArrayList<TvShow> tvState = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY2);
            assert tvState != null;
            tvArrayList.addAll(tvState);
            tvFavAdapter.setListTv(tvState);
            ItemClickSupport.addTo(rvTv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    Intent intent = new Intent(getActivity(), DetailTv_Activity.class);
                    intent.putExtra(DetailTv_Activity.EXTRA_TV, tvState.get(position));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE_KEY2, tvArrayList);
    }


    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(final ArrayList<TvShow> tvList) {
        tvFavAdapter.setListTv(tvList);
        rvTv.setAdapter(tvFavAdapter);
        tvArrayList.addAll(tvList);
        ItemClickSupport.addTo(rvTv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getActivity(), DetailTv_Activity.class);
                intent.putExtra(DetailTv_Activity.EXTRA_TV, tvList.get(position));
                startActivity(intent);
            }
        });

    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<TvShow>>{

        private final WeakReference<TvHelper> tvHelperWeakReference;
        private final WeakReference<LoadTvCallback> loadTvCallbackWeakReference;

        private LoadTvAsync(TvHelper tvHelper, LoadTvCallback callback) {
            tvHelperWeakReference = new WeakReference<>(tvHelper);
            loadTvCallbackWeakReference = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadTvCallbackWeakReference.get().preExecute();
        }

        @Override
        protected ArrayList<TvShow> doInBackground(Void... voids) {
            return tvHelperWeakReference.get().getAllTv();
        }

        @Override
        protected void onPostExecute(ArrayList<TvShow> tvShows) {
            super.onPostExecute(tvShows);
            loadTvCallbackWeakReference.get().postExecute(tvShows);
        }
    }
}
