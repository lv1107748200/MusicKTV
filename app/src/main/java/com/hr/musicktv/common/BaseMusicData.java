package com.hr.musicktv.common;


/*
 * lv   2018/7/23
 */
public class BaseMusicData {

    private boolean isStick;//置顶
    private boolean isSel;

    public boolean isStick() {
        return isStick;
    }

    public void setStick(boolean stick) {
        isStick = stick;
    }

    public boolean isSel() {
        return isSel;
    }

    public void setSel(boolean sel) {
        isSel = sel;
    }
}
