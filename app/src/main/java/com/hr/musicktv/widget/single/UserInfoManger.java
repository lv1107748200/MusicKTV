package com.hr.musicktv.widget.single;


import com.hr.musicktv.net.entry.response.UserToken;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.JsonMananger;
import com.hr.musicktv.utils.SPUtils;
import com.hr.musicktv.utils.SpanUtils;

/**
 * Created by 吕 on 2018/2/6.
 */

public class UserInfoManger {

    public static final String TOKEN = "token";
    public static final String USERTOKEN = "UserToken";


    volatile private static UserInfoManger instance = null;

    private String token;
    private UserToken userToken;

    public static UserInfoManger getInstance(){
        if(instance == null){
            synchronized (UserInfoManger.class) {
                if(instance == null){
                    instance = new UserInfoManger();
                }
            }
        }
        return instance;
    }

    public UserToken getUserToken() {
        if(CheckUtil.isEmpty(userToken)){
            String j = (String) SPUtils.get(USERTOKEN,"");
            if(!CheckUtil.isEmpty(j)) {
                userToken = JsonMananger.jsonToBean(j,UserToken.class);
            }
        }

        return userToken;
    }

    public void setUserToken(UserToken userToken) {
        if(null == userToken){
            return;
        }
        SPUtils.put(USERTOKEN, JsonMananger.beanToJson(userToken));
        this.userToken = userToken;
    }

    public String getToken() {
        if(CheckUtil.isEmpty(token)){
            token = (String) SPUtils.get(TOKEN,"");
        }
        return token;
    }

    public void setToken(String token) {
        if(CheckUtil.isEmpty(token))
            return;
        SPUtils.put(TOKEN,token);
        this.token = token;
    }

    public void clear(){
        token = null;
    }

}
