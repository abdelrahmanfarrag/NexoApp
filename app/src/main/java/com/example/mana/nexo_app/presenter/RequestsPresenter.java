package com.example.mana.nexo_app.presenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mana.nexo_app.Interfaces.requestsview;
import com.example.mana.nexo_app.Models.RequestsData_Model;
import com.example.mana.nexo_app.R;
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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by MANA on 2/1/2018.
 */

public class RequestsPresenter {
    Context ctx;
    requestsview requests;
    List<RequestsData_Model> list=new ArrayList<>();
    public RequestsPresenter(Context context,requestsview requests)
    {ctx=context;
    this.requests=requests;
    }
    public void LoadRequests()
    {
        String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference requestRef= FirebaseDatabase.getInstance().getReference().child("REQUESTS").child(uid);
        Query req=requestRef.orderByChild("REQUEST_TYPE").equalTo("RECEIVED");
        req.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(ctx,dataSnapshot.toString(),Toast.LENGTH_LONG).show();
                RequestsData_Model MyModel=dataSnapshot.getValue(RequestsData_Model.class);
                list.add(MyModel);
                requests.SetRequest(list);
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
    public void SetUserData(final Context ctx, final CircleImageView image, final TextView name, final TextView Status, String id)
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("USERS").child(id);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String uname=dataSnapshot.child("NAME").getValue().toString();
                String ustatus=dataSnapshot.child("STATUS").getValue().toString();
                String uImage=dataSnapshot.child("PROFILE_PICTURE").getValue().toString();
                Picasso.with(ctx).load(uImage).placeholder(R.mipmap.default_pp).into(image);
                name.setText(uname);
                Status.setText(ustatus);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void AcceptRequest(final String ID)
    {
        final String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference req_ref=FirebaseDatabase.getInstance().getReference().child("REQUESTS");
        final DatabaseReference friends_ref=FirebaseDatabase.getInstance().getReference().child("FRIENDS");
        req_ref.child(uid).child(ID).child("KEY").removeValue();
        req_ref.child(uid).child(ID).child("SENT_AT").removeValue();
        req_ref.child(uid).child(ID).child("REQUEST_TYPE").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                req_ref.child(ID).child(uid).child("KEY").removeValue();
                req_ref.child(ID).child(uid).child("SENT_AT").removeValue();
                req_ref.child(ID).child(uid).child("REQUEST_TYPE").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            friends_ref.child(uid).child(ID).child("SINCE").setValue(ServerValue.TIMESTAMP).addOnCompleteListener
                                    (new OnCompleteListener<Void>() {
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
                                                    friends_ref.child(uid).child(ID).setValue(otheruserdata);
                                                }

                                                @Override
                                                public void onCancelled(DatabaseError databaseError) {

                                                }
                                            });
                                            friends_ref.child(ID).child(uid).child("SINCE").setValue(ServerValue.TIMESTAMP).addOnCompleteListener
                                                    (new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            DatabaseReference userref=FirebaseDatabase.getInstance().getReference().child("USERS").child(uid);
                                                            userref.addValueEventListener(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    String name = dataSnapshot.child("NAME").getValue().toString();
                                                                    String pp=dataSnapshot.child("PROFILE_PICTURE").getValue().toString();
                                                                    Map otheruserdata=new HashMap();
                                                                    otheruserdata.put("NAME",name);
                                                                    otheruserdata.put("pp",pp);
                                                                    otheruserdata.put("ID",uid);
                                                                    otheruserdata.put("SINCE",ServerValue.TIMESTAMP);
                                                                    friends_ref.child(ID).child(uid).setValue(otheruserdata);
                                                                }

                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {

                                                                }
                                                            });
                                                        }
                                                    });


                                        }
                                    });

                        }
                    }
                });

            }
        });

    }
    public void DeclineRequest(final String ID)
    {
        final String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference req_ref=FirebaseDatabase.getInstance().getReference().child("REQUESTS");
        req_ref.child(uid).child(ID).child("KEY").removeValue();
        req_ref.child(uid).child(ID).child("SENT_AT").removeValue();
        req_ref.child(uid).child(ID).child("REQUEST_TYPE").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                req_ref.child(ID).child(uid).child("KEY").removeValue();
                req_ref.child(ID).child(uid).child("SENT_AT").removeValue();
                req_ref.child(ID).child(uid).child("REQUEST_TYPE").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(ctx,"CANCELLED",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }





}
