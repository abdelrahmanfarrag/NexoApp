package com.example.mana.nexo_app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mana.nexo_app.Models.CommentsData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.utils.GetTimeAgo;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MANA on 1/20/2018.
 */

public class Comments_Adapter extends RecyclerView.Adapter<Comments_Adapter.COMMENTS_HOLDER> {
    private List<CommentsData_Model> comment_model;
    private Context ctx;
    public Comments_Adapter(Context ctx, List<CommentsData_Model> comment_model)
    {
        this.ctx=ctx;
        this.comment_model=comment_model;
    }



    @Override
    public COMMENTS_HOLDER onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.singlecomment,parent,false);
        return new COMMENTS_HOLDER(v);

  }

    @Override
    public void onBindViewHolder(COMMENTS_HOLDER holder, int position) {
        CommentsData_Model Onecomment_model = comment_model.get(position);
        GetTimeAgo timeAgo = new GetTimeAgo();
        long post_added =Onecomment_model.getCOMMENT_TIME();
        String post_at =timeAgo.getTimeAgo(post_added,ctx);
        holder.userCommentTime.setText(post_at);
        holder.userComment.setText(Onecomment_model.getCOMMENT());
        Picasso.with(ctx).load(Onecomment_model.getUSER_IMAGE()).placeholder(R.mipmap.default_pp).into(holder.userImage);
        holder.userName.setText(Onecomment_model.getUSER_NAME());

    }

    @Override
    public int getItemCount() {
        return comment_model.size();
    }

    class COMMENTS_HOLDER extends RecyclerView.ViewHolder{
        private CircleImageView userImage;
        private TextView userComment,userCommentTime,userName;



        public COMMENTS_HOLDER(View itemView) {
            super(itemView);
            userImage = itemView.findViewById(R.id.usercomment_image);
            userComment = itemView.findViewById(R.id.usercomment_contain);
            userName = itemView.findViewById(R.id.usercomment_name);
            userCommentTime = itemView.findViewById(R.id.usercomment_time);
        }
    }
}
