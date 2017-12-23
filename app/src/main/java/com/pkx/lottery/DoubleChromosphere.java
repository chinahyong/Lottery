package com.pkx.lottery;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.TextView;

import com.pkx.lottery.utils.RandomBallsUtils;

import java.util.ArrayList;

public class DoubleChromosphere extends Activity implements OnClickListener {
	private Button button1;
	private TextView textView1;
	private ArrayList<Integer> ballNumList;
	private GridView ballGrid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.double_chromosphere);
		initViews();
	}

	public void clickBack(View view) {
	super.onBackPressed();
}
	private void initViews() {
		textView1 = (TextView) findViewById(R.id.textView1);
		button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(this);
		ballNumList = new ArrayList<Integer>();
		for (int i = 1; i < 34; i++) {
			ballNumList.add(i);
		}
		ballGrid = (GridView) findViewById(R.id.ballGrid);
		ballGrid.setAdapter(new BallsAdapter(ballNumList));

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			ArrayList<Integer> a = RandomBallsUtils.getRandomBalls(6, 32);
			String rst = "";
			for (int i : a) {
				rst += String.valueOf(i) + ",";
			}
			rst = rst.substring(0, rst.length() - 1);
			Log.e("pkx", rst);
			textView1.setText(rst);
			break;

		default:
			break;
		}

	}

	private class BallsAdapter extends BaseAdapter {
		public ArrayList<Integer> numList;
		public BallsAdapter(ArrayList<Integer> list){
			numList=list;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ballNumList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return ballNumList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inf = getLayoutInflater();
			convertView = inf.inflate(R.layout.ball_grid_item, null);
			CheckBox c=(CheckBox) convertView.findViewById(R.id.boxItem);
			c.setButtonDrawable(R.drawable.ball_radio_selector);
			TextView num = (TextView) convertView.findViewById(R.id.ballNum);
			num.setText(("" + numList.get(position)));
			return convertView;
		}

	}
}
