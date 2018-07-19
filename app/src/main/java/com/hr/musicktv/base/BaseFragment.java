package com.hr.musicktv.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hr.musicktv.net.base.BaseService;

import com.hr.musicktv.widget.focus.FocusBorder;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * Created by 吕 on 2018/3/7.
 */

public class BaseFragment extends AbstractBaseFragment {
    //Fragment的View加载完毕的标记
    private boolean isViewCreated = false;

    //Fragment对用户可见的标记
    private boolean isLoad = false;
    public View baseFgmView;
    Unbinder unbinder;
    public BaseActivity mContext;
    public BaseFragment baseFragment;
    @Inject
    public BaseService baseService;

    protected FocusBorder mFocusBorder;

    private RecyclerView mRecyclerView;
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int scrollState) {
            updateState(scrollState);
        }

        @Override
        public void onScrolled(RecyclerView rv, int i, int i2) {
            updatePosition(rv);
        }

    };

    protected void onMoveFocusBorder(View focusedView, float scale) {
        if(null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale));
        }
    }

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if(null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() instanceof FocusBorderHelper) {
            mFocusBorder = ((FocusBorderHelper)getActivity()).getFocusBorder();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BaseApplation.getBaseApp().getAppComponent().inject(this);
        baseFgmView = inflater.inflate(getLayout(), container, false);
        unbinder =  ButterKnife.bind(this, baseFgmView);
        mContext = (BaseActivity) this.getActivity();
        baseFragment = this;
        init();
        return baseFgmView;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isViewCreated = true;
        lazyLoad();
    }

    public int getLayout(){
        return 0;
    }

    public void init(){

    }
    public void loadData(){

    }
    public void stopLoad(){

    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
      //  NLog.e(NLog.TAGOther,"setUserVisibleHint--->");
        lazyLoad();
    }

    public void isHint(boolean isVisibleToUser){}

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//      //  NLog.e(NLog.TAGOther,"onHiddenChanged--->" + hidden);
//        if (hidden) {
//
//            //隐藏时所作的事情
//            stopLoad();
//
//        } else {
//            //显示时所作的事情
//
//            loadData();
//        }
//
//    }

    private void lazyLoad() {
        if(!isViewCreated)
            return;
        if (getUserVisibleHint()) {
            if(!isLoad){
                loadData();
                isLoad = true;
            }
            isHint(true);
        } else {
            if (isLoad) {
                stopLoad();
            }
            isHint(false);
        }
    }
    protected void setScrollListener(RecyclerView recyclerView) {
        if(mRecyclerView != recyclerView) {
            if(null != mRecyclerView) {
                mRecyclerView.removeOnScrollListener(mOnScrollListener);
            }
            recyclerView.addOnScrollListener(mOnScrollListener);
            mRecyclerView = recyclerView;
        }
    }

    private void updatePosition(RecyclerView rv) {

    }

    private void updateState(int scrollState) {
        if(false) {
            String stateName = "Undefined";
            switch (scrollState) {
                case SCROLL_STATE_IDLE:
                    stateName = "Idle";
                    break;

                case SCROLL_STATE_DRAGGING:
                    stateName = "Dragging";
                    break;

                case SCROLL_STATE_SETTLING:
                    stateName = "Flinging";
                    break;
            }

        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(null != unbinder){
            unbinder.unbind();
        }
        EventBus.getDefault().unregister(this);//解除订阅
        isViewCreated = false;
        isLoad = false;
    }

    public interface FocusBorderHelper {
        FocusBorder getFocusBorder();
    }
}
