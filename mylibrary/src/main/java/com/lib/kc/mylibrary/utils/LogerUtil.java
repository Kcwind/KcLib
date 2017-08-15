package com.lib.kc.mylibrary.utils;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 统一的log处理类，所有的log信息必须使用此类，便于统一控制
 * @author kc
 *
 */
public final class LogerUtil {

	public static boolean isLogOn = true;// 打印信息开关量

	public static Date preDate;
	public static Date nowDate;
	
	public static List<String> LogUrls = new ArrayList<String>();

	public static void v(String tag, String msg) {
		if (isLogOn() && msg != null)
			Log.v(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (isLogOn() && msg != null)
			Log.d(tag, msg);
	}

	public static void i(String tag, String msg) {
		if (isLogOn() && msg != null)
			Log.i(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (isLogOn() && msg != null)
			Log.w(tag, msg);
	}
	public static void e(String tag, String msg) {
		if (isLogOn() && msg != null){
			Log.e(tag, msg);
		}
	}
	
	public static void e(String tag, String msg, int logType) {
		e(tag, msg);
	}

	public static void e(String tag, String msg, int logType, boolean isSendServer) {
		e(tag, msg);
		//TODO send server
	}

	public static void e(String tag, Throwable ex, int logType) {
		e(tag, ex.getMessage());
		if (isLogOn() && ex != null){
			ex.printStackTrace();
		}
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
	}

	public static void e(String tag, Throwable ex, int logType, boolean isSendServer) {
		e(tag, ex.getMessage());	
		if (isLogOn() && ex != null){
			ex.printStackTrace();
		}
	}

	public static void marketTimePoint(String tag) {
		preDate = Calendar.getInstance().getTime();
		i(tag, "标注开始");
	}

	public static void caluteTimeInterval(String tag, boolean isContinue) {
		if (preDate != null) {
			nowDate = Calendar.getInstance().getTime();
			String content = (nowDate.getTime() / 1000 - preDate.getTime() / 1000) + "";
			if (isContinue) {
				preDate = nowDate;
			}
			i(tag, content);
		}
	}

	public static boolean isLogOn() {
		return isLogOn;
	}
}
