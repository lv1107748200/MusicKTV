package com.hr.musicktv.widget.comparator;


import com.hr.musicktv.common.BaseMusicData;

import java.util.Comparator;

/*
 * lv   2018/7/23
 */
public class MusicListComparator implements Comparator<BaseMusicData> {

    public static MusicListComparator instance = null;

    public static MusicListComparator getInstance() {
        if (instance == null) {
            instance = new MusicListComparator();
        }
        return instance;
    }

    @Override
    public int compare(BaseMusicData o1, BaseMusicData o2) {
        if(o1.isStick()){
            return -1;
        }else if(o2.isStick()){
            return 1;
        }
        return 0;
    }
}
