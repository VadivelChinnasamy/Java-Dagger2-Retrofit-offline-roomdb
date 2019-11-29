package com.javadagger2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.javadagger2.dataclass.Result;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PopularMoviesAdapter  extends RecyclerView.Adapter<PopularMoviesAdapter.MovieViewHolder> {
    private List<Result> mList=new ArrayList<>();
    private Context mContext;

    public PopularMoviesAdapter(  Context mContext) {

        this.mContext= mContext;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_popular_movies,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, final int position) {
        holder.rating_txt.setText(""+ mList.get(position).getVoteAverage()+"/10");
        if (Utils.isNetworkConnected(mContext)) {
            Picasso.get().load("http://image.tmdb.org/t/p/w500/" + mList.get(position).getPosterPath())
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(holder.movie_pic);
        }else{
            Picasso.get().load("http://image.tmdb.org/t/p/w500/" + mList.get(position).getPosterPath())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(holder.movie_pic);

        }

        holder.movie_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mContext.startActivity(new Intent(mContext,DetailActivity.class)
                .putExtra("position",position));
            }
        });


    }

    @Override
    public int getItemCount() {
        if(mList!=null)
            return mList.size();
        else
            return 0;

    }

    public void setItem(List<Result> mList){
        Log.e("===","=------setITem()==>"+mList.size());
        this.mList=mList;
       // notifyDataSetChanged();

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView rating_txt;
        ImageView movie_pic;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            rating_txt= itemView.findViewById(R.id.rating_txt);
            movie_pic= itemView.findViewById(R.id.movie_pic);


        }
    }
}
