package com.example.mana.nexo_app.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.example.mana.nexo_app.Interfaces.moviesResponse;
import com.example.mana.nexo_app.Interfaces.trailerResponse;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.MoviesPresenter;

public class MovieVideo_Activity extends AppCompatActivity {
    private VideoView video;
    private Button playpause_btn;
    private MoviesPresenter Mpresenter;
    private moviesResponse response =null;
    private trailerResponse tresponse = null;
    private ProgressBar buffer_prog;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_video_);
        Mpresenter= new MoviesPresenter(getApplicationContext(),response,tresponse);
        video=findViewById(R.id.movie_video);
        playpause_btn=findViewById(R.id.play_btn);
        buffer_prog=findViewById(R.id.buffer_progress);
        name=getIntent().getExtras().getString("name");
     //   Mpresenter.viewMovie(video,playpause_btn,buffer_prog,name);




    }
}
