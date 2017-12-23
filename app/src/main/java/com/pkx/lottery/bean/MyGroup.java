package com.pkx.lottery.bean;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

public class MyGroup extends ViewGroup{

	private Scroller mScroller;
	private float mOriMotionX;
	private float mLastMotionX;
	private VelocityTracker mVelocityTracker;
	private int mTouchState = TOUCH_STATE_REST;
	private static final int TOUCH_STATE_REST = 0;
	private int mTouchSlop;
	private int mMaximumVelocity;
	private static final int TOUCH_STATE_SCROLLING = 1;
	private float mLastDownX;
	private static final int DEFAULT_VALUE = 1000;
	private int mNextScreen = -1;
	private static final int SNAP_VELOCITY = 700;

	
	private int mCurrentScreen;
	public MyGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		initWorkspace();
	}
	
	private void initWorkspace() {
		mScroller = new Scroller(getContext());
		setCurrentScreen(0);

		final ViewConfiguration configuration = ViewConfiguration
				.get(getContext());
		mTouchSlop = configuration.getScaledTouchSlop();//这个是定义控件在scroll的最小像素距离
		mMaximumVelocity = configuration.getScaledMaximumFlingVelocity(); //速率，fling的一个以每秒滑动多少像素的值
	}
	

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		int paddingleft = 0;
		int paddingTop = 0;
		int childLeft = paddingleft;
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);
			if (child.getVisibility() != View.GONE) {
				final int childWidth = child.getMeasuredWidth();
				final int childHeight = child.getMeasuredHeight() ;

				child.layout(childLeft, paddingTop, childLeft + childWidth,
						childHeight + paddingTop);
				childLeft += child.getMeasuredWidth();
			}
		}
		
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		int childCount = getChildCount();
		if (childCount == 0) {
			return;
		}


		boolean restore = false;
		int restoreCount = 0;

		final long drawingTime = getDrawingTime();
		final float scrollPos = (float) getScrollX() / getWidth();
		final int leftScreen = (int) scrollPos;
		final int rightScreen = leftScreen + 1;
		if (leftScreen >= 0 && leftScreen < childCount) {
			drawChild(canvas, getChildAt(leftScreen), drawingTime);
		}
		if  (rightScreen < getChildCount()) {
			drawChild(canvas, getChildAt(rightScreen), drawingTime);
		}

		if (restore) {
			canvas.restoreToCount(restoreCount);
		}

	}
	
	
	
	/**
	 * 调用startScroll()是不会有滚动效果的，只有在computeScroll()获取滚动情况，做出滚动的响应
	 * computeScroll：主要功能是计算拖动的位移量、更新背景、设置要显示的屏幕
	 */
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		} else if (mNextScreen != -1) {
			setCurrentScreen(Math.max(0,
					Math.min(mNextScreen, getChildCount() - 1)));
			mNextScreen = -1;

//			if (mListener != null) {
//				mListener.onViewChanged(mCurrentScreen);
//			}

		}
	}
	void setCurrentScreen(int index) {
		mCurrentScreen = index;
		resetVisibilityForChildren();
	}
	
	private void resetVisibilityForChildren() {
	    int count = getChildCount();
	    for (int i = 0; i < count; i++) {
	        View child = getChildAt(i);
	        if (Math.abs(mCurrentScreen - i) <= 0) {
	            child.setVisibility(View.VISIBLE);
	        } else {
	            child.setVisibility(View.INVISIBLE);
	        }
	    }
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}

	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		final int action = ev.getAction();
		//如果为move事件,mTouchState为TOUCH_STATE_REST为静止状态，这个是防止子控件在滑动时又用手指去滑，这种情况下不响应这个事件
		if ((action == MotionEvent.ACTION_MOVE)
				&& (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}

		final float x = ev.getX();

		switch (action) {
			case MotionEvent.ACTION_MOVE:
				final int xDiff = (int) Math.abs(x - mLastMotionX);
				final int touchSlop = mTouchSlop;
				boolean xMoved = xDiff > touchSlop;
				//如果xMoved为true表示手指在滑动
				if (xMoved) {
					mTouchState = TOUCH_STATE_SCROLLING;
				}
				break;
			case MotionEvent.ACTION_DOWN:
				mLastMotionX = x;
				//mScroller.isFinished() 为true表示滑动结束了，这时候我们把状态置为TOUCH_STATE_REST
				mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST
						: TOUCH_STATE_SCROLLING;
				break;

			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				mTouchState = TOUCH_STATE_REST;
				break;
			default:
				break;
			}
		
		//如果不是在静止状态，都返回true,这样事件就不会传递给onTouchEvent了
		return mTouchState != TOUCH_STATE_REST;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(ev);

		int mScrollX = this.getScrollX(); //mScrollX表示X轴上的距离，往左滑动为正，这个时候屏幕向右移动

		final int action = ev.getAction();
		final float x = ev.getX();
		final float y = ev.getY();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				mOriMotionX = x;
				mLastMotionX = x;
				if (!mScroller.isFinished()) {
					mScroller.abortAnimation();
				}
				mOriMotionX = x;
				mLastMotionX = x;
				mLastDownX = x;
				return true;
			case MotionEvent.ACTION_MOVE:
				System.out.println("====action move mScrollX="+mScrollX);
				final int buffer = getWidth() / 2; //这个表示在第一页或是最后一页还可以滑动半个屏幕
				//如果是往后滑动，屏幕向前，那么mLastMotionX是比x大的，deltaX是正的
				int deltaX = (int) (mLastMotionX - x);
				mLastMotionX = x;
				System.out.println("=====deltaX="+deltaX);
				//deltaX<0表示往前滑动
				if (deltaX < 0) {
					//这个是往右滑动，屏幕向左移动
					scrollBy(Math.max(-mScrollX - buffer, deltaX), 0);
				}else{
					int availableToScroll = 0;
					if (getChildCount() > 0) { //此时Workspace上可能未加任何item，count == 0
						System.out.println("====rihgt="+(getChildAt(
			                            getChildCount() - 1).getRight())+"avail="+(getChildAt(
			                            getChildCount() - 1).getRight()- mScrollX - getWidth()
			                            ));
						//getChildAt(getChildCount() - 1).getRight()为所有的view加一起的宽度，这里加了3个view,一个view为1080,则这个值为3240
					    availableToScroll = getChildAt(
			                            getChildCount() - 1).getRight()
			                            - mScrollX - getWidth();
					    //availableToScroll + buffer可以滑动的最大距离,deltax为滑动的距离
					    scrollBy(Math.min(availableToScroll + buffer, deltaX), 0);
					}
				}
				
			return true;
			case MotionEvent.ACTION_UP:
				final VelocityTracker velocityTracker = mVelocityTracker;

				velocityTracker.computeCurrentVelocity(DEFAULT_VALUE,
						mMaximumVelocity);
				int velocityX = (int) velocityTracker.getXVelocity();
				//velocityX为手指滑动的速率，我们会跟给定值SNAP_VELOCITY做比较
				if (velocityX > SNAP_VELOCITY && mCurrentScreen > 0) {
					//  这个时候是手指往前滑动，屏幕是向后移动
					snapToScreen(mCurrentScreen - 1);
				} else if (velocityX < -SNAP_VELOCITY
						&& mCurrentScreen < getChildCount() - 1) {
					//  move right
					snapToScreen(mCurrentScreen + 1);
				} else {
					snapToDestination(mLastMotionX < mOriMotionX);
				}
				if (mVelocityTracker != null) {
					mVelocityTracker.recycle();
					mVelocityTracker = null;
				}
				mTouchState = TOUCH_STATE_REST;
				if (Math.abs(mLastDownX - x) > 10) {
					return true;
				}
				return false;
			case MotionEvent.ACTION_CANCEL:
				mTouchState = TOUCH_STATE_REST;
				return false;
			default:
				break;
			}

		return true;
	}
	
	
	/**滑动的距离，离屏宽几分之一时，就开始执行换屏动作。*/
	/**
	 * snapToDestination.
	 * mLastMotionX < mOriMotionX (mLastMotion < mOriMotionX)表示这个是手向后滑动，但屏幕是往前的，反之是向前
	 * forward为true为往前划动,这时将scrollX加上三分之二的屏幕的宽度
	 * scrollX / screenWidth 来决定当前在哪个屏幕
	 * @param forward 是前进还是后退.
	 */
	public void snapToDestination(boolean forward) {
		final int screenWidth = getWidth();
		int scrollX = getScrollX();

		if (forward) {
		    scrollX += screenWidth - screenWidth / 3;
		} else { 
		    scrollX += screenWidth / 3;
		}
		System.out.println("======screenWidth="+screenWidth+"scrollX / screenWidth="+(scrollX / screenWidth));
		snapToScreen(scrollX / screenWidth);
	}
	
	/**
	 * 如果计算要滑动的距离:(whichScreen * getWidth())为滑动后的X坐标,this.getScrollX()为当前的坐标，两者相减为滑动的距离
	 * Math.abs(delta) * 2为滑动的持续时间
	 */
	public void snapToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		boolean changingScreens = whichScreen != mCurrentScreen;

		mNextScreen = whichScreen;
		int mScrollX = this.getScrollX();
		final int newX = whichScreen * getWidth();
		final int delta = newX - mScrollX;
		System.out.println("====snapToScreen delta="+delta);
		mScroller.startScroll(mScrollX, 0, delta, 0, Math.abs(delta) * 2);
		//invalidate非常重要，不然你移动一点页面不能回复原状
		invalidate();
	}
}
