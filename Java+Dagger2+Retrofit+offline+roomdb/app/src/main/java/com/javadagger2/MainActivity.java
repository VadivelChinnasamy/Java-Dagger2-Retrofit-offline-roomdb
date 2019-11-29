package com.javadagger2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.javadagger2.dataclass.PopularMovies;
import com.javadagger2.dataclass.Result;
import com.javadagger2.retrofit.ApiServices;
import com.javadagger2.roomdb.MovieRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    @Inject
    Retrofit mRetrofit;

    @Inject
    ApiServices mApiServices;

    @Inject
    MovieRepository movieRepository;

    // Retrofit mRet;
    String TAG = "MainActivity";
    private List<Result> mResultList;
    private List<Result> mLoadmoreItem=new ArrayList<>();

    private boolean isLoading=false;
    private int pageCount=1;
    @Inject
    PopularMoviesAdapter moviesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ((MyApplication) getApplication()).getComponent().inject(this);
        mResultList=new ArrayList<>();
        getPopularMovieList(pageCount);
        mRecyclerView.setAdapter(moviesAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mResultList.size() - 1) {
                        loadMore();
                        isLoading = true;
                    }
                }


            }
        });
    }

    private void getPopularMovieList(final int pageCount) {
        if (Utils.isNetworkConnected(MainActivity.this)) {
            ApiServices mService = mRetrofit.create(ApiServices.class);
            mService.getPopularMovie(pageCount).enqueue(new Callback<PopularMovies>() {
                @Override
                public void onResponse(Call<PopularMovies> call, Response<PopularMovies> response) {
                    if (response.body() != null) {
                        mLoadmoreItem=response.body().getResults();;
                        movieRepository.mInsertPopularMovie(mLoadmoreItem);
                        notifyAdapter();
                    }

                }

                @Override
                public void onFailure(Call<PopularMovies> call, Throwable t) {
                    // Log.e("TAG", "onFailure");
                }
            });
        } else {
            mResultList=new ArrayList<>();
            mResultList=movieRepository.getPopularMov();
            notifyAdapter();
            Toast.makeText(MainActivity.this, "Oops! please check you internet connection!", Toast.LENGTH_LONG).show();

        }


    }

    private void  loadMore() {

        pageCount++;

        Log.e("====","Loadmore --> "+pageCount);

        getPopularMovieList(pageCount);

        notifyAdapter();
    }

    private void notifyAdapter(){

        mResultList.addAll(mLoadmoreItem);
        moviesAdapter.setItem(mResultList);

        mRecyclerView.post(new Runnable() {
            @Override
            public void run() {
                moviesAdapter.notifyDataSetChanged();
                isLoading=false;

            }
        });


    }


}
