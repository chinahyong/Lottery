package com.pkx.lottery.utils;

import com.pkx.lottery.Constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class SharePreferenceUtil {
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	public SharePreferenceUtil(Context context) {
		sp = context.getSharedPreferences(Constant.SHARE, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public String getSN() {
		return sp.getString("SN", "");
	}

	public void setSN(String sn) {
		editor.putString("SN", sn);
		editor.commit();
	}

	public void addLog(String log) {
		if (log != null && log.length() > 10) {
			editor.putString("log", getLog() + log);
			editor.commit();
		} else {
			Log.e("pkx",
					"---------------add log is null exception--------------");
		}
	}

	public String getLog() {
		return sp.getString("log", "");
	}

	public void clearLog() {
		Log.e("pkx", "----clear----log");
		editor.putString("log", "");
		editor.commit();
	}

	public boolean isSNSetted() {
		return sp.getBoolean("isSet", false);
	}

	public void setIsSNSetted(boolean b) {
		editor.putBoolean("isSet", b);
		editor.commit();
	}

	public boolean getShakeVibrat() {
		return sp.getBoolean("isShakeVibrat", true);
	}

	public void setShakeVibrat(boolean b) {
		editor.putBoolean("isShakeVibrat", b);
		editor.commit();
	}

	public boolean getSelectVibrat() {
		return sp.getBoolean("isSelectVibrat", true);
	}

	public void setSelectVibrat(boolean b) {
		editor.putBoolean("isSelectVibrat", b);
		editor.commit();
	}

	public boolean getLoginStatus() {
		return sp.getBoolean("isLogin", false);
	}

	public void setLoginStatus(boolean b) {
		editor.putBoolean("isLogin", b);
		editor.commit();
	}

	public String getSID() {
		return sp.getString("SID", "");
	}

	public void setSID(String sid) {
		editor.putString("SID", sid);
		editor.commit();
	}

	public String getdeviceKEY() {
		return sp.getString("deviceKEY", "");
	}

	public void setdeviceKEY(String deviceKEY) {
		editor.putString("deviceKEY", deviceKEY);
		editor.commit();
	}

	public String getuid() {
		return sp.getString("uid", "");
	}

	public void setuid(String uid) {
		editor.putString("uid", uid);
		editor.commit();
	}

	public String getuserKEY() {
		return sp.getString("userKEY", "");
	}

	public void setuserKEY(String userKEY) {
		editor.putString("userKEY", userKEY);
		editor.commit();
	}
}
