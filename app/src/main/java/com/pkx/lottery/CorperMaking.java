package com.pkx.lottery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.ChormBetAuth;
import com.pkx.lottery.dto.ChormCorpBetAuth;
import com.pkx.lottery.dto.FootballBetAuth;
import com.pkx.lottery.dto.FootballCorpBetAuth;
import com.pkx.lottery.dto.IndexListDto;
import com.pkx.lottery.dto.IndexListItemDto;
import com.pkx.lottery.dto.IndexLotteryInfo;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CorperMaking extends Activity implements OnClickListener {
	private Intent mIntent;
	private SharePreferenceUtil sutil;
	private MyHandler mHandler;
	private Message watchMsg;
	private int yellowText = Color.parseColor("#fd5a21");
	private int whiteText = Color.parseColor("#ffffff");
	private View comitView, percentView1, percentView2, percentView3,
			percentView4, percentView5, percentView6, percentView7,
			percentView8, percentView9, percentView10, corp_totalOpen,
			corp_totalClose, corp_openAfterStop, corp_openToCoper;
	private ArrayList<View> percentViews, secretViews;
	private ArrayList<TextView> serectTexts;
	private int selectPercent, selectSecret;
	private TextView typeName, lottTime, totalPrice, resultText,
			corp_totalOpenText, corp_totalCloseText, corp_openAfterStopText,
			corp_openToCoperText;
	private EditText selfBuy, leastBuy, declare;
	private String allMiwen;
	private int type;
	private int totalPriceNum;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.corp_page);
		initViews();
		IndexLotteryInfo info = new IndexLotteryInfo();
		String mingwen = Net.gson.toJson(info);
		String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen);
		RequestParams params = new RequestParams();
		params.put("SID", sutil.getSID());
		params.put("SN", sutil.getSN());
		params.put("DATA", miwen);
		Net.post(false, this, Constant.POST_URL + "/lottery.api.php", params,
				mHandler, Constant.NET_ACTION_REGIST);
	}

	private void checkBill() {
		String least = leastBuy.getText().toString();
		String self = selfBuy.getText().toString();
		if (least == null || least.length() == 0) {
			least = "0";
		}
		if (self == null || self.length() == 0) {
			self = "0";
		}
		String privateType = "";
		switch (selectSecret) {
		case 0:
			privateType = "完全公开";
			break;
		case 1:
			privateType = "完全保密";
			break;
		case 2:
			privateType = "参与者公开";
			break;
		case 3:
			privateType = "截止后公开";
			break;

		}
		int sbuy = 0;
		int lbuy = 0;
		try {
			sbuy = Integer.valueOf(selfBuy.getText().toString());
		} catch (Exception e) {
			sbuy = 0;
		}
		try {
			lbuy = Integer.valueOf(leastBuy.getText().toString());
		} catch (Exception e) {
			lbuy = 0;
		}
		Log.e("pkx", "方案内容" + privateType + "，佣金"
				+ String.valueOf(selectPercent) + "%，认购"
				+ self + "元，保底" + least + "元，共"
				+ String.valueOf(sbuy + lbuy) + "元");
		resultText.setText("方案内容" + privateType + "，佣金"
				+ String.valueOf(selectPercent) + "%，认购"
				+ self + "元，保底" + least + "元，共"
				+ String.valueOf(sbuy + lbuy) + "元");
	}

	@Override
	protected void onResume() {
		checkBill();
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.percentView1:
		case R.id.percentView2:
		case R.id.percentView3:
		case R.id.percentView4:
		case R.id.percentView5:
		case R.id.percentView6:
		case R.id.percentView7:
		case R.id.percentView8:
		case R.id.percentView9:
		case R.id.percentView10:
			if ((Integer) v.getTag() + 1 == selectPercent) {
				percentViews.get(selectPercent - 1).setBackgroundResource(
						R.drawable.percent);
				selectPercent = 0;
				checkBill();
				return;
			}
			if (selectPercent > 0)
				percentViews.get(selectPercent - 1).setBackgroundResource(
						R.drawable.percent);
			selectPercent = (Integer) v.getTag() + 1;
			percentViews.get((Integer) v.getTag()).setBackgroundResource(
					R.drawable.percent_dowm);
			checkBill();
			break;
		case R.id.corp_totalOpen:
		case R.id.corp_totalClose:
		case R.id.corp_openAfterStop:
		case R.id.corp_openToCoper:
			Log.e("pkx", "选前 select:" + selectSecret);
			if (selectSecret >= 0) {
				secretViews.get(selectSecret).setBackgroundResource(
						R.drawable.corp_orange_rect);
				serectTexts.get(selectSecret).setTextColor(yellowText);
			}
			selectSecret = (Integer) v.getTag();
			serectTexts.get((Integer) v.getTag()).setTextColor(whiteText);
			secretViews.get((Integer) v.getTag()).setBackgroundResource(
					R.drawable.corp_orange);
			checkBill();
			Log.e("pkx", "选后 select:" + selectSecret);
			break;
		case R.id.comitView:
			Log.e("pkx", "佣金：" + selectPercent + "%  认购："
					+ selfBuy.getText().toString() + " 总金额totalNum："
					+ totalPriceNum);
			try {
				if ((Double.valueOf(totalPrice.getText().toString()) / Double
						.valueOf(selfBuy.getText().toString())) > 100.0) {
					Toast.makeText(this, "至少认购总额1%", Toast.LENGTH_SHORT).show();
					return;
				} else if (Integer.valueOf(selfBuy.getText().toString()) >= totalPriceNum) {
					Toast.makeText(this, "认购总额不能大于方案总额", Toast.LENGTH_SHORT)
							.show();
					return;
				} else if (Integer.valueOf(leastBuy.getText().toString()) >= totalPriceNum) {
					Toast.makeText(this, "保底总额不能大于方案总额", Toast.LENGTH_SHORT)
							.show();
					return;
				} else if (Integer.valueOf(leastBuy.getText().toString()) == 0) {
					Toast.makeText(this, "至少保底1元", Toast.LENGTH_SHORT).show();
					return;
				} else if ((Integer.valueOf(leastBuy.getText().toString()) + Integer
						.valueOf(selfBuy.getText().toString())) >= totalPriceNum) {
					Toast.makeText(this, "认购保底之和不能大于方案总额", Toast.LENGTH_SHORT)
							.show();
					return;
				} else if (selectPercent > 0
						&& selectPercent * totalPriceNum > Integer
								.valueOf(selfBuy.getText().toString()) * 100) {
					Toast.makeText(this, "设置佣金后至少认购与佣金等比份额", Toast.LENGTH_SHORT)
							.show();
					return;
				}
			} catch (Exception e) {
				Toast.makeText(this, "认购或保底金额输入错误", Toast.LENGTH_SHORT).show();
				return;
			}
			String intro = null;
			if (declare.getText() == null || declare.getText().length() == 0) {
				intro = "我出方案,你受益~";
			} else {
				intro = declare.getText().toString();
			}
			switch (type) {
			case 1:
				Log.e("pkx", "allmingwen:" + allMiwen);
				ChormBetAuth co = Net.gson.fromJson(allMiwen,
						ChormBetAuth.class);
				ChormCorpBetAuth corp = new ChormCorpBetAuth(co.getUid(),
						co.getBuy_amount(), co.getBuy_count(),
						co.getBuy_multiy(), co.getBetInfo(), "1");
				corp.setBuy_type("1");
				corp.setFangan_baodi(leastBuy.getText().toString());
				corp.setFangan_num(totalPrice.getText().toString());
				corp.setFangan_self(selfBuy.getText().toString());
				corp.setFangan_title(intro);
				corp.setFangan_yongjin(String.valueOf(selectPercent));
				if (selectSecret == 0) {
					corp.setFangan_show("2");
				} else if (selectSecret == 1) {
					corp.setFangan_show("0");
				} else if (selectSecret == 2) {
					corp.setFangan_show("1");
				}
				Log.e("pkx", "select:" + String.valueOf(selectSecret));
				// if (selectSecret == 3) {
				// corp.setFangan_show(String.valueOf(selectSecret - 1));
				// } else {
				// corp.setFangan_show(String.valueOf(selectSecret));
				// }
				String chroCorpMinwen = Net.gson.toJson(corp);
				Log.e("pkx", "提交：" + chroCorpMinwen);
				Log.e("pkx", "  type--->" + corp.getBuy_type());
				String chromMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
						chroCorpMinwen);
				PublicAllAuth all = new PublicAllAuth("betSSQ", chromMiwen);
				String allMingwen = Net.gson.toJson(all);
				String allMiwen1 = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allMingwen);
				RequestParams params = new RequestParams();
				Log.e("pkx", "SID:" + sutil.getSID());
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", allMiwen1);
				Net.post(true, this, Constant.POST_URL + "/bet.api.php",
						params, mHandler, Constant.NET_ACTION_CHORMBET);
				break;

			case 2:
				FootballBetAuth fba = Net.gson.fromJson(allMiwen,
						FootballBetAuth.class);
				FootballCorpBetAuth fcba = new FootballCorpBetAuth(
						fba.getUid(), fba.getBuy_amount(), fba.getBuy_count(),
						fba.getBuy_multiy(), fba.getBetInfo(), "1");
				fcba.setFangan_baodi(leastBuy.getText().toString());
				fcba.setFangan_num(totalPrice.getText().toString());
				fcba.setFangan_self(selfBuy.getText().toString());
				fcba.setFangan_title(intro);
				fcba.setFangan_yongjin(String.valueOf(selectPercent));
				if (selectSecret == 0) {
					fcba.setFangan_show("2");
				} else if (selectSecret == 1) {
					fcba.setFangan_show("0");
				} else if (selectSecret == 2) {
					fcba.setFangan_show("1");
				}
				String footMinwen = Net.gson.toJson(fcba);
				Log.e("pkx", "提交：" + footMinwen);
				String footMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
						footMinwen);
				PublicAllAuth footall = new PublicAllAuth("betJCZQ", footMiwen);
				String footallMingwen = Net.gson.toJson(footall);
				String foofallMiwen1 = MDUtils.MDEncode(sutil.getdeviceKEY(),
						footallMingwen);
				RequestParams params1 = new RequestParams();
				Log.e("pkx", "SID:" + sutil.getSID());
				params1.put("SID", sutil.getSID());
				params1.put("SN", sutil.getSN());
				params1.put("DATA", foofallMiwen1);
				Net.post(true, this, Constant.POST_URL + "/bet.api.php",
						params1, mHandler, Constant.NET_ACTION_FOOTBALLMIX);
				break;
			case 3:
				ChormBetAuth sevenau = Net.gson.fromJson(allMiwen,
						ChormBetAuth.class);
				ChormCorpBetAuth corps = new ChormCorpBetAuth(sevenau.getUid(),
						sevenau.getBuy_amount(), sevenau.getBuy_count(),
						sevenau.getBuy_multiy(), sevenau.getBetInfo(), "1");
				corps.setBuy_type("1");
				corps.setFangan_baodi(leastBuy.getText().toString());
				corps.setFangan_num(totalPrice.getText().toString());
				corps.setFangan_self(selfBuy.getText().toString());
				corps.setFangan_title(intro);
				corps.setFangan_yongjin(String.valueOf(selectPercent));
				if (selectSecret == 0) {
					corps.setFangan_show("2");
				} else if (selectSecret == 1) {
					corps.setFangan_show("0");
				} else if (selectSecret == 2) {
					corps.setFangan_show("1");
				}
				String chroCorpMinwen1 = Net.gson.toJson(corps);
				Log.e("pkx", "提交：" + chroCorpMinwen1);
				Log.e("pkx", "  type--->" + corps.getBuy_type());
				String chromMiwen2 = MDUtils.MDEncode(sutil.getuserKEY(),
						chroCorpMinwen1);
				PublicAllAuth all2 = new PublicAllAuth("betQLC", chromMiwen2);
				String allMingwen2 = Net.gson.toJson(all2);
				String allMiwen12 = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allMingwen2);
				RequestParams params2 = new RequestParams();
				Log.e("pkx", "SID:" + sutil.getSID());
				params2.put("SID", sutil.getSID());
				params2.put("SN", sutil.getSN());
				params2.put("DATA", allMiwen12);
				Net.post(true, this, Constant.POST_URL + "/bet.api.php",
						params2, mHandler, Constant.NET_ACTION_SENCENBET);
				break;
			case 4:
				ChormBetAuth sevenau3 = Net.gson.fromJson(allMiwen,
						ChormBetAuth.class);
				ChormCorpBetAuth corps3 = new ChormCorpBetAuth(
						sevenau3.getUid(), sevenau3.getBuy_amount(),
						sevenau3.getBuy_count(), sevenau3.getBuy_multiy(),
						sevenau3.getBetInfo(), "1");
				corps3.setBuy_type("1");
				corps3.setFangan_baodi(leastBuy.getText().toString());
				corps3.setFangan_num(totalPrice.getText().toString());
				corps3.setFangan_self(selfBuy.getText().toString());
				corps3.setFangan_title(intro);
				corps3.setFangan_yongjin(String.valueOf(selectPercent));
				if (selectSecret == 0) {
					corps3.setFangan_show("2");
				} else if (selectSecret == 1) {
					corps3.setFangan_show("0");
				} else if (selectSecret == 2) {
					corps3.setFangan_show("1");
				}
				String chroCorpMinwen13 = Net.gson.toJson(corps3);
				Log.e("pkx", "提交：" + chroCorpMinwen13);
				Log.e("pkx", "  type--->" + corps3.getBuy_type());
				String chromMiwen23 = MDUtils.MDEncode(sutil.getuserKEY(),
						chroCorpMinwen13);
				PublicAllAuth all23 = new PublicAllAuth("bet3D", chromMiwen23);
				String allMingwen23 = Net.gson.toJson(all23);
				String allMiwen123 = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allMingwen23);
				RequestParams params23 = new RequestParams();
				Log.e("pkx", "SID:" + sutil.getSID());
				params23.put("SID", sutil.getSID());
				params23.put("SN", sutil.getSN());
				params23.put("DATA", allMiwen123);
				Net.post(true, this, Constant.POST_URL + "/bet.api.php",
						params23, mHandler, Constant.NET_ACTION_SENCENBET);
				break;
			case 5:
				FootballBetAuth fbab = Net.gson.fromJson(allMiwen,
						FootballBetAuth.class);
				FootballCorpBetAuth fcbab = new FootballCorpBetAuth(
						fbab.getUid(), fbab.getBuy_amount(),
						fbab.getBuy_count(), fbab.getBuy_multiy(),
						fbab.getBetInfo(), "1");
				fcbab.setFangan_baodi(leastBuy.getText().toString());
				fcbab.setFangan_num(totalPrice.getText().toString());
				fcbab.setFangan_self(selfBuy.getText().toString());
				fcbab.setFangan_title(intro);
				fcbab.setFangan_yongjin(String.valueOf(selectPercent));
				if (selectSecret == 0) {
					fcbab.setFangan_show("2");
				} else if (selectSecret == 1) {
					fcbab.setFangan_show("0");
				} else if (selectSecret == 2) {
					fcbab.setFangan_show("1");
				}
				String footMinwenb = Net.gson.toJson(fcbab);
				Log.e("pkx", "篮球合买提交：" + footMinwenb);
				String footMiwenb = MDUtils.MDEncode(sutil.getuserKEY(),
						footMinwenb);
				PublicAllAuth footallb = new PublicAllAuth("betJCLQ",
						footMiwenb);
				String footallMingwenb = Net.gson.toJson(footallb);
				String foofallMiwen1b = MDUtils.MDEncode(sutil.getdeviceKEY(),
						footallMingwenb);
				RequestParams params1b = new RequestParams();
				Log.e("pkx", "篮球合买SID:" + sutil.getSID());
				params1b.put("SID", sutil.getSID());
				params1b.put("SN", sutil.getSN());
				params1b.put("DATA", foofallMiwen1b);
				Net.post(true, this, Constant.POST_URL + "/bet.api.php",
						params1b, mHandler, Constant.NET_ACTION_FOOTBALLMIX);
				break;
			}
			break;
		}
	}

	private void initViews() {
		mIntent = getIntent();
		mHandler = new MyHandler();
		sutil = new SharePreferenceUtil(this);
		comitView = findViewById(R.id.comitView);
		percentView1 = findViewById(R.id.percentView1);
		percentView2 = findViewById(R.id.percentView2);
		percentView3 = findViewById(R.id.percentView3);
		percentView4 = findViewById(R.id.percentView4);
		percentView5 = findViewById(R.id.percentView5);
		percentView6 = findViewById(R.id.percentView6);
		percentView7 = findViewById(R.id.percentView7);
		percentView8 = findViewById(R.id.percentView8);
		percentView9 = findViewById(R.id.percentView9);
		percentView10 = findViewById(R.id.percentView10);
		percentView1.setTag(0);
		percentView2.setTag(1);
		percentView3.setTag(2);
		percentView4.setTag(3);
		percentView5.setTag(4);
		percentView6.setTag(5);
		percentView7.setTag(6);
		percentView8.setTag(7);
		percentView9.setTag(8);
		percentView10.setTag(9);
		percentViews = new ArrayList<View>();
		secretViews = new ArrayList<View>();
		serectTexts = new ArrayList<TextView>();
		percentViews.add(percentView1);
		percentViews.add(percentView2);
		percentViews.add(percentView3);
		percentViews.add(percentView4);
		percentViews.add(percentView5);
		percentViews.add(percentView6);
		percentViews.add(percentView7);
		percentViews.add(percentView8);
		percentViews.add(percentView9);
		percentViews.add(percentView10);
		corp_totalOpen = findViewById(R.id.corp_totalOpen);
		corp_totalClose = findViewById(R.id.corp_totalClose);
		corp_openAfterStop = findViewById(R.id.corp_openAfterStop);
		corp_openToCoper = findViewById(R.id.corp_openToCoper);
		corp_totalOpen.setTag(0);
		corp_totalClose.setTag(1);
		corp_openAfterStop.setTag(2);
		corp_openToCoper.setTag(3);
		secretViews.add(corp_totalOpen);
		secretViews.add(corp_totalClose);
		secretViews.add(corp_openAfterStop);
		secretViews.add(corp_openToCoper);
		corp_totalOpenText = (TextView) findViewById(R.id.corp_totalOpenText);
		corp_totalCloseText = (TextView) findViewById(R.id.corp_totalCloseText);
		corp_openAfterStopText = (TextView) findViewById(R.id.corp_openAfterStopText);
		corp_openToCoperText = (TextView) findViewById(R.id.corp_openToCoperText);
		serectTexts.add(corp_totalOpenText);
		serectTexts.add(corp_totalCloseText);
		serectTexts.add(corp_openAfterStopText);
		serectTexts.add(corp_openToCoperText);
		typeName = (TextView) findViewById(R.id.typeName);
		lottTime = (TextView) findViewById(R.id.lottTime);
		totalPrice = (TextView) findViewById(R.id.totalPrice);
		resultText = (TextView) findViewById(R.id.resultText);
		selfBuy = (EditText) findViewById(R.id.selfBuy);
		selfBuy.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		selfBuy.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {

				if (s.length() == 0) {
					// corPrice.setText("共0元");
					return;
				}
				try {
					if (0 < Integer.valueOf(selfBuy.getText().toString())
							&& Integer.valueOf(selfBuy.getText().toString()) <= totalPriceNum) {
						checkBill();
					} else {
						selfBuy.setText(String.valueOf(totalPriceNum - 2));
						checkBill();
					}
				} catch (Exception e) {
					if (selfBuy.getText().toString().length() > 0) {
						selfBuy.setText(s.toString().subSequence(0,
								s.length() - 1));
					}
					checkBill();
					// Toast.makeText(CorperDetails.this, "输入份数有误",
					// Toast.LENGTH_SHORT).show();
				}

			}
		});
		watchMsg = new Message();
		watchMsg.what = 60888;
		declare = (EditText) findViewById(R.id.declare);
		leastBuy = (EditText) findViewById(R.id.leastBuy);
		leastBuy.setInputType(EditorInfo.TYPE_CLASS_PHONE);
		leastBuy.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {

				if (s.length() == 0) {
					// corPrice.setText("共0元");
					return;
				}
				try {
					if (0 < Integer.valueOf(leastBuy.getText().toString())
							&& Integer.valueOf(leastBuy.getText().toString()) <= totalPriceNum) {
						checkBill();
					} else {
						leastBuy.setText(String.valueOf(totalPriceNum - 2));
						checkBill();
					}
				} catch (Exception e) {
					if (leastBuy.getText().toString().length() > 0) {
						leastBuy.setText(s.toString().subSequence(0,
								s.length() - 1));
					}
					checkBill();
					// Toast.makeText(CorperDetails.this, "输入份数有误",
					// Toast.LENGTH_SHORT).show();
				}

			}
		});
		comitView.setOnClickListener(this);
		percentView1.setOnClickListener(this);
		percentView2.setOnClickListener(this);
		percentView3.setOnClickListener(this);
		percentView4.setOnClickListener(this);
		percentView5.setOnClickListener(this);
		percentView6.setOnClickListener(this);
		percentView7.setOnClickListener(this);
		percentView8.setOnClickListener(this);
		percentView9.setOnClickListener(this);
		percentView10.setOnClickListener(this);
		corp_totalOpen.setOnClickListener(this);
		corp_totalClose.setOnClickListener(this);
		corp_openAfterStop.setOnClickListener(this);
		corp_openToCoper.setOnClickListener(this);
		allMiwen = mIntent.getStringExtra("allMiwen");
		totalPriceNum = mIntent.getIntExtra("totalPrice", -1);
		type = mIntent.getIntExtra("type", -1);
		Log.e("pkx", "in type----:" + String.valueOf(type));
		switch (type) {
		case 1:
			typeName.setText("彩种:双色球");
			break;
		case 2:
			typeName.setText("彩种:竞彩足球");
			break;
		case 3:
			typeName.setText("彩种:七乐彩");
			break;
		case 4:
			typeName.setText("彩种: 3D ");
			break;
		case 5:
			typeName.setText("彩种:竞彩篮球 ");
			break;
		}
		totalPrice
				.setText(String.valueOf(mIntent.getIntExtra("totalPrice", -1)));

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == Constant.FAST_LOGIN) {
			if (sutil.getLoginStatus()) {
				Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "未登陆", Toast.LENGTH_SHORT).show();
			}
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
			if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				// NET
				// json:{"error":0,"message":"\u8bf7\u6c42\u6210\u529f","data":750,"status":1}
				if (msg.arg1 == Constant.NET_ACTION_REGIST) {
					Log.e("pkx", "首页信息:" + msg.obj.toString());
					try {
						IndexListDto list = Net.gson.fromJson(
								msg.obj.toString(), IndexListDto.class);
						int peroidType=0;
						switch (type) {
						case 1:
							peroidType=1;
							break;
						case 3:
							peroidType=2;
							break;
						case 4:
							peroidType=3;
							break;
						}
						for (IndexListItemDto item : list.getIndexList()) {
							
							if(item.getType_id()==peroidType){
								lottTime.setVisibility(View.VISIBLE);
								lottTime.setText("期次:"+item.getPeroid_name()+"期");
								break;
							}
						}
					} catch (Exception e) {
						Constant.alertWarning(CorperMaking.this, "获取期次失败");
						// Toast.makeText(CorperMaking.this, "获取期次失败",
						// Toast.LENGTH_SHORT).show();
					}
					return;
				}
				JSONObject jo = (JSONObject) msg.obj;
				try {
					if ("10001".equals(jo.get("error").toString())) {
						Intent toLogin = new Intent(CorperMaking.this,
								LoginActivity.class);
						toLogin.putExtra("fastlogin", true);
						startActivityForResult(toLogin, Constant.FAST_LOGIN);
					} else if ("0".equals(jo.get("error").toString())
							|| "20000".equals(jo.get("error").toString())) {
						if (msg.arg1 == Constant.NET_ACTION_CHORMBET) {
							// 双色球合买请求成功成功
							Log.e("pkx", "POST_SUCCESS" + msg.obj);
							String orderID = "ID 获取失败";
							String message = "";
							message = jo.getString("message");
							orderID = jo.getString("data");
							Toast.makeText(CorperMaking.this,
									message + " orderID->" + orderID,
									Toast.LENGTH_SHORT).show();
						} else if (msg.arg1 == Constant.NET_ACTION_FOOTBALLMIX) {
							Constant.MATCH_STATUS = null;
							Log.e("pkx", "POST_SUCCESS" + msg.obj);
							String type = "";
							String orderID = "ID 获取失败";
							orderID = jo.getString("data");
							switch (Constant.FOOTBALL_TYPE) {
							case 1:
								type = "竟足胜平负";
								break;
							case 2:
								type = "竟足让球胜平负";
								break;
							case 3:
								type = "竟足比分";
								break;
							case 4:
								type = "竟足总进球数";
								break;
							case 5:
								type = "竟足半全场";
								break;
							case 6:
								type = "竟足混投";
								break;

							default:
								break;
							}
							Toast.makeText(CorperMaking.this,
									type + "->投注成功 orderID->" + orderID,
									Toast.LENGTH_SHORT).show();
						} else if (msg.arg1 == Constant.NET_ACTION_SENCENBET) {
							Log.e("pkx", "POST_SUCCESS七乐彩合买:" + msg.obj);
							String orderID = "七乐彩合买ID 获取失败";
							String message = "";
							message = jo.getString("message");
							orderID = jo.getString("data");

							Toast.makeText(CorperMaking.this,
									message + " orderID->" + orderID,
									Toast.LENGTH_SHORT).show();
						}
						Intent tohome = new Intent(CorperMaking.this,
								HomeActivity.class);
						startActivity(tohome);
					} else if ("20010".equals(jo.get("error").toString())) {
						alertMoneyLess();
					} else {
						Constant.alertWarning(CorperMaking.this,
								jo.get("message").toString());
						Toast.makeText(CorperMaking.this,
								jo.get("message").toString(), Toast.LENGTH_LONG)
								.show();
					}
				} catch (JSONException e2) {
					e2.printStackTrace();
				}

			} else if (msg.what == Constant.POST_FAIL) {
				Toast.makeText(CorperMaking.this, "请求超时", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				Toast.makeText(CorperMaking.this, "投注失败", Toast.LENGTH_SHORT)
						.show();
			}

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
				intent1.setClass(CorperMaking.this, WebviewActivity.class);
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
}
