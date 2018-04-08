package com.example.mana.nexo_app.Fragments;

import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mana.nexo_app.Adapters.ShopOffers_Adapter;
import com.example.mana.nexo_app.Interfaces.LoadIntent;
import com.example.mana.nexo_app.Models.ShopData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.Shop_Presneter;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.List;

import io.github.yavski.fabspeeddial.FabSpeedDial;

import static android.app.Activity.RESULT_OK;

/**
 * Created by MANA on 2/26/2018.
 */

public class ShopFragment extends Fragment implements LoadIntent{
    Shop_Presneter sPreseneter;
    RecyclerView shopview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.shop_fragment,container,false);
        FabSpeedDial button = v.findViewById(R.id.add_merchais);
        sPreseneter = new Shop_Presneter(getActivity(),this);
        sPreseneter.LoadOffers();
        shopview= v.findViewById(R.id.shop_view);
        shopview.setLayoutManager(new LinearLayoutManager(getActivity()));
        shopview.setHasFixedSize(true);
        button.setMenuListener(new FabSpeedDial.MenuListener() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                return true;
            }

            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                if (menuItem.getItemId()==R.id.add_merchant) {
                    sPreseneter.addMerchant();
                }
                return true;
            }

            @Override
            public void onMenuClosed() {

            }
        });
        return v;
    }

    @Override
    public void Loadintent(Intent i, int RESULT_CODE) {
        startActivityForResult(Intent.createChooser(i,"Choose image"),RESULT_CODE);
    }

    @Override
    public void LoadShopOffers(List<ShopData_Model> model) {
        shopview.setAdapter(new ShopOffers_Adapter(getActivity(),model));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000 && resultCode==RESULT_OK)
        {
            Uri image = data.getData();
            sPreseneter.saveImage(image);


        }
    }
}
