package com.example.mana.nexo_app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MANA on 2/22/2018.
 */

public class ReviewsData_Model {
    @SerializedName("id")
    private int id;
    @SerializedName("page")
    private String page;
    @SerializedName("results")
    private List<ReviewResult> results = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<ReviewResult> getResults() {
        return results;
    }

    public void setResults(List<ReviewResult> results) {
        this.results = results;
    }

    public class ReviewResult
    {
        @SerializedName("id")
        private String id;
        @SerializedName("author")
        private String name;
        @SerializedName("content")
        private String content;
        @SerializedName("url")
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
