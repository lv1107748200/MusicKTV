package com.hr.musicktv.common;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.io.Serializable;

/*
 * lv   2018/7/23
 */
public class BaseMusicData  extends BaseModel implements Serializable {
    @Column
    private boolean isStick;//置顶
    @Column
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
