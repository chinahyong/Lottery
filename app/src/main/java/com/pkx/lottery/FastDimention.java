package com.pkx.lottery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.loopj.android.http.RequestParams;
import com.pkx.lottery.ShakeListener.OnShakeListener;
import com.pkx.lottery.ShowFast.ViewHolder;
import com.pkx.lottery.bean.FastBet;
import com.pkx.lottery.bean.FastNewBet;
import com.pkx.lottery.dto.FastCur;
import com.pkx.lottery.dto.PeroidRes;
import com.pkx.lottery.dto.lott.ChormLott;
import com.pkx.lottery.utils.DiceView;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.MyUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

public class FastDimention extends Activity implements OnClickListener {
	private ViewPager paperFast3;
	private PullToRefreshListView showPullList;
	// private XListView showList;
	private MyHandler mHandler;

	private Random sr = new Random();
	private int i, j, k, page2Select, page3Select, page4Select;
	private boolean isFirstView = true;
	// 摇一摇色子---------------
	private boolean canShake = true;
	DiceView gameView, gameView1, gameView2;
	int width, height;
	List<Point[]> points;
	private SoundPool sp;
	private int music, location;
	private AnimationSet animationSet;
	// ----------------------
	private View view1, view2, view3, view4, view5;
	LayoutInflater inflater;
	// private Handler hand;
	private Intent mIntent;
	private int currentPage = 0;
	private ImageView ball1, ball2, ball3;
	private int playType = -1;
	private int page = 1;
	private ShakeListener mShakeListener = null;
	// private ArrayList<ArrayList<Integer>> fastWins;
	private FastNewBet handBet;
	private View dialogView, pastWins, wholeView, nextView;
	private SharePreferenceUtil sutil;
	private Vibrator mVibretor;
	private TextView billText, minute, sec1, sec2, minute1, sec11, sec21,
			nextText, prizeText, curText;
	private ProgressDialog progress;
	private AlertDialog pastDialog;
	private BaseAdapter showListAdapter;
	private ArrayList<ChormLott> lotts;
	private Thread timeThread;
	private int timerest;
	private View s1_4, s1_5, s1_6, s1_7, s1_8, s1_9, s1_10, s1_11, s1_12,
			s1_13, s1_14, s1_15, s1_16, s1_17, s1_big, s1_small, s1_odd,
			s1_even;
	private View s2_112, s2_113, s2_114, s2_115, s2_116, s2_122, s2_223,
			s2_224, s2_225, s2_226, s2_133, s2_233, s2_334, s2_335, s2_336,
			s2_144, s2_244, s2_344, s2_445, s2_446, s2_155, s2_255, s2_355,
			s2_455, s2_556, s2_166, s2_266, s2_366, s2_466, s2_566, handleBet,
			topRightView;
	private View s3_111, s3_222, s3_333, s3_444, s3_555, s3_666, s3_110,
			s3_220, s3_330, s3_440, s3_550, s3_660, s3_all;
	private View s4_123, s4_124, s4_125, s4_126, s4_134, s4_135, s4_136,
			s4_145, s4_146, s4_156, s4_234, s4_235, s4_236, s4_245, s4_246,
			s4_256, s4_345, s4_346, s4_356, s4_456, s4_all;
	private View s5_12, s5_13, s5_14, s5_15, s5_16, s5_23, s5_24, s5_25, s5_26,
			s5_34, s5_35, s5_36, s5_45, s5_46, s5_56;
	private ImageView showNum1, showNum2, showNum3, showNum4, showNum5,
			dimention_d, dimention_t, dimention_s;
	static final ArrayList<Integer> fastLogos = new ArrayList<Integer>();
	private ArrayList<View> viewList1, viewList2, viewList3, viewList4,
			viewList5;
	private ArrayList<FastBet> betList1, betList2, betList3, betList4,
			betList5;
	private View fastLastShowView, fastTimeView, fastGridView;
	private int count = 0;
	private boolean use_my_net;
	private TextView title;

	public void clickBack(View view) {
	super.onBackPressed();
}
	private void getFastAnimation() {
		sp = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);// 第一个参数为同时播放数据流的最大个数，第二数据流类型，第三为声音质量
		music = sp.load(this, R.raw.rotate, 1); // 把你的声音素材放到res/raw里，第2个参数即为资源文件，第3个为音乐的优先级
		location = sp.load(this, R.raw.location, 1);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;// 屏幕宽度
		animationSet = new AnimationSet(true);
		height = dm.heightPixels;// 屏幕高度
		points = new ArrayList<Point[]>();
		points.add(new Point[] { new Point(width / 5, 5 * height / 24) });
		points.add(new Point[] { new Point(6 * width / 12, 5 * height / 24) });
		points.add(new Point[] { new Point(8 * width / 24, 9 * height / 24) });
		// hand = new Handler(){
		// @Override
		// public void handleMessage(Message msg) {
		// super.handleMessage(msg);
		// Integer[] num = new
		// Integer[]{gameView.indexs[0]+1,gameView1.indexs[0]+1,gameView2.indexs[0]+1};
		// Arrays.sort(num);
		// String str = num[0]+"+"+num[1]+"+"+num[2]+"="+
		// (num[0]+num[1]+num[2]);
		// Toast.makeText(FastDimention.this, str, Toast.LENGTH_LONG).show();
		//
		//
		// TranslateAnimation hideAciton = new TranslateAnimation(
		// 0,100,0,100);
		// hideAciton.setDuration(500);
		//
		// Animation animation = new ScaleAnimation(1, 0, 1, 0);
		// animation.setDuration(500);
		// animation.setFillAfter(true);
		//
		// animationSet.addAnimation(animation);
		// animationSet.addAnimation(hideAciton);
		//
		// animationSet.setFillAfter(true);
		//
		// view.startAnimation(animationSet);
		// view1.startAnimation(animationSet);
		// view2.startAnimation(animationSet);
		// sp.play(location, 1, 1, 0, 0, 1);
		// // final ViewManager mViewManager = (ViewManager) getParent();
		// // hand.postDelayed(new Runnable() {
		// //
		// // @Override
		// // public void run() {
		// // // TODO Auto-generated method stub
		// // mViewManager.removeView(view);//绉婚櫎view
		// // mViewManager.removeView(view1);//绉婚櫎view
		// // mViewManager.removeView(view2);//绉婚櫎view
		// // }
		// // }, 1000);
		// }
		// };
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
		setContentView(R.layout.fast_dimention);
		if (fastLogos.size() > 0) {
			fastLogos.clear();
		}
		getFastAnimation();
		timeThread = new Thread(new MyThread());
		wholeView = findViewById(R.id.wholeView);
		nextView = findViewById(R.id.nextView);
		fastLogos.add(R.drawable.num_1);
		fastLogos.add(R.drawable.num_2);
		fastLogos.add(R.drawable.num_3);
		fastLogos.add(R.drawable.num_4);
		fastLogos.add(R.drawable.num_5);
		fastLogos.add(R.drawable.num_6);
		sutil = new SharePreferenceUtil(this);
		mIntent = getIntent();
		title = (TextView) findViewById(R.id.title);
		switch (mIntent.getIntExtra("type", -1)) {
		case 0:
			title.setText("湖北快三");
			playType = 0;
			break;
		case 1:
			title.setText("江苏快三");
			playType = 1;
			break;
		}
		progress = new ProgressDialog(this);
		mHandler = new MyHandler();
		inflater = LayoutInflater.from(this);
		lotts = new ArrayList<ChormLott>();
		betList1 = new ArrayList<FastBet>();
		betList2 = new ArrayList<FastBet>();
		betList3 = new ArrayList<FastBet>();
		betList4 = new ArrayList<FastBet>();
		betList5 = new ArrayList<FastBet>();
		Constant.TICK_TOCK_FLAG = true;
		// 加载布局
		view1 = inflater.inflate(R.layout.fast_sum, null);
		view2 = inflater.inflate(R.layout.fast_two_same, null);
		view3 = inflater.inflate(R.layout.fast_three, null);
		view4 = inflater.inflate(R.layout.fast_three_unsame, null);
		view5 = inflater.inflate(R.layout.fast_two_unsame, null);
		dialogView = inflater.inflate(R.layout.fast_dialog, null);
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);
		views.add(view5);
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
		paperFast3.setAdapter(adapter);
		timerest = mIntent.getIntExtra("time", -1);
		if (new Random().nextInt(2) == 1) {
			timerest += 1;
		}
		if (timerest > 3) {
			fastLastShowView.setVisibility(View.VISIBLE);
			fastTimeView.setVisibility(View.VISIBLE);
			fastGridView.setVisibility(View.VISIBLE);
			timeThread.start();
			Log.e("pkx", "timerest=" + String.valueOf(timerest));
		} else {
			use_my_net = true;
			if (use_my_net) {
				RequestParams params = new RequestParams();
				params.put("type", "4");
				Net.post(true, this, Constant.PAY_URL
						+ "/ajax/index.php?act=load_lottery_info", params,
						mHandler, Constant.NET_ACTION_SECRET_SECURE);
			} else {
				// JsonCallBack callback=new JsonCallBack() {
				//
				// @Override
				// public void onSuccess(Object responseObject) {
				// // TODO Auto-generated method stub
				// Log.e("pkx", "~~~~onSuccess");
				//
				// }
				//
				// @Override
				// public void onFail(int reason, Object responseObject) {
				// // TODO Auto-generated method stub
				// Log.e("pkx", "~~~~onFail");
				//
				// }
				//
				// @Override
				// public void onError(int errorCode) {
				// // TODO Auto-generated method stub
				// Log.e("pkx", "~~~~onError");
				//
				// }
				//
				// @Override
				// public void onIOException() {
				// // TODO Auto-generated method stub
				// // CallBackNetNew.post(true, JsonAction.ACTION_UPLOAD_FILE,
				// FastDimention.this, null, this , FastCur.class, null);
				// Log.e("pkx", "~~~~onIOException");
				//
				// }
				// };
				// DongNet.post(true, JsonAction.ACTION_UPLOAD_FILE,
				// FastDimention.this, new TypeDto(), callback, FastCur.class,
				// null);
				// CallBackNetNew.post(true, JsonAction.ACTION_UPLOAD_FILE,
				// FastDimention.this, null, callback, FastCur.class, null);
			}

		}
	}

	public void startVibrato() { // 定义震动
		mVibretor.vibrate(new long[] { 50, 500, 50, 500 }, -1); // 第一个｛｝里面是节奏数组，
																// 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (Constant.isHandlySelect) {
				Intent res = new Intent();
				res.putExtra("time", timerest);
				setResult(RESULT_OK, res);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		if (v.getTag(view2.getId()) != null
				&& (Integer) v.getTag(view2.getId()) == 888) {
			resetSumSpecialBet(v);
			FastBet bet;
			if ((Integer) v.getTag(s1_4.getId()) == 1) {
				// 页号1 和值
				if ((Integer) v.getTag() == 0) {
					v.setBackgroundResource(R.drawable.bp);
					v.setTag(1);
					bet = new FastBet();
					bet.setCurrentPageId(0);
					bet.setBetNumber((Integer) v.getTag(s1_5.getId()));
					betList1.add(bet);
				} else {
					v.setBackgroundResource(R.drawable.cp);
					v.setTag(0);
					// for (int i = 0; i < betList1.size(); i++) {
					// if (betList1.get(i).getBetNumber() == (Integer) v
					// .getTag(s1_5.getId())) {
					//
					// }
					// }
					for (FastBet b : betList1) {
						if (b.getBetNumber() == (Integer) v
								.getTag(s1_5.getId())) {
							betList1.remove(b);
							break;
						}
					}
				}
			} else if ((Integer) v.getTag(s1_4.getId()) == 2) {
				// 页号2 2同号
				if ((Integer) v.getTag() == 0) {
					v.setBackgroundResource(R.drawable.bp);
					v.setTag(1);
					bet = new FastBet();
					bet.setCurrentPageId(currentPage);
					bet.setBetNumber((Integer) v.getTag(s1_5.getId()));
					betList2.add(bet);
				} else {
					v.setBackgroundResource(R.drawable.cp);
					v.setTag(0);
					for (FastBet b : betList2) {
						if (b.getBetNumber() == (Integer) v
								.getTag(s1_5.getId())) {
							betList2.remove(b);
							break;
						}
					}
				}

			} else if ((Integer) v.getTag(s1_4.getId()) == 3) {
				// 页号3 3同号
				if ((Integer) v.getTag() == 0) {
					if ((Integer) v.getTag(s1_5.getId()) != 0
							&& (Integer) v.getTag(s1_5.getId()) % 10 == 0) {
						// 二同号复选
						if (betList3.size() == 0) {
							v.setBackgroundResource(R.drawable.bp);
							v.setTag(1);
							bet = new FastBet();
							bet.setCurrentPageId(currentPage);
							bet.setBetNumber((Integer) v.getTag(s1_5.getId()));
							betList3.add(bet);
						} else {
							boolean isFresh = false;
							for (FastBet b : betList3) {
								if (b.getBetNumber() % 10 != 0
										|| b.getBetNumber() == 0) {
									isFresh = true;
								}
							}
							if (isFresh) {
								betList3.clear();
								bet = new FastBet();
								bet.setBetNumber((Integer) v.getTag(s1_5
										.getId()));
								bet.setCurrentPageId(currentPage);
								resetThirdPage();
								betList3.add(bet);
								v.setBackgroundResource(R.drawable.bp);
								v.setTag(1);
							} else {
								bet = new FastBet();
								bet.setBetNumber((Integer) v.getTag(s1_5
										.getId()));
								bet.setCurrentPageId(currentPage);
								betList3.add(bet);
								v.setTag(1);
								v.setBackgroundResource(R.drawable.bp);
							}
						}
						// v.setBackgroundResource(R.drawable.bp);
						// v.setTag(1);
						// bet = new FastBet();
						// bet.setBetNumber((Integer) v.getTag(s1_5.getId()));
						// betList3.add(bet);
					} else {
						// 三同号
						if (betList3.size() == 0) {
							v.setBackgroundResource(R.drawable.bp);
							v.setTag(1);
							bet = new FastBet();
							bet.setCurrentPageId(currentPage);
							bet.setBetNumber((Integer) v.getTag(s1_5.getId()));
							betList3.add(bet);
						} else {
							boolean isFresh = false;
							for (FastBet b : betList3) {
								if (b.getBetNumber() % 10 == 0
										&& b.getBetNumber() != 0) {
									isFresh = true;
								}
							}
							if (isFresh) {
								betList3.clear();
								bet = new FastBet();
								bet.setBetNumber((Integer) v.getTag(s1_5
										.getId()));
								bet.setCurrentPageId(currentPage);
								resetThirdPage();
								betList3.add(bet);
								v.setBackgroundResource(R.drawable.bp);
								v.setTag(1);
							} else {
								bet = new FastBet();
								bet.setBetNumber((Integer) v.getTag(s1_5
										.getId()));
								bet.setCurrentPageId(currentPage);
								betList3.add(bet);
								v.setTag(1);
								v.setBackgroundResource(R.drawable.bp);
							}

						}
						// v.setBackgroundResource(R.drawable.bp);
						// v.setTag(1);
						// bet = new FastBet();
						// bet.setBetNumber((Integer) v.getTag(s1_5.getId()));
						// betList3.add(bet);
					}
				} else {
					v.setBackgroundResource(R.drawable.cp);
					v.setTag(0);
					for (FastBet b : betList3) {
						if (b.getBetNumber() == (Integer) v
								.getTag(s1_5.getId())) {
							betList3.remove(b);
							break;
						}
					}
				}
				String betStr = "";
				for (FastBet b : betList3) {
					betStr += " " + b.getBetNumber();
				}
				Log.e("pkx", "betList3:" + betList3.size() + "betStr:" + betStr);
			} else if ((Integer) v.getTag(s1_4.getId()) == 4) {
				// 页号4 3不同号
				if ((Integer) v.getTag() == 0) {
					v.setBackgroundResource(R.drawable.bp);
					v.setTag(1);
					bet = new FastBet();
					bet.setCurrentPageId(currentPage);
					bet.setBetNumber((Integer) v.getTag(s1_5.getId()));
					betList4.add(bet);
				} else {
					v.setBackgroundResource(R.drawable.cp);
					v.setTag(0);
					for (FastBet b : betList4) {
						if (b.getBetNumber() == (Integer) v
								.getTag(s1_5.getId())) {
							betList4.remove(b);
							break;
						}
					}
				}
				String betStr = "";
				for (FastBet b : betList4) {
					betStr += " " + b.getBetNumber();
				}
				Log.e("pkx", "betList4:" + betList4.size() + "betStr:" + betStr);
			} else if ((Integer) v.getTag(s1_4.getId()) == 5) {
				// 页号5 2不同号
				if ((Integer) v.getTag() == 0) {
					v.setBackgroundResource(R.drawable.bp);
					v.setTag(1);
					bet = new FastBet();
					bet.setCurrentPageId(currentPage);
					bet.setBetNumber((Integer) v.getTag(s1_5.getId()));
					betList5.add(bet);
				} else {
					v.setBackgroundResource(R.drawable.cp);
					v.setTag(0);
					for (FastBet b : betList5) {
						if (b.getBetNumber() == (Integer) v
								.getTag(s1_5.getId())) {
							betList5.remove(b);
							break;
						}
					}
				}
				String betStr = "";
				for (FastBet b : betList5) {
					betStr += " " + b.getBetNumber();
				}
				Log.e("pkx", "betList5:" + betList5.size() + "betStr:" + betStr);
			}
			checkBill();
		}

		switch (v.getId()) {
		case R.id.topRightView:

			break;
		case R.id.pastWins:
			if (lotts != null && lotts.size() > 0) {
				pastDialog.show();
				pastDialog.getWindow().setContentView(dialogView);
				showListAdapter.notifyDataSetChanged();
			} else {
				PeroidRes res = new PeroidRes("getPeroidRes", "4", "30", "1");
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						Net.gson.toJson(res));
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", miwen);
				Net.post(true, FastDimention.this, Constant.POST_URL
						+ "/data.api.php", params, mHandler,
						Constant.NET_ACTION_LOTHIS);
			}
			break;
		case R.id.s1_big:
			// 大
			resetSumBet();
			if (betList1.size() > 0) {
				betList1.clear();
			}
			FastBet bet;
			for (int i = 0; i < viewList1.size(); i++) {

				if (i > 6 && i < 14) {
					viewList1.get(i).setBackgroundResource(R.drawable.bp);
					viewList1.get(i).setTag(1);
					bet = new FastBet();
					bet.setCurrentPageId(0);
					bet.setBetNumber((Integer) viewList1.get(i).getTag(
							s1_5.getId()));
					betList1.add(bet);
				}
			}
			checkBill();
			s1_big.setBackgroundResource(R.drawable.bp);
			// String betStr = "";
			// for (FastBet b : betList1) {
			// betStr += " " + b.getBetNumber();
			// }
			// Log.e("pkx", "betList1" + betList1.size() + "betStr:" + betStr);
			break;
		case R.id.s1_small:
			// 小
			resetSumBet();
			if (betList1.size() > 0) {
				betList1.clear();
			}
			for (int i = 0; i < viewList1.size(); i++) {
				if (i < 7) {
					viewList1.get(i).setBackgroundResource(R.drawable.bp);
					viewList1.get(i).setTag(1);
					bet = new FastBet();
					bet.setBetNumber((Integer) viewList1.get(i).getTag(
							s1_5.getId()));
					betList1.add(bet);
				}
			}
			s1_small.setBackgroundResource(R.drawable.bp);
			checkBill();
			// String betStr1 = "";
			// for (FastBet b : betList1) {
			// betStr1 += " " + b.getBetNumber();
			// }
			// Log.e("pkx", "betList1" + betList1.size() + "betStr:" + betStr1);
			break;
		case R.id.s1_odd:
			// 单
			resetSumBet();
			if (betList1.size() > 0) {
				betList1.clear();
			}
			for (int i = 0; i < viewList1.size(); i++) {
				if (i < 14 && i % 2 == 1) {
					viewList1.get(i).setBackgroundResource(R.drawable.bp);
					viewList1.get(i).setTag(1);
					bet = new FastBet();
					bet.setBetNumber((Integer) viewList1.get(i).getTag(
							s1_5.getId()));
					betList1.add(bet);
				}
			}
			s1_odd.setBackgroundResource(R.drawable.bp);
			checkBill();
			// String betStr2 = "";
			// for (FastBet b : betList1) {
			// betStr2 += " " + b.getBetNumber();
			// }
			// Log.e("pkx", "betList1" + betList1.size() + "betStr:" + betStr2);
			break;
		case R.id.s1_even:
			// 双
			resetSumBet();
			if (betList1.size() > 0) {
				betList1.clear();
			}
			for (int i = 0; i < viewList1.size(); i++) {
				if (i < 14 && i % 2 == 0) {
					viewList1.get(i).setBackgroundResource(R.drawable.bp);
					viewList1.get(i).setTag(1);
					bet = new FastBet();
					bet.setCurrentPageId(currentPage);
					bet.setBetNumber((Integer) viewList1.get(i).getTag(
							s1_5.getId()));
					betList1.add(bet);
				}
			}
			s1_even.setBackgroundResource(R.drawable.bp);
			checkBill();
			// String betStr3 = "";
			// for (FastBet b : betList1) {
			// betStr3 += " " + b.getBetNumber();
			// }
			// Log.e("pkx", "betList1" + betList1.size() + "betStr:" + betStr3);
			break;
		case R.id.handleBet:
			switch (currentPage) {
			case 0:
				if (betList1.size() == 0) {
					Toast.makeText(this, "必须选择一注", Toast.LENGTH_SHORT).show();
					return;
				}
				handBet = MyUtils.changeFastBetsToFastNewBet(betList1);
				break;
			case 1:
				if (betList2.size() == 0) {
					Toast.makeText(this, "必须选择一注", Toast.LENGTH_SHORT).show();
					return;
				}
				handBet = MyUtils.changeFastBetsToFastNewBet(betList2);
				break;
			case 2:
				if (betList3.size() == 0) {
					Toast.makeText(this, "必须选择一注", Toast.LENGTH_SHORT).show();
					return;
				}
				handBet = MyUtils.changeFastBetsToFastNewBet(betList3);
				break;
			case 3:
				if (betList4.size() == 0) {
					Toast.makeText(this, "必须选择一注", Toast.LENGTH_SHORT).show();
					return;
				}
				handBet = MyUtils.changeFastBetsToFastNewBet(betList4);
				break;
			case 4:
				if (betList5.size() == 0) {
					Toast.makeText(this, "必须选择一注", Toast.LENGTH_SHORT).show();
					return;
				}
				handBet = MyUtils.changeFastBetsToFastNewBet(betList5);
				break;
			}
			Bundle bundle = new Bundle();
			bundle.putSerializable("bet", handBet);
			if (Constant.isHandlySelect) {
				Intent res1 = new Intent();
				res1.putExtras(bundle);
				res1.putExtra("time", timerest);
				setResult(RESULT_OK, res1);
				Log.e("pkx", "----------isHandlySelect");
				finish();
			}
			// else if (Constant.isHandlyEdit) {
			//
			// }
			else {
				// 首次投注
				Log.e("pkx", "首次投注:" + timerest);
				Intent toFastCheck = new Intent(this, FastCheck.class);
				toFastCheck.putExtras(bundle);
				toFastCheck.putExtra("type", playType);
				toFastCheck.putExtra("time", timerest);
				startActivity(toFastCheck);
				finish();
			}
			break;
		default:
			// String betStr4 = "";
			// for (FastBet b : betList1) {
			// betStr4 += " " + b.getBetNumber();
			// }
			// Log.e("pkx", "betList1" + betList1.size() + "betStr:" + betStr4);
			break;
		}

	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.e("pkx", "FastDimentison onPause--------");
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.e("pkx", "FastDimentison destroy--------");
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
		Constant.TICK_TOCK_FLAG = false;
		Constant.isHandlySelect = false;
		Constant.isHandlyEdit = false;
	}

	private void initViews() {
		// s1_4, s1_5, s1_6, s1_7, s1_8, s1_9, s1_10, s1_11, s1_12, s1_13,
		// s1_14, s1_15, s1_16, s1_17, s1_big, s1_small, s1_odd, s1_even;
		//
		curText = (TextView) findViewById(R.id.curText);
		ball1 = (ImageView) findViewById(R.id.ball1);
		ball2 = (ImageView) findViewById(R.id.ball2);
		ball3 = (ImageView) findViewById(R.id.ball3);
		fastLastShowView = findViewById(R.id.fastLastShowView);
		fastTimeView = findViewById(R.id.fastTimeView);
		fastGridView = findViewById(R.id.fastGridView);
		pastDialog = new AlertDialog.Builder(this).create();
		showPullList = (PullToRefreshListView) dialogView
				.findViewById(R.id.showPullList);
		pastWins = findViewById(R.id.pastWins);
		// fastWinsList = (ListView) dialogView.findViewById(R.id.fastWinsList);
		// IXListViewListener coperListener = new IXListViewListener() {
		//
		// @Override
		// public void onRefresh() {
		// PeroidRes res = new PeroidRes("getPeroidRes", "4", "30", "1");
		// String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
		// Net.gson.toJson(res));
		// RequestParams params = new RequestParams();
		// params.put("SID", sutil.getSID());
		// params.put("DATA", miwen);
		// Net.post(false, FastDimention.this,Constant.POST_URL+
		// "/data.api.php", params, mHandler,
		// Constant.NET_ACTION_REFRESH);
		// }
		//
		// @Override
		// public void onLoadMore() {
		// PeroidRes res = new PeroidRes("getPeroidRes", "4", "30",
		// String.valueOf(page + 1));
		// String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
		// Net.gson.toJson(res));
		// RequestParams params = new RequestParams();
		// params.put("SID", sutil.getSID());
		// params.put("DATA", miwen);
		// Net.post(false, FastDimention.this,Constant.POST_URL+
		// "/data.api.php", params, mHandler,
		// Constant.NET_ACTION_LOADMORE);
		// }
		// };
		showListAdapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder viewholder = null;
				if (convertView == null) {
					viewholder = new ViewHolder();
					convertView = inflater.inflate(R.layout.fastshow_list_item,
							null);
					viewholder.ballText1 = (ImageView) convertView
							.findViewById(R.id.img1);
					viewholder.ballText2 = (ImageView) convertView
							.findViewById(R.id.img2);
					viewholder.ballText3 = (ImageView) convertView
							.findViewById(R.id.img3);
					viewholder.name = (TextView) convertView
							.findViewById(R.id.name);
					viewholder.time = (TextView) convertView
							.findViewById(R.id.time);
					viewholder.sum = (TextView) convertView
							.findViewById(R.id.sum);
					convertView.setTag(viewholder);
				} else {
					viewholder = (ViewHolder) convertView.getTag();
				}
				if (viewholder != null) {
					if (lotts.get(position).getBalls().size() == 3) {
						Log.e("pkx", "fastLogos:" + fastLogos.size() + " qiu1:"
								+ lotts.get(position).getBalls().get(0)
								+ " qiu2:"
								+ lotts.get(position).getBalls().get(1)
								+ " qiu3:"
								+ lotts.get(position).getBalls().get(2));
						Log.e("pkx", "");
						if (lotts.get(position).getBalls().get(0) == null) {
							Log.e("pkx",
									"lotts.get(position).getBalls().get(0)==null");
						}
						if (Integer.valueOf(lotts.get(position).getBalls()
								.get(0)) == null) {
							Log.e("pkx",
									"Integer.valueOf(lotts.get(position).getBalls().get(0)==null");
						}
						// Log.e("pkx",
						// "num1:"+fastLogos.get(Integer.valueOf(lotts.get(position).getBalls().get(0))));
						// Log.e("pkx",
						// "num:"+String.valueOf(fastLogos.get(Integer.valueOf(lotts.get(position).getBalls().get(0))-1)));
						int ball1num = lotts.get(position).getBalls().get(0) - 1;
						int ball2num = lotts.get(position).getBalls().get(1) - 1;
						int ball3num = lotts.get(position).getBalls().get(2) - 1;
						Log.e("pkx", "ball1:-----" + ball1num + "ball2:-----"
								+ ball2num + "ball3:------" + ball3num);
						if (ball1num > -1 && ball2num > -1 && ball3num > -1) {
							viewholder.ballText1
									.setImageResource(fastLogos
											.get(lotts.get(position).getBalls()
													.get(0) - 1));
							viewholder.ballText2
									.setImageResource(fastLogos
											.get(lotts.get(position).getBalls()
													.get(1) - 1));
							viewholder.ballText3
									.setImageResource(fastLogos
											.get(lotts.get(position).getBalls()
													.get(2) - 1));
							viewholder.sum.setText("和值"
									+ String.valueOf(lotts.get(position)
											.getBalls().get(0)
											+ lotts.get(position).getBalls()
													.get(1)
											+ lotts.get(position).getBalls()
													.get(2)));
						}
						viewholder.name.setText("第"
								+ lotts.get(position).getPeroid_name() + "期");
						if (lotts.get(position).getRes_date().length() > 10) {
							viewholder.time.setText(lotts.get(position)
									.getRes_date().substring(0, 10));
						} else {
							viewholder.time.setText(lotts.get(position)
									.getRes_date());
						}
					}
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
				return lotts.size();
			}
		};
		showPullList.setMode(Mode.BOTH);
		showPullList.setAdapter(showListAdapter);
		showPullList.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				PeroidRes res = new PeroidRes("getPeroidRes", "4", "30", "1");
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						Net.gson.toJson(res));
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("DATA", miwen);
				Net.post(false, FastDimention.this, Constant.POST_URL
						+ "/data.api.php", params, mHandler,
						Constant.NET_ACTION_REFRESH);

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				PeroidRes res = new PeroidRes("getPeroidRes", "4", "30", String
						.valueOf(page + 1));
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						Net.gson.toJson(res));
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("DATA", miwen);
				Net.post(false, FastDimention.this, Constant.POST_URL
						+ "/data.api.php", params, mHandler,
						Constant.NET_ACTION_LOADMORE);
			}
		});
		// showList.setPullRefreshEnable(true);
		// showList.setPullLoadEnable(true);
		// showList.setAdapter(showListAdapter);
		// showList.setXListViewListener(coperListener);
		BaseAdapter adapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				LinearLayout layout = (LinearLayout) inflater.inflate(
						R.layout.fastwin_listitem, null);
				return layout;
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
		minute = (TextView) findViewById(R.id.minute);
		sec1 = (TextView) findViewById(R.id.sec1);
		sec2 = (TextView) findViewById(R.id.sec2);
		minute1 = (TextView) findViewById(R.id.minute1);
		sec11 = (TextView) findViewById(R.id.sec11);
		sec21 = (TextView) findViewById(R.id.sec21);
		pastWins.setOnClickListener(this);
		viewList1 = new ArrayList<View>();
		viewList2 = new ArrayList<View>();
		viewList3 = new ArrayList<View>();
		viewList4 = new ArrayList<View>();
		viewList5 = new ArrayList<View>();
		// s5_35, s5_36, s5_45, s5_46, s5_56;
		s1_4 = view1.findViewById(R.id.s1_4);
		s1_5 = view1.findViewById(R.id.s1_5);
		s1_4.setTag(s1_5.getId(), 4);
		s1_5.setTag(s1_5.getId(), 5);
		s1_6 = view1.findViewById(R.id.s1_6);
		s1_6.setTag(s1_5.getId(), 6);
		s1_7 = view1.findViewById(R.id.s1_7);
		s1_7.setTag(s1_5.getId(), 7);
		s1_8 = view1.findViewById(R.id.s1_8);
		s1_8.setTag(s1_5.getId(), 8);
		s1_9 = view1.findViewById(R.id.s1_9);
		s1_9.setTag(s1_5.getId(), 9);
		s1_10 = view1.findViewById(R.id.s1_10);
		s1_10.setTag(s1_5.getId(), 10);
		s1_11 = view1.findViewById(R.id.s1_11);
		s1_11.setTag(s1_5.getId(), 11);
		s1_12 = view1.findViewById(R.id.s1_12);
		s1_12.setTag(s1_5.getId(), 12);
		s1_13 = view1.findViewById(R.id.s1_13);
		s1_13.setTag(s1_5.getId(), 13);
		s1_14 = view1.findViewById(R.id.s1_14);
		s1_14.setTag(s1_5.getId(), 14);
		s1_15 = view1.findViewById(R.id.s1_15);
		s1_15.setTag(s1_5.getId(), 15);
		s1_16 = view1.findViewById(R.id.s1_16);
		s1_16.setTag(s1_5.getId(), 16);
		s1_17 = view1.findViewById(R.id.s1_17);
		s1_17.setTag(s1_5.getId(), 17);
		s1_big = view1.findViewById(R.id.s1_big);
		// s1_big.setTag(s1_5.getId(), 18);
		s1_small = view1.findViewById(R.id.s1_small);
		// s1_small.setTag(s1_5.getId(), 19);
		s1_odd = view1.findViewById(R.id.s1_odd);
		// s1_odd.setTag(s1_5.getId(), 20);
		s1_even = view1.findViewById(R.id.s1_even);
		// s1_even.setTag(s1_5.getId(), 21);
		s1_big.setOnClickListener(this);
		s1_small.setOnClickListener(this);
		s1_odd.setOnClickListener(this);
		s1_even.setOnClickListener(this);
		viewList1.add(s1_4);
		viewList1.add(s1_5);
		viewList1.add(s1_6);
		viewList1.add(s1_7);
		viewList1.add(s1_8);
		viewList1.add(s1_9);
		viewList1.add(s1_10);
		viewList1.add(s1_11);
		viewList1.add(s1_12);
		viewList1.add(s1_13);
		viewList1.add(s1_14);
		viewList1.add(s1_15);
		viewList1.add(s1_16);
		viewList1.add(s1_17);
		// viewList1.add(s1_big);
		// viewList1.add(s1_small);
		// viewList1.add(s1_odd);
		// viewList1.add(s1_even);
		// s2_112, s2_113, s2_114, s2_115, s2_116, s2_223, s2_224, s2_225,
		// s2_226,
		// s2_133, s2_233, s2_334, s2_335, s2_336, s2_144, s2_244, s2_344,
		// s2_445,
		// s2_446, s2_155, s2_255, s2_355, s2_455, s2_556, s2_166, s2_266,
		// s2_366, s2_466, s2_566;
		//

		s2_112 = view2.findViewById(R.id.s2_112);
		s2_112.setTag(s1_5.getId(), 112);
		s2_113 = view2.findViewById(R.id.s2_113);
		s2_113.setTag(s1_5.getId(), 113);
		s2_114 = view2.findViewById(R.id.s2_114);
		s2_114.setTag(s1_5.getId(), 114);
		s2_115 = view2.findViewById(R.id.s2_115);
		s2_115.setTag(s1_5.getId(), 115);
		s2_116 = view2.findViewById(R.id.s2_116);
		s2_116.setTag(s1_5.getId(), 116);
		s2_122 = view2.findViewById(R.id.s2_122);
		s2_122.setTag(s1_5.getId(), 122);
		s2_223 = view2.findViewById(R.id.s2_223);
		s2_223.setTag(s1_5.getId(), 223);
		s2_224 = view2.findViewById(R.id.s2_224);
		s2_224.setTag(s1_5.getId(), 224);
		s2_225 = view2.findViewById(R.id.s2_225);
		s2_225.setTag(s1_5.getId(), 225);
		s2_226 = view2.findViewById(R.id.s2_226);
		s2_226.setTag(s1_5.getId(), 226);
		s2_133 = view2.findViewById(R.id.s2_133);
		s2_133.setTag(s1_5.getId(), 133);
		s2_233 = view2.findViewById(R.id.s2_233);
		s2_233.setTag(s1_5.getId(), 233);
		s2_334 = view2.findViewById(R.id.s2_334);
		s2_334.setTag(s1_5.getId(), 334);
		s2_335 = view2.findViewById(R.id.s2_335);
		s2_335.setTag(s1_5.getId(), 335);
		s2_336 = view2.findViewById(R.id.s2_336);
		s2_336.setTag(s1_5.getId(), 336);
		s2_144 = view2.findViewById(R.id.s2_144);
		s2_144.setTag(s1_5.getId(), 144);
		s2_244 = view2.findViewById(R.id.s2_244);
		s2_244.setTag(s1_5.getId(), 244);
		s2_344 = view2.findViewById(R.id.s2_344);
		s2_344.setTag(s1_5.getId(), 344);
		s2_445 = view2.findViewById(R.id.s2_445);
		s2_445.setTag(s1_5.getId(), 445);
		s2_446 = view2.findViewById(R.id.s2_446);
		s2_446.setTag(s1_5.getId(), 112);
		s2_155 = view2.findViewById(R.id.s2_155);
		s2_155.setTag(s1_5.getId(), 446);
		s2_255 = view2.findViewById(R.id.s2_255);
		s2_255.setTag(s1_5.getId(), 255);
		s2_355 = view2.findViewById(R.id.s2_355);
		s2_355.setTag(s1_5.getId(), 355);
		s2_455 = view2.findViewById(R.id.s2_455);
		s2_455.setTag(s1_5.getId(), 455);
		s2_556 = view2.findViewById(R.id.s2_556);
		s2_556.setTag(s1_5.getId(), 566);
		s2_166 = view2.findViewById(R.id.s2_166);
		s2_166.setTag(s1_5.getId(), 166);
		s2_266 = view2.findViewById(R.id.s2_266);
		s2_266.setTag(s1_5.getId(), 266);
		s2_366 = view2.findViewById(R.id.s2_366);
		s2_366.setTag(s1_5.getId(), 366);
		s2_466 = view2.findViewById(R.id.s2_466);
		s2_466.setTag(s1_5.getId(), 466);
		s2_566 = view2.findViewById(R.id.s2_566);
		s2_566.setTag(s1_5.getId(), 566);
		viewList2.add(s2_112);
		viewList2.add(s2_113);
		viewList2.add(s2_114);
		viewList2.add(s2_115);
		viewList2.add(s2_116);
		viewList2.add(s2_122);
		viewList2.add(s2_223);
		viewList2.add(s2_224);
		viewList2.add(s2_225);
		viewList2.add(s2_226);
		viewList2.add(s2_133);
		viewList2.add(s2_233);
		viewList2.add(s2_334);
		viewList2.add(s2_335);
		viewList2.add(s2_336);
		viewList2.add(s2_144);
		viewList2.add(s2_244);
		viewList2.add(s2_344);
		viewList2.add(s2_445);
		viewList2.add(s2_446);
		viewList2.add(s2_155);
		viewList2.add(s2_255);
		viewList2.add(s2_355);
		viewList2.add(s2_455);
		viewList2.add(s2_556);
		viewList2.add(s2_166);
		viewList2.add(s2_266);
		viewList2.add(s2_366);
		viewList2.add(s2_466);
		viewList2.add(s2_566);
		// s3_111, s3_222, s3_333, s3_444, s3_555, s3_666, s3_110, s3_220,
		// s3_330,
		// s3_440, s3_550, s3_660, s3_all;
		//

		s3_111 = view3.findViewById(R.id.s3_111);
		s3_111.setTag(s1_5.getId(), 111);
		s3_222 = view3.findViewById(R.id.s3_222);
		s3_222.setTag(s1_5.getId(), 222);
		s3_333 = view3.findViewById(R.id.s3_333);
		s3_333.setTag(s1_5.getId(), 333);
		s3_444 = view3.findViewById(R.id.s3_444);
		s3_444.setTag(s1_5.getId(), 444);
		s3_555 = view3.findViewById(R.id.s3_555);
		s3_555.setTag(s1_5.getId(), 555);
		s3_666 = view3.findViewById(R.id.s3_666);
		s3_666.setTag(s1_5.getId(), 666);
		s3_110 = view3.findViewById(R.id.s3_110);
		s3_110.setTag(s1_5.getId(), 110);
		s3_220 = view3.findViewById(R.id.s3_220);
		s3_220.setTag(s1_5.getId(), 220);
		s3_330 = view3.findViewById(R.id.s3_330);
		s3_330.setTag(s1_5.getId(), 330);
		s3_440 = view3.findViewById(R.id.s3_440);
		s3_440.setTag(s1_5.getId(), 440);
		s3_550 = view3.findViewById(R.id.s3_550);
		s3_550.setTag(s1_5.getId(), 550);
		s3_660 = view3.findViewById(R.id.s3_660);
		s3_660.setTag(s1_5.getId(), 660);
		s3_all = view3.findViewById(R.id.s3_all);
		s3_all.setTag(s1_5.getId(), 0);
		viewList3.add(s3_111);
		viewList3.add(s3_222);
		viewList3.add(s3_333);
		viewList3.add(s3_444);
		viewList3.add(s3_555);
		viewList3.add(s3_666);
		viewList3.add(s3_110);
		viewList3.add(s3_220);
		viewList3.add(s3_330);
		viewList3.add(s3_440);
		viewList3.add(s3_550);
		viewList3.add(s3_660);
		viewList3.add(s3_all);
		// s4_123, s4_124, s4_125, s4_126, s4_134, s4_135, s4_136, s4_145,
		// s4_146,
		// s4_156, s4_234, s4_235, s4_236, s4_245, s4_246, s4_256, s4_345,
		// s4_346,
		// s4_356, s4_456, s4_all;
		//

		s4_123 = view4.findViewById(R.id.s4_123);
		s4_123.setTag(s1_5.getId(), 123);
		s4_124 = view4.findViewById(R.id.s4_124);
		s4_124.setTag(s1_5.getId(), 124);
		s4_125 = view4.findViewById(R.id.s4_125);
		s4_125.setTag(s1_5.getId(), 125);
		s4_126 = view4.findViewById(R.id.s4_126);
		s4_126.setTag(s1_5.getId(), 136);
		s4_134 = view4.findViewById(R.id.s4_134);
		s4_134.setTag(s1_5.getId(), 134);
		s4_135 = view4.findViewById(R.id.s4_135);
		s4_135.setTag(s1_5.getId(), 135);
		s4_136 = view4.findViewById(R.id.s4_136);
		s4_136.setTag(s1_5.getId(), 136);
		s4_145 = view4.findViewById(R.id.s4_145);
		s4_145.setTag(s1_5.getId(), 145);
		s4_146 = view4.findViewById(R.id.s4_146);
		s4_146.setTag(s1_5.getId(), 146);
		s4_156 = view4.findViewById(R.id.s4_156);
		s4_156.setTag(s1_5.getId(), 156);
		s4_234 = view4.findViewById(R.id.s4_234);
		s4_234.setTag(s1_5.getId(), 234);
		s4_235 = view4.findViewById(R.id.s4_235);
		s4_235.setTag(s1_5.getId(), 235);
		s4_236 = view4.findViewById(R.id.s4_236);
		s4_236.setTag(s1_5.getId(), 236);
		s4_245 = view4.findViewById(R.id.s4_245);
		s4_245.setTag(s1_5.getId(), 245);
		s4_246 = view4.findViewById(R.id.s4_246);
		s4_246.setTag(s1_5.getId(), 246);
		s4_256 = view4.findViewById(R.id.s4_256);
		s4_256.setTag(s1_5.getId(), 256);
		s4_345 = view4.findViewById(R.id.s4_345);
		s4_345.setTag(s1_5.getId(), 345);
		s4_346 = view4.findViewById(R.id.s4_346);
		s4_346.setTag(s1_5.getId(), 346);
		s4_356 = view4.findViewById(R.id.s4_356);
		s4_356.setTag(s1_5.getId(), 356);
		s4_456 = view4.findViewById(R.id.s4_456);
		s4_456.setTag(s1_5.getId(), 456);
		s4_all = view4.findViewById(R.id.s4_all);
		s4_all.setTag(s1_5.getId(), 0);
		viewList4.add(s4_123);
		viewList4.add(s4_124);
		viewList4.add(s4_125);
		viewList4.add(s4_126);
		viewList4.add(s4_134);
		viewList4.add(s4_135);
		viewList4.add(s4_136);
		viewList4.add(s4_145);
		viewList4.add(s4_146);
		viewList4.add(s4_156);
		viewList4.add(s4_234);
		viewList4.add(s4_235);
		viewList4.add(s4_236);
		viewList4.add(s4_245);
		viewList4.add(s4_246);
		viewList4.add(s4_256);
		viewList4.add(s4_345);
		viewList4.add(s4_346);
		viewList4.add(s4_356);
		viewList4.add(s4_456);
		viewList4.add(s4_all);
		// s5_12, s5_13, s5_14, s5_15, s5_16, s5_23, s5_24, s5_25, s5_26, s5_34,
		// s5_34, s5_35, s5_36, s5_45, s5_46, s5_56;
		s5_12 = view5.findViewById(R.id.s5_12);
		s5_12.setTag(s1_5.getId(), 12);
		s5_13 = view5.findViewById(R.id.s5_13);
		s5_13.setTag(s1_5.getId(), 13);
		s5_14 = view5.findViewById(R.id.s5_14);
		s5_14.setTag(s1_5.getId(), 14);
		s5_15 = view5.findViewById(R.id.s5_15);
		s5_15.setTag(s1_5.getId(), 15);
		s5_16 = view5.findViewById(R.id.s5_16);
		s5_16.setTag(s1_5.getId(), 16);
		s5_23 = view5.findViewById(R.id.s5_23);
		s5_23.setTag(s1_5.getId(), 23);
		s5_24 = view5.findViewById(R.id.s5_24);
		s5_24.setTag(s1_5.getId(), 24);
		s5_25 = view5.findViewById(R.id.s5_25);
		s5_25.setTag(s1_5.getId(), 25);
		s5_26 = view5.findViewById(R.id.s5_26);
		s5_26.setTag(s1_5.getId(), 26);
		s5_34 = view5.findViewById(R.id.s5_34);
		s5_34.setTag(s1_5.getId(), 34);
		s5_35 = view5.findViewById(R.id.s5_35);
		s5_35.setTag(s1_5.getId(), 35);
		s5_36 = view5.findViewById(R.id.s5_36);
		s5_36.setTag(s1_5.getId(), 36);
		s5_45 = view5.findViewById(R.id.s5_45);
		s5_45.setTag(s1_5.getId(), 45);
		s5_46 = view5.findViewById(R.id.s5_46);
		s5_46.setTag(s1_5.getId(), 46);
		s5_56 = view5.findViewById(R.id.s5_56);
		s5_56.setTag(s1_5.getId(), 56);
		viewList5.add(s5_12);
		viewList5.add(s5_13);
		viewList5.add(s5_14);
		viewList5.add(s5_15);
		viewList5.add(s5_16);
		viewList5.add(s5_23);
		viewList5.add(s5_24);
		viewList5.add(s5_25);
		viewList5.add(s5_26);
		viewList5.add(s5_34);
		viewList5.add(s5_35);
		viewList5.add(s5_36);
		viewList5.add(s5_45);
		viewList5.add(s5_46);
		viewList5.add(s5_56);
		for (View v1 : viewList1) {
			v1.setTag(0);// 选中信息
			v1.setTag(s1_4.getId(), 1);// 页卡信息 投注种类
			v1.setTag(view2.getId(), 888);
			v1.setOnClickListener(this);
		}
		for (View v1 : viewList2) {
			v1.setTag(0);
			v1.setTag(s1_4.getId(), 2);
			v1.setTag(view2.getId(), 888);
			v1.setOnClickListener(this);
		}
		for (View v1 : viewList3) {
			v1.setTag(0);
			v1.setTag(s1_4.getId(), 3);
			v1.setTag(view2.getId(), 888);
			v1.setOnClickListener(this);
		}
		for (View v1 : viewList4) {
			v1.setTag(0);
			v1.setTag(s1_4.getId(), 4);
			v1.setTag(view2.getId(), 888);
			v1.setOnClickListener(this);
		}
		for (View v1 : viewList5) {
			v1.setTag(0);
			v1.setTag(s1_4.getId(), 5);
			v1.setTag(view2.getId(), 888);
			v1.setOnClickListener(this);
		}
		mVibretor = (Vibrator) getSystemService(VIBRATOR_SERVICE);
		handleBet = findViewById(R.id.handleBet);
		handleBet.setOnClickListener(this);
		topRightView = findViewById(R.id.topRightView);
		topRightView.setOnClickListener(this);
		billText = (TextView) findViewById(R.id.billText);
		prizeText = (TextView) findViewById(R.id.prizeText);
		paperFast3 = (ViewPager) findViewById(R.id.paperFast3);
		showNum1 = (ImageView) findViewById(R.id.showNum1);
		showNum2 = (ImageView) findViewById(R.id.showNum2);
		showNum3 = (ImageView) findViewById(R.id.showNum3);
		showNum4 = (ImageView) findViewById(R.id.showNum4);
		showNum5 = (ImageView) findViewById(R.id.showNum5);
		paperFast3.setOnPageChangeListener(new OnPageChangeListener() {

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

	private void checkBill() {
		switch (currentPage) {
		case 0:
			if (betList1.size() > 0) {
				billText.setText("共" + betList1.size() + "注" + betList1.size()
						* 2 + "元");
			} else {
				billText.setText("0");
			}
			break;
		case 1:
			if (betList2.size() > 0) {
				billText.setText("共" + betList2.size() + "注" + betList2.size()
						* 2 + "元");
			} else {
				billText.setText("0");
			}
			break;
		case 2:
			if (betList3.size() > 0) {
				billText.setText("共" + betList3.size() + "注" + betList3.size()
						* 2 + "元");
			} else {
				billText.setText("0");
			}
			break;
		case 3:
			if (betList4.size() > 0) {
				billText.setText("共" + betList4.size() + "注" + betList4.size()
						* 2 + "元");
			} else {
				billText.setText("0");
			}
			break;
		case 4:
			if (betList5.size() > 0) {
				billText.setText("共" + betList5.size() + "注" + betList5.size()
						* 2 + "元");
			} else {
				billText.setText("0");
			}
			break;

		default:
			break;
		}
	}

	private void resetThirdPage() {
		for (View v3 : viewList3) {
			v3.setBackgroundResource(R.drawable.cp);
			v3.setTag(0);
		}
	}

	private void resetSumBet() {
		s1_4.setBackgroundResource(R.drawable.cp);
		s1_4.setTag(0);
		s1_5.setBackgroundResource(R.drawable.cp);
		s1_5.setTag(0);
		s1_6.setBackgroundResource(R.drawable.cp);
		s1_6.setTag(0);
		s1_7.setBackgroundResource(R.drawable.cp);
		s1_7.setTag(0);
		s1_8.setBackgroundResource(R.drawable.cp);
		s1_8.setTag(0);
		s1_9.setBackgroundResource(R.drawable.cp);
		s1_9.setTag(0);
		s1_10.setBackgroundResource(R.drawable.cp);
		s1_10.setTag(0);
		s1_11.setBackgroundResource(R.drawable.cp);
		s1_11.setTag(0);
		s1_12.setBackgroundResource(R.drawable.cp);
		s1_12.setTag(0);
		s1_13.setBackgroundResource(R.drawable.cp);
		s1_13.setTag(0);
		s1_14.setBackgroundResource(R.drawable.cp);
		s1_14.setTag(0);
		s1_15.setBackgroundResource(R.drawable.cp);
		s1_15.setTag(0);
		s1_16.setBackgroundResource(R.drawable.cp);
		s1_16.setTag(0);
		s1_17.setBackgroundResource(R.drawable.cp);
		s1_17.setTag(0);
		s1_big.setBackgroundResource(R.drawable.cp);
		s1_small.setBackgroundResource(R.drawable.cp);
		s1_odd.setBackgroundResource(R.drawable.cp);
		s1_even.setBackgroundResource(R.drawable.cp);
	}

	private void resetSumSpecialBet(View v) {
		if (v.getId() != s1_big.getId() && v.getId() != s1_small.getId()
				&& v.getId() != s1_odd.getId() && v.getId() != s1_even.getId()) {
			s1_big.setBackgroundResource(R.drawable.cp);
			s1_small.setBackgroundResource(R.drawable.cp);
			s1_odd.setBackgroundResource(R.drawable.cp);
			s1_even.setBackgroundResource(R.drawable.cp);
		}

	}

	private void updateShowNumberView(int position) {
		switch (position) {
		case 0:
			showNum1.setImageResource(R.drawable.show_mun_this);
			showNum2.setImageResource(R.drawable.show_mun);
			showNum3.setImageResource(R.drawable.show_mun);
			showNum4.setImageResource(R.drawable.show_mun);
			showNum5.setImageResource(R.drawable.show_mun);
			break;
		case 1:
			showNum1.setImageResource(R.drawable.show_mun);
			showNum2.setImageResource(R.drawable.show_mun_this);
			showNum3.setImageResource(R.drawable.show_mun);
			showNum4.setImageResource(R.drawable.show_mun);
			showNum5.setImageResource(R.drawable.show_mun);
			break;
		case 2:
			showNum1.setImageResource(R.drawable.show_mun);
			showNum2.setImageResource(R.drawable.show_mun);
			showNum3.setImageResource(R.drawable.show_mun_this);
			showNum4.setImageResource(R.drawable.show_mun);
			showNum5.setImageResource(R.drawable.show_mun);
			break;
		case 3:
			showNum1.setImageResource(R.drawable.show_mun);
			showNum2.setImageResource(R.drawable.show_mun);
			showNum3.setImageResource(R.drawable.show_mun);
			showNum4.setImageResource(R.drawable.show_mun_this);
			showNum5.setImageResource(R.drawable.show_mun);
			break;
		case 4:
			showNum1.setImageResource(R.drawable.show_mun);
			showNum2.setImageResource(R.drawable.show_mun);
			showNum3.setImageResource(R.drawable.show_mun);
			showNum4.setImageResource(R.drawable.show_mun);
			showNum5.setImageResource(R.drawable.show_mun_this);
			break;
		}
	}

	private void alert() {
		final AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.show();

		// alert.getWindow().setLayout(DecorationApplication.cpc.changeImageX(657),
		// DecorationApplication.cpc.changeImageX(311));

		alert.getWindow().setContentView(R.layout.exit_dialog);

		TextView title = (TextView) alert.findViewById(R.id.title);
		title.setText("离开页面后将清除原页面上的投注信息，确定离开？");
		View okButton = alert.findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.dismiss();
				FastDimention.this.finish();
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

	private void settext(final int seconds) {
		final Message msg = new Message();
		msg.what = Constant.TICK_TOCK;
		timerest = seconds;
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				// new Runnable() {
				//
				// @Override
				// public void run() {
				new Thread(new Runnable() {

					@Override
					public void run() {
						while (timerest > 0) {
							timerest--;
							try {
								Thread.sleep(1000);
								mHandler.sendMessage(msg);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

					}
				}).start();

				// }
				// };
			}
		});
		// final Runnable ren = new Runnable() {
		//
		// @Override
		// public void run() {
		// if(timerest>0){
		// mHandler.sendMessage(msg);
		// timerest--;
		// mHandler.postDelayed(this, 1000);
		// }
		// }
		// };
		// mHandler.postDelayed(new Runnable() {
		//
		// @Override
		// public void run() {
		// mHandler.postDelayed(ren, 1000);

		//
		// }
		// }, 1000);
		// new Thread(new Runnable() {
		// @Override
		// public void run() {
		// int sec=seconds;
		// while (sec > 0) {
		// try {
		// Thread.sleep(999);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		// sec--;
		// mHandler.sendMessage(msg);
		// msg.arg1=sec;
		//
		// }
		// }
		// }).start();
	}

	private void setValue() {
		gameView.setValue(i);
		gameView1.setValue(j);
		if (currentPage == 4)
			return;
		gameView2.setValue(k);
	}

	class MyHandler extends Handler {
		public MyHandler() {
		}

		// public MyHandler(Looper L) {
		// super(L);
		// }

		// 子类必须重写此方法,接受数据
		@Override
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			if (msg.what == 60887) {
				Log.e("pkxh", "send3333");
				// Integer[] num = null;
				if (currentPage == 4) {
					// num = new Integer[] { gameView.indexs[0] + 1,
					// gameView1.indexs[0] + 1, 888 };
					// Arrays.sort(num);
				} else {
					// num = new Integer[] { gameView.indexs[0] + 1,
					// gameView1.indexs[0] + 1, gameView2.indexs[0] + 1 };
					// Arrays.sort(num);
				}
				// String str = num[0] + "+" + num[1] + "+" + num[2] + "="
				// + (num[0] + num[1] + num[2]);
				// Toast.makeText(FastDimention.this, str, 6000).show();

				TranslateAnimation hideAciton = new TranslateAnimation(0, 100,
						0, 100);
				hideAciton.setDuration(500);

				Animation animation = new ScaleAnimation(1, 0, 1, 0);
				animation.setDuration(50);
				animation.setFillAfter(true);
				animationSet.addAnimation(animation);
				animationSet.addAnimation(hideAciton);
				animationSet.setFillAfter(true);
				gameView.startAnimation(animationSet);
				gameView1.startAnimation(animationSet);
				if (currentPage != 4) {
					gameView2.startAnimation(animationSet);
				}
				sp.play(location, 1, 1, 0, 0, 1);
				Log.e("pkxh", "send4444");
				Constant.FAST_ANIMATION_FINISH = true;
			} else if (msg.what == 60886) {
				Log.e("pkxh", "-------------60886");
				if (!Constant.FAST_ANIMATION_FINISH) {
					Log.e("pkxh", "-------------60886  not finish");
					// Integer[] num = null;
					if (currentPage == 4) {
						// num = new Integer[] { gameView.indexs[0] + 1,
						// gameView1.indexs[0] + 1, 888 };
						// Arrays.sort(num);
					} else {
						// num = new Integer[] { gameView.indexs[0] + 1,
						// gameView1.indexs[0] + 1, gameView2.indexs[0] + 1 };
						// Arrays.sort(num);
					}
					// String str = num[0] + "+" + num[1] + "+" + num[2] + "="
					// + (num[0] + num[1] + num[2]);
					// Toast.makeText(FastDimention.this, str, 6000).show();

					TranslateAnimation hideAciton = new TranslateAnimation(0,
							100, 0, 100);
					hideAciton.setDuration(500);

					Animation animation = new ScaleAnimation(1, 0, 1, 0);
					animation.setDuration(50);
					animation.setFillAfter(true);
					animationSet.addAnimation(animation);
					animationSet.addAnimation(hideAciton);
					animationSet.setFillAfter(true);
					gameView.startAnimation(animationSet);
					gameView1.startAnimation(animationSet);
					if (currentPage != 4) {
						gameView2.startAnimation(animationSet);
					}
					sp.play(location, 1, 1, 0, 0, 1);
					Log.e("pkxh", "send444460886");
					Constant.FAST_ANIMATION_FINISH = true;
				}
			} else if (msg.what == Constant.PAGE_CHANGED) {
				Log.e("pkx", "页号：" + (msg.arg1 + 1));
				switch (msg.arg1) {
				case 0:
					// 选项卡一，和值
					currentPage = 0;
					updateShowNumberView(currentPage);
					checkBill();
					break;
				case 1:
					// 选项卡二，二同号
					currentPage = 1;
					updateShowNumberView(currentPage);
					checkBill();
					break;
				case 2:
					// 选项卡三，三同号
					currentPage = 2;
					updateShowNumberView(currentPage);
					checkBill();
					break;
				case 3:
					// 选项卡四，三不同号
					currentPage = 3;
					updateShowNumberView(currentPage);
					checkBill();
					break;
				case 4:
					// 选项卡五，二不同号
					currentPage = 4;
					updateShowNumberView(currentPage);
					checkBill();
					break;

				}
			} else if (msg.what == Constant.SHAKE_MESSAGE) {
				if (currentPage == 0) {
					int ii = sr.nextInt(6);
					int jj = sr.nextInt(6);
					int kk = sr.nextInt(6);
					Log.e("pkx", "和值可用111 ");
					while (ii + jj + kk == 0 || ii + jj + kk == 15) {
						Log.e("pkx", "和值不可用");
						ii = sr.nextInt(6);
						jj = sr.nextInt(6);
						kk = sr.nextInt(6);
					}
					i = ii;
					j = jj;
					k = kk;
					Log.e("pkx", "和值确定  i" + (i + 1) + "  j" + (j + 1) + "  k"
							+ (k + 1));
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							Message msg8 = new Message();
							msg8.what = Constant.AD_PAGE_CHANGED;
							mHandler.sendMessage(msg8);
						}
					}, 2000);
				} else if (currentPage == 1) {
					page2Select = sr.nextInt(30);
					switch (page2Select) {
					case 0:
						i = 1 - 1;
						j = 1 - 1;
						k = 2 - 1;
						break;
					case 1:
						i = 1 - 1;
						j = 1 - 1;
						k = 3 - 1;
						break;
					case 2:
						i = 1 - 1;
						j = 1 - 1;
						k = 4 - 1;
						break;
					case 3:
						i = 1 - 1;
						j = 1 - 1;
						k = 5 - 1;
						break;
					case 4:
						i = 1 - 1;
						j = 1 - 1;
						k = 6 - 1;
						break;
					case 5:
						i = 1 - 1;
						j = 2 - 1;
						k = 2 - 1;
						break;
					case 6:
						i = 2 - 1;
						j = 2 - 1;
						k = 4 - 1;
						break;
					case 7:
						i = 2 - 1;
						j = 2 - 1;
						k = 4 - 1;
						break;
					case 8:
						i = 2 - 1;
						j = 2 - 1;
						k = 5 - 1;
						break;
					case 9:
						i = 2 - 1;
						j = 2 - 1;
						k = 6 - 1;
						break;
					case 10:
						i = 1 - 1;
						j = 3 - 1;
						k = 3 - 1;
						break;
					case 11:
						i = 2 - 1;
						j = 3 - 1;
						k = 3 - 1;
						break;
					case 12:
						i = 3 - 1;
						j = 3 - 1;
						k = 4 - 1;
						break;
					case 13:
						i = 3 - 1;
						j = 3 - 1;
						k = 5 - 1;
						break;
					case 14:
						i = 3 - 1;
						j = 3 - 1;
						k = 6 - 1;
						break;
					case 15:
						i = 1 - 1;
						j = 4 - 1;
						k = 4 - 1;
						break;
					case 16:
						i = 2 - 1;
						j = 4 - 1;
						k = 4 - 1;
						break;
					case 17:
						i = 3 - 1;
						j = 4 - 1;
						k = 4 - 1;
						break;
					case 18:
						i = 4 - 1;
						j = 4 - 1;
						k = 5 - 1;
						break;
					case 19:
						i = 4 - 1;
						j = 4 - 1;
						k = 6 - 1;
						break;
					case 20:
						i = 1 - 1;
						j = 5 - 1;
						k = 5 - 1;
						break;
					case 21:
						i = 2 - 1;
						j = 5 - 1;
						k = 5 - 1;
						break;
					case 22:
						i = 3 - 1;
						j = 5 - 1;
						k = 5 - 1;
						break;
					case 23:
						i = 4 - 1;
						j = 5 - 1;
						k = 5 - 1;
						break;
					case 24:
						i = 5 - 1;
						j = 5 - 1;
						k = 6 - 1;
						break;
					case 25:
						i = 1 - 1;
						j = 6 - 1;
						k = 6 - 1;
						break;
					case 26:
						i = 2 - 1;
						j = 6 - 1;
						k = 6 - 1;
						break;
					case 27:
						i = 3 - 1;
						j = 6 - 1;
						k = 6 - 1;
						break;
					case 28:
						i = 4 - 1;
						j = 6 - 1;
						k = 6 - 1;
						break;
					case 29:
						i = 5 - 1;
						j = 6 - 1;
						k = 6 - 1;
						break;
					}
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							Message msg8 = new Message();
							msg8.what = Constant.AD_PAGE_CHANGED;
							mHandler.sendMessage(msg8);
						}
					}, 2000);

				} else if (currentPage == 2) {
					i = sr.nextInt(6);
					j = i;
					k = j;
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							Message msg8 = new Message();
							msg8.what = Constant.AD_PAGE_CHANGED;
							mHandler.sendMessage(msg8);
						}
					}, 2000);
				} else if (currentPage == 3) {
					page3Select = sr.nextInt(20);
					switch (page3Select) {
					case 0:
						i = 0;
						j = 1;
						k = 2;
						break;
					case 1:
						i = 0;
						j = 1;
						k = 3;
						break;
					case 2:
						i = 0;
						j = 1;
						k = 4;
						break;
					case 3:
						i = 0;
						j = 1;
						k = 5;
						break;
					case 4:
						i = 0;
						j = 2;
						k = 3;
						break;
					case 5:
						i = 0;
						j = 2;
						k = 4;
						break;
					case 6:
						i = 0;
						j = 2;
						k = 6;
						break;
					case 7:
						i = 0;
						j = 3;
						k = 4;
						break;
					case 8:
						i = 0;
						j = 3;
						k = 5;
						break;
					case 9:
						i = 0;
						j = 4;
						k = 5;
						break;
					case 10:
						i = 1;
						j = 2;
						k = 3;
						break;
					case 11:
						i = 1;
						j = 2;
						k = 4;
						break;
					case 12:
						i = 1;
						j = 2;
						k = 5;
						break;
					case 13:
						i = 1;
						j = 3;
						k = 4;
						break;
					case 14:
						i = 1;
						j = 3;
						k = 5;
						break;
					case 15:
						i = 1;
						j = 4;
						k = 5;
						break;
					case 16:
						i = 2;
						j = 3;
						k = 4;
						break;
					case 17:
						i = 2;
						j = 3;
						k = 5;
						break;
					case 18:
						i = 2;
						j = 4;
						k = 5;
						break;
					case 19:
						i = 3;
						j = 4;
						k = 5;
						break;
					}
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							Message msg8 = new Message();
							msg8.what = Constant.AD_PAGE_CHANGED;
							mHandler.sendMessage(msg8);
						}
					}, 2000);
				} else if (currentPage == 4) {
					page4Select = sr.nextInt(15);
					switch (page4Select) {
					case 0:
						i = 0;
						j = 1;
						k = 0;
						break;
					case 1:
						i = 0;
						j = 2;
						k = 0;
						break;
					case 2:
						i = 0;
						j = 3;
						k = 0;
						break;
					case 3:
						i = 0;
						j = 4;
						k = 0;
						break;
					case 4:
						i = 0;
						j = 5;
						k = 0;
						break;
					case 5:
						i = 1;
						j = 2;
						k = 0;
						break;
					case 6:
						i = 1;
						j = 3;
						k = 0;
						break;
					case 7:
						i = 1;
						j = 4;
						k = 0;
						break;
					case 8:
						i = 1;
						j = 5;
						k = 0;
						break;
					case 9:
						i = 2;
						j = 3;
						k = 0;
						break;
					case 10:
						i = 2;
						j = 4;
						k = 0;
						break;
					case 11:
						i = 2;
						j = 5;
						k = 0;
						break;
					case 12:
						i = 3;
						j = 4;
						k = 0;
						break;
					case 13:
						i = 3;
						j = 5;
						k = 0;
						break;
					case 14:
						i = 4;
						j = 5;
						k = 0;
						break;
					}
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							Message msg8 = new Message();
							msg8.what = Constant.AD_PAGE_CHANGED;
							mHandler.sendMessage(msg8);
						}
					}, 2000);
				}
				if (canShake) {
					startVibrato();
					canShake = false;
					mHandler.postDelayed(new Runnable() {

						@Override
						public void run() {
							canShake = true;
						}
					}, 4000);
					Constant.FAST_ANIMATION_FINISH = false;
					Message msgMessage = new Message();
					msgMessage.what = 60886;
					mHandler.sendMessageDelayed(msgMessage, 4000);
					if (count == 0) {
						count++;
						sp.play(music, 1, 1, 0, 0, 1);
						gameView = new DiceView(FastDimention.this,
								points.get(0), width, height, sp, 0, mHandler);
						gameView1 = new DiceView(FastDimention.this,
								points.get(1), width, height, sp, 1, mHandler);
						if (currentPage != 4) {
							gameView2 = new DiceView(FastDimention.this,
									points.get(2), width, height, sp, 2,
									mHandler);

						}
						setValue();

						addContentView(gameView, new LayoutParams(-2, -2));
						addContentView(gameView1, new LayoutParams(-2, -2));
						gameView.startThread();
						gameView1.startThread();
						if (currentPage != 4) {
							addContentView(gameView2, new LayoutParams(-2, -2));
							gameView2.startThread();
						}

					} else {
						Log.e("杩炵画鐐瑰嚮", (gameView != null && !gameView.flag)
								+ "");
						if (gameView != null && !gameView.flag) {// 濡傛灉涓婃鐨勭嚎绋嬫病鏈夎窇浜嗘墠鍏佽寮�惎姝ゆ绾跨▼
							sp.play(music, 1, 1, 0, 0, 1);
							gameView = new DiceView(FastDimention.this,
									points.get(0), width, height, sp, 0,
									mHandler);
							gameView1 = new DiceView(FastDimention.this,
									points.get(1), width, height, sp, 1,
									mHandler);

							if (currentPage != 4) {
								gameView2 = new DiceView(FastDimention.this,
										points.get(2), width, height, sp, 2,
										mHandler);
								addContentView(gameView2, new LayoutParams(-2,
										-2));
								gameView2.startThread();
							}
							setValue();
							addContentView(gameView, new LayoutParams(-2, -2));
							addContentView(gameView1, new LayoutParams(-2, -2));
							gameView.startThread();
							gameView1.startThread();
						}
					}
				}
				// randomSelectBet();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
			} else if (msg.what == Constant.AD_PAGE_CHANGED) {
				if (currentPage == 0) {
					View v = viewList1.get(i + j + k - 1);
					betList1.clear();
					for (View vl1 : viewList1) {
						vl1.setBackgroundResource(R.drawable.cp);
						vl1.setTag(0);
					}
					s1_big.setBackgroundResource(R.drawable.cp);
					s1_small.setBackgroundResource(R.drawable.cp);
					s1_odd.setBackgroundResource(R.drawable.cp);
					s1_even.setBackgroundResource(R.drawable.cp);
					v.setBackgroundResource(R.drawable.bp);
					v.setTag(1);
					FastBet bet = new FastBet();
					bet.setCurrentPageId(currentPage);
					bet.setBetNumber((Integer) v.getTag(s1_5.getId()));
					betList1.add(bet);
				} else if (currentPage == 1) {
					View v2 = viewList2.get(page2Select);
					betList2.clear();
					for (View vl1 : viewList2) {
						vl1.setBackgroundResource(R.drawable.cp);
						vl1.setTag(0);
					}
					v2.setBackgroundResource(R.drawable.bp);
					v2.setTag(1);
					FastBet bet = new FastBet();
					bet.setCurrentPageId(currentPage);
					bet.setBetNumber((Integer) v2.getTag(s1_5.getId()));
					betList2.add(bet);
				} else if (currentPage == 2) {
					View v3 = viewList3.get(i);
					betList3.clear();
					for (View vl1 : viewList3) {
						vl1.setBackgroundResource(R.drawable.cp);
						vl1.setTag(0);
					}
					v3.setBackgroundResource(R.drawable.bp);
					v3.setTag(1);
					FastBet bet = new FastBet();
					bet.setCurrentPageId(currentPage);
					bet.setBetNumber((Integer) v3.getTag(s1_5.getId()));
					betList3.add(bet);
				} else if (currentPage == 3) {
					View v4 = viewList4.get(page3Select);
					betList4.clear();
					for (View vl1 : viewList4) {
						vl1.setBackgroundResource(R.drawable.cp);
						vl1.setTag(0);
					}
					v4.setBackgroundResource(R.drawable.bp);
					v4.setTag(1);
					FastBet bet = new FastBet();
					bet.setCurrentPageId(currentPage);
					bet.setBetNumber((Integer) v4.getTag(s1_5.getId()));
					betList4.add(bet);
				} else if (currentPage == 4) {
					View v5 = viewList5.get(page4Select);
					betList5.clear();
					for (View vl1 : viewList5) {
						vl1.setBackgroundResource(R.drawable.cp);
						vl1.setTag(0);
					}
					v5.setBackgroundResource(R.drawable.bp);
					v5.setTag(1);
					FastBet bet = new FastBet();
					bet.setCurrentPageId(currentPage);
					bet.setBetNumber((Integer) v5.getTag(s1_5.getId()));
					betList5.add(bet);
				}
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				if (msg.arg1 == Constant.NET_ACTION_LOTHIS) {
					JSONObject ojo = (JSONObject) msg.obj;
					try {
						JSONArray ja = new JSONArray(
								ojo.getString("peroidList"));
						Log.e("pkx", "ja size:" + ja.length());
						for (int i = 0; i < ja.length(); i++) {
							lotts.add(Net.gson.fromJson(ja.get(i).toString(),
									ChormLott.class));
						}
						pastDialog.show();
						pastDialog.getWindow().setContentView(dialogView);
						showListAdapter.notifyDataSetChanged();
						Log.e("pkx", "lotts size:" + lotts.size());
						for (ChormLott lo : lotts) {
							Log.e("pkx", "pid:" + lo.getPeroidID() + " name:"
									+ lo.getPeroid_name());
						}
					} catch (JSONException e) {
						Log.e("pkx", "ja------>JSONException");
						e.printStackTrace();
					}
					Log.e("pkx", "往期彩期3d加载成功：" + ojo.toString());
				} else if (msg.arg1 == Constant.NET_ACTION_REFRESH) {
					JSONObject ojo = (JSONObject) msg.obj;
					try {
						JSONArray ja = new JSONArray(
								ojo.getString("peroidList"));
						Log.e("pkx", "ja size:" + ja.length());
						lotts.clear();
						for (int i = 0; i < ja.length(); i++) {
							lotts.add(Net.gson.fromJson(ja.get(i).toString(),
									ChormLott.class));
						}
						pastDialog.show();
						pastDialog.getWindow().setContentView(dialogView);
						showListAdapter.notifyDataSetChanged();
						Log.e("pkx", "lotts size:" + lotts.size());
						for (ChormLott lo : lotts) {
							Log.e("pkx", "pid:" + lo.getPeroidID() + " name:"
									+ lo.getPeroid_name());
						}
						// showList.stopRefresh();
						showPullList.onRefreshComplete();
						Toast.makeText(FastDimention.this, "刷新成功",
								Toast.LENGTH_SHORT).show();
					} catch (JSONException e) {
						Log.e("pkx", "ja------>JSONException");
						e.printStackTrace();
					}
				} else if (msg.arg1 == Constant.NET_ACTION_LOADMORE) {

					JSONObject ojo = (JSONObject) msg.obj;
					try {
						JSONArray ja = new JSONArray(
								ojo.getString("peroidList"));
						Log.e("pkx", "ja size:" + ja.length());
						for (int i = 0; i < ja.length(); i++) {
							lotts.add(Net.gson.fromJson(ja.get(i).toString(),
									ChormLott.class));
						}
						pastDialog.show();
						pastDialog.getWindow().setContentView(dialogView);
						showListAdapter.notifyDataSetChanged();
						Log.e("pkx", "lotts size:" + lotts.size());
						for (ChormLott lo : lotts) {
							Log.e("pkx", "pid:" + lo.getPeroidID() + " name:"
									+ lo.getPeroid_name());
						}
						page++;
						// showList.stopLoadMore();
						showPullList.onRefreshComplete();
						Toast.makeText(FastDimention.this, "加载成功",
								Toast.LENGTH_SHORT).show();
					} catch (JSONException e) {
						Log.e("pkx", "ja------>JSONException");
						e.printStackTrace();
					}

				} else if (msg.arg1 == Constant.NET_ACTION_SECRET_SECURE) {
					fastLastShowView.setVisibility(View.VISIBLE);
					fastTimeView.setVisibility(View.VISIBLE);
					fastGridView.setVisibility(View.VISIBLE);
					Log.e("pkx",
							"msg.arg1 == Constant.NET_ACTION_SECRET_SECURE");
					// 快三当前其次信息
					JSONObject jo = (JSONObject) msg.obj;
					FastCur fa = Net.gson
							.fromJson(jo.toString(), FastCur.class);
					if (fa.getCurFast().getStart_second() == 0) {
						paperFast3.setVisibility(View.VISIBLE);
						wholeView.setVisibility(View.VISIBLE);
						nextView.setVisibility(View.GONE);
						if (fa.getCurFast().getBalls() != null
								&& fa.getCurFast().getBalls().size() == 3) {
							prizeText.setText(fa
									.getLastFast()
									.getPeroid_name()
									.substring(
											fa.getLastFast().getPeroid_name()
													.length() - 3,
											fa.getLastFast().getPeroid_name()
													.length())
									+ "期:和值"
									+ String.valueOf(Integer.valueOf((fa
											.getLastFast().getBalls().get(0)
											+ fa.getLastFast().getBalls()
													.get(1) + fa.getLastFast()
											.getBalls().get(2)))));
							ball1.setImageResource(fastLogos.get((fa
									.getCurFast().getBalls().get(0) - 1)));
							ball2.setImageResource(fastLogos.get((fa
									.getCurFast().getBalls().get(1) - 1)));
							ball3.setImageResource(fastLogos.get((fa
									.getCurFast().getBalls().get(2) - 1)));
							curText.setText(fa
									.getCurFast()
									.getPeroid_name()
									.substring(
											fa.getCurFast().getPeroid_name()
													.length() - 3)
									+ "期截止");
						} else {
							curText.setText(fa
									.getCurFast()
									.getPeroid_name()
									.substring(
											fa.getCurFast().getPeroid_name()
													.length() - 3)
									+ "期截止");
							prizeText.setText(fa
									.getLastFast()
									.getPeroid_name()
									.substring(
											fa.getLastFast().getPeroid_name()
													.length() - 3,
											fa.getLastFast().getPeroid_name()
													.length())
									+ "期和值-");
							ball1.setImageResource(R.drawable.cp);
							ball2.setImageResource(R.drawable.cp);
							ball3.setImageResource(R.drawable.cp);
						}
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
						wholeView.setVisibility(View.GONE);
						nextView.setVisibility(View.VISIBLE);
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

			if (msg.what == Constant.TICK_TOCK) {
				if (nextView.getVisibility() == View.GONE) {
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
						nextView.setVisibility(View.VISIBLE);
						wholeView.setVisibility(View.GONE);
						RequestParams params = new RequestParams();
						params.put("type", "4");
						Net.post(true, FastDimention.this, Constant.PAY_URL
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
						timerest = 480;
						nextView.setVisibility(View.GONE);
						wholeView.setVisibility(View.VISIBLE);
						RequestParams params = new RequestParams();
						params.put("type", "4");
						Net.post(true, FastDimention.this, Constant.PAY_URL
								+ "/ajax/index.php?act=load_lottery_info",
								params, mHandler,
								Constant.NET_ACTION_SECRET_SECURE);
					}
				}

			}

		}
	}

	private void randomSelectBet() {
		FastBet bet;
		// if(currentPage!=2){
		startVibrato();
		// }
		switch (currentPage) {
		case 0:
			int c1 = new Random().nextInt(viewList1.size());
			View v = viewList1.get(c1);
			betList1.clear();
			for (View vl1 : viewList1) {
				vl1.setBackgroundResource(R.drawable.cp);
				vl1.setTag(0);
			}
			s1_big.setBackgroundResource(R.drawable.cp);
			s1_small.setBackgroundResource(R.drawable.cp);
			s1_odd.setBackgroundResource(R.drawable.cp);
			s1_even.setBackgroundResource(R.drawable.cp);
			v.setBackgroundResource(R.drawable.bp);
			v.setTag(1);
			bet = new FastBet();
			bet.setCurrentPageId(currentPage);
			bet.setBetNumber((Integer) v.getTag(s1_5.getId()));
			betList1.add(bet);
			break;
		case 1:
			int c2 = new Random().nextInt(viewList2.size());
			View v2 = viewList2.get(c2);
			betList2.clear();
			for (View vl1 : viewList2) {
				vl1.setBackgroundResource(R.drawable.cp);
				vl1.setTag(0);
			}
			v2.setBackgroundResource(R.drawable.bp);
			v2.setTag(1);
			bet = new FastBet();
			bet.setCurrentPageId(currentPage);
			bet.setBetNumber((Integer) v2.getTag(s1_5.getId()));
			betList2.add(bet);
			break;
		case 2:
			int c3 = new Random().nextInt(viewList3.size());
			View v3 = viewList3.get(c3);
			betList3.clear();
			for (View vl1 : viewList3) {
				vl1.setBackgroundResource(R.drawable.cp);
				vl1.setTag(0);
			}
			v3.setBackgroundResource(R.drawable.bp);
			v3.setTag(1);
			bet = new FastBet();
			bet.setCurrentPageId(currentPage);
			bet.setBetNumber((Integer) v3.getTag(s1_5.getId()));
			betList3.add(bet);
			break;
		case 3:
			int c4 = new Random().nextInt(viewList4.size());
			View v4 = viewList4.get(c4);
			betList4.clear();
			for (View vl1 : viewList4) {
				vl1.setBackgroundResource(R.drawable.cp);
				vl1.setTag(0);
			}
			v4.setBackgroundResource(R.drawable.bp);
			v4.setTag(1);
			bet = new FastBet();
			bet.setCurrentPageId(currentPage);
			bet.setBetNumber((Integer) v4.getTag(s1_5.getId()));
			betList4.add(bet);
			break;
		case 4:
			int c5 = new Random().nextInt(viewList5.size());
			View v5 = viewList5.get(c5);
			betList5.clear();
			for (View vl1 : viewList5) {
				vl1.setBackgroundResource(R.drawable.cp);
				vl1.setTag(0);
			}
			v5.setBackgroundResource(R.drawable.bp);
			v5.setTag(1);
			bet = new FastBet();
			bet.setCurrentPageId(currentPage);
			bet.setBetNumber((Integer) v5.getTag(s1_5.getId()));
			betList5.add(bet);
			break;

		default:
			break;
		}
		// v.setBackgroundResource(R.drawable.bp);
		// v.setTag(1);
		// bet = new FastBet();
		// bet.setCurrentPageId(0);
		// bet.setBetNumber((Integer) v.getTag(s1_5.getId()));
		// betList1.add(bet);
	}

	public class MyThread implements Runnable {

		@Override
		public void run() {

			while (timerest > 0) {
				if (!Constant.TICK_TOCK_FLAG) {
					timerest = -1;
					break;
				}
				timerest--;
				final Message ticktockmsg = new Message();
				ticktockmsg.what = Constant.TICK_TOCK;
				Log.e("pkx", "while" + timerest);
				mHandler.sendMessage(ticktockmsg);
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
