package com.dicoding.picodiploma.mybottomnavigation.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.mybottomnavigation.R;
import com.dicoding.picodiploma.mybottomnavigation.model.TvShow;

import java.util.ArrayList;

public class TvFavAdapter extends RecyclerView.Adapter<TvFavAdapter.ListViewHolder> {

    private final ArrayList<TvShow> listTv = new ArrayList<>();
    private final Activity activity;

    public ArrayList<TvShow> getListTv() {
        return listTv;
    }

    public TvFavAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListTv(ArrayList<TvShow> listTv) {

        if (listTv.size() > 0 ) {
            this.listTv.clear();
        }
        this.listTv.addAll(listTv);

        notifyDataSetChanged();
    }

    public void addItem(TvShow tvShow) {
        this.listTv.add(tvShow);
        notifyItemInserted(listTv.size() - 1);
    }

    public void removeItem(int position) {
        this.listTv.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, listTv.size());
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder listViewHolder, int i) {
        listViewHolder.bind(listTv.get(i));
    }

    @Override
    public int getItemCount() {
        return listTv.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama, tvDeskripsi;
        ImageView imgpoto;

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.nama_id);
            tvDeskripsi = itemView.findViewById(R.id.deskrips_id);
            imgpoto = itemView.findViewById(R.id.gambar_id);

        }

        void bind(TvShow tvShow) {
            tvNama.setText(tvShow.getTitleTv());
            tvDeskripsi.setText(tvShow.getOverviewTv());
            Glide.with(itemView)
                    .load(tvShow.getPosterpathTv())
                    .into(imgpoto);
        }
    }
}
