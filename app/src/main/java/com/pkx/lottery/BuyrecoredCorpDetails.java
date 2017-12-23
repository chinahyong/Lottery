package com.pkx.lottery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
 * 合买认购
 * @author Administrator
 *
 */
public class BuyrecoredCorpDetails extends Activity {
	private Intent mIntent;
	private int type;
	private String json;
	// private String orderStatus;
	private TextView intro, process, orderstatus, corper, buyMulty, buyDetail,
			buyMultyDetails, commission, keepAmount, lottType,corpTime,corpAmount;
	private static String[] status_set = { "68彩票", "已生成", "已出票", "未出票", "过期",
			"已关闭", "合买进行中", "已发奖", "过期已支付未出票", "过期已支付已退款" };
	//
	private TextView tv_corpdetails_title,tv_corpdetails_dmony,tv_corpdetails_kjrq;
	private TextView tv_corpdetails_qishu,tv_corpdetails_mony,tv_corpdetails_hdate;
	private ImageView iv_corpdetailos_logo;
	private LinearLayout ll_corpdetails_sj,ll_corpdetails_kjhm;
	private ImageCode imagecode=new ImageCode();
	private HashMap<String , Integer> hashmap;
	private int screenWidth,screenHeight;
	//
	public void clickBack(View view) {
	super.onBackPressed();
}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.buyrecored_corp_details);
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
	private void screen(){
		//
    	WindowManager wm = this.getWindowManager();
    	screenWidth=wm.getDefaultDisplay().getWidth();
    	screenHeight=wm.getDefaultDisplay().getHeight();
    	//
		tv_corpdetails_title=(TextView) this.findViewById(R.id.tv_corpdetails_title);
		tv_corpdetails_dmony=(TextView) this.findViewById(R.id.tv_corpdetails_dmony);
		tv_corpdetails_kjrq=(TextView) this.findViewById(R.id.tv_corpdetails_kjrq);
		tv_corpdetails_qishu=(TextView) this.findViewById(R.id.tv_corpdetails_qishu);
		tv_corpdetails_mony=(TextView) this.findViewById(R.id.tv_corpdetails_mony);
		tv_corpdetails_hdate=(TextView) this.findViewById(R.id.tv_corpdetails_hdate);
		iv_corpdetailos_logo=(ImageView) this.findViewById(R.id.iv_corpdetailos_logo);
		ll_corpdetails_sj=(LinearLayout) this.findViewById(R.id.ll_corpdetails_sj);
		iv_corpdetailos_logo.getLayoutParams().width=screenWidth/7;
		iv_corpdetailos_logo.getLayoutParams().height=screenWidth/7;
		ll_corpdetails_kjhm=(LinearLayout) this.findViewById(R.id.ll_corpdetails_kjhm);
		//
		hashmap=imagecode.imageCode();
		//
	}
	//
	//
	//
	private void initViews() {
		mIntent = getIntent();
		type = Integer.valueOf(mIntent.getStringExtra("type"));
		json = mIntent.getStringExtra("json");
		Log.i("hg_json", json);
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
		corpAmount.setText(mIntent.getStringExtra("total")+"元");
		buyMultyDetails.setText("此方案共"+chro.getMultibuy_info().getMax_count()+"份，每份"+chro.getMultibuy_info().getUnit_price()+"元");
		commission.setText(chro.getMultibuy_info().getCommission()+"%");
		keepAmount.setText(chro.getMultibuy_info().getKeep_count()+"份");
		//订单号
		tv_corpdetails_title.setText("订单编号："+chro.getOrder_info().getNo());
		//合买总额
		tv_corpdetails_dmony.setText("合买总额：￥"+chro.getOrder_info().getBet_amount());
		//开将日期
		if(
			chro.getOrder_info().getRes_date()!=null
			&&!"".equals(chro.getOrder_info().getRes_date())
			&&!"null".equals(chro.getOrder_info().getRes_date())
		){
			tv_corpdetails_kjrq.setText("开奖日期："+chro.getOrder_info().getRes_date());
		}
		//彩票期号：
		if(chro.getOrder_info().getPeroid_name()!=null&&!"null".equals(chro.getOrder_info().getPeroid_name())){
			tv_corpdetails_qishu.setText("彩票期号："+chro.getOrder_info().getPeroid_name());
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
		tv_corpdetails_mony.setText("认购金额："+rgje);
		//开始时间
		tv_corpdetails_hdate.setText("开始时间："+chro.getOrder_info().getOrder_date());
		//彩图
		String image_code="";
		try{
			//
			if(hashmap!=null&&hashmap.size()>0&&hashmap.get(chro.getOrder_info().getLottery_type())!=null){
				image_code=hashmap.get(chro.getOrder_info().getLottery_type()).toString();
				iv_corpdetailos_logo.setImageResource(Integer.parseInt(image_code));
			}
			//
		}catch(Exception e){
			e.printStackTrace();
		}
		//发起者
		if(
			chro.getMultibuy_info().getNick_name()!=null
			&&!"".equals(chro.getMultibuy_info().getNick_name())
			&&!"null".equals(chro.getMultibuy_info().getNick_name())
		){
			corper.setText(chro.getMultibuy_info().getNick_name());
		}else{
			corper.setText("匿名用户");
		}
		//开彩号码
		String kchm=chro.getOrder_info().getBonus_number();
		//
		if(chro.getOrder_info().getLottery_type().equals("1")){
			//双色球
			String ssq=chro.getOrder_info().getBonus_number();
			Log.i("qt_json", "Bonus_number-" + ssq);
			ll_corpdetails_kjhm.removeAllViews();//清空列表
			if(ssq!=null&&!"".equals(ssq)){
				String[] shu7=ssq.split(" ");
				if(shu7.length==7){
					for(int y=0;y<shu7.length;y++){
						TextView tv_ssq=new TextView(BuyrecoredCorpDetails.this);			
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
						ll_corpdetails_kjhm.addView(tv_ssq);
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
			ll_corpdetails_kjhm.removeAllViews();//清空列表
			if(ssq!=null&&!"".equals(ssq)){
				String[] shu7=ssq.split(" ");
				if(shu7.length>=7){
					for(int y=0;y<shu7.length;y++){
						TextView tv_ssq=new TextView(BuyrecoredCorpDetails.this);										
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
						ll_corpdetails_kjhm.addView(tv_ssq);
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
			ll_corpdetails_kjhm.removeAllViews();//清空列表
			if(ssq!=null&&!"".equals(ssq)){
				String[] shu7=ssq.split(" ");
				//
				for(int y=0;y<shu7.length;y++){
					TextView tv_ssq=new TextView(BuyrecoredCorpDetails.this);																	
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
						ll_corpdetails_kjhm.addView(tv_ssq);
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
			ll_corpdetails_kjhm.removeAllViews();//清空列表
			if(ssq!=null&&!"".equals(ssq)){
				String[] shu7=ssq.split(" ");
				for(int y=0;y<shu7.length;y++){
					if(shu7!=null&&!"".equals(shu7)&&!"null".equals(shu7)){
						TextView tv_ssq=new TextView(BuyrecoredCorpDetails.this);																		
						//
						Drawable bj_d=this.getResources().getDrawable(R.drawable.black_ball);
						tv_ssq.setBackgroundDrawable(bj_d);
						tv_ssq.setGravity(Gravity.CENTER);
						tv_ssq.setTextColor(Color.WHITE);
						tv_ssq.setText(shu7[y]);												
						//
						ll_corpdetails_kjhm.addView(tv_ssq);
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
			TextView tv_k=new TextView(BuyrecoredCorpDetails.this);
			TextView tv_k2=new TextView(BuyrecoredCorpDetails.this);
			String values=chro.getData().get(k).getBetData_str();
			String types=chro.getData().get(k).getBetStr();
			String nums=chro.getData().get(k).getBet_multi()+"倍";
			tv_k.setText(" "+values);
			tv_k2.setText("     "+types+" "+nums);
			//
			ll_corpdetails_sj.addView(tv_k);
			ll_corpdetails_sj.addView(tv_k2);
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

			// if (fla.getData().getMultibuy_info().getNick_name() == null
			// || fla.getData().getMultibuy_info().getNick_name()
			// .equals("")) {
			corper.setText("匿名用户");
			// } else {
			// corper.setText(fla.getData().getMultibuy_info().getNick_name());
			// }
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
			break;
		}
	}
}
