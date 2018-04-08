package com.example.mana.nexo_app.Interfaces;

import com.example.mana.nexo_app.Models.MovieData_Model;

import java.util.List;

/**
 * Created by MANA on 2/14/2018.
 */

public interface moviesResponse {
    void LoadMovies(List<MovieData_Model.Result> response);
}
