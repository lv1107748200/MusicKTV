package com.hr.musicktv.db;


import com.hr.musicktv.net.entry.response.MKSearch;
import com.hr.musicktv.net.entry.response.MKSearch_Table;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/*
 *
 * lv   2018/7/9
 */
@Table(database = AppDatabase.class)
public class TabsData extends BaseModel{
    @PrimaryKey
    private String tab;

    private List<MKSearch> mkSearchList;


    public String getTab() {
        return tab;
    }

    public void setTab(String tab) {
        this.tab = tab;
    }

    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "mkSearchList")
    public List<MKSearch> getMkSearchList() {
        if (mkSearchList == null || mkSearchList.isEmpty()) {
            mkSearchList = SQLite.select()
                    .from(MKSearch.class)
                    .where(MKSearch_Table.whatType.eq(tab))
                    .queryList();
        }
        return mkSearchList;
    }

    public void setMkSearchList(List<MKSearch> mkSearchList) {
        this.mkSearchList = mkSearchList;
    }
}
