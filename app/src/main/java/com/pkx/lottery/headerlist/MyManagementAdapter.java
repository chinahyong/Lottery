package com.pkx.lottery.headerlist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkx.lottery.Constant;
import com.pkx.lottery.R;
import com.pkx.lottery.headerlist.ManagementListView.QQHeaderAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MyManagementAdapter extends BaseExpandableListAdapter implements
		QQHeaderAdapter {
	// private Match currentMatch;
	private ManagementListView listView;
	// private static int dialogCount=0;
	// private ArrayList<ArrayList<ArrayList<Integer>>> status;
	private Context context;
	private Handler handler;
	final AlertDialog alert;
	private ArrayList<TextView> mtextviews;
	private ArrayList<TextView> mtextviews1;
	// = new AlertDialog.Builder(ctx).create();
	private ArrayList<View> currentFirstSixView, showThreeView;
	private ArrayList<TextView> curentText1, curentText;
	private ArrayList<DayMatchs> dayMatchses;
	private ArrayList<ArrayList<Match>> matchsInOnedayList;
	private Message betMsg;
	private int whitetext = Color.parseColor("#ffffff");
	private int redtext = Color.parseColor("#e6544b");
	private int greentext = Color.parseColor("#2ca403");
	private int graytext = Color.parseColor("#4D4D4D");
	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, Integer> groupStatusMap = new HashMap<Integer, Integer>();
	private ArrayList<View> clickViews;
	private int fullChildposition, fullGroupPositin;

	public MyManagementAdapter(Context context, ManagementListView listView,
			ArrayList<DayMatchs> dayMatchses, Handler handler) {
		this.context = context;
		alert = new AlertDialog.Builder(context).create();
		this.listView = listView;
		this.dayMatchses = dayMatchses;
		this.handler = handler;
		matchsInOnedayList = new ArrayList<ArrayList<Match>>();
		for (DayMatchs ms : dayMatchses) {
			matchsInOnedayList.add(ms.getMatchs());
		}
		if (Constant.MATCH_STATUS == null) {// 64比赛选中标记--63胆标记--59让球标记--62 ID--
			Constant.MATCH_INFOS = new ArrayList<ArrayList<String>>();
			Constant.MATCH_STATUS = new ArrayList<ArrayList<ArrayList<Integer>>>();
			for (int i = 0; i < dayMatchses.size(); i++) {
				Constant.MATCH_STATUS.add(new ArrayList<ArrayList<Integer>>());
				Constant.MATCH_INFOS.add(new ArrayList<String>());
				for (Match ma : dayMatchses.get(i).getMatchs()) {
					ArrayList<Integer> ins = getIntList(65);
					ins.set(62, Integer.valueOf(ma.getId()));
					Constant.MATCH_INFOS.get(i).add(ma.getMatchInfoString());
					Constant.MATCH_STATUS.get(i).add(ins);
				}
			}
		}
	}

	public void clearBet() {
		// 64比赛选中标记--63胆标记--59让球标记--62 ID--
		Constant.MATCH_STATUS = null;
		Constant.MATCH_INFOS = new ArrayList<ArrayList<String>>();
		Constant.MATCH_STATUS = new ArrayList<ArrayList<ArrayList<Integer>>>();
		for (int i = 0; i < dayMatchses.size(); i++) {
			Constant.MATCH_STATUS.add(new ArrayList<ArrayList<Integer>>());
			Constant.MATCH_INFOS.add(new ArrayList<String>());
			for (Match ma : dayMatchses.get(i).getMatchs()) {
				ArrayList<Integer> ins = getIntList(65);
				ins.set(62, Integer.valueOf(ma.getId()));
				Constant.MATCH_INFOS.get(i).add(ma.getMatchInfoString());
				Constant.MATCH_STATUS.get(i).add(ins);
			}
		}

	}

	private ArrayList<Integer> getIntList(int num) {
		ArrayList<Integer> nums = new ArrayList<Integer>();
		for (int i = 0; i < num; i++) {
			nums.add(0);
		}
		return nums;
	}

	@Override
	public int getGroupCount() {
		return dayMatchses.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return dayMatchses.get(groupPosition);
	}

	@Override
	public int getChildrenCount(int groupPosition) {

		return matchsInOnedayList.get(groupPosition).size();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return matchsInOnedayList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public int getQQHeaderState(int groupPosition, int childPosition) {

		final int childCount = getChildrenCount(groupPosition);
		if (childPosition == childCount - 1) {
			return PINNED_HEADER_PUSHED_UP;
		} else if (childPosition == -1
				&& !listView.isGroupExpanded(groupPosition)) {
			return PINNED_HEADER_GONE;
		} else {
			return PINNED_HEADER_VISIBLE;
		}
	}

	@Override
	public void configureQQHeader(View header, int groupPosition,
			int childPosition, int alpha) {

		// Map<String,String> groupData =
		// (Map<String,String>)this.getGroup(groupPosition);
		((TextView) header.findViewById(R.id.groupto)).setText(dayMatchses.get(
				groupPosition).getDate());

	}

	@Override
	public void setGroupClickStatus(int groupPosition, int status) {
		groupStatusMap.put(groupPosition, status);
	}

	@Override
	public int getGroupClickStatus(int groupPosition) {

		if (groupStatusMap.containsKey(groupPosition)) {
			return groupStatusMap.get(groupPosition);
		} else {
			return 0;
		}
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.match_child, null);
		}
		Match child = dayMatchses.get(groupPosition).getMatchs()
				.get(childPosition);
		TextView handic, matchNameText, matchNumberText;
		final TextView noHandicWinText;
		final TextView noHandicEqualText;
		final TextView noHandicLoseText;
		final TextView handicWinText;
		final TextView handicEqualText;
		final TextView handicLoseText;
		final TextView noHandicWinText1;
		final TextView noHandicEqualText1;
		final TextView noHandicLoseText1;
		final TextView handicWinText1;
		final TextView handicEqualText1;
		final TextView handicLoseText1;
		TextView hostTeamNameText, visitTeamNameText;
		final View matchShowView;
		final View noHandicWinView;
		final View noHandicEqualView;
		final View noHandicLoseView;
		View handicView, noHandicView;
		final View handicWinView;
		final View handicEqualView;
		final View handicLoseView;
		final TextView showText1, showText2;
		// String[]
		// infos=Constant.MATCH_INFOS.get(groupPosition).get(childPosition).split("*");
		handic = (TextView) convertView.findViewById(R.id.handic);
		showText1 = (TextView) convertView.findViewById(R.id.showText1);
		showText2 = (TextView) convertView.findViewById(R.id.showText2);
		showText1.setTextColor(graytext);
		showText2.setTextColor(graytext);
		matchNameText = (TextView) convertView.findViewById(R.id.matchNameText);
		// matchNameText.setText(infos[0]);
		handicView = convertView.findViewById(R.id.handicView);
		noHandicView = convertView.findViewById(R.id.noHandicView);
		if (child.getOdds() == null || child.getOdds().size() == 0) {
			handicView.setVisibility(View.GONE);
		} else {
			handicView.setVisibility(View.VISIBLE);
		}
		if (child.getLetBall() != null && child.getLetBall().length() > 1) {
			handic.setText("(" + child.getLetBall() + ")");
			if (child.getLetBall().equals("+1")
					|| child.getLetBall().equals("+1")
					|| child.getLetBall().equals("+2")
					|| child.getLetBall().equals("+3")
					|| child.getLetBall().equals("+4")
					|| child.getLetBall().equals("+5")) {
				handic.setTextColor(redtext);
			} else {
				handic.setTextColor(greentext);
			}
		}
		if (child.getSpfodds() == null || child.getSpfodds().size() == 0) {
			noHandicView.setVisibility(View.GONE);
		} else {
			noHandicView.setVisibility(View.VISIBLE);
		}
		matchNameText.setText(child.getName());
		matchNumberText = (TextView) convertView
				.findViewById(R.id.matchNumberText);
		matchNumberText.setText(child.getRowNumber());
		noHandicWinText = (TextView) convertView
				.findViewById(R.id.noHandicWinText);
		noHandicEqualText = (TextView) convertView
				.findViewById(R.id.noHandicEqualText);
		noHandicLoseText = (TextView) convertView
				.findViewById(R.id.noHandicLoseText);
		handicWinText = (TextView) convertView.findViewById(R.id.handicWinText);
		handicEqualText = (TextView) convertView
				.findViewById(R.id.handicEqualText);
		handicLoseText = (TextView) convertView
				.findViewById(R.id.handicLoseText);
		noHandicWinText1 = (TextView) convertView
				.findViewById(R.id.noHandicWinText1);
		noHandicEqualText1 = (TextView) convertView
				.findViewById(R.id.noHandicEqualText1);
		noHandicLoseText1 = (TextView) convertView
				.findViewById(R.id.noHandicLoseText1);
		handicWinText1 = (TextView) convertView
				.findViewById(R.id.handicWinText1);
		handicEqualText1 = (TextView) convertView
				.findViewById(R.id.handicEqualText1);
		handicLoseText1 = (TextView) convertView
				.findViewById(R.id.handicLoseText1);
		hostTeamNameText = (TextView) convertView
				.findViewById(R.id.hostTeamNameText);
		if (child.getHost().length() < 6) {
			hostTeamNameText.setText(child.getHost());
		} else {
			hostTeamNameText.setText(child.getHost().substring(0, 4));
		}

		visitTeamNameText = (TextView) convertView
				.findViewById(R.id.visitTeamNameText);
		if (child.getGuest().length() < 6) {
			visitTeamNameText.setText(child.getGuest());
		} else {
			visitTeamNameText.setText(child.getGuest().substring(0, 4));
		}
		// visitTeamNameText.setText(child.getGuest());
		matchShowView = convertView.findViewById(R.id.matchShowView);
		noHandicWinView = convertView.findViewById(R.id.noHandicWinView);

		if (Constant.MIX_DAYMATCHESS.get(groupPosition).getMatchs()
				.get(childPosition).getSpfodds() != null
				&& Constant.MIX_DAYMATCHESS.get(groupPosition).getMatchs()
						.get(childPosition).getSpfodds().size() == 3) {
			noHandicWinText.setText(Constant.MIX_DAYMATCHESS.get(groupPosition)
					.getMatchs().get(childPosition).getSpfodds().get(2));
			noHandicEqualText.setText(Constant.MIX_DAYMATCHESS
					.get(groupPosition).getMatchs().get(childPosition)
					.getSpfodds().get(1));
			noHandicLoseText.setText(Constant.MIX_DAYMATCHESS
					.get(groupPosition).getMatchs().get(childPosition)
					.getSpfodds().get(0));

		}
		if (Constant.MIX_DAYMATCHESS.get(groupPosition).getMatchs()
				.get(childPosition).getOdds() != null
				&& Constant.MIX_DAYMATCHESS.get(groupPosition).getMatchs()
						.get(childPosition).getOdds().size() == 3) {
			handicWinText.setText(Constant.MIX_DAYMATCHESS.get(groupPosition)
					.getMatchs().get(childPosition).getOdds().get(2));
			handicEqualText.setText(Constant.MIX_DAYMATCHESS.get(groupPosition)
					.getMatchs().get(childPosition).getOdds().get(1));
			handicLoseText.setText(Constant.MIX_DAYMATCHESS.get(groupPosition)
					.getMatchs().get(childPosition).getOdds().get(0));

		}
		if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition).get(0) == 0) {
			noHandicWinView.setBackgroundResource(R.drawable.cp);
			noHandicWinText.setTextColor(graytext);
			noHandicWinText1.setTextColor(graytext);
		} else {
			noHandicWinView.setBackgroundResource(R.drawable.pink);
			noHandicWinText.setTextColor(whitetext);
			noHandicWinText1.setTextColor(whitetext);
		}
		noHandicWinView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
						.get(0) == 0) {
					v.setBackgroundResource(R.drawable.pink);
					noHandicWinText.setTextColor(whitetext);
					noHandicWinText1.setTextColor(whitetext);
					Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
							.set(0, 1);
				} else {
					v.setBackgroundResource(R.drawable.cp);
					noHandicWinText.setTextColor(graytext);
					noHandicWinText1.setTextColor(graytext);
					Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
							.set(0, 0);
				}
				betMsg = new Message();
				betMsg.what = Constant.FOOTBALL_BET_MSG;
				handler.sendMessage(betMsg);

				if (showThreeView != null && showThreeView.size() == 3) {
					int select = 0;
					for (int i = 6; i < 55; i++) {
						if (Constant.MATCH_STATUS.get(groupPosition)
								.get(childPosition).get(i) == 1) {
							select++;
						}
					}
					if (select > 0) {
						for (int i = 0; i < 6; i++) {
							if (Constant.MATCH_STATUS.get(groupPosition)
									.get(childPosition).get(i) == 1) {
								select++;
							}
						}
						matchShowView.setBackgroundResource(
								R.drawable.pink);
						TextView v1 = (TextView) (matchShowView.findViewById(R.id.showText1));
						TextView v2 = (TextView) (matchShowView.findViewById(R.id.showText2));
						v1.setText("已选");
						v1.setTextColor(whitetext);
						v2.setTextColor(whitetext);
						v2.setText("" + select + "项");
					} else {
						matchShowView.setBackgroundResource(
								R.drawable.cp);
						TextView v1 = (TextView) (matchShowView.findViewById(R.id.showText1));
						TextView v2 = (TextView) (matchShowView.findViewById(R.id.showText2));
						v1.setTextColor(graytext);
						v2.setTextColor(graytext);
						v1.setText("展开");
						v2.setText("全部");
					}
				}
//				Message fsix=new Message();
//				fsix.what=10086;
//				handler.sendMessage(fsix);
			}

		});
		noHandicEqualView = convertView.findViewById(R.id.noHandicEqualView);
		if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition).get(1) == 0) {
			noHandicEqualView.setBackgroundResource(R.drawable.cp);
			noHandicEqualText.setTextColor(graytext);
			noHandicEqualText1.setTextColor(graytext);
		} else {
			noHandicEqualView.setBackgroundResource(R.drawable.pink);
			noHandicEqualText.setTextColor(whitetext);
			noHandicEqualText1.setTextColor(whitetext);
		}
		noHandicEqualView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
						.get(1) == 0) {
					v.setBackgroundResource(R.drawable.pink);
					noHandicEqualText.setTextColor(whitetext);
					noHandicEqualText1.setTextColor(whitetext);
					Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
							.set(1, 1);
				} else {
					v.setBackgroundResource(R.drawable.cp);
					noHandicEqualText.setTextColor(graytext);
					noHandicEqualText1.setTextColor(graytext);
					Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
							.set(1, 0);
				}
				if (showThreeView != null && showThreeView.size() == 3) {
					int select = 0;
					for (int i = 6; i < 55; i++) {
						if (Constant.MATCH_STATUS.get(groupPosition)
								.get(childPosition).get(i) == 1) {
							select++;
						}
					}
					if (select > 0) {
						for (int i = 0; i < 6; i++) {
							if (Constant.MATCH_STATUS.get(groupPosition)
									.get(childPosition).get(i) == 1) {
								select++;
							}
						}
						matchShowView.setBackgroundResource(
								R.drawable.pink);
						TextView v1 = (TextView) (matchShowView.findViewById(R.id.showText1));
						TextView v2 = (TextView) (matchShowView.findViewById(R.id.showText2));
						v1.setText("已选");
						v1.setTextColor(whitetext);
						v2.setTextColor(whitetext);
						v2.setText("" + select + "项");
					} else {
						matchShowView.setBackgroundResource(
								R.drawable.cp);
						TextView v1 = (TextView) (matchShowView.findViewById(R.id.showText1));
						TextView v2 = (TextView) (matchShowView.findViewById(R.id.showText2));
						v1.setTextColor(graytext);
						v2.setTextColor(graytext);
						v1.setText("展开");
						v2.setText("全部");
					}
				}
				betMsg = new Message();
				betMsg.what = Constant.FOOTBALL_BET_MSG;
				handler.sendMessage(betMsg);
//				Message fsix=new Message();
//				fsix.what=10086;
//				handler.sendMessage(fsix);
			}
		});
		noHandicLoseView = convertView.findViewById(R.id.noHandicLoseView);
		if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition).get(2) == 0) {
			noHandicLoseView.setBackgroundResource(R.drawable.cp);
			noHandicLoseText.setTextColor(graytext);
			noHandicLoseText1.setTextColor(graytext);
		} else {
			noHandicLoseView.setBackgroundResource(R.drawable.pink);
			noHandicLoseText.setTextColor(whitetext);
			noHandicLoseText1.setTextColor(whitetext);
		}
		noHandicLoseView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
						.get(2) == 0) {
					v.setBackgroundResource(R.drawable.pink);
					noHandicLoseText.setTextColor(whitetext);
					noHandicLoseText1.setTextColor(whitetext);
					Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
							.set(2, 1);
				} else {
					v.setBackgroundResource(R.drawable.cp);
					noHandicLoseText.setTextColor(graytext);
					noHandicLoseText1.setTextColor(graytext);
					Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
							.set(2, 0);
				}
				if (showThreeView != null && showThreeView.size() == 3) {
					int select = 0;
					for (int i = 6; i < 55; i++) {
						if (Constant.MATCH_STATUS.get(groupPosition)
								.get(childPosition).get(i) == 1) {
							select++;
						}
					}
					if (select > 0) {
						for (int i = 0; i < 6; i++) {
							if (Constant.MATCH_STATUS.get(groupPosition)
									.get(childPosition).get(i) == 1) {
								select++;
							}
						}
						matchShowView.setBackgroundResource(
								R.drawable.pink);
						TextView v1 = (TextView) (matchShowView.findViewById(R.id.showText1));
						TextView v2 = (TextView) (matchShowView.findViewById(R.id.showText2));
						v1.setText("已选");
						v1.setTextColor(whitetext);
						v2.setTextColor(whitetext);
						v2.setText("" + select + "项");
					} else {
						matchShowView.setBackgroundResource(
								R.drawable.cp);
						TextView v1 = (TextView) (matchShowView.findViewById(R.id.showText1));
						TextView v2 = (TextView) (matchShowView.findViewById(R.id.showText2));
						v1.setTextColor(graytext);
						v2.setTextColor(graytext);
						v1.setText("展开");
						v2.setText("全部");
					}
				}
//				Message fsix=new Message();
//				fsix.what=10086;
//				handler.sendMessage(fsix);
				betMsg = new Message();
				betMsg.what = Constant.FOOTBALL_BET_MSG;
				handler.sendMessage(betMsg);
			}
		});
		handicWinView = convertView.findViewById(R.id.handicWinView);
		if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition).get(3) == 0) {
			handicWinView.setBackgroundResource(R.drawable.cp);
			handicWinText.setTextColor(graytext);
			handicWinText1.setTextColor(graytext);
		} else {
			handicWinView.setBackgroundResource(R.drawable.pink);
			handicWinText.setTextColor(whitetext);
			handicWinText1.setTextColor(whitetext);
		}
		handicWinView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
						.get(3) == 0) {
					v.setBackgroundResource(R.drawable.pink);
					handicWinText.setTextColor(whitetext);
					handicWinText1.setTextColor(whitetext);
					Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
							.set(3, 1);
				} else {
					v.setBackgroundResource(R.drawable.cp);
					handicWinText.setTextColor(graytext);
					handicWinText1.setTextColor(graytext);
					Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
							.set(3, 0);
				}
				if (showThreeView != null && showThreeView.size() == 3) {
					int select = 0;
					for (int i = 6; i < 55; i++) {
						if (Constant.MATCH_STATUS.get(groupPosition)
								.get(childPosition).get(i) == 1) {
							select++;
						}
					}
					if (select > 0) {
						for (int i = 0; i < 6; i++) {
							if (Constant.MATCH_STATUS.get(groupPosition)
									.get(childPosition).get(i) == 1) {
								select++;
							}
						}
						matchShowView.setBackgroundResource(
								R.drawable.pink);
						TextView v1 = (TextView) (matchShowView.findViewById(R.id.showText1));
						TextView v2 = (TextView) (matchShowView.findViewById(R.id.showText2));
						v1.setText("已选");
						v1.setTextColor(whitetext);
						v2.setTextColor(whitetext);
						v2.setText("" + select + "项");
					} else {
						matchShowView.setBackgroundResource(
								R.drawable.cp);
						TextView v1 = (TextView) (matchShowView.findViewById(R.id.showText1));
						TextView v2 = (TextView) (matchShowView.findViewById(R.id.showText2));
						v1.setTextColor(graytext);
						v2.setTextColor(graytext);
						v1.setText("展开");
						v2.setText("全部");
					}
				}
//				Message fsix=new Message();
//				fsix.what=10086;
//				handler.sendMessage(fsix);
				betMsg = new Message();
				betMsg.what = Constant.FOOTBALL_BET_MSG;
				handler.sendMessage(betMsg);
			}
		});
		handicEqualView = convertView.findViewById(R.id.handicEqualView);
		if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition).get(4) == 0) {
			handicEqualView.setBackgroundResource(R.drawable.cp);
			handicEqualText.setTextColor(graytext);
			handicEqualText1.setTextColor(graytext);
		} else {
			handicEqualView.setBackgroundResource(R.drawable.pink);
			handicEqualText.setTextColor(whitetext);
			handicEqualText1.setTextColor(whitetext);
		}
		handicEqualView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
						.get(4) == 0) {
					v.setBackgroundResource(R.drawable.pink);
					handicEqualText.setTextColor(whitetext);
					handicEqualText1.setTextColor(whitetext);
					Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
							.set(4, 1);
				} else {
					v.setBackgroundResource(R.drawable.cp);
					handicEqualText.setTextColor(graytext);
					handicEqualText1.setTextColor(graytext);
					Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
							.set(4, 0);
				}
				if (showThreeView != null && showThreeView.size() == 3) {
					int select = 0;
					for (int i = 6; i < 55; i++) {
						if (Constant.MATCH_STATUS.get(groupPosition)
								.get(childPosition).get(i) == 1) {
							select++;
						}
					}
					if (select > 0) {
						for (int i = 0; i < 6; i++) {
							if (Constant.MATCH_STATUS.get(groupPosition)
									.get(childPosition).get(i) == 1) {
								select++;
							}
						}
						matchShowView.setBackgroundResource(
								R.drawable.pink);
						TextView v1 = (TextView) (matchShowView.findViewById(R.id.showText1));
						TextView v2 = (TextView) (matchShowView.findViewById(R.id.showText2));
						v1.setText("已选");
						v1.setTextColor(whitetext);
						v2.setTextColor(whitetext);
						v2.setText("" + select + "项");
					} else {
						matchShowView.setBackgroundResource(
								R.drawable.cp);
						TextView v1 = (TextView) (matchShowView.findViewById(R.id.showText1));
						TextView v2 = (TextView) (matchShowView.findViewById(R.id.showText2));
						v1.setTextColor(graytext);
						v2.setTextColor(graytext);
						v1.setText("展开");
						v2.setText("全部");
					}
				}
//				Message fsix=new Message();
//				fsix.what=10086;
//				handler.sendMessage(fsix);
				betMsg = new Message();
				betMsg.what = Constant.FOOTBALL_BET_MSG;
				handler.sendMessage(betMsg);
			}
		});
		handicLoseView = convertView.findViewById(R.id.handicLoseView);
		if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition).get(5) == 0) {
			handicLoseView.setBackgroundResource(R.drawable.cp);
			handicLoseText.setTextColor(graytext);
			handicLoseText1.setTextColor(graytext);
		} else {
			handicLoseView.setBackgroundResource(R.drawable.pink);
			handicLoseText.setTextColor(whitetext);
			handicLoseText1.setTextColor(whitetext);
		}
		handicLoseView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
						.get(5) == 0) {
					v.setBackgroundResource(R.drawable.pink);
					handicLoseText.setTextColor(whitetext);
					handicLoseText1.setTextColor(whitetext);
					Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
							.set(5, 1);
				} else {
					v.setBackgroundResource(R.drawable.cp);
					handicLoseText.setTextColor(graytext);
					handicLoseText1.setTextColor(graytext);
					Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
							.set(5, 0);
				}
				if (showThreeView != null && showThreeView.size() == 3) {
					int select = 0;
					for (int i = 6; i < 55; i++) {
						if (Constant.MATCH_STATUS.get(groupPosition)
								.get(childPosition).get(i) == 1) {
							select++;
						}
					}
					if (select > 0) {
						for (int i = 0; i < 6; i++) {
							if (Constant.MATCH_STATUS.get(groupPosition)
									.get(childPosition).get(i) == 1) {
								select++;
							}
						}
						matchShowView.setBackgroundResource(
								R.drawable.pink);
						TextView v1 = (TextView) (matchShowView.findViewById(R.id.showText1));
						TextView v2 = (TextView) (matchShowView.findViewById(R.id.showText2));
						v1.setText("已选");
						v1.setTextColor(whitetext);
						v2.setTextColor(whitetext);
						v2.setText("" + select + "项");
					} else {
						matchShowView.setBackgroundResource(
								R.drawable.cp);
						TextView v1 = (TextView) (matchShowView.findViewById(R.id.showText1));
						TextView v2 = (TextView) (matchShowView.findViewById(R.id.showText2));
						v1.setTextColor(graytext);
						v2.setTextColor(graytext);
						v1.setText("展开");
						v2.setText("全部");
					}
				}
//				Message fsix=new Message();
//				fsix.what=10086;
//				handler.sendMessage(fsix);
				betMsg = new Message();
				betMsg.what = Constant.FOOTBALL_BET_MSG;
				handler.sendMessage(betMsg);
			}
		});
		// if
		// (Constant.MATCH_STATUS.get(groupPosition).get(childPosition).get(0)
		// == 0) {
		// matchShowView.setBackgroundResource(R.drawable.cp);
		// } else {
		// matchShowView.setBackgroundResource(R.drawable.pink);
		// }
		matchShowView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				fullChildposition = childPosition;
				fullGroupPositin = groupPosition;
				currentFirstSixView = new ArrayList<View>();
				showThreeView = new ArrayList<View>();
				curentText = new ArrayList<TextView>();
				curentText1 = new ArrayList<TextView>();
				showThreeView.add(matchShowView);
				showThreeView.add(showText1);
				showThreeView.add(showText2);
				curentText.add(noHandicWinText);
				curentText.add(noHandicEqualText);
				curentText.add(noHandicLoseText);
				curentText.add(handicWinText);
				curentText.add(handicEqualText);
				curentText.add(handicLoseText);
				curentText1.add(noHandicWinText1);
				curentText1.add(noHandicEqualText1);
				curentText1.add(noHandicLoseText1);
				curentText1.add(handicWinText1);
				curentText1.add(handicEqualText1);
				curentText1.add(handicLoseText1);
				currentFirstSixView.add(noHandicWinView);
				currentFirstSixView.add(noHandicEqualView);
				currentFirstSixView.add(noHandicLoseView);
				currentFirstSixView.add(handicWinView);
				currentFirstSixView.add(handicEqualView);
				currentFirstSixView.add(handicLoseView);
				alertFootballBet(context, groupPosition, childPosition,
						Constant.MIX_DAYMATCHESS.get(groupPosition).getMatchs()
								.get(childPosition).getHost(),
						Constant.MIX_DAYMATCHESS.get(groupPosition).getMatchs()
								.get(childPosition).getGuest(),
						Constant.MIX_DAYMATCHESS.get(groupPosition).getMatchs()
								.get(childPosition).getLetBall());
			}
		});
		int select = 0;
		for (int i = 6; i < 55; i++) {
			if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
					.get(i) == 1) {
				select++;
			}
		}
		if (select > 0) {
			for (int i = 0; i < 6; i++) {
				if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
						.get(i) == 1) {
					select++;
				}
			}
		}
		if (select > 0) {
			matchShowView.setBackgroundResource(R.drawable.pink);
			showText1.setText("已选");
			showText2.setText("" + select + "项");
			showText1.setTextColor(whitetext);
			showText2.setTextColor(whitetext);
		} else {
			matchShowView.setBackgroundResource(R.drawable.cp);
			showText1.setText("展开");
			showText2.setText("全部");
			showText1.setTextColor(graytext);
			showText2.setTextColor(graytext);
		}
		return convertView;

	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.like_qq_group, null);
		}

		ImageView iv = (ImageView) convertView.findViewById(R.id.groupIcon);
		TextView tv = (TextView) convertView.findViewById(R.id.groupto);
		tv.setText(dayMatchses.get(groupPosition).getDate());

		if (isExpanded) {
			iv.setImageResource(R.drawable.btn_browser2);
		} else {
			iv.setImageResource(R.drawable.btn_browser);
		}

		return convertView;
	}

	private OnClickListener footBallBetListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			for (int i = 0; i < clickViews.size(); i++) {
				if (v == clickViews.get(i)) {
					if (Constant.MATCH_STATUS.get(fullGroupPositin)
							.get(fullChildposition).get(i) == 0) {
						mtextviews.get(i).setTextColor(whitetext);
						mtextviews1.get(i).setTextColor(whitetext);
						v.setBackgroundResource(R.drawable.pink);
						Constant.MATCH_STATUS.get(fullGroupPositin)
								.get(fullChildposition).set(i, 1);
					} else {
						mtextviews1.get(i).setTextColor(graytext);
						mtextviews.get(i).setTextColor(graytext);
						v.setBackgroundResource(R.drawable.cp);
						Constant.MATCH_STATUS.get(fullGroupPositin)
								.get(fullChildposition).set(i, 0);
					}
					break;
				}
			}
		}
	};

	// private void getTextViews(){
	// if(textviews==null||textviews.size()==0||textviews1==null||textviews1.size()==0){
	// sd
	// }
	// }

	private void alertFootballBet(Context ctx, int groupPos, int childPos,
			String host, String visit, String handic) {

		// dialogCount++;
		// if (dialogCount > 1) {
		// return;
		// }

		final ArrayList<TextView> textviews = new ArrayList<TextView>();
		final ArrayList<TextView> textviews1 = new ArrayList<TextView>();
		mtextviews = textviews;
		mtextviews1 = textviews1;
		if (alert.isShowing()) {
			return;
		}
		alert.setCancelable(false);
		alert.show();
		alert.getWindow().setContentView(R.layout.football_dialog);
		final ArrayList<View> views = new ArrayList<View>();
		View d_noHandicWinView, d_noHandicEqualView, d_noHandicLoseView, d_handicWinView, d_handicEqualView, d_handicLoseView, d_goals_0_view, d_goals_1_view, d_goals_2_view, d_goals_3_view, d_goals_4_view, d_goals_5_view, d_goals_6_view, d_goals_7_view, d_ww_view, d_we_view, d_wl_view, d_ew_view, d_ee_view, d_el_view, d_lw_view, d_le_view, d_ll_view, d_whole_10_view, d_whole_20_view, d_whole_21_view, d_whole_30_view, d_whole_31_view, d_whole_32_view, d_whole_40_view, d_whole_41_view, d_whole_42_view, d_whole_50_view, d_whole_51_view, d_whole_52_view, d_whole_welse_view, d_whole_00_view, d_whole_11_view, d_whole_22_view, d_whole_33_view, d_whole_eeelse_view, d_whole_01_view, d_whole_02_view, d_whole_12_view, d_whole_03_view, d_whole_13_view, d_whole_23_view, d_whole_04_view, d_whole_14_view, d_whole_24_view, d_whole_05_view, d_whole_15_view, d_whole_25_view, d_whole_lelse_view;
		TextView d_noHandicWinText, d_noHandicEqualText, d_noHandicLoseText, d_handicWinText, d_handicEqualText, d_handicLoseText, d_goals_0_text, d_goals_1_text, d_goals_2_text, d_goals_3_text, d_goals_4_text, d_goals_5_text, d_goals_6_text, d_goals_7_text, d_ww_text, d_we_textw, d_wl_text, d_ew_text, d_ee_text, d_el_text, d_lw_text, d_le_text, d_ll_text, d_whole_10_text, d_whole_20_text, d_whole_21_text, d_whole_30_text, d_whole_31_text, d_whole_32_text, d_whole_40_text, d_whole_41_text, d_whole_42_text, d_whole_50_text, d_whole_51_text, d_whole_52_text, d_whole_welse_text, d_whole_00_text, d_whole_11_text, d_whole_22_text, d_whole_33_text, d_whole_eeelse_text, d_whole_01_text, d_whole_02_text, d_whole_12_text, d_whole_03_text, d_whole_13_text, d_whole_23_text, d_whole_04_text, d_whole_14_text, d_whole_24_text, d_whole_05_text, d_whole_15_text, d_whole_25_text, d_whole_lelse_text;
		TextView d_noHandicWinText1, d_noHandicEqualText1, d_noHandicLoseText1, d_handicWinText1, d_handicEqualText1, d_handicLoseText1, d_goals_0_text1, d_goals_1_text1, d_goals_2_text1, d_goals_3_text1, d_goals_4_text1, d_goals_5_text1, d_goals_6_text1, d_goals_7_text1, d_ww_text1, d_we_textw1, d_wl_text1, d_ew_text1, d_ee_text1, d_el_text1, d_lw_text1, d_le_text1, d_ll_text1, d_whole_10_text1, d_whole_20_text1, d_whole_21_text1, d_whole_30_text1, d_whole_31_text1, d_whole_32_text1, d_whole_40_text1, d_whole_41_text1, d_whole_42_text1, d_whole_50_text1, d_whole_51_text1, d_whole_52_text1, d_whole_welse_text1, d_whole_00_text1, d_whole_11_text1, d_whole_22_text1, d_whole_33_text1, d_whole_eeelse_text1, d_whole_01_text1, d_whole_02_text1, d_whole_12_text1, d_whole_03_text1, d_whole_13_text1, d_whole_23_text1, d_whole_04_text1, d_whole_14_text1, d_whole_24_text1, d_whole_05_text1, d_whole_15_text1, d_whole_25_text1, d_whole_lelse_text1;
		View d_handicView, d_noHandicView;
		TextView d_host, d_guest, d_handic;
		d_host = (TextView) alert.findViewById(R.id.d_host);
		d_guest = (TextView) alert.findViewById(R.id.d_guest);
		d_handic = (TextView) alert.findViewById(R.id.d_handic);
		d_host.setText(host);
		d_guest.setText(visit);
		if (handic == null || handic.equals("")) {
			d_handic.setText("");
		} else {
			d_handic.setText("(" + handic + ")");
		}
		d_handicView = alert.findViewById(R.id.d_handicView);
		d_noHandicView = alert.findViewById(R.id.d_noHandicView);
		// if
		// (Constant.MATCH_STATUS.get(fullGroupPositin).get(fullChildposition)
		// .get(59) == 0) {
		// d_handicView.setVisibility(View.VISIBLE);
		// d_noHandicView.setVisibility(View.VISIBLE);
		// } else {
		// d_handicView.setVisibility(View.GONE);
		// d_noHandicView.setVisibility(View.VISIBLE);
		// }
		if (Constant.MIX_DAYMATCHESS.get(fullGroupPositin).getMatchs()
				.get(childPos).getOdds() == null
				|| Constant.MIX_DAYMATCHESS.get(fullGroupPositin).getMatchs()
						.get(childPos).getOdds().size() == 0) {
			d_handicView.setVisibility(View.GONE);
		} else {
			d_handicView.setVisibility(View.VISIBLE);
		}
		if (Constant.MIX_DAYMATCHESS.get(fullGroupPositin).getMatchs()
				.get(childPos).getSpfodds() == null
				|| Constant.MIX_DAYMATCHESS.get(fullGroupPositin).getMatchs()
						.get(childPos).getSpfodds().size() == 0) {
			d_noHandicView.setVisibility(View.GONE);
		} else {
			d_noHandicView.setVisibility(View.VISIBLE);
		}
		// if(null==currentMatch){
		// Log.e("pkx", "null==currentMatch");
		// }else{
		// if(currentMatch.isHandip()){
		// d_handicView.setVisibility(View.VISIBLE);
		// }else{
		// d_handicView.setVisibility(View.GONE);
		// }
		// }

		d_noHandicWinText = (TextView) alert
				.findViewById(R.id.d_noHandicWinText);
		d_noHandicEqualText = (TextView) alert
				.findViewById(R.id.d_noHandicEqualText);
		d_noHandicLoseText = (TextView) alert
				.findViewById(R.id.d_noHandicLoseText);
		d_handicWinText = (TextView) alert.findViewById(R.id.d_handicWinText);
		d_handicEqualText = (TextView) alert
				.findViewById(R.id.d_handicEuqalText);
		d_handicLoseText = (TextView) alert.findViewById(R.id.d_handicLoseText);
		d_goals_0_text = (TextView) alert.findViewById(R.id.d_goals_0_text);
		d_goals_1_text = (TextView) alert.findViewById(R.id.d_goals_1_text);
		d_goals_2_text = (TextView) alert.findViewById(R.id.d_goals_2_text);
		d_goals_3_text = (TextView) alert.findViewById(R.id.d_goals_3_text);
		d_goals_4_text = (TextView) alert.findViewById(R.id.d_goals_4_text);
		d_goals_5_text = (TextView) alert.findViewById(R.id.d_goals_5_text);
		d_goals_6_text = (TextView) alert.findViewById(R.id.d_goals_6_text);
		d_goals_7_text = (TextView) alert.findViewById(R.id.d_goals_7_text);
		d_ww_text = (TextView) alert.findViewById(R.id.d_ww_text);
		d_we_textw = (TextView) alert.findViewById(R.id.d_we_text);
		d_wl_text = (TextView) alert.findViewById(R.id.d_wl_text);
		d_ew_text = (TextView) alert.findViewById(R.id.d_ew_text);
		d_ee_text = (TextView) alert.findViewById(R.id.d_ee_text);
		d_el_text = (TextView) alert.findViewById(R.id.d_el_text);
		d_lw_text = (TextView) alert.findViewById(R.id.d_lw_text);
		d_le_text = (TextView) alert.findViewById(R.id.d_le_text);
		d_ll_text = (TextView) alert.findViewById(R.id.d_ll_text);
		d_whole_10_text = (TextView) alert.findViewById(R.id.d_whole_10_text);
		d_whole_20_text = (TextView) alert.findViewById(R.id.d_whole_20_text);
		d_whole_21_text = (TextView) alert.findViewById(R.id.d_whole_21_text);
		d_whole_30_text = (TextView) alert.findViewById(R.id.d_whole_30_text);
		d_whole_31_text = (TextView) alert.findViewById(R.id.d_whole_31_text);
		d_whole_32_text = (TextView) alert.findViewById(R.id.d_whole_32_text);
		d_whole_40_text = (TextView) alert.findViewById(R.id.d_whole_40_text);
		d_whole_41_text = (TextView) alert.findViewById(R.id.d_whole_41_text);
		d_whole_42_text = (TextView) alert.findViewById(R.id.d_whole_42_text);
		d_whole_50_text = (TextView) alert.findViewById(R.id.d_whole_50_text);
		d_whole_51_text = (TextView) alert.findViewById(R.id.d_whole_51_text);
		d_whole_52_text = (TextView) alert.findViewById(R.id.d_whole_52_text);
		d_whole_welse_text = (TextView) alert
				.findViewById(R.id.d_whole_welse_text);
		d_whole_00_text = (TextView) alert.findViewById(R.id.d_whole_00_text);
		d_whole_11_text = (TextView) alert.findViewById(R.id.d_whole_11_text);
		d_whole_22_text = (TextView) alert.findViewById(R.id.d_whole_22_text);
		d_whole_33_text = (TextView) alert.findViewById(R.id.d_whole_33_text);
		d_whole_eeelse_text = (TextView) alert
				.findViewById(R.id.d_whole_eeelse_text);
		d_whole_01_text = (TextView) alert.findViewById(R.id.d_whole_01_text);
		d_whole_02_text = (TextView) alert.findViewById(R.id.d_whole_02_text);
		d_whole_12_text = (TextView) alert.findViewById(R.id.d_whole_12_text);
		d_whole_03_text = (TextView) alert.findViewById(R.id.d_whole_03_text);
		d_whole_13_text = (TextView) alert.findViewById(R.id.d_whole_13_text);
		d_whole_23_text = (TextView) alert.findViewById(R.id.d_whole_23_text);
		d_whole_04_text = (TextView) alert.findViewById(R.id.d_whole_04_text);
		d_whole_14_text = (TextView) alert.findViewById(R.id.d_whole_14_text);
		d_whole_24_text = (TextView) alert.findViewById(R.id.d_whole_24_text);
		d_whole_05_text = (TextView) alert.findViewById(R.id.d_whole_05_text);
		d_whole_15_text = (TextView) alert.findViewById(R.id.d_whole_15_text);
		d_whole_25_text = (TextView) alert.findViewById(R.id.d_whole_25_text);
		d_whole_lelse_text = (TextView) alert
				.findViewById(R.id.d_whole_lelse_text);
		d_noHandicWinText1 = (TextView) alert
				.findViewById(R.id.d_noHandicWinText1);
		d_noHandicEqualText1 = (TextView) alert
				.findViewById(R.id.d_noHandicEqualText1);
		d_noHandicLoseText1 = (TextView) alert
				.findViewById(R.id.d_noHandicLoseText1);
		d_handicWinText1 = (TextView) alert.findViewById(R.id.d_handicWinText1);
		d_handicEqualText1 = (TextView) alert
				.findViewById(R.id.d_handicEuqalText1);
		d_handicLoseText1 = (TextView) alert
				.findViewById(R.id.d_handicLoseText1);
		d_goals_0_text1 = (TextView) alert.findViewById(R.id.d_goals_0_text1);
		d_goals_1_text1 = (TextView) alert.findViewById(R.id.d_goals_1_text1);
		d_goals_2_text1 = (TextView) alert.findViewById(R.id.d_goals_2_text1);
		d_goals_3_text1 = (TextView) alert.findViewById(R.id.d_goals_3_text1);
		d_goals_4_text1 = (TextView) alert.findViewById(R.id.d_goals_4_text1);
		d_goals_5_text1 = (TextView) alert.findViewById(R.id.d_goals_5_text1);
		d_goals_6_text1 = (TextView) alert.findViewById(R.id.d_goals_6_text1);
		d_goals_7_text1 = (TextView) alert.findViewById(R.id.d_goals_7_text1);
		d_ww_text1 = (TextView) alert.findViewById(R.id.d_ww_text1);
		d_we_textw1 = (TextView) alert.findViewById(R.id.d_we_text1);
		d_wl_text1 = (TextView) alert.findViewById(R.id.d_wl_text1);
		d_ew_text1 = (TextView) alert.findViewById(R.id.d_ew_text1);
		d_ee_text1 = (TextView) alert.findViewById(R.id.d_ee_text1);
		d_el_text1 = (TextView) alert.findViewById(R.id.d_el_text1);
		d_lw_text1 = (TextView) alert.findViewById(R.id.d_lw_text1);
		d_le_text1 = (TextView) alert.findViewById(R.id.d_le_text1);
		d_ll_text1 = (TextView) alert.findViewById(R.id.d_ll_text1);
		d_whole_10_text1 = (TextView) alert.findViewById(R.id.d_whole_10_text1);
		d_whole_20_text1 = (TextView) alert.findViewById(R.id.d_whole_20_text1);
		d_whole_21_text1 = (TextView) alert.findViewById(R.id.d_whole_21_text1);
		d_whole_30_text1 = (TextView) alert.findViewById(R.id.d_whole_30_text1);
		d_whole_31_text1 = (TextView) alert.findViewById(R.id.d_whole_31_text1);
		d_whole_32_text1 = (TextView) alert.findViewById(R.id.d_whole_32_text1);
		d_whole_40_text1 = (TextView) alert.findViewById(R.id.d_whole_40_text1);
		d_whole_41_text1 = (TextView) alert.findViewById(R.id.d_whole_41_text1);
		d_whole_42_text1 = (TextView) alert.findViewById(R.id.d_whole_42_text1);
		d_whole_50_text1 = (TextView) alert.findViewById(R.id.d_whole_50_text1);
		d_whole_51_text1 = (TextView) alert.findViewById(R.id.d_whole_51_text1);
		d_whole_52_text1 = (TextView) alert.findViewById(R.id.d_whole_52_text1);
		d_whole_welse_text1 = (TextView) alert
				.findViewById(R.id.d_whole_welse_text1);
		d_whole_00_text1 = (TextView) alert.findViewById(R.id.d_whole_00_text1);
		d_whole_11_text1 = (TextView) alert.findViewById(R.id.d_whole_11_text1);
		d_whole_22_text1 = (TextView) alert.findViewById(R.id.d_whole_22_text1);
		d_whole_33_text1 = (TextView) alert.findViewById(R.id.d_whole_33_text1);
		d_whole_eeelse_text1 = (TextView) alert
				.findViewById(R.id.d_whole_eeelse_text1);
		d_whole_01_text1 = (TextView) alert.findViewById(R.id.d_whole_01_text1);
		d_whole_02_text1 = (TextView) alert.findViewById(R.id.d_whole_02_text1);
		d_whole_12_text1 = (TextView) alert.findViewById(R.id.d_whole_12_text1);
		d_whole_03_text1 = (TextView) alert.findViewById(R.id.d_whole_03_text1);
		d_whole_13_text1 = (TextView) alert.findViewById(R.id.d_whole_13_text1);
		d_whole_23_text1 = (TextView) alert.findViewById(R.id.d_whole_23_text1);
		d_whole_04_text1 = (TextView) alert.findViewById(R.id.d_whole_04_text1);
		d_whole_14_text1 = (TextView) alert.findViewById(R.id.d_whole_14_text1);
		d_whole_24_text1 = (TextView) alert.findViewById(R.id.d_whole_24_text1);
		d_whole_05_text1 = (TextView) alert.findViewById(R.id.d_whole_05_text1);
		d_whole_15_text1 = (TextView) alert.findViewById(R.id.d_whole_15_text1);
		d_whole_25_text1 = (TextView) alert.findViewById(R.id.d_whole_25_text1);
		d_whole_lelse_text1 = (TextView) alert
				.findViewById(R.id.d_whole_lelse_text1);
		d_noHandicWinView = alert.findViewById(R.id.d_noHandicWinView);
		d_noHandicWinView.setOnClickListener(footBallBetListener);
		d_noHandicEqualView = alert.findViewById(R.id.d_noHandicEqualView);
		d_noHandicEqualView.setOnClickListener(footBallBetListener);
		d_noHandicLoseView = alert.findViewById(R.id.d_noHandicLoseView);
		d_noHandicLoseView.setOnClickListener(footBallBetListener);
		d_handicWinView = alert.findViewById(R.id.d_handicWinView);
		d_handicWinView.setOnClickListener(footBallBetListener);
		d_handicEqualView = alert.findViewById(R.id.d_handicEqualView);
		d_handicEqualView.setOnClickListener(footBallBetListener);
		d_handicLoseView = alert.findViewById(R.id.d_handicLoseView);
		d_handicLoseView.setOnClickListener(footBallBetListener);
		d_goals_0_view = alert.findViewById(R.id.d_goals_0_view);
		d_goals_0_view.setOnClickListener(footBallBetListener);
		d_goals_1_view = alert.findViewById(R.id.d_goals_1_view);
		d_goals_1_view.setOnClickListener(footBallBetListener);
		d_goals_2_view = alert.findViewById(R.id.d_goals_2_view);
		d_goals_2_view.setOnClickListener(footBallBetListener);
		d_goals_3_view = alert.findViewById(R.id.d_goals_3_view);
		d_goals_3_view.setOnClickListener(footBallBetListener);
		d_goals_4_view = alert.findViewById(R.id.d_goals_4_view);
		d_goals_4_view.setOnClickListener(footBallBetListener);
		d_goals_5_view = alert.findViewById(R.id.d_goals_5_view);
		d_goals_5_view.setOnClickListener(footBallBetListener);
		d_goals_6_view = alert.findViewById(R.id.d_goals_6_view);
		d_goals_6_view.setOnClickListener(footBallBetListener);
		d_goals_7_view = alert.findViewById(R.id.d_goals_7_view);
		d_goals_7_view.setOnClickListener(footBallBetListener);
		d_ww_view = alert.findViewById(R.id.d_ww_view);
		d_ww_view.setOnClickListener(footBallBetListener);
		d_we_view = alert.findViewById(R.id.d_we_view);
		d_we_view.setOnClickListener(footBallBetListener);
		d_wl_view = alert.findViewById(R.id.d_wl_view);
		d_wl_view.setOnClickListener(footBallBetListener);
		d_ew_view = alert.findViewById(R.id.d_ew_view);
		d_ew_view.setOnClickListener(footBallBetListener);
		d_ee_view = alert.findViewById(R.id.d_ee_view);
		d_ee_view.setOnClickListener(footBallBetListener);
		d_el_view = alert.findViewById(R.id.d_el_view);
		d_el_view.setOnClickListener(footBallBetListener);
		d_lw_view = alert.findViewById(R.id.d_lw_view);
		d_lw_view.setOnClickListener(footBallBetListener);
		d_le_view = alert.findViewById(R.id.d_le_view);
		d_le_view.setOnClickListener(footBallBetListener);
		d_ll_view = alert.findViewById(R.id.d_ll_view);
		d_ll_view.setOnClickListener(footBallBetListener);
		d_whole_10_view = alert.findViewById(R.id.d_whole_10_view);
		d_whole_10_view.setOnClickListener(footBallBetListener);
		d_whole_20_view = alert.findViewById(R.id.d_whole_20_view);
		d_whole_20_view.setOnClickListener(footBallBetListener);
		d_whole_21_view = alert.findViewById(R.id.d_whole_21_view);
		d_whole_21_view.setOnClickListener(footBallBetListener);
		d_whole_30_view = alert.findViewById(R.id.d_whole_30_view);
		d_whole_30_view.setOnClickListener(footBallBetListener);
		d_whole_31_view = alert.findViewById(R.id.d_whole_31_view);
		d_whole_31_view.setOnClickListener(footBallBetListener);
		d_whole_32_view = alert.findViewById(R.id.d_whole_32_view);
		d_whole_32_view.setOnClickListener(footBallBetListener);
		d_whole_40_view = alert.findViewById(R.id.d_whole_40_view);
		d_whole_40_view.setOnClickListener(footBallBetListener);
		d_whole_41_view = alert.findViewById(R.id.d_whole_41_view);
		d_whole_41_view.setOnClickListener(footBallBetListener);
		d_whole_42_view = alert.findViewById(R.id.d_whole_42_view);
		d_whole_42_view.setOnClickListener(footBallBetListener);
		d_whole_50_view = alert.findViewById(R.id.d_whole_50_view);
		d_whole_50_view.setOnClickListener(footBallBetListener);
		d_whole_51_view = alert.findViewById(R.id.d_whole_51_view);
		d_whole_51_view.setOnClickListener(footBallBetListener);
		d_whole_52_view = alert.findViewById(R.id.d_whole_52_view);
		d_whole_52_view.setOnClickListener(footBallBetListener);
		d_whole_welse_view = alert.findViewById(R.id.d_whole_welse_view);
		d_whole_welse_view.setOnClickListener(footBallBetListener);
		d_whole_00_view = alert.findViewById(R.id.d_whole_00_view);
		d_whole_00_view.setOnClickListener(footBallBetListener);
		d_whole_11_view = alert.findViewById(R.id.d_whole_11_view);
		d_whole_11_view.setOnClickListener(footBallBetListener);
		d_whole_22_view = alert.findViewById(R.id.d_whole_22_view);
		d_whole_22_view.setOnClickListener(footBallBetListener);
		d_whole_33_view = alert.findViewById(R.id.d_whole_33_view);
		d_whole_33_view.setOnClickListener(footBallBetListener);
		d_whole_eeelse_view = alert.findViewById(R.id.d_whole_eeelse_view);
		d_whole_eeelse_view.setOnClickListener(footBallBetListener);
		d_whole_01_view = alert.findViewById(R.id.d_whole_01_view);
		d_whole_01_view.setOnClickListener(footBallBetListener);
		d_whole_02_view = alert.findViewById(R.id.d_whole_02_view);
		d_whole_02_view.setOnClickListener(footBallBetListener);
		d_whole_12_view = alert.findViewById(R.id.d_whole_12_view);
		d_whole_12_view.setOnClickListener(footBallBetListener);
		d_whole_03_view = alert.findViewById(R.id.d_whole_03_view);
		d_whole_03_view.setOnClickListener(footBallBetListener);
		d_whole_13_view = alert.findViewById(R.id.d_whole_13_view);
		d_whole_13_view.setOnClickListener(footBallBetListener);
		d_whole_23_view = alert.findViewById(R.id.d_whole_23_view);
		d_whole_23_view.setOnClickListener(footBallBetListener);
		d_whole_04_view = alert.findViewById(R.id.d_whole_04_view);
		d_whole_04_view.setOnClickListener(footBallBetListener);
		d_whole_14_view = alert.findViewById(R.id.d_whole_14_view);
		d_whole_14_view.setOnClickListener(footBallBetListener);
		d_whole_24_view = alert.findViewById(R.id.d_whole_24_view);
		d_whole_24_view.setOnClickListener(footBallBetListener);
		d_whole_05_view = alert.findViewById(R.id.d_whole_05_view);
		d_whole_05_view.setOnClickListener(footBallBetListener);
		d_whole_15_view = alert.findViewById(R.id.d_whole_15_view);
		d_whole_15_view.setOnClickListener(footBallBetListener);
		d_whole_25_view = alert.findViewById(R.id.d_whole_25_view);
		d_whole_25_view.setOnClickListener(footBallBetListener);
		d_whole_lelse_view = alert.findViewById(R.id.d_whole_lelse_view);
		d_whole_lelse_view.setOnClickListener(footBallBetListener);
		if (textviews.size() == 0) {
			textviews.add(d_noHandicWinText);
			textviews.add(d_noHandicEqualText);
			textviews.add(d_noHandicLoseText);
			textviews.add(d_handicWinText);
			textviews.add(d_handicEqualText);
			textviews.add(d_handicLoseText);
			textviews.add(d_goals_0_text);
			textviews.add(d_goals_1_text);
			textviews.add(d_goals_2_text);
			textviews.add(d_goals_3_text);
			textviews.add(d_goals_4_text);
			textviews.add(d_goals_5_text);
			textviews.add(d_goals_6_text);
			textviews.add(d_goals_7_text);
			textviews.add(d_ww_text);
			textviews.add(d_we_textw);
			textviews.add(d_wl_text);
			textviews.add(d_ew_text);
			textviews.add(d_ee_text);
			textviews.add(d_el_text);
			textviews.add(d_lw_text);
			textviews.add(d_le_text);
			textviews.add(d_ll_text);
			textviews.add(d_whole_10_text);
			textviews.add(d_whole_20_text);
			textviews.add(d_whole_21_text);
			textviews.add(d_whole_30_text);
			textviews.add(d_whole_31_text);
			textviews.add(d_whole_32_text);
			textviews.add(d_whole_40_text);
			textviews.add(d_whole_41_text);
			textviews.add(d_whole_42_text);
			textviews.add(d_whole_50_text);
			textviews.add(d_whole_51_text);
			textviews.add(d_whole_52_text);
			textviews.add(d_whole_welse_text);
			textviews.add(d_whole_00_text);
			textviews.add(d_whole_11_text);
			textviews.add(d_whole_22_text);
			textviews.add(d_whole_33_text);
			textviews.add(d_whole_eeelse_text);
			textviews.add(d_whole_01_text);
			textviews.add(d_whole_02_text);
			textviews.add(d_whole_12_text);
			textviews.add(d_whole_03_text);
			textviews.add(d_whole_13_text);
			textviews.add(d_whole_23_text);
			textviews.add(d_whole_04_text);
			textviews.add(d_whole_14_text);
			textviews.add(d_whole_24_text);
			textviews.add(d_whole_05_text);
			textviews.add(d_whole_15_text);
			textviews.add(d_whole_25_text);
			textviews.add(d_whole_lelse_text);
			textviews1.add(d_noHandicWinText1);
			textviews1.add(d_noHandicEqualText1);
			textviews1.add(d_noHandicLoseText1);
			textviews1.add(d_handicWinText1);
			textviews1.add(d_handicEqualText1);
			textviews1.add(d_handicLoseText1);
			textviews1.add(d_goals_0_text1);
			textviews1.add(d_goals_1_text1);
			textviews1.add(d_goals_2_text1);
			textviews1.add(d_goals_3_text1);
			textviews1.add(d_goals_4_text1);
			textviews1.add(d_goals_5_text1);
			textviews1.add(d_goals_6_text1);
			textviews1.add(d_goals_7_text1);
			textviews1.add(d_ww_text1);
			textviews1.add(d_we_textw1);
			textviews1.add(d_wl_text1);
			textviews1.add(d_ew_text1);
			textviews1.add(d_ee_text1);
			textviews1.add(d_el_text1);
			textviews1.add(d_lw_text1);
			textviews1.add(d_le_text1);
			textviews1.add(d_ll_text1);
			textviews1.add(d_whole_10_text1);
			textviews1.add(d_whole_20_text1);
			textviews1.add(d_whole_21_text1);
			textviews1.add(d_whole_30_text1);
			textviews1.add(d_whole_31_text1);
			textviews1.add(d_whole_32_text1);
			textviews1.add(d_whole_40_text1);
			textviews1.add(d_whole_41_text1);
			textviews1.add(d_whole_42_text1);
			textviews1.add(d_whole_50_text1);
			textviews1.add(d_whole_51_text1);
			textviews1.add(d_whole_52_text1);
			textviews1.add(d_whole_welse_text1);
			textviews1.add(d_whole_00_text1);
			textviews1.add(d_whole_11_text1);
			textviews1.add(d_whole_22_text1);
			textviews1.add(d_whole_33_text1);
			textviews1.add(d_whole_eeelse_text1);
			textviews1.add(d_whole_01_text1);
			textviews1.add(d_whole_02_text1);
			textviews1.add(d_whole_12_text1);
			textviews1.add(d_whole_03_text1);
			textviews1.add(d_whole_13_text1);
			textviews1.add(d_whole_23_text1);
			textviews1.add(d_whole_04_text1);
			textviews1.add(d_whole_14_text1);
			textviews1.add(d_whole_24_text1);
			textviews1.add(d_whole_05_text1);
			textviews1.add(d_whole_15_text1);
			textviews1.add(d_whole_25_text1);
			textviews1.add(d_whole_lelse_text1);
		}

		ArrayList<String> allodds = new ArrayList<String>();
		ArrayList<String> fake = new ArrayList<String>();
		fake.add("0.00");
		fake.add("0.00");
		fake.add("0.00");
		if (Constant.MIX_DAYMATCHESS.get(groupPos).getMatchs().get(childPos)
				.getSpfodds() == null
				|| Constant.MIX_DAYMATCHESS.get(groupPos).getMatchs()
						.get(childPos).getSpfodds().size() == 0) {
			allodds.addAll(fake);
		} else {
			allodds.addAll(Constant.MIX_DAYMATCHESS.get(groupPos).getMatchs()
					.get(childPos).getSpfodds());
		}
		if (Constant.MIX_DAYMATCHESS.get(groupPos).getMatchs().get(childPos)
				.getOdds() == null
				|| Constant.MIX_DAYMATCHESS.get(groupPos).getMatchs()
						.get(childPos).getOdds().size() == 0) {
			allodds.addAll(fake);
		} else {
			allodds.addAll(Constant.MIX_DAYMATCHESS.get(groupPos).getMatchs()
					.get(childPos).getOdds());
		}
		allodds.addAll(Constant.MIX_DAYMATCHESS.get(groupPos).getMatchs()
				.get(childPos).getTotalgoal_odds());
		allodds.addAll(Constant.MIX_DAYMATCHESS.get(groupPos).getMatchs()
				.get(childPos).getHalfCourt_odds());
		allodds.addAll(Constant.MIX_DAYMATCHESS.get(groupPos).getMatchs()
				.get(childPos).getScore_odds());
		for (int i = 0; i < textviews.size(); i++) {
			textviews.get(i).setText(allodds.get(i));
		}
		views.add(d_noHandicWinView);
		views.add(d_noHandicEqualView);
		views.add(d_noHandicLoseView);
		views.add(d_handicWinView);
		views.add(d_handicEqualView);
		views.add(d_handicLoseView);
		views.add(d_goals_0_view);
		views.add(d_goals_1_view);
		views.add(d_goals_2_view);
		views.add(d_goals_3_view);
		views.add(d_goals_4_view);
		views.add(d_goals_5_view);
		views.add(d_goals_6_view);
		views.add(d_goals_7_view);
		views.add(d_ww_view);
		views.add(d_we_view);
		views.add(d_wl_view);
		views.add(d_ew_view);
		views.add(d_ee_view);
		views.add(d_el_view);
		views.add(d_lw_view);
		views.add(d_le_view);
		views.add(d_ll_view);
		views.add(d_whole_10_view);
		views.add(d_whole_20_view);
		views.add(d_whole_21_view);
		views.add(d_whole_30_view);
		views.add(d_whole_31_view);
		views.add(d_whole_32_view);
		views.add(d_whole_40_view);
		views.add(d_whole_41_view);
		views.add(d_whole_42_view);
		views.add(d_whole_50_view);
		views.add(d_whole_51_view);
		views.add(d_whole_52_view);
		views.add(d_whole_welse_view);
		views.add(d_whole_00_view);
		views.add(d_whole_11_view);
		views.add(d_whole_22_view);
		views.add(d_whole_33_view);
		views.add(d_whole_eeelse_view);
		views.add(d_whole_01_view);
		views.add(d_whole_02_view);
		views.add(d_whole_12_view);
		views.add(d_whole_03_view);
		views.add(d_whole_13_view);
		views.add(d_whole_23_view);
		views.add(d_whole_04_view);
		views.add(d_whole_14_view);
		views.add(d_whole_24_view);
		views.add(d_whole_05_view);
		views.add(d_whole_15_view);
		views.add(d_whole_25_view);
		views.add(d_whole_lelse_view);
		for (int i = 0; i < views.size(); i++) {
			if (Constant.MATCH_STATUS.get(fullGroupPositin)
					.get(fullChildposition).get(i) == 0) {
				views.get(i).setBackgroundResource(R.drawable.cp);
				textviews1.get(i).setTextColor(graytext);
				textviews.get(i).setTextColor(graytext);
			} else {
				views.get(i).setBackgroundResource(R.drawable.pink);
				textviews1.get(i).setTextColor(whitetext);
				textviews.get(i).setTextColor(whitetext);
			}
		}
		clickViews = views;
		View okButton = alert.findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < 6; i++) {
					if (Constant.MATCH_STATUS.get(fullGroupPositin)
							.get(fullChildposition).get(i) == 0) {
						currentFirstSixView.get(i).setBackgroundResource(
								R.drawable.cp);
						curentText.get(i).setTextColor(graytext);
						curentText1.get(i).setTextColor(graytext);
					} else {
						currentFirstSixView.get(i).setBackgroundResource(
								R.drawable.pink);
						curentText.get(i).setTextColor(whitetext);
						curentText1.get(i).setTextColor(whitetext);
					}
				}
				int select = 0;
				for (int i = 6; i < 55; i++) {
					if (Constant.MATCH_STATUS.get(fullGroupPositin)
							.get(fullChildposition).get(i) == 1) {
						select++;
					}
				}
				if (select > 0) {
					for (int i = 0; i < 6; i++) {
						if (Constant.MATCH_STATUS.get(fullGroupPositin)
								.get(fullChildposition).get(i) == 1) {
							select++;
						}
					}
				}
				// for(int
				// i:Constant.MATCH_STATUS.get(fullGroupPositin).get(fullChildposition)){
				// if(i==1&&i<55){
				// select++;
				// }
				// }
				if (select > 0) {
					showThreeView.get(0).setBackgroundResource(R.drawable.pink);
					TextView v1 = (TextView) (showThreeView.get(1));
					TextView v2 = (TextView) (showThreeView.get(2));
					v1.setText("已选");
					v2.setText("" + select + "项");
					v1.setTextColor(whitetext);
					v2.setTextColor(whitetext);
				} else {
					showThreeView.get(0).setBackgroundResource(R.drawable.cp);
					TextView v1 = (TextView) (showThreeView.get(1));
					TextView v2 = (TextView) (showThreeView.get(2));
					v1.setText("展开");
					v2.setText("全部");
					v1.setTextColor(graytext);
					v2.setTextColor(graytext);
				}
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_BET_FRESH;
				handler.sendMessage(msg);
				betMsg = new Message();
				betMsg.what = Constant.FOOTBALL_BET_MSG;
				handler.sendMessage(betMsg);
				alert.dismiss();
				// alert.isShowing();
				// dialogCount=0;
			}
		});

		View cancelButton = alert.findViewById(R.id.cancel);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < 65; i++) {
					Constant.MATCH_STATUS.get(fullGroupPositin)
							.get(fullChildposition).set(i, 0);
				}
				for (int i = 0; i < 6; i++) {
					currentFirstSixView.get(i).setBackgroundResource(
							R.drawable.cp);
				}
				for (TextView t : curentText) {
					t.setTextColor(graytext);
				}
				for (TextView t : curentText1) {
					t.setTextColor(graytext);
				}
				showThreeView.get(0).setBackgroundResource(R.drawable.cp);
				TextView v1 = (TextView) (showThreeView.get(1));
				TextView v2 = (TextView) (showThreeView.get(2));
				v1.setText("展开");
				v2.setText("全部");
				v1.setTextColor(graytext);
				v2.setTextColor(graytext);
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_BET_FRESH;
				handler.sendMessage(msg);
				betMsg = new Message();
				betMsg.what = Constant.FOOTBALL_BET_MSG;
				handler.sendMessage(betMsg);
				alert.dismiss();
				// dialogCount=0;
			}
		});
	}
}