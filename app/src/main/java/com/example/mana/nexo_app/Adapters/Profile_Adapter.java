package com.example.mana.nexo_app.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mana.nexo_app.Interfaces.profileview;
import com.example.mana.nexo_app.Models.SearchData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.activities.UserProfileActivity;
import com.example.mana.nexo_app.presenter.profile_presenter;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MANA on 1/23/2018.
 */

public class Profile_Adapter extends RecyclerView.Adapter<Profile_Adapter.ProfileHolder>{
    private Context ctx;
    private List<SearchData_Model> search_list;
    View v;
    private profile_presenter presenter;
    private profileview   view=null;

    public Profile_Adapter(Context ctx,List<SearchData_Model> search_list) {
        this.ctx = ctx;
        this.search_list=search_list;
    }

    @Override
    public ProfileHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
         v = LayoutInflater.from(ctx).inflate(R.layout.singlesearchresult,parent,false);
        return new ProfileHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProfileHolder holder, int position) {
        SearchData_Model model = search_list.get(position);
        final String profile_ID=search_list.get(position).getID();
        holder.search_name.setText(model.getNAME());
        presenter=new profile_presenter( view,ctx);
        holder.search_status.setText(model.getSTATUS());
        holder.search_lives.setText(model.getLIVES());
        presenter.REQUESTS(profile_ID,holder.req_btn,holder.can_btn);
        Picasso.with(ctx).load(model.getPROFILE_PICTURE()).placeholder(R.mipmap.default_pp).into(holder.search_image);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.intent(ctx,UserProfileActivity.class,profile_ID);

            }});
        holder.req_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    presenter.SEND_REQUEST(holder.req_btn,holder.can_btn,profile_ID);
                holder.can_btn.setVisibility(View.GONE);
            }
        });
        holder.can_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.removeRequest(holder.can_btn,profile_ID,holder.req_btn);
            }
        });
    }
    @Override
    public int getItemCount() {
        return search_list.size();
    }

    class ProfileHolder extends RecyclerView.ViewHolder{
        private TextView search_name,search_status,search_lives;
        private CircleImageView search_image;
        private Button req_btn,can_btn;

        public ProfileHolder(View itemView) {
            super(itemView);
            search_name=itemView.findViewById(R.id.search_result_name);
            search_image=itemView.findViewById(R.id.search_result_image);
            search_status=itemView.findViewById(R.id.search_result_status);
            search_lives=itemView.findViewById(R.id.search_result_lives);
            req_btn=itemView.findViewById(R.id.sentreqbtn);
            can_btn=itemView.findViewById(R.id.rejectreqbtn);
       }
    }
}
