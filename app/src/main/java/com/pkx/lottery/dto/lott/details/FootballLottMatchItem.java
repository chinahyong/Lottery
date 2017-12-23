package com.pkx.lottery.dto.lott.details;

import com.google.gson.annotations.Expose;

public class FootballLottMatchItem {
	@Expose
	private String score;
	@Expose
	private String letBall;
	@Expose
	private String tz;
	@Expose
	private String mainTeam;
	@Expose
	private String guestTeam;
	@Expose
	private String match_id;
	@Expose
	private int dan;

	public int getDan() {
		return dan;
	}

	public String getLetBall() {
		return letBall;
	}

	public String getTz() {
		return tz;
	}

	public String getMainTeam() {
		return mainTeam;
	}

	public String getGuestTeam() {
		return guestTeam;
	}

	public String getMatch_id() {
		return match_id;
	}

	public String getScore() {
		return score;
	}

}
