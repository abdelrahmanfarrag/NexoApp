package com.example.mana.nexo_app.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MANA on 2/25/2018.
 */

public class TvSeriesDetails_Model {
    @SerializedName("number_of_episodes")
        private int episodes_count;
    @SerializedName("number_of_seasons")
        private int seasons_count;
    @SerializedName("vote_average")
        private double rate;
    @SerializedName("overview")
        private String overview;
    @SerializedName("original_language")
        private String language;
    @SerializedName("name")
        private String name;
    @SerializedName("poster_path")
        private String poster_path;
    @SerializedName("seasons")
    private List<SEASONS>seasons=null;
    @SerializedName("id")
    private int id;
    @SerializedName("episodes")
    private List<EPISODES> episodes=null;
    public int getEpisodes_count() {
            return episodes_count;
        }

        public void setEpisodes_count(int episodes_count) {
            this.episodes_count = episodes_count;
        }

        public int getSeasons_count() {
            return seasons_count;
        }

        public void setSeasons_count(int seasons_count) {
            this.seasons_count = seasons_count;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public List<SEASONS> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<SEASONS> seasons) {
        this.seasons = seasons;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<EPISODES> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<EPISODES> episodes) {
        this.episodes = episodes;
    }

    public class SEASONS{
        @SerializedName("episode_count")
        private int episode_count;
        @SerializedName("poster_path")
        private String poster_path;
        @SerializedName("season_number")
        private int season_number;

        public int getEpisode_count() {
            return episode_count;
        }

        public void setEpisode_count(int episode_count) {
            this.episode_count = episode_count;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public int getSeason_number() {
            return season_number;
        }

        public void setSeason_number(int season_number) {
            this.season_number = season_number;
        }
    }
    public class EPISODES{
        @SerializedName("name")
        private String name;
        @SerializedName("overview")
        private String overView;
        @SerializedName("episode_number")
        private int episode_number;
        @SerializedName("still_path")
        private String back_path;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOverView() {
            return overView;
        }

        public void setOverView(String overView) {
            this.overView = overView;
        }

        public int getEpisode_number() {
            return episode_number;
        }

        public void setEpisode_number(int episode_number) {
            this.episode_number = episode_number;
        }

        public String getBack_path() {
            return back_path;
        }

        public void setBack_path(String back_path) {
            this.back_path = back_path;
        }
    }
}

