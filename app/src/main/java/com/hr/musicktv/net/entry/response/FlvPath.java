package com.hr.musicktv.net.entry.response;


/*
 * lv   2018/7/16
 */
public class FlvPath {

    /**
     * IsHls : false
     * Result : http://server5.dnvod.tv/live/dz-tzr-720p-01044BFBF-hd.mp4?sourceIp=123.149.76.165&signature=d9cd42a018084c32a2be072f87d51492.934a37585866218806793708f92febee&start=1531452607.50572&custom=0&ua=26471d51f77914e2da4957846d94b196
     * Validator :
     * Type : 0
     * Link :
     */

    private boolean IsHls;
    private String Result;
    private String Validator;
    private int Type;
    private String Link;

    public boolean isIsHls() {
        return IsHls;
    }

    public void setIsHls(boolean IsHls) {
        this.IsHls = IsHls;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String Result) {
        this.Result = Result;
    }

    public String getValidator() {
        return Validator;
    }

    public void setValidator(String Validator) {
        this.Validator = Validator;
    }

    public int getType() {
        return Type;
    }

    public void setType(int Type) {
        this.Type = Type;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String Link) {
        this.Link = Link;
    }
}
