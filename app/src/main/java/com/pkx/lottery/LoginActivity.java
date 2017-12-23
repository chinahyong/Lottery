package com.pkx.lottery;

import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.LoginAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

public class LoginActivity extends Activity implements OnClickListener {
	private EditText username;
	private EditText password;
	private TextView checkLoginText, typeName;
	private View loginImage, loginBtn, registView;
	private ImageView checkLogin;
	private InputMethodManager imm;
	private boolean isLoginAuto;
	private MyHandler mHandler;
	private Intent mIntent;

	public void clickBack(View view) {
		super.onBackPressed();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		mIntent = getIntent();
		initViews();
		if (mIntent.getBooleanExtra("fastlogin", false)) {
			typeName.setText("请先登陆");
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.password_ed:
		case R.id.username_ed:
			password.setCursorVisible(true);
			username.setCursorVisible(true);
			break;
		case R.id.loginBtn:
			// password = null;
			// password.getText();
			String key = "88ut1a3%*r{(d!@qacrf+sBaqODrS&53";
			String content = "疯狂的袋子,APP,{status}";
			Log.e("pkx", MDUtils.MDEncode(key, content));
			MDUtils.MDEncode(key, content);

			if (!(password.isSelected() || username.isSelected())) {
				imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
			}
			// if (username.getText().toString().length() == 0
			// && password.getText().toString().length() == 0) {
			// // username.setError("账号不能为空");
			// // password.setError("密码不能为空");
			// LoginAuth lau = new LoginAuth();
			// lau.setAccountName("shuangseqiu");
			// lau.setAccountPassword("shuang");
			// String ljson = Net.gson.toJson(lau);
			// Log.e("pkx", "login mingwen:" + ljson);
			// SharePreferenceUtil sutil = new SharePreferenceUtil(this);
			// String data = MDUtils.MDEncode(sutil.getdeviceKEY(), ljson);
			// Log.e("pkx", "login miwen:" + data);
			// RequestParams params = new RequestParams();
			// params.put("SID", sutil.getSID());
			// params.put("SN", sutil.getSN());
			// params.put("DATA", data);
			// Net.post(true, LoginActivity.this, Constant.POST_URL
			// + "/user.api.php", params, mHandler,
			// Constant.NET_ACTION_LOGIN);
			// return;
			// }
			if (username.getText().toString().length() == 0) {
				username.setError("账号不能为空");
				return;
			}
			if (password.getText().toString().length() == 0) {
				password.setError("密码不能为空");
				return;
			}
			LoginAuth lau = new LoginAuth();
			lau.setAccountName(username.getText().toString());
			lau.setAccountPassword(password.getText().toString());
			String ljson = Net.gson.toJson(lau);
			Log.e("pkx", "login mingwen:" + ljson);
			SharePreferenceUtil sutil = new SharePreferenceUtil(this);
			String data = MDUtils.MDEncode(sutil.getdeviceKEY(), ljson);
			Log.e("pkx", "login miwen:" + data);
			RequestParams params = new RequestParams();
			params.put("SID", sutil.getSID());
			params.put("SN", sutil.getSN());
			params.put("DATA", data);
			Net.post(true, LoginActivity.this, Constant.POST_URL
					+ "/user.api.php", params, mHandler,
					Constant.NET_ACTION_LOGIN);
			password.setCursorVisible(false);
			username.setCursorVisible(false);
			break;
		case R.id.checkLogin:
		case R.id.checkLoginText:
			if (isLoginAuto) {
				isLoginAuto = false;
				checkLogin.setImageResource(R.drawable.checkbox);
			} else {
				isLoginAuto = true;
				checkLogin.setImageResource(R.drawable.checkbox_this);
			}
			break;
		case R.id.registView:
			Intent toRegist = new Intent(this, RegistActivity.class);
			startActivity(toRegist);
			finish();
			break;
		default:
			break;
		}
	}

	private void initViews() {
		typeName = (TextView) findViewById(R.id.typeName);
		mHandler = new MyHandler();
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		username = (EditText) this.findViewById(R.id.username_ed);
		username.setOnClickListener(this);
		password = (EditText) this.findViewById(R.id.password_ed);
		password.setOnClickListener(this);
		loginImage = findViewById(R.id.login_image);
		loginImage.setOnClickListener(this);
		registView = findViewById(R.id.registView);
		registView.setOnClickListener(this);
		loginBtn = findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(this);
		checkLogin = (ImageView) findViewById(R.id.checkLogin);
		checkLogin.setOnClickListener(this);
		checkLoginText = (TextView) findViewById(R.id.checkLoginText);
		checkLoginText.setOnClickListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!(password.isSelected() || username.isSelected())) {
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
				if (msg.arg1 == Constant.NET_ACTION_LOGIN) {
					try {
						JSONObject ojo = (JSONObject) msg.obj;
						SharePreferenceUtil sutil = new SharePreferenceUtil(
								LoginActivity.this);
						sutil.setuserKEY(ojo.getString("userKEY"));
						sutil.setuid(ojo.getString("uid"));
						sutil.setLoginStatus(true);
						// Intent tohome=new Intent(LoginActivity.this,
						// HomeActivity.class);
						// startActivity(tohome);
						Toast.makeText(LoginActivity.this, "登录成功",
								Toast.LENGTH_SHORT).show();
						setResult(60888);
						finish();
					} catch (ParseException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} else if (msg.what == Constant.POST_FAIL) {
				Toast.makeText(LoginActivity.this, "链接超时！", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				Log.e("pkx", "00000000000000000");
				Toast.makeText(LoginActivity.this, "账号名密码错误！",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

}
