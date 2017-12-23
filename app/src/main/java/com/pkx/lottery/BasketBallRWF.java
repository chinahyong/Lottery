package com.pkx.lottery;

import java.util.ArrayList;

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

import com.pkx.lottery.dto.basketball.BasDayMatchs;
import com.pkx.lottery.dto.basketball.DayMatchBasketdto;
import com.pkx.lottery.headerlist.BasketRWFManagementAdapter;
import com.pkx.lottery.headerlist.DayMatchs;
import com.pkx.lottery.headerlist.ManagementListView;

public class BasketBallRWF extends Activity implements OnClickListener {
	private ManagementListView footballList;
	private BasketRWFManagementAdapter myadapter;
	private ArrayList<BasDayMatchs> dayMatchses;
	private DayMatchs dayMatchs;
	// private Match match, match1, match2, match3, match4, match5, match6,
	// match7, match8, match9;
	LinearLayout clikItem;
	private View handleBet, clearView;
	private TextView billText,typeName;
	private int selectedNum = 0;
	private ArrayList<DayMatchBasketdto> matchs;
	private Intent mIntent;
	private int playType = 0;
	public void clickBack(View view) {
		alert();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basketball_wf);
		mIntent = getIntent();
		playType = mIntent.getIntExtra("type", 0);
		matchs = (ArrayList<DayMatchBasketdto>) mIntent
				.getSerializableExtra("matches");
		dayMatchses = new ArrayList<BasDayMatchs>();
		BasDayMatchs daymatch;
//		Log.e("pkx", "111111111111111111111111111111");
		for (int i = 0; i < matchs.size(); i++) {
			Log.e("pkx", "222222222222222222222222222222222");
			daymatch = new BasDayMatchs();
			// daymatch = matchs.get(i);
			Log.e("pkx", "333333333333333333333333333333333333");
			// daymatch.setMatchs(matchs.get(i).getMatchs());
			// ArrayList<MatchBasketBallWFdto> list=new
			// ArrayList<MatchBasketBallWFdto>();
			// for(MatchBasketBallWFdto m:matchs.get(i).getMatchs()){
			// list.add(m);
			// }
			// daymatch.setMatchs(list);
			daymatch.setMatchs(matchs.get(i).getList());
			Log.e("pkx", "44444444444444444444444444444444444444");
			// Log.e("pkx", "序号" + String.valueOf(i) + "  日期"
			// + matchs.get(i).getYearmonthday());
			daymatch.setDate(matchs.get(i).getList().get(0).getSn_date() + " "
					+ matchs.get(i).getList().get(0).getSn_week() + "共有"
					+ String.valueOf(matchs.get(i).getTotal()) + "场比赛");
			dayMatchses.add(daymatch);
		}
		// BasDayMatchs daymatch;
		// for (int i = 0; i < matchs.size(); i++) {
		// daymatch = new DayMatchs();
		// daymatch.setMatchs(matchs.get(i).getList());
		// Log.e("pkx", "序号" + String.valueOf(i) + "  日期"
		// + matchs.get(i).getYearmonthday());
		// daymatch.setDate(matchs.get(i).getList().get(0).getSn_date() + " "
		// + matchs.get(i).getList().get(0).getSn_week() + "共有"
		// + String.valueOf(matchs.get(i).getTotal()) + "场比赛");
		// dayMatchses.add(daymatch);
		// }
		// for (int i = 0; i < 5; i++) {
		// daymatch = new DayMatchs();
		// ArrayList<Match> ms=new ArrayList<Match>();
		// for(int j=0;j<5;j++){
		// Match m=new Match();
		// m.setDefaultData();
		// ms.add(m);
		// }
		// daymatch.setMatchs(ms);
		// daymatch.setDate("共有"+ "5场比赛");
		// dayMatchses.add(daymatch);
		// }
		intiViews();
		Constant.FOOTBALL_TYPE = 1;
		Constant.BASEKET_MIX_DAYMATCHESS = dayMatchses;
		footballList = (ManagementListView) findViewById(R.id.footballView);
		footballList.setHeaderView(getLayoutInflater().inflate(
				R.layout.like_qq_group_header, footballList, false));
		typeName=(TextView) findViewById(R.id.typeName);
		switch (playType) {
		case 0:
			finish();
			break;
		case 1:
			typeName.setText("胜负");
			myadapter = new BasketRWFManagementAdapter(this, footballList,
					dayMatchses, handler,playType);
			break;
		case 2:
			typeName.setText("让分胜负");
			myadapter = new BasketRWFManagementAdapter(this, footballList,
					dayMatchses, handler,playType);
			break;
		case 3:
			typeName.setText("胜分差");
			myadapter = new BasketRWFManagementAdapter(this, footballList,
					dayMatchses, handler,playType);
			break;
		case 4:
			typeName.setText("大小分");
			myadapter = new BasketRWFManagementAdapter(this, footballList,
					dayMatchses, handler,playType);
			break;
		}
		
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
				Intent tocheck = new Intent(this, BaseketBallBetCheck.class);
				tocheck.putExtra("selectedNum", selectedNum);
				tocheck.putExtra("checkType", playType);
				startActivity(tocheck);
			}
			break;
		case R.id.clearView:
			myadapter.clearBet();
			myadapter.notifyDataSetChanged();
			int selectNum = 0;
			Constant.MATCH_BET_STRS.clear();
			for (ArrayList<ArrayList<Integer>> ins : Constant.BASKET_MATCH_STATUS) {
				for (ArrayList<Integer> in : ins) {
					in.set(2, 0);
				}
			}
			for (ArrayList<ArrayList<Integer>> ins : Constant.BASKET_MATCH_STATUS) {
				for (ArrayList<Integer> in : ins) {
					for (int i = 0; i < 2; i++) {
						if (in.get(i) == 1) {
							in.set(2, 1);
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
				Constant.BASKET_MATCH_STATUS = null;
				Constant.BASEKET_MIX_DAYMATCHESS.clear();
				Constant.BASEKET_MIX_DAYMATCHESS = null;
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
				for (ArrayList<ArrayList<Integer>> ins : Constant.BASKET_MATCH_STATUS) {
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
				for (ArrayList<ArrayList<Integer>> ins : Constant.BASKET_MATCH_STATUS) {
					for (ArrayList<Integer> in : ins) {
						in.set(2, 0);
					}
				}
				for (ArrayList<ArrayList<Integer>> ins : Constant.BASKET_MATCH_STATUS) {
					for (ArrayList<Integer> in : ins) {
						for (int i = 0; i < 2; i++) {
							if (in.get(i) == 1) {
								in.set(2, 1);
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
