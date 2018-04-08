package com.example.mana.nexo_app.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.mana.nexo_app.presenter.MoviesPresenter;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MANA on 2/11/2018.
 */

public class MovieData_Model  {
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int total_results;
    @SerializedName("total_pages")
    private int total_pages;
    @SerializedName("results")
    private List<Result> results=null;

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

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public class Result
       {
           @SerializedName("poster_path")
           private String poster_path;
           @SerializedName("original_title")
           private String original_title;
           @SerializedName("id")
           private int id;
           @SerializedName("vote_average")
           private double vote_average;
           @SerializedName("original_language")
           private String language;
           @SerializedName("overview")
           private String overview;
           @SerializedName("release_date")
           private String date;

           public String getPoster_path() {
               return poster_path;
           }

           public void setPoster_path(String poster_path) {
               this.poster_path = poster_path;
           }

           public String getOriginal_title() {
               return original_title;
           }

           public void setOriginal_title(String original_title) {
               this.original_title = original_title;
           }

           public int getId() {
               return id;
           }

           public void setId(int id) {
               this.id = id;
           }

           public double getVote_average() {
               return vote_average;
           }

           public void setVote_average(double vote_average) {
               this.vote_average = vote_average;
           }

           public String getLanguage() {
               return language;
           }

           public void setLanguage(String language) {
               this.language = language;
           }

           public String getOverview() {
               return overview;}

           public void setOverview(String overview) {
               this.overview = overview;
           }

           public String getDate() {
               return date;
           }

           public void setDate(String date) {
               this.date = date;
           }



       }



    }

