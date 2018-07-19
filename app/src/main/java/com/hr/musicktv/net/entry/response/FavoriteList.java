package com.hr.musicktv.net.entry.response;


import java.util.List;

/*
 * lv   2018/7/16
 */
public class FavoriteList {

    private List<Result> result;
    private int  count;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
