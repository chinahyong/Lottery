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

public class ShowSeven extends Activity {
	// private XListView showList;
	private PullToRefreshListView showPullList;
	private MyHandler mHandler;
	private int page = 1, selection;
	private BaseAdapter showListAdapter;
	private LayoutInflater inflater;
	private ArrayList<ChromShowBet> chromShows;
	private TextView typeName;
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
		lotts = new ArrayList<ChormLott>();
		initviews();
		// 测试往期数据
		PeroidRes res = new PeroidRes("getPeroidRes", "2", "30", "1");
		String mingwen = Net.gson.toJson(res);
		String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen);
		RequestParams params = new RequestParams();
		params.put("SID", sutil.getSID());
		params.put("SN", sutil.getSN());
		params.put("DATA", miwen);
		// Log.e("pkx", "mingwen:"+mingwen+" miwen:"+miwen);
		Net.post(true, ShowSeven.this, Constant.POST_URL + "/data.api.php",
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
		inflater = getLayoutInflater();
		mHandler = new MyHandler();
		sutil = new SharePreferenceUtil(this);
		buyNow = findViewById(R.id.buyNow);
		buyNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(ShowSeven.this, SevenLottery.class));
			}
		});
		// showList = (XListView) findViewById(R.id.showList);
		typeName = (TextView) findViewById(R.id.typeName);
		typeName.setText("开奖历史-七乐彩");
		showListAdapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				Log.e("pkx", "lott:" + lotts.get(position).getBonus_number());
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = inflater.inflate(
							R.layout.showseven_list_item, null);
					holder.ballText1 = (TextView) convertView
							.findViewById(R.id.balltext1);
					holder.ballText2 = (TextView) convertView
							.findViewById(R.id.balltext2);
					holder.ballText3 = (TextView) convertView
							.findViewById(R.id.balltext3);
					holder.ballText4 = (TextView) convertView
							.findViewById(R.id.balltext4);
					holder.ballText5 = (TextView) convertView
							.findViewById(R.id.balltext5);
					holder.ballText6 = (TextView) convertView
							.findViewById(R.id.balltext6);
					holder.ballText7 = (TextView) convertView
							.findViewById(R.id.balltext7);
					holder.ballText8 = (TextView) convertView
							.findViewById(R.id.balltext8);
					holder.name = (TextView) convertView
							.findViewById(R.id.name);
					holder.time = (TextView) convertView
							.findViewById(R.id.time);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				if (null == holder) {
					Log.e("pkx", "null==holder");
				}
				if (null == holder.ballText1) {
					Log.e("pkx", "null==holder.ballText1");
				}
				if (lotts.get(position).getBalls().size() == 8) {
					holder.ballText1.setText(String.valueOf(lotts.get(position)
							.getBalls().get(0)));
					holder.ballText2.setText(String.valueOf(lotts.get(position)
							.getBalls().get(1)));
					holder.ballText3.setText(String.valueOf(lotts.get(position)
							.getBalls().get(2)));
					holder.ballText4.setText(String.valueOf(lotts.get(position)
							.getBalls().get(3)));
					holder.ballText5.setText(String.valueOf(lotts.get(position)
							.getBalls().get(4)));
					holder.ballText6.setText(String.valueOf(lotts.get(position)
							.getBalls().get(5)));
					holder.ballText7.setText(String.valueOf(lotts.get(position)
							.getBalls().get(6)));
					holder.ballText8.setText(String.valueOf(lotts.get(position)
							.getBalls().get(7)));
				} else {
					holder.ballText1.setText("-");
					holder.ballText2.setText("-");
					holder.ballText3.setText("-");
					holder.ballText4.setText("-");
					holder.ballText5.setText("-");
					holder.ballText6.setText("-");
					holder.ballText7.setText("-");
					holder.ballText8.setText("-");
				}
				holder.name.setText("第" + lotts.get(position).getPeroid_name()
						+ "期");
				if (lotts.get(position).getRes_date().length() > 10) {
					holder.time.setText(lotts.get(position).getRes_date()
							.substring(0, 10));
				} else {
					holder.time.setText(lotts.get(position).getRes_date());
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
				PeroidRes res = new PeroidRes("getPeroidRes", "2", "30", String
						.valueOf(1));
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						Net.gson.toJson(res));
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", miwen);
				Net.post(false, ShowSeven.this, Constant.POST_URL
						+ "/data.api.php", params, mHandler,
						Constant.NET_ACTION_REFRESH);

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				Log.e("pkx", "exhibitionRefreshView-----onPullUpToRefresh");
				PeroidRes res = new PeroidRes("getPeroidRes", "2", "30", String
						.valueOf(page + 1));
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						Net.gson.toJson(res));
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", miwen);
				Net.post(false, ShowSeven.this, Constant.POST_URL
						+ "/data.api.php", params, mHandler,
						Constant.NET_ACTION_LOADMORE);
				selection = lotts.size() - 1;

			}
		});
		// IXListViewListener coperListener = new IXListViewListener() {
		//
		// @Override
		// public void onRefresh() {
		// PeroidRes res = new PeroidRes("getPeroidRes", "2", "30",
		// String.valueOf(1));
		// String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
		// Net.gson.toJson(res));
		// RequestParams params = new RequestParams();
		// params.put("SID", sutil.getSID());
		// params.put("SN", sutil.getSN());
		// params.put("DATA", miwen);
		// Net.post(false, ShowSeven.this, Constant.POST_URL
		// + "/data.api.php", params, mHandler,
		// Constant.NET_ACTION_REFRESH);
		//
		// }
		//
		// @Override
		// public void onLoadMore() {
		// PeroidRes res = new PeroidRes("getPeroidRes", "2", "30",
		// String.valueOf(page + 1));
		// String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
		// Net.gson.toJson(res));
		// RequestParams params = new RequestParams();
		// params.put("SID", sutil.getSID());
		// params.put("SN", sutil.getSN());
		// params.put("DATA", miwen);
		// Net.post(false, ShowSeven.this, Constant.POST_URL
		// + "/data.api.php", params, mHandler,
		// Constant.NET_ACTION_LOADMORE);
		// selection = lotts.size() - 1;
		// }
		// };
		// showList.setDividerHeight(1);
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
		// showList.setPullLoadEnable(true);
		// showList.setPullRefreshEnable(true);
		// showList.setXListViewListener(coperListener);
	}

	// private void onLoad(XListView x) {
	// x.stopRefresh();
	// x.stopLoadMore();
	// x.setRefreshTime("just fresh");
	// }

	static class ViewHolder {
		TextView ballText1;
		TextView ballText2;
		TextView ballText3;
		TextView ballText4;
		TextView ballText5;
		TextView ballText6;
		TextView ballText7;
		TextView ballText8;
		TextView name;
		TextView time;

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
						msg.arg1).getPeroid_name(), "2");
				String mingwen = Net.gson.toJson(ld);
				Log.e("pkx", "明文" + mingwen);
				String miwen = MDUtils.MDEncode(sutil.getdeviceKEY(), mingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", miwen);
				Net.post(true, ShowSeven.this, Constant.POST_URL
						+ "/lottery.api.php", params, mHandler,
						Constant.NET_ACTION_CHORMBET);
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
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
						showPullList.onRefreshComplete();
						showListAdapter.notifyDataSetChanged();
					} catch (JSONException e) {
						Log.e("pkx", "ja------>JSONException");
						e.printStackTrace();
					}
					Log.e("pkx", "往期彩期加载成功：" + ojo.toString());
				} else if (msg.arg1 == Constant.NET_ACTION_CHORMBET) {
					JSONObject djo = (JSONObject) msg.obj;
					Log.e("pkx", djo.toString());
					try {
						if ("0".equals(djo.getString("error"))) {
							Log.e("pkx", "toDetails");
							Intent toLottDetails = new Intent(ShowSeven.this,
									ShowLottDetails.class);
							toLottDetails.putExtra("data",
									djo.getString("data"));
							toLottDetails.putExtra("type", 2);
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
						showPullList.onRefreshComplete();
						showListAdapter.notifyDataSetChanged();
						Log.e("pkx", "lotts size:" + lotts.size());
						for (ChormLott lo : lotts) {
							Log.e("pkx", "pid:" + lo.getPeroidID() + " name:"
									+ lo.getPeroid_name());
						}
						page++;
						// Toast.makeText(ShowSeven.this, "加载成功",
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
						Log.e("pkx", "lotts size:" + lotts.size());
						for (ChormLott lo : lotts) {
							Log.e("pkx", "pid:" + lo.getPeroidID() + " name:"
									+ lo.getPeroid_name());
						}
						page = 1;
						showPullList.onRefreshComplete();
						showListAdapter.notifyDataSetChanged();
						for (ChormLott c : lotts) {
							Log.e("pkx", " p:" + c.getPeroid_name());
						}
						Toast.makeText(ShowSeven.this, "刷新成功",
								Toast.LENGTH_SHORT).show();
					} catch (JSONException e) {
						Log.e("pkx", "ja------>JSONException");
						e.printStackTrace();
					}
				}
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
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				showPullList.onRefreshComplete();
				Toast.makeText(ShowSeven.this, "加载失败", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}
