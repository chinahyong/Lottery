package com.pkx.lottery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.DelQuestionAuth;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.dto.QuestionAuth;
import com.pkx.lottery.dto.QuestionListAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

public class QuestionInfo extends Activity implements OnClickListener {
	private InputMethodManager imm;
	private Intent mIntent;
	private ListView questionList;
	private BaseAdapter adapter;
	private SharePreferenceUtil sutil;
	private LayoutInflater inflater;
	private QuestionListAuth listAuth;
	private int deleteIndex;
	private MyHandler mHandler;
	private View addQuestion;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.question_info);
		sutil = new SharePreferenceUtil(this);
		mIntent = getIntent();
//		if (mIntent.getBooleanExtra("isAddFinish", false)) {
//			UserInfoAuth ua = new UserInfoAuth(sutil.getuid());
//			String mingwen = Net.gson.toJson(ua);
//			String miwen = MDUtils.MDEncode(sutil.getuserKEY(), mingwen);
//			PublicAllAuth paa = new PublicAllAuth("getSecretSecurity", miwen);
//			String allmingwen = Net.gson.toJson(paa);
//			String allmiwen = MDUtils
//					.MDEncode(sutil.getdeviceKEY(), allmingwen);
//			RequestParams params = new RequestParams();
//			params.put("SID", sutil.getSID());
//			params.put("DATA", allmiwen);
//			Net.post(true, this, "http://api.60888.la/user.api.php", params,
//					mHandler, Constant.NET_ACTION_NOTICE_FRESH);
//		} else {
			if (mIntent.getStringExtra("list") != null
					&& mIntent.getStringExtra("list").length() > 0) {
				listAuth = Net.gson.fromJson(mIntent.getStringExtra("list"),
						QuestionListAuth.class);
			}
			for (QuestionAuth a : listAuth.getSecret_security()) {
				Log.e("pkx",
						" " + a.getSecret_security() + "  " + a.getAnswer()
								+ "  " + a.getId());
			}
//		}
		initviews();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addQuestion:
			Intent toAdd = new Intent(this, QuestionInfoAdd.class);
			startActivity(toAdd);
			finish();
			break;

		default:
			break;
		}
	}

	private void initviews() {
		inflater = getLayoutInflater();
		mHandler = new MyHandler();
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		addQuestion = findViewById(R.id.addQuestion);
		addQuestion.setOnClickListener(this);
		questionList = (ListView) findViewById(R.id.questionList);
		questionList.setDividerHeight(1);
		adapter = new BaseAdapter() {

			@Override
			public View getView(final int position, View convertView,
					ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(R.layout.question_list_item,
							null);
				}
				TextView question = (TextView) convertView
						.findViewById(R.id.number);
				if(listAuth!=null){
					question.setText("密保问题"
							+ String.valueOf(position + 1)
							+ ":"
							+ listAuth.getSecret_security().get(position)
									.getSecret_security());
				}
				ImageView delete = (ImageView) convertView
						.findViewById(R.id.delete);
				delete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Message msg = new Message();
						msg.what = Constant.DELETE_LIST_ITEM;
						msg.arg1 = position;
						deleteIndex = position;
						mHandler.sendMessage(msg);
					}
				});
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				return 0;
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public int getCount() {
				if(listAuth!=null){
					return listAuth.getSecret_security().size();
				}else{
					return 0;
				}
				
			}
		};
		questionList.setAdapter(adapter);
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
				if (msg.arg1 == Constant.NET_ACTION_SECRET_SECURE) {
					listAuth.getSecret_security().remove(deleteIndex);
					adapter.notifyDataSetChanged();
					Toast.makeText(QuestionInfo.this, "密码修改成功",
							Toast.LENGTH_SHORT).show();
				} 
//				else if (msg.arg1 == Constant.NET_ACTION_NOTICE_FRESH) {
//					org.json.JSONObject jo = (JSONObject) msg.obj;
//					listAuth = Net.gson.fromJson(jo.toString(),
//							QuestionListAuth.class);
//					adapter.notifyDataSetChanged();
//					if(listAuth==null){
//						Log.e("pkx", "listAuth==null");
//					}
//					Log.e("pkx", "NET_ACTION_NOTICE_FRESH");
//				}

			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				Toast.makeText(QuestionInfo.this, "提交失败", Toast.LENGTH_SHORT)
						.show();
			} else if (msg.what == Constant.DELETE_LIST_ITEM) {
				DelQuestionAuth addu = new DelQuestionAuth(
						sutil.getuid(),
						listAuth.getSecret_security().get(msg.arg1)
								.getSecret_security(),
						listAuth.getSecret_security().get(msg.arg1).getAnswer(),
						listAuth.getSecret_security().get(msg.arg1).getId());
				String mingwen = Net.gson.toJson(addu);
				String miwen = MDUtils.MDEncode(sutil.getuserKEY(), mingwen);
				PublicAllAuth pau = new PublicAllAuth("changeSecretSecurity",
						miwen);
				String allMingwen = Net.gson.toJson(pau);
				String allmiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allMingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", allmiwen);
				Log.e("pkx", "明文：" + mingwen + " all明文" + allMingwen);
				Net.post(true, QuestionInfo.this,Constant.POST_URL+
						"/user.api.php", params, mHandler,
						Constant.NET_ACTION_SECRET_SECURE);
			}
		}
	}

}
