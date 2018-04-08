package com.example.mana.nexo_app.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mana.nexo_app.activities.Main_Content_Activity;
import com.example.mana.nexo_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by MANA on 1/8/2018.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{
    private EditText mail_text,username_text,password_text;
    private String mail,username,password;
    private FirebaseAuth create_user;
    private Button signup_button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.register_fragment,container,false);
        username_text = v.findViewById(R.id.register_editText_username);
        mail_text = v.findViewById(R.id.register_editText_mail);
        password_text = v.findViewById(R.id.register_editText_password);
        signup_button = v.findViewById(R.id.signup_button);
        signup_button.setOnClickListener(this);
        create_user = FirebaseAuth.getInstance();
        return v;

    }

    @Override
    public void onClick(View view) {
          switch (view.getId())
          {
              case R.id.signup_button:
                  mail=mail_text.getText().toString();
                  username=username_text.getText().toString();
                  password=password_text.getText().toString();
                  if (!mail.isEmpty()&&!password.isEmpty()&&!username.isEmpty())
                  {
                        create_new_user(username,mail,password);
                  }
                  else
                  {
                      // Notify User to fill texts first
                      final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                      LayoutInflater inflater = getActivity().getLayoutInflater();
                      View dialogView = inflater.inflate(R.layout.createaccountalert, null);
                      dialogBuilder.setView(dialogView);
                      Button b = dialogView.findViewById(R.id.do_work);
                      final TextView tv = dialogView.findViewById(R.id.alert_text);
                      final ImageView iv = dialogView.findViewById(R.id.alert_emoticons);
                      tv.setText("Ops , Fill Texts above first");
                      iv.setBackgroundResource(R.drawable.happyfece);
                      b.setText("Try Again !");
                      final AlertDialog alertDialog = dialogBuilder.create();
                      alertDialog.show();
                      b.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              alertDialog.dismiss();

                          }
                      });
                  }
                  break;
          }
    }

    private void create_new_user(final String username, String mail, String password) {
        create_user.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    user_info(username);
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.createaccountalert, null);
                    dialogBuilder.setView(dialogView);
                    Button b = dialogView.findViewById(R.id.do_work);
                    final TextView tv = dialogView.findViewById(R.id.alert_text);
                    final ImageView iv = dialogView.findViewById(R.id.alert_emoticons);
                    tv.setText("Account Created Succesffully");
                    iv.setBackgroundResource(R.drawable.happyface);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getActivity(),Main_Content_Activity.class);
                            startActivity(i);
                            getActivity().finish();
                            alertDialog.dismiss();

                        }
                    });





                }
                else
                {
                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.createaccountalert, null);
                    dialogBuilder.setView(dialogView);
                    Button b = dialogView.findViewById(R.id.do_work);
                    final TextView tv = dialogView.findViewById(R.id.alert_text);
                    final ImageView iv = dialogView.findViewById(R.id.alert_emoticons);
                    tv.setText("Ops , Can not Create Account !");
                    iv.setBackgroundResource(R.drawable.sadface);
                    b.setText("Try Again !");
                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            alertDialog.dismiss();

                        }
                    });
                }
            }
        });


    }
    private void user_info(String name)
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String user_id = user.getUid();
        DatabaseReference register_user_info = FirebaseDatabase.getInstance().getReference().child("USERS").child(user_id);
        String TOKEN= FirebaseInstanceId.getInstance().getToken();

        Map user_information = new HashMap<>();
        user_information.put("NAME",name);
        user_information.put("STATUS","Hello, Im using NeXo");
        user_information.put("COVER_PICTURE","DEFAULT");
        user_information.put("PROFILE_PICTURE","DEFAULT");
        user_information.put("WORK","DEFAULT");
        user_information.put("LIVES","SOMEWHERE");
        user_information.put("ID",user_id);
        user_information.put("ONLINE", "FALSE");
        user_information.put("DeviceToken",TOKEN);

        register_user_info.setValue(user_information);

    }


}
