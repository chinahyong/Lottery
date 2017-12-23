package com.pkx.lottery.view;

import android.content.Context;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;

public class HomeAdsViewPager extends ViewPager implements OnGestureListener {

	private GestureDetector mDetector;
//	private Message msg;

	public HomeAdsViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		GestureDetector detector = new GestureDetector(context, this);
		mDetector = detector;
//		msg=new Message();
//		msg.what=1102;
	}

	public GestureDetector getGestureDetector() {
		return mDetector;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		Log.e("json", "onSingleTapUp");
		if (listener != null) {
			listener.setOnSimpleClickListenr(getCurrentItem());
		}
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	private onSimpleClickListener listener;

	public interface onSimpleClickListener {
		void setOnSimpleClickListenr(int position);
	}

	public void setOnSimpleClickListener(onSimpleClickListener listener) {
		this.listener = listener;
	}

}
