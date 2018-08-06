package com.hr.musicktv.net.entry.response;


import android.support.annotation.NonNull;

import com.hr.musicktv.common.BaseMusicData;
import com.hr.musicktv.db.AppDatabase;
import com.hr.musicktv.db.TabsData;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.DateUtils;
import com.hr.musicktv.utils.NLog;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import java.util.List;

/*
 * lv   2018/7/26
 */
@Table(database = AppDatabase.class)
public class MKSearch extends BaseMusicData  {

    /**
     * Singer :
     * Language :
     * Style :
     * Title : 曲1
     * VisiableStatus :
     * ShareCount :
     * ID :
     */
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static  void saveList(final List<MKSearch> useMusicList){

        if(CheckUtil.isEmpty(useMusicList))
            return;

        FlowManager.getDatabase(AppDatabase.class)
                .beginTransactionAsync(new ITransaction() {
                    @Override
                    public void execute(DatabaseWrapper databaseWrapper) {
                        for(int i=0; i<useMusicList.size(); i++){
                            MKSearch mkSearch = useMusicList.get(i);
                            mkSearch.setTimestamp(DateUtils.getStringTodayl() + i);
                            mkSearch.save();
                        }
                    }
                }).success(new Transaction.Success() {
            @Override
            public void onSuccess(@NonNull Transaction transaction) {
                NLog.e(NLog.DB,"onSuccess---> ");
            }
        }).error(new Transaction.Error() {
            @Override
            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
                NLog.e(NLog.DB,"onError---> " + error.getMessage());
            }
        }).build().executeSync();


//        FlowManager.getDatabase(AppDatabase.class)
//                .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
//                        new ProcessModelTransaction.ProcessModel<MKSearch>() {
//                            @Override
//                            public void processModel(MKSearch mkSearch,DatabaseWrapper wrapper) {
//
//                            }
//
//                        }).addAll(useMusicList).build()).success(new Transaction.Success() {
//            @Override
//            public void onSuccess(@NonNull Transaction transaction) {
//                NLog.e(NLog.DB,"onSuccess---> ");
//            }
//        }).error(new Transaction.Error() {
//            @Override
//            public void onError(@NonNull Transaction transaction, @NonNull Throwable error) {
//                NLog.e(NLog.DB,"onError---> " + error.getMessage());
//            }
//        }).build().executeSync();
    }

}
