package com.pkx.lottery.bean;

import com.pkx.lottery.R;

import java.util.HashMap;

public class ImageCode {
	private HashMap<String , Integer> hashmap = new HashMap<String , Integer>();   
	//
	public HashMap<String , Integer> imageCode(){
//		if (records.get(position).getLotteryType().equals("1")) {
//			holder.buyLott.setText("双色球:");
//			holder.lottImage.setImageResource(R.drawable.chromosphere);
		hashmap.put("1", R.drawable.chromosphere);
//		} else if (records.get(position).getLotteryType().equals("2")) {
//			holder.buyLott.setText("七乐彩:");
//			holder.lottImage.setImageResource(R.drawable.poker);
		hashmap.put("2", R.drawable.poker);
//		} else if (records.get(position).getLotteryType().equals("3")) {
//			holder.buyLott.setText("3D:");
//			holder.lottImage.setImageResource(R.drawable.dimension_3d);
		hashmap.put("3", R.drawable.dimension_3d);
//		} else if (records.get(position).getLotteryType().equals("4")) {
//			holder.buyLott.setText("湖北快三:");
//			holder.lottImage.setImageResource(R.drawable.dimention);
		hashmap.put("4", R.drawable.dimention);
//		} else if (records.get(position).getLotteryType().equals("50")) {
//			holder.buyLott.setText("竞足:");
//			holder.lottImage.setImageResource(R.drawable.football);
		hashmap.put("50", R.drawable.football);
//		} else if (records.get(position).getLotteryType().equals("60")) {
//			holder.buyLott.setText("竞篮:");
//			holder.lottImage.setImageResource(R.drawable.basket);
		hashmap.put("60", R.drawable.basket);
//		} else if (records.get(position).getLotteryType().equals("7")) {// 快乐十分
//			holder.buyLott.setText("快乐十分:");
//			holder.lottImage.setImageResource(R.drawable.happy_ten);
		hashmap.put("7", R.drawable.happy_ten);
//		} else if (records.get(position).getLotteryType().equals("6")) {// 重庆时时彩
//			holder.buyLott.setText("时时彩:");
//			holder.lottImage
//					.setImageResource(R.drawable.chongqing_lott);
		hashmap.put("6", R.drawable.chongqing_lott);
//		} else if (records.get(position).getLotteryType().equals("5")) {// 江苏快三
//			holder.buyLott.setText("江苏快三:");
//			holder.lottImage.setImageResource(R.drawable.dimention);
		hashmap.put("5", R.drawable.dimention);
//		}
		//
		return hashmap;
	}
	//
}
