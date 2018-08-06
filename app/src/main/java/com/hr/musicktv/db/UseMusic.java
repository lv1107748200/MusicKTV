package com.hr.musicktv.db;


import android.support.annotation.NonNull;

import com.hr.musicktv.common.BaseMusicData;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

/*
 * lv   2018/7/27
 */
@Table(database = AppDatabase.class)
public class UseMusic extends BaseMusicData {
    @PrimaryKey
    private long ID;
    @Column
    private String whatType;
    @Column
    private String Singer;
    @Column
    private String Language;
    @Column
    private String Style;
    @Column
    private String Title;
    @Column
    private String VisiableStatus;
    @Column
    private String ShareCount;
    @Column
    private long time;

    @Column
    private long timestamp;

    public String getWhatType() {
        return whatType;
    }

    public void setWhatType(String whatType) {
        this.whatType = whatType;
    }

    public String getSinger() {
        return Singer;
    }

    public void setSinger(String Singer) {
        this.Singer = Singer;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String Language) {
        this.Language = Language;
    }

    public String getStyle() {
        return Style;
    }

    public void setStyle(String Style) {
        this.Style = Style;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getVisiableStatus() {
        return VisiableStatus;
    }

    public void setVisiableStatus(String VisiableStatus) {
        this.VisiableStatus = VisiableStatus;
    }

    public String getShareCount() {
        return ShareCount;
    }

    public void setShareCount(String ShareCount) {
        this.ShareCount = ShareCount;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static  void saveList(List<UseMusic> useMusicList){

        FlowManager.getDatabase(AppDatabase.class)
                .beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        List<UseMusic> mkSearches = SQLite.select()
                                .from(UseMusic.class)
                                .queryList();// 查询所有记录
                    }
                }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {

            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {

            }
        }).build().executeSync();
    }
}
