package com.example.mana.nexo_app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mana.nexo_app.Interfaces.profileview;
import com.example.mana.nexo_app.Interfaces.requestsview;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.RequestsPresenter;
import com.example.mana.nexo_app.presenter.profile_presenter;
import com.example.mana.nexo_app.utils.Common;

public class UserProfileActivity extends AppCompatActivity {
    private TextView name,status,work,lives,likes;
    private ImageView profile,cover;
    private Button sendRequest,cancelRequest;
    private ImageButton sendMsg,backBtn;
    private profileview view=null;
    private RequestsPresenter Rpresener;
    private requestsview Rview=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        final String userID=getIntent().getExtras().getString("ID");
        final profile_presenter presenter=new profile_presenter(view,this);
        Rpresener=new RequestsPresenter(this,Rview);
        /***************INITIALIZE VIEWS*****************************/
        name=findViewById(R.id.user_profile_name);
        status=findViewById(R.id.user_profile_status);
        work=findViewById(R.id.user_profile_work);
        lives=findViewById(R.id.user_profile_lives);
        profile=findViewById(R.id.user_profile_picture);
        cover=findViewById(R.id.user_profile_cover);
        sendRequest=findViewById(R.id.send_request);
        cancelRequest=findViewById(R.id.user_cancel_request);
        sendMsg=findViewById(R.id.user_send_msg);
        backBtn=findViewById(R.id.back_action);
        likes= findViewById(R.id.user_posts_count);


        /*************END OF INITIALIZATION******************************/
        presenter.getUserData(getApplicationContext(),name,status,lives,work,profile,cover,userID);
      sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.SEND_REQUEST(sendRequest,cancelRequest,userID);
            }});
        cancelRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.removeRequest(cancelRequest,userID,sendRequest);
            }});
        presenter.REQUESTS(userID,sendRequest,cancelRequest);
        sendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.intent(getApplicationContext(),ChatActivity.class,userID);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.intent(getApplicationContext(),Main_Content_Activity.class,null);


            }
        });


    }
}
