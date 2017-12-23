package com.pkx.lottery;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.ResetPasswordAuth;
import com.pkx.lottery.dto.SendCaptchaAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class BindPhone extends Activity implements OnClickListener {
	private EditText identText, phoneText;
	private TextView accountText, warmText;
	private ImageButton getIdentBtn;
	private InputMethodManager imm;
	private View bondView;
	private MyHandler mHandler;
	private SharePreferenceUtil sutil;
	private boolean sendFlag;
	public void clickBack(View view) {
		super.onBackPressed();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bind_phone);
		initViews();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.getIdentBtn:
			imm.hideSoftInputFromWindow(phoneText.getWindowToken(), 0);
			sendFlag = false;
			if (phoneText.getText() == null
					|| phoneText.getText().toString() == null
					|| phoneText.getText().toString().length() != 11) {
				phoneText.setError("请输入十一位手机号！");
				return;
				// Toast.makeText(this, "请输入十一位手机号！",
				// Toast.LENGTH_SHORT).show();

			}
			SendCaptchaAuth lau = new SendCaptchaAuth(String.valueOf(phoneText
					.getText()));
			String ljson = Net.gson.toJson(lau);
			String data = MDUtils.MDEncode(sutil.getdeviceKEY(), ljson);
			RequestParams rp = new RequestParams();
			rp.put("SID", sutil.getSID());
			rp.put("SN", sutil.getSN());
			rp.put("DATA", data);
			Net.post(true, this, Constant.POST_URL + "/user.api.php", rp,
					mHandler, Constant.NET_ACTION_CHECKUPDATE);
			break;
		case R.id.bondView:
			if (identText.getText() == null
					|| identText.getText().toString() == null
					|| identText.getText().toString().length() == 0) {
				identText.setError("请输入收到手机验证码！");
				return;
			}
			if (sendFlag) {
				ResetPasswordAuth lau1 = new ResetPasswordAuth(identText
						.getText().toString());
				String ljson1 = Net.gson.toJson(lau1);
				String data1 = MDUtils.MDEncode(sutil.getdeviceKEY(), ljson1);
				RequestParams rp1 = new RequestParams();
				rp1.put("SID", sutil.getSID());
				rp1.put("SN", sutil.getSN());
				rp1.put("DATA", data1);
				Net.post(true, this, Constant.POST_URL + "/user.api.php", rp1,
						mHandler, Constant.NET_ACTION_EDITPWD);
			} else {
				Toast.makeText(BindPhone.this, "获取验证码失败,请稍后重新获取!",
						Toast.LENGTH_SHORT).show();
			}
			break;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!(identText.isSelected() || phoneText.isSelected())) {
			imm.hideSoftInputFromWindow(phoneText.getWindowToken(), 0);
		}
		return super.onTouchEvent(event);
	}

	private void initViews() {
		mHandler = new MyHandler();
		sutil = new SharePreferenceUtil(this);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		identText = (EditText) findViewById(R.id.identText);
		phoneText = (EditText) findViewById(R.id.phoneText);
		accountText = (TextView) findViewById(R.id.accountText);
		warmText = (TextView) findViewById(R.id.warmText);
		getIdentBtn = (ImageButton) findViewById(R.id.getIdentBtn);
		getIdentBtn.setOnClickListener(this);
		bondView = findViewById(R.id.bondView);
		bondView.setOnClickListener(this);
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
			if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				if (msg.arg1 == Constant.NET_ACTION_CHECKUPDATE) {
					Log.e("pkx", "验证：" + msg.obj.toString());
					JSONObject jo = (JSONObject) msg.obj;
					try {
						if (jo.getInt("err") == 1) {
							sendFlag = true;
							Toast.makeText(BindPhone.this,
									"短信发送成功,请在验证码框内输入您手机收到的验证码完成找回密码!",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(BindPhone.this, jo.getString("str"),
									Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else if (msg.arg1 == Constant.NET_ACTION_EDITPWD) {

					Log.e("pkx", "验证：" + msg.obj.toString());
					JSONObject jo = (JSONObject) msg.obj;
					try {
						if (jo.getInt("err") == 1) {// 发送密码成功
							Toast.makeText(BindPhone.this,
									"密码修改成功,请用手机收到的密码进行登录", Toast.LENGTH_LONG)
									.show();
							finish();
						} else {
							Toast.makeText(BindPhone.this, jo.getString("str"),
									Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {

			}
		}
	}
}
