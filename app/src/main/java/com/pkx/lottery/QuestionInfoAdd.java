package com.pkx.lottery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.AddQuestionAuth;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.dto.UserInfoAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

import org.json.JSONObject;

public class QuestionInfoAdd extends Activity implements OnClickListener {
	private TextView autoQuestion;
	private EditText handlyQuestion, answer;
	private SharePreferenceUtil sutil;
	private InputMethodManager imm;
	private LayoutInflater inflater;
	private View submitView, autoView, handicAddView, clickView1, clickView2,
			clickView3;
	private MyHandler mHandler;
	private AlertDialog autoQuestionDialog;
	private static final String[] autoQs = { "手动添加密保问题", "您的工号是？", "您的出生地是？",
			"您的大学学号是？", "您父亲的姓名是？", "您父亲的生日是？", "您母亲的生日是？", "您配偶的姓名是？",
			"您小学的学校名称是？", "您中学的学校名称是？", "您高中班主任的名字是？", "您大学辅导员的名字是？" };

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_info);
		initviews();
	}

	@Override
	public void onClick(View v) {
		if (!(handlyQuestion.isSelected() || answer.isSelected())) {
			imm.hideSoftInputFromWindow(answer.getWindowToken(), 0);
		}
		switch (v.getId()) {
		case R.id.submitView:
			if (answer.getText() == null || answer.getText().toString() == null
					|| answer.getText().toString().length() == 0) {
				Toast.makeText(this, "请输入密保答案", Toast.LENGTH_SHORT).show();
				return;
			}
			String question,
			answers;
			if (handicAddView.getVisibility() == View.VISIBLE) {
				// 手动添加
				if (handlyQuestion.getText() == null
						|| handlyQuestion.getText().toString().trim().length() < 6) {
					Toast.makeText(this, "提问不得少于六个字?", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				if (answer.getText() == null
						|| answer.getText().toString().trim().length() < 3) {
					Toast.makeText(this, "答案不得少于三个字?", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				question = handlyQuestion.getText().toString().trim();
			} else {
				// 自动问题
				if (answer.getText() == null
						|| answer.getText().toString().trim().length() < 3) {
					Toast.makeText(this, "答案不得少于三个字?", Toast.LENGTH_SHORT)
							.show();
					return;
				}
				question = autoQuestion.getText().toString().trim();
			}
			answers = answer.getText().toString();
			AddQuestionAuth addu = new AddQuestionAuth(sutil.getuid(),
					question, answers);
			String mingwen = Net.gson.toJson(addu);
			String miwen = MDUtils.MDEncode(sutil.getuserKEY(), mingwen);
			PublicAllAuth pau = new PublicAllAuth("changeSecretSecurity", miwen);
			String allMingwen = Net.gson.toJson(pau);
			String allmiwen = MDUtils
					.MDEncode(sutil.getdeviceKEY(), allMingwen);
			RequestParams params = new RequestParams();
			params.put("SID", sutil.getSID());
			params.put("SN", sutil.getSN());
			params.put("DATA", allmiwen);
			Log.e("pkx", "明文："+mingwen+" all明文"+allMingwen);
			Net.post(true, this, Constant.POST_URL+"/user.api.php", params,
					mHandler, Constant.NET_ACTION_SECRET_SECURE);
			break;
		case R.id.autoView:
			alertAutoQuestion();
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!(handlyQuestion.isSelected() || answer.isSelected())) {
			imm.hideSoftInputFromWindow(answer.getWindowToken(), 0);
		}
		return super.onTouchEvent(event);
	}

	private void initviews() {
		sutil = new SharePreferenceUtil(this);
		mHandler = new MyHandler();
		inflater = getLayoutInflater();
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		autoQuestion = (TextView) findViewById(R.id.autoQuestion);
		handlyQuestion = (EditText) findViewById(R.id.handlyQuestion);
		answer = (EditText) findViewById(R.id.answer);
		submitView = findViewById(R.id.submitView);
		autoView = findViewById(R.id.autoView);
		submitView.setOnClickListener(this);
		handicAddView = findViewById(R.id.handicAddView);
		autoView.setOnClickListener(this);
		clickView1 = findViewById(R.id.clickView1);
		clickView1.setOnClickListener(this);
		clickView2 = findViewById(R.id.clickView2);
		clickView2.setOnClickListener(this);
		clickView3 = findViewById(R.id.clickView3);
		clickView3.setOnClickListener(this);
		autoQuestionDialog = new AlertDialog.Builder(this).create();

	}

	private void alertAutoQuestion() {
		autoQuestionDialog.show();
		autoQuestionDialog.setContentView(R.layout.auto_question_dialog);
		ListView list = (ListView) autoQuestionDialog
				.findViewById(R.id.followList);
		BaseAdapter adapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(
							R.layout.auto_question_list_item, null);
				}
				TextView text = (TextView) convertView
						.findViewById(R.id.number);
				if (position == 0) {
					text.setText(autoQs[position]);
				} else {
					text.setText("问题" + String.valueOf(position) + ":"
							+ autoQs[position]);
				}

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
				return autoQs.length;
			}
		};
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				if (position == 0) {
					handicAddView.setVisibility(View.VISIBLE);
					autoQuestion.setText("请选择密保问题");
				} else {
					handicAddView.setVisibility(View.GONE);
					autoQuestion.setText(autoQs[position]);
				}
				autoQuestionDialog.dismiss();
			}
		});
		list.setSelected(true);
		list.setDividerHeight(1);
		list.setAdapter(adapter);
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
				if(msg.arg1==Constant.NET_ACTION_SECRET_SECURE){
					Toast.makeText(QuestionInfoAdd.this, "密保创建成功",
							Toast.LENGTH_LONG).show();
					UserInfoAuth ua=new UserInfoAuth(sutil.getuid());
					String mingwen=Net.gson.toJson(ua);
					String miwen=MDUtils.MDEncode(sutil.getuserKEY(), mingwen);
					PublicAllAuth paa=new PublicAllAuth("getSecretSecurity", miwen);
					String allmingwen=Net.gson.toJson(paa);
					String allmiwen=MDUtils.MDEncode(sutil.getdeviceKEY(), allmingwen);
					RequestParams params=new RequestParams();
					params.put("SID", sutil.getSID());
					params.put("SN", sutil.getSN());
					params.put("DATA", allmiwen);
					Net.post(true, QuestionInfoAdd.this, Constant.POST_URL+"/user.api.php", params, mHandler, Constant.NET_ACTION_NOTICE_FRESH);
				}else if(msg.arg1==Constant.NET_ACTION_NOTICE_FRESH){
					JSONObject jo=(JSONObject) msg.obj;
					Intent toSecretList=new Intent(QuestionInfoAdd.this, QuestionInfo.class);
					toSecretList.putExtra("list", jo.toString());
					startActivity(toSecretList);
					finish();
				}
				
//				Intent toList=new Intent(QuestionInfoAdd.this, QuestionInfo.class);
//				toList.putExtra("isAddFinish", true);
//				startActivity(toList);
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				Toast.makeText(QuestionInfoAdd.this, "提交失败", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

}
