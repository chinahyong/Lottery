package com.pkx.lottery;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.pkx.lottery.bean.ChromShowBet;
import com.pkx.lottery.dto.LottDetails;
import com.pkx.lottery.dto.PeroidRes;
import com.pkx.lottery.dto.lott.ChormLott;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowChromsphere extends Activity {
	// private XListView showList;
	private PullToRefreshListView showPullList;
	private MyHandler mHandler;
	private BaseAdapter showListAdapter;
	private int page = 1, selection;
	private LayoutInflater inflater;
	private ArrayList<ChromShowBet> chromShows;
	private SharePreferenceUtil sutil;
	ArrayList<ChormLott> lotts;
	private View buyNow;
	private boolean isFromSelectPage;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chromsphere_pulllist_show);
		initviews();
		lotts = new ArrayList<ChormLott>();
		// 测试往期数据
		PeroidRes res = new PeroidRes("getPeroidRes", "1", "30", "1");
		String mingwen = Net.gson.toJson(res);
		String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen);
		RequestParams params = new RequestParams();
		params.put("SID", sutil.getSID());
		params.put("SN", sutil.getSN());
		params.put("DATA", miwen);
		// Log.e("pkx", "mingwen:"+mingwen+" miwen:"+miwen);
		Net.post(true, ShowChromsphere.this, Constant.POST_URL
				+ "/data.api.php", params, mHandler, Constant.NET_ACTION_LOTHIS);
		isFromSelectPage = getIntent().getBooleanExtra("isFromSelectPage",
				false);
	}

	@Override
	protected void onResume() {
		if (isFromSelectPage) {
			buyNow.setVisibility(View.GONE);
		}
		super.onResume();
	}

	private void initviews() {
		buyNow = findViewById(R.id.buyNow);
		buyNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(ShowChromsphere.this,
						MyDoubleChromosphere.class));
			}
		});
		sutil = new SharePreferenceUtil(this);
		inflater = getLayoutInflater();
		mHandler = new MyHandler();
		// showList = (XListView) findViewById(R.id.showList);
		showListAdapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(
							R.layout.chromshow_list_item, null);
				}
				TextView name = (TextView) convertView.findViewById(R.id.name);
				TextView time = (TextView) convertView.findViewById(R.id.time);
				if (lotts.get(position).getBalls().size() == 7) {
					TextView balltext1 = (TextView) convertView
							.findViewById(R.id.balltext1);
					TextView balltext2 = (TextView) convertView
							.findViewById(R.id.balltext2);
					TextView balltext3 = (TextView) convertView
							.findViewById(R.id.balltext3);
					TextView balltext4 = (TextView) convertView
							.findViewById(R.id.balltext4);
					TextView balltext5 = (TextView) convertView
							.findViewById(R.id.balltext5);
					TextView balltext6 = (TextView) convertView
							.findViewById(R.id.balltext6);
					TextView balltext7 = (TextView) convertView
							.findViewById(R.id.balltext7);
					balltext1.setText(String.valueOf(lotts.get(position)
							.getBalls().get(0)));
					balltext2.setText(String.valueOf(lotts.get(position)
							.getBalls().get(1)));
					balltext3.setText(String.valueOf(lotts.get(position)
							.getBalls().get(2)));
					balltext4.setText(String.valueOf(lotts.get(position)
							.getBalls().get(3)));
					balltext5.setText(String.valueOf(lotts.get(position)
							.getBalls().get(4)));
					balltext6.setText(String.valueOf(lotts.get(position)
							.getBalls().get(5)));
					balltext7.setText(String.valueOf(lotts.get(position)
							.getBalls().get(6)));
				} else {
					TextView balltext1 = (TextView) convertView
							.findViewById(R.id.balltext1);
					TextView balltext2 = (TextView) convertView
							.findViewById(R.id.balltext2);
					TextView balltext3 = (TextView) convertView
							.findViewById(R.id.balltext3);
					TextView balltext4 = (TextView) convertView
							.findViewById(R.id.balltext4);
					TextView balltext5 = (TextView) convertView
							.findViewById(R.id.balltext5);
					TextView balltext6 = (TextView) convertView
							.findViewById(R.id.balltext6);
					TextView balltext7 = (TextView) convertView
							.findViewById(R.id.balltext7);
					balltext1.setText("-");
					balltext2.setText("-");
					balltext3.setText("-");
					balltext4.setText("-");
					balltext5.setText("-");
					balltext6.setText("-");
					balltext7.setText("-");
				}
				name.setText("第" + lotts.get(position).getPeroid_name() + "期");
				if (lotts.get(position).getRes_date().length() > 10) {
					time.setText(lotts.get(position).getRes_date()
							.substring(0, 10));
				} else {
					time.setText(lotts.get(position).getRes_date());
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
				if (lotts != null && lotts.size() > 0) {
					return lotts.size();
				}
				return 0;
			}
		};
		showPullList = (PullToRefreshListView) findViewById(R.id.showPullList);
		showPullList.setMode(Mode.BOTH);
		showPullList.setAdapter(showListAdapter);
		showPullList.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				Log.e("pkx", "exhibitionRefreshView-----onPullDownToRefresh");
				PeroidRes res = new PeroidRes("getPeroidRes", "1", "30", String
						.valueOf(1));
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						Net.gson.toJson(res));
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", miwen);
				Net.post(false, ShowChromsphere.this, Constant.POST_URL
						+ "/data.api.php", params, mHandler,
						Constant.NET_ACTION_REFRESH);

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				Log.e("pkx", "exhibitionRefreshView-----onPullUpToRefresh");
				PeroidRes res = new PeroidRes("getPeroidRes", "1", "30", String
						.valueOf(page + 1));
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						Net.gson.toJson(res));
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", miwen);
				Net.post(false, ShowChromsphere.this, Constant.POST_URL
						+ "/data.api.php", params, mHandler,
						Constant.NET_ACTION_LOADMORE);
				selection = lotts.size() - 1;

			}
		});
		showPullList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				Message msg = new Message();
				msg.what = 60888;
				msg.arg1 = position - 1;
				mHandler.sendMessage(msg);
				// Intent toWebView=new Intent(ShowChromsphere.this,
				// WebviewActivity.class);
				// toWebView.putExtra("weburl",
				// "http://www.cootm.com/cptest/index.html");
				// startActivity(toWebView);
			}

		});
	}

	// private void onLoad(XListView x) {
	// x.stopRefresh();
	// x.stopLoadMore();
	// x.setRefreshTime("just fresh");
	// }

	class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// 子类必须重写此方法,接受数据
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 60888) {
				if (msg.arg1 < 0)
					return;
				LottDetails ld = new LottDetails("lottery_info", lotts.get(
						msg.arg1).getPeroid_name(), "1");
				String mingwen = Net.gson.toJson(ld);
				Log.e("pkx", "明文" + mingwen);
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", miwen);
				Net.post(true, ShowChromsphere.this, Constant.POST_URL
						+ "/lottery.api.php", params, mHandler,
						Constant.NET_ACTION_CHORMBET);
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				// showList.setPullLoadEnable(true);
				// showList.setAdapter(showListAdapter);
				if (msg.arg1 == Constant.NET_ACTION_LOTHIS) {
					JSONObject ojo = (JSONObject) msg.obj;
					try {
						JSONArray ja = new JSONArray(
								ojo.getString("peroidList"));
						Log.e("pkx", "ja size:" + ja.length());
						for (int i = 0; i < ja.length(); i++) {
							lotts.add(Net.gson.fromJson(ja.get(i).toString(),
									ChormLott.class));
						}
						Log.e("pkx", "lotts size:" + lotts.size());
						for (ChormLott lo : lotts) {
							Log.e("pkx", "pid:" + lo.getPeroidID() + " name:"
									+ lo.getPeroid_name());
						}
					} catch (JSONException e) {
						Log.e("pkx", "ja------>JSONException");
						e.printStackTrace();
					}
					showListAdapter.notifyDataSetChanged();
					Log.e("pkx", "往期彩期加载成功：" + ojo.toString());
				} else if (msg.arg1 == Constant.NET_ACTION_CHORMBET) {
					JSONObject djo = (JSONObject) msg.obj;
					Log.e("pkx", djo.toString());
					try {
						if ("0".equals(djo.getString("error"))) {
							Intent toLottDetails = new Intent(
									ShowChromsphere.this, ShowLottDetails.class);
							toLottDetails.putExtra("type", 1);
							toLottDetails.putExtra("data",
									djo.getString("data"));
							startActivity(toLottDetails);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else if (msg.arg1 == Constant.NET_ACTION_LOADMORE) {

					JSONObject ojo = (JSONObject) msg.obj;
					try {
						JSONArray ja = new JSONArray(
								ojo.getString("peroidList"));
						Log.e("pkx", "ja size:" + ja.length());
						for (int i = 0; i < ja.length(); i++) {
							lotts.add(Net.gson.fromJson(ja.get(i).toString(),
									ChormLott.class));
						}
						// showList.setSelection(selection);
						showListAdapter.notifyDataSetChanged();
						Log.e("pkx", "lotts size:" + lotts.size());
						for (ChormLott lo : lotts) {
							Log.e("pkx", "pid:" + lo.getPeroidID() + " name:"
									+ lo.getPeroid_name());
						}
						page++;
						showListAdapter.notifyDataSetChanged();
						showPullList.onRefreshComplete();
						// showList.stopLoadMore();
						// Toast.makeText(ShowChromsphere.this, "加载成功",
						// Toast.LENGTH_SHORT).show();
					} catch (JSONException e) {
						Log.e("pkx", "ja------>JSONException");
						e.printStackTrace();
					}

				} else if (msg.arg1 == Constant.NET_ACTION_REFRESH) {
					JSONObject ojo = (JSONObject) msg.obj;
					try {
						JSONArray ja = new JSONArray(
								ojo.getString("peroidList"));
						Log.e("pkx", "ja size:" + ja.length());
						lotts.clear();
						for (int i = 0; i < ja.length(); i++) {
							lotts.add(Net.gson.fromJson(ja.get(i).toString(),
									ChormLott.class));
						}
						showListAdapter.notifyDataSetChanged();
						Log.e("pkx", "lotts size:" + lotts.size());
						for (ChormLott lo : lotts) {
							Log.e("pkx", "pid:" + lo.getPeroidID() + " name:"
									+ lo.getPeroid_name());
						}
						page = 1;
						// showList.stopRefresh();
						showListAdapter.notifyDataSetChanged();
						showPullList.onRefreshComplete();
						Toast.makeText(ShowChromsphere.this, "刷新成功",
								Toast.LENGTH_SHORT).show();
					} catch (JSONException e) {
						Log.e("pkx", "ja------>JSONException");
						e.printStackTrace();
					}
				}
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				showPullList.onRefreshComplete();
				Toast.makeText(ShowChromsphere.this, "加载失败", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}
