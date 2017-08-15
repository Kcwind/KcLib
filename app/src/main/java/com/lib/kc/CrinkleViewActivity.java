package com.lib.kc;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lib.kc.mylibrary.view.animation.CrinkleView;

import org.w3c.dom.Text;

/**
 * Created by kechao on 2017/7/28.
 */

public class CrinkleViewActivity extends AppCompatActivity{
    private static final  String TAG= CrinkleViewActivity.class.getSimpleName();
    CrinkleView waveView;
    ImageView imageView;
    TextView textView;
    private HandlerThread mThread;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crinkle);
        waveView = (CrinkleView) findViewById(R.id.waveView);
        imageView = (ImageView) findViewById(R.id.image);
        textView = (TextView)findViewById(R.id.text1);
        mThread = new HandlerThread("KcThread");
        mThread.start();
        mHandler = new Handler(mThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                Log.e("NewCar 222",Thread.currentThread().getName()+"xxxxxxxxxxxxxxxxxxxxxx");
                AlertDialog.Builder builder = new AlertDialog.Builder(CrinkleViewActivity.this);
                builder.setMessage("提示");
                builder.setTitle("提示");
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                textView.setText("xxxxxxxxsa");
                                Log.e("NewCar111",Thread.currentThread().getName()+"xxxxxxxxxxxxxxxxxxxxxx");
                            }
                        });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create().show();
            }
        };
        mHandler.sendEmptyMessageDelayed(1,1000);
    }
    @Override
    protected void onResume() {
        super.onResume();
        final FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) imageView.getLayoutParams();
        waveView.setOnWaveAnimationListener(new CrinkleView.OnWaveAnimationListener() {
            @Override
            public void onWaveAnimation(float y) {
                Log.e(TAG,"坐标："+y);
                layoutParams.bottomMargin= (int)y;
                imageView.setLayoutParams(layoutParams);
            }
        });

    }

}
