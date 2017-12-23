package com.pkx.lottery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.pkx.lottery.bean.ImageCode;
import com.pkx.lottery.dto.lott.details.ChormRecordDetail;
import com.pkx.lottery.dto.lott.details.FootballLott;
import com.pkx.lottery.utils.Net;

import java.util.HashMap;
/**
 * 合买发起
 * @author Administrator
 *
 */
public class BuyrecoredMasterCorpDetails extends Activity {
	private Intent mIntent;
	private int type;
	private String json;
	// private String orderStatus;
	private TextView intro, process, orderstatus, corper, buyMulty, buyDetail,
			buyMultyDetails, commission, keepAmount, 
			lottType, corpTime,
			corpAmount;
	private static String[] status_set = { "68彩票", "已生成", "已出票", "未出票", "过期",
			"已关闭", "合买进行中", "已发奖", "过期已支付未出票", "过期已支付已退款" };
	//
	public void clickBack(View view) {
	super.onBackPressed();
}
	private TextView tv_mastercorp_title,tv_mastercorp_dmony,tv_mastercorp_kaijiang;
	private TextView tv_mastercorp_qishu,tv_mastercorp_mony,tv_mastercorp_hdate;
	private ImageView iv_mastercorp_logo;
	private LinearLayout ll_buyrecored_xq,ll_buyrecord_kjhm;
	private ImageCode imagecode=new ImageCode();
	private HashMap<String , Integer> hashmap;
	private int screenWidth,screenHeight;
	//
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buyrecored_master_corp_details);
		//
		screen();
		//
		initViews();

	}

	// /lottery/res/layout/buyrecored_football_buy_detail.xml

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
	}

	//
	private void initViews() {
		mIntent = getIntent();
		type = Integer.valueOf(mIntent.getStringExtra("type"));
		json = mIntent.getStringExtra("json");
		Log.i("hm_json", json);
		// orderStatus = mIntent.getStringExtra("orderStatus");
		intro = (TextView) findViewById(R.id.intro);
		process = (TextView) findViewById(R.id.process);
		orderstatus = (TextView) findViewById(R.id.orderstatus);
		corper = (TextView) findViewById(R.id.corper);
		buyMulty = (TextView) findViewById(R.id.buyMulty);
		buyDetail = (TextView) findViewById(R.id.buyDetail);
		buyMultyDetails = (TextView) findViewById(R.id.buyMultyDetails);
		commission = (TextView) findViewById(R.id.commission);
		keepAmount = (TextView) findViewById(R.id.keepAmount);
		lottType = (TextView) findViewById(R.id.lottType);
		corpTime = (TextView) findViewById(R.id.corpTime);
		corpAmount = (TextView) findViewById(R.id.corpAmount);
		ChormRecordDetail chro = Net.gson.fromJson(json,
				ChormRecordDetail.class);
		intro.setText(chro.getMultibuy_info().getIntro());
		try {
			orderstatus.setText(status_set[Integer.valueOf(chro.getOrder_info()
					.getOrder_status())]);
		} catch (Exception e) {
			orderstatus.setText("订单处理中");
		}
		process.setText(chro.getMultibuy_info().getProcess());
		corpTime.setText(chro.getData().get(0).getOrder_date());
		corpAmount.setText(mIntent.getStringExtra("total") + "元");
		buyMultyDetails.setText("此方案共" + chro.getMultibuy_info().getMax_count()
				+ "份，每份" + chro.getMultibuy_info().getUnit_price() + "元");
		commission.setText(chro.getMultibuy_info().getCommission() + "%");
		keepAmount.setText(chro.getMultibuy_info().getKeep_count() + "份");
		//
		//订单号
		tv_mastercorp_title.setText("订单编号："+chro.getOrder_info().getNo());
		//合买总额
		tv_mastercorp_dmony.setText("合买总额：￥"+chro.getOrder_info().getBet_amount());
		//彩票期号：
		if(chro.getOrder_info().getPeroid_name()!=null&&!"null".equals(chro.getOrder_info().getPeroid_name())){
			tv_mastercorp_qishu.setText("彩票期号："+chro.getOrder_info().getPeroid_name());
		}
		//开奖时间
		if(
			chro.getOrder_info().getRes_date()!=null
			&&!"".equals(chro.getOrder_info().getRes_date())
			&&!"null".equals(chro.getOrder_info().getRes_date())
		){
			tv_mastercorp_kaijiang.setText("开奖时间："+chro.getOrder_info().getRes_date());
		}
		//认购金额
		int rgje=0;
		try{
			String fens=chro.getOrder_info().getOffer_count();
			String zonge=chro.getOrder_info().getBet_amount();
			String zongfen=chro.getOrder_info().getMax_count();
			Log.i("hm_json", fens+"--"+zonge+"--"+zongfen);
			rgje=(int) ((int)Float.parseFloat(zonge)*Float.parseFloat(fens)/Float.parseFloat(zongfen));
		}catch(Exception e){
			e.printStackTrace();
			Log.i("hm_json", e.toString());
		}
		tv_mastercorp_mony.setText("认购金额："+rgje);
		//开始时间
		tv_mastercorp_hdate.setText("开始时间："+chro.getOrder_info().getOrder_date());
		//彩图
		String image_code="";
		try{
			//
			if(hashmap!=null&&hashmap.size()>0&&hashmap.get(chro.getOrder_info().getLottery_type())!=null){
				image_code=hashmap.get(chro.getOrder_info().getLottery_type()).toString();
				iv_mastercorp_logo.setImageResource(Integer.parseInt(image_code));
			}
			//
		}catch(Exception e){
			e.printStackTrace();
		}
		//开奖号码
		String kchm=chro.getOrder_info().getBonus_number();
		//
		if(chro.getOrder_info().getLottery_type().equals("1")){
			//双色球
			String ssq=chro.getOrder_info().getBonus_number();
			Log.i("qt_json", "Bonus_number-" + ssq);
			ll_buyrecord_kjhm.removeAllViews();//清空列表
			if(ssq!=null&&!"".equals(ssq)){
				//
				String[] shu7=ssq.split(" ");
				if(shu7.length==7){
					for(int y=0;y<shu7.length;y++){
						TextView tv_ssq=new TextView(BuyrecoredMasterCorpDetails.this);			
						if(y==6){
							//蓝球				
							Drawable bj_ld=this.getResources().getDrawable(R.drawable.blue_rect);
							tv_ssq.setBackgroundDrawable(bj_ld);
							tv_ssq.setGravity(Gravity.CENTER);
							tv_ssq.setTextColor(Color.WHITE);
							tv_ssq.setText(shu7[y]);								
						}else{
							//红球
							Drawable bj_hd=this.getResources().getDrawable(R.drawable.black_ball);
							tv_ssq.setBackgroundDrawable(bj_hd);
							tv_ssq.setGravity(Gravity.CENTER);
							tv_ssq.setTextColor(Color.WHITE);
							tv_ssq.setText(shu7[y]);
						}
						//
						ll_buyrecord_kjhm.addView(tv_ssq);
						tv_ssq.getLayoutParams().width=screenWidth/11;
						tv_ssq.getLayoutParams().height=screenWidth/11;
						//
					}
				}
			}
		}else if(chro.getOrder_info().getLottery_type().equals("2")){
			//七乐彩
			String ssq=chro.getOrder_info().getBonus_number();
			Log.i("qt_json", "Bonus_number-" + ssq);
			ll_buyrecord_kjhm.removeAllViews();//清空列表
			if(ssq!=null&&!"".equals(ssq)){
				String[] shu7=ssq.split(" ");
				if(shu7.length>=7){
					for(int y=0;y<shu7.length;y++){
						TextView tv_ssq=new TextView(BuyrecoredMasterCorpDetails.this);										
						if(y==7){
							//蓝球	
							Drawable bj_ld=this.getResources().getDrawable(R.drawable.blue_rect);
							tv_ssq.setBackgroundDrawable(bj_ld);
							tv_ssq.setGravity(Gravity.CENTER);
							tv_ssq.setTextColor(Color.WHITE);
							tv_ssq.setText(shu7[y]);								
						}else{
							//红球
							Drawable bj_hd=this.getResources().getDrawable(R.drawable.black_ball);
							tv_ssq.setBackgroundDrawable(bj_hd);
							tv_ssq.setGravity(Gravity.CENTER);
							tv_ssq.setTextColor(Color.WHITE);
							tv_ssq.setText(shu7[y]);
						}							
						//
						ll_buyrecord_kjhm.addView(tv_ssq);
						tv_ssq.getLayoutParams().width=screenWidth/11;
						tv_ssq.getLayoutParams().height=screenWidth/11;
						//
					}
				}
			}
		}else if(
				chro.getOrder_info().getLottery_type().equals("4")
				||chro.getOrder_info().getLottery_type().equals("5")
		){
			//快三
			String ssq=chro.getOrder_info().getBonus_number();
			Log.i("qt_json", "Bonus_number-" + ssq);
			ll_buyrecord_kjhm.removeAllViews();//清空列表
			if(ssq!=null&&!"".equals(ssq)){
				String[] shu7=ssq.split(" ");
				//
				for(int y=0;y<shu7.length;y++){
					TextView tv_ssq=new TextView(BuyrecoredMasterCorpDetails.this);																	
					//
					int isg=0;
					if(shu7[y].equals("1")||shu7[y].equals("01")){
						Drawable bj_d1=this.getResources().getDrawable(R.drawable.num_1);
						tv_ssq.setBackgroundDrawable(bj_d1);
						isg=1;
					}else if(shu7[y].equals("2")||shu7[y].equals("02")){
						Drawable bj_d2=this.getResources().getDrawable(R.drawable.num_2);
						tv_ssq.setBackgroundDrawable(bj_d2);
						isg=1;
					}else if(shu7[y].equals("3")||shu7[y].equals("03")){
						Drawable bj_d3=this.getResources().getDrawable(R.drawable.num_3);
						tv_ssq.setBackgroundDrawable(bj_d3);
						isg=1;
					}else if(shu7[y].equals("4")||shu7[y].equals("04")){
						Drawable bj_d4=this.getResources().getDrawable(R.drawable.num_4);
						tv_ssq.setBackgroundDrawable(bj_d4);
						isg=1;
					}else if(shu7[y].equals("5")||shu7[y].equals("05")){
						Drawable bj_d5=this.getResources().getDrawable(R.drawable.num_5);
						tv_ssq.setBackgroundDrawable(bj_d5);
						isg=1;
					}else if(shu7[y].equals("6")||shu7[y].equals("06")){
						Drawable bj_d6=this.getResources().getDrawable(R.drawable.num_6);
						tv_ssq.setBackgroundDrawable(bj_d6);
						isg=1;
					}else{
						
					}
					tv_ssq.setGravity(Gravity.CENTER);
					tv_ssq.setTextColor(Color.WHITE);
					//tv_ssq.setText(shu7[y]);						
					//
					if(isg==1){
						ll_buyrecord_kjhm.addView(tv_ssq);
						LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
						params.width=screenWidth/9;
						params.height=screenWidth/9;
						params.setMargins(5, 5, 5, 5);
						tv_ssq.setLayoutParams(params);
					}
					//
				}
				//
			}

		}else{
			//其他
			String ssq=chro.getOrder_info().getBonus_number();
			Log.i("qt_json", "Bonus_number-" + ssq);
			ll_buyrecord_kjhm.removeAllViews();//清空列表
			if(ssq!=null&&!"".equals(ssq)){
				String[] shu7=ssq.split(" ");
				for(int y=0;y<shu7.length;y++){
					if(shu7!=null&&!"".equals(shu7)&&!"null".equals(shu7)){
						TextView tv_ssq=new TextView(BuyrecoredMasterCorpDetails.this);																		
						//
						Drawable bj_d=this.getResources().getDrawable(R.drawable.black_ball);
						tv_ssq.setBackgroundDrawable(bj_d);
						tv_ssq.setGravity(Gravity.CENTER);
						tv_ssq.setTextColor(Color.WHITE);
						tv_ssq.setText(shu7[y]);												
						//
						ll_buyrecord_kjhm.addView(tv_ssq);
						tv_ssq.getLayoutParams().width=screenWidth/11;
						tv_ssq.getLayoutParams().height=screenWidth/11;
						//
					}
				}
				//
			}
		}
		//详情
		for(int k=0;k<chro.getData().size();k++){
			TextView tv_k=new TextView(BuyrecoredMasterCorpDetails.this);
			TextView tv_k2=new TextView(BuyrecoredMasterCorpDetails.this);
			String values=chro.getData().get(k).getBetData_str();
			String types=chro.getData().get(k).getBetStr();
			String nums=chro.getData().get(k).getBet_multi()+"倍";
			tv_k.setText(" "+values);
			tv_k2.setText("     "+types+" "+nums);
			//
			ll_buyrecored_xq.addView(tv_k);
			ll_buyrecored_xq.addView(tv_k2);
			//
		}
		//
		switch (type) {
		case 1:
			lottType.setText("双色球");
			break;
		case 2:
			lottType.setText("七乐彩");
			break;
		case 3:
			lottType.setText("3D");
			break;
		case 4:
			lottType.setText("快三");
			break;
		case 50:
			FootballLott fla = Net.gson.fromJson(json, FootballLott.class);
			Log.e("pkx", "足彩json：" + json);
			Log.e("pkx", "status:" + fla.getStatus() + "  betamount:"
					+ fla.getData().getBet_amount() + " orderstatus"
					+ fla.getData().getOrder_status() + "  chuan:"
					+ fla.getData().getChuanGuan() + "  guest:"
					+ fla.getData().getMatch().get(0).getGuestTeam()
					+ "  host:" + fla.getData().getMatch().get(0).getMainTeam());
			// Log.e("pkx",
			// "intro"+fla.getData().getMultibuy_info().getIntro());
			if (fla.getData() == null) {
				Log.e("pkx", "00000000000");
			}
			// if(fla.getData().getOrder_info()==null){
			// Log.e("pkx", "11111111111111");
			// }
			// try {
			// org.json.JSONObject jo=new JSONObject(json);
			// org.json.JSONArray ja=jo.getJSONArray("data");
			// } catch (JSONException e) {
			// e.printStackTrace();
			// }

			// if (fla.getData().getMultibuy_info() == null) {
			// Log.e("pkx",
			// "Multibuy_info()==nullMultibuy_info()==nullMultibuy_info()==null");
			//
			// }
			// if(fla.getData().getMultibuy_info().getIntro()==null){
			// Log.e("pkx", "55555555555");
			// intro.setText("nullllllll");
			// }else{
			process.setText(fla.getMultibuy_info().getProcess());
			intro.setText(fla.getMultibuy_info().getIntro());
			// }

			orderstatus.setText(status_set[Integer.valueOf(fla.getData()
					.getOrder_status())]);

//			 if (fla.getData().getMultibuy_info().getNick_name() == null
//			 || fla.getData().getMultibuy_info().getNick_name()
//			 .equals("")) {
//				 corper.setText("匿名用户");
//			 } else {
//			 corper.setText(fla.getData().getMultibuy_info().getNick_name());
//			 }
			buyMulty.setText(fla.getData().getBet_multi() + "倍");
			buyDetail.setText("点击展开");// 展开投注详情
			// buyMultyDetails.setText("此方案共"
			// + fla.getData().getMultibuy_info().getMax_count() + "份每份"
			// + fla.getData().getMultibuy_info().getUnit_price() + "元");
			// commission.setText(fla.getData().getMultibuy_info().getCommission()
			// + "%");
			// keepAmount.setText("发起人共认购了"
			// + fla.getData().getMultibuy_info().getKeep_count() + "份");
			// +"共"
			// + String.valueOf(Integer.valueOf(fla.getData()
			// .getMultibuy_info().getOffer_count())
			// * Double.valueOf(fla.getData().getMultibuy_info()
			// .getUnit_price()))+"元");
			//
			//
			break;
		}
	}
	//
	private void screen(){
		//
    	WindowManager wm = this.getWindowManager();
    	screenWidth=wm.getDefaultDisplay().getWidth();
    	screenHeight=wm.getDefaultDisplay().getHeight();
    	//
		tv_mastercorp_title=(TextView) this.findViewById(R.id.tv_mastercorp_title);
		tv_mastercorp_dmony=(TextView) this.findViewById(R.id.tv_mastercorp_dmony);
		tv_mastercorp_qishu=(TextView) this.findViewById(R.id.tv_mastercorp_qishu);
		tv_mastercorp_kaijiang=(TextView) this.findViewById(R.id.tv_mastercorp_kaijiang);
		tv_mastercorp_mony=(TextView) this.findViewById(R.id.tv_mastercorp_mony);
		tv_mastercorp_hdate=(TextView) this.findViewById(R.id.tv_mastercorp_hdate);
		iv_mastercorp_logo=(ImageView) this.findViewById(R.id.iv_mastercorp_logo);
		ll_buyrecored_xq=(LinearLayout) this.findViewById(R.id.ll_buyrecored_xq);
		iv_mastercorp_logo.getLayoutParams().width=screenWidth/7;
		iv_mastercorp_logo.getLayoutParams().height=screenWidth/7;
		ll_buyrecord_kjhm=(LinearLayout) this.findViewById(R.id.ll_buyrecord_kjhm);
		//
		hashmap=imagecode.imageCode();
		//
	}
	//
	class MyHandler extends Handler {
		public MyHandler() {
		}

		public MyHandler(Looper L) {
			super(L);
		}

		// 子类必须重写此方法,接受数据
		@Override
		public void handleMessage(Message msg) {
		}
	}
}
