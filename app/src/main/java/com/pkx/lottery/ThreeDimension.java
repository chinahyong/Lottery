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
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pkx.lottery.ShakeListener.OnShakeListener;
import com.pkx.lottery.bean.DimensionBet;
import com.pkx.lottery.utils.RandomBallsUtils;
import com.pkx.lottery.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.Random;

public class ThreeDimension extends Activity implements OnClickListener {
	private ViewPager paper3D;
	private MyHandler mHandler;
	private View view1, view2, view3, handleBet, clearView;
	LayoutInflater inflater;
	private ShakeListener mShakeListener = null;
	private int currentPage = 0;
	private Vibrator mVibretor;
	private SharePreferenceUtil sutil;
	private int redText = Color.parseColor("#CD0000");
	private int whiteText = Color.parseColor("#ffffff");
	private TextView ballsText, billText, directText, group3Text, group6Text,
			dimentionIntro;
	private ImageView dimention_d, dimention_t, dimention_s;
	private Intent mIntent;
	private DimensionBet editBet;
	private ImageView three_direct_h0, three_direct_h1, three_direct_h2,
			three_direct_h3, three_direct_h4, three_direct_h5, three_direct_h6,
			three_direct_h7, three_direct_h8, three_direct_h9, three_direct_t0,
			three_direct_t1, three_direct_t2, three_direct_t3, three_direct_t4,
			three_direct_t5, three_direct_t6, three_direct_t7, three_direct_t8,
			three_direct_t9, three_direct_0, three_direct_1, three_direct_2,
			three_direct_3, three_direct_4, three_direct_5, three_direct_6,
			three_direct_7, three_direct_8, three_direct_9, three_six_0,
			three_six_1, three_six_2, three_six_3, three_six_4, three_six_5,
			three_six_6, three_six_7, three_six_8, three_six_9, three_three_0,
			three_three_1, three_three_2, three_three_3, three_three_4,
			three_three_5, three_three_6, three_three_7, three_three_8,
			three_three_9;
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
			three_six_7t, three_six_8t, three_six_9t, three_three_0t,
			three_three_1t, three_three_2t, three_three_3t, three_three_4t,
			three_three_5t, three_three_6t, three_three_7t, three_three_8t,
			three_three_9t;
	private ArrayList<ImageView> directBalls, directHBalls, directTBalls,
			sixBalls, threeBalls, selectedDirectBalls, selectedDirectHBalls,
			selectedDirectTBalls, selectedSixBalls, selectedThreeBalls;
	private ArrayList<TextView> directBallst, directHBallst, directTBallst,
			sixBallst, threeBallst;
	private View passPeroid;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onResume() {
		super.onResume();
		mShakeListener = new ShakeListener(this);
		mShakeListener.setOnShakeListener(new OnShakeListener() {
			@Override
			public void onShake() {
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
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.three_dimension);
		mHandler = new MyHandler();
		mIntent = getIntent();
		editBet = (DimensionBet) mIntent.getSerializableExtra("editBet");
		// 加载布局
		inflater = LayoutInflater.from(this);
		view1 = inflater.inflate(R.layout.tab_three_d_direct, null);
		view2 = inflater.inflate(R.layout.tab_three_d_three, null);
		view3 = inflater.inflate(R.layout.tab_three_d_six, null);
		// 将布局放入集合
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		initViews();
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
		paper3D.setAdapter(adapter);
		if (editBet != null) {
			switch (editBet.getType()) {
			case 0:
				paper3D.setCurrentItem(0);
				for (int g : editBet.getgBalls()) {
					Log.e("editBet.getgBalls()", "-" + g);
					selectedDirectBalls.add(directBalls.get(g));
					directBalls.get(g).setImageResource(R.drawable.black_ball);
					directBalls.get(g).setTag(1);
					directBallst.get(g).setTextColor(whiteText);
				}
				for (int t : editBet.gettBalls()) {
					selectedDirectTBalls.add(directTBalls.get(t));
					directTBalls.get(t).setImageResource(R.drawable.black_ball);
					directTBalls.get(t).setTag(1);
					directTBallst.get(t).setTextColor(whiteText);
				}
				for (int h : editBet.gethBalls()) {
					selectedDirectHBalls.add(directHBalls.get(h));
					directHBalls.get(h).setImageResource(R.drawable.black_ball);
					directHBalls.get(h).setTag(1);
					directHBallst.get(h).setTextColor(whiteText);
				}
				break;
			case 1:
				paper3D.setCurrentItem(1);
				for (int th : editBet.getBalls3()) {
					selectedThreeBalls.add(threeBalls.get(th));
					threeBalls.get(th).setImageResource(R.drawable.black_ball);
					threeBalls.get(th).setTag(1);
					threeBallst.get(th).setTextColor(whiteText);
				}
				break;
			case 2:
				paper3D.setCurrentItem(2);
				for (int six : editBet.getBalls6()) {
					selectedSixBalls.add(sixBalls.get(six));
					sixBalls.get(six).setImageResource(R.drawable.black_ball);
					sixBalls.get(six).setTag(1);
					sixBallst.get(six).setTextColor(whiteText);
				}
				break;
			}
		}
		checkBill();
	}

	private void checkTypeBtn() {
		if (currentPage == 0) {
			dimention_d.setBackgroundResource(R.drawable.yellow_left_pre);
			dimention_t.setBackgroundResource(R.drawable.yellow_mid_nor);
			dimention_s.setBackgroundResource(R.drawable.yellow_right_nor);
		} else if (currentPage == 1) {
			dimention_d.setBackgroundResource(R.drawable.yellow_left_nor);
			dimention_t.setBackgroundResource(R.drawable.yellow_mid_pre);
			dimention_s.setBackgroundResource(R.drawable.yellow_right_nor);
		} else if (currentPage == 2) {
			dimention_d.setBackgroundResource(R.drawable.yellow_left_nor);
			dimention_t.setBackgroundResource(R.drawable.yellow_mid_nor);
			dimention_s.setBackgroundResource(R.drawable.yellow_right_pre);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (selectedDirectBalls.size() == 0
					&& selectedDirectHBalls.size() == 0
					&& selectedDirectTBalls.size() == 0
					&& selectedSixBalls.size() == 0
					&& selectedThreeBalls.size() == 0) {
				finish();
			} else {
				// new AlertDialog.Builder(this)
				// .setTitle("退出清除投注信息，是否退出？")
				// .setNegativeButton("取消",
				// new DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface d,
				// int which) {
				// d.dismiss();
				// }
				// })
				// .setPositiveButton("确定",
				// new DialogInterface.OnClickListener() {
				// public void onClick(DialogInterface d,
				// int which) {
				// d.dismiss();
				// finish();
				// }
				// }).show();
				alert();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	private void initViews() {
		sutil = new SharePreferenceUtil(this);
		passPeroid = findViewById(R.id.passPeroid);
		passPeroid.setOnClickListener(this);
		mVibretor = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		dimention_d = (ImageView) findViewById(R.id.dimention_d);
		dimention_d.setOnClickListener(this);
		dimention_t = (ImageView) findViewById(R.id.dimention_t);
		dimention_t.setOnClickListener(this);
		dimention_s = (ImageView) findViewById(R.id.dimention_s);
		dimention_s.setOnClickListener(this);
		clearView = findViewById(R.id.clearView);
		clearView.setOnClickListener(this);
		handleBet = findViewById(R.id.handleBet);
		handleBet.setOnClickListener(this);
		dimentionIntro = (TextView) findViewById(R.id.dimentionIntro);
		ballsText = (TextView) findViewById(R.id.ballsText);
		billText = (TextView) findViewById(R.id.billText);
		directText = (TextView) findViewById(R.id.directText);
		group3Text = (TextView) findViewById(R.id.group3Text);
		group6Text = (TextView) findViewById(R.id.group6Text);
		directBalls = new ArrayList<ImageView>();
		directHBalls = new ArrayList<ImageView>();
		directTBalls = new ArrayList<ImageView>();
		sixBalls = new ArrayList<ImageView>();
		threeBalls = new ArrayList<ImageView>();
		directBallst = new ArrayList<TextView>();
		directHBallst = new ArrayList<TextView>();
		directTBallst = new ArrayList<TextView>();
		threeBallst = new ArrayList<TextView>();
		sixBallst = new ArrayList<TextView>();
		selectedDirectBalls = new ArrayList<ImageView>();
		selectedDirectHBalls = new ArrayList<ImageView>();
		selectedDirectTBalls = new ArrayList<ImageView>();
		selectedSixBalls = new ArrayList<ImageView>();
		selectedThreeBalls = new ArrayList<ImageView>();
		three_direct_h0 = (ImageView) view1.findViewById(R.id.three_direct_h0);
		three_direct_h1 = (ImageView) view1.findViewById(R.id.three_direct_h1);
		three_direct_h2 = (ImageView) view1.findViewById(R.id.three_direct_h2);
		three_direct_h3 = (ImageView) view1.findViewById(R.id.three_direct_h3);
		three_direct_h4 = (ImageView) view1.findViewById(R.id.three_direct_h4);
		three_direct_h5 = (ImageView) view1.findViewById(R.id.three_direct_h5);
		three_direct_h6 = (ImageView) view1.findViewById(R.id.three_direct_h6);
		three_direct_h7 = (ImageView) view1.findViewById(R.id.three_direct_h7);
		three_direct_h8 = (ImageView) view1.findViewById(R.id.three_direct_h8);
		three_direct_h9 = (ImageView) view1.findViewById(R.id.three_direct_h9);
		three_direct_h0t = (TextView) view1.findViewById(R.id.three_direct_h0t);
		three_direct_h1t = (TextView) view1.findViewById(R.id.three_direct_h1t);
		three_direct_h2t = (TextView) view1.findViewById(R.id.three_direct_h2t);
		three_direct_h3t = (TextView) view1.findViewById(R.id.three_direct_h3t);
		three_direct_h4t = (TextView) view1.findViewById(R.id.three_direct_h4t);
		three_direct_h5t = (TextView) view1.findViewById(R.id.three_direct_h5t);
		three_direct_h6t = (TextView) view1.findViewById(R.id.three_direct_h6t);
		three_direct_h7t = (TextView) view1.findViewById(R.id.three_direct_h7t);
		three_direct_h8t = (TextView) view1.findViewById(R.id.three_direct_h8t);
		three_direct_h9t = (TextView) view1.findViewById(R.id.three_direct_h9t);
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
		three_direct_t0 = (ImageView) view1.findViewById(R.id.three_direct_t0);
		three_direct_t1 = (ImageView) view1.findViewById(R.id.three_direct_t1);
		three_direct_t2 = (ImageView) view1.findViewById(R.id.three_direct_t2);
		three_direct_t3 = (ImageView) view1.findViewById(R.id.three_direct_t3);
		three_direct_t4 = (ImageView) view1.findViewById(R.id.three_direct_t4);
		three_direct_t5 = (ImageView) view1.findViewById(R.id.three_direct_t5);
		three_direct_t6 = (ImageView) view1.findViewById(R.id.three_direct_t6);
		three_direct_t7 = (ImageView) view1.findViewById(R.id.three_direct_t7);
		three_direct_t8 = (ImageView) view1.findViewById(R.id.three_direct_t8);
		three_direct_t9 = (ImageView) view1.findViewById(R.id.three_direct_t9);
		three_direct_t0t = (TextView) view1.findViewById(R.id.three_direct_t0t);
		three_direct_t1t = (TextView) view1.findViewById(R.id.three_direct_t1t);
		three_direct_t2t = (TextView) view1.findViewById(R.id.three_direct_t2t);
		three_direct_t3t = (TextView) view1.findViewById(R.id.three_direct_t3t);
		three_direct_t4t = (TextView) view1.findViewById(R.id.three_direct_t4t);
		three_direct_t5t = (TextView) view1.findViewById(R.id.three_direct_t5t);
		three_direct_t6t = (TextView) view1.findViewById(R.id.three_direct_t6t);
		three_direct_t7t = (TextView) view1.findViewById(R.id.three_direct_t7t);
		three_direct_t8t = (TextView) view1.findViewById(R.id.three_direct_t8t);
		three_direct_t9t = (TextView) view1.findViewById(R.id.three_direct_t9t);
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
		three_direct_0 = (ImageView) view1.findViewById(R.id.three_direct_0);
		three_direct_1 = (ImageView) view1.findViewById(R.id.three_direct_1);
		three_direct_2 = (ImageView) view1.findViewById(R.id.three_direct_2);
		three_direct_3 = (ImageView) view1.findViewById(R.id.three_direct_3);
		three_direct_4 = (ImageView) view1.findViewById(R.id.three_direct_4);
		three_direct_5 = (ImageView) view1.findViewById(R.id.three_direct_5);
		three_direct_6 = (ImageView) view1.findViewById(R.id.three_direct_6);
		three_direct_7 = (ImageView) view1.findViewById(R.id.three_direct_7);
		three_direct_8 = (ImageView) view1.findViewById(R.id.three_direct_8);
		three_direct_9 = (ImageView) view1.findViewById(R.id.three_direct_9);
		three_direct_0t = (TextView) view1.findViewById(R.id.three_direct_0t);
		three_direct_1t = (TextView) view1.findViewById(R.id.three_direct_1t);
		three_direct_2t = (TextView) view1.findViewById(R.id.three_direct_2t);
		three_direct_3t = (TextView) view1.findViewById(R.id.three_direct_3t);
		three_direct_4t = (TextView) view1.findViewById(R.id.three_direct_4t);
		three_direct_5t = (TextView) view1.findViewById(R.id.three_direct_5t);
		three_direct_6t = (TextView) view1.findViewById(R.id.three_direct_6t);
		three_direct_7t = (TextView) view1.findViewById(R.id.three_direct_7t);
		three_direct_8t = (TextView) view1.findViewById(R.id.three_direct_8t);
		three_direct_9t = (TextView) view1.findViewById(R.id.three_direct_9t);
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
		three_three_0 = (ImageView) view2.findViewById(R.id.three_three_0);
		three_three_1 = (ImageView) view2.findViewById(R.id.three_three_1);
		three_three_2 = (ImageView) view2.findViewById(R.id.three_three_2);
		three_three_3 = (ImageView) view2.findViewById(R.id.three_three_3);
		three_three_4 = (ImageView) view2.findViewById(R.id.three_three_4);
		three_three_5 = (ImageView) view2.findViewById(R.id.three_three_5);
		three_three_6 = (ImageView) view2.findViewById(R.id.three_three_6);
		three_three_7 = (ImageView) view2.findViewById(R.id.three_three_7);
		three_three_8 = (ImageView) view2.findViewById(R.id.three_three_8);
		three_three_9 = (ImageView) view2.findViewById(R.id.three_three_9);
		three_three_0t = (TextView) view2.findViewById(R.id.three_three_0t);
		three_three_1t = (TextView) view2.findViewById(R.id.three_three_1t);
		three_three_2t = (TextView) view2.findViewById(R.id.three_three_2t);
		three_three_3t = (TextView) view2.findViewById(R.id.three_three_3t);
		three_three_4t = (TextView) view2.findViewById(R.id.three_three_4t);
		three_three_5t = (TextView) view2.findViewById(R.id.three_three_5t);
		three_three_6t = (TextView) view2.findViewById(R.id.three_three_6t);
		three_three_7t = (TextView) view2.findViewById(R.id.three_three_7t);
		three_three_8t = (TextView) view2.findViewById(R.id.three_three_8t);
		three_three_9t = (TextView) view2.findViewById(R.id.three_three_9t);
		threeBalls.add(three_three_0);
		threeBalls.add(three_three_1);
		threeBalls.add(three_three_2);
		threeBalls.add(three_three_3);
		threeBalls.add(three_three_4);
		threeBalls.add(three_three_5);
		threeBalls.add(three_three_6);
		threeBalls.add(three_three_7);
		threeBalls.add(three_three_8);
		threeBalls.add(three_three_9);
		threeBallst.add(three_three_0t);
		threeBallst.add(three_three_1t);
		threeBallst.add(three_three_2t);
		threeBallst.add(three_three_3t);
		threeBallst.add(three_three_4t);
		threeBallst.add(three_three_5t);
		threeBallst.add(three_three_6t);
		threeBallst.add(three_three_7t);
		threeBallst.add(three_three_8t);
		threeBallst.add(three_three_9t);
		three_six_0t = (TextView) view3.findViewById(R.id.three_six_0t);
		three_six_1t = (TextView) view3.findViewById(R.id.three_six_1t);
		three_six_2t = (TextView) view3.findViewById(R.id.three_six_2t);
		three_six_3t = (TextView) view3.findViewById(R.id.three_six_3t);
		three_six_4t = (TextView) view3.findViewById(R.id.three_six_4t);
		three_six_5t = (TextView) view3.findViewById(R.id.three_six_5t);
		three_six_6t = (TextView) view3.findViewById(R.id.three_six_6t);
		three_six_7t = (TextView) view3.findViewById(R.id.three_six_7t);
		three_six_8t = (TextView) view3.findViewById(R.id.three_six_8t);
		three_six_9t = (TextView) view3.findViewById(R.id.three_six_9t);
		three_six_0 = (ImageView) view3.findViewById(R.id.three_six_0);
		three_six_1 = (ImageView) view3.findViewById(R.id.three_six_1);
		three_six_2 = (ImageView) view3.findViewById(R.id.three_six_2);
		three_six_3 = (ImageView) view3.findViewById(R.id.three_six_3);
		three_six_4 = (ImageView) view3.findViewById(R.id.three_six_4);
		three_six_5 = (ImageView) view3.findViewById(R.id.three_six_5);
		three_six_6 = (ImageView) view3.findViewById(R.id.three_six_6);
		three_six_7 = (ImageView) view3.findViewById(R.id.three_six_7);
		three_six_8 = (ImageView) view3.findViewById(R.id.three_six_8);
		three_six_9 = (ImageView) view3.findViewById(R.id.three_six_9);
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
		for (ImageView iv : sixBalls) {
			if (iv != null) {
				Log.e("pkx", "iv!=null  size" + sixBalls.size());
			} else {

				Log.e("pkx", "iv=======null  size");
			}
		}
		for (int i = 0; i < directHBalls.size(); i++) {
			directHBalls.get(i).setOnClickListener(this);
			directHBalls.get(i).setTag(0);
			directHBalls.get(i).setTag(three_direct_0.getId(), "dh");// 设置球型标志
			directHBalls.get(i).setTag(three_direct_1.getId(), i);// 球号标志
			directHBallst.get(i).setTextColor(redText);
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
		for (int i = 0; i < threeBalls.size(); i++) {
			threeBalls.get(i).setOnClickListener(this);
			threeBalls.get(i).setTag(0);
			threeBalls.get(i).setTag(three_direct_0.getId(), "t");
			threeBalls.get(i).setTag(three_direct_1.getId(), i);
			threeBallst.get(i).setTextColor(redText);
		}
		paper3D = (ViewPager) findViewById(R.id.paper3D);
		paper3D.setOnPageChangeListener(new OnPageChangeListener() {

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

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.back:
		// if (selectedDirectBalls.size() == 0
		// && selectedDirectHBalls.size() == 0
		// && selectedDirectTBalls.size() == 0
		// && selectedSixBalls.size() == 0
		// && selectedThreeBalls.size() == 0) {
		// finish();
		// } else {
		// // new AlertDialog.Builder(this)
		// // .setTitle("退出清除投注信息，是否退出？")
		// // .setNegativeButton("取消",
		// // new DialogInterface.OnClickListener() {
		// // public void onClick(DialogInterface d,
		// // int which) {
		// // d.dismiss();
		// // }
		// // })
		// // .setPositiveButton("确定",
		// // new DialogInterface.OnClickListener() {
		// // public void onClick(DialogInterface d,
		// // int which) {
		// // d.dismiss();
		// // finish();
		// // }
		// // }).show();
		// alert();
		// }
		// break;
		case R.id.passPeroid:
			Intent toSevenShow = new Intent(ThreeDimension.this,
					ShowDimention.class);
			toSevenShow.putExtra("isFromSelectPage", true);
			startActivity(toSevenShow);
			break;
		case R.id.handleBet:
			DimensionBet bet;
			if (currentPage == 0) {
				// 直选
				if (selectedDirectBalls.size() < 1
						|| selectedDirectTBalls.size() < 1
						|| selectedDirectHBalls.size() < 1) {
					Toast.makeText(this, "每位至少一个球", Toast.LENGTH_SHORT).show();
					return;
				}
				ArrayList<Integer> gBalls = new ArrayList<Integer>();
				ArrayList<Integer> tBalls = new ArrayList<Integer>();
				ArrayList<Integer> hBalls = new ArrayList<Integer>();
				for (ImageView gv : selectedDirectBalls) {
					gBalls.add((Integer) gv.getTag(three_direct_1.getId()));
				}
				for (ImageView tv : selectedDirectTBalls) {
					tBalls.add((Integer) tv.getTag(three_direct_1.getId()));
				}
				for (ImageView hv : selectedDirectHBalls) {
					hBalls.add((Integer) hv.getTag(three_direct_1.getId()));
				}
				bet = new DimensionBet();
				bet.setgBalls(gBalls);
				bet.sethBalls(hBalls);
				bet.settBalls(tBalls);
				bet.setType(0);
				Bundle betBundle = new Bundle();
				betBundle.putSerializable("bet", bet);
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
					Intent toCheckPage = new Intent(this,
							ThreeDimensionCheck.class);
					toCheckPage.putExtras(betBundle);
					startActivity(toCheckPage);
					finish();
				}

			} else if (currentPage == 1) {
				// 组合三
				if (selectedThreeBalls.size() < 2) {
					Toast.makeText(this, "组三最少选择两个球", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				ArrayList<Integer> balls3 = new ArrayList<Integer>();
				for (ImageView b3 : selectedThreeBalls) {
					balls3.add((Integer) b3.getTag(three_direct_1.getId()));
				}
				bet = new DimensionBet();
				bet.setBalls3(balls3);
				bet.setType(1);
				Bundle betBundle = new Bundle();
				betBundle.putSerializable("bet", bet);
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
					Intent toCheckPage = new Intent(this,
							ThreeDimensionCheck.class);
					toCheckPage.putExtras(betBundle);
					startActivity(toCheckPage);
					finish();
				}
			} else if (currentPage == 2) {
				// 组合六
				if (selectedSixBalls.size() < 3) {
					Toast.makeText(this, "组三最少选择三个球", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				ArrayList<Integer> balls6 = new ArrayList<Integer>();
				for (ImageView b6 : selectedSixBalls) {
					balls6.add((Integer) b6.getTag(three_direct_1.getId()));
				}
				bet = new DimensionBet();
				bet.setBalls6(balls6);
				bet.setType(2);
				Bundle betBundle = new Bundle();
				betBundle.putSerializable("bet", bet);
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
					Intent toCheckPage = new Intent(this,
							ThreeDimensionCheck.class);
					toCheckPage.putExtras(betBundle);
					startActivity(toCheckPage);
					finish();
				}
			}
			break;
		case R.id.dimention_d:
			paper3D.setCurrentItem(0);
			break;
		case R.id.dimention_t:
			paper3D.setCurrentItem(1);
			break;
		case R.id.dimention_s:
			paper3D.setCurrentItem(2);
			break;
		case R.id.clearView:
			// private ArrayList<ImageView> directBalls, directHBalls,
			// directTBalls,
			// sixBalls, threeBalls, selectedDirectBalls, selectedDirectHBalls,
			// selectedDirectTBalls, selectedSixBalls, selectedThreeBalls;
			// private ArrayList<TextView> directBallst, directHBallst,
			// directTBallst,
			// sixBallst, threeBallst;
			switch (currentPage) {
			case 0:
				for (int i = 0; i < 10; i++) {
					directBallst.get(i).setTextColor(redText);
					directHBallst.get(i).setTextColor(redText);
					directTBallst.get(i).setTextColor(redText);
					directBalls.get(i).setImageResource(R.drawable.purple_ball);
					directBalls.get(i).setTag(0);
					directHBalls.get(i)
							.setImageResource(R.drawable.purple_ball);
					directHBalls.get(i).setTag(0);
					directTBalls.get(i)
							.setImageResource(R.drawable.purple_ball);
					directTBalls.get(i).setTag(0);
				}
				selectedDirectBalls.clear();
				selectedDirectHBalls.clear();
				selectedDirectTBalls.clear();
				checkBill();
				break;
			case 1:
				for (int i = 0; i < 10; i++) {
					threeBallst.get(i).setTextColor(redText);
					threeBalls.get(i).setImageResource(R.drawable.purple_ball);
					threeBalls.get(i).setTag(0);
				}
				selectedThreeBalls.clear();
				checkBill();
				break;
			case 2:
				for (int i = 0; i < 10; i++) {
					sixBallst.get(i).setTextColor(redText);
					sixBalls.get(i).setImageResource(R.drawable.purple_ball);
					sixBalls.get(i).setTag(0);
				}
				selectedSixBalls.clear();
				checkBill();
				break;

			}
			break;

		default:
			break;
		}
		if ("dh".equals(v.getTag(three_direct_0.getId()))
				|| "dt".equals(v.getTag(three_direct_0.getId()))
				|| "d".equals(v.getTag(three_direct_0.getId()))) {
			// 直选球
			if ("dh".equals(v.getTag(three_direct_0.getId()))) {
				if (v.getTag().equals(0)) {
					// 选中直选球
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
			} else if ("dt".equals(v.getTag(three_direct_0.getId()))) {

				if (v.getTag().equals(0)) {
					// 选中直选球
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
					// 选中直选球
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

		} else if ("t".equals(v.getTag(three_direct_0.getId()))) {
			// 排列三
			if (v.getTag().equals(0)) {
				// 选中直选球
				((ImageView) v).setImageResource(R.drawable.black_ball);
				threeBallst.get((Integer) v.getTag(three_direct_1.getId()))
						.setTextColor(whiteText);
				v.setTag(1);
				selectedThreeBalls.add((ImageView) v);
				startVibrato();
			} else {
				// 取消直选球
				((ImageView) v).setImageResource(R.drawable.purple_ball);
				threeBallst.get((Integer) v.getTag(three_direct_1.getId()))
						.setTextColor(redText);
				v.setTag(0);
				selectedThreeBalls.remove(v);
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
	}

	private void checkBill() {
		if (currentPage == 0) {
			if (selectedDirectBalls.size() > 0
					&& selectedDirectTBalls.size() > 0
					&& selectedDirectHBalls.size() > 0) {
				int price = selectedDirectHBalls.size()
						* selectedDirectTBalls.size()
						* selectedDirectBalls.size() * 2;
				String selectHResult = "";
				String selectTResult = "";
				String selectResult = "";
				for (ImageView hv : selectedDirectHBalls) {
					selectHResult += "" + hv.getTag(three_direct_1.getId());
				}
				for (ImageView tv : selectedDirectTBalls) {
					selectTResult += "" + tv.getTag(three_direct_1.getId());
				}
				for (ImageView gv : selectedDirectBalls) {
					selectResult += "" + gv.getTag(three_direct_1.getId());
				}
				String result = selectHResult + " " + selectTResult + " "
						+ selectResult + " ";
				ballsText.setText(result);
				billText.setText("" + price / 2 + "注" + price + "元");
			} else {
				ballsText.setText("");
				billText.setText("0");
			}
		} else if (currentPage == 1) {
			Log.e("pkx", "currentPage == 1");
			if (selectedThreeBalls.size() > 1) {
				String selectResult = "";
				for (ImageView tv : selectedThreeBalls) {
					selectResult += " " + tv.getTag(three_direct_1.getId());
				}
				int price = selectedThreeBalls.size()
						* (selectedThreeBalls.size() - 1);
				ballsText.setText(selectResult);
				billText.setText("共" + price + "注" + price * 2 + "元");
			} else {
				ballsText.setText("");
				billText.setText("0");
			}

		} else if (currentPage == 2) {
			if (selectedSixBalls.size() > 2) {
				String selectResult = "";
				for (ImageView tv : selectedSixBalls) {
					selectResult += " " + tv.getTag(three_direct_1.getId());
				}
				int price = RandomBallsUtils.getBallBets(3,
						selectedSixBalls.size());
				ballsText.setText(selectResult);
				billText.setText("共" + price + "注" + price * 2 + "元");
			} else {
				ballsText.setText("");
				billText.setText("0");
			}
		}
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

	private void alert() {
		final AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.show();
		alert.getWindow().setContentView(R.layout.exit_dialog);

		TextView title = (TextView) alert.findViewById(R.id.title);
		title.setText("退出清除已选项？");
		View okButton = alert.findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.dismiss();
				finish();
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
			if (msg.what == Constant.PAGE_CHANGED) {
				switch (msg.arg1) {
				case 0:
					// 选项卡一，直选
					dimentionIntro
							.setText("每位各选1个或多个号码投注，所选号码与开奖号码一致，且顺序相同即中奖，单注奖金1040元。");
					currentPage = 0;
					checkBill();
					break;
				case 1:
					// 选项卡二，组合三
					dimentionIntro
							.setText("选择2个或更多号码,若当期2个开奖号码有2个相同且包含投注号码,即中奖!奖金:346元!");
					currentPage = 1;
					checkBill();
					break;
				case 2:
					// 选项卡三，组合六
					dimentionIntro
							.setText("选择3个或更多号码投注,若当期3个开奖号码各不相同且包含投注号码,即中奖!奖金:173元!");
					currentPage = 2;
					checkBill();
					break;

				}
				checkTypeBtn();
			} else if (msg.what == Constant.SHAKE_MESSAGE) {
				randomSlectBet();
			}

		}

		private void resetBalls() {
			switch (currentPage) {
			case 0:
				for (ImageView v : directBalls) {
					v.setImageResource(R.drawable.purple_ball);
				}
				for (ImageView v : directHBalls) {
					v.setImageResource(R.drawable.purple_ball);
				}
				for (ImageView v : directTBalls) {
					v.setImageResource(R.drawable.purple_ball);
				}
				break;
			case 1:
				for (ImageView v : threeBalls) {
					v.setImageResource(R.drawable.purple_ball);
				}
				break;
			case 2:
				for (ImageView v : sixBalls) {
					v.setImageResource(R.drawable.purple_ball);
				}
				break;

			default:
				break;
			}
		}

		private void randomSlectBet() {
			startVibrato1();
			resetBalls();
			switch (currentPage) {
			// selectedDirectBalls, selectedDirectHBalls,
			// selectedDirectTBalls, selectedSixBalls, selectedThreeBalls;
			case 0:
				selectedDirectBalls.clear();
				selectedDirectHBalls.clear();
				selectedDirectTBalls.clear();
				int i = new Random().nextInt(10);
				int j = new Random().nextInt(10);
				int k = new Random().nextInt(10);
				for (int f = 0; f < 10; f++) {
					directBalls.get(f).setTag(0);
					directHBalls.get(f).setTag(0);
					directTBalls.get(f).setTag(0);
				}
				directBalls.get(i).setTag(1);
				directHBalls.get(j).setTag(1);
				directTBalls.get(k).setTag(1);
				directBalls.get(i).setImageResource(R.drawable.black_ball);
				directHBalls.get(j).setImageResource(R.drawable.black_ball);
				directTBalls.get(k).setImageResource(R.drawable.black_ball);
				for (TextView b : directBallst) {
					b.setTextColor(redText);
				}
				for (TextView b : directHBallst) {
					b.setTextColor(redText);
				}
				for (TextView b : directTBallst) {
					b.setTextColor(redText);
				}
				directBallst.get(i).setTextColor(whiteText);
				directHBallst.get(j).setTextColor(whiteText);
				directTBallst.get(k).setTextColor(whiteText);
				selectedDirectBalls.add(directBalls.get(i));
				selectedDirectHBalls.add(directHBalls.get(j));
				selectedDirectTBalls.add(directTBalls.get(k));
				break;
			case 1:
				ArrayList<Integer> ints = RandomBallsUtils
						.getRandomBalls(2, 10);
				selectedThreeBalls.clear();
				for (TextView t : threeBallst) {
					t.setTextColor(redText);
				}
				for (ImageView iv : threeBalls) {
					iv.setTag(0);
				}
				for (int l : ints) {
					selectedThreeBalls.add(threeBalls.get(l));
					threeBallst.get(l).setTextColor(whiteText);
				}
				for (ImageView v : selectedThreeBalls) {
					v.setImageResource(R.drawable.black_ball);
					v.setTag(1);
				}
				break;
			case 2:
				ArrayList<Integer> ints3 = RandomBallsUtils.getRandomBalls(3,
						10);
				selectedSixBalls.clear();
				for (TextView s : sixBallst) {
					s.setTextColor(redText);
				}
				for (ImageView iv : sixBalls) {
					iv.setTag(0);
				}
				for (int m : ints3) {
					selectedSixBalls.add(sixBalls.get(m));
					sixBallst.get(m).setTextColor(whiteText);
				}
				for (ImageView v : selectedSixBalls) {
					v.setImageResource(R.drawable.black_ball);
					v.setTag(1);
				}
				break;
			}
			checkBill();
		}
	}
}
