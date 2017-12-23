package com.pkx.lottery.headerlist;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class HalfMatch implements Serializable {
	private static final long serialVersionUID = 1L;
	@Expose
	private String sn_week;
	@Expose
	private String sn;
	@Expose
	private String matchColor;
	@Expose
	private String status;
	@Expose
	private String sn_date;
	@Expose
	private ArrayList<String> half_odds;
	@Expose
	private String match_id;
	@Expose
	private String letBall;
	@Expose
	private String rowNumber;
	@Expose
	private String endTime;
	@Expose
	private String pollCode;
	@Expose
	private String local_id;
	@Expose
	private String update_time;
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

	public String getSn_week() {
		return sn_week;
	}

	public String getSn() {
		return sn;
	}

	public String getMatchColor() {
		return matchColor;
	}

	public String getStatus() {
		return status;
	}

	public String getSn_date() {
		return sn_date;
	}

	public ArrayList<String> getHalf_odds() {
		return half_odds;
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

	public String getPollCode() {
		return pollCode;
	}

	public String getLocal_id() {
		return local_id;
	}

	public String getUpdate_time() {
		return update_time;
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
