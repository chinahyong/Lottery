package com.pkx.lottery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ComingSoon extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coming_soon);
	}
	public void clickBack(View view) {
	super.onBackPressed();
}
}
