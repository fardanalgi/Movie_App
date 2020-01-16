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
import com.dicoding.picodiploma.mybottomnavigation.database.MovieHelper;
import com.dicoding.picodiploma.mybottomnavigation.model.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";

    private ImageView btn_Fav, btn_Del;
    private ProgressBar progressBar;
    private MovieHelper movieHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        movieHelper = MovieHelper.getInstance(getApplicationContext());
        movieHelper.open();
        btn_Fav = findViewById(R.id.btnFav);
        btn_Del = findViewById(R.id.btnDel);


        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        String movies = Integer.toString(movie.getId());

        progressBar = findViewById(R.id.progressbar_id);


        if (movieHelper.movieCheck(movies)) {
            btn_Fav.setVisibility(View.GONE);
            btn_Del.setVisibility(View.VISIBLE);
        }


        btn_Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie select = getIntent().getParcelableExtra(EXTRA_MOVIE);
                String msgFav = getString(R.string.toastfav);
                String msgfail = getString(R.string.toastFavFail);
                long result = movieHelper.insertMovie(select);
                if (result > 0) {
                    btn_Fav.setVisibility(View.GONE);
                    btn_Del.setVisibility(View.VISIBLE);
                    Toast.makeText(DetailActivity.this, msgFav, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DetailActivity.this, msgfail, Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_Del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Movie select = getIntent().getParcelableExtra(EXTRA_MOVIE);
                String msgDel = getString(R.string.toastDel);
                movieHelper.deleteMovie(select.getId());
                Toast.makeText(DetailActivity.this, msgDel, Toast.LENGTH_SHORT).show();
                btn_Fav.setVisibility(View.VISIBLE);
                btn_Del.setVisibility(View.GONE);

            }
        });

        getMovie();

    }

    private void getMovie() {
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        TextView namaDetail = findViewById(R.id.detailNama);
        TextView deskripsiDetail = findViewById(R.id.detailDeskripsi);
        ImageView gambarDetail = findViewById(R.id.detailGambar);
        ImageView gambarBackground = findViewById(R.id.detailBackground);

        String nama = movie.getTitle();
        String deskripsi = movie.getOverview();
        String gambar = movie.getPosterPath();
        String background = movie.getBackdroppath();

        namaDetail.setText(nama);
        deskripsiDetail.setText(deskripsi);


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

                .into(gambarBackground);


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


    }

}
