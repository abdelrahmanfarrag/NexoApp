package com.example.mana.nexo_app.Models;

import android.os.Parcelable;

/**
 * Created by MANA on 3/10/2018.
 */

public class Parcel implements Parcelable {
    private String name ;
    private String last;
    private int number;
    private boolean bool;


    public Parcel(android.os.Parcel in) {
        name=in.readString();
        last=in.readString();
        number=in.readInt();
        bool= (boolean) in.readValue(null);
    }

    public static final Creator<Parcel> CREATOR = new Creator<Parcel>() {
        @Override
        public Parcel createFromParcel(android.os.Parcel in) {
            return new Parcel(in);
        }

        @Override
        public Parcel[] newArray(int size) {
            return new Parcel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(last);
        parcel.writeInt(number);
        parcel.writeValue(bool);
    }
}
