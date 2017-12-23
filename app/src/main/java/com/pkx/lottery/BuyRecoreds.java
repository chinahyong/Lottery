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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonSyntaxException;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.loopj.android.http.RequestParams;
import com.pkx.lottery.dto.BetRecord;
import com.pkx.lottery.dto.BuyRecord;
import com.pkx.lottery.dto.BuyRecords;
import com.pkx.lottery.dto.BuyrecordDetail;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.dto.lott.details.FootballLott;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BuyRecoreds extends Activity {
	// private XListView showList;
	private PullToRefreshListView showPullList;
	private MyHandler mHandler;
	private SharePreferenceUtil sutil;
	private LayoutInflater inflater;
	private BaseAdapter showListAdapter;
	private Intent mIntent;
	private TextView typeName;
	private ArrayList<BuyRecord> records;
	private BuyRecords res;
	private int clickPosition;
	private int page = 1;
	private int totalNum;
	// private String clickType;
	private View buyNow;

	public void clickBack(View view) {
	super.onBackPressed();
}
	// private static String[] status_set = { "68彩票", "已生成", "已出票", "未出票", "过期",
	// "已关闭", "合买进行中", "已发奖", "过期已支付未出票", "过期已支付已退款" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chromsphere_pulllist_show);
		initviews();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == Constant.REPAY_REQUEST) {
			// 重复支付完成
			Log.e("pkx", "重复支付完成   刷新纪录");
			BetRecord braRE = new BetRecord(sutil.getuid(), 1);
			String mingwenRE = Net.gson.toJson(braRE);
			Log.e("pkx", "明文：" + mingwenRE);
			String miwenRE = MDUtils.MDEncode(sutil.getuserKEY(), mingwenRE);
			PublicAllAuth paaRE = new PublicAllAuth("lottery_list", miwenRE);
			String allRE = Net.gson.toJson(paaRE);
			String allmiwenRE = MDUtils.MDEncode(sutil.getdeviceKEY(), allRE);
			RequestParams pa = new RequestParams();
			pa.put("SID", sutil.getSID());
			pa.put("SN", sutil.getSN());
			pa.put("DATA", allmiwenRE);
			Net.post(true, BuyRecoreds.this, Constant.POST_URL
					+ "/user.api.php", pa, mHandler,
					Constant.NET_ACTION_REFRESH);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void initviews() {
		buyNow = findViewById(R.id.buyNow);
		buyNow.setVisibility(View.GONE);
		typeName = (TextView) findViewById(R.id.typeName);
		typeName.setText("投注记录");
		mIntent = getIntent();
		records = new ArrayList<BuyRecord>();
		try {
			res = Net.gson.fromJson(mIntent.getStringExtra("data"),
					BuyRecords.class);
			totalNum = res.getPage_sum();
			if (res.getPage() == 1) {
				Log.e("pkx", "page==1");
			}
		} catch (JsonSyntaxException e) {
			Toast.makeText(this, "无投注记录", Toast.LENGTH_SHORT).show();
			finish();
		}
		if (res != null && res.getOrder_list() != null
				&& res.getOrder_list().size() > 0) {
			records.addAll(res.getOrder_list());
		}
		for (BuyRecord r : records) {
			Log.e("pkx",
					"type:" + r.getLotteryType() + " amount:"
							+ r.getBet_amount() + " buytype:"
							+ r.getBuy_method());
		}
		sutil = new SharePreferenceUtil(this);
		inflater = getLayoutInflater();
		mHandler = new MyHandler();
		// showPullList = (PullToRefreshListView)
		// findViewById(R.id.showPullList);
		showListAdapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = inflater.inflate(R.layout.buy_recored_item,
							null);
					holder.buyLott = (TextView) convertView
							.findViewById(R.id.buyLott);
					holder.buyAmount = (TextView) convertView
							.findViewById(R.id.buyAmount);
					holder.buyType = (TextView) convertView
							.findViewById(R.id.buyType);
					holder.status = (TextView) convertView
							.findViewById(R.id.status);
					holder.time = (TextView) convertView
							.findViewById(R.id.time);
					holder.lottImage = (ImageView) convertView
							.findViewById(R.id.lottImage);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.buyAmount.setText(records.get(position).getBet_amount());
				
				if (records.get(position).getLotteryType().equals("1")) {
					holder.buyLott.setText("双色球:");
					holder.lottImage.setImageResource(R.drawable.chromosphere);
				} else if (records.get(position).getLotteryType().equals("2")) {
					holder.buyLott.setText("七乐彩:");
					holder.lottImage.setImageResource(R.drawable.poker);
				} else if (records.get(position).getLotteryType().equals("3")) {
					holder.buyLott.setText("3D:");
					holder.lottImage.setImageResource(R.drawable.dimension_3d);
				} else if (records.get(position).getLotteryType().equals("4")) {
					holder.buyLott.setText("湖北快三:");
					holder.lottImage.setImageResource(R.drawable.dimention);
				} else if (records.get(position).getLotteryType().equals("50")) {
					holder.buyLott.setText("竞足:");
					holder.lottImage.setImageResource(R.drawable.football);
				} else if (records.get(position).getLotteryType().equals("60")) {
					holder.buyLott.setText("竞篮:");
					holder.lottImage.setImageResource(R.drawable.basket);
				} else if (records.get(position).getLotteryType().equals("7")) {// 快乐十分
					holder.buyLott.setText("快乐十分:");
					holder.lottImage.setImageResource(R.drawable.happy_ten);
				} else if (records.get(position).getLotteryType().equals("6")) {// 重庆时时彩
					holder.buyLott.setText("时时彩:");
					holder.lottImage
							.setImageResource(R.drawable.chongqing_lott);
				} else if (records.get(position).getLotteryType().equals("5")) {// 重庆时时彩
					holder.buyLott.setText("江苏快三:");
					holder.lottImage.setImageResource(R.drawable.dimention);
				}
				if (Integer.valueOf(records.get(position).getPay_status()) == 1) {
					holder.status.setText("已付款");
				} else if (Integer.valueOf(records.get(position)
						.getOrder_status()) == 6) {
					holder.status.setText("合买进行中");
				} else {
					holder.status.setText("未支付");
				}
				holder.time.setText(records.get(position).getOrder_date());
				if ("50".equals(records.get(position).getLotteryType())) {
					if ("0".equals(records.get(position).getIs_multibuyer())) {
						holder.buyType.setText("自购");
					} else if ("1".equals(records.get(position)
							.getIs_multibuyer())) {
						holder.buyType.setText("合买");
					}
				} else if ("60".equals(records.get(position).getLotteryType())) {
					if ("0".equals(records.get(position).getIs_multibuyer())) {
						holder.buyType.setText("自购");
					} else if ("1".equals(records.get(position)
							.getIs_multibuyer())) {
						holder.buyType.setText("合买");
					}
				} else {
					if (records.get(position).getBuy_method().equals("0")) {
						holder.buyType.setText("自购");
					} else if (records.get(position).getBuy_method()
							.equals("1")) {
						holder.buyType.setText("合买");
					} else if (records.get(position).getBuy_method()
							.equals("2")) {
						holder.buyType.setText("追号");
					}
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
				if (records == null || records.size() == 0)
					return 0;
				return records.size();
			}
		};
		showPullList = (PullToRefreshListView) findViewById(R.id.showPullList);
		showPullList.setMode(Mode.BOTH);
		showPullList.setAdapter(showListAdapter);
		showPullList.setOnRefreshListener(new OnRefreshListener2<ListView>() {

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				BetRecord braRE = new BetRecord(sutil.getuid(), 1);
				String mingwenRE = Net.gson.toJson(braRE);
				Log.e("pkx", "明文：" + mingwenRE);
				String miwenRE = MDUtils.MDEncode(sutil.getuserKEY(), mingwenRE);
				PublicAllAuth paaRE = new PublicAllAuth("lottery_list", miwenRE);
				String allRE = Net.gson.toJson(paaRE);
				String allmiwenRE = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allRE);
				RequestParams pa = new RequestParams();
				pa.put("SID", sutil.getSID());
				pa.put("SN", sutil.getSN());
				pa.put("DATA", allmiwenRE);
				Net.post(false, BuyRecoreds.this, Constant.POST_URL
						+ "/user.api.php", pa, mHandler,
						Constant.NET_ACTION_REFRESH);

			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<ListView> refreshView) {
				BetRecord braRE = new BetRecord(sutil.getuid(), page + 1);
				String mingwenRE = Net.gson.toJson(braRE);
				Log.e("pkx", "明文：" + mingwenRE);
				String miwenRE = MDUtils.MDEncode(sutil.getuserKEY(), mingwenRE);
				PublicAllAuth paaRE = new PublicAllAuth("lottery_list", miwenRE);
				String allRE = Net.gson.toJson(paaRE);
				String allmiwenRE = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allRE);
				RequestParams pa = new RequestParams();
				pa.put("SID", sutil.getSID());
				pa.put("SN", sutil.getSN());
				pa.put("DATA", allmiwenRE);
				Net.post(false, BuyRecoreds.this, Constant.POST_URL
						+ "/user.api.php", pa, mHandler,
						Constant.NET_ACTION_LOADMORE);

			}
		});
		showPullList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				if (position == 0)
					return;
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
		if (res.getPage_sum() == records.size()) {
			showPullList.setMode(Mode.PULL_FROM_START);
			// showList.setPullLoadEnable(false);
		}
		// showList.setAdapter(showListAdapter);
	}

	static class ViewHolder {
		TextView buyLott;
		TextView buyAmount;
		TextView time;
		TextView buyType;
		TextView status;
		ImageView lottImage;
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
				BuyrecordDetail bda;
				Log.e("pkxh", "click method:"
						+ records.get(msg.arg1).getBuy_method());
				if (records.get(msg.arg1).getIs_multibuyer().equals("1")) {
					// 合买
					Log.e("pkx", "mutibut==1:-----");
					bda = new BuyrecordDetail(sutil.getuid(), records.get(
							msg.arg1).getOid(), "");
					String remingwen = Net.gson.toJson(bda);
					Log.e("pkx", "明文：" + remingwen);
					String remiwen = MDUtils.MDEncode(sutil.getuserKEY(),
							remingwen);
					PublicAllAuth repaa = new PublicAllAuth("order_info",
							remiwen);
					String allremingwen = Net.gson.toJson(repaa);
					String allremiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
							allremingwen);
					RequestParams params = new RequestParams();
					params.put("SID", sutil.getSID());
					params.put("SN", sutil.getSN());
					params.put("DATA", allremiwen);
					clickPosition = msg.arg1;
					Net.post(true, BuyRecoreds.this, Constant.POST_URL
							+ "/user.api.php", params, mHandler,
							Constant.NET_ACTION_USERINFO);
				} else {
					Log.e("pkx", "1111mutibut!=1:-----"
							+ records.get(msg.arg1).getMboiid());
					bda = new BuyrecordDetail(sutil.getuid(), records.get(
							msg.arg1).getOid(), records.get(msg.arg1)
							.getMboiid());
					String remingwen = Net.gson.toJson(bda);
					Log.e("pkx", "明文：" + remingwen);
					String remiwen = MDUtils.MDEncode(sutil.getuserKEY(),
							remingwen);
					PublicAllAuth repaa = new PublicAllAuth("order_info",
							remiwen);
					String allremingwen = Net.gson.toJson(repaa);
					String allremiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
							allremingwen);
					RequestParams params = new RequestParams();
					params.put("SID", sutil.getSID());
					params.put("DATA", allremiwen);
					clickPosition = msg.arg1;
					Net.post(true, BuyRecoreds.this, Constant.POST_URL
							+ "/user.api.php", params, mHandler,
							Constant.NET_ACTION_LOTHIS);// 自购
				}
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				if (msg.arg1 == Constant.NET_ACTION_USERINFO) {// 合买
					JSONObject detai = (JSONObject) msg.obj;
					Log.e("pkx", "订单测试  合买：" + detai.toString());
					Log.e("pkx", "buymethod:"
							+ records.get(clickPosition).getBuy_method());
					if ("50".equals(records.get(clickPosition).getLotteryType())) {
						FootballLott lott = Net.gson.fromJson(
								msg.obj.toString(), FootballLott.class);
						if ("1".equals(lott.getOrder_info().getIs_master())) {// 发起合买
							Intent toDetail = new Intent(BuyRecoreds.this,
									BuyrecoredFootBallSelfDetails.class);
							toDetail.putExtra("type", records
									.get(clickPosition).getLotteryType());
							toDetail.putExtra("json", detai.toString());
							String orderStatus = "";
							if (Integer.valueOf(records.get(clickPosition)
									.getPay_status()) == 1) {
								orderStatus = "已付款";
							} else if (Integer.valueOf(records.get(
									clickPosition).getOrder_status()) == 6) {
								orderStatus = "合买进行中";
							} else {
								orderStatus = "未支付";
							}
							toDetail.putExtra("orderStatus", orderStatus);
							startActivity(toDetail);
						} else if ("0".equals(lott.getOrder_info()
								.getIs_master())) {// 合买购买
							Log.e("pkx", "足彩合买认购");
							Intent toDetail = new Intent(BuyRecoreds.this,
									BuyRecoredFootballFollowDetails.class);
							toDetail.putExtra("type", records
									.get(clickPosition).getLotteryType());
							toDetail.putExtra("json", detai.toString());
							String orderStatus = "";
							if (Integer.valueOf(records.get(clickPosition)
									.getPay_status()) == 1) {
								orderStatus = "已付款";
							} else if (Integer.valueOf(records.get(
									clickPosition).getOrder_status()) == 6) {
								orderStatus = "合买进行中";
							} else {
								orderStatus = "未支付";
							}
							toDetail.putExtra("orderStatus", orderStatus);
							startActivity(toDetail);
						}
					} else if ("60".equals(records.get(clickPosition)
							.getLotteryType())) {
						FootballLott lott = Net.gson.fromJson(
								msg.obj.toString(), FootballLott.class);
						if ("1".equals(lott.getOrder_info().getIs_master())) {// 发起合买
							Intent toDetail = new Intent(BuyRecoreds.this,
									BuyrecoredFootBallSelfDetails.class);
							toDetail.putExtra("type", records
									.get(clickPosition).getLotteryType());
							toDetail.putExtra("json", detai.toString());
							String orderStatus = "";
							if (Integer.valueOf(records.get(clickPosition)
									.getPay_status()) == 1) {
								orderStatus = "已付款";
							} else if (Integer.valueOf(records.get(
									clickPosition).getOrder_status()) == 6) {
								orderStatus = "合买进行中";
							} else {
								orderStatus = "未支付";
							}
							toDetail.putExtra("orderStatus", orderStatus);
							toDetail.putExtra("isBasketBall", true);
							startActivity(toDetail);
						} else if ("0".equals(lott.getOrder_info()
								.getIs_master())) {// 合买购买
							Log.e("pkx", "足彩合买认购");
							Intent toDetail = new Intent(BuyRecoreds.this,
									BuyRecoredFootballFollowDetails.class);
							toDetail.putExtra("type", records
									.get(clickPosition).getLotteryType());
							toDetail.putExtra("json", detai.toString());
							String orderStatus = "";
							if (Integer.valueOf(records.get(clickPosition)
									.getPay_status()) == 1) {
								orderStatus = "已付款";
							} else if (Integer.valueOf(records.get(
									clickPosition).getOrder_status()) == 6) {
								orderStatus = "合买进行中";
							} else {
								orderStatus = "未支付";
							}
							toDetail.putExtra("orderStatus", orderStatus);
							toDetail.putExtra("isBasketBall", true);
							startActivity(toDetail);
						}
					} else {
						FootballLott lott = Net.gson.fromJson(
								msg.obj.toString(), FootballLott.class);
						if ("1".equals(lott.getOrder_info().getIs_master())) {// 发起合买
							Intent toDetail = new Intent(BuyRecoreds.this,
									BuyrecoredMasterCorpDetails.class);
							toDetail.putExtra("type", records
									.get(clickPosition).getLotteryType());
							toDetail.putExtra("total",
									records.get(clickPosition).getBet_amount());
							toDetail.putExtra("json", detai.toString());
							String orderStatus = "";
							if (Integer.valueOf(records.get(clickPosition)
									.getPay_status()) == 1) {
								orderStatus = "已付款";
							} else if (Integer.valueOf(records.get(
									clickPosition).getOrder_status()) == 6) {
								orderStatus = "合买进行中";
							} else {
								orderStatus = "未支付";
							}
							toDetail.putExtra("orderStatus", orderStatus);
							// toDetail.putExtra("lottPeriod", );
							startActivity(toDetail);
						} else if ("0".equals(lott.getOrder_info()
								.getIs_master())) {// 合买购买
							Intent toDetail = new Intent(BuyRecoreds.this,
									BuyrecoredCorpDetails.class);
							toDetail.putExtra("type", records
									.get(clickPosition).getLotteryType());
							toDetail.putExtra("total",
									records.get(clickPosition).getBet_amount());
							toDetail.putExtra("json", detai.toString());
							String orderStatus = "";
							if (Integer.valueOf(records.get(clickPosition)
									.getPay_status()) == 1) {
								orderStatus = "已付款";
							} else if (Integer.valueOf(records.get(
									clickPosition).getOrder_status()) == 6) {
								orderStatus = "合买进行中";
							} else {
								orderStatus = "未支付";
							}
							toDetail.putExtra("orderStatus", orderStatus);
							// toDetail.putExtra("lottPeriod", );
							startActivity(toDetail);
						}
					}
				} else if (msg.arg1 == Constant.NET_ACTION_LOTHIS) {
					// 自购
					JSONObject detai = (JSONObject) msg.obj;
					Log.e("pkx", "buymethod:"
							+ records.get(clickPosition).getBuy_method());
					if ("50".equals(records.get(clickPosition).getLotteryType())) {
						Log.e("pkx",
								"buyMethod----:"
										+ records.get(clickPosition)
												.getBuy_method());

						Intent toDetail = new Intent(BuyRecoreds.this,
								BuyRecoredFootballDetails.class);
						toDetail.putExtra("type", records.get(clickPosition)
								.getLotteryType());
						toDetail.putExtra("json", detai.toString());
						String orderStatus = "";
						if (Integer.valueOf(records.get(clickPosition)
								.getPay_status()) == 1) {
							orderStatus = "已付款";
						} else if (Integer.valueOf(records.get(clickPosition)
								.getOrder_status()) == 6) {
							orderStatus = "合买进行中";
						} else {
							orderStatus = "未支付";
						}
						toDetail.putExtra("orderStatus", orderStatus);
						// toDetail.putExtra("lottPeriod", );
						startActivity(toDetail);
					} else if ("60".equals(records.get(clickPosition)
							.getLotteryType())) {
						Log.e("pkx",
								"buyMethod----:"
										+ records.get(clickPosition)
												.getBuy_method());
						// try {
						// FootballLott lott = Net.gson.fromJson(
						// detai.toString(), FootballLott.class);
						Intent toDetail = new Intent(BuyRecoreds.this,
								BuyRecoredFootballDetails.class);
						toDetail.putExtra("type", records.get(clickPosition)
								.getLotteryType());
						toDetail.putExtra("json", detai.toString());
						String orderStatus = "";
						if (Integer.valueOf(records.get(clickPosition)
								.getPay_status()) == 1) {
							orderStatus = "已付款";
						} else if (Integer.valueOf(records.get(clickPosition)
								.getOrder_status()) == 6) {
							orderStatus = "合买进行中";
						} else {
							orderStatus = "未支付";
						}
						toDetail.putExtra("isBasketBall", true);
						toDetail.putExtra("orderStatus", orderStatus);
						// toDetail.putExtra("lottPeriod", );
						startActivity(toDetail);
						// } catch (JsonSyntaxException e) {
						// Log.e("pkx", "篮球 json 异常");
						// // TODO: handle exception
						// }
					} else {// 自购球类彩

						Intent toDetail = new Intent(BuyRecoreds.this,
								BuyrecoredDetails.class);
						toDetail.putExtra("type", records.get(clickPosition)
								.getLotteryType());
						toDetail.putExtra("json", detai.toString());
						String orderStatus = "";
						if (Integer.valueOf(records.get(clickPosition)
								.getPay_status()) == 1) {
							orderStatus = "已付款";
						} else if (Integer.valueOf(records.get(clickPosition)
								.getOrder_status()) == 6) {
							orderStatus = "合买进行中";
						} else {
							orderStatus = "未支付";
						}
						if (Integer.valueOf(records.get(clickPosition)
								.getOrder_status()) == 1) {
							Log.e("pkx", "set  can  repay");
							toDetail.putExtra("canrepay", true);
						}
						toDetail.putExtra("orderStatus", orderStatus);
						// toDetail.putExtra("lottPeriod", );
						startActivityForResult(toDetail, Constant.REPAY_REQUEST);
					}
				} else if (msg.arg1 == Constant.NET_ACTION_REFRESH) {
					JSONObject fresh = (JSONObject) msg.obj;
					if (records != null && records.size() > 0) {
						records.clear();
					}
					page = 1;
					try {
						res = Net.gson.fromJson(fresh.getString("data"),
								BuyRecords.class);
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if (res != null && res.getOrder_list() != null
							&& res.getOrder_list().size() > 0) {
						records.addAll(res.getOrder_list());
					}
					if (res.getPage_sum() == records.size()) {
						showPullList.setMode(Mode.PULL_FROM_START);
						// showList.setPullLoadEnable(false);
					}
					Log.e("pkx", "refresh:" + records.size());
					showListAdapter.notifyDataSetChanged();
					showPullList.onRefreshComplete();
				} else if (msg.arg1 == Constant.NET_ACTION_LOADMORE) {
					JSONObject fresh = (JSONObject) msg.obj;
					page++;
					try {
						res = Net.gson.fromJson(fresh.getString("data"),
								BuyRecords.class);
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
					if (res != null && res.getOrder_list() != null
							&& res.getOrder_list().size() > 0) {
						records.addAll(res.getOrder_list());
					}
					if (res.getPage_sum() == records.size()) {
						showPullList.setMode(Mode.PULL_FROM_START);
					}
					Log.e("pkx", "loadmore:" + records.size());
					showListAdapter.notifyDataSetChanged();
					showPullList.onRefreshComplete();
				}
			}
		}
	}
}
