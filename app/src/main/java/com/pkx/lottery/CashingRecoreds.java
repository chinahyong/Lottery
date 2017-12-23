package com.pkx.lottery;

import android.app.Activity;
import android.app.AlertDialog;
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
import com.pkx.lottery.dto.BetRecord;
import com.pkx.lottery.dto.BuyRecord;
import com.pkx.lottery.dto.CancelCashingAuth;
import com.pkx.lottery.dto.CashingRecoredListDto;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

import java.util.ArrayList;

public class CashingRecoreds extends Activity {
	// private XListView showList;
	private PullToRefreshListView showPullList;
	private MyHandler mHandler;
	private SharePreferenceUtil sutil;
	private LayoutInflater inflater;
	private BaseAdapter showListAdapter;
	private Intent mIntent;
	private TextView typeName;
	private ArrayList<BuyRecord> records;
	// private BuyRecords res;
	private int clickPosition;
	private int page = 1;
	private int totalNum;
	// private String clickType;
	private View buyNow;
	private CashingRecoredListDto cashingList;
	private int selectIndex = -1;

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
			PublicAllAuth paaRE = new PublicAllAuth("withdrawal_list", miwenRE);
			String allRE = Net.gson.toJson(paaRE);
			String allmiwenRE = MDUtils.MDEncode(sutil.getdeviceKEY(), allRE);
			RequestParams pa = new RequestParams();
			pa.put("SID", sutil.getSID());
			pa.put("SN", sutil.getSN());
			pa.put("DATA", allmiwenRE);
			Net.post(true, CashingRecoreds.this, Constant.POST_URL
					+ "/user.api.php", pa, mHandler,
					Constant.NET_ACTION_REFRESH);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void initviews() {
		buyNow = findViewById(R.id.buyNow);
		buyNow.setVisibility(View.GONE);
		typeName = (TextView) findViewById(R.id.typeName);
		typeName.setText("提现记录");
		mIntent = getIntent();
		sutil = new SharePreferenceUtil(this);
		inflater = getLayoutInflater();
		mHandler = new MyHandler();
		BetRecord braRE = new BetRecord(sutil.getuid(), 1);
		String mingwenRE = Net.gson.toJson(braRE);
		Log.e("pkx", "明文：" + mingwenRE);
		String miwenRE = MDUtils.MDEncode(sutil.getuserKEY(), mingwenRE);
		PublicAllAuth paaRE = new PublicAllAuth("withdrawal_list", miwenRE);
		String allRE = Net.gson.toJson(paaRE);
		String allmiwenRE = MDUtils.MDEncode(sutil.getdeviceKEY(), allRE);
		RequestParams pa = new RequestParams();
		pa.put("SID", sutil.getSID());
		pa.put("SN", sutil.getSN());
		pa.put("DATA", allmiwenRE);
		Net.post(true, CashingRecoreds.this, Constant.POST_URL
				+ "/user.api.php", pa, mHandler, Constant.NET_ACTION_REFRESH);
		showListAdapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holder = null;
				if (convertView == null) {
					holder = new ViewHolder();
					convertView = inflater.inflate(
							R.layout.cashing_recored_item, null);
					holder.bankAndCard = (TextView) convertView
							.findViewById(R.id.bankAndCard);
					holder.cashingAmount = (TextView) convertView
							.findViewById(R.id.cashingAmount);
					holder.name = (TextView) convertView
							.findViewById(R.id.name);
					holder.status = (TextView) convertView
							.findViewById(R.id.status);
					holder.time = (TextView) convertView
							.findViewById(R.id.time);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				if (cashingList != null && cashingList.getList() != null
						&& cashingList.getList().size() > 0
						&& cashingList.getList().get(position) != null) {
					holder.bankAndCard.setText(String.valueOf(cashingList
							.getList().get(position).getBank_type())
							+ ":"
							+ String.valueOf(cashingList.getList()
									.get(position).getBank_card()));
					holder.cashingAmount.setText(String.valueOf(cashingList
							.getList().get(position).getMoney())
							+ "元");
					holder.name.setText(String.valueOf(cashingList.getList()
							.get(position).getBank_name()));
					holder.time.setText(String.valueOf(cashingList.getList()
							.get(position).getApply_date()));
					try {

						switch (Integer.valueOf(cashingList.getList()
								.get(position).getApply_status())) {
						case 0:
							holder.status.setText("未处理");
							break;
						case 1:
							holder.status.setText("成功");
							break;
						case 2:
							holder.status.setText("失败");
							break;
						case 3:
							holder.status.setText("申请被拒");
							break;
						case 4:
							holder.status.setText("用户取消");
							break;
						case 5:
							holder.status.setText("处理中");
							break;
						}

					} catch (Exception e) {
						holder.status.setText("操作异常");
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
				if (cashingList == null || cashingList.getList().size() == 0)
					return 0;
				return cashingList.getList().size();
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
				PublicAllAuth paaRE = new PublicAllAuth("withdrawal_list",
						miwenRE);
				String allRE = Net.gson.toJson(paaRE);
				String allmiwenRE = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allRE);
				RequestParams pa = new RequestParams();
				pa.put("SID", sutil.getSID());
				pa.put("SN", sutil.getSN());
				pa.put("DATA", allmiwenRE);
				Net.post(false, CashingRecoreds.this, Constant.POST_URL
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
				PublicAllAuth paaRE = new PublicAllAuth("withdrawal_list",
						miwenRE);
				String allRE = Net.gson.toJson(paaRE);
				String allmiwenRE = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allRE);
				RequestParams pa = new RequestParams();
				pa.put("SID", sutil.getSID());
				pa.put("SN", sutil.getSN());
				pa.put("DATA", allmiwenRE);
				Net.post(false, CashingRecoreds.this, Constant.POST_URL
						+ "/user.api.php", pa, mHandler,
						Constant.NET_ACTION_LOADMORE);

			}
		});
		showPullList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long id) {
				Log.e("pkx", "click position:" + String.valueOf(position));
				if ("0".equals(cashingList.getList().get(position - 1)
						.getApply_status())) {
					selectIndex = position - 1;
					alertMoneyLess();
				} else {
					Toast.makeText(CashingRecoreds.this, "不可取消！",
							Toast.LENGTH_SHORT).show();
				}
				// if (position == 0)
				// return;
				// Message msg = new Message();
				// msg.what = 60888;
				// msg.arg1 = position - 1;
				// mHandler.sendMessage(msg);
				// Intent toWebView=new Intent(ShowChromsphere.this,
				// WebviewActivity.class);
				// toWebView.putExtra("weburl",
				// "http://www.cootm.com/cptest/index.html");
				// startActivity(toWebView);
			}

		});
		// if (res!=null&&res.getPage_sum() == records.size()) {
		// showPullList.setMode(Mode.PULL_FROM_START);
		// // showList.setPullLoadEnable(false);
		// }
		// showList.setAdapter(showListAdapter);
	}

	static class ViewHolder {
		TextView bankAndCard;
		TextView status;
		TextView cashingAmount;
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
			// if (msg.what == 60888) {
			// BuyrecordDetail bda;
			// Log.e("pkxh", "click method:"
			// + records.get(msg.arg1).getBuy_method());
			// if (records.get(msg.arg1).getIs_multibuyer().equals("1")) {
			// // 合买
			// Log.e("pkx", "mutibut==1:-----");
			// bda = new BuyrecordDetail(sutil.getuid(), records.get(
			// msg.arg1).getOid(), "");
			// String remingwen = Net.gson.toJson(bda);
			// Log.e("pkx", "明文：" + remingwen);
			// String remiwen = MDUtils.MDEncode(sutil.getuserKEY(),
			// remingwen);
			// PublicAllAuth repaa = new PublicAllAuth("order_info",
			// remiwen);
			// String allremingwen = Net.gson.toJson(repaa);
			// String allremiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
			// allremingwen);
			// RequestParams params = new RequestParams();
			// params.put("SID", sutil.getSID());
			// params.put("SN", sutil.getSN());
			// params.put("DATA", allremiwen);
			// clickPosition = msg.arg1;
			// Net.post(true, CashingRecoreds.this, Constant.POST_URL
			// + "/user.api.php", params, mHandler,
			// Constant.NET_ACTION_USERINFO);
			// } else {
			// Log.e("pkx", "1111mutibut!=1:-----"
			// + records.get(msg.arg1).getMboiid());
			// bda = new BuyrecordDetail(sutil.getuid(), records.get(
			// msg.arg1).getOid(), records.get(msg.arg1)
			// .getMboiid());
			// String remingwen = Net.gson.toJson(bda);
			// Log.e("pkx", "明文：" + remingwen);
			// String remiwen = MDUtils.MDEncode(sutil.getuserKEY(),
			// remingwen);
			// PublicAllAuth repaa = new PublicAllAuth("order_info",
			// remiwen);
			// String allremingwen = Net.gson.toJson(repaa);
			// String allremiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
			// allremingwen);
			// RequestParams params = new RequestParams();
			// params.put("SID", sutil.getSID());
			// params.put("DATA", allremiwen);
			// clickPosition = msg.arg1;
			// Net.post(true, CashingRecoreds.this, Constant.POST_URL
			// + "/user.api.php", params, mHandler,
			// Constant.NET_ACTION_LOTHIS);// 自购
			// }
			// } else
			if (msg.what == Constant.POST_SUCCESS_STATUS_ONE) {
				if (msg.arg1 == Constant.NET_ACTION_REFRESH) {
					Log.e("pkx", "提现记录" + msg.obj.toString());
					if (cashingList != null && cashingList.getList().size() > 0) {
						cashingList.getList().clear();
					}
					try {
						cashingList = Net.gson.fromJson(msg.obj.toString(),
								CashingRecoredListDto.class);
						page = 1;
						showListAdapter.notifyDataSetChanged();
						showPullList.setMode(Mode.BOTH);
						showPullList.onRefreshComplete();
					} catch (Exception e) {
						Toast.makeText(CashingRecoreds.this, "提现数据刷新异常!",
								Toast.LENGTH_LONG).show();
						// TODO: handle exception
					}
				} else if (msg.arg1 == Constant.NET_ACTION_LOADMORE) {
					try {

						Log.e("pkx", "加载更多记录" + msg.obj.toString());
						CashingRecoredListDto list = Net.gson
								.fromJson(msg.obj.toString(),
										CashingRecoredListDto.class);
						cashingList.getList().addAll(list.getList());
						showListAdapter.notifyDataSetChanged();
						showPullList.onRefreshComplete();
						page++;
					} catch (Exception e) {
						Toast.makeText(CashingRecoreds.this, "提现数据加载异常!",
								Toast.LENGTH_LONG).show();
						// TODO: handle exception
					}
				} else if (msg.arg1 == Constant.NET_ACTION_BUYRECORD) {
					Toast.makeText(CashingRecoreds.this, "取消成功!",
							Toast.LENGTH_SHORT).show();
					BetRecord braRE = new BetRecord(sutil.getuid(), 1);
					String mingwenRE = Net.gson.toJson(braRE);
					Log.e("pkx", "明文：" + mingwenRE);
					String miwenRE = MDUtils.MDEncode(sutil.getuserKEY(),
							mingwenRE);
					PublicAllAuth paaRE = new PublicAllAuth("withdrawal_list",
							miwenRE);
					String allRE = Net.gson.toJson(paaRE);
					String allmiwenRE = MDUtils.MDEncode(sutil.getdeviceKEY(),
							allRE);
					RequestParams pa = new RequestParams();
					pa.put("SID", sutil.getSID());
					pa.put("SN", sutil.getSN());
					pa.put("DATA", allmiwenRE);
					Net.post(false, CashingRecoreds.this, Constant.POST_URL
							+ "/user.api.php", pa, mHandler,
							Constant.NET_ACTION_REFRESH);
				}
			} else if (msg.what == Constant.POST_SUCCESS_STATUS_ZERO) {
				if (msg.arg1 == Constant.NET_ACTION_BUYRECORD) {
					Toast.makeText(CashingRecoreds.this, "取消提现失败!",
							Toast.LENGTH_SHORT).show();
				} else {
					showPullList.onRefreshComplete();
					showPullList.setMode(Mode.PULL_FROM_START);
				}
			}
		}
	}

	private void alertMoneyLess() {
		final AlertDialog alert = new AlertDialog.Builder(this, R.style.dialog)
				.create();
		alert.show();
		alert.getWindow().setContentView(R.layout.exit_dialog);

		TextView title = (TextView) alert.findViewById(R.id.title);
		title.setText("确认取消提现？");
		View okButton = alert.findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// BetRecord braRE = new BetRecord(sutil.getuid(), page + 1);
				CancelCashingAuth braRE = new CancelCashingAuth(sutil.getuid(),
						String.valueOf(cashingList.getList().get(selectIndex)
								.getApply_id()));
				String mingwenRE = Net.gson.toJson(braRE);
				Log.e("pkx", "明文：" + mingwenRE);
				String miwenRE = MDUtils.MDEncode(sutil.getuserKEY(), mingwenRE);
				PublicAllAuth paaRE = new PublicAllAuth("cancelWithdrawal",
						miwenRE);
				String allRE = Net.gson.toJson(paaRE);
				String allmiwenRE = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allRE);
				RequestParams pa = new RequestParams();
				pa.put("SID", sutil.getSID());
				pa.put("SN", sutil.getSN());
				pa.put("DATA", allmiwenRE);
				Net.post(false, CashingRecoreds.this, Constant.POST_URL
						+ "/user.api.php", pa, mHandler,
						Constant.NET_ACTION_BUYRECORD);
				alert.dismiss();
			}
		});

		View cancelButton = alert.findViewById(R.id.cancel);
		cancelButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});
	}
}
