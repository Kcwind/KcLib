package com.lib.kc.mylibrary.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.lib.kc.mylibrary.utils.LogerUtil;

/**
 * Created by Administrator on 2016/12/15.
 */

public class AutoScrollViewPager extends ViewPager {
    private int updataInterval = 4000; //  自动刷新时的更新间隔，单位为毫秒。
    private boolean isAutoScroll = true;
    private static final int SCROLL = 1;

    public AutoScrollViewPager(Context context) {
        super(context);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogerUtil.d("AutoScrollViewPager","-----update-----" + getVisibility());
            switch(msg.what) {
                case SCROLL:
                    int count = getCurrentItem();
                    count ++ ;
                    setCurrentItem(count);
                    handler.sendEmptyMessageDelayed(SCROLL , updataInterval);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility != VISIBLE && handler.hasMessages(SCROLL)){
            handler.removeMessages(SCROLL);
        }else if (visibility == VISIBLE && isCanScroll() && !handler.hasMessages(SCROLL)){
            handler.sendEmptyMessageDelayed(SCROLL , updataInterval);
        }

    }

    @Override
    public void setAdapter(PagerAdapter adapter) {
        super.setAdapter(adapter);
        if (adapter.getCount() > 1 && isAutoScroll && !handler.hasMessages(SCROLL)){
            handler.sendEmptyMessageDelayed(SCROLL , updataInterval);
        }else if(handler.hasMessages(SCROLL)){
            handler.removeMessages(SCROLL);
        }
    }

    public void setUpdataInterval(int time){
        updataInterval = time;
        isAutoScroll = true;
        if (handler.hasMessages(SCROLL)){
            handler.removeMessages(SCROLL);
        }
        handler.sendEmptyMessageDelayed(SCROLL , updataInterval);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if(isAutoScroll)
                    handler.removeMessages(SCROLL);
                break;
            case MotionEvent.ACTION_UP:
                if (isCanScroll())
                    handler.sendEmptyMessageDelayed(SCROLL , updataInterval);
        }
        return super.onTouchEvent(ev);
    }

    public boolean isAutoScroll() {
        return isAutoScroll;
    }

    public void setAutoScroll(boolean autoScroll) {
        isAutoScroll = autoScroll;
        if(!isAutoScroll && handler.hasMessages(SCROLL)){
            handler.removeMessages(SCROLL);
        }else if(isCanScroll() && !handler.hasMessages(SCROLL)){
            handler.sendEmptyMessageDelayed(SCROLL , updataInterval);
        }
    }

    private boolean isCanScroll(){
        return isAutoScroll && getChildCount() > 1;
    }
}
