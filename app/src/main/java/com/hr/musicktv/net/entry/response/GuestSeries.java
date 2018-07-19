package com.hr.musicktv.net.entry.response;


import android.os.Parcel;
import android.os.Parcelable;

/*
 * lv   2018/7/16
 */
public class GuestSeries implements Parcelable {

    /**
     * ID :
     * Key : IO%2fBz2Vsnfw%3d
     * Name : 480P
     * UpdateDate : 2018-06-06T14:29:00
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

    public GuestSeries() {
    }

    protected GuestSeries(Parcel in) {
        this.ID = in.readString();
        this.Key = in.readString();
        this.Name = in.readString();
        this.UpdateDate = in.readString();
        this.IsBought = in.readByte() != 0;
        this.IsNew = in.readByte() != 0;
    }

    public static final Parcelable.Creator<GuestSeries> CREATOR = new Parcelable.Creator<GuestSeries>() {
        @Override
        public GuestSeries createFromParcel(Parcel source) {
            return new GuestSeries(source);
        }

        @Override
        public GuestSeries[] newArray(int size) {
            return new GuestSeries[size];
        }
    };
}
