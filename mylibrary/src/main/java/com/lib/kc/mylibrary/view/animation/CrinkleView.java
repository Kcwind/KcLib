package com.lib.kc.mylibrary.view.animation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by kechao on 2017/7/28.
 */

public class CrinkleView extends View{
    Paint fulPaint;
    Paint dimPaint;
    float currentPercent;
    int width,height;
    PathMeasure measure;
    OnWaveAnimationListener onWaveAnimationListener;
    float[] pos = new float[2];

    public CrinkleView(Context context) {
        super(context);
        init();
    }

    public CrinkleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CrinkleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CrinkleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path fulPath = getActionFulPath(currentPercent);
        canvas.drawPath(fulPath,fulPaint);

        Path idmPath=getActionDimPath(currentPercent);
        canvas.drawPath(idmPath,dimPaint);
    }

    private Path getActionDimPath(float percent) {
        int quadWidth = width / 4;
        int quadHeight = height/3;

        Path path=new Path();
        int x=-width;
        x+=percent*width;
        path.moveTo(x,height/2);
        //第一段
        path.rQuadTo(quadWidth,-quadHeight,quadWidth*2,0);
        path.rQuadTo(quadWidth,quadHeight,quadWidth*2,0);
        //第二段
        path.rQuadTo(quadWidth,-quadHeight,quadWidth*2,0);
        path.rQuadTo(quadWidth,quadHeight,quadWidth*2,0);
        //闭合
        path.lineTo(x+width*2,height);
        path.lineTo(x,height);
        path.close();

        //关联一个path
        measure.setPath(path,true);
        //获取path到view中心的长度
        float length = Math.abs(x)+ (width / 2);
        //获取该path该位置时的坐标值
        measure.getPosTan(length,pos,null);
        //将y坐标的值通过接口传递出去
        onWaveAnimationListener.onWaveAnimation(pos[1]+currentPercent);
        return path;
    }

    public void setOnWaveAnimationListener(OnWaveAnimationListener listener){
        onWaveAnimationListener =listener;
    }
    //接口
    public  interface OnWaveAnimationListener{
        void  onWaveAnimation(float y);
    }

    public Path getActionFulPath(float percent) {
        int quadWidth = width / 4;
        int quadHeight = height/3;

        Path path=new Path();
        int x=-width;
        x+=percent*width;
        path.moveTo(x,height/2);


        path.rQuadTo(quadWidth,quadHeight,quadWidth*2,0);
        path.rQuadTo(quadWidth,-quadHeight,quadWidth*2,0);

        path.rQuadTo(quadWidth,quadHeight,quadWidth*2,0);
        path.rQuadTo(quadWidth,-quadHeight,quadWidth*2,0);

        path.lineTo(x+width*2,height);
        path.lineTo(x,height);
        path.close();
        return path;
    }

    private void init() {
        post(new Runnable() {
            @Override
            public void run() {
                width = getMeasuredWidth();
                height = getMeasuredHeight();
            }
        });

        fulPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fulPaint.setColor(Color.parseColor("#fafafa"));
        fulPaint.setStyle(Paint.Style.FILL);

        dimPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dimPaint.setColor(Color.parseColor("#fafafa"));
        dimPaint.setAlpha(80);
        dimPaint.setStyle(Paint.Style.FILL);

        //画布抗锯齿
        //mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        measure = new PathMeasure();

        ValueAnimator animator= ValueAnimator.ofFloat(0,1);
        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //不断改变currentPercent的值 让水波纹不断的浮动起来
                currentPercent=animation.getAnimatedFraction();
                invalidate();
            }
        });
        animator.start();
    }
}
