package com.example.mana.nexo_app.Models;

/**
 * Created by MANA on 3/4/2018.
 */

public class ShopData_Model {
private String IMAGE,ITEM_IMAGE,LOCATION,NAME,PRICE,USER_ID,DETAILS;
private long TIME;

    public ShopData_Model() {
    }

    public ShopData_Model(String IMAGE, String ITEM_IMAGE, String LOCATION, String NAME, String PRICE, String USER_ID, long TIME,String DETAILS) {
        this.IMAGE = IMAGE;
        this.ITEM_IMAGE = ITEM_IMAGE;
        this.LOCATION = LOCATION;
        this.NAME = NAME;
        this.PRICE = PRICE;
        this.USER_ID = USER_ID;
        this.TIME = TIME;
        this.DETAILS=DETAILS;
    }

    public String getIMAGE() {
        return IMAGE;
    }

    public void setIMAGE(String IMAGE) {
        this.IMAGE = IMAGE;
    }

    public String getLOCATION() {
        return LOCATION;
    }

    public void setLOCATION(String LOCATION) {
        this.LOCATION = LOCATION;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID;
    }

    public long getTIME() {
        return TIME;
    }

    public void setTIME(long TIME) {
        this.TIME = TIME;
    }

    public String getITEM_IMAGE() {
        return ITEM_IMAGE;
    }

    public void setITEM_IMAGE(String ITEM_IMAGE) {
        this.ITEM_IMAGE = ITEM_IMAGE;
    }

    public String getDETAILS() {
        return DETAILS;
    }

    public void setDETAILS(String DETAILS) {
        this.DETAILS = DETAILS;
    }
}
