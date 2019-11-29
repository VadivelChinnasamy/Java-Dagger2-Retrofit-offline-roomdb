package com.javadagger2.roomdb;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.javadagger2.MainActivity;
import com.javadagger2.Utils;
import com.javadagger2.dataclass.Result;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private AppDataBase mAppDbInstance;
    Context context;



    private List<Result> movieList;


    public MovieRepository(Context context) {
        this.context = context;
        mAppDbInstance = Utils.getDbInstance(context);

    }

    public List<Result> getPopularMov() {
        if (Utils.isNetworkConnected(context)) {
            return movieList;
        }else{
            return mAppDbInstance.moviesListDao().getAllDetail();
        }
    }



    public void mInsertPopularMovie(final List<Result> movieList) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mAppDbInstance.moviesListDao().mInsPopMovList(movieList);


            }
        }).start();
    }

}
