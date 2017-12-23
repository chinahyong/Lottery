package com.pkx.lottery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings.Secure;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.bean.SIMCardInfo;
import com.pkx.lottery.dto.AuthJson;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.MyMD5Util;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.RandomBallsUtils;
import com.pkx.lottery.utils.SharePreferenceUtil;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

public class WelcomeActivity extends Activity implements OnClickListener {
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	private Button button7;
	private TextView textView1;
	private String SN;
	private String finalSN;
	private ImageView logo_image;
	// private Intent mIntent;
	SharePreferenceUtil sutil;
	// private View welcomePage;
	private MyHandler mHandler;
	private boolean isDeviceSet;

	// private char[] chs = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
	// 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
	// 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	public void clickBack(View view) {
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		// mIntent = getIntent();
		// Toast.makeText(this, "从悦宝："+mIntent.getStringExtra("info"),
		// Toast.LENGTH_LONG).show();
		initViews();
		// welcomePage=findViewById(R.id.welcomePage);
		Log.e("pkx",
				"serialNUM---------:" + String.valueOf(android.os.Build.SERIAL));
		Log.e("pkx", "SN--------:" + sutil.getSN());
		getSn();
		logo_image = (ImageView) this.findViewById(R.id.welcomePage);
		logo_image.setOnClickListener(this);
		AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
		animation.setDuration(2000);
		logo_image.setAnimation(animation);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity(new Intent(WelcomeActivity.this,
						HomeActivity.class));
				if (isDeviceSet) {
					finish();
				}
			}

		});
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// Message msg=new Message();
		// msg.what=Constant.PAGE_CHANGED;
		// mHandler.sendMessage(msg);
		// }
		// }).start();
		// Intent toWE=new Intent(this, WebviewActivity.class);
		// startActivity(toWE);
	}

	private void getSn() {
		Log.e("pkx", "welcom send devicekey request");
		String SN = "";
		finalSN = "";
		// if (!sutil.isSNSetted()) {
		// if (sutil.getSID() == null || sutil.getdeviceKEY() == null
		// || "".equals(sutil.getSID())
		// || "".equals(sutil.getdeviceKEY())) {

		String phoneNumber;
		Log.e("pkx", " number:" + SIMCardInfo.getManager(this).getLine1Number());
		if (SIMCardInfo.getManager(this).getLine1Number() == null
				|| SIMCardInfo.getManager(this).getLine1Number().length() == 0) {
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
		SN = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);

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
			serialnum = (String) (get.invoke(c, "ro.serialno",
					"serialnumisnull"));
			Log.e("pkx", "ro serialnum-------:" + serialnum);

		} catch (Exception ignored) {
			serialnum = "serialnumisnull";
		}
		AuthJson au = new AuthJson();
		au.setDeviceMobile(phoneNumber);
		finalSN = SN + "&&&" + serialnum + "&&&" + phoneNumber;
		au.setDeviceSN(finalSN);
		Log.e("pkx", "final sn:" + finalSN);
		String miwen = MDUtils.MDEncode(MyMD5Util.MD5("UITN25LMUQC436IM"),
				Net.gson.toJson(au));
		Log.e("pkx", "md5:" + MyMD5Util.MD5("UITN25LMUQC436IM"));
		Log.e("pkx", "md5json:" + miwen);
		RequestParams params = new RequestParams();
		params.put("DATA", miwen);
		Net.post(false, this, Constant.POST_URL
				+ "/auth.api.php?act=deviceAuth", params, mHandler,
				Constant.NET_ACTION_LOGIN);
		// }
		// }
	}

	private void initViews() {
		mHandler = new MyHandler();
		textView1 = (TextView) findViewById(R.id.textView1);
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(this);
		button3 = (Button) findViewById(R.id.button3);
		button3.setOnClickListener(this);
		button4 = (Button) findViewById(R.id.button4);
		button4.setOnClickListener(this);
		button5 = (Button) findViewById(R.id.button5);
		button5.setOnClickListener(this);
		button6 = (Button) findViewById(R.id.button6);
		button6.setOnClickListener(this);
		button7 = (Button) findViewById(R.id.button7);
		button7.setOnClickListener(this);
		sutil = new SharePreferenceUtil(WelcomeActivity.this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			ArrayList<Integer> a = RandomBallsUtils.getRandomBalls(6, 32);
			String rst = "";
			for (int i : a) {
				rst += String.valueOf(i) + ",";
			}
			rst = rst.substring(0, rst.length() - 1);
			textView1.setText(rst);
			break;
		case R.id.button2:
			Intent toDouble = new Intent(this, DoubleChromosphere.class);
			startActivity(toDouble);
			break;
		case R.id.button3:
			Intent toMyDouble = new Intent(this, MyDoubleChromosphere.class);
			startActivity(toMyDouble);
			break;
		case R.id.button4:
			Intent to3D = new Intent(this, ThreeDimension.class);
			startActivity(to3D);
			break;
		case R.id.button5:
			Intent tozu = new Intent(this, FootballMixBet.class);
			startActivity(tozu);
			break;
		case R.id.button6:
			Intent qile = new Intent(this, SevenLottery.class);
			startActivity(qile);
			break;
		case R.id.welcomePage:
			// Intent home = new Intent(this, HomeActivity.class);
			// startActivity(home);
			// finish();
			break;
		default:
			break;
		}

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
			super.handleMessage(msg);
			if (msg.what == Constant.PAGE_CHANGED) {
				// Intent toHome = new Intent(WelcomeActivity.this,
				// HomeActivity.class);
				// startActivity(toHome);
				// finish();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				if (msg.arg1 == Constant.NET_ACTION_LOGIN) {
					try {
						try {
							JSONObject ojo = (JSONObject) msg.obj;
							sutil.setSID(ojo.getString("SID"));
							sutil.setdeviceKEY(ojo.getString("deviceKEY"));
							sutil.setSN(finalSN);
							Log.e("pkx", "SN" + finalSN);
							sutil.setIsSNSetted(true);
							isDeviceSet = true;
							Log.e("pkx", "欢迎页设备key 完成");
							// finish();
							// Toast.makeText(WelcomeActivity.this, "设备码获取成功",
							// Toast.LENGTH_LONG).show();
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
					Toast.makeText(WelcomeActivity.this, msg.obj.toString(),
							Toast.LENGTH_SHORT).show();
				}

			}

		}
	}

}
