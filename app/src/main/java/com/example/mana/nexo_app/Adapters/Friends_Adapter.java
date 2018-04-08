package com.example.mana.nexo_app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mana.nexo_app.Interfaces.profileview;
import com.example.mana.nexo_app.Models.FriendData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.activities.ChatActivity;
import com.example.mana.nexo_app.activities.UserProfileActivity;
import com.example.mana.nexo_app.presenter.profile_presenter;
import com.example.mana.nexo_app.utils.GetTimeAgo;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MANA on 1/26/2018.
 */

public class Friends_Adapter extends RecyclerView.Adapter<Friends_Adapter.FriendsHOLDER> {
    private List<FriendData_Model>friends;
    private Context ctx;
    private profileview views=null;
    View v;
    public Friends_Adapter(Context ctx,List<FriendData_Model>friends)
    {
this.ctx=ctx;
this.friends=friends;

    }

    @Override
    public FriendsHOLDER onCreateViewHolder(ViewGroup parent, int viewType) {
         v = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlefriendlayout,parent,false);
        return new FriendsHOLDER(v);
    }

    @Override
    public void onBindViewHolder(FriendsHOLDER holder, int position) {
        final FriendData_Model model=friends.get(position);
        holder.username.setText(model.getNAME());
        GetTimeAgo timeAgo = new GetTimeAgo();
        long friend_added =model.getSINCE();
        String post_at =timeAgo.getTimeAgo(friend_added,ctx);
        final profile_presenter presenter=new profile_presenter(views,ctx);
        holder.ago.setText(post_at);
        Picasso.with(ctx).load(model.getPP()).into(holder.userimage);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter.intent(ctx, UserProfileActivity.class,model.getID());

            }
        });
        holder.SEND_MSG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.intent(ctx, ChatActivity.class,model.getID());

            }
        });

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    class FriendsHOLDER extends RecyclerView.ViewHolder
    {
        private CircleImageView userimage;
        private TextView status,ago,username;
        private ImageButton SEND_MSG;

        public FriendsHOLDER(View itemView) {
            super(itemView);
            status=itemView.findViewById(R.id.friend_result_status);
            ago=itemView.findViewById(R.id.friend_result_ago);
            username=itemView.findViewById(R.id.friend_result_name);
            userimage=itemView.findViewById(R.id.friend_result_image);
            SEND_MSG=itemView.findViewById(R.id.sendMsgBtn);
        }
    }
}
