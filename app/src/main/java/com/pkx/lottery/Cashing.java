package com.pkx.lottery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.CashAuth;
import com.pkx.lottery.dto.CashCheckDto;
import com.pkx.lottery.dto.CheckSercuriyAuth;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.dto.QuestionAuth;
import com.pkx.lottery.dto.QuestionListAuth;
import com.pkx.lottery.dto.SendCaptchaAuth;
import com.pkx.lottery.dto.UserInfoAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class Cashing extends Activity implements OnClickListener {
	private InputMethodManager imm;
	private View bondView;
	private MyHandler mHandler;
	private SharePreferenceUtil sutil;
	private TextView question, submitText;
	private EditText answer, cash;
	private View anwserView, cashView;
	private boolean getQuestion, firstStep = true;
	private QuestionAuth quesionDto;
	private CashCheckDto check;
	private boolean hasNosercurity = true;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cashing);
		initViews();
		if (sutil.getLoginStatus()) {
			UserInfoAuth ua = new UserInfoAuth(sutil.getuid());
			String mingwen = Net.gson.toJson(ua);
			String miwen = MDUtils.MDEncode(sutil.getuserKEY(), mingwen);
			PublicAllAuth paa = new PublicAllAuth("getSecretSecurity", miwen);
			String allmingwen = Net.gson.toJson(paa);
			String allmiwen = MDUtils
					.MDEncode(sutil.getdeviceKEY(), allmingwen);
			RequestParams params = new RequestParams();
			params.put("SID", sutil.getSID());
			params.put("SN", sutil.getSN());
			params.put("DATA", allmiwen);
			Net.post(true, this, Constant.POST_URL + "/user.api.php", params,
					mHandler, Constant.NET_ACTION_SECRET_SECURE);
		} else {
			Intent toLogin = new Intent(this, LoginActivity.class);
			startActivityForResult(toLogin, 0);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.getIdentBtn:
			imm.hideSoftInputFromWindow(bondView.getWindowToken(), 0);
			SendCaptchaAuth lau = new SendCaptchaAuth("");
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
			imm.hideSoftInputFromWindow(bondView.getWindowToken(), 0);
			if (hasNosercurity) {
				Toast.makeText(Cashing.this, "您未设置密保!", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (firstStep) {// 验证密保
				CheckSercuriyAuth lauc = new CheckSercuriyAuth(
						quesionDto.getId(), answer.getText().toString(),
						quesionDto.getUid());
				String ljsonc = Net.gson.toJson(lauc);
				Log.e("pkx", ljsonc);
				String miwen = MDUtils.MDEncode(sutil.getuserKEY(), ljsonc);
				PublicAllAuth pa = new PublicAllAuth(
						"withdrawal_secret_security_a", miwen);
				String datac = MDUtils.MDEncode(sutil.getdeviceKEY(),
						Net.gson.toJson(pa));
				RequestParams rpc = new RequestParams();
				rpc.put("SID", sutil.getSID());
				rpc.put("SN", sutil.getSN());
				rpc.put("DATA", datac);
				Net.post(true, this, Constant.POST_URL + "/user.api.php", rpc,
						mHandler, Constant.NET_ACTION_BUYRECORD);
			} else {// 提现
				// CheckSercuriyAuth lauc = new CheckSercuriyAuth(
				// quesionDto.getId(), answer.getText().toString(),
				// quesionDto.getUid());
				if (check == null || check.getToken() == null) {
					Toast.makeText(Cashing.this, "密保验证失败!", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				try {
					if (cash.getText() == null
							|| cash.getText().toString() == null
							|| Integer.valueOf(cash.getText().toString()) <= 0) {
						Toast.makeText(Cashing.this, "输入正确的提现金额!",
								Toast.LENGTH_SHORT).show();
						return;
					}
				} catch (Exception e) {
					Toast.makeText(Cashing.this, "提现金额输入有误!",
							Toast.LENGTH_SHORT).show();
					return;
				}

				CashAuth lauc = new CashAuth(check.getToken(), cash.getText()
						.toString(), sutil.getuid());
				String ljsonc = Net.gson.toJson(lauc);
				Log.e("pkx", ljsonc);
				String miwen = MDUtils.MDEncode(sutil.getuserKEY(), ljsonc);
				PublicAllAuth pa = new PublicAllAuth("apply_withdrawal", miwen);
				String datac = MDUtils.MDEncode(sutil.getdeviceKEY(),
						Net.gson.toJson(pa));
				RequestParams rpc = new RequestParams();
				rpc.put("SID", sutil.getSID());
				rpc.put("SN", sutil.getSN());
				rpc.put("DATA", datac);
				Net.post(true, this, Constant.POST_URL + "/user.api.php", rpc,
						mHandler, Constant.NET_ACTION_CHORMBET);
			}
			break;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		imm.hideSoftInputFromWindow(bondView.getWindowToken(), 0);
		return super.onTouchEvent(event);
	}

	private void initViews() {
		mHandler = new MyHandler();
		sutil = new SharePreferenceUtil(this);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		bondView = findViewById(R.id.bondView);
		bondView.setOnClickListener(this);
		question = (TextView) findViewById(R.id.question);
		submitText = (TextView) findViewById(R.id.submitText);
		answer = (EditText) findViewById(R.id.answer);
		cash = (EditText) findViewById(R.id.cash);
		anwserView = findViewById(R.id.anwserView);
		cashView = findViewById(R.id.cashView);
		// private TextView question,submitText;
		// private EditText answer,cash;
		// private View anwserView,cashView;
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
							Toast.makeText(Cashing.this,
									"短信发送成功,请在验证码框内输入您手机收到的验证码完成找回密码!",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(Cashing.this, jo.getString("str"),
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
							Toast.makeText(Cashing.this,
									"密码修改成功,请用手机收到的密码进行登录", Toast.LENGTH_LONG)
									.show();
							finish();
						} else {
							Toast.makeText(Cashing.this, jo.getString("str"),
									Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else if (msg.arg1 == Constant.NET_ACTION_SECRET_SECURE) {
					JSONObject jo = (JSONObject) msg.obj;
					try {
						if (jo.getInt("num") <= 0) {
							Toast.makeText(Cashing.this, "您未设置密保!",
									Toast.LENGTH_LONG).show();
							return;
						} else {
							hasNosercurity = false;
						}
					} catch (JSONException e1) {
						Toast.makeText(Cashing.this, "密保异常!", Toast.LENGTH_LONG)
								.show();
						e1.printStackTrace();
						return;
					}
					try {
						QuestionListAuth listAuth = Net.gson.fromJson(
								msg.obj.toString(), QuestionListAuth.class);
						quesionDto = listAuth.getSecret_security().get(
								new Random().nextInt(listAuth
										.getSecret_security().size()));
						question.setText(quesionDto.getSecret_security());
						getQuestion = true;
					} catch (Exception e) {
						hasNosercurity = false;
						Toast.makeText(Cashing.this, "获取密保异常!",
								Toast.LENGTH_LONG).show();
					}
				} else if (msg.arg1 == Constant.NET_ACTION_BUYRECORD) {
					try {
						check = Net.gson.fromJson(msg.obj.toString(),
								CashCheckDto.class);
						if ("1".equals(check.getErr())) {// 密保验证成功
							submitText.setText("提现");
							firstStep = false;
							anwserView.setVisibility(View.GONE);
							cashView.setVisibility(View.VISIBLE);
						} else {
							Toast.makeText(Cashing.this, "" + check.getMsg(),
									Toast.LENGTH_SHORT).show();
						}
					} catch (Exception e) {
						Toast.makeText(Cashing.this, "验证异常!", Toast.LENGTH_LONG)
								.show();
					}
				} else if (msg.arg1 == Constant.NET_ACTION_CHORMBET) {
					JSONObject jo = (JSONObject) msg.obj;
					try {
						if ("1".equals(jo.getString("err"))) {
							Toast.makeText(Cashing.this, "操作成功",
									Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Toast.makeText(Cashing.this,
									"" + jo.getString("msg"),
									Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						Toast.makeText(Cashing.this, "操作异常", Toast.LENGTH_SHORT)
								.show();
						e.printStackTrace();
					}
				}

			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {

			}
		}
	}
}
