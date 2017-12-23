package com.pkx.lottery;

import org.json.JSONException;
import org.json.JSONObject;
import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.RegistAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;
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
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

public class RegistActivity extends Activity implements OnClickListener {
	private EditText username;
	private EditText password, passwordRepeat,phone;
	private View loginImage, loginBtn;
	private InputMethodManager imm;
	private MyHandler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.regist);
		initViews();
	}

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.password_ed:
		case R.id.username_ed:
		case R.id.phone:
		case R.id.passwordRepeat:
			phone.setCursorVisible(true);
			password.setCursorVisible(true);
			username.setCursorVisible(true);
			passwordRepeat.setCursorVisible(true);
			break;
		case R.id.loginBtn:
			Log.e("pkx", "login--------");
			if (!(password.isSelected() || username.isSelected())) {
				imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
			}
			if (username.getText().toString().length() == 0
					&& password.getText().toString().length() == 0
					&& passwordRepeat.getText().toString().length() == 0) {
				username.setError("账号不能为空");
				password.setError("密码不能为空");
				passwordRepeat.setError("密码不能为空");
				return;
			}
			if (username.getText().toString().length() == 0) {
				username.setError("账号不能为空");
				return;
			}
			if (phone.getText().toString().length() == 0) {
				phone.setError("手机号不能为空");
				return;
			}
			if (password.getText().toString().length() == 0) {
				password.setError("密码不能为空");
				return;
			}
			if (passwordRepeat.getText().toString().length() == 0) {
				passwordRepeat.setError("密码不能为空");
				return;
			}
			if (!passwordRepeat.getText().toString()
					.endsWith(password.getText().toString())) {
				passwordRepeat.setError("确认密码和密码不一致");
				return;
			}
			if (!(password.isSelected() || username.isSelected() || passwordRepeat
					.isSelected()|| phone
					.isSelected())) {
				imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
			}
			RegistAuth rau = new RegistAuth();
			rau.setAccountName(username.getText().toString().trim());
			rau.setAccountPasswd(password.getText().toString().trim());
			rau.setTel(phone.getText().toString().trim());
			final RequestParams params = new RequestParams();
			String mingwen = Net.gson.toJson(rau);
			SharePreferenceUtil sutil = new SharePreferenceUtil(this);
			Log.e("pkx",
					"SID:" + sutil.getSID() + "  dKey:" + sutil.getdeviceKEY());
			String data = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen);
			Log.e("pkx", "mingwen:" + mingwen);
			Log.e("pkx", "miwen:" + data);
			params.put("SID", sutil.getSID());
			params.put("SN", sutil.getSN());
			params.put("DATA", data);
			Net.post(true, RegistActivity.this, Constant.POST_URL
					+ "/user.api.php", params, mHandler,
					Constant.NET_ACTION_REGIST);
			passwordRepeat.setCursorVisible(false);
			password.setCursorVisible(false);
			username.setCursorVisible(false);
			phone.setCursorVisible(false);
			break;
		default:
			break;
		}
	}

	private void initViews() {
		mHandler = new MyHandler();
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		phone = (EditText) this.findViewById(R.id.phone);
		phone.setOnClickListener(this);
		username = (EditText) this.findViewById(R.id.username_ed);
		username.setOnClickListener(this);
		password = (EditText) this.findViewById(R.id.password_ed);
		password.setOnClickListener(this);
		passwordRepeat = (EditText) this.findViewById(R.id.passwordRepeat);
		passwordRepeat.setOnClickListener(this);
		loginImage = findViewById(R.id.login_image);
		loginImage.setOnClickListener(this);
		loginBtn = findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!(password.isSelected() || username.isSelected() || passwordRepeat
				.isSelected())) {
			imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
		}
		return super.onTouchEvent(event);
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
				if (msg.arg1 == Constant.NET_ACTION_REGIST) {
					Toast.makeText(RegistActivity.this, "注册成功",
							Toast.LENGTH_SHORT).show();
					// startActivity(new Intent(RegistActivity.this,
					// LoginActivity.class));
					// try {
					try {
						JSONObject oojo = (JSONObject) msg.obj;
						// if("1".equals(oojo.getString("status"))){
						// 注册成功 JSON:{"status":1,"uid":30,"userKEY":"437072"}
						SharePreferenceUtil sutil = new SharePreferenceUtil(
								RegistActivity.this);
						sutil.setuserKEY(oojo.getString("userKEY"));
						sutil.setuid(oojo.getString("uid"));
						sutil.setLoginStatus(false);
						startActivity(new Intent(RegistActivity.this,
								LoginActivity.class));
						Log.e("pkx", "注册成功");
						// Intent toLogin=new Intent(RegistActivity.this,
						// LoginActivity.class);
						// startActivity(toLogin);
						finish();
						// }
					} catch (JSONException e) {
						Log.e("pkx", "-----------------------JSONException e");
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// }
				}
			} else if (msg.what == Constant.POST_FAIL) {

			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				if (msg.arg1 == Constant.NET_ACTION_REGIST) {
					// NET JSON:{"errorTopic":"该用户名已存在","status":0}NET
					// JSON:{"errorTopic":"该用户名已存在","status":0}
					JSONObject jo = (JSONObject) msg.obj;
					try {
						Toast.makeText(RegistActivity.this,
								jo.getString("errorTopic").toString(),
								Toast.LENGTH_SHORT).show();
					} catch (JSONException e) {
						Toast.makeText(RegistActivity.this, "注册失败",
								Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}

				}
			}

		}
	}

}
