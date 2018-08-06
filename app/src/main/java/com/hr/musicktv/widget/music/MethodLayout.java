package com.hr.musicktv.widget.music;




/*
 * lv   2018/7/25
 */
public interface MethodLayout {
    void start();//开始
    void stop();//停止
    void change();//切换
    void release();//释放
    void speed();//快进
    void fastReverse();//快退
    void rebroadcast();//重播
    void onPrepared();//准备完成
    void onSeekComplete();//快进完成
    void onCompletion();//播放完成
    void onError();//出错
    void onCenter();//中心建
    void onMenu();//菜单键
    void onPause();//暂停
    boolean onBack();//back
    void onKeyDown();
    void songList();//歌单
}
