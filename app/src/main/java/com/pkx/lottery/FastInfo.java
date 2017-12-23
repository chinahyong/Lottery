package com.pkx.lottery;

import com.pkx.lottery.bean.FastBet;
import com.pkx.lottery.bean.FastNewBet;

import java.util.ArrayList;

public class FastInfo {
	private ArrayList<FastNewBet> bets;
	private int buyMulti;
	private int mFollow;
	private ArrayList<FastBet> sumBets;
	private ArrayList<FastBet> twosameBets;
	private ArrayList<FastBet> threesameBets;
	private ArrayList<FastBet> twosamMutiBets;
	private ArrayList<FastBet> threeSameAllBets;
	private ArrayList<FastBet> threeUnsameBets;
	private ArrayList<FastBet> threeLianAllBets;
	private ArrayList<FastBet> twoUnsameBets;

	public FastInfo() {
	}

	public FastInfo(ArrayList<FastNewBet> bs, int multi, int follow) {
		bets = bs;
		buyMulti = multi;
		mFollow = follow;
		sumBets = new ArrayList<FastBet>();
		twosameBets = new ArrayList<FastBet>();
		threesameBets = new ArrayList<FastBet>();
		twosamMutiBets = new ArrayList<FastBet>();
		threeSameAllBets = new ArrayList<FastBet>();
		threeUnsameBets = new ArrayList<FastBet>();
		threeLianAllBets = new ArrayList<FastBet>();
		twoUnsameBets = new ArrayList<FastBet>();
		for (FastNewBet fnb : bs) {
			switch (fnb.getCurrentPage()) {
			case 0:
				ArrayList<Integer> balls = new ArrayList<Integer>();
				for (int i : fnb.getBalls()) {
					balls.add(i);
				}
				FastBet bet;
				for (int b : balls) {
					bet = new FastBet();
					bet.setCurrentPageId(0);
					bet.setBetNumber(b);
					sumBets.add(bet);
				}
				break;
			case 1:
				FastBet bet1;
				for (int e : fnb.getBalls()) {
					bet1 = new FastBet();
					bet1.setBetNumber(e);
					bet1.setCurrentPageId(1);
					twosameBets.add(bet1);
				}
				break;
			case 2:
				FastBet bet2;
				for (int s : fnb.getBalls()) {
					if (s == 0) {
						bet2 = new FastBet();
						bet2.setCurrentPageId(2);
						bet2.setBetNumber(0);
						threeSameAllBets.add(bet2);
					} else {
						if (s % 10 == 0) {// 二同号复选
							bet2 = new FastBet();
							bet2.setBetNumber(s);
							bet2.setCurrentPageId(2);
							twosamMutiBets.add(bet2);
						} else {
							bet2 = new FastBet();
							bet2.setBetNumber(s);
							bet2.setCurrentPageId(2);
							threesameBets.add(bet2);
						}
					}
				}
				break;
			case 3:
				FastBet bet3;
				for (int t : fnb.getBalls()) {
					if (t == 0) {
						bet3 = new FastBet();
						bet3.setBetNumber(0);
						bet3.setCurrentPageId(3);
						threeLianAllBets.add(bet3);
					} else {
						bet3 = new FastBet();
						bet3.setBetNumber(t);
						bet3.setCurrentPageId(3);
						threeUnsameBets.add(bet3);
					}
				}
				break;
			case 4:
				FastBet bet4;
				for (int u : fnb.getBalls()) {
					bet4 = new FastBet();
					bet4.setBetNumber(u);
					bet4.setCurrentPageId(4);
					twoUnsameBets.add(bet4);
				}
				break;
			}
		}
	}

	public String getBetInfo() {
		String sumStr = "";
		String twosameStr = "";
		String threesameStr = "";
		String twosameMutiStr = "";
		String threeSameAllStr = "";
		String threeUnsameStr = "";
		String threeLianAllStr = "";
		String twoUnsameStr = "";
		if (sumBets.size() > 0) {
			for (FastBet s : sumBets) {
				sumStr += "1@@0@@2@@1@@" + buyMulti + "@@" + s.getBetNumber()
						+ "@@@";
			}
		}
		if (twosameBets.size() > 0) {
			String betStr = "";
			for (FastBet w : twosameBets) {
				betStr += w.getBetNumber() + ";";
			}

			twosameStr += "2@@0@@" + twosameBets.size() * 2 + "@@"
					+ twosameBets.size() + "@@" + buyMulti + "@@"
					+ betStr.substring(0, betStr.length() - 1) + "@@@";
		}
		if (threesameBets.size() > 0) {
			String betStr = "";
			for (FastBet t : threesameBets) {
				betStr += t.getBetNumber() + ";";
			}
			threesameStr += "3@@0@@" + threesameBets.size() * 2 + "@@"
					+ threesameBets.size() + "@@" + buyMulti + "@@"
					+ betStr.substring(0, betStr.length() - 1) + "@@@";
		}
		if (twosamMutiBets.size() > 0) {
			for (FastBet s : twosamMutiBets) {
				twosameMutiStr += "2@@1@@2@@1@@" + buyMulti + "@@"
						+ s.getBetNumber() / 10 + "@@@";
			}
		}
		if (threeSameAllBets.size() > 0) {
			String ts = "";
			for (int i = 0; i < threeSameAllBets.size(); i++) {
				ts += "3@@2@@2@@1@@" + buyMulti
						+ "@@111,222,333,444,555,666@@@";
			}
			threeSameAllStr = ts;
		}
		if (threeUnsameBets.size() > 0) {
			String betStr = "";
			for (FastBet w : threeUnsameBets) {
				betStr += w.getBetNumber() + ";";
			}

			threeUnsameStr += "2@@0@@" + threeUnsameBets.size() * 2 + "@@"
					+ threeUnsameBets.size() + "@@" + buyMulti + "@@"
					+ betStr.substring(0, betStr.length() - 1) + "@@@";
		}
		if (threeLianAllBets.size() > 0) {
			String lian = "";
			for (FastBet w : threeLianAllBets) {
				lian += "6@@2@@2@@1@@" + buyMulti + "@@123,234,345,456@@@";
			}
			threeLianAllStr = lian;
		}
		if (twoUnsameBets.size() > 0) {
			for (FastBet u : twoUnsameBets) {
				twoUnsameStr += "4@@1@@2@@1@@1@@" + u.getBetNumber() + "@@@";
			}
		}
		return (sumStr + twosameStr + threesameStr + twosameMutiStr
				+ threeSameAllStr + threeUnsameStr + threeLianAllStr + twoUnsameStr)
				.substring(0, (sumStr + twosameStr + threesameStr
						+ twosameMutiStr + threeSameAllStr + threeUnsameStr
						+ threeLianAllStr + twoUnsameStr).length() - 3);
	}
	public int getTotalPrice() {// 总钱数，不含倍数
//		int price = 0;
//		for (FastBet bet : bets) {
//			price += bet.getPrice();
//		}
		int total=0;
		for(FastNewBet f:bets){
			total+=f.getBetsNumber()*2;
		}
		return total;
	}

	public int getBuy_amount() {// 总钱数，含倍数
		return getTotalPrice() * buyMulti * (mFollow+1);
	}

	public int getBuy_count() {// 总注数
		return getTotalPrice() / 2;
	}
}
