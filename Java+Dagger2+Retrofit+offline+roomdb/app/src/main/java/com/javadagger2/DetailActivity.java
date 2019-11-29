package com.javadagger2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.javadagger2.dataclass.Result;
import com.javadagger2.roomdb.MovieRepository;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity {
    @Inject
    MovieRepository movieRepository;
    private List<Result> mList;

    private ImageView imgBanner;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgBanner = findViewById(R.id.img_detail);
        TextView detail_overview_txt = findViewById(R.id.detail_overview_txt);
        TextView detail_title_txt = findViewById(R.id.detail_title_txt);
        TextView detail_release_date_txt = findViewById(R.id.detail_release_date_txt);
        TextView detail_rating_txt = findViewById(R.id.detail_rating_txt);
        ((MyApplication) getApplication()).getComponent().inject(this);

        mList=movieRepository.getPopularMov();
        int position=getIntent().getIntExtra("position",1);
        detail_overview_txt.setText(""+mList.get(position).getOverview());
        detail_title_txt.setText(""+mList.get(position).getOriginalTitle());
        detail_release_date_txt.setText("Released on : "+mList.get(position).getReleaseDate());
        detail_rating_txt.setText("Rating : "+mList.get(position).getVoteAverage()+"/10");
        if (Utils.isNetworkConnected(this)) {
            setImage(mList.get(position).getPosterPath(), NetworkPolicy.NO_CACHE);
        }else{
            setImage(mList.get(position).getPosterPath(), NetworkPolicy.OFFLINE);

        }
    }
    private void  setImage(String posterPath, NetworkPolicy networkPolicy){
        Picasso.get().load("http://image.tmdb.org/t/p/w500/" +posterPath)
                .networkPolicy(networkPolicy)
                .into(imgBanner);

    }
}
