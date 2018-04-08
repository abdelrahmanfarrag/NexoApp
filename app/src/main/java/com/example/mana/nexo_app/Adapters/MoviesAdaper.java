package com.example.mana.nexo_app.Adapters;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mana.nexo_app.Interfaces.moviesResponse;
import com.example.mana.nexo_app.Interfaces.trailerResponse;
import com.example.mana.nexo_app.Interfaces.trailers;
import com.example.mana.nexo_app.Models.MovieData_Model;
import com.example.mana.nexo_app.Models.TrailersData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.MoviesPresenter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MANA on 2/11/2018.
 */

public class MoviesAdaper extends RecyclerView.Adapter<MoviesAdaper.MoviesHolder>  {
    List<MovieData_Model.Result> results;
    List<TrailersData_Model.Result> trailer_result;
    moviesResponse view=null;
    trailerResponse trailers=null;
    MoviesPresenter presenter;
    MoviesHolder holder;
    Context ctx;
    View v;


public MoviesAdaper( List<MovieData_Model.Result> results,Context ctx)
{
    this.results=results;
    this.ctx=ctx;
}

    @Override
    public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        v= LayoutInflater.from(ctx).inflate(R.layout.single_movie,parent,false);
        presenter= new MoviesPresenter(ctx,view,trailers);
        return new MoviesHolder(v);

    }

    @Override
    public void onBindViewHolder(final MoviesHolder holder, final int position) {
    final MovieData_Model.Result response = results.get(position);
    Picasso.with(ctx).load("http://image.tmdb.org/t/p/w500"+response.getPoster_path()).into(holder.movieImage);
    final String movieTitle = response.getOriginal_title();
    final String moviePoster = response.getPoster_path();
    final double movieRate = response.getVote_average();
    final String movieOverview = response.getOverview();
    final String movieLanguage =response.getLanguage();
    final int movieID= response.getId();
    final String movieDate = response.getDate();
    holder.movieImage.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            presenter.intent(ctx,movieTitle,moviePoster,movieRate,movieLanguage,movieID,movieOverview,movieDate);
        }});
    }

    @Override
    public int getItemCount() {
        return results.size();
    }



    class MoviesHolder extends RecyclerView.ViewHolder{

    private ImageView movieImage;


        public MoviesHolder(View itemView) {
            super(itemView);
            movieImage=itemView.findViewById(R.id.movie_image);



        }
    }
}
