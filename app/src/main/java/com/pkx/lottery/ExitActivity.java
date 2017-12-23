package com.pkx.lottery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class ExitActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.exit_dialog);
	}
	public void clickBack(View view) {
	super.onBackPressed();
}
}
