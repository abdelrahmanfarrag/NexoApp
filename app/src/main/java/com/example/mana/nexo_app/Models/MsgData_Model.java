package com.example.mana.nexo_app.Models;

/**
 * Created by MANA on 2/5/2018.
 */

public class MsgData_Model {
    private String MSG,ID;
    private long SENT;

    public MsgData_Model() {
    }

    public MsgData_Model(String MSG,String ID ,long SENT) {
        this.MSG = MSG;
        this.SENT = SENT;
        this.ID=ID;
    }

    public String getMSG() {
        return MSG;
    }

    public void setMSG(String MSG) {
        this.MSG = MSG;
    }

    public long getSENT() {
        return SENT;
    }

    public void setSENT(long SENT) {
        this.SENT = SENT;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
