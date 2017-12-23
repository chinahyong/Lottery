package com.pkx.lottery.bean;

import android.util.Log;

import com.pkx.lottery.utils.MySetUtil;
import com.pkx.lottery.utils.RandomBallsUtils;

import java.util.ArrayList;
import java.util.List;

public class DimentionInfo {
	private ArrayList<DimensionBet> bets;
	private int buyMulti = 1;
	private int mFollow = 0;

	public DimentionInfo() {
	}

	public DimentionInfo(ArrayList<DimensionBet> ds, int multi, int follow) {
		buyMulti = multi;
		Log.e("pkx", "传入:"+multi+"  赋值后："+buyMulti);
		mFollow = follow;
		bets = ds;
	}

	public String getBetInfo() {
		ArrayList<DimensionBet> directBets = new ArrayList<DimensionBet>();
		ArrayList<DimensionBet> bets3 = new ArrayList<DimensionBet>();
		ArrayList<DimensionBet> bets6 = new ArrayList<DimensionBet>();
		for (DimensionBet bet : bets) {
			if (bet.getType() == 0) {
				directBets.add(bet);
			} else if (bet.getType() == 1) {
				bets3.add(bet);
			} else {
				bets6.add(bet);
			}
		}
		String infod = "";
		int dprice = 0;
		if (directBets.size() > 0) {
			for (DimensionBet bet : directBets) {
				// 直选
				dprice += bet.getPrice();
				String dstr = "";
				for (int g : bet.getgBalls()) {
					for (int s : bet.gettBalls()) {
						for (int h : bet.gethBalls()) {
							dstr += String.valueOf(h) + "," + String.valueOf(s)
									+ "," + String.valueOf(g) + ";";
						}
					}
				}
				// infod += "1@@0@@" + bet.getPrice() + "@@" + bet.getPrice() /
				// 2
				// + "@@" + buyMulti + "@@"
				// + dstr.substring(0, dstr.length() - 1) + "@@@";
				infod += dstr.substring(0, dstr.length() - 1) + ";";
			}
		}
		String info3 = "";
		int price3 = 0;
		if (bets3.size() > 0) {
			for (DimensionBet bet : bets3) {
				// 组三
				price3 += bet.getPrice();
				String str3 = "";
				ArrayList<Integer> balls3 = bet.getBalls3();
				balls3 = RandomBallsUtils.sort(balls3);
				List<int[]> index = MySetUtil.set(2, balls3.size());
				for (int[] ins : index) {
					str3 += "" + balls3.get(ins[0] - 1) + ","
							+ balls3.get(ins[0] - 1) + ","
							+ balls3.get(ins[1] - 1) + ";"
							+ balls3.get(ins[0] - 1) + ","
							+ balls3.get(ins[1] - 1) + ","
							+ balls3.get(ins[1] - 1) + ";";
				}
				info3 += str3;
			}
		}

		String info6 = "";
		int price6 = 0;
		if (bets6.size() > 0) {
			for (DimensionBet bet : bets6) {
				// 组六
				price6 += bet.getPrice();
				String str6 = "";
				ArrayList<Integer> balls6 = bet.getBalls6();
				balls6 = RandomBallsUtils.sort(balls6);
				List<int[]> index6 = MySetUtil.set(3, balls6.size());
				for (int[] ins6 : index6) {
					str6 += "" + balls6.get(ins6[0] - 1) + ","
							+ balls6.get(ins6[1] - 1) + ","
							+ balls6.get(ins6[2] - 1) + ";";
				}
				info6 += str6;
			}
		}
		String infd = "";
		if (infod.length() > 0) {
			infd = "1@@0@@" + String.valueOf(dprice) + "@@"
					+ String.valueOf(dprice / 2) + "@@" + buyMulti + "@@"
					+ infod.substring(0, infod.length() - 1) + "@@@";
		}
		String inf3 = "";
		if (info3.length() > 0) {
			inf3 = "2@@0@@" + String.valueOf(price3) + "@@"
					+ String.valueOf(price3 / 2) + "@@" + buyMulti + "@@"
					+ info3.substring(0, info3.length() - 1) + "@@@";
		}
		String inf6 = "";
		if (info6.length() > 0) {
			inf6 = "3@@0@@" + String.valueOf(price6) + "@@"
					+ String.valueOf(price6 / 2) + "@@" + buyMulti + "@@"
					+ info6.substring(0, info6.length() - 1) + "@@@";
		}
		Log.e("pkx", "get info-----:"+buyMulti);
		return (infd + inf3 + inf6).substring(0,
				(infd + inf3 + inf6).length() - 3);
	}
	public int getTotalPrice() {// 总钱数，不含倍数
		int price = 0;
		for (DimensionBet bet : bets) {
			price += bet.getPrice();
		}
		return price;
	}

	public int getBuy_amount() {// 总钱数，含倍数
		return getTotalPrice() * buyMulti * (mFollow+1);
	}

	public int getBuy_count() {// 总注数
		return getTotalPrice() / 2;
	}
}
