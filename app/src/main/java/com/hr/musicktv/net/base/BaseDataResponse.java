package com.hr.musicktv.net.base;

import java.util.List;

public class BaseDataResponse<T> {
    private int code;
    private String msg;
    private List<T> info;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getInfo() {
        return info;
    }

    public void setInfo(List<T> info) {
        this.info = info;
    }
}
