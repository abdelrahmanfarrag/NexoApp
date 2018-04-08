package com.example.mana.nexo_app.Models;

/**
 * Created by MANA on 2/1/2018.
 */

public class RequestsData_Model {
    private long SENT_AT;
    private String KEY;

    public RequestsData_Model() {
    }

    public RequestsData_Model(long SENT_AT,String KEY) {
        this.SENT_AT = SENT_AT;
        this.KEY=KEY;
    }

    public long getSENT_AT() {
        return SENT_AT;
    }

    public void setSENT_AT(long SENT_AT) {
        this.SENT_AT = SENT_AT;
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }
}
