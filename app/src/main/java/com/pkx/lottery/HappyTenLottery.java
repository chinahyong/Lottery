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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.ShakeListener.OnShakeListener;
import com.pkx.lottery.dto.FastCur;
import com.pkx.lottery.dto.lott.HappyTenLott;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.RandomBallsUtils;
import com.pkx.lottery.utils.SharePreferenceUtil;

import java.util.ArrayList;

public class HappyTenLottery extends Activity implements OnClickListener {
	private TextView tenMinute, minute, sec1, sec2, minute1, sec11, sec21;
	private int timerest = 0;
	private int currentPage = 0;
	private boolean isFirstView = true;
	private Thread timeThread;
	private AlertDialog alertLott;
	private TextView text1, text2, text3;
	// 直选球二位
	private ImageView lred1, lred2, lred3, lred4, lred5, lred6, lred7, lred8,
			lred9, lred10, lred11, lred12, lred13, lred14, lred15, lred16,
			lred17, lred18, lred19, lred20;
	// 直选字二位
	private TextView ltred1, ltred2, ltred3, ltred4, ltred5, ltred6, ltred7,
			ltred8, ltred9, ltred10, ltred11, ltred12, ltred13, ltred14,
			ltred15, ltred16, ltred17, ltred18, ltred19, ltred20;
	// 直选球
	private ImageView llred1, llred2, llred3, llred4, llred5, llred6, llred7,
			llred8, llred9, llred10, llred11, llred12, llred13, llred14,
			llred15, llred16, llred17, llred18, llred19, llred20;
	// 直选字
	private TextView lltred1, lltred2, lltred3, lltred4, lltred5, lltred6,
			lltred7, lltred8, lltred9, lltred10, lltred11, lltred12, lltred13,
			lltred14, lltred15, lltred16, lltred17, lltred18, lltred19,
			lltred20;
	// 直选球
	private ImageView red1, red2, red3, red4, red5, red6, red7, red8, red9,
			red10, red11, red12, red13, red14, red15, red16, red17, red18,
			red19, red20;
	// 直选字
	private TextView tred1, tred2, tred3, tred4, tred5, tred6, tred7, tred8,
			tred9, tred10, tred11, tred12, tred13, tred14, tred15, tred16,
			tred17, tred18, tred19, tred20;
	private TextView red1d, red2d, red3d, red4d, red5d, red6d, red7d, red8d,
			red9d, red10d, red11d, red12d, red13d, red14d, red15d, red16d,
			red17d, red18d, red19d, red20d;
	private TextView red1t, red2t, red3t, red4t, red5t, red6t, red7t, red8t,
			red9t, red10t, red11t, red12t, red13t, red14t, red15t, red16t,
			red17t, red18t, red19t, red20t;
	private ImageView dred1, dred2, dred3, dred4, dred5, dred6, dred7, dred8,
			dred9, dred10, dred11, dred12, dred13, dred14, dred15, dred16,
			dred17, dred18, dred19, dred20;
	private ImageView tred1v, tred2v, tred3v, tred4v, tred5v, tred6v, tred7v,
			tred8v, tred9v, tred10v, tred11v, tred12v, tred13v, tred14v,
			tred15v, tred16v, tred17v, tred18v, tred19v, tred20v;
	private SharePreferenceUtil sutil;
	private int redText = Color.parseColor("#CD0000");
	private int whiteText = Color.parseColor("#ffffff");
	private ArrayList<ImageView> selectedRedBalls, selectedRedBalls1,
			selectedRedBalls2, redBallList, redBallList1, redBallList2,
			tRedBallList, dRedBallList, selectedDredBallList,
			selectedTredBallList;
	private ArrayList<TextView> redBallListT, redBallListT1, redBallListT2,
			dRedBallListT, tRedBallListT;
	private TextView ballsText, billText, selectText, typeName;
	private View handleBet, clearView;
	private Intent mIntent;
	private Vibrator mVibretor;
	private ShakeListener mShakeListener = null;
	private MyHandler mHandler;
	private LayoutInflater inflater;
	private View shakeView, playTypeView, normalView, normalView1, normalView2,
			dtView;// normalView直选球VIEW组
	private HappyTenLott happyLott;
	private int selectNum = 20, selectDanNum = 2;
	private boolean isStraight2, isStraight3;
	private View frontView;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.happy_ten_lottery);
		inflater = getLayoutInflater();
		initViews();
		RequestParams params = new RequestParams();
		params.put("type", "7");
		Net.post(true, this, Constant.PAY_URL
				+ "/ajax/index.php?act=load_lottery_info", params, mHandler,
				Constant.NET_ACTION_SECRET_SECURE);
		timeThread = new Thread(new MyThread());
	}

	private void setSelectNum() {
		switch (Constant.HAPPY_PLAY_TYPE) {
		case 0:
			selectText.setText("至少选1个");
			selectNum = 18;
			break;
		case 1:
			selectText.setText("至少选2个");
			selectNum = 18;
			break;
		case 2:
			selectText.setText("至少选3个");
			selectNum = 17;
			break;
		case 3:
			selectText.setText("至少选4个");
			selectNum = 16;
			break;
		case 4:
			selectText.setText("至少选5个");
			selectNum = 15;
			break;
		case 5:
			selectText.setText("至少选2个");
			selectNum = 18;
			break;
		case 6:
			selectText.setText("至少各选1个,总和大于3个");
			selectNum = 20;
			break;
		case 7:
			selectText.setText("每位至少选1个");
			selectNum = 18;
			break;
		case 8:
			selectText.setText("至少选3个");
			selectNum = 18;
			break;
		case 9:
			selectText.setText("每位至少选1个");
			selectNum = 20;
			break;

		}
	}

	@Override
	protected void onResume() {
		Constant.TICK_TOCK_FLAG = true;
		super.onResume();
		setSelectNum();
		isStraight2 = false;
		isStraight3 = false;
		switch (Constant.HAPPY_PLAY_TYPE) {
		case 0:
			text1.setVisibility(View.GONE);
			text2.setVisibility(View.GONE);
			text3.setVisibility(View.GONE);
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}
			for (ImageView i : redBallList) {
				i.setImageResource(R.drawable.purple_ball);
				i.setTag(0);
			}
			selectedRedBalls.clear();
			checkBill();
			typeName.setText("快乐十分-任选1");
			dtView.setVisibility(View.GONE);
			normalView.setVisibility(View.VISIBLE);
			normalView1.setVisibility(View.GONE);
			normalView2.setVisibility(View.GONE);
			break;
		case 1:
			text1.setVisibility(View.GONE);
			text2.setVisibility(View.GONE);
			text3.setVisibility(View.GONE);
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}
			for (ImageView i : redBallList) {
				i.setImageResource(R.drawable.purple_ball);
				i.setTag(0);
			}
			selectedRedBalls.clear();
			checkBill();
			typeName.setText("快乐十分-任选2");
			dtView.setVisibility(View.GONE);
			normalView.setVisibility(View.VISIBLE);
			normalView1.setVisibility(View.GONE);
			normalView2.setVisibility(View.GONE);
			break;
		case 2:
			text1.setVisibility(View.GONE);
			text2.setVisibility(View.GONE);
			text3.setVisibility(View.GONE);
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}
			for (ImageView i : redBallList) {
				i.setImageResource(R.drawable.purple_ball);
				i.setTag(0);
			}
			selectedRedBalls.clear();
			checkBill();
			typeName.setText("快乐十分-任选3");
			dtView.setVisibility(View.GONE);
			normalView.setVisibility(View.VISIBLE);
			normalView1.setVisibility(View.GONE);
			normalView2.setVisibility(View.GONE);
			break;
		case 3:
			text1.setVisibility(View.GONE);
			text2.setVisibility(View.GONE);
			text3.setVisibility(View.GONE);
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}
			for (ImageView i : redBallList) {
				i.setImageResource(R.drawable.purple_ball);
				i.setTag(0);
			}
			selectedRedBalls.clear();
			checkBill();
			typeName.setText("快乐十分-任选4");
			dtView.setVisibility(View.GONE);
			normalView.setVisibility(View.VISIBLE);
			normalView1.setVisibility(View.GONE);
			normalView2.setVisibility(View.GONE);
			break;
		case 4:
			text1.setVisibility(View.GONE);
			text2.setVisibility(View.GONE);
			text3.setVisibility(View.GONE);
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}
			for (ImageView i : redBallList) {
				i.setImageResource(R.drawable.purple_ball);
				i.setTag(0);
			}
			selectedRedBalls.clear();
			checkBill();
			typeName.setText("快乐十分-任选5");
			dtView.setVisibility(View.GONE);
			normalView.setVisibility(View.VISIBLE);
			normalView1.setVisibility(View.GONE);
			normalView2.setVisibility(View.GONE);
			break;
		case 5:
			text1.setVisibility(View.GONE);
			text2.setVisibility(View.GONE);
			text3.setVisibility(View.GONE);
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}
			for (ImageView i : redBallList) {
				i.setImageResource(R.drawable.purple_ball);
				i.setTag(0);
			}
			selectedRedBalls.clear();
			checkBill();
			typeName.setText("快乐十分-选2连组");
			dtView.setVisibility(View.GONE);
			normalView.setVisibility(View.VISIBLE);
			normalView1.setVisibility(View.GONE);
			normalView2.setVisibility(View.GONE);
			break;
		case 6:
			text1.setVisibility(View.GONE);
			text2.setVisibility(View.GONE);
			text3.setVisibility(View.GONE);
			for (int i = 0; i < dRedBallListT.size(); i++) {
				dRedBallListT.get(i).setTextColor(redText);
				tRedBallListT.get(i).setTextColor(redText);
				tRedBallList.get(i).setImageResource(R.drawable.purple_ball);
				tRedBallList.get(i).setTag(0);
				dRedBallList.get(i).setImageResource(R.drawable.purple_ball);
				dRedBallList.get(i).setTag(0);

			}
			selectedDredBallList.clear();
			selectedTredBallList.clear();
			checkBill();
			typeName.setText("快乐十分-选3胆拖");
			dtView.setVisibility(View.VISIBLE);
			normalView.setVisibility(View.GONE);
			normalView1.setVisibility(View.GONE);
			normalView2.setVisibility(View.GONE);
			break;
		case 7:
			isStraight2 = true;
			text1.setVisibility(View.VISIBLE);
			text2.setVisibility(View.VISIBLE);
			text3.setVisibility(View.GONE);
			for (int i = 0; i < redBallList.size(); i++) {
				redBallList.get(i).setImageResource(R.drawable.purple_ball);
				redBallList.get(i).setTag(0);
				redBallList1.get(i).setImageResource(R.drawable.purple_ball);
				redBallList1.get(i).setTag(0);
				redBallListT.get(i).setTextColor(redText);
				redBallListT1.get(i).setTextColor(redText);
			}
			selectedRedBalls.clear();
			selectedRedBalls1.clear();
			typeName.setText("快乐十分-选2连直");
			dtView.setVisibility(View.GONE);
			normalView.setVisibility(View.VISIBLE);
			normalView1.setVisibility(View.VISIBLE);
			normalView2.setVisibility(View.GONE);
			break;
		case 8:
			text1.setVisibility(View.GONE);
			text2.setVisibility(View.GONE);
			text3.setVisibility(View.GONE);
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}
			for (ImageView i : redBallList) {
				i.setImageResource(R.drawable.purple_ball);
				i.setTag(0);
			}
			selectedRedBalls.clear();
			checkBill();
			typeName.setText("快乐十分-选3前组");
			dtView.setVisibility(View.GONE);
			normalView.setVisibility(View.VISIBLE);
			normalView1.setVisibility(View.GONE);
			normalView2.setVisibility(View.GONE);
			break;
		case 9:
			isStraight3 = true;
			text1.setVisibility(View.VISIBLE);
			text2.setVisibility(View.VISIBLE);
			text3.setVisibility(View.VISIBLE);
			for (int i = 0; i < redBallList.size(); i++) {
				redBallList.get(i).setImageResource(R.drawable.purple_ball);
				redBallList.get(i).setTag(0);
				redBallList1.get(i).setImageResource(R.drawable.purple_ball);
				redBallList1.get(i).setTag(0);
				redBallList2.get(i).setImageResource(R.drawable.purple_ball);
				redBallList2.get(i).setTag(0);
				redBallListT.get(i).setTextColor(redText);
				redBallListT1.get(i).setTextColor(redText);
				redBallListT2.get(i).setTextColor(redText);
			}
			selectedRedBalls.clear();
			selectedRedBalls1.clear();
			selectedRedBalls2.clear();
			typeName.setText("快乐十分-选3前直");
			dtView.setVisibility(View.GONE);
			normalView.setVisibility(View.VISIBLE);
			normalView1.setVisibility(View.VISIBLE);
			normalView2.setVisibility(View.VISIBLE);
			break;
		}

		HappyTenLott inbet = (HappyTenLott) mIntent
				.getSerializableExtra("editBet");
		if (null != inbet) {
			Constant.HAPPY_PLAY_TYPE = inbet.getPlayType();

			switch (inbet.getPlayType()) {
			case 0:
				typeName.setText("快乐十分-任选1");
				break;
			case 1:
				typeName.setText("快乐十分-任选2");
				break;
			case 2:
				typeName.setText("快乐十分-任选3");
				break;
			case 3:
				typeName.setText("快乐十分-任选4");
				break;
			case 4:
				typeName.setText("快乐十分-任选5");
				break;
			case 5:
				typeName.setText("快乐十分-选2连组");
				break;
			case 6:
				typeName.setText("快乐十分-选3胆拖");
				break;
			case 7:
				typeName.setText("快乐十分-选2连直");
				break;
			case 8:
				typeName.setText("快乐十分-选3前组");
				break;
			case 9:
				typeName.setText("快乐十分-选3前直");
				break;
			}
			Log.e("pkx", "inbet：" + inbet.getPlayType());
			if (0 == inbet.getBetType()) {
				for (int b : inbet.getRedBalls()) {
					redBallList.get(b - 1).setTag(1);
					redBallList.get(b - 1).setImageResource(
							R.drawable.black_ball);
					selectedRedBalls.add(redBallList.get(b - 1));
					redBallListT.get(b - 1).setTextColor(whiteText);
				}
				// paperSeven.setCurrentItem(0);
			} else {
				Log.e("pkx", "intbet type:" + inbet.getPlayType());
				if (6 == inbet.getPlayType()) {
					selectedDredBallList.clear();
					selectedTredBallList.clear();
					// paperSeven.setCurrentItem(1);
					for (int d : inbet.getDanBalls()) {
						dRedBallList.get(d - 1).setTag(1);
						dRedBallList.get(d - 1).setImageResource(
								R.drawable.black_ball);
						selectedDredBallList.add(dRedBallList.get(d - 1));
						dRedBallListT.get(d - 1).setTextColor(whiteText);
					}
					for (int t : inbet.getTuoBalls()) {
						tRedBallList.get(t - 1).setTag(1);
						tRedBallList.get(t - 1).setImageResource(
								R.drawable.black_ball);
						selectedTredBallList.add(tRedBallList.get(t - 1));
						tRedBallListT.get(t - 1).setTextColor(whiteText);
					}
				} else {
					// 新增玩法
					switch (inbet.getPlayType()) {
					case 5:
						for (int b : inbet.getRedBalls()) {
							redBallList.get(b - 1).setTag(1);
							redBallList.get(b - 1).setImageResource(
									R.drawable.black_ball);
							selectedRedBalls.add(redBallList.get(b - 1));
							redBallListT.get(b - 1).setTextColor(whiteText);
						}
						break;
					case 7:
						for (int b : inbet.getRedBalls()) {
							redBallList.get(b - 1).setTag(1);
							redBallList.get(b - 1).setImageResource(
									R.drawable.black_ball);
							selectedRedBalls.add(redBallList.get(b - 1));
							redBallListT.get(b - 1).setTextColor(whiteText);
						}
						for (int b : inbet.getRedBalls1()) {
							redBallList1.get(b - 1).setTag(1);
							redBallList1.get(b - 1).setImageResource(
									R.drawable.black_ball);
							selectedRedBalls1.add(redBallList1.get(b - 1));
							redBallListT1.get(b - 1).setTextColor(whiteText);
						}
						// ArrayList<Integer> redBalls0 = new
						// ArrayList<Integer>();
						// for (ImageView sv : selectedRedBalls) {
						// redBalls0.add((Integer) sv.getTag(red2.getId()));
						// }
						// happyLott.setRedBalls(redBalls0);
						// ArrayList<Integer> redBalls11 = new
						// ArrayList<Integer>();
						// for (ImageView sv : selectedRedBalls1) {
						// redBalls11.add((Integer) sv.getTag(red2.getId()));
						// }
						// happyLott.setRedBalls1(redBalls11);
						break;
					case 8:
						for (int b : inbet.getRedBalls()) {
							redBallList.get(b - 1).setTag(1);
							redBallList.get(b - 1).setImageResource(
									R.drawable.black_ball);
							selectedRedBalls.add(redBallList.get(b - 1));
							redBallListT.get(b - 1).setTextColor(whiteText);
						}
						break;
					case 9:

						for (int b : inbet.getRedBalls()) {
							redBallList.get(b - 1).setTag(1);
							redBallList.get(b - 1).setImageResource(
									R.drawable.black_ball);
							selectedRedBalls.add(redBallList.get(b - 1));
							redBallListT.get(b - 1).setTextColor(whiteText);
						}
						for (int b : inbet.getRedBalls1()) {
							redBallList1.get(b - 1).setTag(1);
							redBallList1.get(b - 1).setImageResource(
									R.drawable.black_ball);
							selectedRedBalls1.add(redBallList1.get(b - 1));
							redBallListT1.get(b - 1).setTextColor(whiteText);
						}
						for (int b : inbet.getRedBalls2()) {
							redBallList2.get(b - 1).setTag(1);
							redBallList2.get(b - 1).setImageResource(
									R.drawable.black_ball);
							selectedRedBalls2.add(redBallList2.get(b - 1));
							redBallListT2.get(b - 1).setTextColor(whiteText);
						}
						// ArrayList<Integer> redBalls1 = new
						// ArrayList<Integer>();
						// for (ImageView sv : selectedRedBalls) {
						// redBalls1.add((Integer) sv.getTag(red2.getId()));
						// }
						// ArrayList<Integer> redBalls111 = new
						// ArrayList<Integer>();
						// for (ImageView sv : selectedRedBalls1) {
						// redBalls111.add((Integer) sv.getTag(red2.getId()));
						// }
						// ArrayList<Integer> redBalls2 = new
						// ArrayList<Integer>();
						// for (ImageView sv : selectedRedBalls2) {
						// redBalls2.add((Integer) sv.getTag(red2.getId()));
						// }
						// happyLott.setRedBalls1(redBalls111);
						// happyLott.setRedBalls(redBalls1);
						// happyLott.setRedBalls2(redBalls2);
						break;

					}
				}
			}
			checkBill();
		}
		mShakeListener = new ShakeListener(this);
		mShakeListener.setOnShakeListener(new OnShakeListener() {
			@Override
			public void onShake() {
				// if (currentPage == 1)
				// return;
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
				boolean exsist = false;
				if (isStraight2) {

					for (ImageView srt : selectedRedBalls1) {
						if (srt.getTag(red2.getId()) == v.getTag(red2.getId())) {
							exsist = true;
							break;
						}
					}

				} else if (isStraight3) {
					for (ImageView srt : selectedRedBalls1) {
						if (srt.getTag(red2.getId()) == v.getTag(red2.getId())) {
							exsist = true;
							break;
						}
					}
					if (!exsist) {
						for (ImageView srt : selectedRedBalls2) {
							if (srt.getTag(red2.getId()) == v.getTag(red2
									.getId())) {
								exsist = true;
								break;
							}
						}
					}

				}
				if (exsist) {
					Toast.makeText(HappyTenLottery.this, "直选球已存在！",
							Toast.LENGTH_SHORT).show();
					return;

				} else {
					if (selectedRedBalls.size() > selectNum - 1) {
						Toast.makeText(this,
								"最多选择" + String.valueOf(selectNum) + "个球",
								Toast.LENGTH_LONG).show();
						return;
					}
					((ImageView) v).setImageResource(R.drawable.black_ball);
					v.setTag(1);
					redBallListT.get((Integer) v.getTag(red2.getId()) - 1)
							.setTextColor(whiteText);
					selectedRedBalls.add((ImageView) v);
					startVibrato1();
				}
			} else {
				// 取消红球
				((ImageView) v).setImageResource(R.drawable.purple_ball);
				redBallListT.get((Integer) v.getTag(red2.getId()) - 1)
						.setTextColor(redText);
				v.setTag(0);
				selectedRedBalls.remove(v);
			}
			checkBill();
		} else if ("r1".equals(v.getTag(red1.getId()))) {
			// 点击红球
			boolean exsist = false;
			if (isStraight2) {

				for (ImageView srt : selectedRedBalls) {
					if (srt.getTag(red2.getId()) == v.getTag(red2.getId())) {
						exsist = true;
						break;
					}
				}

			} else if (isStraight3) {
				for (ImageView srt : selectedRedBalls) {
					if (srt.getTag(red2.getId()) == v.getTag(red2.getId())) {
						exsist = true;
						break;
					}
				}
				if (!exsist) {
					for (ImageView srt : selectedRedBalls2) {
						if (srt.getTag(red2.getId()) == v.getTag(red2.getId())) {
							exsist = true;
							break;
						}
					}
				}

			}
			if (exsist) {
				Toast.makeText(HappyTenLottery.this, "直选球已存在！",
						Toast.LENGTH_SHORT).show();
				return;

			} else {
				if (v.getTag().equals(0)) {
					// 选中红球
					if (selectedRedBalls1.size() > selectNum - 1) {
						Toast.makeText(this,
								"最多选择" + String.valueOf(selectNum) + "个球",
								Toast.LENGTH_LONG).show();
						return;
					}
					((ImageView) v).setImageResource(R.drawable.black_ball);
					v.setTag(1);
					redBallListT1.get((Integer) v.getTag(red2.getId()) - 1)
							.setTextColor(whiteText);
					selectedRedBalls1.add((ImageView) v);
					startVibrato1();
				} else {
					// 取消红球
					((ImageView) v).setImageResource(R.drawable.purple_ball);
					redBallListT1.get((Integer) v.getTag(red2.getId()) - 1)
							.setTextColor(redText);
					v.setTag(0);
					selectedRedBalls1.remove(v);
				}
			}
			checkBill();

		} else if ("r2".equals(v.getTag(red1.getId()))) {
			// 点击红球
			boolean exsist = false;
			if (isStraight3) {
				for (ImageView srt : selectedRedBalls) {
					if (srt.getTag(red2.getId()) == v.getTag(red2.getId())) {
						exsist = true;
						break;
					}
				}
				if (!exsist) {
					for (ImageView srt : selectedRedBalls1) {
						if (srt.getTag(red2.getId()) == v.getTag(red2.getId())) {
							exsist = true;
							break;
						}
					}
				}

			}
			if (exsist) {
				Toast.makeText(HappyTenLottery.this, "直选球已存在！",
						Toast.LENGTH_SHORT).show();
				return;
			}
			{
				if (v.getTag().equals(0)) {
					// 选中红球
					if (selectedRedBalls2.size() > selectNum - 1) {
						Toast.makeText(this,
								"最多选择" + String.valueOf(selectNum) + "个球",
								Toast.LENGTH_LONG).show();
						return;
					}
					((ImageView) v).setImageResource(R.drawable.black_ball);
					v.setTag(1);
					redBallListT2.get((Integer) v.getTag(red2.getId()) - 1)
							.setTextColor(whiteText);
					selectedRedBalls2.add((ImageView) v);
					startVibrato1();
				} else {
					// 取消红球
					((ImageView) v).setImageResource(R.drawable.purple_ball);
					redBallListT2.get((Integer) v.getTag(red2.getId()) - 1)
							.setTextColor(redText);
					v.setTag(0);
					selectedRedBalls2.remove(v);
				}
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
					if (selectedDredBallList.size() == selectDanNum) {
						Toast.makeText(
								this,
								"最多只能选择" + String.valueOf(selectDanNum)
										+ "个胆红球", Toast.LENGTH_SHORT).show();
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
					// if (selectedTredBallList.size() == 18) {
					// Toast.makeText(this, "最多只能选择18个拖红球", Toast.LENGTH_SHORT)
					// .show();
					// return;
					// }
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

			switch (Constant.HAPPY_PLAY_TYPE) {
			case 0:
			case 1:
				for (TextView t : redBallListT) {
					t.setTextColor(redText);
				}
				for (ImageView i : redBallList) {
					i.setImageResource(R.drawable.purple_ball);
					i.setTag(0);
				}
				selectedRedBalls.clear();
				// checkBill();
				break;
			case 2:
				for (TextView t : redBallListT) {
					t.setTextColor(redText);
				}
				for (ImageView i : redBallList) {
					i.setImageResource(R.drawable.purple_ball);
					i.setTag(0);
				}
				selectedRedBalls.clear();
				// checkBill();
				break;
			case 3:
				for (TextView t : redBallListT) {
					t.setTextColor(redText);
				}
				for (ImageView i : redBallList) {
					i.setImageResource(R.drawable.purple_ball);
					i.setTag(0);
				}
				selectedRedBalls.clear();
				// checkBill();
				break;
			case 4:
				for (TextView t : redBallListT) {
					t.setTextColor(redText);
				}
				for (ImageView i : redBallList) {
					i.setImageResource(R.drawable.purple_ball);
					i.setTag(0);
				}
				selectedRedBalls.clear();
				// checkBill();
				break;
			case 5:
				for (TextView t : redBallListT) {
					t.setTextColor(redText);
				}
				for (ImageView i : redBallList) {
					i.setImageResource(R.drawable.purple_ball);
					i.setTag(0);
				}
				selectedRedBalls.clear();
				// checkBill();
				break;
			case 6:
				for (int i = 0; i < dRedBallListT.size(); i++) {
					dRedBallListT.get(i).setTextColor(redText);
					tRedBallListT.get(i).setTextColor(redText);
					tRedBallList.get(i)
							.setImageResource(R.drawable.purple_ball);
					tRedBallList.get(i).setTag(0);
					dRedBallList.get(i)
							.setImageResource(R.drawable.purple_ball);
					dRedBallList.get(i).setTag(0);

				}
				selectedDredBallList.clear();
				selectedTredBallList.clear();
				// checkBill();
				break;
			case 7:
				for (int i = 0; i < redBallList.size(); i++) {
					redBallList.get(i).setImageResource(R.drawable.purple_ball);
					redBallList.get(i).setTag(0);
					redBallList1.get(i)
							.setImageResource(R.drawable.purple_ball);
					redBallList1.get(i).setTag(0);
					redBallListT.get(i).setTextColor(redText);
					redBallListT1.get(i).setTextColor(redText);
				}
				selectedRedBalls.clear();
				selectedRedBalls1.clear();
				break;
			case 8:
				for (TextView t : redBallListT) {
					t.setTextColor(redText);
				}
				for (ImageView i : redBallList) {
					i.setImageResource(R.drawable.purple_ball);
					i.setTag(0);
				}
				selectedRedBalls.clear();
				// checkBill();
				break;
			case 9:
				for (int i = 0; i < redBallList.size(); i++) {
					redBallList.get(i).setImageResource(R.drawable.purple_ball);
					redBallList.get(i).setTag(0);
					redBallList1.get(i)
							.setImageResource(R.drawable.purple_ball);
					redBallList1.get(i).setTag(0);
					redBallList2.get(i)
							.setImageResource(R.drawable.purple_ball);
					redBallList2.get(i).setTag(0);
					redBallListT.get(i).setTextColor(redText);
					redBallListT1.get(i).setTextColor(redText);
					redBallListT2.get(i).setTextColor(redText);
				}
				selectedRedBalls.clear();
				selectedRedBalls1.clear();
				selectedRedBalls2.clear();
				break;
			}
			checkBill();
			// if (Constant.HAPPY_PLAY_TYPE == 0) {
			// for (TextView t : redBallListT) {
			// t.setTextColor(redText);
			// }
			// for (ImageView i : redBallList) {
			// i.setImageResource(R.drawable.purple_ball);
			// i.setTag(0);
			// }
			// selectedRedBalls.clear();
			// checkBill();
			// } else {
			// for (TextView t : dRedBallListT) {
			// t.setTextColor(redText);
			// }
			// for (TextView t : tRedBallListT) {
			// t.setTextColor(redText);
			// }
			// for (ImageView i : tRedBallList) {
			// i.setImageResource(R.drawable.purple_ball);
			// i.setTag(0);
			// }
			// for (ImageView i : dRedBallList) {
			// i.setImageResource(R.drawable.purple_ball);
			// i.setTag(0);
			// }
			// selectedDredBallList.clear();
			// selectedTredBallList.clear();
			// checkBill();
			//
			// }
			break;
		case R.id.handleBet:
			if (checkBill() == 0) {
				Toast.makeText(this, "至少选择一注", Toast.LENGTH_SHORT).show();
				return;
			}
			happyLott = new HappyTenLott();
			happyLott.setPlayType(Constant.HAPPY_PLAY_TYPE);
			if (happyLott.getBetType() == 0) {
				boolean has1819 = false;
				ArrayList<Integer> redBalls = new ArrayList<Integer>();
				for (ImageView sv : selectedRedBalls) {
					int s = (Integer) sv.getTag(red2.getId());
					if (s == 20 || s == 19)
						has1819 = true;
					redBalls.add(s);
				}
				if (redBalls.size() > 1 && has1819
						&& Constant.HAPPY_PLAY_TYPE == 0) {
					Toast.makeText(HappyTenLottery.this, "选一复式可选号码为1~18！",
							Toast.LENGTH_SHORT).show();
					return;
				}
				happyLott.setRedBalls(redBalls);
				// 普通
			} else {
				// 胆拖
				if (Constant.HAPPY_PLAY_TYPE == 6) {
					ArrayList<Integer> dredBalls = new ArrayList<Integer>();
					for (ImageView sv : selectedDredBallList) {
						dredBalls.add((Integer) sv.getTag(red2.getId()));
					}
					ArrayList<Integer> tredBalls = new ArrayList<Integer>();
					for (ImageView sv : selectedTredBallList) {
						tredBalls.add((Integer) sv.getTag(red2.getId()));
					}
					happyLott.setDanBalls(dredBalls);
					happyLott.setTuoBalls(tredBalls);
				} else {
					switch (Constant.HAPPY_PLAY_TYPE) {
					case 5:
						ArrayList<Integer> redBalls = new ArrayList<Integer>();
						for (ImageView sv : selectedRedBalls) {
							redBalls.add((Integer) sv.getTag(red2.getId()));
						}
						happyLott.setRedBalls(redBalls);
						break;
					case 7:
						ArrayList<Integer> redBalls0 = new ArrayList<Integer>();
						for (ImageView sv : selectedRedBalls) {
							redBalls0.add((Integer) sv.getTag(red2.getId()));
						}
						happyLott.setRedBalls(redBalls0);
						ArrayList<Integer> redBalls11 = new ArrayList<Integer>();
						for (ImageView sv : selectedRedBalls1) {
							redBalls11.add((Integer) sv.getTag(red2.getId()));
						}
						happyLott.setRedBalls1(redBalls11);
						break;
					case 8:
						ArrayList<Integer> redBalls5 = new ArrayList<Integer>();
						for (ImageView sv : selectedRedBalls) {
							redBalls5.add((Integer) sv.getTag(red2.getId()));
						}
						happyLott.setRedBalls(redBalls5);
						break;
					case 9:

						ArrayList<Integer> redBalls1 = new ArrayList<Integer>();
						for (ImageView sv : selectedRedBalls) {
							redBalls1.add((Integer) sv.getTag(red2.getId()));
						}
						ArrayList<Integer> redBalls111 = new ArrayList<Integer>();
						for (ImageView sv : selectedRedBalls1) {
							redBalls111.add((Integer) sv.getTag(red2.getId()));
						}
						ArrayList<Integer> redBalls2 = new ArrayList<Integer>();
						for (ImageView sv : selectedRedBalls2) {
							redBalls2.add((Integer) sv.getTag(red2.getId()));
						}
						happyLott.setRedBalls1(redBalls111);
						happyLott.setRedBalls(redBalls1);
						happyLott.setRedBalls2(redBalls2);
						break;

					}
				}
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
			betBundle.putSerializable("sbet", happyLott);
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
				Intent toCheckPage = new Intent(this, HappyLotteryCheck.class);
				toCheckPage.putExtras(betBundle);
				startActivity(toCheckPage);
				finish();
			}

			break;

		}

	}

	private int checkBill() {
		int result = 0;
		switch (Constant.HAPPY_PLAY_TYPE) {
		case 0:// 选1
			if (selectedRedBalls.size() > 0) {
				result = selectedRedBalls.size() * 2;
			} else {
				result = 0;
			}
			break;
		case 1:// 选2
			if (selectedRedBalls.size() > 1) {
				result = RandomBallsUtils.getBallBets(2,
						selectedRedBalls.size()) * 2;
			} else {
				result = 0;
			}
			break;
		case 2:// 选3
			if (selectedRedBalls.size() > 2) {
				result = RandomBallsUtils.getBallBets(3,
						selectedRedBalls.size()) * 2;
			} else {
				result = 0;
			}
			break;
		case 3:// 选4
			if (selectedRedBalls.size() > 3) {
				result = RandomBallsUtils.getBallBets(4,
						selectedRedBalls.size()) * 2;
			} else {
				result = 0;
			}
			break;
		case 4:// 选5
			if (selectedRedBalls.size() > 4) {
				result = RandomBallsUtils.getBallBets(5,
						selectedRedBalls.size()) * 2;
			} else {
				result = 0;
			}
			break;
		case 5:// 选2连组
			if (selectedRedBalls.size() > 0) {
				result = RandomBallsUtils.getBallBets(2,
						selectedRedBalls.size()) * 2;
			} else {
				result = 0;
			}
			break;
		case 6:// 选3胆拖
			if (selectedDredBallList.size() > 0
					&& selectedTredBallList.size() > 0
					&& (selectedDredBallList.size() + selectedTredBallList
							.size()) > 3) {
				result = RandomBallsUtils.getBallBets(
						(3 - selectedDredBallList.size()),
						selectedTredBallList.size()) * 2;
			} else {
				result = 0;
			}
			break;
		case 7:// 选2连直
			if (selectedRedBalls.size() > 0 && selectedRedBalls1.size() > 0) {
				result = selectedRedBalls.size() * selectedRedBalls1.size() * 2;
			} else {
				result = 0;
			}
			break;
		case 8:// 选3前组
			if (selectedRedBalls.size() > 0) {
				result = RandomBallsUtils.getBallBets(3,
						selectedRedBalls.size()) * 2;
			} else {
				result = 0;
			}
			break;
		case 9:// 选3前直
			if (selectedRedBalls.size() > 0 && selectedRedBalls1.size() > 0
					&& selectedRedBalls2.size() > 0) {
				result = selectedRedBalls.size() * selectedRedBalls1.size()
						* selectedRedBalls2.size() * 2;
			} else {
				result = 0;
			}
			break;
		}
		// if (currentPage == 0) {
		// if (selectedRedBalls.size() > 6) {
		// int bets = RandomBallsUtils.getBallBets(7,
		// selectedRedBalls.size());
		// billText.setText("共" + bets + "注 " + bets * 2 + "元");
		// result = bets * 2;
		// } else {
		// billText.setText("0");
		// }
		// selectText.setText("已选" + selectedRedBalls.size() + "个，至少选择七个");
		// } else {
		// if (selectedDredBallList.size() > 0
		// && (selectedDredBallList.size() + selectedTredBallList
		// .size()) > 7) {
		// int bets = RandomBallsUtils.getBallBets(
		// 7 - selectedDredBallList.size(),
		// selectedTredBallList.size());
		if (result == 0) {
			billText.setText("0");
		} else {
			billText.setText("共" + result / 2 + "注 " + result + "元");
			// result = bets * 2;
			// } else {
			// billText.setText("0");
			// }
			// }
		}
		return result;
	}

	private void initViews() {
		sutil = new SharePreferenceUtil(this);
		frontView = findViewById(R.id.frontView);
		tenMinute = (TextView) findViewById(R.id.tenMinute);
		minute = (TextView) findViewById(R.id.minute);
		sec1 = (TextView) findViewById(R.id.sec1);
		sec2 = (TextView) findViewById(R.id.sec2);
		minute1 = (TextView) findViewById(R.id.minute1);
		sec11 = (TextView) findViewById(R.id.sec11);
		sec21 = (TextView) findViewById(R.id.sec21);
		clearView = findViewById(R.id.clearView);
		clearView.setOnClickListener(this);
		typeName = (TextView) findViewById(R.id.typeName);
		shakeView = findViewById(R.id.shakeView);
		playTypeView = findViewById(R.id.playTypeView);
		normalView = findViewById(R.id.normalView);
		normalView1 = findViewById(R.id.normalView1);
		normalView2 = findViewById(R.id.normalView2);
		dtView = findViewById(R.id.dtView);
		playTypeView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertLottTypeDialog();
			}
		});
		alertLott = new AlertDialog.Builder(this).create();
		// paperSeven = (ViewPager) findViewById(R.id.paperSeven);
		// paperSeven.setOnPageChangeListener(new OnPageChangeListener() {
		//
		// @Override
		// public void onPageSelected(int arg0) {
		// Message msg = new Message();
		// msg.what = Constant.PAGE_CHANGED;
		// msg.arg1 = arg0;
		// mHandler.sendMessage(msg);
		// }
		//
		// @Override
		// public void onPageScrolled(int arg0, float arg1, int arg2) {
		//
		// }
		//
		// @Override
		// public void onPageScrollStateChanged(int arg0) {
		//
		// }
		// });
		mHandler = new MyHandler();
		mVibretor = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		mIntent = getIntent();
		text1 = (TextView) findViewById(R.id.text1);
		text2 = (TextView) findViewById(R.id.text2);
		text3 = (TextView) findViewById(R.id.text3);
		handleBet = findViewById(R.id.handleBet);
		handleBet.setOnClickListener(this);
		redBallList = new ArrayList<ImageView>();
		redBallListT = new ArrayList<TextView>();
		redBallList1 = new ArrayList<ImageView>();
		redBallListT1 = new ArrayList<TextView>();
		redBallList2 = new ArrayList<ImageView>();
		redBallListT2 = new ArrayList<TextView>();
		selectedRedBalls = new ArrayList<ImageView>();
		selectedRedBalls1 = new ArrayList<ImageView>();
		selectedRedBalls2 = new ArrayList<ImageView>();
		selectedDredBallList = new ArrayList<ImageView>();
		selectedTredBallList = new ArrayList<ImageView>();
		ballsText = (TextView) findViewById(R.id.ballsText);
		billText = (TextView) findViewById(R.id.billText);
		selectText = (TextView) findViewById(R.id.selectText);
		lred1 = (ImageView) findViewById(R.id.lnred1);
		lred2 = (ImageView) findViewById(R.id.lnred2);
		lred3 = (ImageView) findViewById(R.id.lnred3);
		lred4 = (ImageView) findViewById(R.id.lnred4);
		lred5 = (ImageView) findViewById(R.id.lnred5);
		lred6 = (ImageView) findViewById(R.id.lnred6);
		lred7 = (ImageView) findViewById(R.id.lnred7);
		lred8 = (ImageView) findViewById(R.id.lnred8);
		lred9 = (ImageView) findViewById(R.id.lnred9);
		lred10 = (ImageView) findViewById(R.id.lnred10);
		lred11 = (ImageView) findViewById(R.id.lnred11);
		lred12 = (ImageView) findViewById(R.id.lnred12);
		lred13 = (ImageView) findViewById(R.id.lnred13);
		lred14 = (ImageView) findViewById(R.id.lnred14);
		lred15 = (ImageView) findViewById(R.id.lnred15);
		lred16 = (ImageView) findViewById(R.id.lnred16);
		lred17 = (ImageView) findViewById(R.id.lnred17);
		lred18 = (ImageView) findViewById(R.id.lnred18);
		lred19 = (ImageView) findViewById(R.id.lnred19);
		lred20 = (ImageView) findViewById(R.id.lnred20);
		llred1 = (ImageView) findViewById(R.id.llnred1);
		llred2 = (ImageView) findViewById(R.id.llnred2);
		llred3 = (ImageView) findViewById(R.id.llnred3);
		llred4 = (ImageView) findViewById(R.id.llnred4);
		llred5 = (ImageView) findViewById(R.id.llnred5);
		llred6 = (ImageView) findViewById(R.id.llnred6);
		llred7 = (ImageView) findViewById(R.id.llnred7);
		llred8 = (ImageView) findViewById(R.id.llnred8);
		llred9 = (ImageView) findViewById(R.id.llnred9);
		llred10 = (ImageView) findViewById(R.id.llnred10);
		llred11 = (ImageView) findViewById(R.id.llnred11);
		llred12 = (ImageView) findViewById(R.id.llnred12);
		llred13 = (ImageView) findViewById(R.id.llnred13);
		llred14 = (ImageView) findViewById(R.id.llnred14);
		llred15 = (ImageView) findViewById(R.id.llnred15);
		llred16 = (ImageView) findViewById(R.id.llnred16);
		llred17 = (ImageView) findViewById(R.id.llnred17);
		llred18 = (ImageView) findViewById(R.id.llnred18);
		llred19 = (ImageView) findViewById(R.id.llnred19);
		llred20 = (ImageView) findViewById(R.id.llnred20);
		red1 = (ImageView) findViewById(R.id.nred1);
		red2 = (ImageView) findViewById(R.id.nred2);
		red3 = (ImageView) findViewById(R.id.nred3);
		red4 = (ImageView) findViewById(R.id.nred4);
		red5 = (ImageView) findViewById(R.id.nred5);
		red6 = (ImageView) findViewById(R.id.nred6);
		red7 = (ImageView) findViewById(R.id.nred7);
		red8 = (ImageView) findViewById(R.id.nred8);
		red9 = (ImageView) findViewById(R.id.nred9);
		red10 = (ImageView) findViewById(R.id.nred10);
		red11 = (ImageView) findViewById(R.id.nred11);
		red12 = (ImageView) findViewById(R.id.nred12);
		red13 = (ImageView) findViewById(R.id.nred13);
		red14 = (ImageView) findViewById(R.id.nred14);
		red15 = (ImageView) findViewById(R.id.nred15);
		red16 = (ImageView) findViewById(R.id.nred16);
		red17 = (ImageView) findViewById(R.id.nred17);
		red18 = (ImageView) findViewById(R.id.nred18);
		red19 = (ImageView) findViewById(R.id.nred19);
		red20 = (ImageView) findViewById(R.id.nred20);

		ltred1 = (TextView) findViewById(R.id.lntred1);
		ltred2 = (TextView) findViewById(R.id.lntred2);
		ltred3 = (TextView) findViewById(R.id.lntred3);
		ltred4 = (TextView) findViewById(R.id.lntred4);
		ltred5 = (TextView) findViewById(R.id.lntred5);
		ltred6 = (TextView) findViewById(R.id.lntred6);
		ltred7 = (TextView) findViewById(R.id.lntred7);
		ltred8 = (TextView) findViewById(R.id.lntred8);
		ltred9 = (TextView) findViewById(R.id.lntred9);
		ltred10 = (TextView) findViewById(R.id.lntred10);
		ltred11 = (TextView) findViewById(R.id.lntred11);
		ltred12 = (TextView) findViewById(R.id.lntred12);
		ltred13 = (TextView) findViewById(R.id.lntred13);
		ltred14 = (TextView) findViewById(R.id.lntred14);
		ltred15 = (TextView) findViewById(R.id.lntred15);
		ltred16 = (TextView) findViewById(R.id.lntred16);
		ltred17 = (TextView) findViewById(R.id.lntred17);
		ltred18 = (TextView) findViewById(R.id.lntred18);
		ltred19 = (TextView) findViewById(R.id.lntred19);
		ltred20 = (TextView) findViewById(R.id.lntred20);
		lltred1 = (TextView) findViewById(R.id.llntred1);
		lltred2 = (TextView) findViewById(R.id.llntred2);
		lltred3 = (TextView) findViewById(R.id.llntred3);
		lltred4 = (TextView) findViewById(R.id.llntred4);
		lltred5 = (TextView) findViewById(R.id.llntred5);
		lltred6 = (TextView) findViewById(R.id.llntred6);
		lltred7 = (TextView) findViewById(R.id.llntred7);
		lltred8 = (TextView) findViewById(R.id.llntred8);
		lltred9 = (TextView) findViewById(R.id.llntred9);
		lltred10 = (TextView) findViewById(R.id.llntred10);
		lltred11 = (TextView) findViewById(R.id.llntred11);
		lltred12 = (TextView) findViewById(R.id.llntred12);
		lltred13 = (TextView) findViewById(R.id.llntred13);
		lltred14 = (TextView) findViewById(R.id.llntred14);
		lltred15 = (TextView) findViewById(R.id.llntred15);
		lltred16 = (TextView) findViewById(R.id.llntred16);
		lltred17 = (TextView) findViewById(R.id.llntred17);
		lltred18 = (TextView) findViewById(R.id.llntred18);
		lltred19 = (TextView) findViewById(R.id.llntred19);
		lltred20 = (TextView) findViewById(R.id.llntred20);
		tred1 = (TextView) findViewById(R.id.ntred1);
		tred2 = (TextView) findViewById(R.id.ntred2);
		tred3 = (TextView) findViewById(R.id.ntred3);
		tred4 = (TextView) findViewById(R.id.ntred4);
		tred5 = (TextView) findViewById(R.id.ntred5);
		tred6 = (TextView) findViewById(R.id.ntred6);
		tred7 = (TextView) findViewById(R.id.ntred7);
		tred8 = (TextView) findViewById(R.id.ntred8);
		tred9 = (TextView) findViewById(R.id.ntred9);
		tred10 = (TextView) findViewById(R.id.ntred10);
		tred11 = (TextView) findViewById(R.id.ntred11);
		tred12 = (TextView) findViewById(R.id.ntred12);
		tred13 = (TextView) findViewById(R.id.ntred13);
		tred14 = (TextView) findViewById(R.id.ntred14);
		tred15 = (TextView) findViewById(R.id.ntred15);
		tred16 = (TextView) findViewById(R.id.ntred16);
		tred17 = (TextView) findViewById(R.id.ntred17);
		tred18 = (TextView) findViewById(R.id.ntred18);
		tred19 = (TextView) findViewById(R.id.ntred19);
		tred20 = (TextView) findViewById(R.id.ntred20);

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

		redBallList2.add(llred1);
		redBallList2.add(llred2);
		redBallList2.add(llred3);
		redBallList2.add(llred4);
		redBallList2.add(llred5);
		redBallList2.add(llred6);
		redBallList2.add(llred7);
		redBallList2.add(llred8);
		redBallList2.add(llred9);
		redBallList2.add(llred10);
		redBallList2.add(llred11);
		redBallList2.add(llred12);
		redBallList2.add(llred13);
		redBallList2.add(llred14);
		redBallList2.add(llred15);
		redBallList2.add(llred16);
		redBallList2.add(llred17);
		redBallList2.add(llred18);
		redBallList2.add(llred19);
		redBallList2.add(llred20);

		redBallList1.add(lred1);
		redBallList1.add(lred2);
		redBallList1.add(lred3);
		redBallList1.add(lred4);
		redBallList1.add(lred5);
		redBallList1.add(lred6);
		redBallList1.add(lred7);
		redBallList1.add(lred8);
		redBallList1.add(lred9);
		redBallList1.add(lred10);
		redBallList1.add(lred11);
		redBallList1.add(lred12);
		redBallList1.add(lred13);
		redBallList1.add(lred14);
		redBallList1.add(lred15);
		redBallList1.add(lred16);
		redBallList1.add(lred17);
		redBallList1.add(lred18);
		redBallList1.add(lred19);
		redBallList1.add(lred20);

		redBallListT2.add(lltred1);
		redBallListT2.add(lltred2);
		redBallListT2.add(lltred3);
		redBallListT2.add(lltred4);
		redBallListT2.add(lltred5);
		redBallListT2.add(lltred6);
		redBallListT2.add(lltred7);
		redBallListT2.add(lltred8);
		redBallListT2.add(lltred9);
		redBallListT2.add(lltred10);
		redBallListT2.add(lltred11);
		redBallListT2.add(lltred12);
		redBallListT2.add(lltred13);
		redBallListT2.add(lltred14);
		redBallListT2.add(lltred15);
		redBallListT2.add(lltred16);
		redBallListT2.add(lltred17);
		redBallListT2.add(lltred18);
		redBallListT2.add(lltred19);
		redBallListT2.add(lltred20);

		redBallListT1.add(ltred1);
		redBallListT1.add(ltred2);
		redBallListT1.add(ltred3);
		redBallListT1.add(ltred4);
		redBallListT1.add(ltred5);
		redBallListT1.add(ltred6);
		redBallListT1.add(ltred7);
		redBallListT1.add(ltred8);
		redBallListT1.add(ltred9);
		redBallListT1.add(ltred10);
		redBallListT1.add(ltred11);
		redBallListT1.add(ltred12);
		redBallListT1.add(ltred13);
		redBallListT1.add(ltred14);
		redBallListT1.add(ltred15);
		redBallListT1.add(ltred16);
		redBallListT1.add(ltred17);
		redBallListT1.add(ltred18);
		redBallListT1.add(ltred19);
		redBallListT1.add(ltred20);

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

		for (int i = 0; i < redBallList.size(); i++) {
			redBallListT.get(i).setTextColor(redText);
			redBallList.get(i).setTag(0);
			redBallList.get(i).setTag(red1.getId(), "r");
			redBallList.get(i).setTag(red2.getId(), i + 1);
			redBallList.get(i).setOnClickListener(this);
		}
		for (int i = 0; i < redBallList1.size(); i++) {
			redBallListT1.get(i).setTextColor(redText);
			redBallList1.get(i).setTag(0);
			redBallList1.get(i).setTag(red1.getId(), "r1");
			redBallList1.get(i).setTag(red2.getId(), i + 1);
			redBallList1.get(i).setOnClickListener(this);
		}
		for (int i = 0; i < redBallList2.size(); i++) {
			redBallListT2.get(i).setTextColor(redText);
			redBallList2.get(i).setTag(0);
			redBallList2.get(i).setTag(red1.getId(), "r2");
			redBallList2.get(i).setTag(red2.getId(), i + 1);
			redBallList2.get(i).setOnClickListener(this);
		}
		red1d = (TextView) findViewById(R.id.dred1);
		red2d = (TextView) findViewById(R.id.dred2);
		red3d = (TextView) findViewById(R.id.dred3);
		red4d = (TextView) findViewById(R.id.dred4);
		red5d = (TextView) findViewById(R.id.dred5);
		red6d = (TextView) findViewById(R.id.dred6);
		red7d = (TextView) findViewById(R.id.dred7);
		red8d = (TextView) findViewById(R.id.dred8);
		red9d = (TextView) findViewById(R.id.dred9);
		red10d = (TextView) findViewById(R.id.dred10);
		red11d = (TextView) findViewById(R.id.dred11);
		red12d = (TextView) findViewById(R.id.dred12);
		red13d = (TextView) findViewById(R.id.dred13);
		red14d = (TextView) findViewById(R.id.dred14);
		red15d = (TextView) findViewById(R.id.dred15);
		red16d = (TextView) findViewById(R.id.dred16);
		red17d = (TextView) findViewById(R.id.dred17);
		red18d = (TextView) findViewById(R.id.dred18);
		red19d = (TextView) findViewById(R.id.dred19);
		red20d = (TextView) findViewById(R.id.dred20);

		red1t = (TextView) findViewById(R.id.dtred1);
		red2t = (TextView) findViewById(R.id.dtred2);
		red3t = (TextView) findViewById(R.id.dtred3);
		red4t = (TextView) findViewById(R.id.dtred4);
		red5t = (TextView) findViewById(R.id.dtred5);
		red6t = (TextView) findViewById(R.id.dtred6);
		red7t = (TextView) findViewById(R.id.dtred7);
		red8t = (TextView) findViewById(R.id.dtred8);
		red9t = (TextView) findViewById(R.id.dtred9);
		red10t = (TextView) findViewById(R.id.dtred10);
		red11t = (TextView) findViewById(R.id.dtred11);
		red12t = (TextView) findViewById(R.id.dtred12);
		red13t = (TextView) findViewById(R.id.dtred13);
		red14t = (TextView) findViewById(R.id.dtred14);
		red15t = (TextView) findViewById(R.id.dtred15);
		red16t = (TextView) findViewById(R.id.dtred16);
		red17t = (TextView) findViewById(R.id.dtred17);
		red18t = (TextView) findViewById(R.id.dtred18);
		red19t = (TextView) findViewById(R.id.dtred19);
		red20t = (TextView) findViewById(R.id.dtred20);

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

		for (int i = 0; i < dRedBallListT.size(); i++) {
			redBallListT.get(i).setTextColor(redText);
		}
		for (int i = 0; i < tRedBallListT.size(); i++) {
			tRedBallListT.get(i).setTextColor(redText);
		}
		dred1 = (ImageView) findViewById(R.id.red1);
		dred2 = (ImageView) findViewById(R.id.red2);
		dred3 = (ImageView) findViewById(R.id.red3);
		dred4 = (ImageView) findViewById(R.id.red4);
		dred5 = (ImageView) findViewById(R.id.red5);
		dred6 = (ImageView) findViewById(R.id.red6);
		dred7 = (ImageView) findViewById(R.id.red7);
		dred8 = (ImageView) findViewById(R.id.red8);
		dred9 = (ImageView) findViewById(R.id.red9);
		dred10 = (ImageView) findViewById(R.id.red10);
		dred11 = (ImageView) findViewById(R.id.red11);
		dred12 = (ImageView) findViewById(R.id.red12);
		dred13 = (ImageView) findViewById(R.id.red13);
		dred14 = (ImageView) findViewById(R.id.red14);
		dred15 = (ImageView) findViewById(R.id.red15);
		dred16 = (ImageView) findViewById(R.id.red16);
		dred17 = (ImageView) findViewById(R.id.red17);
		dred18 = (ImageView) findViewById(R.id.red18);
		dred19 = (ImageView) findViewById(R.id.red19);
		dred20 = (ImageView) findViewById(R.id.red20);

		tred1v = (ImageView) findViewById(R.id.tred1);
		tred2v = (ImageView) findViewById(R.id.tred2);
		tred3v = (ImageView) findViewById(R.id.tred3);
		tred4v = (ImageView) findViewById(R.id.tred4);
		tred5v = (ImageView) findViewById(R.id.tred5);
		tred6v = (ImageView) findViewById(R.id.tred6);
		tred7v = (ImageView) findViewById(R.id.tred7);
		tred8v = (ImageView) findViewById(R.id.tred8);
		tred9v = (ImageView) findViewById(R.id.tred9);
		tred10v = (ImageView) findViewById(R.id.tred10);
		tred11v = (ImageView) findViewById(R.id.tred11);
		tred12v = (ImageView) findViewById(R.id.tred12);
		tred13v = (ImageView) findViewById(R.id.tred13);
		tred14v = (ImageView) findViewById(R.id.tred14);
		tred15v = (ImageView) findViewById(R.id.tred15);
		tred16v = (ImageView) findViewById(R.id.tred16);
		tred17v = (ImageView) findViewById(R.id.tred17);
		tred18v = (ImageView) findViewById(R.id.tred18);
		tred19v = (ImageView) findViewById(R.id.tred19);
		tred20v = (ImageView) findViewById(R.id.tred20);

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

	private void alertLottTypeDialog() {
		if (alertLott.isShowing()) {
			alertLott.dismiss();
			return;
		}
		int whiteText = Color.parseColor("#ffffff");
		alertLott.show();
		alertLott.getWindow().setContentView(R.layout.happy_play_type_dialog);
		View n_type2, n_type3, n_type4, n_type5, d_type2, d_type3, d_type4, d_type5, d_type6, d_type1;
		TextView n_type2_text, n_type3_text, n_type4_text, n_type5_text, d_type2_text, d_type3_text, d_type4_text, d_type5_text, d_type6_text, d_type1_text;
		d_type1 = alertLott.findViewById(R.id.d_type1);
		d_type1.setTag(0);
		n_type2 = alertLott.findViewById(R.id.n_type2);
		n_type2.setTag(1);
		n_type3 = alertLott.findViewById(R.id.n_type3);
		n_type3.setTag(2);
		n_type4 = alertLott.findViewById(R.id.n_type4);
		n_type4.setTag(3);
		n_type5 = alertLott.findViewById(R.id.n_type5);
		n_type5.setTag(4);
		d_type2 = alertLott.findViewById(R.id.d_type2);
		d_type2.setTag(5);
		d_type3 = alertLott.findViewById(R.id.d_type3);
		d_type3.setTag(6);
		d_type4 = alertLott.findViewById(R.id.d_type4);
		d_type4.setTag(7);
		d_type5 = alertLott.findViewById(R.id.d_type5);
		d_type5.setTag(8);
		d_type6 = alertLott.findViewById(R.id.d_type6);
		d_type6.setTag(9);
		d_type1_text = (TextView) alertLott.findViewById(R.id.d_type1_text);
		n_type2_text = (TextView) alertLott.findViewById(R.id.n_type2_text);
		n_type3_text = (TextView) alertLott.findViewById(R.id.n_type3_text);
		n_type4_text = (TextView) alertLott.findViewById(R.id.n_type4_text);
		n_type5_text = (TextView) alertLott.findViewById(R.id.n_type5_text);
		d_type2_text = (TextView) alertLott.findViewById(R.id.d_type2_text);
		d_type3_text = (TextView) alertLott.findViewById(R.id.d_type3_text);
		d_type4_text = (TextView) alertLott.findViewById(R.id.d_type4_text);
		d_type5_text = (TextView) alertLott.findViewById(R.id.d_type5_text);
		d_type6_text = (TextView) alertLott.findViewById(R.id.d_type6_text);
		View bottomView = alertLott.findViewById(R.id.bottomView);
		bottomView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertLott.dismiss();
			}
		});
		switch (Constant.HAPPY_PLAY_TYPE) {
		case 0:
			d_type1_text.setTextColor(whiteText);
			d_type1.setBackgroundResource(R.drawable.pink);
			break;
		case 1:
			n_type2_text.setTextColor(whiteText);
			n_type2.setBackgroundResource(R.drawable.pink);
			break;
		case 2:
			n_type3_text.setTextColor(whiteText);
			n_type3.setBackgroundResource(R.drawable.pink);
			break;
		case 3:
			n_type4_text.setTextColor(whiteText);
			n_type4.setBackgroundResource(R.drawable.pink);
			break;
		case 4:
			n_type5_text.setTextColor(whiteText);
			n_type5.setBackgroundResource(R.drawable.pink);
			break;
		case 5:
			d_type2_text.setTextColor(whiteText);
			d_type2.setBackgroundResource(R.drawable.pink);
			break;
		case 6:
			d_type3_text.setTextColor(whiteText);
			d_type3.setBackgroundResource(R.drawable.pink);
			break;
		case 7:
			d_type4_text.setTextColor(whiteText);
			d_type4.setBackgroundResource(R.drawable.pink);
			break;
		case 8:
			d_type5_text.setTextColor(whiteText);
			d_type5.setBackgroundResource(R.drawable.pink);
			break;
		case 9:
			d_type6_text.setTextColor(whiteText);
			d_type6.setBackgroundResource(R.drawable.pink);
			break;
		}
		OnClickListener closeDialog = new OnClickListener() {

			@Override
			public void onClick(View v) {
				Constant.HAPPY_PLAY_TYPE = (Integer) v.getTag();
				setSelectNum();
				// if ((Integer) v.getTag() < 5) {
				// for (TextView t : redBallListT) {
				// t.setTextColor(redText);
				// }
				// for (ImageView i : redBallList) {
				// i.setImageResource(R.drawable.purple_ball);
				// i.setTag(0);
				// }
				// selectedRedBalls.clear();
				// checkBill();
				// } else {
				// for (TextView t : dRedBallListT) {
				// t.setTextColor(redText);
				// }
				// for (TextView t : tRedBallListT) {
				// t.setTextColor(redText);
				// }
				// for (ImageView i : tRedBallList) {
				// i.setImageResource(R.drawable.purple_ball);
				// i.setTag(0);
				// }
				// for (ImageView i : dRedBallList) {
				// i.setImageResource(R.drawable.purple_ball);
				// i.setTag(0);
				// }
				// selectedDredBallList.clear();
				// selectedTredBallList.clear();
				// checkBill();
				// }
				isStraight2 = false;
				isStraight3 = false;
				switch ((Integer) v.getTag()) {
				case 0:
					text1.setVisibility(View.GONE);
					text2.setVisibility(View.GONE);
					text3.setVisibility(View.GONE);
					for (TextView t : redBallListT) {
						t.setTextColor(redText);
					}
					for (ImageView i : redBallList) {
						i.setImageResource(R.drawable.purple_ball);
						i.setTag(0);
					}
					selectedRedBalls.clear();
					checkBill();
					typeName.setText("快乐十分-任选1");
					dtView.setVisibility(View.GONE);
					normalView.setVisibility(View.VISIBLE);
					normalView1.setVisibility(View.GONE);
					normalView2.setVisibility(View.GONE);
					break;
				case 1:
					text1.setVisibility(View.GONE);
					text2.setVisibility(View.GONE);
					text3.setVisibility(View.GONE);
					for (TextView t : redBallListT) {
						t.setTextColor(redText);
					}
					for (ImageView i : redBallList) {
						i.setImageResource(R.drawable.purple_ball);
						i.setTag(0);
					}
					selectedRedBalls.clear();
					checkBill();
					typeName.setText("快乐十分-任选2");
					dtView.setVisibility(View.GONE);
					normalView.setVisibility(View.VISIBLE);
					normalView1.setVisibility(View.GONE);
					normalView2.setVisibility(View.GONE);
					break;
				case 2:
					text1.setVisibility(View.GONE);
					text2.setVisibility(View.GONE);
					text3.setVisibility(View.GONE);
					for (TextView t : redBallListT) {
						t.setTextColor(redText);
					}
					for (ImageView i : redBallList) {
						i.setImageResource(R.drawable.purple_ball);
						i.setTag(0);
					}
					selectedRedBalls.clear();
					checkBill();
					typeName.setText("快乐十分-任选3");
					dtView.setVisibility(View.GONE);
					normalView.setVisibility(View.VISIBLE);
					normalView1.setVisibility(View.GONE);
					normalView2.setVisibility(View.GONE);
					break;
				case 3:
					text1.setVisibility(View.GONE);
					text2.setVisibility(View.GONE);
					text3.setVisibility(View.GONE);
					for (TextView t : redBallListT) {
						t.setTextColor(redText);
					}
					for (ImageView i : redBallList) {
						i.setImageResource(R.drawable.purple_ball);
						i.setTag(0);
					}
					selectedRedBalls.clear();
					checkBill();
					typeName.setText("快乐十分-任选4");
					dtView.setVisibility(View.GONE);
					normalView.setVisibility(View.VISIBLE);
					normalView1.setVisibility(View.GONE);
					normalView2.setVisibility(View.GONE);
					break;
				case 4:
					text1.setVisibility(View.GONE);
					text2.setVisibility(View.GONE);
					text3.setVisibility(View.GONE);
					for (TextView t : redBallListT) {
						t.setTextColor(redText);
					}
					for (ImageView i : redBallList) {
						i.setImageResource(R.drawable.purple_ball);
						i.setTag(0);
					}
					selectedRedBalls.clear();
					checkBill();
					typeName.setText("快乐十分-任选5");
					dtView.setVisibility(View.GONE);
					normalView.setVisibility(View.VISIBLE);
					normalView1.setVisibility(View.GONE);
					normalView2.setVisibility(View.GONE);
					break;
				case 5:
					text1.setVisibility(View.GONE);
					text2.setVisibility(View.GONE);
					text3.setVisibility(View.GONE);
					for (TextView t : redBallListT) {
						t.setTextColor(redText);
					}
					for (ImageView i : redBallList) {
						i.setImageResource(R.drawable.purple_ball);
						i.setTag(0);
					}
					selectedRedBalls.clear();
					checkBill();
					typeName.setText("快乐十分-选2连组");
					dtView.setVisibility(View.GONE);
					normalView.setVisibility(View.VISIBLE);
					normalView1.setVisibility(View.GONE);
					normalView2.setVisibility(View.GONE);
					break;
				case 6:
					text1.setVisibility(View.GONE);
					text2.setVisibility(View.GONE);
					text3.setVisibility(View.GONE);
					for (int i = 0; i < dRedBallListT.size(); i++) {
						dRedBallListT.get(i).setTextColor(redText);
						tRedBallListT.get(i).setTextColor(redText);
						tRedBallList.get(i).setImageResource(
								R.drawable.purple_ball);
						tRedBallList.get(i).setTag(0);
						dRedBallList.get(i).setImageResource(
								R.drawable.purple_ball);
						dRedBallList.get(i).setTag(0);

					}
					selectedDredBallList.clear();
					selectedTredBallList.clear();
					checkBill();
					typeName.setText("快乐十分-选3胆拖");
					dtView.setVisibility(View.VISIBLE);
					normalView.setVisibility(View.GONE);
					normalView1.setVisibility(View.GONE);
					normalView2.setVisibility(View.GONE);
					break;
				case 7:
					isStraight2 = true;
					text1.setVisibility(View.VISIBLE);
					text2.setVisibility(View.VISIBLE);
					text3.setVisibility(View.GONE);
					for (int i = 0; i < redBallList.size(); i++) {
						redBallList.get(i).setImageResource(
								R.drawable.purple_ball);
						redBallList.get(i).setTag(0);
						redBallList1.get(i).setImageResource(
								R.drawable.purple_ball);
						redBallList1.get(i).setTag(0);
						redBallListT.get(i).setTextColor(redText);
						redBallListT1.get(i).setTextColor(redText);
					}
					selectedRedBalls.clear();
					selectedRedBalls1.clear();
					typeName.setText("快乐十分-选2连直");
					dtView.setVisibility(View.GONE);
					normalView.setVisibility(View.VISIBLE);
					normalView1.setVisibility(View.VISIBLE);
					normalView2.setVisibility(View.GONE);
					break;
				case 8:
					text1.setVisibility(View.GONE);
					text2.setVisibility(View.GONE);
					text3.setVisibility(View.GONE);
					for (TextView t : redBallListT) {
						t.setTextColor(redText);
					}
					for (ImageView i : redBallList) {
						i.setImageResource(R.drawable.purple_ball);
						i.setTag(0);
					}
					selectedRedBalls.clear();
					checkBill();
					typeName.setText("快乐十分-选3前组");
					dtView.setVisibility(View.GONE);
					normalView.setVisibility(View.VISIBLE);
					normalView1.setVisibility(View.GONE);
					normalView2.setVisibility(View.GONE);
					break;
				case 9:
					isStraight3 = true;
					text1.setVisibility(View.VISIBLE);
					text2.setVisibility(View.VISIBLE);
					text3.setVisibility(View.VISIBLE);
					for (int i = 0; i < redBallList.size(); i++) {
						redBallList.get(i).setImageResource(
								R.drawable.purple_ball);
						redBallList.get(i).setTag(0);
						redBallList1.get(i).setImageResource(
								R.drawable.purple_ball);
						redBallList1.get(i).setTag(0);
						redBallList2.get(i).setImageResource(
								R.drawable.purple_ball);
						redBallList2.get(i).setTag(0);
						redBallListT.get(i).setTextColor(redText);
						redBallListT1.get(i).setTextColor(redText);
						redBallListT2.get(i).setTextColor(redText);
					}
					selectedRedBalls.clear();
					selectedRedBalls1.clear();
					selectedRedBalls2.clear();
					typeName.setText("快乐十分-选3前直");
					dtView.setVisibility(View.GONE);
					normalView.setVisibility(View.VISIBLE);
					normalView1.setVisibility(View.VISIBLE);
					normalView2.setVisibility(View.VISIBLE);
					break;
				}
				setSelectNum();
				alertLott.dismiss();
			}
		};
		d_type1.setOnClickListener(closeDialog);
		n_type2.setOnClickListener(closeDialog);
		n_type3.setOnClickListener(closeDialog);
		n_type4.setOnClickListener(closeDialog);
		n_type5.setOnClickListener(closeDialog);
		d_type2.setOnClickListener(closeDialog);
		d_type3.setOnClickListener(closeDialog);
		d_type4.setOnClickListener(closeDialog);
		d_type5.setOnClickListener(closeDialog);
		d_type6.setOnClickListener(closeDialog);
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
						timerest = 120;
						RequestParams params = new RequestParams();
						params.put("type", "7");
						Net.post(true, HappyTenLottery.this, Constant.PAY_URL
								+ "/ajax/index.php?act=load_lottery_info",
								params, mHandler,
								Constant.NET_ACTION_SECRET_SECURE);
						frontView.setVisibility(View.VISIBLE);
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
						RequestParams params = new RequestParams();
						params.put("type", "7");
						Net.post(true, HappyTenLottery.this, Constant.PAY_URL
								+ "/ajax/index.php?act=load_lottery_info",
								params, mHandler,
								Constant.NET_ACTION_SECRET_SECURE);
						timerest = 480;
					}
				}

			}
			if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				if (msg.arg1 == Constant.NET_ACTION_SECRET_SECURE) {
					Log.e("pkx", "重庆时时彩彩期信息：-----");
					FastCur fa = Net.gson.fromJson(msg.obj.toString(),
							FastCur.class);
					if (fa.getCurFast().getStart_second() == 0) {
						frontView.setVisibility(View.GONE);
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
						Log.e("pkx", "start != 0快三当前期次：当期剩余时间:"
								+ fa.getCurFast().getRest_time() + " name:"
								+ fa.getCurFast().getPeroid_name() + "剩余时间:"
								+ fa.getCurFast().getStart_second()
								+ " 上期： name:"
								+ fa.getLastFast().getPeroid_name() + " id:"
								+ fa.getLastFast().getPeroidID());
					}
				}
			}
			if (msg.what == Constant.PAGE_CHANGED) {
				// switch (msg.arg1) {
				// case 0:
				// // 选项卡一，直选
				// typeName.setText("快乐十分-直选");
				// currentPage = 0;
				// if (shakeView.getVisibility() == View.GONE) {
				// shakeView.setVisibility(View.VISIBLE);
				// }
				// checkBill();
				// break;
				// case 1:
				// // 选项卡二，胆拖
				// typeName.setText("快乐十分-胆拖");
				// if (shakeView.getVisibility() == View.VISIBLE) {
				// shakeView.setVisibility(View.GONE);
				// }
				// currentPage = 1;
				// checkBill();
				// break;
				// }
			}

		}
	}

	private void randomlySelect() {// TODO 随机选球
		switch (Constant.HAPPY_PLAY_TYPE) {
		case 0:
			selectedRedBalls.clear();
			for (ImageView rv : redBallList) {
				rv.setImageResource(R.drawable.purple_ball);
				rv.setTag(0);
			}
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}

			ArrayList<Integer> randomRedBalls10 = RandomBallsUtils
					.getRandomBalls(1, 20);
			for (int i : randomRedBalls10) {
				redBallList.get(i).setImageResource(R.drawable.black_ball);
				selectedRedBalls.add(redBallList.get(i));
				redBallList.get(i).setTag(1);
				redBallListT.get(i).setTextColor(whiteText);
			}
			break;
		case 1:
			selectedRedBalls.clear();
			for (ImageView rv : redBallList) {
				rv.setImageResource(R.drawable.purple_ball);
				rv.setTag(0);
			}
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}

			ArrayList<Integer> randomRedBalls1 = RandomBallsUtils
					.getRandomBalls(2, 20);
			for (int i : randomRedBalls1) {
				redBallList.get(i).setImageResource(R.drawable.black_ball);
				selectedRedBalls.add(redBallList.get(i));
				redBallList.get(i).setTag(1);
				redBallListT.get(i).setTextColor(whiteText);
			}
			break;
		case 2:
			selectedRedBalls.clear();
			for (ImageView rv : redBallList) {
				rv.setImageResource(R.drawable.purple_ball);
				rv.setTag(0);
			}
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}

			ArrayList<Integer> randomRedBalls2 = RandomBallsUtils
					.getRandomBalls(3, 20);
			for (int i : randomRedBalls2) {
				redBallList.get(i).setImageResource(R.drawable.black_ball);
				selectedRedBalls.add(redBallList.get(i));
				redBallList.get(i).setTag(1);
				redBallListT.get(i).setTextColor(whiteText);
			}
			break;
		case 3:
			selectedRedBalls.clear();
			for (ImageView rv : redBallList) {
				rv.setImageResource(R.drawable.purple_ball);
				rv.setTag(0);
			}
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}

			ArrayList<Integer> randomRedBalls3 = RandomBallsUtils
					.getRandomBalls(4, 20);
			for (int i : randomRedBalls3) {
				redBallList.get(i).setImageResource(R.drawable.black_ball);
				selectedRedBalls.add(redBallList.get(i));
				redBallList.get(i).setTag(1);
				redBallListT.get(i).setTextColor(whiteText);
			}
			break;
		case 4:
			selectedRedBalls.clear();
			for (ImageView rv : redBallList) {
				rv.setImageResource(R.drawable.purple_ball);
				rv.setTag(0);
			}
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}

			ArrayList<Integer> randomRedBalls4 = RandomBallsUtils
					.getRandomBalls(5, 20);
			for (int i : randomRedBalls4) {
				redBallList.get(i).setImageResource(R.drawable.black_ball);
				selectedRedBalls.add(redBallList.get(i));
				redBallList.get(i).setTag(1);
				redBallListT.get(i).setTextColor(whiteText);
			}
			break;
		case 5:
			selectedRedBalls.clear();
			for (ImageView rv : redBallList) {
				rv.setImageResource(R.drawable.purple_ball);
				rv.setTag(0);
			}
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}

			ArrayList<Integer> randomRedBalls5 = RandomBallsUtils
					.getRandomBalls(2, 20);
			for (int i : randomRedBalls5) {
				redBallList.get(i).setImageResource(R.drawable.black_ball);
				selectedRedBalls.add(redBallList.get(i));
				redBallList.get(i).setTag(1);
				redBallListT.get(i).setTextColor(whiteText);
			}
			break;
		case 8:
			selectedRedBalls.clear();
			for (ImageView rv : redBallList) {
				rv.setImageResource(R.drawable.purple_ball);
				rv.setTag(0);
			}
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}

			ArrayList<Integer> randomRedBalls8 = RandomBallsUtils
					.getRandomBalls(3, 20);
			for (int i : randomRedBalls8) {
				redBallList.get(i).setImageResource(R.drawable.black_ball);
				selectedRedBalls.add(redBallList.get(i));
				redBallList.get(i).setTag(1);
				redBallListT.get(i).setTextColor(whiteText);
			}
			break;
		case 6:
			selectedDredBallList.clear();
			selectedTredBallList.clear();

			for (ImageView rv : dRedBallList) {
				rv.setImageResource(R.drawable.purple_ball);
				rv.setTag(0);
			}
			for (TextView t : dRedBallListT) {
				t.setTextColor(redText);
			}
			for (ImageView rv : tRedBallList) {
				rv.setImageResource(R.drawable.purple_ball);
				rv.setTag(0);
			}
			for (TextView t : tRedBallListT) {
				t.setTextColor(redText);
			}
			ArrayList<Integer> randomRedBalls6 = RandomBallsUtils
					.getRandomBalls(4, 20);
			dRedBallList.get(randomRedBalls6.get(0)).setImageResource(
					R.drawable.black_ball);
			selectedDredBallList.add(dRedBallList.get(randomRedBalls6.get(0)));
			dRedBallList.get(randomRedBalls6.get(0)).setTag(1);
			dRedBallListT.get(randomRedBalls6.get(0)).setTextColor(whiteText);
			dRedBallList.get(randomRedBalls6.get(1)).setImageResource(
					R.drawable.black_ball);
			selectedDredBallList.add(dRedBallList.get(randomRedBalls6.get(1)));
			dRedBallList.get(randomRedBalls6.get(1)).setTag(1);
			dRedBallListT.get(randomRedBalls6.get(1)).setTextColor(whiteText);
			tRedBallList.get(randomRedBalls6.get(2)).setImageResource(
					R.drawable.black_ball);
			selectedTredBallList.add(tRedBallList.get(randomRedBalls6.get(2)));
			tRedBallList.get(randomRedBalls6.get(2)).setTag(1);
			tRedBallListT.get(randomRedBalls6.get(2)).setTextColor(whiteText);
			tRedBallList.get(randomRedBalls6.get(3)).setImageResource(
					R.drawable.black_ball);
			selectedTredBallList.add(tRedBallList.get(randomRedBalls6.get(3)));
			tRedBallList.get(randomRedBalls6.get(3)).setTag(1);
			tRedBallListT.get(randomRedBalls6.get(3)).setTextColor(whiteText);
			break;
		case 7:
			selectedRedBalls.clear();
			selectedRedBalls1.clear();
			for (ImageView rv : redBallList) {
				rv.setImageResource(R.drawable.purple_ball);
				rv.setTag(0);
			}
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}

			for (ImageView rv : redBallList1) {
				rv.setImageResource(R.drawable.purple_ball);
				rv.setTag(0);
			}
			for (TextView t : redBallListT1) {
				t.setTextColor(redText);
			}
			ArrayList<Integer> randomRedBalls7 = RandomBallsUtils
					.getRandomBalls(2, 20);
			redBallList.get(randomRedBalls7.get(0)).setImageResource(
					R.drawable.black_ball);
			selectedRedBalls.add(redBallList.get(randomRedBalls7.get(0)));
			redBallList.get(randomRedBalls7.get(0)).setTag(1);
			redBallListT.get(randomRedBalls7.get(0)).setTextColor(whiteText);

			redBallList1.get(randomRedBalls7.get(1)).setImageResource(
					R.drawable.black_ball);
			selectedRedBalls1.add(redBallList.get(randomRedBalls7.get(1)));
			redBallList1.get(randomRedBalls7.get(1)).setTag(1);
			redBallListT1.get(randomRedBalls7.get(1)).setTextColor(whiteText);
			break;
		case 9:

			selectedRedBalls.clear();
			selectedRedBalls1.clear();
			selectedRedBalls2.clear();
			for (ImageView rv : redBallList) {
				rv.setImageResource(R.drawable.purple_ball);
				rv.setTag(0);
			}
			for (TextView t : redBallListT) {
				t.setTextColor(redText);
			}

			for (ImageView rv : redBallList1) {
				rv.setImageResource(R.drawable.purple_ball);
				rv.setTag(0);
			}
			for (TextView t : redBallListT1) {
				t.setTextColor(redText);
			}
			for (ImageView rv : redBallList2) {
				rv.setImageResource(R.drawable.purple_ball);
				rv.setTag(0);
			}
			for (TextView t : redBallListT2) {
				t.setTextColor(redText);
			}
			ArrayList<Integer> randomRedBalls9 = RandomBallsUtils
					.getRandomBalls(3, 20);
			redBallList.get(randomRedBalls9.get(0)).setImageResource(
					R.drawable.black_ball);
			selectedRedBalls.add(redBallList.get(randomRedBalls9.get(0)));
			redBallList.get(randomRedBalls9.get(0)).setTag(1);
			redBallListT.get(randomRedBalls9.get(0)).setTextColor(whiteText);

			redBallList1.get(randomRedBalls9.get(1)).setImageResource(
					R.drawable.black_ball);
			selectedRedBalls1.add(redBallList.get(randomRedBalls9.get(1)));
			redBallList1.get(randomRedBalls9.get(1)).setTag(1);
			redBallListT1.get(randomRedBalls9.get(1)).setTextColor(whiteText);

			redBallList2.get(randomRedBalls9.get(2)).setImageResource(
					R.drawable.black_ball);
			selectedRedBalls2.add(redBallList.get(randomRedBalls9.get(2)));
			redBallList2.get(randomRedBalls9.get(2)).setTag(1);
			redBallListT2.get(randomRedBalls9.get(2)).setTextColor(whiteText);
			break;

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
		Constant.TICK_TOCK_FLAG = false;
		if (mShakeListener != null) {
			mShakeListener.stop();
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
