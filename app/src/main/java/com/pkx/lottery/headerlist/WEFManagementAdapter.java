package com.pkx.lottery.headerlist;

import android.annotation.SuppressLint;
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

public class WEFManagementAdapter extends BaseExpandableListAdapter implements
		QQHeaderAdapter {
	private ManagementListView listView;
	private Context context;
	private Handler handler;
	// private ArrayList<View> currentFirstSixView, showThreeView;
	private ArrayList<DayMatchs> dayMatchses;
	private int whitetext = Color.parseColor("#ffffff");
	private int graytext = Color.parseColor("#4D4D4D");
	private ArrayList<ArrayList<Match>> matchsInOnedayList;
	private Message betMsg;
	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, Integer> groupStatusMap = new HashMap<Integer, Integer>();

	// private ArrayList<View> clickViews;
	// private int fullChildposition, fullGroupPositin;

	public WEFManagementAdapter(Context context, ManagementListView listView,
			ArrayList<DayMatchs> dayMatchses, Handler handler) {
		this.context = context;
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
					if (ma.isHandip()) {
						ins.set(59, 1);
					} else {
						ins.set(59, 0);
					}
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
					R.layout.match_wef, null);
		}
		Match child = dayMatchses.get(groupPosition).getMatchs()
				.get(childPosition);
		TextView matchNameText, matchNumberText;
		final TextView noHandicWinText;
		final TextView noHandicEqualText;
		final TextView noHandicLoseText;
		final TextView noHandicWinText1;
		final TextView noHandicEqualText1;
		final TextView noHandicLoseText1;
		TextView hostTeamNameText, visitTeamNameText;
		final View noHandicWinView;
		final View noHandicEqualView;
		final View noHandicLoseView;
		// infos=Constant.MATCH_INFOS.get(groupPosition).get(childPosition).split("*");
		matchNameText = (TextView) convertView.findViewById(R.id.matchNameText);
		// matchNameText.setText(infos[0]);
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
		noHandicWinText1 = (TextView) convertView
				.findViewById(R.id.noHandicWinText1);
		noHandicEqualText1 = (TextView) convertView
				.findViewById(R.id.noHandicEqualText1);
		noHandicLoseText1 = (TextView) convertView
				.findViewById(R.id.noHandicLoseText1);
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
		noHandicWinView = convertView.findViewById(R.id.noHandicWinView);
		if (Constant.MIX_DAYMATCHESS.get(groupPosition).getMatchs()
				.get(childPosition).getSpfodds() != null
				&& Constant.MIX_DAYMATCHESS.get(groupPosition).getMatchs()
						.get(childPosition).getSpfodds().size() == 3) {
			noHandicLoseText.setText(Constant.MIX_DAYMATCHESS
					.get(groupPosition).getMatchs().get(childPosition)
					.getSpfodds().get(0));
			noHandicEqualText.setText(Constant.MIX_DAYMATCHESS
					.get(groupPosition).getMatchs().get(childPosition)
					.getSpfodds().get(1));
			noHandicWinText.setText(Constant.MIX_DAYMATCHESS.get(groupPosition)
					.getMatchs().get(childPosition).getSpfodds().get(2));
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
				betMsg = new Message();
				betMsg.what = Constant.FOOTBALL_BET_MSG;
				handler.sendMessage(betMsg);
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
				betMsg = new Message();
				betMsg.what = Constant.FOOTBALL_BET_MSG;
				handler.sendMessage(betMsg);
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

	// private OnClickListener footBallBetListener = new OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// for (int i = 0; i < clickViews.size(); i++) {
	// if (v == clickViews.get(i)) {
	// if (Constant.MATCH_STATUS.get(fullGroupPositin)
	// .get(fullChildposition).get(i) == 0) {
	// v.setBackgroundResource(R.drawable.pink);
	// Constant.MATCH_STATUS.get(fullGroupPositin)
	// .get(fullChildposition).set(i, 1);
	// } else {
	// v.setBackgroundResource(R.drawable.cp);
	// Constant.MATCH_STATUS.get(fullGroupPositin)
	// .get(fullChildposition).set(i, 0);
	// }
	// break;
	// }
	// }
	// }
	// };
}