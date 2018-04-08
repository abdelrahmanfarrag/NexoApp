package com.example.mana.nexo_app.Interfaces;

import com.example.mana.nexo_app.Models.FriendData_Model;
import com.example.mana.nexo_app.Models.SearchData_Model;
import com.google.firebase.database.Query;

import java.util.List;

/**
 * Created by MANA on 1/22/2018.
 */

public interface profileview {
void SetSearchResult(List<SearchData_Model> model);
void SetFriendResult(List<FriendData_Model>models);
}
