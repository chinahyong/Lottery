package com.pkx.lottery;

import java.util.ArrayList;

import com.pkx.lottery.headerlist.DayMatchs;
import com.pkx.lottery.headerlist.HalfManagementAdapter;
import com.pkx.lottery.headerlist.HalfMatch;
import com.pkx.lottery.headerlist.HalfMatchInDay;
import com.pkx.lottery.headerlist.ManagementListView;
import com.pkx.lottery.headerlist.Match;
import com.pkx.lottery.headerlist.PointsManagementAdapter;
import com.pkx.lottery.headerlist.WEFManagementAdapter;
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

public class FootBallHalf extends Activity implements OnClickListener {
	private ManagementListView footballList;
	private HalfManagementAdapter myadapter;
	private ArrayList<DayMatchs> dayMatchses;
//	private DayMatchs dayMatchs;
//	private Match match, match1, match2, match3, match4, match5, match6,
//			match7, match8, match9;
	LinearLayout clikItem;
	private View handleBet,clearView;
	private TextView billText;
	private int selectedNum = 0;
	private Intent mIntent;
	private ArrayList<HalfMatchInDay> matchs;

	public void clickBack(View view) {
		alert();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.football_half);
		intiViews();
		mIntent=getIntent();
		matchs=(ArrayList<HalfMatchInDay>) mIntent.getSerializableExtra("matches");
		Constant.FOOTBALL_TYPE=5;
		dayMatchses = new ArrayList<DayMatchs>();
		for(int i=0;i<matchs.size();i++){
			DayMatchs dms=new DayMatchs();
			ArrayList<Match> ms=new ArrayList<Match>();
			dms.setDate(matchs.get(i).getList().get(0).getSn_date()+" "+matchs.get(i).getList().get(0).getSn_week()+"共有"+String.valueOf(matchs.get(i).getTotal())+"场比赛");
			for(HalfMatch hm:matchs.get(i).getList()){
				Match m=new Match();
				m.setSn_week(hm.getSn_week());
				m.setSn(hm.getSn());
				m.setMatchColor(hm.getMatchColor());
				m.setStatus(hm.getStatus());
				m.setSn_date(hm.getSn_date());
				m.setHalfCourt_odds(hm.getHalf_odds());
				m.setMatch_id(hm.getMatch_id());
				m.setLetBall(hm.getLetBall());
				m.setRowNumber(hm.getRowNumber());
				m.setEndTime(hm.getEndTime());
				m.setPollCode(hm.getPollCode());
				m.setLocal_id(hm.getLocal_id());
				m.setUpdate_time(hm.getUpdate_time());
				m.setMainTeam(hm.getMainTeam());
				m.setMid(hm.getMid());
				m.setMatchName(hm.getMatchName());
				m.setPlayCode(hm.getPlayCode());
				m.setGuestTeam(hm.getGuestTeam());
				ms.add(m);
			}
			dms.setMatchs(ms);
			dayMatchses.add(dms);
		}
		Constant.MIX_DAYMATCHESS = dayMatchses;
		footballList = (ManagementListView) findViewById(R.id.footballView);
		footballList.setHeaderView(getLayoutInflater().inflate(
				R.layout.like_qq_group_header, footballList, false));
		myadapter = new HalfManagementAdapter(this, footballList, dayMatchses,
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
		handleBet = findViewById(R.id.handleBet);
		handleBet.setOnClickListener(this);
		clearView = findViewById(R.id.clearView);
		clearView.setOnClickListener(this);
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
				tocheck.putExtra("checkType", 5);
				startActivity(tocheck);
			}
			break;
		case R.id.clearView:
			myadapter.clearBet();
			myadapter.notifyDataSetChanged();
			int selectNum = 0;
			Constant.MATCH_BET_STRS.clear();
			for(ArrayList<ArrayList<Integer>> ins : Constant.MATCH_STATUS){
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
			selectedNum=selectNum;
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
		final AlertDialog alert = new AlertDialog.Builder(this,R.style.dialog).create();
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
