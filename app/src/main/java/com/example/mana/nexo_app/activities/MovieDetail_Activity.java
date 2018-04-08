package com.example.mana.nexo_app.activities;

import android.content.Intent;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mana.nexo_app.Adapters.ReviewsAdapter;
import com.example.mana.nexo_app.Adapters.TrailerList_Adapter;
import com.example.mana.nexo_app.Interfaces.moviesResponse;
import com.example.mana.nexo_app.Interfaces.trailerResponse;
import com.example.mana.nexo_app.Models.MovieData_Model;
import com.example.mana.nexo_app.Models.ReviewsData_Model;
import com.example.mana.nexo_app.Models.TrailersData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.MoviesPresenter;
import com.example.mana.nexo_app.presenter.posts_presenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.List;

public class MovieDetail_Activity extends AppCompatActivity implements trailerResponse {
    ImageView moviePoster,appBack;
    TextView movie_name,movie_vote,movie_language,movie_date,movie_overview,movie_availble;
    String name,date,poster_path,lang,overview;
    private RecyclerView Rtrailers,Rreviews;
    int id;
    double vote;
    MoviesPresenter Mpresenter;
    posts_presenter Ppresenter;
    moviesResponse Mresp = null;
    trailerResponse Tresp = null;
    private Button community,watch;
    private DatabaseReference userOnline;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userOnline= FirebaseDatabase.getInstance().getReference().child("USERS").child(uid);
        name = getIntent().getExtras().getString("NAME");
        Ppresenter= new posts_presenter(MovieDetail_Activity.this,null);
        setData();
        Mpresenter = new MoviesPresenter(this,Mresp,this);
        Mpresenter.setMovieDetails(getApplicationContext(),poster_path,moviePoster,appBack,name,movie_name,vote,movie_vote,lang,movie_language,date,movie_date,overview,movie_overview);
        id = getIntent().getExtras().getInt("ID");
        Rtrailers = findViewById(R.id.movietrailers_view);
        ViewCompat.setNestedScrollingEnabled(Rtrailers,false);
        Rtrailers.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Rreviews = findViewById(R.id.moviewreview_view);
        Rreviews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        ViewCompat.setNestedScrollingEnabled(Rreviews,false);
        Mpresenter.getTrailers(String.valueOf(id));
        watch=findViewById(R.id.watch);
        movie_availble=findViewById(R.id.watch_availble);
        Mpresenter.setMovieAvailability(name,movie_availble);
      //  Mpresenter.setMovieFirebase(name);
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),MovieVideo_Activity.class);
                i.putExtra("name",name);
                startActivity(i);
            }
        });
        Mpresenter.GetReviews(String.valueOf(id));
        community = findViewById(R.id.movie_comu);
        community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Ppresenter.sendComments(name);
            }
        });
    }
    private void setData()
    {


        poster_path = getIntent().getExtras().getString("IMAGE");
        lang = getIntent().getExtras().getString("LANGUAGE");
        overview = getIntent().getExtras().getString("OVERVIEW");
        date = getIntent().getExtras().getString("DATE");
        vote= getIntent().getExtras().getDouble("VOTE");
        moviePoster =findViewById(R.id.movie_detail_image);
        appBack= findViewById(R.id.movie_thumb);
        movie_name=findViewById(R.id.movie_name);
        movie_vote=findViewById(R.id.movie_vote);
        movie_language=findViewById(R.id.movie_lang);
        movie_date= findViewById(R.id.movie_date);
        movie_overview = findViewById(R.id.movie_over);

    }

    @Override
    public void LoadTrailers(List<TrailersData_Model.Result> MODEL) {
        Rtrailers.setAdapter(new TrailerList_Adapter(getApplicationContext(),MODEL));

    }

    @Override
    public void LoadReviews(List<ReviewsData_Model.ReviewResult> MODEL) {

        Rreviews.setAdapter(new ReviewsAdapter(getApplicationContext(),MODEL));
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();


    }
}
