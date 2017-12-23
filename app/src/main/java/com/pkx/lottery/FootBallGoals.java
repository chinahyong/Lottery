package com.pkx.lottery;

import java.util.ArrayList;

import com.pkx.lottery.headerlist.DayMatchs;
import com.pkx.lottery.headerlist.GoalsManagementAdapter;
import com.pkx.lottery.headerlist.GoalsMatch;
import com.pkx.lottery.headerlist.GoalsMatchInDay;
import com.pkx.lottery.headerlist.ManagementListView;
import com.pkx.lottery.headerlist.Match;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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

public class FootBallGoals extends Activity implements OnClickListener {
	private ManagementListView footballList;
	private GoalsManagementAdapter myadapter;
	private ArrayList<DayMatchs> dayMatchses;
	// private DayMatchs dayMatchs;
	// private Match match, match1, match2, match3, match4, match5, match6,
	// match7, match8, match9;
	LinearLayout clikItem;
	private View handleBet, clearView;
	private TextView billText;
	private int selectedNum = 0;
	private Intent mIntent;
	private ArrayList<GoalsMatchInDay> matchs;

	public void clickBack(View view) {
		alert();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.football_goals);
		intiViews();
		mIntent = getIntent();
		matchs = (ArrayList<GoalsMatchInDay>) mIntent
				.getSerializableExtra("matches");
		Constant.FOOTBALL_TYPE = 4;
		// match1 = new Match();
		// match1.setGuest("客队比分一");
		// match1.setHost("主队比分一");
		// match1.setTime("001 18:20");
		// match1.setName("国际比分一");
		// match2 = new Match();
		// match2.setGuest("客队比分2");
		// match2.setHost("主队比分2");
		// match2.setTime("001 18:21");
		// match2.setName("国际比分2");
		// match3 = new Match();
		// match3.setGuest("客队比分3");
		// match3.setHost("主队比分3");
		// match3.setTime("001 18:22");
		// match3.setName("国际比分3");
		// match4 = new Match();
		// match4.setGuest("客队比分4");
		// match4.setHost("主队比分4");
		// match4.setTime("001 18:23");
		// match4.setName("国际比分4");
		// match5 = new Match();
		// match5.setGuest("客队比分5");
		// match5.setHost("主队比分5");
		// match5.setTime("001 18:24");
		// match5.setName("国际比分5");
		// match6 = new Match();
		// match6.setGuest("客队比分6");
		// match6.setHost("主队比分6");
		// match6.setTime("001 18:25");
		// match6.setName("国际比分6");
		// match7 = new Match();
		// match7.setGuest("客队比分7");
		// match7.setHost("主队比分7");
		// match7.setTime("001 18:26");
		// match7.setName("国际比分7");
		// match8 = new Match();
		// match8.setGuest("客队比分8");
		// match8.setHost("主队比分8");
		// match8.setTime("001 18:27");
		// match8.setName("国际比分8");
		// match9 = new Match();
		// match9.setGuest("客队比分9");
		// match9.setHost("主队比分9");
		// match9.setTime("001 18:28");
		// match9.setName("国际比分9");
		// ArrayList<Match> day1Matchs = new ArrayList<Match>();
		// day1Matchs.add(match1);
		// day1Matchs.add(match2);
		// day1Matchs.add(match3);
		// ArrayList<Match> day2Matchs = new ArrayList<Match>();
		// day2Matchs.add(match4);
		// day2Matchs.add(match5);
		// ArrayList<Match> day3Matchs = new ArrayList<Match>();
		// day3Matchs.add(match6);
		// day3Matchs.add(match7);
		// day3Matchs.add(match8);
		// day3Matchs.add(match9);
		// DayMatchs day1 = new DayMatchs();
		// day1.setMatchs(day1Matchs);
		// day1.setDate("周三共有" + day1.getMatchs().size() + "场赛事");
		// DayMatchs day2 = new DayMatchs();
		// day2.setMatchs(day2Matchs);
		// day2.setDate("周4共有" + day2.getMatchs().size() + "场赛事");
		// DayMatchs day3 = new DayMatchs();
		// day3.setMatchs(day3Matchs);
		// day3.setDate("周5共有" + day3.getMatchs().size() + "场赛事");
		// dayMatchses.add(day1);
		// dayMatchses.add(day2);
		// dayMatchses.add(day3);

		dayMatchses = new ArrayList<DayMatchs>();
		for (int i = 0; i < matchs.size(); i++) {
			DayMatchs dms = new DayMatchs();
			ArrayList<Match> mas = new ArrayList<Match>();
			dms.setDate(matchs.get(i).getList().get(0).getSn_date() + " "
					+ matchs.get(i).getList().get(0).getSn_week() + "共有"
					+ String.valueOf(matchs.get(i).getTotal()) + "场比赛");
			for (GoalsMatch gm : matchs.get(i).getList()) {
				Match m = new Match();
				m.setSn_week(gm.getSn_week());
				m.setSn(gm.getSn());
				m.setStatus(gm.getStatus());
				m.setSn_date(gm.getSn_date());
				m.setMatch_id(gm.getMatch_id());
				m.setLetBall(gm.getLetBall());
				m.setRowNumber(gm.getRowNumber());
				m.setEndTime(gm.getEndTime());
				m.setUpdate_time(gm.getUpdate_time());
				m.setTotalgoal_odds(gm.getTotalgoal_odds());
				m.setMainTeam(gm.getMainTeam());
				m.setMatchName(gm.getMatchName());
				m.setPlayCode(gm.getPlayCode());
				m.setGuestTeam(gm.getGuestTeam());
				mas.add(m);
			}
			dms.setMatchs(mas);
			dayMatchses.add(dms);
		}
		Constant.MIX_DAYMATCHESS = dayMatchses;
		footballList = (ManagementListView) findViewById(R.id.footballView);
		footballList.setHeaderView(getLayoutInflater().inflate(
				R.layout.like_qq_group_header, footballList, false));
		myadapter = new GoalsManagementAdapter(this, footballList, dayMatchses,
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

	private void intiViews() {
		clearView = findViewById(R.id.clearView);
		clearView.setOnClickListener(this);
		handleBet = findViewById(R.id.handleBet);
		handleBet.setOnClickListener(this);
		billText = (TextView) findViewById(R.id.billText);
		ArrayList<String> select1 = new ArrayList<String>();
		select1.add("3");
		ArrayList<String> select2 = new ArrayList<String>();
		select2.add("3");
		select2.add("2");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.handleBet:
			if (selectedNum < 2) {
				Toast.makeText(this, "最少选择两场比赛", Toast.LENGTH_SHORT).show();
			} else {
				Intent tocheck = new Intent(this, FootballMixBetCheck.class);
				tocheck.putExtra("selectedNum", selectedNum);
				tocheck.putExtra("checkType", 4);
				startActivity(tocheck);
			}
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
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (selectedNum > 0)
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

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			try {
				recordPostionGroup = Integer.parseInt(msg.obj.toString() + "");
			} catch (Exception e) {
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
			}

		}

	};
}
