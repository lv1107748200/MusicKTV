package com.hr.musicktv.net.entry.response;


/*
 * lv   2018/7/16
 */
public class VideoServer {

    /**
     * Status : 0
     * Info :
     * IsMp4Available : true
     */

    private int Status;
    private String Info;
    private boolean IsMp4Available;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public String getInfo() {
        return Info;
    }

    public void setInfo(String Info) {
        this.Info = Info;
    }

    public boolean isIsMp4Available() {
        return IsMp4Available;
    }

    public void setIsMp4Available(boolean IsMp4Available) {
        this.IsMp4Available = IsMp4Available;
    }
}
