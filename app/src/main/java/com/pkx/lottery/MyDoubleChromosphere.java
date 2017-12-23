package com.pkx.lottery;

import android.annotation.SuppressLint;
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
import com.pkx.lottery.bean.ChromosphereBet;
import com.pkx.lottery.utils.RandomBallsUtils;
import com.pkx.lottery.utils.SharePreferenceUtil;

import java.util.ArrayList;
import java.util.Random;

public class MyDoubleChromosphere extends Activity implements OnClickListener {
	private int currentPage = 0;
	private Vibrator mVibretor;
	private ShakeListener mShakeListener = null;
	private MyHandler mHandler;
	private SharePreferenceUtil sutil;
	private int redText = Color.parseColor("#CD0000");
	private int blueText = Color.parseColor("#436EEE");
	private int whiteText = Color.parseColor("#ffffff");
	private ImageView dimention_d, dimention_s;
	private ImageView blue1, blue2, blue3, blue4, blue5, blue6, blue7, blue8,
			blue9, blue10, blue11, blue12, blue13, blue14, blue15, blue16;
	private TextView bblue1, bblue2, bblue3, bblue4, bblue5, bblue6, bblue7,
			bblue8, bblue9, bblue10, bblue11, bblue12, bblue13, bblue14,
			bblue15, bblue16;
	private TextView tblue1, tblue2, tblue3, tblue4, tblue5, tblue6, tblue7,
			tblue8, tblue9, tblue10, tblue11, tblue12, tblue13, tblue14,
			tblue15, tblue16;
	private ImageView dtblue1, dtblue2, dtblue3, dtblue4, dtblue5, dtblue6,
			dtblue7, dtblue8, dtblue9, dtblue10, dtblue11, dtblue12, dtblue13,
			dtblue14, dtblue15, dtblue16;
	private TextView red1n, red2n, red3n, red4n, red5n, red6n, red7n, red8n,
			red9n, red10n, red11n, red12n, red13n, red14n, red15n, red16n,
			red17n, red18n, red19n, red20n, red21n, red22n, red23n, red24n,
			red25n, red26n, red27n, red28n, red29n, red30n, red31n, red32n,
			red33n;
	private TextView red1d, red2d, red3d, red4d, red5d, red6d, red7d, red8d,
			red9d, red10d, red11d, red12d, red13d, red14d, red15d, red16d,
			red17d, red18d, red19d, red20d, red21d, red22d, red23d, red24d,
			red25d, red26d, red27d, red28d, red29d, red30d, red31d, red32d,
			red33d;
	private TextView red1t, red2t, red3t, red4t, red5t, red6t, red7t, red8t,
			red9t, red10t, red11t, red12t, red13t, red14t, red15t, red16t,
			red17t, red18t, red19t, red20t, red21t, red22t, red23t, red24t,
			red25t, red26t, red27t, red28t, red29t, red30t, red31t, red32t,
			red33t;
	private ImageView red1, red2, red3, red4, red5, red6, red7, red8, red9,
			red10, red11, red12, red13, red14, red15, red16, red17, red18,
			red19, red20, red21, red22, red23, red24, red25, red26, red27,
			red28, red29, red30, red31, red32, red33;
	private ImageView dred1, dred2, dred3, dred4, dred5, dred6, dred7, dred8,
			dred9, dred10, dred11, dred12, dred13, dred14, dred15, dred16,
			dred17, dred18, dred19, dred20, dred21, dred22, dred23, dred24,
			dred25, dred26, dred27, dred28, dred29, dred30, dred31, dred32,
			dred33;
	private ImageView tred1, tred2, tred3, tred4, tred5, tred6, tred7, tred8,
			tred9, tred10, tred11, tred12, tred13, tred14, tred15, tred16,
			tred17, tred18, tred19, tred20, tred21, tred22, tred23, tred24,
			tred25, tred26, tred27, tred28, tred29, tred30, tred31, tred32,
			tred33;
	private View deleteLogo;
	private ArrayList<ImageView> redBallList, blueBallList, selectedRedBalls,
			selectedBlueBalls, tRedBallList, selectedTredBallList,
			dRedBallList, selectedDredBallList, dtBlueBallList,
			seletedDtblueBallList;
	private ArrayList<TextView> redBallListT, blueBallListT, selectedRedBallsT,
			selectedBlueBallsT, tRedBallListT, selectedTredBallListT,
			dRedBallListT, selectedDredBallListT, dtBlueBallListT,
			seletedDtblueBallListT;
	private TextView billText, ballsText, typeName;
	private View handleBet;
	private Intent mIntent;
	private ChromosphereBet editBet;
	private LayoutInflater inflater;
	private View tabNormal, tabFollowBet, shakeView;
	private ViewPager paperChrom;
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
				if (currentPage == 0) {
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
			}
		});
		mShakeListener.start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mydouble_chromosphere);
		mIntent = getIntent();
		editBet = (ChromosphereBet) mIntent.getSerializableExtra("editBet");
		inflater = getLayoutInflater();
		tabNormal = inflater.inflate(R.layout.tab_chrom_normal, null);
		tabFollowBet = inflater.inflate(R.layout.tab_chrom_betfollow, null);
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
		paperChrom.setAdapter(adapter);

		if (editBet != null) {
			// 设置返回修改的投注信息
			if (editBet.getType() == 0) {
				// 普通投注
				ArrayList<Integer> redballs = editBet.getRedBalls();
				ArrayList<Integer> blueballs = editBet.getBlueBalls();
				for (int r : redballs) {
					redBallList.get(r - 1).setImageResource(
							R.drawable.black_ball);
					selectedRedBalls.add(redBallList.get(r - 1));
					selectedRedBallsT.add(redBallListT.get(r - 1));
					redBallList.get(r - 1).setTag(1);
					redBallListT.get(r - 1).setTextColor(whiteText);
				}
				for (int b : blueballs) {
					blueBallList.get(b - 1).setImageResource(
							R.drawable.blue_rect);
					selectedBlueBalls.add(blueBallList.get(b - 1));
					selectedBlueBallsT.add(blueBallListT.get(b - 1));
					blueBallList.get(b - 1).setTag(1);
					blueBallListT.get(b - 1).setTextColor(whiteText);

				}
				checkBill();
			} else if (editBet.getType() == 1) {
				selectedDredBallList.clear();
				selectedTredBallList.clear();
				seletedDtblueBallList.clear();
				paperChrom.setCurrentItem(1);
				// 修改胆拖双色球投注
				ArrayList<Integer> danRedBalls = editBet.getDanRedBalls();
				ArrayList<Integer> tuoRedBalls = editBet.getTuoRedBalls();
				ArrayList<Integer> dantuoBlueBalls = editBet
						.getDantuoBlueBalls();
				for (int drb : danRedBalls) {
					dRedBallList.get(drb - 1).setImageResource(
							R.drawable.black_ball);
					selectedDredBallList.add(dRedBallList.get(drb - 1));
					selectedDredBallListT.add(dRedBallListT.get(drb - 1));
					dRedBallList.get(drb - 1).setTag(1);
					dRedBallListT.get(drb - 1).setTextColor(whiteText);
				}
				for (int trb : tuoRedBalls) {
					tRedBallList.get(trb - 1).setImageResource(
							R.drawable.black_ball);
					selectedTredBallList.add(tRedBallList.get(trb - 1));
					selectedTredBallListT.add(tRedBallListT.get(trb - 1));
					tRedBallList.get(trb - 1).setTag(1);
					tRedBallListT.get(trb - 1).setTextColor(whiteText);
				}
				for (int dtb : dantuoBlueBalls) {
					dtBlueBallList.get(dtb - 1).setImageResource(
							R.drawable.blue_rect);
					seletedDtblueBallList.add(dtBlueBallList.get(dtb - 1));
					seletedDtblueBallListT.add(dtBlueBallListT.get(dtb - 1));
					dtBlueBallList.get(dtb - 1).setTag(1);
					dtBlueBallListT.get(dtb - 1).setTextColor(whiteText);
				}
				checkBill();
				Log.e("returnBet", "胆" + selectedDredBallList.size() + "拖"
						+ selectedTredBallList.size() + "蓝"
						+ seletedDtblueBallList.size());

			}

		}
		Log.e("pkx", "timeOK" + System.currentTimeMillis());
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (selectedRedBalls.size() == 0 && selectedBlueBalls.size() == 0
					&& selectedTredBallList.size() == 0
					&& selectedDredBallList.size() == 0
					&& seletedDtblueBallList.size() == 0) {
				finish();
			} else {
				alert();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			alert();
			break;
		case R.id.dimention_d:
			paperChrom.setCurrentItem(0);
			break;
		case R.id.dimention_s:
			paperChrom.setCurrentItem(1);
			break;
		case R.id.passPeroid:
			Intent toSevenShow = new Intent(MyDoubleChromosphere.this,
					ShowChromsphere.class);
			toSevenShow.putExtra("isFromSelectPage", true);
			startActivity(toSevenShow);
			break;
		case R.id.deleteLogo:
			if (currentPage == 0) {
				selectedBlueBalls.clear();
				selectedRedBalls.clear();
				for (TextView t : redBallListT) {
					t.setTextColor(redText);
				}

				for (TextView t : blueBallListT) {
					t.setTextColor(blueText);
				}

				for (ImageView red : redBallList) {
					red.setImageResource(R.drawable.purple_ball);
					red.setTag(0);
				}
				for (ImageView blue : blueBallList) {
					blue.setImageResource(R.drawable.purple_ball);
					blue.setTag(0);
				}
				ballsText.setText("");
				billText.setText("0");
			} else if (currentPage == 1) {
				seletedDtblueBallList.clear();
				selectedDredBallList.clear();
				selectedTredBallList.clear();
				for (TextView t : tRedBallListT) {
					t.setTextColor(redText);
				}
				for (TextView t : dRedBallListT) {
					t.setTextColor(redText);
				}
				for (TextView t : dtBlueBallListT) {
					t.setTextColor(blueText);
				}
				for (ImageView dRed : dRedBallList) {
					dRed.setImageResource(R.drawable.purple_ball);
					dRed.setTag(0);
				}
				for (ImageView tRed : tRedBallList) {
					tRed.setImageResource(R.drawable.purple_ball);
					tRed.setTag(0);
				}
				for (ImageView dtb : dtBlueBallList) {
					dtb.setImageResource(R.drawable.purple_ball);
					dtb.setTag(0);
				}

			}
			checkBill();
			break;
		case R.id.handleBet:
			if (currentPage == 0) {
				// 直选投注
				if (selectedBlueBalls.size() < 1 || selectedRedBalls.size() < 6) {
					Toast.makeText(this, "至少选择6个红球和一个蓝球", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				ArrayList<Integer> redBalls = new ArrayList<Integer>();
				ArrayList<Integer> blueBalls = new ArrayList<Integer>();
				for (ImageView sv : selectedRedBalls) {
					redBalls.add((Integer) sv.getTag(blue2.getId()));
				}
				for (ImageView sv : selectedBlueBalls) {
					blueBalls.add((Integer) sv.getTag(blue2.getId()));
				}
				ChromosphereBet cbet = new ChromosphereBet();
				cbet.setType(0);
				cbet.setBlueBalls(blueBalls);
				cbet.setRedBalls(redBalls);
				Bundle betBundle = new Bundle();
				betBundle.putSerializable("bet", cbet);
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
							DoubleChromosphereCheck.class);
					toCheckPage.putExtras(betBundle);
					startActivity(toCheckPage);
					finish();
				}
			} else {
				// 胆拖投注
				if (selectedDredBallList.size() < 1
						|| seletedDtblueBallList.size() < 1
						|| selectedTredBallList.size() < 1
						|| (selectedTredBallList.size() + selectedDredBallList
								.size()) < 7 || selectedDredBallList.size() > 5) {
					Toast.makeText(this, "至少选择一个胆红球，最多选择5个胆红球和一个蓝球，二个拖红球",
							Toast.LENGTH_SHORT).show();
					return;
				}
				ArrayList<Integer> danRedBalls = new ArrayList<Integer>();
				ArrayList<Integer> tuoRedBalls = new ArrayList<Integer>();
				ArrayList<Integer> dantuoBlueBalls = new ArrayList<Integer>();
				for (ImageView dr : selectedDredBallList) {
					danRedBalls.add((Integer) dr.getTag(blue2.getId()));
				}
				for (ImageView tr : selectedTredBallList) {
					tuoRedBalls.add((Integer) tr.getTag(blue2.getId()));
				}
				for (ImageView dtb : seletedDtblueBallList) {
					dantuoBlueBalls.add((Integer) dtb.getTag(blue2.getId()));
				}
				ChromosphereBet dtbet = new ChromosphereBet();
				dtbet.setType(1);
				dtbet.setDantuoBlueBalls(dantuoBlueBalls);
				dtbet.setDanRedBalls(danRedBalls);
				dtbet.setTuoRedBalls(tuoRedBalls);
				Bundle betBundle = new Bundle();
				betBundle.putSerializable("bet", dtbet);
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
							DoubleChromosphereCheck.class);
					toCheckPage.putExtras(betBundle);
					startActivity(toCheckPage);
					finish();
				}
			}

			break;

		default:
			if ("dtb".equals(v.getTag(blue1.getId()))) {
				// 点击胆拖蓝球
				if (v.getTag().equals(0)) {
					// 选中胆拖蓝球
					dtBlueBallListT
							.get(((Integer) v.getTag(blue2.getId()) - 1))
							.setTextColor(whiteText);
					((ImageView) v).setImageResource(R.drawable.blue_rect);
					v.setTag(1);
					seletedDtblueBallList.add((ImageView) v);
					startVibrato1();
				} else {
					// 取消胆拖蓝球
					dtBlueBallListT
							.get(((Integer) v.getTag(blue2.getId()) - 1))
							.setTextColor(blueText);
					((ImageView) v).setImageResource(R.drawable.purple_ball);
					v.setTag(0);
					seletedDtblueBallList.remove(v);
				}
			} else if ("dr".equals(v.getTag(blue1.getId()))) {
				// 点击胆红球
				boolean exsist = false;

				for (ImageView srt : selectedTredBallList) {
					if (srt.getTag(blue2.getId()) == v.getTag(blue2.getId())) {
						exsist = true;
					}
				}
				if (exsist) {
					Toast.makeText(this, "胆组拖组不同同时存在", Toast.LENGTH_SHORT)
							.show();
				} else {
					if (v.getTag().equals(0)) {
						if (selectedDredBallList.size() == 5) {
							Toast.makeText(this, "最多只能选择5个胆红球",
									Toast.LENGTH_SHORT).show();
							return;
						}
						startVibrato1();
						selectedDredBallList.add((ImageView) v);
						dRedBallListT.get(
								((Integer) v.getTag(blue2.getId()) - 1))
								.setTextColor(whiteText);
						v.setTag(1);
						((ImageView) v).setImageResource(R.drawable.black_ball);
					} else {
						((ImageView) v)
								.setImageResource(R.drawable.purple_ball);
						v.setTag(0);
						dRedBallListT.get(
								((Integer) v.getTag(blue2.getId()) - 1))
								.setTextColor(redText);
						selectedDredBallList.remove(v);
					}

				}
			} else if ("tr".equals(v.getTag(blue1.getId()))) {
				// 点击拖红球
				boolean exsist = false;
				for (ImageView srd : selectedDredBallList) {
					if (srd.getTag(blue2.getId()) == v.getTag(blue2.getId())) {
						exsist = true;
					}
				}
				if (exsist) {
					Toast.makeText(this, "胆组拖组不同同时存在", Toast.LENGTH_SHORT)
							.show();
				} else {
					if (v.getTag().equals(0)) {
						if (selectedTredBallList.size() == 15) {
							Toast.makeText(this, "最多只能选择15个拖红球",
									Toast.LENGTH_SHORT).show();
							return;
						}
						selectedTredBallList.add((ImageView) v);
						startVibrato1();
						v.setTag(1);
						tRedBallListT.get(
								((Integer) v.getTag(blue2.getId()) - 1))
								.setTextColor(whiteText);
						((ImageView) v).setImageResource(R.drawable.black_ball);
					} else {
						((ImageView) v)
								.setImageResource(R.drawable.purple_ball);
						v.setTag(0);
						tRedBallListT.get(
								((Integer) v.getTag(blue2.getId()) - 1))
								.setTextColor(redText);
						selectedTredBallList.remove(v);
					}

				}

			} else if ("r".equals(v.getTag(blue1.getId()))) {
				// 点击红球
				if (v.getTag().equals(0)) {
					// 选中红球
					if (selectedRedBalls.size() > 14) {
						Toast.makeText(this, "最多选择15个红色球", Toast.LENGTH_LONG)
								.show();
						return;
					}
					startVibrato1();
					redBallListT.get(((Integer) v.getTag(blue2.getId()) - 1))
							.setTextColor(whiteText);
					((ImageView) v).setImageResource(R.drawable.black_ball);
					v.setTag(1);
					selectedRedBalls.add((ImageView) v);
				} else {
					// 取消红球
					((ImageView) v).setImageResource(R.drawable.purple_ball);
					redBallListT.get(((Integer) v.getTag(blue2.getId()) - 1))
							.setTextColor(redText);
					v.setTag(0);
					selectedRedBalls.remove(v);
				}

			} else

			if ("b".equals(v.getTag(blue1.getId()))) {
				// 点击蓝球
				if (v.getTag().equals(0)) {
					// 选中蓝球
					((ImageView) v).setImageResource(R.drawable.blue_rect);
					v.setTag(1);
					startVibrato1();
					blueBallListT.get(((Integer) v.getTag(blue2.getId()) - 1))
							.setTextColor(whiteText);
					selectedBlueBalls.add((ImageView) v);
				} else {
					// 取消蓝球
					((ImageView) v).setImageResource(R.drawable.purple_ball);
					blueBallListT.get(((Integer) v.getTag(blue2.getId()) - 1))
							.setTextColor(blueText);
					v.setTag(0);
					selectedBlueBalls.remove(v);
				}

			}
			checkBill();
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
			String str = "";
			for (ImageView red : selectedRedBalls) {
				str += red.getTag(blue2.getId()) + "-";
			}
			String str1 = "";
			for (ImageView red : selectedBlueBalls) {
				str1 += "|" + red.getTag(blue2.getId());
			}
			RandomBallsUtils.getBetsNumber(selectedRedBalls.size(),
					selectedBlueBalls.size() * 2);

			ballsText.setText(str + str1);
			if (selectedBlueBalls.size() > 0 && selectedRedBalls.size() > 5) {
				billText.setText("共"
						+ RandomBallsUtils.getBetsNumber(
								selectedRedBalls.size(),
								selectedBlueBalls.size())
						+ "注"
						+ RandomBallsUtils.getBetsNumber(
								selectedRedBalls.size(),
								selectedBlueBalls.size() * 2) + "元");
			} else {
				billText.setText("0");
			}
		} else {
			String dtstr = "";
			for (ImageView dRed : selectedDredBallList) {
				dtstr += dRed.getTag(blue2.getId()) + " ";
			}
			dtstr = "胆" + dtstr + "拖";
			for (ImageView tRed : selectedTredBallList) {
				dtstr += tRed.getTag(blue2.getId()) + " ";
			}
			dtstr = dtstr + "蓝";
			for (ImageView dtl : seletedDtblueBallList) {
				dtstr += dtl.getTag(blue2.getId()) + " ";
			}
			ballsText.setText(dtstr);
			if (selectedDredBallList.size() > 0
					&& selectedTredBallList.size() > 0
					&& seletedDtblueBallList.size() > 0
					&& (selectedDredBallList.size() + selectedTredBallList
							.size()) > 6) {
				billText.setText("共"
						+ RandomBallsUtils.getBallBets(
								6 - selectedDredBallList.size(),
								selectedTredBallList.size())
						* seletedDtblueBallList.size()
						+ "注"
						+ RandomBallsUtils.getBallBets(
								6 - selectedDredBallList.size(),
								selectedTredBallList.size())
						* seletedDtblueBallList.size() * 2 + "元");
			} else {
				billText.setText("0");
			}
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

	@SuppressLint("NewApi")
	private void initViews() {
		sutil = new SharePreferenceUtil(this);
		mVibretor = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		mHandler = new MyHandler();
		dimention_d = (ImageView) findViewById(R.id.dimention_d);
		dimention_d.setOnClickListener(this);
		dimention_s = (ImageView) findViewById(R.id.dimention_s);
		dimention_s.setOnClickListener(this);
		paperChrom = (ViewPager) findViewById(R.id.paperChrom);
		paperChrom.setOnPageChangeListener(new OnPageChangeListener() {

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
		passPeroid = findViewById(R.id.passPeroid);
		passPeroid.setOnClickListener(this);
		shakeView = findViewById(R.id.shakeView);
		handleBet = findViewById(R.id.handleBet);
		handleBet.setOnClickListener(this);
		billText = (TextView) findViewById(R.id.billText);
		ballsText = (TextView) findViewById(R.id.ballsText);
		typeName = (TextView) findViewById(R.id.typeName);
		deleteLogo = findViewById(R.id.deleteLogo);
		deleteLogo.setOnClickListener(this);
		redBallListT = new ArrayList<TextView>();
		blueBallListT = new ArrayList<TextView>();
		selectedRedBallsT = new ArrayList<TextView>();
		selectedBlueBallsT = new ArrayList<TextView>();
		tRedBallListT = new ArrayList<TextView>();
		selectedTredBallListT = new ArrayList<TextView>();
		dRedBallListT = new ArrayList<TextView>();
		selectedDredBallListT = new ArrayList<TextView>();
		dtBlueBallListT = new ArrayList<TextView>();
		seletedDtblueBallListT = new ArrayList<TextView>();
		redBallList = new ArrayList<ImageView>();
		blueBallList = new ArrayList<ImageView>();
		selectedBlueBalls = new ArrayList<ImageView>();
		selectedRedBalls = new ArrayList<ImageView>();
		tRedBallList = new ArrayList<ImageView>();
		selectedTredBallList = new ArrayList<ImageView>();
		dRedBallList = new ArrayList<ImageView>();
		selectedDredBallList = new ArrayList<ImageView>();
		dtBlueBallList = new ArrayList<ImageView>();
		seletedDtblueBallList = new ArrayList<ImageView>();
		blue1 = (ImageView) tabNormal.findViewById(R.id.blue1);
		blue2 = (ImageView) tabNormal.findViewById(R.id.blue2);
		blue3 = (ImageView) tabNormal.findViewById(R.id.blue3);
		blue4 = (ImageView) tabNormal.findViewById(R.id.blue4);
		blue5 = (ImageView) tabNormal.findViewById(R.id.blue5);
		blue6 = (ImageView) tabNormal.findViewById(R.id.blue6);
		blue7 = (ImageView) tabNormal.findViewById(R.id.blue7);
		blue8 = (ImageView) tabNormal.findViewById(R.id.blue8);
		blue9 = (ImageView) tabNormal.findViewById(R.id.blue9);
		blue10 = (ImageView) tabNormal.findViewById(R.id.blue10);
		blue11 = (ImageView) tabNormal.findViewById(R.id.blue11);
		blue12 = (ImageView) tabNormal.findViewById(R.id.blue12);
		blue13 = (ImageView) tabNormal.findViewById(R.id.blue13);
		blue14 = (ImageView) tabNormal.findViewById(R.id.blue14);
		blue15 = (ImageView) tabNormal.findViewById(R.id.blue15);
		blue16 = (ImageView) tabNormal.findViewById(R.id.blue16);
		bblue1 = (TextView) tabNormal.findViewById(R.id.tblue1);
		bblue2 = (TextView) tabNormal.findViewById(R.id.tblue2);
		bblue3 = (TextView) tabNormal.findViewById(R.id.tblue3);
		bblue4 = (TextView) tabNormal.findViewById(R.id.tblue4);
		bblue5 = (TextView) tabNormal.findViewById(R.id.tblue5);
		bblue6 = (TextView) tabNormal.findViewById(R.id.tblue6);
		bblue7 = (TextView) tabNormal.findViewById(R.id.tblue7);
		bblue8 = (TextView) tabNormal.findViewById(R.id.tblue8);
		bblue9 = (TextView) tabNormal.findViewById(R.id.tblue9);
		bblue10 = (TextView) tabNormal.findViewById(R.id.tblue10);
		bblue11 = (TextView) tabNormal.findViewById(R.id.tblue11);
		bblue12 = (TextView) tabNormal.findViewById(R.id.tblue12);
		bblue13 = (TextView) tabNormal.findViewById(R.id.tblue13);
		bblue14 = (TextView) tabNormal.findViewById(R.id.tblue14);
		bblue15 = (TextView) tabNormal.findViewById(R.id.tblue15);
		bblue16 = (TextView) tabNormal.findViewById(R.id.tblue16);
		dtblue1 = (ImageView) tabFollowBet.findViewById(R.id.blue1);
		dtblue2 = (ImageView) tabFollowBet.findViewById(R.id.blue2);
		dtblue3 = (ImageView) tabFollowBet.findViewById(R.id.blue3);
		dtblue4 = (ImageView) tabFollowBet.findViewById(R.id.blue4);
		dtblue5 = (ImageView) tabFollowBet.findViewById(R.id.blue5);
		dtblue6 = (ImageView) tabFollowBet.findViewById(R.id.blue6);
		dtblue7 = (ImageView) tabFollowBet.findViewById(R.id.blue7);
		dtblue8 = (ImageView) tabFollowBet.findViewById(R.id.blue8);
		dtblue9 = (ImageView) tabFollowBet.findViewById(R.id.blue9);
		dtblue10 = (ImageView) tabFollowBet.findViewById(R.id.blue10);
		dtblue11 = (ImageView) tabFollowBet.findViewById(R.id.blue11);
		dtblue12 = (ImageView) tabFollowBet.findViewById(R.id.blue12);
		dtblue13 = (ImageView) tabFollowBet.findViewById(R.id.blue13);
		dtblue14 = (ImageView) tabFollowBet.findViewById(R.id.blue14);
		dtblue15 = (ImageView) tabFollowBet.findViewById(R.id.blue15);
		dtblue16 = (ImageView) tabFollowBet.findViewById(R.id.blue16);
		tblue1 = (TextView) tabFollowBet.findViewById(R.id.tblue1);
		tblue2 = (TextView) tabFollowBet.findViewById(R.id.tblue2);
		tblue3 = (TextView) tabFollowBet.findViewById(R.id.tblue3);
		tblue4 = (TextView) tabFollowBet.findViewById(R.id.tblue4);
		tblue5 = (TextView) tabFollowBet.findViewById(R.id.tblue5);
		tblue6 = (TextView) tabFollowBet.findViewById(R.id.tblue6);
		tblue7 = (TextView) tabFollowBet.findViewById(R.id.tblue7);
		tblue8 = (TextView) tabFollowBet.findViewById(R.id.tblue8);
		tblue9 = (TextView) tabFollowBet.findViewById(R.id.tblue9);
		tblue10 = (TextView) tabFollowBet.findViewById(R.id.tblue10);
		tblue11 = (TextView) tabFollowBet.findViewById(R.id.tblue11);
		tblue12 = (TextView) tabFollowBet.findViewById(R.id.tblue12);
		tblue13 = (TextView) tabFollowBet.findViewById(R.id.tblue13);
		tblue14 = (TextView) tabFollowBet.findViewById(R.id.tblue14);
		tblue15 = (TextView) tabFollowBet.findViewById(R.id.tblue15);
		tblue16 = (TextView) tabFollowBet.findViewById(R.id.tblue16);
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
		red31 = (ImageView) tabNormal.findViewById(R.id.red31);
		red32 = (ImageView) tabNormal.findViewById(R.id.red32);
		red33 = (ImageView) tabNormal.findViewById(R.id.red33);
		red1n = (TextView) tabNormal.findViewById(R.id.red1n);
		red2n = (TextView) tabNormal.findViewById(R.id.red2n);
		red3n = (TextView) tabNormal.findViewById(R.id.red3n);
		red4n = (TextView) tabNormal.findViewById(R.id.red4n);
		red5n = (TextView) tabNormal.findViewById(R.id.red5n);
		red6n = (TextView) tabNormal.findViewById(R.id.red6n);
		red7n = (TextView) tabNormal.findViewById(R.id.red7n);
		red8n = (TextView) tabNormal.findViewById(R.id.red8n);
		red9n = (TextView) tabNormal.findViewById(R.id.red9n);
		red10n = (TextView) tabNormal.findViewById(R.id.red10n);
		red11n = (TextView) tabNormal.findViewById(R.id.red11n);
		red12n = (TextView) tabNormal.findViewById(R.id.red12n);
		red13n = (TextView) tabNormal.findViewById(R.id.red13n);
		red14n = (TextView) tabNormal.findViewById(R.id.red14n);
		red15n = (TextView) tabNormal.findViewById(R.id.red15n);
		red16n = (TextView) tabNormal.findViewById(R.id.red16n);
		red17n = (TextView) tabNormal.findViewById(R.id.red17n);
		red18n = (TextView) tabNormal.findViewById(R.id.red18n);
		red19n = (TextView) tabNormal.findViewById(R.id.red19n);
		red20n = (TextView) tabNormal.findViewById(R.id.red20n);
		red21n = (TextView) tabNormal.findViewById(R.id.red21n);
		red22n = (TextView) tabNormal.findViewById(R.id.red22n);
		red23n = (TextView) tabNormal.findViewById(R.id.red23n);
		red24n = (TextView) tabNormal.findViewById(R.id.red24n);
		red25n = (TextView) tabNormal.findViewById(R.id.red25n);
		red26n = (TextView) tabNormal.findViewById(R.id.red26n);
		red27n = (TextView) tabNormal.findViewById(R.id.red27n);
		red28n = (TextView) tabNormal.findViewById(R.id.red28n);
		red29n = (TextView) tabNormal.findViewById(R.id.red29n);
		red30n = (TextView) tabNormal.findViewById(R.id.red30n);
		red31n = (TextView) tabNormal.findViewById(R.id.red31n);
		red32n = (TextView) tabNormal.findViewById(R.id.red32n);
		red33n = (TextView) tabNormal.findViewById(R.id.red33n);
		tred1 = (ImageView) tabFollowBet.findViewById(R.id.tred1);
		tred2 = (ImageView) tabFollowBet.findViewById(R.id.tred2);
		tred3 = (ImageView) tabFollowBet.findViewById(R.id.tred3);
		tred4 = (ImageView) tabFollowBet.findViewById(R.id.tred4);
		tred5 = (ImageView) tabFollowBet.findViewById(R.id.tred5);
		tred6 = (ImageView) tabFollowBet.findViewById(R.id.tred6);
		tred7 = (ImageView) tabFollowBet.findViewById(R.id.tred7);
		tred8 = (ImageView) tabFollowBet.findViewById(R.id.tred8);
		tred9 = (ImageView) tabFollowBet.findViewById(R.id.tred9);
		tred10 = (ImageView) tabFollowBet.findViewById(R.id.tred10);
		tred11 = (ImageView) tabFollowBet.findViewById(R.id.tred11);
		tred12 = (ImageView) tabFollowBet.findViewById(R.id.tred12);
		tred13 = (ImageView) tabFollowBet.findViewById(R.id.tred13);
		tred14 = (ImageView) tabFollowBet.findViewById(R.id.tred14);
		tred15 = (ImageView) tabFollowBet.findViewById(R.id.tred15);
		tred16 = (ImageView) tabFollowBet.findViewById(R.id.tred16);
		tred17 = (ImageView) tabFollowBet.findViewById(R.id.tred17);
		tred18 = (ImageView) tabFollowBet.findViewById(R.id.tred18);
		tred19 = (ImageView) tabFollowBet.findViewById(R.id.tred19);
		tred20 = (ImageView) tabFollowBet.findViewById(R.id.tred20);
		tred21 = (ImageView) tabFollowBet.findViewById(R.id.tred21);
		tred22 = (ImageView) tabFollowBet.findViewById(R.id.tred22);
		tred23 = (ImageView) tabFollowBet.findViewById(R.id.tred23);
		tred24 = (ImageView) tabFollowBet.findViewById(R.id.tred24);
		tred25 = (ImageView) tabFollowBet.findViewById(R.id.tred25);
		tred26 = (ImageView) tabFollowBet.findViewById(R.id.tred26);
		tred27 = (ImageView) tabFollowBet.findViewById(R.id.tred27);
		tred28 = (ImageView) tabFollowBet.findViewById(R.id.tred28);
		tred29 = (ImageView) tabFollowBet.findViewById(R.id.tred29);
		tred30 = (ImageView) tabFollowBet.findViewById(R.id.tred30);
		tred31 = (ImageView) tabFollowBet.findViewById(R.id.tred31);
		tred32 = (ImageView) tabFollowBet.findViewById(R.id.tred32);
		tred33 = (ImageView) tabFollowBet.findViewById(R.id.tred33);
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
		red31d = (TextView) tabFollowBet.findViewById(R.id.dred31);
		red32d = (TextView) tabFollowBet.findViewById(R.id.dred32);
		red33d = (TextView) tabFollowBet.findViewById(R.id.dred33);
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
		red31t = (TextView) tabFollowBet.findViewById(R.id.dtred31);
		red32t = (TextView) tabFollowBet.findViewById(R.id.dtred32);
		red33t = (TextView) tabFollowBet.findViewById(R.id.dtred33);
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
		dred31 = (ImageView) tabFollowBet.findViewById(R.id.red31);
		dred32 = (ImageView) tabFollowBet.findViewById(R.id.red32);
		dred33 = (ImageView) tabFollowBet.findViewById(R.id.red33);
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
		redBallList.add(red31);
		redBallList.add(red32);
		redBallList.add(red33);
		redBallListT.add(red1n);
		redBallListT.add(red2n);
		redBallListT.add(red3n);
		redBallListT.add(red4n);
		redBallListT.add(red5n);
		redBallListT.add(red6n);
		redBallListT.add(red7n);
		redBallListT.add(red8n);
		redBallListT.add(red9n);
		redBallListT.add(red10n);
		redBallListT.add(red11n);
		redBallListT.add(red12n);
		redBallListT.add(red13n);
		redBallListT.add(red14n);
		redBallListT.add(red15n);
		redBallListT.add(red16n);
		redBallListT.add(red17n);
		redBallListT.add(red18n);
		redBallListT.add(red19n);
		redBallListT.add(red20n);
		redBallListT.add(red21n);
		redBallListT.add(red22n);
		redBallListT.add(red23n);
		redBallListT.add(red24n);
		redBallListT.add(red25n);
		redBallListT.add(red26n);
		redBallListT.add(red27n);
		redBallListT.add(red28n);
		redBallListT.add(red29n);
		redBallListT.add(red30n);
		redBallListT.add(red31n);
		redBallListT.add(red32n);
		redBallListT.add(red33n);
		tRedBallList.add(tred1);
		tRedBallList.add(tred2);
		tRedBallList.add(tred3);
		tRedBallList.add(tred4);
		tRedBallList.add(tred5);
		tRedBallList.add(tred6);
		tRedBallList.add(tred7);
		tRedBallList.add(tred8);
		tRedBallList.add(tred9);
		tRedBallList.add(tred10);
		tRedBallList.add(tred11);
		tRedBallList.add(tred12);
		tRedBallList.add(tred13);
		tRedBallList.add(tred14);
		tRedBallList.add(tred15);
		tRedBallList.add(tred16);
		tRedBallList.add(tred17);
		tRedBallList.add(tred18);
		tRedBallList.add(tred19);
		tRedBallList.add(tred20);
		tRedBallList.add(tred21);
		tRedBallList.add(tred22);
		tRedBallList.add(tred23);
		tRedBallList.add(tred24);
		tRedBallList.add(tred25);
		tRedBallList.add(tred26);
		tRedBallList.add(tred27);
		tRedBallList.add(tred28);
		tRedBallList.add(tred29);
		tRedBallList.add(tred30);
		tRedBallList.add(tred31);
		tRedBallList.add(tred32);
		tRedBallList.add(tred33);
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
		tRedBallListT.add(red31t);
		tRedBallListT.add(red32t);
		tRedBallListT.add(red33t);
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
		dRedBallList.add(dred31);
		dRedBallList.add(dred32);
		dRedBallList.add(dred33);
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
		dRedBallListT.add(red31d);
		dRedBallListT.add(red32d);
		dRedBallListT.add(red33d);
		blueBallList.add(blue1);
		blueBallList.add(blue2);
		blueBallList.add(blue3);
		blueBallList.add(blue4);
		blueBallList.add(blue5);
		blueBallList.add(blue6);
		blueBallList.add(blue7);
		blueBallList.add(blue8);
		blueBallList.add(blue9);
		blueBallList.add(blue10);
		blueBallList.add(blue11);
		blueBallList.add(blue12);
		blueBallList.add(blue13);
		blueBallList.add(blue14);
		blueBallList.add(blue15);
		blueBallList.add(blue16);
		blueBallListT.add(bblue1);
		blueBallListT.add(bblue2);
		blueBallListT.add(bblue3);
		blueBallListT.add(bblue4);
		blueBallListT.add(bblue5);
		blueBallListT.add(bblue6);
		blueBallListT.add(bblue7);
		blueBallListT.add(bblue8);
		blueBallListT.add(bblue9);
		blueBallListT.add(bblue10);
		blueBallListT.add(bblue11);
		blueBallListT.add(bblue12);
		blueBallListT.add(bblue13);
		blueBallListT.add(bblue14);
		blueBallListT.add(bblue15);
		blueBallListT.add(bblue16);
		dtBlueBallList.add(dtblue1);
		dtBlueBallList.add(dtblue2);
		dtBlueBallList.add(dtblue3);
		dtBlueBallList.add(dtblue4);
		dtBlueBallList.add(dtblue5);
		dtBlueBallList.add(dtblue6);
		dtBlueBallList.add(dtblue7);
		dtBlueBallList.add(dtblue8);
		dtBlueBallList.add(dtblue9);
		dtBlueBallList.add(dtblue10);
		dtBlueBallList.add(dtblue11);
		dtBlueBallList.add(dtblue12);
		dtBlueBallList.add(dtblue13);
		dtBlueBallList.add(dtblue14);
		dtBlueBallList.add(dtblue15);
		dtBlueBallList.add(dtblue16);
		dtBlueBallListT.add(tblue1);
		dtBlueBallListT.add(tblue2);
		dtBlueBallListT.add(tblue3);
		dtBlueBallListT.add(tblue4);
		dtBlueBallListT.add(tblue5);
		dtBlueBallListT.add(tblue6);
		dtBlueBallListT.add(tblue7);
		dtBlueBallListT.add(tblue8);
		dtBlueBallListT.add(tblue9);
		dtBlueBallListT.add(tblue10);
		dtBlueBallListT.add(tblue11);
		dtBlueBallListT.add(tblue12);
		dtBlueBallListT.add(tblue13);
		dtBlueBallListT.add(tblue14);
		dtBlueBallListT.add(tblue15);
		dtBlueBallListT.add(tblue16);
		for (int i = 0; i < redBallListT.size(); i++) {
			Log.e("pkx", "redBallListT" + i);
			redBallListT.get(i).setTextColor(redText);
		}
		for (int i = 0; i < blueBallListT.size(); i++) {
			blueBallListT.get(i).setTextColor(blueText);
		}
		// redBallListT, blueBallListT, selectedRedBallsT,
		// selectedBlueBallsT, tRedBallListT, selectedTredBallListT,
		// dRedBallListT, selectedDredBallListT, dtBlueBallListT,
		// seletedDtblueBallListT;
		for (int i = 0; i < dRedBallListT.size(); i++) {
			dRedBallListT.get(i).setTextColor(redText);
		}
		for (int i = 0; i < tRedBallListT.size(); i++) {
			tRedBallListT.get(i).setTextColor(redText);
		}
		for (int i = 0; i < dtBlueBallListT.size(); i++) {
			dtBlueBallListT.get(i).setTextColor(blueText);
		}
		for (int i = 0; i < dtBlueBallList.size(); i++) {
			dtBlueBallList.get(i).setTag(0);
			dtBlueBallList.get(i).setTag(blue1.getId(), "dtb");
			dtBlueBallList.get(i).setTag(blue2.getId(), i + 1);
			dtBlueBallList.get(i).setOnClickListener(this);
		}
		for (int i = 0; i < blueBallList.size(); i++) {
			blueBallList.get(i).setTag(0);
			blueBallList.get(i).setTag(blue1.getId(), "b");
			blueBallList.get(i).setTag(blue2.getId(), i + 1);
			blueBallList.get(i).setOnClickListener(this);
		}
		for (int i = 0; i < redBallList.size(); i++) {
			redBallList.get(i).setTag(0);// 设置选中标记
			redBallList.get(i).setTag(blue1.getId(), "r");// 设置球型标记
			redBallList.get(i).setTag(blue2.getId(), i + 1);// 设置蓝球球号
			redBallList.get(i).setOnClickListener(this);
		}
		for (int i = 0; i < dRedBallList.size(); i++) {
			dRedBallList.get(i).setTag(0);// 设置选中标记
			dRedBallList.get(i).setTag(blue1.getId(), "dr");// 设置球型标记
			dRedBallList.get(i).setTag(blue2.getId(), i + 1);// 设置蓝球球号
			dRedBallList.get(i).setOnClickListener(this);
		}
		for (int i = 0; i < tRedBallList.size(); i++) {
			tRedBallList.get(i).setTag(0);// 设置选中标记
			tRedBallList.get(i).setTag(blue1.getId(), "tr");// 设置球型标记
			tRedBallList.get(i).setTag(blue2.getId(), i + 1);// 设置蓝球球号
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
			if (msg.what == Constant.PAGE_CHANGED) {
				switch (msg.arg1) {
				case 0:
					// 选项卡一，直选
					typeName.setText("双色球-直选");
					currentPage = 0;
					if (shakeView.getVisibility() == View.GONE) {
						shakeView.setVisibility(View.VISIBLE);
					}
					checkTypeBtn();
					checkBill();
					break;
				case 1:
					// 选项卡二，胆拖
					typeName.setText("双色球-胆拖");
					if (shakeView.getVisibility() == View.VISIBLE) {
						shakeView.setVisibility(View.GONE);
					}
					currentPage = 1;
					checkTypeBtn();
					checkBill();
					break;
				}
			} else if (msg.what == Constant.SHAKE_MESSAGE) {
				// 摇一摇机选
				randomlySelect();
			}

		}
	}

	private void randomlySelect() {
		selectedBlueBalls.clear();
		selectedRedBalls.clear();
		for (TextView t : redBallListT) {
			t.setTextColor(redText);
		}
		for (TextView t : blueBallListT) {
			t.setTextColor(blueText);
		}
		for (ImageView rv : redBallList) {
			rv.setImageResource(R.drawable.purple_ball);
			rv.setTag(0);
		}
		for (ImageView bv : blueBallList) {
			bv.setImageResource(R.drawable.purple_ball);
			bv.setTag(0);
		}
		ArrayList<Integer> randomRedBalls = RandomBallsUtils.getRandomBalls(6,
				32);
		for (int i : randomRedBalls) {
			redBallList.get(i).setImageResource(R.drawable.black_ball);
			selectedRedBalls.add(redBallList.get(i));
			selectedRedBallsT.add(redBallListT.get(i));
			redBallList.get(i).setTag(1);
			redBallListT.get(i).setTextColor(whiteText);
		}
		int blueBall = new Random().nextInt(15);
		selectedBlueBalls.add(blueBallList.get(blueBall));
		selectedBlueBallsT.add(blueBallListT.get(blueBall));
		blueBallListT.get(blueBall).setTextColor(whiteText);
		blueBallList.get(blueBall).setTag(1);
		blueBallList.get(blueBall).setImageResource(R.drawable.blue_rect);
		checkBill();
	}

	@SuppressLint("NewApi")
	private void alert() {
		final AlertDialog alert = new AlertDialog.Builder(this, R.style.dialog)
				.create();
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
