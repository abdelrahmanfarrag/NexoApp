package com.example.mana.nexo_app.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mana.nexo_app.Interfaces.serieResponse;
import com.example.mana.nexo_app.Models.TvSeriesData_Model;
import com.example.mana.nexo_app.Models.TvSeriesDetails_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.TvSeries_Presenter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MANA on 2/24/2018.
 */

public class TvSeries_Adapter extends RecyclerView.Adapter<TvSeries_Adapter.TvSerivers_Holder>{
    private Context ctx;
    private View v;
    private List<TvSeriesData_Model.Results> results;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public TvSeries_Adapter(Context ctx,List<TvSeriesData_Model.Results> results)
    {
        this.ctx=ctx;
        this.results=results;
    }

    @Override
    public TvSerivers_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        v=LayoutInflater.from(ctx).inflate(R.layout.single_movie,parent,false);
        sp = ctx.getSharedPreferences("TEST",Context.MODE_PRIVATE);
        editor = sp.edit();
        return new TvSerivers_Holder(v);
    }

    @Override
    public void onBindViewHolder(TvSerivers_Holder holder, int position) {
        final TvSeriesData_Model.Results SERIES = results.get(position);
        Picasso.with(ctx).load("http://image.tmdb.org/t/p/w500"+SERIES.getPoster_path()).into(holder.serie_poster);
        final TvSeries_Presenter Tpresenter = new TvSeries_Presenter(ctx,null);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Tpresenter.intent(String.valueOf(SERIES.getId()));
                editor.clear();
                Tpresenter.SaveID(SERIES.getId());


            }
        });

    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    class TvSerivers_Holder extends RecyclerView.ViewHolder
    {
        private ImageView serie_poster;

        public TvSerivers_Holder(View itemView) {
            super(itemView);
            serie_poster=itemView.findViewById(R.id.movie_image);
        }
    }
}
