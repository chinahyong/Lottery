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

public class ShowDimention extends Activity {
	// private XListView showList;
	private PullToRefreshListView showPullList;
	private MyHandler mHandler;
	private int page = 1, selection;
	private BaseAdapter showListAdapter;
	private LayoutInflater inflater;
	private SharePreferenceUtil sutil;
	private ArrayList<ChromShowBet> chromShows;
	private TextView typeName;
	ArrayList<ChormLott> lotts;
	private View buyNow;
	private boolean isFromSelectPage;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		lotts = new ArrayList<ChormLott>();
		setContentView(R.layout.chromsphere_pulllist_show);
		initviews();
		// 测试往期数据
		PeroidRes res = new PeroidRes("getPeroidRes", "3", "30", "1");
		String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
				Net.gson.toJson(res));
		RequestParams params = new RequestParams();
		params.put("SID", sutil.getSID());
		params.put("SN", sutil.getSN());
		params.put("DATA", miwen);
		Net.post(true, ShowDimention.this, Constant.POST_URL + "/data.api.php",
				params, mHandler, Constant.NET_ACTION_LOTHIS);
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
		sutil = new SharePreferenceUtil(this);
		inflater = getLayoutInflater();
		mHandler = new MyHandler();

		buyNow = findViewById(R.id.buyNow);
		buyNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(ShowDimention.this,
						ThreeDimension.class));
			}
		});
		showListAdapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(
							R.layout.showdimention_list_item, null);
				}
				TextView ballText1 = (TextView) convertView
						.findViewById(R.id.balltext1);
				TextView ballText2 = (TextView) convertView
						.findViewById(R.id.balltext2);
				TextView ballText3 = (TextView) convertView
						.findViewById(R.id.balltext3);
				TextView name = (TextView) convertView.findViewById(R.id.name);
				TextView time = (TextView) convertView.findViewById(R.id.time);

				if (lotts.get(position).getBalls().size() == 3) {
					ballText1.setText(String.valueOf(lotts.get(position)
							.getBalls().get(0)));
					ballText2.setText(String.valueOf(lotts.get(position)
							.getBalls().get(1)));
					ballText3.setText(String.valueOf(lotts.get(position)
							.getBalls().get(2)));
				} else {
					ballText1.setText("-");
					ballText2.setText("-");
					ballText3.setText("-");
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
				return lotts.size();
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
				PeroidRes res = new PeroidRes("getPeroidRes", "3", "30", String
						.valueOf(1));
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						Net.gson.toJson(res));
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", miwen);
				Net.post(false, ShowDimention.this, Constant.POST_URL
						+ "/data.api.php", params, mHandler,
						Constant.NET_ACTION_REFRESH);

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				Log.e("pkx", "exhibitionRefreshView-----onPullUpToRefresh");
				PeroidRes res = new PeroidRes("getPeroidRes", "3", "30", String
						.valueOf(page + 1));
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						Net.gson.toJson(res));
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", miwen);
				Net.post(false, ShowDimention.this, Constant.POST_URL
						+ "/data.api.php", params, mHandler,
						Constant.NET_ACTION_LOADMORE);
				selection = lotts.size() - 1;

			}
		});
		// showList = (XListView) findViewById(R.id.showList);
		typeName = (TextView) findViewById(R.id.typeName);
		typeName.setText("开奖历史-3D");
		// showList.setOnScrollListener(new OnScrollListener() {
		//
		// @Override
		// public void onScrollStateChanged(AbsListView view, int scrollState) {
		// if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
		// selection = showList.getFirstVisiblePosition();
		// Log.e("pkx",
		// "positon->" + showList.getFirstVisiblePosition()
		// + "---->" + selection);
		// }
		// }
		//
		// @Override
		// public void onScroll(AbsListView view, int firstVisibleItem,
		// int visibleItemCount, int totalItemCount) {
		//
		// }
		// });
		// showList.setAdapter(showListAdapter);
		// IXListViewListener coperListener = new IXListViewListener() {
		//
		// @Override
		// public void onRefresh() {
		// PeroidRes res = new PeroidRes("getPeroidRes", "3", "30",
		// String.valueOf(1));
		// String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
		// Net.gson.toJson(res));
		// RequestParams params = new RequestParams();
		// params.put("SID", sutil.getSID());
		// params.put("SN", sutil.getSN());
		// params.put("DATA", miwen);
		// Net.post(false, ShowDimention.this,
		// Constant.POST_URL+"/data.api.php", params, mHandler,
		// Constant.NET_ACTION_REFRESH);
		//
		// }
		//
		// @Override
		// public void onLoadMore() {
		// PeroidRes res = new PeroidRes("getPeroidRes", "3", "30",
		// String.valueOf(page + 1));
		// String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
		// Net.gson.toJson(res));
		// RequestParams params = new RequestParams();
		// params.put("SID", sutil.getSID());
		// params.put("SN", sutil.getSN());
		// params.put("DATA", miwen);
		// Net.post(false, ShowDimention.this,
		// Constant.POST_URL+"/data.api.php", params, mHandler,
		// Constant.NET_ACTION_LOADMORE);
		// selection = lotts.size() - 1;
		// }
		// };
		// showList.setDividerHeight(1);
		showPullList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				if (position == 0)
					return;
				Message msg = new Message();
				msg.arg1 = position - 1;
				msg.what = 60888;
				mHandler.sendMessage(msg);
				// Intent toWebView=new Intent(ShowChromsphere.this,
				// WebviewActivity.class);
				// toWebView.putExtra("weburl",
				// "http://www.cootm.com/cptest/index.html");
				// startActivity(toWebView);
			}

		});
		// showList.setPullLoadEnable(false);
		// showList.setPullRefreshEnable(true);
		// showList.setXListViewListener(coperListener);
	}

	// private void onLoad(XListView x) {
	// x.stopRefresh();
	// x.stopLoadMore();
	// x.setRefreshTime("just fresh");
	// }

	// static class ViewHolder {
	// TextView ballText1;
	// TextView ballText2;
	// TextView ballText3;
	// TextView name;
	// TextView time;
	//
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
				LottDetails ld = new LottDetails("lottery_info", lotts.get(
						msg.arg1).getPeroid_name(), "3");
				String mingwen = Net.gson.toJson(ld);
				Log.e("pkx", "明文" + mingwen);
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", miwen);
				Net.post(true, ShowDimention.this, Constant.POST_URL
						+ "/lottery.api.php", params, mHandler,
						Constant.NET_ACTION_CHORMBET);
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				// showList.setPullLoadEnable(true);
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
							Log.e("pkx", "pull list pid:" + lo.getPeroidID()
									+ " name:" + lo.getPeroid_name());
						}
					} catch (JSONException e) {
						Log.e("pkx", "ja------>JSONException");
						e.printStackTrace();
					}
					showListAdapter.notifyDataSetChanged();
					Log.e("pkx", "往期彩期3d加载成功：" + ojo.toString());
				} else if (msg.arg1 == Constant.NET_ACTION_CHORMBET) {
					JSONObject djo = (JSONObject) msg.obj;
					Log.e("pkx", djo.toString());
					try {
						if ("0".equals(djo.getString("error"))) {
							Log.e("pkx", "toDetails");
							Intent toLottDetails = new Intent(
									ShowDimention.this, ShowLottDetails.class);
							toLottDetails.putExtra("data",
									djo.getString("data"));
							toLottDetails.putExtra("type", 3);
							startActivity(toLottDetails);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				} else if (msg.arg1 == Constant.NET_ACTION_LOADMORE) {
					showPullList.onRefreshComplete();
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
						// showList.stopLoadMore();
						// Toast.makeText(ShowDimention.this, "加载成功",
						// Toast.LENGTH_SHORT).show();
						for (ChormLott c : lotts) {
							Log.e("pkx", " p:" + c.getPeroid_name());
						}
					} catch (JSONException e) {
						Log.e("pkx", "ja------>JSONException");
						e.printStackTrace();
					}

				} else if (msg.arg1 == Constant.NET_ACTION_REFRESH) {
					JSONObject ojo = (JSONObject) msg.obj;
					showPullList.onRefreshComplete();
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
						for (ChormLott c : lotts) {
							Log.e("pkx", " p:" + c.getPeroid_name());
						}
						Toast.makeText(ShowDimention.this, "刷新成功",
								Toast.LENGTH_SHORT).show();
					} catch (JSONException e) {
						Log.e("pkx", "ja------>JSONException");
						e.printStackTrace();
					}
				}
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				showPullList.onRefreshComplete();
				Toast.makeText(ShowDimention.this, "加载失败", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}
