package com.example.mana.nexo_app.Interfaces;

import android.content.Intent;

import com.example.mana.nexo_app.Models.ShopData_Model;

import java.util.List;

/**
 * Created by MANA on 2/28/2018.
 */

public interface LoadIntent {
    void Loadintent(Intent i ,int RESULT_CODE);
    void LoadShopOffers(List<ShopData_Model> model);
}
