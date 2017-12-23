package com.pkx.lottery.bean;

import com.pkx.lottery.utils.RandomBallsUtils;

import java.util.ArrayList;

public class SevenLotteryInfo {
	private ArrayList<SevenLotteryBet> bets;
	private int buyMulti = 1;
	private int mFollow = 1;

	public SevenLotteryInfo() {
	}

	public SevenLotteryInfo(ArrayList<SevenLotteryBet> bs, int multi, int follow) {
		bets = bs;
		buyMulti = multi;
		mFollow = follow;
	}

	public String getBetInfo() {
		// String info = "";
		ArrayList<SevenLotteryBet> singles = new ArrayList<SevenLotteryBet>();
		ArrayList<SevenLotteryBet> mutis = new ArrayList<SevenLotteryBet>();
		ArrayList<SevenLotteryBet> dts = new ArrayList<SevenLotteryBet>();
		for (SevenLotteryBet bet : bets) {
			if (bet.getType() == 0) {
				if (bet.getBalls().size() == 7) {
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
			for (SevenLotteryBet sbet : singles) {
				String betStr = "";
				ArrayList<Integer> balls = sbet.getBalls();
				balls = RandomBallsUtils.sort(balls);
				singleprice += sbet.getPrice();
				for (int i : balls) {
					if (i > 9) {
						betStr += i + ",";
					} else {
						betStr += "0" + i + ",";
					}
				}
				betStr = betStr.substring(0, betStr.length() - 1);
				singleStr += betStr + ";";

			}
		}
		int mutisprice = 0;
		// String mutiStr = "";
		ArrayList<String> mutiStrs = new ArrayList<String>();
		if (mutis.size() > 0) {
			for (SevenLotteryBet mbet : mutis) {
				mutisprice += mbet.getPrice();
				ArrayList<Integer> balls = mbet.getBalls();
				balls = RandomBallsUtils.sort(balls);
				String betStr = "";
				for (int i : balls) {
					if (i > 9) {
						betStr += i + ",";
					} else {
						betStr += "0" + i + ",";
					}
				}
				betStr = betStr.substring(0, betStr.length() - 1);
				// mutiStr += betStr + ";";
				betStr = "0@@2@@" + mbet.getPrice() + "@@" + mbet.getPrice()
						/ 2 + "@@" + buyMulti + "@@" + betStr + "@@@";
				mutiStrs.add(betStr);
			}
		}
		int dtprice = 0;
		String dtStr = "";
		ArrayList<String> dtStrs = new ArrayList<String>();
		if (dts.size() > 0) {
			for (SevenLotteryBet dtbet : dts) {
				String dStr = "";
				String tStr = "";
				dtprice += dtbet.getPrice();
				ArrayList<Integer> dballs = dtbet.getdBalls();
				ArrayList<Integer> tballs = dtbet.gettBalls();
				dballs = RandomBallsUtils.sort(dballs);
				tballs = RandomBallsUtils.sort(tballs);
				for (int i : dballs) {
					if (i > 9) {
						dStr += i + ",";
					} else {
						dStr += "0" + i + ",";
					}
				}
				for (int i : tballs) {
					if (i > 9) {
						tStr += i + ",";
					} else {
						tStr += "0" + i + ",";
					}
				}
				// dtStr += dStr.substring(0, dStr.length() - 1) + "|"
				// + tStr.substring(0, tStr.length() - 1) + ";";
				dtStr = "0@@1@@" + dtbet.getPrice() + "@@" + dtbet.getPrice()
						/ 2 + "@@" + buyMulti + "@@"
						+ dStr.substring(0, dStr.length() - 1) + "|"
						+ tStr.substring(0, tStr.length() - 1) + "@@@";
				dtStrs.add(dtStr);
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
		return (singleInfo + mutiInfo + dtInfo).substring(0, (singleInfo
				+ mutiInfo + dtInfo).length() - 3);

	}

	public int getTotalPrice() {// 总钱数，不含倍数
		int price = 0;
		for (SevenLotteryBet bet : bets) {
			price += bet.getPrice();
		}
		return price;
	}

	public int getBuy_amount() {// 总钱数，含倍数
		return getTotalPrice() * buyMulti;
	}

	public int getBuy_count() {// 总注数
		return getTotalPrice() / 2;
	}

}
