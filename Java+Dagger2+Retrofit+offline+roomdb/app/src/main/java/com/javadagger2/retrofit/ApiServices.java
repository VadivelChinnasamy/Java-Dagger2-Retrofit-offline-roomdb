package com.javadagger2.retrofit;

import com.javadagger2.Constants;
import com.javadagger2.dataclass.PopularMovies;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("movie/popular?api_key=824e6813a740068e24a630f5083b0811&")
    Call<PopularMovies> getPopularMovie(@Query("page") int page);

}
