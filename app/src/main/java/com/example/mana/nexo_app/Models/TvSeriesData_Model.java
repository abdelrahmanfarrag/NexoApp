package com.example.mana.nexo_app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MANA on 2/24/2018.
 */

public class TvSeriesData_Model {
@SerializedName("page")
   private int page;
@SerializedName("total_results")
   private int total_results;
@SerializedName("total_pages")
   private int total_pages;
@SerializedName("results")
private List<Results> results = null;
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }


    public class Results{
@SerializedName("poster_path")
        private String poster_path;
@SerializedName("id")
        private int id;
        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

}
