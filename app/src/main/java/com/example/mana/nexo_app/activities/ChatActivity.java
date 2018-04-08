package com.example.mana.nexo_app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mana.nexo_app.Adapters.Message_Adapter;
import com.example.mana.nexo_app.Interfaces.msgView;
import com.example.mana.nexo_app.Models.MsgData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.ChatPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity implements msgView {
    TextView id;
    private Toolbar chatBar;
    private TextView CHAT_NAME,CHAT_STATUS;
    private CircleImageView CHAT_IMAGE;
    private ImageView CHAT_ICON;
    private DatabaseReference useRef;
    private RecyclerView mymsg_view;
    String other_user_id;
    ChatPresenter presenter;
    private EditText MSG_TEXT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        String current_ID= FirebaseAuth.getInstance().getCurrentUser().getUid();
        useRef=FirebaseDatabase.getInstance().getReference().child("USERS").child(current_ID);
        chatBar=findViewById(R.id.chatbar);
        setSupportActionBar(chatBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         other_user_id=getIntent().getExtras().getString("ID");
         mymsg_view=findViewById(R.id.chat_view);
         mymsg_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
         mymsg_view.setHasFixedSize(true);

        CHAT_NAME=chatBar.findViewById(R.id.chat_NAME);
        CHAT_IMAGE=chatBar.findViewById(R.id.chat_IMAGE);
        CHAT_STATUS=chatBar.findViewById(R.id.online_ago);
        CHAT_ICON=chatBar.findViewById(R.id.online_icon);
         presenter=new ChatPresenter(getApplicationContext(),this);
        presenter.getUserData(other_user_id,CHAT_NAME,CHAT_IMAGE);
        presenter.UserOnline(other_user_id,CHAT_STATUS,CHAT_ICON);
        MSG_TEXT=findViewById(R.id.msg);
        presenter.addChat(other_user_id);
        presenter.LoadMsgs(other_user_id);
        presenter.setMSGCONTENT(CHAT_IMAGE,getApplicationContext(),other_user_id);

    }

    @Override
    protected void onStart() {
        super.onStart();
        useRef.child("ONLINE").setValue("ONLINE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        useRef.child("ONLINE").setValue(ServerValue.TIMESTAMP);
    }
    public void send_msg(View view) {
        String msg=MSG_TEXT.getText().toString();
        presenter.sendMESSAGE(msg,other_user_id);
        MSG_TEXT.setText("");
    }

    @Override
    public void loadMSGS(List<MsgData_Model> model) {
        Message_Adapter adapter=new Message_Adapter(getApplicationContext(),model);
        mymsg_view.scrollToPosition(model.size()-1);
        mymsg_view.setAdapter(adapter);






    }
}
