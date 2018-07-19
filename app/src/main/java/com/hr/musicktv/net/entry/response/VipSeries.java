package com.hr.musicktv.net.entry.response;


import android.os.Parcel;
import android.os.Parcelable;

/*
 * lv   2018/7/16
 */
public class VipSeries implements Parcelable {
    /**
     * ID :
     * Key : RNwe5MJfB%2fo%3d
     * Name : 720P
     * UpdateDate : 2018-06-06T14:22:00
     * IsBought : false
     * IsNew : false
     */

    private String ID;
    private String Key;
    private String Name;
    private String UpdateDate;
    private boolean IsBought;
    private boolean IsNew;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String Key) {
        this.Key = Key;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(String UpdateDate) {
        this.UpdateDate = UpdateDate;
    }

    public boolean isIsBought() {
        return IsBought;
    }

    public void setIsBought(boolean IsBought) {
        this.IsBought = IsBought;
    }

    public boolean isIsNew() {
        return IsNew;
    }

    public void setIsNew(boolean IsNew) {
        this.IsNew = IsNew;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.Key);
        dest.writeString(this.Name);
        dest.writeString(this.UpdateDate);
        dest.writeByte(this.IsBought ? (byte) 1 : (byte) 0);
        dest.writeByte(this.IsNew ? (byte) 1 : (byte) 0);
    }

    public VipSeries() {
    }

    protected VipSeries(Parcel in) {
        this.ID = in.readString();
        this.Key = in.readString();
        this.Name = in.readString();
        this.UpdateDate = in.readString();
        this.IsBought = in.readByte() != 0;
        this.IsNew = in.readByte() != 0;
    }

    public static final Parcelable.Creator<VipSeries> CREATOR = new Parcelable.Creator<VipSeries>() {
        @Override
        public VipSeries createFromParcel(Parcel source) {
            return new VipSeries(source);
        }

        @Override
        public VipSeries[] newArray(int size) {
            return new VipSeries[size];
        }
    };
}
