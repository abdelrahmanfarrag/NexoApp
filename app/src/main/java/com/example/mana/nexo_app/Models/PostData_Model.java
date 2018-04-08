package com.example.mana.nexo_app.Models;

/**
 * Created by MANA on 1/15/2018.
 */

public class PostData_Model {
   private String ID,Post_Image,Topic_Desc,Topic_Head,User_Name,User_Picture;
    private long Post_Time;

    public PostData_Model() {
    }

    public PostData_Model(String ID,String Post_Image, String topic_Desc, String topic_Head, String user_Name, String user_Picture, long post_Time) {
        this.Post_Image = Post_Image;
        Topic_Desc = topic_Desc;
        Topic_Head = topic_Head;
        User_Name = user_Name;
        User_Picture = user_Picture;
        this.ID=ID;
    }

    public String getPost_IMAGE() {
        return Post_Image;
    }

    public void setPost_IMAGE(String post_IMAGE) {
        Post_Image = post_IMAGE;
    }

    public String getTopic_Desc() {
        return Topic_Desc;
    }

    public void setTopic_Desc(String topic_Desc) {
        Topic_Desc = topic_Desc;
    }

    public String getTopic_Head() {
        return Topic_Head;
    }

    public void setTopic_Head(String topic_Head) {
        Topic_Head = topic_Head;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getUser_Picture() {
        return User_Picture;
    }

    public void setUser_Picture(String user_Picture) {
        User_Picture = user_Picture;
    }

    public long getPost_Time() {
        return Post_Time;
    }

    public void setPost_Time(long post_Time) {
        Post_Time = post_Time;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
