package com.pkx.lottery;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.AccountDetailAuth;
import com.pkx.lottery.dto.AccountDetailItemDto;
import com.pkx.lottery.dto.AccountDetailListDto;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AccountDetails extends Activity {
	// private XListView showList;
	private PullToRefreshListView showPullList;
	private MyHandler mHandler;
	private int page = 1, selection;
	private BaseAdapter showListAdapter;
	private LayoutInflater inflater;
	private SharePreferenceUtil sutil;
	private ArrayList<AccountDetailItemDto> accountList;
	public void clickBack(View view) {
		super.onBackPressed();
	}
	// ArrayList<ChormLott> lotts;

	// private boolean isFromSelectPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_details);
		initviews();
		// 测试往期数据
		accountList = new ArrayList<AccountDetailItemDto>();
		AccountDetailAuth da = new AccountDetailAuth(sutil.getuid(), "4", 1);
		String mingwen = Net.gson.toJson(da);
		Log.e("pkx", "请求:" + mingwen);
		String miwen = MDUtils.MDEncode(sutil.getuserKEY(), mingwen);
		PublicAllAuth paa = new PublicAllAuth("account_detail", miwen);
		String allmingwen = Net.gson.toJson(paa);
		String allmiwen = MDUtils.MDEncode(sutil.getdeviceKEY(), allmingwen);
		RequestParams params = new RequestParams();
		params.put("SID", sutil.getSID());
		params.put("SN", sutil.getSN());
		params.put("DATA", allmiwen);
		Net.post(true, this, Constant.POST_URL + "/user.api.php", params,
				mHandler, Constant.NET_ACTION_SECRET_SECURE);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private void initviews() {
		inflater = getLayoutInflater();
		mHandler = new MyHandler();
		sutil = new SharePreferenceUtil(this);
		showListAdapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = inflater.inflate(
							R.layout.account_detail_item, null);
				}
				TextView amount, remark, time;
				amount = (TextView) convertView.findViewById(R.id.amount);
				remark = (TextView) convertView.findViewById(R.id.remark);
				time = (TextView) convertView.findViewById(R.id.time);
				if (accountList.get(position).getAmount() != null) {
					amount.setText(String.valueOf(accountList.get(position)
							.getAmount()));
				}
				if (accountList.get(position).getRemark() != null) {
					remark.setText("交易类型:"
							+ String.valueOf(accountList.get(position)
									.getRemark()));
				}
				if (accountList.get(position).getTrade_date() != null) {
					time.setText(String.valueOf(accountList.get(position)
							.getTrade_date()));
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
				if (accountList == null || accountList.size() == 0)
					return 0;
				return accountList.size();
			}
		};
		showPullList = (PullToRefreshListView) findViewById(R.id.accountPullList);
		showPullList.setMode(Mode.BOTH);
		showPullList.setAdapter(showListAdapter);
		showPullList.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				Log.e("pkx", "exhibitionRefreshView-----onPullDownToRefresh");
				AccountDetailAuth da = new AccountDetailAuth(sutil.getuid(),
						"0", 1);
				String mingwen = Net.gson.toJson(da);
				Log.e("pkx", "请求:" + mingwen);
				String miwen = MDUtils.MDEncode(sutil.getuserKEY(), mingwen);
				PublicAllAuth paa = new PublicAllAuth("account_detail", miwen);
				String allmingwen = Net.gson.toJson(paa);
				String allmiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allmingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", allmiwen);
				Net.post(true, AccountDetails.this, Constant.POST_URL
						+ "/user.api.php", params, mHandler,
						Constant.NET_ACTION_REFRESH);

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				Log.e("pkx", "exhibitionRefreshView-----onPullUpToRefresh");
				AccountDetailAuth da = new AccountDetailAuth(sutil.getuid(),
						"0", page + 1);
				String mingwen = Net.gson.toJson(da);
				Log.e("pkx", "请求:" + mingwen);
				String miwen = MDUtils.MDEncode(sutil.getuserKEY(), mingwen);
				PublicAllAuth paa = new PublicAllAuth("account_detail", miwen);
				String allmingwen = Net.gson.toJson(paa);
				String allmiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allmingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", allmiwen);
				Net.post(true, AccountDetails.this, Constant.POST_URL
						+ "/user.api.php", params, mHandler,
						Constant.NET_ACTION_LOADMORE);

			}
		});

		// showPullList.setOnItemClickListener(new OnItemClickListener() {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View arg1,
		// int position, long id) {
		// if (position == 0)
		// return;
		// Message msg = new Message();
		// msg.arg1 = position - 1;
		// msg.what = 60888;
		// mHandler.sendMessage(msg);
		//
		// }
		//
		// });
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
			if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				if (msg.arg1 == Constant.NET_ACTION_SECRET_SECURE) {
					try {
						JSONObject jo = (JSONObject) msg.obj;
						try {
							if (jo.getJSONObject("data") != null) {
								AccountDetailListDto list = Net.gson.fromJson(
										jo.getJSONObject("data").toString(),
										AccountDetailListDto.class);
								accountList.addAll(list.getLog_list());
								page = 1;
								if (1 == list.getPage_sum()) {
									showPullList.setMode(Mode.PULL_FROM_START);
								} else if (list.getPage_sum() > 1) {
									showPullList.setMode(Mode.BOTH);
								}
								for (AccountDetailItemDto item : list
										.getLog_list()) {
									Log.e("pkx", "item" + item.getAmount()
											+ "  " + item.getRemark());
								}
							}
						} catch (JSONException e) {
							Toast.makeText(AccountDetails.this, "加载异常",
									Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
					} catch (Exception e) {
						Toast.makeText(AccountDetails.this, "无数据",
								Toast.LENGTH_SHORT).show();
					}
					showListAdapter.notifyDataSetChanged();
					showPullList.onRefreshComplete();
				} else if (msg.arg1 == Constant.NET_ACTION_REFRESH) {
					try {
						JSONObject jo = (JSONObject) msg.obj;
						try {
							if (jo.getJSONObject("data") != null) {
								AccountDetailListDto list = Net.gson.fromJson(
										jo.getJSONObject("data").toString(),
										AccountDetailListDto.class);
								accountList.clear();
								accountList.addAll(list.getLog_list());
								page = 1;
								if (1 == list.getPage_sum()) {
									showPullList.setMode(Mode.PULL_FROM_START);
								} else if (list.getPage_sum() > 1) {
									showPullList.setMode(Mode.BOTH);
								}
								for (AccountDetailItemDto item : list
										.getLog_list()) {
									Log.e("pkx", "item" + item.getAmount()
											+ "  " + item.getRemark());
								}
							}
						} catch (JSONException e) {
							Toast.makeText(AccountDetails.this, "刷新异常",
									Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
					} catch (Exception e) {
						Toast.makeText(AccountDetails.this, "无数据",
								Toast.LENGTH_SHORT).show();
					}
					showListAdapter.notifyDataSetChanged();
					showPullList.onRefreshComplete();
				} else if (msg.arg1 == Constant.NET_ACTION_LOADMORE) {
					try {
						JSONObject jo = (JSONObject) msg.obj;
						try {
							if (jo.getJSONObject("data") != null) {
								AccountDetailListDto list = Net.gson.fromJson(
										jo.getJSONObject("data").toString(),
										AccountDetailListDto.class);
								accountList.addAll(list.getLog_list());
								page++;
								if (page == list.getPage_sum()) {
									showPullList.setMode(Mode.PULL_FROM_START);
								} else if (list.getPage_sum() > page) {
									showPullList.setMode(Mode.BOTH);
								}
								for (AccountDetailItemDto item : list
										.getLog_list()) {
									Log.e("pkx", "item" + item.getAmount()
											+ "  " + item.getRemark());
								}
							}
						} catch (JSONException e) {
							Toast.makeText(AccountDetails.this, "刷新异常",
									Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}
					} catch (Exception e) {
						Toast.makeText(AccountDetails.this, "无数据",
								Toast.LENGTH_SHORT).show();
					}
					showListAdapter.notifyDataSetChanged();
					showPullList.onRefreshComplete();
				}
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				showPullList.onRefreshComplete();
				Toast.makeText(AccountDetails.this, "加载失败", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}
}
