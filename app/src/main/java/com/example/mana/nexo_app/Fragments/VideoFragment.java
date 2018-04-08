package com.example.mana.nexo_app.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
//import com.vidyo.VidyoClient.Connector.Connector;
import com.example.mana.nexo_app.Adapters.TvSeries_Adapter;
import com.example.mana.nexo_app.Interfaces.serieResponse;
import com.example.mana.nexo_app.Models.TvSeriesData_Model;
import com.example.mana.nexo_app.Models.TvSeriesDetails_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.TvSeries_Presenter;

import java.util.List;

/**
 * Created by MANA on 2/22/2018.
 */

public class VideoFragment extends Fragment implements serieResponse {
    private RecyclerView tvSeries;
    private TvSeries_Presenter Tpresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.video_frag,container,false);
        tvSeries=v.findViewById(R.id.series_view);
        Tpresenter= new TvSeries_Presenter(getActivity(),this);
        Tpresenter.LoadSeries();
        tvSeries.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        tvSeries.setHasFixedSize(true);


        return v;
    }

    @Override
    public void LoadSeries(List<TvSeriesData_Model.Results> results) {
        tvSeries.setAdapter(new TvSeries_Adapter(getActivity(),results));
    }

    @Override
    public void LoadCount(TvSeriesDetails_Model results) {

    }

    @Override
    public void LoadSeasons(List<TvSeriesDetails_Model.SEASONS> seasons) {

    }


}
