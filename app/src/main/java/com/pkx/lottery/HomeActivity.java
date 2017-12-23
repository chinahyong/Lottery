package com.pkx.lottery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings.Secure;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonSyntaxException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.pkx.lottery.bean.MyViewPaper;
import com.pkx.lottery.bean.SIMCardInfo;
import com.pkx.lottery.dto.ActAuth;
import com.pkx.lottery.dto.AuthJson;
import com.pkx.lottery.dto.BetRecord;
import com.pkx.lottery.dto.BuyRecords;
import com.pkx.lottery.dto.CheckBean;
import com.pkx.lottery.dto.CorpAuth;
import com.pkx.lottery.dto.CorpAuthList;
import com.pkx.lottery.dto.ExceptionAuth;
import com.pkx.lottery.dto.IndexLotteryInfo;
import com.pkx.lottery.dto.LoopDto;
import com.pkx.lottery.dto.NoticeAuth;
import com.pkx.lottery.dto.PeroidRes;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.dto.UpdateAuth;
import com.pkx.lottery.dto.UserInfoAllAuth;
import com.pkx.lottery.dto.UserInfoAuth;
import com.pkx.lottery.dto.lott.ChormLott;
import com.pkx.lottery.imagerloader.ImageLoader;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.MyMD5Util;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;
import com.pkx.lottery.utils.UpdateManager;
import com.pkx.lottery.view.CircleProgressBar;
import com.pkx.lottery.view.HomeAdsViewPager;
import com.pkx.lottery.view.HomeAdsViewPager.onSimpleClickListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;
import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//bb3227
@SuppressLint("NewApi")
public class HomeActivity extends Activity implements OnClickListener {
	// private PullToRefreshListView homeRefresh;
	// =======================================
	final ArrayList<View> adsViews = new ArrayList<View>();;
	private float xDistance, yDistance, mLastMotionX, mLastMotionY;
	private boolean mIsBeingDragged;
	private HomeAdsViewPager adspaper;
	private View firstAdsView;
	private ImageLoader loader;
	private PagerAdapter addsadapter;
	private LoopDto loop;
	// =======================================
	private PullToRefreshListView homePullList;
	private PullToRefreshListView noticePullList, coperPullList;
	private String finalSN = "";
	// private PullToRefreshListView pulllist;
	private MyViewPaper homePaper;
	private View corpBuyView, homeView, moreClickView, myLottView,
			noticeClickView;
	private MyViewPaper adsPaper;
	private int whiteText = Color.parseColor("#737373");
	private int redText = Color.parseColor("#ffffff");
	// private ViewPager adsPaper;
	private SharePreferenceUtil sutil;
	private AlertDialog alertLott;
	// private XListView homeList;
	// , corperList;
	private ArrayList<CorpAuth> corpAythList;
	private boolean isFirstFresh, noticeFirstFresh;
	private int noticePage = 1;
	private String noticeType = "0", noticeSortOrder = "DESC",
			noticeSortBy = "process";
	// private ListView noticeList;
	private LayoutInflater inflater;
	private MyHandler mHandler;
	private TextView loginText, updateText;
	private long mExitTime;
	private int currentPage;
	private ArrayList<ChormLott> lotts;
	// private RefreshableView homeRefreshView;
	private BaseAdapter homeListAdapter, coperListAdapter, noticeListAdapter;
	private View lotteryHallView, buyTogetherView, noticeView, myLotteryView,
			moreView, adView;
	private View my_followView, my_cashing_view, my_detail_view,
			my_make_follow_view, my_userinfo_view, my_linkphone_view,
			my_editpwd_view;
	private ProgressDialog dialog;
	private View more_lottery_help, more_settings, more_fresh_guide,
			more_question_us, more_check_update, more_about_us,
			more_login_exit;
	private boolean isRecovery;
	private View lotteryHall, buyTogether, notice, myLottery, more,
			homeLotteryNews;
	private ImageView img_lotteryHall, img_buyTogether, img_notice,
			img_myLottery, img_more;
	private View lottTypeView, noticeFresh;
	private TextView lottTypeText, moreText, myText, noticeText, corpText,
			buyText;
	final ArrayList<Integer> fastLogos = new ArrayList<Integer>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_activity);
		if (fastLogos.size() > 0) {
			fastLogos.clear();
		}
		fastLogos.add(R.drawable.num_1);
		fastLogos.add(R.drawable.num_2);
		fastLogos.add(R.drawable.num_3);
		fastLogos.add(R.drawable.num_4);
		fastLogos.add(R.drawable.num_5);
		fastLogos.add(R.drawable.num_6);
		initViews();
		if (sutil.getLog() != null && sutil.getLog().length() > 10) {
			Log.e("pkx", "sendlog");
			sendLogRequest();
		} else {
			Log.e("pkx", " not  sendlog");
		}
		// MyMD5Util.main();
		updateText.setText("版本更新（" + Constant.getVersionName(this) + ")");
		getSn();
	}

	private void getLoop() {
		ActAuth res1 = new ActAuth("lottery_banner");
		String mingwen1 = Net.gson.toJson(res1);
		String miwen1 = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen1);
		RequestParams params1 = new RequestParams();
		params1.put("SID", sutil.getSID());
		params1.put("SN", sutil.getSN());
		params1.put("DATA", miwen1);
		Net.post(false, HomeActivity.this, Constant.POST_URL
				+ "/lottery.api.php", params1, mHandler,
				Constant.NET_ACTION_EDITPWD);
	}

	private void getSn() {
		Log.e("pkx", "home send devicekey request");
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
		Net.post(isRecovery, this, Constant.POST_URL
				+ "/auth.api.php?act=deviceAuth", params, mHandler,
				Constant.NET_ACTION_LOTHIS_WEF);
		// }
		// }
	}

	private void sendLogRequest() {
		RequestParams params = new RequestParams();
		params.put("str", sutil.getLog());
		params.put("act", "print_file");
		Net.post(true, HomeActivity.this, Constant.POST_URL_LOG
				+ "/data.api.php?debug=1", params, mHandler,
				Constant.NET_ACTION_LOG_SEND);
	}

	private void getCurrentPeroids() {
		if (lotts != null && lotts.size() > 0) {
			lotts.clear();
		}
		PeroidRes res1 = new PeroidRes("getPeroidRes", "1", "1", "1");
		String mingwen1 = Net.gson.toJson(res1);
		String miwen1 = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen1);
		RequestParams params1 = new RequestParams();
		params1.put("SID", sutil.getSID());
		params1.put("SN", sutil.getSN());
		params1.put("DATA", miwen1);
		// Log.e("pkx", "mingwen:"+mingwen+" miwen:"+miwen);
		Net.post(false, HomeActivity.this, Constant.POST_URL + "/data.api.php",
				params1, mHandler, Constant.NET_ACTION_LOTHIS);

		PeroidRes res2 = new PeroidRes("getPeroidRes", "2", "1", "1");
		String mingwen2 = Net.gson.toJson(res2);
		String miwen2 = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen2);
		RequestParams params2 = new RequestParams();
		params2.put("SID", sutil.getSID());
		params2.put("SN", sutil.getSN());
		params2.put("DATA", miwen2);
		// Log.e("pkx", "mingwen:"+mingwen+" miwen:"+miwen);
		Net.post(false, HomeActivity.this, Constant.POST_URL + "/data.api.php",
				params2, mHandler, Constant.NET_ACTION_LOTHIS);

		PeroidRes res3 = new PeroidRes("getPeroidRes", "3", "1", "1");
		String miwen3 = MDUtils.MDEncode(sutil.getdeviceKEY(),
				Net.gson.toJson(res3));
		RequestParams params3 = new RequestParams();
		params3.put("SID", sutil.getSID());
		params3.put("SN", sutil.getSN());
		params3.put("DATA", miwen3);
		Net.post(false, HomeActivity.this, Constant.POST_URL + "/data.api.php",
				params3, mHandler, Constant.NET_ACTION_LOTHIS);
		PeroidRes res = new PeroidRes("getPeroidRes", "4", "1", "1");

		String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
				Net.gson.toJson(res));
		RequestParams params = new RequestParams();
		params.put("SID", sutil.getSID());
		params.put("SN", sutil.getSN());
		params.put("DATA", miwen);
		Net.post(false, HomeActivity.this, Constant.POST_URL + "/data.api.php",
				params, mHandler, Constant.NET_ACTION_LOTHIS);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 60888 && resultCode == 60888) {
			loginText.setText("退出登录");
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_lotteryHall:
		case R.id.lotteryHall:
			homePaper.setCurrentItem(0, false);
			resetHomeBottom();
			resetHomeText();
			buyText.setTextColor(redText);
			img_lotteryHall.setImageResource(R.drawable.home_btn_click);
			break;
		case R.id.img_buyTogether:
		case R.id.buyTogether:
			homePaper.setCurrentItem(1, false);
			resetHomeBottom();
			resetHomeText();
			corpText.setTextColor(redText);
			img_buyTogether.setImageResource(R.drawable.total_btn_click);
			break;

		case R.id.img_notice:
		case R.id.notice:
			homePaper.setCurrentItem(2, false);
			resetHomeBottom();
			resetHomeText();
			noticeText.setTextColor(redText);
			img_notice.setImageResource(R.drawable.notice_btn_click);
			break;
		case R.id.img_myLottery:
		case R.id.myLottery:
			homePaper.setCurrentItem(3, false);
			resetHomeBottom();
			resetHomeText();
			myText.setTextColor(redText);
			img_myLottery.setImageResource(R.drawable.mine_btn_click);
			break;
		case R.id.img_more:
		case R.id.more:
			homePaper.setCurrentItem(4, false);
			resetHomeBottom();
			resetHomeText();
			moreText.setTextColor(redText);
			img_more.setImageResource(R.drawable.more_btn_click);
			break;
		case R.id.more_lottery_help:// 购彩帮助
			// Intent toCome6 = new Intent(this, ComingSoon.class);
			// startActivity(toCome6);
			isRecovery = true;
			getSn();
			break;
		case R.id.more_settings:// 软件设置
			Intent toset = new Intent(this, Settings.class);
			startActivity(toset);
			break;
		case R.id.more_fresh_guide:// 新手指南
			Intent toCome5 = new Intent(this, ComingSoon.class);
			startActivity(toCome5);
			break;
		case R.id.more_question_us:// 问题反馈
			AuthJson text = new AuthJson();
			text.setDeviceMobile("13000000000");
			text.setDeviceSN("ABCDEFGHIJKLMNOP");
			String mingwen8 = Net.gson.toJson(text);
			String miwen8 = MDUtils.MDEncode("UITN25LMUQC436IM", "Hello");
			Log.e("pkx", "明文：" + mingwen8);
			Log.e("pkx", "密文：" + miwen8);
			// Intent toCome4 = new Intent(this, ComingSoon.class);
			// startActivity(toCome4);
			break;
		case R.id.more_check_update:// 检查更新
			UpdateAuth lau = new UpdateAuth();
			String ljson = Net.gson.toJson(lau);
			String data = MDUtils.MDEncode(sutil.getdeviceKEY(), ljson);
			RequestParams rp = new RequestParams();
			rp.put("SID", sutil.getSID());
			rp.put("SN", sutil.getSN());
			rp.put("DATA", data);
			Net.post(true, this, Constant.POST_URL + "/user.api.php", rp,
					mHandler, Constant.NET_ACTION_CHECKUPDATE);
			break;
		case R.id.more_about_us:// 关于我们
			Intent toCome3 = new Intent(this, ComingSoon.class);
			startActivity(toCome3);
			break;
		case R.id.more_login_exit:// 登入登出
			if (sutil.getLoginStatus()) {
				alertExitDialog();
			} else {
				Intent toLogin = new Intent(this, LoginActivity.class);
				startActivityForResult(toLogin, 60888);
			}
			break;
		case R.id.my_followView:// 我的追号-->个人中心
			if (sutil.getLoginStatus()) {
				Intent toLogin = new Intent(this,
						IndividualCenterActivity.class);
				startActivity(toLogin);

			} else {
				Intent toLogin = new Intent(this, LoginActivity.class);
				startActivityForResult(toLogin, 0);
			}
			// Log.e("pkx", "ta1");
			// if (sutil.isSNSetted()) {
			// Toast.makeText(this, "设备已验证", Toast.LENGTH_SHORT).show();
			// // return;
			// }
			// Intent toau = new Intent(this, GetAuthActivity.class);
			// startActivity(toau);
			// Log.e("pkx", "ta2");
			break;
		case R.id.my_cashing_view:// 账户提款
			UserInfoAuth usinfo = new UserInfoAuth(sutil.getuid());
			String userInfiMingwen = Net.gson.toJson(usinfo);
			String userInfoMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
					userInfiMingwen);
			UserInfoAllAuth usall = new UserInfoAllAuth();
			usall.setDATA(userInfoMiwen);
			String userInfoAllMingwen = Net.gson.toJson(usall);
			String userInfoAllMiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
					userInfoAllMingwen);
			RequestParams paramsu = new RequestParams();
			paramsu.put("SID", sutil.getSID());
			paramsu.put("DATA", userInfoAllMiwen);
			Log.e("pkx",
					"SID:" + sutil.getSID() + " uid:" + sutil.getuid()
							+ " userKEY" + sutil.getuserKEY() + " deviceKEY"
							+ sutil.getdeviceKEY() + " info1:"
							+ userInfiMingwen + "info1 mi:" + userInfoMiwen
							+ " info2:" + userInfoAllMingwen + " info2 mi:"
							+ userInfoAllMiwen);
			Net.post(true, this, Constant.POST_URL + "/user.api.php", paramsu,
					mHandler, Constant.NET_ACTION_USERINFO);
			break;
		case R.id.my_detail_view:// 投注记录
			if (sutil.getLoginStatus()) {
				BetRecord braRE = new BetRecord(sutil.getuid(), 1);
				String mingwenRE = Net.gson.toJson(braRE);
				Log.e("pkx", "明文：" + mingwenRE);
				String miwenRE = MDUtils
						.MDEncode(sutil.getuserKEY(), mingwenRE);
				PublicAllAuth paaRE = new PublicAllAuth("lottery_list", miwenRE);
				String allRE = Net.gson.toJson(paaRE);
				String allmiwenRE = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allRE);
				RequestParams pa = new RequestParams();
				pa.put("SID", sutil.getSID());
				pa.put("SN", sutil.getSN());
				pa.put("DATA", allmiwenRE);
				Net.post(true, this, Constant.POST_URL + "/user.api.php", pa,
						mHandler, Constant.NET_ACTION_BUYRECORD);
			} else {
				Intent toLogin = new Intent(this, LoginActivity.class);
				startActivityForResult(toLogin, 0);
			}

			break;
		case R.id.my_make_follow_view:// 设置密保
			if (sutil.getLoginStatus()) {
				UserInfoAuth ua = new UserInfoAuth(sutil.getuid());
				String mingwen = Net.gson.toJson(ua);
				String miwen = MDUtils.MDEncode(sutil.getuserKEY(), mingwen);
				PublicAllAuth paa = new PublicAllAuth("getSecretSecurity",
						miwen);
				String allmingwen = Net.gson.toJson(paa);
				String allmiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allmingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", allmiwen);
				Net.post(true, this, Constant.POST_URL + "/user.api.php",
						params, mHandler, Constant.NET_ACTION_SECRET_SECURE);
			} else {
				Intent toLogin = new Intent(this, LoginActivity.class);
				startActivityForResult(toLogin, 0);
			}
			break;
		case R.id.my_userinfo_view:// 银行卡绑定

			if (sutil.getLoginStatus()) {
				UserInfoAuth uia = new UserInfoAuth(sutil.getuid());
				String bankmingwen = Net.gson.toJson(uia);
				String bankmiwen = MDUtils.MDEncode(sutil.getuserKEY(),
						bankmingwen);
				PublicAllAuth bpaa = new PublicAllAuth("BankCardBinding",
						bankmiwen);
				String bamiwen = Net.gson.toJson(bpaa);
				String bami = MDUtils.MDEncode(sutil.getdeviceKEY(), bamiwen);
				RequestParams bankparams = new RequestParams();
				bankparams.put("SID", sutil.getSID());
				bankparams.put("SN", sutil.getSN());
				Log.e("pkx", "all 明文" + bamiwen);
				bankparams.put("DATA", bami);
				Log.e("pkx", "明文" + bankmingwen);
				Net.post(true, this, Constant.POST_URL + "/user.api.php",
						bankparams, mHandler, Constant.NET_ACTION_LOGIN);
			} else {
				Intent toLogin = new Intent(this, LoginActivity.class);
				startActivityForResult(toLogin, 0);
			}

			break;
		case R.id.noticeFresh:
			break;
		case R.id.my_linkphone_view:// 绑定手机
			// if (sutil.getLoginStatus()) {
			Intent toBind = new Intent(this, BindPhone.class);
			startActivity(toBind);
			// } else {
			// Intent toLogin = new Intent(this, LoginActivity.class);
			// startActivityForResult(toLogin, 0);
			// }
			break;
		case R.id.my_editpwd_view:// 修改密码
			if (sutil.getLoginStatus()) {
				Intent toEditPwd = new Intent(this, EditPassword.class);
				startActivity(toEditPwd);
			} else {
				Intent toLogin = new Intent(this, LoginActivity.class);
				startActivityForResult(toLogin, 0);
			}

			break;
		case R.id.homeLotteryNews:// 主页资讯
			break;
		case R.id.lottTypeView:
			alertLottTypeDialog();
			break;
		default:
			break;
		}

	}

	@Override
	protected void onResume() {
		if (sutil.getLoginStatus()) {
			loginText.setText("退出登录");
		} else {
			loginText.setText("立即登录");
		}
		super.onResume();
	}

	private void resetHomeBottom() {
		img_lotteryHall.setImageResource(R.drawable.home_btn);
		img_buyTogether.setImageResource(R.drawable.total_btn);
		img_notice.setImageResource(R.drawable.notice_btn);
		img_myLottery.setImageResource(R.drawable.mine_btn);
		img_more.setImageResource(R.drawable.more_btn);
	}

	private void resetHomeText() {
		buyText.setTextColor(whiteText);
		corpText.setTextColor(whiteText);
		noticeText.setTextColor(whiteText);
		myText.setTextColor(whiteText);
		moreText.setTextColor(whiteText);
	}

	private void initViews() {
		loader = new ImageLoader(this, 400);
		buyText = (TextView) findViewById(R.id.buyText);
		corpText = (TextView) findViewById(R.id.corpText);
		noticeText = (TextView) findViewById(R.id.noticeText);
		myText = (TextView) findViewById(R.id.myText);
		moreText = (TextView) findViewById(R.id.moreText);
		lotts = new ArrayList<ChormLott>();
		corpAythList = new ArrayList<CorpAuth>();
		isFirstFresh = true;
		dialog = new ProgressDialog(this);
		sutil = new SharePreferenceUtil(this);
		lotteryHall = findViewById(R.id.lotteryHall);
		lotteryHall.setOnClickListener(this);
		buyTogether = findViewById(R.id.buyTogether);
		buyTogether.setOnClickListener(this);
		notice = findViewById(R.id.notice);
		notice.setOnClickListener(this);
		myLottery = findViewById(R.id.myLottery);
		myLottery.setOnClickListener(this);
		alertLott = new AlertDialog.Builder(this, R.style.dialog).create();
		more = findViewById(R.id.more);
		more.setOnClickListener(this);
		img_lotteryHall = (ImageView) findViewById(R.id.img_lotteryHall);
		// img_lotteryHall.setOnClickListener(this);
		img_lotteryHall.setImageResource(R.drawable.home_btn_click);
		buyText.setTextColor(redText);
		img_buyTogether = (ImageView) findViewById(R.id.img_buyTogether);
		// img_buyTogether.setOnClickListener(this);
		img_notice = (ImageView) findViewById(R.id.img_notice);
		// img_notice.setOnClickListener(this);
		img_myLottery = (ImageView) findViewById(R.id.img_myLottery);
		// img_myLottery.setOnClickListener(this);
		img_more = (ImageView) findViewById(R.id.img_more);
		// img_more.setOnClickListener(this);
		homePaper = (MyViewPaper) findViewById(R.id.homePaper);
		inflater = LayoutInflater.from(this);
		firstAdsView = inflater.inflate(R.layout.home_top_ads_view, null);
		// firstAdsView.setVisibility(View.GONE);
		adspaper = (HomeAdsViewPager) firstAdsView.findViewById(R.id.adspaper);
		adsViews.add(inflater.inflate(R.layout.tab_ad1, null));
		adsViews.add(inflater.inflate(R.layout.tab_ad2, null));
		adsViews.add(inflater.inflate(R.layout.tab_ad3, null));
		adsViews.add(inflater.inflate(R.layout.tab_ad4, null));
		adsViews.add(inflater.inflate(R.layout.tab_ad5, null));
		addsadapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return adsViews.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(adsViews.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(adsViews.get(position));
				return adsViews.get(position);
			}
		};

		adspaper.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				adspaper.getGestureDetector().onTouchEvent(event);

				// TODO Auto-generated method stub
				final float x = event.getRawX();
				final float y = event.getRawY();

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.e("json", "ACTION_DOWN");
					xDistance = yDistance = 0f;
					mLastMotionX = x;
					mLastMotionY = y;
				case MotionEvent.ACTION_MOVE:
					Log.e("json", "ACTION_MOVE, x: " + x + ", y: " + y);

					final float xDiff = Math.abs(x - mLastMotionX);
					final float yDiff = Math.abs(y - mLastMotionY);
					xDistance += xDiff;
					yDistance += yDiff;

					float dx = xDistance - yDistance;
					Log.e("json", "ACTION_MOVE dx= " + dx + ",xDistance: "
							+ xDistance + ", yDistance: " + yDistance);
					if (xDistance > yDistance
							|| Math.abs(xDistance - yDistance) < 0.00001f) {
						Log.e("json", "ACTION_MOVE interceptor.");
						mIsBeingDragged = true;
						mLastMotionX = x;
						mLastMotionY = y;
						((ViewParent) v.getParent())
								.requestDisallowInterceptTouchEvent(true);
					} else {
						mIsBeingDragged = false;
						((ViewParent) v.getParent())
								.requestDisallowInterceptTouchEvent(false);
					}
					break;
				case MotionEvent.ACTION_UP:
					Log.e("json", "ACTION_UP");
					break;
				case MotionEvent.ACTION_CANCEL:
					Log.e("json", "ACTION_CANCEL");
					Log.e("json", "mIsBeingDragged=" + mIsBeingDragged
							+ " xDistance= " + xDistance + " y=" + yDistance);
					if (mIsBeingDragged) {
						((ViewParent) v.getParent())
								.requestDisallowInterceptTouchEvent(false);
					}
					break;
				default:
					break;
				}
				return false;
			}
		});

		adspaper.setOnSimpleClickListener(new onSimpleClickListener() {

			@Override
			public void setOnSimpleClickListenr(int position) {
				Log.e("pkx", "simple click");
				Message msg = new Message();
				msg.what = Constant.ADS_PAGE_CHANGED;
				msg.arg1 = position;
				mHandler.sendMessage(msg);
				// if (adspaper.getCurrentItem() == 0) {
				// Intent toFootball = new Intent(HomeActivity.this,
				// ThreeDimension.class);
				// startActivity(toFootball);
				// } else if (adspaper.getCurrentItem() == 1) {
				// Intent toBas = new Intent(HomeActivity.this,
				// SevenLottery.class);
				// startActivity(toBas);
				// } else if (adspaper.getCurrentItem() == 2) {
				// Intent toMyDouble = new Intent(HomeActivity.this,
				// MyDoubleChromosphere.class);
				// startActivity(toMyDouble);
				// }
			}
		});
		adspaper.setAdapter(addsadapter);
		lotteryHallView = inflater.inflate(R.layout.tab_home_lottery, null);
		homeView = lotteryHallView.findViewById(R.id.homeView);
		homeView.setOnClickListener(this);
		// corpBuyView,homeView,moreClickView,myLottView,noticeClickView
		buyTogetherView = inflater.inflate(R.layout.tab_home_buytogether, null);
		corpBuyView = buyTogetherView.findViewById(R.id.corpBuyView);
		corpBuyView.setOnClickListener(this);
		noticeView = inflater.inflate(R.layout.tab_home_notice, null);
		noticeFresh = noticeView.findViewById(R.id.noticeFresh);
		noticeFresh.setOnClickListener(this);
		noticeClickView = noticeView.findViewById(R.id.noticeClickView);
		noticeClickView.setOnClickListener(this);
		myLotteryView = inflater.inflate(R.layout.tab_home_mylottery, null);
		myLottView = myLotteryView.findViewById(R.id.myLottView);
		myLottView.setOnClickListener(this);
		moreView = inflater.inflate(R.layout.tab_home_more, null);
		updateText = (TextView) moreView.findViewById(R.id.updateText);
		moreClickView = moreView.findViewById(R.id.moreClickView);
		moreClickView.setOnClickListener(this);
		adsPaper = (MyViewPaper) lotteryHallView.findViewById(R.id.adPaper);
		lottTypeText = (TextView) buyTogetherView
				.findViewById(R.id.lottTypeText);
		lottTypeView = buyTogetherView.findViewById(R.id.lottTypeView);
		lottTypeView.setOnClickListener(this);
		homeLotteryNews = lotteryHallView.findViewById(R.id.homeLotteryNews);
		homeLotteryNews.setOnClickListener(this);
		// adsPaper = (ViewPager) lotteryHallView.findViewById(R.id.adPaper);
		adView = inflater.inflate(R.layout.tab_ad1, null);

		final ArrayList<View> adViews = new ArrayList<View>();
		adView.setBackgroundResource(R.drawable.ad3);
		adViews.add(adView);
		adView = inflater.inflate(R.layout.tab_ad1, null);
		adView.setBackgroundResource(R.drawable.ad2);
		adViews.add(adView);
		adView = inflater.inflate(R.layout.tab_ad1, null);
		adView.setBackgroundResource(R.drawable.ad1);
		adViews.add(adView);
		final ArrayList<View> views = new ArrayList<View>();
		views.add(lotteryHallView);
		views.add(buyTogetherView);
		views.add(noticeView);
		views.add(myLotteryView);
		views.add(moreView);
		loginText = (TextView) moreView.findViewById(R.id.loginText);
		if (sutil.getLoginStatus()) {
			loginText.setText("退出登录");
		} else {
			loginText.setText("立即登录");
		}
		more_lottery_help = moreView.findViewById(R.id.more_lottery_help);
		more_lottery_help.setOnClickListener(this);
		// more_lottery_help.setVisibility(View.GONE);
		more_settings = moreView.findViewById(R.id.more_settings);
		more_settings.setOnClickListener(this);
		more_fresh_guide = moreView.findViewById(R.id.more_fresh_guide);
		more_fresh_guide.setOnClickListener(this);
		more_fresh_guide.setVisibility(View.GONE);
		more_question_us = moreView.findViewById(R.id.more_question_us);
		more_question_us.setOnClickListener(this);
		more_question_us.setVisibility(View.GONE);
		more_check_update = moreView.findViewById(R.id.more_check_update);
		more_check_update.setOnClickListener(this);
		more_about_us = moreView.findViewById(R.id.more_about_us);
		more_about_us.setOnClickListener(this);
		more_about_us.setVisibility(View.GONE);
		more_login_exit = moreView.findViewById(R.id.more_login_exit);
		more_login_exit.setOnClickListener(this);
		my_followView = myLotteryView.findViewById(R.id.my_followView);
		my_followView.setOnClickListener(this);
		my_cashing_view = myLotteryView.findViewById(R.id.my_cashing_view);
		my_cashing_view.setOnClickListener(this);
		my_cashing_view.setVisibility(View.GONE);
		my_detail_view = myLotteryView.findViewById(R.id.my_detail_view);
		my_detail_view.setOnClickListener(this);
		my_make_follow_view = myLotteryView
				.findViewById(R.id.my_make_follow_view);
		my_make_follow_view.setOnClickListener(this);
		my_userinfo_view = myLotteryView.findViewById(R.id.my_userinfo_view);
		my_userinfo_view.setOnClickListener(this);
		my_linkphone_view = myLotteryView.findViewById(R.id.my_linkphone_view);
		my_linkphone_view.setOnClickListener(this);
		my_editpwd_view = myLotteryView.findViewById(R.id.my_editpwd_view);
		my_editpwd_view.setOnClickListener(this);
		PagerAdapter adapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};
		PagerAdapter adapter1 = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return adViews.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(adViews.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(adViews.get(position));
				return adViews.get(position);
			}
		};
		homePaper.setAdapter(adapter);
		adsPaper.setAdapter(adapter1);
		mHandler = new MyHandler();
		homePaper.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.e("pkx", "onTouch");
				return false;
			}
		});
		homePaper.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				Message msg = new Message();
				msg.what = Constant.PAGE_CHANGED;
				msg.arg1 = arg0;
				mHandler.sendMessage(msg);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		adsPaper.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				Message msg = new Message();
				msg.what = Constant.AD_PAGE_CHANGED;
				msg.arg1 = arg0;
				mHandler.sendMessage(msg);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		homePullList = (PullToRefreshListView) lotteryHallView
				.findViewById(R.id.homePullList);
		homePullList.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				IndexLotteryInfo info = new IndexLotteryInfo();
				String mingwen = Net.gson.toJson(info);
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", miwen);
				Net.post(false, HomeActivity.this, Constant.POST_URL
						+ "/lottery.api.php", params, mHandler,
						Constant.NET_ACTION_REGIST);

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {

			}
		});
		coperPullList = (PullToRefreshListView) buyTogetherView
				.findViewById(R.id.coperPullList);
		noticePullList = (PullToRefreshListView) noticeView
				.findViewById(R.id.noticePullList);
		coperPullList.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				NoticeAuth na = new NoticeAuth(noticeType, String
						.valueOf(noticePage), "10", noticeSortOrder,
						noticeSortBy);
				String mingwen = Net.gson.toJson(na);
				Log.e("pkx", "请求JSON：" + mingwen);
				String data = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", data);
				Net.post(false, HomeActivity.this, Constant.POST_URL
						+ "/multibuy.api.php", params, mHandler,
						Constant.NET_ACTION_NOTICE_FRESH);

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				NoticeAuth na = new NoticeAuth(noticeType, String
						.valueOf(noticePage + 1), "10", noticeSortOrder,
						noticeSortBy);
				String mingwen = Net.gson.toJson(na);
				Log.e("pkx", "请求JSON：" + mingwen);
				String data = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", data);
				Net.post(false, HomeActivity.this, Constant.POST_URL
						+ "/multibuy.api.php", params, mHandler,
						Constant.NET_ACTION_NOTICE_LOAD);

			}
		});
		final ArrayList<Integer> res = new ArrayList<Integer>();
		res.add(R.drawable.football);
		res.add(R.drawable.basket);
		res.add(R.drawable.dimension_3d);
		res.add(R.drawable.chromosphere);
		res.add(R.drawable.poker);
		res.add(R.drawable.dimention);
		res.add(R.drawable.chongqing_lott);
		res.add(R.drawable.happy_ten);
		res.add(R.drawable.dimention);
		final ArrayList<String> lotterName = new ArrayList<String>();
		lotterName.add("竞彩足球");
		lotterName.add("竞彩篮球");
		lotterName.add("福彩3D");
		lotterName.add("双色球");
		lotterName.add("七乐彩");
		lotterName.add("湖北快三");
		lotterName.add("重庆时时彩");
		lotterName.add("天津快乐十分");
		lotterName.add("江苏快三");
		final ArrayList<String> lotteryInf = new ArrayList<String>();
		lotteryInf.add("2串1易中奖");
		lotteryInf.add("玩转NBA");
		lotteryInf.add("三位数字赢千元");
		lotteryInf.add("2元博500万");
		lotteryInf.add("30选7赢百万");
		lotteryInf.add("返奖率59%");
		lotteryInf.add("最高赢10万");
		lotteryInf.add("简单容易玩");
		lotteryInf.add("快乐好玩");

		final ArrayList<Integer> res1 = new ArrayList<Integer>();
		// res.add(R.drawable.football);
		// res.add(R.drawable.basket);
		res1.add(R.drawable.dimension_3d);
		res1.add(R.drawable.chromosphere);
		res1.add(R.drawable.poker);
		res1.add(R.drawable.dimention);
		res1.add(R.drawable.chongqing_lott);
		res1.add(R.drawable.happy_ten);
		res1.add(R.drawable.dimention);
		final ArrayList<String> lotterName1 = new ArrayList<String>();
		// lotterName.add("竞彩足球");
		// lotterName.add("竞彩篮球");
		lotterName1.add("福彩3D");
		lotterName1.add("双色球");
		lotterName1.add("七乐彩");
		lotterName1.add("湖北快三");
		lotterName1.add("重庆时时彩");
		lotterName1.add("天津快乐十分");
		lotterName1.add("江苏快三");
		final ArrayList<String> lotteryInf1 = new ArrayList<String>();
		// lotteryInf.add("2串1易中奖");
		// lotteryInf.add("玩转NBA");
		lotteryInf1.add("三位数字赢千元");
		lotteryInf1.add("2元博500万");
		lotteryInf1.add("30选7赢百万");
		lotteryInf1.add("返奖率59%");
		lotteryInf1.add("最高赢10万");
		lotteryInf1.add("简单容易玩");
		lotteryInf1.add("快乐好玩");
		if (Constant.VERSION_NO_FOOTBALLorBASTKETBALL) {
			homeListAdapter = new BaseAdapter() {

				@SuppressLint("NewApi")
				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {

					if (position == 0) {
						return firstAdsView;
					}
					// if (convertView == null) {
					convertView = (LinearLayout) inflater.inflate(
							R.layout.home_lottery_item, null);
					// }
					Log.e("pkx", "res1 size:" + res.size());
					((ImageView) convertView.findViewById(R.id.lotteryLogo))
							.setBackgroundResource(res1.get(position - 1));
					((TextView) convertView.findViewById(R.id.lotteryInfo))
							.setText(lotteryInf1.get(position - 1));
					// ((TextView) convertView.findViewById(R.id.lotteryTime))
					// .setText(timeInfo.get(position));
					((TextView) convertView.findViewById(R.id.lotteryName))
							.setText(lotterName1.get(position - 1));
					if (position == 3 || position == 6 || position == 7
							|| position == 8 || position == 9) {
						((TextView) convertView
								.findViewById(R.id.lotterAnnounce))
								.setVisibility(View.GONE);
					} else {
						((TextView) convertView
								.findViewById(R.id.lotterAnnounce))
								.setVisibility(View.GONE);
					}
					return convertView;
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
					return 8;
				}
			};
		} else {
			homeListAdapter = new BaseAdapter() {

				@SuppressLint("NewApi")
				@Override
				public View getView(int position, View convertView,
						ViewGroup parent) {

					if (position == 0) {
						return firstAdsView;
					}
					// if (convertView == null) {
					convertView = (LinearLayout) inflater.inflate(
							R.layout.home_lottery_item, null);
					// }
					((ImageView) convertView.findViewById(R.id.lotteryLogo))
							.setBackgroundResource(res.get(position - 1));
					((TextView) convertView.findViewById(R.id.lotteryInfo))
							.setText(lotteryInf.get(position - 1));
					// ((TextView) convertView.findViewById(R.id.lotteryTime))
					// .setText(timeInfo.get(position));
					((TextView) convertView.findViewById(R.id.lotteryName))
							.setText(lotterName.get(position - 1));
					if (position == 3 || position == 6 || position == 7
							|| position == 8 || position == 9) {
						((TextView) convertView
								.findViewById(R.id.lotterAnnounce))
								.setVisibility(View.VISIBLE);
					} else {
						((TextView) convertView
								.findViewById(R.id.lotterAnnounce))
								.setVisibility(View.GONE);
					}
					return convertView;
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
					return 10;
				}
			};
		}
		coperListAdapter = new BaseAdapter() {

			@SuppressLint("NewApi")
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder viewholder = null;
				if (convertView == null) {
					viewholder = new ViewHolder();
					convertView = inflater.inflate(R.layout.corbuy_list_item,
							null);
					viewholder.corperName = (TextView) convertView
							.findViewById(R.id.corperName);
					viewholder.commission = (TextView) convertView
							.findViewById(R.id.commission);
					viewholder.lottery_type = (TextView) convertView
							.findViewById(R.id.lottery_type);
					viewholder.process = (CircleProgressBar) convertView
							.findViewById(R.id.process);
					viewholder.rest_count = (TextView) convertView
							.findViewById(R.id.rest_count);
					viewholder.total_price = (TextView) convertView
							.findViewById(R.id.total_price);
					convertView.setTag(viewholder);
				} else {
					viewholder = (ViewHolder) convertView.getTag();
				}
				if (corpAythList.get(position).getNick_name() != null
						&& corpAythList.get(position).getNick_name().length() > 0) {
					viewholder.corperName.setText(corpAythList.get(position)
							.getNick_name());
				} else {
					viewholder.corperName.setText("匿名用户");
				}
				viewholder.commission.setText("佣"
						+ corpAythList.get(position).getCommission() + "%");
				String lottType = "";
				if ("1".equals(corpAythList.get(position).getLottery_type())) {
					lottType = "双色球";
				} else if ("2".equals(corpAythList.get(position)
						.getLottery_type())) {
					lottType = "七乐彩";
				} else if ("3".equals(corpAythList.get(position)
						.getLottery_type())) {
					lottType = "3D";
				} else if ("50".equals(corpAythList.get(position)
						.getLottery_type())) {
					lottType = "竟足";
				} else if ("60".equals(corpAythList.get(position)
						.getLottery_type())) {
					lottType = "竟篮";
				}
				Log.e("pkx", "" + corpAythList.get(position).getLottery_type());
				viewholder.lottery_type.setText(lottType);
				int pro = 15;
				try {
					pro = Integer.valueOf(String.format("%.0f", Double
							.valueOf(corpAythList.get(position).getProcess())));
				} catch (Exception e) {
					Log.e("pkx", "progress  format exception----");
				}

				double keepcount = Double.valueOf(corpAythList.get(position)
						.getKeep_count());
				int k = 0;
				try {
					if (keepcount > 0) {
						double all = Double.valueOf(corpAythList.get(position)
								.getMax_count());
						double percent = keepcount * 100 / all;
						k = Integer.valueOf(String.format("%.0f", percent));
					}
				} catch (Exception e) {
					k = 0;
				}
				if (pro >= 0 && pro <= 100) {
					viewholder.process.setProgress(pro, k);

				} else {
					viewholder.process.setProgress(15, k);
				}
				viewholder.rest_count.setText("未认购:"
						+ String.format(
								"%.1f",
								corpAythList.get(position).getRest_count()
										* Double.valueOf(corpAythList.get(
												position).getUnit_price()))
						+ "元");
				viewholder.total_price.setText("方案金额:"
						+ corpAythList.get(position).getBet_amount() + "元");

				return convertView;
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
				return corpAythList.size();
			}
		};
		noticeListAdapter = new BaseAdapter() {

			@SuppressLint("NewApi")
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				switch (position) {
				case 0:
					convertView = inflater.inflate(R.layout.show_chorm_item,
							null);
					TextView time,
					three_direct_0t,
					three_direct_1t,
					three_direct_2t,
					three_direct_3t,
					three_direct_4t,
					three_direct_5t,
					three_direct_6t;
					time = (TextView) convertView.findViewById(R.id.time);
					three_direct_0t = (TextView) convertView
							.findViewById(R.id.three_direct_0t);
					three_direct_1t = (TextView) convertView
							.findViewById(R.id.three_direct_1t);
					three_direct_2t = (TextView) convertView
							.findViewById(R.id.three_direct_2t);
					three_direct_3t = (TextView) convertView
							.findViewById(R.id.three_direct_3t);
					three_direct_4t = (TextView) convertView
							.findViewById(R.id.three_direct_4t);
					three_direct_5t = (TextView) convertView
							.findViewById(R.id.three_direct_5t);
					three_direct_6t = (TextView) convertView
							.findViewById(R.id.three_direct_6t);
					if (lotts != null && lotts.size() > 0) {
						for (ChormLott c : lotts) {
							if (c.getLottery_type().equals("1")) {
								if (c.getBalls().size() == 7) {
									time.setText("第" + c.getPeroid_name() + "期");
									three_direct_0t.setText(String.valueOf(c
											.getBalls().get(0)));
									three_direct_1t.setText(String.valueOf(c
											.getBalls().get(1)));
									three_direct_2t.setText(String.valueOf(c
											.getBalls().get(2)));
									three_direct_3t.setText(String.valueOf(c
											.getBalls().get(3)));
									three_direct_4t.setText(String.valueOf(c
											.getBalls().get(4)));
									three_direct_5t.setText(String.valueOf(c
											.getBalls().get(5)));
									three_direct_6t.setText(String.valueOf(c
											.getBalls().get(6)));
								}
							}
						}
					}
					break;
				case 1:
					convertView = inflater.inflate(
							R.layout.show_dimention_item, null);
					TextView time1,
					three_direct_0,
					three_direct_1,
					three_direct_2;
					time1 = (TextView) convertView.findViewById(R.id.time);
					three_direct_0 = (TextView) convertView
							.findViewById(R.id.three_direct_0t);
					three_direct_1 = (TextView) convertView
							.findViewById(R.id.three_direct_1t);
					three_direct_2 = (TextView) convertView
							.findViewById(R.id.three_direct_2t);
					if (lotts != null && lotts.size() > 0) {
						for (ChormLott c : lotts) {
							if (c.getLottery_type().equals("3")) {
								if (c.getBalls().size() == 3) {
									three_direct_0.setText(String.valueOf(c
											.getBalls().get(0)));
									three_direct_1.setText(String.valueOf(c
											.getBalls().get(1)));
									three_direct_2.setText(String.valueOf(c
											.getBalls().get(2)));
								}
								time1.setText("第" + c.getPeroid_name() + "期");
							}
						}
					}
					break;
				case 2:
					convertView = inflater.inflate(R.layout.show_fast_item,
							null);
					TextView time2,
					openWins;
					ImageView three_direct_0i,
					three_direct_1i,
					three_direct_2i;
					openWins = (TextView) convertView
							.findViewById(R.id.openWins);
					time2 = (TextView) convertView.findViewById(R.id.time);
					three_direct_0i = (ImageView) convertView
							.findViewById(R.id.three_direct_0);
					three_direct_1i = (ImageView) convertView
							.findViewById(R.id.three_direct_1);
					three_direct_2i = (ImageView) convertView
							.findViewById(R.id.three_direct_2);
					if (lotts != null && lotts.size() > 0) {
						for (ChormLott c : lotts) {
							if (c.getLottery_type().equals("4")) {
								if (c.getBalls().size() == 3
										&& c.getBalls().get(1) != 0
										&& c.getBalls().get(2) != 0
										&& c.getBalls().get(0) != 0) {
									openWins.setVisibility(View.INVISIBLE);
									three_direct_0i.setImageResource(fastLogos
											.get(c.getBalls().get(0) - 1));
									three_direct_1i.setImageResource(fastLogos
											.get(c.getBalls().get(1) - 1));
									three_direct_2i.setImageResource(fastLogos
											.get(c.getBalls().get(2) - 1));
									time2.setText("第" + c.getPeroid_name()
											+ "期");
								} else {
									openWins.setVisibility(View.VISIBLE);
									three_direct_0i
											.setImageResource(R.drawable.cp);
									three_direct_1i
											.setImageResource(R.drawable.cp);
									three_direct_2i
											.setImageResource(R.drawable.cp);
									time2.setText("第" + c.getPeroid_name()
											+ "期");
								}
							}
						}
					}
					break;
				case 3:
					convertView = inflater.inflate(R.layout.show_seven_item,
							null);
					TextView time3,
					three_direct_0ts,
					three_direct_1ts,
					three_direct_2ts,
					three_direct_3ts,
					three_direct_4ts,
					three_direct_5ts,
					three_direct_6ts,
					three_direct_7ts;
					time3 = (TextView) convertView.findViewById(R.id.time);
					three_direct_0ts = (TextView) convertView
							.findViewById(R.id.three_direct_0t);
					three_direct_1ts = (TextView) convertView
							.findViewById(R.id.three_direct_1t);
					three_direct_2ts = (TextView) convertView
							.findViewById(R.id.three_direct_2t);
					three_direct_3ts = (TextView) convertView
							.findViewById(R.id.three_direct_3t);
					three_direct_4ts = (TextView) convertView
							.findViewById(R.id.three_direct_4t);
					three_direct_5ts = (TextView) convertView
							.findViewById(R.id.three_direct_5t);
					three_direct_6ts = (TextView) convertView
							.findViewById(R.id.three_direct_6t);
					three_direct_7ts = (TextView) convertView
							.findViewById(R.id.three_direct_7t);
					if (lotts != null && lotts.size() > 0) {
						for (ChormLott c : lotts) {
							if (c.getLottery_type().equals("2")) {
								if (c.getBalls().size() == 8) {
									three_direct_0ts.setText(String.valueOf(c
											.getBalls().get(0)));
									three_direct_1ts.setText(String.valueOf(c
											.getBalls().get(1)));
									three_direct_2ts.setText(String.valueOf(c
											.getBalls().get(2)));
									three_direct_3ts.setText(String.valueOf(c
											.getBalls().get(3)));
									three_direct_4ts.setText(String.valueOf(c
											.getBalls().get(4)));
									three_direct_5ts.setText(String.valueOf(c
											.getBalls().get(5)));
									three_direct_6ts.setText(String.valueOf(c
											.getBalls().get(6)));
									three_direct_7ts.setText(String.valueOf(c
											.getBalls().get(7)));
									time3.setText("第" + c.getPeroid_name()
											+ "期");
								}
							}
						}
					}
					break;
				case 4:
					convertView = inflater.inflate(R.layout.show_fast_item,
							null);
					TextView name = (TextView) convertView
							.findViewById(R.id.name);

					TextView openWins1 = (TextView) convertView
							.findViewById(R.id.openWins);
					openWins1.setVisibility(View.INVISIBLE);
					name.setText("江苏快三");
					break;
				case 5:
					convertView = inflater.inflate(
							R.layout.show_chongqing_item, null);
					break;
				case 6:
					convertView = inflater.inflate(R.layout.show_happy_item,
							null);
					break;
				}

				return convertView;
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
				return 4;
			}
		};
		homePullList.setAdapter(homeListAdapter);
		coperPullList.setAdapter(coperListAdapter);
		noticePullList.setAdapter(noticeListAdapter);
		noticePullList.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				getCurrentPeroids();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
			}
		});
		noticePullList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				switch (position - 1) {
				case 0:
					Intent toChromShow = new Intent(HomeActivity.this,
							ShowChromsphere.class);
					startActivity(toChromShow);
					break;
				case 1:
					Intent toDimentionShow = new Intent(HomeActivity.this,
							ShowDimention.class);
					startActivity(toDimentionShow);
					break;
				case 2:
					Intent toFastShow = new Intent(HomeActivity.this,
							ShowFast.class);
					startActivity(toFastShow);
					break;
				case 3:
					Intent toSevenShow = new Intent(HomeActivity.this,
							ShowSeven.class);
					startActivity(toSevenShow);
					break;
				case 4:
					Intent tojiangsuShow = new Intent(HomeActivity.this,
							ShowFast.class);
					tojiangsuShow.putExtra("isJiangsuType", true);
					startActivity(tojiangsuShow);
					break;
				case 5:
					Intent tochongqingShow = new Intent(HomeActivity.this,
							ShowChongqing.class);
					startActivity(tochongqingShow);
					break;
				case 6:
					Intent tohappyShow = new Intent(HomeActivity.this,
							ShowHappy.class);
					startActivity(tohappyShow);
					break;

				default:
					break;
				}

			}

		});
		coperPullList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				Message msg = new Message();
				msg.what = Constant.COPER_ITEM_CLICK;
				msg.arg1 = position;
				mHandler.sendMessage(msg);
			}

		});
		homePullList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				if (Constant.VERSION_NO_FOOTBALLorBASTKETBALL) {
					if (position > 0) {
						Message msg = new Message();
						msg.what = Constant.HOME_LIST_ITEM;
						msg.arg1 = position + 1;
						mHandler.sendMessage(msg);
					}
				} else {
					if (position > 0) {
						Message msg = new Message();
						msg.what = Constant.HOME_LIST_ITEM;
						msg.arg1 = position - 1;
						mHandler.sendMessage(msg);
					}
				}
			}

		});
	}

	static class ViewHolder {
		CircleProgressBar process;
		TextView corperName;
		// TextView keep_count;
		TextView total_price;
		TextView rest_count;
		TextView lottery_type;
		TextView commission;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if ((System.currentTimeMillis() - mExitTime) > 2000) {// 如果两次按键时间间隔大于2000毫秒，则不退出

				Toast t = Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT);
				t.setGravity(Gravity.BOTTOM, 0, 10);
				t.show();

				mExitTime = System.currentTimeMillis();// 更新mExitTime

			} else {
				// moveTaskToBack(true); // 否则退出程序
				sutil.setLoginStatus(false);
				super.onBackPressed();

			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
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
			Log.d("test", "handleMessage is " + msg.what);
			if (msg.what == Constant.PAGE_CHANGED) {
				switch (msg.arg1) {
				case 0:
					// 选项卡一，购彩大厅
					new Thread(new Runnable() {

						@Override
						public void run() {
							try {
								Thread.sleep(500);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							Message msg = new Message();
							msg.arg1 = 0;
							mHandler.sendMessage(msg);
						}
					}).start();
					currentPage = 0;
					resetHomeBottom();
					resetHomeText();
					buyText.setTextColor(redText);
					img_lotteryHall.setImageResource(R.drawable.home_btn_click);
					homePullList.onRefreshComplete();
					// homeList.stopRefresh();
					break;
				case 1:
					// 选项卡二，合买大厅
					coperPullList.onRefreshComplete();
					if (isFirstFresh) {
						isFirstFresh = false;
						NoticeAuth na = new NoticeAuth("0", "1", "30", "DESC",
								"process");
						String mingwen = Net.gson.toJson(na);
						Log.e("pkx", "请求JSON：" + mingwen);
						String data = MDUtils.MDEncode(sutil.getdeviceKEY(),
								mingwen);
						RequestParams params = new RequestParams();
						params.put("SID", sutil.getSID());
						params.put("SN", sutil.getSN());
						params.put("DATA", data);
						Net.post(true, HomeActivity.this, Constant.POST_URL
								+ "/multibuy.api.php", params, mHandler,
								Constant.NET_ACTION_NOTICE_FRESH);
					}
					currentPage = 1;
					resetHomeBottom();
					resetHomeText();
					corpText.setTextColor(redText);
					img_buyTogether
							.setImageResource(R.drawable.total_btn_click);
					break;
				case 2:
					// 选项卡三，开奖公告
					if (!noticeFirstFresh) {
						getCurrentPeroids();
						noticeFirstFresh = true;
					}
					if (noticePullList.isRefreshing()) {
						noticePullList.onRefreshComplete();
					}
					currentPage = 2;
					resetHomeBottom();
					resetHomeText();
					noticeText.setTextColor(redText);
					img_notice.setImageResource(R.drawable.notice_btn_click);
					break;
				case 3:
					// 选项卡三，我的彩票
					currentPage = 3;
					resetHomeBottom();
					resetHomeText();
					myText.setTextColor(redText);
					img_myLottery.setImageResource(R.drawable.mine_btn_click);
					break;
				case 4:
					// 选项卡三，更多功能//已转移
					if (sutil.getLoginStatus()) {
						loginText.setText("退出登录");
					} else {
						loginText.setText("立即登录");
					}
					currentPage = 4;
					resetHomeBottom();
					resetHomeText();
					moreText.setTextColor(redText);
					img_more.setImageResource(R.drawable.more_btn_click);
					break;

				}
			} else if (msg.what == Constant.HOME_LIST_ITEM) {
				switch (msg.arg1) {
				case 1:
					// 竟足
					Intent toFootball = new Intent(HomeActivity.this,
							FootballLottery.class);
					startActivity(toFootball);
					break;
				case 2:
					// 竟篮
					Intent toBas = new Intent(HomeActivity.this,
							BasketBallLottery.class);
					startActivity(toBas);
					break;
				case 3:
					// 3D
					Intent to3D = new Intent(HomeActivity.this,
							ThreeDimension.class);
					startActivity(to3D);
					break;
				case 4:
					// 双色球
					Intent toMyDouble = new Intent(HomeActivity.this,
							MyDoubleChromosphere.class);
					startActivity(toMyDouble);
					break;
				case 5:
					// 七彩乐
					Intent qile = new Intent(HomeActivity.this,
							SevenLottery.class);
					startActivity(qile);
					break;
				case 6:
					// 快三
					Intent toFast3 = new Intent(HomeActivity.this,
							FastDimention.class);
					toFast3.putExtra("type", 0);// 0（湖北）快三1江苏快三
					startActivity(toFast3);
					break;
				case 7:
					Intent toChongqing = new Intent(HomeActivity.this,
							ChongQingLottory.class);
					startActivity(toChongqing);
					break;
				case 8:
					Intent toHappyTen = new Intent(HomeActivity.this,
							HappyTenLottery.class);
					startActivity(toHappyTen);
					break;
				case 9:
					Intent toJiangsuFast = new Intent(HomeActivity.this,
							FastDimention.class);
					toJiangsuFast.putExtra("type", 1);// 0（湖北）快三1江苏快三
					startActivity(toJiangsuFast);
					break;

				default:
					break;
				}
			} else if (msg.what == Constant.ADS_PAGE_CHANGED) {// 轮播点击
				if (loop == null || loop.getLoops() == null
						|| loop.getLoops().size() == 0) {
					Constant.alertWarning(HomeActivity.this, "无推荐广告!");
					return;
				}
				switch (loop.getLoops().get(Integer.valueOf(msg.arg1))
						.getType_id()) {
				case 1:

					// 双色球
					Intent toMyDouble = new Intent(HomeActivity.this,
							MyDoubleChromosphere.class);
					startActivity(toMyDouble);
					break;
				case 2:

					// 七彩乐
					Intent qile = new Intent(HomeActivity.this,
							SevenLottery.class);
					startActivity(qile);
					break;
				case 3:

					// 3D
					Intent to3D = new Intent(HomeActivity.this,
							ThreeDimension.class);
					startActivity(to3D);
					break;
				case 4:

					// 快三
					Intent toFast3 = new Intent(HomeActivity.this,
							FastDimention.class);
					toFast3.putExtra("type", 0);// 0（湖北）快三1江苏快三
					startActivity(toFast3);
					break;
				case 99:
					Intent toRegist = new Intent(HomeActivity.this,
							RegistActivity.class);
					startActivity(toRegist);
					break;
				}

			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				Log.e("pkxh", "act:" + String.valueOf(msg.arg1));
				Log.e("pkx", "POST_SUCCESS" + msg.obj);
				JSONObject ojo = (JSONObject) msg.obj;

				if (msg.arg1 == Constant.NET_ACTION_LOTHIS_WEF) {
					try {
						try {
							JSONObject ojo1 = (JSONObject) msg.obj;
							Log.e("pkx",
									"主页设备key 完成 sid" + ojo1.getString("SID")
											+ " devicekey:"
											+ ojo1.getString("deviceKEY"));
							sutil.setSID(ojo1.getString("SID"));
							sutil.setdeviceKEY(ojo1.getString("deviceKEY"));
							sutil.setSN(finalSN);
							Log.e("pkx", "SN" + finalSN);
							sutil.setIsSNSetted(true);
							getLoop();
							if (isRecovery) {
								Toast.makeText(HomeActivity.this, "修复成功",
										Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}

				} else if (msg.arg1 == Constant.NET_ACTION_LOG_SEND) {
					Log.e("pkxlog", "发送日志返回：" + msg.obj.toString());
					sutil.clearLog();
				} else if (msg.arg1 == Constant.NET_ACTION_LOTHIS) {
					try {
						JSONArray ja = new JSONArray(
								ojo.getString("peroidList"));
						Log.e("pkx", "ja size:" + ja.length());
						for (int i = 0; i < ja.length(); i++) {
							lotts.add(Net.gson.fromJson(ja.get(i).toString(),
									ChormLott.class));
						}
						Log.e("pkx", "lotts size:" + lotts.size());
					} catch (JSONException e) {
						Log.e("pkx", "ja------>JSONException");
						e.printStackTrace();
					}
					if (lotts.size() == 4) {
						noticePullList.onRefreshComplete();
						noticeListAdapter.notifyDataSetChanged();
					}
				} else if (msg.arg1 == Constant.NET_ACTION_USERINFO) {
					// 用户信息获取成功
					Log.e("pkx", "用户信息获取成功:" + ojo.toString());
				} else if (msg.arg1 == Constant.NET_ACTION_USERACCOUNT) {
					ojo = (JSONObject) msg.obj;
					Log.e("pkx", "账户信息获取成功:" + ojo.toString());
				} else if (msg.arg1 == Constant.NET_ACTION_CHECKUPDATE) {
					// 更新通知NET
					// JSON:{"apkURL":"http:\/\/118.192.9.173:8080\/download\/lottery.apk",
					// "versionCode":"2","status":"1","version":"1.0.1"}
					try {
						int v = HomeActivity.this.getPackageManager()
								.getPackageInfo("com.pkx.lottery", 0).versionCode;
						JSONObject checkJO = (JSONObject) msg.obj;
						CheckBean cb = Net.gson.fromJson(checkJO.toString(),
								CheckBean.class);
						if (cb.getVersionCode() > v) {
							Toast.makeText(
									HomeActivity.this,
									"需要更新  当前Version:" + v + " 最新Version:"
											+ cb.getVersionCode(),
									Toast.LENGTH_SHORT).show();
							UpdateManager m = new UpdateManager(
									HomeActivity.this, cb.getApkURL());
							m.checkUpdate();
						} else {
							Toast.makeText(HomeActivity.this, "已是最新版本",
									Toast.LENGTH_SHORT).show();
						}
						Log.e("pkxh", "当前版本号" + v);
					} catch (NameNotFoundException e) {
						e.printStackTrace();
					}

				} else if (msg.arg1 == Constant.NET_ACTION_GETPERIO) {
					ojo = (JSONObject) msg.obj;
					Log.e("pkx", "彩期获取成功:" + ojo.toString());
				} else if (msg.arg1 == Constant.NET_ACTION_NOTICE_FRESH) {
					ojo = (JSONObject) msg.obj;
					try {
						noticePage = 1;
						Log.e("pkx", "NET_ACTION_NOTICE_FRESH------------:"
								+ ojo.toString());
						CorpAuthList cal = Net.gson.fromJson(
								ojo.getString("data"), CorpAuthList.class);
						Log.e("pkx", "listcount:" + cal.getCount());
						if (corpAythList.size() > 0) {
							corpAythList.clear();
						}
						corpAythList.addAll(cal.getList());
						coperPullList.setMode(Mode.BOTH);
						if (corpAythList.size() == cal.getCount()) {
							// Toast.makeText(HomeActivity.this, "全部数据加载完毕!",
							// Toast.LENGTH_SHORT).show();
							coperPullList.setMode(Mode.PULL_FROM_START);
						}
						coperPullList.onRefreshComplete();
						coperListAdapter.notifyDataSetChanged();
						for (CorpAuth ca : cal.getList()) {
							Log.e("pkx",
									"遍历：" + ca.getIntro() + "  Type:"
											+ ca.getLottery_type());
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else if (msg.arg1 == Constant.NET_ACTION_NOTICE_LOAD) {

					ojo = (JSONObject) msg.obj;
					Log.e("pkx",
							"NET_ACTION_NOTICE_LOAD------------:"
									+ ojo.toString());
					CorpAuthList cal;

					try {
						cal = Net.gson.fromJson(ojo.getString("data"),
								CorpAuthList.class);
						Log.e("pkx", "listcount:" + cal.getCount());
						corpAythList.addAll(cal.getList());
						coperPullList.setMode(Mode.BOTH);
						// corperList.setPullLoadEnable(true);
						if (corpAythList.size() == cal.getCount()) {
							Toast.makeText(HomeActivity.this, "全部数据加载完毕!",
									Toast.LENGTH_SHORT).show();
							coperPullList.setMode(Mode.PULL_FROM_START);
						}
						coperPullList.onRefreshComplete();
						coperListAdapter.notifyDataSetChanged();
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					noticePage++;

				} else if (msg.arg1 == Constant.NET_ACTION_SECRET_SECURE) {
					JSONObject jo = (JSONObject) msg.obj;
					try {
						if ("0".equals(String.valueOf(jo.getInt("num")))) {
							Toast.makeText(HomeActivity.this, "您还没有设置密保问题。",
									Toast.LENGTH_LONG).show();
							Intent toAddSecret = new Intent(HomeActivity.this,
									QuestionInfoAdd.class);
							startActivity(toAddSecret);
						} else {
							Intent toSecretList = new Intent(HomeActivity.this,
									QuestionInfo.class);
							toSecretList.putExtra("list", jo.toString());
							startActivity(toSecretList);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					Log.e("pkx", "SECRET status 1:" + msg.obj.toString());
				} else if (msg.arg1 == Constant.NET_ACTION_LOGIN) {
					JSONObject jo = (JSONObject) msg.obj;
					try {
						if ("0".equals(String.valueOf(jo.getInt("err")))) {
							Intent toBindCard = new Intent(HomeActivity.this,
									CreditCardBinding.class);
							toBindCard.putExtra("binded", true);
							toBindCard.putExtra("cardinfo",
									jo.getString("user_bank"));
							startActivity(toBindCard);
						} else {
							Intent toBindCard = new Intent(HomeActivity.this,
									CreditCardBinding.class);
							startActivity(toBindCard);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else if (msg.arg1 == Constant.NET_ACTION_BUYRECORD) {
					JSONObject rejo = (JSONObject) msg.obj;
					Intent tore = new Intent(HomeActivity.this,
							BuyRecoreds.class);
					try {
						Net.gson.fromJson(rejo.getString("data"),
								BuyRecords.class);
						try {
							tore.putExtra("data", rejo.getString("data"));
							startActivity(tore);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					} catch (Exception e) {
						try {
							ExceptionAuth ea = Net.gson
									.fromJson(rejo.getString("data"),
											ExceptionAuth.class);
							if (ea.getPage() == 0 && ea.getPage_sum() == 0) {
								Toast.makeText(HomeActivity.this, "您没有投注记录",
										Toast.LENGTH_SHORT).show();
							}
						} catch (JsonSyntaxException e1) {
							e1.printStackTrace();
						} catch (JSONException e1) {
							e1.printStackTrace();
						}
					}

				} else if (msg.arg1 == Constant.NET_ACTION_REGIST) {
					homePullList.onRefreshComplete();
					Log.e("pkx", " 首页信息：" + msg.obj.toString());
				} else if (msg.arg1 == Constant.NET_ACTION_EDITPWD) {

					Log.e("pkx", "轮播:" + msg.obj.toString());
					try {
						loop = Net.gson.fromJson(msg.obj.toString(),
								LoopDto.class);
						int size = loop.getLoops().size();
						if (size > 0) {
							if (adsViews != null && adsViews.size() > 0) {
								adsViews.clear();
							}
							View view;
							for (int i = 0; i < size; i++) {
								view = inflater.inflate(R.layout.tab_ad1, null);
								ImageView img = (ImageView) view
										.findViewById(R.id.img);
								loader.DisplayImage(loop.getLoops().get(i)
										.getImgurl(), img, false);
								adsViews.add(view);
							}
							addsadapter.notifyDataSetChanged();
//							if (size > 3) {
//								adspaper.setCurrentItem(3);
//							}
//							if (size > 2) {
//								adspaper.setCurrentItem(2);
//							}
//							if (size > 1) {
//								adspaper.setCurrentItem(0);
//							}
							Log.e("pkx", "轮播图片加载完成");
						}
					} catch (Exception e) {
						Constant.alertWarning(HomeActivity.this, "获取广告失败！");
					}
				}
			} else if (msg.what == Constant.POST_FAIL) {
				Toast.makeText(HomeActivity.this, "请求超时", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == Constant.COPER_ITEM_CLICK) {
				// 合买页item点击
				if (msg.arg1 == 0)
					return;
				Intent toCorpDetail = new Intent(HomeActivity.this,
						CorperDetails.class);
				Bundle extras = new Bundle();
				extras.putSerializable("clickCorp",
						corpAythList.get(msg.arg1 - 1));
				toCorpDetail.putExtras(extras);
				startActivity(toCorpDetail);

			} else if (msg.what == Constant.NOTICE_ITEM_CLICK) {

			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				Toast.makeText(HomeActivity.this, "连接失败", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == Constant.CHECK_UPDATE) {
				if (msg.arg1 == 0) {
					dialog.show();
				} else if (msg.arg1 == 1) {
					Toast.makeText(HomeActivity.this, "已是最新版本",
							Toast.LENGTH_SHORT).show();
					dialog.dismiss();
				}
			} else if (msg.what == Constant.FOOTBALL_TYPE_CHANGED) {
				switch (Constant.CORPBUY_LOTT_TYPE) {
				case 1:
					lottTypeText.setText("全部彩种");
					noticeType = "";
					break;
				case 2:
					lottTypeText.setText("双色球");
					noticeType = "1";
					break;
				case 3:
					lottTypeText.setText("福彩3D");
					noticeType = "3";
					break;
				case 4:
					lottTypeText.setText("七乐彩");
					noticeType = "2";
					break;
				case 5:
					lottTypeText.setText("竞彩足球");
					noticeType = "50";
					break;
				case 6:
					lottTypeText.setText("竞彩篮球");
					noticeType = "60";
					break;

				}
				NoticeAuth na = new NoticeAuth(noticeType,
						String.valueOf(noticePage), "10", noticeSortOrder,
						noticeSortBy);
				String mingwen = Net.gson.toJson(na);
				Log.e("pkx", "请求JSON：" + mingwen);
				String data = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", data);
				Net.post(true, HomeActivity.this, Constant.POST_URL
						+ "/multibuy.api.php", params, mHandler,
						Constant.NET_ACTION_NOTICE_FRESH);
			}
			if (msg.arg1 == 0) {
				homeListAdapter.notifyDataSetChanged();
			} else if (msg.arg1 == 1) {
				noticeListAdapter.notifyDataSetChanged();
			}

		}
	}

	private void alertLottTypeDialog() {
		if (alertLott.isShowing()) {
			alertLott.dismiss();
			return;
		}
		int whiteText = Color.parseColor("#ffffff");
		alertLott.show();
		alertLott.getWindow().setContentView(R.layout.lott_type_dialog);
		TextView type_weft, type_rweft, type_pointst, type_goalst, type_halft, type_mixt;
		type_weft = (TextView) alertLott.findViewById(R.id.type_weft);
		type_rweft = (TextView) alertLott.findViewById(R.id.type_rweft);
		type_pointst = (TextView) alertLott.findViewById(R.id.type_pointst);
		type_goalst = (TextView) alertLott.findViewById(R.id.type_goalst);
		type_halft = (TextView) alertLott.findViewById(R.id.type_halft);
		type_mixt = (TextView) alertLott.findViewById(R.id.type_mixt);
		View type_wef = alertLott.findViewById(R.id.type_wef);
		View type_rwef = alertLott.findViewById(R.id.type_rwef);
		View type_points = alertLott.findViewById(R.id.type_points);
		View type_goals = alertLott.findViewById(R.id.type_goals);
		View type_half = alertLott.findViewById(R.id.type_half);
		View type_mix = alertLott.findViewById(R.id.type_mix);
		if (Constant.VERSION_NO_FOOTBALLorBASTKETBALL) {
			type_half.setVisibility(View.INVISIBLE);
			type_mix.setVisibility(View.INVISIBLE);
		}
		View bottomView = alertLott.findViewById(R.id.bottomView);
		bottomView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertLott.dismiss();
			}
		});
		switch (Constant.CORPBUY_LOTT_TYPE) {
		case 1:
			type_weft.setTextColor(whiteText);
			type_wef.setBackgroundResource(R.drawable.pink);
			break;
		case 2:
			type_rweft.setTextColor(whiteText);
			type_rwef.setBackgroundResource(R.drawable.pink);
			break;
		case 3:
			type_pointst.setTextColor(whiteText);
			type_points.setBackgroundResource(R.drawable.pink);
			break;
		case 4:
			type_goalst.setTextColor(whiteText);
			type_goals.setBackgroundResource(R.drawable.pink);
			break;
		case 5:
			type_halft.setTextColor(whiteText);
			type_half.setBackgroundResource(R.drawable.pink);
			break;
		case 6:
			type_mixt.setTextColor(whiteText);
			type_mix.setBackgroundResource(R.drawable.pink);
			break;
		}
		type_wef.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_TYPE_CHANGED;
				msg.arg1 = 1;
				Constant.CORPBUY_LOTT_TYPE = 1;
				mHandler.sendMessage(msg);
				alertLott.dismiss();
			}
		});
		type_rwef.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_TYPE_CHANGED;
				msg.arg1 = 2;
				Constant.CORPBUY_LOTT_TYPE = 2;
				mHandler.sendMessage(msg);
				alertLott.dismiss();
			}
		});
		type_points.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_TYPE_CHANGED;
				msg.arg1 = 3;
				Constant.CORPBUY_LOTT_TYPE = 3;
				mHandler.sendMessage(msg);
				alertLott.dismiss();
			}
		});
		type_goals.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_TYPE_CHANGED;
				msg.arg1 = 4;
				Constant.CORPBUY_LOTT_TYPE = 4;
				mHandler.sendMessage(msg);
				alertLott.dismiss();
			}
		});
		type_half.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_TYPE_CHANGED;
				msg.arg1 = 5;
				Constant.CORPBUY_LOTT_TYPE = 5;
				mHandler.sendMessage(msg);
				alertLott.dismiss();
			}
		});
		type_mix.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_TYPE_CHANGED;
				msg.arg1 = 6;
				Constant.CORPBUY_LOTT_TYPE = 6;
				mHandler.sendMessage(msg);
				alertLott.dismiss();
			}
		});
	}

	private void alertExitDialog() {
		final AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.show();
		alert.getWindow().setContentView(R.layout.exit_dialog);

		TextView title = (TextView) alert.findViewById(R.id.title);
		title.setText("确定退出登录？");
		View okButton = alert.findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginText.setText("立即登录");
				sutil.setLoginStatus(false);
				Toast.makeText(HomeActivity.this, "已退出登录", Toast.LENGTH_SHORT)
						.show();
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
