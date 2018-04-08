package com.example.mana.nexo_app.Adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mana.nexo_app.Models.TrailersData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.MoviesPresenter;

import java.util.List;

public class TrailerList_Adapter extends RecyclerView.Adapter<TrailerList_Adapter.TrailersHolder>
{
    List<TrailersData_Model.Result> trailers;
    MoviesPresenter presenter;
    Context ctx;
    View v;
    public TrailerList_Adapter(Context ctx,List<TrailersData_Model.Result> trailers)
    {
        this.ctx=ctx;
        this.trailers=trailers;
    }

    @Override
    public TrailersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         v = LayoutInflater.from(ctx).inflate(R.layout.single_trailers,parent,false);
        return new TrailersHolder(v);
    }

    @Override
    public void onBindViewHolder(TrailersHolder holder, int position) {
        TrailersData_Model.Result model = trailers.get(position);
        presenter = new MoviesPresenter(ctx,null,null);
        holder.traielr_name.setText(model.getName());
        final String key = model.getKey();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.YoutubeIntent(key);

            }
        });

    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class TrailersHolder extends RecyclerView.ViewHolder
    {
private TextView traielr_name;
        public TrailersHolder(View itemView) {
            super(itemView);
            traielr_name=itemView.findViewById(R.id.trailer_name);
        }
    }
}
