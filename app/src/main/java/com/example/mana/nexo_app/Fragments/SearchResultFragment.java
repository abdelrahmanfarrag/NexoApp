package com.example.mana.nexo_app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mana.nexo_app.Adapters.Posts_Adapter;
import com.example.mana.nexo_app.Adapters.Profile_Adapter;
import com.example.mana.nexo_app.Interfaces.profileview;
import com.example.mana.nexo_app.Models.FriendData_Model;
import com.example.mana.nexo_app.Models.SearchData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.profile_presenter;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by MANA on 1/23/2018.
 */

public class SearchResultFragment extends Fragment implements profileview {
    private RecyclerView searchResult;
    private profile_presenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_result_view,container,false);
        searchResult=v.findViewById(R.id.searchResult_Recycler);
        searchResult.setHasFixedSize(true);
        searchResult.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter= new profile_presenter(this,getActivity());
        String data=getArguments().getString("DATA");
        presenter.SearchForProfile(data);


        return v;
    }

    @Override
    public void SetSearchResult(List<SearchData_Model> model) {
        if (model.size()>0) {
            searchResult.setAdapter(new Profile_Adapter(getActivity(), model));

        }
        else
        {
        }
    }

    @Override
    public void SetFriendResult(List<FriendData_Model> models) {

    }
}
