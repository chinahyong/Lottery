package com.pkx.lottery;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.bean.SIMCardInfo;
import com.pkx.lottery.dto.AuthJson;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.Random;

public class GetAuthActivity extends Activity implements OnClickListener {
	private MyHandler handler;
	private View loginBtn;
	private SharePreferenceUtil sutil;
	private boolean isLoginAuto;
	private String SN;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.auth);
		sutil=new SharePreferenceUtil(this);
		initViews();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.loginBtn:
//			String phoneNumber;
//			Log.e("pkx", " number:"+SIMCardInfo.getManager(this).getLine1Number());
//			if (SIMCardInfo.getManager(this).getLine1Number()==null||SIMCardInfo.getManager(this).getLine1Number().length()==0) {
//				Random r = new Random();
//				String num = "+882";
//				for (int i = 0; i < 10; i++) {
//					num += String.valueOf(r.nextInt(10));
//				}
//				phoneNumber = num;
//				Log.e("pkx", "getManager is null  number:" +num);
//			} else {
//				SIMCardInfo info = new SIMCardInfo(this);
//				phoneNumber = info.getNativePhoneNumber();
//			}
//			SN = Secure.getString(this.getContentResolver(),
//					Secure.ANDROID_ID);
			char[] chs = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
					'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
					'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
					'8', '9' };
//			if (SN == null || SN.length() < 16) {
//				Random sr = new Random();
//				String gsn="";
//				for (int i = 0; i < 16; i++) {
//					gsn += String.valueOf(chs[sr.nextInt(36)]);
//					gsn.toUpperCase();
//				}
//				SN=gsn;
//			}
//			String serialnum = null;
//
//			try {
//
//				Class<?> c = Class.forName("android.os.SystemProperties");
//
//				Method get = c.getMethod("get", String.class, String.class);
//				Random sr = new Random();
//				String gsn = "";
//				for (int i = 0; i < 10; i++) {
//					gsn += String.valueOf(chs[sr.nextInt(36)]);
//					gsn.toUpperCase();
//				}
//
//				serialnum = (String) (get.invoke(c, "ro.serialno", "random"
//						+ gsn));
//				Log.e("pkx", "ro serialnum-------:" + serialnum);
//
//			} catch (Exception ignored)
//
//			{
//
//			}
			String phoneNumber;
			Log.e("pkx", " number:"
					+ SIMCardInfo.getManager(this).getLine1Number());
			if (SIMCardInfo.getManager(this).getLine1Number() == null
					|| SIMCardInfo.getManager(this).getLine1Number()
							.length() == 0) {
				Random r = new Random();
				String num = "+882";
				for (int i = 0; i < 10; i++) {
					num += String.valueOf(r.nextInt(10));
				}
				phoneNumber = "phoneisnull";
				Log.e("pkx", "getManager is null  number:" + num);
			} else {
				SIMCardInfo info = new SIMCardInfo(this);
				phoneNumber = info.getNativePhoneNumber();
			}
			SN = Secure.getString(this.getContentResolver(),
					Secure.ANDROID_ID);

			if (SN == null || SN.length() == 0) {
				SN = "androidisnull";
				// Random sr = new Random();
				// String gsn = "";
				// for (int i = 0; i < 16; i++) {
				// gsn += String.valueOf(chs[sr.nextInt(36)]);
				// gsn.toUpperCase();
				// }
				// SN = gsn;
			}
			String serialnum = null;

			try {

				Class<?> c = Class.forName("android.os.SystemProperties");

				Method get = c.getMethod("get", String.class, String.class);
				Random sr = new Random();
				String gsn = "";
				for (int i = 0; i < 10; i++) {
					gsn += String.valueOf(chs[sr.nextInt(36)]);
					gsn.toUpperCase();
				}

				serialnum = (String) (get.invoke(c, "ro.serialno",
						"serialnumisnull"));
				Log.e("pkx", "ro serialnum-------:" + serialnum);

			} catch (Exception ignored) {
				serialnum = "serialnumisnull";
			}
			AuthJson au = new AuthJson();
			au.setDeviceMobile(phoneNumber);
			String finalSN = SN + "&&&" + serialnum + "&&&" + phoneNumber;
			au.setDeviceSN(finalSN);
			String json = Net.gson.toJson(au);
			Log.e("pkx", "json:"+json);
			String miwen = MDUtils.MDEncode("aba6ab3d85f4b8d7d2f23b9da9b5ac7d",
					Net.gson.toJson(au));
			Log.e("pkx", "md json1:"+miwen);
			RequestParams params = new RequestParams();
			params.put("DATA", miwen);
			Net.post(true, this,
					Constant.POST_URL+"/auth.api.php?act=deviceAuth", params,
					handler, Constant.NET_ACTION_LOGIN);
		
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	private void initViews() {
		loginBtn = findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(this);
		handler = new MyHandler();
	}

	class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// 子类必须重写此方法,接受数据
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				if (msg.arg1 == Constant.NET_ACTION_LOGIN) {
					try {
						try {
							JSONObject ojo = (JSONObject) msg.obj;
							SharePreferenceUtil sutil=new SharePreferenceUtil(GetAuthActivity.this);
							sutil.setSID(ojo.getString("SID"));
							sutil.setdeviceKEY(ojo.getString("deviceKEY"));
							Toast.makeText(GetAuthActivity.this, "设备码获取成功", Toast.LENGTH_LONG).show();
							sutil.setSN(SN);
							Log.e("pkx", "SN"+SN);
							sutil.setIsSNSetted(true);
							finish();
//							Intent toLogin = new Intent(GetAuthActivity.this,
//									HomeActivity.class);
//							startActivity(toLogin);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			} else if (msg.what == Constant.POST_FAIL) {

			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				if (msg.arg1 == Constant.NET_ACTION_LOGIN) {
					Toast.makeText(GetAuthActivity.this, msg.obj.toString(),
							Toast.LENGTH_SHORT).show();
				}

			}

		}
	}
}
