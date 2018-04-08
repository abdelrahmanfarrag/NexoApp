package com.example.mana.nexo_app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.Volley;
import com.example.mana.nexo_app.Interfaces.postsview;
import com.example.mana.nexo_app.Models.PostData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.posts_presenter;
import com.example.mana.nexo_app.utils.GetTimeAgo;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MANA on 1/14/2018.
 */

public class Posts_Adapter extends RecyclerView.Adapter<Posts_Adapter.PostsViewHolder>   {
    private List<PostData_Model> model;
    private Context ctx;
    private postsview postsview =null;
    private PostsViewHolder holder;
public Posts_Adapter(Context ctx,List<PostData_Model> model)
{
    this.ctx=ctx;
    this.model=model;
}
    @Override
    public PostsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_post_layout,parent,false);
        return new PostsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PostsViewHolder holder, final int position) {
    final PostData_Model models = model.get(position);
        final posts_presenter presenter1 = new posts_presenter(ctx,postsview);
        if (models.getPost_IMAGE().equals("DEFAULT"))
        {
            holder.postimage.setVisibility(View.GONE);
        }
        else {
            Picasso.with(ctx).load(models.getPost_IMAGE()).into(holder.postimage);

        }
        holder.publishername.setText(models.getUser_Name());
       String postid=models.getID();
        presenter1.getCurrentImage(ctx,holder.userimage,postid);
        holder.topichead.setText(models.getTopic_Head());
        holder.topicdesc.setText(models.getTopic_Desc());
        GetTimeAgo timeAgo = new GetTimeAgo();
        long post_added =models.getPost_Time();
        String post_at =timeAgo.getTimeAgo(post_added,ctx);
        holder.timeAgo.setText(post_at);
        holder.like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenter1.setlikes(model.get(position).getID());
                }
        });
presenter1.getlikes(model.get(position).getID(),holder.like_btn);
presenter1.getlikescount(model.get(position).getID(),holder.likesCount);
presenter1.getComments(model.get(position).getID(),holder.commentCount);
holder.comment_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
  presenter1.sendComments(model.get(position).getID());

 }
});

}
    @Override
    public int getItemCount() {
        return model.size();
    }
    class PostsViewHolder  extends RecyclerView.ViewHolder
    {
        private ImageView postimage;
        private TextView topichead,topicdesc,timeAgo,publishername;
        private CircleImageView userimage;
        private Button like_btn,comment_btn;
        private TextView likesCount,commentCount;


        public PostsViewHolder(View itemView) {
            super(itemView);
            postimage = itemView.findViewById(R.id.posts_image);
            topichead = itemView.findViewById(R.id.topic_head);
            topicdesc = itemView.findViewById(R.id.topic_desc);
            timeAgo   = itemView.findViewById(R.id.time_ago);
            publishername = itemView.findViewById(R.id.posts_publisher_name);
            userimage = itemView.findViewById(R.id.posts_publisher_image);
            like_btn = itemView.findViewById(R.id.likebtn);
            likesCount = itemView.findViewById(R.id.likes_count);
            comment_btn = itemView.findViewById(R.id.commenbtn);
            commentCount = itemView.findViewById(R.id.comments_count);


        }
    }
}


