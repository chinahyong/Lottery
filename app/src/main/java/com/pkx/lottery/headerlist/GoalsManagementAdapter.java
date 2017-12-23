package com.pkx.lottery.headerlist;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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

public class GoalsManagementAdapter extends BaseExpandableListAdapter implements
		QQHeaderAdapter {
	// private Match currentMatch;
	// private static int dialogCount=0;
	private ManagementListView listView;
	final AlertDialog alert;
	// private ArrayList<ArrayList<ArrayList<Integer>>> status;
	private Context context;
	private Handler handler;
	private int blueText = Color.parseColor("#904c0f");
	private int whiteText = Color.parseColor("#FFFFFF");
	private int blackText = Color.parseColor("#4D4D4D");
	private ArrayList<View> currentFirstSixView, showThreeView;
	private ArrayList<DayMatchs> dayMatchses;
	private ArrayList<ArrayList<Match>> matchsInOnedayList;
	private Message betMsg;
	@SuppressLint("UseSparseArrays")
	public HashMap<Integer, Integer> groupStatusMap = new HashMap<Integer, Integer>();
	private ArrayList<View> clickViews;
	private ArrayList<TextView> clickTexts;
	private int fullChildposition, fullGroupPositin;

	public GoalsManagementAdapter(Context context, ManagementListView listView,
			ArrayList<DayMatchs> dayMatchses, Handler handler) {
		alert = new AlertDialog.Builder(context).create();
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
					R.layout.match_points, null);
		}
		Match child = dayMatchses.get(groupPosition).getMatchs()
				.get(childPosition);
		TextView matchNameText, matchNumberText, noHandicWinText, noHandicEqualText, noHandicLoseText, handicWinText, handicEqualText, handicLoseText, hostTeamNameText, visitTeamNameText;
		final View matchShowView;
		final TextView showText1;
		// String[]
		// infos=Constant.MATCH_INFOS.get(groupPosition).get(childPosition).split("*");
		showText1 = (TextView) convertView.findViewById(R.id.showText1);
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
		handicWinText = (TextView) convertView.findViewById(R.id.handicWinText);
		handicEqualText = (TextView) convertView
				.findViewById(R.id.handicEqualText);
		handicLoseText = (TextView) convertView
				.findViewById(R.id.handicLoseText);
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

		matchShowView = convertView.findViewById(R.id.matchShowView);
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
				showThreeView = new ArrayList<View>();
				showThreeView.add(matchShowView);
				showThreeView.add(showText1);
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
			String betStr = "";
			for (int i = 6; i < 55; i++) {
				if (Constant.MATCH_STATUS.get(groupPosition).get(childPosition)
						.get(i) == 1) {
					if (betStr.length() == 0) {
						betStr = Constant.FOOTBALL_BET_STRS[i];
					} else {
						betStr += "," + Constant.FOOTBALL_BET_STRS[i];
					}

				}
			}
			matchShowView.setBackgroundResource(R.drawable.pink);
			// TextView v1 = (TextView) (showThreeView.get(1));
			// TextView v2 = (TextView) (showThreeView.get(2));
			showText1.setText(betStr);
			showText1.setTextColor(whiteText);
			// v2.setText("" + select + "项");
		} else {
			matchShowView.setBackgroundResource(R.drawable.cp);
			// TextView v1 = (TextView) (showThreeView.get(1));
			showText1.setTextColor(blackText);
			// TextView v2 = (TextView) (showThreeView.get(2));
			showText1.setText("请点击选择投注项");
			// v2.setText("全部");
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
						v.setBackgroundResource(R.drawable.points_rect_red);
						clickTexts.get(i).setTextColor(whiteText);
						Constant.MATCH_STATUS.get(fullGroupPositin)
								.get(fullChildposition).set(i, 1);
					} else {
						v.setBackgroundResource(R.drawable.cp);
						clickTexts.get(i).setTextColor(blueText);
						Constant.MATCH_STATUS.get(fullGroupPositin)
								.get(fullChildposition).set(i, 0);
					}
					break;
				}
			}
		}
	};

	private void alertFootballBet(Context ctx, int groupPos, int childPos,
			String host, String visit, String handic) {
		// dialogCount++;
		// if (dialogCount > 1) {
		// return;
		// }
		if (alert.isShowing()) {
			return;
		}
		alert.setCancelable(false);
		alert.show();
		alert.getWindow().setContentView(R.layout.football_goals_dialog);
		final ArrayList<View> views = new ArrayList<View>();
		final ArrayList<TextView> textviews = new ArrayList<TextView>();
		final ArrayList<TextView> textviews1 = new ArrayList<TextView>();
		View d_noHandicWinView, d_noHandicEqualView, d_noHandicLoseView, d_handicWinView, d_handicEqualView, d_handicLoseView, d_goals_0_view, d_goals_1_view, d_goals_2_view, d_goals_3_view, d_goals_4_view, d_goals_5_view, d_goals_6_view, d_goals_7_view, d_ww_view, d_we_view, d_wl_view, d_ew_view, d_ee_view, d_el_view, d_lw_view, d_le_view, d_ll_view, d_whole_10_view, d_whole_20_view, d_whole_21_view, d_whole_30_view, d_whole_31_view, d_whole_32_view, d_whole_40_view, d_whole_41_view, d_whole_42_view, d_whole_50_view, d_whole_51_view, d_whole_52_view, d_whole_welse_view, d_whole_00_view, d_whole_11_view, d_whole_22_view, d_whole_33_view, d_whole_eeelse_view, d_whole_01_view, d_whole_02_view, d_whole_12_view, d_whole_03_view, d_whole_13_view, d_whole_23_view, d_whole_04_view, d_whole_14_view, d_whole_24_view, d_whole_05_view, d_whole_15_view, d_whole_25_view, d_whole_lelse_view;
		TextView d_noHandicWinText, d_noHandicEqualText, d_noHandicLoseText, d_handicWinText, d_handicEqualText, d_handicLoseText, d_goals_0_text, d_goals_1_text, d_goals_2_text, d_goals_3_text, d_goals_4_text, d_goals_5_text, d_goals_6_text, d_goals_7_text, d_ww_text, d_we_textw, d_wl_text, d_ew_text, d_ee_text, d_el_text, d_lw_text, d_le_text, d_ll_text, d_whole_10_text, d_whole_20_text, d_whole_21_text, d_whole_30_text, d_whole_31_text, d_whole_32_text, d_whole_40_text, d_whole_41_text, d_whole_42_text, d_whole_50_text, d_whole_51_text, d_whole_52_text, d_whole_welse_text, d_whole_00_text, d_whole_11_text, d_whole_22_text, d_whole_33_text, d_whole_eeelse_text, d_whole_01_text, d_whole_02_text, d_whole_12_text, d_whole_03_text, d_whole_13_text, d_whole_23_text, d_whole_04_text, d_whole_14_text, d_whole_24_text, d_whole_05_text, d_whole_15_text, d_whole_25_text, d_whole_lelse_text;
		TextView d_noHandicWinText1, d_noHandicEqualText1, d_noHandicLoseText1, d_handicWinText1, d_handicEqualText1, d_handicLoseText1, d_goals_0_text1, d_goals_1_text1, d_goals_2_text1, d_goals_3_text1, d_goals_4_text1, d_goals_5_text1, d_goals_6_text1, d_goals_7_text1, d_ww_text1, d_we_textw1, d_wl_text1, d_ew_text1, d_ee_text1, d_el_text1, d_lw_text1, d_le_text1, d_ll_text1, d_whole_10_text1, d_whole_20_text1, d_whole_21_text1, d_whole_30_text1, d_whole_31_text1, d_whole_32_text1, d_whole_40_text1, d_whole_41_text1, d_whole_42_text1, d_whole_50_text1, d_whole_51_text1, d_whole_52_text1, d_whole_welse_text1, d_whole_00_text1, d_whole_11_text1, d_whole_22_text1, d_whole_33_text1, d_whole_eeelse_text1, d_whole_01_text1, d_whole_02_text1, d_whole_12_text1, d_whole_03_text1, d_whole_13_text1, d_whole_23_text1, d_whole_04_text1, d_whole_14_text1, d_whole_24_text1, d_whole_05_text1, d_whole_15_text1, d_whole_25_text1, d_whole_lelse_text1;
		TextView d_host, d_guest, d_handic;
		// View d_handicView, d_noHandicView;
		// d_handicView = alert.findViewById(R.id.d_handicView);
		// d_noHandicView = alert.findViewById(R.id.d_noHandicView);
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
		d_handicLoseText1 = (TextView) alert.findViewById(R.id.d_handicLoseText1);
		d_goals_0_text1= (TextView) alert.findViewById(R.id.d_goals_0_text1);
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
		clickTexts=textviews1;
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
		ArrayList<TextView> goalsViews = new ArrayList<TextView>();
		goalsViews.add(d_goals_0_text);
		goalsViews.add(d_goals_1_text);
		goalsViews.add(d_goals_2_text);
		goalsViews.add(d_goals_3_text);
		goalsViews.add(d_goals_4_text);
		goalsViews.add(d_goals_5_text);
		goalsViews.add(d_goals_6_text);
		goalsViews.add(d_goals_7_text);
		for (int i = 0; i < goalsViews.size(); i++) {
			goalsViews.get(i).setText(
					Constant.MIX_DAYMATCHESS.get(groupPos).getMatchs()
							.get(childPos).getTotalgoal_odds().get(i));
		}
		for (int i = 0; i < views.size(); i++) {
			if (Constant.MATCH_STATUS.get(fullGroupPositin)
					.get(fullChildposition).get(i) == 0) {
				views.get(i).setBackgroundResource(R.drawable.cp);
				textviews1.get(i).setTextColor(blueText);
			} else {
				views.get(i).setBackgroundResource(R.drawable.points_rect_red);
				textviews1.get(i).setTextColor(whiteText);
			}
		}
		clickViews = views;
		View bg = alert.findViewById(R.id.bgView);
		bg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.e("pkx", "bg click");
			}
		});
		View okButton = alert.findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
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
				if (select > 0) {

					String betStr = "";
					for (int i = 6; i < 55; i++) {
						if (Constant.MATCH_STATUS.get(fullGroupPositin)
								.get(fullChildposition).get(i) == 1) {
							if (betStr.length() == 0) {
								betStr = Constant.FOOTBALL_BET_STRS[i];
							} else {
								betStr += "," + Constant.FOOTBALL_BET_STRS[i];
							}
						}
					}
					showThreeView.get(0).setBackgroundResource(R.drawable.pink);
					TextView v1 = (TextView) (showThreeView.get(1));
					// TextView v2 = (TextView) (showThreeView.get(2));
					v1.setText(betStr);
					v1.setTextColor(whiteText);
					// v2.setText("" + select + "项");
				} else {
					showThreeView.get(0).setBackgroundResource(R.drawable.cp);
					TextView v1 = (TextView) (showThreeView.get(1));
					v1.setTextColor(blackText);
					// TextView v2 = (TextView) (showThreeView.get(2));
					v1.setText("请点击选择投注项");
					// v2.setText("全部");
				}
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_BET_FRESH;
				handler.sendMessage(msg);
				betMsg = new Message();
				betMsg.what = Constant.FOOTBALL_BET_MSG;
				handler.sendMessage(betMsg);
				alert.dismiss();
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
				Message msg = new Message();
				msg.what = Constant.FOOTBALL_BET_FRESH;
				handler.sendMessage(msg);
				betMsg = new Message();
				betMsg.what = Constant.FOOTBALL_BET_MSG;
				handler.sendMessage(betMsg);
				showThreeView.get(0).setBackgroundResource(R.drawable.cp);
				TextView v1 = (TextView) (showThreeView.get(1));
				v1.setText("请点击选择投注项");
				v1.setTextColor(blackText);
				alert.dismiss();
			}
		});
	}
}