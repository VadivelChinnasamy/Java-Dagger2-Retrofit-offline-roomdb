package com.javadagger2.dagger2;

import android.content.Context;

import com.javadagger2.PopularMoviesAdapter;
import com.javadagger2.dataclass.PopularMovies;
import com.javadagger2.retrofit.ApiServices;
import com.javadagger2.roomdb.MovieRepository;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    Context context;
    public NetworkModule(Context context){
      this.context=context;
    }

    @Provides
    Retrofit getRetrofit() {
        return new Retrofit.Builder().baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
    @Provides
    ApiServices getRetrofitService() {
        return new Retrofit.Builder().baseUrl("http://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(ApiServices.class);
    }

    @Provides
    MovieRepository getMovieRepo(){
        return new MovieRepository(context);
    }

    @Provides
    PopularMoviesAdapter getAdapter(){

        return new PopularMoviesAdapter(context);
    }
}
