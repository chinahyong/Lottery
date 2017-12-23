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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pkx.lottery.ShakeListener.OnShakeListener;
import com.pkx.lottery.bean.SevenLotteryBet;
import com.pkx.lottery.utils.RandomBallsUtils;
import com.pkx.lottery.utils.SharePreferenceUtil;

import java.util.ArrayList;

public class SevenLottery extends Activity implements OnClickListener {
	private int currentPage = 0;
	private ImageView dimention_d, dimention_s;
	private ImageView red1, red2, red3, red4, red5, red6, red7, red8, red9,
			red10, red11, red12, red13, red14, red15, red16, red17, red18,
			red19, red20, red21, red22, red23, red24, red25, red26, red27,
			red28, red29, red30;
	private TextView tred1, tred2, tred3, tred4, tred5, tred6, tred7, tred8,
			tred9, tred10, tred11, tred12, tred13, tred14, tred15, tred16,
			tred17, tred18, tred19, tred20, tred21, tred22, tred23, tred24,
			tred25, tred26, tred27, tred28, tred29, tred30;
	private TextView red1d, red2d, red3d, red4d, red5d, red6d, red7d, red8d,
			red9d, red10d, red11d, red12d, red13d, red14d, red15d, red16d,
			red17d, red18d, red19d, red20d, red21d, red22d, red23d, red24d,
			red25d, red26d, red27d, red28d, red29d, red30d;
	private TextView red1t, red2t, red3t, red4t, red5t, red6t, red7t, red8t,
			red9t, red10t, red11t, red12t, red13t, red14t, red15t, red16t,
			red17t, red18t, red19t, red20t, red21t, red22t, red23t, red24t,
			red25t, red26t, red27t, red28t, red29t, red30t;
	private ImageView dred1, dred2, dred3, dred4, dred5, dred6, dred7, dred8,
			dred9, dred10, dred11, dred12, dred13, dred14, dred15, dred16,
			dred17, dred18, dred19, dred20, dred21, dred22, dred23, dred24,
			dred25, dred26, dred27, dred28, dred29, dred30;
	private ImageView tred1v, tred2v, tred3v, tred4v, tred5v, tred6v, tred7v,
			tred8v, tred9v, tred10v, tred11v, tred12v, tred13v, tred14v,
			tred15v, tred16v, tred17v, tred18v, tred19v, tred20v, tred21v,
			tred22v, tred23v, tred24v, tred25v, tred26v, tred27v, tred28v,
			tred29v, tred30v;
	private SharePreferenceUtil sutil;
	private ViewPager paperSeven;
	private int redText = Color.parseColor("#CD0000");
	private int whiteText = Color.parseColor("#ffffff");
	private ArrayList<ImageView> selectedRedBalls, redBallList, tRedBallList,
			dRedBallList, selectedDredBallList, selectedTredBallList;
	private ArrayList<TextView> redBallListT, dRedBallListT, tRedBallListT;
	private TextView ballsText, billText, selectText, typeName;
	private View handleBet, clearView;
	private Intent mIntent;
	private Vibrator mVibretor;
	private ShakeListener mShakeListener = null;
	private MyHandler mHandler;
	private LayoutInflater inflater;
	private View tabNormal, tabFollowBet, shakeView;
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
				if (currentPage == 1)
					return;
				startVibrato();
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
		setContentView(R.layout.seven_lottery);
		inflater = getLayoutInflater();
		tabNormal = inflater.inflate(R.layout.tab_seven_normal, null);
		tabFollowBet = inflater.inflate(R.layout.tab_seven_betfollow, null);
		final ArrayList<View> tabs = new ArrayList<View>();
		tabs.add(tabNormal);
		tabs.add(tabFollowBet);
		initViews();
		PagerAdapter adapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return tabs.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(tabs.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(tabs.get(position));
				return tabs.get(position);
			}
		};
		paperSeven.setAdapter(adapter);
		SevenLotteryBet inbet = (SevenLotteryBet) mIntent
				.getSerializableExtra("editBet");
		if (null != inbet) {
			if (0 == inbet.getType()) {
				for (int b : inbet.getBalls()) {
					redBallList.get(b - 1).setTag(1);
					redBallList.get(b - 1).setImageResource(
							R.drawable.black_ball);
					selectedRedBalls.add(redBallList.get(b - 1));
					redBallListT.get(b - 1).setTextColor(whiteText);
				}
				paperSeven.setCurrentItem(0);
			} else {
				selectedDredBallList.clear();
				selectedTredBallList.clear();
				paperSeven.setCurrentItem(1);
				for (int d : inbet.getdBalls()) {
					dRedBallList.get(d - 1).setTag(1);
					dRedBallList.get(d - 1).setImageResource(
							R.drawable.black_ball);
					selectedDredBallList.add(dRedBallList.get(d - 1));
					dRedBallListT.get(d - 1).setTextColor(whiteText);
				}
				for (int t : inbet.gettBalls()) {
					tRedBallList.get(t - 1).setTag(1);
					tRedBallList.get(t - 1).setImageResource(
							R.drawable.black_ball);
					selectedTredBallList.add(tRedBallList.get(t - 1));
					tRedBallListT.get(t - 1).setTextColor(whiteText);
				}

			}
			checkBill();
		}
	}

	public void startVibrato() { // 定义震动
		if (sutil.getShakeVibrat()) {
			mVibretor.vibrate(new long[] { 50, 500, 50, 500 }, -1); // 第一个｛｝里面是节奏数组，
			// 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
		}
	}

	public void startVibrato1() { // 定义震动
		if (sutil.getSelectVibrat()) {
			mVibretor.vibrate(new long[] { 0, 100 }, -1); // 第一个｛｝里面是节奏数组，
			// 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (selectedRedBalls.size() == 0) {
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

	@Override
	public void onClick(View v) {
		if ("r".equals(v.getTag(red1.getId()))) {
			// 点击红球
			if (v.getTag().equals(0)) {
				// 选中红球
				if (selectedRedBalls.size() > 14) {
					Toast.makeText(this, "七彩乐最多选择15个球", Toast.LENGTH_LONG)
							.show();
					return;
				}
				((ImageView) v).setImageResource(R.drawable.black_ball);
				v.setTag(1);
				redBallListT.get((Integer) v.getTag(red2.getId()) - 1)
						.setTextColor(whiteText);
				selectedRedBalls.add((ImageView) v);
				startVibrato1();
			} else {
				// 取消红球
				((ImageView) v).setImageResource(R.drawable.purple_ball);
				redBallListT.get((Integer) v.getTag(red2.getId()) - 1)
						.setTextColor(redText);
				v.setTag(0);
				selectedRedBalls.remove(v);
			}
			checkBill();
		} else if ("d".equals(v.getTag(red1.getId()))) {
			boolean exsist = false;

			for (ImageView srt : selectedTredBallList) {
				if (srt.getTag(red2.getId()) == v.getTag(red2.getId())) {
					exsist = true;
				}
			}
			if (exsist) {
				Toast.makeText(this, "胆组拖组不同同时存在", Toast.LENGTH_SHORT).show();
			} else {
				if (v.getTag().equals(0)) {
					if (selectedDredBallList.size() == 6) {
						Toast.makeText(this, "最多只能选择6个胆红球", Toast.LENGTH_SHORT)
								.show();
						return;
					}
					startVibrato1();
					selectedDredBallList.add((ImageView) v);
					dRedBallListT.get(((Integer) v.getTag(red2.getId()) - 1))
							.setTextColor(whiteText);
					v.setTag(1);
					((ImageView) v).setImageResource(R.drawable.black_ball);
				} else {
					((ImageView) v).setImageResource(R.drawable.purple_ball);
					v.setTag(0);
					dRedBallListT.get(((Integer) v.getTag(red2.getId()) - 1))
							.setTextColor(redText);
					selectedDredBallList.remove(v);
				}

			}
			checkBill();

		} else if ("t".equals(v.getTag(red1.getId()))) {
			// 点击拖红球
			boolean exsist = false;
			for (ImageView srd : selectedDredBallList) {
				if (srd.getTag(red2.getId()) == v.getTag(red2.getId())) {
					exsist = true;
				}
			}
			if (exsist) {
				Toast.makeText(this, "胆组拖组不同同时存在", Toast.LENGTH_SHORT).show();
			} else {
				if (v.getTag().equals(0)) {
					if (selectedTredBallList.size() == 15) {
						Toast.makeText(this, "最多只能选择18个拖红球", Toast.LENGTH_SHORT)
								.show();
						return;
					}
					selectedTredBallList.add((ImageView) v);
					startVibrato1();
					v.setTag(1);
					tRedBallListT.get(((Integer) v.getTag(red2.getId()) - 1))
							.setTextColor(whiteText);
					((ImageView) v).setImageResource(R.drawable.black_ball);
				} else {
					((ImageView) v).setImageResource(R.drawable.purple_ball);
					v.setTag(0);
					tRedBallListT.get(((Integer) v.getTag(red2.getId()) - 1))
							.setTextColor(redText);
					selectedTredBallList.remove(v);
				}

			}
			checkBill();
		}
		switch (v.getId()) {
		case R.id.dimention_d:
			paperSeven.setCurrentItem(0);
			break;
		case R.id.dimention_s:
			paperSeven.setCurrentItem(1);
			break;
		case R.id.passPeroid:
			Intent toSevenShow = new Intent(SevenLottery.this,
					ShowSeven.class);
			toSevenShow.putExtra("isFromSelectPage", true);
			startActivity(toSevenShow);
			break;
		case R.id.back:
			if (selectedRedBalls.size() == 0) {
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
			break;
		case R.id.clearView:
			// private ArrayList<ImageView> selectedRedBalls, redBallList,
			// tRedBallList,
			// dRedBallList, selectedDredBallList, selectedTredBallList;
			// private ArrayList<TextView> redBallListT, dRedBallListT,
			// tRedBallListT;
			if (currentPage == 0) {
				for (TextView t : redBallListT) {
					t.setTextColor(redText);
				}
				for (ImageView i : redBallList) {
					i.setImageResource(R.drawable.purple_ball);
					i.setTag(0);
				}
				selectedRedBalls.clear();
				checkBill();
			} else {
				for (TextView t : dRedBallListT) {
					t.setTextColor(redText);
				}
				for (TextView t : tRedBallListT) {
					t.setTextColor(redText);
				}
				for (ImageView i : tRedBallList) {
					i.setImageResource(R.drawable.purple_ball);
					i.setTag(0);
				}
				for (ImageView i : dRedBallList) {
					i.setImageResource(R.drawable.purple_ball);
					i.setTag(0);
				}
				selectedDredBallList.clear();
				selectedTredBallList.clear();
				checkBill();

			}
			break;
		case R.id.handleBet:
			if (currentPage == 0) {
				if (selectedRedBalls.size() < 7) {
					Toast.makeText(this, "至少选择7个球", Toast.LENGTH_SHORT).show();
					return;
				}
			} else {
				if (selectedDredBallList.size() == 0
						|| selectedTredBallList.size() == 0
						|| (selectedDredBallList.size() + selectedTredBallList
								.size()) < 8) {
					Toast.makeText(this, "最多选择六个胆球，至少选择二个拖球，总球数不得小于8",
							Toast.LENGTH_SHORT).show();
					return;
				}
			}

			Bundle betBundle = new Bundle();
			if (currentPage == 0) {
				ArrayList<Integer> redBalls = new ArrayList<Integer>();
				for (ImageView sv : selectedRedBalls) {
					redBalls.add((Integer) sv.getTag(red2.getId()));
				}
				SevenLotteryBet cbet = new SevenLotteryBet();
				cbet.setType(currentPage);
				cbet.setBalls(redBalls);
				betBundle.putSerializable("sbet", cbet);

			} else {
				ArrayList<Integer> dredBalls = new ArrayList<Integer>();
				for (ImageView sv : selectedDredBallList) {
					dredBalls.add((Integer) sv.getTag(red2.getId()));
				}
				ArrayList<Integer> tredBalls = new ArrayList<Integer>();
				for (ImageView sv : selectedTredBallList) {
					tredBalls.add((Integer) sv.getTag(red2.getId()));
				}
				SevenLotteryBet bet = new SevenLotteryBet();
				bet.setType(currentPage);
				bet.setdBalls(dredBalls);
				bet.settBalls(tredBalls);
				betBundle.putSerializable("sbet", bet);

			}
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
				Intent toCheckPage = new Intent(this, SevenLotteryCheck.class);
				toCheckPage.putExtras(betBundle);
				startActivity(toCheckPage);
				finish();
			}

			break;

		default:
			break;
		}

	}
	private void checkTypeBtn() {
		if (currentPage == 0) {
			dimention_d.setBackgroundResource(R.drawable.yellow_left_pre);
			dimention_s.setBackgroundResource(R.drawable.yellow_right_nor);
		} else if (currentPage == 1) {
			dimention_d.setBackgroundResource(R.drawable.yellow_left_nor);
			dimention_s.setBackgroundResource(R.drawable.yellow_right_pre);
		}
	}
	private void checkBill() {
		if (currentPage == 0) {
			if (selectedRedBalls.size() > 6) {
				int bets = RandomBallsUtils.getBallBets(7,
						selectedRedBalls.size());
				billText.setText("共" + bets + "注 " + bets * 2 + "元");
			} else {
				billText.setText("0");
			}
			selectText.setText("已选" + selectedRedBalls.size() + "个，至少选择七个");
		} else {
			if (selectedDredBallList.size() > 0
					&& (selectedDredBallList.size() + selectedTredBallList
							.size()) > 7) {
				int bets = RandomBallsUtils.getBallBets(
						7 - selectedDredBallList.size(),
						selectedTredBallList.size());
				billText.setText("共" + bets + "注 " + bets * 2 + "元");
			} else {
				billText.setText("0");
			}
		}

	}

	private void initViews() {
		sutil = new SharePreferenceUtil(this);
		passPeroid = findViewById(R.id.passPeroid);
		passPeroid.setOnClickListener(this);
		clearView = findViewById(R.id.clearView);
		clearView.setOnClickListener(this);
		typeName = (TextView) findViewById(R.id.typeName);
		shakeView = findViewById(R.id.shakeView);
		paperSeven = (ViewPager) findViewById(R.id.paperSeven);
		dimention_d = (ImageView) findViewById(R.id.dimention_d);
		dimention_d.setOnClickListener(this);
		dimention_s = (ImageView) findViewById(R.id.dimention_s);
		dimention_s.setOnClickListener(this);
		paperSeven.setOnPageChangeListener(new OnPageChangeListener() {

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
		mHandler = new MyHandler();
		mVibretor = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		mIntent = getIntent();
		handleBet = findViewById(R.id.handleBet);
		handleBet.setOnClickListener(this);
		redBallList = new ArrayList<ImageView>();
		redBallListT = new ArrayList<TextView>();
		selectedRedBalls = new ArrayList<ImageView>();
		selectedDredBallList = new ArrayList<ImageView>();
		selectedTredBallList = new ArrayList<ImageView>();
		ballsText = (TextView) findViewById(R.id.ballsText);
		billText = (TextView) findViewById(R.id.billText);
		selectText = (TextView) findViewById(R.id.selectText);
		red1 = (ImageView) tabNormal.findViewById(R.id.red1);
		red2 = (ImageView) tabNormal.findViewById(R.id.red2);
		red3 = (ImageView) tabNormal.findViewById(R.id.red3);
		red4 = (ImageView) tabNormal.findViewById(R.id.red4);
		red5 = (ImageView) tabNormal.findViewById(R.id.red5);
		red6 = (ImageView) tabNormal.findViewById(R.id.red6);
		red7 = (ImageView) tabNormal.findViewById(R.id.red7);
		red8 = (ImageView) tabNormal.findViewById(R.id.red8);
		red9 = (ImageView) tabNormal.findViewById(R.id.red9);
		red10 = (ImageView) tabNormal.findViewById(R.id.red10);
		red11 = (ImageView) tabNormal.findViewById(R.id.red11);
		red12 = (ImageView) tabNormal.findViewById(R.id.red12);
		red13 = (ImageView) tabNormal.findViewById(R.id.red13);
		red14 = (ImageView) tabNormal.findViewById(R.id.red14);
		red15 = (ImageView) tabNormal.findViewById(R.id.red15);
		red16 = (ImageView) tabNormal.findViewById(R.id.red16);
		red17 = (ImageView) tabNormal.findViewById(R.id.red17);
		red18 = (ImageView) tabNormal.findViewById(R.id.red18);
		red19 = (ImageView) tabNormal.findViewById(R.id.red19);
		red20 = (ImageView) tabNormal.findViewById(R.id.red20);
		red21 = (ImageView) tabNormal.findViewById(R.id.red21);
		red22 = (ImageView) tabNormal.findViewById(R.id.red22);
		red23 = (ImageView) tabNormal.findViewById(R.id.red23);
		red24 = (ImageView) tabNormal.findViewById(R.id.red24);
		red25 = (ImageView) tabNormal.findViewById(R.id.red25);
		red26 = (ImageView) tabNormal.findViewById(R.id.red26);
		red27 = (ImageView) tabNormal.findViewById(R.id.red27);
		red28 = (ImageView) tabNormal.findViewById(R.id.red28);
		red29 = (ImageView) tabNormal.findViewById(R.id.red29);
		red30 = (ImageView) tabNormal.findViewById(R.id.red30);
		tred1 = (TextView) tabNormal.findViewById(R.id.tred1);
		tred2 = (TextView) tabNormal.findViewById(R.id.tred2);
		tred3 = (TextView) tabNormal.findViewById(R.id.tred3);
		tred4 = (TextView) tabNormal.findViewById(R.id.tred4);
		tred5 = (TextView) tabNormal.findViewById(R.id.tred5);
		tred6 = (TextView) tabNormal.findViewById(R.id.tred6);
		tred7 = (TextView) tabNormal.findViewById(R.id.tred7);
		tred8 = (TextView) tabNormal.findViewById(R.id.tred8);
		tred9 = (TextView) tabNormal.findViewById(R.id.tred9);
		tred10 = (TextView) tabNormal.findViewById(R.id.tred10);
		tred11 = (TextView) tabNormal.findViewById(R.id.tred11);
		tred12 = (TextView) tabNormal.findViewById(R.id.tred12);
		tred13 = (TextView) tabNormal.findViewById(R.id.tred13);
		tred14 = (TextView) tabNormal.findViewById(R.id.tred14);
		tred15 = (TextView) tabNormal.findViewById(R.id.tred15);
		tred16 = (TextView) tabNormal.findViewById(R.id.tred16);
		tred17 = (TextView) tabNormal.findViewById(R.id.tred17);
		tred18 = (TextView) tabNormal.findViewById(R.id.tred18);
		tred19 = (TextView) tabNormal.findViewById(R.id.tred19);
		tred20 = (TextView) tabNormal.findViewById(R.id.tred20);
		tred21 = (TextView) tabNormal.findViewById(R.id.tred21);
		tred22 = (TextView) tabNormal.findViewById(R.id.tred22);
		tred23 = (TextView) tabNormal.findViewById(R.id.tred23);
		tred24 = (TextView) tabNormal.findViewById(R.id.tred24);
		tred25 = (TextView) tabNormal.findViewById(R.id.tred25);
		tred26 = (TextView) tabNormal.findViewById(R.id.tred26);
		tred27 = (TextView) tabNormal.findViewById(R.id.tred27);
		tred28 = (TextView) tabNormal.findViewById(R.id.tred28);
		tred29 = (TextView) tabNormal.findViewById(R.id.tred29);
		tred30 = (TextView) tabNormal.findViewById(R.id.tred30);
		redBallList.add(red1);
		redBallList.add(red2);
		redBallList.add(red3);
		redBallList.add(red4);
		redBallList.add(red5);
		redBallList.add(red6);
		redBallList.add(red7);
		redBallList.add(red8);
		redBallList.add(red9);
		redBallList.add(red10);
		redBallList.add(red11);
		redBallList.add(red12);
		redBallList.add(red13);
		redBallList.add(red14);
		redBallList.add(red15);
		redBallList.add(red16);
		redBallList.add(red17);
		redBallList.add(red18);
		redBallList.add(red19);
		redBallList.add(red20);
		redBallList.add(red21);
		redBallList.add(red22);
		redBallList.add(red23);
		redBallList.add(red24);
		redBallList.add(red25);
		redBallList.add(red26);
		redBallList.add(red27);
		redBallList.add(red28);
		redBallList.add(red29);
		redBallList.add(red30);
		redBallListT.add(tred1);
		redBallListT.add(tred2);
		redBallListT.add(tred3);
		redBallListT.add(tred4);
		redBallListT.add(tred5);
		redBallListT.add(tred6);
		redBallListT.add(tred7);
		redBallListT.add(tred8);
		redBallListT.add(tred9);
		redBallListT.add(tred10);
		redBallListT.add(tred11);
		redBallListT.add(tred12);
		redBallListT.add(tred13);
		redBallListT.add(tred14);
		redBallListT.add(tred15);
		redBallListT.add(tred16);
		redBallListT.add(tred17);
		redBallListT.add(tred18);
		redBallListT.add(tred19);
		redBallListT.add(tred20);
		redBallListT.add(tred21);
		redBallListT.add(tred22);
		redBallListT.add(tred23);
		redBallListT.add(tred24);
		redBallListT.add(tred25);
		redBallListT.add(tred26);
		redBallListT.add(tred27);
		redBallListT.add(tred28);
		redBallListT.add(tred29);
		redBallListT.add(tred30);
		for (int i = 0; i < redBallList.size(); i++) {
			redBallListT.get(i).setTextColor(redText);
			redBallList.get(i).setTag(0);
			redBallList.get(i).setTag(red1.getId(), "r");
			redBallList.get(i).setTag(red2.getId(), i + 1);
			redBallList.get(i).setOnClickListener(this);
		}
		red1d = (TextView) tabFollowBet.findViewById(R.id.dred1);
		red2d = (TextView) tabFollowBet.findViewById(R.id.dred2);
		red3d = (TextView) tabFollowBet.findViewById(R.id.dred3);
		red4d = (TextView) tabFollowBet.findViewById(R.id.dred4);
		red5d = (TextView) tabFollowBet.findViewById(R.id.dred5);
		red6d = (TextView) tabFollowBet.findViewById(R.id.dred6);
		red7d = (TextView) tabFollowBet.findViewById(R.id.dred7);
		red8d = (TextView) tabFollowBet.findViewById(R.id.dred8);
		red9d = (TextView) tabFollowBet.findViewById(R.id.dred9);
		red10d = (TextView) tabFollowBet.findViewById(R.id.dred10);
		red11d = (TextView) tabFollowBet.findViewById(R.id.dred11);
		red12d = (TextView) tabFollowBet.findViewById(R.id.dred12);
		red13d = (TextView) tabFollowBet.findViewById(R.id.dred13);
		red14d = (TextView) tabFollowBet.findViewById(R.id.dred14);
		red15d = (TextView) tabFollowBet.findViewById(R.id.dred15);
		red16d = (TextView) tabFollowBet.findViewById(R.id.dred16);
		red17d = (TextView) tabFollowBet.findViewById(R.id.dred17);
		red18d = (TextView) tabFollowBet.findViewById(R.id.dred18);
		red19d = (TextView) tabFollowBet.findViewById(R.id.dred19);
		red20d = (TextView) tabFollowBet.findViewById(R.id.dred20);
		red21d = (TextView) tabFollowBet.findViewById(R.id.dred21);
		red22d = (TextView) tabFollowBet.findViewById(R.id.dred22);
		red23d = (TextView) tabFollowBet.findViewById(R.id.dred23);
		red24d = (TextView) tabFollowBet.findViewById(R.id.dred24);
		red25d = (TextView) tabFollowBet.findViewById(R.id.dred25);
		red26d = (TextView) tabFollowBet.findViewById(R.id.dred26);
		red27d = (TextView) tabFollowBet.findViewById(R.id.dred27);
		red28d = (TextView) tabFollowBet.findViewById(R.id.dred28);
		red29d = (TextView) tabFollowBet.findViewById(R.id.dred29);
		red30d = (TextView) tabFollowBet.findViewById(R.id.dred30);
		red1t = (TextView) tabFollowBet.findViewById(R.id.dtred1);
		red2t = (TextView) tabFollowBet.findViewById(R.id.dtred2);
		red3t = (TextView) tabFollowBet.findViewById(R.id.dtred3);
		red4t = (TextView) tabFollowBet.findViewById(R.id.dtred4);
		red5t = (TextView) tabFollowBet.findViewById(R.id.dtred5);
		red6t = (TextView) tabFollowBet.findViewById(R.id.dtred6);
		red7t = (TextView) tabFollowBet.findViewById(R.id.dtred7);
		red8t = (TextView) tabFollowBet.findViewById(R.id.dtred8);
		red9t = (TextView) tabFollowBet.findViewById(R.id.dtred9);
		red10t = (TextView) tabFollowBet.findViewById(R.id.dtred10);
		red11t = (TextView) tabFollowBet.findViewById(R.id.dtred11);
		red12t = (TextView) tabFollowBet.findViewById(R.id.dtred12);
		red13t = (TextView) tabFollowBet.findViewById(R.id.dtred13);
		red14t = (TextView) tabFollowBet.findViewById(R.id.dtred14);
		red15t = (TextView) tabFollowBet.findViewById(R.id.dtred15);
		red16t = (TextView) tabFollowBet.findViewById(R.id.dtred16);
		red17t = (TextView) tabFollowBet.findViewById(R.id.dtred17);
		red18t = (TextView) tabFollowBet.findViewById(R.id.dtred18);
		red19t = (TextView) tabFollowBet.findViewById(R.id.dtred19);
		red20t = (TextView) tabFollowBet.findViewById(R.id.dtred20);
		red21t = (TextView) tabFollowBet.findViewById(R.id.dtred21);
		red22t = (TextView) tabFollowBet.findViewById(R.id.dtred22);
		red23t = (TextView) tabFollowBet.findViewById(R.id.dtred23);
		red24t = (TextView) tabFollowBet.findViewById(R.id.dtred24);
		red25t = (TextView) tabFollowBet.findViewById(R.id.dtred25);
		red26t = (TextView) tabFollowBet.findViewById(R.id.dtred26);
		red27t = (TextView) tabFollowBet.findViewById(R.id.dtred27);
		red28t = (TextView) tabFollowBet.findViewById(R.id.dtred28);
		red29t = (TextView) tabFollowBet.findViewById(R.id.dtred29);
		red30t = (TextView) tabFollowBet.findViewById(R.id.dtred30);
		dRedBallListT = new ArrayList<TextView>();
		tRedBallListT = new ArrayList<TextView>();
		dRedBallListT.add(red1d);
		dRedBallListT.add(red2d);
		dRedBallListT.add(red3d);
		dRedBallListT.add(red4d);
		dRedBallListT.add(red5d);
		dRedBallListT.add(red6d);
		dRedBallListT.add(red7d);
		dRedBallListT.add(red8d);
		dRedBallListT.add(red9d);
		dRedBallListT.add(red10d);
		dRedBallListT.add(red11d);
		dRedBallListT.add(red12d);
		dRedBallListT.add(red13d);
		dRedBallListT.add(red14d);
		dRedBallListT.add(red15d);
		dRedBallListT.add(red16d);
		dRedBallListT.add(red17d);
		dRedBallListT.add(red18d);
		dRedBallListT.add(red19d);
		dRedBallListT.add(red20d);
		dRedBallListT.add(red21d);
		dRedBallListT.add(red22d);
		dRedBallListT.add(red23d);
		dRedBallListT.add(red24d);
		dRedBallListT.add(red25d);
		dRedBallListT.add(red26d);
		dRedBallListT.add(red27d);
		dRedBallListT.add(red28d);
		dRedBallListT.add(red29d);
		dRedBallListT.add(red30d);
		tRedBallListT.add(red1t);
		tRedBallListT.add(red2t);
		tRedBallListT.add(red3t);
		tRedBallListT.add(red4t);
		tRedBallListT.add(red5t);
		tRedBallListT.add(red6t);
		tRedBallListT.add(red7t);
		tRedBallListT.add(red8t);
		tRedBallListT.add(red9t);
		tRedBallListT.add(red10t);
		tRedBallListT.add(red11t);
		tRedBallListT.add(red12t);
		tRedBallListT.add(red13t);
		tRedBallListT.add(red14t);
		tRedBallListT.add(red15t);
		tRedBallListT.add(red16t);
		tRedBallListT.add(red17t);
		tRedBallListT.add(red18t);
		tRedBallListT.add(red19t);
		tRedBallListT.add(red20t);
		tRedBallListT.add(red21t);
		tRedBallListT.add(red22t);
		tRedBallListT.add(red23t);
		tRedBallListT.add(red24t);
		tRedBallListT.add(red25t);
		tRedBallListT.add(red26t);
		tRedBallListT.add(red27t);
		tRedBallListT.add(red28t);
		tRedBallListT.add(red29t);
		tRedBallListT.add(red30t);
		for (int i = 0; i < dRedBallListT.size(); i++) {
			redBallListT.get(i).setTextColor(redText);
		}
		for (int i = 0; i < tRedBallListT.size(); i++) {
			tRedBallListT.get(i).setTextColor(redText);
		}
		dred1 = (ImageView) tabFollowBet.findViewById(R.id.red1);
		dred2 = (ImageView) tabFollowBet.findViewById(R.id.red2);
		dred3 = (ImageView) tabFollowBet.findViewById(R.id.red3);
		dred4 = (ImageView) tabFollowBet.findViewById(R.id.red4);
		dred5 = (ImageView) tabFollowBet.findViewById(R.id.red5);
		dred6 = (ImageView) tabFollowBet.findViewById(R.id.red6);
		dred7 = (ImageView) tabFollowBet.findViewById(R.id.red7);
		dred8 = (ImageView) tabFollowBet.findViewById(R.id.red8);
		dred9 = (ImageView) tabFollowBet.findViewById(R.id.red9);
		dred10 = (ImageView) tabFollowBet.findViewById(R.id.red10);
		dred11 = (ImageView) tabFollowBet.findViewById(R.id.red11);
		dred12 = (ImageView) tabFollowBet.findViewById(R.id.red12);
		dred13 = (ImageView) tabFollowBet.findViewById(R.id.red13);
		dred14 = (ImageView) tabFollowBet.findViewById(R.id.red14);
		dred15 = (ImageView) tabFollowBet.findViewById(R.id.red15);
		dred16 = (ImageView) tabFollowBet.findViewById(R.id.red16);
		dred17 = (ImageView) tabFollowBet.findViewById(R.id.red17);
		dred18 = (ImageView) tabFollowBet.findViewById(R.id.red18);
		dred19 = (ImageView) tabFollowBet.findViewById(R.id.red19);
		dred20 = (ImageView) tabFollowBet.findViewById(R.id.red20);
		dred21 = (ImageView) tabFollowBet.findViewById(R.id.red21);
		dred22 = (ImageView) tabFollowBet.findViewById(R.id.red22);
		dred23 = (ImageView) tabFollowBet.findViewById(R.id.red23);
		dred24 = (ImageView) tabFollowBet.findViewById(R.id.red24);
		dred25 = (ImageView) tabFollowBet.findViewById(R.id.red25);
		dred26 = (ImageView) tabFollowBet.findViewById(R.id.red26);
		dred27 = (ImageView) tabFollowBet.findViewById(R.id.red27);
		dred28 = (ImageView) tabFollowBet.findViewById(R.id.red28);
		dred29 = (ImageView) tabFollowBet.findViewById(R.id.red29);
		dred30 = (ImageView) tabFollowBet.findViewById(R.id.red30);
		tred1v = (ImageView) tabFollowBet.findViewById(R.id.tred1);
		tred2v = (ImageView) tabFollowBet.findViewById(R.id.tred2);
		tred3v = (ImageView) tabFollowBet.findViewById(R.id.tred3);
		tred4v = (ImageView) tabFollowBet.findViewById(R.id.tred4);
		tred5v = (ImageView) tabFollowBet.findViewById(R.id.tred5);
		tred6v = (ImageView) tabFollowBet.findViewById(R.id.tred6);
		tred7v = (ImageView) tabFollowBet.findViewById(R.id.tred7);
		tred8v = (ImageView) tabFollowBet.findViewById(R.id.tred8);
		tred9v = (ImageView) tabFollowBet.findViewById(R.id.tred9);
		tred10v = (ImageView) tabFollowBet.findViewById(R.id.tred10);
		tred11v = (ImageView) tabFollowBet.findViewById(R.id.tred11);
		tred12v = (ImageView) tabFollowBet.findViewById(R.id.tred12);
		tred13v = (ImageView) tabFollowBet.findViewById(R.id.tred13);
		tred14v = (ImageView) tabFollowBet.findViewById(R.id.tred14);
		tred15v = (ImageView) tabFollowBet.findViewById(R.id.tred15);
		tred16v = (ImageView) tabFollowBet.findViewById(R.id.tred16);
		tred17v = (ImageView) tabFollowBet.findViewById(R.id.tred17);
		tred18v = (ImageView) tabFollowBet.findViewById(R.id.tred18);
		tred19v = (ImageView) tabFollowBet.findViewById(R.id.tred19);
		tred20v = (ImageView) tabFollowBet.findViewById(R.id.tred20);
		tred21v = (ImageView) tabFollowBet.findViewById(R.id.tred21);
		tred22v = (ImageView) tabFollowBet.findViewById(R.id.tred22);
		tred23v = (ImageView) tabFollowBet.findViewById(R.id.tred23);
		tred24v = (ImageView) tabFollowBet.findViewById(R.id.tred24);
		tred25v = (ImageView) tabFollowBet.findViewById(R.id.tred25);
		tred26v = (ImageView) tabFollowBet.findViewById(R.id.tred26);
		tred27v = (ImageView) tabFollowBet.findViewById(R.id.tred27);
		tred28v = (ImageView) tabFollowBet.findViewById(R.id.tred28);
		tred29v = (ImageView) tabFollowBet.findViewById(R.id.tred29);
		tred30v = (ImageView) tabFollowBet.findViewById(R.id.tred30);
		tRedBallList = new ArrayList<ImageView>();
		dRedBallList = new ArrayList<ImageView>();
		tRedBallList.add(tred1v);
		tRedBallList.add(tred2v);
		tRedBallList.add(tred3v);
		tRedBallList.add(tred4v);
		tRedBallList.add(tred5v);
		tRedBallList.add(tred6v);
		tRedBallList.add(tred7v);
		tRedBallList.add(tred8v);
		tRedBallList.add(tred9v);
		tRedBallList.add(tred10v);
		tRedBallList.add(tred11v);
		tRedBallList.add(tred12v);
		tRedBallList.add(tred13v);
		tRedBallList.add(tred14v);
		tRedBallList.add(tred15v);
		tRedBallList.add(tred16v);
		tRedBallList.add(tred17v);
		tRedBallList.add(tred18v);
		tRedBallList.add(tred19v);
		tRedBallList.add(tred20v);
		tRedBallList.add(tred21v);
		tRedBallList.add(tred22v);
		tRedBallList.add(tred23v);
		tRedBallList.add(tred24v);
		tRedBallList.add(tred25v);
		tRedBallList.add(tred26v);
		tRedBallList.add(tred27v);
		tRedBallList.add(tred28v);
		tRedBallList.add(tred29v);
		tRedBallList.add(tred30v);
		dRedBallList.add(dred1);
		dRedBallList.add(dred2);
		dRedBallList.add(dred3);
		dRedBallList.add(dred4);
		dRedBallList.add(dred5);
		dRedBallList.add(dred6);
		dRedBallList.add(dred7);
		dRedBallList.add(dred8);
		dRedBallList.add(dred9);
		dRedBallList.add(dred10);
		dRedBallList.add(dred11);
		dRedBallList.add(dred12);
		dRedBallList.add(dred13);
		dRedBallList.add(dred14);
		dRedBallList.add(dred15);
		dRedBallList.add(dred16);
		dRedBallList.add(dred17);
		dRedBallList.add(dred18);
		dRedBallList.add(dred19);
		dRedBallList.add(dred20);
		dRedBallList.add(dred21);
		dRedBallList.add(dred22);
		dRedBallList.add(dred23);
		dRedBallList.add(dred24);
		dRedBallList.add(dred25);
		dRedBallList.add(dred26);
		dRedBallList.add(dred27);
		dRedBallList.add(dred28);
		dRedBallList.add(dred29);
		dRedBallList.add(dred30);
		for (int i = 0; i < dRedBallList.size(); i++) {
			dRedBallListT.get(i).setTextColor(redText);
			dRedBallList.get(i).setTag(0);
			dRedBallList.get(i).setTag(red1.getId(), "d");
			dRedBallList.get(i).setTag(red2.getId(), i + 1);
			dRedBallList.get(i).setOnClickListener(this);
		}
		for (int i = 0; i < tRedBallList.size(); i++) {
			tRedBallListT.get(i).setTextColor(redText);
			tRedBallList.get(i).setTag(0);
			tRedBallList.get(i).setTag(red1.getId(), "t");
			tRedBallList.get(i).setTag(red2.getId(), i + 1);
			tRedBallList.get(i).setOnClickListener(this);
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
			if (msg.what == Constant.SHAKE_MESSAGE) {
				randomlySelect();
			}
			if (msg.what == Constant.PAGE_CHANGED) {
				switch (msg.arg1) {
				case 0:
					// 选项卡一，直选
					typeName.setText("七乐彩-直选");
					currentPage = 0;
					if (shakeView.getVisibility() == View.GONE) {
						shakeView.setVisibility(View.VISIBLE);
					}
					checkTypeBtn();
					checkBill();
					break;
				case 1:
					// 选项卡二，胆拖
					typeName.setText("七乐彩-胆拖");
					if (shakeView.getVisibility() == View.VISIBLE) {
						shakeView.setVisibility(View.GONE);
					}
					currentPage = 1;
					checkTypeBtn();
					checkBill();
					break;
				}
			}

		}
	}

	private void randomlySelect() {
		selectedRedBalls.clear();
		for (ImageView rv : redBallList) {
			rv.setImageResource(R.drawable.purple_ball);
			rv.setTag(0);
		}
		for (TextView t : redBallListT) {
			t.setTextColor(redText);
		}

		ArrayList<Integer> randomRedBalls = RandomBallsUtils.getRandomBalls(7,
				30);
		for (int i : randomRedBalls) {
			redBallList.get(i).setImageResource(R.drawable.black_ball);
			selectedRedBalls.add(redBallList.get(i));
			redBallList.get(i).setTag(1);
			redBallListT.get(i).setTextColor(whiteText);
		}
		checkBill();
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

}
