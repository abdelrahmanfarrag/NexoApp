package com.example.mana.nexo_app.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mana.nexo_app.Interfaces.postsview;
import com.example.mana.nexo_app.Models.PostData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.posts_presenter;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by MANA on 1/14/2018.
 */

public class newPostFragment extends Fragment implements View.OnClickListener,postsview {
    private EditText posthead,postdesc;
    private Button addpost,addimg;
    posts_presenter posts_presenter;
private String head,desc;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.newpostfragment,container,false);
        posthead = v.findViewById(R.id.addpost_posthead);
        postdesc = v.findViewById(R.id.addpost_postdesc);
        addpost = v.findViewById(R.id.addpost_btn);
        addpost.setOnClickListener(this);
        addimg = v.findViewById(R.id.addimage);
        addimg.setOnClickListener(this);
         head = posthead.getText().toString();
         desc = postdesc.getText().toString();
        posts_presenter = new posts_presenter(getActivity(),this);

        return v;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.addpost_btn) {

            head = posthead.getText().toString();
            desc = postdesc.getText().toString();
            if (head.isEmpty() || desc.isEmpty())
            {
                Toast.makeText(getActivity(),"Fill texts first",Toast.LENGTH_LONG).show();
            }
            else {
                posts_presenter.add_posts(getActivity(), head, desc, "");
            }
        }
        else if (view.getId()==R.id.addimage)
        {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(i,"Choose image"),1000);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000&&resultCode==RESULT_OK)
        {
           Uri img = data.getData();
            head = posthead.getText().toString();
            desc = postdesc.getText().toString();
            if (!desc.isEmpty()||!head.isEmpty()) {
                posts_presenter.addimage(getActivity(), head, desc, img);
            }
            else {
                Toast.makeText(getActivity(),"Fill texts first",Toast.LENGTH_LONG).show();
            }


        }
    }

    @Override
    public void loadposts(List<PostData_Model> model) {

    }
}
