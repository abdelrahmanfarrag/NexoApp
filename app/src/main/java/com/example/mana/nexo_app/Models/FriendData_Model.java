package com.example.mana.nexo_app.Models;

/**
 * Created by MANA on 1/26/2018.
 */

public class FriendData_Model {
    private String pp,NAME,ID;
    private long SINCE;

    public FriendData_Model() {
    }

    public FriendData_Model(long SINCE, String pp, String NAME,String ID) {
        this.SINCE = SINCE;
        this.pp = pp;
        this.NAME = NAME;
        this.ID=ID;
    }

    public long getSINCE() {
        return SINCE;
    }

    public void setSINCE(long SINCE) {
        this.SINCE = SINCE;
    }

    public String getPP() {
        return pp;
    }

    public void setPP(String pp) {
        this.pp = pp;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
