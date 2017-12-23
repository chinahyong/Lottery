package com.pkx.lottery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.CorpAuth;
import com.pkx.lottery.dto.CorpDetailAuth;
import com.pkx.lottery.dto.CorpInfoAuth;
import com.pkx.lottery.dto.CorperBuyAuth;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.dto.corp.CorpChorm;
import com.pkx.lottery.dto.corp.CorpFootballLott;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;
import com.pkx.lottery.view.CircleProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

public class CorperDetails extends Activity implements OnClickListener {
	private Intent mIntent;
	private CorpAuth clickAuth;
	private PullToRefreshScrollView showPullScrollList;
	private SharePreferenceUtil sutil;
	private TextView commission, lottery_type, rest_count, total_price,
			privateText, corPrice, starTime, corpMulti, unitPrice, orderStatus,
			intro, corperName;
	private CircleProgressBar process;
	private EditText buyCount;
	private View handleBet, privateTypeView;
	private MyHandler handler;
	private int privateTypeInt, totalAmount;
	private CorpFootballLott lott;
	private CorpChorm cdetail;
	private LayoutInflater inflater;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.corper_details);
		initViews();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.FAST_LOGIN) {
			if (sutil.getLoginStatus()) {
				Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "未登陆", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.handleBet:
			if (buyCount.getText() == null
					|| buyCount.getText().toString().length() == 0) {
				Toast.makeText(this, "输入份数有误", Toast.LENGTH_SHORT).show();
				return;
			}
			if (Integer.valueOf(buyCount.getText().toString()) > 0
					&& Integer.valueOf(buyCount.getText().toString()) <= totalAmount) {

			} else {
				Toast.makeText(CorperDetails.this, "输入份数有误", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (sutil.getLoginStatus()) {
				try {
					if (Integer.valueOf(buyCount.getText().toString()) > clickAuth
							.getRest_count()) {
						Toast.makeText(
								CorperDetails.this,
								"购买份数超过剩余份数:"
										+ String.valueOf(clickAuth
												.getRest_count()),
								Toast.LENGTH_SHORT).show();
						return;
					} else if (Integer.valueOf(buyCount.getText().toString()) < 1) {
						Toast.makeText(CorperDetails.this, "最少购买一份",
								Toast.LENGTH_SHORT).show();
						return;
					}
				} catch (Exception e) {
					Toast.makeText(CorperDetails.this, "输入错误",
							Toast.LENGTH_SHORT).show();
					return;
				}
				Log.e("pkx", "auth" + clickAuth.getOid() + " restcount:"
						+ clickAuth.getRest_count());
				CorperBuyAuth cba = new CorperBuyAuth(sutil.getuid(),
						clickAuth.getMboid(), buyCount.getText().toString());
				String mingwen = Net.gson.toJson(cba);
				Log.e("pkx", "coper mingwen:" + mingwen);
				String miwen = MDUtils.MDEncode(sutil.getuserKEY(), mingwen);
				PublicAllAuth pa = new PublicAllAuth("createOrder", miwen);
				String allMingwen = Net.gson.toJson(pa);
				Log.e("pkx", "allMingwen" + allMingwen);
				String allMiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allMingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", allMiwen);
				Net.post(true, this, Constant.POST_URL + "/multibuy.api.php",
						params, handler, Constant.NET_ACTION_CORPBUY);
			} else {
				Intent toLogin = new Intent(CorperDetails.this,
						LoginActivity.class);
				toLogin.putExtra("fastlogin", true);
				startActivityForResult(toLogin, Constant.FAST_LOGIN);
			}
			break;
		case R.id.refresh:

			break;
		case R.id.privateTypeView:
			if (sutil.getLoginStatus()) {
				if (privateTypeInt != 2) {
					Toast.makeText(this, "方案不公开", Toast.LENGTH_SHORT).show();
					return;
				}
				CorpDetailAuth bda = new CorpDetailAuth(clickAuth.getMboid());
				// BuyrecordDetail bda;
				// bda = new BuyrecordDetail(sutil.getuid(), clickAuth.getOid(),
				// clickAuth.getMboid());
				String remingwen = Net.gson.toJson(bda);
				Log.e("pkx", "明文：" + remingwen);
				String remiwen = MDUtils
						.MDEncode(sutil.getuserKEY(), remingwen);
				PublicAllAuth repaa = new PublicAllAuth("info", remiwen);
				String allremingwen = Net.gson.toJson(repaa);
				String allremiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allremingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", allremiwen);
				Net.post(true, CorperDetails.this, Constant.POST_URL
						+ "/multibuy.api.php", params, handler,
						Constant.NET_ACTION_USERINFO);
			} else {
				Intent toLogin = new Intent(CorperDetails.this,
						LoginActivity.class);
				toLogin.putExtra("fastlogin", true);
				startActivityForResult(toLogin, Constant.FAST_LOGIN);
			}

			break;

		default:
			break;
		}
	}

	private void initViews() {
		inflater = getLayoutInflater();
		showPullScrollList = (PullToRefreshScrollView) findViewById(R.id.showPullScrollList);
		showPullScrollList
				.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ScrollView> refreshView) {
						CorpInfoAuth cia = new CorpInfoAuth(sutil.getSID(),
								clickAuth.getMboid());
						String infomingwen = Net.gson.toJson(cia);
						Log.e("pkx", "infomingwen:" + infomingwen);
						String infomiwen = MDUtils.MDEncode(sutil.getuserKEY(),
								infomingwen);
						PublicAllAuth paa = new PublicAllAuth("info", infomiwen);
						String infoAllmiwen = Net.gson.toJson(paa);
						String infoAllMi = MDUtils.MDEncode(
								sutil.getdeviceKEY(), infoAllmiwen);
						Log.e("pkx", "" + infoAllmiwen);
						RequestParams params1 = new RequestParams();
						params1.put("SID", sutil.getSID());
						params1.put("SN", sutil.getSN());
						params1.put("DATA", infoAllMi);
						Net.post(true, CorperDetails.this, Constant.POST_URL
								+ "/multibuy.api.php", params1, handler,
								Constant.NET_ACTION_CORPBUY_INFO);

					}

				});
		sutil = new SharePreferenceUtil(this);
		handler = new MyHandler();
		mIntent = getIntent();
		corperName = (TextView) findViewById(R.id.corperName);
		handleBet = findViewById(R.id.handleBet);
		handleBet.setOnClickListener(this);
		privateTypeView = findViewById(R.id.privateTypeView);
		privateTypeView.setOnClickListener(this);
		// refresh = findViewById(R.id.refresh);
		// refresh.setOnClickListener(this);
		clickAuth = (CorpAuth) mIntent.getSerializableExtra("clickCorp");

		if (clickAuth.getNick_name() != null
				&& clickAuth.getNick_name().length() > 0) {
			corperName.setText(clickAuth.getNick_name());
		} else {
			corperName.setText("匿名用户");
		}
		corPrice = (TextView) findViewById(R.id.corPrice);
		starTime = (TextView) findViewById(R.id.starTime);
		unitPrice = (TextView) findViewById(R.id.unitPrice);
		intro = (TextView) findViewById(R.id.intro);
		orderStatus = (TextView) findViewById(R.id.orderStatus);
		intro.setText(clickAuth.getIntro());
		unitPrice.setText("每份" + clickAuth.getUnit_price() + "元");
		starTime.setText(clickAuth.getOrder_date());
		privateText = (TextView) findViewById(R.id.privateText);
		try {
			privateTypeInt = Integer.valueOf(clickAuth.getPrivate_type());
		} catch (Exception e) {
			privateTypeInt = 0;
		}
		switch (privateTypeInt) {
		case 0:// 完全保密
			privateText.setText("完全保密");
			break;
		case 1:// 参与者公开
			privateText.setText("参与者公开");
			break;
		case 2:// 完全公开
			privateText.setText("完全公开");
			break;
		}
		commission = (TextView) findViewById(R.id.commission);
		// keep_count = (TextView) findViewById(R.id.keep_count);
		lottery_type = (TextView) findViewById(R.id.lottery_type);
		process = (CircleProgressBar) findViewById(R.id.process);
		rest_count = (TextView) findViewById(R.id.rest_count);
		buyCount = (EditText) findViewById(R.id.buyCount);
		// TextWatcher watcher = new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before,
		// int count) {
		// Log.e("pkx", "onTextChanged---text:" + s + "  start:" + start
		// + "  before:" + before + " count:" + count);
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		// Log.e("pkx", "beforeTextChanged---text:" + s + "  start:"
		// + start + " count:" + count);
		//
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// if (String.valueOf(s).indexOf(s.length() - 1) == '0'
		// || String.valueOf(s).indexOf(s.length() - 1) == '1'
		// || String.valueOf(s).indexOf(s.length() - 1) == '2'
		// || String.valueOf(s).indexOf(s.length() - 1) == '3'
		// || String.valueOf(s).indexOf(s.length() - 1) == '4'
		// || String.valueOf(s).indexOf(s.length() - 1) == '5'
		// || String.valueOf(s).indexOf(s.length() - 1) == '6'
		// || String.valueOf(s).indexOf(s.length() - 1) == '7'
		// || String.valueOf(s).indexOf(s.length() - 1) == '8'
		// || String.valueOf(s).indexOf(s.length() - 1) == '9') {
		// Log.e("pkx", " is number");
		// } else {
		// Log.e("pkx", " not number");
		// if (s.length() == 1) {
		// s.clear();
		// } else if (s.length() > 1) {
		// s.delete(s.length() - 1, s.length());
		// }
		// }
		// Log.e("pkx", "afterTextChanged---text:" + s);
		// }
		// };
		// buyCount.addTextChangedListener(watcher);
		total_price = (TextView) findViewById(R.id.total_price);

		commission.setText("佣" + clickAuth.getCommission() + "%");
		String lottType = "";
		if ("1".equals(clickAuth.getLottery_type())) {
			lottType = "双色球";
		} else if ("2".equals(clickAuth.getLottery_type())) {
			lottType = "七乐彩";
		} else if ("3".equals(clickAuth.getLottery_type())) {
			lottType = "3D";
		} else if ("50".equals(clickAuth.getLottery_type())) {
			lottType = "竟足";
		} else if ("60".equals(clickAuth.getLottery_type())) {
			lottType = "竟篮";
		}
		Log.e("pkx", clickAuth.getLottery_type());
		lottery_type.setText(lottType);
		// int keep=0;
		// keep=Integer.valueOf(clickAuth.getKeep_count());
		double keepcount = Double.valueOf(clickAuth.getKeep_count());
		int k = 0;
		try {
			if (keepcount > 0) {
				double all = Double.valueOf(clickAuth.getMax_count());
				double percent = keepcount * 100 / all;
				k = Integer.valueOf(String.format("%.0f", percent));
			}
		} catch (Exception e) {
			k = 0;
		}
		if (clickAuth.getProcess() >= 0 && clickAuth.getProcess() <= 100) {
			process.setProgress(clickAuth.getProcess(), k);
		}
		// process.setText(String.valueOf(clickAuth.getProcess()));
		rest_count.setText("未认购:"
				+ String.format(
						"%.1f",
						clickAuth.getRest_count()
								* Double.valueOf(clickAuth.getUnit_price()))
				// String.valueOf(Double.valueOf(corpAythList.get(
				// position).getRest_count())
				// * Double.valueOf(clickAuth
				// .getUnit_price()))
				+ "元");
		totalAmount = Integer.valueOf(clickAuth.getBet_amount().substring(0,
				clickAuth.getBet_amount().length() - 3));
		total_price.setText("方案金额:" + clickAuth.getBet_amount() + "元");
		buyCount.setHint("至少1份(剩余" + String.valueOf(clickAuth.getRest_count())
				+ "份)");
		buyCount.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				if (s.length() == 0) {
					corPrice.setText("共0元");
					return;
				}
				try {
					Log.e("pkx", "buy:" + buyCount.getText().toString()
							+ "  totalAmount:" + totalAmount);
					if (Integer.valueOf(buyCount.getText().toString()) > 0
							&& Integer.valueOf(buyCount.getText().toString()) <= clickAuth
									.getRest_count()) {
						corPrice.setText("共"
								+ String.valueOf(Double.valueOf(clickAuth
										.getUnit_price())
										* Integer.valueOf(buyCount.getText()
												.toString())) + "元");
					} else {
						corPrice.setText("共"
								+ String.valueOf(Double.valueOf(clickAuth
										.getUnit_price())
										* clickAuth.getRest_count()) + "元");
						buyCount.setText(String.valueOf(clickAuth
								.getRest_count()));
					}
				} catch (Exception e) {
					if (buyCount.getText().toString().length() > 0) {
						buyCount.setText(s.toString().subSequence(0,
								s.length() - 1));
					}
					if (corPrice.getText() == null
							|| corPrice.getText().length() == 0) {
						corPrice.setText("共0元");
						return;
					}
					corPrice.setText("共0元");
					// Toast.makeText(CorperDetails.this, "输入份数有误",
					// Toast.LENGTH_SHORT).show();
				}
			}
		});

		// double keep = Double.valueOf(clickAuth.getKeep_count());
		// if (keep > 0) {
		// double all = Double.valueOf(clickAuth.getMax_count());
		// double percent = keep * 100 / all;
		// keep_count.setText(String.format("%.0f", percent) + "%");
		// }

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
				if (msg.arg1 == Constant.NET_ACTION_CORPBUY) {
					// NET
					// json:{"error":0,"message":"\u8bf7\u6c42\u6210\u529f","data":750,"status":1}
					Log.e("pkx", "POST_SUCCESS" + msg.obj);
					JSONObject jo = (JSONObject) msg.obj;
					String orderID = "ID 获取失败";
					String message = "";
					try {

						if ("10001".equals(jo.get("error").toString())) {
							Intent toLogin = new Intent(CorperDetails.this,
									LoginActivity.class);
							toLogin.putExtra("fastlogin", true);
							startActivityForResult(toLogin, Constant.FAST_LOGIN);
						} else if ("20010".equals(jo.get("error").toString())) {
							alertMoneyLess();
							return;

						} else if ("0".equals(jo.get("error").toString())
								|| "20000".equals(jo.get("error").toString())) {
							Toast.makeText(CorperDetails.this, "投注成功",
									Toast.LENGTH_SHORT).show();
							Intent toHome = new Intent(CorperDetails.this,
									HomeActivity.class);
							startActivity(toHome);
						} else if ("10015".equals(jo.get("error").toString())) {
							Toast.makeText(CorperDetails.this, "您不能购买自己发起的合买!",
									Toast.LENGTH_SHORT).show();
						} else {
							Constant.alertWarning(CorperDetails.this,
									jo.get("message").toString());
							Toast.makeText(CorperDetails.this,
									jo.get("message").toString(),
									Toast.LENGTH_LONG).show();
							// message = "error:" + jo.get("error").toString();
							// Toast.makeText(CorperDetails.this,
							// message + " orderID->" + orderID,
							// Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				} else if (msg.arg1 == Constant.NET_ACTION_CORPBUY_INFO) {
					showPullScrollList.onRefreshComplete();
					Log.e("pkx", "POST_SUCCESS" + msg.obj.toString());
				} else if (msg.arg1 == Constant.NET_ACTION_USERINFO) {
					JSONObject detai = (JSONObject) msg.obj;
					if ("50".equals(clickAuth.getLottery_type())) {
						lott = Net.gson.fromJson(detai.toString(),
								CorpFootballLott.class);
						getFootBallBetView();
					} else if ("60".equals(clickAuth.getLottery_type())) {
						lott = Net.gson.fromJson(detai.toString(),
								CorpFootballLott.class);
						getFootBallBetView();
					} else {
						cdetail = Net.gson.fromJson(detai.toString(),
								CorpChorm.class);
						alertDetail(cdetail);
					}
				}

			} else if (msg.what == Constant.POST_FAIL) {
				Toast.makeText(CorperDetails.this, "请求超时", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				Toast.makeText(CorperDetails.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
			}

		}
	}

	BaseAdapter adapter = new BaseAdapter() {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public int getCount() {
			return 0;
		}
	};

	@SuppressLint("NewApi")
	public void getFootBallBetView() {

		final AlertDialog alert = new AlertDialog.Builder(this, R.style.dialog)
				.create();
		alert.show();
		// LinearLayout dialog = (LinearLayout) inflater.inflate(
		// R.layout.corp_detail_dialog, null);
		alert.getWindow().setContentView(R.layout.corp_detail_dialog);
		LinearLayout rect = (LinearLayout) alert.findViewById(R.id.rect);
		TextView chuanGuan = (TextView) alert.findViewById(R.id.chuanGuan);
		chuanGuan.setText("串关:"
				+ lott.getData().getOrder_detail().getChuanGuan());
		// ListView contentList = (ListView)
		// alert.findViewById(R.id.contentList);
		for (int position = 0; position < lott.getData().getOrder_detail()
				.getMatch().size(); position++) {
			// if (convertView == null) {
			// }
			View convertView = inflater.inflate(
					R.layout.buyrecored_football_buy_detail, null);
			TextView hostName, handicPoints, visitName, betString, points, pointsText;
			ImageView danFlag;
			hostName = (TextView) convertView.findViewById(R.id.hostName);
			danFlag = (ImageView) convertView.findViewById(R.id.danFlag);
			handicPoints = (TextView) convertView
					.findViewById(R.id.handicPoints);
			points = (TextView) convertView.findViewById(R.id.points);
			pointsText = (TextView) convertView.findViewById(R.id.pointsText);
			visitName = (TextView) convertView.findViewById(R.id.visitName);
			betString = (TextView) convertView.findViewById(R.id.betString);
			points.setVisibility(View.GONE);
			pointsText.setVisibility(View.GONE);
			if(lott.getData().getOrder_detail().getMatch().get(position).getDan()==1){
				danFlag.setVisibility(View.VISIBLE);
			}else{
				danFlag.setVisibility(View.GONE);
			}
			hostName.setText(lott.getData().getOrder_detail().getMatch()
					.get(position).getMainTeam());
			handicPoints.setText(lott.getData().getOrder_detail().getMatch()
					.get(position).getLetBall());
			visitName.setText(lott.getData().getOrder_detail().getMatch()
					.get(position).getGuestTeam());
			hostName.setText(lott.getData().getOrder_detail().getMatch()
					.get(position).getMainTeam());
			betString.setText(lott.getData().getOrder_detail().getMatch()
					.get(position).getTz());
			rect.addView(convertView);
		}

	}

	private void alertMoneyLess() {
		final AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.show();
		alert.getWindow().setContentView(R.layout.exit_dialog);

		TextView title = (TextView) alert.findViewById(R.id.title);
		title.setText("账户余额不足，进入充值界面？");
		View okButton = alert.findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent();
				intent1.putExtra("weburl", Constant.PAY_URL
						+ "/wapPay/index.php?uid=" + sutil.getuid());
				// "http://www.yuebao100.com/weixin/store/inedx/15811461125");
				intent1.setClass(CorperDetails.this, WebviewActivity.class);
				startActivity(intent1);
				alert.dismiss();
			}
		});

		View cancelButton = alert.findViewById(R.id.cancel);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});
	}

	@SuppressLint("NewApi")
	private void alertDetail(CorpChorm s) {
		final AlertDialog alert = new AlertDialog.Builder(this, R.style.dialog)
				.create();
		alert.show();
		alert.getWindow().setContentView(R.layout.corp_detail_dialog);
		TextView chuanGuan = (TextView) alert.findViewById(R.id.chuanGuan);
		chuanGuan.setVisibility(View.GONE);
		LinearLayout rect = (LinearLayout) alert.findViewById(R.id.rect);
		for (int position = 0; position < s.getData().getOrder_detail().size(); position++) {
			// if (convertView == null) {
			// }
			View convertView = inflater.inflate(R.layout.num_lott_item, null);
			TextView nums;
			nums = (TextView) convertView.findViewById(R.id.nums);
			nums.setText(s.getData().getOrder_detail().get(position)
					.getBetStr()
					+ ":"
					+ s.getData().getOrder_detail().get(position)
							.getBetData_str());
			rect.addView(convertView);
		}
	}
}
