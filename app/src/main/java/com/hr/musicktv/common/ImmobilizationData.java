package com.hr.musicktv.common;

public class ImmobilizationData {

    public static final String SelSong = "已点";
    public static final String UseSong = "已唱";
    public static final String TAB = "tab";

    public static final String StarCLASSIFY = "歌手分类";
    public static final String LanCLASSIFY = "语种分类";
    public static final String StyleCLASSIFY = "曲风分类";


    public static final String StarCLASSIFYURL = "KVideo/GetStarList";
    public static final String LanCLASSIFYURL  = "KVideo/GetLanguageList";
    public static final String StyleCLASSIFYURL  = "KVideo/GetStyleList";



    public enum Tags{
        StarCLASSIFY(ImmobilizationData.StarCLASSIFY,StarCLASSIFYURL,0),
        LanCLASSIFY(ImmobilizationData.LanCLASSIFY,LanCLASSIFYURL,1),
        StyleCLASSIFY(ImmobilizationData.StyleCLASSIFY,StyleCLASSIFYURL,2);

        private String name;
        private String typeurl;
        private String url;
        private int index;
        // 构造方法：传递所有的属性
        private Tags(String name, int index) {
            this.name = name;
            this.index = index;

        }
        private Tags(String name,String url,int index) {
            this.name = name;
            this.index = index;
            this.url = url;
        }
        private Tags(String name, String typeurl,String url,int index) {
            this.name = name;
            this.index = index;
            this.typeurl = typeurl;
            this.url = url;
        }

        // 1.通过编号得到名字
        public static String getNameByIndex(int index) {
            for (Tags c : Tags.values()) {
                if (c.getIndex() == index) {
                    return c.name;
                }
            }
            return null;
        }
        // 2.通过编号得到枚举
        public static Tags getColorByIndex(int index) {
            for (Tags c : Tags.values()) {
                if (c.getIndex() == index) {
                    return c;
                }
            }
            return null;
        }
        // 3.通过名字得到编号
        public static int getIndexByName(String name) {
            for (Tags c : Tags.values()) {
                if (c.getName().equals(name)) {
                    return c.index;
                }
            }
            return -1;
        }
        //通过名字得到 url
        public static String getUrlByName(String name) {
            for (Tags c : Tags.values()) {
                if (c.getName().equals(name)) {
                    return c.url;
                }
            }
            return null;
        }
        public static String getUrlByIndex(int index) {
            for (Tags c : Tags.values()) {
                if (c.getIndex() == index) {
                    return c.url;
                }
            }
            return null;
        }
        //通过名字得到 typeurl
        public static String getTypeUrlByName(String name) {
            for (Tags c : Tags.values()) {
                if (c.getName().equals(name)) {
                    return c.typeurl;
                }
            }
            return null;
        }

        // 通过名字得到枚举
        public static Tags getColorByName(String name) {
            for (Tags c : Tags.values()) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
            return null;
        }


        //得到枚举的名字
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        //得到枚举的编号
        public int getIndex() {
            return index;
        }
        public void setIndex(int index) {
            this.index = index;
        }

        public String getTypeurl() {
            return typeurl;
        }

        public void setTypeurl(String typeurl) {
            this.typeurl = typeurl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }



}
