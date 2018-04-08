package com.example.mana.nexo_app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mana.nexo_app.Interfaces.requestsview;
import com.example.mana.nexo_app.Models.RequestsData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.RequestsPresenter;
import com.example.mana.nexo_app.utils.GetTimeAgo;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MANA on 2/1/2018.
 */

public class RequestsAdapter extends RecyclerView.Adapter<RequestsAdapter.RequestsHolder>{
    View v;
    Context ctx;
    List<RequestsData_Model>model;
    requestsview inter=null;
    public RequestsAdapter(Context ctx,List<RequestsData_Model>model)
    {this.ctx=ctx;
    this.model=model;
    }


    @Override
    public RequestsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v=LayoutInflater.from(ctx).inflate(R.layout.single_request_layout,parent,false);
     /*   RequestsPresenter presenter=new RequestsPresenter(ctx,inter);
        presenter.LoadRequests();*/
        return new RequestsHolder(v);
    }

    @Override
    public void onBindViewHolder(RequestsHolder holder, final int position) {
     //   notifyDataSetChanged();

        final RequestsData_Model models=model.get(position);
        final RequestsPresenter presenter=new RequestsPresenter(ctx,inter);

        presenter.SetUserData(ctx,holder.imageView,holder.name,holder.status,models.getKEY());
        holder.accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.AcceptRequest(models.getKEY());
                model.remove(position);
                notifyDataSetChanged();}
        });
        holder.decline_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.DeclineRequest(models.getKEY());
                model.remove(position);
                notifyDataSetChanged();
            }
        });
        GetTimeAgo timeAgo = new GetTimeAgo();
        long rerquest_sent =models.getSENT_AT();
        String post_at =timeAgo.getTimeAgo(rerquest_sent,ctx);
        holder.time_Ago.setText(post_at);


    }

    @Override
    public int getItemCount() {
        return model.size();
    }

    class RequestsHolder extends RecyclerView.ViewHolder
    {
        CircleImageView imageView;
        TextView status,name,time_Ago;
        Button accept_btn,decline_btn;

        public RequestsHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.request_image);
            status=itemView.findViewById(R.id.request_status);
            name=itemView.findViewById(R.id.request_name);
            time_Ago=itemView.findViewById(R.id.request_time);
            accept_btn=itemView.findViewById(R.id.requst_accept_btn);
            decline_btn=itemView.findViewById(R.id.request_decline_btn);

        }
    }
}
