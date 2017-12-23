package com.pkx.lottery.bean;

import android.app.Dialog;
import android.content.Context;

public class MyAlertDialog extends Dialog {

	public MyAlertDialog(Context context, int theme) {
		super(context, theme);
	}

	public MyAlertDialog(Context context) {
		super(context);
	}

	protected MyAlertDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}
	@Override
	public void dismiss() {
		
		super.dismiss();
	}
//	public interface (){
//		public void onDis();
//	}

}
