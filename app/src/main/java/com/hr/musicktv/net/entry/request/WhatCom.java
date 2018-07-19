package com.hr.musicktv.net.entry.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hr.musicktv.net.base.BaseDataRequest;

/**
 * lv
 */
public class WhatCom extends BaseDataRequest {

    public WhatCom(
            String token,
            String CID,
            String UID,
            String GID,
            String sign,
            String expire
    ) {
        super(token, CID, UID, GID, sign, expire);
    }

    public WhatCom(
            String token,
            String CID,
            String UID,
            String GID,
            String sign,
            String expire,
            String size,
            String page
    ) {
        super(token, CID, UID, GID, sign, expire);
        Size = size;
        Page = page;
    }
    public WhatCom(
            String token,
            String CID,
            String UID,
            String GID,
            String sign,
            String expire,
            String size,
            String page,
            String tags
    ) {
        super(token, CID, UID, GID, sign, expire);
        Size = size;
        Page = page;
        Tags = tags;
    }

    public WhatCom(
            String token,
            String CID,
            String UID,
            String GID,
            String sign,
            String expire,
            String size,
            String page,
            String tags,
            boolean isindex) {
        super(token, CID, UID, GID, sign, expire);
        Size = size;
        Page = page;
        Tags = tags;
        Isindex = isindex;
    }

    /**
     * Size : 20
     * Page : 1
     */
    private String Size;
    private String Page;
    private String Tags;
    private String ID;
    private String PageSize;
    private String PageIndex;
    private boolean Isindex;

    private String VideoID;

    public String getPageSize() {
        return PageSize;
    }

    public void setPageSize(String pageSize) {
        PageSize = pageSize;
    }

    public String getPageIndex() {
        return PageIndex;
    }

    public void setPageIndex(String pageIndex) {
        PageIndex = pageIndex;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }

    public String getPage() {
        return Page;
    }

    public void setPage(String Page) {
        this.Page = Page;
    }

    public String getTags() {
        return Tags;
    }

    public void setTags(String tags) {
        Tags = tags;
    }

    public boolean isIsindex() {
        return Isindex;
    }

    public void setIsindex(boolean isindex) {
        Isindex = isindex;
    }

    public String getVideoID() {
        return VideoID;
    }

    public void setVideoID(String videoID) {
        VideoID = videoID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
