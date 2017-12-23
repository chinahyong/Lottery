package com.pkx.lottery.bean;

import android.util.Log;

import java.util.ArrayList;

public class ChromBetinfo {
	private ArrayList<ChromosphereBet> mBets;
	private int buyMulti = 1;
	private int mFollow;

	public ChromBetinfo(ArrayList<ChromosphereBet> bets, int multi, int follow) {
		mBets = bets;
		buyMulti = multi;
		mFollow = follow + 1;
	}

	public ChromBetinfo() {
	}

	public String getBetInfo() {
		ArrayList<ChromosphereBet> singles = new ArrayList<ChromosphereBet>();
		ArrayList<ChromosphereBet> mutis = new ArrayList<ChromosphereBet>();
		ArrayList<ChromosphereBet> dts = new ArrayList<ChromosphereBet>();
		for (ChromosphereBet bet : mBets) {
			if (bet.getType() == 0) {
				if (bet.getRedBalls().size() == 6
						&& bet.getBlueBalls().size() == 1) {
					singles.add(bet);
				} else {
					mutis.add(bet);
				}
			} else {
				dts.add(bet);
			}
		}
		int singleprice = 0;
		String singleStr = "";
		if (singles.size() > 0) {
			for (ChromosphereBet sbet : singles) {
				singleprice += sbet.getPrice();
				singleStr += sbet.getBetBallString() + ";";
			}
		}
		ArrayList<String> mutiStrs = new ArrayList<String>();
		if (mutis.size() > 0) {
			for (ChromosphereBet mbet : mutis) {
				String inStr = "";
				inStr = "0@@2@@" + mbet.getPrice() + "@@" + mbet.getPrice() / 2
						+ "@@" + buyMulti + "@@" + mbet.getBetBallString()
						+ "@@@";
				Log.e("pkx", "复式:" + mbet.getBetBallString());
				mutiStrs.add(inStr);
			}
		}
		ArrayList<String> dtStrs = new ArrayList<String>();
		if (dts.size() > 0) {
			for (ChromosphereBet dtbet : dts) {
				String inStr = "";
				inStr = "0@@1@@" + dtbet.getPrice() + "@@" + dtbet.getPrice()
						/ 2 + "@@" + buyMulti + "@@" + dtbet.getBetBallString()
						+ "@@@";
				Log.e("pkx", "胆拖:" + dtbet.getBetBallString());
				dtStrs.add(inStr);
			}
		}
		String singleInfo = "";
		String mutiInfo = "";
		String dtInfo = "";
		if (singleStr.length() > 0) {
			singleInfo = "0@@0@@" + singleprice + "@@" + singleprice / 2 + "@@"
					+ buyMulti + "@@"
					+ singleStr.substring(0, singleStr.length() - 1) + "@@@";
		}
		if (mutiStrs.size() > 0) {
			for (String mu : mutiStrs) {
				mutiInfo += mu;
			}
		}
		if (dtStrs.size() > 0) {
			for (String dt : dtStrs) {
				dtInfo += dt;
			}
		}
		// if (mutiStr.length() > 0) {
		// mutiInfo = "0@@2@@" + mutisprice + "@@" + mutisprice / 2 + "@@"
		// + buyMulti + "@@"
		// + mutiStr.substring(0, mutiStr.length() - 1) + "@@@";
		// }
		// if (dtStr.length() > 0) {
		// dtInfo = "0@@1@@" + dtprice + "@@" + dtprice / 2 + "@@" + buyMulti
		// + "@@" + dtStr.substring(0, dtStr.length() - 1) + "@@@";
		// }
		return (singleInfo + mutiInfo + dtInfo).substring(0, (singleInfo
				+ mutiInfo + dtInfo).length() - 3);
	}

	public String getBetInfo1() {
		String betInfo = "";
		for (ChromosphereBet bet : mBets) {
			if (bet.getType() == 0) {
				// 单式复式
				if (bet.getBlueBalls().size() == 1
						&& bet.getRedBalls().size() == 6) {
					// 单式
					betInfo += "0@@0@@2@@1@@" + String.valueOf(buyMulti) + "@@"
							+ bet.getBetBallString() + "@@@";
				} else {
					// 复式
					betInfo += "0@@2@@" + String.valueOf(bet.getPrice()) + "@@"
							+ String.valueOf(bet.getPrice() / 2) + "@@"
							+ String.valueOf(buyMulti) + "@@"
							+ bet.getBetBallString() + "@@@";
				}
			} else {
				// 胆拖
				betInfo += "0@@1@@" + String.valueOf(bet.getPrice()) + "@@"
						+ String.valueOf(bet.getPrice() / 2) + "@@"
						+ String.valueOf(buyMulti) + "@@"
						+ bet.getBetBallString() + "@@@";
			}
		}
		return betInfo.substring(0, betInfo.length() - 3);
	}

	public int getTotalPrice() {// 总钱数，不含倍数
		int price = 0;
		for (ChromosphereBet bet : mBets) {
			price += bet.getPrice();
		}
		return price;
	}

	public int getBuy_amount() {// 总钱数，含倍数
		Log.e("pkx", "getBuy_amount---"+getTotalPrice()+" muti:"+buyMulti+"  follow"+mFollow);
		return getTotalPrice() * buyMulti * mFollow;
	}

	public int getBuy_count() {// 总注数
		return getTotalPrice() / 2;
	}

}
