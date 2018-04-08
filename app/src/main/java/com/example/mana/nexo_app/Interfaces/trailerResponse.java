package com.example.mana.nexo_app.Interfaces;

import com.example.mana.nexo_app.Models.ReviewsData_Model;
import com.example.mana.nexo_app.Models.TrailersData_Model;

import java.util.List;

/**
 * Created by MANA on 2/15/2018.
 */

public interface trailerResponse  {
    void LoadTrailers(List<TrailersData_Model.Result> MODEL);
    void LoadReviews (List<ReviewsData_Model.ReviewResult> MODEL);
}
