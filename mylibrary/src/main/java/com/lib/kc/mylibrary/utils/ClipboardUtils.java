package com.lib.kc.mylibrary.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.StringDef;

/**
 * Created by kechao on 2017/7/19.
 */

public class ClipboardUtils {

    /**
     * 复制到剪切板
     * @param context
     */
    public static void copy(Context context,String text){
        ClipboardManager myClipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("text", text);
        myClipboard.setPrimaryClip(myClip);
    }

    /**
     * 从剪切板取出数据
     * @param context
     */
    public static String getClipboardText(Context context){
        ClipboardManager myClipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = myClipboard.getPrimaryClip();
        String result = null;
        if (clipData != null){
            ClipData.Item item = clipData.getItemAt(0);
            result = item.getText().toString();
        }

        return result;
    }
}
