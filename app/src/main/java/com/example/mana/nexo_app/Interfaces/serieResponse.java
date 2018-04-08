package com.example.mana.nexo_app.Interfaces;

import com.example.mana.nexo_app.Models.TvSeriesData_Model;
import com.example.mana.nexo_app.Models.TvSeriesDetails_Model;

import java.util.List;

/**
 * Created by MANA on 2/24/2018.
 */

public interface serieResponse {
    void LoadSeries(List<TvSeriesData_Model.Results> results);
    void LoadCount(TvSeriesDetails_Model results);
    void LoadSeasons(List<TvSeriesDetails_Model.SEASONS> seasons);
}
