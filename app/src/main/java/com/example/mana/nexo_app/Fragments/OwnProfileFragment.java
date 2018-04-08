package com.example.mana.nexo_app.Fragments;



import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.posts_presenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;



public class OwnProfileFragment extends Fragment implements View.OnClickListener {
    private ImageView coverphoto;
    private StorageReference USER_COVER_PHOTOS;
    private CircleImageView profilephoto;
    private DatabaseReference USER_INFO,SET_USER_INFO;
    private String current_user_id;
    private TextView USER_WORK,USER_HOME,USER_STATUS,USER_NAME;
    private static final int GAL_CODE = 1000;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         v = inflater.inflate(R.layout.ownprofile_fragment, container, false);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        Toast.makeText(getActivity(),user.getUid().toString(),Toast.LENGTH_LONG).show();
        current_user_id=user.getUid();
        USER_INFO= FirebaseDatabase.getInstance().getReference().child("USERS").child(current_user_id);
        SET_USER_INFO =  FirebaseDatabase.getInstance().getReference().child("USERS").child(current_user_id);
        coverphoto = v.findViewById(R.id.ownuser_cover_photo);
        profilephoto =v.findViewById(R.id.ownuser_profile_photo);
        USER_NAME = v.findViewById(R.id.ownuser_name);
        USER_HOME = v.findViewById(R.id.ownuser_home);
        USER_HOME.setOnClickListener(this);
        USER_WORK = v.findViewById(R.id.ownuser_work);
        USER_WORK.setOnClickListener(this);
        USER_STATUS = v.findViewById(R.id.ownuser_status);
        USER_STATUS.setOnClickListener(this);
        coverphoto.setOnClickListener(this);
        profilephoto.setOnClickListener(this);
        setUSERINFO();
        return v;

    }


    @Override
    public
    void onClick(View view) {
        switch (view.getId())
        {
            case R.id.ownuser_cover_photo:
                gallery_open("COVERS","COVER_PICTURE");
                break;
            case R.id.ownuser_profile_photo:
                gallery_open("PROFILES","PROFILE_PICTURE");
                break;
            case R.id.ownuser_home:
                update_info("WHERE YOU LIVING ?","LIVES");
                break;
            case R.id.ownuser_work:
                update_info("WHERE YOU WORKING ?","WORK");
                break;
            case R.id.ownuser_status:
                update_info("SET YOUR STATUS","STATUS");
                break;
        }


    }
    private void gallery_open(String storage_path,String database_path)
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        USER_COVER_PHOTOS = FirebaseStorage.getInstance().getReference().child(storage_path).child(current_user_id);
        USER_INFO= FirebaseDatabase.getInstance().getReference().child("USERS").child(current_user_id).child(database_path);
        startActivityForResult(Intent.createChooser(i,"Choose image"),GAL_CODE);
    }

    private void update_info(String set_hint, final String info_type)
    {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update_info_adialog, null);
        Button b = dialogView.findViewById(R.id.button);
        final EditText editText = dialogView.findViewById(R.id.new_info);
        dialogBuilder.setView(dialogView);
        editText.setHint(set_hint);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String update_data = editText.getText().toString();
                SET_USER_INFO.child(info_type).setValue(update_data);
                Toast.makeText(getContext(),info_type+" UPDATED",Toast.LENGTH_LONG).show();
                alertDialog.dismiss();

            }
        });
    }
    private void setUSERINFO()
    {
        SET_USER_INFO.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child("NAME").getValue().toString();
                String status = dataSnapshot.child("STATUS").getValue().toString();
                String work = dataSnapshot.child("WORK").getValue().toString();
                String home = dataSnapshot.child("LIVES").getValue().toString();
                String cover_pic = dataSnapshot.child("COVER_PICTURE").getValue().toString();
                String profile_pic = dataSnapshot.child("PROFILE_PICTURE").getValue().toString();
                Picasso.with(getActivity()).load(cover_pic).placeholder(R.mipmap.defualt_cover).resize(360, 200).into(coverphoto);
                Picasso.with(getActivity()).load(profile_pic).placeholder(R.mipmap.default_pp).into(profilephoto);
                USER_NAME.setText(name);
                USER_HOME.setText(home);
                USER_WORK.setText(work);
                USER_STATUS.setText(status);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GAL_CODE && resultCode == RESULT_OK)
        {
            Uri img = data.getData();
            CropImage.activity(img).setAspectRatio(3,3).start(getContext(),this);

        }
        try {if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    StorageReference put_image = USER_COVER_PHOTOS.child(resultUri.getLastPathSegment());
                        put_image.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()) {
                                    final String img_uri = task.getResult().getDownloadUrl().toString();
                                    USER_INFO.setValue(img_uri).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                       if (task.isSuccessful())
                                       {
                                           Toast.makeText(getActivity(),"UPDATED !",Toast.LENGTH_LONG).show();
                                       }
                                        }
                                    });
                                }}
                        });}
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();}}
        }catch (Exception e)
        {

            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }


}
