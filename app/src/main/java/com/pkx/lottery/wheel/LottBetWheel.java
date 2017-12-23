package com.pkx.lottery.wheel;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.pkx.lottery.Constant;
import com.pkx.lottery.R;

@SuppressLint("NewApi")
public class LottBetWheel {
	private Handler mHandler;
	private int mType=-1;
	private int value;
	private View view;
	private WheelView wv_year;
	private OnWheelChangedListener wheelListener_year;
	private AlertDialog dialog;
	public void showWheel(int position,int type) {
		mType=type;
		TextView tv=(TextView) dialog.findViewById(R.id.dialogType);
		if(type==0){
			tv.setText("倍投");
			wv_year.setLabel("倍");
			wv_year.setAdapter(new NumericWheelAdapter(1, 99));
		}else{
			tv.setText("追号");
			wv_year.setLabel("期");
			wv_year.setAdapter(new NumericWheelAdapter(0, 12));
		}
		wv_year.setCurrentItem(position);
		dialog.show();
	}

	public LottBetWheel(Context ctx, Handler h) {
		mHandler = h;
		dialog = new AlertDialog.Builder(ctx, R.style.dialog).create();
		dialog.show();
		dialog.getWindow().setContentView(R.layout.wheel_dialog);
		dialog.dismiss();
		View okButton = dialog.findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message msg = new Message();
				msg.what = Constant.WHEEL_SELECTED;
				msg.arg1 = mType;
				msg.arg2 = value;
				mHandler.sendMessage(msg);
				dialog.dismiss();
			}
		});
		View cancelButton = dialog.findViewById(R.id.cancel);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		this.view = dialog.findViewById(R.id.myWheel);
		wv_year = (WheelView) dialog.findViewById(R.id.times);
		wv_year.setAdapter(new NumericWheelAdapter(1, 99));// 设置显示数据
		wv_year.setCyclic(true);// 可循环滚动
		wv_year.setLabel("倍");// 添加文字
		wv_year.setCurrentItem(0);// 初始化时显示的数据
		wheelListener_year = new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				value = newValue;
			}
		};
		wv_year.addChangingListener(wheelListener_year);
	}

	public int getValue() {
		return value;
	}

	public void setCurrentItem(int p) {
		wv_year.setCurrentItem(p);
	}

	public View getView() {
		return view;
	}
}
