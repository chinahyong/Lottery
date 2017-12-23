package com.pkx.lottery.headerlist;
import java.io.Serializable;
import java.util.ArrayList;
import com.google.gson.annotations.Expose;
public class MixMatch implements Serializable{
	private static final long serialVersionUID = 1L;
//	@Expose
//	 private ArrayList<String> totalGoalSpStr;
//	@Expose
//	 private ArrayList<String> halfCourtSpStr;
//	public ArrayList<String> getTotalGoalSpStr() {
//		return totalGoalSpStr;
//	}
//	public ArrayList<String> getHalfCourtSpStr() {
//		return halfCourtSpStr;
//	}
	@Expose
	private String sn_week;
	@Expose
	private ArrayList<String> halfCourt_odds;
	@Expose
	private ArrayList<String> score_odds;
	@Expose
	private String sn;
	public String getSn_week() {
		return sn_week;
	}
	@Expose
	private String matchColor;
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
	private String update_time;
	@Expose
	private ArrayList<String> totalgoal_odds;
	@Expose
	private String mainTeam;
	@Expose
	private String mid;
	@Expose
	private String matchName;
	@Expose
	private String playCode;
	@Expose
	private ArrayList<String> spfodds;
	@Expose
	private String guestTeam;
	public String getWeekDay() {
		return sn_week;
	}
	public ArrayList<String> getHalfCourt_odds() {
		return halfCourt_odds;
	}
	public ArrayList<String> getScore_odds() {
		return score_odds;
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
	public String getUpdate_time() {
		return update_time;
	}
	public ArrayList<String> getTotalgoal_odds() {
		return totalgoal_odds;
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
	public ArrayList<String> getSpfodds() {
		return spfodds;
	}
	public String getGuestTeam() {
		return guestTeam;
	}

}
