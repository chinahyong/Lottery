package com.pkx.lottery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.pkx.lottery.bean.SelectFootBallTeam;
import com.pkx.lottery.headerlist.DayMatchs;
import com.pkx.lottery.headerlist.ManagementListView;
import com.pkx.lottery.headerlist.Match;
import com.pkx.lottery.headerlist.MixMatch;
import com.pkx.lottery.headerlist.MixMatchesInDay;
import com.pkx.lottery.headerlist.MyManagementAdapter;
import com.pkx.lottery.utils.RandomBallsUtils;
import com.pkx.lottery.utils.MySetUtil;
import com.pkx.lottery.utils.SetUtil;

import android.R.mipmap;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FootballMixBet extends Activity implements OnClickListener {
	private SelectFootBallTeam t1, t2, t3, t4, t5, t6, t7, t8;
	private ArrayList<SelectFootBallTeam> teams;
	private ManagementListView footballList;
	private MyManagementAdapter myadapter;
	private ArrayList<DayMatchs> dayMatchses;
	private DayMatchs dayMatchs;
	private Match match, match1, match2, match3, match4, match5, match6,
			match7, match8, match9;
	LinearLayout clikItem;
	private View handleBet, footballType, clearView;
	private TextView billText;
	private int selectedNum = 0;
	private Intent mIntent;
	private ArrayList<MixMatchesInDay> matchs;

	public void clickBack(View view) {
		alert();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.football_mix);
		intiViews();
		mIntent = getIntent();
		matchs = (ArrayList<MixMatchesInDay>) mIntent
				.getSerializableExtra("matches");
		Constant.FOOTBALL_TYPE = 6;
		dayMatchses = new ArrayList<DayMatchs>();
		for (int i = 0; i < matchs.size(); i++) {
			DayMatchs ds = new DayMatchs();
			ArrayList<Match> dsm = new ArrayList<Match>();
			ds.setDate(matchs.get(i).getList().get(0).getSn_date() + " "
					+ matchs.get(i).getList().get(0).getSn_week() + "共有"
					+ String.valueOf(matchs.get(i).getTotal()) + "场比赛");
			for (MixMatch m : matchs.get(i).getList()) {
				if(m.getSpfodds().size()!=0){
					Log.e("pkx", "-----赔率不为零---"+m.getSpfodds().size());
				}else{
					Log.e("pkx", "-----赔率为零"+m.getSpfodds().size());
				}
				if (m.getSpfodds().size()==3) {
					Match ma = new Match();
					ma.setScore_odds(m.getScore_odds());
					ma.setHalfCourt_odds(m.getHalfCourt_odds());
					ma.setTotalgoal_odds(m.getTotalgoal_odds());
					ma.setSn_week(m.getSn_week());
					ma.setSn(m.getSn());
					ma.setStatus(m.getStatus());
					ma.setSn_date(m.getSn_date());
					ma.setMatchName(m.getMatchName());
					Log.e("pkx", "设置赛名：" + m.getMatchName());
					Log.e("pkx", "设置完比赛名：" + ma.getMatchName());
					ma.setMatch_id(m.getMatch_id());
					ma.setLetBall(m.getLetBall());
					ma.setRowNumber(m.getRowNumber());
					ma.setEndTime(m.getEndTime());
					ma.setPollCode(m.getPollCode());
					ma.setUpdate_time(m.getUpdate_time());
					ma.setMainTeam(m.getMainTeam());
					ma.setGuest(m.getGuestTeam());
					ma.setOdds(m.getOdds());
					ma.setSpfodds(m.getSpfodds());
					ma.setMid(m.getMid());
					ma.setPlayCode(m.getPlayCode());
					ma.setMatchColor(m.getMatchColor());
					dsm.add(ma);
				}
			}
			ds.setMatchs(dsm);
			dayMatchses.add(ds);
		}
		Constant.MIX_DAYMATCHESS = dayMatchses;
		footballList = (ManagementListView) findViewById(R.id.footballView);
		footballList.setHeaderView(getLayoutInflater().inflate(
				R.layout.like_qq_group_header, footballList, false));
		myadapter = new MyManagementAdapter(this, footballList, dayMatchses,
				handler);
		footballList.setAdapter(myadapter);
		footballList.setHandler(handler);
		footballList.setVisibility(View.VISIBLE);
		clikItem = (LinearLayout) findViewById(R.id.clikItem);
		for (int i = 0; i < dayMatchses.size(); i++) {
			footballList.expandGroup(i);
			myadapter.setGroupClickStatus(i, 1);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.handleBet:
			//
			// int day = 1;
			// for (ArrayList<ArrayList<Integer>> ins : Constant.MATCH_STATUS) {
			// int index = 1;
			// for (ArrayList<Integer> in : ins) {
			// String match = "";
			// for (int i : in) {
			// match += String.valueOf(i);
			// }
			// Log.e("pkx", "确定:第" + day + "天第" + index + "场比赛:" + match);
			// index++;
			// }
			// day++;
			// }
			if (selectedNum < 2) {
				Toast.makeText(this, "最少选择两场比赛", Toast.LENGTH_SHORT).show();
			} else {
				Intent tocheck = new Intent(this, FootballMixBetCheck.class);
				tocheck.putExtra("checkType", 6);
				tocheck.putExtra("selectedNum", selectedNum);
				startActivity(tocheck);
			}
			break;
		case R.id.footballType:
			// RandomBallsUtils.alertFootballTypeDialog(this, handler);
			break;
		case R.id.clearView:
			myadapter.clearBet();
			myadapter.notifyDataSetChanged();
			int selectNum = 0;
			Constant.MATCH_BET_STRS.clear();
			for (ArrayList<ArrayList<Integer>> ins : Constant.MATCH_STATUS) {
				for (ArrayList<Integer> in : ins) {
					in.set(64, 0);
				}
			}
			for (ArrayList<ArrayList<Integer>> ins : Constant.MATCH_STATUS) {
				for (ArrayList<Integer> in : ins) {
					for (int i = 0; i < 55; i++) {
						if (in.get(i) == 1) {
							in.set(64, 1);
							selectNum++;
							break;
						}

					}
				}
			}

			billText.setText("已选" + selectNum + "场比赛");
			selectedNum = selectNum;
			break;

		default:
			break;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Constant.MATCH_STATUS.clear();
		// Constant.MATCH_STATUS=null;
	}

	private void intiViews() {
		clearView = findViewById(R.id.clearView);
		clearView.setOnClickListener(this);
		footballType = findViewById(R.id.footballType);
		// footballType.setOnClickListener(this);
		handleBet = findViewById(R.id.handleBet);
		handleBet.setOnClickListener(this);
		billText = (TextView) findViewById(R.id.billText);
		ArrayList<String> select1 = new ArrayList<String>();
		select1.add("3");
		ArrayList<String> select2 = new ArrayList<String>();
		select2.add("3");
		select2.add("2");
		t1 = new SelectFootBallTeam();
		t1.setTeamNumber("001");
		t1.setSPF(select2);
		t2 = new SelectFootBallTeam();
		t2.setTeamNumber("002");
		t2.setSPF(select1);
		t3 = new SelectFootBallTeam();
		t3.setTeamNumber("003");
		t3.setSPF(select1);
		t4 = new SelectFootBallTeam();
		t4.setTeamNumber("004");
		t4.setSPF(select1);
		t5 = new SelectFootBallTeam();
		t5.setTeamNumber("005");
		t5.setSPF(select1);
		teams = new ArrayList<SelectFootBallTeam>();
		teams.add(t1);
		teams.add(t2);
		teams.add(t3);
		teams.add(t4);
		teams.add(t5);
	}

	private void testBet() {
		String betInfo = "xx1-1:3,2;xx1-2:3;xx2-1:3,2;xx3-1:3;xx4-1:3;xx5-1:3|3*1";
		String[] infoSet = betInfo.split("\\|");
		String[] betData = infoSet[0].split(";");
		String[] passType = infoSet[1].split(";");
		ArrayList<String> dataList = new ArrayList<String>();
		ArrayList<String> typeList = new ArrayList<String>();
		for (String s : betData) {
			dataList.add(s);
		}
		for (String s : passType) {
			typeList.add(s);
		}
		ArrayList<String> betItems = new ArrayList<String>();
		for (String d : betData) {
			String[] dataSet = d.split(":");
			String game = dataSet[0];
			String[] points = dataSet[1].split(",");
			for (String p : points) {
				betItems.add(game + "-" + p);
			}
		}
		long l = System.currentTimeMillis();
		for (int i = 1; i < 20; i++) {
			for (int j = 1; j < 7; j++) {
				if (j < i) {
					List<int[]> myset = MySetUtil.set(j, i);
					Log.e("pkx", "" + i + "取" + j + "Random:"
							+ RandomBallsUtils.getBallBets(j, i) + "MySet:"
							+ myset.size());
				}
			}
		}
		Log.e("pkx", "耗时：" + (System.currentTimeMillis() - l));
		List<int[]> setList = MySetUtil.set(2, betItems.size());// 获取组合项
		int f = 1;
		for (int[] a : setList) {
			Log.e("pkx", "组合" + f + ":" + Arrays.toString(a));
			f = f + 1;
		}
		Log.e("pkx", "比赛组合项：" + betItems);
		ArrayList<ArrayList<String>> betSet = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < setList.size(); i++) {
			ArrayList<String> betSetItem = new ArrayList<String>();
			Set<String> matchSet = new HashSet<String>();
			for (int k : setList.get(i)) {
				// 循环下标，获得投注项
				betSetItem.add(betItems.get(k - 1));
			}
			Log.e("pkx", "排列：" + (i + 1) + Arrays.toString(setList.get(i)));
			// 此处判断投注项是否合法：同一场比赛的结果在同一个串中
			String betStr = "";
			for (String s : betSetItem) {
				betStr += s + "+";
				matchSet.add(s.substring(0, 3));
			}

			// Log.e("pkx", "所有投注"+(i+1)+":"+betStr);
			if (matchSet.size() == betSetItem.size()) {
				String okStr = "";
				for (String s : betSetItem) {
					okStr += s + "+";
				}
				Log.e("pkx", "合法投注" + (i + 1) + ":" + okStr);
				betSet.add(betSetItem);
			}

		}
		for (ArrayList<String> a : betSet) {
			String str = "";
			for (String s : a) {
				str += s + " ";
			}
			// Log.e("pkx", "match:"+str);
		}
		Log.e("pkx", "bets:" + betSet.size());
		for (String bt : betItems) {
			Log.e("pkx", "beItem:" + bt);
		}
	}

	// 顶部时的点击
	public int recordPostionGroup = 0;
	public int newPostionGroup = 0;// 当前展开的级id
	public int cosPostionGroup = 0;

	public void topClick(View v) {
		String state = "";
		if (newPostionGroup == -1) {
			clikItem.setVisibility(8);
			newPostionGroup = -1;
			return;
		} else if (newPostionGroup == -2) {

		} else {
			recordPostionGroup = newPostionGroup;
		}

		if (myadapter.groupStatusMap.containsKey(recordPostionGroup)) {
			state = myadapter.groupStatusMap.get(recordPostionGroup) + "";
		}
		if ("1".equals(state)) {
			footballList.collapseGroup(recordPostionGroup);
			myadapter.groupStatusMap.put(recordPostionGroup, 0);
			clikItem.setVisibility(8);
			newPostionGroup = -3;
		} else {
			// exList.expandGroup(recordPostionGroup);
			// adapter.groupStatusMap.put(recordPostionGroup, 1);
			clikItem.setVisibility(8);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (selectedNum > 0)
				alert();
		}
		return super.onKeyDown(keyCode, event);
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
				Constant.MATCH_STATUS = null;
				Constant.MIX_DAYMATCHESS.clear();
				Constant.MIX_DAYMATCHESS = null;
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

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			try {
				recordPostionGroup = Integer.parseInt(msg.obj.toString() + "");
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (msg.what == 1) {
				// 画了表头的
				clikItem.setVisibility(0);
				if (newPostionGroup == -3) {
					newPostionGroup = recordPostionGroup;
				}
			} else if (msg.what == 2) {
				// 隐藏了画的表头的
				clikItem.setVisibility(8);
				recordPostionGroup = 0;
			} else if (msg.what == 3) {
				// 点击展开的
				clikItem.setVisibility(8);
				newPostionGroup = recordPostionGroup;
			} else if (msg.what == 4) {
				// 点击收缩的
				clikItem.setVisibility(8);
				cosPostionGroup = recordPostionGroup;
				newPostionGroup = -2;
			} else if (msg.what == Constant.FOOTBALL_BET_FRESH) {
				int day = 1;
				for (ArrayList<ArrayList<Integer>> ins : Constant.MATCH_STATUS) {
					int index = 1;
					for (ArrayList<Integer> in : ins) {
						String match = "";
						for (int i : in) {
							match += String.valueOf(i);
						}
						Log.e("pkx", "第" + day + "天第" + index + "场比赛投注信息:"
								+ match);
						index++;
					}
					day++;
				}
			} else if (msg.what == Constant.FOOTBALL_BET_MSG) {
				int selectNum = 0;
				Constant.MATCH_BET_STRS.clear();
				for (ArrayList<ArrayList<Integer>> ins : Constant.MATCH_STATUS) {
					for (ArrayList<Integer> in : ins) {
						in.set(64, 0);
					}
				}
				// for(int i=0;i<Constant.MATCH_STATUS.size();i++){
				// Constant.MATCH_BET_STRS.add(new
				// ArrayList<ArrayList<Integer>>());
				// for(int j=0;j<Constant.MATCH_STATUS.get(i).size();j++){
				// Constant.MATCH_BET_STRS.get(i).add(new ArrayList<Integer>());
				// for(int k=0;k<55;k++){
				// if(Constant.MATCH_STATUS.get(i).get(j).get(k)==1){
				// Constant.MATCH_BET_STRS.get(i).get(j).set(k, 1);
				// }
				// }
				// }
				// }
				for (ArrayList<ArrayList<Integer>> ins : Constant.MATCH_STATUS) {
					for (ArrayList<Integer> in : ins) {
						for (int i = 0; i < 55; i++) {
							if (in.get(i) == 1) {
								in.set(64, 1);
								selectNum++;
								break;
							}

						}
					}
				}

				billText.setText("已选" + selectNum + "场比赛");
				selectedNum = selectNum;
			} else if (msg.what == Constant.FOOTBALL_TYPE_CHANGED) {
				switch (msg.arg1) {
				case 1:
					Intent toWef = new Intent(FootballMixBet.this,
							FootBallWEF.class);
					startActivity(toWef);
					finish();
					break;
				case 2:
					Intent torWef = new Intent(FootballMixBet.this,
							FootBallRWEF.class);
					startActivity(torWef);
					finish();
					break;
				case 3:
					Intent topoints = new Intent(FootballMixBet.this,
							FootBallPoints.class);
					startActivity(topoints);
					finish();
					break;
				case 4:
					Intent togoals = new Intent(FootballMixBet.this,
							FootBallGoals.class);
					startActivity(togoals);
					finish();
					break;
				case 5:
					Intent tohalf = new Intent(FootballMixBet.this,
							FootBallHalf.class);
					startActivity(tohalf);
					finish();
					break;
				case 6:
					Intent tomix = new Intent(FootballMixBet.this,
							FootballMixBet.class);
					startActivity(tomix);
					finish();
					break;
				}
			} else if (msg.what == 10086) {
				Log.e("pkx", "10000000000000086");
				myadapter.notifyDataSetChanged();
			}

		}

	};

}
