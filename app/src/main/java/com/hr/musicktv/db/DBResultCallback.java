package com.hr.musicktv.db;

/*
 * lv   2018/7/9
 */
public abstract class DBResultCallback<T> {


    public static class Result<T> {
        public T t;
    }

    public DBResultCallback() {

    }

    /**
     * 成功时回调。
     *
     * @param t 已声明的类型。
     */
    public abstract void onSuccess(T t);

    /**
     * 错误时回调。
     *
     * @param errString 错误提示
     */
    public abstract void onError(String errString);


    public void onFail(final String errString) {
        RealmDBManger.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                onError(errString);
            }
        });
    }

    public void onCallback(final T t) {
        RealmDBManger.getMainHandler().post(new Runnable() {
            @Override
            public void run() {
                onSuccess(t);
            }
        });
    }
}
