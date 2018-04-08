package com.example.mana.nexo_app.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mana.nexo_app.Interfaces.LoadIntent;
import com.example.mana.nexo_app.Models.ShopData_Model;
import com.example.mana.nexo_app.R;
import com.example.mana.nexo_app.presenter.Shop_Presneter;

import java.util.List;

/**
 * Created by MANA on 2/26/2018.
 */

public class add_merchant_fragment extends Fragment implements LoadIntent {
View v;
Shop_Presneter presneter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.add_merchant_frag,container,false);
        presneter = new Shop_Presneter(getActivity(),this);

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public void Loadintent(Intent i, int RESULT_CODE) {
        startActivityForResult(Intent.createChooser(i,"Choose image"),1000);
    }

    @Override
    public void LoadShopOffers(List<ShopData_Model> model) {

    }
}
