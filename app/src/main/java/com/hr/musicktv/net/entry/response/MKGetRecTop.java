package com.hr.musicktv.net.entry.response;


/*
 * lv   2018/7/26
 */
public class MKGetRecTop {

    /**
     * Title : 榜单1
     * Taxis :
     * IsShow : false
     * Description :
     * Imgpath : //static.dnvod.tv/upload/2018/banner1.jpg
     * IsAdult : false
     * Hit :
     * ID :
     */

    private String Title;
    private String Taxis;
    private boolean IsShow;
    private String Description;
    private String Imgpath;
    private boolean IsAdult;
    private String Hit;
    private String ID;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getTaxis() {
        return Taxis;
    }

    public void setTaxis(String Taxis) {
        this.Taxis = Taxis;
    }

    public boolean isIsShow() {
        return IsShow;
    }

    public void setIsShow(boolean IsShow) {
        this.IsShow = IsShow;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getImgpath() {
        return Imgpath;
    }

    public void setImgpath(String Imgpath) {
        this.Imgpath = Imgpath;
    }

    public boolean isIsAdult() {
        return IsAdult;
    }

    public void setIsAdult(boolean IsAdult) {
        this.IsAdult = IsAdult;
    }

    public String getHit() {
        return Hit;
    }

    public void setHit(String Hit) {
        this.Hit = Hit;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
