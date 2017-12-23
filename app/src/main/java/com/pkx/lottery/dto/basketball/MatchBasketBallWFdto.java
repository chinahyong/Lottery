package com.pkx.lottery.dto.basketball;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

public class MatchBasketBallWFdto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Expose
	private String sn_week;
	@Expose
	private String sn;
	@Expose
	private String status;
	@Expose
	private String sn_date;
	@Expose
	private String match_id;
	@Expose
	private String letBall;
	@Expose
	private String rowNumber;
	@Expose
	private String endTime;
	@Expose
	private ArrayList<String> odds;
	@Expose
	private String pollCode;
	@Expose
	private String local_id;
	@Expose
	private String preCast;
	@Expose
	private String mainTeam;
	@Expose
	private String mid;
	@Expose
	private String matchName;
	@Expose
	private String playCode;
	@Expose
	private String guestTeam;

	public void setDefaultData(){
		this.letBall="+0.00";
		this.endTime="12423";
		this.mainTeam="主队队";
		this.guestTeam="客队";
		ArrayList<String> od=new ArrayList<String>();
		od.add("0.00");
		od.add("0.00");
		this.odds=od;
	}
	public String getMatchInfoString() {
		return matchName + "*" + mainTeam + "*" + guestTeam + "*"+letBall+"*"+match_id;
	}
	public String getSn_week() {
		return sn_week;
	}

	public String getSn() {
		return sn;
	}

	public String getStatus() {
		return status;
	}

	public String getSn_date() {
		return sn_date;
	}

	public String getMatch_id() {
		return match_id;
	}

	public String getLetBall() {
		return letBall;
	}

	public String getRowNumber() {
		return rowNumber;
	}

	public String getEndTime() {
		return endTime;
	}

	public ArrayList<String> getOdds() {
		return odds;
	}

	public String getPollCode() {
		return pollCode;
	}

	public String getLocal_id() {
		return local_id;
	}

	public String getPreCast() {
		return preCast;
	}

	public String getMainTeam() {
		return mainTeam;
	}

	public String getMid() {
		return mid;
	}

	public String getMatchName() {
		return matchName;
	}

	public String getPlayCode() {
		return playCode;
	}

	public String getGuestTeam() {
		return guestTeam;
	}
}
