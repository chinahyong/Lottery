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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.bean.FootBallBetItem;
import com.pkx.lottery.bean.SevenLotteryBet;
import com.pkx.lottery.dto.FootballBetAuth;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.RandomBallsUtils;
import com.pkx.lottery.utils.SharePreferenceUtil;
import com.pkx.lottery.wheel.LottBetWheel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

//likjas
public class BaseketBallBetCheck extends Activity implements OnClickListener {
	private Intent mIntent;
	private SevenLotteryBet inBet, clickBet, editBet;
	private SharePreferenceUtil sutil;
	private AlertDialog followalert;
	private ArrayList<SevenLotteryBet> betList;
	private Set<String> danIndex;
	private ListView betListview;
	private BaseAdapter betAdapter;
	private int handleDouble = 1;
	private MyHandler mHandler;
	private LottBetWheel doubleWheel;
	private LayoutInflater inflater;
	private ArrayList<FootBallBetItem> FOTTBAL_BETITEMS;
	private int editPosition;
	private TextView billText, typeName, mixBetDouble;
	private ImageView addBetHandly, addBetRandomly;
	private View pay, chuan2, chuan3, chuan4, chuan5, chuan6, chuan7, chuan8,
			secondView, editView, corper, mixBetDoubleView;
	private boolean chuan2flag, chuan3flag, chuan4flag, chuan5flag, chuan6flag,
			chuan7flag, chuan8flag;
	private ImageView chuanImg2, chuanImg3, chuanImg4, chuanImg5, chuanImg6,
			chuanImg7, chuanImg8;
	private ArrayList<String> stringInfos, betInfos;
	private int selectedNum, checkType;
	private ArrayList<Boolean> chuans;
	// private Set<Integer> selectDans;
	private final ArrayList<Integer> gPositions = new ArrayList<Integer>(),
			cPositions = new ArrayList<Integer>();
	public void clickBack(View view) {
		super.onBackPressed();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.footballmixbet_check);
		mIntent = getIntent();
		selectedNum = mIntent.getIntExtra("selectedNum", 0);
		checkType = mIntent.getIntExtra("checkType", 0);
		danIndex = new HashSet<String>();
		initViews();
		switch (checkType) {
		case 1:
			typeName.setText("胜负");
			break;
		case 2:
			typeName.setText("让球胜负");
			break;
		case 3:
			typeName.setText("胜分差");
			break;
		case 4:
			typeName.setText("大小分");
			break;
		case 5:
			typeName.setText("半");
			break;
		case 6:
			typeName.setText("混");
			break;
		}
		makeChuanView(selectedNum);
		chuans = new ArrayList<Boolean>();
		for (int i = 0; i < 7; i++) {
			chuans.add(false);
		}
		for (int i = 0; i < Constant.BASKET_MATCH_STATUS.size(); i++) {
			for (int j = 0; j < Constant.BASKET_MATCH_STATUS.get(i).size(); j++) {
				Constant.BASKET_MATCH_STATUS.get(i).get(j).set(3, 0);
			}

		}
		chuan2flag = true;
		chuan3flag = true;
		chuan4flag = true;
		chuan5flag = true;
		chuan6flag = true;
		chuan7flag = true;
		FOTTBAL_BETITEMS = RandomBallsUtils.changStatusToBasketBet();
		// selectDans=new HashSet<Integer>();
	}

	private void makeChuanView(int num) {
		if (num > 7) {
			chuan2.setVisibility(View.VISIBLE);
			chuan3.setVisibility(View.VISIBLE);
			chuan4.setVisibility(View.VISIBLE);
			chuan5.setVisibility(View.VISIBLE);
			chuan6.setVisibility(View.VISIBLE);
			chuan7.setVisibility(View.VISIBLE);
			chuan8.setVisibility(View.VISIBLE);
			secondView.setVisibility(View.VISIBLE);
		} else {
			switch (num) {
			case 2:
				chuan2.setVisibility(View.VISIBLE);
				break;
			case 3:
				chuan2.setVisibility(View.VISIBLE);
				chuan3.setVisibility(View.VISIBLE);
				break;
			case 4:
				chuan2.setVisibility(View.VISIBLE);
				chuan3.setVisibility(View.VISIBLE);
				chuan4.setVisibility(View.VISIBLE);
				break;
			case 5:
				chuan2.setVisibility(View.VISIBLE);
				chuan3.setVisibility(View.VISIBLE);
				chuan4.setVisibility(View.VISIBLE);
				chuan5.setVisibility(View.VISIBLE);
				break;
			case 6:
				chuan2.setVisibility(View.VISIBLE);
				chuan3.setVisibility(View.VISIBLE);
				chuan4.setVisibility(View.VISIBLE);
				chuan5.setVisibility(View.VISIBLE);
				chuan6.setVisibility(View.VISIBLE);
				break;
			case 7:
				chuan2.setVisibility(View.VISIBLE);
				chuan3.setVisibility(View.VISIBLE);
				chuan4.setVisibility(View.VISIBLE);
				chuan5.setVisibility(View.VISIBLE);
				chuan6.setVisibility(View.VISIBLE);
				chuan7.setVisibility(View.VISIBLE);
				secondView.setVisibility(View.VISIBLE);
				break;
			}
		}
		// boolean isGoalsPlay = false;
		// boolean isHalfOrScores = false;
		// for (int i = 0; i < Constant.BASKET_MATCH_STATUS.size(); i++) {
		// for (int j = 0; j < Constant.BASKET_MATCH_STATUS.get(i).size(); j++)
		// {
		// for (int k = 6; k < 14; k++) {
		// if (Constant.MATCH_STATUS.get(i).get(j).get(k) == 1) {
		// isGoalsPlay = true;
		// break;
		// }
		// }
		// for (int m = 14; m < 54; m++) {
		// if (Constant.MATCH_STATUS.get(i).get(j).get(m) == 1) {
		// isHalfOrScores = true;
		// break;
		// }
		// }
		// }
		//
		// }
		// if (isGoalsPlay) {
		// chuan7.setVisibility(View.INVISIBLE);
		// chuan8.setVisibility(View.INVISIBLE);
		// secondView.setVisibility(View.GONE);
		// }
		// if (isHalfOrScores) {
		// chuan5.setVisibility(View.INVISIBLE);
		// chuan6.setVisibility(View.INVISIBLE);
		// chuan7.setVisibility(View.INVISIBLE);
		// chuan8.setVisibility(View.INVISIBLE);
		// secondView.setVisibility(View.GONE);
		// }
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.editView:
			finish();
			break;
		case R.id.addBetHandly:
			Constant.isHandlySelect = true;
			Intent addBet = new Intent(this, SevenLottery.class);
			startActivityForResult(addBet, 0);
			break;
		case R.id.addBetRandomly:
			SevenLotteryBet randomBet = new SevenLotteryBet();
			ArrayList<Integer> redBalls = new ArrayList<Integer>();
			for (int i : RandomBallsUtils.getRandomBalls(7, 30)) {
				redBalls.add(i + 1);
			}
			randomBet.setBalls(redBalls);
			betList.add(0, randomBet);
			betAdapter.notifyDataSetChanged();
			break;
		case R.id.corper:
			boolean chuanSelected = false;
			for (boolean b : chuans) {
				if (b) {
					chuanSelected = true;
					break;
				}
			}
			if (!chuanSelected) {
				Toast.makeText(this, "请选择正确的串关数", Toast.LENGTH_SHORT).show();
				return;
			}
			if (!sutil.isSNSetted()) {
				Toast.makeText(this, "请认证设备！", Toast.LENGTH_SHORT).show();
				return;
			}
			if (sutil.getLoginStatus()) {
				String chuanStr = "";
				String[] chs = { "2*1", "3*1", "4*1", "5*1", "6*1", "7*1",
						"8*1" };
				for (int i = 0; i < chuans.size(); i++) {
					if (chuans.get(i)) {
						chuanStr += chs[i] + ",";
					}
				}
				String danStr = "";
				for (String s : danIndex) {
					danStr += s + ",";
				}
				if (danStr.length() > 1)
					danStr = danStr.substring(0, danStr.length() - 1);
				String info = Constant.getMixBasString();
				FootballBetAuth cb = new FootballBetAuth(sutil.getuid(),
						String.valueOf(checkBill() * 2),
						String.valueOf(checkBill() / handleDouble),
						String.valueOf(handleDouble), info.substring(0,
								info.length() - 1)
								+ "|"
								+ chuanStr.substring(0, chuanStr.length() - 1)
								+ "|" + danStr, "0");
				String chormMingwen = Net.gson.toJson(cb);
				Intent toCorp = new Intent(this, CorperMaking.class);
				toCorp.putExtra("allMiwen", chormMingwen);
				toCorp.putExtra("type", 5);// 5竞彩足球
				toCorp.putExtra("totalPrice", checkBill() * 2);// 1双色球
				startActivity(toCorp);
			} else {
				Intent toLogin = new Intent(BaseketBallBetCheck.this,
						LoginActivity.class);
				toLogin.putExtra("fastlogin", true);
				startActivityForResult(toLogin, Constant.FAST_LOGIN);
			}

			break;
		case R.id.pay:
			// boolean chuanSelected = false;
			// for (boolean b : chuans) {
			// if (b) {
			// chuanSelected = true;
			// break;
			// }
			// }
			// if (!chuanSelected) {
			// Toast.makeText(this, "请选择正确的串关数", Toast.LENGTH_SHORT).show();
			// return;
			// }
			// if(!sutil.isSNSetted()){
			// Toast.makeText(this, "请认证设备！", Toast.LENGTH_SHORT).show();
			// return;
			// }
			// if (sutil.getLoginStatus()) {
			// String chuanStr = "";
			// String[] chs = { "2*1", "3*1", "4*1", "5*1", "6*1", "7*1",
			// "8*1" };
			// for (int i = 0; i < chuans.size(); i++) {
			// if (chuans.get(i)) {
			// chuanStr += chs[i] + ",";
			// }
			// }
			// String danStr="";
			// for(String s:danIndex){
			// danStr+=s+",";
			// }
			// if(danStr.length()>1){
			// danStr=danStr.substring(0, danStr.length()-1);
			// danStr="|"+danStr;
			// }
			// String info = Constant.getMixBetString();
			// info=info.substring(0,info.length()-1);
			//
			// FootballBetAuth cb = new FootballBetAuth(sutil.getuid(),
			// String.valueOf(checkBill() * 2),
			// String.valueOf(checkBill()/handleDouble),
			// String.valueOf(handleDouble), info
			// + "|"
			// + chuanStr.substring(0, chuanStr.length() - 1)
			// + danStr,"0");
			// String chormMingwen = Net.gson.toJson(cb);
			// String chromMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
			// chormMingwen);
			// PublicAllAuth all = new PublicAllAuth("betJCZQ", chromMiwen);
			// String allMingwen = Net.gson.toJson(all);
			// String allMiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
			// allMingwen);
			// RequestParams params = new RequestParams();
			// Log.e("pkx", "SID:" + sutil.getSID()+" SN "+sutil.getSN());
			// params.put("SID", sutil.getSID());
			// params.put("SN", sutil.getSN());
			// params.put("DATA", allMiwen);
			// Log.e("pkx", "mingwen1:" + chormMingwen + "  miwen1:"
			// + chromMiwen + " mingwen2:" + allMingwen + " miwen2:"
			// + allMiwen);
			// Net.post(true, this, "http://api.60888.la/bet.api.php", params,
			// mHandler, Constant.NET_ACTION_FOOTBALLMIX);
			// Log.e("pkx",
			// "betString:" + info.substring(0, info.length() - 1)
			// + "|"
			// + chuanStr.substring(0, chuanStr.length() - 1)
			// + "|"+danStr);
			// } else {
			// Intent toLogin = new Intent(FootballMixBetCheck.this,
			// LoginActivity.class);
			// toLogin.putExtra("fastlogin", true);
			// startActivityForResult(toLogin, Constant.FAST_LOGIN);
			// }

			boolean chuanSelected1 = false;
			for (boolean b : chuans) {
				if (b) {
					chuanSelected1 = true;
					break;
				}
			}
			if (!chuanSelected1) {
				Toast.makeText(this, "请选择正确的串关数", Toast.LENGTH_SHORT).show();
				return;
			}
			if (!sutil.isSNSetted()) {
				Toast.makeText(this, "请认证设备！", Toast.LENGTH_SHORT).show();
				return;
			}
			if (sutil.getLoginStatus()) {
				String chuanStr = "";
				String[] chs = { "2*1", "3*1", "4*1", "5*1", "6*1", "7*1",
						"8*1" };
				for (int i = 0; i < chuans.size(); i++) {
					if (chuans.get(i)) {
						chuanStr += chs[i] + ",";
					}
				}
				String danStr = "";
				for (String s : danIndex) {
					danStr += s + ",";
				}
				if (danStr.length() > 1)
					danStr = danStr.substring(0, danStr.length() - 1);
				String info = Constant.getMixBasString();
				FootballBetAuth cb = new FootballBetAuth(sutil.getuid(),
						String.valueOf(checkBill() * 2),
						String.valueOf(checkBill() / handleDouble),
						String.valueOf(handleDouble), info.substring(0,
								info.length() - 1)
								+ "|"
								+ chuanStr.substring(0, chuanStr.length() - 1)
								+ "|" + danStr, "0");
				String chormMingwen = Net.gson.toJson(cb);
				String chromMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
						chormMingwen);
				PublicAllAuth all = new PublicAllAuth("betJCLQ", chromMiwen);
				String allMingwen = Net.gson.toJson(all);
				String allMiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allMingwen);
				RequestParams params = new RequestParams();
				Log.e("pkx", "SID:" + sutil.getSID() + " SN " + sutil.getSN());
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", allMiwen);
				Log.e("pkx", "mingwen1:" + chormMingwen + "  miwen1:"
						+ chromMiwen + " mingwen2:" + allMingwen + " miwen2:"
						+ allMiwen);
				Net.post(true, this, Constant.POST_URL + "/bet.api.php",
						params, mHandler, Constant.NET_ACTION_FOOTBALLMIX);
				Log.e("pkx",
						"betString:" + info.substring(0, info.length() - 1)
								+ "|"
								+ chuanStr.substring(0, chuanStr.length() - 1)
								+ "|" + danStr);
			} else {
				Intent toLogin = new Intent(BaseketBallBetCheck.this,
						LoginActivity.class);
				toLogin.putExtra("fastlogin", true);
				startActivityForResult(toLogin, Constant.FAST_LOGIN);
			}

			// boolean chuanSelected = false;
			// for (boolean b : chuans) {
			// if (b) {
			// chuanSelected = true;
			// break;
			// }
			// }
			// if (!chuanSelected) {
			// Toast.makeText(this, "请选择正确的串关数", Toast.LENGTH_SHORT).show();
			// return;
			// }
			// if (!sutil.isSNSetted()) {
			// Toast.makeText(this, "请认证设备！", Toast.LENGTH_SHORT).show();
			// return;
			// }
			// if (sutil.getLoginStatus()) {
			// String chuanStr = "";
			// String[] chs = { "2*1", "3*1", "4*1", "5*1", "6*1", "7*1",
			// "8*1" };
			// for (int i = 0; i < chuans.size(); i++) {
			// if (chuans.get(i)) {
			// chuanStr += chs[i] + ",";
			// }
			// }
			// String danStr = "";
			// for (String s : danIndex) {
			// danStr += s + ",";
			// }
			// if (danStr.length() > 1)
			// danStr = danStr.substring(0, danStr.length() - 1);
			// String info = Constant.getMixBetString();
			// FootballBetAuth cb = new FootballBetAuth(sutil.getuid(),
			// String.valueOf(checkBill() * 2),
			// String.valueOf(checkBill() / handleDouble),
			// String.valueOf(handleDouble), info.substring(0,
			// info.length() - 1)
			// + "|"
			// + chuanStr.substring(0, chuanStr.length() - 1)
			// + "|" + danStr,"0");
			// String chormMingwen = Net.gson.toJson(cb);
			// String chromMiwen = MDUtils.MDEncode(sutil.getuserKEY(),
			// chormMingwen);
			// PublicAllAuth all = new PublicAllAuth("betJCZQ", chromMiwen);
			// String allMingwen = Net.gson.toJson(all);
			// String allMiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
			// allMingwen);
			// RequestParams params = new RequestParams();
			// Log.e("pkx", "SID:" + sutil.getSID() + " SN " + sutil.getSN());
			// params.put("SID", sutil.getSID());
			// params.put("SN", sutil.getSN());
			// params.put("DATA", allMiwen);
			// Log.e("pkx", "mingwen1:" + chormMingwen + "  miwen1:"
			// + chromMiwen + " mingwen2:" + allMingwen + " miwen2:"
			// + allMiwen);
			// Net.post(true, this, "http://api.60888.la/bet.api.php", params,
			// mHandler, Constant.NET_ACTION_FOOTBALLMIX);
			// Log.e("pkx",
			// "betString:" + info.substring(0, info.length() - 1)
			// + "|"
			// + chuanStr.substring(0, chuanStr.length() - 1)
			// + "|" + danStr);
			// } else {
			// Intent toLogin = new Intent(FootballMixBetCheck.this,
			// LoginActivity.class);
			// toLogin.putExtra("fastlogin", true);
			// startActivityForResult(toLogin, Constant.FAST_LOGIN);
			// }
			break;
		case R.id.chuan2:
			if (chuan2flag) {
				if (!chuans.get(0)) {
					// chuan2.setBackgroundResource(R.drawable.bp);
					chuanImg2.setImageResource(R.drawable.chuan_selected);
					chuans.set(0, true);
					// selectDans.add(2);

				} else {
					chuanImg2.setImageResource(R.drawable.chuan_normal);
					chuans.set(0, false);
					// selectDans.remove(2);
				}
				checkBill();
			}
			break;
		case R.id.chuan3:
			if (chuan3flag) {
				if (!chuans.get(1)) {
					chuanImg3.setImageResource(R.drawable.chuan_selected);
					chuans.set(1, true);
					// selectDans.add(3);
				} else {
					chuanImg3.setImageResource(R.drawable.chuan_normal);
					chuans.set(1, false);
					// selectDans.remove(3);
				}
				checkBill();
			}
			break;
		case R.id.chuan4:
			if (chuan4flag) {
				if (!chuans.get(2)) {
					chuanImg4.setImageResource(R.drawable.chuan_selected);
					chuans.set(2, true);
					// selectDans.add(4);
				} else {
					chuanImg4.setImageResource(R.drawable.chuan_normal);
					chuans.set(2, false);
					// selectDans.remove(4);
				}
				checkBill();
			}
			break;
		case R.id.chuan5:
			if (chuan5flag) {
				if (!chuans.get(3)) {
					chuanImg5.setImageResource(R.drawable.chuan_selected);
					chuans.set(3, true);
					// selectDans.add(5);
				} else {
					chuanImg5.setImageResource(R.drawable.chuan_normal);
					chuans.set(3, false);
					// selectDans.remove(5);
				}
				checkBill();
			}
			break;
		case R.id.chuan6:
			if (chuan6flag) {
				if (!chuans.get(4)) {
					chuanImg6.setImageResource(R.drawable.chuan_selected);
					chuans.set(4, true);
				} else {
					chuanImg6.setImageResource(R.drawable.chuan_normal);
					chuans.set(4, false);
				}
				checkBill();
			}
			break;
		case R.id.chuan7:
			if (chuan7flag) {
				if (!chuans.get(5)) {
					chuanImg7.setImageResource(R.drawable.chuan_selected);
					chuans.set(5, true);
				} else {
					chuanImg7.setImageResource(R.drawable.chuan_normal);
					chuans.set(5, false);
				}
				checkBill();
			}
			break;
		case R.id.chuan8:
			if (!chuans.get(6)) {
				chuanImg8.setImageResource(R.drawable.chuan_selected);
				chuans.set(6, true);
			} else {
				chuanImg8.setImageResource(R.drawable.chuan_normal);
				chuans.set(6, false);
			}
			checkBill();
			break;
		case R.id.mixBetDoubleView:
			// doubleWheel.setCurrentItem(handleDouble);
			if (handleDouble < 1) {
				doubleWheel.showWheel(0, 0);
			} else {
				doubleWheel.showWheel(handleDouble - 1, 0);
			}
			// alertFollow(99);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			// alert();
		}
		return super.onKeyDown(keyCode, event);
	}

	private void checkDan() {
		// int danNum=getDanNumber();
		chuan2flag = true;
		chuan3flag = true;
		chuan4flag = true;
		chuan5flag = true;
		chuan6flag = true;
		chuan7flag = true;
		if (!chuans.get(0)) {
			chuanImg2.setImageResource(R.drawable.chuan_normal);
		} else {
			chuanImg2.setImageResource(R.drawable.chuan_selected);
		}
		if (!chuans.get(1)) {
			chuanImg3.setImageResource(R.drawable.chuan_normal);
		} else {
			chuanImg3.setImageResource(R.drawable.chuan_selected);
		}
		if (!chuans.get(2)) {
			chuanImg4.setImageResource(R.drawable.chuan_normal);
		} else {
			chuanImg4.setImageResource(R.drawable.chuan_selected);
		}
		if (!chuans.get(3)) {
			chuanImg5.setImageResource(R.drawable.chuan_normal);
		} else {
			chuanImg5.setImageResource(R.drawable.chuan_selected);
		}
		if (!chuans.get(4)) {
			chuanImg6.setImageResource(R.drawable.chuan_normal);
		} else {
			chuanImg6.setImageResource(R.drawable.chuan_selected);
		}
		if (!chuans.get(5)) {
			chuanImg7.setImageResource(R.drawable.chuan_normal);
		} else {
			chuanImg7.setImageResource(R.drawable.chuan_selected);
		}
		// chuanImg3.setImageResource(R.drawable.chuan_normal);
		// chuanImg4.setImageResource(R.drawable.chuan_normal);
		// chuanImg5.setImageResource(R.drawable.chuan_normal);
		// chuanImg6.setImageResource(R.drawable.chuan_normal);
		// chuanImg7.setImageResource(R.drawable.chuan_normal);
		switch (getDanNumber()) {
		case 2:
			chuan2flag = false;
			chuanImg2.setImageResource(R.drawable.chuan_unselected);
			chuans.set(0, false);
			break;
		case 3:
			chuan2flag = false;
			chuan3flag = false;
			chuanImg2.setImageResource(R.drawable.chuan_unselected);
			chuanImg3.setImageResource(R.drawable.chuan_unselected);
			chuans.set(0, false);
			chuans.set(1, false);
			break;
		case 4:
			chuan2flag = false;
			chuan3flag = false;
			chuan4flag = false;
			chuanImg2.setImageResource(R.drawable.chuan_unselected);
			chuanImg3.setImageResource(R.drawable.chuan_unselected);
			chuanImg4.setImageResource(R.drawable.chuan_unselected);
			chuans.set(0, false);
			chuans.set(1, false);
			chuans.set(2, false);
			break;
		case 5:
			chuan2flag = false;
			chuan3flag = false;
			chuan4flag = false;
			chuan5flag = false;
			chuanImg2.setImageResource(R.drawable.chuan_unselected);
			chuanImg3.setImageResource(R.drawable.chuan_unselected);
			chuanImg4.setImageResource(R.drawable.chuan_unselected);
			chuanImg5.setImageResource(R.drawable.chuan_unselected);
			chuans.set(0, false);
			chuans.set(1, false);
			chuans.set(2, false);
			chuans.set(3, false);
			break;
		case 6:
			chuan2flag = false;
			chuan3flag = false;
			chuan4flag = false;
			chuan5flag = false;
			chuan6flag = false;
			chuanImg2.setImageResource(R.drawable.chuan_unselected);
			chuanImg3.setImageResource(R.drawable.chuan_unselected);
			chuanImg4.setImageResource(R.drawable.chuan_unselected);
			chuanImg5.setImageResource(R.drawable.chuan_unselected);
			chuanImg6.setImageResource(R.drawable.chuan_unselected);
			chuans.set(0, false);
			chuans.set(1, false);
			chuans.set(2, false);
			chuans.set(3, false);
			chuans.set(4, false);
			break;
		case 7:
			chuan2flag = false;
			chuan3flag = false;
			chuan4flag = false;
			chuan5flag = false;
			chuan6flag = false;
			chuan7flag = false;
			chuanImg2.setImageResource(R.drawable.chuan_unselected);
			chuanImg3.setImageResource(R.drawable.chuan_unselected);
			chuanImg4.setImageResource(R.drawable.chuan_unselected);
			chuanImg5.setImageResource(R.drawable.chuan_unselected);
			chuanImg6.setImageResource(R.drawable.chuan_unselected);
			chuanImg7.setImageResource(R.drawable.chuan_unselected);
			chuans.set(0, false);
			chuans.set(1, false);
			chuans.set(2, false);
			chuans.set(3, false);
			chuans.set(4, false);
			chuans.set(5, false);
			break;

		default:
			checkBill();
			break;
		}

	}

	private int checkBill() {
		if (FOTTBAL_BETITEMS == null) {
			Log.e("pkx", "FOTTBAL_BETITEMS==null");
		} else {
			Log.e("pkx", "FOTTBAL_BETITEMS:" + FOTTBAL_BETITEMS.size());
		}
		Log.e("pkx", "");
		int betsNum = 0;
		if (chuans.get(0)) {
			betsNum += RandomBallsUtils.getBasketBetNumberFromBets(
					FOTTBAL_BETITEMS, 2, getDanNumber());
		}
		if (chuans.get(1)) {
			betsNum += RandomBallsUtils.getBasketBetNumberFromBets(
					FOTTBAL_BETITEMS, 3, getDanNumber());
		}
		if (chuans.get(2)) {
			betsNum += RandomBallsUtils.getBasketBetNumberFromBets(
					FOTTBAL_BETITEMS, 4, getDanNumber());
		}
		if (chuans.get(3)) {
			betsNum += RandomBallsUtils.getBasketBetNumberFromBets(
					FOTTBAL_BETITEMS, 5, getDanNumber());
		}
		if (chuans.get(4)) {
			betsNum += RandomBallsUtils.getBasketBetNumberFromBets(
					FOTTBAL_BETITEMS, 6, getDanNumber());
		}
		if (chuans.get(5)) {
			betsNum += RandomBallsUtils.getBasketBetNumberFromBets(
					FOTTBAL_BETITEMS, 7, getDanNumber());
		}
		if (chuans.get(6)) {
			betsNum += RandomBallsUtils.getBasketBetNumberFromBets(
					FOTTBAL_BETITEMS, 8, getDanNumber());
		}
		if (betsNum > 0) {
			billText.setText("共" + betsNum + "注" + handleDouble * betsNum * 2
					+ "元");
		} else {
			billText.setText("0");
		}
		Log.e("pkx", "betSUM:" + betsNum + "  handleDoule:" + handleDouble);
		return betsNum * handleDouble;
	}

	private void getAdapterContent() {
		stringInfos = new ArrayList<String>();
		betInfos = new ArrayList<String>();
		gPositions.clear();
		cPositions.clear();
		for (int groupPositin = 0; groupPositin < Constant.BASKET_MATCH_STATUS
				.size(); groupPositin++) {
			for (int childPositon = 0; childPositon < Constant.BASKET_MATCH_STATUS
					.get(groupPositin).size(); childPositon++) {
				if (Constant.BASKET_MATCH_STATUS.get(groupPositin)
						.get(childPositon).get(2) == 1) {
					stringInfos.add(Constant.BASKET_MATCH_INFOS.get(
							groupPositin).get(childPositon));
				}
				String matchInfo = "";
				boolean isSelect = false;
				for (int i = 0; i < 2; i++) {
					if (Constant.BASKET_MATCH_STATUS.get(groupPositin)
							.get(childPositon).get(i) == 1) {
						switch (checkType) {
						case 1:
							matchInfo += Constant.BASKETBALL_BET_STRS[i] + ";";
							break;
						case 2:
							matchInfo += Constant.BASKETBALL_BET_STRS[i] + ";";
							break;
						case 3:
							// matchInfo += Constant.BASKETBALL_BET_STRS[i] +
							// ";";
							break;
						case 4:
							matchInfo += Constant.BASKETBALL_BS_BET_STRS[i]
									+ ";";
							break;

						default:
							matchInfo = "000";
							// matchInfo += Constant.BASKETBALL_BS_BET_STRS[i] +
							// ";";
							break;
						}
						isSelect = true;
					}
				}
				if (isSelect) {
					gPositions.add(groupPositin);
					cPositions.add(childPositon);
				}
				if (matchInfo.length() > 0) {
					betInfos.add(matchInfo);
				}
			}
		}
		// selectedMatchNum=stringInfos.size();
	}

	private void initViews() {
		inflater = getLayoutInflater();
		mHandler = new MyHandler();
		doubleWheel = new LottBetWheel(this, mHandler);
		corper = findViewById(R.id.corper);
		corper.setOnClickListener(this);
		editView = findViewById(R.id.editView);
		editView.setOnClickListener(this);
		typeName = (TextView) findViewById(R.id.typeName);
		mixBetDouble = (TextView) findViewById(R.id.mixBetDouble);
		mixBetDoubleView = findViewById(R.id.mixBetDoubleView);
		mixBetDoubleView.setOnClickListener(this);
		sutil = new SharePreferenceUtil(this);
		followalert = new AlertDialog.Builder(this).create();
		chuan2 = findViewById(R.id.chuan2);
		chuan3 = findViewById(R.id.chuan3);
		chuan4 = findViewById(R.id.chuan4);
		chuan5 = findViewById(R.id.chuan5);
		chuan6 = findViewById(R.id.chuan6);
		chuan7 = findViewById(R.id.chuan7);
		chuan8 = findViewById(R.id.chuan8);
		chuanImg2 = (ImageView) findViewById(R.id.chuanImg2);
		chuanImg3 = (ImageView) findViewById(R.id.chuanImg3);
		chuanImg4 = (ImageView) findViewById(R.id.chuanImg4);
		chuanImg5 = (ImageView) findViewById(R.id.chuanImg5);
		chuanImg6 = (ImageView) findViewById(R.id.chuanImg6);
		chuanImg7 = (ImageView) findViewById(R.id.chuanImg7);
		chuanImg8 = (ImageView) findViewById(R.id.chuanImg8);
		secondView = findViewById(R.id.secondView);
		chuan2.setOnClickListener(this);
		chuan3.setOnClickListener(this);
		chuan4.setOnClickListener(this);
		chuan5.setOnClickListener(this);
		chuan6.setOnClickListener(this);
		chuan7.setOnClickListener(this);
		chuan8.setOnClickListener(this);
		pay = findViewById(R.id.pay);
		pay.setOnClickListener(this);
		billText = (TextView) findViewById(R.id.billText);
		// addBetHandly = (ImageView) findViewById(R.id.addBetHandly);
		// addBetHandly.setOnClickListener(this);
		// addBetRandomly = (ImageView) findViewById(R.id.addBetRandomly);
		// addBetRandomly.setOnClickListener(this);
		betList = new ArrayList<SevenLotteryBet>();
		mIntent = getIntent();
		inBet = (SevenLotteryBet) mIntent.getSerializableExtra("sbet");
		if (null != inBet) {
			betList.add(inBet);
			checkBill();
		}
		betListview = (ListView) findViewById(R.id.billList);
		betListview.setDividerHeight(0);
		getAdapterContent();
		betAdapter = new BaseAdapter() {

			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				if (convertView == null) {
					convertView = (LinearLayout) inflater.inflate(
							R.layout.footballmixbet_item, null);
				}
				String[] strs = stringInfos.get(position).split("\\*");
				String infos = "";
				for (String s : strs) {
					infos += s + " - ";
				}
				Log.e("pkx", "info:" + infos);
				TextView hostName = (TextView) convertView
						.findViewById(R.id.hostName);
				hostName.setText(strs[1]);
				TextView handicPoints = (TextView) convertView
						.findViewById(R.id.handicPoints);
				if (Constant.FOOTBALL_TYPE == 6 || Constant.FOOTBALL_TYPE == 2) {
					handicPoints.setText(strs[3]);
				} else {
					handicPoints.setText("VS");
				}

				TextView visitName = (TextView) convertView
						.findViewById(R.id.visitName);
				visitName.setText(strs[2]);
				TextView betInfo = (TextView) convertView
						.findViewById(R.id.betInfo);
				betInfo.setText(betInfos.get(position));
				final ImageView danImg = (ImageView) convertView
						.findViewById(R.id.danImg);
				if (Constant.BASKET_MATCH_STATUS.get(gPositions.get(position))
						.get(cPositions.get(position)).get(3) == 1) {
					danImg.setImageResource(R.drawable.dan_selected);
				} else {
					danImg.setImageResource(R.drawable.dan_normal);
				}
				danImg.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Log.e("pkxh", "click positon:" + position);
						if (Constant.BASKET_MATCH_STATUS
								.get(gPositions.get(position))
								.get(cPositions.get(position)).get(3) == 1) {
							Constant.BASKET_MATCH_STATUS
									.get(gPositions.get(position))
									.get(cPositions.get(position)).set(3, 0);
							danImg.setImageResource(R.drawable.dan_normal);
							danIndex.remove(Constant.BASEKET_MIX_DAYMATCHESS
									.get(gPositions.get(position)).getMatchs()
									.get(cPositions.get(position))
									.getMatch_id());
							// danIndex.remove(Constant.BASEKET_MIX_DAYMATCHESS
							// .get(gPositions.get(position)).getMatchs()
							// .get(cPositions.get(position)).getId());
							// danIndex.remove(Constant.BASEKET_MIX_DAYMATCHESS
							// .get(gPositions.get(position)).getMatchs()
							// .get(cPositions.get(position)).getId());
						} else {
							if (getDanNumber() == betInfos.size() - 1
									|| getDanNumber() == 7) {
								Toast.makeText(BaseketBallBetCheck.this,
										"设胆已达上限", Toast.LENGTH_SHORT).show();
								return;
							}
							Constant.BASKET_MATCH_STATUS
									.get(gPositions.get(position))
									.get(cPositions.get(position)).set(3, 1);
							danImg.setImageResource(R.drawable.dan_selected);
							danIndex.add(Constant.BASEKET_MIX_DAYMATCHESS
									.get(gPositions.get(position)).getMatchs()
									.get(cPositions.get(position))
									.getMatch_id());
							// danIndex.add(Constant.MIX_DAYMATCHESS
							// .get(gPositions.get(position)).getMatchs()
							// .get(cPositions.get(position)).getId());
							// danIndex.add(Constant.MIX_DAYMATCHESS
							// .get(gPositions.get(position)).getMatchs()
							// .get(cPositions.get(position)).getId());
						}
						checkDan();
						checkBill();
						Log.e("pkx", "dansize:" + danIndex.size());
					}
				});
				Log.e("pkx",
						"位置："
								+ position
								+ "-"
								+ Constant.BASKET_MATCH_STATUS
										.get(gPositions.get(position))
										.get(cPositions.get(position)).get(3)
								+ "Gsize:" + gPositions.size() + "Csize"
								+ cPositions.size());
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
				return stringInfos.size();
			}
		};
		// betListview.addFooterView(footer);
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
				Intent addHand = new Intent(BaseketBallBetCheck.this,
						SevenLottery.class);
				Bundle clickBundle = new Bundle();
				clickBundle.putSerializable("editBet", clickBet);
				addHand.putExtras(clickBundle);
				startActivityForResult(addHand, 1);

			}
		});

	}

	private int getDanNumber() {
		int num = 0;
		for (int i = 0; i < Constant.BASKET_MATCH_STATUS.size(); i++) {
			for (int j = 0; j < Constant.BASKET_MATCH_STATUS.get(i).size(); j++) {
				if (Constant.BASKET_MATCH_STATUS.get(i).get(j).get(3) == 1) {
					num++;
				}

			}

		}
		return num;
	}

	private void alertFollow(final int number) {
		followalert.show();
		followalert.getWindow().setContentView(R.layout.followbet_dialog);
		ListView list = (ListView) followalert.findViewById(R.id.followList);
		BaseAdapter adapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(
							R.layout.followbet_list_item, null);
				}
				TextView text = (TextView) convertView
						.findViewById(R.id.number);
				text.setText("" + (position + 1) + "倍");
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
				handleDouble = position + 1;
				checkBill();
				mixBetDouble.setText("投 " + String.valueOf(position + 1) + " 倍");
				followalert.dismiss();
			}
		});
		list.setSelected(true);
		list.setDividerHeight(1);
		list.setAdapter(adapter);
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
				intent1.setClass(BaseketBallBetCheck.this,
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

	@SuppressLint("NewApi")
	private void alert1() {
		final AlertDialog alert = new AlertDialog.Builder(this, R.style.dialog)
				.create();
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

	// private void alert() {
	// final AlertDialog alert = new AlertDialog.Builder(this).create();
	// alert.show();
	// alert.getWindow().setContentView(R.layout.exit_dialog);
	//
	// TextView title = (TextView) alert.findViewById(R.id.title);
	// title.setText("离开页面后将清除原页面上的投注信息，确定离开？");
	// View okButton = alert.findViewById(R.id.ok);
	// okButton.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// alert.dismiss();
	// finish();
	// }
	// });
	//
	// View cancelButton = alert.findViewById(R.id.cancel);
	// cancelButton.setOnClickListener(new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// alert.dismiss();
	// }
	// });
	// }

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
				// POST_SUCCESS{"status":"1","orderID":710}
				Log.e("pkx", "POST_SUCCESS" + msg.obj);
				JSONObject jo = (JSONObject) msg.obj;

				try {
					if ("10001".equals(jo.get("error").toString())) {
						Intent toLogin = new Intent(BaseketBallBetCheck.this,
								LoginActivity.class);
						toLogin.putExtra("fastlogin", true);
						startActivityForResult(toLogin, Constant.FAST_LOGIN);
					} else if ("0".equals(jo.get("error").toString())
							|| "20000".equals(jo.get("error").toString())) {
						Constant.BASKET_MATCH_STATUS = null;
						Toast.makeText(BaseketBallBetCheck.this, "投注成功",
								Toast.LENGTH_SHORT).show();
						Intent toHome = new Intent(BaseketBallBetCheck.this,
								HomeActivity.class);
						startActivity(toHome);
					} else if ("20010".equals(jo.get("error").toString())) {
						alertMoneyLess();
						return;

					} else {
						Constant.alertWarning(BaseketBallBetCheck.this,
								jo.get("message").toString());
						Toast.makeText(BaseketBallBetCheck.this,
								jo.get("message").toString(), Toast.LENGTH_LONG)
								.show();
					}
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
			} else if (msg.what == Constant.POST_FAIL) {
				Toast.makeText(BaseketBallBetCheck.this, "请求超时",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				JSONObject jo = (JSONObject) msg.obj;
				try {
					if ("20010".equals(jo.get("error").toString())) {
						alertMoneyLess();
						return;

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				Log.e("pkx", "zero json:" + jo.toString());
				Toast.makeText(BaseketBallBetCheck.this, "投注失败",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == Constant.WHEEL_SELECTED) {
				if (msg.arg1 == 0) {
					handleDouble = msg.arg2 + 1;
					mixBetDouble.setText(String.valueOf(msg.arg2 + 1) + " 倍");
					checkBill();
				}
			}

		}
	}

}
