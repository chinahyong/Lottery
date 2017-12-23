package com.pkx.lottery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pkx.lottery.dto.lott.details.FootballLott;
import com.pkx.lottery.utils.Net;

public class BuyRecoredFootballFollowDetails extends Activity {
	/**
	 * 足彩合买认购详情页
	 */
	private TextView orderID, payTime, payPrice, orderStatus, buyMulty,
			prizeInfo;
	private static String[] status_set = { "68彩票", "已生成", "已出票", "未出票", "过期",
			"已关闭", "合买进行中", "已发奖", "过期已支付未出票", "过期已支付已退款" };
	// private ListView footballBetList;
	// private ListView footballBundList;
	private LayoutInflater inflater;
	private Intent mIntent;
	private FootballLott lott;
	// private boolean isBund;
	private BaseAdapter betAdapter, bundAdapter;
	private TextView typeName;
	private LinearLayout showBetView, showBundView;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buyrecored_football_follow_details);
		initViews();
		Log.e("pkx",
				"-----------------BuyRecoredFootballDetails-----------------");
	}

	public void getView() {

		for (int position = 0; position < lott.getData().getMatch().size(); position++) {
			// if (convertView == null) {
			// }
			View convertView = inflater.inflate(
					R.layout.buyrecored_football_buy_detail, null);
			TextView hostName, handicPoints, visitName, points, betString;
			ImageView danFlag;
			hostName = (TextView) convertView.findViewById(R.id.hostName);
			danFlag = (ImageView) convertView.findViewById(R.id.danFlag);
			handicPoints = (TextView) convertView
					.findViewById(R.id.handicPoints);
			visitName = (TextView) convertView.findViewById(R.id.visitName);
			points = (TextView) convertView.findViewById(R.id.points);
			betString = (TextView) convertView.findViewById(R.id.betString);
			hostName.setText(lott.getData().getMatch().get(position)
					.getMainTeam());
			handicPoints.setText(lott.getData().getMatch().get(position)
					.getLetBall());
			visitName.setText(lott.getData().getMatch().get(position)
					.getGuestTeam());
			if(lott.getData().getMatch().get(position).getDan()==1){
				danFlag.setVisibility(View.VISIBLE);
			}else{
				danFlag.setVisibility(View.GONE);
			}
			if (lott.getData().getMatch().get(position).getScore() == null) {
				points.setText("--");
			} else {
				points.setText(lott.getData().getMatch().get(position)
						.getScore());
			}
			hostName.setText(lott.getData().getMatch().get(position)
					.getMainTeam());
			betString.setText(lott.getData().getMatch().get(position).getTz());
			showBetView.addView(convertView);
		}

	}

	public void getBundView() {
		for (int p = 0; p < lott.getData().getMatchOdds().size(); p++) {
			LinearLayout linear = (LinearLayout) inflater.inflate(
					R.layout.bund_linear, null);
			for (int position = 0; position < lott.getData().getMatchOdds()
					.get(p).getOdds().size(); position++) {
				// if (convertView == null) {
				// }
				View convertView = inflater.inflate(
						R.layout.buyrecored_football_bund_detail, null);
				TextView hostName, handicPoints, visitName, points, betString, spPrice;
				hostName = (TextView) convertView.findViewById(R.id.hostName);
				handicPoints = (TextView) convertView
						.findViewById(R.id.handicPoints);
				visitName = (TextView) convertView.findViewById(R.id.visitName);
				points = (TextView) convertView.findViewById(R.id.points);
				betString = (TextView) convertView.findViewById(R.id.betString);
				spPrice = (TextView) convertView.findViewById(R.id.spPrice);
				hostName.setText(lott.getData().getMatchOdds().get(0).getOdds()
						.get(position).getMainTeam());
				handicPoints.setText(lott.getData().getMatchOdds().get(0)
						.getOdds().get(position).getLetBall());
				visitName.setText(lott.getData().getMatchOdds().get(0)
						.getOdds().get(position).getGuestTeam());
				if (lott.getData().getMatchOdds().get(0).getOdds()
						.get(position).getScore() == null) {
					points.setText("--");
				} else {
					points.setText(lott.getData().getMatchOdds().get(0)
							.getOdds().get(position).getScore());
				}
				hostName.setText(lott.getData().getMatchOdds().get(0).getOdds()
						.get(position).getMainTeam());
				spPrice.setText(lott.getData().getMatchOdds().get(0).getOdds()
						.get(position).getOdds());
				// betString.setText(lott.getData().getMatch().get(position).getTz());
				linear.addView(convertView);
			}
			showBundView.addView(linear);
		}
	}

	private void initViews() {
		mIntent = getIntent();
		inflater = getLayoutInflater();
		String json = mIntent.getStringExtra("json");
		if (mIntent.getBooleanExtra("isBasketBall", false)) {
			typeName = (TextView) findViewById(R.id.typeName);
			typeName.setText("篮球合买认购");
		}
		orderID = (TextView) findViewById(R.id.orderID);
		payTime = (TextView) findViewById(R.id.payTime);
		payPrice = (TextView) findViewById(R.id.payPrice);
		orderStatus = (TextView) findViewById(R.id.orderStatus);
		buyMulty = (TextView) findViewById(R.id.buyMulty);
		prizeInfo = (TextView) findViewById(R.id.prizeInfo);
		lott = Net.gson.fromJson(json, FootballLott.class);
		showBundView = (LinearLayout) findViewById(R.id.showBundView);

		showBetView = (LinearLayout) findViewById(R.id.showBetView);
		getView();
		if (lott.getData().getMatchOdds() != null
				&& lott.getData().getMatchOdds().size() > 0) {
			getBundView();
		}
		orderID.setText(lott.getOrder_info().getNo());
		payTime.setText(lott.getOrder_info().getPay_date());
		payPrice.setText(lott.getData().getBet_amount() + "元");
		orderStatus.setText(status_set[Integer.valueOf(lott.getData()
				.getOrder_status())]);
		// footballBetList = (ListView) findViewById(R.id.footballBetList);
		// footballBetList.setDividerHeight(5);
		// betAdapter = new BaseAdapter() {
		//
		// @Override
		// public View getView(int position, View convertView, ViewGroup parent)
		// {
		// if (convertView == null) {
		// convertView = inflater.inflate(
		// R.layout.buyrecored_football_buy_detail, null);
		// }
		// TextView hostName, handicPoints, visitName, points, betString;
		// hostName = (TextView) convertView.findViewById(R.id.hostName);
		// handicPoints = (TextView) convertView
		// .findViewById(R.id.handicPoints);
		// visitName = (TextView) convertView.findViewById(R.id.visitName);
		// points = (TextView) convertView.findViewById(R.id.points);
		// betString = (TextView) convertView.findViewById(R.id.betString);
		// hostName.setText(lott.getData().getMatch().get(position)
		// .getMainTeam());
		// handicPoints.setText(lott.getData().getMatch().get(position)
		// .getLetBall());
		// visitName.setText(lott.getData().getMatch().get(position)
		// .getGuestTeam());
		// if (lott.getData().getMatch().get(position).getScore() == null) {
		// points.setText("--");
		// } else {
		// points.setText(lott.getData().getMatch().get(position)
		// .getScore());
		// }
		// hostName.setText(lott.getData().getMatch().get(position)
		// .getMainTeam());
		// betString.setText(lott.getData().getMatch().get(position)
		// .getTz());
		// return convertView;
		// }
		//
		// @Override
		// public long getItemId(int position) {
		// return 0;
		// }
		//
		// @Override
		// public Object getItem(int position) {
		// return null;
		// }
		//
		// @Override
		// public int getCount() {
		// return lott.getData().getMatch().size();
		// }
		// };
		// footballBetList.setAdapter(betAdapter);
		// if (isBund) {
		// footballBundList = (ListView) findViewById(R.id.footballBundList);
		// footballBundList.setDividerHeight(5);
		// bundAdapter = new BaseAdapter() {
		//
		// @Override
		// public View getView(int position, View convertView,
		// ViewGroup parent) {
		// if (convertView == null) {
		// convertView = inflater.inflate(
		// R.layout.buyrecored_football_bund_detail, null);
		// }
		// TextView hostName, handicPoints, visitName, points, betString,
		// spPrice;
		// hostName = (TextView) convertView
		// .findViewById(R.id.hostName);
		// handicPoints = (TextView) convertView
		// .findViewById(R.id.handicPoints);
		// visitName = (TextView) convertView
		// .findViewById(R.id.visitName);
		// points = (TextView) convertView.findViewById(R.id.points);
		// betString = (TextView) convertView
		// .findViewById(R.id.betString);
		// spPrice = (TextView) convertView.findViewById(R.id.spPrice);
		// hostName.setText(lott.getData().getMatchOdds()
		// .get(position).getOdds().get(0).getMainTeam());
		// handicPoints.setText(lott.getData().getMatchOdds()
		// .get(position).getOdds().get(0).getLetBall());
		// visitName.setText(lott.getData().getMatchOdds()
		// .get(position).getOdds().get(0).getGuestTeam());
		// if (lott.getData().getMatchOdds().get(position).getOdds()
		// .get(0).getScore() == null) {
		// points.setText("--");
		// } else {
		// points.setText(lott.getData().getMatchOdds()
		// .get(position).getOdds().get(0).getScore());
		// }
		// hostName.setText(lott.getData().getMatchOdds()
		// .get(position).getOdds().get(0).getMainTeam());
		// betString.setText(lott.getData().getMatchOdds()
		// .get(position).getOdds().get(0).getTz());
		// spPrice.setText(lott.getData().getMatchOdds().get(position)
		// .getOdds().get(0).getOdds());
		// return convertView;
		// }
		//
		// @Override
		// public long getItemId(int position) {
		// return 0;
		// }
		//
		// @Override
		// public Object getItem(int position) {
		// return null;
		// }
		//
		// @Override
		// public int getCount() {
		// return lott.getData().getMatchOdds().size();
		// }
		// };
		// footballBundList.setAdapter(bundAdapter);
		// }

	}
}
