package com.example.mana.nexo_app.presenter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mana.nexo_app.Fragments.add_merchant_fragment;
import com.example.mana.nexo_app.Interfaces.LoadIntent;
import com.example.mana.nexo_app.Models.ShopData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.activities.ChatActivity;
import com.example.mana.nexo_app.utils.ApplicationContext;
import com.example.mana.nexo_app.utils.GetTimeAgo;
import com.example.mana.nexo_app.utils.locationListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.security.acl.Permission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by MANA on 2/26/2018.
 */

    public class Shop_Presneter {
    Context ctx;
    private LoadIntent load;
    private List<ShopData_Model> list= new ArrayList<>();



    public Shop_Presneter(Context ctx,LoadIntent load) {
        this.ctx = ctx;
        this.load=load;
    }

    public void addMerchant() {
        AlertDialog.Builder build = new AlertDialog.Builder(ctx);
        LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.add_merchant_frag, null);
        Button LOCATION = v.findViewById(R.id.btn_add_location);
        build.setView(v);
        AlertDialog alert = build.create();
        alert.show();

        LOCATION.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocation();
            }
        });
        Button GALLERY = v.findViewById(R.id.btn_add_image);
        GALLERY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        final EditText Offer = v.findViewById(R.id.add_merchant_detail);
        final Button Submit = v.findViewById(R.id.shop_post_submit);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
String offer = Offer.getText().toString();
if (!TextUtils.isEmpty(offer))
{
    addToShp(offer);
}
else
{
    Toast.makeText(ctx,"Enter Desc First",Toast.LENGTH_SHORT).show();
}

            }
        });
        Button Price = v.findViewById(R.id.btn_add_price);
        Price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPrice();
            }
        });


    }
    public void getLocation() {
      final FusedLocationProviderClient client= LocationServices.getFusedLocationProviderClient(ctx);
      client.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
          @Override
          public void onSuccess(Location location) {
              if (location!=null)
              {
                  try {
                      getAddress(location.getLongitude(),location.getLatitude());
                      Toast.makeText(ctx,String.valueOf(location.getLongitude()+"\n"+String.valueOf(location.getLatitude())),Toast.LENGTH_SHORT)
                              .show();
                      String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                      DatabaseReference addloc = FirebaseDatabase.getInstance().getReference().child("TEMP").child(id);
                      Map map = new HashMap<>();
                      map.put("LONGITUTDE",location.getLongitude());
                      map.put("LATITUDE",location.getLatitude());
                      addloc.setValue(map);
                  } catch (IOException e) {
                      Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
                  }
              }
              else
              {
                  Toast.makeText(ctx,"NULL",Toast.LENGTH_SHORT).show();

              }
          }
      });


    }



    public void openGallery() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(android.content.Intent.ACTION_GET_CONTENT);
        load.Loadintent(i, 1000);
    }
    public void saveImage(Uri data)
    {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        StorageReference shop_image = FirebaseStorage.getInstance().getReference().child("SHOP_OFFER");

        shop_image.child(uid).child(data.getLastPathSegment()).putFile(data).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful())
                {
                    String download_uri = task.getResult().getDownloadUrl().toString();
                    String user_id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    DatabaseReference IMG_TEMP = FirebaseDatabase.getInstance().getReference().child("TEMP").child(user_id);
                    IMG_TEMP.child("IMAGE_URL").setValue(download_uri);

                }

            }
        });



    }
    public void setPrice()
    {
        AlertDialog.Builder build = new AlertDialog.Builder(ctx);
        LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.alert_price, null);
        build.setView(v);
        final EditText getPrice = v.findViewById(R.id.edit_getprice);
        RelativeLayout submitprice = v.findViewById(R.id.relat_click);

        final AlertDialog alert = build.create();
        alert.show();
        submitprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("TEMP").child(uid);
                dref.child("PRICE").setValue(getPrice.getText().toString());
                alert.dismiss();


            }

        });






    }
    private void addToShp(final String offer)
    {
        final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference dref = FirebaseDatabase.getInstance().getReference().child("TEMP").child(id);
        final DatabaseReference newShopItem = FirebaseDatabase.getInstance().getReference().child("SHOP");
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    double latitude = Double.parseDouble(dataSnapshot.child("LATITUDE").getValue().toString());
                    double longitiude = Double.parseDouble(dataSnapshot.child("LONGITUTDE").getValue().toString());

                    String img = dataSnapshot.child("IMAGE_URL").getValue().toString();
                    String price = dataSnapshot.child("PRICE").getValue().toString();
                    final Map setData = new HashMap<>();
                    try {
                        setData.put("LOCATION", getAddress(longitiude, latitude));
                        setData.put("ITEM_IMAGE", img);
                        setData.put("PRICE", price);
                        setData.put("TIME", ServerValue.TIMESTAMP);
                        setData.put("DETAILS",offer);
                        DatabaseReference user_ref = FirebaseDatabase.getInstance().getReference().child("USERS").child(id);
                        user_ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String image = dataSnapshot.child("PROFILE_PICTURE").getValue().toString();
                                String name = dataSnapshot.child("NAME").getValue().toString();
                                setData.put("NAME", name);
                                setData.put("IMAGE", image);
                                setData.put("USER_ID", id);
                                newShopItem.push().setValue(setData);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                else {

                }

            }

                @Override
                public void onCancelled (DatabaseError databaseError){

                }


        });



    }

    private String getAddress(double longitude,double latitude) throws IOException {
        Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());

        List<Address> addresses  = geocoder.getFromLocation(latitude,longitude, 1);
        String location = addresses.get(0).getAdminArea();
return location;

    }
    public void LoadOffers()
    {
        DatabaseReference offer_list = FirebaseDatabase.getInstance().getReference().child("SHOP");
        offer_list.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ShopData_Model model = dataSnapshot.getValue(ShopData_Model.class);
                list.add(model);
                load.LoadShopOffers(list);

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
    public void TransformTime(TextView gettimeAgo,long post_added)
    {
        GetTimeAgo timeAgo = new GetTimeAgo();
        String post_at =timeAgo.getTimeAgo(post_added,ctx);
        gettimeAgo.setText(post_at);
    }
public void intentToMsg(Context c,String id)
{
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    if (uid.equals(id))
    {
        Toast.makeText(ctx,"CAN NOT SEND MSG TO YOURSELF",Toast.LENGTH_LONG).show();
    }
    else {
        Intent i = new Intent(c, ChatActivity.class);
        i.putExtra("ID", id);
        c.startActivity(i);
    }
}








}
