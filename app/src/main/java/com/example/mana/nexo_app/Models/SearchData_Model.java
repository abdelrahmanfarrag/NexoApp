package com.example.mana.nexo_app.Models;

/**
 * Created by MANA on 1/23/2018.
 */
public class SearchData_Model {
private String NAME,PROFILE_PICTURE,STATUS,ID,LIVES;

    public SearchData_Model() {
    }

    public SearchData_Model(String ID,String NAME, String PROFILE_PICTURE, String STATUS,String LIVES) {
        this.NAME = NAME;
        this.PROFILE_PICTURE = PROFILE_PICTURE;
        this.STATUS = STATUS;
        this.ID=ID;
        this.LIVES=LIVES;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPROFILE_PICTURE() {
        return PROFILE_PICTURE;
    }

    public void setPROFILE_PICTURE(String PROFILE_PICTURE) {
        this.PROFILE_PICTURE = PROFILE_PICTURE;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLIVES() {
        return LIVES;
    }

    public void setLIVES(String LIVES) {
        this.LIVES = LIVES;
    }
}
