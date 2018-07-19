package com.hr.musicktv.db;

import com.hr.musicktv.net.entry.response.WhatType;



import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmClass;

/*
 *
 * lv   2018/7/9
 */
@RealmClass
public class TabsData implements RealmModel {

    @PrimaryKey
    private String tab;

    private RealmList<WhatType> realmList ;

    public RealmList<WhatType> getRealmList() {
        return realmList;
    }

    public void setRealmList(RealmList<WhatType> realmList) {
        this.realmList = realmList;
    }

    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

}
