package com.example.mana.nexo_app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mana.nexo_app.Adapters.Seasons_adapter;
import com.example.mana.nexo_app.Interfaces.serieResponse;
import com.example.mana.nexo_app.Models.TvSeriesData_Model;
import com.example.mana.nexo_app.Models.TvSeriesDetails_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.TvSeries_Presenter;
import com.example.mana.nexo_app.presenter.posts_presenter;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class TvSeries_Details extends AppCompatActivity implements serieResponse {
    TvSeries_Presenter Tpresenter ;
    private TextView s_name,s_rating,s_language,s_seasons,s_episodes,s_overview;
    private ImageView s_image,s_overview_background;
    private RecyclerView RSeasons;
    private Seasons_adapter adapter;
    private posts_presenter Ppresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_series__details);
        final String id = getIntent().getExtras().getString("ID");
        Tpresenter = new TvSeries_Presenter(getApplicationContext(),this);
        Tpresenter.getSERIE_detaisl(id);
        Ppresenter = new posts_presenter(TvSeries_Details.this,null);

        s_name= findViewById(R.id.serie_detail_name);
        s_rating = findViewById(R.id.serie_detail_rate);
        s_language= findViewById(R.id.serie_details_language);
        s_seasons = findViewById(R.id.serie_detail_seasons);
        s_episodes= findViewById(R.id.serie_details_episodes);
        s_image = findViewById(R.id.serie_detail_image);
        s_overview_background=findViewById(R.id.serie_overview_background);
        s_overview=findViewById(R.id.serie_detail_overview);
        RSeasons= findViewById(R.id.serie_detail_view_seasons);
        RSeasons.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        Button b = findViewById(R.id.serie_community);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ppresenter.sendComments(id);
            }
        });

    }

    @Override
    public void LoadSeries(List<TvSeriesData_Model.Results> results) {

    }

    @Override
    public void LoadCount(TvSeriesDetails_Model results) {
        s_name.setText(results.getName());
        s_rating.setText("RATING : "+results.getRate()+"/10");
        s_language.setText("LANGUAGE : "+results.getLanguage());
        s_seasons.setText("NUMBER OF SEASONS : "+results.getSeasons_count());
        s_episodes.setText("NUMBER OF EPISODES : "+results.getEpisodes_count());
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500"+results.getPoster_path()).into(s_image);
        s_overview.setText(results.getOverview());
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w500"+results.getPoster_path()).into(s_overview_background);
    }

    @Override
    public void LoadSeasons(List<TvSeriesDetails_Model.SEASONS> seasons) {
        adapter= new Seasons_adapter(this,seasons);
        RSeasons.setAdapter(adapter);
    }


}
