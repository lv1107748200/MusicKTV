package com.hr.musicktv.net.entry.response;


/*
 * lv   2018/7/16
 */
public class CommHot {

    /**
     * LouCheng : 20
     * RecordSum : 0
     * HeadImage : //static.dnvod.tv/images/user/default_women2.jpg
     * Post_Date : 2018-05-29
     * Context : 我操你妈的。-这片是侮辱中国嘛?..一到香港就碰到抢劫？。几个年轻人还敢拿刀。香港治安有那么差嘛？，把香港拍的跟泰国一样！。
     水上市集
     * UserLevelPic :
     * ReplyID : 0
     * NickName : null
     * ReplyUID : 0
     * UID : 0
     */

    private CommentSon Comment;

    public CommentSon getComment() {
        return Comment;
    }

    public void setComment(CommentSon comment) {
        Comment = comment;
    }

    private String LouCheng;
    private int RecordSum;
    private String HeadImage;
    private String Post_Date;
    private String Context;
    private String UserLevelPic;
    private int ReplyID;
    private Object NickName;
    private int ReplyUID;
    private int UID;

    public String getLouCheng() {
        return LouCheng;
    }

    public void setLouCheng(String LouCheng) {
        this.LouCheng = LouCheng;
    }

    public int getRecordSum() {
        return RecordSum;
    }

    public void setRecordSum(int RecordSum) {
        this.RecordSum = RecordSum;
    }

    public String getHeadImage() {
        return HeadImage;
    }

    public void setHeadImage(String HeadImage) {
        this.HeadImage = HeadImage;
    }

    public String getPost_Date() {
        return Post_Date;
    }

    public void setPost_Date(String Post_Date) {
        this.Post_Date = Post_Date;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String Context) {
        this.Context = Context;
    }

    public String getUserLevelPic() {
        return UserLevelPic;
    }

    public void setUserLevelPic(String UserLevelPic) {
        this.UserLevelPic = UserLevelPic;
    }

    public int getReplyID() {
        return ReplyID;
    }

    public void setReplyID(int ReplyID) {
        this.ReplyID = ReplyID;
    }

    public Object getNickName() {
        return NickName;
    }

    public void setNickName(Object NickName) {
        this.NickName = NickName;
    }

    public int getReplyUID() {
        return ReplyUID;
    }

    public void setReplyUID(int ReplyUID) {
        this.ReplyUID = ReplyUID;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int UID) {
        this.UID = UID;
    }
}
