package com.pkx.lottery;

import java.util.ArrayList;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;
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
import android.view.Window;
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
import com.pkx.lottery.bean.ChromBetinfo;
import com.pkx.lottery.bean.ChromosphereBet;
import com.pkx.lottery.dto.ChormBetAuth;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.dto.lott.LottWEF;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.RandomBallsUtils;
import com.pkx.lottery.utils.SharePreferenceUtil;
import com.pkx.lottery.wheel.LottBetWheel;

//sdakfjlk
public class DoubleChromosphereCheck extends Activity implements
		OnClickListener {
	private ArrayList<ChromosphereBet> betList;
	private ImageView addBetHandly, addBetRandomly, rulesImage;
	private LottBetWheel doubleWheel;
	private Intent mIntent;
	private View handicView, randomView, followView, doubleView;
	private AlertDialog followalert;
	private SharePreferenceUtil sutil;
	private int bet_follow = 0;
	private int bet_double = 1;
	private ChromosphereBet addBet, clickBet, editBet;
	private ListView billList;
	private TextView checkBillText, doubleBetText, followText, rulesText,
			phoneRules;
	private BaseAdapter betAdapter;
	private LayoutInflater inflater;
	private int editPosition;
	private MyHandler mHandler;
	private View footer, pay, corper;
	private boolean checkRule;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.doublechromosphere_check);
		betList = new ArrayList<ChromosphereBet>();
		mIntent = getIntent();
		addBet = (ChromosphereBet) mIntent.getSerializableExtra("bet");
		if (addBet != null) {
			betList.add(addBet);
		}
		initViews();
		checkBill();

		phoneRules = (TextView) findViewById(R.id.phoneRules);
		phoneRules.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent1 = new Intent();
				intent1.putExtra("weburl", Constant.RULES_URL);
				intent1.setClass(DoubleChromosphereCheck.this,
						WebviewActivity.class);
				intent1.putExtra("type", 1);
				startActivity(intent1);
			}
		});
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			super.handleMessage(msg);
			if (msg.what == Constant.DOUBLE_FOLLOW) {
				Log.e("msg.what==Constant.DOUBLE_FOLLOW",
						"----msg.what==Constant.DOUBLE_FOLLOW");
				doubleBetText.setText("" + msg.arg1);
				followText.setText("" + msg.arg2);
			} else if (msg.what == Constant.DELETE_LIST_ITEM) {
				Log.e("msg.what==Constant.DELETE_LIST_ITEM",
						"----msg.what==Constant.DELETE_LIST_ITEM");

				betList.remove(msg.arg1);
				if (betList.size() == 0)
					footer.setVisibility(View.GONE);
				betAdapter.notifyDataSetChanged();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				// NET
				// json:{"error":0,"message":"\u8bf7\u6c42\u6210\u529f","data":750,"status":1}
				Log.e("pkx", "POST_SUCCESS" + msg.obj);
				JSONObject jo = (JSONObject) msg.obj;
				String orderID = "ID 获取失败";
				String message = "";
				try {
					if ("0".equals(jo.get("error").toString())
							|| "20000".equals(jo.get("error").toString())) {
						Toast.makeText(DoubleChromosphereCheck.this, "投注成功",
								Toast.LENGTH_SHORT).show();
						Intent toHome = new Intent(
								DoubleChromosphereCheck.this,
								HomeActivity.class);
						startActivity(toHome);
						// message = jo.getString("message");
						// orderID = jo.getString("data");
					} else if ("20010".equals(jo.get("error").toString())) {
						alertMoneyLess();
						return;

					} else if ("10001".equals(jo.get("error").toString())) {
						Intent toLogin = new Intent(
								DoubleChromosphereCheck.this,
								LoginActivity.class);
						toLogin.putExtra("fastlogin", true);
						startActivityForResult(toLogin, Constant.FAST_LOGIN);
						return;
					} else {
						Constant.alertWarning(DoubleChromosphereCheck.this,
								jo.get("message").toString());
						Toast.makeText(DoubleChromosphereCheck.this,
								jo.get("message").toString(), Toast.LENGTH_LONG)
								.show();
						// message = "error:" + jo.get("error").toString();
						// Toast.makeText(DoubleChromosphereCheck.this,
						// message + " orderID->" + orderID,
						// Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

			} else if (msg.what == Constant.POST_FAIL) {
				Toast.makeText(DoubleChromosphereCheck.this, "请求超时",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				Toast.makeText(DoubleChromosphereCheck.this,
						msg.obj.toString(), Toast.LENGTH_SHORT).show();
			}

		};
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && null != data) {
			addBet = (ChromosphereBet) data.getSerializableExtra("bet");
			if (addBet != null) {
				betList.add(addBet);
			}
			betAdapter.notifyDataSetChanged();
			checkBill();
		} else if (requestCode == 1 && null != data) {
			editBet = (ChromosphereBet) data.getSerializableExtra("bet");
			betList.set(editPosition, editBet);
			betAdapter.notifyDataSetChanged();
			checkBill();
		} else if (requestCode == Constant.FAST_LOGIN) {
			if (sutil.getLoginStatus()) {
				Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "未登陆", Toast.LENGTH_SHORT).show();
			}
		}

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
			Intent addHand = new Intent(this, MyDoubleChromosphere.class);
			startActivityForResult(addHand, 0);
			break;
		case R.id.back:
			alert();
			break;
		case R.id.addBetRandomly:
		case R.id.randomView:
			ChromosphereBet randomBet = new ChromosphereBet();
			ArrayList<Integer> redBalls = new ArrayList<Integer>();
			for (int i : RandomBallsUtils.getRandomBalls(6, 33)) {
				redBalls.add(i + 1);
			}
			randomBet.setRedBalls(redBalls);
			ArrayList<Integer> blueball = new ArrayList<Integer>();
			blueball.add(new Random().nextInt(16) + 1);
			randomBet.setBlueBalls(blueball);
			randomBet.setType(0);
			betList.add(0, randomBet);
			betAdapter.notifyDataSetChanged();
			checkBill();
			break;
		case R.id.doubleView:
			doubleWheel.showWheel(bet_double - 1, 0);
			// alertFollow(0, 99);
			break;
		case R.id.followView:
			doubleWheel.showWheel(bet_follow, 1);
			// alertFollow(1, 23);
			// ChinesePickerDialog dialog = new ChinesePickerDialog(this, 99,
			// 50);
			// dialog.setOnChooseSetListener(new OnChooseSetListener() {
			// public void OnChooseSet(AlertDialog dialog, String week,
			// String end) {
			// Message msg = new Message();
			// msg.what = Constant.DOUBLE_FOLLOW;
			// msg.arg1 = Integer.valueOf(week);
			// msg.arg2 = Integer.valueOf(end);
			// handler.sendMessage(msg);
			// }
			//
			// });
			// dialog.show();
			break;
		case R.id.corper:
			if (bet_follow > 0) {
				Toast.makeText(DoubleChromosphereCheck.this, "合买不能设置追号！",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (sutil.getLoginStatus()) {
				if (betList.size() == 0) {
					Toast.makeText(this, "至少投一注...", Toast.LENGTH_SHORT).show();
					return;
				}
				ChromBetinfo info = new ChromBetinfo(betList, bet_double, 0);
				ChormBetAuth cb = new ChormBetAuth(sutil.getuid(),
						String.valueOf(info.getBuy_amount()),
						String.valueOf(info.getBuy_count()),
						String.valueOf(bet_double), info.getBetInfo(), "1", 0);
				String chormMingwen = Net.gson.toJson(cb);
				Log.e("pkx", "合买追号：" + chormMingwen);
				// String chromMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
				// chormMingwen);
				// PublicAllAuth all = new PublicAllAuth("betSSQ", chromMiwen);
				// String allMingwen = Net.gson.toJson(all);
				// String allMiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
				// allMingwen);
				Intent toCorp = new Intent(this, CorperMaking.class);
				toCorp.putExtra("allMiwen", chormMingwen);
				toCorp.putExtra("type", 1);// 1双色球
				toCorp.putExtra("totalPrice", checkBill());// 1双色球
				startActivity(toCorp);
				// RequestParams params = new RequestParams();
				// Log.e("pkx", "SID:" + sutil.getSID());
				// params.put("SID", sutil.getSID());
				// params.put("SN", sutil.getSN());
				// params.put("DATA", allMiwen);
				// Log.e("pkx", "mingwen1:" + chormMingwen + "  miwen1:"
				// + chromMiwen + " mingwen2:" + allMingwen + " miwen2:"
				// + allMiwen);
				// Net.post(true, this, "http://api.60888.la/bet.api.php",
				// params,
				// handler, Constant.NET_ACTION_CHORMBET);
				// Log.e("pkx",
				// "info:" + info.getBetInfo() + "---buy_count"
				// + info.getBuy_count() + "----buy_amunt"
				// + info.getBuy_amount());

			} else {
				Intent toLogin = new Intent(DoubleChromosphereCheck.this,
						LoginActivity.class);
				toLogin.putExtra("fastlogin", true);
				startActivityForResult(toLogin, Constant.FAST_LOGIN);
			}
			break;
		// case R.id.rules:
		// // 服务协议
		// break;
		case R.id.rulesImage:
		case R.id.rulesText:
			if (checkRule) {
				checkRule = false;
				rulesImage.setImageResource(R.drawable.rules_normal);
			} else {
				checkRule = true;
				rulesImage.setImageResource(R.drawable.rules_selected);
			}
			break;
		case R.id.pay:
			if (!checkRule) {
				Toast.makeText(this, "请确认已同意《用户服务协议》", Toast.LENGTH_SHORT)
						.show();
				return;
			}
			if (sutil.getLoginStatus()) {
				if (betList.size() == 0) {
					Toast.makeText(this, "至少投一注...", Toast.LENGTH_SHORT).show();
					return;
				}
				ChromBetinfo info = new ChromBetinfo(betList, bet_double, 0);
				ChormBetAuth cb = new ChormBetAuth(sutil.getuid(),
						String.valueOf(info.getBuy_amount()),
						String.valueOf(info.getBuy_count()),
						String.valueOf(bet_double), info.getBetInfo(), "0",
						bet_follow + 1);
				Log.e("pkx", "追号" + (bet_follow + 1));
				String chormMingwen = Net.gson.toJson(cb);
				String chromMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
						chormMingwen);
				PublicAllAuth all = new PublicAllAuth("betSSQ", chromMiwen);
				String allMingwen = Net.gson.toJson(all);
				String allMiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allMingwen);
				RequestParams params = new RequestParams();
				Log.e("pkx", "SID:" + sutil.getSID());
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", allMiwen);
				Log.e("pkx", "mingwen1:" + chormMingwen + "  miwen1:"
						+ chromMiwen + " mingwen2:" + allMingwen + " miwen2:"
						+ allMiwen);
				Net.post(true, this, Constant.POST_URL + "/bet.api.php",
						params, handler, Constant.NET_ACTION_CHORMBET);
				Log.e("pkx",
						"info:" + info.getBetInfo() + "---buy_count"
								+ info.getBuy_count() + "----buy_amunt"
								+ info.getBuy_amount());

			} else {
				Intent toLogin = new Intent(DoubleChromosphereCheck.this,
						LoginActivity.class);
				toLogin.putExtra("fastlogin", true);
				startActivityForResult(toLogin, Constant.FAST_LOGIN);
			}
			// if (!checkRule) {
			// Toast.makeText(this, "请确认已同意《用户服务协议》", Toast.LENGTH_SHORT)
			// .show();
			// return;
			// }
			// if (sutil.getLoginStatus()) {
			// if (betList.size() == 0) {
			// Toast.makeText(this, "至少投一注...", Toast.LENGTH_SHORT).show();
			// return;
			// }
			// ChromBetinfo info = new ChromBetinfo(betList, bet_double, 0);
			// ChormBetAuth cb = new ChormBetAuth(sutil.getuid(),
			// String.valueOf(info.getBuy_amount() * bet_double),
			// String.valueOf(info.getBuy_count()),
			// String.valueOf(bet_double), info.getBetInfo(), "0");
			// String chormMingwen = Net.gson.toJson(cb);
			// String chromMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
			// chormMingwen);
			// PublicAllAuth all = new PublicAllAuth("betSSQ", chromMiwen);
			// String allMingwen = Net.gson.toJson(all);
			// String allMiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
			// allMingwen);
			// RequestParams params = new RequestParams();
			// Log.e("pkx", "SID:" + sutil.getSID());
			// params.put("SID", sutil.getSID());
			// params.put("SN", sutil.getSN());
			// params.put("DATA", allMiwen);
			// Log.e("pkx", "mingwen1:" + chormMingwen + "  miwen1:"
			// + chromMiwen + " mingwen2:" + allMingwen + " miwen2:"
			// + allMiwen);
			// Net.post(true, this, "http://api.60888.la/bet.api.php", params,
			// handler, Constant.NET_ACTION_CHORMBET);
			// Log.e("pkx",
			// "info:" + info.getBetInfo() + "---buy_count"
			// + info.getBuy_count() + "----buy_amunt"
			// + info.getBuy_amount());
			//
			// } else {
			// Intent toLogin = new Intent(DoubleChromosphereCheck.this,
			// LoginActivity.class);
			// toLogin.putExtra("fastlogin", true);
			// startActivityForResult(toLogin, Constant.FAST_LOGIN);
			// }
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
			// // Intent toExt=new Intent(this, ExitActivity.class);
			// // startActivityForResult(toExt, 1);
		}
		return super.onKeyDown(keyCode, event);
	}

	private int checkBill() {
		int totalprice = 0;
		for (ChromosphereBet b : betList) {
			totalprice += b.getPrice();
		}

		if (totalprice > 0) {
			// if(bet_follow>0){
			checkBillText.setText("共" + betList.size() + "注" + totalprice
					* bet_double * (bet_follow + 1) + "元");
			// }else{
			// checkBillText.setText("共" + betList.size() + "注" + totalprice
			// * bet_double + "元");}
		} else {
			checkBillText.setText("0");
		}
		return totalprice * bet_double * (bet_follow + 1);
	}

	private void initViews() {
		handicView = findViewById(R.id.handicView);
		handicView.setOnClickListener(this);
		randomView = findViewById(R.id.randomView);
		randomView.setOnClickListener(this);
		checkRule = true;
		rulesText = (TextView) findViewById(R.id.rulesText);
		// rules = (TextView) findViewById(R.id.rules);
		rulesImage = (ImageView) findViewById(R.id.rulesImage);
		rulesText.setOnClickListener(this);
		// rules.setOnClickListener(this);
		rulesImage.setOnClickListener(this);
		followalert = new AlertDialog.Builder(this).create();
		sutil = new SharePreferenceUtil(this);
		mHandler = new MyHandler();
		doubleWheel = new LottBetWheel(this, mHandler);
		// doubleBet = (TextView) findViewById(R.id.doubleBet);
		// doubleBet.setOnClickListener(this);
		doubleBetText = (TextView) findViewById(R.id.doubleBetText);
		// follow = (TextView) findViewById(R.id.follow);
		// follow.setOnClickListener(this);
		followView = findViewById(R.id.followView);
		followView.setOnClickListener(this);
		doubleView = findViewById(R.id.doubleView);
		doubleView.setOnClickListener(this);
		followText = (TextView) findViewById(R.id.followText);
		addBetHandly = (ImageView) findViewById(R.id.addBetHandly);
		addBetHandly.setOnClickListener(this);
		addBetRandomly = (ImageView) findViewById(R.id.addBetRandomly);
		addBetRandomly.setOnClickListener(this);
		billList = (ListView) findViewById(R.id.billList);
		billList.setDividerHeight(1);
		inflater = getLayoutInflater();
		corper = findViewById(R.id.corper);
		corper.setOnClickListener(this);
		footer = inflater.inflate(R.layout.checklist_footer, null);
		pay = findViewById(R.id.pay);
		pay.setOnClickListener(this);
		betAdapter = new BaseAdapter() {

			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(
							R.layout.chromosphere_bet_listitem, null);
				}
				TextView billText = (TextView) convertView
						.findViewById(R.id.billText);
				TextView redBallText = (TextView) convertView
						.findViewById(R.id.redBallText);
				redBallText.setText(betList.get(position).getRedBallString());
				TextView blueBallText = (TextView) convertView
						.findViewById(R.id.blueBallText);
				blueBallText.setVisibility(View.VISIBLE);
				blueBallText.setText(betList.get(position).getBlueBallString());
				billText.setText(String.valueOf(betList.get(position)
						.getPrice()) + "元");
				ImageView delete = (ImageView) convertView
						.findViewById(R.id.deleteItem);
				if (betList.size() > 0) {
					footer.setVisibility(View.VISIBLE);
				} else {
					footer.setVisibility(View.GONE);
				}
				delete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Log.e("pkx", "positon:" + position);
						Message msg = new Message();
						msg.what = Constant.DELETE_LIST_ITEM;
						msg.arg1 = position;
						handler.sendMessage(msg);
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
				Intent addHand = new Intent(DoubleChromosphereCheck.this,
						MyDoubleChromosphere.class);
				Bundle clickBundle = new Bundle();
				// Log.e("clickBet", clickBet.getBetString());
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
				intent1.setClass(DoubleChromosphereCheck.this,
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
			} else if (msg.what == Constant.DELETE_LIST_ITEM) {
				betList.remove(msg.arg1);
				betAdapter.notifyDataSetChanged();
				checkBill();
			} else if (msg.what == Constant.CLEAR_BET) {
				alert1();
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
