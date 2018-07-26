package com.hr.musicktv.ui.activity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseActivity;
import com.hr.musicktv.net.base.BaseDataResponse;
import com.hr.musicktv.net.base.BaseResponse;
import com.hr.musicktv.net.entry.request.WhatCom;
import com.hr.musicktv.net.entry.response.Detail;
import com.hr.musicktv.net.entry.response.UserToken;
import com.hr.musicktv.net.entry.response.VL;
import com.hr.musicktv.net.entry.response.VipSeries;
import com.hr.musicktv.net.http.HttpCallback;
import com.hr.musicktv.net.http.HttpException;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.utils.NToast;
import com.hr.musicktv.utils.UrlUtils;
import com.hr.musicktv.widget.dialog.LoadingDialog;
import com.hr.musicktv.widget.music.MusicPlayerLayout;
import com.hr.musicktv.widget.single.UserInfoManger;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 播放页
 */
public class MusicPlayerActivity extends BaseActivity {

    private boolean shortPress = false;
   // private String url = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
    private String url = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f20.mp4";
    private long firstTime=0;

    private String playId;
    private Detail detail;

    @BindView(R.id.music_player)
    MusicPlayerLayout musicPlayer;

    @OnClick({R.id.btn_menu,R.id.btn_center})
    public void Onclick(View view){

        switch (view.getId()){
            case R.id.btn_menu:
                musicPlayer.onMenu();
                musicPlayer.onKeyDown();
                break;
            case R.id.btn_center:
                musicPlayer.onCenter();
                break;
        }

    }

    @Override
    public int getLayout() {
        return R.layout.activity_player;
    }
    @Override
    public void init() {
        super.init();

        playId = getIntent().getStringExtra("PLAYID");

        musicPlayer.setUrlAndPlay(url);

    }

    private void huoqv(){

    }



    //获取影片播放地址
    private void Play(){
        UserToken userToken = UserInfoManger.getInstance().getUserToken();
        if(null == userToken){
            return;
        }
        WhatCom whatCom = new WhatCom(
                UserInfoManger.getInstance().getToken(),
                null,
                userToken.getUID(),
                userToken.getGID(),
                userToken.getSign(),
                userToken.getExpire()
        );
        if(!CheckUtil.isEmpty(playId)){
            whatCom.setID(playId);
            //  whatCom.setID("RNwe5MJfB%2fo%3d");
        }

        baseService.Play(whatCom, new HttpCallback<BaseResponse<BaseDataResponse<Detail>>>() {
            @Override
            public void onError(HttpException e) {
                if(e.getCode() == 1){
                    NToast.shortToastBaseApp(e.getMsg());
                }else {
                    LoadingDialog.disMiss();
                }
            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<Detail>> baseDataResponseBaseResponse) {


                 detail = baseDataResponseBaseResponse.getData().getInfo().get(0);

                if(null != detail){
                    VL VL = detail.getVL();

                    String PlayRecordURL = detail.getPlayRecordURL();

                    List<VipSeries> vipSeriesList = detail.getVipSeriesList();
                    if(!CheckUtil.isEmpty(vipSeriesList)){

                        String u = vipSeriesList.get(0).getKey();
                        String s = UrlUtils.getUrl(PlayRecordURL);
                        String baseUrl = UrlUtils.UrlPage(s);
                        Map<String,String> stringMap = UrlUtils.URLRequest(s);
                        stringMap.put("key",u);

            try {
                url = UrlUtils.createLinkStringByGet(baseUrl,stringMap);
                NLog.e(NLog.PLAYER," 播放地址 --->" + url);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
                    }

                }
            }
        },MusicPlayerActivity.this.bindUntilEvent(ActivityEvent.DESTROY));
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_LEFT: //向左键


                if(shortPress){

                }else {



                }

                shortPress = false;

                return true;

            case KeyEvent.KEYCODE_DPAD_RIGHT:  //向右键
                if(shortPress){

                }else {

                }
                shortPress = false;

                return true;
        }



        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //    NLog.d(NLog.TAGOther,"keyCode--->" + keyCode);
        musicPlayer.onKeyDown();

        switch (keyCode) {

            case KeyEvent.KEYCODE_ENTER:     //确定键enter
            case KeyEvent.KEYCODE_DPAD_CENTER:

                musicPlayer.onCenter();

                break;

            case KeyEvent.KEYCODE_BACK:    //返回键

                if(event.getAction()==KeyEvent.ACTION_DOWN){

                    if(musicPlayer.onBack()){
                        return true;
                    }else {
                        if (System.currentTimeMillis()-firstTime>2000){
                            Toast.makeText(MusicPlayerActivity.this,"再按一次退出播放器",Toast.LENGTH_SHORT).show();
                            firstTime=System.currentTimeMillis();
                        }else{
                            finish();
                        }
                        return true;
                    }
                }

                break;
            case KeyEvent.KEYCODE_MENU:
                //    controlPlayer.onClickKey(KeyEvent.KEYCODE_MENU);
                musicPlayer.onMenu();
                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:   //向下键

                /*    实际开发中有时候会触发两次，所以要判断一下按下时触发 ，松开按键时不触发
                 *    exp:KeyEvent.ACTION_UP
                 */
                if (event.getAction() == KeyEvent.ACTION_DOWN){

                    //   controlPlayer.onClickKey(KeyEvent.ACTION_DOWN);

                }

                break;

            case KeyEvent.KEYCODE_DPAD_UP:   //向上键
                //  NLog.d(NLog.TAGOther,"up--->");

                break;

            case     KeyEvent.KEYCODE_0:   //数字键0
                musicPlayer.onMenu();
                //  finish();

                break;
            case     KeyEvent.KEYCODE_1:   //数字键0
                // controlPlayer.playOrstopOrRe();//遥控确定键

                break;
            case     KeyEvent.KEYCODE_2:   //数字键0
                //  controlPlayer.onClickKey(KeyEvent.KEYCODE_MENU);

                break;

            case KeyEvent.KEYCODE_DPAD_LEFT: //向左键



                if(event.getRepeatCount() == 0){
                    event.startTracking();
                    shortPress = true;

                }else {

                    shortPress = false;

                    return true;
                }


                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:  //向右键


                if(event.getRepeatCount() == 0){

                    event.startTracking();
                    shortPress = true;


                }else {

                    shortPress = false;

                    return true;

                }

                break;
            default:
                break;
        }

        return super.onKeyDown(keyCode, event);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicPlayer.release();
    }

}
