package com.hr.musicktv.net.entry.response;

public class UserInfo {
    private UserToken UserToken;
    /**
     * UID :
     * UserName : zwj741
     * IsLogin : true
     * NickName : Rich
     * GID :
     * AutoLoginKey : AtGukMyVscE54QkNx+QMEOfQpyJIg2T55EESZ589VySeHUW7AogBzBIFMEwrBHYa
     */

    private String UID;
    private String UserName;
    private boolean IsLogin;
    private String NickName;
    private String GID;
    private String AutoLoginKey;

    public com.hr.musicktv.net.entry.response.UserToken getUserToken() {
        return UserToken;
    }

    public void setUserToken(com.hr.musicktv.net.entry.response.UserToken userToken) {
        UserToken = userToken;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public boolean isIsLogin() {
        return IsLogin;
    }

    public void setIsLogin(boolean IsLogin) {
        this.IsLogin = IsLogin;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public String getGID() {
        return GID;
    }

    public void setGID(String GID) {
        this.GID = GID;
    }

    public String getAutoLoginKey() {
        return AutoLoginKey;
    }

    public void setAutoLoginKey(String AutoLoginKey) {
        this.AutoLoginKey = AutoLoginKey;
    }
}
