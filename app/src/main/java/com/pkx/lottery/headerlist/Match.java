package com.pkx.lottery.headerlist;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

public class Match implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> score_odds;
	private ArrayList<String> halfCourt_odds;
	private ArrayList<String> totalgoal_odds;
	@Expose
	private String letBall=" ";
	@Expose
	private String rowNumber;
	@Expose
	private String endTime;
	@Expose
	private String pollCode;// 选号方式
	@Expose
	private String local_id;
	@Expose
	private String update_time;
	@Expose
	private String mainTeam;// 主队
	@Expose
	private String guestTeam;// 客队
	@Expose
	private ArrayList<String> odds;// 让球赔率
	@Expose
	private ArrayList<String> spfodds;// 非让球赔率
	@Expose
	private String mid;
	@Expose
	private String playCode;
	@Expose
	private String matchColor;

	// private boolean isHandip;// 是否让球
	// private boolean isNoHandip;// 是否非让球

	// private int handipNumber;// 让球数
	@Expose
	private String sn_week;
	@Expose
	private String sn;
	@Expose
	private String status;
	@Expose
	private String sn_date;// 时间
	@Expose
	private String matchName;// 比赛名
	@Expose
	private String match_id;
	public void setDefaultData(){
		this.endTime="12423";
		this.guestTeam="客队";
		ArrayList<String> od=new ArrayList<String>();
		od.add("1.25");
		od.add("1.26");
		od.add("1.27");
		this.odds=od;
		this.spfodds=od;
	}
	public void setSn_week(String sn_week) {
		this.sn_week = sn_week;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setSn_date(String sn_date) {
		this.sn_date = sn_date;
	}

	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}

	public void setMatch_id(String match_id) {
		this.match_id = match_id;
	}

	public void setLetBall(String letBall) {
		this.letBall = letBall;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setPollCode(String pollCode) {
		this.pollCode = pollCode;
	}

	public void setLocal_id(String local_id) {
		this.local_id = local_id;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public void setMainTeam(String mainTeam) {
		this.mainTeam = mainTeam;
	}

	public void setGuestTeam(String guestTeam) {
		this.guestTeam = guestTeam;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public void setPlayCode(String playCode) {
		this.playCode = playCode;
	}

	public void setMatchColor(String matchColor) {
		this.matchColor = matchColor;
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

	public String getMatchName() {
		return matchName;
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

	public String getGuestTeam() {
		return guestTeam;
	}

	public ArrayList<String> getOdds() {
		return odds;
	}

	public ArrayList<String> getSpfodds() {
		return spfodds;
	}

	public String getMid() {
		return mid;
	}

	public String getPlayCode() {
		return playCode;
	}

	public String getMatchColor() {
		return matchColor;
	}

	public String getId() {
		return match_id;
	}

	public String getMatchInfoString() {
		return matchName + "*" + mainTeam + "*" + guestTeam + "*"+letBall+"*"+match_id;
	}

	public String getName() {
		return matchName;
	}

	public void setName(String name) {
		this.matchName = name;
	}

	public String getTime() {
		return sn_date;
	}

	public void setTime(String time) {
		this.sn_date = time;
	}

	public String getHost() {
		return mainTeam;
	}

	public void setHost(String host) {
		this.mainTeam = host;
	}

	public String getGuest() {
		return guestTeam;
	}

	public void setGuest(String guest) {
		this.guestTeam = guest;
	}

	public ArrayList<String> getHandipLines() {
		return odds;
	}

	public void setHandipLines(ArrayList<String> handipLines) {
		this.odds = handipLines;
	}

	public ArrayList<String> getNoHandipLines() {
		return spfodds;
	}

	public void setNoHandipLines(ArrayList<String> noHandipLines) {
		this.spfodds = noHandipLines;
	}

	public boolean isHandip() {
		if (odds.size() == 3) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isNoHandip() {
		if (spfodds.size() == 3) {
			return true;
		} else {
			return false;
		}
	}

	public ArrayList<String> getScore_odds() {
		return score_odds;
	}

	public void setScore_odds(ArrayList<String> score_odds) {
		this.score_odds = score_odds;
	}

	public ArrayList<String> getHalfCourt_odds() {
		return halfCourt_odds;
	}

	public void setHalfCourt_odds(ArrayList<String> halfCourt_odds) {
		this.halfCourt_odds = halfCourt_odds;
	}

	public ArrayList<String> getTotalgoal_odds() {
		return totalgoal_odds;
	}

	public void setTotalgoal_odds(ArrayList<String> totalgoal_odds) {
		this.totalgoal_odds = totalgoal_odds;
	}

	public void setOdds(ArrayList<String> odds) {
		this.odds = odds;
	}

	public void setSpfodds(ArrayList<String> spfodds) {
		this.spfodds = spfodds;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
