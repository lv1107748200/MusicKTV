package com.hr.musicktv.net.base;

public class BaseDataRequest {
    private String Token;
    private String CID;
    /**
     * UID :
     * GID :
     * Sign : 3031cc7ab00fe18635f1995fd2d7a47a192b97e0f10bd48522eb7101b87babf5
     * Expire : 1530540022.34759
     */

    private String UID;
    private String GID;
    private String Sign;
    private String Expire;

    public BaseDataRequest() {
    }

    public BaseDataRequest(String token, String CID, String UID, String GID, String sign, String expire) {
        Token = token;
        this.CID = CID;
        this.UID = UID;
        this.GID = GID;
        Sign = sign;
        Expire = expire;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        this.Token = token;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getGID() {
        return GID;
    }

    public void setGID(String GID) {
        this.GID = GID;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String Sign) {
        this.Sign = Sign;
    }

    public String getExpire() {
        return Expire;
    }

    public void setExpire(String Expire) {
        this.Expire = Expire;
    }
}
