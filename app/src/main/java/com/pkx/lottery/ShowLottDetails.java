package com.pkx.lottery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkx.lottery.dto.ChormDetail;
import com.pkx.lottery.utils.Net;

public class ShowLottDetails extends Activity {
	private Intent mIntent;
	private int type;
	private ChormDetail detail;
	private ImageView lottImage, three_direct_6;
	private TextView lottTypeText, lottPeroidText, ball1, ball2, ball3, ball4,
			ball5, ball6, ball7, ball8, bundNum1, bundNum2, bundNum3, bundNum4,
			bundNum5, bundNum6, bundNum7, bundMoney1, bundMoney2, bundMoney3,
			bundMoney4, bundMoney5, bundMoney6, bundMoney7, salesNum,
			prizePool, betExpire, prize3, prize2, prize1;
	private View lastFiveView, seventhView, bund4, bund5, bund6, bund7;
	private View buyNow;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showlott_details);
		initViews();

	}

	// @Override
	// public void onClick(View v) {
	//
	// }

	private void initViews() {
		mIntent = getIntent();
		type = mIntent.getIntExtra("type", 0);
		buyNow = findViewById(R.id.buyNow);
		buyNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (type) {
				case 0:

					break;
				case 1:
					startActivity(new Intent(ShowLottDetails.this,
							MyDoubleChromosphere.class));
					break;
				case 2:

					startActivity(new Intent(ShowLottDetails.this,
							SevenLottery.class));
					break;
				case 3:
					startActivity(new Intent(ShowLottDetails.this,
							ThreeDimension.class));
					break;
				}
			}
		});
		detail = Net.gson.fromJson(mIntent.getStringExtra("data"),
				ChormDetail.class);
		three_direct_6 = (ImageView) findViewById(R.id.three_direct_6);
		lottImage = (ImageView) findViewById(R.id.lottImage);
		lottTypeText = (TextView) findViewById(R.id.lottTypeText);
		lottPeroidText = (TextView) findViewById(R.id.lottPeroidText);
		lastFiveView = findViewById(R.id.lastFiveView);
		seventhView = findViewById(R.id.seventhView);
		bund4 = findViewById(R.id.bund4);
		bund5 = findViewById(R.id.bund5);
		bund6 = findViewById(R.id.bund6);
		bund7 = findViewById(R.id.bund7);
		// lastFiveView.setOnClickListener(this);
		// seventhView.setOnClickListener(this);
		// buyView.setOnClickListener(this);
		betExpire = (TextView) findViewById(R.id.betExpire);
		prize1 = (TextView) findViewById(R.id.prize1);
		prize2 = (TextView) findViewById(R.id.prize2);
		prize3 = (TextView) findViewById(R.id.prize3);
		ball1 = (TextView) findViewById(R.id.ball1);
		ball2 = (TextView) findViewById(R.id.ball2);
		ball3 = (TextView) findViewById(R.id.ball3);
		ball4 = (TextView) findViewById(R.id.ball4);
		ball5 = (TextView) findViewById(R.id.ball5);
		ball6 = (TextView) findViewById(R.id.ball6);
		ball7 = (TextView) findViewById(R.id.ball7);
		ball8 = (TextView) findViewById(R.id.ball8);
		bundNum1 = (TextView) findViewById(R.id.bundNum1);
		bundNum2 = (TextView) findViewById(R.id.bundNum2);
		bundNum3 = (TextView) findViewById(R.id.bundNum3);
		bundNum4 = (TextView) findViewById(R.id.bundNum4);
		bundNum5 = (TextView) findViewById(R.id.bundNum5);
		bundNum6 = (TextView) findViewById(R.id.bundNum6);
		bundNum7 = (TextView) findViewById(R.id.bundNum7);
		bundMoney1 = (TextView) findViewById(R.id.bundMoney1);
		bundMoney2 = (TextView) findViewById(R.id.bundMoney2);
		bundMoney3 = (TextView) findViewById(R.id.bundMoney3);
		bundMoney4 = (TextView) findViewById(R.id.bundMoney4);
		bundMoney5 = (TextView) findViewById(R.id.bundMoney5);
		bundMoney6 = (TextView) findViewById(R.id.bundMoney6);
		bundMoney7 = (TextView) findViewById(R.id.bundMoney7);
		salesNum = (TextView) findViewById(R.id.salesNum);
		prizePool = (TextView) findViewById(R.id.prizePool);
		switch (type) {
		case 1:
			lottTypeText.setText("双色球");
			lottImage.setImageResource(R.drawable.chromosphere);
			seventhView.setVisibility(View.INVISIBLE);
			three_direct_6.setImageResource(R.drawable.blue_rect);
			bund7.setVisibility(View.GONE);
			if (detail != null && detail.getRed_ball() != null
					&& detail.getRed_ball().size() == 6) {
				ball1.setText(String.valueOf(detail.getRed_ball().get(0)));
				ball2.setText(String.valueOf(detail.getRed_ball().get(1)));
				ball3.setText(String.valueOf(detail.getRed_ball().get(2)));
				ball4.setText(String.valueOf(detail.getRed_ball().get(3)));
				ball5.setText(String.valueOf(detail.getRed_ball().get(4)));
				ball6.setText(String.valueOf(detail.getRed_ball().get(5)));
				ball7.setText(String.valueOf(detail.getBlue_ball()));
				betExpire.setText("开奖时间 : " + String.valueOf(detail.getRes_date()));
				bundNum1.setText(String.valueOf(detail.getBonus_detail().get(0).getBet()));
				bundMoney1.setText(String.valueOf(detail.getBonus_detail().get(0).getPrize()));
				bundNum2.setText(String.valueOf(detail.getBonus_detail().get(1).getBet()));
				bundMoney2.setText(String.valueOf(detail.getBonus_detail().get(1).getPrize()));
				bundNum3.setText(String.valueOf(detail.getBonus_detail().get(2).getBet()));
				bundMoney3.setText(String.valueOf(detail.getBonus_detail().get(2).getPrize()));
				bundNum4.setText(String.valueOf(detail.getBonus_detail().get(3).getBet()));
				bundMoney4.setText(String.valueOf(detail.getBonus_detail().get(3).getPrize()));
				bundNum5.setText(String.valueOf(detail.getBonus_detail().get(4).getBet()));
				bundMoney5.setText(String.valueOf(detail.getBonus_detail().get(4).getPrize()));
				bundNum6.setText(String.valueOf(detail.getBonus_detail().get(5).getBet()));
				bundMoney6.setText(String.valueOf(detail.getBonus_detail().get(5).getPrize()));
				salesNum.setText(String.valueOf(detail.getSale_total()));
				prizePool.setText(String.valueOf(detail.getPrize_pool()));
			}
			break;
		case 2:
			lottTypeText.setText("七乐彩");
			lottImage.setImageResource(R.drawable.poker);

			if (detail != null && detail.getRed_ball() != null
					&& detail.getRed_ball().size() == 7) {
				ball1.setText(String.valueOf(detail.getRed_ball().get(0)));
				ball2.setText(String.valueOf(detail.getRed_ball().get(1)));
				ball3.setText(String.valueOf(detail.getRed_ball().get(2)));
				ball4.setText(String.valueOf(detail.getRed_ball().get(3)));
				ball5.setText(String.valueOf(detail.getRed_ball().get(4)));
				ball6.setText(String.valueOf(detail.getRed_ball().get(5)));
				ball7.setText(String.valueOf(detail.getRed_ball().get(6)));
				ball8.setText(String.valueOf(detail.getBlue_ball()));
				betExpire.setText("开奖时间 : " + String.valueOf(detail.getRes_date()));
				bundNum1.setText(String.valueOf(detail.getBonus_detail().get(0).getBet()));
				bundMoney1.setText(String.valueOf(detail.getBonus_detail().get(0).getPrize()));
				bundNum2.setText(String.valueOf(detail.getBonus_detail().get(1).getBet()));
				bundMoney2.setText(String.valueOf(detail.getBonus_detail().get(1).getPrize()));
				bundNum3.setText(String.valueOf(detail.getBonus_detail().get(2).getBet()));
				bundMoney3.setText(String.valueOf(detail.getBonus_detail().get(2).getPrize()));
				bundNum4.setText(String.valueOf(detail.getBonus_detail().get(3).getBet()));
				bundMoney4.setText(String.valueOf(detail.getBonus_detail().get(3).getPrize()));
				bundNum5.setText(String.valueOf(detail.getBonus_detail().get(4).getBet()));
				bundMoney5.setText(String.valueOf(detail.getBonus_detail().get(4).getPrize()));
				bundNum6.setText(String.valueOf(detail.getBonus_detail().get(5).getBet()));
				bundMoney6.setText(String.valueOf(detail.getBonus_detail().get(5).getPrize()));
				bundNum7.setText(String.valueOf(detail.getBonus_detail().get(6).getBet()));
				bundMoney7.setText(String.valueOf(detail.getBonus_detail().get(6).getPrize()));
				salesNum.setText(String.valueOf(detail.getSale_total()));
				prizePool.setText(String.valueOf(detail.getPrize_pool()));
			}
			break;
		case 3:
			lottTypeText.setText("3D");
			prize1.setText("直选");
			prize2.setText("组三");
			prize3.setText("组六");
			betExpire.setText("开奖时间 : " + detail.getRes_date());
			lottImage.setImageResource(R.drawable.dimension_3d);
			lastFiveView.setVisibility(View.INVISIBLE);
			bund4.setVisibility(View.GONE);
			bund5.setVisibility(View.GONE);
			bund6.setVisibility(View.GONE);
			bund7.setVisibility(View.GONE);

			if (detail != null && detail.getRed_ball() != null
					&& detail.getRed_ball().size() == 3) {
				ball1.setText(String.valueOf(detail.getRed_ball().get(0)));
				ball2.setText(String.valueOf(detail.getRed_ball().get(1)));
				ball3.setText(String.valueOf(detail.getRed_ball().get(2)));
				salesNum.setText(String.valueOf(detail.getSale_total()));
				prizePool.setText(String.valueOf(detail.getPrize_pool()));
				bundNum1.setText(String.valueOf(detail.getBonus_detail().get(0).getBet()));
				bundMoney1.setText(String.valueOf(detail.getBonus_detail().get(0).getPrize()));
				bundNum2.setText(String.valueOf(detail.getBonus_detail().get(1).getBet()));
				bundMoney2.setText(String.valueOf(detail.getBonus_detail().get(1).getPrize()));
				bundNum3.setText(String.valueOf(detail.getBonus_detail().get(2).getBet()));
				bundMoney3.setText(String.valueOf(detail.getBonus_detail().get(2).getPrize()));
			}
			break;
		}
		lottPeroidText.setText("第" + detail.getPeroid_name() + "期");
	}
}
