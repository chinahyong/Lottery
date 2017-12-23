package com.pkx.lottery.bean;

import com.pkx.lottery.utils.RandomBallsUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class DimensionBet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type;
	private ArrayList<Integer> balls3;
	private ArrayList<Integer> balls6;
	private ArrayList<Integer> gBalls;
	private ArrayList<Integer> hBalls;
	private ArrayList<Integer> tBalls;

	public String getBetString() {
		String returnStr = "";
		switch (type) {
		case 0:
			// 直选
			hBalls = RandomBallsUtils.sort(hBalls);
			for (int h : hBalls) {
					returnStr += h + " ";

			}
			tBalls = RandomBallsUtils.sort(tBalls);
			returnStr = returnStr.trim() + ",";
			for (int t : tBalls) {
					returnStr += t + " ";
			}
			gBalls = RandomBallsUtils.sort(gBalls);
			returnStr = returnStr.trim() + ",";
			for (int b : gBalls) {
					returnStr += b + " ";
				
			}
			returnStr = "[直选]" + returnStr;
			break;
		case 1:
			// 组三
			balls3 = RandomBallsUtils.sort(balls3);
			for (int b : balls3) {
					returnStr += b + " ";
			}
			returnStr = "[组三]" + returnStr;
			break;
		case 2:
			// 组六
			balls6 = RandomBallsUtils.sort(balls6);
			for (int b : balls6) {
					returnStr += b + " ";
			}
			returnStr = "[组六]" + returnStr;
			break;

		default:
			break;
		}
		return returnStr.trim();
	}

	public int getPrice() {
		int price = 0;
		switch (type) {
		case 0:
			price = gBalls.size() * tBalls.size() * hBalls.size() * 2;
			break;
		case 1:
			price = RandomBallsUtils.getBallBets(2, balls3.size()) * 4;
			break;
		case 2:
			price = RandomBallsUtils.getBallBets(3, balls6.size()) * 2;
			break;
		}
		return price;
	}

	public int getBetNumber() {
		int num = 0;
		switch (type) {
		case 0:
			num = gBalls.size() * tBalls.size() * hBalls.size();
			break;
		case 1:
			num = RandomBallsUtils.getBallBets(2, balls3.size()) * 2;
			break;
		case 2:
			num = RandomBallsUtils.getBallBets(3, balls6.size());
			break;
		}
		return num;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ArrayList<Integer> getBalls3() {
		return balls3;
	}

	public void setBalls3(ArrayList<Integer> balls3) {
		this.balls3 = balls3;
	}

	public ArrayList<Integer> getBalls6() {
		return balls6;
	}

	public void setBalls6(ArrayList<Integer> balls6) {
		this.balls6 = balls6;
	}

	public ArrayList<Integer> getgBalls() {
		return gBalls;
	}

	public void setgBalls(ArrayList<Integer> gBalls) {
		this.gBalls = gBalls;
	}

	public ArrayList<Integer> gethBalls() {
		return hBalls;
	}

	public void sethBalls(ArrayList<Integer> hBalls) {
		this.hBalls = hBalls;
	}

	public ArrayList<Integer> gettBalls() {
		return tBalls;
	}

	public void settBalls(ArrayList<Integer> tBalls) {
		this.tBalls = tBalls;
	}
}
