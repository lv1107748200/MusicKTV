package com.hr.musicktv.net.entry.response;

public class UserToken {

    /**
     * UID :
     * GID :
     * Token : db198ac4de6748429985cbd8d5e8d98f
     * Sign : 3031cc7ab00fe18635f1995fd2d7a47a192b97e0f10bd48522eb7101b87babf5
     * Expire : 1530540022.34759
     */

    private String UID;
    private String GID;
    private String Token;
    private String Sign;
    private String Expire;

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

    public String getToken() {
        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
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
