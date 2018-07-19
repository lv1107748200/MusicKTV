package com.hr.musicktv.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hr.musicktv.R;
import com.hr.musicktv.base.BaseApplation;


/**
 * Created by 吕 on 2017/12/21.
 */

public class LoadingDialog {

    volatile private static LoadingDialog instance = null;
    private Dialog dialog;
    private Builder builder;

    private static LoadingDialog getInstance() {
        if(instance == null){
            synchronized (LoadingDialog.class) {
                if(instance == null){
                    instance = new LoadingDialog();

                }
            }
        }
        return instance;
    }
    public static void showText(Context context,String text){

        showBuilder( new LoadingDialog.Builder(context)
                .setText(text)
                .setCanOut(false)
                .setIsPro(false)
                .setGravity(Gravity.CENTER));

    }

    public static void showProgress(Context context){

        showBuilder(new LoadingDialog.Builder(context)
                .setText(null)
                .setCanOut(false)
                .setIsPro(true)
                .setGravity(Gravity.CENTER));

    }

    public static void showProWithText(Context context,String text){

        showBuilder(new LoadingDialog.Builder(context)
                .setText(text)
                .setCanOut(false)
                .setIsPro(true)
                .setGravity(Gravity.CENTER));

    }
    private static void showBuilder(Builder context){

        if(null == LoadingDialog.getInstance().dialog){
            LoadingDialog.getInstance().builder = context;
            LoadingDialog.getInstance().dialog  =  context.creatDialog();
        }else {
            LoadingDialog.getInstance().builder.setView(context);
        }

    }
    public static void disMiss(){
        if(null !=  LoadingDialog.getInstance().dialog){
            if(LoadingDialog.getInstance().dialog.isShowing())
            LoadingDialog.getInstance().dialog.dismiss();
        }

    }



    public static class Builder{
        private Context mContext;
        private Dialog mDialog;
        private String text;
        private boolean canOut = false;
        private boolean isShow = true;
        private boolean isPro = true;

        private int gravity = -1;
        private int animationResId = -1;

        private TextView textView;
        private  ProgressBar progressBar;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder setText(String text){
            this.text = text;
            return this;
        }
        public Builder setCanOut(boolean is){

            this.canOut = is;

            return this;
        }

        public Builder setIsShow(boolean isShow){
            this.isShow = isShow;
            return this;
        }
        public Builder setIsPro(boolean isShow){
            this.isPro = isShow;
            return this;
        }

        public Builder setGravity(int gravity){
            this.gravity = gravity;
            return this;
        }

        public Builder setAnimationResid(int animationResId){
            this.animationResId = animationResId;
            return this;
        }

        public Dialog creatDialog(){

            mDialog = new Dialog(mContext, R.style.dialog);

            View view = LayoutInflater.from(mContext).inflate(R.layout.loading_dialog_layout_one, null);
            mDialog.setContentView(view);
            mDialog.setCanceledOnTouchOutside(canOut);


             textView = view.findViewById(R.id.tv);
             progressBar = view.findViewById(R.id.progressBar);
            if(null == text){
                textView.setVisibility(View.GONE);
            }else {
                textView.setVisibility(View.VISIBLE);
                textView.setText(text);
            }
            if(isPro){
                progressBar.setVisibility(View.VISIBLE);
            }else {
                progressBar.setVisibility(View.GONE);
            }


            Window dialogWindow = mDialog.getWindow();
            WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值

            if(gravity>0){
                dialogWindow.setGravity(gravity);//设置对话框位置
            }

            if(animationResId > 0){
                dialogWindow.setWindowAnimations(animationResId);
            }


           // params.width = (int) (1080 * 0.8); // 宽度设置为屏幕的0.65，根据实际情况调整

            dialogWindow.setAttributes(params);

            if(isShow){
                mDialog.show();
            }


            return mDialog;
        }

        public void setView(Builder context){
            if(null == context.text){
                textView.setVisibility(View.GONE);
            }else {
                textView.setVisibility(View.VISIBLE);
                textView.setText(context.text);
            }
            if(context.isPro){
                progressBar.setVisibility(View.VISIBLE);
            }else {
                progressBar.setVisibility(View.GONE);
            }
            if(context.isShow){
                if(!mDialog.isShowing()){
                    mDialog.show();
                }
            }

        }

      }


}
