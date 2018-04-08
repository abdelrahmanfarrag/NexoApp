package com.example.mana.nexo_app.Adapters;

import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mana.nexo_app.Models.TvSeriesDetails_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.activities.TvSeries_Details;
import com.example.mana.nexo_app.presenter.TvSeries_Presenter;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MANA on 2/25/2018.
 */

public class Seasons_adapter extends RecyclerView.Adapter<Seasons_adapter.Seasons_Holder> {
    Context ctx;
    List<TvSeriesDetails_Model.SEASONS> result;
    TvSeries_Presenter Tpresennter;

    public View v;
    public Seasons_adapter(Context ctx,List<TvSeriesDetails_Model.SEASONS> result)
    {
        this.ctx=ctx;
        this.result=result;


    }


    @Override
    public Seasons_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        v= LayoutInflater.from(ctx).inflate(R.layout.single_seasons,parent,false);
        return new Seasons_Holder(v);
    }

    @Override
    public void onBindViewHolder(Seasons_Holder holder, final int position) {
   final TvSeriesDetails_Model.SEASONS seasons= result.get(position);
   holder.episodes_count.setText("EPISODES : "+seasons.getEpisode_count());
   holder.season_numbers.setText("SEASON : "+seasons.getSeason_number());
   Tpresennter = new TvSeries_Presenter(ctx,null);
        Picasso.with(ctx).load("http://image.tmdb.org/t/p/w500"+seasons.getPoster_path()).into(holder.season_covers);
        if (position%2==0)
        {
            int color = Color.parseColor("#DF8116");
            v.setBackgroundColor(color);
        }
        else
        {
            int color2 = Color.parseColor("#74550C");
            v.setBackgroundColor(color2);
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tpresennter.getEpisodes(seasons.getSeason_number(),Tpresennter.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();

    }

    class Seasons_Holder extends RecyclerView.ViewHolder{
        private TextView season_numbers,episodes_count;
        private ImageView season_covers;


        public Seasons_Holder(View itemView) {
            super(itemView);
            season_covers=itemView.findViewById(R.id.season_cover);
            season_numbers=itemView.findViewById(R.id.season_rakm);
            episodes_count=itemView.findViewById(R.id.episdoes_cont);
        }
    }
}
