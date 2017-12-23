package com.pkx.lottery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.BetRecord;
import com.pkx.lottery.dto.BuyRecords;
import com.pkx.lottery.dto.ExceptionAuth;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class WebviewActivity extends Activity {
	private Intent mIntent;
	private WebView storeweb;
	private String viewURL;
	private View topLeftView;
	private TextView typeName;
	private int type;
	private AlertDialog alert;
	private SharePreferenceUtil sutil;
	private MyHandler mHandler;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.web_browser);
		initViews();
		mIntent = getIntent();
		viewURL = mIntent.getStringExtra("weburl");
		Log.e("pkx", "url" + mIntent.getStringExtra("weburl"));
		switch (mIntent.getIntExtra("type", 0)) {
		case 1:
			type = 1;
			typeName.setText("购彩协议");
			break;
		}
		storeweb = (WebView) findViewById(R.id.webview);
		storeweb.getSettings().setJavaScriptEnabled(true);
		storeweb.loadUrl(viewURL);
		// storeweb.loadUrl("http://118.192.9.173:10800/test2/");
	}

	@Override
	protected void onResume() {
		// overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
		super.onResume();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		if (type != 1) {
			if (!alert.isShowing()) {
				storeweb.setVisibility(View.INVISIBLE);
				alert.show();
				alert.getWindow().setContentView(R.layout.exit_dialog);
				TextView title = (TextView) alert.findViewById(R.id.title);
				TextView cancelText = (TextView) alert
						.findViewById(R.id.cancelText);
				TextView okText = (TextView) alert.findViewById(R.id.okText);
				cancelText.setText("个人中心");
				okText.setText("去支付");
				title.setText("充值成功？去支付,进入个人中心可查看余额或者继续充值.");
				View okButton = alert.findViewById(R.id.ok);
				okButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (sutil.getLoginStatus()) {
							BetRecord braRE = new BetRecord(sutil.getuid(), 1);
							String mingwenRE = Net.gson.toJson(braRE);
							Log.e("pkx", "明文：" + mingwenRE);
							String miwenRE = MDUtils.MDEncode(
									sutil.getuserKEY(), mingwenRE);
							PublicAllAuth paaRE = new PublicAllAuth(
									"lottery_list", miwenRE);
							String allRE = Net.gson.toJson(paaRE);
							String allmiwenRE = MDUtils.MDEncode(
									sutil.getdeviceKEY(), allRE);
							RequestParams pa = new RequestParams();
							pa.put("SID", sutil.getSID());
							pa.put("SN", sutil.getSN());
							pa.put("DATA", allmiwenRE);
							Net.post(true, WebviewActivity.this,
									Constant.POST_URL + "/user.api.php", pa,
									mHandler, Constant.NET_ACTION_BUYRECORD);
						} else {
							Intent toLogin = new Intent(WebviewActivity.this,
									LoginActivity.class);
							startActivityForResult(toLogin, 0);
						}
						alert.dismiss();
					}
				});

				View cancelButton = alert.findViewById(R.id.cancel);
				cancelButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						if (sutil.getLoginStatus()) {
							Intent toHome = new Intent(WebviewActivity.this,
									HomeActivity.class);
							startActivity(toHome);
							Intent toLogin = new Intent(WebviewActivity.this,
									IndividualCenterActivity.class);
							startActivity(toLogin);

						} else {
							Intent toLogin = new Intent(WebviewActivity.this,
									LoginActivity.class);
							startActivityForResult(toLogin, 0);
						}
						alert.dismiss();
					}
				});
			}
		}
	}

	private void initViews() {
		alert = new AlertDialog.Builder(this).create();
		sutil = new SharePreferenceUtil(this);
		mHandler = new MyHandler();
		typeName = (TextView) findViewById(R.id.typeName);
		topLeftView = findViewById(R.id.topLeftView);
		topLeftView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onDestroy() {
		storeweb.clearView();
		storeweb.destroy();
		super.onDestroy();
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
			if (msg.arg1 == Constant.NET_ACTION_BUYRECORD) {
				JSONObject rejo = (JSONObject) msg.obj;
				Intent tore = new Intent(WebviewActivity.this,
						BuyRecoreds.class);
				try {
					Net.gson.fromJson(rejo.getString("data"), BuyRecords.class);
					try {
						Intent toHome = new Intent(WebviewActivity.this,
								HomeActivity.class);
						startActivity(toHome);
						tore.putExtra("data", rejo.getString("data"));
						startActivity(tore);
						finish();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					try {
						ExceptionAuth ea = Net.gson.fromJson(
								rejo.getString("data"), ExceptionAuth.class);
						if (ea.getPage() == 0 && ea.getPage_sum() == 0) {
							Toast.makeText(WebviewActivity.this, "您没有投注记录",
									Toast.LENGTH_SHORT).show();
						}
					} catch (JsonSyntaxException e1) {
						e1.printStackTrace();
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}

			}
		}
	}

}
