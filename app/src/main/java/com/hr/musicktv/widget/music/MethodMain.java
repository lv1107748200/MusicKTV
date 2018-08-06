package com.hr.musicktv.widget.music;


import com.hr.musicktv.net.entry.response.MKSearch;

/*
 * lv   2018/7/31
 */
public interface MethodMain {
    void change(MKSearch mkSearch);
    MKSearch getMK();
}
