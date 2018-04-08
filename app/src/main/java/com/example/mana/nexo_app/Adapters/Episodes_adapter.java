package com.example.mana.nexo_app.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mana.nexo_app.Models.TvSeriesDetails_Model;
import com.example.mana.nexo_app.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MANA on 2/26/2018.
 */

public class Episodes_adapter extends RecyclerView.Adapter<Episodes_adapter.Episodes_holder> {
    Context ctx;
    List<TvSeriesDetails_Model.EPISODES> episodes;
    View v;
    public Episodes_adapter(Context ctx,List<TvSeriesDetails_Model.EPISODES> episodes)
    {
        this.ctx=ctx;
        this.episodes=episodes;
    }

    @Override
    public Episodes_holder onCreateViewHolder(ViewGroup parent, int viewType) {
        v= LayoutInflater.from(ctx).inflate(R.layout.single_episode,parent,false);
        return new Episodes_holder(v);
    }

    @Override
    public void onBindViewHolder(Episodes_holder holder, int position) {
        TvSeriesDetails_Model.EPISODES EPISODES = episodes.get(position);
        holder.overview.setText(EPISODES.getOverView());
        holder.number.setText("EPISODE NUMBER "+EPISODES.getEpisode_number());
        holder.name.setText(EPISODES.getName());
        Picasso.with(ctx).load("http://image.tmdb.org/t/p/w500"+EPISODES.getBack_path()).into(holder.episode_image);
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


    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    class Episodes_holder extends RecyclerView.ViewHolder{
        private TextView number,name,overview;
        private ImageView episode_image;


        public Episodes_holder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.single_episode_name);
            number=itemView.findViewById(R.id.single_episode_number);
            overview=itemView.findViewById(R.id.single_episode_overview);
            episode_image = itemView.findViewById(R.id.single_episode_image);
        }
    }
}
