package com.hr.musicktv.ui.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseActivity;
import com.hr.musicktv.net.base.BaseDataRequest;
import com.hr.musicktv.net.base.BaseDataResponse;
import com.hr.musicktv.net.base.BaseResponse;
import com.hr.musicktv.net.entry.request.WhatCom;
import com.hr.musicktv.net.entry.response.GetUserInfo;
import com.hr.musicktv.net.entry.response.UserToken;
import com.hr.musicktv.net.http.HttpCallback;
import com.hr.musicktv.net.http.HttpException;
import com.hr.musicktv.utils.DisplayUtils;
import com.hr.musicktv.utils.NLog;
import com.hr.musicktv.utils.SpanUtils;
import com.hr.musicktv.widget.focus.FocusBorder;
import com.hr.musicktv.widget.single.UserInfoManger;
import com.hr.musicktv.widget.single.WhatView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户中心
 */
public class UserCenterActivity extends BaseActivity {

    @BindView(R.id.tv_title_child)
    TextView tvTitleChild;
    @BindView(R.id.tv_title_name)
    TextView tv_title_name;
    @BindView(R.id.tv_user_say_more)
    TextView tv_user_say_more;

    @OnClick({R.id.layout_played_videos,R.id.layout_favorite,R.id.layout_recharge_center,
            R.id.layout_help_center,R.id.layout_about_men})
    public void Onclick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.layout_played_videos:
                intent.setClass(this,DiversityActivity.class);
                intent.putExtra("DiversityType",DiversityActivity.PLAYERRECORD);
                startActivity(intent);
                break;
            case R.id.layout_favorite:
                intent.setClass(this,DiversityActivity.class);
                intent.putExtra("DiversityType",DiversityActivity.FAVORITE);
                startActivity(intent);
                break;
            case R.id.layout_recharge_center:

                break;
            case R.id.layout_help_center:

                break;
            case R.id.layout_about_men:

                break;
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_user_center;
    }

    @Override
    public void init() {
        super.init();

        tvTitleChild.setText(getString(R.string.svp_personal_center));

        mFocusBorder.boundGlobalFocusListener(new FocusBorder.OnFocusCallback() {
            @Override
            public FocusBorder.Options onFocus(View oldFocus, View newFocus) {
                return FocusBorder.OptionsFactory.get(1.1f, 1.1f, 0); //返回null表示不使用焦点框框架
            }
        });

        GetUserInfo();

    }

    private void GetUserInfo(){

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

        baseService.GetUserInfo(whatCom, new HttpCallback<BaseResponse<BaseDataResponse<GetUserInfo>>>() {
            @Override
            public void onError(HttpException e) {

                NLog.e(NLog.TAG,"GetUserInfo --->" +e.getCode()+ e.getMsg());

            }

            @Override
            public void onSuccess(BaseResponse<BaseDataResponse<GetUserInfo>> baseDataResponseBaseResponse) {

                GetUserInfo getUserInfo = baseDataResponseBaseResponse.getData().getInfo().get(0);

                SpanUtils spanUtils = new SpanUtils();
                tv_title_name.setText(
                        spanUtils.append("账号：" + getUserInfo.getNickName())
                                .appendLine()
                                .create()
                );
                spanUtils.setMText();

                tv_user_say_more.setText(
                        spanUtils.append("VIP到期：" + getUserInfo.getEndDate())
                                .appendLine()
                                .appendLine("VIP剩余时间："+getUserInfo.getEndDays())
                                .appendLine("账号登录次数："+getUserInfo.getLoginNumber())
                                .appendLine("上次登录IP："+getUserInfo.getLastIP())
                                .appendLine("上次登录时间："+getUserInfo.getLastTime())
                                .create()
                );
            }
        });
    }

}
