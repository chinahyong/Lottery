package com.pkx.lottery;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.bean.SevenLotteryBet;
import com.pkx.lottery.bean.SevenLotteryInfo;
import com.pkx.lottery.dto.ChormBetAuth;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.RandomBallsUtils;
import com.pkx.lottery.utils.SharePreferenceUtil;
import com.pkx.lottery.wheel.LottBetWheel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SevenLotteryCheck extends Activity implements OnClickListener {
	private Intent mIntent;
	private LottBetWheel doubleWheel;
	private SevenLotteryBet inBet, clickBet, editBet;
	private ArrayList<SevenLotteryBet> betList;
	private ListView betListview;
	private AlertDialog followalert;
	private BaseAdapter betAdapter;
	private int bet_follow = 0;
	private View handicView, randomView;
	private int bet_double = 1;
	SharePreferenceUtil sutil;
	private LayoutInflater inflater;
	private int editPosition;
	private TextView billText, doubleBetText, followText, phoneRules;
	private ImageView addBetHandly, addBetRandomly;
	private View footer, pay, corpView, doubleView, followView;
	private Handler mHandler;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sevenlottery_check);
		initViews();
		phoneRules = (TextView) findViewById(R.id.phoneRules);
		phoneRules.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent();
				intent1.putExtra("weburl", Constant.RULES_URL);
				intent1.setClass(SevenLotteryCheck.this, WebviewActivity.class);
				intent1.putExtra("type", 1);
				startActivity(intent1);
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && data != null) {
			inBet = (SevenLotteryBet) data.getSerializableExtra("sbet");
			if (null != inBet) {
				betList.add(inBet);
				betAdapter.notifyDataSetChanged();
			}
		} else if (requestCode == 1 && data != null) {
			editBet = (SevenLotteryBet) data.getSerializableExtra("sbet");
			betList.set(editPosition, editBet);
			betAdapter.notifyDataSetChanged();
		} else if (requestCode == Constant.FAST_LOGIN) {
			if (sutil.getLoginStatus()) {
				Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "未登陆", Toast.LENGTH_SHORT).show();
			}
		}
		checkBill();
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addBetHandly:
		case R.id.handicView:
			Constant.isHandlySelect = true;
			Intent addBet = new Intent(this, SevenLottery.class);
			startActivityForResult(addBet, 0);
			break;
		case R.id.addBetRandomly:
		case R.id.randomView:
			SevenLotteryBet randomBet = new SevenLotteryBet();
			ArrayList<Integer> redBalls = new ArrayList<Integer>();
			for (int i : RandomBallsUtils.getRandomBalls(7, 30)) {
				redBalls.add(i + 1);
			}
			randomBet.setBalls(redBalls);
			// betList.add(randomBet);
			betList.add(0, randomBet);
			betAdapter.notifyDataSetChanged();
			checkBill();
			break;
		case R.id.doubleView:
			Log.e("pkx", "背投");
			doubleWheel.showWheel(bet_double - 1, 0);
			break;
		case R.id.followView:
			Log.e("pkx", "追号");
			doubleWheel.showWheel(bet_follow, 1);
			break;
		case R.id.corpView:
			if (bet_follow > 0) {
				Toast.makeText(SevenLotteryCheck.this, "合买不能设置追号！",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (sutil.getLoginStatus()) {
				if (betList.size() == 0) {
					Toast.makeText(this, "至少投一注...", Toast.LENGTH_SHORT).show();
					return;
				}
				SevenLotteryInfo info = new SevenLotteryInfo(betList,
						bet_double, 0);
				ChormBetAuth cb = new ChormBetAuth(sutil.getuid(),
						String.valueOf(info.getBuy_amount()),
						String.valueOf(info.getBuy_count()),
						String.valueOf(bet_double), info.getBetInfo(), "0", 0);
				String chormMingwen = Net.gson.toJson(cb);
				Intent toCorp = new Intent(this, CorperMaking.class);
				toCorp.putExtra("allMiwen", chormMingwen);
				toCorp.putExtra("type", 3);// 3七乐彩
				toCorp.putExtra("totalPrice", checkBill());// 3七乐彩
				startActivity(toCorp);
			} else {
				Intent toLogin = new Intent(SevenLotteryCheck.this,
						LoginActivity.class);
				toLogin.putExtra("fastlogin", true);
				startActivityForResult(toLogin, Constant.FAST_LOGIN);
			}
			break;
		case R.id.pay:
			if (sutil.getLoginStatus()) {
				if (betList.size() == 0) {
					Toast.makeText(this, "至少投一注...", Toast.LENGTH_SHORT).show();
					return;
				}
				SevenLotteryInfo info = new SevenLotteryInfo(betList,
						bet_double, 0);
				ChormBetAuth cb = new ChormBetAuth(sutil.getuid(),
						String.valueOf(info.getBuy_amount()),
						String.valueOf(info.getBuy_count()),
						String.valueOf(bet_double), info.getBetInfo(), "0",
						bet_follow);
				String chormMingwen = Net.gson.toJson(cb);
				String chromMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
						chormMingwen);
				PublicAllAuth all = new PublicAllAuth("betQLC", chromMiwen);
				String allMingwen = Net.gson.toJson(all);
				String allMiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allMingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", allMiwen);
				Net.post(true, this, Constant.POST_URL + "/bet.api.php",
						params, mHandler, Constant.NET_ACTION_SENCENBET);

				Log.e("pkx", "info:" + info.getBetInfo());
			} else {
				Intent toLogin = new Intent(SevenLotteryCheck.this,
						LoginActivity.class);
				toLogin.putExtra("fastlogin", true);
				startActivityForResult(toLogin, Constant.FAST_LOGIN);
			}

			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			alert();
		}
		return super.onKeyDown(keyCode, event);
	}

	private int checkBill() {
		int price = 0;
		if (betList != null && betList.size() > 0) {
			for (SevenLotteryBet b : betList) {
				price += b.getPrice();
			}
			billText.setText("共" + betList.size() + "注" + price * bet_double
					* (bet_follow + 1) + "元");
		} else {
			billText.setText("0");
		}
		return price * bet_double;

	}

	private void initViews() {
		handicView = findViewById(R.id.handicView);
		handicView.setOnClickListener(this);
		doubleView = findViewById(R.id.doubleView);
		doubleView.setOnClickListener(this);
		followView = findViewById(R.id.followView);
		followView.setOnClickListener(this);
		corpView = findViewById(R.id.corpView);
		corpView.setOnClickListener(this);
		randomView = findViewById(R.id.randomView);
		randomView.setOnClickListener(this);
		followalert = new AlertDialog.Builder(this).create();
		doubleBetText = (TextView) findViewById(R.id.doubleBetText);
		followText = (TextView) findViewById(R.id.followText);
		// doubleBetText.setOnClickListener(this);
		// followText.setOnClickListener(this);
		mHandler = new MyHandler();
		doubleWheel = new LottBetWheel(this, mHandler);
		sutil = new SharePreferenceUtil(this);
		inflater = getLayoutInflater();
		footer = inflater.inflate(R.layout.checklist_footer, null);
		pay = findViewById(R.id.pay);
		pay.setOnClickListener(this);
		billText = (TextView) findViewById(R.id.billText);
		addBetHandly = (ImageView) findViewById(R.id.addBetHandly);
		addBetHandly.setOnClickListener(this);
		addBetRandomly = (ImageView) findViewById(R.id.addBetRandomly);
		addBetRandomly.setOnClickListener(this);
		betList = new ArrayList<SevenLotteryBet>();
		mIntent = getIntent();
		inBet = (SevenLotteryBet) mIntent.getSerializableExtra("sbet");
		if (null != inBet) {
			betList.add(inBet);
			checkBill();
		}
		betListview = (ListView) findViewById(R.id.billList);
		betListview.setDividerHeight(1);
		betAdapter = new BaseAdapter() {

			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				if (convertView == null) {
					convertView = (LinearLayout) inflater.inflate(
							R.layout.seven_bet_listitem, null);
				}
				TextView billText = (TextView) convertView
						.findViewById(R.id.billText);
				TextView ballText = (TextView) convertView
						.findViewById(R.id.redBallText);
				ImageView delete = (ImageView) convertView
						.findViewById(R.id.deleteItem);
				ballText.setText(betList.get(position).getBetStr());
				billText.setText(String.valueOf(betList.get(position)
						.getPrice()) + "元");
				if (betList.size() > 0) {
					footer.setVisibility(View.VISIBLE);
				} else {
					footer.setVisibility(View.GONE);
				}
				delete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						betList.remove(position);
						betAdapter.notifyDataSetChanged();
						if (betList.size() == 0)
							footer.setVisibility(View.GONE);
						// Log.e("pkx", "positon:" + position);
						// Message msg = new Message();
						// msg.what = Constant.DELETE_LIST_ITEM;
						// msg.arg1 = position;
						// mHandler.sendMessage(msg);
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
		betListview.addFooterView(footer);
		betListview.setAdapter(betAdapter);
		betListview.setOnItemClickListener(new OnItemClickListener() {

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
				clickBet = betList.get(position);
				editPosition = position;
				Constant.isHandlySelect = true;
				Constant.isHandlyEdit = true;
				Intent addHand = new Intent(SevenLotteryCheck.this,
						SevenLottery.class);
				Bundle clickBundle = new Bundle();
				clickBundle.putSerializable("editBet", clickBet);
				addHand.putExtras(clickBundle);
				startActivityForResult(addHand, 1);

			}
		});

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
				intent1.setClass(SevenLotteryCheck.this, WebviewActivity.class);
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
				betAdapter.notifyDataSetChanged();
				footer.setVisibility(View.GONE);
				checkBill();
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
			if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				// NET
				// json:{"error":0,"message":"\u8bf7\u6c42\u6210\u529f","data":750,"status":1}
				Log.e("pkx", "POST_SUCCESS" + msg.obj);
				JSONObject jo = (JSONObject) msg.obj;
				String orderID = "ID 获取失败";
				String message = "";
				try {
					if ("10001".equals(jo.get("error").toString())) {
						Intent toLogin = new Intent(SevenLotteryCheck.this,
								LoginActivity.class);
						toLogin.putExtra("fastlogin", true);
						startActivityForResult(toLogin, Constant.FAST_LOGIN);
					} else if ("0".equals(jo.get("error").toString())
							|| "20000".equals(jo.get("error").toString())) {
						Toast.makeText(SevenLotteryCheck.this, "投注成功",
								Toast.LENGTH_SHORT).show();
						Intent toHome = new Intent(SevenLotteryCheck.this,
								HomeActivity.class);
						startActivity(toHome);
					} else if ("20010".equals(jo.get("error").toString())) {
						alertMoneyLess();
						return;

					} else {
						Constant.alertWarning(SevenLotteryCheck.this,
								jo.get("message").toString());
						Toast.makeText(SevenLotteryCheck.this,
								jo.get("message").toString(), Toast.LENGTH_LONG)
								.show();
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

			} else if (msg.what == Constant.POST_FAIL) {
				Toast.makeText(SevenLotteryCheck.this, "请求超时",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				Toast.makeText(SevenLotteryCheck.this, msg.obj.toString(),
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == Constant.WHEEL_SELECTED) {
				if (msg.arg1 == 0) {
					bet_double = msg.arg2 + 1;
					doubleBetText.setText(String.valueOf(msg.arg2 + 1) + "倍");
					checkBill();
				} else {
					bet_follow = msg.arg2;
					followText.setText(String.valueOf(msg.arg2) + "期");
					checkBill();
				}
			}

		}
	}
}
