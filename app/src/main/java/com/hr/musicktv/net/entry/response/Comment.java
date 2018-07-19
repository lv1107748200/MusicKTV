package com.hr.musicktv.net.entry.response;


import java.util.List;

/*
 * lv   2018/7/16 评论
 */
public class Comment {

    private List<CommHot> CommHot;

    public List<com.hr.musicktv.net.entry.response.CommHot> getCommHot() {
        return CommHot;
    }

    public void setCommHot(List<com.hr.musicktv.net.entry.response.CommHot> commHot) {
        CommHot = commHot;
    }
}
