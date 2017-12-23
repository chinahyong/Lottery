package com.pkx.lottery;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.pkx.lottery.bean.ImageCode;
import com.pkx.lottery.dto.PublicAllAuth;
import com.pkx.lottery.dto.RepayDto;
import com.pkx.lottery.dto.lott.details.ChormRecordDetail;
import com.pkx.lottery.dto.lott.details.FootballLott;
import com.pkx.lottery.utils.MDUtils;
import com.pkx.lottery.utils.Net;
import com.pkx.lottery.utils.SharePreferenceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class BuyrecoredDetails extends Activity {
	private Intent mIntent;
	private int type;
	private String json;
	private ListView lottList;
	private BaseAdapter adapter;
	private LayoutInflater inflater;
	private ChormRecordDetail cdtail;
	private String orderStatus;
	private TextView repay;
	// private ChormRecordDetail detail;
	private SharePreferenceUtil sutil;
	private MyHandler mHandler;
	private String oid = "";
	//
	public void clickBack(View view) {
	super.onBackPressed();
}
	private TextView tv_buyrecored_ddnum, tv_buyrecored_ddata,
			tv_buyrecored_dmony, tv_buyrecored_qihao, tv_buyrecored_tdate,
			tv_buyrecordd_dtype;
	private ImageView iv_buyrecored_cptb;
	private ImageCode imagecode = new ImageCode();
	private HashMap<String, Integer> hashmap;
	private int screenWidth, screenHeight;

	//

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buyrecored_details);
		//
		screen();
		initViews();
		//
	}

	private void alertRepayDialog() {
		final AlertDialog alert = new AlertDialog.Builder(this).create();
		alert.show();
		alert.getWindow().setContentView(R.layout.exit_dialog);

		TextView title = (TextView) alert.findViewById(R.id.title);
		title.setText("确认支付？");
		View okButton = alert.findViewById(R.id.ok);
		okButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				RepayDto bda = new RepayDto(oid, "", sutil.getuid());
				String remingwen = Net.gson.toJson(bda);
				Log.e("pkx", "明文：" + remingwen);
				String remiwen = MDUtils.MDEncode(sutil.getuserKEY(), remingwen);
				PublicAllAuth repaa = new PublicAllAuth("BalancePay", remiwen);
				String allremingwen = Net.gson.toJson(repaa);
				String allremiwen = MDUtils.MDEncode(sutil.getdeviceKEY(),
						allremingwen);
				RequestParams params = new RequestParams();
				params.put("SID", sutil.getSID());
				params.put("SN", sutil.getSN());
				params.put("DATA", allremiwen);
				Net.post(true, BuyrecoredDetails.this, Constant.POST_URL
						+ "/pay.api.php", params, mHandler,
						Constant.NET_ACTION_USERINFO);
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

	static class ViewHolder {
		View priceView;
		TextView orderID;
		TextView payTime;
		TextView payPrice;
		TextView bund;
		TextView orderStatus;
		TextView betInfo;
		TextView buyMulty;
		TextView prizeInfo;
		//
		TextView tv_lott_datail_fs;
		TextView tv_lottdatail_piaohao;
		LinearLayout ll_lott_detail_kjjl;
	}

	//
	private void screen() {
		//
		WindowManager wm = this.getWindowManager();
		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();
		//
		tv_buyrecored_ddnum = (TextView) this
				.findViewById(R.id.tv_buyrecored_ddnum);
		tv_buyrecored_ddata = (TextView) this
				.findViewById(R.id.tv_buyrecored_ddata);
		tv_buyrecored_dmony = (TextView) this
				.findViewById(R.id.tv_buyrecored_dmony);
		tv_buyrecored_qihao = (TextView) this
				.findViewById(R.id.tv_buyrecored_qihao);
		tv_buyrecored_tdate = (TextView) this
				.findViewById(R.id.tv_buyrecored_tdate);
		tv_buyrecordd_dtype = (TextView) this
				.findViewById(R.id.tv_buyrecordd_dtype);
		iv_buyrecored_cptb = (ImageView) this
				.findViewById(R.id.iv_buyrecored_cptb);
		iv_buyrecored_cptb.getLayoutParams().width = screenWidth / 7;
		iv_buyrecored_cptb.getLayoutParams().height = screenWidth / 7;
		//
		hashmap = imagecode.imageCode();
		//
	}

	//
	private void initViews() {
		mHandler = new MyHandler();
		mIntent = getIntent();
		sutil = new SharePreferenceUtil(this);
		json = mIntent.getStringExtra("json");
		Log.i("qt_json", json);
		if (json != null && json.length() > 0) {
			cdtail = Net.gson.fromJson(json, ChormRecordDetail.class);
		}
		type = Integer.valueOf(mIntent.getStringExtra("type"));
		//
		orderStatus = mIntent.getStringExtra("orderStatus");
		repay = (TextView) findViewById(R.id.repay);
		if (cdtail != null && cdtail.getData() != null
				&& cdtail.getData().size() > 0
				&& cdtail.getData().get(0).getOid() != null) {
			oid = cdtail.getData().get(0).getOid();
		}
		repay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alertRepayDialog();
			}
		});
		if (mIntent.getBooleanExtra("canrepay", false)) {
			repay.setVisibility(View.VISIBLE);
		}
		inflater = getLayoutInflater();
		lottList = (ListView) findViewById(R.id.lottList);
		adapter = new BaseAdapter() {

			//
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				ViewHolder holdler = null;
				//
				if (convertView == null) {
					convertView = inflater.inflate(
							R.layout.num_lott_detail_item, null);
					holdler = new ViewHolder();
					holdler.betInfo = (TextView) convertView
							.findViewById(R.id.betInfo);
					holdler.bund = (TextView) convertView
							.findViewById(R.id.bund);
					holdler.buyMulty = (TextView) convertView
							.findViewById(R.id.buyMulty);
					holdler.orderID = (TextView) convertView
							.findViewById(R.id.orderID);
					holdler.orderStatus = (TextView) convertView
							.findViewById(R.id.orderStatus);
					holdler.payPrice = (TextView) convertView
							.findViewById(R.id.payPrice);
					holdler.payTime = (TextView) convertView
							.findViewById(R.id.payTime);
					holdler.prizeInfo = (TextView) convertView
							.findViewById(R.id.prizeInfo);
					holdler.priceView = convertView
							.findViewById(R.id.priceView);
					//
					holdler.tv_lott_datail_fs = (TextView) convertView
							.findViewById(R.id.tv_lott_datail_fs);
					holdler.tv_lottdatail_piaohao = (TextView) convertView
							.findViewById(R.id.tv_lottdatail_piaohao);
					holdler.ll_lott_detail_kjjl = (LinearLayout) convertView
							.findViewById(R.id.ll_lott_detail_kjjl);
					//
					convertView.setTag(holdler);
				} else {
					holdler = (ViewHolder) convertView.getTag();
				}
				// if
				// ("3".equals(cdtail.getData().get(position).getLottery_type()))
				// {
				// holdler.priceView.setVisibility(View.GONE);
				// } else {
				// holdler.priceView.setVisibility(View.VISIBLE);
				// }
				// 订单编号
				tv_buyrecored_ddnum.setText("订单编号："
						+ cdtail.getOrder_info().getNo());
				// 订单时间
				tv_buyrecored_ddata.setText("投注时间："
						+ cdtail.getOrder_info().getPay_date());
				// 订单总金额
				tv_buyrecored_dmony.setText("订单金额：" + "￥"
						+ cdtail.getOrder_info().getBet_amount());
				// 彩图
				String image_code = "";
				try {
					//
					if (hashmap != null
							&& hashmap.size() > 0
							&& hashmap.get(cdtail.getOrder_info()
									.getLottery_type()) != null) {
						image_code = hashmap.get(
								cdtail.getOrder_info().getLottery_type())
								.toString();
						iv_buyrecored_cptb.setImageResource(Integer
								.parseInt(image_code));
					}
					//
				} catch (Exception e) {
					e.printStackTrace();
				}
				// 期号
				if (cdtail.getOrder_info().getPeroid_name() != null
						&& !"null".equals(cdtail.getOrder_info()
								.getPeroid_name())) {
					tv_buyrecored_qihao.setText("彩票期号："
							+ cdtail.getOrder_info().getPeroid_name());
				}
				// 开将日期
				if (cdtail.getOrder_info().getRes_date() != null
						&& !"".equals(cdtail.getOrder_info().getRes_date())
						&& !"null".equals(cdtail.getOrder_info().getRes_date())) {
					tv_buyrecored_tdate.setText("开奖时间："
							+ cdtail.getOrder_info().getRes_date());
				}
				// 彩票是否追号
				if (cdtail.getOrder_info().getBuy_method() != null
						&& !"".equals(cdtail.getOrder_info().getBuy_method() != null)) {
					if (cdtail.getOrder_info().getBuy_method().equals("2")) {
						tv_buyrecordd_dtype.setText("购彩类别：追号");
					} else if (cdtail.getOrder_info().getBuy_method()
							.equals("0")) {
						tv_buyrecordd_dtype.setText("购彩类别：自购");
					}
				}
				// 开奖号码
				if (cdtail.getOrder_info().getLottery_type().equals("1")) {
					// 双色球
					String ssq = cdtail.getOrder_info().getBonus_number();
					Log.i("qt_json", "Bonus_number-" + ssq);
					holdler.ll_lott_detail_kjjl.removeAllViews();// 清空列表
					if (ssq != null && !"".equals(ssq)) {
						String[] shu7 = ssq.split(" ");
						if (shu7.length == 7) {
							for (int y = 0; y < shu7.length; y++) {
								TextView tv_ssq = new TextView(
										BuyrecoredDetails.this);
								if (y == 6) {
									// 蓝球
									Drawable bj_ld = convertView.getResources()
											.getDrawable(R.drawable.blue_rect);
									tv_ssq.setBackgroundDrawable(bj_ld);
									tv_ssq.setGravity(Gravity.CENTER);
									tv_ssq.setTextColor(Color.WHITE);
									tv_ssq.setText(shu7[y]);
								} else {
									// 红球
									Drawable bj_hd = convertView.getResources()
											.getDrawable(R.drawable.black_ball);
									tv_ssq.setBackgroundDrawable(bj_hd);
									tv_ssq.setGravity(Gravity.CENTER);
									tv_ssq.setTextColor(Color.WHITE);
									tv_ssq.setText(shu7[y]);
								}
								//
								holdler.ll_lott_detail_kjjl.addView(tv_ssq);
								tv_ssq.getLayoutParams().width = screenWidth / 11;
								tv_ssq.getLayoutParams().height = screenWidth / 11;
								//
							}
						}
					}
				} else if (cdtail.getOrder_info().getLottery_type().equals("2")) {
					// 七乐彩
					String ssq = cdtail.getOrder_info().getBonus_number();
					Log.i("qt_json", "Bonus_number-" + ssq);
					holdler.ll_lott_detail_kjjl.removeAllViews();// 清空列表
					if (ssq != null && !"".equals(ssq)) {
						String[] shu7 = ssq.split(" ");
						if (shu7.length >= 7) {
							for (int y = 0; y < shu7.length; y++) {
								TextView tv_ssq = new TextView(
										BuyrecoredDetails.this);
								if (y == 7) {
									// 蓝球
									Drawable bj_ld = convertView.getResources()
											.getDrawable(R.drawable.blue_rect);
									tv_ssq.setBackgroundDrawable(bj_ld);
									tv_ssq.setGravity(Gravity.CENTER);
									tv_ssq.setTextColor(Color.WHITE);
									tv_ssq.setText(shu7[y]);
								} else {
									// 红球
									Drawable bj_hd = convertView.getResources()
											.getDrawable(R.drawable.black_ball);
									tv_ssq.setBackgroundDrawable(bj_hd);
									tv_ssq.setGravity(Gravity.CENTER);
									tv_ssq.setTextColor(Color.WHITE);
									tv_ssq.setText(shu7[y]);
								}
								//
								holdler.ll_lott_detail_kjjl.addView(tv_ssq);
								tv_ssq.getLayoutParams().width = screenWidth / 11;
								tv_ssq.getLayoutParams().height = screenWidth / 11;
								//
							}
						}
					}
				} else if (cdtail.getOrder_info().getLottery_type().equals("4")
						|| cdtail.getOrder_info().getLottery_type().equals("5")) {
					// 快三
					String ssq = cdtail.getOrder_info().getBonus_number();
					Log.i("qt_json", "Bonus_number-" + ssq);
					holdler.ll_lott_detail_kjjl.removeAllViews();// 清空列表
					if (ssq != null && !"".equals(ssq)) {
						String[] shu7 = ssq.split(" ");
						//
						for (int y = 0; y < shu7.length; y++) {
							TextView tv_ssq = new TextView(
									BuyrecoredDetails.this);
							//
							int isg = 0;
							if (shu7[y].equals("1") || shu7[y].equals("01")) {
								Drawable bj_num1 = convertView.getResources()
										.getDrawable(R.drawable.num_1);
								tv_ssq.setBackgroundDrawable(bj_num1);
								isg = 1;
							} else if (shu7[y].equals("2")
									|| shu7[y].equals("02")) {
								Drawable bj_num2 = convertView.getResources()
										.getDrawable(R.drawable.num_2);
								tv_ssq.setBackgroundDrawable(bj_num2);
								isg = 1;
							} else if (shu7[y].equals("3")
									|| shu7[y].equals("03")) {
								Drawable bj_num3 = convertView.getResources()
										.getDrawable(R.drawable.num_3);
								tv_ssq.setBackgroundDrawable(bj_num3);
								isg = 1;
							} else if (shu7[y].equals("4")
									|| shu7[y].equals("04")) {
								Drawable bj_num4 = convertView.getResources()
										.getDrawable(R.drawable.num_4);
								tv_ssq.setBackgroundDrawable(bj_num4);
								isg = 1;
							} else if (shu7[y].equals("5")
									|| shu7[y].equals("05")) {
								Drawable bj_num5 = convertView.getResources()
										.getDrawable(R.drawable.num_5);
								tv_ssq.setBackgroundDrawable(bj_num5);
								isg = 1;
							} else if (shu7[y].equals("6")
									|| shu7[y].equals("06")) {
								Drawable bj_num6 = convertView.getResources()
										.getDrawable(R.drawable.num_6);
								tv_ssq.setBackgroundDrawable(bj_num6);
								isg = 1;
							} else {

							}
							tv_ssq.setGravity(Gravity.CENTER);
							tv_ssq.setTextColor(Color.WHITE);
							// tv_ssq.setText(shu7[y]);
							//
							if (isg == 1) {
								holdler.ll_lott_detail_kjjl.addView(tv_ssq);
								LayoutParams params = new LayoutParams(
										LayoutParams.WRAP_CONTENT,
										LayoutParams.WRAP_CONTENT);
								params.width = screenWidth / 9;
								params.height = screenWidth / 9;
								params.setMargins(5, 5, 5, 5);
								tv_ssq.setLayoutParams(params);
							}
							//
						}
						//
					}

				} else {
					// 其他
					String ssq = cdtail.getOrder_info().getBonus_number();
					Log.i("qt_json", "Bonus_number-" + ssq);
					holdler.ll_lott_detail_kjjl.removeAllViews();// 清空列表
					if (ssq != null && !"".equals(ssq)) {
						String[] shu7 = ssq.split(" ");
						for (int y = 0; y < shu7.length; y++) {
							if (shu7 != null && !"".equals(shu7)
									&& !"null".equals(shu7)) {
								TextView tv_ssq = new TextView(
										BuyrecoredDetails.this);
								//
								Drawable bj_n = convertView.getResources()
										.getDrawable(R.drawable.black_ball);
								tv_ssq.setBackgroundDrawable(bj_n);
								tv_ssq.setGravity(Gravity.CENTER);
								tv_ssq.setTextColor(Color.WHITE);
								tv_ssq.setText(shu7[y]);
								//
								holdler.ll_lott_detail_kjjl.addView(tv_ssq);
								tv_ssq.getLayoutParams().width = screenWidth / 11;
								tv_ssq.getLayoutParams().height = screenWidth / 11;
								//
							}
						}
						//
					}
				}
				//
				holdler.betInfo.setText(cdtail.getData().get(position)
						.getBetData_str());
				//
				if (cdtail.getData().get(position).getTicketID() != null
						&& !"".equals(cdtail.getData().get(position)
								.getTicketID())
						&& !"null".equals(cdtail.getData().get(position)
								.getTicketID())) {
					if (cdtail.getData().get(position).getPay_status() != null
							&& !"".equals(cdtail.getData().get(position)
									.getPay_status())
							&& !"null".equals(cdtail.getData().get(position)
									.getPay_status())
							&& "1".equals(cdtail.getData().get(position)
									.getPay_status())) {
						if (cdtail.getData().get(position).getStatus() != null
								&& !"".equals(cdtail.getData().get(position)
										.getStatus())
								&& !"null".equals(cdtail.getData()
										.get(position).getStatus())
								&& "1".equals(cdtail.getData().get(position)
										.getStatus())) {
							if (cdtail.getData().get(position)
									.getTicket_status() != null
									&& "2".equals(cdtail.getData()
											.get(position).getTicket_status())) {
								if (cdtail.getData().get(position)
										.getBonus_status() != null
										&& "0".equals(cdtail.getData()
												.get(position)
												.getBonus_status())) {
									holdler.bund.setText("等待开奖");
								} else if (cdtail.getData().get(position)
										.getBonus_status() != null
										&& "1".equals(cdtail.getData()
												.get(position)
												.getBonus_status())) {
									holdler.bund.setText("未中奖");
								} else if (cdtail.getData().get(position)
										.getBonus_status() != null
										&& "2".equals(cdtail.getData()
												.get(position)
												.getBonus_status())) {
									holdler.bund.setText("已中奖未发奖");
								} else if (cdtail.getData().get(position)
										.getBonus_status() != null
										&& "3".equals(cdtail.getData()
												.get(position)
												.getBonus_status())) {
									holdler.bund.setText("已发奖");
								} else {
									holdler.bund.setText("未中奖");
								}
							} else if (cdtail.getData().get(position)
									.getTicket_status() != null
									&& "1".equals(cdtail.getData()
											.get(position).getTicket_status())) {
								holdler.bund.setText("投注中");
							} else if (cdtail.getData().get(position)
									.getTicket_status() != null
									&& "3".equals(cdtail.getData()
											.get(position).getTicket_status())) {
								holdler.bund.setText("投注失败");
							} else {
								holdler.bund.setText("投注中");
							}
						} else {
							holdler.bund.setText("投注中");
						}
					}
				}
				//
				holdler.buyMulty.setText(cdtail.getData().get(position)
						.getBet_multi()
						+ "倍");
				holdler.orderID.setText(cdtail.getOrder_info().getNo());

				holdler.orderStatus.setText(orderStatus);
				String zje = "", piaohao = "--";
				if (
				// cdtail.getOrder_info().getPeroid_name()!=null
				cdtail.getData().get(position).getTicketID() != null
						&& !"".equals(cdtail.getData().get(position)
								.getTicketID())
						&& !"null".equals(cdtail.getData().get(position)
								.getTicketID())) {
					// 有票号
					zje = cdtail.getData().get(position).getAmount();
					piaohao = cdtail.getData().get(position).getTicketID();
				} else {
					// 没票号
					zje = cdtail.getData().get(position).getBet_amount();
				}
				if (zje != null && !"".equals(zje) && !"null".equals(zje)) {
					holdler.payPrice.setText(zje + "元");
				}
				holdler.tv_lottdatail_piaohao.setText(piaohao);
				holdler.payTime.setText(cdtail.getData().get(position)
						.getOrder_date());
				if (cdtail.getData().get(position).getBonus_tax() == null) {
					holdler.prizeInfo.setText("0元");
				} else {
					holdler.prizeInfo.setText(cdtail.getData().get(position)
							.getBonus_tax()
							+ "元");
				}
				//
				holdler.tv_lott_datail_fs.setText(cdtail.getData()
						.get(position).getBetStr());
				//
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
				return cdtail.getData().size();
			}
		};
		lottList.setDividerHeight(8);
		lottList.setAdapter(adapter);
		switch (type) {
		case 1:
			ChormRecordDetail chro = Net.gson.fromJson(json,
					ChormRecordDetail.class);
			break;
		case 2:
			ChormRecordDetail seven = Net.gson.fromJson(json,
					ChormRecordDetail.class);
			break;
		case 3:
			ChormRecordDetail dimention = Net.gson.fromJson(json,
					ChormRecordDetail.class);
			break;
		case 4:
			ChormRecordDetail fast = Net.gson.fromJson(json,
					ChormRecordDetail.class);
			break;
		case 50:
			FootballLott fla = Net.gson.fromJson(json, FootballLott.class);
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
				if (msg.arg1 == Constant.NET_ACTION_USERINFO) {
					Log.e("pkx", "支付成功");
					JSONObject jo = (JSONObject) msg.obj;
					try {
//						Toast.makeText(BuyrecoredDetails.this,
//								String.valueOf(jo.get("error_msg")),
//								Toast.LENGTH_SHORT).show();
//						Constant.alertWarning(BuyrecoredDetails.this,
//								String.valueOf(jo.get("error_msg")));
						if(jo.getInt("err")==20000){
							final AlertDialog alert = new AlertDialog.Builder(BuyrecoredDetails.this).create();
							alert.show();
							alert.setCancelable(false);
							alert.getWindow().setContentView(R.layout.warning_dialog);
							TextView title = (TextView) alert.findViewById(R.id.title);
							title.setText("支付成功！");
							View okButton = alert.findViewById(R.id.ok);
							okButton.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									finish();
									alert.dismiss();
								}
							});
							return;
						}

						final AlertDialog alert = new AlertDialog.Builder(BuyrecoredDetails.this).create();
						alert.show();
						alert.setCancelable(false);
						alert.getWindow().setContentView(R.layout.warning_dialog);
						TextView title = (TextView) alert.findViewById(R.id.title);
						title.setText(String.valueOf(jo.get("error_msg")));
						View okButton = alert.findViewById(R.id.ok);
						okButton.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								finish();
								alert.dismiss();
							}
						});
					
					} catch (JSONException e) {
						Toast.makeText(BuyrecoredDetails.this, "支付异常!",
								Toast.LENGTH_SHORT).show();
						e.printStackTrace();
					}
					repay.setVisibility(View.INVISIBLE);
					setResult(Constant.REPAY_REQUEST);
				}

			}
		}
	}
}
