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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
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

public class ShowFast extends Activity {
	// private XListView showList;
	private PullToRefreshListView showPullList;
	private MyHandler mHandler;
	private int page = 1, selection;
	private BaseAdapter showListAdapter;
	private LayoutInflater inflater;
	// private ArrayList<ChromShowBet> chromShows;
	private TextView typeName;
	private SharePreferenceUtil sutil;
	static final ArrayList<Integer> fastLogos = new ArrayList<Integer>();
	ArrayList<ChormLott> lotts;
	private String lotterytypeStr = "4";
	private View buyNow;

	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chromsphere_pulllist_show);
		lotts = new ArrayList<ChormLott>();
		if (fastLogos.size() > 0) {
			fastLogos.clear();
		}
		fastLogos.add(R.drawable.num_1);
		fastLogos.add(R.drawable.num_2);
		fastLogos.add(R.drawable.num_3);
		fastLogos.add(R.drawable.num_4);
		fastLogos.add(R.drawable.num_5);
		fastLogos.add(R.drawable.num_6);
		initviews();
		// 测试往期数据
		PeroidRes res = new PeroidRes("getPeroidRes", lotterytypeStr, "30", "1");
		String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
				Net.gson.toJson(res));
		RequestParams params = new RequestParams();
		params.put("SID", sutil.getSID());
		params.put("SN", sutil.getSN());
		params.put("DATA", miwen);
		Net.post(true, ShowFast.this, Constant.POST_URL + "/data.api.php",
				params, mHandler, Constant.NET_ACTION_LOTHIS);
	}

	private void initviews() {
		sutil = new SharePreferenceUtil(this);
		inflater = getLayoutInflater();
		mHandler = new MyHandler();
		typeName = (TextView) findViewById(R.id.typeName);
		final Intent toFast = new Intent(ShowFast.this,
				FastDimention.class);
		if (getIntent().getBooleanExtra("isJiangsuType", false)) {
			typeName.setText("开奖历史-江苏快三");
			lotterytypeStr = "5";
			toFast.putExtra("type", 1);
		} else {
			typeName.setText("开奖历史-湖北快三");
			toFast.putExtra("type", 0);

		}
		buyNow = findViewById(R.id.buyNow);
		buyNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(toFast);
			}
		});
		showListAdapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder viewholder = null;
				if (convertView == null) {
					viewholder = new ViewHolder();
					convertView = inflater.inflate(R.layout.fastshow_list_item,
							null);
					viewholder.ballText1 = (ImageView) convertView
							.findViewById(R.id.img1);
					viewholder.ballText2 = (ImageView) convertView
							.findViewById(R.id.img2);
					viewholder.ballText3 = (ImageView) convertView
							.findViewById(R.id.img3);
					viewholder.name = (TextView) convertView
							.findViewById(R.id.name);
					viewholder.time = (TextView) convertView
							.findViewById(R.id.time);
					viewholder.sum = (TextView) convertView
							.findViewById(R.id.sum);
					convertView.setTag(viewholder);
				} else {
					viewholder = (ViewHolder) convertView.getTag();
				}
				if (viewholder != null) {
					if (lotts.get(position).getBalls().size() == 3
							&& lotts.get(position).getBalls().get(0) != 0
							&& lotts.get(position).getBalls().get(1) != 0
							&& lotts.get(position).getBalls().get(2) != 0) {
						viewholder.ballText1
								.setImageResource(fastLogos.get(lotts
										.get(position).getBalls().get(0) - 1));
						viewholder.ballText2
								.setImageResource(fastLogos.get(lotts
										.get(position).getBalls().get(1) - 1));
						viewholder.ballText3
								.setImageResource(fastLogos.get(lotts
										.get(position).getBalls().get(2) - 1));
						viewholder.sum
								.setText("和值"
										+ String.valueOf(lotts.get(position)
												.getBalls().get(0)
												+ lotts.get(position)
														.getBalls().get(1)
												+ lotts.get(position)
														.getBalls().get(2)));
						if (lotts.get(position).getPeroid_name() != null)
							viewholder.name.setText("第"
									+ lotts.get(position).getPeroid_name()
									+ "期");
						if (lotts.get(position).getRes_date() != null
								&& lotts.get(position).getRes_date().length() > 10) {
							viewholder.time.setText(lotts.get(position)
									.getRes_date().substring(0, 10));
						} else if (lotts.get(position).getRes_date() != null
								&& lotts.get(position).getRes_date().length() < 10) {
							viewholder.time.setText(lotts.get(position)
									.getRes_date());
						}
					} else {
						viewholder.ballText1.setImageResource(R.drawable.cp);
						viewholder.ballText2.setImageResource(R.drawable.cp);
						viewholder.ballText3.setImageResource(R.drawable.cp);
						viewholder.sum.setText("开奖中");
						if (lotts.get(position).getPeroid_name() != null)
							viewholder.name.setText("第"
									+ lotts.get(position).getPeroid_name()
									+ "期");
						if (lotts.get(position).getRes_date() != null
								&& lotts.get(position).getRes_date().length() > 10) {
							viewholder.time.setText(lotts.get(position)
									.getRes_date().substring(0, 10));
						} else if (lotts.get(position).getRes_date() != null
								&& lotts.get(position).getRes_date().length() < 10) {
							viewholder.time.setText(lotts.get(position)
									.getRes_date());
						} else {
							viewholder.time.setText("-");
						}
					}
				} else {
					Log.e("pkx", "view holder is null---------");
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
				if (lotts == null || lotts.size() == 0)
					return 0;
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
				PeroidRes res = new PeroidRes("getPeroidRes", lotterytypeStr,
						"30", String.valueOf(1));
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						Net.gson.toJson(res));
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", miwen);
				Net.post(false, ShowFast.this, Constant.POST_URL
						+ "/data.api.php", params, mHandler,
						Constant.NET_ACTION_REFRESH);

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				Log.e("pkx", "exhibitionRefreshView-----onPullUpToRefresh");
				PeroidRes res = new PeroidRes("getPeroidRes", lotterytypeStr,
						"30", String.valueOf(page + 1));
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						Net.gson.toJson(res));
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", miwen);
				Net.post(false, ShowFast.this, Constant.POST_URL
						+ "/data.api.php", params, mHandler,
						Constant.NET_ACTION_LOADMORE);
				selection = lotts.size() - 1;

			}
		});
		// IXListViewListener coperListener = new IXListViewListener() {
		//
		// @Override
		// public void onRefresh() {
		// PeroidRes res = new PeroidRes("getPeroidRes", lotterytypeStr, "30",
		// String.valueOf(1));
		// String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
		// Net.gson.toJson(res));
		// RequestParams params = new RequestParams();
		// params.put("SID", sutil.getSID());
		// params.put("SN", sutil.getSN());
		// params.put("DATA", miwen);
		// Net.post(false, ShowFast.this, Constant.POST_URL
		// + "/data.api.php", params, mHandler,
		// Constant.NET_ACTION_REFRESH);
		//
		// }
		//
		// @Override
		// public void onLoadMore() {
		// PeroidRes res = new PeroidRes("getPeroidRes", lotterytypeStr, "30",
		// String.valueOf(page + 1));
		// String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
		// Net.gson.toJson(res));
		// RequestParams params = new RequestParams();
		// params.put("SID", sutil.getSID());
		// params.put("SN", sutil.getSN());
		// params.put("DATA", miwen);
		// Net.post(false, ShowFast.this, Constant.POST_URL
		// + "/data.api.php", params, mHandler,
		// Constant.NET_ACTION_LOADMORE);
		// selection = lotts.size() - 1;
		// }
		// };
		// showList.setDividerHeight(1);
		showPullList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				Toast.makeText(ShowFast.this, "无快三开奖详情", Toast.LENGTH_SHORT)
						.show();
				// Message msg = new Message();
				// msg.arg1 = position;
				// msg.what = 60888;
				// mHandler.sendMessage(msg);
				// Intent toWebView=new Intent(ShowChromsphere.this,
				// WebviewActivity.class);
				// toWebView.putExtra("weburl",
				// "http://www.cootm.com/cptest/index.html");
				// startActivity(toWebView);
			}

		});
		// showList.setPullLoadEnable(true);
		// showList.setPullRefreshEnable(true);
		// showList.setXListViewListener(coperListener);
		// showList.setOnScrollListener(new OnScrollListener() {
		//
		// @Override
		// public void onScrollStateChanged(AbsListView view, int scrollState) {
		// if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
		// selection = showList.getFirstVisiblePosition();
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
	}

	// private void onLoad(XListView x) {
	// x.stopRefresh();
	// x.stopLoadMore();
	// x.setRefreshTime("just fresh");
	// }

	static class ViewHolder {
		ImageView ballText1;
		ImageView ballText2;
		ImageView ballText3;
		TextView name;
		TextView time;
		TextView sum;

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
			if (msg.what == 60888) {
				LottDetails ld = new LottDetails("lottery_info", lotts.get(
						msg.arg1).getPeroid_name(), lotterytypeStr);
				String mingwen = Net.gson.toJson(ld);
				Log.e("pkx", "明文" + mingwen);
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", miwen);
				Net.post(true, ShowFast.this, Constant.POST_URL
						+ "/lottery.api.php", params, mHandler,
						Constant.NET_ACTION_CHORMBET);
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
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
						Log.e("pkx", "ooooooooooooooooo");
						showListAdapter.notifyDataSetChanged();
						Log.e("pkx", "1111111111111111");
						Log.e("pkx", "lotts size:" + lotts.size());
						for (ChormLott lo : lotts) {
							Log.e("pkxh", "pid:" + lo.getPeroidID() + " name:"
									+ lo.getPeroid_name());
						}
					} catch (JSONException e) {
						Log.e("pkx", "ja------>JSONException");
						e.printStackTrace();
					}
					Log.e("pkx", "往期彩期快三加载成功：" + ojo.toString());
				} else if (msg.arg1 == Constant.NET_ACTION_CHORMBET) {
					JSONObject djo = (JSONObject) msg.obj;
					Log.e("pkx", djo.toString());
					try {
						if ("0".equals(djo.getString("error"))) {
							// Intent toLottDetails=new
							// Intent(ShowChromsphere.this,ShowLottDetails.class);
							// toLottDetails.putExtra("data",
							// djo.getString("data"));
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
						// showListAdapter.notifyDataSetChanged();
						Log.e("pkx", "lotts size:" + lotts.size());
						for (ChormLott lo : lotts) {
							Log.e("pkx", "pid:" + lo.getPeroidID() + " name:"
									+ lo.getPeroid_name());
						}
						page++;
						// showList.stopLoadMore();
						showPullList.onRefreshComplete();
						showListAdapter.notifyDataSetChanged();
						// Toast.makeText(ShowFast.this, "加载成功",
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
					try {
						JSONArray ja = new JSONArray(
								ojo.getString("peroidList"));
						Log.e("pkx", "ja size:" + ja.length());
						lotts.clear();
						for (int i = 0; i < ja.length(); i++) {
							lotts.add(Net.gson.fromJson(ja.get(i).toString(),
									ChormLott.class));
						}
						showPullList.onRefreshComplete();
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
						Toast.makeText(ShowFast.this, "刷新成功",
								Toast.LENGTH_SHORT).show();
					} catch (JSONException e) {
						Log.e("pkx", "ja------>JSONException");
						e.printStackTrace();
					}
				}
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				showPullList.onRefreshComplete();
				Toast.makeText(ShowFast.this, "加载失败", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}
