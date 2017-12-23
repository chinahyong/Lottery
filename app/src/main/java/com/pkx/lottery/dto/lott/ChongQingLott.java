package com.pkx.lottery.dto.lott;

import com.pkx.lottery.utils.RandomBallsUtils;

import java.io.Serializable;
import java.util.ArrayList;

public class ChongQingLott implements Serializable {
	private static final long serialVersionUID = 1L;
	private int playType;
	private ArrayList<Integer> redBalls;
	private ArrayList<Integer> tenRedBalls;
	private ArrayList<Integer> hunRedBalls;
	private ArrayList<Integer> thouRedBalls;
	private ArrayList<Integer> wRedBalls;

	public int getPlayType() {
		return playType;
	}

	public void setPlayType(int playType) {
		this.playType = playType;
	}

	public ArrayList<Integer> getRedBalls() {
		return redBalls;
	}

	public void setRedBalls(ArrayList<Integer> redBalls) {
		this.redBalls = redBalls;
	}

	public ArrayList<Integer> getTenRedBalls() {
		return tenRedBalls;
	}

	public void setTenRedBalls(ArrayList<Integer> tenRedBalls) {
		this.tenRedBalls = tenRedBalls;
	}

	public ArrayList<Integer> getHunRedBalls() {
		return hunRedBalls;
	}

	public void setHunRedBalls(ArrayList<Integer> hunRedBalls) {
		this.hunRedBalls = hunRedBalls;
	}

	public ArrayList<Integer> getThouRedBalls() {
		return thouRedBalls;
	}

	public void setThouRedBalls(ArrayList<Integer> thouRedBalls) {
		this.thouRedBalls = thouRedBalls;
	}

	public ArrayList<Integer> getwRedBalls() {
		return wRedBalls;
	}

	public void setwRedBalls(ArrayList<Integer> wRedBalls) {
		this.wRedBalls = wRedBalls;
	}

	public int getPrice() {
		int result = 0;
		switch (playType) {
		case 1:
			if (redBalls == null || redBalls.get(0) != 0
					&& redBalls.get(1) != 0) {
				result = 2;
			} else {
				result = 0;
			}
			break;
		case 2:
			if (redBalls == null || redBalls.size() == 0) {
				result = 0;
			} else {
				result = redBalls.size() * 2;
			}
			break;
		case 3:
			if (redBalls == null || tenRedBalls == null || redBalls.size() == 0
					|| tenRedBalls.size() == 0) {
				result = 0;
			} else {
				result = tenRedBalls.size() * redBalls.size() * 2;
			}
			break;
		case 4:
			if (redBalls == null || redBalls.size() < 2) {
				result = 0;
			} else {
				result = redBalls.size() * (redBalls.size() - 1);
			}
			break;
		case 5:
			if (redBalls == null || tenRedBalls == null || redBalls.size() == 0
					|| tenRedBalls.size() == 0 || hunRedBalls.size() == 0) {
				result = 0;
			} else {
				result = hunRedBalls.size() * tenRedBalls.size()
						* redBalls.size() * 2;
			}
			break;
		case 6:
			if (redBalls != null) {
				switch (redBalls.size()) {
				case 0:
					result = 0;
					break;
				case 1:
					result = 0;
					break;
				case 2:
					result = 4;
					break;
				case 3:
					result = 12;
					break;
				case 4:
					result = 24;
					break;
				case 5:
					result = 40;
					break;
				case 6:
					result = 60;
					break;
				case 7:
					result = 84;
					break;
				case 8:
					result = 112;
					break;
				case 9:
					result = 144;
					break;
				case 10:
					result = 180;
					break;
				}
			}
			break;
		case 7:
			if (redBalls == null || redBalls.size() < 3) {
				result = 0;
			} else {
				result = RandomBallsUtils.getBallBets(3, redBalls.size()) * 2;
			}
			break;
		case 8:
			if (redBalls == null || tenRedBalls == null || hunRedBalls == null
					|| thouRedBalls == null || wRedBalls == null
					|| redBalls.size() == 0 || tenRedBalls.size() == 0
					|| hunRedBalls.size() == 0 || thouRedBalls.size() == 0
					|| wRedBalls.size() == 0) {
				result = 0;
			} else {
				result = wRedBalls.size() * thouRedBalls.size()
						* hunRedBalls.size() * tenRedBalls.size()
						* redBalls.size() * 2;
			}
			break;
		case 9:

			if (redBalls == null || tenRedBalls == null || hunRedBalls == null
					|| thouRedBalls == null || wRedBalls == null
					|| redBalls.size() == 0 || tenRedBalls.size() == 0
					|| hunRedBalls.size() == 0 || thouRedBalls.size() == 0
					|| wRedBalls.size() == 0) {
				result = 0;
			} else {
				result = wRedBalls.size() * thouRedBalls.size()
						* hunRedBalls.size() * tenRedBalls.size()
						* redBalls.size() * 2;
			}
			break;
		}
		return result;
	}

	private String getTypeString() {
		String str = "";
		switch (playType) {
		case 1:
			str = "大小单双:";
			break;
		case 2:
			str = "一星直选:";
			break;
		case 3:
			str = "二星直选:";
			break;
		case 4:
			str = "二星组选:";
			break;
		case 5:
			str = "三星直选:";
			break;
		case 6:
			str = "三星组三:";
			break;
		case 7:
			str = "三星组六:";
			break;
		case 8:
			str = "五星直选:";
			break;
		case 9:
			str = "五星通选:";
			break;
		}
		return str;
	}

	private String[] BIGSMALLSTRS = { "大", "小", "单", "双" };

	public CharSequence getBetInfo(int betDouble) {
		String betInfo = "";
		switch (playType) {
		case 1:// 大小单双
			betInfo = "0@@8@@2@@1@@" + String.valueOf(betDouble) + "@@"
					+ BIGSMALLSTRS[redBalls.get(0) - 1] + ","
					+ BIGSMALLSTRS[redBalls.get(1) - 1];
			break;
		case 2:
			if (redBalls.size() == 1) {// 一星直选单式
				betInfo = "0@@7@@2@@1@@" + String.valueOf(betDouble) + "@@"
						+ String.valueOf(redBalls.get(0));
				// 0@@7@@2@@1@@1@@9
			} else {// 一星直选复试
				String balls = "";
				for (int i : redBalls) {
					balls += String.valueOf(i);
				}
				betInfo = "1@@7@@" + String.valueOf(getPrice()) + "@@"
						+ String.valueOf(getPrice() / 2) + "@@"
						+ String.valueOf(betDouble) + "@@" + balls;
			}
			break;
		case 3:
			if (redBalls.size() == 1 && tenRedBalls.size() == 1) {// 二星星直选单式
				betInfo = "0@@5@@2@@1@@" + String.valueOf(betDouble) + "@@"
						+ String.valueOf(redBalls.get(0)) + ","
						+ String.valueOf(tenRedBalls.get(0));
			} else {// 复试
				String balls = "";
				for (int t : tenRedBalls) {
					balls += String.valueOf(t);
				}
				balls += ",";
				for (int g : redBalls) {
					balls += String.valueOf(g);
				}
				betInfo = "1@@5@@" + String.valueOf(getPrice()) + "@@"
						+ String.valueOf(getPrice() / 2) + "@@"
						+ String.valueOf(betDouble) + "@@" + balls;
			}
			break;
		case 4:
			if (redBalls.size() == 2) {// 二星组选单式
				betInfo = "0@@6@@2@@1@@" + String.valueOf(betDouble) + "@@"
						+ String.valueOf(redBalls.get(0)) + ","
						+ String.valueOf(redBalls.get(1));
			} else {// 复试
				String balls = "";
				for (int i : redBalls) {
					balls += String.valueOf(i);
				}
				betInfo = "1@@6@@" + String.valueOf(getPrice()) + "@@"
						+ String.valueOf(getPrice() / 2) + "@@"
						+ String.valueOf(betDouble) + "@@" + balls;
			}
			break;
		case 5:// 0@@2@@2@@1@@3@@4,4,4@@@1@@2@@8@@4@@3@@1,23,67
			if (redBalls.size() == 1 && tenRedBalls.size() == 1
					&& hunRedBalls.size() == 1) {// 三星直选单式
				betInfo = "0@@2@@2@@1@@" + String.valueOf(betDouble) + "@@"
						+ String.valueOf(hunRedBalls.get(0)) + ","
						+ String.valueOf(tenRedBalls.get(0)) + ","
						+ String.valueOf(redBalls.get(0));

			} else {
				String balls = "";
				for (int t : hunRedBalls) {
					balls += String.valueOf(t);
				}
				balls += ",";
				for (int t : tenRedBalls) {
					balls += String.valueOf(t);
				}
				balls += ",";
				for (int g : redBalls) {
					balls += String.valueOf(g);
				}
				betInfo = "1@@2@@" + String.valueOf(getPrice()) + "@@"
						+ String.valueOf(getPrice() / 2) + "@@"
						+ String.valueOf(betDouble) + "@@" + balls;

			}
			break;
		case 6:// 0@@3@@4@@2@@1@@1,2 三星组三
			String balls = "";
			for (int i : redBalls) {
				balls = balls + String.valueOf(i) + ",";

			}
			balls = balls.substring(0, balls.length() - 1);
			betInfo = "0@@3@@" + String.valueOf(getPrice()) + "@@"
					+ String.valueOf(getPrice() / 2) + "@@"
					+ String.valueOf(betDouble) + "@@" + balls;

			break;
		case 7:
			String balls6 = "";
			if (redBalls.size() == 3) {
				for (int i : redBalls) {
					balls6 = balls6 + String.valueOf(i) + ",";

				}
				balls6 = balls6.substring(0, balls6.length() - 1);
				betInfo = "1@@4@@" + String.valueOf(getPrice()) + "@@"
						+ String.valueOf(getPrice() / 2) + "@@"
						+ String.valueOf(betDouble) + "@@" + balls6;
			} else {
				for (int i : redBalls) {
					balls6 = balls6 + String.valueOf(i) + ",";

				}
				balls6 = balls6.substring(0, balls6.length() - 1);
				betInfo = "0@@4@@" + String.valueOf(getPrice()) + "@@"
						+ String.valueOf(getPrice() / 2) + "@@"
						+ String.valueOf(betDouble) + "@@" + balls6;
			}

			break;
		case 8:// 0@@0@@2@@1@@7@@1,1,1,1,2@@@1@@0@@4@@2@@7@@1,1,1,1,34
			if (getPrice() == 2) {
				betInfo = "0@@0@@2@@1@@" + String.valueOf(betDouble) + "@@"
						+ String.valueOf(wRedBalls.get(0)) + ","
						+ String.valueOf(thouRedBalls.get(0)) + ","
						+ String.valueOf(hunRedBalls.get(0)) + ","
						+ String.valueOf(tenRedBalls.get(0)) + ","
						+ String.valueOf(redBalls.get(0));
			} else {
				String ballszhi = "";
				for (int i : wRedBalls) {
					ballszhi += String.valueOf(i);
				}
				ballszhi += ",";
				for (int i : thouRedBalls) {
					ballszhi += String.valueOf(i);
				}
				ballszhi += ",";
				for (int i : hunRedBalls) {
					ballszhi += String.valueOf(i);
				}
				ballszhi += ",";
				for (int i : tenRedBalls) {
					ballszhi += String.valueOf(i);
				}
				ballszhi += ",";
				for (int i : redBalls) {
					ballszhi += String.valueOf(i);
				}
				betInfo = "1@@0@@" + String.valueOf(getPrice()) + "@@"
						+ String.valueOf(getPrice() / 2) + "@@"
						+ String.valueOf(betDouble) + "@@" + ballszhi;

			}
			break;
		case 9:
			betInfo = "0@@1@@2@@1@@" + String.valueOf(betDouble) + "@@"
					+ String.valueOf(wRedBalls.get(0)) + ","
					+ String.valueOf(thouRedBalls.get(0)) + ","
					+ String.valueOf(hunRedBalls.get(0)) + ","
					+ String.valueOf(tenRedBalls.get(0)) + ","
					+ String.valueOf(redBalls.get(0));
			break;
		}
		return betInfo;
	}

	public CharSequence getBetStr() {
		String result = "";
		switch (playType) {
		case 1:
			switch (redBalls.get(0)) {
			case 1:
				result = "大";
				break;
			case 2:
				result = "小";
				break;
			case 3:
				result = "单";
				break;
			case 4:
				result = "双";
				break;
			}
			switch (redBalls.get(1)) {
			case 1:
				result += ",大";
				break;
			case 2:
				result += ",小";
				break;
			case 3:
				result += ",单";
				break;
			case 4:
				result += ",双";
				break;
			}
			break;
		case 2:
		case 4:
		case 6:
		case 7:
			for (Integer i : redBalls) {
				result += String.valueOf(i) + " ";
			}
			break;
		case 3:
			for (Integer i : redBalls) {
				result += String.valueOf(i) + " ";
			}
			result += ",";
			for (Integer i : tenRedBalls) {
				result += String.valueOf(i) + " ";
			}
			break;
		case 5:
			for (Integer i : redBalls) {
				result += String.valueOf(i) + " ";
			}
			result += ",";
			for (Integer i : tenRedBalls) {
				result += String.valueOf(i) + " ";
			}
			result += ",";
			for (Integer i : hunRedBalls) {
				result += String.valueOf(i) + " ";
			}
			break;
		case 8:
		case 9:
			for (Integer i : wRedBalls) {
				result += String.valueOf(i) + " ";
			}
			result += ",";
			for (Integer i : thouRedBalls) {
				result += String.valueOf(i) + " ";
			}
			result += ",";
			for (Integer i : hunRedBalls) {
				result += String.valueOf(i) + " ";
			}
			result += ",";
			for (Integer i : tenRedBalls) {
				result += String.valueOf(i) + " ";
			}
			result += ",";
			for (Integer i : redBalls) {
				result += String.valueOf(i) + " ";
			}
			break;
		}
		return getTypeString() + result;
	}
}
