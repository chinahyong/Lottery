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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.bean.DimensionBet;
import com.pkx.lottery.bean.DimentionInfo;
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

public class ThreeDimensionCheck extends Activity implements OnClickListener {
	private ListView billList;
	private LottBetWheel doubleWheel;
	private ArrayList<DimensionBet> betList;
	private AlertDialog followalert;
	private View handicView, randomView, corpView, followView, doubleView;
	// private int handleDouble = 1;
	private BaseAdapter adapter;
	private LayoutInflater inflater;
	SharePreferenceUtil sutil;
	private Intent mIntent;
	private ImageView addBetHandly, addBetRandomly;
	private DimensionBet inBet, clickBet;
	private int editPosition = 0;
	private View footer, pay;
	private int bet_follow = 0;
	private int bet_double = 1;
	private Handler mHandler;
	private Random random;
	private TextView doubleBetText, followText, checkBillText, phoneRules;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.three_dimension_check);
		initViews();
		checkBill();
		phoneRules = (TextView) findViewById(R.id.phoneRules);
		phoneRules.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent();
				intent1.putExtra("weburl", Constant.RULES_URL);
				intent1.setClass(ThreeDimensionCheck.this,
						WebviewActivity.class);
				intent1.putExtra("type", 1);
				startActivity(intent1);
			}
		});
	}

	private int checkBill() {
		int totalPrice = 0;
		for (DimensionBet bet : betList) {
			totalPrice += bet.getPrice();
		}
		// totalPrice *= bet_double;
		// if (bet_follow > 0) {
		// totalPrice *= bet_follow + 1;
		// }
		if (totalPrice > 0) {
			checkBillText.setText("共" + (totalPrice / 2) + "注" + totalPrice
					* bet_double * (bet_follow + 1) + "元");
		} else {
			checkBillText.setText("0");
			return 0;
		}
		return totalPrice * bet_double * (bet_follow + 1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addBetHandly:
		case R.id.handicView:
			Constant.isHandlySelect = true;
			Intent toAdd = new Intent(this, ThreeDimension.class);
			startActivityForResult(toAdd, 0);
			break;
		case R.id.back:
			alert();
			break;
		case R.id.corpView:
			if (bet_follow > 0) {
				Toast.makeText(ThreeDimensionCheck.this, "合买不能设置追号！",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (sutil.getLoginStatus()) {
				DimentionInfo info = new DimentionInfo(betList, bet_double, 0);
				ChormBetAuth cb = new ChormBetAuth(sutil.getuid(),
						String.valueOf(checkBill() / (bet_follow + 1)),
						String.valueOf(checkBill() / 2 / bet_double
								/ (bet_follow + 1)),
						String.valueOf(bet_double), info.getBetInfo(), "0", 1);
				// ChormBetAuth cb = new ChormBetAuth(sutil.getuid(),
				// String.valueOf(checkBill() / (bet_follow + 1)),
				// String.valueOf(checkBill() / 2 / bet_double),
				// String.valueOf(bet_double), info.getBetInfo(), "0", 1);
				// ChormBetAuth cb = new ChormBetAuth(sutil.getuid(),
				// String.valueOf(checkBill() / bet_double
				// / (bet_follow + 1)), String.valueOf(checkBill()
				// / 2 / bet_double / (bet_follow + 1)),
				// String.valueOf(bet_double), info.getBetInfo(), "0",
				// bet_follow + 1);
				String chormMingwen = Net.gson.toJson(cb);
				Log.e("pkx", "3D:" + chormMingwen);
				Intent toCorp = new Intent(this, CorperMaking.class);
				toCorp.putExtra("allMiwen", chormMingwen);
				toCorp.putExtra("type", 4);// 4 3D
				toCorp.putExtra("totalPrice", checkBill());
				startActivity(toCorp);
				// Log.e("pkx", "mingwen:" + chormMingwen);
				// String chromMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
				// chormMingwen);
				// PublicAllAuth all = new PublicAllAuth("bet3D", chromMiwen);
				// String allMingwen = Net.gson.toJson(all);
				// String allMiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
				// allMingwen);
				// RequestParams params = new RequestParams();
				// params.put("SID", sutil.getSID());
				// params.put("SN", sutil.getSN());
				// params.put("DATA", allMiwen);
				// Net.post(true, this, "http://api.60888.la/bet.api.php",
				// params,
				// mHandler, Constant.NET_ACTION_DIMENTIONBET);
				// Log.e("pkx", "info" + info.getBetInfo());
			} else {
				Intent toLogin = new Intent(ThreeDimensionCheck.this,
						LoginActivity.class);
				toLogin.putExtra("fastlogin", true);
				startActivityForResult(toLogin, Constant.FAST_LOGIN);
			}

			break;
		case R.id.pay:
			if (sutil.getLoginStatus()) {
				DimentionInfo info = new DimentionInfo(betList, bet_double, 0);
				// new ChormBetAuth(uid, buy_amount, buy_count, buy_multiy,
				// betInfo, buyType, follow)
				ChormBetAuth cb = new ChormBetAuth(sutil.getuid(),
						String.valueOf(checkBill() / (bet_follow + 1)),
						String.valueOf(checkBill() / 2 / bet_double
								/ (bet_follow + 1)),
						String.valueOf(bet_double), info.getBetInfo(), "0",
						bet_follow + 1);
				String chormMingwen = Net.gson.toJson(cb);
				Log.e("pkx", "mingwen:" + chormMingwen);
				String chromMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
						chormMingwen);
				PublicAllAuth all = new PublicAllAuth("bet3D", chromMiwen);
				String allMingwen = Net.gson.toJson(all);
				String allMiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allMingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", allMiwen);
				Net.post(true, this, Constant.POST_URL + "/bet.api.php",
						params, mHandler, Constant.NET_ACTION_DIMENTIONBET);
				Log.e("pkx", "info" + info.getBetInfo());
			} else {
				Intent toLogin = new Intent(ThreeDimensionCheck.this,
						LoginActivity.class);
				toLogin.putExtra("fastlogin", true);
				startActivityForResult(toLogin, Constant.FAST_LOGIN);
			}

			break;
		case R.id.addBetRandomly:
		case R.id.randomView:
			int g = random.nextInt(10);
			int s = random.nextInt(10);
			int b = random.nextInt(10);
			ArrayList<Integer> gs = new ArrayList<Integer>();
			ArrayList<Integer> ss = new ArrayList<Integer>();
			ArrayList<Integer> bs = new ArrayList<Integer>();
			gs.add(g);
			bs.add(b);
			ss.add(s);
			DimensionBet dbet = new DimensionBet();
			dbet.setType(0);
			dbet.setgBalls(gs);
			dbet.settBalls(ss);
			dbet.sethBalls(bs);
			betList.add(dbet);
			adapter.notifyDataSetChanged();
			checkBill();
			break;
		case R.id.followView:
			doubleWheel.showWheel(bet_follow, 1);
			// alertFollow(1, 13);
			break;
		case R.id.doubleView:
			doubleWheel.showWheel(bet_double - 1, 0);
			// alertFollow(0, 999);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			alert();
			// new AlertDialog.Builder(this)
			// .setTitle("退出清除投注信息，是否退出？")
			// .setNegativeButton("取消",
			// new DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface d, int which) {
			// d.dismiss();
			// }
			// })
			// .setPositiveButton("确定",
			// new DialogInterface.OnClickListener() {
			// public void onClick(DialogInterface d, int which) {
			// d.dismiss();
			// finish();
			// }
			// }).show();
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0) {
			// 手动添加
			if (data != null) {
				DimensionBet addBet = (DimensionBet) data
						.getSerializableExtra("bet");
				if (addBet != null) {
					betList.add(addBet);
				}
			}
			adapter.notifyDataSetChanged();
		} else if (requestCode == 1) {
			// 修改
			if (data != null) {
				DimensionBet editBet = (DimensionBet) data
						.getSerializableExtra("bet");
				if (editBet != null) {
					betList.set(editPosition, editBet);
				}
				adapter.notifyDataSetChanged();
			}
		} else if (requestCode == Constant.FAST_LOGIN) {
			if (sutil.getLoginStatus()) {
				Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "未登陆", Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void initViews() {
		corpView = findViewById(R.id.corpView);
		corpView.setOnClickListener(this);
		handicView = findViewById(R.id.handicView);
		handicView.setOnClickListener(this);
		randomView = findViewById(R.id.randomView);
		randomView.setOnClickListener(this);
		mHandler = new MyHandler();
		doubleWheel = new LottBetWheel(this, mHandler);
		sutil = new SharePreferenceUtil(this);
		checkBillText = (TextView) findViewById(R.id.checkBillText);
		followalert = new AlertDialog.Builder(this).create();
		random = new Random();
		followView = findViewById(R.id.followView);
		followView.setOnClickListener(this);
		doubleView = findViewById(R.id.doubleView);
		doubleView.setOnClickListener(this);
		// doubleBet = (TextView) findViewById(R.id.doubleBet);
		// doubleBet.setOnClickListener(this);
		doubleBetText = (TextView) findViewById(R.id.doubleBetText);
		// follow = (TextView) findViewById(R.id.follow);
		// follow.setOnClickListener(this);
		followText = (TextView) findViewById(R.id.followText);
		pay = findViewById(R.id.pay);
		pay.setOnClickListener(this);
		addBetHandly = (ImageView) findViewById(R.id.addBetHandly);
		addBetRandomly = (ImageView) findViewById(R.id.addBetRandomly);
		addBetHandly.setOnClickListener(this);
		addBetRandomly.setOnClickListener(this);
		mIntent = getIntent();
		betList = new ArrayList<DimensionBet>();
		inBet = (DimensionBet) mIntent.getSerializableExtra("bet");
		if (inBet != null) {
			betList.add(inBet);
		}
		billList = (ListView) findViewById(R.id.billList);
		billList.setDividerHeight(1);
		inflater = getLayoutInflater();
		footer = inflater.inflate(R.layout.checklist_footer, null);
		adapter = new BaseAdapter() {

			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(
							R.layout.dimention_bet_listitem, null);
				}
				TextView ballText = (TextView) convertView
						.findViewById(R.id.redBallText);
				TextView billText = (TextView) convertView
						.findViewById(R.id.billText);
				ballText.setText(betList.get(position).getBetString());
				billText.setText("共" + betList.get(position).getPrice() + "元");
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
			public Object getItem(int position) {
				return betList.get(position);
			}

			@Override
			public int getCount() {
				return betList.size();
			}
		};
		billList.addFooterView(footer);
		billList.setAdapter(adapter);
		billList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (betList == null || betList.size() == 0
						|| position >= betList.size()) {
					if (betList != null && betList.size() > 0
							&& position >= betList.size()) {
						Message msg = new Message();
						msg.what = Constant.CLEAR_BET;
						mHandler.sendMessage(msg);
					}
					return;
				}
				clickBet = betList.get(position);
				editPosition = position;
				Constant.isHandlySelect = true;
				Constant.isHandlyEdit = true;
				Intent addHand = new Intent(ThreeDimensionCheck.this,
						ThreeDimension.class);
				Bundle clickBundle = new Bundle();
				clickBundle.putSerializable("editBet", clickBet);
				addHand.putExtras(clickBundle);
				startActivityForResult(addHand, 1);

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
				adapter.notifyDataSetChanged();
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
				intent1.setClass(ThreeDimensionCheck.this,
						WebviewActivity.class);
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

	class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// 子类必须重写此方法,接受数据
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == Constant.CLEAR_BET) {
				alert1();
			} else if (msg.what == Constant.DELETE_LIST_ITEM) {
				checkBill();
				betList.remove(msg.arg1);
				adapter.notifyDataSetChanged();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				// NET
				// json:{"error":0,"message":"\u8bf7\u6c42\u6210\u529f","data":750,"status":1}
				Log.e("pkx", "POST_SUCCESS" + msg.obj);
				JSONObject jo = (JSONObject) msg.obj;
				String orderID = "ID 获取失败";
				String message = "";
				try {
					if ("10001".equals(jo.get("error").toString())) {
						Intent toLogin = new Intent(ThreeDimensionCheck.this,
								LoginActivity.class);
						toLogin.putExtra("fastlogin", true);
						startActivityForResult(toLogin, Constant.FAST_LOGIN);
					} else if ("0".equals(jo.get("error").toString())
							|| "20000".equals(jo.get("error").toString())) {
						Toast.makeText(ThreeDimensionCheck.this, "投注成功",
								Toast.LENGTH_SHORT).show();
						Intent toHome = new Intent(ThreeDimensionCheck.this,
								HomeActivity.class);
						startActivity(toHome);
					} else if ("20010".equals(jo.get("error").toString())) {
						alertMoneyLess();
						return;

					} else {
						Constant.alertWarning(ThreeDimensionCheck.this,
								jo.get("message").toString());
						Toast.makeText(ThreeDimensionCheck.this,
								jo.get("message").toString(), Toast.LENGTH_LONG)
								.show();
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

			} else if (msg.what == Constant.POST_FAIL) {
				Toast.makeText(ThreeDimensionCheck.this, "请求超时",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				Toast.makeText(ThreeDimensionCheck.this, msg.obj.toString(),
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
