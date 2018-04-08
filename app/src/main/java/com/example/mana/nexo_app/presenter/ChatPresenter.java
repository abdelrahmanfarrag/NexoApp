package com.example.mana.nexo_app.presenter;

import android.content.Context;
import android.graphics.Color;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mana.nexo_app.Interfaces.msgView;
import com.example.mana.nexo_app.Models.MsgData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.utils.GetTimeAgo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MANA on 2/5/2018.
 */

public class ChatPresenter {
    Context ctx;
    msgView msg;
    List<MsgData_Model> adding_list=new ArrayList<>();
    public ChatPresenter(Context ctx,msgView msg)
    {
      this.ctx=ctx;
      this.msg=msg;
    }

    public void getUserData(final String id, final TextView TextNAME, final CircleImageView ImageUser)
    {
        DatabaseReference userRef= FirebaseDatabase.getInstance().getReference().child("USERS").child(id);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("NAME").getValue().toString();
                String image=dataSnapshot.child("PROFILE_PICTURE").getValue().toString();

                TextNAME.setText(name);
                Picasso.with(ctx).load(image).placeholder(R.mipmap.default_pp).into(ImageUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void addChat(final String userId)
    {
        final String currentID= FirebaseAuth.getInstance().getCurrentUser().getUid();
    final DatabaseReference chat_Ref=FirebaseDatabase.getInstance().getReference();
chat_Ref.child(currentID).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if (!dataSnapshot.hasChild(userId))
        {
            Map map=new HashMap();
            map.put("since",ServerValue.TIMESTAMP);
            Map data=new HashMap();
            data.put("CHATS/"+currentID+"/"+userId,map);
            data.put("CHATS/"+userId+"/"+currentID,map);
            chat_Ref.updateChildren(data);
        }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
});


    }
    public void UserOnline(String ID, final TextView status, final ImageView onlineimage)
    {
        final DatabaseReference CID= FirebaseDatabase.getInstance().getReference().child("USERS").child(ID);
        CID.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               if (dataSnapshot!=null)
               {
             String stat=dataSnapshot.child("ONLINE").getValue().toString();
             if (stat.equals("ONLINE"))
             {

                 onlineimage.setVisibility(View.VISIBLE);
                 status.setText("ACTIVE");
             }
             else
             {
                 onlineimage.setVisibility(View.GONE);
                 GetTimeAgo timeAgo = new GetTimeAgo();
                 long x= (long) dataSnapshot.child("ONLINE").getValue();
                 String last_online =timeAgo.getTimeAgo(x,ctx);
                 status.setText(last_online);
             }

               }}
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void sendMESSAGE(String msg,String UserId)
    {
        String currentID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (!TextUtils.isEmpty(msg))
        {
            String CurrentUserRef="MESSAGES/"+currentID+"/"+UserId;
            String OtherUserRef="MESSAGES/"+UserId+"/"+currentID;
            Map chatMap=new HashMap();
            chatMap.put("ID",currentID);
            chatMap.put("SENT",ServerValue.TIMESTAMP);
            chatMap.put("MSG",msg);
            DatabaseReference roots=FirebaseDatabase.getInstance().getReference();
            DatabaseReference ROOt=FirebaseDatabase.getInstance().getReference().child("MESSAGES").push();
            String pushed_key= ROOt.getKey();
            Map AddCHat=new HashMap();
            AddCHat.put(CurrentUserRef+"/"+pushed_key,chatMap);
            AddCHat.put(OtherUserRef+"/"+pushed_key,chatMap);
            roots.updateChildren(AddCHat);
            DatabaseReference notifcationREF=FirebaseDatabase.getInstance().getReference().child("MESSAGE_NOTIFICATION").child(UserId);
            Map map2=new HashMap();
            map2.put("FROM",currentID);
            map2.put("MESSAGE",msg);
            map2.put("TYPE","MESSAGE");
            notifcationREF.push().setValue(map2);




        }

    }
    public void LoadMsgs(String userID)
    {
        String CurrentuserID=FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("MESSAGES").child(CurrentuserID).child(userID);
    ref.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            MsgData_Model mymodel=dataSnapshot.getValue(MsgData_Model.class);
            adding_list.add(mymodel);
            msg.loadMSGS(adding_list);

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });



    }
    public void setMSGCONTENT(final CircleImageView userImage, final Context ctx, final String id)
    {
        DatabaseReference userREF=FirebaseDatabase.getInstance().getReference().child("USERS").child(id);
        userREF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image=dataSnapshot.child("PROFILE_PICTURE").getValue().toString();
                Picasso.with(ctx).load(image).into(userImage);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        }

    public void changeParams(String id,CircleImageView image,TextView ago,TextView msg)
    {String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
if (uid.equals(id))
    {
        RelativeLayout.LayoutParams PARAMS= (RelativeLayout.LayoutParams) ago.getLayoutParams();
        PARAMS.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        ago.setLayoutParams(PARAMS);
        RelativeLayout.LayoutParams PARAMS1= (RelativeLayout.LayoutParams) msg.getLayoutParams();
        PARAMS1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        msg.setLayoutParams(PARAMS1);
        image.setVisibility(View.GONE);




    }
    else
    {
        setMSGCONTENT(image,ctx,id);

     //   Toast.makeText(ctx,"USERID  "+uid+"\n"+"OTHERID  "+id,Toast.LENGTH_LONG).show();


    }

    }










}
