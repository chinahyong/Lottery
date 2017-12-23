package com.pkx.lottery.bean;

import com.pkx.lottery.utils.RandomBallsUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class ChromosphereBet implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Integer> redBalls;
	private ArrayList<Integer> blueBalls;
	private ArrayList<Integer> danRedBalls;
	private ArrayList<Integer> tuoRedBalls;
	private ArrayList<Integer> dantuoBlueBalls;
	private int type;

	public ArrayList<Integer> getDanRedBalls() {
		return danRedBalls;
	}

	public void setDanRedBalls(ArrayList<Integer> danRedBalls) {
		this.danRedBalls = danRedBalls;
	}

	public ArrayList<Integer> getTuoRedBalls() {
		return tuoRedBalls;
	}

	public void setTuoRedBalls(ArrayList<Integer> tuoRedBalls) {
		this.tuoRedBalls = tuoRedBalls;
	}

	public ArrayList<Integer> getDantuoBlueBalls() {
		return dantuoBlueBalls;
	}

	public void setDantuoBlueBalls(ArrayList<Integer> dantuoBlueBalls) {
		this.dantuoBlueBalls = dantuoBlueBalls;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getBetBallString() {
		String betStr = "";
		if (type == 0) {
			redBalls = RandomBallsUtils.sort(redBalls);
			for (int i : redBalls) {
				if (i > 9) {
					betStr += i + ",";
				} else {
					betStr += "0" + i + ",";
				}
			}
			betStr = betStr.substring(0, betStr.length() - 1) + "|";
			blueBalls = RandomBallsUtils.sort(blueBalls);
			for (int i : blueBalls) {
				if (i > 9) {
					betStr += i + ",";
				} else {
					betStr += "0" + i + ",";
				}
			}
			return betStr.substring(0, betStr.length() - 1);
		} else {
			String dan = "";
			String tuo = "";
			danRedBalls = RandomBallsUtils.sort(danRedBalls);
			for (int i : danRedBalls) {
				if (i > 9) {
					dan += i + ",";
				} else {
					dan += "0" + i + ",";
				}
			}
			dan = dan.substring(0, dan.length() - 1) + "|";
			tuoRedBalls = RandomBallsUtils.sort(tuoRedBalls);
			for (int t : tuoRedBalls) {
				if (t > 9) {
					tuo += t + ",";
				} else {
					tuo += "0" + t + ",";
				}
			}
			tuo = tuo.substring(0, tuo.length() - 1) + "|";
			dantuoBlueBalls = RandomBallsUtils.sort(dantuoBlueBalls);
			for (int dt : dantuoBlueBalls) {
				if (dt > 9) {
					betStr += dt + ",";
				} else {
					betStr += "0" + dt + ",";
				}
			}
			return dan+tuo+betStr.substring(0, betStr.length() - 1);
		}
	}

	public String getRedBallString() {
		if (type == 0) {
			String betStr = "";
			redBalls = RandomBallsUtils.sort(redBalls);
			for (int i : redBalls) {
				if (i > 9) {
					betStr += i + " ";
				} else {
					betStr += "0" + i + " ";
				}

			}
			if (betStr.trim().length() > 26) {
				return "红球:" + betStr.substring(0, 26) + "...";
			}
			return "红球:" + betStr.trim();
		} else {
			String dan = "";
			String tuo = "";
//			String dtblue = "";
			danRedBalls = RandomBallsUtils.sort(danRedBalls);
			for (int i : danRedBalls) {
				if (i > 9) {
					dan += i + " ";
				} else {
					dan += "0" + i + " ";
				}
			}
			tuoRedBalls = RandomBallsUtils.sort(tuoRedBalls);
			for (int t : tuoRedBalls) {
				if (t > 9) {
					tuo += t + " ";
				} else {
					tuo += "0" + t + " ";
				}
			}
			if (tuo.trim().length() > 14) {
				return "红球:(" + dan.trim() + ")" + tuo.trim().substring(0, 14)
						+ "...";
			}
			return "红球:(" + dan.trim() + ")" + tuo.trim();
		}

	}

	public String getBlueBallString() {
		String betStr = "";
		if (type == 0) {
			blueBalls = RandomBallsUtils.sort(blueBalls);
			for (int i : blueBalls) {
				if (i > 9) {
					betStr += i + " ";
				} else {
					betStr += "0" + i + " ";
				}
			}
		} else {
			dantuoBlueBalls = RandomBallsUtils.sort(dantuoBlueBalls);
			for (int dt : dantuoBlueBalls) {
				if (dt > 9) {
					betStr += dt + " ";
				} else {
					betStr += "0" + dt + " ";
				}
			}
		}
		return "蓝球:" + betStr;
	}

	public int getPrice() {
		if (type == 0) {
			return RandomBallsUtils.getBallBets(6, redBalls.size())
					* blueBalls.size() * 2;
		} else {
			return RandomBallsUtils.getBallBets(6 - danRedBalls.size(),
					tuoRedBalls.size()) * dantuoBlueBalls.size() * 2;
		}

	}

	// public int getBetNum(){
	// return getPrice()/2;
	// }
	public ArrayList<Integer> getRedBalls() {
		return redBalls;
	}

	public void setRedBalls(ArrayList<Integer> redBalls) {
		this.redBalls = redBalls;
	}

	public ArrayList<Integer> getBlueBalls() {
		return blueBalls;
	}

	public void setBlueBalls(ArrayList<Integer> blueBalls) {
		this.blueBalls = blueBalls;
	}
}
