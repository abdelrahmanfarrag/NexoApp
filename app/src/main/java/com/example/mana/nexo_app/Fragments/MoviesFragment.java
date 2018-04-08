package com.example.mana.nexo_app.Fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.mana.nexo_app.Adapters.MoviesAdaper;
import com.example.mana.nexo_app.Interfaces.moviesResponse;
import com.example.mana.nexo_app.Interfaces.trailerResponse;
import com.example.mana.nexo_app.Models.MovieData_Model;
import com.example.mana.nexo_app.Models.Parcel;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.MoviesPresenter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MANA on 2/11/2018.
 */

public class MoviesFragment extends Fragment implements moviesResponse
{
    View v;
    MoviesPresenter Mpresenter;
    private RecyclerView myView ;
    private trailerResponse resp= null;

    private List<MovieData_Model.Result> results= new ArrayList<>();
    int x =0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.movies_fragment,container,false);
        myView=v.findViewById(R.id.movies_view);
        myView.setHasFixedSize(true);
        myView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        Mpresenter=new MoviesPresenter(getActivity(),this,resp);



            Mpresenter.getMovies();



        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
           }

    @Override
    public void LoadMovies(List<MovieData_Model.Result> response) {
        myView.setAdapter(new MoviesAdaper(response,getActivity()));

    }
}
