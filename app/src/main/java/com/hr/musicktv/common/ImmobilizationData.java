package com.hr.musicktv.common;

public class ImmobilizationData {
    public static final String HOMEPAGE = "首页";
    public static final String CLASSIFY = "分类";
    public static final String FILM = "电影";
    public static final String TELEPLAY = "电视剧";
    public static final String VARIETY = "综艺";
    public static final String ANIME = "动漫";
    public static final String SPORTS = "体育";
    public static final String OVERSEAS = "华人圈";
    public static final String DOCUMENTARY = "纪录片";
    public static final String GAME = "游戏";

    public static final String FILMTYPEURL = "List/FilmType";
    public static final String TELEPLAYTYPEURL  = "List/TVType";
    public static final String VARIETYTYPEURL  = "List/VarietyType";
    public static final String ANIMETYPEURL  = "List/AnimeType";
    public static final String SPORTSTYPEURL  = "List/SportType";
    public static final String OVERSEASTYPEURL  = "List/CircleType";
    public static final String DOCUMENTARYTYPEURL  = "List/DocumentaryType";
    public static final String GAMETYPEURL  = "List/GameType";

    public static final String FILMURL = "List/Film";
    public static final String TELEPLAYURL  = "List/TV";
    public static final String VARIETYURL  = "List/Variety";
    public static final String ANIMEURL  = "List/Anime";
    public static final String SPORTSURL  = "List/Sport";
    public static final String OVERSEASURL  = "List/Documentary";
    public static final String DOCUMENTARYURL  = "List/Circle";
    public static final String GAMEURL  = "List/Game";



    public enum Tags{
        HOMEPAGE(ImmobilizationData.HOMEPAGE,0),
        CLASSIFY(ImmobilizationData.CLASSIFY,1),
        FILM(ImmobilizationData.FILM,FILMTYPEURL,FILMURL,2),
        TELEPLAY(ImmobilizationData.TELEPLAY,TELEPLAYTYPEURL,TELEPLAYURL,3),
        VARIETY(ImmobilizationData.VARIETY,VARIETYTYPEURL,VARIETYURL,4),
        ANIME(ImmobilizationData.ANIME,ANIMETYPEURL,ANIMEURL,5),
        SPORTS(ImmobilizationData.SPORTS,SPORTSTYPEURL,SPORTSURL,6),
        OVERSEAS(ImmobilizationData.OVERSEAS,OVERSEASTYPEURL,OVERSEASURL,7),
        DOCUMENTARY(ImmobilizationData.DOCUMENTARY,DOCUMENTARYTYPEURL,DOCUMENTARYURL,8),
        GAME(ImmobilizationData.GAME,GAMETYPEURL,GAMEURL,9);

        private String name;
        private String typeurl;
        private String url;
        private int index;
        // 构造方法：传递所有的属性
        private Tags(String name, int index) {
            this.name = name;
            this.index = index;

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

    public enum HomePages{
        HEAD("HEAD",0),
        REC("正在热播",1),
        FILM(ImmobilizationData.FILM,2),
        TELEPLAY(ImmobilizationData.TELEPLAY,3),
        VARIETY(ImmobilizationData.VARIETY,4),
        ANIME(ImmobilizationData.ANIME,5),
        SPORTS(ImmobilizationData.SPORTS,6),
        OVERSEAS(ImmobilizationData.OVERSEAS,7),
        DOCUMENTARY(ImmobilizationData.DOCUMENTARY,8),
        GAME(ImmobilizationData.GAME,9)
        ;

        private String key;
        private int index;

        HomePages(String key, int index) {
            this.key = key;
            this.index = index;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        // 1.通过编号得到名字
        public static String getKeyByIndex(int index) {
            for (HomePages c : HomePages.values()) {
                if (c.getIndex() == index) {
                    return c.key;
                }
            }
            return null;
        }

        // 通过名字得到枚举
        public static HomePages getHomePagesByName(String key) {
            for (HomePages c : HomePages.values()) {
                if (c.getKey().equals(key)) {
                    return c;
                }
            }
            return null;
        }

    }



}
