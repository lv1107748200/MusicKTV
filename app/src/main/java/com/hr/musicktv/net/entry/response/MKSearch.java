package com.hr.musicktv.net.entry.response;


import com.hr.musicktv.common.BaseMusicData;

/*
 * lv   2018/7/26
 */
public class MKSearch extends BaseMusicData{

    /**
     * Singer :
     * Language :
     * Style :
     * Title : 曲1
     * VisiableStatus :
     * ShareCount :
     * ID :
     */

    private String Singer;
    private String Language;
    private String Style;
    private String Title;
    private String VisiableStatus;
    private String ShareCount;
    private String ID;

    public String getSinger() {
        return Singer;
    }

    public void setSinger(String Singer) {
        this.Singer = Singer;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public String getStyle() {
        return Style;
    }

    public void setStyle(String Style) {
        this.Style = Style;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getVisiableStatus() {
        return VisiableStatus;
    }

    public void setVisiableStatus(String VisiableStatus) {
        this.VisiableStatus = VisiableStatus;
    }

    public String getShareCount() {
        return ShareCount;
    }

    public void setShareCount(String ShareCount) {
        this.ShareCount = ShareCount;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
