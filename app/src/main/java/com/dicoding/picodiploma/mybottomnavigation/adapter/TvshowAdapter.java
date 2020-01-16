package com.dicoding.picodiploma.mybottomnavigation.adapter;

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
import com.dicoding.picodiploma.mybottomnavigation.model.TvShow;

import java.util.ArrayList;

public class TvshowAdapter extends RecyclerView.Adapter<TvshowAdapter.ListViewHolder> {

    private ArrayList<TvShow> tvData = new ArrayList<>();

    public void setTvData(ArrayList<TvShow> items){
        tvData.clear();
        tvData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_movie,viewGroup,false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, final int position) {
        holder.bind(tvData.get(position));

    }

    @Override
    public int getItemCount() {
        return tvData.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama, tvDeskripsi;
        ImageView imgpoto;
        CardView cardView;

        ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.nama_id);
            tvDeskripsi = itemView.findViewById(R.id.deskrips_id);
            imgpoto = itemView.findViewById(R.id.gambar_id);
            cardView = itemView.findViewById(R.id.cardview_id);

        }

        void bind (TvShow tvShow){
            tvNama.setText(tvShow.getTitleTv());
            tvDeskripsi.setText(tvShow.getOverviewTv());
            Glide.with(itemView)
                    .load(tvShow.getPosterpathTv())
                    .into(imgpoto);

        }
    }

}
