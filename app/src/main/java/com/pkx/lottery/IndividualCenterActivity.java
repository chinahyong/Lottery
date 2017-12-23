package com.pkx.lottery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.AccountDetailAuth;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.dto.UserInfoAllAuth;
import com.pkx.lottery.dto.UserInfoAuth;
import com.pkx.lottery.dto.UserInfoDto;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

public class IndividualCenterActivity extends Activity implements
		OnClickListener {
	private EditText username;
	private EditText password;
	private InputMethodManager imm;
	private MyHandler mHandler;
	private Intent mIntent;
	private SharePreferenceUtil sutil;
	private PullToRefreshScrollView individualPullList;
	private TextView fund, blocked_fund, fund_cash, charging, cashing, info,
			account, cashingRecoreds, accountDetails;
	private UserInfoDto userinfo;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.inividual);
		mIntent = getIntent();
		initViews();

		// SharePreferenceUtil sutil = new SharePreferenceUtil(this);
		UserInfoAuth usinfo = new UserInfoAuth(sutil.getuid());
		String userInfiMingwen = Net.gson.toJson(usinfo);
		String userInfoMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
				userInfiMingwen);
		UserInfoAllAuth usall = new UserInfoAllAuth();
		usall.setDATA(userInfoMiwen);
		String userInfoAllMingwen = Net.gson.toJson(usall);
		String userInfoAllMiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
				userInfoAllMingwen);
		RequestParams params = new RequestParams();
		params.put("SID", sutil.getSID());
		params.put("DATA", userInfoAllMiwen);
		Log.e("pkx",
				"SID:" + sutil.getSID() + " uid:" + sutil.getuid() + " userKEY"
						+ sutil.getuserKEY() + " deviceKEY"
						+ sutil.getdeviceKEY() + " info1:" + userInfiMingwen
						+ "info1 mi:" + userInfoMiwen + " info2:"
						+ userInfoAllMingwen + " info2 mi:" + userInfoAllMiwen);
		Net.post(true, this, Constant.POST_URL + "/user.api.php", params,
				mHandler, Constant.NET_ACTION_USERINFO);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		UserInfoAuth usinfo = new UserInfoAuth(sutil.getuid());
		String userInfiMingwen = Net.gson.toJson(usinfo);
		String userInfoMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
				userInfiMingwen);
		UserInfoAllAuth usall = new UserInfoAllAuth();
		usall.setDATA(userInfoMiwen);
		String userInfoAllMingwen = Net.gson.toJson(usall);
		String userInfoAllMiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
				userInfoAllMingwen);
		RequestParams params = new RequestParams();
		params.put("SID", sutil.getSID());
		params.put("DATA", userInfoAllMiwen);
		Log.e("pkx",
				"SID:" + sutil.getSID() + " uid:" + sutil.getuid() + " userKEY"
						+ sutil.getuserKEY() + " deviceKEY"
						+ sutil.getdeviceKEY() + " info1:" + userInfiMingwen
						+ "info1 mi:" + userInfoMiwen + " info2:"
						+ userInfoAllMingwen + " info2 mi:" + userInfoAllMiwen);
		Net.post(true, this, Constant.POST_URL + "/user.api.php", params,
				mHandler, Constant.NET_ACTION_USERINFO);
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.charging:
			Intent intent1 = new Intent();
			Log.e("pkx",
					Constant.PAY_URL + "/wapPay/index.php?uid="
							+ sutil.getuid());
			intent1.putExtra("weburl", Constant.PAY_URL
					+ "/wapPay/index.php?uid=" + sutil.getuid());
			intent1.setClass(IndividualCenterActivity.this,
					WebviewActivity.class);
			startActivityForResult(intent1, 0);
			break;
		case R.id.cashing:
			// Toast.makeText(this, "周一完成", Toast.LENGTH_SHORT).show();
			startActivityForResult(new Intent(this, Cashing.class), 0);
			break;
		case R.id.cashingRecoreds:
			// Toast.makeText(this, "周一完成", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(this, CashingRecoreds.class));
			break;
		case R.id.info:
			if (userinfo != null) {
				Intent toInfo = new Intent(this, EditUserInfo.class);
				toInfo.putExtra("info", Net.gson.toJson(userinfo));
				startActivityForResult(toInfo, 0);
			}
			break;
		case R.id.accountDetails:
			startActivity(new Intent(this, AccountDetails.class));
			break;
		}
	}

	private void initViews() {
		sutil = new SharePreferenceUtil(this);
		mHandler = new MyHandler();
		account = (TextView) findViewById(R.id.account);
		accountDetails = (TextView) findViewById(R.id.accountDetails);
		cashingRecoreds = (TextView) findViewById(R.id.cashingRecoreds);
		fund = (TextView) findViewById(R.id.fund);
		blocked_fund = (TextView) findViewById(R.id.blocked_fund);
		fund_cash = (TextView) findViewById(R.id.fund_cash);
		charging = (TextView) findViewById(R.id.charging);
		cashing = (TextView) findViewById(R.id.cashing);
		info = (TextView) findViewById(R.id.info);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		charging.setOnClickListener(this);
		cashing.setOnClickListener(this);
		info.setOnClickListener(this);
		cashingRecoreds.setOnClickListener(this);
		accountDetails.setOnClickListener(this);
	}

	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// // if (!(password.isSelected() || username.isSelected())) {
	// // imm.hideSoftInputFromWindow(password.getWindowToken(), 0);
	// // }
	// return super.onTouchEvent(event);
	// }

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
				if (msg.arg1 == Constant.NET_ACTION_USERINFO) {
					Log.e("pkx", "个人中心:" + msg.obj.toString());
					userinfo = Net.gson.fromJson(msg.obj.toString(),
							UserInfoDto.class);
					fund.setText("¥"
							+ String.valueOf(userinfo.getUserInfo().getFund()));
					fund_cash.setText("¥"
							+ String.valueOf(userinfo.getUserInfo().getFund()));
					blocked_fund.setText("¥"
							+ String.valueOf(userinfo.getUserInfo()
									.getBlocked_fund()));
					account.setText(String.valueOf(userinfo.getUserInfo()
							.getAccount()));
					Log.e("pkx", "个人信息:" + userinfo.getUserInfo().getAccount()
							+ "  余额:" + userinfo.getUserInfo().getFund()
							+ " 真实姓名:" + userinfo.getUserInfo().getReal_name());

				}
			} else if (msg.what == Constant.POST_FAIL) {
				Toast.makeText(IndividualCenterActivity.this, "链接超时！",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				Log.e("pkx", "00000000000000000");
				Toast.makeText(IndividualCenterActivity.this, "连接错误！",
						Toast.LENGTH_SHORT).show();
			}

		}
	}

}
