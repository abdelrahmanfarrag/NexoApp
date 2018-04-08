package com.example.mana.nexo_app.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mana.nexo_app.Models.ReviewsData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.MoviesPresenter;

import java.util.List;

/**
 * Created by MANA on 2/22/2018.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsHolder> {
    Context ctx;
    List<ReviewsData_Model.ReviewResult> Reviews;
    View v;
    MoviesPresenter Mpresenter;

    public ReviewsAdapter(Context ctx, List<ReviewsData_Model.ReviewResult> Reviews)
    {
        this.ctx=ctx;
        this.Reviews=Reviews;}



    @Override
    public ReviewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        v= LayoutInflater.from(ctx).inflate(R.layout.single_review,parent,false);
        return new ReviewsHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewsHolder holder, int position) {
        ReviewsData_Model.ReviewResult Model = Reviews.get(position);
        holder.name.setText(Model.getName());
        holder.content.setText(Model.getContent());
        final String REVIEW_URL = Model.getUrl();
        Mpresenter = new MoviesPresenter(ctx,null,null);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mpresenter.BrowserIntent(REVIEW_URL);
            }
        });



    }

    @Override
    public int getItemCount() {
        return Reviews.size();
    }

    public class ReviewsHolder extends RecyclerView.ViewHolder
    {
        private TextView name,content;

        public ReviewsHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.review_author_name);
            content=itemView.findViewById(R.id.review_author_content);
        }
    }
}
