package com.dicoding.picodiploma.mybottomnavigation.activity;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dicoding.picodiploma.mybottomnavigation.R;
import com.dicoding.picodiploma.mybottomnavigation.database.TvHelper;
import com.dicoding.picodiploma.mybottomnavigation.model.TvShow;


public class DetailTv_Activity extends AppCompatActivity {

    public static final String EXTRA_TV = "extra_tv";

    private ImageView btn_Fav, btn_Del;
    private TvHelper tvHelper;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tv_);

        TextView namaDetail = findViewById(R.id.detailNama);
        TextView deskripsiDetail = findViewById(R.id.detailDeskripsi);
        ImageView gambarDetail = findViewById(R.id.detailGambar);
        ImageView backdrop = findViewById(R.id.detailBackground);

        tvHelper = TvHelper.getInstance(getApplicationContext());
        tvHelper.open();

        btn_Fav = findViewById(R.id.btnFavTv);
        btn_Del = findViewById(R.id.btnDelTv);
        progressBar = findViewById(R.id.progressbarTv_id);

        TvShow tvShow = getIntent().getParcelableExtra(EXTRA_TV);
        String tv = Integer.toString(tvShow.getIdTv());
        String nama = tvShow.getTitleTv();
        String deskripsi = tvShow.getOverviewTv();
        String gambar = tvShow.getPosterpathTv();
        String background = tvShow.getBackdroppathTv();

        namaDetail.setText(nama);
        deskripsiDetail.setText(deskripsi);

        progressBar.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext())
                .load(gambar)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(gambarDetail);

        progressBar.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext())
                .load(background)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(backdrop);

        if (tvHelper.checkTv(tv)){
            btn_Fav.setVisibility(View.GONE);
            btn_Del.setVisibility(View.VISIBLE);
        }

        btn_Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TvShow select = getIntent().getParcelableExtra(EXTRA_TV);
                String msgFav = getString(R.string.toastfav);
                String msgFail = getString(R.string.toastFavFail);
                long result = tvHelper.insertTv(select);
                if (result > 0) {
                    btn_Fav.setVisibility(View.GONE);
                    btn_Del.setVisibility(View.VISIBLE);
                    Toast.makeText(DetailTv_Activity.this, msgFav, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(DetailTv_Activity.this, msgFail, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TvShow select = getIntent().getParcelableExtra(EXTRA_TV);
                String msgDel = getString(R.string.toastDel);
                tvHelper.deleteTv(select.getIdTv());
                Toast.makeText(DetailTv_Activity.this, msgDel, Toast.LENGTH_SHORT).show();
                btn_Fav.setVisibility(View.VISIBLE);
                btn_Del.setVisibility(View.GONE);
            }
        });

    }
}
