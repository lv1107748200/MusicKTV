package com.hr.musicktv.net.entry.request;


import android.os.Parcel;
import android.os.Parcelable;

/*
 * lv   2018/7/26
 */
public class MKSearch implements Parcelable {

    /**
     * Star : 刘德华
     * Language : 英语
     * Style : 爵士
     * Top : 金曲榜
     * Title : WQS
     */

    private String Star;
    private String Language;
    private String Style;
    private String Top;
    private String Title;

    public String getStar() {
        return Star;
    }

    public void setStar(String Star) {
        this.Star = Star;
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

    public String getTop() {
        return Top;
    }

    public void setTop(String Top) {
        this.Top = Top;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Star);
        dest.writeString(this.Language);
        dest.writeString(this.Style);
        dest.writeString(this.Top);
        dest.writeString(this.Title);
    }

    public MKSearch() {
    }

    protected MKSearch(Parcel in) {
        this.Star = in.readString();
        this.Language = in.readString();
        this.Style = in.readString();
        this.Top = in.readString();
        this.Title = in.readString();
    }

    public static final Parcelable.Creator<MKSearch> CREATOR = new Parcelable.Creator<MKSearch>() {
        @Override
        public MKSearch createFromParcel(Parcel source) {
            return new MKSearch(source);
        }

        @Override
        public MKSearch[] newArray(int size) {
            return new MKSearch[size];
        }
    };
}
