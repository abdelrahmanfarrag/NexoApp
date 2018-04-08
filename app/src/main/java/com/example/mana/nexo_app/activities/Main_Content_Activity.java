package com.example.mana.nexo_app.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mana.nexo_app.Adapters.Profile_Adapter;
import com.example.mana.nexo_app.Fragments.FriendsFragment;
import com.example.mana.nexo_app.Fragments.MoviesFragment;
import com.example.mana.nexo_app.Fragments.OwnProfileFragment;
import com.example.mana.nexo_app.Fragments.PostsFragment;
import com.example.mana.nexo_app.Fragments.RequestFragment;
import com.example.mana.nexo_app.Fragments.SearchResultFragment;
import com.example.mana.nexo_app.Fragments.ShopFragment;
import com.example.mana.nexo_app.Fragments.VideoFragment;
import com.example.mana.nexo_app.Interfaces.profileview;
import com.example.mana.nexo_app.Models.FriendData_Model;
import com.example.mana.nexo_app.Models.SearchData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.Shop_Presneter;
import com.example.mana.nexo_app.presenter.profile_presenter;
import com.example.mana.nexo_app.utils.ApplicationContext;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.CallClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.graphics.Color.*;

public class Main_Content_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
,profileview {

    private DrawerLayout draweer;
    private ActionBarDrawerToggle TOGGLE;
    private Toolbar toolbar;
    private NavigationView n_view;
    private FrameLayout container;
    private DatabaseReference users_ref;
    private TextView title;
    private SearchView search;
    public profile_presenter profile_presenter;
    private RecyclerView list;
    PostsFragment posts;
    private FrameLayout frameLayout;
    private static final String APPLICATION_KEY="6107d3bd-f1df-4554-8c46-02ce075b37bc";
    private static final String APPLICATION_SECRET ="DRh+O6FNR0OvC3fN/pV/hw==";
    private static final String HOST_NAME = "clientapi.sinch.com";
    int REQUEST_CODE_ASK_PERMISSIONS = 123;
    Shop_Presneter presneter;
    View myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__content_);
presneter = new Shop_Presneter(getApplicationContext(),null);

        String id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        users_ref = FirebaseDatabase.getInstance().getReference().child("USERS").child(id);
        frameLayout=findViewById(R.id.fragment_container);
        profile_presenter = new profile_presenter(this,getApplicationContext());
        list=findViewById(R.id.rlist);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setHasFixedSize(true);
        posts=new PostsFragment();
        draweer = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.mybar);
        title = toolbar.findViewById(R.id.project_title);
        n_view = findViewById(R.id.main_navigation);
        container = findViewById(R.id.fragment_container);
        n_view.setNavigationItemSelectedListener(this);
        setSupportActionBar(toolbar);
        TOGGLE =new ActionBarDrawerToggle(this,draweer,R.string.open,R.string.close);
        draweer.addDrawerListener(TOGGLE);
        TOGGLE.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setTitle("");
        users_ref.child("ONLINE").setValue("ONLINE");
        PostsFragment postsFragment = new PostsFragment();
        change_fragment(R.id.fragment_container,postsFragment);
        myView = n_view.inflateHeaderView(R.layout.navigation_header);
       final  TextView tv = myView.findViewById(R.id.text_user_name);
       final   CircleImageView img = myView.findViewById(R.id.user_image_1);
       String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
       DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("USERS").child(uid);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image = dataSnapshot.child("NAME").getValue().toString();
                String picture = dataSnapshot.child("PROFILE_PICTURE").getValue().toString();
                tv.setText(image);
                if (picture.equals("DEFAULT"))
                {
                    Picasso.with(getApplicationContext()).load(picture).into(img);
                }
                else
                {
                    Picasso.with(getApplicationContext()).load(R.mipmap.default_pp).into(img);}

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        if (savedInstanceState==null)
        {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        users_ref.child("ONLINE").setValue("ONLINE");
    }

    @Override
    protected void onResume() {
        super.onResume();

    }



    @Override
    protected void onStop() {
        super.onStop();
        users_ref.child("ONLINE").setValue(ServerValue.TIMESTAMP);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (TOGGLE.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item =menu.findItem(R.id.action_search);
         search = (SearchView) item.getActionView();

       search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

            SearchResultFragment result=new SearchResultFragment();
            change_fragment(R.id.fragment_container,result);
            Bundle b=new Bundle();
            b.putString("DATA",query);
            result.setArguments(b);
            title.setText("RESULTS");
            return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.friends)
        {
            FriendsFragment f=new FriendsFragment();
            change_fragment(R.id.fragment_container,f);
        }
        else if (item.getItemId()==R.id.requests)
        {
            RequestFragment fragment=new RequestFragment();
            change_fragment(R.id.fragment_container,fragment);
        }
        else if (item.getItemId()==R.id.settings)
        {
           OwnProfileFragment myprofile = new OwnProfileFragment();
           change_fragment(R.id.fragment_container,myprofile);

        }
        else if (item.getItemId()==R.id.movies)
        {
            MoviesFragment Mfragment=new MoviesFragment();
            change_fragment(R.id.fragment_container,Mfragment);
        }
        else if (item.getItemId()==R.id.news)
        {
            ShopFragment shop = new ShopFragment();
            change_fragment(R.id.fragment_container,shop);
            PermissionCheck();
        }
        else if (item.getItemId()==R.id.posts)
        {

            change_fragment(R.id.fragment_container,posts);
            title.setText("POSTS");

        }
        else if (item.getItemId()==R.id.save)
        {
            VideoFragment serie = new VideoFragment();
            change_fragment(R.id.fragment_container,serie);

        }
        else if (item.getItemId()==R.id.log_out)
        {
            FirebaseAuth logout_auth = FirebaseAuth.getInstance();
            logout_auth.signOut();
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }


        return false;
    }
    private void change_fragment(int v,Fragment c)
    {

        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction frag_transaction = manager.beginTransaction();
        frag_transaction.replace(v,c);
        frag_transaction.commit();
        draweer.closeDrawers();
        list.setVisibility(View.GONE);
    }



    @Override
    public void SetSearchResult(List<SearchData_Model> model) {


    }

    @Override
    public void SetFriendResult(List<FriendData_Model> models) {

    }
    public void PermissionCheck(){

            if ( Build.VERSION.SDK_INT >= 23){
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED  ){
                    requestPermissions(new String[]{
                                    android.Manifest.permission.ACCESS_FINE_LOCATION},
                            123);
                    return ;
                }
               // presneter.getLocation();
            }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 123:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(),"GRANTED ",Toast.LENGTH_LONG).show();
                presneter.getLocation();
                }
                else {
                    // Permission Denied
                    Toast.makeText( this,"PERMISSION NOT GRANTED !" , Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
