package com.hr.musicktv.net.entry.response;


import java.util.List;

/*
 * lv   2018/7/16  影视详情
 */
public class Detail {
    private String ID;
    private VL VL;
    private List<GuestSeries> GuestSeriesList;
    private List<VipSeries> VipSeriesList;
    private List<FlvPath>  FlvPathList;
    private VideoServer VideoServer;

    public List<FlvPath> getFlvPathList() {
        return FlvPathList;
    }

    public void setFlvPathList(List<FlvPath> flvPathList) {
        FlvPathList = flvPathList;
    }

    public com.hr.musicktv.net.entry.response.VideoServer getVideoServer() {
        return VideoServer;
    }

    public void setVideoServer(com.hr.musicktv.net.entry.response.VideoServer videoServer) {
        VideoServer = videoServer;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public com.hr.musicktv.net.entry.response.VL getVL() {
        return VL;
    }

    public void setVL(com.hr.musicktv.net.entry.response.VL VL) {
        this.VL = VL;
    }

    public List<GuestSeries> getGuestSeriesList() {
        return GuestSeriesList;
    }

    public void setGuestSeriesList(List<GuestSeries> guestSeriesList) {
        GuestSeriesList = guestSeriesList;
    }

    public List<VipSeries> getVipSeriesList() {
        return VipSeriesList;
    }

    public void setVipSeriesList(List<VipSeries> vipSeriesList) {
        VipSeriesList = vipSeriesList;
    }

    public boolean isVideoFavrited() {
        return isVideoFavrited;
    }

    public void setVideoFavrited(boolean videoFavrited) {
        isVideoFavrited = videoFavrited;
    }

    /**
     * Add_date : 2018年06月06日
     * Post_Year : 2018
     * Channel : 电影
     * VideoType : 动作
     * Contxt : <p>&nbsp;　弗兰奇（斯科特&middot;阿金斯 Scott Adkins 饰演）最近面临着诸多债务问题：他在城中开设的武馆经营不善并且经常被人上门找麻烦，自己也可能因交不起房租而随时 无家可归，而爱情方面更是无暇顾及、毫无进展。于是弗兰奇打算铤而走险，接受了一份讨债人的工作，他需要冒着生命危险替雇主以暴力手段逼迫欠债人还债或施加压力，而和他搭档的是一位名字叫苏（路易斯&middot;曼迪勒 Louis Mandylor 饰演）的前警署探员。于是这对看似奇怪的组合开着轿车，在一条逐渐失控的讨债道路上越走越远。</p>
     * Updateweekly :
     * CommentNumber : 2
     * isVideoFavrited : true
     * FlvPathList : null
     * Title : null
     * ImgPath : //static.dnvod.tv/upload/video/201806061407260735833s.gif
     * UnlockGold : 120
     * PlayRecordURL : //counter.hwhrq.com/api/Counter/PlusOne?key=AddHitToMovie&id=16411&cid=0,1,3,21&uid=168887&title=w5E5tRzG3ke%2fFJ5f1I4QYA%3d%3d
     * FavrateNumber : 3
     * FilterGold : 20
     * Key : wm68b7x43Ko%3d
     * CID : 0,1,3,21
     * CommentStatus : 0
     * ShareCount : 4
     * PinfenRate : 0.6666666666666666
     * RenqiRate : 0.02
     */

    private String Add_date;
    private String Post_Year;
    private String Channel;
    private String VideoType;
    private String Contxt;
    private String Updateweekly;
    private int CommentNumber;
    private boolean isVideoFavrited;
    private Object Title;
    private String ImgPath;
    private int UnlockGold;
    private String PlayRecordURL;
    private int FavrateNumber;
    private int FilterGold;
    private String Key;
    private String CID;
    private int CommentStatus;
    private int ShareCount;
    private double PinfenRate;
    private double RenqiRate;

    public String getAdd_date() {
        return Add_date;
    }

    public void setAdd_date(String Add_date) {
        this.Add_date = Add_date;
    }

    public String getPost_Year() {
        return Post_Year;
    }

    public void setPost_Year(String Post_Year) {
        this.Post_Year = Post_Year;
    }

    public String getChannel() {
        return Channel;
    }

    public void setChannel(String Channel) {
        this.Channel = Channel;
    }

    public String getVideoType() {
        return VideoType;
    }

    public void setVideoType(String VideoType) {
        this.VideoType = VideoType;
    }

    public String getContxt() {
        return Contxt;
    }

    public void setContxt(String Contxt) {
        this.Contxt = Contxt;
    }

    public String getUpdateweekly() {
        return Updateweekly;
    }

    public void setUpdateweekly(String Updateweekly) {
        this.Updateweekly = Updateweekly;
    }

    public int getCommentNumber() {
        return CommentNumber;
    }

    public void setCommentNumber(int CommentNumber) {
        this.CommentNumber = CommentNumber;
    }

    public boolean isIsVideoFavrited() {
        return isVideoFavrited;
    }

    public void setIsVideoFavrited(boolean isVideoFavrited) {
        this.isVideoFavrited = isVideoFavrited;
    }

    public Object getTitle() {
        return Title;
    }

    public void setTitle(Object Title) {
        this.Title = Title;
    }

    public String getImgPath() {
        return ImgPath;
    }

    public void setImgPath(String ImgPath) {
        this.ImgPath = ImgPath;
    }

    public int getUnlockGold() {
        return UnlockGold;
    }

    public void setUnlockGold(int UnlockGold) {
        this.UnlockGold = UnlockGold;
    }

    public String getPlayRecordURL() {
        return PlayRecordURL;
    }

    public void setPlayRecordURL(String PlayRecordURL) {
        this.PlayRecordURL = PlayRecordURL;
    }

    public int getFavrateNumber() {
        return FavrateNumber;
    }

    public void setFavrateNumber(int FavrateNumber) {
        this.FavrateNumber = FavrateNumber;
    }

    public int getFilterGold() {
        return FilterGold;
    }

    public void setFilterGold(int FilterGold) {
        this.FilterGold = FilterGold;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String Key) {
        this.Key = Key;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public int getCommentStatus() {
        return CommentStatus;
    }

    public void setCommentStatus(int CommentStatus) {
        this.CommentStatus = CommentStatus;
    }

    public int getShareCount() {
        return ShareCount;
    }

    public void setShareCount(int ShareCount) {
        this.ShareCount = ShareCount;
    }

    public double getPinfenRate() {
        return PinfenRate;
    }

    public void setPinfenRate(double PinfenRate) {
        this.PinfenRate = PinfenRate;
    }

    public double getRenqiRate() {
        return RenqiRate;
    }

    public void setRenqiRate(double RenqiRate) {
        this.RenqiRate = RenqiRate;
    }
}
