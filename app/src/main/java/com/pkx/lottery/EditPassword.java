package com.pkx.lottery;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.EditPwdAuth;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

public class EditPassword extends Activity implements OnClickListener {
	private EditText username;
	private EditText password, passwordRepeat;
	private View loginImage, loginBtn;
	private InputMethodManager imm;
	private MyHandler mHandler;
	private SharePreferenceUtil sutil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_password);
		initViews();
	}

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.password_ed:
		case R.id.username_ed:
		case R.id.passwordRepeat:
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
			if (password.getText().toString().length() == 0) {
				password.setError("密码不能为空");
				return;
			}
			if (passwordRepeat.getText().toString().length() == 0) {
				passwordRepeat.setError("密码不能为空");
				return;
			}
			if (!(password.isSelected() || username.isSelected() || passwordRepeat
					.isSelected())) {
				imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
			}
			EditPwdAuth eau = new EditPwdAuth(sutil.getuid(), username
					.getText().toString(), password.getText().toString());
			String eauMingwen = Net.gson.toJson(eau);
			String eauMiwen = MDUtils.MDEncode(sutil.getuserKEY(), eauMingwen);
			PublicAllAuth pau = new PublicAllAuth("changePassword", eauMiwen);
			String allMingwen = Net.gson.toJson(pau);
			String allMiwen = MDUtils
					.MDEncode(sutil.getdeviceKEY(), allMingwen);
			RequestParams params = new RequestParams();
			params.put("SID", sutil.getSID());
			params.put("SN", sutil.getSN());
			params.put("DATA", allMiwen);
			Net.post(true, this, Constant.POST_URL+"/user.api.php", params,
					mHandler, Constant.NET_ACTION_EDITPWD);
			passwordRepeat.setCursorVisible(false);
			password.setCursorVisible(false);
			username.setCursorVisible(false);
			break;
		default:
			break;
		}
	}

	private void initViews() {
		mHandler = new MyHandler();
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
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
		sutil = new SharePreferenceUtil(this);
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
				Toast.makeText(EditPassword.this, "密码修改成功",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				Toast.makeText(EditPassword.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

}
