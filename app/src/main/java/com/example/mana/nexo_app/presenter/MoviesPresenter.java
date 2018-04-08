package com.example.mana.nexo_app.presenter;

import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mana.nexo_app.Interfaces.moviesResponse;
import com.example.mana.nexo_app.Interfaces.trailerResponse;
import com.example.mana.nexo_app.Interfaces.trailers;
import com.example.mana.nexo_app.Models.MovieData_Model;
import com.example.mana.nexo_app.Models.ReviewsData_Model;
import com.example.mana.nexo_app.Models.TrailersData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.activities.MovieDetail_Activity;
import com.example.mana.nexo_app.utils.ApplicationSingleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import android.widget.MediaController;
import android.widget.VideoView;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoviesPresenter {
 private Context ctx;
 private moviesResponse movie_response;
 private trailerResponse movie_trailer=null;
 private RequestQueue queue;
 private boolean isPlaying = false;
 private String API_KEY = "ddacbc16669b4962d6c6c553654f7a22";
    public MoviesPresenter(Context ctx,moviesResponse movie_response,trailerResponse movie_trailer)
    {
     this.ctx=ctx;
     this.movie_response=movie_response;
     this.movie_trailer=movie_trailer;
    }
    public void intent(Context ctx,String movieTitle,String movieImage,double movieVote,String movieLanguage,int movieId,String movieOverview,
                       String movieDate)
    {
        Intent i = new Intent(ctx, MovieDetail_Activity.class);
        i.putExtra("NAME",movieTitle);
        i.putExtra("IMAGE",movieImage);
        i.putExtra("VOTE",movieVote);
        i.putExtra("LANGUAGE",movieLanguage);
        i.putExtra("ID",movieId);
        i.putExtra("OVERVIEW",movieOverview);
        i.putExtra("DATE",movieDate);
        ctx.startActivity(i);
    }
    public void setMovieDetails(Context ctx, String poster_path, ImageView movieThumb, ImageView moviePoster,String movieName, TextView tmovieName,double movieVote,TextView tmovieVote
    ,String movieLanguage,TextView tmovieLanguage
    ,String movieDate, TextView tmovieDate
    ,String movieOver, TextView tmovieOver)
    {
        Picasso.with(ctx).load("http://image.tmdb.org/t/p/w500"+poster_path).into(movieThumb);
        Picasso.with(ctx).load("http://image.tmdb.org/t/p/w500"+poster_path).into(moviePoster);
        tmovieName.setText("MOVIE NAME : "+movieName);
        tmovieVote.setText("VOTE : "+String.valueOf(movieVote)+" /10");
        tmovieLanguage.setText("LANGUAGE : "+movieLanguage);
        tmovieDate.setText("REALEASED DATE : "+movieDate);
        tmovieOver.setText(" OVERVIEW "+"\n"+movieOver);
    }

    public void getMovies()
    {
        String url="http://api.themoviedb.org/3/movie/popular?api_key=ddacbc16669b4962d6c6c553654f7a22";
        queue= ApplicationSingleton.getInstance().getQueue();
        StringRequest movies_url=new StringRequest(Request.Method.GET,url,success,error);
        queue.add(movies_url);


    }

    private Response.Listener<String> success=new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Gson convertinData = new Gson();
            MovieData_Model DATA = convertinData.fromJson(response,MovieData_Model.class);
            movie_response.LoadMovies(DATA.getResults());


        }
    };
    private Response.ErrorListener error=new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    public void getTrailers(String id)
    {
        Uri.Builder trailers_URI = Uri.parse("http://api.themoviedb.org/3/movie/").buildUpon()
                .appendPath(id)
                .appendPath("videos")
                .appendQueryParameter("api_key",API_KEY);
        String trailers_URL=trailers_URI.toString();
        queue=ApplicationSingleton.getInstance().getQueue();
        StringRequest trailers = new StringRequest(Request.Method.GET,trailers_URL,trailers_success,trailers_failure);
        queue.add(trailers);
    }
    private Response.Listener<String> trailers_success = new Response.Listener<String>() {
        @Override
        public void onResponse(String response)
        {
            Gson converTrailers = new Gson();
            TrailersData_Model trailersData = converTrailers.fromJson(response,TrailersData_Model.class);
            movie_trailer.LoadTrailers(trailersData.getResults());
           ;

        }
    };
    private Response.ErrorListener trailers_failure = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(ctx,error.toString(),Toast.LENGTH_LONG).show();

        }
    };

/*
  public void viewMovie(final VideoView movie, final Button playbtn, final ProgressBar bufferprog,String name){
      DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("MOVIES").child(name);
      reference.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              String link = dataSnapshot.child("LINK").getValue().toString();
              Uri movie_Uri= Uri.parse(link);
              movie.setVideoURI(movie_Uri);
              movie.requestFocus();
              movie.start();
              isPlaying=true;
              movie.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                  @Override
                  public boolean onInfo(MediaPlayer mediaPlayer, int i, int i1) {
                      //      if (mediaPlayer.MEDIA_INFO_BUFFERING_START)
                      return false;
                  }
              });
              playbtn.setBackgroundResource(R.mipmap.pausebtn);



              playbtn.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if (isPlaying)
                      {movie.pause();
                          playbtn.setBackgroundResource(R.mipmap.playbtn);
                          isPlaying = false;
                      }
                      else
                      {
                          movie.start();
                          playbtn.setBackgroundResource(R.mipmap.pausebtn);
                          isPlaying=true;
                      }
                  }
              });

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });
  }*/
  public void YoutubeIntent(String key)
  {
      Intent in = new Intent(Intent.ACTION_VIEW,Uri.parse("vnd.youtube:"+key));
      ctx.startActivity(in);

  }
  public void GetReviews (String id)
  {
      Uri.Builder reviews_URI = Uri.parse("http://api.themoviedb.org/3/movie/").buildUpon()
              .appendPath(id)
              .appendPath("reviews")
              .appendQueryParameter("api_key",API_KEY);
      String reviews_url = reviews_URI.toString();
      queue=ApplicationSingleton.getInstance().getQueue();
      StringRequest reviews_request = new StringRequest(Request.Method.GET,reviews_url,reviews_listenre,reviews_ERROR);
      queue.add(reviews_request);



  }
  private Response.Listener<String> reviews_listenre = new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
          Gson convertReviews = new Gson();
          ReviewsData_Model reviewsData = convertReviews.fromJson(response, ReviewsData_Model.class);
          movie_trailer.LoadReviews(reviewsData.getResults());
      }
  };
  private Response.ErrorListener reviews_ERROR = new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {

      }
  };
  public void BrowserIntent (String url)
  {
      Intent i = new Intent(Intent.ACTION_VIEW,Uri.parse(url));
      ctx.startActivity(i);
  }
  public void setMovieFirebase(String moviename)
  {
      DatabaseReference movieRef = FirebaseDatabase.getInstance().getReference().child("MOVIES").child(moviename);
      Map map = new HashMap<>();
      map.put("WATCH",true);
      movieRef.setValue(map);

  }
  public void setMovieAvailability (String moviename, final TextView watch)
  {
      DatabaseReference movieRef = FirebaseDatabase.getInstance().getReference().child("MOVIES").child(moviename);
      movieRef.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(DataSnapshot dataSnapshot) {
              if (dataSnapshot.hasChild("LINK"))
              {
                  watch.setText("WATCH : "+" "+"AVAILABLE");
                  watch.setTextColor(Color.GREEN);

              }
              else
              {
                  watch.setText("WATCH : "+" "+"NOT AVAILABLE");
                  watch.setTextColor(Color.RED);
              }

          }

          @Override
          public void onCancelled(DatabaseError databaseError) {

          }
      });


  }






}
