package com.pkx.lottery.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class FastNewBet implements Serializable {
	private static final long serialVersionUID = 1L;
	private int currentPage;
	private boolean isTong;

	public boolean isTong() {
		return isTong;
	}

	public void setTong(boolean isTong) {
		this.isTong = isTong;
	}

	private ArrayList<Integer> balls;

	public int getCurrentPage() {
		return currentPage;
	}

	public int getBetsNumber() {
		return balls.size();
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public ArrayList<Integer> getBalls() {
		return balls;
	}

	public void setBalls(ArrayList<Integer> balls) {
		this.balls = balls;
	}

	public String getBetString() {
		String returnSTR = "";
		for (int i : balls) {
			if (i != 0) {
				returnSTR = returnSTR + i + " ";
			}

		}
		switch (currentPage) {
		case 0:
			returnSTR = "[和值] [" + returnSTR.trim() + "]";
			break;
		case 1:
			returnSTR = "[二同号] [" + returnSTR.trim() + "]";
			break;
		case 2:
			boolean isTwoSame = false;
			for (int i : balls) {
				if (i % 10 == 0 && i != 0) {
					isTwoSame = true;
				}
				if (i == 0) {
					setTong(true);
				}
			}
			if (isTwoSame) {
				String str = "";
				for (int i : balls) {
					str = str + i / 10 + "* ";
				}
				returnSTR = "[二同号] [" + str.trim() + "]";
			} else if (balls.size() == 1 && balls.get(0) == 0) {
				returnSTR = "[三同号通选]";
			} else {
				if (isTong) {
					returnSTR = "[三同号] [" + returnSTR.trim() + "]," + "[三同号通选]";
				} else {
					returnSTR = "[三同号] [" + returnSTR.trim() + "]";
				}

			}

			break;
		case 3:
			boolean flagLian = false;
			ArrayList<Integer> bs = new ArrayList<Integer>();
			for (int b : balls) {
				bs.add(b);
			}
			for (int i = 0; i < bs.size(); i++) {
				if (bs.get(i) == 0) {
					flagLian = true;
					bs.remove(i);
					returnSTR = "";
					for (int j : bs) {
						if (j != 0) {
							returnSTR = returnSTR + j + " ";
						}
					}
				}
			}

			if (flagLian) {
				if (bs.size() > 0) {
					returnSTR = "[三不同号] [" + returnSTR.trim() + "][三连号通选]";
				} else {
					returnSTR = "[三连号通选]";
				}
			} else {
				returnSTR = "[三不同号] [" + returnSTR.trim() + "]";
			}
			break;
		case 4:
			returnSTR = "[二不同号] [" + returnSTR.trim() + "]";
			break;

		default:
			break;
		}
		return returnSTR;
	}
}
