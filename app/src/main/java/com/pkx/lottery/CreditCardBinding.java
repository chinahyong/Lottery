package com.pkx.lottery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.BindCardAuth;
import com.pkx.lottery.dto.CardInfo;
import com.pkx.lottery.dto.EditCardAuth;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

public class CreditCardBinding extends Activity implements OnClickListener {
	private EditText nameText, bankText, cardNumber, cardInfo;
	private View submitView;
	private InputMethodManager imm;
	private SharePreferenceUtil sutil;
	private View allView;
	private MyHandler mHandler;
	private Intent mIntent;
	private TextView text1, text2, text3, text4,submitText,typeName;
	private CardInfo card;
	private boolean binded;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credit_card_binding);
		initViews();
		mIntent=getIntent();
		binded=mIntent.getBooleanExtra("binded", false);
		if(binded){
			submitText.setText("提交");
			typeName.setText("银行卡修改");
			card=Net.gson.fromJson(mIntent.getStringExtra("cardinfo"), CardInfo.class);
			nameText.setText(card.getBank_name());
			bankText.setText(card.getBank_type());
			cardNumber.setText(card.getBank_card());
			cardInfo.setText(card.getOpening_bank());
			Log.e("pkx", card.getBank_bind_id()+"  "+card.getBank_name());
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (nameText.isSelected() || bankText.isSelected()
				|| cardNumber.isSelected() || cardInfo.isSelected()) {
			imm.hideSoftInputFromWindow(cardNumber.getWindowToken(), 0);
		}
		return super.onTouchEvent(event);
	}

	private void initViews() {
		mHandler = new MyHandler();
		sutil = new SharePreferenceUtil(this);
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		nameText = (EditText) findViewById(R.id.nameText);
		bankText = (EditText) findViewById(R.id.bankText);
		cardNumber = (EditText) findViewById(R.id.cardNumber);
		cardInfo = (EditText) findViewById(R.id.cardInfo);
		submitView = findViewById(R.id.submitView);
		submitView.setOnClickListener(this);
		allView = findViewById(R.id.allView);
		allView.setOnClickListener(this);
		submitText = (TextView) findViewById(R.id.submitText);
		typeName = (TextView) findViewById(R.id.typeName);
		text1 = (TextView) findViewById(R.id.text1);
		text1.setOnClickListener(this);
		text2 = (TextView) findViewById(R.id.text2);
		text2.setOnClickListener(this);
		text3 = (TextView) findViewById(R.id.text3);
		text3.setOnClickListener(this);
		text4 = (TextView) findViewById(R.id.text4);
		text4.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if (!(nameText.isSelected() || bankText.isSelected()
				|| cardNumber.isSelected() || cardInfo.isSelected())) {
			imm.hideSoftInputFromWindow(nameText.getWindowToken(), 0);
		}
		switch (v.getId()) {
		case R.id.submitView:
			if (nameText.getText() == null
					&& nameText.getText().toString().trim().length() < 1) {
				Toast.makeText(this, "请正确填写持卡人信息", Toast.LENGTH_SHORT).show();
				return;
			}
			if (bankText.getText() == null
					&& bankText.getText().toString().trim().length() < 1) {
				Toast.makeText(this, "请正确填写开户行信息", Toast.LENGTH_SHORT).show();
				return;
			}
			if (cardNumber.getText() == null
					&& cardNumber.getText().toString().trim().length() < 16) {
				Toast.makeText(this, "请正确填写银行卡信息", Toast.LENGTH_SHORT).show();
				return;
			}
			if (cardInfo.getText() == null
					&& cardInfo.getText().toString().trim().length() < 16) {
				Toast.makeText(this, "请正确填写开户信息", Toast.LENGTH_SHORT).show();
				return;
			}
			if(binded){
//				new EditCardAuth(uid, bankName, cardNumber, userName, bankInfo, bank_bind_id)
				EditCardAuth eca=new EditCardAuth(sutil.getuid(), bankText
						.getText().toString(), cardNumber.getText().toString(),
						nameText.getText().toString(), cardInfo.getText()
								.toString(),card.getBank_bind_id());
				String emingwen=Net.gson.toJson(eca);
				Log.e("pkx", "all明文"+emingwen);
				String emiwen=MDUtils.MDEncode(sutil.getuserKEY(), emingwen);
				PublicAllAuth epaa=new PublicAllAuth("BankCardBinding", emiwen);
				String eallmingwen=Net.gson.toJson(epaa);
				String eallmiwen=MDUtils.MDEncode(sutil.getdeviceKEY(), eallmingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", eallmiwen);
				Net.post(true, this, Constant.POST_URL+"/user.api.php", params,
						mHandler, Constant.NET_ACTION_LOGIN);
			}else{
				BindCardAuth bca = new BindCardAuth(sutil.getuid(), bankText
						.getText().toString(), cardNumber.getText().toString(),
						nameText.getText().toString(), cardInfo.getText()
								.toString());
				String mingwen = Net.gson.toJson(bca, BindCardAuth.class);
				Log.e("pkx", "明文" + mingwen);
				String miwen = MDUtils.MDEncode(sutil.getuserKEY(), mingwen);
				PublicAllAuth paa = new PublicAllAuth("BankCardBinding", miwen);
				String allmingwen = Net.gson.toJson(paa);
				String allmiwen = MDUtils
						.MDEncode(sutil.getdeviceKEY(), allmingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", allmiwen);
				Net.post(true, this, Constant.POST_URL+"/user.api.php", params,
						mHandler, Constant.NET_ACTION_LOGIN);
			}
			break;
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
			if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				if (msg.arg1 == Constant.NET_ACTION_LOGIN) {
					if(binded){
						Toast.makeText(CreditCardBinding.this, "银行卡修改成功",
								Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(CreditCardBinding.this, "银行卡绑定成功",
								Toast.LENGTH_LONG).show();
					}
					finish();
				}
			} else if (msg.what == Constant.POST_FAIL) {
				Toast.makeText(CreditCardBinding.this, "链接超时！",
						Toast.LENGTH_SHORT).show();
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				Toast.makeText(CreditCardBinding.this, "账号名密码错误！",
						Toast.LENGTH_SHORT).show();
			}

		}
	}
}
