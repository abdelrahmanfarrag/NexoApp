package com.example.mana.nexo_app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mana.nexo_app.Adapters.Posts_Adapter;
import com.example.mana.nexo_app.Models.PostData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.posts_presenter;

import java.util.List;

/**
 * Created by MANA on 1/14/2018.
 */

public class PostsFragment extends Fragment implements com.example.mana.nexo_app.Interfaces.postsview,View.OnClickListener {
    private RecyclerView postsview;
    private Button changeFrag;
    posts_presenter presenter;
    private Posts_Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.posts_fragment,container,false);
        postsview = v.findViewById(R.id.posts_view);
        postsview.setHasFixedSize(true);
        postsview.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter = new posts_presenter(getActivity(),this);
        presenter.getPosts();
        changeFrag = v.findViewById(R.id.new_post_btn);
        changeFrag.setOnClickListener(this);



        return v;
    }


    @Override
    public void loadposts(List<PostData_Model> model) {
        adapter = new Posts_Adapter(getActivity(),model);
        postsview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.new_post_btn)
        {
            newPostFragment change = new newPostFragment();
           FragmentManager manager = getFragmentManager();
           FragmentTransaction transaction = manager.beginTransaction();
           transaction.replace(R.id.fragment_container,change);
           transaction.commit();
        }

    }
}
