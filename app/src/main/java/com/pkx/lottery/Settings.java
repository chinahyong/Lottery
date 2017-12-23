package com.pkx.lottery;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pkx.lottery.utils.SharePreferenceUtil;

import java.util.ArrayList;

public class Settings extends Activity implements OnClickListener {
	private SharePreferenceUtil sutil;
	private ViewPager paperSetting;
	private View view1, view2, winPushView, deadTimeView, selectVibraView,
			shakeVibraView;
	private TextView selectText, pushText;
	private ImageView deadTimeImg, winImg, selectImg, vibraImg, selectHeadImg,
			pushHeadImg;
	private LayoutInflater inflater;
	private int redText = Color.parseColor("#CD0000");
	private int whiteText = Color.parseColor("#ffffff");
//	private int currentPage = 0;
	private MyHandler mHandler;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		initViews();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.selectHeadImg:
			paperSetting.setCurrentItem(0, true);
			selectText.setTextColor(whiteText);
			pushText.setTextColor(redText);
			selectHeadImg.setBackgroundResource(R.drawable.sele_btn);
			pushHeadImg.setBackgroundResource(R.drawable.nor_btn);
			break;
		case R.id.pushHeadImg:
			paperSetting.setCurrentItem(1, true);
			selectText.setTextColor(redText);
			pushText.setTextColor(whiteText);
			selectHeadImg.setBackgroundResource(R.drawable.nor_btn);
			pushHeadImg.setBackgroundResource(R.drawable.sele_btn);
			break;
		case R.id.selectVibraView:
			if(sutil.getSelectVibrat()){
				sutil.setSelectVibrat(false);
				selectImg.setImageResource(R.drawable.chuan_normal);
			}else{
				sutil.setSelectVibrat(true);
				selectImg.setImageResource(R.drawable.chuan_selected);
			}
			break;
		case R.id.shakeVibraView:
			if(sutil.getShakeVibrat()){
				sutil.setShakeVibrat(false);
				vibraImg.setImageResource(R.drawable.chuan_normal);
			}else{
				sutil.setShakeVibrat(true);
				vibraImg.setImageResource(R.drawable.chuan_selected);
			}
			break;
		case R.id.winPushView:
		case R.id.deadTimeView:
			Toast.makeText(this, "Coming soon...", Toast.LENGTH_SHORT).show();
			break;
		}

	}

	private void initViews() {
		sutil = new SharePreferenceUtil(this);
		inflater = getLayoutInflater();
		mHandler = new MyHandler();
		paperSetting = (ViewPager) findViewById(R.id.paperSetting);
		paperSetting.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				Message msg = new Message();
				msg.what = Constant.PAGE_CHANGED;
				msg.arg1 = arg0;
				mHandler.sendMessage(msg);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		view1 = inflater.inflate(R.layout.tab_setting_set, null);
		view2 = inflater.inflate(R.layout.tab_setting_push, null);
		selectVibraView = view1.findViewById(R.id.selectVibraView);
		shakeVibraView = view1.findViewById(R.id.shakeVibraView);
		selectImg = (ImageView) view1.findViewById(R.id.selectImg);
		vibraImg = (ImageView) view1.findViewById(R.id.vibraImg);
		winPushView = view2.findViewById(R.id.winPushView);
		deadTimeView = view2.findViewById(R.id.deadTimeView);
		deadTimeImg = (ImageView) view2.findViewById(R.id.deadTimeImg);
		winImg = (ImageView) view2.findViewById(R.id.winImg);
		selectVibraView.setOnClickListener(this);
		shakeVibraView.setOnClickListener(this);
		winPushView.setOnClickListener(this);
		deadTimeView.setOnClickListener(this);
		selectText = (TextView) findViewById(R.id.selectText);
		pushText = (TextView) findViewById(R.id.pushText);
		selectHeadImg = (ImageView) findViewById(R.id.selectHeadImg);
		selectHeadImg.setOnClickListener(this);
		pushHeadImg = (ImageView) findViewById(R.id.pushHeadImg);
		pushHeadImg.setOnClickListener(this);
		// 将布局放入集合
		final ArrayList<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		PagerAdapter adapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return views.size();
			}

			@Override
			public void destroyItem(View container, int position, Object object) {
				((ViewPager) container).removeView(views.get(position));
			}

			@Override
			public Object instantiateItem(View container, int position) {
				((ViewPager) container).addView(views.get(position));
				return views.get(position);
			}
		};
		paperSetting.setAdapter(adapter);
		if(sutil.getSelectVibrat()){
			selectImg.setImageResource(R.drawable.chuan_selected);
		}else{
			selectImg.setImageResource(R.drawable.chuan_normal);
		}
		if(sutil.getShakeVibrat()){
			vibraImg.setImageResource(R.drawable.chuan_selected);
		}else{
			vibraImg.setImageResource(R.drawable.chuan_normal);
		}
	}

	class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// 子类必须重写此方法,接受数据
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == Constant.PAGE_CHANGED) {
				if (msg.arg1 == 0) {
					paperSetting.setCurrentItem(0, true);
					selectText.setTextColor(whiteText);
					pushText.setTextColor(redText);
					selectHeadImg.setBackgroundResource(R.drawable.sele_btn);
					pushHeadImg.setBackgroundResource(R.drawable.nor_btn);
				} else {
					paperSetting.setCurrentItem(1, true);
					selectText.setTextColor(redText);
					pushText.setTextColor(whiteText);
					selectHeadImg.setBackgroundResource(R.drawable.nor_btn);
					pushHeadImg.setBackgroundResource(R.drawable.sele_btn);
				}
			}
		}
	}
}
