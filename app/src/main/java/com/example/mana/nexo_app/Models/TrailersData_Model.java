package com.example.mana.nexo_app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MANA on 2/14/2018.
 */

public class TrailersData_Model {
    @SerializedName("id")
    private String id;
    @SerializedName("results")
    private List<Result> results=null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public class Result
    {
        @SerializedName("key")
        private String key;
        @SerializedName("name")
        private String name;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
