package com.hr.musicktv.widget.single;


import com.hr.musicktv.common.Iddddd;
import com.hr.musicktv.net.entry.response.Detail;
import com.hr.musicktv.net.entry.response.GuestSeries;
import com.hr.musicktv.net.entry.response.VipSeries;

import java.util.List;

/*
 * lv   2018/7/16
 */
public class CollectManger {
    volatile private static CollectManger instance = null;

    private static CollectManger getInstance(){
        if(instance == null){
            synchronized (CollectManger.class) {
                if(instance == null){
                    instance = new CollectManger();
                }
            }
        }
        return instance;
    }

    private CollectManger() {

    }
    private Detail detail;

    private List<GuestSeries> guestSeriesList;
    private List<VipSeries> VipSeriesList;
    private String PlayRecordURL;
    private Iddddd iddddd;

    public List<GuestSeries> getGuestSeriesList() {
        return guestSeriesList;
    }

    public void setGuestSeriesList(List<GuestSeries> guestSeriesList) {
        this.guestSeriesList = guestSeriesList;
    }

    public List<VipSeries> getVipSeriesList() {
        return VipSeriesList;
    }

    public void setVipSeriesList(List<VipSeries> vipSeriesList) {
        VipSeriesList = vipSeriesList;
    }

    public String getPlayRecordURL() {
        return PlayRecordURL;
    }

    public void setPlayRecordURL(String playRecordURL) {
        PlayRecordURL = playRecordURL;
    }

    public Iddddd getIddddd() {
        return iddddd;
    }

    public void setIddddd(Iddddd iddddd) {
        this.iddddd = iddddd;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }

    public static void Clear(){
        getInstance().guestSeriesList = null;
        getInstance().VipSeriesList = null;
        getInstance().PlayRecordURL = null;
        getInstance().iddddd = null;
        getInstance().detail = null;
        instance = null;
    }
}
