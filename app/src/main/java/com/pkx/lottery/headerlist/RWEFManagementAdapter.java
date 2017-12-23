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

public class RWEFManagementAdapter extends BaseExpandableListAdapter implements
		QQHeaderAdapter {
	private ManagementListView listView;
	private Context context;
	private Handler handler;
	private ArrayList<DayMatchs> dayMatchses;
	private ArrayList<ArrayList<Match>> matchsInOnedayList;
	private Message betMsg;
	private int whitetext = Color.parseColor("#ffffff");
	private int graytext = Color.parseColor("#4D4D4D");
	private int redtext = Color.parseColor("#e6544b");
	private int greentext = Color.parseColor("#2ca403");
	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, Integer> groupStatusMap = new HashMap<Integer, Integer>();

	public RWEFManagementAdapter(Context context, ManagementListView listView,
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
					// if (ma.isHandip()) {
					// ins.set(59, 1);
					// } else {
					// ins.set(59, 0);
					// }
					if (null == ma.getId()) {
						ins.set(62, 8888);
					} else {
						ins.set(62, Integer.valueOf(ma.getId()));
					}
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
					R.layout.match_rwef, null);
		}
		Match child = dayMatchses.get(groupPosition).getMatchs()
				.get(childPosition);
		TextView matchNameText, matchNumberText;
		TextView handic;// 用时需解除
		final TextView handicWinText;
		final TextView handicEqualText;
		final TextView handicLoseText;
		final TextView handicWinText1;
		final TextView handicEqualText1;
		final TextView handicLoseText1;
		TextView hostTeamNameText, visitTeamNameText;
		final View handicWinView;
		final View handicEqualView;
		final View handicLoseView;
		handic = (TextView) convertView.findViewById(R.id.handic);
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
		handicWinText = (TextView) convertView.findViewById(R.id.handicWinText);
		handicEqualText = (TextView) convertView
				.findViewById(R.id.handicEqualText);
		handicLoseText = (TextView) convertView
				.findViewById(R.id.handicLoseText);
		handicWinText1 = (TextView) convertView
				.findViewById(R.id.handicWinText1);
		handicEqualText1 = (TextView) convertView
				.findViewById(R.id.handicEqualText1);
		handicLoseText1 = (TextView) convertView
				.findViewById(R.id.handicLoseText1);
		// infos=Constant.MATCH_INFOS.get(groupPosition).get(childPosition).split("*");
		matchNameText = (TextView) convertView.findViewById(R.id.matchNameText);
		// matchNameText.setText(infos[0]);
		matchNameText.setText(child.getName());
		matchNumberText = (TextView) convertView
				.findViewById(R.id.matchNumberText);
		matchNumberText.setText(child.getRowNumber());
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
		handicWinView = convertView.findViewById(R.id.handicWinView);
		if (Constant.MIX_DAYMATCHESS.get(groupPosition).getMatchs()
				.get(childPosition).getOdds() != null
				&& Constant.MIX_DAYMATCHESS.get(groupPosition).getMatchs()
						.get(childPosition).getOdds().size() == 3) {
			handicLoseText.setText(Constant.MIX_DAYMATCHESS.get(groupPosition)
					.getMatchs().get(childPosition).getOdds().get(0));
			handicEqualText.setText(Constant.MIX_DAYMATCHESS.get(groupPosition)
					.getMatchs().get(childPosition).getOdds().get(1));
			handicWinText.setText(Constant.MIX_DAYMATCHESS.get(groupPosition)
					.getMatchs().get(childPosition).getOdds().get(2));
		}
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
					Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
							.set(3, 1);
					handicWinText.setTextColor(whitetext);
					handicWinText1.setTextColor(whitetext);
				} else {
					v.setBackgroundResource(R.drawable.cp);
					handicWinText.setTextColor(graytext);
					handicWinText1.setTextColor(graytext);
					Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
							.set(3, 0);
				}
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
}