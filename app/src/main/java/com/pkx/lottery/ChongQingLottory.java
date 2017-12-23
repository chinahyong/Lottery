package com.pkx.lottery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.ShakeListener.OnShakeListener;
import com.pkx.lottery.dto.FastCur;
import com.pkx.lottery.dto.lott.ChongQingLott;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.RandomBallsUtils;
import com.pkx.lottery.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.Random;

public class ChongQingLottory extends Activity implements OnClickListener {
	private TextView tenMinute, minute, sec1, sec2, minute1, sec11, sec21;
	private View playTypeView;
	private int timerest = 0;
	private int redText = Color.parseColor("#CD0000");
	private int whiteText = Color.parseColor("#ffffff");
	private SharePreferenceUtil sutil;
	private boolean isFirstView = true;
	private AlertDialog alertLott;
	private ChongQingLott chongqingLott;
	private MyHandler mHandler;
	private Vibrator mVibretor;
	private ShakeListener mShakeListener = null;
	private View oneView, tenView, hundredView, thousandView, tenThousandView,
			groupView, evenOddView1, evenOddView2, clearView, pastView;
	private ImageView CQTenBigView, CQTenSmallView, CQTenOddView,
			CQTenEvenView;
	private TextView CQTenBigText, CQTenSmallText, CQTenOddText, CQTenEvenText,
			typeNameText, period;
	private Thread timeThread;
	private ImageView CQBigView, CQSmallView, CQOddView, CQEvenView;
	private TextView CQBigText, CQSmallText, CQOddText, CQEvenText;
	private int bigSmall, bigSmallTen;
	private ArrayList<ImageView> directBalls, directHBalls, directTBalls,
			directTHBalls, directWBalls, sixBalls, selectedDirectBalls,
			selectedDirectHBalls, selectedDirectTBalls, selectedDirectTHBalls,
			selectedDirectWBalls, selectedSixBalls, CQViews, CQTenViews;
	private ArrayList<TextView> directBallst, directHBallst, directTHBallst,
			directWBallst, directTBallst, sixBallst, CQTexts, CQTenTexts;
	private ImageView three_direct_h0, three_direct_h1, three_direct_h2,
			three_direct_h3, three_direct_h4, three_direct_h5, three_direct_h6,
			three_direct_h7, three_direct_h8, three_direct_h9, three_direct_t0,
			three_direct_t1, three_direct_t2, three_direct_t3, three_direct_t4,
			three_direct_t5, three_direct_t6, three_direct_t7, three_direct_t8,
			three_direct_t9, three_direct_0, three_direct_1, three_direct_2,
			three_direct_3, three_direct_4, three_direct_5, three_direct_6,
			three_direct_7, three_direct_8, three_direct_9, three_six_0,
			three_six_1, three_six_2, three_six_3, three_six_4, three_six_5,
			three_six_6, three_six_7, three_six_8, three_six_9;
	private ImageView three_direct_th0, three_direct_th1, three_direct_th2,
			three_direct_th3, three_direct_th4, three_direct_th5,
			three_direct_th6, three_direct_th7, three_direct_th8,
			three_direct_th9, three_direct_wh0, three_direct_wh1,
			three_direct_wh2, three_direct_wh3, three_direct_wh4,
			three_direct_wh5, three_direct_wh6, three_direct_wh7,
			three_direct_wh8, three_direct_wh9;
	private TextView three_direct_th0t, three_direct_th1t, three_direct_th2t,
			three_direct_th3t, three_direct_th4t, three_direct_th5t,
			three_direct_th6t, three_direct_th7t, three_direct_th8t,
			three_direct_th9t, three_direct_wh0t, three_direct_wh1t,
			three_direct_wh2t, three_direct_wh3t, three_direct_wh4t,
			three_direct_wh5t, three_direct_wh6tt, three_direct_wh7t,
			three_direct_wh8t, three_direct_wh9t;
	private TextView three_direct_h0t, three_direct_h1t, three_direct_h2t,
			three_direct_h3t, three_direct_h4t, three_direct_h5t,
			three_direct_h6t, three_direct_h7t, three_direct_h8t,
			three_direct_h9t, three_direct_t0t, three_direct_t1t,
			three_direct_t2t, three_direct_t3t, three_direct_t4t,
			three_direct_t5t, three_direct_t6t, three_direct_t7t,
			three_direct_t8t, three_direct_t9t, three_direct_0t,
			three_direct_1t, three_direct_2t, three_direct_3t, three_direct_4t,
			three_direct_5t, three_direct_6t, three_direct_7t, three_direct_8t,
			three_direct_9t, three_six_0t, three_six_1t, three_six_2t,
			three_six_3t, three_six_4t, three_six_5t, three_six_6t,
			three_six_7t, three_six_8t, three_six_9t;
	private TextView billText, dimentionIntro;
	private View handleBet;
	private Intent mIntent;
	private boolean isTong;
	private View frontView;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chongqing_lott);
		initViews();
		RequestParams params = new RequestParams();
		params.put("type", "6");
		Net.post(true, this, Constant.PAY_URL
				+ "/ajax/index.php?act=load_lottery_info", params, mHandler,
				Constant.NET_ACTION_SECRET_SECURE);
		timeThread = new Thread(new MyThread());
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		mIntent = getIntent();
		mShakeListener = new ShakeListener(this);
		mShakeListener.setOnShakeListener(new OnShakeListener() {
			@Override
			public void onShake() {
				// if (currentPage == 1)
				// return;
				startVibrato1();
				mShakeListener.stop();
				Message msg = new Message();
				msg.what = Constant.SHAKE_MESSAGE;
				mHandler.sendMessage(msg);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						mShakeListener.start();
					}
				}, 1000);
			}
		});
		mShakeListener.start();
		Constant.TICK_TOCK_FLAG = true;
		ChongQingLott inbet = (ChongQingLott) mIntent
				.getSerializableExtra("editBet");
		if (Constant.CHONGQING_PLAY_TYPE == 9) {
			isTong = true;
		} else {
			isTong = false;
		}
		if (null != inbet) {
			Constant.CHONGQING_PLAY_TYPE = inbet.getPlayType();
			if (Constant.CHONGQING_PLAY_TYPE == 9) {
				isTong = true;
			} else {
				isTong = false;
			}
			switch (Constant.CHONGQING_PLAY_TYPE) {
			case 1:
				bigSmall = inbet.getRedBalls().get(1);
				bigSmallTen = inbet.getRedBalls().get(0);
				CQViews.get(inbet.getRedBalls().get(1) - 1)
						.setBackgroundResource(R.drawable.rect_red);
				CQViews.get(inbet.getRedBalls().get(1) - 1).setTag(1);
				CQTenViews.get(inbet.getRedBalls().get(0) - 1).setTag(1);
				CQTenViews.get(inbet.getRedBalls().get(0) - 1)
						.setBackgroundResource(R.drawable.rect_red);
				CQTexts.get(inbet.getRedBalls().get(1) - 1).setTextColor(
						whiteText);
				CQTenTexts.get(inbet.getRedBalls().get(0) - 1).setTextColor(
						whiteText);
				break;
			case 2:
			case 4:
			case 6:
			case 7:
				for (int i : inbet.getRedBalls()) {
					selectedSixBalls.add(sixBalls.get(i));
					sixBalls.get(i).setImageResource(R.drawable.black_ball);
					sixBalls.get(i).setTag(1);
					sixBallst.get(i).setTextColor(whiteText);
				}
				break;
			case 3:
				for (int i : inbet.getTenRedBalls()) {
					selectedDirectTBalls.add(directTBalls.get(i));
					directTBalls.get(i).setImageResource(R.drawable.black_ball);
					directTBalls.get(i).setTag(1);
					directTBallst.get(i).setTextColor(whiteText);
				}
				for (int i : inbet.getRedBalls()) {
					selectedDirectBalls.add(directBalls.get(i));
					directBalls.get(i).setImageResource(R.drawable.black_ball);
					directBalls.get(i).setTag(1);
					directBallst.get(i).setTextColor(whiteText);

				}
				break;
			case 5:

				for (int i : inbet.getTenRedBalls()) {
					selectedDirectTBalls.add(directTBalls.get(i));
					directTBalls.get(i).setImageResource(R.drawable.black_ball);
					directTBalls.get(i).setTag(1);
					directTBallst.get(i).setTextColor(whiteText);
				}
				for (int i : inbet.getRedBalls()) {
					selectedDirectBalls.add(directBalls.get(i));
					directBalls.get(i).setImageResource(R.drawable.black_ball);
					directBalls.get(i).setTag(1);
					directBallst.get(i).setTextColor(whiteText);

				}
				for (int i : inbet.getHunRedBalls()) {
					selectedDirectHBalls.add(directHBalls.get(i));
					directHBalls.get(i).setImageResource(R.drawable.black_ball);
					directHBalls.get(i).setTag(1);
					directHBallst.get(i).setTextColor(whiteText);
				}
				break;
			case 8:
			case 9:
				for (int i : inbet.getTenRedBalls()) {
					selectedDirectTBalls.add(directTBalls.get(i));
					directTBalls.get(i).setImageResource(R.drawable.black_ball);
					directTBalls.get(i).setTag(1);
					directTBallst.get(i).setTextColor(whiteText);
				}
				for (int i : inbet.getRedBalls()) {
					selectedDirectBalls.add(directBalls.get(i));
					directBalls.get(i).setImageResource(R.drawable.black_ball);
					directBalls.get(i).setTag(1);
					directBallst.get(i).setTextColor(whiteText);
				}
				for (int i : inbet.getHunRedBalls()) {
					selectedDirectHBalls.add(directHBalls.get(i));
					directHBalls.get(i).setImageResource(R.drawable.black_ball);
					directHBalls.get(i).setTag(1);
					directHBallst.get(i).setTextColor(whiteText);
				}
				for (int i : inbet.getThouRedBalls()) {
					selectedDirectTHBalls.add(directTHBalls.get(i));
					directTHBalls.get(i)
							.setImageResource(R.drawable.black_ball);
					directTHBalls.get(i).setTag(1);
					directTHBallst.get(i).setTextColor(whiteText);
				}
				for (int i : inbet.getwRedBalls()) {
					selectedDirectWBalls.add(directWBalls.get(i));
					directWBalls.get(i).setImageResource(R.drawable.black_ball);
					directWBalls.get(i).setTag(1);
					directWBallst.get(i).setTextColor(whiteText);
				}
				break;
			}
			checkBill();
		}
		oneView.setVisibility(View.GONE);
		tenView.setVisibility(View.GONE);
		hundredView.setVisibility(View.GONE);
		thousandView.setVisibility(View.GONE);
		tenThousandView.setVisibility(View.GONE);
		groupView.setVisibility(View.GONE);
		evenOddView1.setVisibility(View.GONE);
		evenOddView2.setVisibility(View.GONE);
		// currentPage=Constant.CHONGQING_PLAY_TYPE;
		switch (Constant.CHONGQING_PLAY_TYPE) {
		case 1:
			typeNameText.setText("重庆时时彩-大小单双");
			dimentionIntro.setText("所选大小单双与开奖号码后两位一致,且顺序相同,即中4元");
			evenOddView1.setVisibility(View.VISIBLE);
			evenOddView2.setVisibility(View.VISIBLE);
			break;
		case 2:
			typeNameText.setText("重庆时时彩-一星直选");
			dimentionIntro.setText("所选号码与开奖号码后一位一致,即中奖金10元");
			groupView.setVisibility(View.VISIBLE);
			break;
		case 3:
			typeNameText.setText("重庆时时彩-二星直选");
			dimentionIntro.setText("所选号码与开奖号码后两位一致,且顺序相同,即中奖金100元");
			oneView.setVisibility(View.VISIBLE);
			tenView.setVisibility(View.VISIBLE);
			break;
		case 4:
			typeNameText.setText("重庆时时彩-二星组选");
			dimentionIntro.setText("所选号码与开奖号码后两位一致,顺序不限,即中奖金50元");
			groupView.setVisibility(View.VISIBLE);
			break;
		case 5:
			typeNameText.setText("重庆时时彩-三星直选");
			dimentionIntro.setText("所选号码与开奖号码后三位一致,且顺序相同,即中奖金1000元");
			oneView.setVisibility(View.VISIBLE);
			tenView.setVisibility(View.VISIBLE);
			hundredView.setVisibility(View.VISIBLE);
			break;
		case 6:
			typeNameText.setText("重庆时时彩-三星组三");
			dimentionIntro.setText("所选号码与开奖号码后三位一致,顺序不限,即中奖金320元");
			groupView.setVisibility(View.VISIBLE);
			break;
		case 7:
			typeNameText.setText("重庆时时彩-三星组六");
			dimentionIntro.setText("所选号码与开奖号码后三位一致,顺序不限即中奖金160元");
			groupView.setVisibility(View.VISIBLE);
			break;
		case 8:
			typeNameText.setText("重庆时时彩-五星直选");
			dimentionIntro.setText("所选号码与开奖号码一致,且顺序相同,即中奖金100000元");
			oneView.setVisibility(View.VISIBLE);
			tenView.setVisibility(View.VISIBLE);
			hundredView.setVisibility(View.VISIBLE);
			thousandView.setVisibility(View.VISIBLE);
			tenThousandView.setVisibility(View.VISIBLE);
			break;
		case 9:
			typeNameText.setText("重庆时时彩-五星通选");
			dimentionIntro
					.setText("每位选一个或多个号码,按顺序全部命中,命中前三或者后三,命中前二或者后二,即中奖金20000/200/20元");
			oneView.setVisibility(View.VISIBLE);
			tenView.setVisibility(View.VISIBLE);
			hundredView.setVisibility(View.VISIBLE);
			thousandView.setVisibility(View.VISIBLE);
			tenThousandView.setVisibility(View.VISIBLE);
			break;
		}
	}

	private void initViews() {
		// private View
		// oneView,tenView,hundredView,thousandView,tenThousandView,groupView,evenOddView1,evenOddView2;
		// private ImageView
		// CQTenBigView,CQTenSmallView,CQTenOddView,CQTenEvenView;
		// private TextView
		// CQTenBigText,CQTenSmallText,CQTenOddText,CQTenEvenText;
		// private ImageView CQBigView,CQSmallView,CQOddView,CQEvenView;
		// private TextView CQBigText,CQSmallText,CQOddText,CQEvenText;
		mHandler = new MyHandler();
		period = (TextView) findViewById(R.id.period);
		pastView = findViewById(R.id.pastView);
		pastView.setOnClickListener(this);
		clearView = findViewById(R.id.clearView);
		clearView.setOnClickListener(this);
		playTypeView = findViewById(R.id.playTypeView);
		playTypeView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertLottTypeDialog();
			}
		});
		alertLott = new AlertDialog.Builder(this).create();
		frontView = findViewById(R.id.frontView);
		tenMinute = (TextView) findViewById(R.id.tenMinute);
		minute = (TextView) findViewById(R.id.minute);
		sec1 = (TextView) findViewById(R.id.sec1);
		sec2 = (TextView) findViewById(R.id.sec2);
		minute1 = (TextView) findViewById(R.id.minute1);
		sec11 = (TextView) findViewById(R.id.sec11);
		sec21 = (TextView) findViewById(R.id.sec21);
		frontView.setOnClickListener(this);
		oneView = findViewById(R.id.oneView);
		tenView = findViewById(R.id.tenView);
		hundredView = findViewById(R.id.hundredView);
		thousandView = findViewById(R.id.thousandView);
		tenThousandView = findViewById(R.id.tenThousandView);
		groupView = findViewById(R.id.groupView);
		evenOddView1 = findViewById(R.id.evenOddView1);
		evenOddView2 = findViewById(R.id.evenOddView2);
		CQTenBigView = (ImageView) findViewById(R.id.CQTenBigView);
		CQTenSmallView = (ImageView) findViewById(R.id.CQTenSmallView);
		CQTenOddView = (ImageView) findViewById(R.id.CQTenOddView);
		CQTenEvenView = (ImageView) findViewById(R.id.CQTenEvenView);
		CQBigView = (ImageView) findViewById(R.id.CQBigView);
		CQSmallView = (ImageView) findViewById(R.id.CQSmallView);
		CQOddView = (ImageView) findViewById(R.id.CQOddView);
		CQEvenView = (ImageView) findViewById(R.id.CQEvenView);
		CQTenBigText = (TextView) findViewById(R.id.CQTenBigText);
		CQTenSmallText = (TextView) findViewById(R.id.CQTenSmallText);
		CQTenOddText = (TextView) findViewById(R.id.CQTenOddText);
		CQTenEvenText = (TextView) findViewById(R.id.CQTenEvenText);
		CQBigText = (TextView) findViewById(R.id.CQBigText);
		CQSmallText = (TextView) findViewById(R.id.CQSmallText);
		CQOddText = (TextView) findViewById(R.id.CQOddText);
		CQEvenText = (TextView) findViewById(R.id.CQEvenText);
		billText = (TextView) findViewById(R.id.billText);
		dimentionIntro = (TextView) findViewById(R.id.dimentionIntro);
		CQTenViews = new ArrayList<ImageView>();
		CQViews = new ArrayList<ImageView>();
		CQTenTexts = new ArrayList<TextView>();
		CQTexts = new ArrayList<TextView>();
		CQViews.add(CQBigView);
		CQViews.add(CQSmallView);
		CQViews.add(CQOddView);
		CQViews.add(CQEvenView);
		CQTenViews.add(CQTenBigView);
		CQTenViews.add(CQTenSmallView);
		CQTenViews.add(CQTenOddView);
		CQTenViews.add(CQTenEvenView);
		CQTexts.add(CQBigText);
		CQTexts.add(CQSmallText);
		CQTexts.add(CQOddText);
		CQTexts.add(CQEvenText);
		CQTenTexts.add(CQTenBigText);
		CQTenTexts.add(CQTenSmallText);
		CQTenTexts.add(CQTenOddText);
		CQTenTexts.add(CQTenEvenText);
		sutil = new SharePreferenceUtil(this);
		mVibretor = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		directBalls = new ArrayList<ImageView>();
		directHBalls = new ArrayList<ImageView>();
		directTBalls = new ArrayList<ImageView>();
		directTHBalls = new ArrayList<ImageView>();
		directWBalls = new ArrayList<ImageView>();
		sixBalls = new ArrayList<ImageView>();
		directBallst = new ArrayList<TextView>();
		directHBallst = new ArrayList<TextView>();
		directTHBallst = new ArrayList<TextView>();
		directWBallst = new ArrayList<TextView>();
		directTBallst = new ArrayList<TextView>();
		sixBallst = new ArrayList<TextView>();
		selectedDirectBalls = new ArrayList<ImageView>();
		selectedDirectHBalls = new ArrayList<ImageView>();
		selectedDirectTHBalls = new ArrayList<ImageView>();
		selectedDirectWBalls = new ArrayList<ImageView>();
		selectedDirectTBalls = new ArrayList<ImageView>();
		selectedSixBalls = new ArrayList<ImageView>();
		three_direct_h0 = (ImageView) findViewById(R.id.three_direct_h0);
		three_direct_h1 = (ImageView) findViewById(R.id.three_direct_h1);
		three_direct_h2 = (ImageView) findViewById(R.id.three_direct_h2);
		three_direct_h3 = (ImageView) findViewById(R.id.three_direct_h3);
		three_direct_h4 = (ImageView) findViewById(R.id.three_direct_h4);
		three_direct_h5 = (ImageView) findViewById(R.id.three_direct_h5);
		three_direct_h6 = (ImageView) findViewById(R.id.three_direct_h6);
		three_direct_h7 = (ImageView) findViewById(R.id.three_direct_h7);
		three_direct_h8 = (ImageView) findViewById(R.id.three_direct_h8);
		three_direct_h9 = (ImageView) findViewById(R.id.three_direct_h9);
		three_direct_h0t = (TextView) findViewById(R.id.three_direct_h0t);
		three_direct_h1t = (TextView) findViewById(R.id.three_direct_h1t);
		three_direct_h2t = (TextView) findViewById(R.id.three_direct_h2t);
		three_direct_h3t = (TextView) findViewById(R.id.three_direct_h3t);
		three_direct_h4t = (TextView) findViewById(R.id.three_direct_h4t);
		three_direct_h5t = (TextView) findViewById(R.id.three_direct_h5t);
		three_direct_h6t = (TextView) findViewById(R.id.three_direct_h6t);
		three_direct_h7t = (TextView) findViewById(R.id.three_direct_h7t);
		three_direct_h8t = (TextView) findViewById(R.id.three_direct_h8t);
		three_direct_h9t = (TextView) findViewById(R.id.three_direct_h9t);
		typeNameText = (TextView) findViewById(R.id.typeNameText);
		directHBalls.add(three_direct_h0);
		directHBalls.add(three_direct_h1);
		directHBalls.add(three_direct_h2);
		directHBalls.add(three_direct_h3);
		directHBalls.add(three_direct_h4);
		directHBalls.add(three_direct_h5);
		directHBalls.add(three_direct_h6);
		directHBalls.add(three_direct_h7);
		directHBalls.add(three_direct_h8);
		directHBalls.add(three_direct_h9);
		directHBallst.add(three_direct_h0t);
		directHBallst.add(three_direct_h1t);
		directHBallst.add(three_direct_h2t);
		directHBallst.add(three_direct_h3t);
		directHBallst.add(three_direct_h4t);
		directHBallst.add(three_direct_h5t);
		directHBallst.add(three_direct_h6t);
		directHBallst.add(three_direct_h7t);
		directHBallst.add(three_direct_h8t);
		directHBallst.add(three_direct_h9t);

		three_direct_th0 = (ImageView) findViewById(R.id.three_direct_th0);
		three_direct_th1 = (ImageView) findViewById(R.id.three_direct_th1);
		three_direct_th2 = (ImageView) findViewById(R.id.three_direct_th2);
		three_direct_th3 = (ImageView) findViewById(R.id.three_direct_th3);
		three_direct_th4 = (ImageView) findViewById(R.id.three_direct_th4);
		three_direct_th5 = (ImageView) findViewById(R.id.three_direct_th5);
		three_direct_th6 = (ImageView) findViewById(R.id.three_direct_th6);
		three_direct_th7 = (ImageView) findViewById(R.id.three_direct_th7);
		three_direct_th8 = (ImageView) findViewById(R.id.three_direct_th8);
		three_direct_th9 = (ImageView) findViewById(R.id.three_direct_th9);
		three_direct_th0t = (TextView) findViewById(R.id.three_direct_th0t);
		three_direct_th1t = (TextView) findViewById(R.id.three_direct_th1t);
		three_direct_th2t = (TextView) findViewById(R.id.three_direct_th2t);
		three_direct_th3t = (TextView) findViewById(R.id.three_direct_th3t);
		three_direct_th4t = (TextView) findViewById(R.id.three_direct_th4t);
		three_direct_th5t = (TextView) findViewById(R.id.three_direct_th5t);
		three_direct_th6t = (TextView) findViewById(R.id.three_direct_th6t);
		three_direct_th7t = (TextView) findViewById(R.id.three_direct_th7t);
		three_direct_th8t = (TextView) findViewById(R.id.three_direct_th8t);
		three_direct_th9t = (TextView) findViewById(R.id.three_direct_th9t);
		directTHBalls.add(three_direct_th0);
		directTHBalls.add(three_direct_th1);
		directTHBalls.add(three_direct_th2);
		directTHBalls.add(three_direct_th3);
		directTHBalls.add(three_direct_th4);
		directTHBalls.add(three_direct_th5);
		directTHBalls.add(three_direct_th6);
		directTHBalls.add(three_direct_th7);
		directTHBalls.add(three_direct_th8);
		directTHBalls.add(three_direct_th9);
		directTHBallst.add(three_direct_th0t);
		directTHBallst.add(three_direct_th1t);
		directTHBallst.add(three_direct_th2t);
		directTHBallst.add(three_direct_th3t);
		directTHBallst.add(three_direct_th4t);
		directTHBallst.add(three_direct_th5t);
		directTHBallst.add(three_direct_th6t);
		directTHBallst.add(three_direct_th7t);
		directTHBallst.add(three_direct_th8t);
		directTHBallst.add(three_direct_th9t);
		three_direct_wh0 = (ImageView) findViewById(R.id.three_direct_wh0);
		three_direct_wh1 = (ImageView) findViewById(R.id.three_direct_wh1);
		three_direct_wh2 = (ImageView) findViewById(R.id.three_direct_wh2);
		three_direct_wh3 = (ImageView) findViewById(R.id.three_direct_wh3);
		three_direct_wh4 = (ImageView) findViewById(R.id.three_direct_wh4);
		three_direct_wh5 = (ImageView) findViewById(R.id.three_direct_wh5);
		three_direct_wh6 = (ImageView) findViewById(R.id.three_direct_wh6);
		three_direct_wh7 = (ImageView) findViewById(R.id.three_direct_wh7);
		three_direct_wh8 = (ImageView) findViewById(R.id.three_direct_wh8);
		three_direct_wh9 = (ImageView) findViewById(R.id.three_direct_wh9);
		three_direct_wh0t = (TextView) findViewById(R.id.three_direct_wh0t);
		three_direct_wh1t = (TextView) findViewById(R.id.three_direct_wh1t);
		three_direct_wh2t = (TextView) findViewById(R.id.three_direct_wh2t);
		three_direct_wh3t = (TextView) findViewById(R.id.three_direct_wh3t);
		three_direct_wh4t = (TextView) findViewById(R.id.three_direct_wh4t);
		three_direct_wh5t = (TextView) findViewById(R.id.three_direct_wh5t);
		three_direct_wh6tt = (TextView) findViewById(R.id.three_direct_wh6t);
		three_direct_wh7t = (TextView) findViewById(R.id.three_direct_wh7t);
		three_direct_wh8t = (TextView) findViewById(R.id.three_direct_wh8t);
		three_direct_wh9t = (TextView) findViewById(R.id.three_direct_wh9t);
		directWBalls.add(three_direct_wh0);
		directWBalls.add(three_direct_wh1);
		directWBalls.add(three_direct_wh2);
		directWBalls.add(three_direct_wh3);
		directWBalls.add(three_direct_wh4);
		directWBalls.add(three_direct_wh5);
		directWBalls.add(three_direct_wh6);
		directWBalls.add(three_direct_wh7);
		directWBalls.add(three_direct_wh8);
		directWBalls.add(three_direct_wh9);
		directWBallst.add(three_direct_wh0t);
		directWBallst.add(three_direct_wh1t);
		directWBallst.add(three_direct_wh2t);
		directWBallst.add(three_direct_wh3t);
		directWBallst.add(three_direct_wh4t);
		directWBallst.add(three_direct_wh5t);
		directWBallst.add(three_direct_wh6tt);
		directWBallst.add(three_direct_wh7t);
		directWBallst.add(three_direct_wh8t);
		directWBallst.add(three_direct_wh9t);
		three_direct_t0 = (ImageView) findViewById(R.id.three_direct_t0);
		three_direct_t1 = (ImageView) findViewById(R.id.three_direct_t1);
		three_direct_t2 = (ImageView) findViewById(R.id.three_direct_t2);
		three_direct_t3 = (ImageView) findViewById(R.id.three_direct_t3);
		three_direct_t4 = (ImageView) findViewById(R.id.three_direct_t4);
		three_direct_t5 = (ImageView) findViewById(R.id.three_direct_t5);
		three_direct_t6 = (ImageView) findViewById(R.id.three_direct_t6);
		three_direct_t7 = (ImageView) findViewById(R.id.three_direct_t7);
		three_direct_t8 = (ImageView) findViewById(R.id.three_direct_t8);
		three_direct_t9 = (ImageView) findViewById(R.id.three_direct_t9);
		three_direct_t0t = (TextView) findViewById(R.id.three_direct_t0t);
		three_direct_t1t = (TextView) findViewById(R.id.three_direct_t1t);
		three_direct_t2t = (TextView) findViewById(R.id.three_direct_t2t);
		three_direct_t3t = (TextView) findViewById(R.id.three_direct_t3t);
		three_direct_t4t = (TextView) findViewById(R.id.three_direct_t4t);
		three_direct_t5t = (TextView) findViewById(R.id.three_direct_t5t);
		three_direct_t6t = (TextView) findViewById(R.id.three_direct_t6t);
		three_direct_t7t = (TextView) findViewById(R.id.three_direct_t7t);
		three_direct_t8t = (TextView) findViewById(R.id.three_direct_t8t);
		three_direct_t9t = (TextView) findViewById(R.id.three_direct_t9t);
		directTBalls.add(three_direct_t0);
		directTBalls.add(three_direct_t1);
		directTBalls.add(three_direct_t2);
		directTBalls.add(three_direct_t3);
		directTBalls.add(three_direct_t4);
		directTBalls.add(three_direct_t5);
		directTBalls.add(three_direct_t6);
		directTBalls.add(three_direct_t7);
		directTBalls.add(three_direct_t8);
		directTBalls.add(three_direct_t9);
		directTBallst.add(three_direct_t0t);
		directTBallst.add(three_direct_t1t);
		directTBallst.add(three_direct_t2t);
		directTBallst.add(three_direct_t3t);
		directTBallst.add(three_direct_t4t);
		directTBallst.add(three_direct_t5t);
		directTBallst.add(three_direct_t6t);
		directTBallst.add(three_direct_t7t);
		directTBallst.add(three_direct_t8t);
		directTBallst.add(three_direct_t9t);
		three_direct_0 = (ImageView) findViewById(R.id.three_direct_0);
		three_direct_1 = (ImageView) findViewById(R.id.three_direct_1);
		three_direct_2 = (ImageView) findViewById(R.id.three_direct_2);
		three_direct_3 = (ImageView) findViewById(R.id.three_direct_3);
		three_direct_4 = (ImageView) findViewById(R.id.three_direct_4);
		three_direct_5 = (ImageView) findViewById(R.id.three_direct_5);
		three_direct_6 = (ImageView) findViewById(R.id.three_direct_6);
		three_direct_7 = (ImageView) findViewById(R.id.three_direct_7);
		three_direct_8 = (ImageView) findViewById(R.id.three_direct_8);
		three_direct_9 = (ImageView) findViewById(R.id.three_direct_9);
		three_direct_0t = (TextView) findViewById(R.id.three_direct_0t);
		three_direct_1t = (TextView) findViewById(R.id.three_direct_1t);
		three_direct_2t = (TextView) findViewById(R.id.three_direct_2t);
		three_direct_3t = (TextView) findViewById(R.id.three_direct_3t);
		three_direct_4t = (TextView) findViewById(R.id.three_direct_4t);
		three_direct_5t = (TextView) findViewById(R.id.three_direct_5t);
		three_direct_6t = (TextView) findViewById(R.id.three_direct_6t);
		three_direct_7t = (TextView) findViewById(R.id.three_direct_7t);
		three_direct_8t = (TextView) findViewById(R.id.three_direct_8t);
		three_direct_9t = (TextView) findViewById(R.id.three_direct_9t);
		directBalls.add(three_direct_0);
		directBalls.add(three_direct_1);
		directBalls.add(three_direct_2);
		directBalls.add(three_direct_3);
		directBalls.add(three_direct_4);
		directBalls.add(three_direct_5);
		directBalls.add(three_direct_6);
		directBalls.add(three_direct_7);
		directBalls.add(three_direct_8);
		directBalls.add(three_direct_9);
		directBallst.add(three_direct_0t);
		directBallst.add(three_direct_1t);
		directBallst.add(three_direct_2t);
		directBallst.add(three_direct_3t);
		directBallst.add(three_direct_4t);
		directBallst.add(three_direct_5t);
		directBallst.add(three_direct_6t);
		directBallst.add(three_direct_7t);
		directBallst.add(three_direct_8t);
		directBallst.add(three_direct_9t);
		three_six_0t = (TextView) findViewById(R.id.three_six_0t);
		three_six_1t = (TextView) findViewById(R.id.three_six_1t);
		three_six_2t = (TextView) findViewById(R.id.three_six_2t);
		three_six_3t = (TextView) findViewById(R.id.three_six_3t);
		three_six_4t = (TextView) findViewById(R.id.three_six_4t);
		three_six_5t = (TextView) findViewById(R.id.three_six_5t);
		three_six_6t = (TextView) findViewById(R.id.three_six_6t);
		three_six_7t = (TextView) findViewById(R.id.three_six_7t);
		three_six_8t = (TextView) findViewById(R.id.three_six_8t);
		three_six_9t = (TextView) findViewById(R.id.three_six_9t);
		three_six_0 = (ImageView) findViewById(R.id.three_six_0);
		three_six_1 = (ImageView) findViewById(R.id.three_six_1);
		three_six_2 = (ImageView) findViewById(R.id.three_six_2);
		three_six_3 = (ImageView) findViewById(R.id.three_six_3);
		three_six_4 = (ImageView) findViewById(R.id.three_six_4);
		three_six_5 = (ImageView) findViewById(R.id.three_six_5);
		three_six_6 = (ImageView) findViewById(R.id.three_six_6);
		three_six_7 = (ImageView) findViewById(R.id.three_six_7);
		three_six_8 = (ImageView) findViewById(R.id.three_six_8);
		three_six_9 = (ImageView) findViewById(R.id.three_six_9);
		sixBallst.add(three_six_0t);
		sixBallst.add(three_six_1t);
		sixBallst.add(three_six_2t);
		sixBallst.add(three_six_3t);
		sixBallst.add(three_six_4t);
		sixBallst.add(three_six_5t);
		sixBallst.add(three_six_6t);
		sixBallst.add(three_six_7t);
		sixBallst.add(three_six_8t);
		sixBallst.add(three_six_9t);
		sixBalls.add(three_six_0);
		sixBalls.add(three_six_1);
		sixBalls.add(three_six_2);
		sixBalls.add(three_six_3);
		sixBalls.add(three_six_4);
		sixBalls.add(three_six_5);
		sixBalls.add(three_six_6);
		sixBalls.add(three_six_7);
		sixBalls.add(three_six_8);
		sixBalls.add(three_six_9);
		handleBet = findViewById(R.id.handleBet);
		handleBet.setOnClickListener(this);
		for (ImageView iv : sixBalls) {
			if (iv != null) {
				Log.e("pkx", "iv!=null  size" + sixBalls.size());
			} else {

				Log.e("pkx", "iv=======null  size");
			}
		}
		for (int i = 0; i < CQViews.size(); i++) {
			CQViews.get(i).setOnClickListener(this);
			CQViews.get(i).setTag(0);
			CQViews.get(i).setTag(three_direct_0.getId(), "cq");// 设置球型标志
			CQViews.get(i).setTag(three_direct_1.getId(), i);// 球号标志
			CQTexts.get(i).setTextColor(redText);
		}
		for (int i = 0; i < CQTenViews.size(); i++) {
			CQTenViews.get(i).setOnClickListener(this);
			CQTenViews.get(i).setTag(0);
			CQTenViews.get(i).setTag(three_direct_0.getId(), "cqt");// 设置球型标志
			CQTenViews.get(i).setTag(three_direct_1.getId(), i);// 球号标志
			CQTenTexts.get(i).setTextColor(redText);
		}
		for (int i = 0; i < directHBalls.size(); i++) {
			directHBalls.get(i).setOnClickListener(this);
			directHBalls.get(i).setTag(0);
			directHBalls.get(i).setTag(three_direct_0.getId(), "dh");// 设置球型标志
			directHBalls.get(i).setTag(three_direct_1.getId(), i);// 球号标志
			directHBallst.get(i).setTextColor(redText);
		}
		for (int i = 0; i < directTHBalls.size(); i++) {
			directTHBalls.get(i).setOnClickListener(this);
			directTHBalls.get(i).setTag(0);
			directTHBalls.get(i).setTag(three_direct_0.getId(), "dth");// 设置球型标志
			directTHBalls.get(i).setTag(three_direct_1.getId(), i);// 球号标志
			directTHBallst.get(i).setTextColor(redText);
		}
		for (int i = 0; i < directWBalls.size(); i++) {
			directWBalls.get(i).setOnClickListener(this);
			directWBalls.get(i).setTag(0);
			directWBalls.get(i).setTag(three_direct_0.getId(), "dwh");// 设置球型标志
			directWBalls.get(i).setTag(three_direct_1.getId(), i);// 球号标志
			directWBallst.get(i).setTextColor(redText);
		}
		for (int i = 0; i < directTBalls.size(); i++) {
			directTBalls.get(i).setOnClickListener(this);
			directTBalls.get(i).setTag(0);
			directTBalls.get(i).setTag(three_direct_0.getId(), "dt");
			directTBalls.get(i).setTag(three_direct_1.getId(), i);
			directTBallst.get(i).setTextColor(redText);
		}
		for (int i = 0; i < directBalls.size(); i++) {
			directBalls.get(i).setOnClickListener(this);
			directBalls.get(i).setTag(0);
			directBalls.get(i).setTag(three_direct_0.getId(), "d");
			directBalls.get(i).setTag(three_direct_1.getId(), i);
			directBallst.get(i).setTextColor(redText);
		}
		for (int i = 0; i < sixBalls.size(); i++) {
			sixBalls.get(i).setOnClickListener(this);
			sixBalls.get(i).setTag(0);
			sixBalls.get(i).setTag(three_direct_0.getId(), "s");
			sixBalls.get(i).setTag(three_direct_1.getId(), i);
			sixBallst.get(i).setTextColor(redText);
		}

	}

	private void alertLottTypeDialog() {
		if (alertLott.isShowing()) {
			alertLott.dismiss();
			return;
		}
		int whiteText = Color.parseColor("#ffffff");
		alertLott.show();
		alertLott.getWindow().setContentView(
				R.layout.chongqing_play_type_dialog);
		TextView type_big_small_text, type_one_star_text, type_two_star_text, type_two_star_group_text, type_three_star_text, type_three_star_group_text, type_three_group_six_text, type_five_star_text, type_five_star_all_text;
		View type_big_small, type_one_star, type_two_star, type_two_star_group, type_three_star, type_three_star_group, type_three_group_six, type_five_star, type_five_star_all;
		type_big_small_text = (TextView) alertLott
				.findViewById(R.id.type_big_small_text);
		type_one_star_text = (TextView) alertLott
				.findViewById(R.id.type_one_star_text);
		type_two_star_text = (TextView) alertLott
				.findViewById(R.id.type_two_star_text);
		type_two_star_group_text = (TextView) alertLott
				.findViewById(R.id.type_two_star_group_text);
		type_three_star_text = (TextView) alertLott
				.findViewById(R.id.type_three_star_text);
		type_three_star_group_text = (TextView) alertLott
				.findViewById(R.id.type_three_star_group_text);
		type_three_group_six_text = (TextView) alertLott
				.findViewById(R.id.type_three_star_group_six_text);
		type_five_star_text = (TextView) alertLott
				.findViewById(R.id.type_five_star_text);
		type_five_star_all_text = (TextView) alertLott
				.findViewById(R.id.type_five_star_all_text);
		type_big_small = alertLott.findViewById(R.id.type_big_small);
		type_big_small.setTag(1);
		type_one_star = alertLott.findViewById(R.id.type_one_star);
		type_one_star.setTag(2);
		type_two_star = alertLott.findViewById(R.id.type_two_star);
		type_two_star.setTag(3);
		type_two_star_group = alertLott.findViewById(R.id.type_two_star_group);
		type_two_star_group.setTag(4);
		type_three_star = alertLott.findViewById(R.id.type_three_star);
		type_three_star.setTag(5);
		type_three_star_group = alertLott
				.findViewById(R.id.type_three_star_group);
		type_three_star_group.setTag(6);
		type_three_group_six = alertLott
				.findViewById(R.id.type_three_star_group_six);
		type_three_group_six.setTag(7);
		type_five_star = alertLott.findViewById(R.id.type_five_star);
		type_five_star.setTag(8);
		type_five_star_all = alertLott.findViewById(R.id.type_five_star_all);
		type_five_star_all.setTag(9);
		View bottomView = alertLott.findViewById(R.id.bottomView);
		bottomView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertLott.dismiss();
			}
		});
		switch (Constant.CHONGQING_PLAY_TYPE) {
		case 1:
			type_big_small_text.setTextColor(whiteText);
			type_big_small.setBackgroundResource(R.drawable.pink);
			break;
		case 2:
			type_one_star_text.setTextColor(whiteText);
			type_one_star.setBackgroundResource(R.drawable.pink);
			break;
		case 3:
			type_two_star_text.setTextColor(whiteText);
			type_two_star.setBackgroundResource(R.drawable.pink);
			break;
		case 4:
			type_two_star_group_text.setTextColor(whiteText);
			type_two_star_group.setBackgroundResource(R.drawable.pink);
			break;
		case 5:
			type_three_star_text.setTextColor(whiteText);
			type_three_star.setBackgroundResource(R.drawable.pink);
			break;
		case 6:
			type_three_star_group_text.setTextColor(whiteText);
			type_three_star_group.setBackgroundResource(R.drawable.pink);
			break;
		case 7:
			type_three_group_six_text.setTextColor(whiteText);
			type_three_group_six.setBackgroundResource(R.drawable.pink);
			break;
		case 8:
			type_five_star_text.setTextColor(whiteText);
			type_five_star.setBackgroundResource(R.drawable.pink);
			break;
		case 9:
			type_five_star_all_text.setTextColor(whiteText);
			type_five_star_all.setBackgroundResource(R.drawable.pink);
			break;
		}
		OnClickListener closeDialog = new OnClickListener() {

			@Override
			public void onClick(View v) {
				oneView.setVisibility(View.GONE);
				tenView.setVisibility(View.GONE);
				hundredView.setVisibility(View.GONE);
				thousandView.setVisibility(View.GONE);
				tenThousandView.setVisibility(View.GONE);
				groupView.setVisibility(View.GONE);
				evenOddView1.setVisibility(View.GONE);
				evenOddView2.setVisibility(View.GONE);
				switch ((Integer) v.getTag()) {
				case 1:
					typeNameText.setText("重庆时时彩-大小单双");
					dimentionIntro.setText("所选大小单双与开奖号码后两位一致,且顺序相同,即中4元");
					for (int i = 0; i < CQTenViews.size(); i++) {
						CQTenViews.get(i).setTag(0);
						CQTenTexts.get(i).setTextColor(redText);
						CQTenViews.get(i).setBackgroundResource(
								R.drawable.rect_gray);
						CQViews.get(i).setTag(0);
						CQTexts.get(i).setTextColor(redText);
						CQViews.get(i).setBackgroundResource(
								R.drawable.rect_gray);
					}
					evenOddView1.setVisibility(View.VISIBLE);
					evenOddView2.setVisibility(View.VISIBLE);
					break;
				case 2:
					for (int i = 0; i < sixBalls.size(); i++) {
						sixBalls.get(i).setTag(0);
						sixBalls.get(i)
								.setImageResource(R.drawable.purple_ball);
						sixBallst.get(i).setTextColor(redText);
					}
					selectedSixBalls.clear();
					typeNameText.setText("重庆时时彩-一星直选");
					dimentionIntro.setText("所选号码与开奖号码后一位一致,即中奖金10元");
					groupView.setVisibility(View.VISIBLE);
					break;
				case 3:
					for (int i = 0; i < directTHBalls.size(); i++) {
						directTBalls.get(i).setTag(0);
						directTBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directTBallst.get(i).setTextColor(redText);
						directBalls.get(i).setTag(0);
						directBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directBallst.get(i).setTextColor(redText);
					}
					selectedDirectBalls.clear();
					selectedDirectTBalls.clear();
					typeNameText.setText("重庆时时彩-二星直选");
					dimentionIntro.setText("所选号码与开奖号码后两位一致,且顺序相同,即中奖金100元");
					oneView.setVisibility(View.VISIBLE);
					tenView.setVisibility(View.VISIBLE);
					break;
				case 4:
					for (int i = 0; i < sixBalls.size(); i++) {
						sixBalls.get(i).setTag(0);
						sixBalls.get(i)
								.setImageResource(R.drawable.purple_ball);
						sixBallst.get(i).setTextColor(redText);
					}
					selectedSixBalls.clear();
					typeNameText.setText("重庆时时彩-二星组选");
					dimentionIntro.setText("所选号码与开奖号码后两位一致,顺序不限,即中奖金50元");
					groupView.setVisibility(View.VISIBLE);
					break;
				case 5:

					for (int i = 0; i < directHBalls.size(); i++) {
						directHBalls.get(i).setTag(0);
						directHBallst.get(i).setTextColor(redText);
						directHBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directTBalls.get(i).setTag(0);
						directTBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directTBallst.get(i).setTextColor(redText);
						directBalls.get(i).setTag(0);
						directBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directBallst.get(i).setTextColor(redText);
					}
					selectedDirectBalls.clear();
					selectedDirectTBalls.clear();
					selectedDirectHBalls.clear();
					typeNameText.setText("重庆时时彩-三星直选");
					dimentionIntro.setText("所选号码与开奖号码后三位一致,且顺序相同,即中奖金1000元");
					oneView.setVisibility(View.VISIBLE);
					tenView.setVisibility(View.VISIBLE);
					hundredView.setVisibility(View.VISIBLE);
					break;
				case 6:
					for (int i = 0; i < sixBalls.size(); i++) {
						sixBalls.get(i).setTag(0);
						sixBalls.get(i)
								.setImageResource(R.drawable.purple_ball);
						sixBallst.get(i).setTextColor(redText);
					}
					selectedSixBalls.clear();
					typeNameText.setText("重庆时时彩-三星组三");
					dimentionIntro.setText("所选号码与开奖号码后三位一致,顺序不限,即中奖金320元");
					groupView.setVisibility(View.VISIBLE);
					break;
				case 7:
					for (int i = 0; i < sixBalls.size(); i++) {
						sixBalls.get(i).setTag(0);
						sixBalls.get(i)
								.setImageResource(R.drawable.purple_ball);
						sixBallst.get(i).setTextColor(redText);
					}
					selectedSixBalls.clear();
					typeNameText.setText("重庆时时彩-三星组六");
					dimentionIntro.setText("所选号码与开奖号码后三位一致,顺序不限即中奖金160元");
					groupView.setVisibility(View.VISIBLE);
					break;
				case 8:
					for (int i = 0; i < directHBalls.size(); i++) {
						directHBalls.get(i).setTag(0);
						directHBallst.get(i).setTextColor(redText);
						directHBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directTHBalls.get(i).setTag(0);
						directTHBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directTHBallst.get(i).setTextColor(redText);
						directWBalls.get(i).setTag(0);
						directWBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directWBallst.get(i).setTextColor(redText);
						directTBalls.get(i).setTag(0);
						directTBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directTBallst.get(i).setTextColor(redText);
						directBalls.get(i).setTag(0);
						directBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directBallst.get(i).setTextColor(redText);
					}
					selectedDirectBalls.clear();
					selectedDirectHBalls.clear();
					selectedDirectTBalls.clear();
					selectedDirectTHBalls.clear();
					selectedDirectWBalls.clear();
					typeNameText.setText("重庆时时彩-五星直选");
					dimentionIntro.setText("所选号码与开奖号码一致,且顺序相同,即中奖金100000元");
					oneView.setVisibility(View.VISIBLE);
					tenView.setVisibility(View.VISIBLE);
					hundredView.setVisibility(View.VISIBLE);
					thousandView.setVisibility(View.VISIBLE);
					tenThousandView.setVisibility(View.VISIBLE);
					break;
				case 9:
					for (int i = 0; i < directHBalls.size(); i++) {
						directHBalls.get(i).setTag(0);
						directHBallst.get(i).setTextColor(redText);
						directHBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directTHBalls.get(i).setTag(0);
						directTHBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directTHBallst.get(i).setTextColor(redText);
						directWBalls.get(i).setTag(0);
						directWBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directWBallst.get(i).setTextColor(redText);
						directTBalls.get(i).setTag(0);
						directTBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directTBallst.get(i).setTextColor(redText);
						directBalls.get(i).setTag(0);
						directBalls.get(i).setImageResource(
								R.drawable.purple_ball);
						directBallst.get(i).setTextColor(redText);
					}
					selectedDirectBalls.clear();
					selectedDirectHBalls.clear();
					selectedDirectTBalls.clear();
					selectedDirectTHBalls.clear();
					selectedDirectWBalls.clear();
					typeNameText.setText("重庆时时彩-五星通选");
					dimentionIntro
							.setText("每位选一个或多个号码,按顺序全部命中,命中前三或者后三,命中前二或者后二,即中奖金20000/200/20元");
					oneView.setVisibility(View.VISIBLE);
					tenView.setVisibility(View.VISIBLE);
					hundredView.setVisibility(View.VISIBLE);
					thousandView.setVisibility(View.VISIBLE);
					tenThousandView.setVisibility(View.VISIBLE);
					break;
				}
				// Message msg = new Message();
				// msg.what = Constant.FOOTBALL_TYPE_CHANGED;
				// msg.arg1 = (Integer) v.getTag();
				Constant.CHONGQING_PLAY_TYPE = (Integer) v.getTag();
				if (Constant.CHONGQING_PLAY_TYPE == 9) {
					isTong = true;
				} else {
					isTong = false;
				}
				checkBill();
				// mHandler.sendMessage(msg);
				alertLott.dismiss();
			}
		};
		type_big_small.setOnClickListener(closeDialog);
		type_one_star.setOnClickListener(closeDialog);
		type_two_star.setOnClickListener(closeDialog);
		type_two_star_group.setOnClickListener(closeDialog);
		type_three_star.setOnClickListener(closeDialog);
		type_three_star_group.setOnClickListener(closeDialog);
		type_three_group_six.setOnClickListener(closeDialog);
		type_five_star.setOnClickListener(closeDialog);
		type_five_star_all.setOnClickListener(closeDialog);
	}

	@Override
	public void onClick(View v) {
		if ("cq".equals(v.getTag(three_direct_0.getId()))
				|| "cqt".equals(v.getTag(three_direct_0.getId()))
				|| "dh".equals(v.getTag(three_direct_0.getId()))
				|| "dt".equals(v.getTag(three_direct_0.getId()))
				|| "d".equals(v.getTag(three_direct_0.getId()))
				|| "dth".equals(v.getTag(three_direct_0.getId()))
				|| "dwh".equals(v.getTag(three_direct_0.getId()))) {
			// 直选球
			if ("cqt".equals(v.getTag(three_direct_0.getId()))) {
				if (v.getTag().equals(0)) {
					// 选中直选球
					for (int i = 0; i < CQTenViews.size(); i++) {
						CQTenViews.get(i).setTag(0);
						CQTenTexts.get(i).setTextColor(redText);
						CQTenViews.get(i).setBackgroundResource(
								R.drawable.rect_gray);
					}
					((ImageView) v).setBackgroundResource(R.drawable.rect_red);
					CQTenTexts.get((Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(whiteText);
					v.setTag(1);
					bigSmallTen = (Integer) v.getTag(three_direct_1.getId()) + 1;
					startVibrato();
				} else {
					// 取消直选球
					// for (int i = 0; i < CQTenViews.size(); i++) {
					// CQTenViews.get(i).setTag(0);
					// CQTenTexts.get(i).setTextColor(redText);
					// CQTenViews.get(i).setBackgroundResource(
					// R.drawable.rect_gray);
					// }
					((ImageView) v).setBackgroundResource(R.drawable.rect_gray);
					CQTenTexts.get((Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(redText);
					v.setTag(0);
					bigSmallTen = 0;
				}

			} else if ("cq".equals(v.getTag(three_direct_0.getId()))) {
				if (v.getTag().equals(0)) {
					// 选中直选球
					for (int i = 0; i < CQViews.size(); i++) {
						CQViews.get(i).setTag(0);
						CQTexts.get(i).setTextColor(redText);
						CQViews.get(i).setBackgroundResource(
								R.drawable.rect_gray);
					}
					((ImageView) v).setBackgroundResource(R.drawable.rect_red);
					CQTexts.get((Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(whiteText);
					v.setTag(1);
					bigSmall = (Integer) v.getTag(three_direct_1.getId()) + 1;
					startVibrato();
				} else {
					for (int i = 0; i < CQViews.size(); i++) {
						CQViews.get(i).setTag(0);
						CQTexts.get(i).setTextColor(redText);
						CQViews.get(i).setBackgroundResource(
								R.drawable.rect_gray);
					}
					// 取消直选球
					((ImageView) v).setBackgroundResource(R.drawable.rect_gray);
					CQTexts.get((Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(redText);
					v.setTag(0);
					bigSmall = 0;
				}
			} else if ("dh".equals(v.getTag(three_direct_0.getId()))) {
				if (v.getTag().equals(0)) {
					// 选中直选球 百位
					if (isTong && selectedDirectHBalls.size() == 1) {
						Toast.makeText(ChongQingLottory.this, "五星通选暂不支持复试",
								Toast.LENGTH_SHORT).show();
						return;
					}
					((ImageView) v).setImageResource(R.drawable.black_ball);
					directHBallst.get(
							(Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(whiteText);
					v.setTag(1);
					selectedDirectHBalls.add((ImageView) v);
					directHBallst.get(
							(Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(whiteText);
					startVibrato();
				} else {
					// 取消直选球
					((ImageView) v).setImageResource(R.drawable.purple_ball);
					directHBallst.get(
							(Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(redText);
					v.setTag(0);
					selectedDirectHBalls.remove(v);
				}
			} else if ("dth".equals(v.getTag(three_direct_0.getId()))) {
				if (v.getTag().equals(0)) {
					// 选中直选球 千位
					if (isTong && selectedDirectTHBalls.size() == 1) {
						Toast.makeText(ChongQingLottory.this, "五星通选暂不支持复试",
								Toast.LENGTH_SHORT).show();
						return;
					}
					((ImageView) v).setImageResource(R.drawable.black_ball);
					directTHBallst.get(
							(Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(whiteText);
					v.setTag(1);
					selectedDirectTHBalls.add((ImageView) v);
					directTHBallst.get(
							(Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(whiteText);
					startVibrato();
				} else {
					// 取消直选球
					((ImageView) v).setImageResource(R.drawable.purple_ball);
					directTHBallst.get(
							(Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(redText);
					v.setTag(0);
					selectedDirectTHBalls.remove(v);
				}
			} else if ("dwh".equals(v.getTag(three_direct_0.getId()))) {
				if (v.getTag().equals(0)) {
					// 选中直选球 万位
					if (isTong && selectedDirectWBalls.size() == 1) {
						Toast.makeText(ChongQingLottory.this, "五星通选暂不支持复试",
								Toast.LENGTH_SHORT).show();
						return;
					}
					((ImageView) v).setImageResource(R.drawable.black_ball);
					directWBallst.get(
							(Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(whiteText);
					v.setTag(1);
					selectedDirectWBalls.add((ImageView) v);
					directWBallst.get(
							(Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(whiteText);
					startVibrato();
				} else {
					// 取消直选球
					((ImageView) v).setImageResource(R.drawable.purple_ball);
					directWBallst.get(
							(Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(redText);
					v.setTag(0);
					selectedDirectWBalls.remove(v);
				}
			} else if ("dt".equals(v.getTag(three_direct_0.getId()))) {

				if (v.getTag().equals(0)) {
					// 选中直选球 十位
					if (isTong && selectedDirectTBalls.size() == 1) {
						Toast.makeText(ChongQingLottory.this, "五星通选暂不支持复试",
								Toast.LENGTH_SHORT).show();
						return;
					}
					((ImageView) v).setImageResource(R.drawable.black_ball);
					v.setTag(1);
					directTBallst.get(
							(Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(whiteText);
					selectedDirectTBalls.add((ImageView) v);
					startVibrato();
				} else {
					// 取消直选球
					((ImageView) v).setImageResource(R.drawable.purple_ball);
					directTBallst.get(
							(Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(redText);
					v.setTag(0);
					selectedDirectTBalls.remove(v);
				}

			} else if ("d".equals(v.getTag(three_direct_0.getId()))) {

				if (v.getTag().equals(0)) {
					// 选中直选球 个位
					if (isTong && selectedDirectBalls.size() == 1) {
						Toast.makeText(ChongQingLottory.this, "五星通选暂不支持复试",
								Toast.LENGTH_SHORT).show();
						return;
					}
					((ImageView) v).setImageResource(R.drawable.black_ball);
					directBallst
							.get((Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(whiteText);
					v.setTag(1);
					selectedDirectBalls.add((ImageView) v);
					startVibrato();
				} else {
					// 取消直选球
					((ImageView) v).setImageResource(R.drawable.purple_ball);
					v.setTag(0);
					directBallst
							.get((Integer) v.getTag(three_direct_1.getId()))
							.setTextColor(redText);
					selectedDirectBalls.remove(v);
				}

			}
		} else if ("s".equals(v.getTag(three_direct_0.getId()))) {
			// 排列六
			if (v.getTag().equals(0)) {
				// 选中直选球
				((ImageView) v).setImageResource(R.drawable.black_ball);
				sixBallst.get((Integer) v.getTag(three_direct_1.getId()))
						.setTextColor(whiteText);
				v.setTag(1);
				selectedSixBalls.add((ImageView) v);
				startVibrato();
			} else {
				// 取消直选球
				((ImageView) v).setImageResource(R.drawable.purple_ball);
				sixBallst.get((Integer) v.getTag(three_direct_1.getId()))
						.setTextColor(redText);
				v.setTag(0);
				selectedSixBalls.remove(v);
			}
		}
		checkBill();
		switch (v.getId()) {
		case R.id.pastView:
			Toast.makeText(this, "暂无时时彩近期开奖", Toast.LENGTH_SHORT).show();
			break;
		case R.id.clearView:
			clearBet();
			break;
		case R.id.frontView:
			Log.e("pkx", "frontView click");
			break;
		case R.id.handleBet:
			if (checkBill() == 0) {
				Toast.makeText(this, "至少选择一注", Toast.LENGTH_SHORT).show();
				return;
			}
			chongqingLott = new ChongQingLott();
			chongqingLott.setPlayType(Constant.CHONGQING_PLAY_TYPE);
			switch (Constant.CHONGQING_PLAY_TYPE) {
			case 1:
				Log.e("pkx", "大小单双 十位：" + bigSmallTen + " 个位" + bigSmall + " ");
				ArrayList<Integer> bigsmalss = new ArrayList<Integer>();
				bigsmalss.add(bigSmallTen);
				bigsmalss.add(bigSmall);
				chongqingLott.setRedBalls(bigsmalss);
				break;
			case 2:
				ArrayList<Integer> sixs = new ArrayList<Integer>();
				for (ImageView iv : selectedSixBalls) {
					sixs.add((Integer) iv.getTag(three_direct_1.getId()));
				}
				chongqingLott.setRedBalls(RandomBallsUtils.sort(sixs));
				break;
			case 3:
				ArrayList<Integer> ge = new ArrayList<Integer>();
				ArrayList<Integer> shi = new ArrayList<Integer>();
				for (ImageView gv : selectedDirectBalls) {
					ge.add((Integer) gv.getTag(three_direct_1.getId()));
				}
				for (ImageView gv : selectedDirectTBalls) {
					shi.add((Integer) gv.getTag(three_direct_1.getId()));
				}
				chongqingLott.setRedBalls(RandomBallsUtils.sort(ge));
				chongqingLott.setTenRedBalls(RandomBallsUtils.sort(shi));
				break;
			case 4:
				ArrayList<Integer> g2 = new ArrayList<Integer>();
				for (ImageView gv : selectedSixBalls) {
					g2.add((Integer) gv.getTag(three_direct_1.getId()));
				}
				chongqingLott.setRedBalls(RandomBallsUtils.sort(g2));
				break;
			case 5:
				ArrayList<Integer> ge3 = new ArrayList<Integer>();
				ArrayList<Integer> shi3 = new ArrayList<Integer>();
				ArrayList<Integer> bai3 = new ArrayList<Integer>();
				for (ImageView gv : selectedDirectBalls) {
					ge3.add((Integer) gv.getTag(three_direct_1.getId()));
				}
				for (ImageView gv : selectedDirectTBalls) {
					shi3.add((Integer) gv.getTag(three_direct_1.getId()));
				}
				for (ImageView gv : selectedDirectHBalls) {
					bai3.add((Integer) gv.getTag(three_direct_1.getId()));
				}
				chongqingLott.setRedBalls(RandomBallsUtils.sort(ge3));
				chongqingLott.setTenRedBalls(RandomBallsUtils.sort(shi3));
				chongqingLott.setHunRedBalls(RandomBallsUtils.sort(bai3));
				break;
			case 6:
				ArrayList<Integer> g3 = new ArrayList<Integer>();
				for (ImageView gv : selectedSixBalls) {
					g3.add((Integer) gv.getTag(three_direct_1.getId()));
				}
				chongqingLott.setRedBalls(RandomBallsUtils.sort(g3));
				break;
			case 7:
				ArrayList<Integer> g6 = new ArrayList<Integer>();
				for (ImageView gv : selectedSixBalls) {
					g6.add((Integer) gv.getTag(three_direct_1.getId()));
				}
				chongqingLott.setRedBalls(RandomBallsUtils.sort(g6));
				break;
			case 8:
			case 9:
				ArrayList<Integer> allge = new ArrayList<Integer>();
				ArrayList<Integer> allshi = new ArrayList<Integer>();
				ArrayList<Integer> allbai = new ArrayList<Integer>();
				ArrayList<Integer> allqian = new ArrayList<Integer>();
				ArrayList<Integer> allwan = new ArrayList<Integer>();
				for (ImageView gv : selectedDirectBalls) {
					allge.add((Integer) gv.getTag(three_direct_1.getId()));
				}
				for (ImageView gv : selectedDirectTBalls) {
					allshi.add((Integer) gv.getTag(three_direct_1.getId()));
				}
				for (ImageView gv : selectedDirectHBalls) {
					allbai.add((Integer) gv.getTag(three_direct_1.getId()));
				}
				for (ImageView gv : selectedDirectTHBalls) {
					allqian.add((Integer) gv.getTag(three_direct_1.getId()));
				}
				for (ImageView gv : selectedDirectWBalls) {
					allwan.add((Integer) gv.getTag(three_direct_1.getId()));
				}
				chongqingLott.setRedBalls(RandomBallsUtils.sort(allge));
				chongqingLott.setTenRedBalls(RandomBallsUtils.sort(allshi));
				chongqingLott.setHunRedBalls(RandomBallsUtils.sort(allbai));
				chongqingLott.setThouRedBalls(RandomBallsUtils.sort(allqian));
				chongqingLott.setwRedBalls(RandomBallsUtils.sort(allwan));
				break;
			// ArrayList<Integer> allge1 = new ArrayList<Integer>();
			// ArrayList<Integer> allshi1 = new ArrayList<Integer>();
			// ArrayList<Integer> allbai1 = new ArrayList<Integer>();
			// ArrayList<Integer> allqian1 = new ArrayList<Integer>();
			// ArrayList<Integer> allwan1 = new ArrayList<Integer>();
			// for (ImageView gv : selectedDirectBalls) {
			// allge1.add((Integer) gv.getTag(three_direct_1.getId()));
			// }
			// for (ImageView gv : selectedDirectTBalls) {
			// allshi1.add((Integer) gv.getTag(three_direct_1.getId()));
			// }
			// for (ImageView gv : selectedDirectHBalls) {
			// allbai1.add((Integer) gv.getTag(three_direct_1.getId()));
			// }
			// for (ImageView gv : selectedDirectTHBalls) {
			// allqian1.add((Integer) gv.getTag(three_direct_1.getId()));
			// }
			// for (ImageView gv : selectedDirectWBalls) {
			// allwan1.add((Integer) gv.getTag(three_direct_1.getId()));
			// }
			// chongqingLott.setRedBalls(allge1);
			// chongqingLott.setTenRedBalls(allshi1);
			// chongqingLott.setHunRedBalls(allbai1);
			// chongqingLott.setThouRedBalls(allqian1);
			// chongqingLott.setwRedBalls(allwan1);
			// break;

			}
			// if (currentPage == 0) {
			// if (selectedRedBalls.size() < 7) {
			// Toast.makeText(this, "至少选择7个球", Toast.LENGTH_SHORT).show();
			// return;
			// }
			// } else {
			// if (selectedDredBallList.size() == 0
			// || selectedTredBallList.size() == 0
			// || (selectedDredBallList.size() + selectedTredBallList
			// .size()) < 8) {
			// Toast.makeText(this, "最多选择六个胆球，至少选择1个拖球，总球数不得小于8",
			// Toast.LENGTH_SHORT).show();
			// return;
			// }
			// }
			//
			Bundle betBundle = new Bundle();
			// if (currentPage == 0) {
			// ArrayList<Integer> redBalls = new ArrayList<Integer>();
			// for (ImageView sv : selectedRedBalls) {
			// redBalls.add((Integer) sv.getTag(red2.getId()));
			// }
			// SevenLotteryBet cbet = new SevenLotteryBet();
			// cbet.setType(currentPage);
			// cbet.setBalls(redBalls);
			betBundle.putSerializable("sbet", chongqingLott);
			//
			// } else {
			// ArrayList<Integer> dredBalls = new ArrayList<Integer>();
			// for (ImageView sv : selectedDredBallList) {
			// dredBalls.add((Integer) sv.getTag(red2.getId()));
			// }
			// ArrayList<Integer> tredBalls = new ArrayList<Integer>();
			// for (ImageView sv : selectedTredBallList) {
			// tredBalls.add((Integer) sv.getTag(red2.getId()));
			// }
			// SevenLotteryBet bet = new SevenLotteryBet();
			// bet.setType(currentPage);
			// bet.setdBalls(dredBalls);
			// bet.settBalls(tredBalls);
			// betBundle.putSerializable("sbet", bet);
			//
			// }
			if (Constant.isHandlySelect) {
				// 手动添加新投注
				if (Constant.isHandlyEdit) {
					// 修改投注
					Constant.isHandlyEdit = false;
					Intent resultIntent = new Intent();
					resultIntent.putExtras(betBundle);
					setResult(RESULT_OK, resultIntent);
				} else {
					Intent resultIntent = new Intent();
					resultIntent.putExtras(betBundle);
					setResult(RESULT_OK, resultIntent);
				}
				Constant.isHandlySelect = false;
				finish();
			} else {
				// 首次选号
				Intent toCheckPage = new Intent(this, ChongQingLottCheck.class);
				toCheckPage.putExtras(betBundle);
				startActivity(toCheckPage);
				finish();
			}

			break;
		}
	}

	private void randomBet() {
		Random r = new Random();
		switch (Constant.CHONGQING_PLAY_TYPE) {
		case 1:
			bigSmall = 0;
			bigSmallTen = 0;
			for (int i = 0; i < CQViews.size(); i++) {
				CQViews.get(i).setTag(0);
				CQViews.get(i).setBackgroundResource(R.drawable.rect_gray);
				CQTenViews.get(i).setTag(0);
				CQTenViews.get(i).setBackgroundResource(R.drawable.rect_gray);
				CQTexts.get(i).setTextColor(redText);
				CQTenTexts.get(i).setTextColor(redText);
			}
			bigSmall = r.nextInt(4) + 1;
			bigSmallTen = r.nextInt(4) + 1;
			CQViews.get(bigSmall - 1).setTag(1);
			CQViews.get(bigSmall - 1)
					.setBackgroundResource(R.drawable.rect_red);
			CQTenViews.get(bigSmallTen - 1).setTag(1);
			CQTenViews.get(bigSmallTen - 1).setBackgroundResource(
					R.drawable.rect_red);
			CQTexts.get(bigSmall - 1).setTextColor(whiteText);
			CQTenTexts.get(bigSmallTen - 1).setTextColor(whiteText);
			break;
		case 2:
		case 4:
		case 6:
		case 7:
			if (selectedSixBalls != null)
				selectedSixBalls.clear();
			for (int i = 0; i < sixBallst.size(); i++) {
				sixBallst.get(i).setTextColor(redText);
				sixBalls.get(i).setImageResource(R.drawable.purple_ball);
				sixBalls.get(i).setTag(0);
			}
			switch (Constant.CHONGQING_PLAY_TYPE) {
			case 2:
				int ran = r.nextInt(10);
				selectedSixBalls.add(sixBalls.get(ran));
				sixBalls.get(ran).setTag(1);
				sixBalls.get(ran).setImageResource(R.drawable.black_ball);
				sixBallst.get(ran).setTextColor(whiteText);
				break;
			case 4:
			case 6:
				ArrayList<Integer> twoBalls = RandomBallsUtils.getRandomBalls(
						2, 10);
				selectedSixBalls.add(sixBalls.get(twoBalls.get(0)));
				selectedSixBalls.add(sixBalls.get(twoBalls.get(1)));
				sixBalls.get(twoBalls.get(0)).setTag(1);
				sixBalls.get(twoBalls.get(1)).setTag(1);
				sixBalls.get(twoBalls.get(0)).setImageResource(
						R.drawable.black_ball);
				sixBalls.get(twoBalls.get(1)).setImageResource(
						R.drawable.black_ball);
				sixBallst.get(twoBalls.get(0)).setTextColor(whiteText);
				sixBallst.get(twoBalls.get(1)).setTextColor(whiteText);
				break;
			case 7:
				ArrayList<Integer> twoBalls3 = RandomBallsUtils.getRandomBalls(
						3, 10);
				selectedSixBalls.add(sixBalls.get(twoBalls3.get(0)));
				selectedSixBalls.add(sixBalls.get(twoBalls3.get(1)));
				selectedSixBalls.add(sixBalls.get(twoBalls3.get(2)));
				sixBalls.get(twoBalls3.get(0)).setTag(1);
				sixBalls.get(twoBalls3.get(1)).setTag(1);
				sixBalls.get(twoBalls3.get(2)).setTag(1);
				sixBalls.get(twoBalls3.get(0)).setImageResource(
						R.drawable.black_ball);
				sixBalls.get(twoBalls3.get(1)).setImageResource(
						R.drawable.black_ball);
				sixBalls.get(twoBalls3.get(2)).setImageResource(
						R.drawable.black_ball);
				sixBallst.get(twoBalls3.get(0)).setTextColor(whiteText);
				sixBallst.get(twoBalls3.get(1)).setTextColor(whiteText);
				sixBallst.get(twoBalls3.get(2)).setTextColor(whiteText);
				break;

			}
			break;
		case 3:
			if (selectedDirectTBalls != null)
				selectedDirectTBalls.clear();
			for (int i = 0; i < directTBalls.size(); i++) {
				directTBallst.get(i).setTextColor(redText);
				directTBalls.get(i).setImageResource(R.drawable.purple_ball);
				directTBalls.get(i).setTag(0);
			}
			if (selectedDirectBalls != null)
				selectedDirectBalls.clear();
			for (int i = 0; i < directBalls.size(); i++) {
				directBallst.get(i).setTextColor(redText);
				directBalls.get(i).setImageResource(R.drawable.purple_ball);
				directBalls.get(i).setTag(0);
			}
			int ball1 = r.nextInt(10);
			int ball2 = r.nextInt(10);
			selectedDirectTBalls.add(directTBalls.get(ball1));
			directTBallst.get(ball1).setTextColor(whiteText);
			directTBalls.get(ball1).setImageResource(R.drawable.black_ball);
			directTBalls.get(ball1).setTag(1);
			selectedDirectBalls.add(directBalls.get(ball2));
			directBallst.get(ball2).setTextColor(whiteText);
			directBalls.get(ball2).setImageResource(R.drawable.black_ball);
			directBalls.get(ball2).setTag(1);
			break;
		case 5:
			if (selectedDirectTBalls != null)
				selectedDirectTBalls.clear();
			for (int i = 0; i < directTBalls.size(); i++) {
				directTBallst.get(i).setTextColor(redText);
				directTBalls.get(i).setImageResource(R.drawable.purple_ball);
				directTBalls.get(i).setTag(0);
			}
			if (selectedDirectBalls != null)
				selectedDirectBalls.clear();
			for (int i = 0; i < directBalls.size(); i++) {
				directBallst.get(i).setTextColor(redText);
				directBalls.get(i).setImageResource(R.drawable.purple_ball);
				directBalls.get(i).setTag(0);
			}
			if (selectedDirectHBalls != null)
				selectedDirectHBalls.clear();
			for (int i = 0; i < directHBalls.size(); i++) {
				directHBallst.get(i).setTextColor(redText);
				directHBalls.get(i).setImageResource(R.drawable.purple_ball);
				directHBalls.get(i).setTag(0);
			}
			int ball13 = r.nextInt(10);
			int ball23 = r.nextInt(10);
			int ball33 = r.nextInt(10);
			selectedDirectTBalls.add(directTBalls.get(ball13));
			directTBallst.get(ball13).setTextColor(whiteText);
			directTBalls.get(ball13).setImageResource(R.drawable.black_ball);
			directTBalls.get(ball13).setTag(1);
			selectedDirectBalls.add(directBalls.get(ball23));
			directBallst.get(ball23).setTextColor(whiteText);
			directBalls.get(ball23).setImageResource(R.drawable.black_ball);
			directBalls.get(ball23).setTag(1);
			selectedDirectHBalls.add(directHBalls.get(ball33));
			directHBallst.get(ball33).setTextColor(whiteText);
			directHBalls.get(ball33).setImageResource(R.drawable.black_ball);
			directHBalls.get(ball33).setTag(1);
			break;
		case 8:
		case 9:
			if (selectedDirectTBalls != null)
				selectedDirectTBalls.clear();
			for (int i = 0; i < directTBalls.size(); i++) {
				directTBallst.get(i).setTextColor(redText);
				directTBalls.get(i).setImageResource(R.drawable.purple_ball);
				directTBalls.get(i).setTag(0);
			}

			if (selectedDirectBalls != null)
				selectedDirectBalls.clear();
			for (int i = 0; i < directBalls.size(); i++) {
				directBallst.get(i).setTextColor(redText);
				directBalls.get(i).setImageResource(R.drawable.purple_ball);
				directBalls.get(i).setTag(0);
			}

			if (selectedDirectHBalls != null)
				selectedDirectHBalls.clear();
			for (int i = 0; i < directHBalls.size(); i++) {
				directHBallst.get(i).setTextColor(redText);
				directHBalls.get(i).setImageResource(R.drawable.purple_ball);
				directHBalls.get(i).setTag(0);
			}
			if (selectedDirectTHBalls != null)
				selectedDirectTHBalls.clear();
			for (int i = 0; i < directHBalls.size(); i++) {
				directTHBallst.get(i).setTextColor(redText);
				directTHBalls.get(i).setImageResource(R.drawable.purple_ball);
				directTHBalls.get(i).setTag(0);
			}
			if (selectedDirectWBalls != null)
				selectedDirectWBalls.clear();
			for (int i = 0; i < directHBalls.size(); i++) {
				directWBallst.get(i).setTextColor(redText);
				directWBalls.get(i).setImageResource(R.drawable.purple_ball);
				directWBalls.get(i).setTag(0);
			}
			int ball135 = r.nextInt(10);
			int ball235 = r.nextInt(10);
			int ball335 = r.nextInt(10);
			int ball435 = r.nextInt(10);
			int ball535 = r.nextInt(10);
			selectedDirectTBalls.add(directTBalls.get(ball135));
			directTBallst.get(ball135).setTextColor(whiteText);
			directTBalls.get(ball135).setImageResource(R.drawable.black_ball);
			directTBalls.get(ball135).setTag(1);
			selectedDirectBalls.add(directBalls.get(ball235));
			directBallst.get(ball235).setTextColor(whiteText);
			directBalls.get(ball235).setImageResource(R.drawable.black_ball);
			directBalls.get(ball235).setTag(1);
			selectedDirectHBalls.add(directHBalls.get(ball335));
			directHBallst.get(ball335).setTextColor(whiteText);
			directHBalls.get(ball335).setImageResource(R.drawable.black_ball);
			directHBalls.get(ball335).setTag(1);
			selectedDirectWBalls.add(directWBalls.get(ball535));
			directWBallst.get(ball535).setTextColor(whiteText);
			directWBalls.get(ball535).setImageResource(R.drawable.black_ball);
			directWBalls.get(ball535).setTag(1);
			selectedDirectTHBalls.add(directTHBalls.get(ball435));
			directTHBallst.get(ball435).setTextColor(whiteText);
			directTHBalls.get(ball435).setImageResource(R.drawable.black_ball);
			directTHBalls.get(ball435).setTag(1);
			break;
		}
		checkBill();

	}

	private void clearBet() {

		switch (Constant.CHONGQING_PLAY_TYPE) {
		case 1:
			bigSmall = 0;
			bigSmallTen = 0;
			for (int i = 0; i < CQViews.size(); i++) {
				CQViews.get(i).setTag(0);
				CQViews.get(i).setBackgroundResource(R.drawable.rect_gray);
				CQTenViews.get(i).setTag(0);
				CQTenViews.get(i).setBackgroundResource(R.drawable.rect_gray);
				CQTexts.get(i).setTextColor(redText);
				CQTenTexts.get(i).setTextColor(redText);
			}
			break;
		case 2:
		case 4:
		case 6:
		case 7:
			if (selectedSixBalls != null)
				selectedSixBalls.clear();
			for (int i = 0; i < sixBallst.size(); i++) {
				sixBallst.get(i).setTextColor(redText);
				sixBalls.get(i).setImageResource(R.drawable.purple_ball);
				sixBalls.get(i).setTag(0);
			}
			break;
		case 3:
			if (selectedDirectTBalls != null)
				selectedDirectTBalls.clear();
			for (int i = 0; i < directTBalls.size(); i++) {
				directTBallst.get(i).setTextColor(redText);
				directTBalls.get(i).setImageResource(R.drawable.purple_ball);
				directTBalls.get(i).setTag(0);
			}
			if (selectedDirectBalls != null)
				selectedDirectBalls.clear();
			for (int i = 0; i < directBalls.size(); i++) {
				directBallst.get(i).setTextColor(redText);
				directBalls.get(i).setImageResource(R.drawable.purple_ball);
				directBalls.get(i).setTag(0);
			}
			break;
		case 5:
			if (selectedDirectTBalls != null)
				selectedDirectTBalls.clear();
			for (int i = 0; i < directTBalls.size(); i++) {
				directTBallst.get(i).setTextColor(redText);
				directTBalls.get(i).setImageResource(R.drawable.purple_ball);
				directTBalls.get(i).setTag(0);
			}
			if (selectedDirectBalls != null)
				selectedDirectBalls.clear();
			for (int i = 0; i < directBalls.size(); i++) {
				directBallst.get(i).setTextColor(redText);
				directBalls.get(i).setImageResource(R.drawable.purple_ball);
				directBalls.get(i).setTag(0);
			}
			if (selectedDirectHBalls != null)
				selectedDirectHBalls.clear();
			for (int i = 0; i < directHBalls.size(); i++) {
				directHBallst.get(i).setTextColor(redText);
				directHBalls.get(i).setImageResource(R.drawable.purple_ball);
				directHBalls.get(i).setTag(0);
			}
			break;
		case 8:
		case 9:
			if (selectedDirectTBalls != null)
				selectedDirectTBalls.clear();
			for (int i = 0; i < directTBalls.size(); i++) {
				directTBallst.get(i).setTextColor(redText);
				directTBalls.get(i).setImageResource(R.drawable.purple_ball);
				directTBalls.get(i).setTag(0);
			}

			if (selectedDirectBalls != null)
				selectedDirectBalls.clear();
			for (int i = 0; i < directBalls.size(); i++) {
				directBallst.get(i).setTextColor(redText);
				directBalls.get(i).setImageResource(R.drawable.purple_ball);
				directBalls.get(i).setTag(0);
			}

			if (selectedDirectHBalls != null)
				selectedDirectHBalls.clear();
			for (int i = 0; i < directHBalls.size(); i++) {
				directHBallst.get(i).setTextColor(redText);
				directHBalls.get(i).setImageResource(R.drawable.purple_ball);
				directHBalls.get(i).setTag(0);
			}
			if (selectedDirectTHBalls != null)
				selectedDirectTHBalls.clear();
			for (int i = 0; i < directHBalls.size(); i++) {
				directTHBallst.get(i).setTextColor(redText);
				directTHBalls.get(i).setImageResource(R.drawable.purple_ball);
				directTHBalls.get(i).setTag(0);
			}
			if (selectedDirectWBalls != null)
				selectedDirectWBalls.clear();
			for (int i = 0; i < directHBalls.size(); i++) {
				directWBallst.get(i).setTextColor(redText);
				directWBalls.get(i).setImageResource(R.drawable.purple_ball);
				directWBalls.get(i).setTag(0);
			}
			break;
		}
		checkBill();

	}

	private int checkBill() {
		int result = 0;
		switch (Constant.CHONGQING_PLAY_TYPE) {
		case 1:
			Log.e("pkx", "个位：" + bigSmall + " 十位：" + bigSmallTen);
			if (bigSmall != 0 && bigSmallTen != 0) {
				billText.setText("共1注2元");
				result = 2;
			} else {
				billText.setText("0");
				result = 0;
			}
			break;
		case 2:
			if (selectedSixBalls.size() == 0) {
				billText.setText("0");
				result = 0;
			} else {
				result = selectedSixBalls.size() * 2;
				billText.setText("共" + String.valueOf(result / 2) + "注"
						+ String.valueOf(result) + "元");
			}
			break;
		case 3:
			if (selectedDirectBalls.size() == 0
					|| selectedDirectTBalls.size() == 0) {
				billText.setText("0");
				result = 0;
			} else {
				result = selectedDirectTBalls.size()
						* selectedDirectBalls.size() * 2;
				billText.setText("共" + String.valueOf(result / 2) + "注"
						+ String.valueOf(result) + "元");
			}
			break;
		case 4:
			if (selectedSixBalls.size() < 2) {
				billText.setText("0");
				result = 0;
			} else {
				result = selectedSixBalls.size()
						* (selectedSixBalls.size() - 1);
				billText.setText("共" + String.valueOf(result / 2) + "注"
						+ String.valueOf(result) + "元");
			}
			break;
		case 5:
			if (selectedDirectBalls.size() == 0
					|| selectedDirectTBalls.size() == 0
					|| selectedDirectHBalls.size() == 0) {
				billText.setText("0");
				result = 0;
			} else {
				result = selectedDirectHBalls.size()
						* selectedDirectTBalls.size()
						* selectedDirectBalls.size() * 2;
				billText.setText("共" + String.valueOf(result / 2) + "注"
						+ String.valueOf(result) + "元");
			}
			break;
		case 6:
			switch (selectedSixBalls.size()) {
			case 0:
				billText.setText("0");
				result = 0;
				break;
			case 1:
				result = 0;
				billText.setText("0");
				break;
			case 2:
				result = 4;
				billText.setText("共" + String.valueOf(result / 2) + "注"
						+ String.valueOf(result) + "元");
				break;
			case 3:
				result = 12;
				billText.setText("共" + String.valueOf(result / 2) + "注"
						+ String.valueOf(result) + "元");
				break;
			case 4:
				result = 24;
				billText.setText("共" + String.valueOf(result / 2) + "注"
						+ String.valueOf(result) + "元");
				break;
			case 5:
				result = 40;
				billText.setText("共" + String.valueOf(result / 2) + "注"
						+ String.valueOf(result) + "元");
				break;
			case 6:
				result = 60;
				billText.setText("共" + String.valueOf(result / 2) + "注"
						+ String.valueOf(result) + "元");
				break;
			case 7:
				result = 84;
				billText.setText("共" + String.valueOf(result / 2) + "注"
						+ String.valueOf(result) + "元");
				break;
			case 8:
				result = 112;
				billText.setText("共" + String.valueOf(result / 2) + "注"
						+ String.valueOf(result) + "元");
				break;
			case 9:
				result = 144;
				billText.setText("共" + String.valueOf(result / 2) + "注"
						+ String.valueOf(result) + "元");
				break;
			case 10:
				result = 180;
				billText.setText("共" + String.valueOf(result / 2) + "注"
						+ String.valueOf(result) + "元");
				break;
			}
			break;
		case 7:
			if (selectedSixBalls.size() < 3) {
				billText.setText("0");
				result = 0;
			} else {
				result = RandomBallsUtils.getBallBets(3,
						selectedSixBalls.size()) * 2;
				billText.setText(String.valueOf(result) + "元");
			}
			break;
		case 8:
			if (selectedDirectBalls.size() == 0
					|| selectedDirectTBalls.size() == 0
					|| selectedDirectHBalls.size() == 0
					|| selectedDirectTHBalls.size() == 0
					|| selectedDirectWBalls.size() == 0) {
				billText.setText("0");
				result = 0;
			} else {
				result = selectedDirectWBalls.size()
						* selectedDirectTHBalls.size()
						* selectedDirectHBalls.size()
						* selectedDirectTBalls.size()
						* selectedDirectBalls.size() * 2;
				billText.setText(String.valueOf(result) + "元");
			}
			break;
		case 9:

			if (selectedDirectBalls.size() == 0
					|| selectedDirectTBalls.size() == 0
					|| selectedDirectHBalls.size() == 0
					|| selectedDirectTHBalls.size() == 0
					|| selectedDirectWBalls.size() == 0) {
				billText.setText("0");
				result = 0;
			} else {
				result = selectedDirectWBalls.size()
						* selectedDirectTHBalls.size()
						* selectedDirectHBalls.size()
						* selectedDirectTBalls.size()
						* selectedDirectBalls.size() * 2;
				billText.setText(String.valueOf(result) + "元");
			}
			break;
		}
		return result;
	}

	public void startVibrato() { // 定义震动
		if (sutil.getSelectVibrat()) {
			mVibretor.vibrate(new long[] { 0, 100 }, -1);// 第一个｛｝里面是节奏数组，
			// 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
		}
	}

	public void startVibrato1() { // 定义震动
		if (sutil.getShakeVibrat()) {
			mVibretor.vibrate(new long[] { 50, 500, 50, 500 }, -1); // 第一个｛｝里面是节奏数组，
			// 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Constant.TICK_TOCK_FLAG = false;
		if (mShakeListener != null) {
			mShakeListener.stop();
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
				if (msg.arg1 == Constant.NET_ACTION_SECRET_SECURE) {
					// frontView.setVisibility(View.GONE);
					Log.e("pkx", "重庆时时彩彩期信息：-----");
					FastCur fa = Net.gson.fromJson(msg.obj.toString(),
							FastCur.class);
					if (fa.getCurFast().getStart_second() == 0) {
						frontView.setVisibility(View.GONE);
						// prizeText.setText(fa
						// .getCurFast()
						// .getPeroid_name()
						// .substring(
						// fa.getCurFast().getPeroid_name()
						// .length() - 3,
						// fa.getCurFast().getPeroid_name()
						// .length())
						// + "期:和值" + "8");
						if (fa.getCurFast().getPeroid_name() != null)
							period.setText(fa.getCurFast().getPeroid_name());
						Log.e("pkx", "start=0 快三当前期次：当期剩余时间:"
								+ fa.getCurFast().getRest_time() + " name:"
								+ fa.getCurFast().getPeroid_name()
								+ "上期： name:"
								+ fa.getLastFast().getPeroid_name() + " id:"
								+ fa.getLastFast().getPeroidID());
						timerest = fa.getCurFast().getRest_time();
						if (isFirstView) {
							isFirstView = false;
							timeThread.start();
						}
					} else {
						frontView.setVisibility(View.VISIBLE);
						timerest = fa.getCurFast().getStart_second();
						if (isFirstView) {
							isFirstView = false;
							timeThread.start();
						}
						if (fa.getCurFast().getPeroid_name() != null)
							period.setText(fa.getCurFast().getPeroid_name());
						Log.e("pkx", "start != 0快三当前期次：当期剩余时间:"
								+ fa.getCurFast().getRest_time() + " name:"
								+ fa.getCurFast().getPeroid_name() + "剩余时间:"
								+ fa.getCurFast().getStart_second()
								+ " 上期： name:"
								+ fa.getLastFast().getPeroid_name() + " id:"
								+ fa.getLastFast().getPeroidID());
					}
				}
			} else if (msg.what == Constant.SHAKE_MESSAGE) {
				Log.e("pkx", "重庆时时彩shake");
				randomBet();
//				Toast.makeText(ChongQingLottory.this, "摇一摇", Toast.LENGTH_SHORT)
//						.show();
			}
			if (msg.what == Constant.TICK_TOCK) {
				if (frontView.getVisibility() == View.GONE) {
					Log.e("pkx", "TICK_TOCK" + timerest);
					int sec = timerest;
					int min = sec / 60;
					int seco = sec % 60;
					minute.setText("" + min);
					if (seco < 10) {
						sec1.setText("" + 0);
						sec2.setText("" + seco);
					} else {
						sec1.setText("" + seco / 10);
						sec2.setText("" + seco % 10);
					}
					if (timerest == 0) {
						frontView.setVisibility(View.VISIBLE);
						timerest = 120;

						RequestParams params = new RequestParams();
						params.put("type", "6");
						Net.post(true, ChongQingLottory.this, Constant.PAY_URL
								+ "/ajax/index.php?act=load_lottery_info",
								params, mHandler,
								Constant.NET_ACTION_SECRET_SECURE);
					}
				} else {
					Log.e("pkx", "TICK_TOCK" + timerest);
					int sec = timerest;
					int min = sec / 60;
					int seco = sec % 60;
					minute1.setText("" + min);
					if (seco < 10) {
						sec11.setText("" + 0);
						sec21.setText("" + seco);
					} else {
						sec11.setText("" + seco / 10);
						sec21.setText("" + seco % 10);
					}
					if (timerest == 0) {
						frontView.setVisibility(View.GONE);
						timerest = 480;
						RequestParams params = new RequestParams();
						params.put("type", "6");
						Net.post(true, ChongQingLottory.this, Constant.PAY_URL
								+ "/ajax/index.php?act=load_lottery_info",
								params, mHandler,
								Constant.NET_ACTION_SECRET_SECURE);
					}
				}

			}
		}
	}

	public class MyThread implements Runnable {

		@Override
		public void run() {

			while (timerest > 0) {
				if (!Constant.TICK_TOCK_FLAG) {
					timerest = -1;
					break;
				}
				final Message ticktockmsg = new Message();
				ticktockmsg.what = Constant.TICK_TOCK;
				Log.e("pkx", "while" + timerest);
				mHandler.sendMessage(ticktockmsg);
				timerest--;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					Log.e("pkx", "InterruptedException");
				}

			}
		}

	}

}
