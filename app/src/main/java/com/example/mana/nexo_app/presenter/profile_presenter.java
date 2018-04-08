package com.example.mana.nexo_app.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mana.nexo_app.Interfaces.profileview;
import com.example.mana.nexo_app.Models.FriendData_Model;
import com.example.mana.nexo_app.Models.SearchData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.activities.UserProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MANA on 1/22/2018.
 */

public class profile_presenter  {
    profileview profile;
    Context ctx;
    List<SearchData_Model> data = new ArrayList<>();
    List<FriendData_Model>fdata=new ArrayList<>();
    SearchData_Model result;
    FriendData_Model friend_model;
    public profile_presenter(profileview profile,Context ctx)
    {
        this.profile=profile;
        this.ctx=ctx;
    }
    public void SearchForProfile(final String TEXT)
    {
        DatabaseReference users_ref = FirebaseDatabase.getInstance().getReference().child("USERS");
        Query query = users_ref.orderByChild("NAME").equalTo(TEXT);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.hasChildren())
                {
                    data.remove(result);
                    result=dataSnapshot.getValue(SearchData_Model.class);
                    data.add(result);
                    profile.SetSearchResult(data);
                }
                else
                {
                    Toast.makeText(ctx,"NO uSER",Toast.LENGTH_SHORT).show();
                }
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
    public void intent(Context c, Class cc, String data)
    {
        Intent i = new Intent(c,cc);
        i.putExtra("ID",data);
        c.startActivity(i);
    }
    public void SEND_REQUEST(final Button b, final Button b2, final String ID) {
        b.setEnabled(false);
        final String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference req_ref = FirebaseDatabase.getInstance().getReference().child("REQUESTS");
        if (b.getText().equals("SEND FRIEND REQUEST")) {
            req_ref.child(UID).child(ID).child("REQUEST_TYPE").setValue("SENT").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                req_ref.child(UID).child(ID).child("SENT_AT").setValue(ServerValue.TIMESTAMP);
                                req_ref.child(UID).child(ID).child("KEY").setValue(ID);
                                req_ref.child(ID).child(UID).child("REQUEST_TYPE").setValue("RECEIVED").addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            req_ref.child(ID).child(UID).child("SENT_AT").setValue(ServerValue.TIMESTAMP);
                                            req_ref.child(ID).child(UID).child("KEY").setValue(UID);
DatabaseReference notifcationREF=FirebaseDatabase.getInstance().getReference().child("NOTIFICATION").child(ID);
Map map2=new HashMap();
map2.put("FROM",UID);
map2.put("TYPE","REQUEST");
notifcationREF.push().setValue(map2);
                                            b2.setVisibility(View.GONE);
                                            b.setEnabled(true);
                                            b.setText("CANCEL FRIEND REQUEST");
                                            b.setWidth(160);}}
                                });}}
                    });
        }
        else if (b.getText().equals("CANCEL FRIEND REQUEST")) {
            req_ref.child(UID).child(ID).child("SENT_AT").removeValue();
            req_ref.child(UID).child(ID).child("KEY").removeValue();
                  req_ref.child(UID).child(ID).child("REQUEST_TYPE").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            req_ref.child(ID).child(UID).child("SENT_AT").removeValue();
                            req_ref.child(ID).child(UID).child("KEY").removeValue();
                            req_ref.child(ID).child(UID).child("REQUEST_TYPE").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {

                                        b.setEnabled(true);

                                        b.setText("SEND FRIEND REQUEST");
                                    b.setWidth(160);}}});}});}
        else if (b.getText().equals("FRIENDS"))
        {
            b.setEnabled(true);
            UNFRIEND(b,ID);
        }
        else {
            final DatabaseReference friend_ref = FirebaseDatabase.getInstance().getReference().child("FRIENDS");
            req_ref.child(UID).child(ID).child("KEY").removeValue();
            req_ref.child(UID).child(ID).child("SENT_AT").removeValue();
            req_ref.child(UID).child(ID).child("REQUEST_TYPE").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            req_ref.child(ID).child(UID).child("KEY").removeValue();
                            req_ref.child(ID).child(UID).child("SENT_AT").removeValue();
            req_ref.child(ID).child(UID).child("REQUEST_TYPE").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                     if (task.isSuccessful()) {
                         friend_ref.child(UID).child(ID).child("SINCE").setValue(ServerValue.TIMESTAMP).addOnCompleteListener(new OnCompleteListener<Void>() {
                             @Override
                             public void onComplete(@NonNull Task<Void> task) {
                                 DatabaseReference userref=FirebaseDatabase.getInstance().getReference().child("USERS").child(ID);
                                 userref.addValueEventListener(new ValueEventListener() {
                                     @Override
                                     public void onDataChange(DataSnapshot dataSnapshot) {
                                         String name = dataSnapshot.child("NAME").getValue().toString();
                                         String pp=dataSnapshot.child("PROFILE_PICTURE").getValue().toString();
                                         Map otheruserdata=new HashMap();
                                         otheruserdata.put("NAME",name);
                                         otheruserdata.put("pp",pp);
                                         otheruserdata.put("ID",ID);
                                         otheruserdata.put("SINCE",ServerValue.TIMESTAMP);
                                         friend_ref.child(UID).child(ID).setValue(otheruserdata);
                                     }

                                     @Override
                                     public void onCancelled(DatabaseError databaseError) {

                                     }
                                 });

                                 friend_ref.child(ID).child(UID).child("SINCE").setValue(ServerValue.TIMESTAMP).addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         DatabaseReference userref=FirebaseDatabase.getInstance().getReference().child("USERS").child(UID);
                                         userref.addValueEventListener(new ValueEventListener() {
                                             @Override
                                             public void onDataChange(DataSnapshot dataSnapshot) {
                                                 String name = dataSnapshot.child("NAME").getValue().toString();
                                                 String pp=dataSnapshot.child("PROFILE_PICTURE").getValue().toString();
                                                 Map otheruserdata=new HashMap();
                                                 otheruserdata.put("NAME",name);
                                                 otheruserdata.put("pp",pp);
                                                 otheruserdata.put("ID",UID);
                                                 otheruserdata.put("SINCE",ServerValue.TIMESTAMP);
                                                 friend_ref.child(ID).child(UID).setValue(otheruserdata);
                                             }

                                             @Override
                                             public void onCancelled(DatabaseError databaseError) {

                                             }
                                         });

                                         b.setText("FRIENDS");
                                         b2.setVisibility(View.GONE);
                                     b.setWidth(160);
                                     }});}});}}
                            });}
                    });
        }
    }
    public void REQUESTS(final String ID, final Button b, final Button b2) {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (uid.equals(ID)) {
            b.setVisibility(View.GONE);
        } else {
            final DatabaseReference req_ref = FirebaseDatabase.getInstance().getReference().child("REQUESTS").child(uid).child(ID);
            final DatabaseReference friends_ref = FirebaseDatabase.getInstance().getReference().child("FRIENDS").child(uid);
            friends_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(ID)) {
                        b.setText("FRIENDS");
                        b.setWidth(160);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            req_ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()) {
                        String req_type = dataSnapshot.child("REQUEST_TYPE").getValue().toString();
                        if (req_type.equals("SENT")) {
                            b.setText("CANCEL FRIEND REQUEST");
                            b.setWidth(160);

                        } else if (req_type.equals("RECEIVED")) {
                            b.setText("ACCEPT FRIEND REQUEST");
                            b.setWidth(80);
                            b2.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    }
    public void SETFRIENDS()
    {
        String UID =FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference cref= FirebaseDatabase.getInstance().getReference().child("FRIENDS").child(UID);
        cref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                friend_model=dataSnapshot.getValue(FriendData_Model.class);
                fdata.add(friend_model);
                profile.SetFriendResult(fdata);

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
    public void removeRequest(final Button b2, final String id, final Button b1) {
        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference reqref = FirebaseDatabase.getInstance().getReference().child("REQUESTS");
        reqref.child(uid).child(id).child("SENT_AT").removeValue();
        reqref.child(uid).child(id).child("KEY").removeValue();
      reqref.child(uid).child(id).child("REQUEST_TYPE").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
          @Override
          public void onComplete(@NonNull Task<Void> task) {

              if (task.isSuccessful())
              {
                  reqref.child(id).child(uid).child("SENT_AT").removeValue();
                  reqref.child(id).child(uid).child("KEY").removeValue();
                  reqref.child(id).child(uid).child("REQUEST_TYPE").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                  @Override
                  public void onComplete(@NonNull Task<Void> task) {
                      if (task.isSuccessful())
                      {

                          b2.setVisibility(View.GONE);
                          b1.setText("SEND FRIEND REQUEST");
                          b1.setWidth(160);
                          b1.setBackgroundColor(Color.GREEN);
                          Toast.makeText(ctx, "DONE", Toast.LENGTH_LONG).show();
                      }
                  }
              });

              }
          }
      });
         /*  b2.setVisibility(View.GONE);
                    b1.setText("SEND FRIEND REQUEST");
                    b1.setWidth(120);
                    Toast.makeText(ctx, "DONE", Toast.LENGTH_LONG).show();
                    */
    }
    private void UNFRIEND(final Button b, final String id)
    {
        if (b.getText().equals("FRIENDS"))
        {
            final String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
            final DatabaseReference friendRef=FirebaseDatabase.getInstance().getReference().child("FRIENDS");
            friendRef.child(uid).child(id).child("NAME").removeValue();
            friendRef.child(uid).child(id).child("SINCE").removeValue();
            friendRef.child(uid).child(id).child("ID").removeValue();

            friendRef.child(uid).child(id).child("pp").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                    {
                        friendRef.child(id).child(uid).child("NAME").removeValue();
                        friendRef.child(id).child(uid).child("SINCE").removeValue();
                        friendRef.child(id).child(uid).child("pp").removeValue();
                        friendRef.child(id).child(uid).child("ID").removeValue();
                        b.setText("SEND FRIEND REQUEST");
                        b.setWidth(160);
                    }
                }
            });
        }
    }
    public void getUserData(final Context ctx, final TextView NAME, final TextView STATUS,
                            final TextView LIVES , final TextView WORK , final ImageView PROFILE_PICTURE,
                            final ImageView COVER_PICTURE, String id)
    {
        DatabaseReference userREF=FirebaseDatabase.getInstance().getReference().child("USERS").child(id);
        userREF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("NAME").getValue().toString();
                String status=dataSnapshot.child("STATUS").getValue().toString();
                String cover=dataSnapshot.child("COVER_PICTURE").getValue().toString();
                String profile_picture=dataSnapshot.child("PROFILE_PICTURE").getValue().toString();
                String work=dataSnapshot.child("WORK").getValue().toString();
                String live=dataSnapshot.child("LIVES").getValue().toString();
                NAME.setText(name);
                STATUS.setText(status);
                LIVES.setText(live);
                WORK.setText(work);
                if (profile_picture.equals("DEFAULT"))
                {
                    PROFILE_PICTURE.setBackgroundResource(R.mipmap.default_pp);
                }
                else
                {
                    Picasso.with(ctx).load(profile_picture).into(PROFILE_PICTURE);
                }
                if (cover.equals("DEFAULT"))
                {
                    COVER_PICTURE.setBackgroundResource(R.mipmap.defualt_cover);
                }
                else
                {
                    Picasso.with(ctx).load(cover).into(COVER_PICTURE);
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }




}
