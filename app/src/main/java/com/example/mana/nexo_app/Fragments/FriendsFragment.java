package com.example.mana.nexo_app.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mana.nexo_app.Adapters.Friends_Adapter;
import com.example.mana.nexo_app.Adapters.Profile_Adapter;
import com.example.mana.nexo_app.Interfaces.profileview;
import com.example.mana.nexo_app.Models.FriendData_Model;
import com.example.mana.nexo_app.Models.SearchData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.profile_presenter;

import java.util.List;

/**
 * Created by MANA on 1/26/2018.
 */

public class FriendsFragment extends Fragment implements profileview {
    View v;
    RecyclerView friendsview;
    profile_presenter presenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.friends_fragment,container,false);
        friendsview=v.findViewById(R.id.friends_list);
        friendsview.setHasFixedSize(true);
        friendsview.setLayoutManager(new LinearLayoutManager(getActivity()));
        presenter=new profile_presenter(this,getActivity());
        presenter.SETFRIENDS();
        return v;
    }


    @Override
    public void SetSearchResult(List<SearchData_Model> model) {

    }

    @Override
    public void SetFriendResult(List<FriendData_Model> models) {
       // Toast.makeText(getActivity(),models.toString(),Toast.LENGTH_LONG).show();
         friendsview.setAdapter(new Friends_Adapter(getActivity(),models));  }
}
