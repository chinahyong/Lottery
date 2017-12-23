package com.pkx.lottery.bean;

import java.io.Serializable;
import java.util.ArrayList;

import com.pkx.lottery.utils.RandomBallsUtils;

public class SevenLotteryBet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> balls;
	private ArrayList<Integer> dBalls;
	private ArrayList<Integer> tBalls;
	private int type;

	public ArrayList<Integer> getdBalls() {
		return dBalls;
	}

	public void setdBalls(ArrayList<Integer> dBalls) {
		this.dBalls = dBalls;
	}

	public ArrayList<Integer> gettBalls() {
		return tBalls;
	}

	public void settBalls(ArrayList<Integer> tBalls) {
		this.tBalls = tBalls;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	public int getPrice() {
		if (type == 0) {
			return RandomBallsUtils.getBallBets(7, balls.size()) * 2;
		} else {
			return RandomBallsUtils.getBallBets(7 - dBalls.size(),
					tBalls.size()) * 2;
		}
	}

	public ArrayList<Integer> getBalls() {
		return balls;
	}

	public void setBalls(ArrayList<Integer> balls) {
		this.balls = balls;
	}

	public String getBetStr() {
		if (type == 0) {
			String betStr = "";
			balls = RandomBallsUtils.sort(balls);
			for (int i : balls) {
				if (i > 9) {
					betStr += i + " ";
				} else {
					betStr += "0" + i + " ";
				}
			}
			betStr.substring(0, betStr.length() - 1);
			if (betStr.length() > 29) {
				return betStr.substring(0, 29) + "..";
			}
			return betStr;
		} else {
			String betStr = "";
			dBalls = RandomBallsUtils.sort(dBalls);
			for (int i : dBalls) {
				if (i > 9) {
					betStr += i + " ";
				} else {
					betStr += "0" + i + " ";
				}
			}
			betStr = "(" + betStr.substring(0, betStr.length() - 1) + ")";
			tBalls = RandomBallsUtils.sort(tBalls);
			for (int i : tBalls) {
				if (i > 9) {
					betStr += i + " ";
				} else {
					betStr += "0" + i + " ";
				}
			}
			betStr = betStr.substring(0, betStr.length() - 1);
			if (betStr.length() > 29) {
				return betStr.substring(0, 29) + "..";
			}
			return betStr;
		}

	}

}
