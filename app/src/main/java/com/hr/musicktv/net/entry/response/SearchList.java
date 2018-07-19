package com.hr.musicktv.net.entry.response;


import java.util.List;

/*
 * lv   2018/7/12  22查询收藏夹
 */
public class SearchList {
    private int recordcount;
    private List<WhatList> result;

    public int getRecordcount() {
        return recordcount;
    }

    public void setRecordcount(int recordcount) {
        this.recordcount = recordcount;
    }

    public List<WhatList> getResult() {
        return result;
    }

    public void setResult(List<WhatList> result) {
        this.result = result;
    }
}
