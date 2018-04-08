package com.example.mana.nexo_app.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mana.nexo_app.Adapters.Episodes_adapter;
import com.example.mana.nexo_app.Interfaces.serieResponse;
import com.example.mana.nexo_app.Models.TvSeriesData_Model;
import com.example.mana.nexo_app.Models.TvSeriesDetails_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.activities.TvSeries_Details;
import com.example.mana.nexo_app.utils.ApplicationContext;
import com.example.mana.nexo_app.utils.ApplicationSingleton;
import com.google.gson.Gson;

import java.lang.reflect.Method;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by MANA on 2/24/2018.
 */

public class TvSeries_Presenter {
    Context ctx;
    private serieResponse Sresponse;
    private StringRequest LoadSeries;
    private RequestQueue queue;
    private static final String API_KEY="0d233259429016eb39b6385c801bec6c";
    public TvSeries_Presenter(Context ctx,serieResponse Sresponse)
    {
        this.ctx=ctx;
        this.Sresponse=Sresponse;
    }

    private static final String SERIE_URL="https://api.themoviedb.org/3/tv/popular?api_key=0d233259429016eb39b6385c801bec6c&language=en-US&page=1";

    public void LoadSeries()
    {
        LoadSeries = new StringRequest(Request.Method.GET,SERIE_URL,Success_Conecction,Failure_Connection);
        queue= ApplicationSingleton.getInstance().getQueue();
        queue.add(LoadSeries);



    }
    private Response.Listener<String> Success_Conecction = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            Gson convertSeries = new Gson();
            TvSeriesData_Model data_model= convertSeries.fromJson(response,TvSeriesData_Model.class);
            Sresponse.LoadSeries(data_model.getResults());
        }
    };
    private Response.ErrorListener Failure_Connection = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };
  public void intent(String id)
  {
      Intent i = new Intent(ctx, TvSeries_Details.class);
      i.putExtra("ID",id);
      ctx.startActivity(i);
  }
  public void getSERIE_detaisl(String id)
  {
      Uri.Builder SERIE_DEATAILS_URI = Uri.parse("http://api.themoviedb.org/3/tv/").buildUpon()
              .appendPath(id)
              .appendQueryParameter("api_key",API_KEY);
      String SERIE_DETAILS_URL= SERIE_DEATAILS_URI.toString();
      StringRequest request = new StringRequest(Request.Method.GET,SERIE_DETAILS_URL,success,error);
      queue = ApplicationSingleton.getInstance().getQueue();
      queue.add(request);

  }
  private Response.Listener<String> success = new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
       Gson gson = new Gson();
       TvSeriesDetails_Model MODEL = gson.fromJson(response,TvSeriesDetails_Model.class);
       Sresponse.LoadCount(MODEL);
       Sresponse.LoadSeasons(MODEL.getSeasons());

      }
  };
private Response.ErrorListener error = new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {
      Toast.makeText(ctx,error.toString(),Toast.LENGTH_LONG).show();
    }
};
public void SaveID(int id)
    {
        SharedPreferences pref =ctx.getSharedPreferences("TEST",Context.MODE_PRIVATE) ;
        SharedPreferences.Editor editor= pref.edit();
        editor.putInt("ID",id);
        editor.commit();

    }
    public int getId()
    {
        SharedPreferences pref = ctx.getSharedPreferences("TEST",Context.MODE_PRIVATE);
       int x= pref.getInt("ID",0);
       return x;
    }
    public void getEpisodes(int season_number,int serie_id)
    {
        Uri.Builder SERIE_EPISODES_URI = Uri.parse("http://api.themoviedb.org/3/tv/").buildUpon()
                .appendPath(String.valueOf(serie_id))
                .appendPath("season")
                .appendPath(String.valueOf(season_number))
                .appendQueryParameter("api_key",API_KEY);
        String SERIE_EPISODES_URL = SERIE_EPISODES_URI.toString();
        queue=ApplicationSingleton.getInstance().getQueue();
        StringRequest episodes_req = new StringRequest(Request.Method.GET,SERIE_EPISODES_URL,getEpisodes_success,getEpisodes_error);
        queue.add(episodes_req);

    }
    private Response.Listener<String> getEpisodes_success = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
         Gson gson = new Gson();
         TvSeriesDetails_Model episodes = gson.fromJson(response,TvSeriesDetails_Model.class);
         LoadEpisodes(episodes.getEpisodes());
        }
    };
private Response.ErrorListener getEpisodes_error = new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError error) {

    }

};
private void LoadEpisodes(List<TvSeriesDetails_Model.EPISODES> episodes)
{
    AlertDialog.Builder ViewEpisodes = new AlertDialog.Builder(ctx);
    LayoutInflater EPISODES_INFLATER = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View v = EPISODES_INFLATER.inflate(R.layout.serie_dialog,null);
    ViewEpisodes.setView(v);
    RecyclerView epidsode_display = v.findViewById(R.id.episodes_view);
    epidsode_display.setLayoutManager(new LinearLayoutManager(ctx,LinearLayoutManager.HORIZONTAL,false));
    epidsode_display.setAdapter(new Episodes_adapter(ctx,episodes));
    AlertDialog dialog = ViewEpisodes.create();
    dialog.show();

}










}
