package com.example.mana.nexo_app.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.mana.nexo_app.Fragments.SignInFragment;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.activities.Main_Content_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FrameLayout frame;
    private FirebaseUser check_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frame = findViewById(R.id.MainActivity_Frame);
        SignInFragment signin = new SignInFragment();
        FragmentManager sign_manager = getFragmentManager();
        FragmentTransaction sign_Transaction = sign_manager.beginTransaction();
        sign_Transaction.replace(R.id.MainActivity_Frame,signin);
        sign_Transaction.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        check_user = FirebaseAuth.getInstance().getCurrentUser();
        if (check_user!=null)
        {
            Intent i = new Intent(getApplicationContext(),Main_Content_Activity.class);
            startActivity(i);
            finish();
        }
        else
        {
            Toast.makeText(getApplicationContext(),"notuser",Toast.LENGTH_LONG).show();
        }
    }
}
