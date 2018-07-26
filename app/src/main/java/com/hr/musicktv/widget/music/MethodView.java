package com.hr.musicktv.widget.music;


/*
 * lv   2018/7/25
 */
public interface MethodView {
    void rebroadcast();//重播
    void change();//切换
    void pause();//暂停
    void originalSinger();//原唱
    void accompany();//伴唱
    void tone(int is);//音调 0 升 1 降
    long getCurrentPosition();//当前播放时间
    long getTotalPosition();//总时间
    void songList();//歌单
    boolean getIsPlayer();//是否正在播放
    void vStart();//开始
    void select();//选中那一集
}
