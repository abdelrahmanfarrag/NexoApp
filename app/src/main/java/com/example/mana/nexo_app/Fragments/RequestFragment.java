package com.example.mana.nexo_app.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mana.nexo_app.Adapters.RequestsAdapter;
import com.example.mana.nexo_app.Interfaces.requestsview;
import com.example.mana.nexo_app.Models.RequestsData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.RequestsPresenter;

import java.util.List;

/**
 * Created by MANA on 2/1/2018.
 */

public class RequestFragment extends Fragment implements requestsview {
    RequestsPresenter presenter;
    RecyclerView DISPLAY_REQUESTS;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.requestfragment,container,false);
        presenter=new RequestsPresenter(getActivity(),this);
        DISPLAY_REQUESTS=view.findViewById(R.id.requests_view);
        DISPLAY_REQUESTS.setLayoutManager(new LinearLayoutManager(getActivity()));
        DISPLAY_REQUESTS.setHasFixedSize(true);
        presenter.LoadRequests();
        return view;
    }

    @Override
    public void SetRequest(List<RequestsData_Model> model) {
        RequestsAdapter adapter=new RequestsAdapter(getActivity(),model);
        DISPLAY_REQUESTS.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
