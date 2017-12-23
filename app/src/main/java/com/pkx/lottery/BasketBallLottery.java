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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.PeroidFootBallRes;
import com.pkx.lottery.dto.basketball.BasketMatchList;
import com.pkx.lottery.headerlist.HalfMatchInDayList;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

import org.json.JSONObject;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class BasketBallLottery extends Activity implements OnClickListener {
	private View wef_view, rwef_view, points_view, goals_view, half_view,
			mix_view;
	private SharePreferenceUtil sutil;
	static final ArrayList<Integer> fastLogos = new ArrayList<Integer>();
	// ArrayList<ChormLott> lotts;
	private MyHandler mHandler;
	private AlertDialog mDialog;
	public void clickBack(View view) {
		super.onBackPressed();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basketball_lottery);
		initViews();
	}

	@Override
	protected void onResume() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// Toast.makeText(this, "抱歉,无竞彩篮球赛事！", Toast.LENGTH_SHORT).show();
		switch (v.getId()) {
		case R.id.wef_view:
			// Intent towf=new Intent(this, BasketBallRWF.class);
			// startActivity(towf);
			PeroidFootBallRes res = new PeroidFootBallRes("jclqWinLose",
					"1000", "1");
			String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
					Net.gson.toJson(res));
			RequestParams params = new RequestParams();
			params.put("SID", sutil.getSID());
			params.put("SN", sutil.getSN());
			params.put("DATA", miwen);
			Net.post(true, BasketBallLottery.this, Constant.POST_URL
					+ "/data.api.php", params, mHandler,
					Constant.NET_ACTION_LOTHIS_WEF);
			break;
		case R.id.rwef_view:
			PeroidFootBallRes res1 = new PeroidFootBallRes("jclqScoreWinLose",
					"1000", "1");
			String miwen1 = MDUtils.MDEncode(sutil.getdeviceKEY(),
					Net.gson.toJson(res1));
			RequestParams params1 = new RequestParams();
			params1.put("SID", sutil.getSID());
			params1.put("SN", sutil.getSN());
			params1.put("DATA", miwen1);
			Net.post(true, BasketBallLottery.this, Constant.POST_URL
					+ "/data.api.php", params1, mHandler,
					Constant.NET_ACTION_LOTHIS_RWEF);
			break;
		case R.id.points_view:
//			Toast.makeText(this, "抱歉,无竞彩篮球胜分差赛事！", Toast.LENGTH_SHORT).show();
			 PeroidFootBallRes res2 = new PeroidFootBallRes("getJczqScore",
			 "1000", "1");
			 String miwen2 = MDUtils.MDEncode(sutil.getdeviceKEY(),
			 Net.gson.toJson(res2));
			 RequestParams params2 = new RequestParams();
			 params2.put("SID", sutil.getSID());
			 params2.put("SN", sutil.getSN());
			 params2.put("DATA", miwen2);
			 Net.post(true, BasketBallLottery.this,Constant.POST_URL+
			 "/data.api.php", params2, mHandler,
			 Constant.NET_ACTION_LOTHIS_POINTS);
			break;
		case R.id.goals_view:
			PeroidFootBallRes resgoals = new PeroidFootBallRes("jclqBigSmall",
					"1000", "1");
			String miwengoals = MDUtils.MDEncode(sutil.getdeviceKEY(),
					Net.gson.toJson(resgoals));
			RequestParams paramsgoals = new RequestParams();
			paramsgoals.put("SID", sutil.getSID());
			paramsgoals.put("SN", sutil.getSN());
			paramsgoals.put("DATA", miwengoals);
			Net.post(true, BasketBallLottery.this, Constant.POST_URL
					+ "/data.api.php", paramsgoals, mHandler,
					Constant.NET_ACTION_LOTHIS_GOALS);
			break;
		case R.id.half_view:
			 PeroidFootBallRes reshalf = new PeroidFootBallRes("getJczqHalf",
			 "1000", "1");
			 String miwenhalf = MDUtils.MDEncode(sutil.getdeviceKEY(),
			 Net.gson.toJson(reshalf));
			 RequestParams paramshalf = new RequestParams();
			 paramshalf.put("SID", sutil.getSID());
			 paramshalf.put("SN", sutil.getSN());
			 paramshalf.put("DATA", miwenhalf);
			 Net.post(true, BasketBallLottery.this,Constant.POST_URL+
			 "/data.api.php", paramshalf, mHandler,
			 Constant.NET_ACTION_LOTHIS_HALF);
			 Intent tohalf = new Intent(this, FootBallHalf.class);
			 startActivity(tohalf);
			break;
		case R.id.mix_view:
			 PeroidFootBallRes resht = new PeroidFootBallRes("getJczqHt",
			 "1000",
			 "1");
			 String miwenht = MDUtils.MDEncode(sutil.getdeviceKEY(),
			 Net.gson.toJson(resht));
			 RequestParams paramsht = new RequestParams();
			 paramsht.put("SID", sutil.getSID());
			 paramsht.put("SN", sutil.getSN());
			 paramsht.put("DATA", miwenht);
			 Net.post(true, BasketBallLottery.this,Constant.POST_URL+
			 "/data.api.php", paramsht, mHandler,
			 Constant.NET_ACTION_LOTHIS_MIX);
			 Intent tomix = new Intent(this, FootballMixBet.class);
			 startActivity(tomix);
			break;
		}

	}

	private void initViews() {
		sutil = new SharePreferenceUtil(this);
		mHandler = new MyHandler();
		wef_view = findViewById(R.id.wef_view);
		wef_view.setOnClickListener(this);
		points_view = findViewById(R.id.points_view);
		points_view.setOnClickListener(this);
		goals_view = findViewById(R.id.goals_view);
		goals_view.setOnClickListener(this);
		half_view = findViewById(R.id.half_view);
		half_view.setOnClickListener(this);
		rwef_view = findViewById(R.id.rwef_view);
		rwef_view.setOnClickListener(this);
		mix_view = findViewById(R.id.mix_view);
		mix_view.setOnClickListener(this);
		mDialog = new AlertDialog.Builder(BasketBallLottery.this,
				R.style.dialog).create();
		mDialog.setCancelable(true);
		mDialog.setCanceledOnTouchOutside(false);
	}

	public void showRoundProcessDialog() {
		if (mDialog.isShowing()) {
			return;
		}
		mDialog.show();
		// 注意此处要放在show之后 否则会报异常
		mDialog.setContentView(R.layout.loading_process_dialog_anim);
	}

	private String getDateString(String date) {
		// String[] strs=date.trim().split("-");
		// String returnStr="";
		// for(){
		//
		// }
		return date.substring(0, 4) + date.substring(5, 7) + date.substring(8);
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
				showRoundProcessDialog();
				if (msg.arg1 == Constant.NET_ACTION_LOTHIS_WEF) {// TODO 竞彩篮球胜负
					BasketMatchList list = Net.gson.fromJson(
							msg.obj.toString(), BasketMatchList.class);
					Log.e("pkx", "basket matches:"+list.getMatchData().size());
					 Intent towef = new Intent(BasketBallLottery.this,
					 BasketBallRWF.class);
					 Bundle bundle = new Bundle();
					 bundle.putSerializable("matches",
					 list.getMatchData());
					 towef.putExtras(bundle);
					 towef.putExtra("type", 1);
					 startActivity(towef);
					 Constant.BASKETBALL_PLAY_TYPE=1;
					// Log.e("pkx", "往期彩期加载成功：" + ojo.toString());
				} else if (msg.arg1 == Constant.NET_ACTION_LOTHIS_RWEF) {
					BasketMatchList list = Net.gson.fromJson(
							msg.obj.toString(), BasketMatchList.class);
					Log.e("pkx", "basket matches:"+list.getMatchData().size());
					 Intent towef = new Intent(BasketBallLottery.this,
					 BasketBallRWF.class);
					 Bundle bundle = new Bundle();
					 bundle.putSerializable("matches",
					 list.getMatchData());
					 towef.putExtra("type", 2);
					 towef.putExtras(bundle);
					 startActivity(towef);
					 Constant.BASKETBALL_PLAY_TYPE=2;
//					org.json.JSONObject ojo = (JSONObject) msg.obj;
//					MatchesInDayList daylist = Net.gson.fromJson(
//							ojo.toString(), MatchesInDayList.class);
//					ArrayList<MatchesInDay> all = daylist.getMatchData();
//					for (MatchesInDay m : all) {
//						Log.e("pkx",
//								"--all--" + m.getTotal() + "  "
//										+ m.getYearmonthday() + " "
//										+ m.getList().size());
//					}
//					// HashMap<Integer, String> map = new HashMap<Integer,
//					// String>();
//					// while (it.hasNext()) {
//					// String str = it.next();
//					// map.put(Integer.valueOf(getDateString(str)), str);
//					//
//					// }
//					// ArrayList<Integer> keys = new ArrayList<Integer>();
//					// keys.addAll(map.keySet());
//					// keys = RandomBallsUtils.sort(keys);
//					// for (int i : keys) {
//					// MatchesInDay m = Net.gson.fromJson(
//					// matchdata.getString(map.get(i)),
//					// MatchesInDay.class);
//					// m.setYearmonthday(map.get(i));
//					// all.add(m);
//					//
//					// }
//					// for (MatchesInDay ms : all) {
//					// for (Match m : ms.getList()) {
//					// Log.e("pkx",
//					// "场次：" + ms.getTotal() + " 主队："
//					// + m.getHost() + " 客队："
//					// + m.getGuest() + "  ID:"
//					// + m.getId());
//					// }
//					// }
//					Intent torwef = new Intent(BasketBallLottery.this,
//							FootBallRWEF.class);
//					Bundle bundle = new Bundle();
//					bundle.putSerializable("matches", all);
//					torwef.putExtras(bundle);
//					startActivity(torwef);
//					// } catch (JSONException e) {
//					// Log.e("pkx", "ja------>JSONException");
//					// e.printStackTrace();
//					// }
//					Log.e("pkx", "往期彩期加载成功：" + ojo.toString());

				} else if (msg.arg1 == Constant.NET_ACTION_LOTHIS_POINTS) {

					BasketMatchList list = Net.gson.fromJson(
							msg.obj.toString(), BasketMatchList.class);
					Log.e("pkx", "basket matches:"+list.getMatchData().size());
					 Intent towef = new Intent(BasketBallLottery.this,
					 BasketBallRWF.class);
					 Bundle bundle = new Bundle();
					 bundle.putSerializable("matches",
					 list.getMatchData());
					 towef.putExtra("type", 3);
					 towef.putExtras(bundle);
					 startActivity(towef);
//					org.json.JSONObject ojo = (JSONObject) msg.obj;
//					Log.e("pkx", "all points:" + ojo.toString());
//					Log.e("pkx", "json length:" + ojo.toString().length());
//					PointsMatchInDayList plist = Net.gson.fromJson(
//							ojo.toString(), PointsMatchInDayList.class);
//					// for(int i=0;i<ojo.toString().length();i++){
//					//
//					// }
//					// try {
//					// org.json.JSONObject matchdata = new org.json.JSONObject(
//					// ojo.getString("matchData"));
//					// Iterator<String> it = matchdata.keys();
//					// ArrayList<PointsMatchInDay> all = new
//					// ArrayList<PointsMatchInDay>();
//					// HashMap<Integer, String> map = new HashMap<Integer,
//					// String>();
//					// while (it.hasNext()) {
//					// String str = it.next();
//					// map.put(Integer.valueOf(getDateString(str)), str);
//					//
//					// }
//					// ArrayList<Integer> keys = new ArrayList<Integer>();
//					// keys.addAll(map.keySet());
//					// keys = RandomBallsUtils.sort(keys);
//					// for (int i : keys) {
//					// PointsMatchInDay m = Net.gson.fromJson(
//					// matchdata.getString(map.get(i)),
//					// PointsMatchInDay.class);
//					// m.setYearmonthday(map.get(i));
//					// all.add(m);
//					//
//					// }
//					// for (PointsMatchInDay ms : all) {
//					// for (PointsMatch m : ms.getList()) {
//					// Log.e("pkx",
//					// "points 场次：" + ms.getTotal() + " 主队："
//					// + m.getMainTeam() + " 客队："
//					// + m.getGuestTeam() + "  ID:"
//					// + m.getMatch_id());
//					// }
//					// }
//					Intent torwef = new Intent(BasketBallLottery.this,
//							FootBallPoints.class);
//					Bundle bundle = new Bundle();
//					bundle.putSerializable("matches", plist.getMatchData());
//					torwef.putExtras(bundle);
//					startActivity(torwef);
//					// } catch (JSONException e) {
//					// Log.e("pkx", "ja------>JSONException");
//					// e.printStackTrace();
//					// }
//					Log.e("pkx", "往期彩期POINTS加载成功：" + ojo.toString());

				} else if (msg.arg1 == Constant.NET_ACTION_LOTHIS_MIX) {

					BasketMatchList list = Net.gson.fromJson(
							msg.obj.toString(), BasketMatchList.class);
					Log.e("pkx", "basket matches:"+list.getMatchData().size());
					 Intent towef = new Intent(BasketBallLottery.this,
					 BasketBallRWF.class);
					 Bundle bundle = new Bundle();
					 bundle.putSerializable("matches",
					 list.getMatchData());
					 towef.putExtra("type", 4);
					 towef.putExtras(bundle);
					 startActivity(towef);
//					org.json.JSONObject ojo = (JSONObject) msg.obj;
//					Log.e("pkx", "mix---:" + ojo.toString());
//					Constant.log = ojo.toString();
//					MixMatchesInDayList mlist = Net.gson.fromJson(
//							ojo.toString(), MixMatchesInDayList.class);
//
//					// try {
//					// org.json.JSONObject matchdata = new org.json.JSONObject(
//					// ojo.getString("matchData"));
//					// Iterator<String> it = matchdata.keys();
//					// Log.e("pkx", "dataString" + ojo.getString("matchData"));
//					// ArrayList<MixMatchesInDay> all = new
//					// ArrayList<MixMatchesInDay>();
//					// HashMap<Integer, String> map = new HashMap<Integer,
//					// String>();
//					// while (it.hasNext()) {
//					// String str = it.next();
//					// map.put(Integer.valueOf(getDateString(str)), str);
//					//
//					// }
//					// ArrayList<Integer> keys = new ArrayList<Integer>();
//					// keys.addAll(map.keySet());
//					// keys = RandomBallsUtils.sort(keys);
//					// for (int i : keys) {
//					// MixMatchesInDay m = Net.gson.fromJson(
//					// matchdata.getString(map.get(i)),
//					// MixMatchesInDay.class);
//					// m.setYearmonthday(map.get(i));
//					// all.add(m);
//					//
//					// }
//					// for (MixMatchesInDay ms : all) {
//					// for (MixMatch m : ms.getList()) {
//					// Log.e("pkx",
//					// " MIX 场次：" + ms.getTotal() + " 客队："
//					// + m.getGuestTeam() + " 主队："
//					// + m.getMainTeam() + "  ID:"
//					// + m.getMatch_id() + " oddstr:"
//					// + m.getOdds() + " roddstr:"
//					// + m.getSpfodds());
//					// }
//					// }
//					Intent tomix = new Intent(BasketBallLottery.this,
//							FootballMixBet.class);
//					Bundle bundle = new Bundle();
//					bundle.putSerializable("matches", mlist.getMatchData());
//					tomix.putExtras(bundle);
//					startActivity(tomix);
//					// } catch (JSONException e) {
//					// Log.e("pkx", "ja------>JSONException");
//					// e.printStackTrace();
//					// }
//					Log.e("pkx", "往期彩期混投加载成功：" + ojo.toString());

				} else if (msg.arg1 == Constant.NET_ACTION_LOTHIS_GOALS) {

					BasketMatchList list = Net.gson.fromJson(
							msg.obj.toString(), BasketMatchList.class);
					Log.e("pkx", "basket matches:"+list.getMatchData().size());
					 Intent towef = new Intent(BasketBallLottery.this,
					 BasketBallRWF.class);
					 Bundle bundle = new Bundle();
					 bundle.putSerializable("matches",
					 list.getMatchData());
					 towef.putExtra("type", 4);
					 towef.putExtras(bundle);
					 startActivity(towef);
					 Constant.BASKETBALL_PLAY_TYPE=4;
//					org.json.JSONObject ojo = (JSONObject) msg.obj;
//					Log.e("pkx", "goals:" + ojo.toString());
//					GoalsMatchInDayList glist = Net.gson.fromJson(
//							ojo.toString(), GoalsMatchInDayList.class);
//					// org.json.JSONObject matchdata;
//					// try {
//					// matchdata = new org.json.JSONObject(
//					// ojo.getString("matchData"));
//					// Iterator<String> it = matchdata.keys();
//					// Log.e("pkx", "dataString" + ojo.getString("matchData"));
//					// ArrayList<GoalsMatchInDay> all = new
//					// ArrayList<GoalsMatchInDay>();
//					// HashMap<Integer, String> map = new HashMap<Integer,
//					// String>();
//					// while (it.hasNext()) {
//					// String str = it.next();
//					// map.put(Integer.valueOf(getDateString(str)), str);
//					//
//					// }
//					// ArrayList<Integer> keys = new ArrayList<Integer>();
//					// keys.addAll(map.keySet());
//					// keys = RandomBallsUtils.sort(keys);
//					// for (int i : keys) {
//					// GoalsMatchInDay m = Net.gson.fromJson(
//					// matchdata.getString(map.get(i)),
//					// GoalsMatchInDay.class);
//					// m.setYearmonthday(map.get(i));
//					// all.add(m);
//					//
//					// }
//					// for (GoalsMatchInDay ms : all) {
//					// for (GoalsMatch m : ms.getList()) {
//					// Log.e("pkx",
//					// " goal 场次：" + ms.getTotal() + " 客队："
//					// + m.getGuestTeam() + " 主队："
//					// + m.getMainTeam() + "  ID:"
//					// + m.getMatch_id());
//					// }
//					// }
//					Intent togoals = new Intent(BasketBallLottery.this,
//							FootBallGoals.class);
//					Bundle bundle = new Bundle();
//					bundle.putSerializable("matches", glist.getMatchData());
//					togoals.putExtras(bundle);
//					startActivity(togoals);
//					// } catch (JSONException e) {
//					// e.printStackTrace();
//					// }

				} else if (msg.arg1 == Constant.NET_ACTION_LOTHIS_HALF) {
					JSONObject ojo = (JSONObject) msg.obj;
					Log.e("pkx", "half:" + ojo.toString());
					HalfMatchInDayList hlist = Net.gson.fromJson(
							ojo.toString(), HalfMatchInDayList.class);
					// org.json.JSONObject matchdata;
					// try {
					// matchdata = new org.json.JSONObject(
					// ojo.getString("matchData"));
					// Iterator<String> it = matchdata.keys();
					// Log.e("pkx", "dataString" + ojo.getString("matchData"));
					// ArrayList<HalfMatchInDay> all = new
					// ArrayList<HalfMatchInDay>();
					// HashMap<Integer, String> map = new HashMap<Integer,
					// String>();
					// while (it.hasNext()) {
					// String str = it.next();
					// map.put(Integer.valueOf(getDateString(str)), str);
					//
					// }
					// ArrayList<Integer> keys = new ArrayList<Integer>();
					// keys.addAll(map.keySet());
					// keys = RandomBallsUtils.sort(keys);
					// for (int i : keys) {
					// HalfMatchInDay m = Net.gson.fromJson(
					// matchdata.getString(map.get(i)),
					// HalfMatchInDay.class);
					// m.setYearmonthday(map.get(i));
					// all.add(m);
					//
					// }
					// for (HalfMatchInDay ms : all) {
					// for (HalfMatch m : ms.getList()) {
					// Log.e("pkx",
					// " half 场次：" + ms.getTotal() + " 客队："
					// + m.getGuestTeam() + " 主队："
					// + m.getMainTeam() + "  ID:"
					// + m.getMatch_id());
					// }
					// }
					Intent togoals = new Intent(BasketBallLottery.this,
							FootBallHalf.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("matches", hlist.getMatchData());
					togoals.putExtras(bundle);
					startActivity(togoals);
					// } catch (JSONException e) {
					// e.printStackTrace();
					// }
				}
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				Toast.makeText(BasketBallLottery.this, "当前无篮彩投注赛事信息",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
}
