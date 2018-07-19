package com.hr.musicktv.db;

import android.os.Handler;
import android.os.Looper;

import com.hr.musicktv.utils.NLog;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmModel;
import io.realm.RealmResults;

import static io.realm.OrderedCollectionChangeSet.State.ERROR;

/*
 *
 * lv   2018/7/9
 */
public class RealmDBManger {

    private  Realm myRealm;
    private  Handler mainHandler;
    private static RealmDBManger realmDBManger = null;

    public static RealmDBManger getRealmSingle(){
        if(null == realmDBManger){
            synchronized (RealmDBManger.class){
                if(null == realmDBManger){
                    realmDBManger = new RealmDBManger();
                }
            }
        }
        return realmDBManger;
    }

    public  static  Realm getMyRealm(){
        if(null == getRealmSingle().myRealm){
            getRealmSingle().myRealm = Realm.getDefaultInstance();
        }
        return getRealmSingle().myRealm;
    }

    public static Handler getMainHandler(){
        if(null == getRealmSingle().mainHandler){
           getRealmSingle(). mainHandler = new Handler(Looper.getMainLooper());
        }
        return getRealmSingle(). mainHandler;
    }



    public static void setInsertOrUpdate(final RealmModel data, final DBResultCallback callback){

      getRealmSingle().getMyRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                bgRealm.insertOrUpdate(data);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if(null != callback){
                    callback.onCallback(data);
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if(null != callback){
                    callback.onFail(error.getMessage());
                }
            }
        });

    }

    public static void copyToRealmOrUpdate(final RealmModel data, final DBResultCallback callback){

        getRealmSingle().getMyRealm().executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {

                bgRealm.copyToRealmOrUpdate(data);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                if(null != callback){
                    callback.onCallback(data);
                }
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                if(null != callback){
                    callback.onFail(error.getMessage());
                }
            }
        });

    }


    public static void getTabsData(
            final Class cfff
            ,String key
            ,String what
            ,final DBResultCallback<RealmResults<TabsData>> callback
    ){
        RealmResults realmResults =  getRealmSingle().getMyRealm()
                .where(cfff)
                .equalTo(key,what)
                .findAll();
        if(null != callback){
            callback.onCallback(realmResults);
        }
    }
    public static void getPHData(
            final Class cfff
            ,String key
            ,String what
            ,final DBResultCallback<RealmResults<PHData>> callback
    ){
        RealmResults realmResults =  getRealmSingle().getMyRealm()
                .where(cfff)
                .equalTo(key,what)
                .findAll();
        if(null != callback){
            callback.onCallback(realmResults);
        }
    }

    public static Object getTabsData(final Class cfff,String key,String what){
        try {
            return   getRealmSingle().getMyRealm().where(cfff).equalTo(key,what).findFirst();
        } finally {
            getRealmSingle().getMyRealm().close();
        }
    }



    public static void closed(){
        getRealmSingle().getMyRealm().removeAllChangeListeners();
        getRealmSingle().getMyRealm().close();
    }


    public static class ChagListener<T> implements  RealmChangeListener<T>{

        private DBResultCallback<T> callback;
        public ChagListener(DBResultCallback<T> callback) {
            this.callback = callback;
        }

        @Override
        public void onChange(T t) {
            if(null != callback){
                callback.onCallback(t);
            }
        }
    }
}
