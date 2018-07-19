package com.hr.musicktv.common;


import android.os.Parcel;
import android.os.Parcelable;

/*
 * lv   2018/7/16 传送id
 */
public class Iddddd implements Parcelable {
    private String id;
    private String eId;//加密的

    public Iddddd(String id, String eId) {
        this.id = id;
        this.eId = eId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.eId);
    }

    protected Iddddd(Parcel in) {
        this.id = in.readString();
        this.eId = in.readString();
    }

    public static final Parcelable.Creator<Iddddd> CREATOR = new Parcelable.Creator<Iddddd>() {
        @Override
        public Iddddd createFromParcel(Parcel source) {
            return new Iddddd(source);
        }

        @Override
        public Iddddd[] newArray(int size) {
            return new Iddddd[size];
        }
    };
}
