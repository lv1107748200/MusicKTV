package com.hr.musicktv.common;


import java.util.List;

/*
 * lv   2018/7/17
 */
public class ListType {
    private String type;

    private List list;

    public ListType(String type, List list) {
        this.type = type;
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
