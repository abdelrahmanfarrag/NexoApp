package com.example.mana.nexo_app.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mana.nexo_app.Adapters.Comments_Adapter;
import com.example.mana.nexo_app.Models.CommentsData_Model;
import com.example.mana.nexo_app.Models.PostData_Model;
import com.example.mana.nexo_app.Interfaces.postsview;
import com.example.mana.nexo_app.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class posts_presenter {

    com.example.mana.nexo_app.Interfaces.postsview postsview;
    private Context ctx;
    private DatabaseReference user_ref;
    private DatabaseReference newposts_ref;
    List<PostData_Model> list = new ArrayList<>();
    private boolean is_clicked = false;
    private RecyclerView comment_view;

    public posts_presenter(Context ctx, postsview postsview)
    {
        this.postsview = postsview;
        this.ctx = ctx;
    }
    public void getPosts()
    {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference sposts_ref = FirebaseDatabase.getInstance().getReference();
        sposts_ref.child("POSTS").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                PostData_Model model = dataSnapshot.getValue(PostData_Model.class);
                list.add(model);
                postsview.loadposts(list);
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
    public void add_posts(final Context ctx, final String Post_Head, final String Post_Desc, final String image)
    {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        user_ref = FirebaseDatabase.getInstance().getReference().child("USERS").child(uid);
        user_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newposts_ref = FirebaseDatabase.getInstance().getReference().child("POSTS").push();

                String name = dataSnapshot.child("NAME").getValue().toString();

                String user_image = dataSnapshot.child("PROFILE_PICTURE").getValue().toString();
                HashMap addpost = new HashMap();
                addpost.put("Topic_Desc", Post_Desc);
                addpost.put("Topic_Head", Post_Head);
                addpost.put("User_Name", name);
                addpost.put("User_Picture", user_image);
                addpost.put("Post_Time", ServerValue.TIMESTAMP);
                addpost.put("Post_Image", "DEFAULT");

                if (image.isEmpty()) {
                    addpost.put("Post_Image", "DEFAULT");
                } else {
                    addpost.put("Post_Image", image);
                }
                newposts_ref.setValue(addpost).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            String k = newposts_ref.getRef().getKey().toString();
                            newposts_ref.child("ID").setValue(k);
                            Toast.makeText(ctx, "Post Added", Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    public void addimage(final Context ctx, final String PostHead, final String PostTopic, final Uri data)
    {
        String uids = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference post_images = FirebaseStorage.getInstance().getReference().child("POST_IMAGES");

        StorageReference s_Ref = post_images.child(uids).child(data.getLastPathSegment());
        s_Ref.putFile(data).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    final String uri = task.getResult().getDownloadUrl().toString();
                    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    newposts_ref = FirebaseDatabase.getInstance().getReference().child("POSTS").push();
                    user_ref = FirebaseDatabase.getInstance().getReference().child("USERS").child(uid);
                    user_ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String name = dataSnapshot.child("NAME").getValue().toString();
                            String user_image = dataSnapshot.child("PROFILE_PICTURE").getValue().toString();
                            HashMap addpost = new HashMap();
                            addpost.put("Topic_Desc", PostTopic);
                            addpost.put("Topic_Head", PostHead);
                            addpost.put("User_Name", name);
                            addpost.put("User_Picture", user_image);
                            addpost.put("Post_Time", ServerValue.TIMESTAMP);
                            addpost.put("Post_Image", uri);


                            newposts_ref.setValue(addpost).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())

                                    {
                                        String k = newposts_ref.getRef().getKey().toString();
                                        newposts_ref.child("ID").setValue(k);
                                        Toast.makeText(ctx, "Post Added", Toast.LENGTH_LONG).show();
                                        Toast.makeText(ctx, "Post is added", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }
        });


    }
    public void setlikes(final String key)
    {
        is_clicked = true;
        final String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userref = FirebaseDatabase.getInstance().getReference().child("USERS").child(user_id);
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String user_name = dataSnapshot.child("NAME").getValue().toString();
                final String user_image = dataSnapshot.child("PROFILE_PICTURE").getValue().toString();
                if (key == null) {
                } else {
                    final DatabaseReference likes_ref = FirebaseDatabase.getInstance().getReference().child("LIKES").child(key).child(user_id);
                    likes_ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (is_clicked) {
                                if (dataSnapshot.hasChild("NAME")) {
                                    likes_ref.child("NAME").removeValue();
                                    likes_ref.child("USERIMAGE").removeValue();
                                    is_clicked = false;


                                } else {
                                    HashMap map = new HashMap();
                                    map.put("NAME", user_name);
                                    map.put("USERIMAGE", user_image);
                                    likes_ref.setValue(map);
                                    is_clicked = false;


                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void getlikes(String key, final Button NewView) {

        final String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (key == null) {

        } else {
            DatabaseReference getlikes = FirebaseDatabase.getInstance().getReference().child("LIKES").child(key).child(user_id);
            getlikes.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()) {
                        if (dataSnapshot.hasChild("NAME")) {
                            Drawable heart = ctx.getResources().getDrawable(R.mipmap.redlike);
                            //         heart.setBounds(50,50,0,0);
                            NewView.setCompoundDrawablesRelativeWithIntrinsicBounds(heart, null, null, null);
                            NewView.setText("UNLIKE");
                            NewView.setTextColor(Color.RED);
                        } else {
                            Drawable heart1 = ctx.getResources().getDrawable(R.mipmap.heart);

                            NewView.setCompoundDrawablesRelativeWithIntrinsicBounds(heart1, null, null, null);
                            NewView.setText("LIKE");
                            NewView.setTextColor(Color.WHITE);
                        }
                    } else {

                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }
    public void getlikescount (String key, final TextView likescount)
    {
        DatabaseReference likes_count= FirebaseDatabase.getInstance().getReference().child("LIKES").child(key);
        likes_count.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren())
                {
                    int i = (int) dataSnapshot.getChildrenCount();
                    likescount.setText("  "+i+" Like");

                }
                else {
                    likescount.setText("   " +" Like");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void setSingleCommentData(final DatabaseReference comment_ref, final String comment_contain)
    {
    final String key = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference user_ref = FirebaseDatabase.getInstance().getReference().child("USERS").child(key);
    user_ref.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String name = dataSnapshot.child("NAME").getValue().toString();
            String image =dataSnapshot.child("PROFILE_PICTURE").getValue().toString();
            Map setUserData_inCommet = new HashMap();
            setUserData_inCommet.put("USER_NAME",name);
            setUserData_inCommet.put("USER_IMAGE",image);
            setUserData_inCommet.put("COMMENT",comment_contain);
            setUserData_inCommet.put("COMMENT_TIME",ServerValue.TIMESTAMP);

            comment_ref.setValue(setUserData_inCommet);


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
}
    public void getComments(String key, final TextView commentsCount)
    {
    DatabaseReference post_comment_ref = FirebaseDatabase.getInstance().getReference().child("COMMENTS").child(key);
    Query query = post_comment_ref.orderByChild("COMMENT");
    query.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (!dataSnapshot.hasChildren())
            {
                commentsCount.setText("  "+" Comments");
            }
            else
            {
                int count = (int) dataSnapshot.getChildrenCount();
                commentsCount.setText("  "+count+" Comments");
            }


        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });




}
    public void sendComments (final String key)
    {
    final List<CommentsData_Model> SINGLE_MODEL = new ArrayList<>();
    Comments_Adapter adapter = new Comments_Adapter(ctx,SINGLE_MODEL);
    AlertDialog.Builder MYDIALOG = new AlertDialog.Builder(ctx);
    LayoutInflater COMMENTS_INFLATER = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View COMMENTS_VIEW = COMMENTS_INFLATER.inflate(R.layout.comments_layout, null);
    MYDIALOG.setView(COMMENTS_VIEW);
    final EditText COMMENT_TEXT = COMMENTS_VIEW.findViewById(R.id.new_comment);
    Button COMMENT_SEND = COMMENTS_VIEW.findViewById(R.id.sendcommentbtn);
    RecyclerView COMMENTS_RECYCLER = COMMENTS_VIEW.findViewById(R.id.comments_view);
    COMMENTS_RECYCLER.setHasFixedSize(true);
    COMMENTS_RECYCLER.setLayoutManager(new LinearLayoutManager(ctx));
    DatabaseReference commentsref = FirebaseDatabase.getInstance().getReference().child("COMMENTS").child(key);
    commentsref.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            CommentsData_Model model = dataSnapshot.getValue(CommentsData_Model.class);
            SINGLE_MODEL.add(model);
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
    COMMENTS_RECYCLER.setAdapter(adapter);
    COMMENTS_RECYCLER.scrollToPosition(SINGLE_MODEL.size()-1);
    adapter.notifyDataSetChanged();

    final AlertDialog COMMENT_DIALOG = MYDIALOG.create();
    COMMENT_DIALOG.getWindow().getAttributes().windowAnimations=R.style.DialogSlide;
    COMMENT_DIALOG.show();

    final DatabaseReference comments = FirebaseDatabase.getInstance().getReference().child("COMMENTS").child(key);

    COMMENT_SEND.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (COMMENT_TEXT.getText().toString().isEmpty())
            {
             Toast.makeText(ctx,"Enter a comment First",Toast.LENGTH_LONG).show();
            }
            else {
                setSingleCommentData(comments.push(), COMMENT_TEXT.getText().toString());
                COMMENT_TEXT.setText("");
            }
        }
    });


}
    public void getCurrentImage(final Context ctx, final ImageView img, final String id)
    {
    String UID=FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference CURRENT_REF=FirebaseDatabase.getInstance().getReference().child("USERS").child(UID);
    final DatabaseReference POSTS_REF=FirebaseDatabase.getInstance().getReference().child("POSTS").child(id);
    CURRENT_REF.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            String image=dataSnapshot.child("PROFILE_PICTURE").getValue().toString();
            POSTS_REF.child("User_Picture").setValue(image);
            Picasso.with(ctx).load(image).into(img);

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
}

}