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
import com.dicoding.picodiploma.mybottomnavigation.activity.DetailTv_Activity;
import com.dicoding.picodiploma.mybottomnavigation.adapter.TvshowAdapter;
import com.dicoding.picodiploma.mybottomnavigation.model.TvShow;
import com.dicoding.picodiploma.mybottomnavigation.viewmodel.TvViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvShowFragment extends Fragment {

    private RecyclerView rvTvshow;
    private TvshowAdapter tvshowAdapter;
    private ProgressBar progressBar;


    public TvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_tv_show, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        rvTvshow = view.findViewById(R.id.rv_Tv);
        progressBar = view.findViewById(R.id.progressbarTv_id);
        progressBar.setVisibility(View.VISIBLE);

        showRecyclerListTv(view);

        TvViewModel tvViewModel = ViewModelProviders.of(this).get(TvViewModel.class);
        tvViewModel.setTvData();
        tvViewModel.getTv().observe(this,getTv);

    }

    private final Observer<ArrayList<TvShow>> getTv = new Observer<ArrayList<TvShow>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<TvShow> tvShows) {
            if (tvShows != null){
                tvshowAdapter.setTvData(tvShows);
                progressBar.setVisibility(View.GONE);
                ItemClickSupport.addTo(rvTvshow).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        showSelectedPopularData(tvShows.get(position));
                    }
                });
            }
        }
    };

    private void showSelectedPopularData(TvShow itemPopularData) {
        Intent intent = new Intent(getActivity(), DetailTv_Activity.class);
        intent.putExtra(DetailTv_Activity.EXTRA_TV, itemPopularData);
        startActivity(intent);
    }

    private void showRecyclerListTv(View view){
        tvshowAdapter = new TvshowAdapter();
        tvshowAdapter.notifyDataSetChanged();
        rvTvshow.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,view.isInLayout()));

        rvTvshow.setAdapter(tvshowAdapter);


    }


}
