package com.hr.musicktv.widget.seek;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.hr.musicktv.R;
import com.hr.musicktv.utils.CheckUtil;
import com.hr.musicktv.utils.DisplayUtils;

import java.util.ArrayList;

/*
 * lv   2018/8/1
 */
public class CustomSeekbar extends View {
    private final String TAG = "CustomSeekbar";
    private int width;
    private int height;
    private int perWidth = 0;
    private Paint mPaint;
    private Paint mTextPaint;
    private Paint buttonPaint;
    private int cur_sections = 2;
    private int bitMapHeight = 38;//第一个点的起始位置起始，图片的长宽是76，所以取一半的距离
    private int textMove = 60;//字与下方点的距离，因为字体字体是40px，再加上10的间隔
    private int[] colors = new int[]{0xff77CEEE,0xff96a7b9,0xff0082e9};//进度条的橙色,进度条的灰色,字体的灰色
    private int textSize;
    private int circleRadius;
    private ArrayList<String> section_title;

    int thumbI = 20 ;
    int spotI = 8;
    int spot_onI = 8;
    private int moveY = 0;

    public CustomSeekbar(Context context) {
        super(context);
    }
    public CustomSeekbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public CustomSeekbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        thumbI = DisplayUtils.getDimen(R.dimen.x15);
        spotI = DisplayUtils.getDimen(R.dimen.x8);
        spot_onI = DisplayUtils.getDimen(R.dimen.x8);
        moveY = DisplayUtils.getDimen(R.dimen.x40);//算作误差
        textMove = DisplayUtils.getDimen(R.dimen.x30);

        cur_sections = 0;

        bitMapHeight = thumbI;

        textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18, getResources().getDisplayMetrics());
        circleRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
        mPaint = new Paint(Paint.DITHER_FLAG);
        mPaint.setAntiAlias(true);//锯齿不显示

        mPaint.setStrokeWidth(DisplayUtils.getDimen(R.dimen.x5));

        mTextPaint = new Paint(Paint.DITHER_FLAG);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(0xfff0f0f0);

        buttonPaint = new Paint(Paint.DITHER_FLAG);
        buttonPaint.setAntiAlias(true);
        initData(null);
    }
    /**
     * 实例化后调用，设置bar的段数和文字
     */
    public void initData(ArrayList<String> section){
        if(section != null){
            section_title = section;
        }else {
            String[] str = new String[]{"-5dB","", "","","","0", "","","","","+5dB"};
            section_title = new ArrayList<String>();
            for (int i = 0; i < str.length; i++) {
                section_title.add(str[i]);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        width = widthSize;

        //控件的高度
        //height = 185;

        height  = getTextHight() + textMove + thumbI * 2;

        setMeasuredDimension(width, height);
        width = width - thumbI * 2;

        perWidth = (width) / (section_title.size()-1);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAlpha(150);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setAlpha(255);
        mPaint.setColor(colors[1]);

        int hLC = getTextHight() + textMove + thumbI;

        canvas.drawLine(bitMapHeight, hLC, bitMapHeight+width, hLC, mPaint);



//        mPaint.setColor(colors[2]);
//        canvas.drawLine(bitMapHeight,hLC,
//                bitMapHeight + cur_sections * perWidth,hLC,mPaint);

        int section = 0;

        while(section < section_title.size()){
            if(section < cur_sections) {
                canvas.drawCircle( bitMapHeight + section * perWidth,hLC,spot_onI,mPaint);
            }else{
                mPaint.setAlpha(255);
                canvas.drawCircle( bitMapHeight + section * perWidth,hLC,spot_onI,mPaint);
                if(section == cur_sections){
                    mPaint.setColor(colors[2]);
                    canvas.drawCircle(  bitMapHeight + section * perWidth, hLC, thumbI,mPaint);
                }else {
                    mPaint.setColor(colors[0]);
                    canvas.drawCircle( bitMapHeight + section * perWidth,hLC,spot_onI,mPaint);
                }
            }

            if(section == section_title.size()-1) {
                canvas.drawText(section_title.get(section), (bitMapHeight + section * perWidth) - getTextWidth(section_title.get(section)), hLC - textMove, mTextPaint);
            }else if(section == 0){
                canvas.drawText(section_title.get(section), bitMapHeight, hLC - textMove, mTextPaint);
            }else {
                canvas.drawText(section_title.get(section), (bitMapHeight + section * perWidth) - getTextWidth(section_title.get(section))/2, hLC - textMove, mTextPaint);
            }
            section++;
        }


    }

    //设置进度
    public void setProgress(int progress){
        if(progress < 0 || progress >= section_title.size()){
            if(progress < 0){
                cur_sections = 0;
            }else {
                cur_sections = section_title.size() - 1;
            }
        }else {
            cur_sections = progress;
        }
        postInvalidate();
    }

    public int getCur_sections() {
        return cur_sections;
    }

    public int getJianJv(){

        if(!CheckUtil.isEmpty(section_title))
            return section_title.size() - 1;

        return 0;
    }


    private int  getTextHight(){

        String text = "1dB";
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        int width = rect.width();//文本的宽度
        int height = rect.height();//文本的高度

        return height;
    }

    private int  getTextWidth(String text){

        if(null == text)
            return 0;

        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        int width = rect.width();//文本的宽度
        int height = rect.height();//文本的高度

        return width;
    }
}
