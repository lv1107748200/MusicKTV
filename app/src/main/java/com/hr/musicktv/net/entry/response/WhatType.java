package com.hr.musicktv.net.entry.response;

import android.os.Parcel;
import android.os.Parcelable;




/**
 * lv
 */

public class WhatType   {

    /**
     * PID : 0,1,3
     * ClassName : 经典
     * Taxis :
     * IsIndex : true
     * Path : 0,1,3,31
     * AltLink :
     * ID :
     */

    private String PID;
    private String ClassName;
    private String Taxis;
    private boolean IsIndex;
    private String Path;
    private String AltLink;
    private String ID;


    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String ClassName) {
        this.ClassName = ClassName;
    }

    public String getTaxis() {
        return Taxis;
    }

    public void setTaxis(String Taxis) {
        this.Taxis = Taxis;
    }

    public boolean isIsIndex() {
        return IsIndex;
    }

    public void setIsIndex(boolean IsIndex) {
        this.IsIndex = IsIndex;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String Path) {
        this.Path = Path;
    }

    public String getAltLink() {
        return AltLink;
    }

    public void setAltLink(String AltLink) {
        this.AltLink = AltLink;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
