package com.example.mana.nexo_app.Models;

/**
 * Created by MANA on 1/20/2018.
 */

public class CommentsData_Model {
    private String COMMENT,USER_IMAGE,USER_NAME;
    private long COMMENT_TIME;

    public CommentsData_Model() {
    }

    public CommentsData_Model(String COMMENT, String USER_IMAGE, String USER_NAME, long COMMENT_TIME) {
        this.COMMENT = COMMENT;
        this.USER_IMAGE = USER_IMAGE;
        this.USER_NAME = USER_NAME;
        this.COMMENT_TIME = COMMENT_TIME;
    }

    public String getCOMMENT() {
        return COMMENT;
    }

    public void setCOMMENT(String COMMENT) {
        this.COMMENT = COMMENT;
    }

    public String getUSER_IMAGE() {
        return USER_IMAGE;
    }

    public void setUSER_IMAGE(String USER_IMAGE) {
        this.USER_IMAGE = USER_IMAGE;
    }

    public String getUSER_NAME() {
        return USER_NAME;
    }

    public void setUSER_NAME(String USER_NAME) {
        this.USER_NAME = USER_NAME;
    }

    public long getCOMMENT_TIME() {
        return COMMENT_TIME;
    }

    public void setCOMMENT_TIME(long COMMENT_TIME) {
        this.COMMENT_TIME = COMMENT_TIME;
    }
}
