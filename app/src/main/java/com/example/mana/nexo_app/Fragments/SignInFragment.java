package com.example.mana.nexo_app.Fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by MANA on 1/8/2018.
 */

public class SignInFragment extends Fragment implements View.OnClickListener {
    private TextView sign_up;
    private Button sign_in;
    private EditText sign_in_mail,sign_in_password;
    private FirebaseAuth SIGN_IN_AUTH;
    private DatabaseReference userRef;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sign_in_fragment,container,false);
        SIGN_IN_AUTH = FirebaseAuth.getInstance();
        sign_up = v.findViewById(R.id.signup_text);
        sign_up.setOnClickListener(this);
        sign_in = v.findViewById(R.id.signin_button);
        sign_in.setOnClickListener(this);
        sign_in_mail = v.findViewById(R.id.login_editText_mail);
        sign_in_password = v.findViewById(R.id.login_editText_password);
        userRef= FirebaseDatabase.getInstance().getReference().child("USERS");
        return v;
    }

    @Override
    public void onClick(View view) {
    switch (view.getId())
    {
        case R.id.signup_text:
            RegisterFragment registerFragment = new RegisterFragment();
            FragmentManager registerManager = getFragmentManager();
            FragmentTransaction registerTransaction = registerManager.beginTransaction();
            registerTransaction.replace(R.id.MainActivity_Frame,registerFragment);
            registerTransaction.commit();
            break;
        case R.id.signin_button:
            String mail = sign_in_mail.getText().toString();
            String password = sign_in_password.getText().toString();
            if (!mail.isEmpty()&&!password.isEmpty()) {
                sign_in_process(mail, password);
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









default:
    break;
    }
    }

    private void sign_in_process(String mail, String password) {
        SIGN_IN_AUTH.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    String id=SIGN_IN_AUTH.getCurrentUser().getUid();
                 //   String TOKEN= FirebaseInstanceId.getInstance().getToken();
                   // userRef.child(id).child("DeviceToken").setValue(TOKEN);



                    Intent i = new Intent(getActivity(),Main_Content_Activity.class);
                    startActivity(i);
                    getActivity().finish();
                }
                else
                {
                    //Notify User Password or mail is wrong !

                    final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.createaccountalert, null);
                    dialogBuilder.setView(dialogView);
                    Button b = dialogView.findViewById(R.id.do_work);
                    final TextView tv = dialogView.findViewById(R.id.alert_text);
                    final ImageView iv = dialogView.findViewById(R.id.alert_emoticons);
                    tv.setText("Ops ,You Entered your mail or password incorrectly !");
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

            }
        });
    }

}
