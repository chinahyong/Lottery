package com.pkx.lottery;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.bean.FastNewBet;
import com.pkx.lottery.dto.ChormBetAuth;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;
import com.pkx.lottery.wheel.LottBetWheel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class FastCheck extends Activity implements OnClickListener {
	private Intent mIntent;
	private FastNewBet inBet;
	private ArrayList<FastNewBet> betList;
	private ImageView addBetHandly, addBetRandomly;
	SharePreferenceUtil sutil;
	private AlertDialog followalert;
	private LottBetWheel doubleWheel;
	private View handicView, randomView, doubleView, followView;
	private int bet_follow = 0;
	private int bet_double = 1;
	private ListView billList;
	private Random random;
	private TextView checkBillText, doubleBetText, follow, followText,
			lotteryName, phoneRules;
	private BaseAdapter betAdapter;
	private LayoutInflater inflater;
	private int editPosition;
	private MyHandler mHandler;
	private int timerest;
	private View footer, pay;
	private Thread mythread;
	private int playType = -1;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fast_new_check);
		Constant.TICK_TOCK_CHECK_FLAG = true;
		initViews();
		switch (mIntent.getIntExtra("type", -1)) {
		case 0:
			lotteryName.setText("湖北快三");
			playType = 0;
			break;
		case 1:
			lotteryName.setText("江苏快三");
			playType = 1;
			break;
		}
		random = new Random();
		mythread = new Thread(new MyThread());
		Constant.TICK_TOCK_FLAG = true;
		checkBill();
		mythread.start();

		phoneRules = (TextView) findViewById(R.id.phoneRules);
		phoneRules.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent();
				intent1.putExtra("weburl", Constant.RULES_URL);
				intent1.setClass(FastCheck.this, WebviewActivity.class);
				intent1.putExtra("type", 1);
				startActivity(intent1);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("pkx", "--------onActivityResult11111111");
		if (resultCode == RESULT_OK) {
			Log.e("pkx", "resultCode==RESULT_OK");
		}
		if (requestCode == 0 && null != data) {
			Log.e("pkx", "--------onActivityResult");
			FastNewBet newbet = (FastNewBet) data.getSerializableExtra("bet");
			timerest = data.getIntExtra("time", -1);
			Log.e("pkx", "tocheck time:" + timerest);
			Constant.TICK_TOCK_CHECK_FLAG = true;
			new Thread(new MyThread()).start();
			Constant.TICK_TOCK_FLAG = true;
			if (null != newbet) {
				Log.e("pkx", "null != newbet");
				betList.add(newbet);
				checkBill();
			}
			Message msg = new Message();
			msg.what = 1;
			mHandler.sendMessage(msg);
		} else if (requestCode == 1 && null != data) {

		} else if (requestCode == Constant.FAST_LOGIN) {
			if (sutil.getLoginStatus()) {
				Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "未登陆", Toast.LENGTH_SHORT).show();
			}
		}

	}

	private void checkBill() {
		int totalPrice = 0;
		if (betList.size() == 0) {
			checkBillText.setText("0");
			return;
		}
		for (FastNewBet f : betList) {
			totalPrice += f.getBalls().size() * 2;
		}
		checkBillText.setText("共" + String.valueOf(betList.size()) + "注"
				+ String.valueOf(totalPrice * bet_double * (bet_follow + 1))
				+ "元");
	}

	private void alertFollow(final int type, final int number) {
		followalert.show();
		followalert.getWindow().setContentView(R.layout.followbet_dialog);
		ListView list = (ListView) followalert.findViewById(R.id.followList);
		TextView title = (TextView) followalert.findViewById(R.id.title);
		if (type == 1) {
			title.setText("选择期数");
		}
		BaseAdapter adapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(
							R.layout.followbet_list_item, null);
				}
				if (type == 0) {

					TextView text = (TextView) convertView
							.findViewById(R.id.number);
					text.setText("" + (position + 1) + "倍");
				} else {
					TextView text = (TextView) convertView
							.findViewById(R.id.number);
					text.setText("" + position + "期");
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
				return number;
			}
		};
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				if (type == 0) {
					bet_double = position + 1;
					doubleBetText.setText("" + bet_double);
				} else {
					bet_follow = position;
					followText.setText("" + bet_follow);
				}
				followalert.dismiss();
				checkBill();
			}
		});
		list.setSelected(true);
		list.setDividerHeight(1);
		list.setAdapter(adapter);
		if (type == 0) {
			if (bet_double > 2) {

				list.setSelection(bet_double - 3);
			} else {
				list.setSelection(0);
			}
		} else {
			if (bet_follow > 2) {
				list.setSelection(bet_follow - 3);
			} else {
				list.setSelection(0);
			}
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.addBetHandly:
		case R.id.handicView:
			Constant.isHandlySelect = true;
			Intent toFast = new Intent(this, FastDimention.class);
			toFast.putExtra("time", timerest);
			toFast.putExtra("type", playType);
			startActivityForResult(toFast, 0);
			Constant.TICK_TOCK_CHECK_FLAG = false;
			checkBill();
			break;
		case R.id.pay:
			Log.e("pkx", "loginStatus:" + sutil.getLoginStatus());
			if (sutil.getLoginStatus()) {
				if (betList.size() == 0) {
					Toast.makeText(this, "至少投一注...", Toast.LENGTH_SHORT).show();
					return;
				}
				// if(playType==0){//快三

				int totalPrice = 0;
				for (FastNewBet f : betList) {
					totalPrice += f.getBalls().size() * 2;
				}
				FastInfo info = new FastInfo(betList, 1, 0);
				Log.e("pkx", "UID:" + sutil.getuid());
				Log.e("pkx", "追号：" + String.valueOf(bet_follow));
				ChormBetAuth cb = new ChormBetAuth(sutil.getuid(),
						String.valueOf(totalPrice * bet_double),
						String.valueOf(info.getBuy_count()),
						String.valueOf(bet_double), info.getBetInfo(), "0",
						bet_follow + 1);
				String chormMingwen = Net.gson.toJson(cb);
				String chromMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
						chormMingwen);
				PublicAllAuth all;
				if (playType == 0) {
					all = new PublicAllAuth("betK3", chromMiwen);
				} else {
					all = new PublicAllAuth("betJSK3", chromMiwen);
				}
				String allMingwen = Net.gson.toJson(all);
				String allMiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allMingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", allMiwen);
				Log.e("pkx", "FAST:" + chormMingwen);
				Net.post(true, this, Constant.POST_URL + "/bet.api.php",
						params, mHandler, Constant.NET_ACTION_DIMENTIONBET);
				// }else{//江苏快三
				//
				// }
			} else {
				Intent toLogin = new Intent(FastCheck.this, LoginActivity.class);
				toLogin.putExtra("fastlogin", true);
				startActivityForResult(toLogin, Constant.FAST_LOGIN);
			}

			break;
		case R.id.doubleView:
			doubleWheel.showWheel(bet_double - 1, 0);
			// alertFollow(0, 99);
			break;
		case R.id.followView:
			doubleWheel.showWheel(bet_follow, 1);
			// alertFollow(0, 99);
			break;
		case R.id.addBetRandomly:
		case R.id.randomView:
			FastNewBet nbet = new FastNewBet();
			nbet.setCurrentPage(0);
			int o = random.nextInt(14);
			ArrayList<Integer> os = new ArrayList<Integer>();
			os.add(o + 4);
			nbet.setBalls(os);
			betList.add(nbet);
			betAdapter.notifyDataSetChanged();
			checkBill();
			break;
		default:
			break;
		}

	}

	private void initViews() {
		// followText = (TextView) findViewById(R.id.followText);
		followView = findViewById(R.id.followView);
		followView.setOnClickListener(this);
		handicView = findViewById(R.id.handicView);
		handicView.setOnClickListener(this);
		randomView = findViewById(R.id.randomView);
		randomView.setOnClickListener(this);
		followalert = new AlertDialog.Builder(this).create();
		sutil = new SharePreferenceUtil(this);
		mHandler = new MyHandler();
		doubleWheel = new LottBetWheel(this, mHandler);
		pay = findViewById(R.id.pay);
		pay.setOnClickListener(this);
		lotteryName = (TextView) findViewById(R.id.lotteryName);
		lotteryName.setText("快三");
		mIntent = getIntent();
		betList = new ArrayList<FastNewBet>();
		inBet = (FastNewBet) mIntent.getSerializableExtra("bet");
		timerest = mIntent.getIntExtra("time", -1);
		betList.add(inBet);
		inflater = getLayoutInflater();
		footer = inflater.inflate(R.layout.checklist_footer, null);
		addBetHandly = (ImageView) findViewById(R.id.addBetHandly);
		addBetHandly.setOnClickListener(this);
		addBetRandomly = (ImageView) findViewById(R.id.addBetRandomly);
		addBetRandomly.setOnClickListener(this);
		billList = (ListView) findViewById(R.id.billList);
		billList.setDividerHeight(1);
		doubleView = findViewById(R.id.doubleView);
		doubleView.setOnClickListener(this);
		doubleBetText = (TextView) findViewById(R.id.doubleBetText);
		// follow = (TextView) findViewById(R.id.follow);
		// follow.setOnClickListener(this);
		followText = (TextView) findViewById(R.id.followText);
		followText.setOnClickListener(this);
		betAdapter = new BaseAdapter() {

			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.fast_bet_listitem,
							null);
				}
				TextView billText = (TextView) convertView
						.findViewById(R.id.billText);
				TextView ballText = (TextView) convertView
						.findViewById(R.id.redBallText);
				billText.setText("共" + betList.get(position).getBalls().size()
						* 2 + "元");
				ballText.setText(betList.get(position).getBetString());
				if (betList.size() > 0) {
					footer.setVisibility(View.VISIBLE);
				} else {
					footer.setVisibility(View.GONE);
				}
				ImageView delete = (ImageView) convertView
						.findViewById(R.id.deleteItem);
				delete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Log.e("pkx", "positon:" + position);
						Message msg = new Message();
						msg.what = Constant.DELETE_LIST_ITEM;
						msg.arg1 = position;
						mHandler.sendMessage(msg);
					}
				});
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public Object getItem(int arg0) {
				return null;
			}

			@Override
			public int getCount() {
				return betList.size();
			}
		};
		billList.addFooterView(footer);
		billList.setAdapter(betAdapter);
		checkBillText = (TextView) findViewById(R.id.checkBillText);
		billList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (betList == null || betList.size() == 0
						|| position >= betList.size()) {
					if (betList != null && betList.size() > 0
							&& position >= betList.size()) {
						alert1();
					}
					return;
				}
				// clickBet = betList.get(position);
				// editPosition = position;
				// Constant.isHandlySelect = true;
				// Constant.isHandlyEdit = true;
				// Intent addHand = new Intent(DoubleChromosphereCheck.this,
				// MyDoubleChromosphere.class);
				// Bundle clickBundle = new Bundle();
				// Log.e("clickBet", clickBet.getBetString());
				// clickBundle.putSerializable("editBet", clickBet);
				// addHand.putExtras(clickBundle);
				// startActivityForResult(addHand, 1);

			}
		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Constant.TICK_TOCK_CHECK_FLAG = false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			alert();
		}
		return super.onKeyDown(keyCode, event);
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

	@SuppressLint("NewApi")
	private void alertMoneyLess() {
		final AlertDialog alert = new AlertDialog.Builder(this, R.style.dialog)
				.create();
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
				intent1.setClass(FastCheck.this, WebviewActivity.class);
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

	private void alert1() {
		final AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.show();
		alert.getWindow().setContentView(R.layout.exit_dialog);

		TextView title = (TextView) alert.findViewById(R.id.title);
		title.setText("清除所有投注信息？");
		View okButton = alert.findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				betList.clear();
				checkBill();
				betAdapter.notifyDataSetChanged();
				footer.setVisibility(View.GONE);
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
			if (msg.what == Constant.DOUBLE_FOLLOW) {
				doubleBetText.setText("" + msg.arg1);
				followText.setText("" + msg.arg2);
				checkBill();
			} else if (msg.what == Constant.DELETE_LIST_ITEM) {
				betList.remove(msg.arg1);
				betAdapter.notifyDataSetChanged();
				checkBill();
			} else if (msg.what == 1) {
				betAdapter.notifyDataSetChanged();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				// NET
				// json:{"error":0,"message":"\u8bf7\u6c42\u6210\u529f","data":750,"status":1}
				Log.e("pkx", "POST_SUCCESS" + msg.obj);
				JSONObject jo = (JSONObject) msg.obj;
				String orderID = "ID 获取失败";
				String message = "";
				try {
					if ("10001".equals(jo.get("error").toString())) {
						Intent toLogin = new Intent(FastCheck.this,
								LoginActivity.class);
						toLogin.putExtra("fastlogin", true);
						startActivityForResult(toLogin, Constant.FAST_LOGIN);
					} else if ("0".equals(jo.get("error").toString())
							|| "20000".equals(jo.get("error").toString())) {
						Toast.makeText(FastCheck.this, "投注成功",
								Toast.LENGTH_SHORT).show();
						Intent toHome = new Intent(FastCheck.this,
								HomeActivity.class);
						startActivity(toHome);
					} else if ("20010".equals(jo.get("error").toString())) {
						alertMoneyLess();

					} else if ("10009".equals(jo.get("error").toString())) {
						Toast.makeText(FastCheck.this, "当期期号不存在，稍候再试",
								Toast.LENGTH_SHORT).show();
					} else {
						Constant.alertWarning(FastCheck.this,
								jo.get("message").toString());
						Toast.makeText(FastCheck.this,
								jo.get("message").toString(), Toast.LENGTH_LONG)
								.show();
						// message = "error:" + jo.get("error").toString();
						// Toast.makeText(FastCheck.this,
						// message + " orderID->" + orderID,
						// Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			} else if (msg.what == Constant.POST_FAIL) {
				Toast.makeText(FastCheck.this, "请求超时", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				Toast.makeText(FastCheck.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == Constant.WHEEL_SELECTED) {
				if (msg.arg1 == 0) {
					bet_double = msg.arg2 + 1;
					doubleBetText.setText(String.valueOf(msg.arg2 + 1) + "倍");
					checkBill();
				} else if (msg.arg1 == 1) {
					bet_follow = msg.arg2;
					followText.setText(String.valueOf(msg.arg2) + "期");
					checkBill();
				}
			}

		}
	}

	public class MyThread implements Runnable {

		@Override
		public void run() {

			while (timerest > 0) {
				if (!Constant.TICK_TOCK_CHECK_FLAG) {
					timerest = -1;
					break;
				}
				final Message ticktockmsg = new Message();
				ticktockmsg.what = Constant.TICK_TOCK;
				Log.e("pkx", "check" + timerest);
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
