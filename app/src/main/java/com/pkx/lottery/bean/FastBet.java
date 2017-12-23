package com.pkx.lottery.bean;

import java.io.Serializable;

public class FastBet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int currentPageId;
	private int betNumber;

	public int getCurrentPageId() {
		return currentPageId;
	}

	public void setCurrentPageId(int currentPageId) {
		this.currentPageId = currentPageId;
	}


	public int getBetNumber() {
		return betNumber;
	}

	public void setBetNumber(int betNumber) {
		this.betNumber = betNumber;
	}
}
